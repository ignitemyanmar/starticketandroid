package com.ignite.mdm.ticketing.sqlite.database.model;

import java.util.List;

import com.google.gson.annotations.SerializedName;


public class PaymentResponse {

	@SerializedName("total_paidamount")
	private String totalPaidAmount;
	
	@SerializedName("total_soldamount")
	private String totalSoldAmount;
	
	@SerializedName("total_commission")
	private String totalCommission;
	
	@SerializedName("total_balance")
	private String totalBalance;
	
	@SerializedName("payment_lists")
	private List<Payment>paymentList;

	public String getTotalPaidAmount() {
		return totalPaidAmount;
	}

	public String getTotalSoldAmount() {
		return totalSoldAmount;
	}

	public String getTotalCommission() {
		return totalCommission;
	}

	public String getTotalBalance() {
		return totalBalance;
	}

	public List<Payment> getPaymentList() {
		return paymentList;
	}
	
	
}
