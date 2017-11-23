package com.vorxsoft.ieye.eventservice;

import com.vorxsoft.ieye.proto.*;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.List;
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


  public Map<String, String> monitorAlarm2map(VSMonitorAlarm monitorAlarm){
    Map<String, String> map = new HashMap<String, String>();
    map.put("evenType",monitorAlarm.getEvenType().name());
    map.put("resourceId", String.valueOf(monitorAlarm.getResourceId()));
    map.put("happenTime",monitorAlarm.getHappenTime().toString());
    map.put("extraContent",monitorAlarm.getExtraContent());
    return map;
  }

  public Map<String, String> sioAlarm2map(VSSioAlarm sioAlarm){
    Map<String, String> map = new HashMap<String, String>();
    map.put("evenType",sioAlarm.getEvenType().name());
    map.put("resourceId", String.valueOf(sioAlarm.getResourceId()));
    map.put("happenTime",sioAlarm.getHappenTime().toString());
    map.put("extraContent",sioAlarm.getExtraContent());
    return map;
  }

  public Map<String, String> iaAlarm2map(VSIaAlarm iaAlarm){
    Map<String, String> map = new HashMap<String, String>();
    map.put("evenType",iaAlarm.getEvenType().name());
    map.put("resourceId", String.valueOf(iaAlarm.getResourceId()));
    map.put("iaagId",String.valueOf(iaAlarm.getIaagId()));
    map.put("iauId",String.valueOf(iaAlarm.getIauId()));
    map.put("happenTime",iaAlarm.getHappenTime().toString());
    map.put("extraContent",iaAlarm.getExtraContent());
    return map;
  }

  public Map<String, String> serverAlarm2map(VSServerAlarm serverAlarm){
    Map<String, String> map = new HashMap<String, String>();
    map.put("evenType",serverAlarm.getEvenType().name());
    map.put("machineId", String.valueOf(serverAlarm.getMachineId()));
    map.put("happenTime",serverAlarm.getHappenTime().toString());
    map.put("extraContent",serverAlarm.getExtraContent());
    return map;
  }

  public Map<String, String> deviceAlarm2map(VSDeviceAlarm deviceAlarm){
    Map<String, String> map = new HashMap<String, String>();
    map.put("evenType",deviceAlarm.getEvenType().name());
    map.put("deviceId", String.valueOf(deviceAlarm.getDeviceId()));
    map.put("happenTime",deviceAlarm.getHappenTime().toString());
    map.put("extraContent",deviceAlarm.getExtraContent());
    return map;
  }


  public VSEventGenus eventType2genus(VSEventType type)
  {
    switch (type) {
      case event_monitor:
      case event_motion_detect:
      case event_video_lose:
      case event_video_occlusion:
      case event_face_snapshot:
      case event_face_recognize:
      case event_enter_region:
      case event_leave_region:
      case event_steal_move:
      case event_perimeter_alarm:
        return event_genus_monitor;

      case event_sio:
      case event_sio_common:
      case event_sio_smoke:
      case event_sio_temperature:
      case event_sio_fire:
      case event_sio_gas:
      case event_sio_infrared:
      case event_sio_vibration:
      case event_sio_leakage:
        return event_genus_sio;

      case event_ia:
      case event_ia_traffic_stop:
      case event_ia_traffic_human:
      case event_ia_traffic_converse:
      case event_ia_traffic_lowspeed:
      case event_ia_traffic_condition:
      case event_ia_traffic_flowrate:
        return event_genus_ia;

      case event_device:
      case event_dev_offline:
      case event_dev_disk_full:
      case event_dev_disk_err:
      case event_dev_update_fail:
        return event_genus_device;

      case event_server:
      case event_svr_offline:
      case event_svr_cpu:
      case event_svr_ram:
      case event_svr_disk_err:
      case event_svr_disk_full:
      case event_svr_record_err:
        return event_genus_server;

      case event_enviroment:
      case event_dust:
      case event_temperature:
      case event_temperature_humidity:
      case event_pressure:
      case event_windspeed:
        return event_genus_enviroment;

      case device_monitor:
      case event_tower_angle_monitor_alarm:
      case hightemplate_deform_alarm:
        return genus_device_monitor;

      case work_monitor:
      case work_overtime_alarm:
        return genus_work_monitor;

      case manual_record:
      case construction_event:
      case traffic_jam_event:
      case landslide_event:
        return genus_manual_record;

      case event_null:
      case UNRECOGNIZED:
        break;
    }
    return event_genus_null;
  }

    @Override
    public void sentAlarm(VSAlarmRequest request,io.grpc.stub.StreamObserver<com.vorxsoft.ieye.proto.VSAlarmResponse> response){
      Map<String, String> map;
      String key="";
      for (int i = 0; i < request.getMonitorAlarmCount(); i++) {
        VSMonitorAlarm alarm = request.getMonitorAlarm(i);
        map = monitorAlarm2map(alarm);
        monitor_count++;
        key = "alarm_monitor_"+monitor_count;
        jedis.hmset(key,map);
      }
      for (int i = 0; i < request.getSioAlarmCount(); i++) {
        VSSioAlarm alarm = request.getSioAlarm(i);
        map = sioAlarm2map(alarm);
        sio_count++;
        key = "alarm_sio_"+ sio_count;
        jedis.hmset(key,map);
      }
      for (int i = 0; i < request.getIaAlarmCount(); i++) {
        VSIaAlarm alarm =  request.getIaAlarm(i);
        map = iaAlarm2map(alarm);
        ia_count++;
        key = "alarm_ia_" + ia_count;
        jedis.hmset(key,map);
      }
      for (int i = 0; i < request.getDeviceAlarmCount(); i++) {
        VSDeviceAlarm alarm = request.getDeviceAlarm(i);
        map = deviceAlarm2map(alarm);
        device_count++;
        key = "alarm_device_" + device_count;
        jedis.hmset(key,map);
      }
      for (int i = 0; i < request.getServerAlarmCount(); i++) {
        VSServerAlarm alarmq = request.getServerAlarm(i);
        map = serverAlarm2map(alarmq);
        server_count++;
        key = "alarm_server_" +  server_count;
        jedis.hmset(key,map);
      }
        System.out.println("receive : " +  request);
        count++;

      VSAlarmResponse reply = com.vorxsoft.ieye.proto.VSAlarmResponse.newBuilder().
          setSerialId(request.getSerialId()).setResult(true).build();
        response.onNext(reply);
        response.onCompleted();
    }

}
