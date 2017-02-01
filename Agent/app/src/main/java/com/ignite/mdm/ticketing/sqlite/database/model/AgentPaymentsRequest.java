package com.ignite.mdm.ticketing.sqlite.database.model;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class AgentPaymentsRequest {
	
	
	@SerializedName("access_token")
	private String access_token;
	
	@SerializedName("code_no")
	private String code_no;
	
	@SerializedName("start_date")
	private String start_date;
	
	@SerializedName("end_date")
	private String end_date;

	public AgentPaymentsRequest(String access_token, String code_no,
			String start_date, String end_date) {
		super();
		this.access_token = access_token;
		this.code_no = code_no;
		this.start_date = start_date;
		this.end_date = end_date;
	}
	
	public String toJson(){
		return new Gson().toJson(this);
	}

}
