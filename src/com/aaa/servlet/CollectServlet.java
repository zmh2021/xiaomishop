package com.aaa.servlet;

import com.aaa.dao.ICollectDAO;
import com.aaa.dao.impl.CollectDaoImpl;
import com.aaa.entity.Collect;
import com.aaa.entity.Result;
import com.alibaba.fastjson.JSON;
import com.sun.corba.se.impl.ior.OldJIDLObjectKeyTemplate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/foo/collect")
public class CollectServlet extends BaseServlet{

	private static final long serialVersionUID = 1L;
	private ICollectDAO dao = new CollectDaoImpl();
	/**
	 * 根据收藏的id删除收藏
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void deleteCollect(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//传入商品id
		String pid = req.getParameter("pid");
		//从session中拿到user信息
		Map<String,Object> user = (Map<String, Object>) req.getSession().getAttribute("user");
		//拿到uid
		Object uid = user.get("uid");
		//调用Dao层方法删除收藏的商品
		int i = dao.deleteOne(pid,String.valueOf(uid));
		//调用Dao层方法Product表中的collect的值减1
		int reduceProductNum = dao.reduceProductNum(pid);
		//返回result，result是一个带有标识符的返回实体类
		Result result = new Result(20019,"删除成功",null);

		String jsonString = JSON.toJSONString(result);
		resp.getWriter().write(jsonString);
	}
	/**
	 * 获取收藏夹信息
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void getCollect(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//从session中拿出user的信息
		Map<String,Object> user = (Map<String, Object>) req.getSession().getAttribute("user");
		//拿到userid
		Object uid = user.get("uid");
		//根据userid拿到对应的收藏夹中的信息
		List<Map<String, Object>> data = dao.getUserCollect(uid);
		//返回拿到的收藏夹信息和状态码
		Result result = new Result(20018,"获取成功",data);
		//将result转换为json字符串返回前端
		String jsonString = JSON.toJSONString(result);
		resp.getWriter().write(jsonString);
	}

	/**
	 * 在商品详情页面，点击收藏，将对应商品信息添加到收藏夹中
	 * 收藏商品,如果用户收藏过来，返回失败结果，如果用户没用收藏过，先将商品表中的商品的收藏数+1，然后再把商品放到用户的收藏夹中去
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void collect(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		//结果集默认为空,在处理过后，用于存储我们的处理结果（收藏结果）--成功or失败？
		Result result = null;
		// 从请求中获取到商品
		//我要收藏一个商品，我就要知道我收藏的是哪个商品，所以我需要从请求中获取到商品id，因为id不会重复，具有唯一性，可以用来识别商品
		String pid = req.getParameter("pid");
		//使用Map存储从session拿到的user信息,用于我们确定，这是我们哪个用户点的收藏
		Map<String,Object> user = (Map<String,Object>) req.getSession().getAttribute("user");
		//从map中获取id
		Object uid = user.get("uid");
		//调用dao层的isExist方法判断商品是否已经在收藏夹中
		//一定是在进行收藏操作之前，就要调用dao层的isExist方法判断商品是否已经在收藏夹中
		boolean i = dao.isExist(Integer.parseInt(pid),String.valueOf(uid));
		//如果在收藏夹中
		if(i==false){
			//直接返回结果，20021，“请勿重复添加”,null
			result = new Result(20021,"您已经添加过了，是否查看",null);
		}else{
			//不在收藏夹中就添加收藏夹，调用dao层addProductNum方法
			int addProductNum = dao.addProductNum(pid);
			//我们进行实体类封装的原因，是我们要调用的productCollect方法，要求我们传入一个Collect实体类
			//将pid和uid进行collect实体类封装
			Collect collect = new Collect(0,Integer.parseInt(pid),String.valueOf(uid));
			//调用dao层productCollect方法进行收藏夹的商品信息添加
			int a= dao.productCollect(collect);
			//result，20017，“添加成功，是否查看收藏夹”，null
			result = new Result(20017,"添加成功",null);
		}
		//返回前端
		String jsonString = JSON.toJSONString(result);
		resp.getWriter().write(jsonString);
	}
}
