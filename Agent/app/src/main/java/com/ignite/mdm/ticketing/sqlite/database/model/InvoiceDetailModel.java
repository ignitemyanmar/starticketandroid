package com.ignite.mdm.ticketing.sqlite.database.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

/**
 * Created by user on 2/1/17.
 */

public class InvoiceDetailModel implements Serializable{
  @SerializedName("from") @Expose private String from;
  @SerializedName("to") @Expose private String to;
  @SerializedName("departure_date") @Expose private String departureDate;
  @SerializedName("time") @Expose private String time;
  @SerializedName("class") @Expose private String _class;
  @SerializedName("customer_name") @Expose private String customerName;
  @SerializedName("customer_phone") @Expose private String customerPhone;
  @SerializedName("remark") @Expose private String remark;
  @SerializedName("seat_no") @Expose private String seatNo;
  @SerializedName("seat_qty") @Expose private Integer seatQty;
  @SerializedName("commission") @Expose private Integer commission;
  @SerializedName("net_amount") @Expose private Integer netAmount;

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

  public String getDepartureDate() {
    return departureDate;
  }

  public void setDepartureDate(String departureDate) {
    this.departureDate = departureDate;
  }

  public String getTime() {
    return time;
  }

  public void setTime(String time) {
    this.time = time;
  }

  public String getClass_() {
    return _class;
  }

  public void setClass_(String _class) {
    this._class = _class;
  }

  public String getCustomerName() {
    return customerName;
  }

  public void setCustomerName(String customerName) {
    this.customerName = customerName;
  }

  public String getCustomerPhone() {
    return customerPhone;
  }

  public void setCustomerPhone(String customerPhone) {
    this.customerPhone = customerPhone;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public String getSeatNo() {
    return seatNo;
  }

  public void setSeatNo(String seatNo) {
    this.seatNo = seatNo;
  }

  public Integer getSeatQty() {
    return seatQty;
  }

  public void setSeatQty(Integer seatQty) {
    this.seatQty = seatQty;
  }

  public Integer getCommission() {
    return commission;
  }

  public void setCommission(Integer commission) {
    this.commission = commission;
  }

  public Integer getNetAmount() {
    return netAmount;
  }

  public void setNetAmount(Integer netAmount) {
    this.netAmount = netAmount;
  }

  @Override public String toString() {
    return "InvoiceDetailModel{" +
        "_class='" + _class + '\'' +
        ", from='" + from + '\'' +
        ", to='" + to + '\'' +
        ", departureDate='" + departureDate + '\'' +
        ", time='" + time + '\'' +
        ", customerName='" + customerName + '\'' +
        ", customerPhone='" + customerPhone + '\'' +
        ", remark='" + remark + '\'' +
        ", seatNo='" + seatNo + '\'' +
        ", seatQty=" + seatQty +
        ", commission=" + commission +
        ", netAmount=" + netAmount +
        '}';
  }
}
