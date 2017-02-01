package com.ignite.mdm.ticketing.sqlite.database.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.Map;

/**
 * Created by user on 2/1/17.
 */

public class InvoiceDetail {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("invoice_no")
    @Expose
    private String invoiceNo;
    @SerializedName("inv_date")
    @Expose
    private String invDate;
    @SerializedName("total_amount")
    @Expose
    private String totalAmount;
    @SerializedName("tickets")
    @Expose
    private Map<String,InvoiceDetailModel> tickets;

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

    public String getTotalAmount() {
      return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
      this.totalAmount = totalAmount;
    }

  public Map<String, InvoiceDetailModel> getTickets() {
    return tickets;
  }

  public void setTickets(Map<String, InvoiceDetailModel> tickets) {
    this.tickets = tickets;
  }

  @Override public String toString() {
    return "InvoiceDetail{" +
        "id='" + id + '\'' +
        ", invoiceNo='" + invoiceNo + '\'' +
        ", invDate='" + invDate + '\'' +
        ", totalAmount='" + totalAmount + '\'' +
        ", tickets=" + tickets +
        '}';
  }
}
