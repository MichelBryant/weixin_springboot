package com.beyondlmz.aspect;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by liumingzhong on 2017/12/15.
 */
@Aspect
@Component
public class HttpAspectTool {
    Logger logger = LoggerFactory.getLogger(HttpAspectTool.class);
    @Pointcut("execution(public * com.beyondlmz.controller.UserController.*(..))")
    public void log(){
        logger.info("HttpAspectTool Pointcut：进入Pointcut");
    }
    @Before("log()")
    public void doBefore(){
        logger.info("HttpAspectTool Before：进入Before");
    }
   @After("log()")
    public void doAfter(){
       logger.info("HttpAspectTool After：进入After");
   }
}
