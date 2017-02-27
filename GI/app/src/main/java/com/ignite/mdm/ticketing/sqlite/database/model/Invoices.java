package com.ignite.mdm.ticketing.sqlite.database.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by user on 1/30/17.
 */
public class Invoices {

  @SerializedName("id") @Expose private String id;
  @SerializedName("invoice_no") @Expose private String invoiceNo;
  @SerializedName("inv_date") @Expose private String invDate;
  @SerializedName("total_amount") @Expose private Integer totalAmount;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getInvoiceNo() {
    return invoiceNo;
  }

  public void setInvoiceNo(String invoiceNo) {
    this.invoiceNo = invoiceNo;
  }

  public String getInvDate() {
    return invDate;
  }

  public void setInvDate(String invDate) {
    this.invDate = invDate;
  }

  public Integer getTotalAmount() {
    return totalAmount;
  }

  public void setTotalAmount(Integer totalAmount) {
    this.totalAmount = totalAmount;
  }

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Invoices)) return false;

    Invoices invoices = (Invoices) o;

    return invoiceNo != null ? invoiceNo.equals(invoices.invoiceNo) : invoices.invoiceNo == null;
  }

  @Override public int hashCode() {
    return invoiceNo != null ? invoiceNo.hashCode() : 0;
  }
}