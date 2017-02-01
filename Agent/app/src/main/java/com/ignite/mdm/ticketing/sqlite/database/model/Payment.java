package com.ignite.mdm.ticketing.sqlite.database.model;

import com.google.gson.annotations.SerializedName;

public class Payment {
	
	@SerializedName("id")
	private String paymentId;
	
	@SerializedName("payment_destination_id")
	private String paymentDestinationId;
	
	@SerializedName("invoice_no")
	private String invoiceNo;
	
	@SerializedName("agentgroup_id")
	private String agentGroupId;
	
	@SerializedName("amount")
	private String amount;
	
	@SerializedName("pay_date")
	private String payDate;
	
	@SerializedName("user_id")
	private String userId;
	
	@SerializedName("status")
	private String status;
	
	@SerializedName("pay_remark")
	private String payRemark;
	
	@SerializedName("delete_remark")
	private String deleteRemark;
	
	@SerializedName("created_at")
	private String createdAt;
	
	@SerializedName("updated_at")
	private String updatedAt;
	
	@SerializedName("payment_destination")
	private String paymentDestination;

	public String getPaymentId() {
		return paymentId;
	}

	public String getPaymentDestinationId() {
		return paymentDestinationId;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public String getAgentGroupId() {
		return agentGroupId;
	}

	public String getAmount() {
		return amount;
	}

	public String getPayDate() {
		return payDate;
	}

	public String getUserId() {
		return userId;
	}

	public String getStatus() {
		return status;
	}

	public String getPayRemark() {
		return payRemark;
	}

	public String getDeleteRemark() {
		return deleteRemark;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public String getUpdatedAt() {
		return updatedAt;
	}

	public String getPaymentDestination() {
		return paymentDestination;
	}
	
	

}
