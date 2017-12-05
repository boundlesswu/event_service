package com.vorxsoft.ieye.eventservice;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;
import com.vorxsoft.ieye.proto.*;
import io.grpc.stub.StreamObserver;
import redis.clients.jedis.Jedis;

public class EventServer2 extends VsIeyeProtoGrpc.VsIeyeProtoImplBase{
  private Jedis jedis ;

  EventServer2(String ip,int port)  {
    jedis  = new  Jedis(ip, port);
  }
  @Override
  public void reload (ReloadRequest req, StreamObserver<DefaultReply> reply){
    System.out.println("receiver" +  req);
    try {
      String s = JsonFormat.printer().print(req.toBuilder());
      //System.out.println("s");
      jedis.hset("reload_config_req"+String.valueOf(System.currentTimeMillis()),"req",s);
    } catch (InvalidProtocolBufferException e) {
      e.printStackTrace();
    }
    DefaultReply defaultReply = DefaultReply.newBuilder().setResult(1).setSBusinessID("1212121").build();
    reply.onNext(defaultReply);
    reply.onCompleted();
  }

}
