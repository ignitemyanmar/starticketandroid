package com.ignite.mdm.ticketing.sqlite.database.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AccessToken {

@Expose
private String id;
@Expose
private String name;
@Expose
private String email;
@SerializedName("code_no")
@Expose
private String codeNo;
@Expose
private int role;
@SerializedName("agentgroup_id")
@Expose
private int agentgroupId;
@SerializedName("group_branch")
@Expose
private int groupBranch;
@SerializedName("access_token")
@Expose
private String accessToken;
@SerializedName("created_at")
@Expose
private String createdAt;

@SerializedName("updated_at")
@Expose
private String updatedAt;
@Expose
private String phone;
@Expose
private String address;
@Expose
private String agentgroup_name;
@Expose
private String total_paid;
@Expose
private String total_sold_amount;
@Expose
private Double percentage;

	/**
	* 
	* @return
	* The id
	*/
	public String getId() {
	return id;
	}
	
	/**
	* 
	* @param id
	* The id
	*/
	public void setId(String id) {
	this.id = id;
	}
	
	/**
	* 
	* @return
	* The name
	*/
	public String getName() {
	return name;
	}
	
	/**
	* 
	* @param name
	* The name
	*/
	public void setName(String name) {
	this.name = name;
	}
	
	/**
	* 
	* @return
	* The email
	*/
	public String getEmail() {
	return email;
	}
	
	/**
	* 
	* @param email
	* The email
	*/
	public void setEmail(String email) {
	this.email = email;
	}
	
	/**
	* 
	* @return
	* The codeNo
	*/
	public String getCodeNo() {
	return codeNo;
	}
	
	/**
	* 
	* @param codeNo
	* The code_no
	*/
	public void setCodeNo(String codeNo) {
	this.codeNo = codeNo;
	}
	
	/**
	* 
	* @return
	* The role
	*/
	public int getRole() {
	return role;
	}
	
	/**
	* 
	* @param role
	* The role
	*/
	public void setRole(int role) {
	this.role = role;
	}
	
	/**
	* 
	* @return
	* The agentgroupId
	*/
	public int getAgentgroupId() {
	return agentgroupId;
	}
	
	/**
	* 
	* @param agentgroupId
	* The agentgroup_id
	*/
	public void setAgentgroupId(int agentgroupId) {
	this.agentgroupId = agentgroupId;
	}
	
	/**
	* 
	* @return
	* The groupBranch
	*/
	public int getGroupBranch() {
	return groupBranch;
	}
	
	/**
	* 
	* @param groupBranch
	* The group_branch
	*/
	public void setGroupBranch(int groupBranch) {
	this.groupBranch = groupBranch;
	}
	
	/**
	* 
	* @return
	* The accessToken
	*/
	public String getAccessToken() {
	return accessToken;
	}
	
	/**
	* 
	* @param accessToken
	* The access_token
	*/
	public void setAccessToken(String accessToken) {
	this.accessToken = accessToken;
	}
	
	/**
	* 
	* @return
	* The createdAt
	*/
	public String getCreatedAt() {
	return createdAt;
	}
	
	
	/**
	* 
	* @param createdAt
	* The created_at
	*/
	public void setCreatedAt(String createdAt) {
	this.createdAt = createdAt;
	}
	
	/**
	* 
	* @return
	* The updatedAt
	*/
	public String getUpdatedAt() {
	return updatedAt;
	}
	
	/**
	* 
	* @param updatedAt
	* The updated_at
	*/
	public void setUpdatedAt(String updatedAt) {
	this.updatedAt = updatedAt;
	}
	
	

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAgentgroup_name() {
		return agentgroup_name;
	}

	public void setAgentgroup_name(String agentgroup_name) {
		this.agentgroup_name = agentgroup_name;
	}
	
	

	public String getTotal_paid() {
		return total_paid;
	}

	public void setTotal_paid(String total_paid) {
		this.total_paid = total_paid;
	}

	public String getTotal_sold_amount() {
		return total_sold_amount;
	}

	public void setTotal_sold_amount(String total_sold_amount) {
		this.total_sold_amount = total_sold_amount;
	}
	
	

	public Double getPercentage() {
		return percentage;
	}

	public void setPercentage(Double percentage) {
		this.percentage = percentage;
	}

	@Override
	public String toString() {
		return "AccessToken [id=" + id + ", name=" + name + ", email=" + email
				+ ", codeNo=" + codeNo + ", role=" + role + ", agentgroupId="
				+ agentgroupId + ", groupBranch=" + groupBranch
				+ ", accessToken=" + accessToken + ", createdAt=" + createdAt
				+ ", updatedAt=" + updatedAt + ", phone=" + phone
				+ ", address=" + address + ", agentgroup_name="
				+ agentgroup_name + ", total_paid=" + total_paid
				+ ", total_sold_amount=" + total_sold_amount + ", percentage="
				+ percentage + "]";
	}

	
}