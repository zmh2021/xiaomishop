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
	 * ��ȡ��ҳ��Ʒ
	 */
	@Override
	public List<Map<String, Object>> getHomeProduct() {
		String sql = "select * from product where pflag = 0 and state = 1";
		return DaoUtil.executeQuery(sql);
	}
	/**
	 * ����id��ȡ��Ʒ��Ϣ
	 * �����ľ���ʵ�ֲ㣬����һ����Ҫ�����ݿ�򽻵�
	 * @return
	 */
	@Override
	public Map<String, Object> getProductByID(int id) {
		//impl����Ҫ���sql���������sql֮ǰ����������Ҫ����ΪʲôҪ������sql
		//1.��Ϊimpl����ʵ�ַ�����һ�㣬����Ҫ�����ݿ��л�ȡ/��ȡ����?����Ҫ�������ݿ����Ҫsql
		//2.������������Ǹ�����Ʒ��id�����ݿ��л�ȡ��Ӧ����Ʒ��Ϣ����ô��������Ƿ��������󣬶�������Ƶ�sql��һ����Ϊ����������
		String sql = "select * from product where pid = ?";
		List<Map<String,Object>> list = DaoUtil.executeQuery(sql, id);

		if (list.size() > 0) {

			return list.get(0);
		}
		return null;
	}
	/**
	 * ��ȡ�������б�
	 * @return
	 */
	@Override
	public List<Map<String, Object>> getJacket(int cid) {
		String sql = "select * from product where cid = ?";
		
		return DaoUtil.executeQuery(sql , cid);
	}

	/**
	 * ��ҳ������
	 */
	public List<Map<String,Object>> topmenu() {
        String sql="SELECT * FROM category";
        List<Map<String, Object>> list = DaoUtil.executeQuery(sql);
        return list;
    }
	/**
	  * �������˵�
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
     * ��̬��ȡǰ̨��ҳ���ǵ�Ʒ�����ҳ��
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
	  * ��ѯ ��ǰҳ��Ʒ 
	  * @param page
	  * @param name
	  * @return
	  */
	@Override
	public Map<String, Object> getProduct(String page, String name) {
		//�����ĺ����������name�п�����  �п���û��   �е�ʱ����Ҫ��where pname like ? û�е�ʱ��  ����Ҫ
		//����������Ҫ��̬SQL
		//params.toArray()��listת��������
		//����˵�������ǿ���   name
		List<Object> params = new ArrayList<>();
		StringBuilder sb = new StringBuilder();
		if (name != null) {
			sb.append("   and   pname  like ?   ");
			params.add("%" + name + "%");
		}
		//1 ��ȡ��ǰ��Ʒ������
		StringBuilder sql1 = new StringBuilder("select count(*) c from product where pflag = 0 " +
				"and state = 1");
		
		sql1.append(sb);
		
		List<Map<String,Object>> d1 = DaoUtil.executeQuery(sql1.toString(), params.toArray());
		// d1 [{c=9}]
		long gong = (long) d1.get(0).get("c");
		
		PageUtil pageUtil = new PageUtil(Integer.parseInt(page), (int)gong, 20);
		
		// ��ѯ��ǰҳ������
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
