package com.aaa.dao.impl;

import com.aaa.dao.IProductDAO;
import com.aaa.util.DaoUtil;
import com.aaa.util.PageUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public  class ProductDAOImpl implements IProductDAO{

	/**
	 * 获取首页商品
	 */
	@Override
	public List<Map<String, Object>> getHomeProduct() {
		String sql = "select * from product where pflag = 0 and state = 1";
		return DaoUtil.executeQuery(sql);
	}
	/**
	 * 根据id获取商品信息
	 * 方法的具体实现层，在这一层需要跟数据库打交道
	 * @return
	 */
	@Override
	public Map<String, Object> getProductByID(int id) {
		//impl层需要设计sql，但在设计sql之前，我们首先要明白为什么要设计这句sql
		//1.因为impl层是实现方法的一层，是需要往数据库中获取/拿取数据?，而要操作数据库就需要sql
		//2.我们这个方法是根据商品的id从数据库中获取对应的商品信息，那么这就是我们方法的需求，而我们设计的sql，一定是为了需求服务的
		String sql = "select * from product where pid = ?";
		List<Map<String,Object>> list = DaoUtil.executeQuery(sql, id);

		if (list.size() > 0) {

			return list.get(0);
		}
		return null;
	}
	/**
	 * 获取导航栏列表
	 * @return
	 */
	@Override
	public List<Map<String, Object>> getJacket(int cid) {
		String sql = "select * from product where cid = ?";
		
		return DaoUtil.executeQuery(sql , cid);
	}

	/**
	 * 主页的请求
	 */
	public List<Map<String,Object>> topmenu() {
        String sql="SELECT * FROM category";
        List<Map<String, Object>> list = DaoUtil.executeQuery(sql);
        return list;
    }
	/**
	  * 左侧二级菜单
	  * @return
	  */
    public List<Map<String,Object>> lefttopmenu()  {
        String sql="SELECT * FROM category";
        String sql2="SELECT * FROM product";
        List<Map<String,Object>> list=DaoUtil.executeQuery(sql);
        List<Map<String,Object>> list1=DaoUtil.executeQuery(sql2);
        for (Map map:list) {
            List<Map<String,Object>> list2=new ArrayList<Map<String,Object>>();
            for (Map map2:list1) {
               String cid1= (String) map.get("cid");
                String cid12= (String) map2.get("cid");
                if (cid1.equals(cid12)){
                  list2.add(map2);
                }
                map.put("zicaodao",list2);
            }
        }
        return list;
    }
    /**
     * 动态获取前台首页明星单品和配件页面
     */
    public Map<String,Object> centerProducts()  {
        String sql="SELECT * FROM product LIMIT 0,3";
        String sql1="SELECT * FROM product " +
                "ORDER BY  collect DESC " +
                "LIMIT 0,5 ";
        String sql2="SELECT * FROM product  " +
                " WHERE cid=8 LIMIT 0,8 ";
        List<Map<String,Object>> list=DaoUtil.executeQuery(sql);
        List<Map<String,Object>> list1=DaoUtil.executeQuery(sql1);
        List<Map<String,Object>> list2=DaoUtil.executeQuery(sql2);
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("parets",list2);
        map.put("clebrity",list1);
        map.put("centerProducts",list);
        return map;
    }

    /**
	  * 查询 当前页商品 
	  * @param page
	  * @param name
	  * @return
	  */
	@Override
	public Map<String, Object> getProduct(String page, String name) {
		//搜索的核心在于这个name有可能有  有可能没有   有的时候需要家where pname like ? 没有的时候  不需要
		//所以我们需要动态SQL
		//params.toArray()将list转换成数组
		//假如说搜索的是裤子   name
		List<Object> params = new ArrayList<>();
		StringBuilder sb = new StringBuilder();
		if (name != null) {
			sb.append("   and   pname  like ?   ");
			params.add("%" + name + "%");
		}
		//1 获取当前商品的数量
		StringBuilder sql1 = new StringBuilder("select count(*) c from product where pflag = 0 " +
				"and state = 1");
		
		sql1.append(sb);
		
		List<Map<String,Object>> d1 = DaoUtil.executeQuery(sql1.toString(), params.toArray());
		// d1 [{c=9}]
		long gong = (long) d1.get(0).get("c");
		
		PageUtil pageUtil = new PageUtil(Integer.parseInt(page), (int)gong, 20);
		
		// 查询当前页的数据
		StringBuilder sql2 = new StringBuilder("select * from product where pflag = 0 and state = 1");
		sql2.append(sb);
		sql2.append("  limit   ?    ,     ?    ");
		params.add(pageUtil.getIndex());
		params.add(pageUtil.getCount());
		
		List<Map<String, Object>> d2 = DaoUtil.executeQuery(sql2.toString(), params.toArray());
		
		Map<String, Object> data = new HashMap<>();
		data.put("pageInfo", pageUtil);
		data.put("productArr", d2);
		
		return data;
	}

}
