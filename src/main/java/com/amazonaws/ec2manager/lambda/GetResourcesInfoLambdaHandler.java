package com.amazonaws.ec2manager.lambda;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.services.lambda.runtime.Context;

import software.amazon.awssdk.services.ssm.SsmClient;
import software.amazon.awssdk.services.ssm.model.DescribeInstanceInformationRequest;
import software.amazon.awssdk.services.ssm.model.InstanceInformation;
import software.amazon.awssdk.services.ssm.model.SsmException;

public class GetResourcesInfoLambdaHandler extends AbstractLambdaHandler {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) throws IOException {

		String inputString = toString(inputStream);
		logger.info("Input event received: {}", inputString);
		// HashMap event = gson.fromJson(inputString, HashMap.class);

		writeOutput(outputStream, getInstancesInfoJson());
	}

	private String getInstancesInfoJson() {

		String jsonStr = null;
		try (SsmClient ssmClient = getSsmClient();) {

			// describeInstanceProperties -> describeInstanceInformation
			DescribeInstanceInformationRequest request = DescribeInstanceInformationRequest.builder().maxResults(10)
					.build();
			List<InstanceInformation> instanceInfoList = ssmClient.describeInstanceInformation(request)
					.instanceInformationList();
			if (instanceInfoList == null) {
				logger.info("No managed nodes found");
			} else {
				jsonStr = gson.toJson(instanceInfoList);
			}
		} catch (SsmException e) {
			logger.error("SsmException occurs: ", e);
		}

		return jsonStr;
	}
	
	public static void main(String[] args) {

		GetResourcesInfoLambdaHandler request = new GetResourcesInfoLambdaHandler();
		String jsonString = request.getInstancesInfoJson();
		System.out.println(jsonString);
	}
}
