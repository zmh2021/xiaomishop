package com.aaa.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.aaa.dao.impl.ProductDAOImpl;
import com.aaa.util.DaoUtil;
import com.alibaba.fastjson.JSON;

@WebServlet("/index/*")
@SuppressWarnings({"all"})
public class IndexServlet extends HttpServlet {

	ProductDAOImpl daoImper = new ProductDAOImpl();
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/json;charset=UTF-8");
		String url = request.getParameter("url");
		try {
			this.getClass().getDeclaredMethod(url, HttpServletRequest.class,
					HttpServletResponse.class).invoke(this, request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void topmenu(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
		List<Map<String,Object>> map = daoImper.topmenu();
		String jsonString = JSON.toJSONString(map);
		response.getWriter().write(jsonString);

	}
	/**
	 * 左侧二级菜单获取
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @throws SQLException
	 */
	protected void lefttopmenu(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
		//调用lefttopmenu方法
		List<Map<String,Object>> list = daoImper.lefttopmenu();
		//结果集转换json
		String jsonString = JSON.toJSONString(list);
		//返回前端
		response.getWriter().write(jsonString);
	}

	protected void centerProducts(HttpServletRequest request,
								  HttpServletResponse response)
			throws ServletException, IOException, SQLException {

		Map<String,Object> map = daoImper.centerProducts();
		String jsonString = JSON.toJSONString(map);
		response.getWriter().write(jsonString);

	}
}
