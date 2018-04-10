package com.beyondlmz.util;

import java.security.MessageDigest;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;

public class WeixinUtil {
    
	 /***
     * ֧���ӿڵ�ַ
     */
    public static String PAY_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    
    /***
     * ΢��jssdk ǩ��
     * @param jsapi_ticket
     * @param url
     * @return
     * @throws Exception
     */
    public static Map<String, String> sign(String jsapi_ticket, String url)	throws Exception {
        Map<String, String> ret = new HashMap<String, String>();
        //����Ϊ1���ַ������֣�
        String nonce_str = StringUtil.getRandomString(16,"1");
        String timestamp = (System.currentTimeMillis() / 1000) + "";
        String string1;
        String signature = "";

        // ע���������������ȫ��Сд���ұ�������
        string1 = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + nonce_str
                + "&timestamp=" + timestamp + "&url=" + url;

        MessageDigest crypt = MessageDigest.getInstance("SHA-1");
        crypt.reset();
        crypt.update(string1.getBytes("UTF-8"));
        signature = byteToHex(crypt.digest());
        ret.put("url", url);
        ret.put("jsapi_ticket", jsapi_ticket);
        ret.put("nonceStr", nonce_str);
        ret.put("timestamp", timestamp);
        ret.put("signature", signature);

        return ret;
    }
    
    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }
    
}
