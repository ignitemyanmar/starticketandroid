package com.ignite.mdm.ticketing.sqlite.database.model;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AgentDeposit {

@SerializedName("id")
@Expose
public String id;
@SerializedName("name")
@Expose
public String name;
@SerializedName("total_paid")
@Expose
public String totalPaid;
@SerializedName("permit_credit_amount")
@Expose
public String permitCreditAmount;
@SerializedName("total_sold_amount")
@Expose
public String totalSoldAmount;
@SerializedName("search_sold_amount")
@Expose
public String searchSoldAmount;
@SerializedName("paid_history")
@Expose
public List<PaidHistory> paidHistory = new ArrayList<PaidHistory>();
@Expose
public Double percentage; 

public AgentDeposit(String id, String name, String totalPaid,
		String permitCreditAmount, String totalSoldAmount,
		String searchSoldAmount, List<PaidHistory> paidHistory, Double percentage) {
	super();
	this.id = id;
	this.name = name;
	this.totalPaid = totalPaid;
	this.permitCreditAmount = permitCreditAmount;
	this.totalSoldAmount = totalSoldAmount;
	this.searchSoldAmount = searchSoldAmount;
	this.paidHistory = paidHistory;
	this.percentage = percentage;
}
public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getTotalPaid() {
	return totalPaid;
}
public void setTotalPaid(String totalPaid) {
	this.totalPaid = totalPaid;
}
public String getPermitCreditAmount() {
	return permitCreditAmount;
}
public void setPermitCreditAmount(String permitCreditAmount) {
	this.permitCreditAmount = permitCreditAmount;
}
public String getTotalSoldAmount() {
	return totalSoldAmount;
}
public void setTotalSoldAmount(String totalSoldAmount) {
	this.totalSoldAmount = totalSoldAmount;
}
public String getSearchSoldAmount() {
	return searchSoldAmount;
}
public void setSearchSoldAmount(String searchSoldAmount) {
	this.searchSoldAmount = searchSoldAmount;
}
public List<PaidHistory> getPaidHistory() {
	return paidHistory;
}
public void setPaidHistory(List<PaidHistory> paidHistory) {
	this.paidHistory = paidHistory;
}

public Double getPercentage() {
	return percentage;
}
public void setPercentage(Double percentage) {
	this.percentage = percentage;
}
@Override
public String toString() {
	return "AgentDeposit [id=" + id + ", name=" + name + ", totalPaid="
			+ totalPaid + ", permitCreditAmount=" + permitCreditAmount
			+ ", totalSoldAmount=" + totalSoldAmount + ", searchSoldAmount="
			+ searchSoldAmount + ", paidHistory=" + paidHistory
			+ ", percentage=" + percentage + "]";
}
}