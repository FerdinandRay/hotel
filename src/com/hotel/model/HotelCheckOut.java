package com.hotel.model;

public class HotelCheckOut {
	private int checkout_id;
	private int checkout_user_id;
	private int checkout_cus_id;
	private int checkout_room_id;
	private String checkout_datetime;
	private double checkout_pay;
	
	public HotelCheckOut() {
	}
	
	public HotelCheckOut(int checkout_user_id, int checkout_cus_id, int checkout_room_id, String checkout_datetime) {
		this.checkout_user_id = checkout_user_id;
		this.checkout_cus_id = checkout_cus_id;
		this.checkout_room_id = checkout_room_id;
		this.checkout_datetime = checkout_datetime;
	}
	
	public double getCheckout_pay() {
		return checkout_pay;
	}

	public void setCheckout_pay(double checkout_pay) {
		this.checkout_pay = checkout_pay;
	}

	public int getCheckout_id() {
		return checkout_id;
	}
	public void setCheckout_id(int checkout_id) {
		this.checkout_id = checkout_id;
	}
	public int getCheckout_user_id() {
		return checkout_user_id;
	}
	public void setCheckout_user_id(int checkout_user_id) {
		this.checkout_user_id = checkout_user_id;
	}
	public int getCheckout_cus_id() {
		return checkout_cus_id;
	}
	public void setCheckout_cus_id(int checkout_cus_id) {
		this.checkout_cus_id = checkout_cus_id;
	}
	public int getCheckout_room_id() {
		return checkout_room_id;
	}
	public void setCheckout_room_id(int checkout_room_id) {
		this.checkout_room_id = checkout_room_id;
	}
	public String getCheckout_datetime() {
		return checkout_datetime;
	}
	public void setCheckout_datetime(String checkout_datetime) {
		this.checkout_datetime = checkout_datetime;
	}
}
