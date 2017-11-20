package com.vorxsoft.ieye.eventservice;

import com.vorxsoft.ieye.proto.VSAlarmRequest;
import com.vorxsoft.ieye.proto.VSEventServiceGrpc;


public class SimpleEventServer extends  VSEventServiceGrpc.VSEventServiceImplBase{
    @Override
    public void sentAlarm(VSAlarmRequest request, io.grpc.stub.StreamObserver<com.vorxsoft.ieye.proto.VSAlarmResponse> response){
        String deviceNo = request.getDeviceNo();
        String resourceUid = request.getResourceUid();
        System.out.print("evenType :"+ request.getEvenType()+"deviceNo:"+ deviceNo);
        System.out.println("resourceUid:"+ resourceUid+"happenTime:"+ request.getHappenTime());

        com.vorxsoft.ieye.proto.VSAlarmResponse reply = com.vorxsoft.ieye.proto.VSAlarmResponse.newBuilder().setDeviceNo(deviceNo).
                                                setResourceUid(resourceUid).setResult(true).build();
        response.onNext(reply);
        response.onCompleted();

    }
}
