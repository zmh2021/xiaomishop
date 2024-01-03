package com.aaa.dao.impl;

import com.aaa.dao.ICollectDAO;
import com.aaa.entity.Collect;
import com.aaa.util.DaoUtil;

import java.util.List;
import java.util.Map;

public class   CollectDaoImpl implements ICollectDAO{
	/**
	 * 添加收藏夹信息(用户ID和商品ID)
	 */
	@Override
	public int productCollect(Collect collect) {
//		向collect表中新增一列，新增商品id和用户id，可以识别是哪个用户收藏了那件商品
		String sql = "insert into collect values(null,?,?)";
//		int i
        return DaoUtil.executeUpdate(sql,collect.getPid(),collect.getUid());
    }
	/**
	 * 根据用户的id获取收藏夹信息
	 */
	@Override
	public List<Map<String, Object>> getUserCollect(Object uid) {
		String sql="select *from product p inner join collect c on p.pid=c.pid where uid=?";
		return DaoUtil.executeQuery(sql, uid);
	}
	/**
	 * 根据pid 和 uid 删除收藏夹信息
	 */
	@Override
	public int deleteOne(String pid,String uid) {
		String sql="delete from collect where pid=? and uid=?";
		return DaoUtil.executeUpdate(sql,pid,uid);
	}
	/**
	 * 判断用户是否已经收藏过对应的商品
	 * 传入的参数，一个是商品id，一个是用户id
	 */
	@Override
	public boolean isExist(int pid, String uid) {
//		从collect表中查询与pid和uid对应的商品信息
//		说明只要这句sql能查到，就说明对应uid用户已经收藏过对应pid的商品了
		String sql = "select * from collect where pid=? and uid=?";
		List<Map<String,Object>> list = DaoUtil.executeQuery(sql,pid,uid);
//		如果list长度>0，证明里边有东西（数据），有数据就证明，对应uid的用户收藏了对应pid的商品
		if(list.size()>0){
			return false;
		}
//		如果list长度不>0，就证明list中啥都没有（没有数据）,证明对应uid的用户还没有收藏对应pid的商品
		return true;

    }
	/**
	 * 点击收藏后product表中的collect列的值加1
	 */
	@Override
	public int addProductNum(String pid) {
//		sql：更新了product表中对应pid的商品的collect列的值，让他在原有基础上+1
		String sql = "update product set collect = collect +1 where pid=?";
		return DaoUtil.executeUpdate(sql,pid);
//		int i  = DaoUtil.executeUpdate(sql,pid);
//		返回的是受影响的行数

    }
	/**
	 * 点击收藏夹的删除后  product表中的collect的值减1
	 */
	@Override
	public int reduceProductNum(String pid) {
		String sql="update product set collect = collect -1 where pid = ?";
		return DaoUtil.executeUpdate(sql, pid);
	}

	
}
