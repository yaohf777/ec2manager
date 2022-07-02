package com.amazonaws.ec2manager.lambda;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.client.config.ClientOverrideConfiguration;
import software.amazon.awssdk.core.retry.RetryPolicy;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ssm.SsmClient;

public abstract class AbstractLambdaHandler implements RequestStreamHandler {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	Gson gson = new GsonBuilder().setPrettyPrinting().create();
	
	protected SsmClient getSsmClient() {

		// Replace EnvironmentVariableCredentialsProvider with StaticCredentialsProvider
		// since we are not allowed to change Access Key EnvironmentVariable in AWS
		// console
		ClientOverrideConfiguration configuration = ClientOverrideConfiguration.builder()//
				.retryPolicy(RetryPolicy.builder().numRetries(2).build())//
				.apiCallTimeout(Duration.ofMinutes(3)).build();
		return SsmClient.builder().overrideConfiguration(configuration)//
				.region(Region.US_EAST_1)//
				.credentialsProvider(this.getAwsCredentialsProvider())//
				.build();

//		return SsmClient.builder().region(Region.US_EAST_1)
//				.credentialsProvider(EnvironmentVariableCredentialsProvider.create()).build();
	}

	protected AwsCredentialsProvider getAwsCredentialsProvider() {

		// Replace EnvironmentVariableCredentialsProvider with StaticCredentialsProvider
		// since we are not allowed to change Access Key EnvironmentVariable in AWS
		// console
		// EnvironmentVariableCredentialsProvider.create()
		Map<String, String> envVariableMap = System.getenv();
		String accessKeyId = envVariableMap.get("AWS_ACCESS_KEY_ID_CUS");
		String secretAccessKey = envVariableMap.get("AWS_SECRET_ACCESS_KEY_CUS");
		return StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKeyId, secretAccessKey));

	}

	protected String toString(InputStream inputStream) {

		// IOUtils.toString(is, Charset.defaultCharset());
		return new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8)).lines()
				.collect(Collectors.joining("\n"));
	}
	
	protected void writeOutput(OutputStream outputStream, String outputString) {

		PrintWriter writer = new PrintWriter(
				new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8)));
		try {

			writer.write(outputString);
			if (writer.checkError()) {
				logger.warn("WARNING: Writer encountered an error.");
			}
		} catch (Exception exception) {
			logger.error("Exception write response: ", exception);
		}
	}
	
//	protected String convert2Json(Object object) {
//
//		return CommonUtility.convertEntity2Json(object);
//	}
}
