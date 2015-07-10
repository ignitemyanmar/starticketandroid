package com.ignite.mm.ticketing.sqlite.database.model;

import java.util.ArrayList;
import java.util.List;


public class BundleListObjSeats {
	List<SelectSeat> seatsList = new ArrayList<SelectSeat>();

	public List<SelectSeat> getSeatsList() {
		return seatsList;
	}

	public void setSeatsList(List<SelectSeat> seatsList) {
		this.seatsList = seatsList;
	}

	
}
