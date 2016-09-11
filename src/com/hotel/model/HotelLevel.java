package com.hotel.model;

public class HotelLevel {
	private int level_id;
	private String level_name;
	private int level_points;
	private int level_rate;
	
	public HotelLevel() {
	}
	
	public HotelLevel(String level_name, int level_points, int level_rate) {
		this.level_name = level_name;
		this.level_points = level_points;
		this.level_rate = level_rate;
	}
	
	
	public int getLevel_points() {
		return level_points;
	}

	public void setLevel_points(int level_points) {
		this.level_points = level_points;
	}

	public int getLevel_id() {
		return level_id;
	}
	public void setLevel_id(int level_id) {
		this.level_id = level_id;
	}
	public String getLevel_name() {
		return level_name;
	}
	public void setLevel_name(String level_name) {
		this.level_name = level_name;
	}

	public int getLevel_rate() {
		return level_rate;
	}

	public void setLevel_rate(int level_rate) {
		this.level_rate = level_rate;
	}
	
}
