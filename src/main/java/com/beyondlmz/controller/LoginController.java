package com.beyondlmz.controller;

import com.beyondlmz.entity.SysUser;
import com.beyondlmz.entity.WxUserInfo;
import com.beyondlmz.service.UserService;
import com.beyondlmz.service.WeixinService;
import com.beyondlmz.util.MD5Util;
import com.beyondlmz.util.QRCodeGenerate;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.InputStream;
import java.util.Date;

/**
 * Created by liumingzhong on 2017/12/15.
 */
@Controller
public class LoginController {
    private Logger logger = LoggerFactory.getLogger(LoggerFactory.class);

    @Autowired
    private UserService userService;
    @Autowired
    private WeixinService weixinService;

    @RequestMapping("/indexQR.action")
    public String index(HttpSession session){
        logger.info("进入 indexQR() 方法");
        String openId = (String) session.getAttribute("openId");
        WxUserInfo wx = weixinService.getWxInfoByOpenId(openId);
        session.setAttribute("wx",wx);
        return "indexQr";
    }

    @RequestMapping("/loginQr.action")
    public void ewmQR(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String ewmAddress = "http://"+request.getServerName()+":"+request.getServerPort()+""+request.getContextPath();//当前项目地址
        String forwardActionUrl = "/wx/wxLogin.action";//微信扫码跳转路径
        StringBuffer  contents=new StringBuffer();
        contents.append(ewmAddress);
        contents.append(forwardActionUrl);
        String content = contents.toString();
        response.setContentType("application/octet-stream; charset=utf-8");
        ServletOutputStream os = response.getOutputStream();
        InputStream in = QRCodeGenerate.encode(content, 150, 150, 0);
        byte abyte0[] = new byte[1024];
        for (int j = 0; (j = in.read(abyte0)) >= 0;) {
            os.write(abyte0, 0, j);
        }
        in.close();
        os.close();
    }
    @RequestMapping(value = {"/login.action","/"})
    public String login(HttpServletRequest request, @Param("username") String username, @Param("password") String password){
        logger.info("进入 login.action 方法");
        SysUser user =  userService.findUserByAccount(username);
        if(user!=null&&user.getPassword().equals(password)){
           request.getSession().setAttribute("user",user);
           return "redirect:/index/index.action";
        }
        return "login";
    }

    @RequestMapping("/register.action")
    @ResponseBody
    public String register(HttpServletRequest request,SysUser user){
        logger.info("进入 register.action 方法");
        SysUser user1 =  userService.findUserByAccount(user.getAccount());
        if(user1!=null){
            return "1";//用户名已存在
        }else{
            user.setPassword(MD5Util.createEncryptPSW(user.getPassword()));
            boolean flag = userService.add(user);
            if(flag){
                request.getSession().setAttribute("user",user);
                return "2";//注册成功，去登陆
            }
            return "3";//注册异常
        }

    }
    @RequestMapping("/logout.action")
    public String logout(HttpServletRequest request){
        logger.info("进入 logout.action 方法");
        request.getSession().removeAttribute("user");
        return "login";
    }
}
