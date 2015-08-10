package com.ignite.mm.ticketing.sqlite.database.model;

public class ForgotPassword {
	private Integer status;
	private String description;
	private String message;
	public ForgotPassword(Integer status, String description, String message) {
		super();
		this.status = status;
		this.description = description;
		this.message = message;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	@Override
	public String toString() {
		return "ForgotPassword [status=" + status + ", description="
				+ description + ", message=" + message + "]";
	}
	
	
}
