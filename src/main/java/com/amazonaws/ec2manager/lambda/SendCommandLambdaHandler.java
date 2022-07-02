package com.amazonaws.ec2manager.lambda;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.services.lambda.runtime.Context;

import software.amazon.awssdk.services.ssm.SsmClient;
import software.amazon.awssdk.services.ssm.model.CommandInvocation;
import software.amazon.awssdk.services.ssm.model.CommandStatus;
import software.amazon.awssdk.services.ssm.model.SendCommandRequest;
import software.amazon.awssdk.services.ssm.model.SendCommandResponse;

public class SendCommandLambdaHandler extends AbstractLambdaHandler {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) throws IOException {

		String inputString = toString(inputStream);
		logger.info("Input event received: {}", inputString);

		writeOutput(outputStream, "Command executed");
	}

	// https://stackoverflow.com/questions/58627387/aws-java-sdk-running-a-command-using-ssm-on-ec2-instances
	private String sendCommand() {

		// Command to be run
		List<String> commandList = new ArrayList<String>();
		commandList.add("ls -l");
		Map<String, List<String>> params = new HashMap<String, List<String>>();
		params.put("commands", commandList);

		// CommandStatus
		String commandStatus = "";
		try (SsmClient ssmClient = getSsmClient();) {

			SendCommandRequest commandRequest = SendCommandRequest.builder().instanceIds("i-0c2cf0542b9dccbbb")
					.documentName("AWS-RunShellScript").parameters(params).build();
			SendCommandResponse commandResponse = ssmClient.sendCommand(commandRequest);
			commandStatus = commandResponse.command().statusAsString();
			String commandId = commandResponse.command().commandId();

			// Wait until the command ends
			do {
				List<CommandInvocation> invocations = ssmClient.listCommandInvocations().commandInvocations();
				if (invocations != null) {
					for (CommandInvocation invocation : invocations) {
						if (commandId.equals(invocation.commandId())) {
							commandStatus = invocation.statusAsString();
							logger.info("Command status is {}", commandStatus);
							break;
						}
					}
				}

				if (!CommandStatus.PENDING.toString().equals(commandStatus)
						&& !CommandStatus.IN_PROGRESS.toString().equals(commandStatus)) {
					break;
				}

				try {
					logger.info("Wait for 10 seconds before checking status again");
					TimeUnit.SECONDS.sleep(10);
				} catch (InterruptedException e) {
					// Handle not being able to sleep
				}
			} while (CommandStatus.PENDING.toString().equals(commandStatus)
					|| CommandStatus.IN_PROGRESS.toString().equals(commandStatus));

		} catch (Exception e) {
			logger.error("SsmException occurs: ", e);
			commandStatus = e.getMessage();
		}

		return commandStatus;

	}

	public static void main(String[] args) {

		SendCommandLambdaHandler request = new SendCommandLambdaHandler();
		String commandStatus = request.sendCommand();
		System.out.print("Command Status: " + commandStatus);
	}
}
