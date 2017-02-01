package com.ignite.mdm.ticketing.sqlite.database.model;

import com.google.gson.annotations.SerializedName;

public class CbOddRequest {
	
	@SerializedName("agentgroup_id")
	private String agentgroup_id;
	
	public void setAgentgroup_id(String agentgroup_id) {
		this.agentgroup_id = agentgroup_id;
	}
	

}
