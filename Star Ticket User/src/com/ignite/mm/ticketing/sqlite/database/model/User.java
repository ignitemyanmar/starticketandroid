package com.ignite.mm.ticketing.sqlite.database.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

@Expose
private String name;
@Expose
private String email;
@Expose
private String phone;
@Expose
private String address;
@SerializedName("code_no")
@Expose
private String codeNo;
@Expose
private int role;
@SerializedName("group_branch")
@Expose
private int groupBranch;
@SerializedName("updated_at")
@Expose
private String updatedAt;
@SerializedName("created_at")
@Expose
private String createdAt;
@Expose
private int id;
@SerializedName("agentgroup_id")
@Expose
private int agentgroupId;
@SerializedName("agentgroup_name")
@Expose
private String agentgroupName;
@Expose
private String points;
@Expose
private String gift_moneys;
@Expose
private String username;


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
* The phone
*/
public String getPhone() {
return phone;
}

/**
* 
* @param phone
* The phone
*/
public void setPhone(String phone) {
this.phone = phone;
}

/**
* 
* @return
* The address
*/
public String getAddress() {
return address;
}

/**
* 
* @param address
* The address
*/
public void setAddress(String address) {
this.address = address;
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
* The id
*/
public int getId() {
return id;
}

/**
* 
* @param id
* The id
*/
public void setId(int id) {
this.id = id;
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
* The agentgroupName
*/
public String getAgentgroupName() {
return agentgroupName;
}

/**
* 
* @param agentgroupName
* The agentgroup_name
*/
public void setAgentgroupName(String agentgroupName) {
this.agentgroupName = agentgroupName;
}



public String getPoints() {
	return points;
}

public void setPoints(String points) {
	this.points = points;
}

public String getGift_moneys() {
	return gift_moneys;
}

public void setGift_moneys(String gift_moneys) {
	this.gift_moneys = gift_moneys;
}



public String getUsername() {
	return username;
}

public void setUsername(String username) {
	this.username = username;
}

@Override
public String toString() {
	return "User [name=" + name + ", email=" + email + ", phone=" + phone
			+ ", address=" + address + ", codeNo=" + codeNo + ", role=" + role
			+ ", groupBranch=" + groupBranch + ", updatedAt=" + updatedAt
			+ ", createdAt=" + createdAt + ", id=" + id + ", agentgroupId="
			+ agentgroupId + ", agentgroupName=" + agentgroupName + ", points="
			+ points + ", gift_moneys=" + gift_moneys + ", username="
			+ username + "]";
}



}