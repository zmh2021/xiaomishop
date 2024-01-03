package com.aaa.servlet;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.aaa.dao.IOderDAO;
import com.aaa.dao.impl.OrderDAOImpl;
import com.aaa.entity.Order;
import com.aaa.entity.Result;
import com.aaa.util.OrdersUtil;
import com.alibaba.fastjson.JSON;
@WebServlet("/order")
public class OrderServlet extends BaseServlet{
	
	private IOderDAO dao = new OrderDAOImpl();
	/**
	 * 获取单个订单信息
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void details(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Map<String, Object> user = (Map<String, Object>) req.getSession().getAttribute("user");
		Object uid = user.get("uid");
		String serial = req.getParameter("serial");
		List<Map<String, Object>> list = dao.details(String.valueOf(uid),serial);
		OrdersUtil map = new OrdersUtil(list);
		Result result = new Result(20035, "获取订单信息成功", map);
		String jsonString = JSON.toJSONString(result);
		resp.getWriter().write(jsonString);
		
	}
	
	/**
	 * 获取用户所有订单信息
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void getOrders(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Map<String, Object> user = (Map<String, Object>) req.getSession().getAttribute("user");
		Object uid = user.get("uid");
		
		List<Map<String, Object>> list = dao.getMyOrders(String.valueOf(uid));
		Result result = new Result(20033, "获取所有订单信息成功", list);
		String jsonString = JSON.toJSONStringWithDateFormat(result, "yyyy-MM-dd HH:mm:ss");
		resp.getWriter().write(jsonString);
	}
	/**
	 * 购物车全部付款   清空购物车
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void getMoney(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Result result =null;
		Map<String, Object> user = (Map<String, Object>) req.getSession().getAttribute("user");
		Object uid = user.get("uid");
		String tradeNum=req.getParameter("tradeNum");
		String province=req.getParameter("province");
		String city=req.getParameter("city");
		String county=req.getParameter("county");
		String site=req.getParameter("site");
		String phone=req.getParameter("phone");
		String consigneeName=req.getParameter("consigneeName");
		Order order = new Order(0, String.valueOf(uid),tradeNum, province, city, county, site, phone, consigneeName);
		IOderDAO dao = new OrderDAOImpl();
		int addOrders = dao.addOrders(order);
		if(addOrders==1) {
			result = new Result(20032, "购买失败", null);
			
		}else {
			result = new Result(20033, "购买成功", null);
		}
		String jsonString = JSON.toJSONString(result);
		resp.getWriter().write(jsonString);
	}
	
	/**
	 * 添加订单
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void addOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Map<String, Object> user = (Map<String, Object>) req.getSession().getAttribute("user");
		Object uid = user.get("uid");
		String pid=req.getParameter("pid");
		String tradeNum=req.getParameter("tradeNum");
		String province=req.getParameter("province");
		String city=req.getParameter("city");
		String county=req.getParameter("county");
		String site=req.getParameter("site");
		String phone=req.getParameter("phone");
		String consigneeName=req.getParameter("consigneeName");
		Order order = new Order(0, String.valueOf(uid), pid, tradeNum, province, city, county, site, phone, consigneeName);
		IOderDAO dao = new OrderDAOImpl();
		int i = dao.addOrder(order);
		dao.addQuantity(tradeNum, pid);
		Result result = new Result(20027, "购买成功", null);
		String jsonString = JSON.toJSONString(result);
		resp.getWriter().write(jsonString);
	}
	
	/**
	 * 判断是否登录
	 */
	protected void isExist(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Object user = req.getSession().getAttribute("user");
		
		if (user == null) {
			
			Result result = new Result(20011, "对不起   请先登录", null);
			String jsonString = JSON.toJSONString(result);
			resp.getWriter().write(jsonString);
		}
		
		
	}
}
