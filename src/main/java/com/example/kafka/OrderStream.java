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

    String ORDER_BACK_INPUT = "order_back_input";
    String ORDER_BACK_OUTPUT = "order_back_output";

    /**
     * 发消息的通道
     *
     * @return
     */
    @Output(ORDER_OUTPUT)
    MessageChannel sendShopMessage();

    //@SendTo时可以不写返回的@Output
//    @Output(ORDER_BACK_OUTPUT)
//    MessageChannel orderBackOutput();

    /**
     * 收消息的通道
     *
     * @return
     */
    @Input(ORDER_INPUT)
    SubscribableChannel recieveShopMessage();

    /**
     * 接收到返回值
     *
     * @return
     */
    @Input(ORDER_BACK_INPUT)
    SubscribableChannel recieveBackMessage();
}