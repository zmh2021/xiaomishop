package com.aaa.dao;

import com.aaa.entity.Collect;

import java.util.List;
import java.util.Map;

public interface ICollectDAO {
	/**
	 * 根据对应的pid和uid，将用户点击收藏的商品添加到对应用户的收藏夹中取
	 * @param collect
	 * @return
	 */
	int productCollect(Collect collect);
	/** 
	 * ?????????id??????????
	 * @param uid
	 * @return
	 */
	List<Map<String, Object>> getUserCollect(Object uid);
	/**
	 * ????pid ?? uid ??????????
	 * @param pid
	 * @return
	 */
	int deleteOne(String pid, String uid);
	/**
	 * 查询用户想要收藏的商品，是否已经在用户之前的收藏夹中了
	 *
	 * @param pid
	 * @param uid
	 * @return
	 */
	boolean isExist(int pid, String uid);
	/**
	 * 将对应商品的收藏数量+1
	 * @param pid
	 * @return
	 */
	int addProductNum(String pid);
	/**
	 * ??????е??????  product???е?collect?????1
	 * @param pid
	 * @return
	 */
	int reduceProductNum(String pid);
	
}
