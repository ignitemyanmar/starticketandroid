package com.ignite.mm.ticketing.sqlite.database.model;

public class MenuIcon {
	private int icon;
	private String title;
	public MenuIcon(int icon, String title) {
		super();
		this.icon = icon;
		this.title = title;
	}
	public int getIcon() {
		return icon;
	}
	public void setIcon(int icon) {
		this.icon = icon;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@Override
	public String toString() {
		return "MenuIcon [icon=" + icon + ", title=" + title + "]";
	}
	
	
}
