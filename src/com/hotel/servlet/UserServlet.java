package com.hotel.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hotel.dao.HotelUserDao;
import com.hotel.model.HotelUser;
import com.hotel.util.DbUtil;
import com.hotel.util.StringUtil;


public class UserServlet extends HttpServlet {

	private int method;
	DbUtil dbUtil = new DbUtil();
	HotelUserDao userDao = new HotelUserDao();

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
			// 用户登录
			this.login(req, resp);
		}
		if (method == 2) {
			// 添加用户
			this.addUser(req, resp);
		}
		if (method == 3) {
			// 更改用户密码
			this.pwdChange(req, resp);
		}
		if (method == 4) {
			// 用户退出
			this.quit(req, resp);
		}
	}

	private void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String user_account = req.getParameter("user_account");
		String user_password = req.getParameter("user_password");
		if(StringUtil.isEmpty(user_account) || StringUtil.isEmpty(user_password)){
			req.setAttribute("error", "用户名或密码为空");
			req.getRequestDispatcher("index.jsp").forward(req, resp);
			return ;
		}
		
		HotelUser hotelUser = new HotelUser(user_account, user_password);
		Connection conn = null;
		try {
			conn = dbUtil.getCon();
			HotelUser currentUser = userDao.login(conn, hotelUser);
			
			if (currentUser == null){
				req.setAttribute("error", "用户名或密码错误");
				req.getRequestDispatcher("index.jsp").forward(req, resp);
			}else{
				HttpSession session = req.getSession();
				session.setAttribute("currentUser", currentUser);
				session.setAttribute("user_id", currentUser.getUser_id());
				session.setAttribute("user_name", currentUser.getUser_name());
				session.setAttribute("user_account", currentUser.getUser_account());
				resp.sendRedirect("main.jsp");
			}
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

	private void addUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String user_account = req.getParameter("user_account");
		String user_password = req.getParameter("user_password");
		String user_name = req.getParameter("user_name");
		String user_sex = req.getParameter("user_sex");
		String user_phone = req.getParameter("user_phone");
		String confirmPassWord = req.getParameter("confirmPassWord");
		
		if(StringUtil.isEmpty(user_account) || StringUtil.isEmpty(user_password) || StringUtil.isEmpty(confirmPassWord)){
			req.setAttribute("error", "用户名或密码为空");
			req.getRequestDispatcher("register.jsp").forward(req, resp);
			return ;
		}
		
		if(user_phone.length() != 11){
			req.setAttribute("error", "手机号不对");
			req.getRequestDispatcher("register.jsp").forward(req, resp);
			return ;
		}

		if(!user_password.equals(confirmPassWord)){
			req.setAttribute("error", "两次输入密码不同");
			req.getRequestDispatcher("register.jsp").forward(req, resp);
			return ;
		}
		
		HotelUser hotelUser = new HotelUser(user_account, user_password);
		hotelUser.setUser_name(user_name);
		hotelUser.setUser_sex(user_sex);
		hotelUser.setUser_phone(user_phone);
		Connection conn = null;
		
		try {
			conn = dbUtil.getCon();
			
			if (userDao.checkOldUser(conn, hotelUser)){
				req.setAttribute("error", "用户已存在");
				req.getRequestDispatcher("register.jsp").forward(req, resp);
			}else{
				if(userDao.addUser(conn, hotelUser)){
					req.setAttribute("error", "添加用户成功<a href='index.jsp'>登陆</a>");
					req.getRequestDispatcher("register.jsp").forward(req, resp);
				}else{
					req.setAttribute("error", "添加用户失败");
					req.getRequestDispatcher("register.jsp").forward(req, resp);
				}
			}
			
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

	private void pwdChange(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String user_account = req.getParameter("user_account");
		String oldPassWord = req.getParameter("oldPassWord");
		String newPassWord = req.getParameter("newPassWord");
		String confirmNewPassWord = req.getParameter("confirmNewPassWord");
		Connection conn = null;
		
		if(StringUtil.isEmpty(user_account) || StringUtil.isEmpty(oldPassWord) || StringUtil.isEmpty(newPassWord) || StringUtil.isEmpty(confirmNewPassWord)){
			req.setAttribute("error", "用户名或密码为空");
			req.getRequestDispatcher("change.jsp").forward(req, resp);
			return ;
		}
		
		if(!newPassWord.equals(confirmNewPassWord)){
			req.setAttribute("error", "两次输入密码不同");
			req.getRequestDispatcher("change.jsp").forward(req, resp);
			return ;
		}
		
		HotelUser hotelUser = new HotelUser();
		hotelUser.setUser_id(Integer.parseInt(req.getParameter("user_id")));
		hotelUser.setUser_password(oldPassWord);
		
		try {
			conn = dbUtil.getCon();
			if (!userDao.checkOldPass(conn, hotelUser)){
				req.setAttribute("error", "旧密码错误");
				req.getRequestDispatcher("change.jsp").forward(req, resp);
				return ;
			} else {
				hotelUser = new HotelUser();
				hotelUser.setUser_id(Integer.parseInt(req.getParameter("user_id")));
				hotelUser.setUser_password(confirmNewPassWord);
				if(userDao.chgPwd(conn, hotelUser)){
					req.setAttribute("error", "修改密码成功<a href='index.jsp'>登陆</a>");
					req.getSession().setAttribute("currentUser", null);
					req.getRequestDispatcher("change.jsp").forward(req, resp);
				}else{
					req.setAttribute("error", "修改密码失败");
					req.getRequestDispatcher("change.jsp").forward(req, resp);
				}
			}
			
		} catch (SQLException e1) {
			e1.printStackTrace();
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

	private void quit(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		HttpSession session = req.getSession();
		session.setAttribute("currentUser", null);
		resp.sendRedirect("index.jsp");
	}

}
