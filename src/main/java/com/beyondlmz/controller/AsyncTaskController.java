package com.beyondlmz.controller;

import com.beyondlmz.task.AsyncTask;
import com.beyondlmz.task.TestTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Future;

/**
 *
 * @author liumingzhong
 * @date 2018-4-8
 */
@RestController
public class AsyncTaskController {

    @Autowired
    private AsyncTask asyncTask;


    @RequestMapping("asyncTest1")
    public String asyncTaskTest() throws Exception{
     long start = System.currentTimeMillis();
        Future<Boolean> a = asyncTask.doTask1();
        Future<Boolean> b= asyncTask.doTask2();
        Future<Boolean> c = asyncTask.doTask3();
        while(!a.isDone() || !b.isDone() || !c.isDone()){
            if(a.isDone() && b.isDone() && c.isDone()){
                break;
            }
        }
        long end = System.currentTimeMillis();
        String times = "任务全部完成"+(end-start)+"毫秒";
        return times;
    }
}
