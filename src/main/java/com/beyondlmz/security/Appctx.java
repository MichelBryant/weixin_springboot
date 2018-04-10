package com.beyondlmz.security;

import org.springframework.context.ApplicationContext;

/**
 * Created by liumingzhong on 2017/12/19.
 */
public class Appctx {
    public static ApplicationContext ctx=null;
    public static Object getObject(String string){
        return ctx.getBean(string);
    }
}
