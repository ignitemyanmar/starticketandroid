
package com.ignite.mm.ticketing.sqlite.database.model;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Delivery {

    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("date")
    @Expose
    public String date;
    @SerializedName("departure_date")
    @Expose
    public String departureDate;
    @SerializedName("from")
    @Expose
    public String from;
    @SerializedName("to")
    @Expose
    public String to;
    @SerializedName("extra_destination_id")
    @Expose
    public String extraDestinationId;
    @SerializedName("time")
    @Expose
    public String time;
    @SerializedName("class")
    @Expose
    public String _class;
    @SerializedName("trip_id")
    @Expose
    public String tripId;
    @SerializedName("arrival_date")
    @Expose
    public String arrivalDate;
    @SerializedName("customer_id")
    @Expose
    public String customerId;
    @SerializedName("customer_name")
    @Expose
    public String customerName;
    @SerializedName("customer_nrc")
    @Expose
    public String customerNrc;
    @SerializedName("customer_phone")
    @Expose
    public String customerPhone;
    @SerializedName("agent_id")
    @Expose
    public String agentId;
    @SerializedName("agent_code_no")
    @Expose
    public String agentCodeNo;
    @SerializedName("operator_id")
    @Expose
    public String operatorId;
    @SerializedName("seller_id")
    @Expose
    public String sellerId;
    @SerializedName("transaction_id")
    @Expose
    public String transactionId;
    @SerializedName("loyalty_code")
    @Expose
    public Object loyaltyCode;
    @SerializedName("discount_amount")
    @Expose
    public String discountAmount;
    @SerializedName("booking_user_id")
    @Expose
    public String bookingUserId;
    @SerializedName("total_amount")
    @Expose
    public String totalAmount;
    @SerializedName("payment_type")
    @Expose
    public String paymentType;
    @SerializedName("delivery")
    @Expose
    public String delivery;
    @SerializedName("booking_status")
    @Expose
    public String bookingStatus;
    @SerializedName("booking_expired")
    @Expose
    public String bookingExpired;
    @SerializedName("deleted_flag")
    @Expose
    public String deletedFlag;
    @SerializedName("remark")
    @Expose
    public String remark;
    @SerializedName("round_trip")
    @Expose
    public String roundTrip;
    @SerializedName("created_at")
    @Expose
    public String createdAt;
    @SerializedName("updated_at")
    @Expose
    public String updatedAt;
    @SerializedName("total_seat")
    @Expose
    public int totalSeat;
    @SerializedName("departure_date_time")
    @Expose
    public String departureDateTime;
    @SerializedName("free_ticket")
    @Expose
    public int freeTicket;
    @SerializedName("seat_nos")
    @Expose
    public String seatNos;
    @SerializedName("ticket_nos")
    @Expose
    public String ticketNos;
    @SerializedName("price")
    @Expose
    public String price;
    @SerializedName("operator")
    @Expose
    public String operator;
    @SerializedName("trip")
    @Expose
    public String trip;
    @SerializedName("agent_name")
    @Expose
    public String agentName;
    @SerializedName("saleitems")
    @Expose
    public List<Saleitem> saleitems = new ArrayList<Saleitem>();
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
	public String getDiscountAmount() {
		return discountAmount;
	}
	public void setDiscountAmount(String discountAmount) {
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
	public int getTotalSeat() {
		return totalSeat;
	}
	public void setTotalSeat(int totalSeat) {
		this.totalSeat = totalSeat;
	}
	public String getDepartureDateTime() {
		return departureDateTime;
	}
	public void setDepartureDateTime(String departureDateTime) {
		this.departureDateTime = departureDateTime;
	}
	public int getFreeTicket() {
		return freeTicket;
	}
	public void setFreeTicket(int freeTicket) {
		this.freeTicket = freeTicket;
	}
	public String getSeatNos() {
		return seatNos;
	}
	public void setSeatNos(String seatNos) {
		this.seatNos = seatNos;
	}
	public String getTicketNos() {
		return ticketNos;
	}
	public void setTicketNos(String ticketNos) {
		this.ticketNos = ticketNos;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getTrip() {
		return trip;
	}
	public void setTrip(String trip) {
		this.trip = trip;
	}
	public String getAgentName() {
		return agentName;
	}
	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}
	public List<Saleitem> getSaleitems() {
		return saleitems;
	}
	public void setSaleitems(List<Saleitem> saleitems) {
		this.saleitems = saleitems;
	}
	
	
	public String get_class() {
		return _class;
	}
	public void set_class(String _class) {
		this._class = _class;
	}
	@Override
	public String toString() {
		return "Delivery [id=" + id + ", date=" + date + ", departureDate="
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
				+ delivery + ", bookingStatus=" + bookingStatus
				+ ", bookingExpired=" + bookingExpired + ", deletedFlag="
				+ deletedFlag + ", remark=" + remark + ", roundTrip="
				+ roundTrip + ", createdAt=" + createdAt + ", updatedAt="
				+ updatedAt + ", totalSeat=" + totalSeat
				+ ", departureDateTime=" + departureDateTime + ", freeTicket="
				+ freeTicket + ", seatNos=" + seatNos + ", ticketNos="
				+ ticketNos + ", price=" + price + ", operator=" + operator
				+ ", trip=" + trip + ", agentName=" + agentName
				+ ", saleitems=" + saleitems + "]";
	}
	
	

}
