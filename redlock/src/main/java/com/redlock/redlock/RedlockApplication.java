package com.redlock.redlock;

import com.redlock.redlock.cache.RedisLocker;
import com.redlock.redlock.service.AquiredLockWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

@SpringBootApplication
@RestController
public class RedlockApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedlockApplication.class, args);
    }


}
