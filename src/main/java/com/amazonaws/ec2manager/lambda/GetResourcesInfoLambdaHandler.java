package com.amazonaws.ec2manager.lambda;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.ec2manager.utility.CommonUtility;
import com.amazonaws.services.lambda.runtime.Context;

import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ssm.SsmClient;
import software.amazon.awssdk.services.ssm.model.DescribeInstanceInformationRequest;
import software.amazon.awssdk.services.ssm.model.InstanceInformation;
import software.amazon.awssdk.services.ssm.model.SsmException;

@SuppressWarnings("rawtypes")
public class GetResourcesInfoLambdaHandler extends AbstractLambdaHandler {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) throws IOException {

		HashMap event = CommonUtility.parseJsonString(inputStream, HashMap.class);
		logger.info("Input event received: {}", convert2Json(event));

		writeOutput(outputStream, getInstancesInfo());
	}

	private List<InstanceInfo> getInstancesInfo() {

		Region region = Region.US_EAST_1;

		List<InstanceInfo> instanceInfoList = new ArrayList<InstanceInfo>();
		try (SsmClient ssmClient = SsmClient.builder().region(region)
				.credentialsProvider(EnvironmentVariableCredentialsProvider.create()).build();) {

			// describeInstanceProperties -> describeInstanceInformation
			DescribeInstanceInformationRequest request = DescribeInstanceInformationRequest.builder().maxResults(10)
					.build();
			List<InstanceInformation> instanceInformationList = ssmClient.describeInstanceInformation(request)
					.instanceInformationList();
			if (instanceInformationList != null) {
				for (InstanceInformation instanceInformation : instanceInformationList) {
					instanceInfoList.add(new InstanceInfo(instanceInformation));
				}
			} else {
				logger.info("No managed nodes found");
			}

		} catch (SsmException e) {

			logger.error("SsmException occurs: ", e);
		}

		return instanceInfoList;
	}
}
