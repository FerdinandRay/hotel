package com.hotel.util;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ResponseUtil {

	public static void write(HttpServletResponse response,JSONObject jsonObject)throws Exception{
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out=response.getWriter();
		out.println(jsonObject.toString());
		out.flush();
		out.close();
	}
	
	public static void write(HttpServletResponse response,JSONArray jsonArray)throws Exception{
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out=response.getWriter();
		out.println(jsonArray.toString());
		out.flush();
		out.close();
	}
}
