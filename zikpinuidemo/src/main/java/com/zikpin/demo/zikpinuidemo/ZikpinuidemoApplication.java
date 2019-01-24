package com.zikpin.demo.zikpinuidemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import zipkin.server.internal.EnableZipkinServer;

@SpringBootApplication
@EnableZipkinServer
public class ZikpinuidemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZikpinuidemoApplication.class, args);
    }
}
