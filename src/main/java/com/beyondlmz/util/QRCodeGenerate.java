package com.beyondlmz.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.UUID;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.stream.ImageOutputStream;
import javax.imageio.ImageIO;

/**
 * Created by IntelliJ IDEA.
 * User: chentian
 * Date: 2012-3-8
 * Time: 14:42:27
 * To change this template use File | Settings | File Templates.
 */
public class QRCodeGenerate {

    /**
     * ����
     *
     * @param contents
     */
    public synchronized static InputStream encode(String contents) {
        Hashtable<Object, Object> hints = new Hashtable<Object, Object>();
        int BLACK = 0xff000000;
        int WHITE = 0xFFFFFFFF;
        // ָ������ȼ�
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        // ָ�������ʽ
        hints.put(EncodeHintType.CHARACTER_SET, "GBK");
        ImageOutputStream imOut;
        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(contents,
                    BarcodeFormat.QR_CODE, 141, 141, hints);
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            BufferedImage image = new BufferedImage(width - 40, height - 40, BufferedImage.TYPE_INT_ARGB);
            for (int x = 20; x < width - 20; x++) {
                for (int y = 20; y < height - 20; y++) {
                    image.setRGB(x - 20, y - 20, bitMatrix.get(x, y) ? BLACK : WHITE);
                }
            }
            image.flush();
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            imOut = ImageIO.createImageOutputStream(os);
            ImageIO.write(image, "png", imOut);
//            MatrixToImageWriter.writeToStream(bitMatrix, "png", os);
            InputStream is = new ByteArrayInputStream(os.toByteArray());
            return is;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

     public synchronized static InputStream encode(String content, int width, int height,int padding) {

        int BLACK = 0xff000000;
        int WHITE = 0xFFFFFFFF;

        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
        // ���ñ�������ΪGBK
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        // ���ö�ά�������������ΪL����ͣ�
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        BitMatrix byteMatrix = null;
        ImageOutputStream imOut;
        
        try {
            // ��ɶ�ά��
            byteMatrix = new MultiFormatWriter().encode(content,
                    BarcodeFormat.QR_CODE, width, height, hints);
            width = byteMatrix.getWidth();
            height = byteMatrix.getHeight();
            int startX = 0;
            int startY = 0;
            boolean exitFlag=false;
            for (int y = 0; y < height; y++) {
                if(exitFlag)
                    break;
                for (int x = 0; x < width; x++) {
                    if (byteMatrix.get(x, y)) {
                        startX = x;
                        startY = y;
                        exitFlag=true;
                        break;
                    }
                }
            }
            int x1=0;
            int y1=0;
            if((startX-padding>0)&&(startY-padding>0)){
                x1=startX-padding;
                y1=startY-padding;
            }
            BufferedImage image = new BufferedImage(width - x1*2, height - y1*2, BufferedImage.TYPE_INT_ARGB);
            for (int x = x1; x < width - x1; x++) {
                for (int y = y1; y < height - y1; y++) {
                    image.setRGB(x - x1, y - y1, byteMatrix.get(x, y) ? BLACK : WHITE);
                }
            }
            
            image.flush();
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            imOut = ImageIO.createImageOutputStream(os);
            ImageIO.write(image, "png", imOut);
            InputStream is = new ByteArrayInputStream(os.toByteArray());
            os.flush();
            os.close();
            return is;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
     public synchronized static InputStream encode(String content, int width, int height,int padding,String path) {

         int BLACK = 0xff000000;
         int WHITE = 0xFFFFFFFF;

         Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
         // ���ñ�������ΪGBK
         hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
         // ���ö�ά�������������ΪL����ͣ�
         hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
         BitMatrix byteMatrix = null;
         ImageOutputStream imOut;
         
         try {
             // ��ɶ�ά��
             byteMatrix = new MultiFormatWriter().encode(content,
                     BarcodeFormat.QR_CODE, width, height, hints);
             width = byteMatrix.getWidth();
             height = byteMatrix.getHeight();
             int startX = 0;
             int startY = 0;
             boolean exitFlag=false;
             for (int y = 0; y < height; y++) {
                 if(exitFlag)
                     break;
                 for (int x = 0; x < width; x++) {
                     if (byteMatrix.get(x, y)) {
                         startX = x;
                         startY = y;
                         exitFlag=true;
                         break;
                     }
                 }
             }
             int x1=0;
             int y1=0;
             if((startX-padding>0)&&(startY-padding>0)){
                 x1=startX-padding;
                 y1=startY-padding;
             }
             BufferedImage image = new BufferedImage(width - x1*2, height - y1*2, BufferedImage.TYPE_INT_ARGB);
             for (int x = x1; x < width - x1; x++) {
                 for (int y = y1; y < height - y1; y++) {
                     image.setRGB(x - x1, y - y1, byteMatrix.get(x, y) ? BLACK : WHITE);
                 }
             }
             
             image.flush();
           //-------------------添加logo开始----------------------//
             Graphics2D gs = image.createGraphics();  
             BufferedImage img = ImageIO.read(new File(path));


             gs.drawImage(img.getScaledInstance(40, 40,java.awt.Image.SCALE_SMOOTH), 75, 80, null);//对图片进行缩放

            gs.dispose();  

            image.flush();
            //-------------------添加logo结束----------------------//
             ByteArrayOutputStream os = new ByteArrayOutputStream();
             imOut = ImageIO.createImageOutputStream(os);
             ImageIO.write(image, "png", imOut);
             InputStream is = new ByteArrayInputStream(os.toByteArray());
             os.flush();
             os.close();
             return is;
         }catch (Exception e) {
             e.printStackTrace();
         }
         return null;
     }

    /**
     * ���Ψһ��UUID
     *
     * @return string;
     */
    public synchronized static String getQRCode() {
        String uuid = UUID.randomUUID().toString();
        return uuid;
    }
}