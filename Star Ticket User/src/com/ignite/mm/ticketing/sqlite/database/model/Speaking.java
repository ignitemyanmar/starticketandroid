package com.ignite.mm.ticketing.sqlite.database.model;

public class Speaking {
	private String title;
	private String subtitle;
	private int sound;
	
	public Speaking() {
		super();
	}

	public Speaking(String title, String subtitle,int sound) {
		super();
		this.title = title;
		this.subtitle = subtitle;
		this.sound = sound;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public int getSound() {
		return sound;
	}

	public void setSound(int sound) {
		this.sound = sound;
	}
	
	
}
