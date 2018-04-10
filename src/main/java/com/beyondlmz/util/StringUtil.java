package com.beyondlmz.util;

import java.util.Random;

public class StringUtil {

	/***
	 * ��ȡ����ַ���
	 * 
	 * @param length
	 * type 1�ַ�+���֣�2���ָ�ʽ
	 * @return
	 */
	public static String getRandomString(int length,String type) { // length��ʾ�����ַ����ĳ���
		String base = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		if("2".equals(type)){
			base = "0123456789";
		}
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}
}
