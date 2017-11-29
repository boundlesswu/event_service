package com.vorxsoft.ieye.eventservice.grpc;

import com.vorxsoft.ieye.proto.*;
import io.grpc.stub.StreamObserver;

public class VsIeyeClient extends GrpcClient{
  private VsIeyeProtoGrpc.VsIeyeProtoStub stub;

  public VsIeyeClient(String IP, int PORT) {
    super(IP, PORT);
  }

  public VsIeyeClient() {
  }

  public void init(){
    createChannel();
    stub =  VsIeyeProtoGrpc.newStub(getManagedChannel());
  }
  public void reportEvent(ReportEventRequest request){
    StreamObserver<DefaultReply> responseObserver = new StreamObserver<DefaultReply>() {
      @Override
      public void onNext(DefaultReply value) {
        if (value.getResult() != 0) {
          return;
        }
      }
      @Override
      public void onError(Throwable t) {
        System.out.println("report event onError"+t);
      }
      @Override
      public void onCompleted() {
        // Won't be called, since the server in this example always
        // fails.
      }
    };
    stub.reportEvent(request, responseObserver);
  }
  public void reportLinkage(ReportLinkageRequest request){
    StreamObserver<DefaultReply> responseObserver = new StreamObserver<DefaultReply>() {
      @Override
      public void onNext(DefaultReply value) {
        if (value.getResult() != 0) {
          return;
        }
      }
      @Override
      public void onError(Throwable t) {
        System.out.println("report linkage onError"+t);
      }
      @Override
      public void onCompleted() {
        // Won't be called, since the server in this example always
        // fails.
      }
    };
    stub.reportLinkage(request, responseObserver);
  }
}
