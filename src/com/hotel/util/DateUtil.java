package com.hotel.util;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateUtil {
	public static String longtoDate(long time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(new Date(time));
	}
	
	public static long dateToLong(String date) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return ((Date) sdf.parse(date)).getTime();
	}
	
	public static int getDay(String checkin_datetime, String checkout_datetime) throws ParseException{
		Date checkin_date = new Date(DateUtil.dateToLong(checkin_datetime));
		Date checkout_date = new Date(DateUtil.dateToLong(checkout_datetime));

		return (int) (checkout_date.getTime() - checkin_date.getTime())/(1000*60*60*24);
	}


	public static void main(String[] args) throws ParseException {
		long time = System.currentTimeMillis();
		System.out.println(longtoDate(Long.parseLong("1432224000000")));
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date trans = sdf.parse("2015-05-22");
		
		System.out.println(trans.getTime());
	}
}
