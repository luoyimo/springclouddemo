package com.mqdemo.mqdemo.mq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mqdemo.mqdemo.util.JsonHelper;
import com.rabbitmq.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author huqi
 * @Description:
 * @Date Create In 18:19 2018/5/18 0018
 */
@Component
@EnableAsync
public class MqProducer {


    private static Logger logger = LoggerFactory.getLogger(MqProducer.class);
    private final static long DELAY_LIMIT_PERIOD = 20 * 24 * 60 * 60L;

    @Autowired
    private ConnectionFactory connectFactory;


    private Connection connection;

    @PostConstruct
    public void postConstruct() throws Exception {
        connection = connectFactory.newConnection();
    }

    /**
     * 发送消息方法主体，默认异步发送消息
     *
     * @param objectJson
     * @return
     */
    @Async
    public void send(String taskQueueName, String objectJson) {

        Channel channel = null;
        try {
            channel = connection.createChannel();
            channel.queueDeclare(taskQueueName, true, false, false, null);
            channel.basicPublish("", taskQueueName, MessageProperties.PERSISTENT_TEXT_PLAIN, objectJson.getBytes());
        } catch (Exception e) {
            logger.error("消息发送失败", e);
        } finally {
            closeChannel(channel);
        }
    }

    @Async
    public void sendDelayMessage(String taskQueueName, String objectJson, long delayTimeInSecond) {

        Channel channel = null;
        try {
            channel = connection.createChannel();
            channel.queueDeclare(taskQueueName, true, false, false, null);
            byte[] messageBodyBytes = objectJson.getBytes("UTF-8");
            Map<String, Object> headers = new HashMap<String, Object>();
            headers.put("x-delay", 1000 * delayTimeInSecond);
            AMQP.BasicProperties.Builder props = new AMQP.BasicProperties.Builder().headers(headers);
            channel.queueDeclare(taskQueueName, true, false, false, null);
            channel.basicPublish("delay_exchange", taskQueueName, props.build(), messageBodyBytes);
            System.out.println("send---"+System.currentTimeMillis());
        } catch (Exception e) {
            logger.error("消息发送失败", e);
        } finally {
            closeChannel(channel);
        }
    }

    private void closeChannel(Channel channel) {

        if (channel != null) {
            try {
                channel.close();
            } catch (Exception e) {
                logger.error("关闭channel失败", e);
            }
        }
    }

    /**
     * 发送消息方法，可以直接传Bean
     *
     * @param taskQueueName
     * @param data
     * @param <T>
     * @return
     */
    public <T> void send(String taskQueueName, T data) {
        String objectJson = "";
        //如果是基本类型，直接处理
        if (data.getClass().isPrimitive()) {
            objectJson = data.toString();
            send(taskQueueName, objectJson);
        } else {
            try {
                objectJson = JsonHelper.OM.writeValueAsString(data);
                send(taskQueueName, objectJson);
            } catch (JsonProcessingException e) {
                logger.error("对象转json失败", e);
            }
        }
    }

    /**
     * 发送延迟消息方法，可以直接传Bean
     *
     * @param data
     * @param <T>
     * @return
     */
    public <T> void sendDelayMessage(String routingKey, T data, long delayTimeInSecond) {
        if (delayTimeInSecond >= DELAY_LIMIT_PERIOD) {
            throw new IllegalArgumentException("设置时间距离现在过久，不得超过20天");
        }
        String objectJson = "";
        //如果是基本类型，直接处理
        if (data.getClass().isPrimitive()) {
            objectJson = data.toString();
            sendDelayMessage(routingKey, objectJson, delayTimeInSecond);
        } else {
            try {
                objectJson = JsonHelper.OM.writeValueAsString(data);
                sendDelayMessage(routingKey, objectJson, delayTimeInSecond);
            } catch (JsonProcessingException e) {
                logger.error("对象转json失败", e);
            }
        }
    }


}
