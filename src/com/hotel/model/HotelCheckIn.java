package com.hotel.model;

public class HotelCheckIn {
	private int checkin_id;
	private int checkin_user_id;
	private int checkin_cus_id;
	private int checkin_room_id;
	private String checkin_datetime;
	private int checkin_day;
	
	public HotelCheckIn() {
	}
	
	public HotelCheckIn(int checkin_user_id, int checkin_cus_id, int checkin_room_id, String checkin_datetime, int checkin_day) {
		this.checkin_user_id = checkin_user_id;
		this.checkin_cus_id = checkin_cus_id;
		this.checkin_room_id = checkin_room_id;
		this.checkin_datetime = checkin_datetime;
		this.checkin_day = checkin_day;
	}
	
	public int getCheckin_id() {
		return checkin_id;
	}
	public void setCheckin_id(int checkin_id) {
		this.checkin_id = checkin_id;
	}
	public int getCheckin_user_id() {
		return checkin_user_id;
	}
	public void setCheckin_user_id(int checkin_user_id) {
		this.checkin_user_id = checkin_user_id;
	}
	public int getCheckin_cus_id() {
		return checkin_cus_id;
	}
	public void setCheckin_cus_id(int checkin_cus_id) {
		this.checkin_cus_id = checkin_cus_id;
	}
	public int getCheckin_room_id() {
		return checkin_room_id;
	}
	public void setCheckin_room_id(int checkin_room_id) {
		this.checkin_room_id = checkin_room_id;
	}
	public String getCheckin_datetime() {
		return checkin_datetime;
	}
	public void setCheckin_datetime(String checkin_datetime) {
		this.checkin_datetime = checkin_datetime;
	}
	public int getCheckin_day() {
		return checkin_day;
	}
	public void setCheckin_day(int checkin_day) {
		this.checkin_day = checkin_day;
	}
	
}
