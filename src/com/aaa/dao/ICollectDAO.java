package com.aaa.dao;

import com.aaa.entity.Collect;

import java.util.List;
import java.util.Map;

public interface ICollectDAO {
	/**
	 * ���ݶ�Ӧ��pid��uid�����û�����ղص���Ʒ��ӵ���Ӧ�û����ղؼ���ȡ
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
	 * ��ѯ�û���Ҫ�ղص���Ʒ���Ƿ��Ѿ����û�֮ǰ���ղؼ�����
	 *
	 * @param pid
	 * @param uid
	 * @return
	 */
	boolean isExist(int pid, String uid);
	/**
	 * ����Ӧ��Ʒ���ղ�����+1
	 * @param pid
	 * @return
	 */
	int addProductNum(String pid);
	/**
	 * ??????��??????  product???��?collect?????1
	 * @param pid
	 * @return
	 */
	int reduceProductNum(String pid);
	
}
