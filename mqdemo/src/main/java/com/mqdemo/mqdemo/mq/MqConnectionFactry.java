package com.mqdemo.mqdemo.mq;

import com.rabbitmq.client.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author huqi
 * @Description:
 * @Date Create In 18:09 2018/5/18 0018
 */
@Configuration
public class MqConnectionFactry {

    @Bean
    public ConnectionFactory connectionFactory() {
        ConnectionFactory connectionFactory = new ConnectionFactory();
//        connectionFactory.setPassword("seego2017");
//        connectionFactory.setHost("119.29.202.225");
//        connectionFactory.setVirtualHost("/product");
//        connectionFactory.setUsername("javabackend");
        return connectionFactory;
    }


}
