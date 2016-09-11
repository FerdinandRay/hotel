package com.hotel.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.RequestWrapper;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.hotel.dao.HotelCheckinDao;
import com.hotel.dao.HotelCheckoutDao;
import com.hotel.dao.HotelCustomerDao;
import com.hotel.dao.HotelLevelDao;
import com.hotel.dao.HotelRoomDao;
import com.hotel.model.HotelCheckIn;
import com.hotel.model.HotelCheckOut;
import com.hotel.model.PageBean;
import com.hotel.util.DateUtil;
import com.hotel.util.DbUtil;
import com.hotel.util.JsonUtil;
import com.hotel.util.ResponseUtil;

public class CheckinServlet extends HttpServlet{
	
	private int method;
	private DbUtil dbUtil = new DbUtil();
	private HotelCheckinDao checkinDao = new HotelCheckinDao();
	private HotelRoomDao roomDao = new HotelRoomDao();
	private HotelCustomerDao customerDao = new HotelCustomerDao();
	private HotelCheckoutDao checkoutDao = new HotelCheckoutDao();
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
			// 入住信息显示
			this.showCheckin(req, resp);
		}
		if (method == 2) {
			// 入住信息添加
			this.checkinAdd(req, resp);
		}
		if (method == 3) {
			// 入住信息修改
			this.checkinModify(req, resp);
		}
		if (method == 4) {
			// 入住信息删除
			try {
				this.checkinDel(req, resp);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if (method == 5){
			// 初始化ComboxCus
			this.initComboxCus(req, resp);
		}
		if (method == 6){
			// 初始化ComboxRoom
			this.initComboxRoom(req, resp);
		}
	}
	
	private void showCheckin(HttpServletRequest req, HttpServletResponse resp){
		String page = req.getParameter("page");
		String rows = req.getParameter("rows");
		String checkin_datetime = req.getParameter("checkin_datetime");
		
		if(checkin_datetime == null){
			checkin_datetime = "";
		}
		
		PageBean pageBean = new PageBean(Integer.parseInt(page), Integer.parseInt(rows));
		Connection conn = null;
		JSONArray jsonArray = null;
		
		HotelCheckIn checkin = new HotelCheckIn();
		checkin.setCheckin_datetime(checkin_datetime);
		
		try {
			conn = dbUtil.getCon();
			JSONObject result = new JSONObject();
			jsonArray = JsonUtil.formatRsToJsonArray(checkinDao.checkinList(conn, pageBean, checkin));
			int total = checkinDao.checkinCount(conn);
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
	
	private void checkinAdd(HttpServletRequest req, HttpServletResponse resp){
		int checkin_user_id = (int) req.getSession().getAttribute("user_id");
		int checkin_cus_id = Integer.parseInt(req.getParameter("checkin_cus_id"));
		int checkin_room_id = Integer.parseInt(req.getParameter("checkin_room_id"));
		int checkin_day = Integer.parseInt(req.getParameter("checkin_day"));
		String checkin_datetime = req.getParameter("checkin_datetime");
				
		Connection conn = null;
		
		HotelCheckIn checkin = new HotelCheckIn(checkin_user_id, checkin_cus_id, checkin_room_id, checkin_datetime, checkin_day);
		
		try {
			conn = dbUtil.getCon();
			JSONObject result = new JSONObject();
			int saveNums = checkinDao.checkinAdd(conn, checkin);
			
			if (saveNums > 0){
				result.put("success", "true");
				roomDao.updateRoomStatusToFill(conn, checkin_room_id);
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
	
	private void checkinModify(HttpServletRequest req, HttpServletResponse resp){
		int checkin_user_id = Integer.parseInt(req.getParameter("checkin_user_id"));
		int checkin_cus_id = Integer.parseInt(req.getParameter("checkin_cus_id"));
		int checkin_room_id = Integer.parseInt(req.getParameter("checkin_room_id"));
		int checkin_day = Integer.parseInt(req.getParameter("checkin_day"));
		String checkin_datetime = req.getParameter("checkin_datetime");
		int checkin_id = Integer.parseInt(req.getParameter("checkin_id"));
		
		HotelCheckIn checkin = new HotelCheckIn(checkin_user_id, checkin_cus_id, checkin_room_id, checkin_datetime, checkin_day);
		checkin.setCheckin_id(checkin_id);
		Connection conn = null;
		
		try {
			conn = dbUtil.getCon();
			JSONObject result = new JSONObject();
			int saveNums = checkinDao.checkinModify(conn, checkin);
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
	
	private void checkinDel(HttpServletRequest req, HttpServletResponse resp) throws ParseException{
		int delIds=Integer.parseInt(req.getParameter("delIds"));
		int checkin_room_id=Integer.parseInt(req.getParameter("checkin_room_id"));
		int checkout_user_id=(int) req.getSession().getAttribute("user_id");
		int checkout_cus_id=Integer.parseInt(req.getParameter("checkin_cus_id"));
		String checkout_datetime=new Date(System.currentTimeMillis()).toString();
		String checkin_datetime=req.getParameter("checkin_datetime");
		int roomtype_cost = Integer.parseInt(req.getParameter("roomtype_cost"));
		int spend_day = DateUtil.getDay(checkin_datetime, checkout_datetime);
		
		Connection conn=null;
		HotelCheckOut checkout = new HotelCheckOut(checkout_user_id, checkout_cus_id, checkin_room_id, checkout_datetime);
		
		try{
			conn=dbUtil.getCon();
			int count_rate = levelDao.getRate(conn, checkout_cus_id);
			checkout.setCheckout_pay((spend_day + 1) * roomtype_cost * (count_rate/100D));
			JSONObject result=new JSONObject();
			int delNums=checkinDao.checkinDelete(conn, delIds);
			if(delNums>0){
				result.put("success", "true");
				result.put("delNums", delNums);
				roomDao.updateRoomStatusToEmpty(conn, checkin_room_id);
				checkoutDao.checkoutAdd(conn, checkout);
				customerDao.updatePoints(conn, checkout_cus_id);
				customerDao.updateLevel(conn, checkout_cus_id);
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
	
	private void initComboxCus(HttpServletRequest req, HttpServletResponse resp){
		String type = req.getParameter("type");
		Connection conn = null;
		
		try{
			conn=dbUtil.getCon();
			JSONArray jsonArray = null;
			
			jsonArray = JsonUtil.formatRsToJsonArrayWithCombobox(customerDao.getCustomerComboboxData(conn));
			
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
	
	private void initComboxRoom(HttpServletRequest req, HttpServletResponse resp){
		Connection conn = null;
		
		try{
			conn=dbUtil.getCon();
			JSONArray jsonArray = null;
			
			jsonArray = JsonUtil.formatRsToJsonArrayWithCombobox(roomDao.getRoomComboboxData(conn));

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
