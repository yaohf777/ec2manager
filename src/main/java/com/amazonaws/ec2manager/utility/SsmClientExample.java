
package com.amazonaws.ec2manager.utility;

import com.amazonaws.ec2manager.lambda.GetResourcesInfoLambdaHandler;

public class SsmClientExample extends GetResourcesInfoLambdaHandler {

	public static void main(String[] args) {

		SsmClientExample request = new SsmClientExample();
		String jsonString = request.getInstancesInfoJson();
		System.out.println(jsonString);
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
