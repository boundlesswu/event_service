package com.vorxsoft.ieye.eventservice.grpc;

import com.vorxsoft.ieye.proto.*;
import io.grpc.stub.StreamObserver;

public class VsIAClient extends GrpcClient {
  private VSIAServiceGrpc.VSIAServiceStub stub;
  //private IAAGInfo iaagInfo;

  public VsIAClient(String name, String IP, int PORT) {
    super(name, IP, PORT);
    if (getStub() == null) {
      stub = VSIAServiceGrpc.newStub(getManagedChannel());
    }
  }

  public VsIAClient(String name, String address) {
    super(name, address);
    if (getStub() == null) {
      stub = VSIAServiceGrpc.newStub(getManagedChannel());
    }
  }

//  public IAAGInfo getIaagInfo() {
//    return iaagInfo;
//  }
//
//  public void setIaagInfo(IAAGInfo iaagInfo) {
//    this.iaagInfo = iaagInfo;
//  }

  public VSIAServiceGrpc.VSIAServiceStub getStub() {
    return stub;
  }

  public void setStub(VSIAServiceGrpc.VSIAServiceStub stub) {
    this.stub = stub;
  }


  public VsIAClient(String IP, int PORT) {
    super(IP, PORT);
  }


  public VsIAClient() {
  }

  public void init() {
    createChannel();
    stub = VSIAServiceGrpc.newStub(getManagedChannel());
  }

  public void sentIACMD(SentIACMDRequest request) {
    StreamObserver<SentIACMDResponse> responseObserver = new StreamObserver<SentIACMDResponse>() {
      @Override
      public void onNext(SentIACMDResponse value) {
        if (!value.getResult()) {
          return;
        }
      }

      @Override
      public void onError(Throwable t) {
        System.out.println("send ia cmd  onError" + t);
      }

      @Override
      public void onCompleted() {
        // Won't be called, since the server in this example always
        // fails.
      }
    };
    stub.sentIACMD(request, responseObserver);
  }

  public void queryIAAG(QueryIAAGRequest request) {
    StreamObserver<QueryIAAGResponse> responseObserver = new StreamObserver<QueryIAAGResponse>() {
      @Override
      public void onNext(QueryIAAGResponse value) {
        System.out.println("iaag info " + value.getIaagInfo());
      }

      @Override
      public void onError(Throwable t) {
        System.out.println("query iaag onError" + t);
      }

      @Override
      public void onCompleted() {
        // Won't be called, since the server in this example always
        // fails.
      }
    };
    stub.queryIAAG(request, responseObserver);
  }

  public void queryIAUList(QueryIAUListRequest request) {
    StreamObserver<QueryIAUListResponse> responseObserver = new StreamObserver<QueryIAUListResponse>() {
      @Override
      public void onNext(QueryIAUListResponse value) {
        System.out.println("iaag info " + value.getIauListList());
      }

      @Override
      public void onError(Throwable t) {
        System.out.println("query iau list onError" + t);
      }

      @Override
      public void onCompleted() {
        // Won't be called, since the server in this example always
        // fails.
      }
    };
    stub.queryIAUList(request, responseObserver);
  }

  public void sendIAInterval(SentIAIntervalRequest request){
    StreamObserver<SentIAIntervalResponse> responseObserver = new StreamObserver<SentIAIntervalResponse>(){
      @Override
      public void onNext(SentIAIntervalResponse value) {
        System.out.println("cmdId : " + value.getCmdId() + " result is : "+ value.getResult());
      }

      @Override
      public void onError(Throwable t) {
        System.out.println("sendIAInterval onError" + t);
      }

      @Override
      public void onCompleted() {
        // Won't be called, since the server in this example always
        // fails.
      }
    };
    stub.sentIAInterval(request,responseObserver);
  }

  public void zero() {
    this.stub = null;
    super.zero();
  }

}
