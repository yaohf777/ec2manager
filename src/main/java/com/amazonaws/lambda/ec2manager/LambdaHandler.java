package com.amazonaws.lambda.ec2manager;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.lambda.ec2manager.utility.CommonUtility;
import com.amazonaws.services.lambda.runtime.Context;

@SuppressWarnings("rawtypes")
public class LambdaHandler extends AbstractLambdaHandler {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) throws IOException {

		//logger.info("ENVIRONMENT VARIABLES: {}", convert2Json(System.getenv()));

		HashMap event = CommonUtility.parseJsonString(inputStream, HashMap.class);
		logger.info("Input event: {}" ,convert2Json(event));
	
		writeOutput(outputStream, "Task successfully completed");
	}
}
