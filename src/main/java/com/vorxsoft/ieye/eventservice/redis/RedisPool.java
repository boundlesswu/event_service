package com.vorxsoft.ieye.eventservice.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;

public class RedisPool {
  private static redis.clients.jedis.JedisPool redisPool = null;
  private String redisIP;
  private int redisPort;

  RedisPool() {
  }

  RedisPool(String ip, int port) {
    this.redisIP = new String(ip);
    this.redisPort = port;
    redisPoolInit();
  }

  public void redisPoolInit() {
    JedisPoolConfig config = new JedisPoolConfig();
    //配置最大jedis实例数
    config.setMaxTotal(1000);
    //配置资源池最大闲置数
    config.setMaxIdle(200);
    //等待可用连接的最大时间
    config.setMaxWaitMillis(10000);
    //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的
    config.setTestOnBorrow(true);
    redisPool = new redis.clients.jedis.JedisPool(redisIP, redisPort);
  }

  //获取Jedis实例
  public synchronized static Jedis getJedis() {
    if (redisPool != null) {
      Jedis resource = redisPool.getResource();
      return resource;
    } else {
      return null;
    }
  }

  //释放Jedis资源
  public static void returnResource(final Jedis jedis) {
    if (jedis != null) {
      jedis.close();
      //jedisPool.returnResource(jedis);
    }
  }
}
