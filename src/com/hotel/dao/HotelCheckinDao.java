package com.hotel.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.hotel.model.HotelCheckIn;
import com.hotel.model.PageBean;
import com.hotel.util.StringUtil;

public class HotelCheckinDao {
	
	public ResultSet checkinList(Connection conn, PageBean pageBean, HotelCheckIn checkin) throws Exception {
		String sql = "select tbl_checkin.checkin_id, tbl_user.user_name, tbl_customer.cus_name, tbl_checkin.checkin_cus_id, tbl_room.room_number, tbl_roomtype.roomtype_cost, tbl_checkin.checkin_room_id ,tbl_checkin.checkin_datetime, tbl_checkin.checkin_day "
				+ "from tbl_checkin, tbl_user, tbl_customer, tbl_room, tbl_roomtype "
				+ "where tbl_checkin.checkin_user_id = tbl_user.user_id "
				+ "and tbl_checkin.checkin_cus_id = tbl_customer.cus_id "
				+ "and tbl_checkin.checkin_room_id = tbl_room.room_id "
				+ "and tbl_room.room_type_id = tbl_roomtype.roomtype_id ";
		
		if(StringUtil.isNotEmpty(checkin.getCheckin_datetime())){
			sql = sql + "and checkin_datetime like '%" + checkin.getCheckin_datetime() + "%'";
		}
		
		if (pageBean != null) {
			sql = sql + " limit " + pageBean.getStart() + "," + pageBean.getRows();
		}

		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		return pstmt.executeQuery();
	}
	
	public int checkinCount(Connection conn) throws Exception {
		String sql = "SELECT COUNT(*) AS total FROM tbl_checkin";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		if (rs.next()) {
			return rs.getInt("total");
		} else {
			return 0;
		}
	}

	public int checkinAdd(Connection conn, HotelCheckIn checkin) throws Exception {
		String sql = "insert into tbl_checkin values(null, ?, ?, ?, ?, ?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, checkin.getCheckin_user_id());
		pstmt.setInt(2, checkin.getCheckin_cus_id());
		pstmt.setInt(3, checkin.getCheckin_room_id());
		pstmt.setString(4, checkin.getCheckin_datetime());
		pstmt.setInt(5, checkin.getCheckin_day());
		
		return pstmt.executeUpdate();
	}

	public int checkinDelete(Connection conn, int delIds) throws Exception {
		String sql = "delete from tbl_checkin where checkin_id = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, delIds);

		return pstmt.executeUpdate();
	}
		
	public int checkinModify(Connection conn, HotelCheckIn checkin) throws Exception {
		String sql = "update tbl_checkin set checkin_user_id=?, checkin_cus_id=?, checkin_room_id=?, checkin_datetime=?, checkin_day=? where checkin_id=?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, checkin.getCheckin_user_id());
		pstmt.setInt(2, checkin.getCheckin_cus_id());
		pstmt.setInt(3, checkin.getCheckin_room_id());
		pstmt.setString(4, checkin.getCheckin_datetime());
		pstmt.setInt(5, checkin.getCheckin_day());
		pstmt.setInt(6, checkin.getCheckin_id());

		return pstmt.executeUpdate();
	}
	
	

	
}
