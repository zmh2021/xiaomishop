package com.aaa.dao.impl;

import com.aaa.dao.ICollectDAO;
import com.aaa.entity.Collect;
import com.aaa.util.DaoUtil;

import java.util.List;
import java.util.Map;

public class   CollectDaoImpl implements ICollectDAO{
	/**
	 * ����ղؼ���Ϣ(�û�ID����ƷID)
	 */
	@Override
	public int productCollect(Collect collect) {
//		��collect��������һ�У�������Ʒid���û�id������ʶ�����ĸ��û��ղ����Ǽ���Ʒ
		String sql = "insert into collect values(null,?,?)";
//		int i
        return DaoUtil.executeUpdate(sql,collect.getPid(),collect.getUid());
    }
	/**
	 * �����û���id��ȡ�ղؼ���Ϣ
	 */
	@Override
	public List<Map<String, Object>> getUserCollect(Object uid) {
		String sql="select *from product p inner join collect c on p.pid=c.pid where uid=?";
		return DaoUtil.executeQuery(sql, uid);
	}
	/**
	 * ����pid �� uid ɾ���ղؼ���Ϣ
	 */
	@Override
	public int deleteOne(String pid,String uid) {
		String sql="delete from collect where pid=? and uid=?";
		return DaoUtil.executeUpdate(sql,pid,uid);
	}
	/**
	 * �ж��û��Ƿ��Ѿ��ղع���Ӧ����Ʒ
	 * ����Ĳ�����һ������Ʒid��һ�����û�id
	 */
	@Override
	public boolean isExist(int pid, String uid) {
//		��collect���в�ѯ��pid��uid��Ӧ����Ʒ��Ϣ
//		˵��ֻҪ���sql�ܲ鵽����˵����Ӧuid�û��Ѿ��ղع���Ӧpid����Ʒ��
		String sql = "select * from collect where pid=? and uid=?";
		List<Map<String,Object>> list = DaoUtil.executeQuery(sql,pid,uid);
//		���list����>0��֤������ж��������ݣ��������ݾ�֤������Ӧuid���û��ղ��˶�Ӧpid����Ʒ
		if(list.size()>0){
			return false;
		}
//		���list���Ȳ�>0����֤��list��ɶ��û�У�û�����ݣ�,֤����Ӧuid���û���û���ղض�Ӧpid����Ʒ
		return true;

    }
	/**
	 * ����ղغ�product���е�collect�е�ֵ��1
	 */
	@Override
	public int addProductNum(String pid) {
//		sql��������product���ж�Ӧpid����Ʒ��collect�е�ֵ��������ԭ�л�����+1
		String sql = "update product set collect = collect +1 where pid=?";
		return DaoUtil.executeUpdate(sql,pid);
//		int i  = DaoUtil.executeUpdate(sql,pid);
//		���ص�����Ӱ�������

    }
	/**
	 * ����ղؼе�ɾ����  product���е�collect��ֵ��1
	 */
	@Override
	public int reduceProductNum(String pid) {
		String sql="update product set collect = collect -1 where pid = ?";
		return DaoUtil.executeUpdate(sql, pid);
	}

	
}
