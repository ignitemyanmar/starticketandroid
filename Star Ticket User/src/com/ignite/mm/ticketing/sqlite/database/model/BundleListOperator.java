package com.ignite.mm.ticketing.sqlite.database.model;

import java.util.List;

public class BundleListOperator {
	private List<OperatorSeat> operatorSeat;

	public List<OperatorSeat> getOperatorSeat() {
		return operatorSeat;
	}

	public void setOperatorSeat(List<OperatorSeat> operatorSeat) {
		this.operatorSeat = operatorSeat;
	}

	@Override
	public String toString() {
		return "BundleListOperator [operatorSeat=" + operatorSeat + "]";
	}
	
	
}
