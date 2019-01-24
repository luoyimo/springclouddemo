package com.sc.robin.robin;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.ribbon.proxy.annotation.Hystrix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HelloService {

    @Autowired
    RestTemplate restTemplate;


    @HystrixCommand(fallbackMethod = "hiErr")
    public String getHi(String name) {
        return restTemplate.getForObject("http://service-hi/hi?name={name}", String.class, name);
    }


    public String hiErr(String name) {
        return "sorry";
    }
}
