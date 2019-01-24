package com.mqdemo.mqdemo.controller;

import com.mqdemo.mqdemo.mq.MqCustomer;
import com.mqdemo.mqdemo.mq.MqProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @Author huqi
 * @Description:
 * @Date Create In 18:30 2018/5/18 0018
 */
@RestController
public class MqController {
    @Autowired
    private MqProducer mqProducer;

    @Autowired
    private MqCustomer mqCustomer;


    @RequestMapping(value = "/hello")
    public String hello() throws IOException, InterruptedException {
        mqProducer.sendDelayMessage("test", "hello", 1728000000);
        mqCustomer.test();
        return "hello";
    }


}
