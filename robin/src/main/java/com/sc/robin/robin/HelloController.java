package com.sc.robin.robin;

import com.netflix.ribbon.proxy.annotation.Hystrix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class HelloController {

    @Autowired
  HelloService helloService;


    @RequestMapping("/hi")
    public String getHi(@RequestParam String name){
        return helloService.getHi(name);
    }


}
