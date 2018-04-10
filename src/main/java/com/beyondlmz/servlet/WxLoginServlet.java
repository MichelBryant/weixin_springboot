package com.beyondlmz.servlet;

import com.beyondlmz.util.AuthorUtil;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;

import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author liumingzhong
 */
@WebServlet("/wxLogin.do")
public class WxLoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	//微信授权登录回调地址
	@Value(value = "${wxCallBackUrl}")
	private String backUrl;


	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		backUrl +="/weixin_springboot/callBack";
		String url="https://open.weixin.qq.com/connect/oauth2/authorize?appid="+ AuthorUtil.APPID
				+ "&redirect_uri="+URLEncoder.encode(backUrl)
				+ "&response_type=code"
				+ "&scope=snsapi_userinfo"
				+ "&state=STATE#wechat_redirect ";
		System.out.println("微信回调地址:"+url);
		resp.sendRedirect(url);
	}

}
