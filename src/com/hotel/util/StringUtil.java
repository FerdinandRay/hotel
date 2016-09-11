package com.hotel.util;

import java.io.UnsupportedEncodingException;

public class StringUtil {

	public static boolean isEmpty(String str) {
		if ("".equals(str) || str == null) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isNotEmpty(String str) {
		if (!"".equals(str) && str != null) {
			return true;
		} else {
			return false;
		}
	}
	
	public static String isoToUTF8(String str) throws UnsupportedEncodingException{
		return new String(str.getBytes("iso-8859-1"), "utf-8");
	}
}
