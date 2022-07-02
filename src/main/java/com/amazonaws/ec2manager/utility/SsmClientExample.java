
package com.amazonaws.ec2manager.utility;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.amazonaws.ec2manager.lambda.AbstractLambdaHandler;
import com.amazonaws.services.lambda.runtime.Context;

import software.amazon.awssdk.services.ssm.SsmClient;
import software.amazon.awssdk.services.ssm.model.DescribeInstanceInformationRequest;
import software.amazon.awssdk.services.ssm.model.InstanceInformation;
import software.amazon.awssdk.services.ssm.model.SsmException;

public class SsmClientExample extends AbstractLambdaHandler {

	public static void main(String[] args) {

		SsmClientExample request = new SsmClientExample();
		request.getInstancesInfo();
	}

	public void getInstancesInfo() {

		try (SsmClient ssmClient = getSsmClient();) {

			// describeInstanceProperties -> describeInstanceInformation
			DescribeInstanceInformationRequest request = DescribeInstanceInformationRequest.builder().maxResults(10)
					.build();
			for (InstanceInformation instanceInfo : ssmClient.describeInstanceInformation(request)
					.instanceInformationList()) {
				System.out.println("InstanceInformation: " + instanceInfo);
			}

		} catch (SsmException e) {
			System.err.println(e.getMessage());
		}
	}

	@Override
	public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) throws IOException {

	}

//	private void listInventoryEntries(SsmClient ssmClient) {
//
//		// Configuring inventory collection
//		// AWS:AWSComponent
//		// AWS:Application
//		// AWS:InstanceInformation
//		// AWS:Network
//		// AWS:WindowsUpdate
//		ListInventoryEntriesRequest request = ListInventoryEntriesRequest.builder().maxResults(10)
//				.instanceId("i-0c2cf0542b9dccbbb").typeName("AWS:InstanceInformation").build();
//		for (Map<String, String> item : ssmClient.listInventoryEntries(request).entries()) {
//			System.out.println("The item title is " + item);
//		}
//	}
//
//	private void describeItems(SsmClient ssmClient) {
//
//		DescribeOpsItemsRequest itemsRequest = DescribeOpsItemsRequest.builder().maxResults(10).build();
//		for (OpsItemSummary item : ssmClient.describeOpsItems(itemsRequest).opsItemSummaries()) {
//			System.out.println("The item title is " + item.title());
//		}
//
//	}
//
//	private void describeParams(SsmClient ssmClient) {
//
//		// Create a DescribeParametersRequest object
//		DescribeParametersRequest desRequest = DescribeParametersRequest.builder().maxResults(10).build();
//
//		// Get SSM Parameters (you can define them in the AWS Console)
//		for (ParameterMetadata paraMeta : ssmClient.describeParameters(desRequest).parameters()) {
//
//			System.out.println(paraMeta.name());
//			System.out.println(paraMeta.description());
//		}
//	}
}
