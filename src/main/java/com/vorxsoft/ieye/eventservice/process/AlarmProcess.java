package com.vorxsoft.ieye.eventservice.process;

import com.vorxsoft.ieye.eventservice.config.*;
import com.vorxsoft.ieye.eventservice.redis.AlarmStormRecordMap;
import com.vorxsoft.ieye.eventservice.redis.EventRecord;
import com.vorxsoft.ieye.eventservice.redis.EventRecordMap;
import com.vorxsoft.ieye.eventservice.util.ResUtil;
import redis.clients.jedis.Jedis;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import static com.vorxsoft.ieye.eventservice.process.AlarmProcess.ProcessType.*;

public class AlarmProcess implements Runnable {
  private String name;
  private EventConfig eventConfig;


  public AlarmStormConfig getAlarmStormConfig() {
    return alarmStormConfig;
  }

  public void setAlarmStormConfig(AlarmStormConfig alarmStormConfig) {
    this.alarmStormConfig = alarmStormConfig;
  }

  public AlarmStormRecordMap getAlarmStormRecordMap() {
    return alarmStormRecordMap;
  }

  public void setAlarmStormRecordMap(AlarmStormRecordMap alarmStormRecordMap) {
    this.alarmStormRecordMap = alarmStormRecordMap;
  }

  public Jedis getJedis() {
    return jedis;
  }

  public void setJedis(Jedis jedis) {
    this.jedis = jedis;
  }

  private AlarmStormConfig alarmStormConfig;
  private AlarmStormRecordMap alarmStormRecordMap;
  private Jedis jedis;
  private ProcessType processType;
  private EventRecordMap eventRecordMap;

  public int init(){
    return 0;
  }

  public ProcessType getProcessType() {
    return processType;
  }

  public void setProcessType(ProcessType processType) {
    this.processType = processType;
  }

  public com.vorxsoft.ieye.eventservice.redis.EventRecordMap getEventRecordMap() {
    return eventRecordMap;
  }

  public void setEventRecordMap(com.vorxsoft.ieye.eventservice.redis.EventRecordMap map) {
    eventRecordMap = map;
  }

  enum ProcessType {
    ProcessMonitorType,
    ProcessIaType,
    ProcessSioType,
    ProcessServerType,
    ProcessDeviceType,
    ProcessAlarmStorm;
  }



  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public EventConfig getEventConfig() {
    return eventConfig;
  }

  public void setEventConfig(EventConfig eventConfig) {
    this.eventConfig = eventConfig;
  }

  AlarmProcess(String name, ProcessType type, EventRecordMap map) {
    this.name = name;
    this.processType = type;
    this.eventRecordMap = map;
  }

  public AlarmProcess(String name) {
    this.name = name;
  }


