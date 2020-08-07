package com.bcjj.redisandwebsocket.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

@Configuration //相当于xml中的beans
public class RedisConfig {
    /**
     * 需要手动注册RedisMessageListenerContainer加入IOC容器
     * @author lijt
     * @return
     */
//    @Bean
//    public RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory connectionFactory) {
//        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
//        container.setConnectionFactory(connectionFactory);
//        //订阅了一个叫chat 的通道
//        container.addMessageListener(new MessageListener(){
//            @Override
//            public void onMessage(Message message, byte[] pattern) {
//                String msg = new String(message.getBody());
//                System.out.println(new String(pattern) + "主题发布：" + msg);
//            }
//        }, new PatternTopic("football_data_20207008"));
//        return container;
//    }

//    private static final String channel01 = "football_data_202007142";
//    private static final String channel02 = "football_data_20207001";
//    private static final String channel03 = "football_data_202007141";

    @Value("${spring.redis.subscribe.channel01}")
    private  String channel01;

    @Value("${spring.redis.subscribe.channel02}")
    private  String channel02;

    @Value("${spring.redis.subscribe.channel03}")
    private  String channel03;

    @Value("${spring.redis.subscribe.channel04}")
    private  String channel04;

    @Value("${spring.redis.subscribe.channel05}")
    private  String channel05;

    @Value("${spring.redis.subscribe.channel06}")
    private  String channel06;

    @Value("${spring.redis.subscribe.channel07}")
    private  String channel07;

    @Value("${spring.redis.subscribe.channel08}")
    private  String channel08;

    @Value("${spring.redis.subscribe.channel09}")
    private  String channel09;

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory connectionFactory,
                                                                       MessageListenerAdapter listenerAdapter1,
                                                                       MessageListenerAdapter listenerAdapter2,
                                                                       MessageListenerAdapter listenerAdapter3,
                                                                       MessageListenerAdapter listenerAdapter4,
                                                                       MessageListenerAdapter listenerAdapter5,
                                                                       MessageListenerAdapter listenerAdapter6,
                                                                       MessageListenerAdapter listenerAdapter7,
                                                                       MessageListenerAdapter listenerAdapter8,
                                                                       MessageListenerAdapter listenerAdapter9
    ){

        final RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);



        //订阅多频道---频道01
        container.addMessageListener(listenerAdapter1,new PatternTopic(channel01));
        //订阅多频道---频道02
        container.addMessageListener(listenerAdapter2,new PatternTopic(channel02));
        //订阅多频道---频道03
        container.addMessageListener(listenerAdapter3,new PatternTopic(channel03));
        //订阅多频道---频道04
        container.addMessageListener(listenerAdapter4,new PatternTopic(channel04));
        //订阅多频道---频道05
        container.addMessageListener(listenerAdapter5,new PatternTopic(channel05));
        //订阅多频道---频道06
        container.addMessageListener(listenerAdapter6,new PatternTopic(channel06));
        //订阅多频道---频道07
        container.addMessageListener(listenerAdapter7,new PatternTopic(channel07));
        //订阅多频道---频道08
        container.addMessageListener(listenerAdapter8,new PatternTopic(channel08));
        //订阅多频道---频道09
        container.addMessageListener(listenerAdapter9,new PatternTopic(channel09));

        return container;
    }

    /**
     * 消息监听器适配1
     * @param receiver
     * @return
     */
    @Bean
    MessageListenerAdapter listenerAdapter1(MessageReceiver receiver){
        return new MessageListenerAdapter(receiver,"receiveMessage1");
    }

    /**
     * 消息监听器适配2
     * @param receiver
     * @return
     */
    @Bean
    MessageListenerAdapter listenerAdapter2(MessageReceiver receiver){
        return new MessageListenerAdapter(receiver,"receiveMessage2");
    }

    /**
     * 消息监听器适配3
     * @param receiver
     * @return
     */
    @Bean
    MessageListenerAdapter listenerAdapter3(MessageReceiver receiver){
        return new MessageListenerAdapter(receiver,"receiveMessage3");
    }

    /**
     * 消息监听器适配4
     * @param receiver
     * @return
     */
    @Bean
    MessageListenerAdapter listenerAdapter4(MessageReceiver receiver){
        return new MessageListenerAdapter(receiver,"receiveMessage4");
    }

    /**
     * 消息监听器适配5
     * @param receiver
     * @return
     */
    @Bean
    MessageListenerAdapter listenerAdapter5(MessageReceiver receiver){
        return new MessageListenerAdapter(receiver,"receiveMessage5");
    }

    /**
     * 消息监听器适配6
     * @param receiver
     * @return
     */
    @Bean
    MessageListenerAdapter listenerAdapter6(MessageReceiver receiver){
        return new MessageListenerAdapter(receiver,"receiveMessage6");
    }
    /**
     * 消息监听器适配7
     * @param receiver
     * @return
     */
    @Bean
    MessageListenerAdapter listenerAdapter7(MessageReceiver receiver){
        return new MessageListenerAdapter(receiver,"receiveMessage7");
    }
    /**
     * 消息监听器适配8
     * @param receiver
     * @return
     */
    @Bean
    MessageListenerAdapter listenerAdapter8(MessageReceiver receiver){
        return new MessageListenerAdapter(receiver,"receiveMessage8");
    }
    /**
     * 消息监听器适配9
     * @param receiver
     * @return
     */
    @Bean
    MessageListenerAdapter listenerAdapter9(MessageReceiver receiver){
        return new MessageListenerAdapter(receiver,"receiveMessage9");
    }



    /**
     * redis 读取内容
     * @param connectionFactory
     * @return
     */
    @Bean
    StringRedisTemplate stringRedisTemplate(RedisConnectionFactory connectionFactory){
        return new StringRedisTemplate(connectionFactory);
    }


}
