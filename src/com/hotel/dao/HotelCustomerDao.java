package com.hotel.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.hotel.model.HotelCustomer;
import com.hotel.model.PageBean;
import com.hotel.util.StringUtil;

public class HotelCustomerDao {

	public ResultSet customerList(Connection conn, PageBean pageBean, HotelCustomer customer) throws Exception {
		String sql = "select tbl_customer.cus_id, tbl_customer.cus_name, tbl_customer.cus_sex, tbl_customer.cus_identity, tbl_customer.cus_phone, tbl_customer.cus_points, tbl_level.level_name "
				+ "from tbl_customer, tbl_level "
				+ "where tbl_customer.cus_level_id = tbl_level.level_id";
		
		if (StringUtil.isNotEmpty(customer.getCus_name())){
			sql = sql + " and cus_name like '%" + customer.getCus_name() + "%'";
		}

		if (pageBean != null) {
			sql = sql + " limit " + pageBean.getStart() + "," + pageBean.getRows();
		}

		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		return pstmt.executeQuery();
	}
	
	public int customerCount(Connection conn, HotelCustomer customer) throws Exception {
		String sql = "SELECT COUNT(*) AS total FROM tbl_customer";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		if (rs.next()) {
			return rs.getInt("total");
		} else {
			return 0;
		}
	}

	public int customerAdd(Connection conn, HotelCustomer customer) throws Exception {
		String sql = "insert into tbl_customer values(null, ?, ?, ?, ?, ?, ?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, customer.getCus_name());
		pstmt.setString(2, customer.getCus_sex());
		pstmt.setString(3, customer.getCus_identity());
		pstmt.setString(4, customer.getCus_phone());
		pstmt.setInt(5, customer.getCus_points());
		pstmt.setInt(6, customer.getCus_level_id());

		return pstmt.executeUpdate();
	}

	public int customerDelete(Connection conn, String delIds) throws Exception {
		String sql = "delete from tbl_customer where cus_id in (" + delIds + ")";
		PreparedStatement pstmt = conn.prepareStatement(sql);

		return pstmt.executeUpdate();
	}

	public int customerModify(Connection conn, HotelCustomer customer) throws Exception {
		String sql = "update tbl_customer set cus_name=?, cus_sex=?, cus_identity=?, cus_phone=?, cus_points=?, cus_level_id=? where cus_id=?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, customer.getCus_name());
		pstmt.setString(2, customer.getCus_sex());
		pstmt.setString(3, customer.getCus_identity());
		pstmt.setString(4, customer.getCus_phone());
		pstmt.setInt(5, customer.getCus_points());
		pstmt.setInt(6, customer.getCus_level_id());
		pstmt.setInt(7, customer.getCus_id());

		return pstmt.executeUpdate();
	}
	
	public ResultSet getCustomerComboboxData(Connection conn) throws Exception{
		String sql = "select cus_id, cus_name from tbl_customer";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);

		return pstmt.executeQuery();
	}
	
	public int updatePoints(Connection conn, int cus_id) throws Exception{
		String sql = "update tbl_customer set cus_points=cus_points+100 where cus_id=" + cus_id;
		PreparedStatement pstmt = conn.prepareStatement(sql);

		return pstmt.executeUpdate();
	}
	
	public int updateLevel(Connection conn, int cus_id) throws Exception{
		String sql = "update tbl_customer set cus_level_id=? where cus_id=" + cus_id;
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, getLevel(getPoints(conn, cus_id), conn));

		return pstmt.executeUpdate();
	}
	
	private int getLevel(int points, Connection conn) throws Exception{
		HotelLevelDao levelDao = new HotelLevelDao();
		ResultSet rs = levelDao.getLevelAll(conn);
		int level_id = 0;
		while(rs.next()){
			if(points > rs.getInt("level_points")){
				level_id = rs.getInt("level_id");
				System.out.println("level_id：" + level_id);
			}
		}
		return level_id;
	}
	
	private int getPoints(Connection conn, int cus_id) throws Exception{
		String sql = "select cus_points from tbl_customer where cus_id=" + cus_id;
		PreparedStatement pstmt = conn.prepareStatement(sql);
		int points = 0;
		
		ResultSet rs = pstmt.executeQuery();
		if(rs.next()){
			points = rs.getInt("cus_points");
			System.out.println("获得分数："+points);
		}
		
		return points;
	}
}
