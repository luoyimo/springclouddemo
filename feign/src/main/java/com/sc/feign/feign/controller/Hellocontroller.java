package com.sc.feign.feign.controller;

import com.sc.feign.feign.service.FeignSayHi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Hellocontroller {
    @Autowired
    private FeignSayHi feignSayHi;

    @RequestMapping("/hi")
    public String sayHi(@RequestParam String name) {
        return feignSayHi.sayHiFromClientOne(name);
    }

}
