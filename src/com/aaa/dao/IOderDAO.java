package com.aaa.dao;

import java.util.List;
import java.util.Map;

import com.aaa.entity.Order;

public interface IOderDAO {
	/**
	 * �ύ��Ʒ���鶩��
	 * @param order
	 * @return
	 */
	int addOrder(Order order);
	
	/**
	 * �ύ���ﳵ����
	 * @param order
	 * @return
	 */
	int addOrders(Order order);

	/**
	 * ����uid��ȡ������Ϣ
	 * @param uid
	 * @return
	 */
	List<Map<String,Object>> getMyOrders(String uid);
	/**
	 * ��ȡÿ����������
	 * @param uid
	 * @param Serial
	 * @return
	 */
	List<Map<String,Object>> details(String uid, String serial);
	/**
	 * ��Ʒ���鹺��� ������tradeNum
	 * @param pid
	 * @return
	 */
	int addQuantity(String tradeNum, String pid);
	
}
