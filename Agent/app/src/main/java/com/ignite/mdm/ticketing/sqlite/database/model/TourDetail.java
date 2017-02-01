package com.ignite.mdm.ticketing.sqlite.database.model;

public class TourDetail {
	private String title;
	private String description;
	private String photo;
	public TourDetail(String title, String description, String photo) {
		super();
		this.title = title;
		this.description = description;
		this.photo = photo;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	@Override
	public String toString() {
		return "TourDetail [title=" + title + ", description=" + description
				+ ", photo=" + photo + "]";
	}
	
	
}
