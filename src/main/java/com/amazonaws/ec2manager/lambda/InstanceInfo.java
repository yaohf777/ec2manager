package com.amazonaws.ec2manager.lambda;

import java.time.Instant;

import org.springframework.beans.BeanUtils;

import software.amazon.awssdk.services.ssm.model.InstanceInformation;

public class InstanceInfo {

	private String instanceId;

	private String pingStatus;

	private Instant lastPingDateTime;

	private String agentVersion;

	private Boolean isLatestVersion;

	private String platformType;

	private String platformName;

	private String platformVersion;

	private String activationId;

	private String iamRole;

	private Instant registrationDate;

	private String resourceType;

	private String name;

	private String ipAddress;

	private String computerName;

	private String associationStatus;

	// private Instant lastAssociationExecutionDate;

	// private Instant lastSuccessfulAssociationExecutionDate;

	// private InstanceAggregatedAssociationOverview associationOverview;

	private String sourceId;

	private String sourceType;

	public InstanceInfo() {

	}

	public InstanceInfo(InstanceInformation instanceInfo) {
		BeanUtils.copyProperties(instanceInfo, this);
	}

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public String getPingStatus() {
		return pingStatus;
	}

	public void setPingStatus(String pingStatus) {
		this.pingStatus = pingStatus;
	}

	public Instant getLastPingDateTime() {
		return lastPingDateTime;
	}

	public void setLastPingDateTime(Instant lastPingDateTime) {
		this.lastPingDateTime = lastPingDateTime;
	}

	public String getAgentVersion() {
		return agentVersion;
	}

	public void setAgentVersion(String agentVersion) {
		this.agentVersion = agentVersion;
	}

	public Boolean getIsLatestVersion() {
		return isLatestVersion;
	}

	public void setIsLatestVersion(Boolean isLatestVersion) {
		this.isLatestVersion = isLatestVersion;
	}

	public String getPlatformType() {
		return platformType;
	}

	public void setPlatformType(String platformType) {
		this.platformType = platformType;
	}

	public String getPlatformName() {
		return platformName;
	}

	public void setPlatformName(String platformName) {
		this.platformName = platformName;
	}

	public String getPlatformVersion() {
		return platformVersion;
	}

	public void setPlatformVersion(String platformVersion) {
		this.platformVersion = platformVersion;
	}

	public String getActivationId() {
		return activationId;
	}

	public void setActivationId(String activationId) {
		this.activationId = activationId;
	}

	public String getIamRole() {
		return iamRole;
	}

	public void setIamRole(String iamRole) {
		this.iamRole = iamRole;
	}

	public Instant getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Instant registrationDate) {
		this.registrationDate = registrationDate;
	}

	public String getResourceType() {
		return resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getComputerName() {
		return computerName;
	}

	public void setComputerName(String computerName) {
		this.computerName = computerName;
	}

	public String getAssociationStatus() {
		return associationStatus;
	}

	public void setAssociationStatus(String associationStatus) {
		this.associationStatus = associationStatus;
	}

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}
}
