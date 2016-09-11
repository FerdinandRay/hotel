package com.hotel.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.hotel.model.HotelRoom;
import com.hotel.model.PageBean;

public class HotelRoomDao {
	public ResultSet roomList(Connection conn, PageBean pageBean, HotelRoom room) throws Exception {
		String sql = "select tbl_room.room_id, tbl_room.room_number, tbl_roomtype.roomtype_name, tbl_room.room_type_id, tbl_room.room_status "
				+ "from tbl_room, tbl_roomtype "
				+ "where tbl_room.room_type_id = tbl_roomtype.roomtype_id";

		if (pageBean != null) {
			sql = sql + " limit " + pageBean.getStart() + "," + pageBean.getRows();
		}

		PreparedStatement pstmt = conn.prepareStatement(sql);

		return pstmt.executeQuery();
	}
	

	public int roomCount(Connection conn) throws Exception {
		String sql = "SELECT COUNT(*) AS total FROM tbl_room";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		if (rs.next()) {
			return rs.getInt("total");
		} else {
			return 0;
		}
	}

	public int roomAdd(Connection conn, HotelRoom room) throws Exception {
		String sql = "insert into tbl_room values(null, ?, ?, ?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, room.getRoom_number());
		pstmt.setInt(2, room.getRoom_type_id());
		pstmt.setString(3, room.getRoom_status());

		return pstmt.executeUpdate();
	}
	

	public int roomDelete(Connection conn, int delIds) throws Exception {
		String sql = "delete from tbl_room where room_id = " + delIds;
		PreparedStatement pstmt = conn.prepareStatement(sql);

		return pstmt.executeUpdate();
	}

	public int roomModify(Connection conn, HotelRoom room) throws Exception {
		String sql = "update tbl_room set room_number=?, room_type_id=?, room_status=? where room_id=?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, room.getRoom_number());
		pstmt.setInt(2, room.getRoom_type_id());
		pstmt.setString(3, room.getRoom_status());
		pstmt.setInt(4, room.getRoom_id());

		return pstmt.executeUpdate();
	}
	
	public ResultSet getRoomComboboxData(Connection conn) throws Exception{
		String sql = "select room_id, room_number from tbl_room where room_status = '空'";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);

		return pstmt.executeQuery();
	}
	
	public int updateRoomStatusToFill(Connection conn, int checkin_room_id) throws Exception{
		String sql = "update tbl_room set room_status='非空' where room_id = " + checkin_room_id;
		PreparedStatement pstmt = conn.prepareStatement(sql);

		return pstmt.executeUpdate();
	}
	
	public int updateRoomStatusToEmpty(Connection conn, int checkin_room_id) throws Exception{
		String sql = "update tbl_room set room_status='空' where room_id=" + checkin_room_id;
		PreparedStatement pstmt = conn.prepareStatement(sql);
	
		return pstmt.executeUpdate();
	}
}
