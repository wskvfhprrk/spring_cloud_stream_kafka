package com.example.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ShopController {

    @Autowired
    private ShopStream shopStream;

    @GetMapping("shop/sendMsg")
    public String sendShopMessage(String content) {
        System.out.println("sendMsg接收到消息");
        boolean isSendSuccess = shopStream.sendShopMessage().
                send(MessageBuilder.withPayload(content).build());
        return isSendSuccess ? "发送成功" : "发送失败";
    }

    @StreamListener(ShopStream.SHOP_INPUT)
    public void receive(Message<String> message) {
        System.out.println("shop监听到的消息===========" + message.getPayload());
    }
}