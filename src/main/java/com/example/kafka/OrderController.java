package com.example.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class OrderController {

    @Autowired
    private OrderStream orderStream;

    @GetMapping("order/sendMsg")
    public String sendShopMessage(String content) {
        boolean isSendSuccess = orderStream.sendShopMessage().
                send(MessageBuilder.withPayload(content).build());
        return isSendSuccess ? "发送成功" : "发送失败";
    }

    @StreamListener(OrderStream.ORDER_INPUT)
    public void receive(Message<String> message) {
        System.out.println("order监听到的消息===========" + message.getPayload());
    }
}