# EC2 Assignment

<img src="https://github.com/yaohf777/ec2manager/blob/main/EC2ManagerFlow.png" border="1" alt="EC2 Manager flow"/>

## Design
[EC2 Manager design document](https://wiki.wdf.sap.corp/wiki/display/GRCIAGWDF/Getting+Started)


## Implementation code
Function one (retrieve resource information) implementation is contained in [GetResourcesInfoHandler.java](https://github.com/yaohf777/ec2manager/blob/main/src/main/java/com/amazonaws/ec2manager/lambda/GetResourcesInfoHandler.java#L32-L51).

Function two (Run OS layer short task) implementation is contained in [SendCommandHandler.java](https://github.com/yaohf777/ec2manager/blob/main/src/main/java/com/amazonaws/ec2manager/lambda/SendCommandHandler.java#L40-L81).
