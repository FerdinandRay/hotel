package com.hotel.model;

public class HotelCustomer {
	private int cus_id;
	private String cus_name;
	private String cus_sex;
	private String cus_identity;
	private String cus_phone;
	private int cus_points;
	private int cus_level_id;
	
	public HotelCustomer() {
	}
	
	public HotelCustomer(String cus_name, String cus_sex, String cus_identity, String cus_phone, int cus_points, int cus_level_id){
		this.cus_name = cus_name;
		this.cus_sex = cus_sex;
		this.cus_identity = cus_identity;
		this.cus_phone = cus_phone;
		this.cus_points = cus_points;
		this.cus_level_id = cus_level_id;
	}
	
	public int getCus_id() {
		return cus_id;
	}
	public void setCus_id(int cus_id) {
		this.cus_id = cus_id;
	}
	public String getCus_name() {
		return cus_name;
	}
	public void setCus_name(String cus_name) {
		this.cus_name = cus_name;
	}
	public String getCus_sex() {
		return cus_sex;
	}
	public void setCus_sex(String cus_sex) {
		this.cus_sex = cus_sex;
	}
	public String getCus_identity() {
		return cus_identity;
	}
	public void setCus_identity(String cus_identity) {
		this.cus_identity = cus_identity;
	}
	public String getCus_phone() {
		return cus_phone;
	}
	public void setCus_phone(String cus_phone) {
		this.cus_phone = cus_phone;
	}
	public int getCus_points() {
		return cus_points;
	}
	public void setCus_points(int cus_points) {
		this.cus_points = cus_points;
	}
	public int getCus_level_id() {
		return cus_level_id;
	}
	public void setCus_level_id(int cus_level_id) {
		this.cus_level_id = cus_level_id;
	}
}