  @Override
  public void run() {
    for (int i = 0; ; i++) {
      System.out.println(name + "运行  :  " + i);
      try {
        processAlarm(processType);
        Thread.sleep((int) Math.random() * 10);
        //Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

  }

  public static String getTime(String user_time) {
    String re_time = null;
    SimpleDateFormat sdf = new SimpleDateFormat("YYYY/MM/dd HH:mm:ss.SSS");
    Date d;
    try {
      d = sdf.parse(user_time);
      long l = d.getTime();
      String str = String.valueOf(l);
      re_time = str.substring(0, 10);
    } catch (ParseException e) {
// TODO Auto-generated catch block
      e.printStackTrace();
    }
    return re_time;
  }

  public void processAlarm(ProcessType processType) throws Exception {
    String patterKey = "";
    switch (processType) {
      case ProcessMonitorType:
        patterKey = "alarm_monitor_*";
        break;
      case ProcessIaType:
        patterKey = "alarm_ia_*";
        break;
      case ProcessSioType:
        patterKey = "alarm_sio_*";
        break;
      case ProcessServerType:
        patterKey = "alarm_server_*";
        break;
      case ProcessDeviceType:
        patterKey = "alarm_device_*";
        break;
      case ProcessAlarmStorm:
      default:
        System.out.println("wrong processType");
        return;
    }
    Set<String> set = jedis.keys("patterKey");
    Iterator<String> it = set.iterator();
    String sCount = "";
    String evenType = "";
    int resourceId = 0;
    String happenTime = "";
    String extraContent = "";
    int iaadId = 0;
    int iauId = 0;
    int machineId = 0;
    int deviceId = 0;
    EventInfo eventInfo = new EventInfo();
    while (it.hasNext()) {
      String keyStr = it.next();
      System.out.println(keyStr);
      Map<String, String> map = jedis.hgetAll(keyStr);
      evenType = map.get("evenType");
      happenTime = map.get("happenTime");
      extraContent = map.get("extraContent");
      if (processType == ProcessMonitorType) {
        resourceId = Integer.parseInt(map.get("resourceId"));
        MonitorConfigKey monitorConfigKey = new MonitorConfigKey(evenType, resourceId);
        eventInfo = eventConfig.getMonitorConfig(monitorConfigKey);
      } else if (processType == ProcessSioType) {
        resourceId = Integer.parseInt(map.get("resourceId"));
        SioConfigKey sioConfigKey = new SioConfigKey(evenType, resourceId);
        eventInfo = eventConfig.getSioConfig(sioConfigKey);
      } else if (processType == ProcessIaType) {
        iaadId = Integer.parseInt(map.get("iaagId"));
        iauId = Integer.parseInt(map.get("iauId"));
        IaConfigKey iaConfigKey = new IaConfigKey(evenType, resourceId, iaadId, iauId);
        eventInfo = eventConfig.getIaConfig(iaConfigKey);
      } else if (processType == ProcessServerType) {
        machineId = Integer.parseInt(map.get("machineId"));
        ServerConfigKey serverConfigKey = new ServerConfigKey(evenType, machineId);
        eventInfo = eventConfig.getServerConfig(serverConfigKey);
      } else if (processType == ProcessDeviceType) {
        deviceId = Integer.parseInt(map.get("deviceId"));
        DeviceConfigKey deviceConfigKey = new DeviceConfigKey(evenType, deviceId);
        eventInfo = eventConfig.getDeviceConfig(deviceConfigKey);
      } else {
        System.out.println("wrong processType");
        continue;
      }
      jedis.del(keyStr);
      if (eventInfo == null) {
        System.out.println("No event config for alarm evenType:" + evenType +
                "happenTime" + happenTime + "extraContent" + extraContent +
                "resourceId:" + resourceId + " iaadId:" + iaadId + " iauId" + iauId +
                "machineId:" + machineId + " deviceId:" + deviceId);
        //no event happen,insert into tl_event_src_monitor table
        insertSrcLogList(processType,evenType,resourceId,happenTime,iaadId,machineId,deviceId);
        continue;
      }
      GuardPlan guardPlan = eventInfo.getGuardPlan();
      if (guardPlan == null || !guardPlan.isInGuardPlan(happenTime)) {
        System.out.println("No guard plan  config for alarm evenType:" + evenType +
                "happenTime" + happenTime + "extraContent" + extraContent +
                "resourceId:" + resourceId + " iaadId:" + iaadId + " iauId" + iauId +
                "machineId:" + machineId + " deviceId:" + deviceId);
        //no event happen,insert into tl_event_src_* table
        insertSrcLogList(processType,evenType,resourceId,happenTime,iaadId,machineId,deviceId);
        continue;
      }
      AlarmStorm alarmStorm = alarmStormConfig.getAlarmStorm(evenType);
      if (alarmStorm == null) {
        System.out.println("no alarm storm config for alarm evenType:" + evenType +
                "happenTime" + happenTime + "extraContent" + extraContent +
                "resourceId:" + resourceId + " iaadId:" + iaadId + " iauId" + iauId +
                "machineId:" + machineId + " deviceId:" + deviceId);
        System.out.println("send to event queue");
        // send to event queue
        insertEvenList(processType,evenType,resourceId,happenTime,iaadId,machineId,deviceId,eventInfo,extraContent);
      } else {
        Long stom_time = alarmStorm.getEvent_stom();
        if (stom_time == 0) {
          System.out.println("storm time == 0 no alarm storm config of event_type:" + evenType +
                  "happenTime" + happenTime + "extraContent" + extraContent +
                  "resourceId:" + resourceId + " iaadId:" + iaadId + " iauId" + iauId +
                  "machineId:" + machineId + " deviceId:" + deviceId);
          System.out.println("send to event queue");
          // send to event queue
          insertEvenList(processType,evenType,resourceId,happenTime,iaadId,machineId,deviceId,eventInfo,extraContent);
        } else if (stom_time <= alarmStormRecordMap.diffCurrentTime(Integer.parseInt(happenTime))) {
          System.out.println("define storm time  <=  :");
          // send to event queue
          insertEvenList(processType,evenType,resourceId,happenTime,iaadId,machineId,deviceId,eventInfo,extraContent);
          alarmStormRecordMap.add(evenType, resourceId, Integer.parseInt(happenTime), extraContent);
        } else {
          System.out.println("define storm time  >  :");
          //no event happen,insert into tl_event_src_* table
          insertSrcLogList(processType,evenType,resourceId,happenTime,iaadId,machineId,deviceId);
          alarmStormRecordMap.add(evenType, resourceId, Integer.parseInt(happenTime), extraContent);

        }
      }
    }
  }

  public void insertEvenList(ProcessType type,
                             String evenType,
                             int resourceId,
                             String happenTime,
                             int iaadId,
                             int machineId,
                             int deviceId,
                             EventInfo eventInfo, String extraContent) {
    switch (type) {
      case ProcessMonitorType:
        EventRecord eventRecord = EventRecord.newBuilder().sEventGenus("event_monitor").
                sEventType(evenType).sEventName(eventInfo.getEvent_name()).
                sEventDesc(eventInfo.getEvent_desc()).
                nEventlevel(eventInfo.getEvent_level()).
                nAutoReleaseInterval(eventInfo.getAuto_release_interval()).
                sHappentime(happenTime).
                sExtraDesc(extraContent).
                nResID(resourceId).
                sResName(ResUtil.ResID2ResName(resourceId)).
                bInsert2srcLog(true).
                bInsert2log(true).build();
        if(eventInfo.getEventLinkagelistSize() >  0){
          eventRecord.setEventLinkage(eventInfo.getEventLinkagelist());
          eventRecord.setLinkageFlag();
        }
        getEventRecordMap().add(eventRecord);
        break;
      case ProcessIaType:
        EventRecord eventRecord2 = EventRecord.newBuilder().sEventGenus("event_ia").
                sEventType(evenType).sEventName(eventInfo.getEvent_name()).
                sEventDesc(eventInfo.getEvent_desc()).
                nEventlevel(eventInfo.getEvent_level()).
                nAutoReleaseInterval(eventInfo.getAuto_release_interval()).
                sHappentime(happenTime).
                sExtraDesc(extraContent).
                nSvrID(iaadId).
                sSvrName(ResUtil.SvrID2SvrName(iaadId)).
                nResID(resourceId).
                sResName(ResUtil.ResID2ResName(resourceId)).
                bInsert2srcLog(true).
                bInsert2log(true).build();
        if(eventInfo.getEventLinkagelistSize() >  0){
          eventRecord2.setEventLinkage(eventInfo.getEventLinkagelist());
          eventRecord2.setLinkageFlag();
        }
        getEventRecordMap().add(eventRecord2);
        break;
      case ProcessSioType:
        EventRecord eventRecord3 = EventRecord.newBuilder().sEventGenus("event_sio").
                sEventType(evenType).sEventName(eventInfo.getEvent_name()).
                sEventDesc(eventInfo.getEvent_desc()).
                nEventlevel(eventInfo.getEvent_level()).
                nAutoReleaseInterval(eventInfo.getAuto_release_interval()).
                sHappentime(happenTime).
                sExtraDesc(extraContent).
                nResID(resourceId).
                sResName(ResUtil.ResID2ResName(resourceId)).
                bInsert2srcLog(true).
                bInsert2log(true).build();
        if(eventInfo.getEventLinkagelistSize() >  0){
          eventRecord3.setEventLinkage(eventInfo.getEventLinkagelist());
          eventRecord3.setLinkageFlag();
        }
        getEventRecordMap().add(eventRecord3);
        break;
      case ProcessServerType:
        EventRecord eventRecord4 = EventRecord.newBuilder().sEventGenus("event_server").
                sEventType(evenType).sEventName(eventInfo.getEvent_name()).
                sEventDesc(eventInfo.getEvent_desc()).
                nEventlevel(eventInfo.getEvent_level()).
                nAutoReleaseInterval(eventInfo.getAuto_release_interval()).
                sHappentime(happenTime).
                sExtraDesc(extraContent).
                nMachineID(machineId).
                sMachineName(ResUtil.MachineID2MachineName(machineId)).
                bInsert2srcLog(true).bInsert2log(true).build();
        if(eventInfo.getEventLinkagelistSize() >  0){
          eventRecord4.setEventLinkage(eventInfo.getEventLinkagelist());
          eventRecord4.setLinkageFlag();
        }
        getEventRecordMap().add(eventRecord4);
        break;
      case ProcessDeviceType:
        EventRecord eventRecord5 = EventRecord.newBuilder().sEventGenus("event_device").
                sEventType(evenType).sEventName(eventInfo.getEvent_name()).
                sEventDesc(eventInfo.getEvent_desc()).
                nEventlevel(eventInfo.getEvent_level()).
                nAutoReleaseInterval(eventInfo.getAuto_release_interval()).
                sHappentime(happenTime).
                sExtraDesc(extraContent).
                nDevID(deviceId).
                sDevName(ResUtil.DevID2DevName(deviceId)).
                bInsert2srcLog(true).bInsert2log(true).build();
        if(eventInfo.getEventLinkagelistSize() >  0){
          eventRecord5.setEventLinkage(eventInfo.getEventLinkagelist());
          eventRecord5.setLinkageFlag();
        }
        getEventRecordMap().add(eventRecord5);
        break;
      default:
        break;
    }
    return;
  }

  public void insertSrcLogList(ProcessType type,
                               String evenType,
                               int resourceId,
                               String happenTime,
                               int iaadId,
                               int machineId,
                               int deviceId){
    switch (type) {
      case ProcessMonitorType:
        EventRecord eventRecord = EventRecord.newBuilder().sEventType(evenType).nResID(resourceId).
                sResName(ResUtil.ResID2ResName(resourceId)).sHappentime(happenTime).
                bInsert2srcLog(true).sEventGenus("event_monitor").build();
        getEventRecordMap().add(eventRecord);
        break;
      case ProcessIaType:
        EventRecord eventRecord2 = EventRecord.newBuilder().sEventType(evenType).nSvrID(iaadId).
                sSvrName(ResUtil.SvrID2SvrName(iaadId)).sHappentime(happenTime).
                nResID(resourceId).sResName(ResUtil.ResID2ResName(resourceId)).
                bInsert2srcLog(true).sEventGenus("event_ia").build();
        getEventRecordMap().add(eventRecord2);
        break;
      case ProcessSioType:
        EventRecord eventRecord3 = EventRecord.newBuilder().sEventType(evenType).nResID(resourceId).
                sResName(ResUtil.ResID2ResName(resourceId)).sHappentime(happenTime).
                bInsert2srcLog(true).sEventGenus("event_sio").build();
        getEventRecordMap().add(eventRecord3);
        break;
      case ProcessServerType:
        EventRecord eventRecord4 = EventRecord.newBuilder().sEventType(evenType).nMachineID(machineId).
                sMachineName(ResUtil.MachineID2MachineName(machineId)).sHappentime(happenTime).
                bInsert2srcLog(true).sEventGenus("event_server").build();
        getEventRecordMap().add(eventRecord4);
        break;
      case ProcessDeviceType:
        EventRecord eventRecord5 = EventRecord.newBuilder().sEventType(evenType).nDevID(deviceId).
                sDevName(ResUtil.DevID2DevName(deviceId)).sHappentime(happenTime).
                bInsert2srcLog(true).sEventGenus("event_device").build();
        getEventRecordMap().add(eventRecord5);
        break;
      default:
        break;
    }
  }



  public void insertSrcLog2db(Connection conn){

  }
  public void insertLog2db(Connection conn){ }
  public void insertAllLog2db(Connection conn){}

}


