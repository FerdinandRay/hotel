package com.hotel.model;

public class HotelRoom {
	private int room_id;
	private int room_number;
	private int room_type_id;
	private String room_status; 
	
	public HotelRoom() {
	}
	
	public HotelRoom(int room_number, int room_type_id, String room_status) {
		this.room_number = room_number;
		this.room_type_id = room_type_id;
		this.room_status = room_status;
	}
	
	public int getRoom_id() {
		return room_id;
	}
	public void setRoom_id(int room_id) {
		this.room_id = room_id;
	}
	public int getRoom_number() {
		return room_number;
	}
	public void setRoom_number(int room_number) {
		this.room_number = room_number;
	}
	public int getRoom_type_id() {
		return room_type_id;
	}
	public void setRoom_type_id(int room_type_id) {
		this.room_type_id = room_type_id;
	}
	public String getRoom_status() {
		return room_status;
	}
	public void setRoom_status(String room_status) {
		this.room_status = room_status;
	}
	
}
