package com.hotel.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.hotel.model.HotelRoom;
import com.hotel.model.HotelRoomType;
import com.hotel.model.PageBean;

public class HotelRoomTypeDao {
	
	public ResultSet roomTypeList(Connection conn, PageBean pageBean, HotelRoomType roomType) throws Exception {
		String sql = "select * from tbl_roomtype";

		if (pageBean != null) {
			sql = sql + " limit " + pageBean.getStart() + "," + pageBean.getRows();
		}

		PreparedStatement pstmt = conn.prepareStatement(sql);

		return pstmt.executeQuery();
	}

	public int roomTypeCount(Connection conn) throws Exception {
		String sql = "SELECT COUNT(*) AS total FROM tbl_roomtype";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		if (rs.next()) {
			return rs.getInt("total");
		} else {
			return 0;
		}
	}

	public int roomTypeAdd(Connection conn, HotelRoomType roomType) throws Exception {
		String sql = "insert into tbl_roomtype values(null, ?, ?, ?, ?, ?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, roomType.getRoomtype_name());
		pstmt.setInt(2, roomType.getRoomtype_cost());
		pstmt.setInt(3, roomType.getRoomtype_deposit());
		pstmt.setInt(4, roomType.getRoomtype_total());
		pstmt.setInt(5, roomType.getRoomtype_surplus());

		return pstmt.executeUpdate();
	}

	public int roomTypeDelete(Connection conn, int delIds) throws Exception {
		String sql = "delete from tbl_roomtype where roomtype_id=" + delIds;
		PreparedStatement pstmt = conn.prepareStatement(sql);

		return pstmt.executeUpdate();
	}

	public int roomTypeModify(Connection conn, HotelRoomType roomType) throws Exception {
		String sql = "update tbl_roomtype set roomtype_name=?, roomtype_cost=?, roomtype_deposit=?, roomtype_total=?, roomtype_surplus=? where roomtype_id=?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, roomType.getRoomtype_name());
		pstmt.setInt(2, roomType.getRoomtype_cost());
		pstmt.setInt(3, roomType.getRoomtype_deposit());
		pstmt.setInt(4, roomType.getRoomtype_total());
		pstmt.setInt(5, roomType.getRoomtype_surplus());
		pstmt.setInt(6, roomType.getRoomtype_id());

		return pstmt.executeUpdate();
	}
	
	
	public int plusSurplusInRoomType(Connection conn, int delTypeIds) throws SQLException{
		String sql = "update tbl_roomtype set roomtype_surplus=roomtype_surplus+1 where roomtype_id=" + delTypeIds;
		PreparedStatement pstmt = conn.prepareStatement(sql);

		return pstmt.executeUpdate();
	}
	
	public int reduceSurplusInRoomType(Connection conn, HotelRoom room) throws SQLException{
		String sql = "update tbl_roomtype set roomtype_surplus=roomtype_surplus-1 where roomtype_id=?";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, room.getRoom_type_id());
		return pstmt.executeUpdate();
	}
	
	public ResultSet getRoomTypeComboboxData(Connection conn) throws Exception{
		String sql = "select roomtype_id, roomtype_name from tbl_roomtype where roomtype_surplus > 0";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);

		return pstmt.executeQuery();
	}
}
