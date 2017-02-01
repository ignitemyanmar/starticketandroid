package com.ignite.mdm.ticketing.sqlite.database.model;

public class NotiCounts {
	private Integer bookingnoti_count;
	private Integer deliverynoti_count;
	private Integer agentordernoti_count;
	public NotiCounts(Integer bookingnoti_count, Integer deliverynoti_count,
			Integer agentordernoti_count) {
		super();
		this.bookingnoti_count = bookingnoti_count;
		this.deliverynoti_count = deliverynoti_count;
		this.agentordernoti_count = agentordernoti_count;
	}
	public Integer getBookingnoti_count() {
		return bookingnoti_count;
	}
	public void setBookingnoti_count(Integer bookingnoti_count) {
		this.bookingnoti_count = bookingnoti_count;
	}
	public Integer getDeliverynoti_count() {
		return deliverynoti_count;
	}
	public void setDeliverynoti_count(Integer deliverynoti_count) {
		this.deliverynoti_count = deliverynoti_count;
	}
	public Integer getAgentordernoti_count() {
		return agentordernoti_count;
	}
	public void setAgentordernoti_count(Integer agentordernoti_count) {
		this.agentordernoti_count = agentordernoti_count;
	}
	@Override
	public String toString() {
		return "NotiCounts [bookingnoti_count=" + bookingnoti_count
				+ ", deliverynoti_count=" + deliverynoti_count
				+ ", agentordernoti_count=" + agentordernoti_count + "]";
	}

	
}
