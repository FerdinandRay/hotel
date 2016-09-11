package com.hotel.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.hotel.model.HotelUser;

public class HotelUserDao {
	/**
	 * 登录验证
	 * @param con
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public HotelUser login(Connection conn,HotelUser hotelUser) throws Exception{
		HotelUser resultUser=null;
		String sql="select * from tbl_user where user_account=? and user_password=?";
		PreparedStatement pstmt=conn.prepareStatement(sql);
		pstmt.setString(1, hotelUser.getUser_account());
		pstmt.setString(2, hotelUser.getUser_password());
		ResultSet rs=pstmt.executeQuery();
		if(rs.next()){
			resultUser = new HotelUser();
			resultUser.setUser_id(rs.getInt("user_id"));
			resultUser.setUser_name(rs.getString("user_name"));
			resultUser.setUser_account(rs.getString("user_account"));
		}
		return resultUser;
	}
	
	public boolean chgPwd(Connection conn, HotelUser hotelUser) throws SQLException{
		String sql = "update tbl_user set user_password=? where user_id=?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, hotelUser.getUser_password());
		pstmt.setInt(2, hotelUser.getUser_id());
		int rs = pstmt.executeUpdate();
		if (rs > 0)
			return true;
		else
			return false;
	}
	
	public boolean addUser(Connection conn, HotelUser hotelUser) throws SQLException{
		String sql = "insert into tbl_user values(null, ?, ?, ?, ?, ?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, hotelUser.getUser_account());
		pstmt.setString(2, hotelUser.getUser_password());
		pstmt.setString(3, hotelUser.getUser_name());
		pstmt.setString(4, hotelUser.getUser_sex());
		pstmt.setString(5, hotelUser.getUser_phone());
		int rs = pstmt.executeUpdate();
		if (rs > 0)
			return true;
		else
			return false;
	}
	
	public boolean checkOldPass(Connection conn, HotelUser hotelUser) throws SQLException{
		String sql = "select * from tbl_user where user_id=? and user_password=?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, hotelUser.getUser_id());
		pstmt.setString(2, hotelUser.getUser_password());
		ResultSet rs = pstmt.executeQuery();
		
		if (rs.next())
			return true;
		else
			return false;
	}
	
	public boolean checkOldUser(Connection conn, HotelUser hotelUser) throws SQLException{
		String sql = "select * from tbl_user where user_account=?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, hotelUser.getUser_account());

		ResultSet rs = pstmt.executeQuery();
		
		if (rs.next())
			return true;
		else
			return false;
	}
}
