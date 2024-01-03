package com.aaa.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ObjectUtils.Null;

import com.aaa.dao.IUserDao;
import com.aaa.dao.impl.UserDAOImpl;
import com.aaa.entity.Result;
import com.aaa.entity.User;
import com.aaa.util.AliyunCodeUtil;
import com.alibaba.fastjson.JSON;

@WebServlet("/user")
public class UserServlet extends BaseServlet {
	private IUserDao dao = new UserDAOImpl();
	/**
	 * 修改密码
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void changePsw(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}
	/**
	 * 注销
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void logout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}
	/**
	 * 获取用户信息
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void getUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//从session中获取用户登录信息

		//返回状态码200010和msg获取成功，附带用户信息
		Result result = new Result(200010,"用户信息获取成功",req.getSession().getAttribute("user"));
		//转换json
		String json = JSON.toJSONString(result);
		//返回前端
		resp.getWriter().write(json);
	}
	/**
	 * 登录业务
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void login(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//定义空结果集
		Result result = new Result();
		//获取telephone
		String telephone = req.getParameter("telephone");
		//获取password
		String password = req.getParameter("password");
		//调用dao层isLogin方法，验证用户输入的用户名和密码是否在数据库中存在
		Map<String,Object> user = dao.isLogin(telephone,password);
		if(user == null){
			//返回状态码20008和msg账号密码错误，附带空信息、
			System.out.println("登录失败");
		}
		else{
			//将查询到的用户信息存入session,KV,"user",user
			req.getSession().setAttribute("user",user);
			//返回状态码20009和msg登录成功，附带空信息
			result = new Result(20009,"登陆成功",null);
		}
		//结果集转换json
		String jsonString = JSON.toJSONString(result);
		//返回前端
		resp.getWriter().write(jsonString);
	}
	/**
	 * 发送验证码
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void reg(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}
	/**
	 * 验证码
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void sendCode(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}
}
