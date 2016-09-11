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

import com.hotel.dao.HotelLevelDao;
import com.hotel.model.HotelLevel;
import com.hotel.model.HotelRoomType;
import com.hotel.model.PageBean;
import com.hotel.util.DbUtil;
import com.hotel.util.JsonUtil;
import com.hotel.util.ResponseUtil;

public class LevelServlet extends HttpServlet {
	
	private int method;
	private DbUtil dbUtil = new DbUtil();
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
			// 等级显示
			this.showLevel(req, resp);
		}
		if (method == 2) {
			// 等级添加
			this.levelAdd(req, resp);
		}
		if (method == 3) {
			// 等级修改
			this.levelModify(req, resp);
		}
		if (method == 4) {
			// 等级删除
			this.levelDel(req, resp);
		}
	}
	
	private void showLevel(HttpServletRequest req, HttpServletResponse resp){
		String page = req.getParameter("page");
		String rows = req.getParameter("rows");
		
		PageBean pageBean = new PageBean(Integer.parseInt(page), Integer.parseInt(rows));
		Connection conn = null;
		JSONArray jsonArray = null;
		
		HotelLevel level = new HotelLevel();
		
		try {
			conn = dbUtil.getCon();
			JSONObject result = new JSONObject();
			jsonArray = JsonUtil.formatRsToJsonArray(levelDao.levelList(conn, pageBean, level));
			int total = levelDao.levelCount(conn);
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
	
	private void levelAdd(HttpServletRequest req, HttpServletResponse resp){
		String level_name = req.getParameter("level_name");
		int level_points = Integer.parseInt(req.getParameter("level_points"));
		int level_rate = Integer.parseInt(req.getParameter("level_rate"));
				
		Connection conn = null;
		HotelLevel level = new HotelLevel(level_name, level_points, level_rate);
		
		try {
			conn = dbUtil.getCon();
			JSONObject result = new JSONObject();
			int saveNums = levelDao.levelAdd(conn, level);
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
	
	private void levelModify(HttpServletRequest req, HttpServletResponse resp){
		String level_name = req.getParameter("level_name");
		int level_points = Integer.parseInt(req.getParameter("level_points"));
		int level_id = Integer.parseInt(req.getParameter("level_id"));
		int level_rate = Integer.parseInt(req.getParameter("level_rate"));
		
		Connection conn = null;
		HotelLevel level = new HotelLevel(level_name, level_points, level_rate);
		level.setLevel_id(level_id);
		
		try {
			conn = dbUtil.getCon();
			JSONObject result = new JSONObject();
			int saveNums = levelDao.levelModify(conn, level);
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
	
	private void levelDel(HttpServletRequest req, HttpServletResponse resp){
		String delIds=req.getParameter("delIds");
		Connection conn=null;
		try{
			conn=dbUtil.getCon();
			JSONObject result=new JSONObject();
			int delNums=levelDao.levelDelete(conn, delIds);
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
