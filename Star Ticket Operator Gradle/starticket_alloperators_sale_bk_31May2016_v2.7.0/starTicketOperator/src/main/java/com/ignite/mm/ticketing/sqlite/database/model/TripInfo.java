package com.ignite.mm.ticketing.sqlite.database.model;

import java.util.List;

public class TripInfo {

	private String AgentID;
	private String OperatorID;
	private String FromCity;
	private String ToCity;
	private String From;
	private String To;
	private String Classes;
	private String Time;
	private String Date;
	public TripInfo(String agentID, String operatorID, String fromCity,
			String toCity, String from, String to, String classes, String time,
			String date) {
		super();
		AgentID = agentID;
		OperatorID = operatorID;
		FromCity = fromCity;
		ToCity = toCity;
		From = from;
		To = to;
		Classes = classes;
		Time = time;
		Date = date;
	}
	public String getAgentID() {
		return AgentID;
	}
	public void setAgentID(String agentID) {
		AgentID = agentID;
	}
	public String getOperatorID() {
		return OperatorID;
	}
	public void setOperatorID(String operatorID) {
		OperatorID = operatorID;
	}
	public String getFromCity() {
		return FromCity;
	}
	public void setFromCity(String fromCity) {
		FromCity = fromCity;
	}
	public String getToCity() {
		return ToCity;
	}
	public void setToCity(String toCity) {
		ToCity = toCity;
	}
	public String getFrom() {
		return From;
	}
	public void setFrom(String from) {
		From = from;
	}
	public String getTo() {
		return To;
	}
	public void setTo(String to) {
		To = to;
	}
	public String getClasses() {
		return Classes;
	}
	public void setClasses(String classes) {
		Classes = classes;
	}
	public String getTime() {
		return Time;
	}
	public void setTime(String time) {
		Time = time;
	}
	public String getDate() {
		return Date;
	}
	public void setDate(String date) {
		Date = date;
	}
	@Override
	public String toString() {
		return "TripInfo [AgentID=" + AgentID + ", OperatorID=" + OperatorID
				+ ", FromCity=" + FromCity + ", ToCity=" + ToCity + ", From="
				+ From + ", To=" + To + ", Classes=" + Classes + ", Time="
				+ Time + ", Date=" + Date + "]";
	}
	
	
}
