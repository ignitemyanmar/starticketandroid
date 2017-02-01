package com.ignite.mdm.ticketing.sqlite.database.model;

import com.google.gson.Gson;

public class GetAgentBalanceRequest {
	
	private String access_token;
	private String code_no;
	
	public GetAgentBalanceRequest(String access_token, String code_no) {
		super();
		this.access_token = access_token;
		this.code_no = code_no;
	}
	
	public String toJson(){
		return new Gson().toJson(this);
	}
	

}
