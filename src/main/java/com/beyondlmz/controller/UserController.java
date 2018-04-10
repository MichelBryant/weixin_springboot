package com.beyondlmz.controller;

import com.beyondlmz.entity.SysUser;
import com.beyondlmz.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by liumingzhong on 2017/11/24.
 */
@Controller
public class UserController {
    Logger log = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;


    @RequestMapping("index.action")
    public String index(){
        log.info("UserController index.action:方法");
        return "index";
    }
    @RequestMapping("getAllUser.action")
    @ResponseBody
    public List<SysUser> getAllUser(){

        log.info("查询全部用户信息-----");
        return userService.getAllUser();
    }
}
