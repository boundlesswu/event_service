package com.vorxsoft.ieye.eventservice;

import com.vorxsoft.ieye.proto.VSEventRequest;
import com.vorxsoft.ieye.proto.VSEventResponse;
import com.vorxsoft.ieye.proto.VSEventServiceGrpc;

public class EventServer2 extends VSEventServiceGrpc.VSEventServiceImplBase{
  @Override
  public void sentEvent(VSEventRequest req, io.grpc.stub.StreamObserver<com.vorxsoft.ieye.proto.VSEventResponse> response){
    System.out.println("receive : " +  req);
    VSEventResponse reply = com.vorxsoft.ieye.proto.VSEventResponse.newBuilder().setEventId(112).setResult(true).build();
    response.onNext(reply);
    response.onCompleted();
  }

}
