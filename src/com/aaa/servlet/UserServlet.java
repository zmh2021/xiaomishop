package com.aaa.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ObjectUtils.Null;

import com.aaa.dao.IUserDao;
import com.aaa.dao.impl.UserDAOImpl;
import com.aaa.entity.Result;
import com.aaa.entity.User;
import com.aaa.util.AliyunCodeUtil;
import com.alibaba.fastjson.JSON;

@WebServlet("/user")
public class UserServlet extends BaseServlet {
	private IUserDao dao = new UserDAOImpl();
	/**
	 * �޸�����
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void changePsw(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}
	/**
	 * ע��
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void logout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}
	/**
	 * ��ȡ�û���Ϣ
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void getUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//��session�л�ȡ�û���¼��Ϣ

		//����״̬��200010��msg��ȡ�ɹ��������û���Ϣ
		Result result = new Result(200010,"�û���Ϣ��ȡ�ɹ�",req.getSession().getAttribute("user"));
		//ת��json
		String json = JSON.toJSONString(result);
		//����ǰ��
		resp.getWriter().write(json);
	}
	/**
	 * ��¼ҵ��
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void login(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//����ս����
		Result result = new Result();
		//��ȡtelephone
		String telephone = req.getParameter("telephone");
		//��ȡpassword
		String password = req.getParameter("password");
		//����dao��isLogin��������֤�û�������û����������Ƿ������ݿ��д���
		Map<String,Object> user = dao.isLogin(telephone,password);
		if(user == null){
			//����״̬��20008��msg�˺�������󣬸�������Ϣ��
			System.out.println("��¼ʧ��");
		}
		else{
			//����ѯ�����û���Ϣ����session,KV,"user",user
			req.getSession().setAttribute("user",user);
			//����״̬��20009��msg��¼�ɹ�����������Ϣ
			result = new Result(20009,"��½�ɹ�",null);
		}
		//�����ת��json
		String jsonString = JSON.toJSONString(result);
		//����ǰ��
		resp.getWriter().write(jsonString);
	}
	/**
	 * ������֤��
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void reg(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}
	/**
	 * ��֤��
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void sendCode(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}
}
