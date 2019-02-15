package com.example.kafka;

import com.sun.org.apache.xpath.internal.operations.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class OrderController {

    @Autowired
    private OrderStream orderStream;

    @GetMapping("order/sendMsg")
    public String sendShopMessage(String content) {
        Order order=new Order();
        order.setOrderName("order1");
        order.setOrderId(1);
        boolean isSendSuccess = orderStream.sendShopMessage().
                send(MessageBuilder.withPayload(order).build());
        return isSendSuccess ? "发送成功" : "发送失败";
    }


    @StreamListener(OrderStream.ORDER_INPUT)
    @SendTo(OrderStream.ORDER_BACK_OUTPUT)
    public String receive(Message<String> message) {
        System.out.println("order监听到的消息===========" + message.getPayload());
        //使用@SendTo时方法必须有返回值，否则报错
        return "ok";
    }
    @StreamListener(OrderStream.ORDER_BACK_INPUT)
    public void back(Message<String> message) {
        System.out.println("order监听ORDER_BACK到的消息===========" + message.getPayload());
    }
}