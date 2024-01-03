package com.aaa.dao;

import com.aaa.entity.Car;

import java.util.List;
import java.util.Map;

public interface ICartDao {
	/**
	 * ��ӹ��ﳵ
	 * @param car
	 * @return
	 */
	int addCart(Car car);
	/**
	 * ��ȡ���ﳵ��Ϣ
	 * @param uid
	 * @return
	 */
	List<Map<String, Object>> getUserCart(Object uid);
	/**
	 * ɾ�����ﳵȫ����Ϣ
	 */
	List<Map<String, Object>> deleteAll(String uid);
	
	/**
	 * ɾ������
	 */
	List<Map<String, Object>> delete(int id, Object uid);

	/**
	 * ���ﳵ�������
	 * @param uid
	 * @param pid
	 * @return
	 */
	List<Map<String, Object>> plusNum(String uid, String pid);
	/**
	 * �����ﳵ��Ʒ����
	 * @param uid
	 * @param pid
	 * @return
	 */
	List<Map<String, Object>>reduceNum(String uid, String pid);
	/**
	 * �ж��Ƿ��Ѿ�����ӹ��ﳵ
	 * @param uid
	 * @param pid 
	 * @return
	 */
	boolean isExistProduct(String uid, String pid);
	/**
	 * �ظ���Ӻ�  ������1
	 * @param uid
	 * @param pid
	 * @return
	 */
	int addNum(String uid, String pid);

}
