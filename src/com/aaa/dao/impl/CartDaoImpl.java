package com.aaa.dao.impl;

import com.aaa.dao.ICartDao;
import com.aaa.entity.Car;
import com.aaa.util.DaoUtil;

import java.util.List;
import java.util.Map;

public class CartDaoImpl implements ICartDao {
	/**
	 * 添加购物车
	 * 商品没有存在购物车中，我们向car表中添加一条新的数据
	 */
	@Override
	public int addCart(Car car) {
		String sql = "insert into car values (null , ? , ? , ? , ? , ? ,?)";
//		int i  = DaoUtil.executeUpdate(sql, car.getUid() , car.getPid() , car.getPname() , car.getPimage() , car.getPrice() , car.getNum());
		return DaoUtil.executeUpdate(sql, car.getUid() , car.getPid() , car.getPname() , car.getPimage() , car.getPrice() , car.getNum());
	}
	/**
	 * 获取购物车信息
	 */
	@Override
	public List<Map<String, Object>> getUserCart(Object uid) {
		String sql = "select * from car where uid = ?";
//		List<Map<String,Object>> result = DaoUtil.executeQuery(sql,uid);

		return DaoUtil.executeQuery(sql,uid);
	}
	/**
	 * 全部删除购物车信息
	 */
	@Override
	public List<Map<String, Object>> deleteAll(String uid) {
		//根据uid删除 
		String sql = "delete from car where uid = ?";		
		int i = DaoUtil.executeUpdate(sql, uid);
		if(i>0) {
			String sql2 = "select * from car where uid = ?";
			return DaoUtil.executeQuery(sql2, uid);
		}
		return null;
		//根据uid查询购物车
		
	}
	/**
	 * 购物车单个删除
	 */
	@Override
	public List<Map<String, Object>> delete(int id,Object uid) {
		
		String sql = "delete  from car where oid = ?"; 
		 int i = DaoUtil.executeUpdate(sql, id);
		 
		 if(i>0) {
			 String sql2 = "select * from car where uid = ?"; 
				
			 return DaoUtil.executeQuery(sql2, uid);
		 }
		 return null;
		 
	}
	/**
	 * 购物车数量他添加
	 */
	@Override
	public List<Map<String, Object>> plusNum(String uid, String pid) {
		String sql="update car set num = num +1 where uid = ? and pid = ?  ";
		int i = DaoUtil.executeUpdate(sql, uid,pid);  
		
		if(i>0) {
			String sql2 = "select * from car where uid = ?";
			return DaoUtil.executeQuery(sql2, uid);
		}
		return null;
		
	}
	/**
	 * 减购物车数量
	 */
	@Override
	public List<Map<String, Object>> reduceNum(String uid, String pid) {
		String sql="update car set num = num - 1 where uid = ? and pid = ? and num>1";
		int i = DaoUtil.executeUpdate(sql, uid,pid);
		
		if(i>0) {
			String sql2 = "select * from car where uid = ?";
			return DaoUtil.executeQuery(sql2, uid);
		}
		
		return null;
	}
	/**
	 * 判断是否已经添加过购物车
	 *
	 */
	@Override
	public boolean isExistProduct(String uid, String pid) {
//		从car表中查询对应uid用户是否已经添加了对应pid商品的信息
		String sql = "select * from car where uid=? and pid=?";
		List<Map<String,Object>> list = DaoUtil.executeQuery(sql,uid,pid);
		if(list.size()>0){
			return true;
		}
        return false;
    }
	/**
	 * 重复添加之后  数量加1
	 */
	@Override
	public int addNum(String uid, String pid) {
		String sql = "update car set num = num + 1 where uid = ? and pid = ?";
//		int i
        return DaoUtil.executeUpdate(sql,uid,pid);
    }
}
