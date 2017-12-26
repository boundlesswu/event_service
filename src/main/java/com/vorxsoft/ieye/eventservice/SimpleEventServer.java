package com.vorxsoft.ieye.eventservice;

import com.vorxsoft.ieye.proto.VSEventServiceGrpc;


public class SimpleEventServer extends  VSEventServiceGrpc.VSEventServiceImplBase{
//    @Override
//    public void sentAlarm(VSAlarmRequest request, io.grpc.stub.StreamObserver<com.vorxsoft.ieye.proto.VSAlarmResponse> response){
//        int  resourceId = request.getResourceId();
//        String resourceNo= request.getResourceNo();
//        System.out.print("evenType :"+ request.getEvenType()+"resourceId:"+ resourceId);
//        System.out.println("resourceNo:"+ resourceNo+"happenTime:"+ request.getHappenTime());
//
//        com.vorxsoft.ieye.proto.VSAlarmResponse reply = com.vorxsoft.ieye.proto.VSAlarmResponse.newBuilder().setResourceId(resourceId).
//                                                setResourceNo(resourceNo).setResult(true).build();
//        response.onNext(reply);
//        response.onCompleted();
//
//    }
}
