package com.example.kafka;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface OrderStream {

    /**
     * 发消息的通道名称
     */
    String ORDER_OUTPUT = "order_output";

    /**
     * 消息的订阅通道名称
     */
    String ORDER_INPUT = "order_input";

    /**
     * 发消息的通道
     *
     * @return
     */
    @Output(ORDER_OUTPUT)
    MessageChannel sendShopMessage();

    /**
     * 收消息的通道
     *
     * @return
     */
    @Input(ORDER_INPUT)
    SubscribableChannel recieveShopMessage();


}