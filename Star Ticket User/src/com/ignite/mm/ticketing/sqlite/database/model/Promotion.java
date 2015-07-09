package com.ignite.mm.ticketing.sqlite.database.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Promotion {

@Expose
private String id;
@SerializedName("start_date")
@Expose
private String startDate;
@SerializedName("end_date")
@Expose
private String endDate;
@SerializedName("operator_id")
@Expose
private String operatorId;
@SerializedName("agentgroup_id")
@Expose
private String agentgroupId;
@SerializedName("payment_method_id")
@Expose
private String paymentMethodId;
@SerializedName("start_amount")
@Expose
private String startAmount;
@SerializedName("end_amount")
@Expose
private String endAmount;
@Expose
private String points;
@SerializedName("gift_money")
@Expose
private String giftMoney;
@SerializedName("gift_parcel")
@Expose
private String giftParcel;
@SerializedName("promotion_code")
@Expose
private String promotionCode;
@Expose
private String status;
@SerializedName("operator_name")
@Expose
private String operatorName;
@SerializedName("agentgroup_name")
@Expose
private String agentgroupName;
@SerializedName("payment_method")
@Expose
private String paymentMethod;



public Promotion(String id, String startDate, String endDate,
		String operatorId, String agentgroupId, String paymentMethodId,
		String startAmount, String endAmount, String points, String giftMoney,
		String giftParcel, String promotionCode, String status,
		String operatorName, String agentgroupName, String paymentMethod) {
	super();
	this.id = id;
	this.startDate = startDate;
	this.endDate = endDate;
	this.operatorId = operatorId;
	this.agentgroupId = agentgroupId;
	this.paymentMethodId = paymentMethodId;
	this.startAmount = startAmount;
	this.endAmount = endAmount;
	this.points = points;
	this.giftMoney = giftMoney;
	this.giftParcel = giftParcel;
	this.promotionCode = promotionCode;
	this.status = status;
	this.operatorName = operatorName;
	this.agentgroupName = agentgroupName;
	this.paymentMethod = paymentMethod;
}

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
* The startDate
*/
public String getStartDate() {
return startDate;
}

/**
* 
* @param startDate
* The start_date
*/
public void setStartDate(String startDate) {
this.startDate = startDate;
}

/**
* 
* @return
* The endDate
*/
public String getEndDate() {
return endDate;
}

/**
* 
* @param endDate
* The end_date
*/
public void setEndDate(String endDate) {
this.endDate = endDate;
}

/**
* 
* @return
* The operatorId
*/
public String getOperatorId() {
return operatorId;
}

/**
* 
* @param operatorId
* The operator_id
*/
public void setOperatorId(String operatorId) {
this.operatorId = operatorId;
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
* The paymentMethodId
*/
public String getPaymentMethodId() {
return paymentMethodId;
}

/**
* 
* @param paymentMethodId
* The payment_method_id
*/
public void setPaymentMethodId(String paymentMethodId) {
this.paymentMethodId = paymentMethodId;
}

/**
* 
* @return
* The startAmount
*/
public String getStartAmount() {
return startAmount;
}

/**
* 
* @param startAmount
* The start_amount
*/
public void setStartAmount(String startAmount) {
this.startAmount = startAmount;
}

/**
* 
* @return
* The endAmount
*/
public String getEndAmount() {
return endAmount;
}

/**
* 
* @param endAmount
* The end_amount
*/
public void setEndAmount(String endAmount) {
this.endAmount = endAmount;
}

/**
* 
* @return
* The points
*/
public String getPoints() {
return points;
}

/**
* 
* @param points
* The points
*/
public void setPoints(String points) {
this.points = points;
}

/**
* 
* @return
* The giftMoney
*/
public String getGiftMoney() {
return giftMoney;
}

/**
* 
* @param giftMoney
* The gift_money
*/
public void setGiftMoney(String giftMoney) {
this.giftMoney = giftMoney;
}

/**
* 
* @return
* The giftParcel
*/
public String getGiftParcel() {
return giftParcel;
}

/**
* 
* @param giftParcel
* The gift_parcel
*/
public void setGiftParcel(String giftParcel) {
this.giftParcel = giftParcel;
}

/**
* 
* @return
* The promotionCode
*/
public String getPromotionCode() {
return promotionCode;
}

/**
* 
* @param promotionCode
* The promotion_code
*/
public void setPromotionCode(String promotionCode) {
this.promotionCode = promotionCode;
}

/**
* 
* @return
* The status
*/
public String getStatus() {
return status;
}

/**
* 
* @param status
* The status
*/
public void setStatus(String status) {
this.status = status;
}

/**
* 
* @return
* The operatorName
*/
public String getOperatorName() {
return operatorName;
}

/**
* 
* @param operatorName
* The operator_name
*/
public void setOperatorName(String operatorName) {
this.operatorName = operatorName;
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

/**
* 
* @return
* The paymentMethod
*/
public String getPaymentMethod() {
return paymentMethod;
}

/**
* 
* @param paymentMethod
* The payment_method
*/
public void setPaymentMethod(String paymentMethod) {
this.paymentMethod = paymentMethod;
}

@Override
public String toString() {
	return "Promotion [id=" + id + ", startDate=" + startDate + ", endDate="
			+ endDate + ", operatorId=" + operatorId + ", agentgroupId="
			+ agentgroupId + ", paymentMethodId=" + paymentMethodId
			+ ", startAmount=" + startAmount + ", endAmount=" + endAmount
			+ ", points=" + points + ", giftMoney=" + giftMoney
			+ ", giftParcel=" + giftParcel + ", promotionCode=" + promotionCode
			+ ", status=" + status + ", operatorName=" + operatorName
			+ ", agentgroupName=" + agentgroupName + ", paymentMethod="
			+ paymentMethod + "]";
}



}