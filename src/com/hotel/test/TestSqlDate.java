package com.hotel.test;

import java.sql.Date;
import java.text.ParseException;

import com.hotel.util.DateUtil;


public class TestSqlDate {
	public static void main(String[] args) throws ParseException {
		
		Date checkin_date = new Date(DateUtil.dateToLong("2016-06-01"));
		
		Date checkout_date = new Date(DateUtil.dateToLong("2016-06-02"));

		int day = (int) (checkout_date.getTime() - checkin_date.getTime())/(1000*60*60*24);
		
		System.out.println("天数：" + day);
		
	}
}
