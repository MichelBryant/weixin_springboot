package com.beyondlmz.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.beyondlmz.util.AuthorUtil;
import com.beyondlmz.util.WeixinUtil;
import net.sf.json.JSONObject;


/**
 * @author liumingzhong
 */
public class WeixinTokenServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		HttpSession session = req.getSession();
		try{
			//1.获取token
			String tokenUrl="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential"
					+ "&appid="+ AuthorUtil.APPID
					+ "&secret="+AuthorUtil.APPSECRET;
			JSONObject tokenInfo = AuthorUtil.doGetJson(tokenUrl,"GET");
			String token = tokenInfo.getString("access_token");
			//2.获取jsapi_ticket(票据)
			String ticketUrl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+token
					+ "&type=jsapi";
			JSONObject ticketInfo = AuthorUtil.doGetJson(ticketUrl,"GET");
			String jsapi_ticket = ticketInfo.getString("ticket");
			//session存储token和jsapi_ticket
			System.out.println("获取的access_token为："+token);
			System.out.println("获取的jsapi_ticket为："+jsapi_ticket);
			session.setAttribute("access_token", token);
			session.setAttribute("jsapi_ticket", jsapi_ticket);

			String config_url=req.getParameter("config_url");
			Map<String,String> map = new HashMap<String,String>();
			//
			map = WeixinUtil.sign(jsapi_ticket, config_url);
			map.put("appid", AuthorUtil.APPID);
			session.setAttribute("map", map);
			System.out.println("js-sdk初始化参数"+map);
			req.getRequestDispatcher("/index/weixinSDK.action").forward(req, resp);
			//		out.println(map);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doPost(req,resp);
	}
}
