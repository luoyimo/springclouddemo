package com.sc.client2.client2;

import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;

/**
 * @Author huqi
 * @Description:
 * @Date Create In 20:06 2018/5/24 0024
 */
public class Listerner implements ApplicationListener<ApplicationStartedEvent> {
    @Override
    public void onApplicationEvent(ApplicationStartedEvent applicationStartedEvent) {
        System.err.println("-----------------------------------------");
    }
}
