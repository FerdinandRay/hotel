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

import com.hotel.dao.HotelRoomDao;
import com.hotel.dao.HotelRoomTypeDao;
import com.hotel.model.HotelRoom;
import com.hotel.model.PageBean;
import com.hotel.util.DbUtil;
import com.hotel.util.JsonUtil;
import com.hotel.util.ResponseUtil;

public class RoomServlet extends HttpServlet {
	private int method;
	private DbUtil dbUtil = new DbUtil();
	private HotelRoomDao roomDao = new HotelRoomDao();
	private HotelRoomTypeDao roomTypeDao = new HotelRoomTypeDao();
	
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
			// 房间显示
			this.showRoom(req, resp);
		}
		if (method == 2) {
			// 房间添加
			this.roomAdd(req, resp);
		}
		if (method == 3) {
			// 房间修改
			this.roomModify(req, resp);
		}
		if (method == 4) {
			// 房间删除
			this.roomDel(req, resp);
		}
		if (method == 5){
			// Combobox
			this.initComboxRoomType(req, resp);
		}
	}
	
	private void showRoom(HttpServletRequest req, HttpServletResponse resp){
		String page = req.getParameter("page");
		String rows = req.getParameter("rows");
		
		PageBean pageBean = new PageBean(Integer.parseInt(page), Integer.parseInt(rows));
		Connection conn = null;
		JSONArray jsonArray = null;
		
		HotelRoom room = new HotelRoom();
		
		try {
			conn = dbUtil.getCon();
			JSONObject result = new JSONObject();
			jsonArray = JsonUtil.formatRsToJsonArray(roomDao.roomList(conn, pageBean, room));
			int total = roomDao.roomCount(conn);
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
	
	private void roomAdd(HttpServletRequest req, HttpServletResponse resp){
		int room_number = Integer.parseInt(req.getParameter("room_number"));
		int room_type_id = Integer.parseInt(req.getParameter("room_type_id"));
		String room_status = req.getParameter("room_status");
		
		Connection conn = null;
		HotelRoom room = new HotelRoom(room_number, room_type_id, room_status);
		
		try {
			conn = dbUtil.getCon();
			JSONObject result = new JSONObject();
			int saveNums = roomDao.roomAdd(conn, room);
			if (saveNums > 0){
				result.put("success", "true");
				roomTypeDao.reduceSurplusInRoomType(conn, room);
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
	
	private void roomModify(HttpServletRequest req, HttpServletResponse resp){
		int room_number = Integer.parseInt(req.getParameter("room_number"));
		int room_type_id = Integer.parseInt(req.getParameter("room_type_id"));
		String room_status = req.getParameter("room_status");
		int room_id = Integer.parseInt(req.getParameter("room_id"));
		
		Connection conn = null;
		HotelRoom room = new HotelRoom(room_number, room_type_id, room_status);
		room.setRoom_id(room_id);
		
		try {
			conn = dbUtil.getCon();
			JSONObject result = new JSONObject();
			int saveNums = roomDao.roomModify(conn, room);
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
	
	private void roomDel(HttpServletRequest req, HttpServletResponse resp){
		int delIds = Integer.parseInt(req.getParameter("delIds"));
		int delTypeIds = Integer.parseInt(req.getParameter("delTypeIds"));
		Connection conn=null;
		try{
			conn=dbUtil.getCon();
			JSONObject result=new JSONObject();
			int delNums=roomDao.roomDelete(conn, delIds);
			if(delNums>0){
				result.put("success", "true");
				result.put("delNums", delNums);
				roomTypeDao.plusSurplusInRoomType(conn, delTypeIds);
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
	
	private void initComboxRoomType(HttpServletRequest req, HttpServletResponse resp){
		Connection conn = null;
		
		try{
			conn=dbUtil.getCon();
			JSONArray jsonArray = null;
			
			jsonArray = JsonUtil.formatRsToJsonArrayWithCombobox(roomTypeDao.getRoomTypeComboboxData(conn));
			System.out.println(jsonArray);

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
