package com.beyondlmz.controller;

import com.beyondlmz.entity.WxUserInfo;
import com.beyondlmz.service.WeixinService;
import com.beyondlmz.util.AuthorUtil;
import com.beyondlmz.util.HttpRequestUtil;
import com.beyondlmz.util.WeixinUtil;
import net.sf.json.JSONObject;
import org.apache.ibatis.reflection.ExceptionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 *
 * @author liumingzhong
 * @date 2018-4-9
 */
@Controller
@RequestMapping("wx")
public class WxController {

    private Logger logger = LoggerFactory.getLogger(WxController.class);

    //微信授权登录回调地址
    @Value("${wxCallBackUrl}")
    private String backUrl;

    @Value("${wxGetTokenUrl}")
    private String tokenUrlTemp;

    @Value("${wxGetJsApiTicketUrl}")
    private String ticketUrlTemp;

    @Autowired
    private WeixinService weixinService;
    /**
     * 微信授权登录
     * @return
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping("wxLogin.action")
    public String callBackUrl() throws ServletException, IOException
    {
        backUrl +="/wxCallBack.action";
        String url="https://open.weixin.qq.com/connect/oauth2/authorize?appid="+ AuthorUtil.APPID
                + "&redirect_uri="+ URLEncoder.encode(backUrl)
                + "&response_type=code"
                + "&scope=snsapi_userinfo"
                + "&state=STATE#wechat_redirect ";
        System.out.println("微信回调地址:"+url);
        return "redirect:/"+url;
    }

    @RequestMapping("toPay.action")
    public String toPay(){
        return "payment";
    }

    @RequestMapping("jsInit.action")
    @ResponseBody
    public Map<String, String> jsInit(String config_url) {
        Map<String, String> map = new HashMap<String, String>();
        try{
            //1.获取token
            String tokenUrl=tokenUrlTemp + "&appid="+ AuthorUtil.APPID
                    + "&secret="+AuthorUtil.APPSECRET;
            JSONObject tokenInfo = AuthorUtil.doGetJson(tokenUrl,"GET");
            String token = tokenInfo.getString("access_token");
            //2.获取jsapi_ticket(票据)
            String ticketUrl = ticketUrlTemp+"?access_token="+token
                    + "&type=jsapi";
            JSONObject ticketInfo = AuthorUtil.doGetJson(ticketUrl,"GET");
            String jsapi_ticket = ticketInfo.getString("ticket");
            //session存储token和jsapi_ticket
            System.out.println("获取的access_token为："+token);
            System.out.println("获取的jsapi_ticket为："+jsapi_ticket);

            map = WeixinUtil.sign(jsapi_ticket, config_url);
            map.put("appid", AuthorUtil.APPID);
            System.out.println("js-sdk初始化参数"+map);
        }catch(Exception e){
            e.printStackTrace();
        }
        return map;
    }

    @RequestMapping("pay.action")
    @ResponseBody
    public SortedMap<String, String> pay(HttpServletRequest request) {
        SortedMap<String, String> map=new TreeMap<String, String>();
        Long tradeId = 201845362319L;
        try {
            String openId="oYkFlw8XprAQRTWS9vDVbaDQYc0Q";
            WxUserInfo wx = weixinService.getWxInfoByOpenId(openId);
            map =  weixinService.wxPay(tradeId,openId);
            request.setAttribute("wx",wx);
        } catch (Exception e) {
            logger.info(e.toString());
        }
        return map;
    }
    @RequestMapping(value = "/pay_notify", method = {RequestMethod.POST })
    @ResponseBody
    public void pay_notify(HttpServletRequest request,HttpServletResponse response) {
        try {
            String xml= HttpRequestUtil.getXML(request);
            PrintWriter out=response.getWriter();
            out.write("<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>");
            out.flush();
            out.close();
            weixinService.savePayNotify(xml);
        } catch (Exception e) {
            logger.error(e.toString());
        }
    }

}
