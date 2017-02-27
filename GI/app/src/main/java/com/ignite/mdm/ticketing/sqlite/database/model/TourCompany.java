package com.ignite.mdm.ticketing.sqlite.database.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class TourCompany implements Serializable{
	private String companyName;
	private String fromCity;
	private String toCity;
	private String night;
	private String day;
	private String seater;
	private String price;
	private String description;
	private String departDate;
	private String hotel; 
	private String food;
	
	public TourCompany(String companyName, String fromCity, String toCity,
			String night, String day, String seater, String price,
			String description, String departDate, String hotel, String food) {
		super();
		this.companyName = companyName;
		this.fromCity = fromCity;
		this.toCity = toCity;
		this.night = night;
		this.day = day;
		this.seater = seater;
		this.price = price;
		this.description = description;
		this.departDate = departDate;
		this.hotel = hotel;
		this.food = food;
	}


	public String getCompanyName() {
		return companyName;
	}
	
	
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getFromCity() {
		return fromCity;
	}
	public void setFromCity(String fromCity) {
		this.fromCity = fromCity;
	}
	public String getToCity() {
		return toCity;
	}
	public void setToCity(String toCity) {
		this.toCity = toCity;
	}
	public String getNight() {
		return night;
	}
	public void setNight(String night) {
		this.night = night;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public String getSeater() {
		return seater;
	}
	public void setSeater(String seater) {
		this.seater = seater;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDepartDate() {
		return departDate;
	}
	public void setDepartDate(String departDate) {
		this.departDate = departDate;
	}
	
	
	public String getHotel() {
		return hotel;
	}


	public void setHotel(String hotel) {
		this.hotel = hotel;
	}


	public String getFood() {
		return food;
	}


	public void setFood(String food) {
		this.food = food;
	}


	@Override
	public String toString() {
		return "TourCompany [companyName=" + companyName + ", fromCity="
				+ fromCity + ", toCity=" + toCity + ", night=" + night
				+ ", day=" + day + ", seater=" + seater + ", price=" + price
				+ ", description=" + description + ", departDate=" + departDate
				+ ", hotel=" + hotel + ", food=" + food + "]";
	}


	
}
