package com.ignite.mm.ticketing.sqlite.database.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Loyalty {

@Expose
private String id;
@Expose
private String name;
@Expose
private String email;
@Expose
private String password;
@Expose
private String phone;
@Expose
private String code;
@Expose
private String address;
@Expose
private String township;
@SerializedName("pre_amount")
@Expose
private String preAmount;
@SerializedName("used_amount")
@Expose
private String usedAmount;
@Expose
private int points;
@SerializedName("gift_money")
@Expose
private int giftMoney;
@Expose
private String times;
@SerializedName("gift_parcel")
@Expose
private String giftParcel;
@SerializedName("check_status")
@Expose
private int checkStatus;
@Expose
private String message;
@Expose
private String current_points;
@Expose
private String current_gift_money;


public Loyalty(String id, String name, String email, String password,
		String phone, String code, String address, String township,
		String preAmount, String usedAmount, int points, int giftMoney,
		String times, String giftParcel, int checkStatus, String message) {
	super();
	this.id = id;
	this.name = name;
	this.email = email;
	this.password = password;
	this.phone = phone;
	this.code = code;
	this.address = address;
	this.township = township;
	this.preAmount = preAmount;
	this.usedAmount = usedAmount;
	this.points = points;
	this.giftMoney = giftMoney;
	this.times = times;
	this.giftParcel = giftParcel;
	this.checkStatus = checkStatus;
	this.message = message;
}

/**
* 
* @return
* The id
*/
public String getId() {
return id;
}

/**
* 
* @param id
* The id
*/
public void setId(String id) {
this.id = id;
}

/**
* 
* @return
* The name
*/
public String getName() {
return name;
}

/**
* 
* @param name
* The name
*/
public void setName(String name) {
this.name = name;
}

/**
* 
* @return
* The email
*/
public String getEmail() {
return email;
}

/**
* 
* @param email
* The email
*/
public void setEmail(String email) {
this.email = email;
}

/**
* 
* @return
* The password
*/
public String getPassword() {
return password;
}

/**
* 
* @param password
* The password
*/
public void setPassword(String password) {
this.password = password;
}

/**
* 
* @return
* The phone
*/
public String getPhone() {
return phone;
}

/**
* 
* @param phone
* The phone
*/
public void setPhone(String phone) {
this.phone = phone;
}

/**
* 
* @return
* The code
*/
public String getCode() {
return code;
}

/**
* 
* @param code
* The code
*/
public void setCode(String code) {
this.code = code;
}

/**
* 
* @return
* The address
*/
public String getAddress() {
return address;
}

/**
* 
* @param address
* The address
*/
public void setAddress(String address) {
this.address = address;
}

/**
* 
* @return
* The township
*/
public String getTownship() {
return township;
}

/**
* 
* @param township
* The township
*/
public void setTownship(String township) {
this.township = township;
}

/**
* 
* @return
* The preAmount
*/
public String getPreAmount() {
return preAmount;
}

/**
* 
* @param preAmount
* The pre_amount
*/
public void setPreAmount(String preAmount) {
this.preAmount = preAmount;
}

/**
* 
* @return
* The usedAmount
*/
public String getUsedAmount() {
return usedAmount;
}

/**
* 
* @param usedAmount
* The used_amount
*/
public void setUsedAmount(String usedAmount) {
this.usedAmount = usedAmount;
}

/**
* 
* @return
* The points
*/
public int getPoints() {
return points;
}

/**
* 
* @param points
* The points
*/
public void setPoints(int points) {
this.points = points;
}

/**
* 
* @return
* The giftMoney
*/
public int getGiftMoney() {
return giftMoney;
}

/**
* 
* @param giftMoney
* The gift_money
*/
public void setGiftMoney(int giftMoney) {
this.giftMoney = giftMoney;
}

/**
* 
* @return
* The times
*/
public String getTimes() {
return times;
}

/**
* 
* @param times
* The times
*/
public void setTimes(String times) {
this.times = times;
}

/**
* 
* @return
* The giftParcel
*/
public String getGiftParcel() {
return giftParcel;
}

/**
* 
* @param giftParcel
* The gift_parcel
*/
public void setGiftParcel(String giftParcel) {
this.giftParcel = giftParcel;
}

/**
* 
* @return
* The checkStatus
*/
public int getCheckStatus() {
return checkStatus;
}

/**
* 
* @param checkStatus
* The check_status
*/
public void setCheckStatus(int checkStatus) {
this.checkStatus = checkStatus;
}

/**
* 
* @return
* The message
*/
public String getMessage() {
return message;
}

/**
* 
* @param message
* The message
*/
public void setMessage(String message) {
this.message = message;
}



public String getCurrent_points() {
	return current_points;
}

public void setCurrent_points(String current_points) {
	this.current_points = current_points;
}

public String getCurrent_gift_money() {
	return current_gift_money;
}

public void setCurrent_gift_money(String current_gift_money) {
	this.current_gift_money = current_gift_money;
}

@Override
public String toString() {
	return "Loyalty [id=" + id + ", name=" + name + ", email=" + email
			+ ", password=" + password + ", phone=" + phone + ", code=" + code
			+ ", address=" + address + ", township=" + township
			+ ", preAmount=" + preAmount + ", usedAmount=" + usedAmount
			+ ", points=" + points + ", giftMoney=" + giftMoney + ", times="
			+ times + ", giftParcel=" + giftParcel + ", checkStatus="
			+ checkStatus + ", message=" + message + ", current_points="
			+ current_points + ", current_gift_money=" + current_gift_money
			+ "]";
}



}