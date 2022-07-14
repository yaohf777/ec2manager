<img src="https://github.com/yaohf777/ec2manager/blob/main/EC2ManagerFlow.png" border="1" alt="EC2 Manager flow"/>

## Design
[EC2 Manager design document](https://github.com/yaohf777/ec2manager/blob/main/EC2%20Manager%20Design.docx)


## Implementation code
Function one (retrieve resource information) implementation is in [GetResourcesInfoHandler.java](https://github.com/yaohf777/ec2manager/blob/main/src/main/java/com/amazonaws/ec2manager/lambda/GetResourcesInfoHandler.java#L32-L51).

Function two (Run OS layer short task) implementation is in [SendCommandHandler.java](https://github.com/yaohf777/ec2manager/blob/main/src/main/java/com/amazonaws/ec2manager/lambda/SendCommandHandler.java#L40-L81).

Step Functions definition is in [EC2ManagerStateMachine.json](https://github.com/yaohf777/ec2manager/blob/main/EC2ManagerStateMachine.json).
