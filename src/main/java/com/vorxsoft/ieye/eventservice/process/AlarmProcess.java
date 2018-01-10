package com.vorxsoft.ieye.eventservice.process;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;
import com.vorxsoft.ieye.eventservice.config.*;
import com.vorxsoft.ieye.eventservice.db.*;
import com.vorxsoft.ieye.eventservice.grpc.VsIeyeClient;
import com.vorxsoft.ieye.eventservice.mq.Publisher;
import com.vorxsoft.ieye.eventservice.redis.AlarmStormInfo;
import com.vorxsoft.ieye.eventservice.redis.AlarmStormRecordMap;
import com.vorxsoft.ieye.eventservice.redis.EventRecord;
import com.vorxsoft.ieye.eventservice.redis.EventRecordMap;
import com.vorxsoft.ieye.eventservice.util.*;
import com.vorxsoft.ieye.proto.EventWithLinkage;
import com.vorxsoft.ieye.proto.ReportLinkageRequest;
import javafx.scene.paint.LinearGradient;
import org.apache.logging.log4j.Logger;
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
  //private Jedis jedis;
  private RedisUtil redisUtil = null;
  private Connection conn;
  VsIeyeClient cmsIeyeClient;
  VsIeyeClient blgTeyeClient;


  //HashMap<String,VsIeyeClient>
  private static Logger logger;
  private Publisher publisher;
  private IaagMap iaagMap;


  public static Logger getLogger() {
    return logger;
  }

  public static void setLogger(Logger logger) {
    AlarmProcess.logger = logger;
  }

  public IaagMap getIaagMap() {
    return iaagMap;
  }

  public void setIaagMap(IaagMap iaagMap) {
    this.iaagMap = iaagMap;
  }

  public Publisher getPublisher() {
    return publisher;
  }

  public void setPublisher(Publisher publisher) {
    this.publisher = publisher;
  }


  public ResUtil getResUtil() {
    return resUtil;
  }

  public void setResUtil(ResUtil resUtil) {
    this.resUtil = resUtil;
  }

  private ResUtil resUtil;

  public AlarmProcess() {
    this.eventConfig = new EventConfig();
    this.alarmStormRecordMap = new AlarmStormRecordMap();
    this.eventRecordMap = new EventRecordMap();
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

  public RedisUtil getRedisUtil() {
    return redisUtil;
  }

  public void setRedisUtil(RedisUtil redisUtil) {
    this.redisUtil = redisUtil;
  }

  public void mqInit(String ip, int port) {
    publisher = new Publisher(ip, port);
  }


  public int init() {
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

  public enum ProcessType {
    ProcessMonitorType,
    ProcessIaType,
    ProcessSioType,
    ProcessServerType,
    ProcessDeviceType,
  }

  public void dbInit(String dbname, String dbAddress, String driverClassName, String dbUser, String dbPasswd) throws SQLException, ClassNotFoundException {
    String dbUrl = "jdbc:" + dbname + "://" + dbAddress;
    System.out.println("db url :" + dbUrl);
    Class.forName(driverClassName);
    conn = DriverManager.getConnection(dbUrl, dbUser, dbPasswd);
    //st = conn.createStatement();
    resUtil = new ResUtilImpl();
    resUtil.init(conn);
  }

//  public void redisInit(String redisIP, int redisPort) {
//    redisUtil = new RedisUtil(redisIP,redisPort);
//    //jedis = new Jedis(redisIP, redisPort);
//  }

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
      //System.out.println(name + "运行  :  " + i);
      try {
        processAlarm();
        Thread.sleep((int) Math.random() * 10);
        processEvent();
        Thread.sleep((int) Math.random() * 10);
        if (i % 200000 == 0) {
          //System.out.println("process :" + getName() + getProcessType() + "is running");
          getLogger().debug("process :" + getName() + " " + getProcessType() + " is running!!! " +
                  Thread.currentThread().getName() + " " + Thread.currentThread().getId());
        }
      } catch (InterruptedException e) {
        e.printStackTrace();
        getLogger().error(e.getMessage(), e);
      } catch (Exception e) {
        e.printStackTrace();
        getLogger().error(e.getMessage(), e);
      }
    }

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
    //Set<String> set = jedis.keys(patterKey);
    Set<String> set = redisUtil.keys(patterKey);
    if (set.size() == 0) {
      //System.out.println("patterKey :" + patterKey + "is not exist");
      return;
    }
    Iterator<String> it = set.iterator();
    String sCount = "";
    String evenType = "";
    int resourceId = 0;
    String happenTime = "";
    String extraContent = "";
    int iaag_chn_id = 0;
    int iaadId = 0;
    int iauId = 0;
    int machineId = 0;
    int deviceId = 0;
    EventInfo eventInfo = null;
    while (it.hasNext()) {
      String keyStr = it.next();
      System.out.println(keyStr);
      //Map<String, String> map = jedis.hgetAll(keyStr);
      Map<String, String> map = redisUtil.hgetAll(keyStr);
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
        resourceId = Integer.parseInt(map.get("resourceId"));
        iaadId = Integer.parseInt(map.get("iaagId"));
        iauId = Integer.parseInt(map.get("iauId"));
        iaag_chn_id = (getIaagMap() == null) ? 0 : getIaagMap().getIaag_chn_id(iaadId, resourceId);
        IaConfigKey iaConfigKey = new IaConfigKey(evenType, resourceId, iaadId, iaag_chn_id);
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
      //jedis.del(keyStr);
      redisUtil.del(keyStr);
      if (eventInfo == null) {
        getLogger().warn("No event config for alarm evenType:" + evenType +
                " happenTime " + happenTime + " extraContent: " + extraContent +
                " resourceId: " + resourceId + " iaagId: " + iaadId + " iauId: " + iauId +
                " machineId: " + machineId + " deviceId: " + deviceId + " iaag_chn_id: " + iaag_chn_id);
        System.out.println("No event config for alarm evenType:" + evenType +
                " happenTime " + happenTime + " extraContent " + extraContent +
                " resourceId: " + resourceId + " iaagId: " + iaadId + " iauId: " + iauId +
                " machineId: " + machineId + " deviceId: " + deviceId + " iaag_chn_id: " + iaag_chn_id);
        //no event happen,insert into tl_event_src_monitor table
        insertSrcLogList(processType, evenType, resourceId, happenTime, iaadId, machineId, deviceId);
        continue;
      }
      GuardPlan guardPlan = eventInfo.getGuardPlan();
      if (guardPlan == null || !guardPlan.isInGuardPlan(happenTime)) {
        if (guardPlan == null) {
          getLogger().warn("No guard plan  config for alarm evenType:" + evenType +
                  " happenTime: " + happenTime + " extraContent: " + extraContent +
                  " resourceId: " + resourceId + " iaagId: " + iaadId + " iauId: " + iauId +
                  " machineId: " + machineId + " deviceId: " + deviceId + " iaag_chn_id: " + iaag_chn_id);
        } else if (!guardPlan.isInGuardPlan(happenTime)) {
          getLogger().warn("guard plan :" + guardPlan.toString());
          getLogger().warn("alarm info is  evenType:" + evenType +
                  " happenTime: " + happenTime + " extraContent: " + extraContent +
                  " resourceId: " + resourceId + " iaagId: " + iaadId + " iauId: " + iauId +
                  " machineId: " + machineId + " deviceId: " + deviceId + " iaag_chn_id: " + iaag_chn_id);
          getLogger().warn("alarm is not in guard plan");
        }
        //no event happen,insert into tl_event_src_* table
        insertSrcLogList(processType, evenType, resourceId, happenTime, iaadId, machineId, deviceId);
        continue;
      }
      AlarmStorm alarmStorm = null;
      if (getEventConfig().getAlarmStormConfig() != null)
        alarmStorm = getEventConfig().getAlarmStormConfig().getAlarmStorm(evenType);
      if (alarmStorm == null) {
        getLogger().info("no alarm storm config for alarm evenType: " + evenType +
                " happenTime: " + happenTime + " extraContent: " + extraContent +
                " resourceId: " + resourceId + " iaagId: " + iaadId + " iauId: " + iauId +
                " machineId: " + machineId + " deviceId: " + deviceId + " iaag_chn_id: " + iaag_chn_id);
        getLogger().info("alarm matching event_id" + eventInfo.toString());
        System.out.println("no alarm storm config for alarm evenType:" + evenType +
                " happenTime: " + happenTime + " extraContent: " + extraContent +
                " resourceId: " + resourceId + " iaagId: " + iaadId + " iauId: " + iauId +
                " machineId: " + machineId + " deviceId: " + deviceId + " iaag_chn_id: " + iaag_chn_id);
        System.out.println("send to event queue");
        // send to event queue
        insertEvenList(processType, evenType, resourceId, happenTime, iaadId, machineId, deviceId, eventInfo, extraContent);
      } else {
        int stom_time = alarmStorm.getEventStom();
        if (stom_time == 0) {
          System.out.println("storm time == 0 no alarm storm config of event_type:" + evenType +
                  " happenTime: " + happenTime + " extraContent: " + extraContent +
                  " resourceId: " + resourceId + " iaagId: " + iaadId + " iauId: " + iauId +
                  " machineId: " + machineId + " deviceId: " + deviceId + " iaag_chn_id: " + iaag_chn_id);
          System.out.println("send to event queue");
          getLogger().info("storm time == 0 no alarm storm config of event_type:" + evenType +
                  " happenTime: " + happenTime + " extraContent: " + extraContent +
                  " resourceId: " + resourceId + " iaagId: " + iaadId + " iauId: " + iauId +
                  " machineId: " + machineId + " deviceId: " + deviceId + " iaag_chn_id: " + iaag_chn_id);
          getLogger().info("alarm matching event_id" + eventInfo.toString());
          // send to event queue
          insertEvenList(processType, evenType, resourceId, happenTime, iaadId, machineId, deviceId, eventInfo, extraContent);
        } else if (stom_time <= alarmStormRecordMap.diffCurrentTime(happenTime, eventInfo)) {
          System.out.println("define storm time  <=  :");
          getLogger().info("define storm time  <=  :event_type:" + evenType +
                  " happenTime: " + happenTime + " extraContent: " + extraContent +
                  " resourceId: " + resourceId + " iaagId: " + iaadId + " iauId: " + iauId +
                  " machineId: " + machineId + " deviceId: " + deviceId + " iaag_chn_id: " + iaag_chn_id);
          getLogger().info("alarm matching event_id" + eventInfo.toString());
          // send to event queue
          insertEvenList(processType, evenType, resourceId, happenTime, iaadId, machineId, deviceId, eventInfo, extraContent);
          alarmStormRecordMap.update(TimeUtil.string2timestamplong(happenTime), alarmStorm.getStomId(), eventInfo);
        } else {
          System.out.println("define storm time  >  :");
          getLogger().warn("define storm time  >  ::event_type:" + evenType +
                  " happenTime: " + happenTime + " extraContent: " + extraContent +
                  " resourceId: " + resourceId + " iaagId: " + iaadId + " iauId: " + iauId +
                  " machineId: " + machineId + " deviceId: " + deviceId + " iaag_chn_id: " + iaag_chn_id);
          getLogger().info("alarm not matching event_id" + eventInfo.toString());
          //no event happen,insert into tl_event_src_* table
          insertSrcLogList(processType, evenType, resourceId, happenTime, iaadId, machineId, deviceId);
          //alarmStormRecordMap.update(TimeUtil.string2timestamplong(happenTime),alarmStorm.getStomId(),eventInfo);
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
                nEventlogID(0).
                sResName(getResUtil().getResName(resourceId)).
                nEventID(eventInfo.getEvent_id()).
                bInsert2srcLog(true).
                bInsert2log(true).build();
        if (eventInfo.getEventLinkagelistSize() > 0) {
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
                sSvrName(getResUtil().getSvrName(iaadId)).
                nResID(resourceId).
                nEventlogID(0).
                sResName(getResUtil().getResName(resourceId)).
                nEventID(eventInfo.getEvent_id()).
                bInsert2srcLog(true).
                bInsert2log(true).build();
        if (eventInfo.getEventLinkagelistSize() > 0) {
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
                nEventlogID(0).
                sResName(getResUtil().getResName(resourceId)).
                nEventID(eventInfo.getEvent_id()).
                bInsert2srcLog(true).
                bInsert2log(true).build();
        if (eventInfo.getEventLinkagelistSize() > 0) {
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
                nEventlogID(0).
                sMachineName(getResUtil().getMachineName(machineId)).
                nEventID(eventInfo.getEvent_id()).
                bInsert2srcLog(true).bInsert2log(true).build();
        if (eventInfo.getEventLinkagelistSize() > 0) {
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
                nEventlogID(0).
                sDevName(getResUtil().getDevName(deviceId)).
                nEventID(eventInfo.getEvent_id()).
                bInsert2srcLog(true).bInsert2log(true).build();
        if (eventInfo.getEventLinkagelistSize() > 0) {
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
                               int deviceId) {
    switch (type) {
      case ProcessMonitorType:
        EventRecord eventRecord = EventRecord.newBuilder().sEventType(evenType).nResID(resourceId).
                sResName(getResUtil().getResName(resourceId)).sHappentime(happenTime).
                bInsert2srcLog(true).sEventGenus("event_monitor").build();
        eventRecord.setnEventlogID(0);
        getEventRecordMap().add(eventRecord);
        break;
      case ProcessIaType:
        EventRecord eventRecord2 = EventRecord.newBuilder().sEventType(evenType).nSvrID(iaadId).
                sSvrName(getResUtil().getSvrName(iaadId)).sHappentime(happenTime).
                nResID(resourceId).sResName(getResUtil().getResName(resourceId)).
                bInsert2srcLog(true).sEventGenus("event_ia").build();
        eventRecord2.setnEventlogID(0);
        getEventRecordMap().add(eventRecord2);
        break;
      case ProcessSioType:
        EventRecord eventRecord3 = EventRecord.newBuilder().sEventType(evenType).nResID(resourceId).
                sResName(getResUtil().getResName(resourceId)).sHappentime(happenTime).
                bInsert2srcLog(true).sEventGenus("event_sio").build();
        eventRecord3.setnEventlogID(0);
        getEventRecordMap().add(eventRecord3);
        break;
      case ProcessServerType:
        EventRecord eventRecord4 = EventRecord.newBuilder().sEventType(evenType).nMachineID(machineId).
                sMachineName(getResUtil().getMachineName(machineId)).sHappentime(happenTime).
                bInsert2srcLog(true).sEventGenus("event_server").build();
        eventRecord4.setnEventlogID(0);
        getEventRecordMap().add(eventRecord4);
        break;
      case ProcessDeviceType:
        EventRecord eventRecord5 = EventRecord.newBuilder().sEventType(evenType).nDevID(deviceId).
                sDevName(getResUtil().getDevName(deviceId)).sHappentime(happenTime).
                bInsert2srcLog(true).sEventGenus("event_device").build();
        eventRecord5.setnEventlogID(0);
        getEventRecordMap().add(eventRecord5);
        break;
      default:
        break;
    }
  }

  public void processEvent() throws SQLException, InvalidProtocolBufferException, JMSException {
    int LogId = 0;
    if (getEventRecordMap().getEventRecords().isEmpty()) {
      //System.out.println("EventRecordMap is empty!!!!!!!!!!");
      return;
    }
    Iterator<EventRecord> it = getEventRecordMap().getEventRecords().iterator();
    while (it.hasNext()) {
      LogId = 0;
      EventRecord record = it.next();
      if (record.isbInsert2log()) {
        EventLog eventLog = new EventLog().newBuilder().sEventGenus(record.getsEventGenus()).
                sEventType(record.getsEventType()).sEventName(record.getsEventName()).
                sEventDesc(record.getsEventDesc()).nEventlevel(record.getnEventlevel()).
                tHappenTime(TimeUtil.string2timestamp(record.getsHappentime())).
                sExtraDesc(record.getsExtraDesc()).nEventId(record.getnEventID()).build();
        LogId = eventLog.insert2db(conn);
        if (LogId <= 0) {
          System.out.println("insert event  log error");
          getLogger().error("insert event  log error");
          continue;
        } else {
          //it.next().setnEventlogID(LogId);
          getLogger().debug("success insert event  log" + "event log id :" + LogId);
          record.setnEventlogID(LogId);
          record.setbInsert2log(false);
        }
      }
      if (LogId > 0) {
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
                getLogger().error("insert event  src monitor log error");
              } else {
                getLogger().debug("success insert event src monitor log" + "event log id :" + LogId);
                record.setbInsert2srcLog(false);
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
                getLogger().error("insert event  src ia log error");
              } else {
                getLogger().debug("success insert event src ia log" + "event log id :" + LogId);
                record.setbInsert2srcLog(false);
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
              System.out.println(eventSrcSio);
              if (!eventSrcSio.insert2db(conn)) {
                System.out.println("insert event  src sio log error");
                getLogger().error("insert event  src sio log error");
              } else {
                getLogger().debug("success insert event src sio log" + "event log id :" + LogId);
                record.setbInsert2srcLog(false);
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
                System.out.println("insert event  src server log error");
                getLogger().error("insert event  src server log error");
              } else {
                getLogger().debug("success insert event src service log" + "event log id :" + LogId);
                record.setbInsert2srcLog(false);
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
                System.out.println("insert event  src device log error");
                getLogger().error("insert event  src device log error");
              } else {
                getLogger().debug("success insert event src device log" + "event log id :" + LogId);
                record.setbInsert2srcLog(false);
              }
            }
            break;
          default:
            break;
        }
      } else {  //Log =  0 (donnot insert to src log db)
        record.setbInsert2srcLog(false);
      }
      //send to blg
      if (record.isbSend2blg()) {
        ReportLinkageRequest.Builder reqBuilder = ReportLinkageRequest.newBuilder().
                setSBusinessID("00000000");
        EventWithLinkage eventWithLinkage = record.covert2EventWithLinkage();
        reqBuilder.addEventWithLinkages(eventWithLinkage);
        if (getBlgTeyeClient() != null) {
          getBlgTeyeClient().reportLinkage(reqBuilder.build());
        }
        record.setbSend2blg(false);
      }
      //send to mq
      if (record.isbSend2mq()) {
//        ReportEventRequest.Builder reqBuiler = ReportEventRequest.newBuilder().setSBusinessID("00000000");
//        Events event = record.covert2Events();
//        reqBuiler.addEvents(event);
//        getPublisher().publishMsg(JsonFormat.printer().print(reqBuiler.build().toBuilder()));
//        record.setbSend2mq(false);
        ReportLinkageRequest.Builder reqBuilder = ReportLinkageRequest.newBuilder().
                setSBusinessID("00000000");
        EventWithLinkage eventWithLinkage = record.covert2EventWithLinkage();
        reqBuilder.addEventWithLinkages(eventWithLinkage);
        getPublisher().publishMsg(JsonFormat.printer().print(reqBuilder));
        record.setbSend2mq(false);
      }
      //send to cms
      if (record.isbSend2cms()) {
        //need not send to cms
        record.setbSend2cms(false);
      }
      //it.remove();
    }
    /*
    //send to cms
    ReportEventRequest eventRequest = getEventRecordMap().convert2ReportEventRequest();
    if( eventRequest != null || !eventRequest.isInitialized()){
      if(getCmsIeyeClient()!=null) {
        getCmsIeyeClient().reportEvent(eventRequest);
      }
    }
    */
//    ReportLinkageRequest linkageReq = getEventRecordMap().convert2ReportLinkageRequest();
//    if((linkageReq != null) || !linkageReq.isInitialized()){
//      if(getBlgTeyeClient()!=null) {
//        getBlgTeyeClient().reportLinkage(linkageReq);
//      }
//    }
//    if(getPublisher()!=null) {
//      publisher.publishMsg(getEventRecordMap().convert2jsonString());
//    }
    getEventRecordMap().clearRecord();
  }
}



