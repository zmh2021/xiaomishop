package com.aaa.servlet;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.aaa.dao.ICartDao;
import com.aaa.dao.impl.CartDaoImpl;
import com.aaa.entity.Car;
import com.aaa.entity.Result;
import com.aaa.util.ImputedPriceUtil;
import com.alibaba.fastjson.JSON;
/**
 * 购物车
 * @author kong
 *
 */
@WebServlet("/foo/cart")
public class CartServlet extends BaseServlet{
	private ICartDao dao = new CartDaoImpl();
	
	/**
	 * 购物车数量加
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void plusNum(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Map<String, Object> user = (Map<String, Object>) req.getSession().getAttribute("user");
		Object uid = user.get("uid");
		String pid = req.getParameter("pid");
		List<Map<String, Object>> list = dao.plusNum(uid.toString(), pid);
		ImputedPriceUtil data = new ImputedPriceUtil(list);

		Result result = new Result(20026, "添加数量成功", data);
		String jsonString = JSON.toJSONString(result);
		resp.getWriter().write(jsonString);
	}
	/**
	 * 购物车数量减
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void reduceNum(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Map<String, Object> user = (Map<String, Object>) req.getSession().getAttribute("user");
		Object uid = user.get("uid");
		String pid = req.getParameter("pid");
		List<Map<String, Object>> list = dao.reduceNum(uid.toString(), pid);

		ImputedPriceUtil data = new ImputedPriceUtil(list);

		Result result = new Result(20027, "减少数量成功", data);
		String jsonString = JSON.toJSONString(result);
		resp.getWriter().write(jsonString);
	}
	
	/**
	 * 删除商品
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void deleteOne(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		Map<String,Object> user = (Map<String, Object>) req.getSession().getAttribute("user");
		Object uid = user.get("uid");

		String id = req.getParameter("id");
		//根据oid  删除购物车 对应商品    并返回购物车 内容
		List<Map<String, Object>> data = dao.delete(Integer.parseInt(id), uid);
		//
		ImputedPriceUtil list = new ImputedPriceUtil(data);

		Result result = new Result(20010, "删除信息成功", list);

		String jsonString = JSON.toJSONString(result);
		resp.getWriter().write(jsonString);
	}
	/**
	 * 删除购物车全部商品
	 * 
	 */
	protected void deleteAll(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String uid = req.getParameter("uid");
		Result result = null;
		List<Map<String, Object>> date = dao.deleteAll(uid);
		if (date!=null) {
			ImputedPriceUtil list = new ImputedPriceUtil(date);
			 result = new Result(20014, "删除信息成功", list);
		} else {
			result = new Result(20015, "删除信息失败", null);
		}
		String jsonString = JSON.toJSONString(result);
		resp.getWriter().write(jsonString);
	}
	
	/**
	 * 添加购物车
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void addCart(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Result result = null;
		String pid = req.getParameter("pid");
		String pname = req.getParameter("pname");
		String pimage = req.getParameter("pimage");
		String shopPrice = req.getParameter("shopPrice");
		String num = req.getParameter("num");

		Map<String, Object> user = (Map<String, Object>) req.getSession().getAttribute("user");

		Object uid = user.get("uid");

		 boolean i = dao.isExistProduct(String.valueOf(uid), pid);
		 if(i==true) {
			 int addNum = dao.addNum(String.valueOf(uid), pid);
			 result = new Result(20030, "您已经添加过了 数量加1", null);
		 }else {
		Car car = new Car(0, String.valueOf(uid), pid, pname, pimage, shopPrice, num);
		//首先判断当前商品是否已经被当前用户添加过购物车了   如果没有则添加  如果有  修改num值
		int a = dao.addCart(car);

		result = new Result(20012, "添加购物车成功", null);
		 }
		String jsonString = JSON.toJSONString(result);
		resp.getWriter().write(jsonString);
 
	}
	/**
	 * 获取购物车信息
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
//	获取购物车信息
	protected void getUserCart(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Map<String,Object> user = (Map<String, Object>) req.getSession().getAttribute("user");
		Object uid = user.get("uid");
		List<Map<String,Object>> data = dao.getUserCart(uid);
		ImputedPriceUtil list = new ImputedPriceUtil(data);
		Result result = new Result(20013,"获取成功",list);
		String jsonString = JSON.toJSONString(result);
		resp.getWriter().write(jsonString);
	}
	
}
