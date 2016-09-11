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

import com.hotel.dao.HotelCheckoutDao;
import com.hotel.model.HotelCheckOut;
import com.hotel.model.PageBean;
import com.hotel.util.DbUtil;
import com.hotel.util.JsonUtil;
import com.hotel.util.ResponseUtil;

public class CheckoutServlet extends HttpServlet{
	private int method;
	private DbUtil dbUtil = new DbUtil();
	private HotelCheckoutDao checkoutDao = new HotelCheckoutDao();
	
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
			this.showCheckout(req, resp);
		}
		if (method == 2) {
			// 入住信息删除
			this.checkoutDel(req, resp);
		}
	}
	
	private void showCheckout(HttpServletRequest req, HttpServletResponse resp){
		String page = req.getParameter("page");
		String rows = req.getParameter("rows");
		String checkout_datetime = req.getParameter("checkout_datetime");
		
		if(checkout_datetime == null){
			checkout_datetime = "";
		}
		
		PageBean pageBean = new PageBean(Integer.parseInt(page), Integer.parseInt(rows));
		Connection conn = null;
		JSONArray jsonArray = null;
		
		HotelCheckOut checkout = new HotelCheckOut();
		checkout.setCheckout_datetime(checkout_datetime);
		
		try {
			conn = dbUtil.getCon();
			JSONObject result = new JSONObject();
			jsonArray = JsonUtil.formatRsToJsonArray(checkoutDao.checkoutList(conn, pageBean, checkout));
			int total = checkoutDao.checkoutCount(conn);
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
	
	private void checkoutDel(HttpServletRequest req, HttpServletResponse resp){
		String delIds=req.getParameter("delIds");
		Connection conn=null;
		try{
			conn=dbUtil.getCon();
			JSONObject result=new JSONObject();
			int delNums=checkoutDao.checkoutDelete(conn, delIds);
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
