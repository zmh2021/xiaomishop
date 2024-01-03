package com.aaa.dao;

import java.util.List;
import java.util.Map;

public interface IProductDAO {
	/**
	 * ��ȡ��ҳ��Ʒ
	 */
	List<Map<String, Object>> getHomeProduct();
	/**
	 * ����id��ȡ��Ʒ��Ϣ
	 * ������Ʒid��product���в��Ҷ�Ӧ��Ʒ��Ϣ���������ǽӿڲ㣬�����𷽷��ľ���ʵ�֣��������𷽷���׼������
	 * ����ʵ�ֻ�Ҫȥimpl��
	 * @return
	 */
	Map<String, Object> getProductByID(int id);
	/**
	 * ��ȡ�������б�
	 * @return
	 */
	List<Map<String, Object>> getJacket(int cid);
	/**
	 * ��ҳ������
	 */
	 List<Map<String,Object>> topmenu();
	 /**
	  * �������˵�
	  * @return
	  */
	 List<Map<String,Object>> lefttopmenu();
	 /**
	 * ��̬��ȡǰ̨��ҳ���ǵ�Ʒ�����ҳ��
	 */
	 Map<String,Object> centerProducts() ;
	 /**
	  * ��ѯ ��ǰҳ��Ʒ 
	  * @param page
	  * @param name
	  * @return
	  */
	Map<String, Object> getProduct(String page, String name);

}
