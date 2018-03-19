package com.vorxsoft.ieye.eventservice;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;
import com.vorxsoft.ieye.eventservice.util.RedisUtil;
import com.vorxsoft.ieye.proto.*;
import io.grpc.stub.StreamObserver;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.RedisPipeline;

public class EventServer2 extends VsIeyeProtoGrpc.VsIeyeProtoImplBase{
  private RedisUtil redisUtil = null;
  //private Jedis jedis ;

  public RedisUtil getRedisUtil() {
    return redisUtil;
  }

  public void setRedisUtil(RedisUtil redisUtil) {
    this.redisUtil = redisUtil;
  }

  EventServer2(String ip, int port)  {
    //jedis  = new  Jedis(ip, port);
    redisUtil = new RedisUtil(ip,port);
  }
  EventServer2(RedisUtil redisUtil)  {
    //jedis  = new  Jedis(ip, port);
    setRedisUtil(redisUtil);
  }
  @Override
  public void reload (ReloadRequest req, StreamObserver<DefaultReply> reply){
    System.out.println("receiver" +  req);
    try {
      String s = JsonFormat.printer().print(req.toBuilder());
      //System.out.println("s");
      //jedis.hset("reload_config_req"+String.valueOf(System.currentTimeMillis()),"req",s);
      redisUtil.hset("reload_config_req"+String.valueOf(System.currentTimeMillis()),"req",s);
    } catch (InvalidProtocolBufferException e) {
      e.printStackTrace();
    }
    DefaultReply defaultReply = DefaultReply.newBuilder().setResult(1).setSBusinessID("1212121").build();
    reply.onNext(defaultReply);
    reply.onCompleted();
  }

}
