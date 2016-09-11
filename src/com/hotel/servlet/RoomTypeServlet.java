package com.hotel.servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.hotel.dao.HotelRoomTypeDao;
import com.hotel.model.HotelRoomType;
import com.hotel.model.PageBean;
import com.hotel.util.DbUtil;
import com.hotel.util.JsonUtil;
import com.hotel.util.ResponseUtil;

public class RoomTypeServlet extends HttpServlet{
	
	private int method;
	private DbUtil dbUtil = new DbUtil();
	private HotelRoomTypeDao roomTypeDao = new HotelRoomTypeDao();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		super.doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.method = Integer.parseInt(req.getParameter("method"));
		if (method == 1) {
			// 客房类型显示
			this.showRoomType(req, resp);
		}
		if (method == 2) {
			// 客房类型添加
			this.addRoomType(req, resp);
		}
		if (method == 3) {
			// 客房类型修改
			this.updateRoomType(req, resp);
		}
		if (method == 4) {
			// 客房类型删除
			this.delRoomType(req, resp);
		}
	}
	
	private void showRoomType(HttpServletRequest req, HttpServletResponse resp){

		String page = req.getParameter("page");
		String rows = req.getParameter("rows");
		
		PageBean pageBean = new PageBean(Integer.parseInt(page), Integer.parseInt(rows));
		Connection conn = null;
		JSONArray jsonArray = null;
		
		HotelRoomType roomType = new HotelRoomType();
		
		try {
			conn = dbUtil.getCon();
			JSONObject result = new JSONObject();
			jsonArray = JsonUtil.formatRsToJsonArray(roomTypeDao.roomTypeList(conn, pageBean, roomType));
			int total = roomTypeDao.roomTypeCount(conn);
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
	
	private void addRoomType(HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException{
		String roomtype_name = req.getParameter("roomtype_name");
		int roomtype_cost = Integer.parseInt(req.getParameter("roomtype_cost"));
		int roomtype_deposit = Integer.parseInt(req.getParameter("roomtype_deposit"));
		int roomtype_total = Integer.parseInt(req.getParameter("roomtype_total"));
		int roomtype_surplus = Integer.parseInt(req.getParameter("roomtype_total"));
		
		HotelRoomType roomType = new HotelRoomType(roomtype_name, roomtype_cost, roomtype_deposit, roomtype_total, roomtype_surplus);
				
		Connection conn = null;
		
		try {
			conn = dbUtil.getCon();
			JSONObject result = new JSONObject();
			int saveNums = roomTypeDao.roomTypeAdd(conn, roomType);
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
	
	private void updateRoomType(HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException{
		String roomtype_name = req.getParameter("roomtype_name");
		int roomtype_cost = Integer.parseInt(req.getParameter("roomtype_cost"));
		int roomtype_deposit = Integer.parseInt(req.getParameter("roomtype_deposit"));
		int roomtype_total = Integer.parseInt(req.getParameter("roomtype_total"));
		int roomtype_surplus = Integer.parseInt(req.getParameter("roomtype_surplus"));
		int roomtype_id = Integer.parseInt(req.getParameter("roomtype_id"));
		
		HotelRoomType roomType = new HotelRoomType(roomtype_name, roomtype_cost, roomtype_deposit, roomtype_total, roomtype_surplus);
		roomType.setRoomtype_id(roomtype_id);
		Connection conn = null;
		
		try {
			conn = dbUtil.getCon();
			JSONObject result = new JSONObject();
			int saveNums = roomTypeDao.roomTypeModify(conn, roomType);
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
	
	private void delRoomType(HttpServletRequest req, HttpServletResponse resp){
		int delIds=Integer.parseInt(req.getParameter("delIds"));
		Connection conn=null;
		try{
			conn=dbUtil.getCon();
			JSONObject result=new JSONObject();
			int delNums=roomTypeDao.roomTypeDelete(conn, delIds);
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
}
