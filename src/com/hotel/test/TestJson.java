package com.hotel.test;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class TestJson {
	public static void main(String[] args) {
		
		JSONArray jsonArray = new JSONArray();
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", 1);
		jsonObject.put("name", "张三");
		jsonArray.add(jsonObject);
		
		jsonObject = new JSONObject();
		jsonObject.put("id", 2);
		jsonObject.put("name", "李四");
		jsonArray.add(jsonObject);
		
		System.out.println(jsonArray);
		
		
		
	}
}
