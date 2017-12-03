package com.vorxsoft.ieye.eventservice.process;

import com.google.protobuf.InvalidProtocolBufferException;
import com.googlecode.protobuf.format.JsonFormat;
import com.vorxsoft.ieye.eventservice.config.*;
import com.vorxsoft.ieye.eventservice.db.*;
import com.vorxsoft.ieye.eventservice.grpc.VsIeyeClient;
import com.vorxsoft.ieye.eventservice.mq.Publisher;
import com.vorxsoft.ieye.eventservice.redis.AlarmStormRecordMap;
import com.vorxsoft.ieye.eventservice.redis.EventRecord;
import com.vorxsoft.ieye.eventservice.redis.EventRecordMap;
import com.vorxsoft.ieye.eventservice.util.ResUtil;
import com.vorxsoft.ieye.eventservice.util.TimeUtil;
import com.vorxsoft.ieye.proto.ReloadRequest;
import com.vorxsoft.ieye.proto.ReportEventRequest;
import com.vorxsoft.ieye.proto.ReportLinkageRequest;
import redis.clients.jedis.Jedis;

import javax.jms.JMSException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.vorxsoft.ieye.eventservice.process.AlarmProcess.ProcessType.*;

public class AlarmProcess implements Runnable {
  private String name;
  private ProcessType processType;
  private EventConfig eventConfig;
  //private AlarmStormConfig alarmStormConfig;
  private AlarmStormRecordMap alarmStormRecordMap;
  private EventRecordMap eventRecordMap;
  private Jedis jedis;
  private Connection conn;
  VsIeyeClient cmsIeyeClient;
  VsIeyeClient blgTeyeClient;
  //HashMap<String,VsIeyeClient>
  Publisher publisher;

  public AlarmProcess() {
  }


//  public AlarmStormConfig getAlarmStormConfig() {
//    return alarmStormConfig;
//  }
//
//  public void setAlarmStormConfig(AlarmStormConfig alarmStormConfig) {
//    this.alarmStormConfig = alarmStormConfig;
//  }

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

  public Connection getConn() {
    return conn;
  }

  public void setConn(Connection conn) {
    this.conn = conn;
  }

  public VsIeyeClient getCmsIeyeClient() {
    return cmsIeyeClient;
  }

  public void setCmsIeyeClient(VsIeyeClient cmsIeyeClient) {
    this.cmsIeyeClient = cmsIeyeClient;
  }

  public VsIeyeClient getBlgTeyeClient() {
    return blgTeyeClient;
  }

  public void setBlgTeyeClient(VsIeyeClient blgTeyeClient) {
    this.blgTeyeClient = blgTeyeClient;
  }

  public Publisher getPublisher() {
    return publisher;
  }

  public void setPublisher(Publisher publisher) {
    this.publisher = publisher;
  }

  public enum ProcessType {
    ProcessMonitorType,
    ProcessIaType,
    ProcessSioType,
    ProcessServerType,
    ProcessDeviceType,
  }

  public void dbInit(String dbname,String dbAddress,String driverClassName,String dbUser,String dbPasswd) throws SQLException, ClassNotFoundException {
    String dbUrl = "jdbc:"+dbname+"://"+dbAddress;
    System.out.println("db url :" + dbUrl);
    Class.forName(driverClassName);
    conn = DriverManager.getConnection(dbUrl,dbUser,dbPasswd);
    //st = conn.createStatement();
  }

