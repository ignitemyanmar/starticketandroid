package com.ignite.mdm.ticketing.sqlite.database.model;

public class AgentPayment {
	/**
	 * {
  "total_paidamount": "50000",
  "total_soldamount": 0,
  "total_commission": 0,
  "total_balance": 50000
}
	 */
	
	private String total_paidamount;
	private int total_soldamount;
	private int total_commission;
	private int total_balance;
	

	public String getTotal_paidamount() {
		return total_paidamount;
	}
	public void setTotal_paidamount(String total_paidamount) {
		this.total_paidamount = total_paidamount;
	}
	public int getTotal_soldamount() {
		return total_soldamount;
	}
	public void setTotal_soldamount(int total_soldamount) {
		this.total_soldamount = total_soldamount;
	}
	public int getTotal_commission() {
		return total_commission;
	}
	public void setTotal_commission(int total_commission) {
		this.total_commission = total_commission;
	}
	public int getTotal_balance() {
		return total_balance;
	}
	public void setTotal_balance(int total_balance) {
		this.total_balance = total_balance;
	}
	
	
	

}
