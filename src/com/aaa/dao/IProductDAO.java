package com.aaa.dao;

import java.util.List;
import java.util.Map;

public interface IProductDAO {
	/**
	 * 获取首页商品
	 */
	List<Map<String, Object>> getHomeProduct();
	/**
	 * 根据id获取商品信息
	 * 根据商品id从product表中查找对应商品信息——但这是接口层，不负责方法的具体实现，仅仅负责方法标准的声明
	 * 具体实现还要去impl层
	 * @return
	 */
	Map<String, Object> getProductByID(int id);
	/**
	 * 获取导航栏列表
	 * @return
	 */
	List<Map<String, Object>> getJacket(int cid);
	/**
	 * 主页的请求
	 */
	 List<Map<String,Object>> topmenu();
	 /**
	  * 左侧二级菜单
	  * @return
	  */
	 List<Map<String,Object>> lefttopmenu();
	 /**
	 * 动态获取前台首页明星单品和配件页面
	 */
	 Map<String,Object> centerProducts() ;
	 /**
	  * 查询 当前页商品 
	  * @param page
	  * @param name
	  * @return
	  */
	Map<String, Object> getProduct(String page, String name);

}
