package com.vorxsoft.ieye.eventservice.redis;

import com.vorxsoft.ieye.eventservice.config.*;
import com.vorxsoft.ieye.eventservice.util.Constant;

import java.awt.*;
import java.util.HashMap;

import static com.vorxsoft.ieye.eventservice.util.Constant.*;

public class AlarmStormRecordMap {
  private HashMap<MonitorConfigKey, AlarmStormRecordItem> monitorAlarmRecords;
  private HashMap<IaConfigKey, AlarmStormRecordItem> iaAlarmRecords;
  private HashMap<SioConfigKey, AlarmStormRecordItem> sioAlarmRecords;
  private HashMap<ServerConfigKey, AlarmStormRecordItem> serverAlarmRecords;
  private HashMap<DeviceConfigKey, AlarmStormRecordItem> deviceAlarmRecords;

  public AlarmStormRecordMap() {
    monitorAlarmRecords = new HashMap<>();
    iaAlarmRecords = new HashMap<>();
    sioAlarmRecords = new HashMap<>();
    serverAlarmRecords = new HashMap<>();
    deviceAlarmRecords = new HashMap<>();
  }

  public AlarmStormRecordItem add(long happenTime, int alarmId, EventInfo eventInfo) {
    String genus = Constant.eventType2Genus(eventInfo.getEvent_type());
    if(genus == null){
      return null;
    }
    AlarmStormRecordItem item = AlarmStormRecordItem.newBuilder().alarmStormId(alarmId).happenTime(happenTime).
            event_type(eventInfo.getEvent_type()).build();
    if(genus.equals(sGenusMonitor)){
      monitorAlarmRecords.put(eventInfo.getMonitorConfigKey(),item);
    }else if(genus.equals(sGenusSio)){
      sioAlarmRecords.put(eventInfo.getSioConfigKey(),item);
    }else if(genus.equals(sGenusIa)){
      iaAlarmRecords.put(eventInfo.getIaConfigKey(),item);
    }else if(genus.equals(sGenusServer)){
      serverAlarmRecords.put(eventInfo.getServerConfigKey(),item);
    }else if(genus.equals(sGenusDeveice)){
      deviceAlarmRecords.put(eventInfo.getDeviceConfigKey(),item);
    }else{
      return null;
    }
    return item;
  }

  public AlarmStormRecordItem find(EventInfo eventInfo){
    String genus = Constant.eventType2Genus(eventInfo.getEvent_type());
    if(genus == null){
      return null;
    }
    if(genus.equals(sGenusMonitor)){
      return monitorAlarmRecords.get(eventInfo.getMonitorConfigKey());
    }else if(genus.equals(sGenusSio)){
      return sioAlarmRecords.get(eventInfo.getSioConfigKey());
    }else if(genus.equals(sGenusIa)){
      return iaAlarmRecords.get(eventInfo.getIaConfigKey());
    }else if(genus.equals(sGenusServer)){
      return serverAlarmRecords.get(eventInfo.getServerConfigKey());
    }else if(genus.equals(sGenusDeveice)){
      return deviceAlarmRecords.get(eventInfo.getDeviceConfigKey());
    }else{
      return null;
    }
  }

  public long diffCurrentTime(String happenTime, EventInfo eventInfo) {
    AlarmStormRecordItem item =find(eventInfo);
    if (item == null) {
      System.out.println("no AlarmStormItem: found:");
      return Long.MAX_VALUE;
    } else {
      return item.diffTime(happenTime);
    }
  }

  public void update(long happenTime, int alarmId,EventInfo eventInfo){
    AlarmStormRecordItem item =find(eventInfo);
    if (item == null) {
      add(happenTime,alarmId,eventInfo);
    }else{
      item.setHappenTime(happenTime);
      item.setEvent_type(eventInfo.getEvent_type());
      item.setAlarmStormId(alarmId);
    }
  }

  public long diffCurrentTime(long happenTime) {
    return System.currentTimeMillis() / 1000 - happenTime;
  }
}

