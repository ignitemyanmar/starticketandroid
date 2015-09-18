package com.ignite.mm.ticketing.sqlite.database.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StarTicketAgents {

@Expose
private String id;
@SerializedName("code_no")
@Expose
private String codeNo;
@SerializedName("agentgroup_id")
@Expose
private String agentgroupId;
@Expose
private String name;
@Expose
private String email;
@Expose
private String password;
@SerializedName("user_id")
@Expose
private String userId;
@Expose
private String address;
@SerializedName("address_2")
@Expose
private String address2;
@Expose
private String phone;
@Expose
private String company;
@SerializedName("created_at")
@Expose
private String createdAt;
@SerializedName("updated_at")
@Expose
private String updatedAt;

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
* The agentgroupId
*/
public String getAgentgroupId() {
return agentgroupId;
}

/**
* 
* @param agentgroupId
* The agentgroup_id
*/
public void setAgentgroupId(String agentgroupId) {
this.agentgroupId = agentgroupId;
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
* The password
*/
public String getPassword() {
return password;
}

/**
* 
* @param password
* The password
*/
public void setPassword(String password) {
this.password = password;
}

/**
* 
* @return
* The userId
*/
public String getUserId() {
return userId;
}

/**
* 
* @param userId
* The user_id
*/
public void setUserId(String userId) {
this.userId = userId;
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
* The address2
*/
public String getAddress2() {
return address2;
}

/**
* 
* @param address2
* The address_2
*/
public void setAddress2(String address2) {
this.address2 = address2;
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
* The company
*/
public String getCompany() {
return company;
}

/**
* 
* @param company
* The company
*/
public void setCompany(String company) {
this.company = company;
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

@Override
public String toString() {
	return "StarTicketAgents [id=" + id + ", codeNo=" + codeNo
			+ ", agentgroupId=" + agentgroupId + ", name=" + name + ", email="
			+ email + ", password=" + password + ", userId=" + userId
			+ ", address=" + address + ", address2=" + address2 + ", phone="
			+ phone + ", company=" + company + ", createdAt=" + createdAt
			+ ", updatedAt=" + updatedAt + "]";
}



}