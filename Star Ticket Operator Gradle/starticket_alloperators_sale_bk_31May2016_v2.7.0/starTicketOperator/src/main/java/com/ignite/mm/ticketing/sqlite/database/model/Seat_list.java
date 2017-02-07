package com.ignite.mm.ticketing.sqlite.database.model;

import com.google.gson.annotations.Expose;

public class Seat_list {
	
	@Expose
	private Integer id;
	@Expose
	private String seat_no;
	@Expose
	private Integer status;
	@Expose
	private String salecheck;
	@Expose
	private Integer free_ticket;
	@Expose
	private String free_ticket_remark;
	@Expose
	private Integer discount;
	@Expose
	private Integer booking;
	@Expose
	private Integer remark_type;
	@Expose
	private String remark;
	@Expose
	private Integer operatorgroup_id;
	@Expose
	private Integer operatorgroup_color;
	@Expose
	private String operatorgroup_name;
	@Expose
	private String nationality;
	@Expose
	private CustomerInfo customer_info;
	
	public Seat_list(Integer id, String seat_no, Integer status) {
		super();
		this.id = id;
		this.seat_no = seat_no;
		this.status = status;
	}

	public Integer getId() {
	return id;
	}
	
	public void setId(Integer id) {
	this.id = id;
	}
	
	public String getSeat_no() {
	return seat_no;
	}
	
	public void setSeat_no(String seat_no) {
	this.seat_no = seat_no;
	}
	
	public Integer getStatus() {
	return status;
	}
	
	public void setStatus(Integer status) {
	this.status = status;
	}
	
	public Integer getFree_ticket() {
		return free_ticket;
	}

	public void setFree_ticket(Integer free_ticket) {
		this.free_ticket = free_ticket;
	}

	public String getFree_ticket_remark() {
		return free_ticket_remark;
	}

	public void setFree_ticket_remark(String free_ticket_remark) {
		this.free_ticket_remark = free_ticket_remark;
	}

	public Integer getDiscount() {
		return discount;
	}

	public void setDiscount(Integer discount) {
		this.discount = discount;
	}

	public Integer getBooking() {
		return booking;
	}

	public void setBooking(Integer booking) {
		this.booking = booking;
	}
	
	public Integer getRemark_type() {
		return remark_type;
	}

	public void setRemark_type(Integer remark_type) {
		this.remark_type = remark_type;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getOperatorgroup_id() {
		return operatorgroup_id;
	}

	public void setOperatorgroup_id(Integer operatorgroup_id) {
		this.operatorgroup_id = operatorgroup_id;
	}
	
	public Integer getOperatorgroup_color() {
		return operatorgroup_color;
	}

	public void setOperatorgroup_color(Integer operatorgroup_color) {
		this.operatorgroup_color = operatorgroup_color;
	}
	
	public String getOperatorgroup_name() {
		return operatorgroup_name;
	}

	public void setOperatorgroup_name(String operatorgroup_name) {
		this.operatorgroup_name = operatorgroup_name;
	}

	public CustomerInfo getCustomerInfo() {
		return customer_info;
	}

	public void setCustomerInfo(CustomerInfo customerInfo) {
		this.customer_info = customerInfo;
	}
	

	public String getSalecheck() {
		return salecheck;
	}

	public void setSalecheck(String salecheck) {
		this.salecheck = salecheck;
	}

	
	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	@Override
	public String toString() {
		return "Seat_list [id=" + id + ", seat_no=" + seat_no + ", status="
				+ status + ", salecheck=" + salecheck + ", free_ticket="
				+ free_ticket + ", free_ticket_remark=" + free_ticket_remark
				+ ", discount=" + discount + ", booking=" + booking
				+ ", remark_type=" + remark_type + ", remark=" + remark
				+ ", operatorgroup_id=" + operatorgroup_id
				+ ", operatorgroup_color=" + operatorgroup_color
				+ ", operatorgroup_name=" + operatorgroup_name
				+ ", nationality=" + nationality + ", customer_info="
				+ customer_info + "]";
	}

	
	
}