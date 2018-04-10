package com.beyondlmz.controller;

import com.beyondlmz.util.AuthorUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import java.io.IOException;
import java.net.URLEncoder;

/**
 *
 * @author liumingzhong
 * @date 2018-4-9
 */
@Controller
public class WxController {

    //微信授权登录回调地址
    @Value("${wxCallBackUrl}")
    private String backUrl;

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


}
