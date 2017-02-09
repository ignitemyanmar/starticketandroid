package com.ignite.mdm.ticketing.sqlite.database.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by user on 1/22/17.
 */

public class SoldTicketList {

  @SerializedName("id") @Expose private String id;

  @SerializedName("price") @Expose private String price;
  @SerializedName("orderdate") @Expose private String orderdate;
  @SerializedName("departure_date") @Expose private String departureDate;
  @SerializedName("name") @Expose private String name;
  @SerializedName("nrc_no") @Expose private String nrcNo;
  @SerializedName("phone") @Expose private String phone;
  @SerializedName("total_amount") @Expose private String totalAmount;
  @SerializedName("agent_commission") @Expose private String agentCommission;
  @SerializedName("booking") @Expose private String booking;
  @SerializedName("nationality") @Expose private String nationality;
  @SerializedName("remark") @Expose private String remark;
  @SerializedName("agent_name") @Expose private String agentName;
  @SerializedName("agent_group_name") @Expose private String agentGroupName;
  @SerializedName("from") @Expose private String from;
  @SerializedName("to") @Expose private String to;
  @SerializedName("boarding_alighting_city") @Expose private Object boardingAlightingCity;
  @SerializedName("busclass") @Expose private String busclass;
  @SerializedName("seat_no") @Expose private String seatNo;
  @SerializedName("ticket_no") @Expose private String ticketNo;
  @SerializedName("seat_qty") @Expose private Integer seatQty;
  @SerializedName("source") @Expose private Integer source;

  @SerializedName("operator") @Expose private String operator;
  @SerializedName("operator_phone") @Expose private String operatorPhone;
  @SerializedName("time") @Expose private String time;

  public String getOperator() {
    return operator;
  }

  public void setOperator(String operator) {
    this.operator = operator;
  }

  public String getOperatorPhone() {
    return operatorPhone;
  }

  public void setOperatorPhone(String operatorPhone) {
    this.operatorPhone = operatorPhone;
  }

  public String getTime() {
    return time;
  }

  public void setTime(String time) {
    this.time = time;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getOrderdate() {
    return orderdate;
  }

  public void setOrderdate(String orderdate) {
    this.orderdate = orderdate;
  }

  public String getDepartureDate() {
    return departureDate;
  }

  public void setDepartureDate(String departureDate) {
    this.departureDate = departureDate;
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

  public String getTotalAmount() {
    return totalAmount;
  }

  public void setTotalAmount(String totalAmount) {
    this.totalAmount = totalAmount;
  }

  public String getAgentCommission() {
    return agentCommission;
  }

  public void setAgentCommission(String agentCommission) {
    this.agentCommission = agentCommission;
  }

  public String getBooking() {
    return booking;
  }

  public void setBooking(String booking) {
    this.booking = booking;
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

  public String getAgentName() {
    return agentName;
  }

  public void setAgentName(String agentName) {
    this.agentName = agentName;
  }

  public String getAgentGroupName() {
    return agentGroupName;
  }

  public void setAgentGroupName(String agentGroupName) {
    this.agentGroupName = agentGroupName;
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

  public String getPrice() {
    return price;
  }

  public void setPrice(String price) {
    this.price = price;
  }

  public void setTo(String to) {
    this.to = to;
  }

  public Object getBoardingAlightingCity() {
    return boardingAlightingCity;
  }

  public void setBoardingAlightingCity(Object boardingAlightingCity) {
    this.boardingAlightingCity = boardingAlightingCity;
  }

  public String getBusclass() {
    return busclass;
  }

  public void setBusclass(String busclass) {
    this.busclass = busclass;
  }

  public String getSeatNo() {
    return seatNo;
  }

  public void setSeatNo(String seatNo) {
    this.seatNo = seatNo;
  }

  public String getTicketNo() {
    return ticketNo;
  }

  public void setTicketNo(String ticketNo) {
    this.ticketNo = ticketNo;
  }

  public Integer getSeatQty() {
    return seatQty;
  }

  public void setSeatQty(Integer seatQty) {
    this.seatQty = seatQty;
  }

  public Integer getSource() {
    return source;
  }

  public void setSource(Integer source) {
    this.source = source;
  }

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof SoldTicketList)) return false;

    SoldTicketList that = (SoldTicketList) o;

    return id != null ? id.equals(that.id) : that.id == null;
  }

  @Override public int hashCode() {
    return id != null ? id.hashCode() : 0;
  }
}