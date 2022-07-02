package com.amazonaws.ec2manager.lambda;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.ec2manager.utility.CommonUtility;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;

import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.core.client.config.ClientOverrideConfiguration;
import software.amazon.awssdk.core.retry.RetryPolicy;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ssm.SsmClient;

public abstract class AbstractLambdaHandler implements RequestStreamHandler {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	protected SsmClient getSsmClient() {

		ClientOverrideConfiguration configuration = ClientOverrideConfiguration.builder()//
				.retryPolicy(RetryPolicy.builder().numRetries(2).build())//
				.apiCallTimeout(Duration.ofMinutes(3)).build();
		return SsmClient.builder().overrideConfiguration(configuration)//
				.region(Region.US_EAST_1)//
				.credentialsProvider(EnvironmentVariableCredentialsProvider.create())//
				.build();

//		return SsmClient.builder().region(Region.US_EAST_1)
//				.credentialsProvider(EnvironmentVariableCredentialsProvider.create()).build();
	}

	protected void writeOutput(OutputStream outputStream, Object object) {

		PrintWriter writer = new PrintWriter(
				new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8)));
		try {

			writer.write(convert2Json(object));
			if (writer.checkError()) {
				logger.warn("WARNING: Writer encountered an error.");
			}
		} catch (Exception exception) {
			logger.error("Exception write response: ", exception);
		}
	}

	protected String convert2Json(Object object) {

		return CommonUtility.convertEntity2Json(object);
	}
}
