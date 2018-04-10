package com.beyondlmz.util;

import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;


public class MD5Util {

    //解密密码�?
    private static final char code[] = {'0', '1', '2', '3', '4', '5','6', '7', '8', '9' };

    /**
     * 校验密码md5
     *
     * @param s
     * @param s1
     * @return boolean
     */
    public static boolean checkPasswordMD5(String s, String s1)
            throws RuntimeException {
        try {
            byte abyte0[] = Base64.decodeBase64(s1.getBytes("UTF8"));
            MessageDigest messagedigest = MessageDigest.getInstance("MD5");
            messagedigest.update(s.getBytes("UTF8"));
            byte abyte1[] = messagedigest.digest();
            return Arrays.equals(abyte1, abyte0);
        } catch (Exception e) {
            throw new RuntimeException("密码加密出现异常!", e);
        }
    }

    /**
     * 创建加密后的密文 MD5加密
     *
     * @param psw -
     *            明文密码
     * @return String
     */
    public static String createEncryptPSW(String psw) throws RuntimeException {
        MessageDigest messagedigest = null;
        try {
            messagedigest = MessageDigest.getInstance("MD5");
            messagedigest.update(psw.getBytes("UTF8"));
            byte abyte0[] = messagedigest.digest();
            return new String(Base64.encodeBase64(abyte0),"UTF8");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("密码加密出现异常!", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("密码加密出现异常!", e);
        }
    }

    public static void main(String[] args) {
        System.out.println(createEncryptPSW("123"));
        Md5_6("4QrcOUm6Wau+VuBX8g+IPg==");
    }

    /**
    * 六位密码破解
    */
    public static String Md5_6(String md5Password) {
        if(md5Password==null || md5Password.equals("")){
            return "获取密码失败";
        }
        String testPassword;
        String result;
        for (int a = 0; a < code.length; a++) {
            testPassword = "";
            testPassword += code[a];
            for (int b = 0; b < code.length; b++) {
                testPassword = testPassword.substring(0, 1);
                testPassword += code[b];
                for (int c = 0; c < code.length; c++) {
                    testPassword = testPassword.substring(0, 2);
                    testPassword += code[c];
                    for (int d = 0; d < code.length; d++) {
                        testPassword = testPassword.substring(0, 3);
                        testPassword += code[d];
                        for (int e = 0; e < code.length; e++) {
                            testPassword = testPassword.substring(0, 4);
                            testPassword += code[e];
                            for (int f = 0; f < code.length; f++) {
                                testPassword = testPassword.substring(0, 5);
                                testPassword += code[f];
                                result = createEncryptPSW(testPassword);
                                if (md5Password.equals(result)) {
                                    return testPassword;
                                }
                            }
                        }
                    }
                }
            }
        }
        return "";
    }
    
    /**
	 * MD5字符串加�?
	 * 
	 * @param resource
	 *            源字符串
	 * @return <tt>String</tt> 加密后的MD5字符�?
	 */
	public static String md5Encryption(String resource) {
		if (resource == null) {
			resource = "null";
		}
		String str = null;
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
			md.update(resource.getBytes("UTF-8"));
			byte b[] = md.digest();
			int i;

			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			// 32位的加密
			 str = buf.toString();

			// 16位的加密
//			str = buf.toString().substring(8, 24);

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str;
	}
	/**
	 * sha256计算.
	 * @param data 待计算的数据
	 * @return 计算结果
	 */
	public static byte[] sha256(byte[] data) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-256");
			md.reset();
			md.update(data);
			return md.digest();
		} catch (Exception e) {
			return null;
		}
	}
}
