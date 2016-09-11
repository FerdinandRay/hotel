package com.hotel.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.hotel.model.HotelLevel;
import com.hotel.model.PageBean;

public class HotelLevelDao {
	public ResultSet levelList(Connection conn, PageBean pageBean, HotelLevel level) throws Exception {
		String sql = "select * from tbl_level order by level_points";

		if (pageBean != null) {
			sql = sql + " limit " + pageBean.getStart() + "," + pageBean.getRows();
		}

		PreparedStatement pstmt = conn.prepareStatement(sql);

		return pstmt.executeQuery();
	}
	
	public ResultSet getLevelAll(Connection conn) throws Exception{
		String sql = "select * from tbl_level order by level_points";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		return pstmt.executeQuery();
	}

	public int levelCount(Connection conn) throws Exception {
		String sql = "SELECT COUNT(*) AS total FROM tbl_level";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		if (rs.next()) {
			return rs.getInt("total");
		} else {
			return 0;
		}
	}

	public int levelAdd(Connection conn, HotelLevel level) throws Exception {
		String sql = "insert into tbl_level values(null, ?, ?, ?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, level.getLevel_name());
		pstmt.setInt(2, level.getLevel_points());
		pstmt.setInt(3, level.getLevel_rate());

		return pstmt.executeUpdate();
	}

	public int levelDelete(Connection conn, String delIds) throws Exception {
		String sql = "delete from tbl_level where level_id in (" + delIds + ")";
		PreparedStatement pstmt = conn.prepareStatement(sql);

		return pstmt.executeUpdate();
	}

	public int levelModify(Connection conn, HotelLevel level) throws Exception {
		String sql = "update tbl_level set level_name=?, level_points=?, level_rate=? where level_id=?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, level.getLevel_name());
		pstmt.setInt(2, level.getLevel_points());
		pstmt.setInt(3, level.getLevel_rate());
		pstmt.setInt(4, level.getLevel_id());

		return pstmt.executeUpdate();
	}
	
	public ResultSet getLevelComboboxData(Connection conn) throws Exception{
		String sql = "select level_id, level_name from tbl_level";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);

		return pstmt.executeQuery();
	}
	
	public int getRate(Connection conn, int cus_id) throws Exception {
		String sql = "select * from tbl_level "
				+ "where level_id in (select cus_level_id from tbl_customer where cus_id="
				+ cus_id + ")";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		int count_rate = 100;
		ResultSet rs = pstmt.executeQuery();
		if (rs.next()){
			count_rate = rs.getInt("level_rate");
		}
		return count_rate;
	}
	
}
