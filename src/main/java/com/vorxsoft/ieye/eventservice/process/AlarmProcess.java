package com.vorxsoft.ieye.eventservice.process;

import com.vorxsoft.ieye.eventservice.config.*;
import com.vorxsoft.ieye.eventservice.redis.AlarmStormRecordMap;
import com.vorxsoft.ieye.eventservice.redis.EventRecordMap;
import redis.clients.jedis.Jedis;

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
  private AlarmStormConfig alarmStormConfig;
  private AlarmStormRecordMap alarmStormRecordMap;
  private Jedis jedis;

  public ProcessType getProcessType() {
    return processType;
  }

  public void setProcessType(ProcessType processType) {
    this.processType = processType;
  }

  enum ProcessType {
    ProcessMonitorType,
    ProcessIaType,
    ProcessSioType,
    ProcessServerType,
    ProcessDeviceType,
    ProcessAlarmStorm;
  }

  private ProcessType processType;
  private EventRecordMap EventRecordMap;

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

  AlarmProcess(String name, ProcessType type, EventRecordMap EventRecordMap) {
    this.name = name;
    this.processType = type;
    this.EventRecordMap = EventRecordMap;
  }

  public AlarmProcess(String name) {
    this.name = name;
  }


  @Override
  public void run() {
    for (int i = 0; ; i++) {
      System.out.println(name + "运行  :  " + i);
      try {
        switch (processType) {
          case ProcessMonitorType:
            break;
          case ProcessIaType:
            break;
          case ProcessSioType:
            break;
          case ProcessServerType:
            break;
          case ProcessDeviceType:
            break;
          case ProcessAlarmStorm:
            processAlarmStorm(alarmStormConfig, alarmStormRecordMap);
            break;
        }
        Thread.sleep((int) Math.random() * 10);
        //Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      } catch (SQLException e) {
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

  public void processAlarm(EventConfig eventConfig, ProcessType processType) throws Exception {
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
        patterKey = "alarm_device_*"
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
        EventConfig.MonitorConfigKey monitorConfigKey = new EventConfig.MonitorConfigKey(evenType, resourceId);
        eventInfo = eventConfig.getMonitorConfig(monitorConfigKey);
      } else if (processType == ProcessSioType) {
        resourceId = Integer.parseInt(map.get("resourceId"));
        EventConfig.SioConfigKey sioConfigKey = new EventConfig.SioConfigKey(evenType, resourceId);
        eventInfo = eventConfig.getSioConfig(sioConfigKey);
      } else if (processType == ProcessIaType) {
        iaadId = Integer.parseInt(map.get("iaagId"));
        iauId = Integer.parseInt(map.get("iauId"));
        EventConfig.IaConfigKey iaConfigKey = new EventConfig.IaConfigKey(evenType, resourceId, iaadId, iauId;
        eventInfo = eventConfig.getIaConfig(iaConfigKey);
      } else if (processType == ProcessServerType) {
        machineId = Integer.parseInt(map.get("machineId"));
        EventConfig.ServerConfigKey serverConfigKey = new EventConfig.ServerConfigKey(evenType, machineId);
        eventInfo = eventConfig.getServerConfig(serverConfigKey);
      } else if (processType == ProcessDeviceType) {
        deviceId = Integer.parseInt(map.get("deviceId"));
        EventConfig.DeviceConfigKey deviceConfigKey = new EventConfig.DeviceConfigKey(evenType, deviceId);
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
        //insert into tl_event_src_monitor table todo
        continue;
      }
      GuardPlan guardPlan = eventInfo.getGuardPlan();
      if (guardPlan == null || !guardPlan.isInGuardPlan(happenTime)) {
        System.out.println("No guard plan  config for alarm evenType:" + evenType +
                "happenTime" + happenTime + "extraContent" + extraContent +
                "resourceId:" + resourceId + " iaadId:" + iaadId + " iauId" + iauId +
                "machineId:" + machineId + " deviceId:" + deviceId);
        //insert into tl_event_src_monitor table todo
        continue;
      }
      AlarmStorm alarmStorm = alarmStormConfig.getAlarmStorm(evenType);
      if (alarmStorm == null) {
        System.out.println("no alarm storm config for alarm evenType:" + evenType +
                "happenTime" + happenTime + "extraContent" + extraContent +
                "resourceId:" + resourceId + " iaadId:" + iaadId + " iauId" + iauId +
                "machineId:" + machineId + " deviceId:" + deviceId);
        System.out.println("send to event queue");
        // send to event queue todo
      } else {
        Long stom_time = alarmStorm.getEvent_stom();
        if (stom_time == 0) {
          System.out.println("storm time == 0 no alarm storm config of event_type:" + evenType +
                  "happenTime" + happenTime + "extraContent" + extraContent +
                  "resourceId:" + resourceId + " iaadId:" + iaadId + " iauId" + iauId +
                  "machineId:" + machineId + " deviceId:" + deviceId);
          System.out.println("send to event queue");
          // send to event queue todo
        } else if (stom_time <= alarmStormRecordMap.diffCurrentTime(Integer.parseInt(happenTime))) {
          System.out.println("define storm time  <=  :");
          EventRecordMap.alarmMap2eventMap(map, jedis, Integer.parseInt(sCount));
          alarmStormRecordMap.add(evenType, resourceId, Integer.parseInt(happenTime), extraContent);
        } else {
          System.out.println("define storm time  >  :");
          alarmStormRecordMap.add(evenType, resourceId, Integer.parseInt(happenTime), extraContent);
          //insert into tl_event_src_monitor table todo
        }
      }
    }
  }
public void  4.19.3event_src_monitor

  public void processAlarmStorm(AlarmStormConfig alarmStormConfig, AlarmStormRecordMap asrm) throws SQLException {
    Set<String> set = jedis.keys("alarm_" + "*");
    Iterator<String> it = set.iterator();
    while (it.hasNext()) {
      String keyStr = it.next();
      System.out.println(keyStr);
      Map<String, String> map = jedis.hgetAll(keyStr);
      String sCount = keyStr.substring(6);
      String type = map.get("event_type");
      String resourceId = map.get("resourceId");
      String resourceNo = map.get("resourceNo");
      String happenTime = map.get("happenTime");
      String extraContent = map.get("extraContent");
      AlarmStorm alarmStorm = alarmStormConfig.getAlarmStorm(type);
      if (alarmStorm == null) {
        System.out.println("no alarm storm config of event_type:" + type);
        continue;
      }
      Long stom_time = alarmStorm.getEvent_stom();
      //need event process
      if (stom_time == 0) {
        System.out.println("no alarm storm config of event_type:" + type);
        EventRecordMap.alarmMap2eventMap(map, jedis, Integer.parseInt(sCount));
        asrm.add(type, Integer.parseInt(resourceId), resourceNo, Integer.parseInt(happenTime), extraContent);
        continue;
      } else if (stom_time <= asrm.diffCurrentTime(Integer.parseInt(happenTime))) {
        System.out.println("define storm time  <=  :");
        EventRecordMap.alarmMap2eventMap(map, jedis, Integer.parseInt(sCount));
        asrm.add(type, Integer.parseInt(resourceId), resourceNo, Integer.parseInt(happenTime), extraContent);
      } else {
        System.out.println("define storm time  >  :");
        asrm.add(type, Integer.parseInt(resourceId), resourceNo, Integer.parseInt(happenTime), extraContent);
      }
      jedis.del(keyStr);
    }
  }

}


