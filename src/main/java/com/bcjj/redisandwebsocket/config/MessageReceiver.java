package com.bcjj.redisandwebsocket.config;

import org.springframework.stereotype.Component;

/**
 * @description
 * @author: By--Dk
 * @create: 2020-07-25 14:49:34
 **/
@Component
public class MessageReceiver {
    public void receiveMessage1(String message){

        System.out.println("收到消息1" +message);
    }
    public void receiveMessage2(String message){

        System.out.println("收到消息2" + message);
    }
    public void receiveMessage3(String message){

        System.out.println("收到消息3" + message);
    }
    public void receiveMessage4(String message){

        System.out.println("收到消息4" + message);
    }
    public void receiveMessage5(String message){

        System.out.println("收到消息5" + message);
    }
    public void receiveMessage6(String message){

        System.out.println("收到消息6" + message);
    }
    public void receiveMessage7(String message){

        System.out.println("收到消息7" + message);
    }
    public void receiveMessage8(String message){

        System.out.println("收到消息8" + message);
    }
    public void receiveMessage9(String message){

        System.out.println("收到消息9" + message);
    }
}
