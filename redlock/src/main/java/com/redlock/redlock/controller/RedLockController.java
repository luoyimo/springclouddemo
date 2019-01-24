package com.redlock.redlock.controller;

import com.redlock.redlock.cache.RedisLocker;
import com.redlock.redlock.service.AquiredLockWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * @Author huqi
 * @Description:
 * @Date Create In 15:27 2018/5/17 0017
 */
@RestController
@RequestMapping("/test")
public class RedLockController {


    volatile
    @Autowired
    RedisLocker distributedLocker;

    @RequestMapping(value = "/redlock")
    public String testRedlock() throws Exception {

        System.out.println("----");

        CountDownLatch startSignal = new CountDownLatch(1);
        CountDownLatch doneSignal = new CountDownLatch(5);
        for (int i = 0; i < 5; ++i) { // create and start threads
            new Thread(() -> {
                try {
                    startSignal.await();
                    distributedLocker.lock("test", () -> {
                        doTask(doneSignal);
                        return null;
                    });
                } catch (Exception e) {
                    System.out.println(e);
                }
            }).start();
        }
        startSignal.countDown(); // let all threads proceed
        System.out.println(doneSignal.getCount());
        doneSignal.await();
        System.out.println(doneSignal.getCount());
        System.out.println("All processors done. Shutdown connection");
        return "redlock";
    }

    void doTask(CountDownLatch doneSignal) {
        System.out.println(Thread.currentThread().getName() + " start");
        Random random = new Random();
        int _int = random.nextInt(200);
        System.out.println(Thread.currentThread().getName() + " sleep " + _int + "millis");
        try {
            Thread.sleep(_int);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " end");
        doneSignal.countDown();
    }
}
