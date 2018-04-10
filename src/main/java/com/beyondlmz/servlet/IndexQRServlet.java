package com.beyondlmz.servlet;


import com.beyondlmz.util.QRCodeGenerate;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by liumingzhong on 2017/11/3.
 */
@WebServlet("/index.do")
public class IndexQRServlet extends HttpServlet {
    //微信授权登录回调地址
    @Value(value = "wxCallBackUrl")
    private String backUrl;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // String ewmAddress = "http://"+request.getServerName()+":"+request.getServerPort()+""+request.getContextPath();//当前项目地址
        String ewmAddress=backUrl+request.getContextPath();//扫描二维码回调项目地址
        String forwardActionUrl = "/wxLogin.action";//微信扫码跳转路径
        String  contents="";
        contents=contents+ewmAddress+forwardActionUrl;

        response.setHeader("Content-Disposition", "attachment; filename=" + java.net.URLEncoder.encode("ewm", "UTF-8"));
        response.setContentType("application/octet-stream; charset=utf-8");
        ServletOutputStream os = response.getOutputStream();
        InputStream in = QRCodeGenerate.encode(contents, 150, 150, 0);
        byte abyte0[] = new byte[1024];
        for (int j = 0; (j = in.read(abyte0)) >= 0;) {
            os.write(abyte0, 0, j);
        }
        in.close();
        os.close();
    }
}
