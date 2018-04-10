package com.beyondlmz.util;

import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import java.net.URL;




public class AuthorUtil {
	     public static final String APPID="wx713278433908fbc7";
	     public static final String APPSECRET="1291293cb32ae3f3a5f6b7dad1d5c6ef";
    
	public static JSONObject doGetJson(String u, String requestMethod){
		JSONObject jsonObject = null;
		StringBuffer buffer = new StringBuffer();
		try{
			 URL url = new URL(u);
	            // httpЭ�鴫��
	            HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();

	            httpUrlConn.setDoOutput(true);
	            httpUrlConn.setDoInput(true);
	            httpUrlConn.setUseCaches(false);
	            // ��������ʽ��GET/POST��
	            httpUrlConn.setRequestMethod(requestMethod);

	            if ("GET".equalsIgnoreCase(requestMethod))
	                httpUrlConn.connect();
	            // �����ص�������ת�����ַ���
	            InputStream inputStream = httpUrlConn.getInputStream();
	            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
	            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

	            String str = null;
	            while ((str = bufferedReader.readLine()) != null) {
	                buffer.append(str);
	            }
	            bufferedReader.close();
	            inputStreamReader.close();
	            // �ͷ���Դ
	            inputStream.close();
	            inputStream = null;
	            httpUrlConn.disconnect();
	            jsonObject = JSONObject.fromObject(buffer.toString());
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return jsonObject;
	}
}
