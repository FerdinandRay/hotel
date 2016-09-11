package com.hotel.util;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JsonUtil {
	public static JSONArray formatRsToJsonArray(ResultSet rs) throws Exception{
		JSONArray array = new JSONArray();
		ResultSetMetaData md = rs.getMetaData();
		int num = md.getColumnCount();
		
		while(rs.next()){
			JSONObject mapOfColValues = new JSONObject();
			for (int i=1; i<=num; i++){
				mapOfColValues.put(md.getColumnName(i), rs.getObject(i));
			}
			array.add(mapOfColValues);
		}
		
		return array;
	}
	
	public static JSONArray formatRsToJsonArrayWithCombobox(ResultSet rs) throws Exception{
		JSONArray array = new JSONArray();
		ResultSetMetaData md = rs.getMetaData();
		int num = md.getColumnCount();
		
		while(rs.next()){
			JSONObject combobox = new JSONObject();
			for(int i=1; i<=num; i++){
				combobox.put(md.getColumnName(i), rs.getObject(i));
			}
			array.add(combobox);
		}
				
		return array;
	}
	
}
