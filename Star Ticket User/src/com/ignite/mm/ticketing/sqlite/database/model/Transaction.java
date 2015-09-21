
package com.ignite.mm.ticketing.sqlite.database.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Transaction {

@SerializedName("order_id")
@Expose
private String orderId;
@SerializedName("transaction_id")
@Expose
private String transactionId;
@Expose
private String date;
@SerializedName("departure_date")
@Expose
private String departureDate;
@SerializedName("trip_id")
@Expose
private String tripId;
@Expose
private String from;
@Expose
private String to;
@SerializedName("extra_destination_id")
@Expose
private String extraDestinationId;
@Expose
private String trip;
@Expose
private String time;
@SerializedName("class")
@Expose
private String _class;
@SerializedName("agent_id")
@Expose
private String agentId;
@SerializedName("agent_code_no")
@Expose
private String agentCodeNo;
@SerializedName("agentgroup_name")
@Expose
private String agentgroupName;
@SerializedName("operator_id")
@Expose
private String operatorId;
@Expose
private String operator;
@SerializedName("operator_phone")
@Expose
private String operatorPhone;
@SerializedName("customer_name")
@Expose
private String customerName;
@SerializedName("customer_nrc")
@Expose
private String customerNrc;
@SerializedName("customer_phone")
@Expose
private String customerPhone;
@SerializedName("ticket_no")
@Expose
private String ticketNo;
@SerializedName("seat_no")
@Expose
private String seatNo;
@Expose
private String passengers;
@Expose
private String price;
@SerializedName("ticket_qty")
@Expose
private int ticketQty;
@SerializedName("discount_amount")
@Expose
private String discountAmount;
@SerializedName("total_amount")
@Expose
private String totalAmount;
@SerializedName("payment_type")
@Expose
private String paymentType;
@Expose
private String delivery;
@SerializedName("Total_USD")
@Expose
private int TotalUSD;
@SerializedName("exchange_rate")
@Expose
private int exchangeRate;
@SerializedName("seller_id")
@Expose
private String sellerId;
@Expose
private Seller seller;

/**
* 
* @return
* The orderId
*/
public String getOrderId() {
return orderId;
}

/**
* 
* @param orderId
* The order_id
*/
public void setOrderId(String orderId) {
this.orderId = orderId;
}

/**
* 
* @return
* The transactionId
*/
public String getTransactionId() {
return transactionId;
}

/**
* 
* @param transactionId
* The transaction_id
*/
public void setTransactionId(String transactionId) {
this.transactionId = transactionId;
}

/**
* 
* @return
* The date
*/
public String getDate() {
return date;
}

/**
* 
* @param date
* The date
*/
public void setDate(String date) {
this.date = date;
}

/**
* 
* @return
* The departureDate
*/
public String getDepartureDate() {
return departureDate;
}

/**
* 
* @param departureDate
* The departure_date
*/
public void setDepartureDate(String departureDate) {
this.departureDate = departureDate;
}

/**
* 
* @return
* The tripId
*/
public String getTripId() {
return tripId;
}

/**
* 
* @param tripId
* The trip_id
*/
public void setTripId(String tripId) {
this.tripId = tripId;
}

/**
* 
* @return
* The from
*/
public String getFrom() {
return from;
}

/**
* 
* @param from
* The from
*/
public void setFrom(String from) {
this.from = from;
}

/**
* 
* @return
* The to
*/
public String getTo() {
return to;
}

/**
* 
* @param to
* The to
*/
public void setTo(String to) {
this.to = to;
}

/**
* 
* @return
* The extraDestinationId
*/
public String getExtraDestinationId() {
return extraDestinationId;
}

/**
* 
* @param extraDestinationId
* The extra_destination_id
*/
public void setExtraDestinationId(String extraDestinationId) {
this.extraDestinationId = extraDestinationId;
}

/**
* 
* @return
* The trip
*/
public String getTrip() {
return trip;
}

/**
* 
* @param trip
* The trip
*/
public void setTrip(String trip) {
this.trip = trip;
}

/**
* 
* @return
* The time
*/
public String getTime() {
return time;
}

/**
* 
* @param time
* The time
*/
public void setTime(String time) {
this.time = time;
}

/**
* 
* @return
* The _class
*/
public String getClass_() {
return _class;
}

/**
* 
* @param _class
* The class
*/
public void setClass_(String _class) {
this._class = _class;
}

/**
* 
* @return
* The agentId
*/
public String getAgentId() {
return agentId;
}

/**
* 
* @param agentId
* The agent_id
*/
public void setAgentId(String agentId) {
this.agentId = agentId;
}

/**
* 
* @return
* The agentCodeNo
*/
public String getAgentCodeNo() {
return agentCodeNo;
}

/**
* 
* @param agentCodeNo
* The agent_code_no
*/
public void setAgentCodeNo(String agentCodeNo) {
this.agentCodeNo = agentCodeNo;
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
* The operator
*/
public String getOperator() {
return operator;
}

/**
* 
* @param operator
* The operator
*/
public void setOperator(String operator) {
this.operator = operator;
}

/**
* 
* @return
* The operatorPhone
*/
public String getOperatorPhone() {
return operatorPhone;
}

/**
* 
* @param operatorPhone
* The operator_phone
*/
public void setOperatorPhone(String operatorPhone) {
this.operatorPhone = operatorPhone;
}

/**
* 
* @return
* The customerName
*/
public String getCustomerName() {
return customerName;
}

/**
* 
* @param customerName
* The customer_name
*/
public void setCustomerName(String customerName) {
this.customerName = customerName;
}

/**
* 
* @return
* The customerNrc
*/
public String getCustomerNrc() {
return customerNrc;
}

/**
* 
* @param customerNrc
* The customer_nrc
*/
public void setCustomerNrc(String customerNrc) {
this.customerNrc = customerNrc;
}

/**
* 
* @return
* The customerPhone
*/
public String getCustomerPhone() {
return customerPhone;
}

/**
* 
* @param customerPhone
* The customer_phone
*/
public void setCustomerPhone(String customerPhone) {
this.customerPhone = customerPhone;
}

/**
* 
* @return
* The ticketNo
*/
public String getTicketNo() {
return ticketNo;
}

/**
* 
* @param ticketNo
* The ticket_no
*/
public void setTicketNo(String ticketNo) {
this.ticketNo = ticketNo;
}

/**
* 
* @return
* The seatNo
*/
public String getSeatNo() {
return seatNo;
}

/**
* 
* @param seatNo
* The seat_no
*/
public void setSeatNo(String seatNo) {
this.seatNo = seatNo;
}

/**
* 
* @return
* The passengers
*/
public String getPassengers() {
return passengers;
}

/**
* 
* @param passengers
* The passengers
*/
public void setPassengers(String passengers) {
this.passengers = passengers;
}

/**
* 
* @return
* The price
*/
public String getPrice() {
return price;
}

/**
* 
* @param price
* The price
*/
public void setPrice(String price) {
this.price = price;
}

/**
* 
* @return
* The ticketQty
*/
public int getTicketQty() {
return ticketQty;
}

/**
* 
* @param ticketQty
* The ticket_qty
*/
public void setTicketQty(int ticketQty) {
this.ticketQty = ticketQty;
}

/**
* 
* @return
* The discountAmount
*/
public String getDiscountAmount() {
return discountAmount;
}

/**
* 
* @param discountAmount
* The discount_amount
*/
public void setDiscountAmount(String discountAmount) {
this.discountAmount = discountAmount;
}

/**
* 
* @return
* The totalAmount
*/
public String getTotalAmount() {
return totalAmount;
}

/**
* 
* @param totalAmount
* The total_amount
*/
public void setTotalAmount(String totalAmount) {
this.totalAmount = totalAmount;
}

/**
* 
* @return
* The paymentType
*/
public String getPaymentType() {
return paymentType;
}

/**
* 
* @param paymentType
* The payment_type
*/
public void setPaymentType(String paymentType) {
this.paymentType = paymentType;
}

/**
* 
* @return
* The delivery
*/
public String getDelivery() {
return delivery;
}

/**
* 
* @param delivery
* The delivery
*/
public void setDelivery(String delivery) {
this.delivery = delivery;
}

/**
* 
* @return
* The TotalUSD
*/
public int getTotalUSD() {
return TotalUSD;
}

/**
* 
* @param TotalUSD
* The Total_USD
*/
public void setTotalUSD(int TotalUSD) {
this.TotalUSD = TotalUSD;
}

/**
* 
* @return
* The exchangeRate
*/
public int getExchangeRate() {
return exchangeRate;
}

/**
* 
* @param exchangeRate
* The exchange_rate
*/
public void setExchangeRate(int exchangeRate) {
this.exchangeRate = exchangeRate;
}

/**
* 
* @return
* The sellerId
*/
public String getSellerId() {
return sellerId;
}

/**
* 
* @param sellerId
* The seller_id
*/
public void setSellerId(String sellerId) {
this.sellerId = sellerId;
}

/**
* 
* @return
* The seller
*/
public Seller getSeller() {
return seller;
}

/**
* 
* @param seller
* The seller
*/
public void setSeller(Seller seller) {
this.seller = seller;
}

}