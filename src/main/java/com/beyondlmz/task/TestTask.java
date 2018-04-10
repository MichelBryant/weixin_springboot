package com.beyondlmz.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author liumingzhong
 * @date 2018-4-8
 */
@Component
public class TestTask {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    //@Scheduled(fixedRate = 3000)  //每隔3秒执行一次
    @Scheduled(cron = "10-20 * * * * ?")   //10到20秒执行 http://cron.qqe2.com/
    public void getCurrentTime(){
        System.out.println("当前时间："+dateFormat.format(new Date()));
    }
}
