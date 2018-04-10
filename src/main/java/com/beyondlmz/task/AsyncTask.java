package com.beyondlmz.task;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.concurrent.Future;

/**
 * Created by liumingzhong on 2018-4-8.
 */
@Component
public class AsyncTask {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Async
    public Future<Boolean> doTask1() throws Exception{
        long start = System.currentTimeMillis();
        Thread.sleep(1000);
        long end = System.currentTimeMillis();
        System.out.println("任务1耗时：" +(end - start)+"毫秒");
        return new AsyncResult<>(true);
    }
    @Async
    public Future<Boolean> doTask2() throws Exception{
        long start = System.currentTimeMillis();
        Thread.sleep(700);
        long end = System.currentTimeMillis();
        System.out.println("任务2耗时：" +(end - start)+"毫秒");
        return new AsyncResult<>(true);
    }
    @Async
    public Future<Boolean> doTask3() throws Exception{
        long start = System.currentTimeMillis();
        Thread.sleep(600);
        long end = System.currentTimeMillis();
        System.out.println("任务3耗时：" +(end - start)+"毫秒");
        return new AsyncResult<>(true);
    }

    @Scheduled(cron = "0/10 0/5 * * * ?")
    public void timerTest() throws Exception{
        long nowTime = System.currentTimeMillis();
        System.out.println("现在时间："+dateFormat.format(nowTime));
    }
}
