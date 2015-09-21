package com.ignite.mm.ticketing.sqlite.database.model;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyOrder {

@SerializedName("transaction_id")
@Expose
private int transactionId;
@Expose
private List<Transaction> transactions = new ArrayList<Transaction>();
@Expose
private int totalAmount;
@Expose
private int totalDiscount;



public MyOrder() {
	super();
	// TODO Auto-generated constructor stub
}

public MyOrder(int totalAmount, int totalDiscount) {
	super();
	this.totalAmount = totalAmount;
	this.totalDiscount = totalDiscount;
}

/**
* 
* @return
* The transactionId
*/
public int getTransactionId() {
return transactionId;
}

/**
* 
* @param transactionId
* The transaction_id
*/
public void setTransactionId(int transactionId) {
this.transactionId = transactionId;
}

/**
* 
* @return
* The transactions
*/
public List<Transaction> getTransactions() {
return transactions;
}

/**
* 
* @param transactions
* The transactions
*/
public void setTransactions(List<Transaction> transactions) {
this.transactions = transactions;
}

public int getTotalAmount() {
	return totalAmount;
}

public void setTotalAmount(int totalAmount) {
	this.totalAmount = totalAmount;
}

public int getTotalDiscount() {
	return totalDiscount;
}

public void setTotalDiscount(int totalDiscount) {
	this.totalDiscount = totalDiscount;
}

@Override
public String toString() {
	return "MyOrder [transactionId=" + transactionId + ", transactions="
			+ transactions + ", totalAmount=" + totalAmount
			+ ", totalDiscount=" + totalDiscount + "]";
}



}