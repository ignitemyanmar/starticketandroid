package com.ignite.mm.ticketing.sqlite.database.model;

public class ThingsToDo {
	private int photo;
	private String todo_title;
	private String todo_body;
	public ThingsToDo(int photo, String todo_title, String todo_body) {
		super();
		this.photo = photo;
		this.todo_title = todo_title;
		this.todo_body = todo_body;
	}
	public int getPhoto() {
		return photo;
	}
	public void setPhoto(int photo) {
		this.photo = photo;
	}
	public String getTodo_title() {
		return todo_title;
	}
	public void setTodo_title(String todo_title) {
		this.todo_title = todo_title;
	}
	public String getTodo_body() {
		return todo_body;
	}
	public void setTodo_body(String todo_body) {
		this.todo_body = todo_body;
	}
	@Override
	public String toString() {
		return "ThingsToDo [photo=" + photo + ", todo_title=" + todo_title
				+ ", todo_body=" + todo_body + "]";
	}
	
	
}
