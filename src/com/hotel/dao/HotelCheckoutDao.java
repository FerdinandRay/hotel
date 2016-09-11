package com.hotel.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.hotel.model.HotelCheckOut;
import com.hotel.model.PageBean;
import com.hotel.util.StringUtil;

public class HotelCheckoutDao {
	public ResultSet checkoutList(Connection conn, PageBean pageBean, HotelCheckOut checkout) throws Exception {
		String sql = "select tbl_checkout.checkout_id, tbl_user.user_name, tbl_customer.cus_name, tbl_room.room_number, tbl_checkout.checkout_datetime, tbl_checkout.checkout_pay "
				+ "from tbl_checkout, tbl_user, tbl_customer, tbl_room "
				+ "where tbl_checkout.checkout_user_id = tbl_user.user_id "
				+ "and tbl_checkout.checkout_cus_id = tbl_customer.cus_id "
				+ "and tbl_checkout.checkout_room_id = tbl_room.room_id";
		
		if(StringUtil.isNotEmpty(checkout.getCheckout_datetime())){
			sql = sql + " and checkout_datetime like '%" + checkout.getCheckout_datetime() + "%'";
		}

		if (pageBean != null) {
			sql = sql + " limit " + pageBean.getStart() + "," + pageBean.getRows();
		}

		PreparedStatement pstmt = conn.prepareStatement(sql);

		return pstmt.executeQuery();
	}

	public int checkoutCount(Connection conn) throws Exception {
		String sql = "SELECT COUNT(*) AS total FROM tbl_checkout";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		if (rs.next()) {
			return rs.getInt("total");
		} else {
			return 0;
		}
	}

	public int checkoutDelete(Connection conn, String delIds) throws Exception {
		String sql = "delete from tbl_checkout where checkout_id in (" + delIds + ")";
		PreparedStatement pstmt = conn.prepareStatement(sql);

		return pstmt.executeUpdate();
	}
	
	public int checkoutAdd(Connection conn, HotelCheckOut checkout) throws Exception{
		String sql = "insert into tbl_checkout values(null, ?, ?, ?, ?, ?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, checkout.getCheckout_user_id());
		pstmt.setInt(2, checkout.getCheckout_cus_id());
		pstmt.setInt(3, checkout.getCheckout_room_id());
		pstmt.setString(4, checkout.getCheckout_datetime());
		pstmt.setDouble(5, checkout.getCheckout_pay());
		
		return pstmt.executeUpdate();
	}
}