  public void redisInit(String redisIP, int redisPort){
    jedis  = new  Jedis(redisIP, redisPort);
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
        processAlarm();
        Thread.sleep((int) Math.random() * 10);
        processEvent();
        Thread.sleep((int) Math.random() * 10);
        //updateConfig();
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

  public void processAlarm() throws Exception {
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
      AlarmStorm alarmStorm = getEventConfig().getAlarmStormConfig().getAlarmStorm(evenType);
      if (alarmStorm == null) {
        System.out.println("no alarm storm config for alarm evenType:" + evenType +
                "happenTime" + happenTime + "extraContent" + extraContent +
                "resourceId:" + resourceId + " iaadId:" + iaadId + " iauId" + iauId +
                "machineId:" + machineId + " deviceId:" + deviceId);
        System.out.println("send to event queue");
        // send to event queue
        insertEvenList(processType,evenType,resourceId,happenTime,iaadId,machineId,deviceId,eventInfo,extraContent);
      } else {
        int stom_time = alarmStorm.getEventStom();
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

  public void processEvent() throws SQLException, InvalidProtocolBufferException, JMSException {
    int LogId = 0;
    Iterator<EventRecord>  it = getEventRecordMap().getEventRecords().iterator();
    while(it.hasNext()){
      EventRecord record = it.next();
      if (record.isbInsert2log()) {
        EventLog eventLog = new EventLog().newBuilder().sEventGenus(record.getsEventGenus()).
            sEventType(record.getsEventType()).sEventName(record.getsEventName()).
            sEventDesc(record.getsEventDesc()).nEventlevel(record.getnEventlevel()).
            tHappenTime(TimeUtil.string2timestamp(record.getsHappentime())).
            sExtraDesc(record.getsExtraDesc()).build();
        LogId = eventLog.insert2db(conn);
        if (LogId <= 0) {
          System.out.println("insert event  log error");
        }
        it.next().setnEventlogID(LogId);
        record.setnEventlogID(LogId);
      }
      switch (processType) {
        case ProcessMonitorType:
          if (record.isbInsert2srcLog()) {
            EventSrcMonitor eventSrcMonitor = EventSrcMonitor.newBuilder().
                                              nResID(record.getnResID()).
                                              sResName(record.getsResName()).build();
            eventSrcMonitor.setnEventLogId(LogId);
            eventSrcMonitor.setsEventType(record.getsEventType());
            eventSrcMonitor.settHappenTime(TimeUtil.string2timestamp(record.getsHappentime()));
            if (!eventSrcMonitor.insert2db(conn)) {
              System.out.println("insert event  src monitor log error");
            }
          }
          break;
        case ProcessIaType:
          if (record.isbInsert2srcLog()) {
            EventSrcIA eventSrcIA = EventSrcIA.newBuilder().nSvrID(record.getnSvrID()).
                                                            sSvrName(record.getsSvrName()).
                                                            nResID(record.getnResID()).
                                                            sResName(record.getsResName()).build();
            eventSrcIA.setnEventLogId(LogId);
            eventSrcIA.setsEventType(record.getsEventType());
            eventSrcIA.settHappenTime(TimeUtil.string2timestamp(record.getsHappentime()));
            if (!eventSrcIA.insert2db(conn)) {
              System.out.println("insert event  src ia log error");
            }
          }
          break;
        case ProcessSioType:
          if (record.isbInsert2srcLog()) {
            EventSrcSio eventSrcSio = EventSrcSio.newBuilder().
                                                  nResID(record.getnResID()).
                                                  sResName(record.getsResName()).build();
            eventSrcSio.setnEventLogId(LogId);
            eventSrcSio.setsEventType(record.getsEventType());
            eventSrcSio.settHappenTime(TimeUtil.string2timestamp(record.getsHappentime()));
            if (!eventSrcSio.insert2db(conn)) {
              System.out.println("insert event  src sio log error");
            }
          }
          break;
        case ProcessServerType:
          if (record.isbInsert2srcLog()) {
            EventSrcMachine eventSrcMachine = EventSrcMachine.newBuilder().
                                                              nMachineID(record.getnMachineID()).
                                                              sMachineName(record.getsMachineName()).build();
            eventSrcMachine.setnEventLogId(LogId);
            eventSrcMachine.setsEventType(record.getsEventType());
            eventSrcMachine.settHappenTime(TimeUtil.string2timestamp(record.getsHappentime()));
            if (!eventSrcMachine.insert2db(conn)) {
              System.out.println("insert event  src sio log error");
            }
          }
          break;
        case ProcessDeviceType:
          if (record.isbInsert2srcLog()) {
            EventSrcDev eventSrcDev = EventSrcDev.newBuilder().
                                                  nDevID(record.getnDevID()).
                                                  sDevName(record.getsDevName()).build();
            eventSrcDev.setnEventLogId(LogId);
            eventSrcDev.setsEventType(record.getsEventType());
            eventSrcDev.settHappenTime(TimeUtil.string2timestamp(record.getsHappentime()));
            if (!eventSrcDev.insert2db(conn)) {
              System.out.println("insert event  src sio log error");
            }
          }
          break;
        default:
          break;
      }
      //send to blg
      if(record.isbSend2blg()){

      }
      //send to mq
      if(record.isbSend2mq()){

      }
      //send to cms
      if(record.isbSend2cms()){

      }
      //it.remove();
    }
    ReportEventRequest eventRequest = getEventRecordMap().convert2ReportEventRequest();
    if( eventRequest != null || !eventRequest.isInitialized()){
      getCmsIeyeClient().reportEvent(eventRequest);
    }
    ReportLinkageRequest linkageReq = getEventRecordMap().convert2ReportLinkageRequest();
    if((linkageReq != null) || !linkageReq.isInitialized()){
      getBlgTeyeClient().reportLinkage(linkageReq);
    }
    publisher.publishMsg(getEventRecordMap().convert2jsonString());
  }


}


