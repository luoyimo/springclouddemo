package com.sc.feign.feign.service.impl;

import com.sc.feign.feign.service.FeignSayHi;
import org.springframework.stereotype.Component;

//Hystrix
@Component
public class FeignSayHiHystrix implements FeignSayHi {
    @Override
    public String sayHiFromClientOne(String name) {
        return "sorry";
    }
}
