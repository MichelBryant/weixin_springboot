package com.beyondlmz.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * @ClassName: EncryptUtil.java
 * @Description: TODO(用一句话描述该文件做什么)
 *
 * @author lipeng
 * @version V1.0
 * @email lipeng@zjian.net
 * @Date 2015年10月29日 下午2:41:11
 */
public class EncryptUtil {

    private byte[] desKey;

    public EncryptUtil(String desKey)
    {
        this.desKey = desKey.getBytes();
    }

    /***
     * sha1加密
     *
     * @param decript
     * @return
     * @throws Exception
     */
    public static String SHA1(String decript) throws Exception{
        MessageDigest digest = MessageDigest.getInstance("SHA-1");
        digest.update(decript.getBytes());
        byte messageDigest[] = digest.digest();
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < messageDigest.length; i++) {
            String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
            if (shaHex.length() < 2) {
                hexString.append(0);
            }
            hexString.append(shaHex);
        }
        return hexString.toString();
    }



    /***
     * md5加密
     * @param str
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String MD5(String str) throws NoSuchAlgorithmException {
        StringBuffer buf = new StringBuffer("");
        if (str == null || "".equals(str)) {
            return null;
        }
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(str.getBytes());// update处理
        byte[] encryContext = md.digest();// 调用该方法完成计算
        int i;
        for (int offset = 0; offset < encryContext.length; offset++) {// 做相应的转化（十六进制）
            i = encryContext[offset];
            if (i < 0)
                i += 256;
            if (i < 16)
                buf.append("0");
            buf.append(Integer.toHexString(i));
        }

        return buf.toString().toUpperCase();

    }


    public byte[] desEncrypt(byte[] plainText) throws Exception {
        SecureRandom sr = new SecureRandom();
        byte rawKeyData[] = desKey;
        DESKeySpec dks = new DESKeySpec(rawKeyData);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey key = keyFactory.generateSecret(dks);
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.ENCRYPT_MODE, key, sr);
        byte data[] = plainText;
        byte encryptedData[] = cipher.doFinal(data);
        return encryptedData;
    }

    public byte[] desDecrypt(byte[] encryptText) throws Exception {
        SecureRandom sr = new SecureRandom();
        byte rawKeyData[] = desKey;
        DESKeySpec dks = new DESKeySpec(rawKeyData);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey key = keyFactory.generateSecret(dks);
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.DECRYPT_MODE, key, sr);
        byte encryptedData[] = encryptText;
        byte decryptedData[] = cipher.doFinal(encryptedData);
        return decryptedData;
    }

    public static String base64Encode(byte[] s) {
        if (s == null)
            return null;
        BASE64Encoder b = new BASE64Encoder();
        return b.encode(s);
    }

    public static byte[] base64Decode(String s) throws IOException  {
        if (s == null)
            return null;
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] b = decoder.decodeBuffer(s);
        return b;
    }
    public String encrypt(String input) throws Exception {
        return base64Encode(desEncrypt(input.getBytes()));
    }

    public String decrypt(String input) throws Exception {
        byte[] result = base64Decode(input);
        return new String(desDecrypt(result));
    }

}
