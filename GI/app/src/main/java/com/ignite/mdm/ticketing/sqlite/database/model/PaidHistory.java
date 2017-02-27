package com.ignite.mdm.ticketing.sqlite.database.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaidHistory {

@SerializedName("id")
@Expose
public String id;
@SerializedName("agentgroup_id")
@Expose
public String agentgroupId;
@SerializedName("deposit")
@Expose
public String deposit;
@SerializedName("pay_date")
@Expose
public String payDate;
@SerializedName("user_id")
@Expose
public String userId;
@SerializedName("status")
@Expose
public String status;
@SerializedName("permit_credit_amount")
@Expose
public String permitCreditAmount;
@SerializedName("remark")
@Expose
public String remark;
@SerializedName("created_at")
@Expose
public String createdAt;
@SerializedName("updated_at")
@Expose
public String updatedAt;
public PaidHistory(String id, String agentgroupId, String deposit,
		String payDate, String userId, String status,
		String permitCreditAmount, String remark, String createdAt,
		String updatedAt) {
	super();
	this.id = id;
	this.agentgroupId = agentgroupId;
	this.deposit = deposit;
	this.payDate = payDate;
	this.userId = userId;
	this.status = status;
	this.permitCreditAmount = permitCreditAmount;
	this.remark = remark;
	this.createdAt = createdAt;
	this.updatedAt = updatedAt;
}
public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}
public String getAgentgroupId() {
	return agentgroupId;
}
public void setAgentgroupId(String agentgroupId) {
	this.agentgroupId = agentgroupId;
}
public String getDeposit() {
	return deposit;
}
public void setDeposit(String deposit) {
	this.deposit = deposit;
}
public String getPayDate() {
	return payDate;
}
public void setPayDate(String payDate) {
	this.payDate = payDate;
}
public String getUserId() {
	return userId;
}
public void setUserId(String userId) {
	this.userId = userId;
}
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}
public String getPermitCreditAmount() {
	return permitCreditAmount;
}
public void setPermitCreditAmount(String permitCreditAmount) {
	this.permitCreditAmount = permitCreditAmount;
}
public String getRemark() {
	return remark;
}
public void setRemark(String remark) {
	this.remark = remark;
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
	return "PaidHistory [id=" + id + ", agentgroupId=" + agentgroupId
			+ ", deposit=" + deposit + ", payDate=" + payDate + ", userId="
			+ userId + ", status=" + status + ", permitCreditAmount="
			+ permitCreditAmount + ", remark=" + remark + ", createdAt="
			+ createdAt + ", updatedAt=" + updatedAt + "]";
}
}