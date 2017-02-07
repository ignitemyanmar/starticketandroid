package com.ignite.mm.ticketing.sqlite.database.model;

import java.util.ArrayList;
import java.util.List;

public class AllTimeBundleListObject {

	List<Time> allTimes = new ArrayList<Time>();

	public List<Time> getAllTimes() {
		return allTimes;
	}

	public void setAllTimes(List<Time> allTimes) {
		this.allTimes = allTimes;
	}
}
