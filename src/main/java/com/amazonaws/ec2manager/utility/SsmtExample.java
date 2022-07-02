
package com.amazonaws.ec2manager.utility;

import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ssm.SsmClient;
import software.amazon.awssdk.services.ssm.model.DescribeInstanceInformationRequest;
import software.amazon.awssdk.services.ssm.model.InstanceInformation;
import software.amazon.awssdk.services.ssm.model.SsmException;

public class SsmtExample {

	public static void main(String[] args) {

		SsmtExample request = new SsmtExample();
		request.runExample();
	}

	public void runExample() {

		Region region = Region.US_EAST_1;

		try (SsmClient ssmClient = SsmClient.builder().region(region)
				.credentialsProvider(EnvironmentVariableCredentialsProvider.create()).build();) {

			// describeInstanceProperties -> describeInstanceInformation
			DescribeInstanceInformationRequest request = DescribeInstanceInformationRequest.builder().maxResults(10)
					.build();
			for (InstanceInformation instanceInfo : ssmClient.describeInstanceInformation(request).instanceInformationList()) {
				System.out.println("InstanceInformation: " + instanceInfo);
			}

			// listInventoryEntries(ssmClient);
			// describeItems(ssmClient);
			// describeParams(ssmClient);

		} catch (SsmException e) {
			System.err.println(e.getMessage());
		}
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
