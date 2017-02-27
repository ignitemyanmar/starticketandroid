
package com.ignite.mdm.ticketing.sqlite.database;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class SoldTicketModel {

    @SerializedName("sold_ticket_list")
    @Expose
    private List<com.ignite.mdm.ticketing.sqlite.database.model.SoldTicketList> soldTicketList = null;
    @SerializedName("agent_group_name")
    @Expose
    private String agentGroupName;
    @SerializedName("total_amount")
    @Expose
    private Integer totalAmount;
    @SerializedName("total_commission")
    @Expose
    private Integer totalCommission;
    @SerializedName("total_discount")
    @Expose
    private Integer totalDiscount;
    @SerializedName("total_ticketqty")
    @Expose
    private Integer totalTicketqty;

    public List<com.ignite.mdm.ticketing.sqlite.database.model.SoldTicketList> getSoldTicketList() {
        return soldTicketList;
    }

    public void setSoldTicketList(List<com.ignite.mdm.ticketing.sqlite.database.model.SoldTicketList> soldTicketList) {
        this.soldTicketList = soldTicketList;
    }

    public String getAgentGroupName() {
        return agentGroupName;
    }

    public void setAgentGroupName(String agentGroupName) {
        this.agentGroupName = agentGroupName;
    }

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getTotalCommission() {
        return totalCommission;
    }

    public void setTotalCommission(Integer totalCommission) {
        this.totalCommission = totalCommission;
    }

    public Integer getTotalDiscount() {
        return totalDiscount;
    }

    public void setTotalDiscount(Integer totalDiscount) {
        this.totalDiscount = totalDiscount;
    }

    public Integer getTotalTicketqty() {
        return totalTicketqty;
    }

    public void setTotalTicketqty(Integer totalTicketqty) {
        this.totalTicketqty = totalTicketqty;
    }

}
