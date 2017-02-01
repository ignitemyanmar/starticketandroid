
package com.ignite.mdm.ticketing.sqlite.database.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Saleitem {

    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("order_id")
    @Expose
    public String orderId;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("nrc_no")
    @Expose
    public String nrcNo;
    @SerializedName("phone")
    @Expose
    public String phone;
    @SerializedName("trip_id")
    @Expose
    public String tripId;
    @SerializedName("ticket_no")
    @Expose
    public String ticketNo;
    @SerializedName("starticket_no")
    @Expose
    public Object starticketNo;
    @SerializedName("seat_no")
    @Expose
    public String seatNo;
    @SerializedName("price")
    @Expose
    public String price;
    @SerializedName("foreign_price")
    @Expose
    public String foreignPrice;
    @SerializedName("free_ticket")
    @Expose
    public String freeTicket;
    @SerializedName("commission")
    @Expose
    public String commission;
    @SerializedName("discount")
    @Expose
    public String discount;
    @SerializedName("sub_total")
    @Expose
    public String subTotal;
    @SerializedName("remark")
    @Expose
    public String remark;
    @SerializedName("created_at")
    @Expose
    public String createdAt;
    @SerializedName("updated_at")
    @Expose
    public String updatedAt;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNrcNo() {
		return nrcNo;
	}
	public void setNrcNo(String nrcNo) {
		this.nrcNo = nrcNo;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getTripId() {
		return tripId;
	}
	public void setTripId(String tripId) {
		this.tripId = tripId;
	}
	public String getTicketNo() {
		return ticketNo;
	}
	public void setTicketNo(String ticketNo) {
		this.ticketNo = ticketNo;
	}
	public Object getStarticketNo() {
		return starticketNo;
	}
	public void setStarticketNo(Object starticketNo) {
		this.starticketNo = starticketNo;
	}
	public String getSeatNo() {
		return seatNo;
	}
	public void setSeatNo(String seatNo) {
		this.seatNo = seatNo;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getForeignPrice() {
		return foreignPrice;
	}
	public void setForeignPrice(String foreignPrice) {
		this.foreignPrice = foreignPrice;
	}
	public String getFreeTicket() {
		return freeTicket;
	}
	public void setFreeTicket(String freeTicket) {
		this.freeTicket = freeTicket;
	}
	public String getCommission() {
		return commission;
	}
	public void setCommission(String commission) {
		this.commission = commission;
	}
	public String getDiscount() {
		return discount;
	}
	public void setDiscount(String discount) {
		this.discount = discount;
	}
	public String getSubTotal() {
		return subTotal;
	}
	public void setSubTotal(String subTotal) {
		this.subTotal = subTotal;
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
		return "Saleitem [id=" + id + ", orderId=" + orderId + ", name=" + name
				+ ", nrcNo=" + nrcNo + ", phone=" + phone + ", tripId="
				+ tripId + ", ticketNo=" + ticketNo + ", starticketNo="
				+ starticketNo + ", seatNo=" + seatNo + ", price=" + price
				+ ", foreignPrice=" + foreignPrice + ", freeTicket="
				+ freeTicket + ", commission=" + commission + ", discount="
				+ discount + ", subTotal=" + subTotal + ", remark=" + remark
				+ ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
	}
    
    

}
