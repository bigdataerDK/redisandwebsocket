package com.bcjj.redisandwebsocket.web;

import com.bcjj.redisandwebsocket.listener.SubscribeListener;
import com.bcjj.redisandwebsocket.service.impl.NewOpenFlushImpl;
import com.bcjj.redisandwebsocket.util.SpringUtils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ServerEndpoint 注解是一个类层次的注解，它的功能主要是将目前的类定义成一个websocket服务器端,
 * 注解的值将被用于监听用户连接的终端访问URL地址,客户端可以通过这个URL来连接到WebSocket服务器端
 * 使用springboot的唯一区别是要@Component声明下，而使用独立容器是由容器自己管理websocket的，但在springboot中连容器都是spring管理的。
 * 虽然@Component默认是单例模式的，但springboot还是会为每个websocket连接初始化一个bean，所以可以用一个静态set保存起来。
 */
@Component
@ServerEndpoint("/websocket/{channel}")
public class WebSocketServer {
    /**
     * 因为@ServerEndpoint不支持注入，所以使用SpringUtils获取IOC实例
     */
    //注入redisMessageListenerContainer
     private RedisMessageListenerContainer redisMessageListenerContainer = SpringUtils.getBean(RedisMessageListenerContainer.class);

     NewOpenFlushImpl newOpenFlush = SpringUtils.getBean(NewOpenFlushImpl.class);
    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
     private static  AtomicInteger onlineCount=new AtomicInteger(0);
     //concurrent包的线程安全Set，用来存放每个客户端对应的webSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
//     private static CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<WebSocketServer>();
     //把session和channel绑定
     private static ConcurrentHashMap<String, WebSocketServer> webSocketSetMutip = new ConcurrentHashMap<String,WebSocketServer>();

     //与某个客户端的连接会话，需要通过它来给客户端发送数据
     private Session session;
     private String channel;

     private SubscribeListener subscribeListener;



    /**
     * 连接建立成功调用的方法
     * @param session  可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     */

    @OnOpen
    public void onOpen (Session session, @PathParam("channel") String channel){
        this.session = session;
        this.channel = channel;
        webSocketSetMutip.put(channel,this);
        addOnlineCount();

        System.out.println("++++++++++++++++++"+channel);
        try {
            sendMessage(newOpenFlush.fliushKeyEvents(channel));
        } catch (IOException e) {
            e.printStackTrace();
        }
        subscribeListener = new SubscribeListener();
        subscribeListener.setSession(session);

        //设置订阅频道
        redisMessageListenerContainer.addMessageListener(subscribeListener,new ChannelTopic(channel));

    }

    @OnClose
    public void onClose() throws IOException{
        webSocketSetMutip.remove(channel,this);
        subOnlineCount();
        redisMessageListenerContainer.removeMessageListener(subscribeListener);
        System.out.println("有一个连接关闭！当前在线人数为" + getOnlineCount());
    }


    @OnMessage
    public void onMessage(String message, Session session){
        System.out.println("来自客户端的消息：" + message);
        ConcurrentHashMap.KeySetView<String, WebSocketServer> keys = webSocketSetMutip.keySet();
        for(String key:keys){
            System.out.println("当前的channel："+key + "内容："+message + "sessionID："+session.getId());
        }
    }

    /**
     * 发生错误时调用
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error){
        System.out.println("发生错误");
        error.printStackTrace();
    }

    /**
     * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
     * @param messageList
     * @throws IOException
     */
    public void sendMessage(List<String> messageList) throws IOException {
        System.out.println(messageList.size());
        if(messageList.size()>0){
            for (String message : messageList) {
                System.out.println(message);
                this.session.getBasicRemote().sendText(message);
            }
        }


    }

    public   int getOnlineCount() {
        return onlineCount.get();
    }

    public   void addOnlineCount() {
        WebSocketServer.onlineCount.getAndIncrement();
    }

    public   void subOnlineCount() {
        WebSocketServer.onlineCount.getAndDecrement();
    }

}
