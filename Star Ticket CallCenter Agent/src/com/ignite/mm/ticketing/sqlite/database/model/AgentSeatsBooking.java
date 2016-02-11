
package com.ignite.mm.ticketing.sqlite.database.model;

public class AgentSeatsBooking {

    public String id;
    public String trip_id;
    public String departure_date;
    public String booking_user_id;
    public String total_seat;
    public String phone_no;
    public String status;
    public String created_at;
    public String updated_at;
    public Trip trip;
    public User user;
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
	public String getDeparture_date() {
		return departure_date;
	}
	public void setDeparture_date(String departure_date) {
		this.departure_date = departure_date;
	}
	public String getBooking_user_id() {
		return booking_user_id;
	}
	public void setBooking_user_id(String booking_user_id) {
		this.booking_user_id = booking_user_id;
	}
	public String getTotal_seat() {
		return total_seat;
	}
	public void setTotal_seat(String total_seat) {
		this.total_seat = total_seat;
	}
	public String getPhone_no() {
		return phone_no;
	}
	public void setPhone_no(String phone_no) {
		this.phone_no = phone_no;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public Trip getTrip() {
		return trip;
	}
	public void setTrip(Trip trip) {
		this.trip = trip;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	@Override
	public String toString() {
		return "AgentSeatsBooking [id=" + id + ", trip_id=" + trip_id
				+ ", departure_date=" + departure_date + ", booking_user_id="
				+ booking_user_id + ", total_seat=" + total_seat
				+ ", phone_no=" + phone_no + ", status=" + status
				+ ", created_at=" + created_at + ", updated_at=" + updated_at
				+ ", trip=" + trip + ", user=" + user + "]";
	}
	
    
}
