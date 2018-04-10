package com.beyondlmz.controller;

import com.beyondlmz.entity.WxUserInfo;
import com.beyondlmz.service.UserService;
import com.beyondlmz.service.WeixinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by liumingzhong on 2017/12/18.
 */

@Controller
@RequestMapping("index")
public class IndexController {

    @Autowired
    private WeixinService weixinService;

   @RequestMapping(value = {"index.action","toIndex.action"})
    public String index(){
        return "index";
    }
    @RequestMapping("shopcart.action")
    public String shopcart(){
        return "shopcart";
    }
    @RequestMapping("classify.action")
    public String classify(){
        return "classify";
    }
    @RequestMapping("mycenter.action")
    public String mycenter(HttpSession session){
        return "mycenter";
    }
    @RequestMapping("weixinSDK.action")
    public String weixinSDK(){
        return "weixinSDK";
    }
}
