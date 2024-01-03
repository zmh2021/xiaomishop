package com.aaa.servlet;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import com.aaa.dao.IProductDAO;
//import com.aaa.dao.impl.ProductDAOImpl;
import com.aaa.dao.IProductDAO;
import com.aaa.dao.impl.ProductDAOImpl;
import com.aaa.entity.Result;
import com.alibaba.fastjson.JSON;
//���ǵ���Ŀ��ǰ�˷��������·���У�exploded֮ǰ���ǹ̶��ģ�exploded֮��  ��֮ǰ�����ݣ��������ǵ�ǰ�˷��͵�����ȷ��servelet������
//http://localhost:8080/xiaomishop_war_exploded/pro?m=getProduct&id=55
//������󡣷���֮�󵽴��̨��Ѱ��·����Ϊpro��servlet������ö�Ӧservlet�е�getProduct������������Я���Ĳ���Ϊid������ֵΪ55
@WebServlet("/pro")
public class ProductServlet extends BaseServlet {

	Result result = null;

	private static final long serialVersionUID = 1L;
	private IProductDAO dao = new ProductDAOImpl();

	/**
	 * ��Ʒ����
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void jacket(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String cid = req.getParameter("cid");
		List<Map<String, Object>> jacket = dao.getJacket(Integer.parseInt(cid));
		Result result = new Result(20008, "��ȡ������Ϣ�ɹ�", jacket);
		String jsonString = JSON.toJSONString(result);
		resp.getWriter().write(jsonString);
	}

	/**
	 * ����Я������Ϊid=55����Ʒid���������͵���̨�����ô˷�������Ϊ���û������Ӧ����ƷͼƬ֮����ҳ������ʾ��Ӧ��Ʒ����Ϣ
	 * �����͵���̨��Ӧ��servlet�����ö�Ӧ�ķ�������Ҫ�����ݿ��е�product���в�ѯ����Ӧ��Ʒ��������Ϣ��������ǰ��
	 * ����Ҫ��ȡ����Ӧ��Ʒ����Ϣ������Ҫ��Ӧ��Ʒ��id����Ϊ��Ʒid����Ψһ�ԣ������ֲ�ͬ��ƷЧ����ߵ����ݣ�
	 * ��Ʒid���������л�ȡ
	 * ����id��Ʒ����
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void getProduct(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//����Ҫ������Ʒid��ȡ���ڵ���Ʒ��Ϣ����Ʒid�������У����Ǿʹ������л�ȡidֵ������Ʒid��ǰ��ת����ǰ��
		String sid = req.getParameter("id");
		//ת��int���ͣ������Ǵ��������õ�����Ʒid�����ݸ�ʽ��Stringת��Ϊint
		//String.valueof	int=>Integer
		int id = Integer.parseInt(sid);
		//����dao��getProductById���� ͨ�� ��Ʒid ��ȡ��Ʒ��ϸ��Ϣ
		Map<String,Object> map = dao.getProductByID(id);
		//���map==null,֤�����ǵ�impl��ʲô��û�в鵽
		if(map == null){
			//result,20002,"�Բ�����Ʒ��ȡʧ��"��null
			result = new Result(20002,"�Բ��� ��Ʒ��ȡʧ��",null);
		}else{
			//result,20003,"��Ʒ��ȡ�ɹ�����map
			result = new Result(20003,"��Ʒ��ȡ�ɹ�",map);
		}
		//����ǰ��
		String jsonString = JSON.toJSONString(result);
		resp.getWriter().write(jsonString);
	}
	/**
	 * ��ȡȫ����Ʒ��Ϣ
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void home(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 1 ��ȡ������� ÿҳ��ʾʮ��
		String page = req.getParameter("page");
		// name ��ȡ��ѯ��Ϣ
		String name = req.getParameter("name");
		// 2 ����page��ѯ��ǰҳ����
		Map<String, Object> data = dao.getProduct(page, name);
		if (data != null) {
			result = new Result(20001, "��ȡ��ҳ��Ϣ�ɹ�", data);
		} else {
			result = new Result(20002, "��ȡ��ҳ��Ϣʧ��", data);
		}
		String jsonString = JSON.toJSONString(result);
		resp.getWriter().write(jsonString);
	}
}
