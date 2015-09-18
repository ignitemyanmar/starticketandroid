package com.ignite.mm.ticketing.sqlite.database.model;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.annotations.Expose;

public class Permission {

	@Expose
	private String ip;
	@Expose
	private String access_token;
	@Expose
	private String operator_id;
	@Expose
	private String operatorgroup_id;
	@Expose
	private String onlinesaleagent_id;
	@Expose
	private Context ctx;
	@Expose
	private String operator_phone;


	public Permission(String ip, String access_token, String operator_id, String operator_phone) {
		super();
		this.ip = ip;
		this.access_token = access_token;
		this.operator_id = operator_id;
		this.operator_phone = operator_phone;
	}


	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public String getOperator_id() {
		return operator_id;
	}

	public void setOperator_id(String operator_id) {
		this.operator_id = operator_id;
	}


	public String getOperatorgroup_id() {
		return operatorgroup_id;
	}


	public void setOperatorgroup_id(String operatorgroup_id) {
		this.operatorgroup_id = operatorgroup_id;
	}


	public String getOnlinesaleagent_id() {
		return onlinesaleagent_id;
	}


	public void setOnlinesaleagent_id(String onlinesaleagent_id) {
		this.onlinesaleagent_id = onlinesaleagent_id;
	}

	

	public String getOperator_phone() {
		return operator_phone;
	}


	public void setOperator_phone(String operator_phone) {
		this.operator_phone = operator_phone;
	}


	@Override
	public String toString() {
		return "Permission [ip=" + ip + ", access_token=" + access_token
				+ ", operator_id=" + operator_id + ", operatorgroup_id="
				+ operatorgroup_id + ", onlinesaleagent_id="
				+ onlinesaleagent_id + ", ctx=" + ctx + ", operator_phone="
				+ operator_phone + "]";
	}


	
	
}
