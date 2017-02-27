package com.ignite.mdm.ticketing.sqlite.database.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BookingUser {

@SerializedName("id")
@Expose
public String id;
@SerializedName("name")
@Expose
public String name;
@SerializedName("email")
@Expose
public String email;
@SerializedName("code_no")
@Expose
public String codeNo;
public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getCodeNo() {
	return codeNo;
}
public void setCodeNo(String codeNo) {
	this.codeNo = codeNo;
}
@Override
public String toString() {
	return "BookingUser [id=" + id + ", name=" + name + ", email=" + email
			+ ", codeNo=" + codeNo + "]";
}



}
