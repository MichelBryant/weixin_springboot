package com.beyondlmz.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;




/**
 * @author liumingzhong
 */
@Component("httpUtil")
public class HttpRequestUtil {

	private final static Logger logger = LoggerFactory.getLogger(HttpRequestUtil.class);
	

	
	/***
	 * 从request中获取xml
	 * 
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	public static String getXML(HttpServletRequest request) throws Exception {
		String xmlStr = "";
			InputStream is = request.getInputStream();
			// 取HTTP请求流长度
			int size = request.getContentLength();// 用于缓存每次读取的数据
			byte[] buffer = new byte[size];// 用于存放结果的数组
			byte[] xmldataByte = new byte[size];
			int count = 0;
			int rbyte = 0;
			// 循环读取
			while (count < size) {// 每次实际读取长度存于rbyte中
				rbyte = is.read(buffer);
				for (int i = 0; i < rbyte; i++) {
					xmldataByte[count + i] = buffer[i];
				}
				count += rbyte;
			}
			is.close();
			xmlStr = new String(xmldataByte, "UTF-8");
		return xmlStr;
	}
	

}



