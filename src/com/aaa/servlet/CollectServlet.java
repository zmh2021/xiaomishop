package com.aaa.servlet;

import com.aaa.dao.ICollectDAO;
import com.aaa.dao.impl.CollectDaoImpl;
import com.aaa.entity.Collect;
import com.aaa.entity.Result;
import com.alibaba.fastjson.JSON;
import com.sun.corba.se.impl.ior.OldJIDLObjectKeyTemplate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/foo/collect")
public class CollectServlet extends BaseServlet{

	private static final long serialVersionUID = 1L;
	private ICollectDAO dao = new CollectDaoImpl();
	/**
	 * �����ղص�idɾ���ղ�
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void deleteCollect(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//������Ʒid
		String pid = req.getParameter("pid");
		//��session���õ�user��Ϣ
		Map<String,Object> user = (Map<String, Object>) req.getSession().getAttribute("user");
		//�õ�uid
		Object uid = user.get("uid");
		//����Dao�㷽��ɾ���ղص���Ʒ
		int i = dao.deleteOne(pid,String.valueOf(uid));
		//����Dao�㷽��Product���е�collect��ֵ��1
		int reduceProductNum = dao.reduceProductNum(pid);
		//����result��result��һ�����б�ʶ���ķ���ʵ����
		Result result = new Result(20019,"ɾ���ɹ�",null);

		String jsonString = JSON.toJSONString(result);
		resp.getWriter().write(jsonString);
	}
	/**
	 * ��ȡ�ղؼ���Ϣ
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void getCollect(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//��session���ó�user����Ϣ
		Map<String,Object> user = (Map<String, Object>) req.getSession().getAttribute("user");
		//�õ�userid
		Object uid = user.get("uid");
		//����userid�õ���Ӧ���ղؼ��е���Ϣ
		List<Map<String, Object>> data = dao.getUserCollect(uid);
		//�����õ����ղؼ���Ϣ��״̬��
		Result result = new Result(20018,"��ȡ�ɹ�",data);
		//��resultת��Ϊjson�ַ�������ǰ��
		String jsonString = JSON.toJSONString(result);
		resp.getWriter().write(jsonString);
	}

	/**
	 * ����Ʒ����ҳ�棬����ղأ�����Ӧ��Ʒ��Ϣ��ӵ��ղؼ���
	 * �ղ���Ʒ,����û��ղع���������ʧ�ܽ��������û�û���ղع����Ƚ���Ʒ���е���Ʒ���ղ���+1��Ȼ���ٰ���Ʒ�ŵ��û����ղؼ���ȥ
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void collect(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		//�����Ĭ��Ϊ��,�ڴ���������ڴ洢���ǵĴ��������ղؽ����--�ɹ�orʧ�ܣ�
		Result result = null;
		// �������л�ȡ����Ʒ
		//��Ҫ�ղ�һ����Ʒ���Ҿ�Ҫ֪�����ղص����ĸ���Ʒ����������Ҫ�������л�ȡ����Ʒid����Ϊid�����ظ�������Ψһ�ԣ���������ʶ����Ʒ
		String pid = req.getParameter("pid");
		//ʹ��Map�洢��session�õ���user��Ϣ,��������ȷ�������������ĸ��û�����ղ�
		Map<String,Object> user = (Map<String,Object>) req.getSession().getAttribute("user");
		//��map�л�ȡid
		Object uid = user.get("uid");
		//����dao���isExist�����ж���Ʒ�Ƿ��Ѿ����ղؼ���
		//һ�����ڽ����ղز���֮ǰ����Ҫ����dao���isExist�����ж���Ʒ�Ƿ��Ѿ����ղؼ���
		boolean i = dao.isExist(Integer.parseInt(pid),String.valueOf(uid));
		//������ղؼ���
		if(i==false){
			//ֱ�ӷ��ؽ����20021���������ظ���ӡ�,null
			result = new Result(20021,"���Ѿ���ӹ��ˣ��Ƿ�鿴",null);
		}else{
			//�����ղؼ��о�����ղؼУ�����dao��addProductNum����
			int addProductNum = dao.addProductNum(pid);
			//���ǽ���ʵ�����װ��ԭ��������Ҫ���õ�productCollect������Ҫ�����Ǵ���һ��Collectʵ����
			//��pid��uid����collectʵ�����װ
			Collect collect = new Collect(0,Integer.parseInt(pid),String.valueOf(uid));
			//����dao��productCollect���������ղؼе���Ʒ��Ϣ���
			int a= dao.productCollect(collect);
			//result��20017������ӳɹ����Ƿ�鿴�ղؼС���null
			result = new Result(20017,"��ӳɹ�",null);
		}
		//����ǰ��
		String jsonString = JSON.toJSONString(result);
		resp.getWriter().write(jsonString);
	}
}
