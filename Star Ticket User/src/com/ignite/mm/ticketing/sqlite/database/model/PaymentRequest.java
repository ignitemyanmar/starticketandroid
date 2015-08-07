package com.ignite.mm.ticketing.sqlite.database.model;

public class PaymentRequest {
	private String order_no;
	private String order_amount;
	
	public PaymentRequest(String order_no, String order_amount) {
		super();
		this.order_no = order_no;
		this.order_amount = order_amount;
	}

	public String getOrder_no() {
		return order_no;
	}

	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}

	public String getOrder_amount() {
		return order_amount;
	}

	public void setOrder_amount(String order_amount) {
		this.order_amount = order_amount;
	}

	@Override
	public String toString() {
		return "PaymentRequest [order_no=" + order_no + ", order_amount="
				+ order_amount + "]";
	}
	
	
}
