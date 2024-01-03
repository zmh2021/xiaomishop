package com.aaa.dao.impl;

import com.aaa.dao.IUserDao;
import com.aaa.entity.User;
import com.aaa.util.DaoUtil;

import java.util.List;
import java.util.Map;

public class UserDAOImpl implements IUserDao{
	/**
	 * �ֻ���Ψһ��У��
	 * @param telephone
	 * @return
	 */
	@Override
	public boolean isExist(String telephone) {
//		��̬sql����ѯ�绰�Ƿ��Ѿ�ע���,��ѯ��Ӧ�绰�Ƿ���
		String sql = "select * from user where telephone = ?";
//		ʹ��list������ΪMap��Map����ΪString��Object���յ���DaoUtil��������executrQuery(sql,telephone)�������صĽ������
		List<Map<String,Object>> list = DaoUtil.executeQuery(sql, telephone);
//		���ص��ǲ���ֵ���ٷ���ǰ���ж�list.size>0�������true����true����false����false
//		����ֵ��false
		return list.size() > 0;
	}
	/**
	 * �û�ע��
	 * @param user
	 * @return
	 */
	@Override
	public int add(User user) {
//		��̬sql����user�������id���绰�����룬�ǳƺ����䣬idΪ��
		String sql = "insert into user values (null , ? , ? , ? , ?)";
//		���ص���DaoUtil.executeUpdate()�����Ľ��
		return DaoUtil.executeUpdate(sql, user.getTelephone() ,
				user.getPassword() , user.getEmail(),user.getNickname());
	}
	/**
	 * ��¼
	 * @param telephone
	 * @param password
	 * @return
	 */
	@Override
	public Map<String, Object> isLogin(String telephone, String password) {
//		��̬sql����ѯuser�����Ƿ��ж�Ӧ�绰��������û���Ϣ
		String sql = "select * from user where telephone = ? and password = ?";
//		����DaoUtil��executeQuery()������ִ��sql�����������б�Ϊsql��telephone��password
		List<Map<String,Object>> list = DaoUtil.executeQuery(sql, telephone , password);
//		list>0��֤���û���Ϣ�������ݿ���
		if (list.size() > 0) {
//			��list�л�ȡ�±�Ϊ0���û���Ϣ������
			return list.get(0);
		}
//		�鲻����Ӧ�û���Ϣ�ͷ��ؿ�
		return null;
	}
	/**
	 * �޸ĵ�¼����
	 * @param password
	 * @return
	 */
	@Override
	public int changePsw(String password,String uid) {
//		��̬sql������user���uidΪ�û�id��password��
		String sql="update user set password=? where uid=?";
//		���ص���DaoUtil��������executeUpdate()�Ľ���������б�sql,password,uid
		return DaoUtil.executeUpdate(sql,password, uid);
	}
}
