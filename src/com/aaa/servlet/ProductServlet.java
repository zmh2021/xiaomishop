package com.aaa.servlet;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import com.aaa.dao.IProductDAO;
//import com.aaa.dao.impl.ProductDAOImpl;
import com.aaa.dao.IProductDAO;
import com.aaa.dao.impl.ProductDAOImpl;
import com.aaa.entity.Result;
import com.alibaba.fastjson.JSON;
//我们的项目，前端发送请求的路径中，exploded之前都是固定的，exploded之后  ？之前的内容，就是我们的前端发送的请求确定servelet的依据
//http://localhost:8080/xiaomishop_war_exploded/pro?m=getProduct&id=55
//这个请求。发送之后到达后台会寻找路径名为pro的servlet，会调用对应servlet中的getProduct方法，该请求携带的参数为id，参数值为55
@WebServlet("/pro")
public class ProductServlet extends BaseServlet {

	Result result = null;

	private static final long serialVersionUID = 1L;
	private IProductDAO dao = new ProductDAOImpl();

	/**
	 * 商品分类
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void jacket(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String cid = req.getParameter("cid");
		List<Map<String, Object>> jacket = dao.getJacket(Integer.parseInt(cid));
		Result result = new Result(20008, "获取上衣信息成功", jacket);
		String jsonString = JSON.toJSONString(result);
		resp.getWriter().write(jsonString);
	}

	/**
	 * 请求携带参数为id=55（商品id），请求发送到后台，调用此方法，是为了用户点击对应的商品图片之后。在页面中显示对应商品的信息
	 * 请求发送到后台对应的servlet，调用对应的方法，需要从数据库中的product表中查询到对应商品的所有信息，并返回前端
	 * 我们要获取到对应商品的信息，就需要对应商品的id（因为商品id具有唯一性，是区分不同商品效率最高的依据）
	 * 商品id，从请求中获取
	 * 根据id商品详情
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void getProduct(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//我们要根据商品id获取对于的商品信息，商品id在请求中，我们就从请求中获取id值，把商品id从前端转换到前端
		String sid = req.getParameter("id");
		//转换int类型，将我们从请求中拿到的商品id的数据格式从String转换为int
		//String.valueof	int=>Integer
		int id = Integer.parseInt(sid);
		//调用dao层getProductById方法 通过 商品id 获取商品详细信息
		Map<String,Object> map = dao.getProductByID(id);
		//如果map==null,证明我们的impl层什么都没有查到
		if(map == null){
			//result,20002,"对不起，商品获取失败"，null
			result = new Result(20002,"对不起 商品获取失败",null);
		}else{
			//result,20003,"商品获取成功“，map
			result = new Result(20003,"商品获取成功",map);
		}
		//返回前端
		String jsonString = JSON.toJSONString(result);
		resp.getWriter().write(jsonString);
	}
	/**
	 * 获取全部商品信息
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void home(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 1 获取请求参数 每页显示十个
		String page = req.getParameter("page");
		// name 获取查询信息
		String name = req.getParameter("name");
		// 2 根据page查询当前页数据
		Map<String, Object> data = dao.getProduct(page, name);
		if (data != null) {
			result = new Result(20001, "获取首页信息成功", data);
		} else {
			result = new Result(20002, "获取首页信息失败", data);
		}
		String jsonString = JSON.toJSONString(result);
		resp.getWriter().write(jsonString);
	}
}
