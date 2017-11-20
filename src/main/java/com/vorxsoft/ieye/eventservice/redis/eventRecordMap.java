package com.vorxsoft.ieye.eventservice.redis;

import redis.clients.jedis.Jedis;

import java.util.Map;

public class eventRecordMap {

  public void alarmMap2eventMap(Map<String, String> map,Jedis jedis,long count){
    String key = "event_" + count;
    jedis.hmset(key,map);
  }
}
