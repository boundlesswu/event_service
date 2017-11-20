package com.vorxsoft.ieye.eventservice.process;

import com.vorxsoft.ieye.eventservice.config.AlarmStorm;
import com.vorxsoft.ieye.eventservice.config.AlarmStormConfig;
import com.vorxsoft.ieye.eventservice.config.EventConfig;
import com.vorxsoft.ieye.eventservice.redis.AlarmStormRecordMap;
import com.vorxsoft.ieye.eventservice.redis.eventRecordMap;
import redis.clients.jedis.Jedis;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

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

  enum ProcessType{
    ProcessMonitorType,
    ProcessIaType,
    ProcessSioType,
    ProcessServerType,
    ProcessDeviceType,
    ProcessAlarmStorm;
  }

  private  ProcessType processType;
  private eventRecordMap eventRecordMap;
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

  AlarmProcess(String name, ProcessType type, eventRecordMap eventRecordMap) {
    this.name = name;
    this.processType = type;
    this.eventRecordMap = eventRecordMap;
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
            processAlarmStorm(alarmStormConfig,alarmStormRecordMap);
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

  public void processAlarmStorm(AlarmStormConfig alarmStormConfig,AlarmStormRecordMap asrm) throws SQLException {
    Set<String> set = jedis.keys("alarm_" +"*");
    Iterator<String> it = set.iterator();
    while(it.hasNext()){
      String keyStr = it.next();
      System.out.println(keyStr);
      Map<String, String> map = jedis.hgetAll(keyStr);
      String  sCount =  keyStr.substring(6);
      String type = map.get("event_type");
      String resourceId = map.get("resourceId");
      String resourceNo = map.get("resourceNo");
      String  happenTime = map.get("happenTime");
      String extraContent = map.get("extraContent");
      AlarmStorm alarmStorm =  alarmStormConfig.getAlarmStorm(type);
      if(alarmStorm == null){
        System.out.println("no alarm storm config of event_type:"+ type);
        continue;
      }
      Long stom_time =  alarmStorm.getEvent_stom();
      //need event process
      if( stom_time == 0){
        System.out.println("no alarm storm config of event_type:"+ type);
        eventRecordMap.alarmMap2eventMap(map,jedis,Integer.parseInt(sCount));
        asrm.add(type,Integer.parseInt(resourceId),resourceNo,Integer.parseInt(happenTime),extraContent);
        continue;
      }else if( stom_time <= asrm.diffCurrentTime(Integer.parseInt(happenTime))){
        System.out.println("define storm time  <=  :");
        eventRecordMap.alarmMap2eventMap(map,jedis,Integer.parseInt(sCount));
        asrm.add(type,Integer.parseInt(resourceId),resourceNo,Integer.parseInt(happenTime),extraContent);
      }else{
        System.out.println("define storm time  >  :");
        asrm.add(type,Integer.parseInt(resourceId),resourceNo,Integer.parseInt(happenTime),extraContent);
      }
      jedis.del(keyStr);
    }
  }

}


