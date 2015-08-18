package com.ignite.mm.ticketing.sqlite.database.model;

import com.google.gson.annotations.Expose;

public class Currency {
	
	@Expose
	private Integer usd;

	public Currency(Integer usd) {
		super();
		this.usd = usd;
	}

	public Integer getUsd() {
		return usd;
	}

	public void setUsd(Integer usd) {
		this.usd = usd;
	}

	@Override
	public String toString() {
		return "Currency [usd=" + usd + "]";
	}
	
	
	
}