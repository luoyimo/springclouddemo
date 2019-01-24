package com.sc.client2.client2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Enumeration;

@SpringBootApplication
@EnableEurekaClient
@RestController
public class Client2Application {

    public static void main(String[] args) {
        SpringApplication.run(Client2Application.class, args);
    }

    @Value("${server.port}")
    String port;

    @RequestMapping("/hi")
    public String sayHi(@RequestParam String name) {

        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        Enumeration<String> headerNames = servletRequestAttributes.getRequest().getHeaderNames();
        while (headerNames != null && headerNames.hasMoreElements()) {
            System.out.println(headerNames.nextElement().toString());
        }

        return "hi ," + name + "  port:" + port;

    }
}
