package com.ignite.mm.ticketing.sqlite.database.model;

import com.google.gson.annotations.Expose;

public class User {
	
	@Expose
	private String id;
	
	@Expose
	private String operatorgroup_id;
	
	@Expose
	private String user_id;
		
	@Expose
	private String name;
	
	@Expose
	private String type;
	
	@Expose
    public String username;
	@Expose
    public String email;
	@Expose
    public String phone;
	@Expose
    public String codeNo;
	@Expose
    public String address;
	@Expose
    public String role;
	@Expose
    public String agentgroupId;
	@Expose
    public String groupBranch;
	@Expose
    public String loyaltyUserCode;
	@Expose
    public String accessToken;
	@Expose
    public String forgotPwCode;
	@Expose
    public String createdAt;
	@Expose
    public String updatedAt;
	
	
	public String getId() {
	return id;
	}
	
	public void setId(String id) {
	this.id = id;
	}
	
	public String getOperatorgroup_id() {
		return operatorgroup_id;
	}

	public void setOperatorgroup_id(String operatorgroup_id) {
		this.operatorgroup_id = operatorgroup_id;
	}
	
	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getName() {
	return name;
	}
	
	public void setName(String name) {
	this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCodeNo() {
		return codeNo;
	}

	public void setCodeNo(String codeNo) {
		this.codeNo = codeNo;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getAgentgroupId() {
		return agentgroupId;
	}

	public void setAgentgroupId(String agentgroupId) {
		this.agentgroupId = agentgroupId;
	}

	public String getGroupBranch() {
		return groupBranch;
	}

	public void setGroupBranch(String groupBranch) {
		this.groupBranch = groupBranch;
	}

	public String getLoyaltyUserCode() {
		return loyaltyUserCode;
	}

	public void setLoyaltyUserCode(String loyaltyUserCode) {
		this.loyaltyUserCode = loyaltyUserCode;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getForgotPwCode() {
		return forgotPwCode;
	}

	public void setForgotPwCode(String forgotPwCode) {
		this.forgotPwCode = forgotPwCode;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", operatorgroup_id=" + operatorgroup_id
				+ ", user_id=" + user_id + ", name=" + name + ", type=" + type
				+ ", username=" + username + ", email=" + email + ", phone="
				+ phone + ", codeNo=" + codeNo + ", address=" + address
				+ ", role=" + role + ", agentgroupId=" + agentgroupId
				+ ", groupBranch=" + groupBranch + ", loyaltyUserCode="
				+ loyaltyUserCode + ", accessToken=" + accessToken
				+ ", forgotPwCode=" + forgotPwCode + ", createdAt=" + createdAt
				+ ", updatedAt=" + updatedAt + "]";
	}
	
	

}