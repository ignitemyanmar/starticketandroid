package com.ignite.mm.ticketing.sqlite.database.model;

public class SelectSeat {
	private String busoccurance_id;
	private String seat_no;
	
	public SelectSeat(String busoccurance_id, String seat_no) {
		super();
		this.busoccurance_id = busoccurance_id;
		this.seat_no = seat_no;
	}
	public String getBusoccurance_id() {
		return busoccurance_id;
	}
	public void setBusoccurance_id(String busoccurance_id) {
		this.busoccurance_id = busoccurance_id;
	}
	public String getSeat_no() {
		return seat_no;
	}
	public void setSeat_no(String seat_no) {
		this.seat_no = seat_no;
	}
	@Override
	public String toString() {
		return "{\"busoccurance_id\":\"" + busoccurance_id + "\", \"seat_no\":\""
				+ seat_no + "\"}";
	}
	
	
	
}
