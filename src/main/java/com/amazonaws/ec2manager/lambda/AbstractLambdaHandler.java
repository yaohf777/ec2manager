package com.amazonaws.ec2manager.lambda;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.ec2manager.utility.CommonUtility;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;

public abstract class AbstractLambdaHandler implements RequestStreamHandler {

	Logger logger = LoggerFactory.getLogger(this.getClass());

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
