package com.amazonaws.lambda.ec2manager.utility;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

// https://github.com/awsdocs/aws-lambda-developer-guide/tree/main/sample-apps/java-basic/src/main/java/example
// https://github.com/awsdocs/aws-lambda-developer-guide/tree/main/sample-apps/blank-java/src/main/java/example
public class HandlerExample implements RequestHandler<String, String> {

//	LambdaAsyncClient lambdaClient = LambdaAsyncClient.create();

	@Override
	public String handleRequest(String inputString, Context context) {

		LambdaLogger logger = context.getLogger();

//		// Call Lambda API
//		logger.log("Getting account settings");
//		CompletableFuture<GetAccountSettingsResponse> accountSettings = lambdaClient
//				.getAccountSettings(GetAccountSettingsRequest.builder().build());
//		// process Lambda API response
//		try {
//			GetAccountSettingsResponse settings = accountSettings.get();
//			String accountUsage = convert2Json(settings.accountUsage());
//			logger.log("Account usage: " + accountUsage);
//		} catch (Exception e) {
//			logger.log("Input: " + e.getMessage());
//		}
		
		// HashMap eventMap = CommonUtility.parseJsonString(inputString, HashMap.class);
		logger.log("Input: " + inputString);
		return inputString;
	}
}