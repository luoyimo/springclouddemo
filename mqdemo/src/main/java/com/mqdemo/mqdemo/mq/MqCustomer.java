package com.mqdemo.mqdemo.mq;

import com.rabbitmq.client.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * @Author huqi
 * @Description:
 * @Date Create In 18:47 2018/5/18 0018
 */
@Component
public class MqCustomer {
    @Autowired
    private ConnectionFactory connectionFactory;

    private Channel channel;

    @PostConstruct
    public void init() throws IOException, TimeoutException, InterruptedException {
        channel = connectionFactory.newConnection().createChannel();

    }

    public void test() throws IOException, InterruptedException {

//        QueueingConsumer queueingConsumer = new QueueingConsumer(channel);
//
//        channel.queueDeclare("test", true, false, false, null);
//        channel.queueBind("test", "delay_exchange", "test");
//        channel.basicConsume("test", true, queueingConsumer);
//        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
//        try {
//            System.out.println("****************WAIT***************");
//            while (true) {
//                QueueingConsumer.Delivery delivery = queueingConsumer
//                        .nextDelivery(); //
//
//                String message = (new String(delivery.getBody()));
//                System.out.println("message:" + message);
//                System.out.println("now:\t" + sf.format(new Date()));
//            }
//
//        } catch (Exception exception) {
//            exception.printStackTrace();
//
//        }
//
//    }


        Map<String, Object> args = new HashMap<>();
        args.put("x-delay-type", "direct");
        AMQP.Exchange.DeclareOk delay_exchange = channel.exchangeDeclare("delay_exchange", "x-delayed-message", true, false, args);

        channel.queueDeclare("test", true, false, false, null);


        channel.queueBind("test", "delay_exchange", "test");

        /*
         * 监听队列
         * 参数1:队列名称
         * 参数2：是否发送ack包，不发送ack消息会持续在服务端保存，直到收到ack。  可以通过channel.basicAck手动回复ack
         * 参数3：消费者
         */
//        channel.basicConsume("", true, consumer);
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                System.out.println("receive---" + System.currentTimeMillis());
                String message = new String(body, "UTF-8");
//                String message = (String) ObjectConvert.ByteToObject(body);
                System.out.println("Customer Received '" + message + "'");
            }
        };
        //自动回复队列应答 -- RabbitMQ中的消息确认机制
        channel.basicConsume("test", true, consumer);
    }


}
