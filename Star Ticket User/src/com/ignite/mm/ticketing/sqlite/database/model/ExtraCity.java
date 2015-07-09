package com.ignite.mm.ticketing.sqlite.database.model;

public class ExtraCity {
	private String id;
	private String trip_id;
	private String city_id;
	private String local_price;
	private String foreign_price;
	private String  city_name;
	private String created_at;
	private String updated_at;
	

	public ExtraCity(String id, String trip_id, String city_id,
			String local_price, String foreign_price, String city_name,
			String created_at, String updated_at) {
		super();
		this.id = id;
		this.trip_id = trip_id;
		this.city_id = city_id;
		this.local_price = local_price;
		this.foreign_price = foreign_price;
		this.city_name = city_name;
		this.created_at = created_at;
		this.updated_at = updated_at;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTrip_id() {
		return trip_id;
	}
	public void setTrip_id(String trip_id) {
		this.trip_id = trip_id;
	}
	public String getCity_id() {
		return city_id;
	}
	public void setCity_id(String city_id) {
		this.city_id = city_id;
	}
	public String getLocal_price() {
		return local_price;
	}
	public void setLocal_price(String local_price) {
		this.local_price = local_price;
	}
	public String getForeign_price() {
		return foreign_price;
	}
	public void setForeign_price(String foreign_price) {
		this.foreign_price = foreign_price;
	}
	public String getCity_name() {
		return city_name;
	}
	public void setCity_name(String city_name) {
		this.city_name = city_name;
	}
	public String getCreated_at() {
		return created_at;
	}
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	public String getUpdated_at() {
		return updated_at;
	}
	public void setUpdated_at(String updated_at) {
		this.updated_at = updated_at;
	}
	@Override
	public String toString() {
		return "ExtraCity [id=" + id + ", trip_id=" + trip_id + ", city_id="
				+ city_id + ", local_price=" + local_price + ", foreign_price="
				+ foreign_price + ", city_name=" + city_name + ", created_at="
				+ created_at + ", updated_at=" + updated_at + "]";
	}
	
	
	
	
}
