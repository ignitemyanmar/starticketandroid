package com.ignite.mm.ticketing.sqlite.database.model;

public class SelectSeatBooking {
	
	private String select_time;
	private String select_seat;
	public SelectSeatBooking(String select_time, String select_seat) {
		super();
		this.select_time = select_time;
		this.select_seat = select_seat;
	}
	public String getSelect_time() {
		return select_time;
	}
	public void setSelect_time(String select_time) {
		this.select_time = select_time;
	}
	public String getSelect_seat() {
		return select_seat;
	}
	public void setSelect_seat(String select_seat) {
		this.select_seat = select_seat;
	}
	@Override
	public String toString() {
		return "SelectSeatBooking [select_time=" + select_time
				+ ", select_seat=" + select_seat + "]";
	}
	
	
}
