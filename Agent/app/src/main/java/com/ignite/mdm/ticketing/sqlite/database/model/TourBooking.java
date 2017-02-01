package com.ignite.mdm.ticketing.sqlite.database.model;

import java.io.Serializable;


public class TourBooking implements Serializable{
	private String companyName;
	private String fromCity;
	private String toCity;
	private String night;
	private String day;
	private String seater;
	private Double price;
	private String description;
	private String departDate;
	private String hotel; 
	private String food;
	private Integer passengerQty;
	private String orderId;
	private String customerName;
	private String customerPhone;
	private String customerEmail;
	private String bookingDate;
	
	public TourBooking(String companyName, String fromCity, String toCity,
			String night, String day, String seater, Double price,
			String description, String departDate, String hotel, String food,
			Integer passengerQty, String orderId, String customerName, String customerPhone, String customerEmail, String bookingDate) {
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
		this.passengerQty = passengerQty;
		this.orderId = orderId;
		this.customerName = customerName;
		this.customerPhone = customerPhone;
		this.customerEmail = customerEmail;
		this.bookingDate = bookingDate;
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
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
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
	public Integer getPassengerQty() {
		return passengerQty;
	}
	public void setPassengerQty(Integer passengerQty) {
		this.passengerQty = passengerQty;
	}
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	public String getCustomerPhone() {
		return customerPhone;
	}
	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}
	public String getCustomerEmail() {
		return customerEmail;
	}
	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}
	
	public String getBookingDate() {
		return bookingDate;
	}
	public void setBookingDate(String bookingDate) {
		this.bookingDate = bookingDate;
	}
	@Override
	public String toString() {
		return "TourBooking [companyName=" + companyName + ", fromCity="
				+ fromCity + ", toCity=" + toCity + ", night=" + night
				+ ", day=" + day + ", seater=" + seater + ", price=" + price
				+ ", description=" + description + ", departDate=" + departDate
				+ ", hotel=" + hotel + ", food=" + food + ", passengerQty="
				+ passengerQty + ", orderId=" + orderId + ", customerName="
				+ customerName + ", customerPhone=" + customerPhone
				+ ", customerEmail=" + customerEmail + ", bookingDate="
				+ bookingDate + "]";
	}
	
}
