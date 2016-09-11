package com.hotel.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.hotel.dao.HotelCustomerDao;
import com.hotel.dao.HotelLevelDao;
import com.hotel.model.HotelCustomer;
import com.hotel.model.HotelRoomType;
import com.hotel.model.PageBean;
import com.hotel.util.DbUtil;
import com.hotel.util.JsonUtil;
import com.hotel.util.ResponseUtil;

public class CustomerServlet extends HttpServlet {

	private int method;
	private DbUtil dbUtil = new DbUtil();
	private HotelCustomerDao customerDao = new HotelCustomerDao();
	private HotelLevelDao levelDao = new HotelLevelDao();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.method = Integer.parseInt(req.getParameter("method"));
		if (method == 1) {
			// 客户显示
			this.showCustomer(req, resp);
		}
		if (method == 2) {
			// 客户添加
			this.customerAdd(req, resp);
		}
		if (method == 3) {
			// 客户修改
			this.customerModify(req, resp);
		}
		if (method == 4) {
			// 客户删除
			this.customerDel(req, resp);
		}
		if (method == 5){
			// 客户等级类型combox
			this.initComboxLevel(req, resp);
		}
	}
	
	private void showCustomer(HttpServletRequest req, HttpServletResponse resp){
		String page = req.getParameter("page");
		String rows = req.getParameter("rows");
		String cus_name = req.getParameter("cus_name");
		
		if(cus_name == null){
			cus_name = "";
		}
		
		PageBean pageBean = new PageBean(Integer.parseInt(page), Integer.parseInt(rows));
		Connection conn = null;
		JSONArray jsonArray = null;
		
		HotelCustomer customer = new HotelCustomer();
		customer.setCus_name(cus_name);
		
		try {
			conn = dbUtil.getCon();
			JSONObject result = new JSONObject();
			jsonArray = JsonUtil.formatRsToJsonArray(customerDao.customerList(conn, pageBean, customer));
			int total = customerDao.customerCount(conn, customer);
			result.put("rows", jsonArray);
			result.put("total", total);
			ResponseUtil.write(resp, result);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void customerAdd(HttpServletRequest req, HttpServletResponse resp){
		String cus_name = req.getParameter("cus_name");
		String cus_sex = req.getParameter("cus_sex");
		String cus_identity = req.getParameter("cus_identity");
		String cus_phone = req.getParameter("cus_phone");
		int cus_points = Integer.parseInt(req.getParameter("cus_points"));
		int cus_level_id = Integer.parseInt(req.getParameter("cus_level_id"));
		
		HotelCustomer customer = new HotelCustomer(cus_name, cus_sex, cus_identity, cus_phone, cus_points, cus_level_id);
				
		Connection conn = null;
		
		try {
			conn = dbUtil.getCon();
			JSONObject result = new JSONObject();
			int saveNums = customerDao.customerAdd(conn, customer);
			if (saveNums > 0){
				result.put("success", "true");
			}else{
				result.put("success", "true");
				result.put("errorMsg", "添加失败");
			}
			ResponseUtil.write(resp, result);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				dbUtil.closeCon(conn);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void customerModify(HttpServletRequest req, HttpServletResponse resp){
		String cus_name = req.getParameter("cus_name");
		String cus_sex = req.getParameter("cus_sex");
		String cus_identity = req.getParameter("cus_identity");
		String cus_phone = req.getParameter("cus_phone");
		int cus_points = Integer.parseInt(req.getParameter("cus_points"));
		int cus_level_id = Integer.parseInt(req.getParameter("cus_level_id"));
		int cus_id = Integer.parseInt(req.getParameter("cus_id"));
		
		HotelCustomer customer = new HotelCustomer(cus_name, cus_sex, cus_identity, cus_phone, cus_points, cus_level_id);
		customer.setCus_id(cus_id);
		Connection conn = null;
		
		try {
			conn = dbUtil.getCon();
			JSONObject result = new JSONObject();
			int saveNums = customerDao.customerModify(conn, customer);
			if (saveNums > 0){
				result.put("success", "true");
			}else{
				result.put("success", "true");
				result.put("errorMsg", "修改失败");
			}
			ResponseUtil.write(resp, result);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				dbUtil.closeCon(conn);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void customerDel(HttpServletRequest req, HttpServletResponse resp){
		String delIds=req.getParameter("delIds");
		Connection conn=null;
		try{
			conn=dbUtil.getCon();
			JSONObject result=new JSONObject();
			int delNums=customerDao.customerDelete(conn, delIds);
			if(delNums>0){
				result.put("success", "true");
				result.put("delNums", delNums);
			}else{
				result.put("errorMsg", "删除失败");
			}
			ResponseUtil.write(resp, result);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				dbUtil.closeCon(conn);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void initComboxLevel(HttpServletRequest req, HttpServletResponse resp){
		Connection conn = null;
		
		try{
			conn=dbUtil.getCon();
			JSONArray jsonArray = null;
			
			jsonArray = JsonUtil.formatRsToJsonArrayWithCombobox(levelDao.getLevelComboboxData(conn));
			
			ResponseUtil.write(resp, jsonArray);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				dbUtil.closeCon(conn);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
}
