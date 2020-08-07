package com.bcjj.redisandwebsocket.service.impl;

import com.bcjj.redisandwebsocket.service.NewOpenFlushService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @description
 * @author: By--Dk
 * @create: 2020-08-07 13:28:13
 **/

@Service
public class NewOpenFlushImpl implements NewOpenFlushService {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Value("${spring.redis.subscribe.channel01}")
    String channel01;

    @Value("${spring.redis.subscribe.channel02}")
    String channel02;

    @Value("${spring.redis.subscribe.channel03}")
    String channel03;

    @Value("${spring.redis.subscribe.channel04}")
    String channel04;

    @Value("${spring.redis.subscribe.channel05}")
    String channel05;

    @Value("${spring.redis.subscribe.channel06}")
    String channel06;

    @Value("${spring.redis.subscribe.channel07}")
    String channel07;

    @Value("${spring.redis.subscribe.channel08}")
    String channel08;

    @Value("${spring.redis.subscribe.channel09}")
    String channel09;

    /**
     * 寻找和channel匹配的matchid，从redis数据库中查出相应的关键性的事件进行推送
     * @param channel
     * @return
     */
    @Override
    public List<String> fliushKeyEvents(String channel) {

        //坝列表中的list添加到一个channelList当中
        ArrayList<String> channelList = new ArrayList<>();
        channelList.add(channel01);
        channelList.add(channel02);
        channelList.add(channel03);
        channelList.add(channel04);
        channelList.add(channel05);
        channelList.add(channel06);
        channelList.add(channel07);
        channelList.add(channel08);
        channelList.add(channel09);

        //从channelList当中判断当前打开的界面是包含在已存在的channelList当中，如果
        //存在，就把当前的channel的matchid获取，并且完场取值
        if(channelList.contains(channel)) {
            String matchId = channel.split("_")[2];
            System.out.println("当前比赛的matchid是："+matchId);

            List<String> channelKeys = new ArrayList<String>();
            //获取开球事件(202007141_KO*)
            Set<String> koKeys = stringRedisTemplate.keys(matchId + "_" + "KO*");
            if (koKeys.size() != 0) {
                for (String koKey : koKeys) {
                    channelKeys.add(stringRedisTemplate.opsForValue().get(koKey));
                }
            }

            //获取进球事件
            Set<String> goalsKeys = stringRedisTemplate.keys(matchId + "_" + "CONF_GOAL*");
            if (goalsKeys.size() != 0) {
                for (String goalsKey : goalsKeys) {
                    channelKeys.add(stringRedisTemplate.opsForValue().get(goalsKey));
                }
            }

            //获取进球取消事件
            Set<String> cGoalKeys = stringRedisTemplate.keys(matchId + "_" + "CGOAL*");
            if (cGoalKeys.size() != 0) {
                for (String cGoalKey : cGoalKeys) {
                    channelKeys.add(stringRedisTemplate.opsForValue().get(cGoalKey));
                }
            }

            //获取角球事件
            Set<String> cornerKeys = stringRedisTemplate.keys(matchId + "_" + "CORNER*");
            if (cornerKeys.size() != 0) {
                for (String cornerKey : cornerKeys) {
                    channelKeys.add(stringRedisTemplate.opsForValue().get(cornerKey));
                }
            }

            //获取任意球事件
            Set<String> fkKeys = stringRedisTemplate.keys(matchId + "_" + "PEN*");
            if (fkKeys.size() != 0) {
                for (String fkKey : fkKeys) {
                    channelKeys.add(stringRedisTemplate.opsForValue().get(fkKey));
                }
            }

            //获取点球事件
            Set<String> penkickKeys = stringRedisTemplate.keys(matchId + "_" + "PENALTY*");
            if (penkickKeys.size() != 0) {
                for (String penkickKey : penkickKeys) {
                    channelKeys.add(stringRedisTemplate.opsForValue().get(penkickKey));
                }
//                channelKeys.addAll(penkickKeys);
            }

            //获取替补事件
            Set<String> subKeys = stringRedisTemplate.keys(matchId + "_" + "SUB*");
            if (subKeys.size() != 0) {
                for (String subKey : subKeys) {
                    channelKeys.add(stringRedisTemplate.opsForValue().get(subKey));
                }
//                channelKeys.addAll(subKeys);
            }

            //获取犯规事件
            Set<String> foulKeys = stringRedisTemplate.keys(matchId + "_" + "F*");
            if (foulKeys.size() != 0) {
                for (String foulKey : foulKeys) {
                    channelKeys.add(stringRedisTemplate.opsForValue().get(foulKey));
                }
//                channelKeys.addAll(foulKeys);
            }

            //获取红牌事件
            Set<String> redKeys = stringRedisTemplate.keys(matchId + "_" + "REDCARD*");
            if (redKeys.size() != 0) {
                for (String redKey : redKeys) {
                    channelKeys.add(stringRedisTemplate.opsForValue().get(redKey));
                }
//                channelKeys.addAll(redKeys);
            }

            //获取黄牌事件
            Set<String> yellKeys = stringRedisTemplate.keys(matchId + "_" + "YELLCARD*");
            if (yellKeys.size() != 0) {
                for (String yellKey : yellKeys) {
                    channelKeys.add(stringRedisTemplate.opsForValue().get(yellKey));
                }
//                channelKeys.addAll(yellKeys);
            }

            //获取射门事件
            Set<String> shotsKeys = stringRedisTemplate.keys(matchId + "_" + "GK*");
            if (shotsKeys.size() != 0) {
                for (String shotsKey : shotsKeys) {
//                    System.out.println(stringRedisTemplate.opsForValue().get(shotsKey));
                    channelKeys.add(stringRedisTemplate.opsForValue().get(shotsKey));
                }
//                channelKeys.addAll(shotsKeys);
            }

            //获取射正事件
            Set<String> shootOnKeys = stringRedisTemplate.keys(matchId + "_" + "SHG*");
            if (shootOnKeys.size() != 0) {
                for (String shootOnKey : shootOnKeys) {
                    channelKeys.add(stringRedisTemplate.opsForValue().get(shootOnKey));
                }
//                channelKeys.addAll(shootOnKeys);
            }

            //获取射偏事件
            Set<String> shootoutKeys = stringRedisTemplate.keys(matchId + "_" + "SHB*");
//            System.out.println(stringRedisTemplate.keys(matchId + "_" + "SHB*"));
//            System.out.println((matchId + "_" + "SHB*"));
//            System.out.println("===============" + shootoutKeys.size());
            if (shootoutKeys.size() != 0) {
                for (String shootoutKey : shootoutKeys) {
                    channelKeys.add(stringRedisTemplate.opsForValue().get(shootoutKey));
                }
//                channelKeys.addAll(shootoutKeys);
            }

            //获取越位事件
            Set<String> OKeys = stringRedisTemplate.keys(matchId + "_" + "O*");
            if (OKeys.size() != 0) {
                for (String oKey : OKeys) {
                    channelKeys.add(stringRedisTemplate.opsForValue().get(oKey));
                }
//                channelKeys.addAll(OKeys);
            }

            //获取界外球事件
            Set<String> throwInKeys = stringRedisTemplate.keys(matchId + "_" + "TI*");
            if (throwInKeys.size() != 0) {
                for (String throwInKey : throwInKeys) {
                    channelKeys.add(stringRedisTemplate.opsForValue().get(throwInKey));
                }
//                channelKeys.addAll(throwInKeys);
            }

            //获取上半场结束事件
            Set<String> stopHalfKey = stringRedisTemplate.keys(matchId + "_" + "Stop RT1*");
            if (stopHalfKey.size() != 0) {
                for (String s : stopHalfKey) {
                    channelKeys.add(stringRedisTemplate.opsForValue().get(s));
                }
//                channelKeys.addAll(stopHalfKey);
            }

            //获取下半场开始事件
            Set<String> beginSecondKey = stringRedisTemplate.keys(matchId + "_" + "Start RT2*");
            if (beginSecondKey.size() != 0) {
                for (String s : beginSecondKey) {
                    channelKeys.add(stringRedisTemplate.opsForValue().get(s));
                }
//                channelKeys.addAll(beginSecondKey);
            }
            return channelKeys;
        }else {
            return null;
        }
    }
}
