package com.hotel.model;

public class HotelRoomType {
	private int roomtype_id;
	private String roomtype_name;
	private int roomtype_cost; // 房间花费
	private int roomtype_deposit; // 房间押金
	private int roomtype_total; // 房间总量
	private int roomtype_surplus;// 房间剩余量
	
	public HotelRoomType() {
	}
	
	public HotelRoomType(String roomtype_name, int roomtype_cost, int roomtype_deposit, int roomtype_total, int roomtype_surplus) {
		this.roomtype_name = roomtype_name;
		this.roomtype_cost = roomtype_cost;
		this.roomtype_deposit = roomtype_deposit;
		this.roomtype_total = roomtype_total;
		this.roomtype_surplus = roomtype_surplus;
	}
	public int getRoomtype_id() {
		return roomtype_id;
	}
	public void setRoomtype_id(int roomtype_id) {
		this.roomtype_id = roomtype_id;
	}
	public String getRoomtype_name() {
		return roomtype_name;
	}
	public void setRoomtype_name(String roomtype_name) {
		this.roomtype_name = roomtype_name;
	}
	public int getRoomtype_cost() {
		return roomtype_cost;
	}
	public void setRoomtype_cost(int roomtype_cost) {
		this.roomtype_cost = roomtype_cost;
	}
	public int getRoomtype_deposit() {
		return roomtype_deposit;
	}
	public void setRoomtype_deposit(int roomtype_deposit) {
		this.roomtype_deposit = roomtype_deposit;
	}
	public int getRoomtype_total() {
		return roomtype_total;
	}
	public void setRoomtype_total(int roomtype_total) {
		this.roomtype_total = roomtype_total;
	}
	public int getRoomtype_surplus() {
		return roomtype_surplus;
	}
	public void setRoomtype_surplus(int roomtype_surplus) {
		this.roomtype_surplus = roomtype_surplus;
	}
	
}
