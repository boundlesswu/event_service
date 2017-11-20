package com.vorxsoft.ieye.eventservice;

import com.vorxsoft.ieye.proto.*;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;

import static com.vorxsoft.ieye.proto.VSEventGenus.*;

/**
 * Created by Administrator on 2017/8/3 0003.
 */

public class EventServer extends VSEventServiceGrpc.VSEventServiceImplBase{

  private Jedis jedis ;
  private long count;
  private long monitor_count = 0;
  private long sio_count = 0;
  private long ia_count = 0;
  private long device_count = 0;
  private long server_count = 0;

  EventServer(String ip,int port)  {
    jedis  = new  Jedis(ip, port);
  }
  public Jedis getJedis(){
    return jedis;
  }

  public Map<String, String> Alarm2map(VSAlarmRequest request){
    Map<String, String> map = new HashMap<String, String>();
    map.put("evenType",request.getEvenType().name());
    map.put("resourceId", String.valueOf(request.getResourceId()));
    map.put("resoureNo",request.getResourceNo());
    map.put("happenTime",request.getHappenTime());
    map.put("extraContent",request.getExtraContent());
    return map;
  }

  public VSEventGenus eventType2genus(VSEventType type)
  {
    switch (type){
      case VSEventTypeNull:
        return VSEventGenusNull;

      case VSEventTypeMonitor:
      case VSEventTypeMonitorMotionDetect:
      case VSEventTypeMonitorVideoLose:
      case VSEventTypeMonitorVideoOcclusion:
      case VSEventTypeMonitorFaceSnapshot:
      case VSEventTypeMonitorFaceRecognize:
      case VSEventTypeMonitorEnterRegion:
      case VSEventTypeMonitorLeaveRegion:
      case VSEventTypeMonitorStealMove:
      case VSEventTypeMonitorPerimeterAlarm:
        return VSEventGenusMonitor;

      case VSEventTypeDigitalIO:
      case VSEventTypeDigitalIOCommon:
      case VSEventTypeDigitalIOSmoke:
      case VSEventTypeDigitalIOTemperature:
      case VSEventTypeDigitalIOFire:
      case VSEventTypeDigitalIOGas:
      case VSEventTypeDigitalIOInfrared:
      case VSEventTypeDigitalIOVibration:
      case VSEventTypeDigitalIOLeakage:
        return VSEventGenusDigitalIO;

      case VSEventTypeIntelligentChannelAlarm:
      case VSEventTypeIntelligentChannelTrafficStop:
      case VSEventTypeIntelligentChannelTrafficHuman:
      case VSEventTypeIntelligentChannelTrafficConverse:
      case VSEventTypeIntelligentChannelTrafficLowspeed:
      case VSEventTypeIntelligentChannelTrafficCondition:
      case VSEventTypeIntelligentChannelTrafficFlowrate:
        return VSEventGenusIntelligentChannel;

      case VSEventTypeDeviceAlarm:
      case VSEventTypeDeviceOffline:
      case VSEventTypeDeviceDiskFull:
      case VSEventTypeDeviceDiskErr:
      case VSEventTypeDeviceUpdateFail:
        return VSEventGenusDevice;

      case VSEventTypeServerAlarm:
      case VSEventTypeServerOffline:
      case VSEventTypeServerCpu:
      case VSEventTypeServerRam:
      case VSEventTypeServerDiskErr:
      case VSEventTypeServerDiskFull:
      case VSEventTypeServerRecordErr:
        return VSEventGenusServer;

      case VSEventTypeEnvironmentMonitor:
      case VSEventTypeEnvDust:
      case VSEventTypeEnvTemperature:
      case VSEventTypeEnvTempHumi:
      case VSEventTypeEnvPressure:
      case VSEventTypeEnvWindspeed:
      case VSEventTypeDeviceMonitor:
      case VSEventTypeDeviceTowerAngleMonitor:
      case VSEventTypeDeviceHightemplateDeformAlarm:
      case VSEventTypeWorkMonitor:
      case VSEventTypeWorkOverTime:
      case VSEventTypeManualInput:
      case VSEventTypeManualInputConstruction:
      case VSEventTypeManualInputTrafficJam:
      case VSEventTypeManualInputLandslide:
      case UNRECOGNIZED:
        return VSEventGenusNull;
    }
    return VSEventGenusNull;
  }

    @Override
    public void sentAlarm(VSAlarmRequest request,io.grpc.stub.StreamObserver<com.vorxsoft.ieye.proto.VSAlarmResponse> response){
        System.out.println("receive : " +  request);
        count++;
      Map<String, String> map = Alarm2map(request);
      String key = null;
      boolean alarm_type_wrong = false;
      switch (eventType2genus(request.getEvenType())){
        case VSEventGenusMonitor:
          monitor_count++;
          //key = "alarm_monitor"+monitor_count;
          break;
        case VSEventGenusDigitalIO:
          sio_count++;
          //key = "alarm_sio"+ sio_count;
          break;
        case VSEventGenusIntelligentChannel:
          ia_count++;
          //key = "alarm_ia" + ia_count;
          break;
        case VSEventGenusDevice:
          device_count++;
          //key = "alarm_device" + device_count;
          break;
        case VSEventGenusServer:
          server_count++;
          //key = "alarm_server" +  server_count;
          break;
        case VSEventGenusNull:
        case UNRECOGNIZED:
          alarm_type_wrong = true;
          break;
      }
      key = "alarm_" + count;
      if(!alarm_type_wrong)
        jedis.hmset(key,map);
      VSAlarmResponse reply = com.vorxsoft.ieye.proto.VSAlarmResponse.newBuilder().setResourceId(request.getResourceId()).
                                                             setResourceNo(request.getResourceNo()).
                                                              setResult(true).build();
        response.onNext(reply);
        response.onCompleted();
    }

}
