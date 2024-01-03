package com.aaa.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;


public class AliyunCodeUtil {
	public static String sendCode(String tel) {
		int code = (int)(Math.random()*10000);
		String host = "https://cxkjsms.market.alicloudapi.com";
	    String path = "/chuangxinsms/dxjk";
	    String method = "POST";
	    String appcode = "961a4cee7bb74278ba426b665fae99b0";
	    Map<String, String> headers = new HashMap<String, String>();
	    //�����header�еĸ�ʽ(�м���Ӣ�Ŀո�)ΪAuthorization:APPCODE 83359fd73fe94948385f570e3c139105
	    headers.put("Authorization", "APPCODE " + appcode);
	    Map<String, String> querys = new HashMap<String, String>();
	    //���Կ���Ĭ�϶���ģ��,����ģ��Ϊר��ģ�岻���޸�,�����Զ���������ݻ�Ķ������ַ�,����ϵ������QQ726980650��������
        querys.put("content", "�����š������֤���ǣ�"+code+"��3��������Ч��");
	    querys.put("mobile", tel);
	    Map<String, String> bodys = new HashMap<String, String>();
	    try {
	    	/**
	    	* ��Ҫ��ʾ����:
	    	* HttpUtils���
	    	* https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
	    	* ����
	    	*
	    	* ��Ӧ�����������
	    	* https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
	    	*/
	    	HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
	    	//��ȡresponse��body
	    	//System.out.println(EntityUtils.toString(response.getEntity()));
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	    return String.valueOf(code);
	}

}
