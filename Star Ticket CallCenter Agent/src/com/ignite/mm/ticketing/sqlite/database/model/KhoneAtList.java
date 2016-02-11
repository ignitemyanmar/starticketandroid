
package com.ignite.mm.ticketing.sqlite.database.model;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class KhoneAtList {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("departure_date")
    @Expose
    private String departureDate;
    @SerializedName("from")
    @Expose
    private String from;
    @SerializedName("to")
    @Expose
    private String to;
    @SerializedName("extra_destination_id")
    @Expose
    private String extraDestinationId;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("class")
    @Expose
    private String _class;
    @SerializedName("trip_id")
    @Expose
    private String tripId;
    @SerializedName("arrival_date")
    @Expose
    private String arrivalDate;
    @SerializedName("customer_id")
    @Expose
    private String customerId;
    @SerializedName("customer_name")
    @Expose
    private String customerName;
    @SerializedName("customer_nrc")
    @Expose
    private String customerNrc;
    @SerializedName("customer_phone")
    @Expose
    private String customerPhone;
    @SerializedName("agent_id")
    @Expose
    private String agentId;
    @SerializedName("agent_code_no")
    @Expose
    private String agentCodeNo;
    @SerializedName("operator_id")
    @Expose
    private String operatorId;
    @SerializedName("seller_id")
    @Expose
    private String sellerId;
    @SerializedName("transaction_id")
    @Expose
    private String transactionId;
    @SerializedName("loyalty_code")
    @Expose
    private Object loyaltyCode;
    @SerializedName("discount_amount")
    @Expose
    private Object discountAmount;
    @SerializedName("booking_user_id")
    @Expose
    private String bookingUserId;
    @SerializedName("total_amount")
    @Expose
    private String totalAmount;
    @SerializedName("payment_type")
    @Expose
    private String paymentType;
    @SerializedName("delivery")
    @Expose
    private String delivery;
    @SerializedName("delivery_address")
    @Expose
    private Object deliveryAddress;
    @SerializedName("booking_status")
    @Expose
    private String bookingStatus;
    @SerializedName("booking_expired")
    @Expose
    private String bookingExpired;
    @SerializedName("deleted_flag")
    @Expose
    private String deletedFlag;
    @SerializedName("nationality")
    @Expose
    private String nationality;
    @SerializedName("remark")
    @Expose
    private String remark;
    @SerializedName("device_id")
    @Expose
    private String deviceId;
    @SerializedName("round_trip")
    @Expose
    private String roundTrip;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("time_to_24hour")
    @Expose
    private String timeTo24hour;
    @SerializedName("knoneup_time")
    @Expose
    private String knoneupTime;
    @SerializedName("trip")
    @Expose
    private Trip trip;
    @SerializedName("saleitems")
    @Expose
    private List<Saleitem> saleitems = new ArrayList<Saleitem>();
    @SerializedName("seller")
    @Expose
    private Seller seller;
    @Expose
    private String ticket_nos;
    @Expose
    private String trip_name;
    @Expose
    private String seat_nos;
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getDepartureDate() {
		return departureDate;
	}
	public void setDepartureDate(String departureDate) {
		this.departureDate = departureDate;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getExtraDestinationId() {
		return extraDestinationId;
	}
	public void setExtraDestinationId(String extraDestinationId) {
		this.extraDestinationId = extraDestinationId;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getTripId() {
		return tripId;
	}
	public void setTripId(String tripId) {
		this.tripId = tripId;
	}
	public String getArrivalDate() {
		return arrivalDate;
	}
	public void setArrivalDate(String arrivalDate) {
		this.arrivalDate = arrivalDate;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerNrc() {
		return customerNrc;
	}
	public void setCustomerNrc(String customerNrc) {
		this.customerNrc = customerNrc;
	}
	public String getCustomerPhone() {
		return customerPhone;
	}
	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}
	public String getAgentId() {
		return agentId;
	}
	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}
	public String getAgentCodeNo() {
		return agentCodeNo;
	}
	public void setAgentCodeNo(String agentCodeNo) {
		this.agentCodeNo = agentCodeNo;
	}
	public String getOperatorId() {
		return operatorId;
	}
	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}
	public String getSellerId() {
		return sellerId;
	}
	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public Object getLoyaltyCode() {
		return loyaltyCode;
	}
	public void setLoyaltyCode(Object loyaltyCode) {
		this.loyaltyCode = loyaltyCode;
	}
	public Object getDiscountAmount() {
		return discountAmount;
	}
	public void setDiscountAmount(Object discountAmount) {
		this.discountAmount = discountAmount;
	}
	public String getBookingUserId() {
		return bookingUserId;
	}
	public void setBookingUserId(String bookingUserId) {
		this.bookingUserId = bookingUserId;
	}
	public String getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	public String getDelivery() {
		return delivery;
	}
	public void setDelivery(String delivery) {
		this.delivery = delivery;
	}
	public Object getDeliveryAddress() {
		return deliveryAddress;
	}
	public void setDeliveryAddress(Object deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}
	public String getBookingStatus() {
		return bookingStatus;
	}
	public void setBookingStatus(String bookingStatus) {
		this.bookingStatus = bookingStatus;
	}
	public String getBookingExpired() {
		return bookingExpired;
	}
	public void setBookingExpired(String bookingExpired) {
		this.bookingExpired = bookingExpired;
	}
	public String getDeletedFlag() {
		return deletedFlag;
	}
	public void setDeletedFlag(String deletedFlag) {
		this.deletedFlag = deletedFlag;
	}
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getRoundTrip() {
		return roundTrip;
	}
	public void setRoundTrip(String roundTrip) {
		this.roundTrip = roundTrip;
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
	public String getTimeTo24hour() {
		return timeTo24hour;
	}
	public void setTimeTo24hour(String timeTo24hour) {
		this.timeTo24hour = timeTo24hour;
	}
	public String getKnoneupTime() {
		return knoneupTime;
	}
	public void setKnoneupTime(String knoneupTime) {
		this.knoneupTime = knoneupTime;
	}
	public Trip getTrip() {
		return trip;
	}
	public void setTrip(Trip trip) {
		this.trip = trip;
	}
	public List<Saleitem> getSaleitems() {
		return saleitems;
	}
	public void setSaleitems(List<Saleitem> saleitems) {
		this.saleitems = saleitems;
	}
	public Seller getSeller() {
		return seller;
	}
	public void setSeller(Seller seller) {
		this.seller = seller;
	}
	
	
	public String getTicket_nos() {
		return ticket_nos;
	}
	public void setTicket_nos(String ticket_nos) {
		this.ticket_nos = ticket_nos;
	}
	
	public String get_class() {
		return _class;
	}
	public void set_class(String _class) {
		this._class = _class;
	}
	
	public String getTrip_name() {
		return trip_name;
	}
	public void setTrip_name(String trip_name) {
		this.trip_name = trip_name;
	}
	
	public String getSeat_nos() {
		return seat_nos;
	}
	public void setSeat_nos(String seat_nos) {
		this.seat_nos = seat_nos;
	}
	@Override
	public String toString() {
		return "KhoneAtList [id=" + id + ", date=" + date + ", departureDate="
				+ departureDate + ", from=" + from + ", to=" + to
				+ ", extraDestinationId=" + extraDestinationId + ", time="
				+ time + ", _class=" + _class + ", tripId=" + tripId
				+ ", arrivalDate=" + arrivalDate + ", customerId=" + customerId
				+ ", customerName=" + customerName + ", customerNrc="
				+ customerNrc + ", customerPhone=" + customerPhone
				+ ", agentId=" + agentId + ", agentCodeNo=" + agentCodeNo
				+ ", operatorId=" + operatorId + ", sellerId=" + sellerId
				+ ", transactionId=" + transactionId + ", loyaltyCode="
				+ loyaltyCode + ", discountAmount=" + discountAmount
				+ ", bookingUserId=" + bookingUserId + ", totalAmount="
				+ totalAmount + ", paymentType=" + paymentType + ", delivery="
				+ delivery + ", deliveryAddress=" + deliveryAddress
				+ ", bookingStatus=" + bookingStatus + ", bookingExpired="
				+ bookingExpired + ", deletedFlag=" + deletedFlag
				+ ", nationality=" + nationality + ", remark=" + remark
				+ ", deviceId=" + deviceId + ", roundTrip=" + roundTrip
				+ ", createdAt=" + createdAt + ", updatedAt=" + updatedAt
				+ ", timeTo24hour=" + timeTo24hour + ", knoneupTime="
				+ knoneupTime + ", trip=" + trip + ", saleitems=" + saleitems
				+ ", seller=" + seller + ", ticket_nos=" + ticket_nos + "]";
	}
	
}
