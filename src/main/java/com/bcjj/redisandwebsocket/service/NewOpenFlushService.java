package com.bcjj.redisandwebsocket.service;

import java.util.List;

public interface NewOpenFlushService {

    public List<String> fliushKeyEvents(String channel);
}
