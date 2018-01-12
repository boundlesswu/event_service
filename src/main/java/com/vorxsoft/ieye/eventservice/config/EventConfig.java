package com.vorxsoft.ieye.eventservice.config;

import com.vorxsoft.ieye.eventservice.linkage.EventLinkage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static com.vorxsoft.ieye.eventservice.util.Constant.*;

public class EventConfig {
  public HashMap<Integer, EventInfo> getMonitorConfigList() {
    return monitorConfigList;
  }

  public HashMap<Integer, EventInfo> getIaConfigList() {
    return iaConfigList;
  }

  public HashMap<Integer, EventInfo> getSioConfigList() {
    return sioConfigList;
  }

  public HashMap<Integer, EventInfo> getServerConfigList() {
    return serverConfigList;
  }

  public HashMap<Integer, EventInfo> getDeviceConfigList() {
    return deviceConfigList;
  }

  public HashMap<MonitorConfigKey, EventInfo> getMonitorConfigList2() {
    return monitorConfigList2;
  }

  public HashMap<IaConfigKey, EventInfo> getIaConfigList2() {
    return iaConfigList2;
  }

  public HashMap<SioConfigKey, EventInfo> getSioConfigList2() {
    return sioConfigList2;
  }

  public HashMap<ServerConfigKey, EventInfo> getServerConfigList2() {
    return serverConfigList2;
  }

  public HashMap<DeviceConfigKey, EventInfo> getDeviceConfigList2() {
    return deviceConfigList2;
  }

  public void setMonitorConfigList(HashMap<Integer, EventInfo> monitorConfigList) {
    this.monitorConfigList = monitorConfigList;
  }

  public void setIaConfigList(HashMap<Integer, EventInfo> iaConfigList) {
    this.iaConfigList = iaConfigList;
  }

  public void setSioConfigList(HashMap<Integer, EventInfo> sioConfigList) {
    this.sioConfigList = sioConfigList;
  }

  public void setServerConfigList(HashMap<Integer, EventInfo> serverConfigList) {
    this.serverConfigList = serverConfigList;
  }

  public void setDeviceConfigList(HashMap<Integer, EventInfo> deviceConfigList) {
    this.deviceConfigList = deviceConfigList;
  }

  public void setMonitorConfigList2(HashMap<MonitorConfigKey, EventInfo> monitorConfigList2) {
    this.monitorConfigList2 = monitorConfigList2;
  }

  public void setIaConfigList2(HashMap<IaConfigKey, EventInfo> iaConfigList2) {
    this.iaConfigList2 = iaConfigList2;
  }

  public void setSioConfigList2(HashMap<SioConfigKey, EventInfo> sioConfigList2) {
    this.sioConfigList2 = sioConfigList2;
  }

  public void setServerConfigList2(HashMap<ServerConfigKey, EventInfo> serverConfigList2) {
    this.serverConfigList2 = serverConfigList2;
  }

  public void setDeviceConfigList2(HashMap<DeviceConfigKey, EventInfo> deviceConfigList2) {
    this.deviceConfigList2 = deviceConfigList2;
  }

  public EventConfig() {
    this.monitorConfigList = new HashMap<>();
    this.iaConfigList = new HashMap<>();
    this.sioConfigList = new HashMap<>();
    this.serverConfigList = new HashMap<>();
    this.deviceConfigList = new HashMap<>();

    this.monitorConfigList2 = new HashMap<>();
    this.iaConfigList2 = new HashMap<>();
    this.sioConfigList2 = new HashMap<>();
    this.serverConfigList2 = new HashMap<>();
    this.deviceConfigList2 = new HashMap<>();
    this.alarmStormConfig = new AlarmStormConfig();
  }

  private HashMap<Integer, EventInfo> monitorConfigList;
  private HashMap<Integer, EventInfo> iaConfigList;
  private HashMap<Integer, EventInfo> sioConfigList;
  private HashMap<Integer, EventInfo> serverConfigList;
  private HashMap<Integer, EventInfo> deviceConfigList;

  private HashMap<MonitorConfigKey, EventInfo> monitorConfigList2;
  private HashMap<IaConfigKey, EventInfo> iaConfigList2;
  private HashMap<SioConfigKey, EventInfo> sioConfigList2;
  private HashMap<ServerConfigKey, EventInfo> serverConfigList2;
  private HashMap<DeviceConfigKey, EventInfo> deviceConfigList2;

  private AlarmStormConfig alarmStormConfig;

  private int listNum;
  private int disListNum;

  public Long getFreshTime() {
    return freshTime;
  }

  public void setFreshTime(Long freshTime) {
    this.freshTime = freshTime;
  }

  Long freshTime = 0L;


  public int getListNum() {
    return listNum;
  }

  public void setListNum() {
    this.listNum = monitorConfigList.size() + iaConfigList.size()
            + sioConfigList.size() + serverConfigList.size() + deviceConfigList.size();
  }

  public void setListNum(int listNum) {
    this.listNum = listNum;
  }

  public int getDisListNum() {
    return disListNum;
  }

  public void setDisListNum(int disListNum) {
    this.disListNum = disListNum;
  }


  public void clearMonitorConfigConfigListbyKey(HashMap<MonitorConfigKey, EventInfo> list) {
    Iterator iter = list.entrySet().iterator();
    while (iter.hasNext()) {
      Map.Entry entry = (Map.Entry) iter.next();
      Object key = entry.getKey();
      Object val = entry.getValue();
      EventInfo eventInfo = (EventInfo) val;
      //list.remove(key);
      eventInfo.zero();
    }
    list.clear();
  }

  public void clearIaConfigConfigListbyKey(HashMap<IaConfigKey, EventInfo> list) {
    Iterator iter = list.entrySet().iterator();
    while (iter.hasNext()) {
      Map.Entry entry = (Map.Entry) iter.next();
      Object key = entry.getKey();
      Object val = entry.getValue();
      EventInfo eventInfo = (EventInfo) val;
      //list.remove(key);
      eventInfo.zero();
    }
    list.clear();
  }

  public void clearSioConfigConfigListbyKey(HashMap<SioConfigKey, EventInfo> list) {
    Iterator iter = list.entrySet().iterator();
    while (iter.hasNext()) {
      Map.Entry entry = (Map.Entry) iter.next();
      Object key = entry.getKey();
      Object val = entry.getValue();
      EventInfo eventInfo = (EventInfo) val;
      //list.remove(key);
      eventInfo.zero();
    }
    list.clear();
  }

  public void clearServerConfigConfigListbyKey(HashMap<ServerConfigKey, EventInfo> list) {
    Iterator iter = list.entrySet().iterator();
    while (iter.hasNext()) {
      Map.Entry entry = (Map.Entry) iter.next();
      Object key = entry.getKey();
      Object val = entry.getValue();
      EventInfo eventInfo = (EventInfo) val;
      //list.remove(key);
      eventInfo.zero();
    }
    list.clear();
  }

  public void clearDeviceConfigConfigListbyKey(HashMap<DeviceConfigKey, EventInfo> list) {
    Iterator iter = list.entrySet().iterator();
    while (iter.hasNext()) {
      Map.Entry entry = (Map.Entry) iter.next();
      Object key = entry.getKey();
      Object val = entry.getValue();
      EventInfo eventInfo = (EventInfo) val;
      //list.remove(key);
      eventInfo.zero();
    }
    list.clear();
  }

  public void clearConfigList(HashMap<Integer, EventInfo> list) {
    Iterator iter = list.entrySet().iterator();
    while (iter.hasNext()) {
      Map.Entry entry = (Map.Entry) iter.next();
      Object key = entry.getKey();
      Object val = entry.getValue();
      EventInfo eventInfo = (EventInfo) val;
      //list.remove(key);
      eventInfo.zero();
    }
    list.clear();
  }

  public void clearConfig() {

    clearConfigList(monitorConfigList);
    clearConfigList(iaConfigList);
    clearConfigList(sioConfigList);
    clearConfigList(serverConfigList);
    clearConfigList(deviceConfigList);

    clearMonitorConfigConfigListbyKey(monitorConfigList2);
    clearIaConfigConfigListbyKey(iaConfigList2);
    clearSioConfigConfigListbyKey(sioConfigList2);
    clearServerConfigConfigListbyKey(serverConfigList2);
    clearDeviceConfigConfigListbyKey(deviceConfigList2);

//    setDeviceConfigList(null);
//    setDeviceConfigList2(null);
//    setIaConfigList(null);
//    setIaConfigList2(null);
//    setMonitorConfigList(null);
//    setMonitorConfigList2(null);
//    setServerConfigList(null);
//    setServerConfigList2(null);
//    setSioConfigList(null);
//    setSioConfigList2(null);

    alarmStormConfig.zero();
  }

  public void reLoadConfig(Connection conn) throws SQLException {
    clearConfig();
    loadConfig(conn);
  }

  public synchronized void loadConfig(Connection conn) throws SQLException {
    String sql = "SELECT  count(*) totalCount  FROM ti_event WHERE enable_state = 1";
    PreparedStatement pstmt = conn.prepareStatement(sql);
    ResultSet ret = pstmt.executeQuery();
    System.out.println(ret);
    if (ret.next()) {
      listNum = ret.getInt("totalCount");
    } else {
      listNum = 0;
    }

    sql = "SELECT  count(*) totalCount FROM ti_event WHERE enable_state != 1";
    pstmt = conn.prepareStatement(sql);
    ret = pstmt.executeQuery();
    if (ret.next()) {
      disListNum = ret.getInt("totalCount");
    } else {
      disListNum = 0;
    }
    int num = listNum + disListNum;
    System.out.println("listNum:" + listNum + "  disListNum:" + disListNum + "  num:" + num);
    if (num <= 0) return;
    int event_id;
    String event_no;
    String event_genus;
    String event_type;
    String event_name;
    String event_desc;
    int enable_state = 1;
    int event_level;
    int auto_release_interval;

    int guard_plan_id;
    int res_id;
    int machine_id;
    int dev_id;
    int sourceId = 0;
    int iaagId = 0;
    int iaag_chn_id = 0;

    sql = "SELECT event_id,event_no,event_genus,event_name,event_desc,event_level,auto_release_interval,event_type,ti_event.guard_plan_id " +
            "from ti_event inner JOIN ti_guard_plan on ti_event.guard_plan_id = ti_guard_plan.guard_plan_id" +
            " where ti_event.enable_state = 1";
    pstmt = conn.prepareStatement(sql);
    ret = pstmt.executeQuery();
    int i = 0;

    while (ret.next()) {
      event_id = ret.getInt("event_id");
      event_no = ret.getString("event_no");
      event_genus = new String(ret.getString("event_genus"));
      event_type = ret.getString("event_type");
      event_name = ret.getString("event_name");
      event_desc = ret.getString("event_desc");
      event_level = ret.getInt("event_level");
      auto_release_interval = ret.getInt("auto_release_interval");
      guard_plan_id = ret.getInt("guard_plan_id");
      //guard plan
      GuardPlan guardPlan = createGuardPlanFromDB(conn, guard_plan_id);
      //linage
      List<EventLinkage> linkages = createLinkageListFromDB(conn, event_id);

      EventInfo eventInfo = EventInfo.newBuilder().build();
      eventInfo.setEvent_id(event_id);
      eventInfo.setEvent_level(event_level);
      eventInfo.setEvent_name(event_name);
      eventInfo.setEvent_no(event_no);
      eventInfo.setEvent_type(event_type);
      eventInfo.setEvent_desc(event_desc);
      eventInfo.setEvent_genus(event_genus);
      eventInfo.setEnable_state(enable_state);
      eventInfo.setGuardPlan(guardPlan);
      eventInfo.setEventLinkagelist(linkages);
      eventInfo.setAuto_release_interval(auto_release_interval);

      if (event_genus.equals(sGenusMonitor)) {
        String sql2 = "SELECT res_id FROM ti_event_monitor_ex WHERE event_id = ?";
        PreparedStatement pstmt2 = conn.prepareStatement(sql2);
        pstmt2.setInt(1, event_id);
        ResultSet ret2 = pstmt2.executeQuery();
        if (ret2.next()) {
          sourceId = ret2.getInt("res_id");
          res_id = ret2.getInt("res_id");
          eventInfo.setSourceId(sourceId);
          eventInfo.setRes_id(res_id);
          if (getMonitorConfigList() == null)
            monitorConfigList = new HashMap<Integer, EventInfo>();
          if (getMonitorConfigList2() == null)
            monitorConfigList2 = new HashMap<MonitorConfigKey, EventInfo>();
          monitorConfigList.put(event_id, eventInfo);
          monitorConfigList2.put(eventInfo.getMonitorConfigKey(), eventInfo);
        }
        ret2.close();
        pstmt2.close();
      } else if (event_genus.equals(sGenusSio)) {
        String sql2 = "SELECT res_id FROM ti_event_sio_ex WHERE event_id = ?";
        PreparedStatement pstmt2 = conn.prepareStatement(sql2);
        pstmt2.setInt(1, event_id);
        ResultSet ret2 = pstmt2.executeQuery();
        if (ret2.next()) {
          sourceId = ret2.getInt("res_id");
          res_id = ret2.getInt("res_id");
          eventInfo.setRes_id(res_id);
          eventInfo.setSourceId(sourceId);
          if (getSioConfigList() == null)
            sioConfigList = new HashMap<Integer, EventInfo>();
          if (getSioConfigList2() == null)
            sioConfigList2 = new HashMap<SioConfigKey, EventInfo>();
          sioConfigList.put(event_id, eventInfo);
          sioConfigList2.put(eventInfo.getSioConfigKey(), eventInfo);
        }
        ret2.close();
        pstmt2.close();
      } else if (event_genus.equals(sGenusIa)) {
        String sql2 = "SELECT res_id,svr_id,iaag_chn_id FROM ti_event_ia_ex WHERE event_id = ?";
        PreparedStatement pstmt2 = conn.prepareStatement(sql2);
        pstmt2.setInt(1, event_id);
        ResultSet ret2 = pstmt2.executeQuery();
        if (ret2.next()) {
          sourceId = ret2.getInt("res_id");
          res_id = ret2.getInt("res_id");
          iaag_chn_id = ret2.getInt("iaag_chn_id");
          iaagId = ret2.getInt("svr_id");
          eventInfo.setRes_id(res_id);
          eventInfo.setSourceId(sourceId);
          eventInfo.setIaag_chn_id(iaag_chn_id);
          eventInfo.setIaagId(iaagId);
          eventInfo.setSvr_id(iaagId);
          if (getIaConfigList() == null)
            iaConfigList = new HashMap<Integer, EventInfo>();
          if (getIaConfigList2() == null)
            iaConfigList2 = new HashMap<IaConfigKey, EventInfo>();
          iaConfigList.put(event_id, eventInfo);
          iaConfigList2.put(eventInfo.getIaConfigKey(), eventInfo);
        }
        ret2.close();
        pstmt2.close();
      } else if (event_genus.equals(sGenusServer)) {
        String sql2 = "SELECT machine_id FROM ti_event_machine_ex WHERE event_id = ?";
        PreparedStatement pstmt2 = conn.prepareStatement(sql2);
        pstmt2.setInt(1, event_id);
        ResultSet ret2 = pstmt2.executeQuery();
        if (ret2.next()) {
          sourceId = ret2.getInt("machine_id");
          machine_id = ret2.getInt("machine_id");
          eventInfo.setSourceId(sourceId);
          eventInfo.setMachine_id(machine_id);
          if (getServerConfigList() == null)
            serverConfigList = new HashMap<Integer, EventInfo>();
          if (getServerConfigList2() == null)
            serverConfigList2 = new HashMap<ServerConfigKey, EventInfo>();
          serverConfigList.put(event_id, eventInfo);
          serverConfigList2.put(eventInfo.getServerConfigKey(), eventInfo);
        }
        ret2.close();
        pstmt2.close();
      } else if (event_genus.equals(sGenusDeveice)) {
        String sql2 = "SELECT device_id FROM ti_event_device_ex WHERE event_id = ?";
        PreparedStatement pstmt2 = conn.prepareStatement(sql2);
        pstmt2.setInt(1, event_id);
        ResultSet ret2 = pstmt2.executeQuery();
        if (ret2.next()) {
          sourceId = ret2.getInt("device_id");
          dev_id = ret2.getInt("device_id");
          eventInfo.setSourceId(sourceId);
          eventInfo.setDev_id(dev_id);
          if (getDeviceConfigList() == null)
            deviceConfigList = new HashMap<Integer, EventInfo>();
          if (getDeviceConfigList2() == null)
            deviceConfigList2 = new HashMap<DeviceConfigKey, EventInfo>();
          deviceConfigList.put(event_id, eventInfo);
          deviceConfigList2.put(eventInfo.getDeviceConfigKey(), eventInfo);
        }
        ret2.close();
        pstmt2.close();
      } else {
        System.out.println("error event_genus :" + event_genus);
        continue;
      }
    }
    getAlarmStormConfig().load(conn);
    listNum = monitorConfigList.size() + iaConfigList.size()
            + sioConfigList.size() + serverConfigList.size() + deviceConfigList.size();
    setFreshTime(System.currentTimeMillis());
  }

  public EventInfo getMonitorConfig(MonitorConfigKey monitorConfigKey) {
    return this.monitorConfigList2.get(monitorConfigKey);
  }

  public EventInfo getSioConfig(SioConfigKey sioConfigKey) {
    return this.sioConfigList2.get(sioConfigKey);
  }

  public EventInfo getIaConfig(IaConfigKey iaConfigKey) {
    return this.iaConfigList2.get(iaConfigKey);
  }

  public EventInfo getServerConfig(ServerConfigKey serverConfigKey) {
    return this.serverConfigList2.get(serverConfigKey);
  }

  public EventInfo getDeviceConfig(DeviceConfigKey deviceConfigKey) {
    return this.deviceConfigList2.get(deviceConfigKey);
  }


  public AlarmStormConfig getAlarmStormConfig() {
    return alarmStormConfig;
  }

  public void setAlarmStormConfig(AlarmStormConfig alarmStormConfig) {
    this.alarmStormConfig = alarmStormConfig;
  }

  private EventInfo findEventInfo(int id) {
    EventInfo eventInfo = getMonitorConfigList().get(id);
    if (eventInfo != null)
      return eventInfo;
    eventInfo = getSioConfigList().get(id);
    if (eventInfo != null)
      return eventInfo;
    eventInfo = getIaConfigList().get(id);
    if (eventInfo != null)
      return eventInfo;
    eventInfo = getServerConfigList().get(id);
    if (eventInfo != null)
      return eventInfo;
    eventInfo = getDeviceConfigList().get(id);
    return eventInfo;
  }

  private EventInfo findEventInfo2(Iterator iter, int id) {
    while (iter.hasNext()) {
      Map.Entry entry = (Map.Entry) iter.next();
      Object key = entry.getKey();
      Object val = entry.getValue();
      EventInfo eventInfo = (EventInfo) val;
      if (eventInfo.getEvent_id() == id)
        return eventInfo;
    }
    return null;
  }

  public EventInfo findEventInfo2(int id) {
    Iterator iter = monitorConfigList2.entrySet().iterator();
    EventInfo eventInfo = findEventInfo2(iter, id);
    if (eventInfo != null)
      return eventInfo;
    iter = sioConfigList2.entrySet().iterator();
    eventInfo = findEventInfo2(iter, id);
    if (eventInfo != null)
      return eventInfo;
    iter = iaConfigList2.entrySet().iterator();
    eventInfo = findEventInfo2(iter, id);
    if (eventInfo != null)
      return eventInfo;
    iter = serverConfigList2.entrySet().iterator();
    eventInfo = findEventInfo2(iter, id);
    if (eventInfo != null)
      return eventInfo;
    iter = deviceConfigList2.entrySet().iterator();
    eventInfo = findEventInfo2(iter, id);
    return eventInfo;
  }

  private List<EventInfo> findEventListByGuardId(Iterator iter, int guardPlanId) {
    List<EventInfo> eventInfos = new ArrayList<>();
    while (iter.hasNext()) {
      Map.Entry entry = (Map.Entry) iter.next();
      Object key = entry.getKey();
      Object val = entry.getValue();
      EventInfo eventInfo = (EventInfo) val;
      if (eventInfo.getGuardPlan().getGuard_plan_id() == guardPlanId)
        eventInfos.add(eventInfo);
    }
    return eventInfos;
  }

  public List<EventInfo> findEventListByGuardId(int guardPlanId) {
    Iterator iter = monitorConfigList2.entrySet().iterator();
    List<EventInfo> eventInfos1 = findEventListByGuardId(iter, guardPlanId);
    iter = sioConfigList2.entrySet().iterator();
    List<EventInfo> eventInfos2 = findEventListByGuardId(iter, guardPlanId);
    iter = iaConfigList2.entrySet().iterator();
    List<EventInfo> eventInfos3 = findEventListByGuardId(iter, guardPlanId);
    iter = serverConfigList2.entrySet().iterator();
    List<EventInfo> eventInfos4 = findEventListByGuardId(iter, guardPlanId);
    iter = deviceConfigList2.entrySet().iterator();
    List<EventInfo> eventInfos5 = findEventListByGuardId(iter, guardPlanId);
    eventInfos1.addAll(eventInfos2);
    eventInfos1.addAll(eventInfos3);
    eventInfos1.addAll(eventInfos4);
    eventInfos1.addAll(eventInfos5);
    return eventInfos1;
  }

  private List<EventInfo> findEventListBylinkageId(Iterator iter, int lingkageId) {
    List<EventInfo> eventInfos = new ArrayList<>();
    while (iter.hasNext()) {
      Map.Entry entry = (Map.Entry) iter.next();
      Object key = entry.getKey();
      Object val = entry.getValue();
      EventInfo eventInfo = (EventInfo) val;
      for (int i = 0; i < eventInfo.getEventLinkagelist().size(); i++) {
        if (eventInfo.getEventLinkagelist().get(i).getLinkage_id() == lingkageId)
          eventInfos.add(eventInfo);
      }
    }
    return eventInfos;
  }

  public List<EventInfo> findEventListBylinkageId(int lingkageId) {
    Iterator iter = monitorConfigList2.entrySet().iterator();
    List<EventInfo> eventInfos1 = findEventListBylinkageId(iter, lingkageId);
    iter = iaConfigList2.entrySet().iterator();
    List<EventInfo> eventInfos2 = findEventListBylinkageId(iter, lingkageId);
    iter = sioConfigList2.entrySet().iterator();
    List<EventInfo> eventInfos3 = findEventListBylinkageId(iter, lingkageId);
    iter = serverConfigList2.entrySet().iterator();
    List<EventInfo> eventInfos4 = findEventListBylinkageId(iter, lingkageId);
    iter = deviceConfigList2.entrySet().iterator();
    List<EventInfo> eventInfos5 = findEventListBylinkageId(iter, lingkageId);
    eventInfos1.addAll(eventInfos2);
    eventInfos1.addAll(eventInfos3);
    eventInfos1.addAll(eventInfos4);
    eventInfos1.addAll(eventInfos5);
    return eventInfos1;
  }

  //query db and create a eventInfo instance
  public GuardPlan createGuardPlanFromDB(Connection conn, int id) throws SQLException {
    String sql = "SELECT guard_plan_name,time_schedule,guard_plan_type,start_time,end_time " +
            "FROM ti_guard_plan WHERE guard_plan_id = ?";
    PreparedStatement pstmt = conn.prepareStatement(sql);
    pstmt.setInt(1, id);
    //pstmt.setString(1, String.valueOf(id));
    ResultSet ret = pstmt.executeQuery();
    if (!ret.next()) {
      ret.close();
      pstmt.close();
      System.out.println("no GuardPlan id :" + id + "GuardPlan info found ");
      return null;
    }
    GuardPlan guardPlan = GuardPlan.newBuilder().guard_plan_id(id).
            guard_plan_name(ret.getString("guard_plan_name")).
            guard_plan_type(GuardPlan.Long2GuardPlanType(ret.getInt("guard_plan_type"))).
            time_schedule(ret.getString("time_schedule")).
            start_time(ret.getTimestamp("start_time")).
            end_time(ret.getTimestamp("end_time")).build();
    guardPlan.setTimeSchedule(ret.getString("time_schedule"));

    ret.close();
    pstmt.close();
    return guardPlan;
  }

  public List<EventLinkage> createLinkageListFromDB(Connection conn, int event_id) throws SQLException {
    String sql = "SELECT linkage_id,linkage_type,arg1,arg2, arg3,arg4,arg5,arg6,arg7,arg8 " +
            "FROM ti_event_linkage WHERE event_id = ?";
    PreparedStatement pstmt = conn.prepareStatement(sql);
    pstmt.setInt(1, event_id);

    ResultSet ret = pstmt.executeQuery();

    List<EventLinkage> eventLinkages = new ArrayList();
    while (ret.next()) {
      int linkage_id;
      String linkage_type;
      String arg1;
      String arg2;
      String arg3;
      String arg4;
      String arg5;
      String arg6;
      String arg7;
      String arg8;
      linkage_id = ret.getInt("linkage_id");
      linkage_type = ret.getString("linkage_type");
      arg1 = ret.getString("arg1");
      arg2 = ret.getString("arg2");
      arg3 = ret.getString("arg3");
      arg4 = ret.getString("arg4");
      arg5 = ret.getString("arg5");
      arg6 = ret.getString("arg6");
      arg7 = ret.getString("arg7");
      arg8 = ret.getString("arg8");
      EventLinkage eventLinkage = EventLinkage.newBuilder().build();
      eventLinkage.setEvent_id(event_id);
      eventLinkage.setLinkage_id(linkage_id);
      eventLinkage.setLinkage_type(linkage_type);
      eventLinkage.setArg1(arg1);
      eventLinkage.setArg2(arg2);
      eventLinkage.setArg3(arg3);
      eventLinkage.setArg4(arg4);
      eventLinkage.setArg5(arg5);
      eventLinkage.setArg6(arg6);
      eventLinkage.setArg7(arg7);
      eventLinkage.setArg8(arg8);
      eventLinkages.add(eventLinkage);
    }
    return eventLinkages;
  }

  public EventLinkage createLinkageFromDB(Connection conn, int linkage_id) throws SQLException {
    String sql = "SELECT linkage_type,event_id,arg1,arg2, arg3,arg4,arg5,arg6,arg7,arg8 " +
            "FROM ti_event_linkage WHERE linkage_id = ?";
    PreparedStatement pstmt = conn.prepareStatement(sql);
    pstmt.setString(1, String.valueOf(linkage_id));
    ResultSet ret = pstmt.executeQuery();

    if (!ret.next()) {
      ret.close();
      pstmt.close();
      System.out.println("no linkage id :" + linkage_id + "linkage  info found ");
      return null;
    }

    EventLinkage eventLinkage = EventLinkage.newBuilder().build();
    eventLinkage.setEvent_id(ret.getInt("event_id"));
    eventLinkage.setLinkage_id(linkage_id);
    eventLinkage.setLinkage_type(ret.getString("linkage_type"));
    eventLinkage.setArg1(ret.getString("arg1"));
    eventLinkage.setArg2(ret.getString("arg2"));
    eventLinkage.setArg3(ret.getString("arg3"));
    eventLinkage.setArg4(ret.getString("arg4"));
    eventLinkage.setArg5(ret.getString("arg5"));
    eventLinkage.setArg6(ret.getString("arg6"));
    eventLinkage.setArg7(ret.getString("arg7"));
    eventLinkage.setArg8(ret.getString("arg8"));
    return eventLinkage;

  }

  //query db and create a eventInfo instance
  public EventInfo createEventInfoFromDB(Connection conn, int id) throws SQLException {
    String sql = "SELECT event_no,event_genus,event_name,event_desc,event_level,auto_release_interval,event_type,ti_event.guard_plan_id " +
            "from ti_event inner JOIN ti_guard_plan on ti_event.guard_plan_id = ti_guard_plan.guard_plan_id" +
            " where ti_event.enable_state = 1 and event_id = ?";
    PreparedStatement pstmt = conn.prepareStatement(sql);
    pstmt.setInt(1, id);
    ResultSet ret = pstmt.executeQuery();
    if (!ret.next()) {
      ret.close();
      pstmt.close();
      System.out.println("no event id :" + id + "event config info found ");
      return null;
    }
    String event_no = ret.getString("event_no");
    String event_genus = ret.getString("event_genus");
    String event_type = ret.getString("event_type");
    String event_name = ret.getString("event_name");
    String event_desc = ret.getString("event_desc");
    int event_level = ret.getInt("event_level");
    int auto_release_interval = ret.getInt("auto_release_interval");
    int guard_plan_id = ret.getInt("guard_plan_id");
    ret.close();
    pstmt.close();
    GuardPlan guardPlan = createGuardPlanFromDB(conn, guard_plan_id);
    if (guardPlan == null) {
      //ret.close();
      //pstmt.close();
      System.out.println("no guardPlan id :" + id + " config info found ");
      return null;
    }
    //linage
    List<EventLinkage> eventLinkages = createLinkageListFromDB(conn, id);
    if (eventLinkages == null) {
      //ret.close();
      //pstmt.close();
      //System.out.println("no event id :" + event_id + "linkage  info found ");
      return null;
    }

    int res_id;
    int iaag_chn_id = 0;
    int iaagId = 0;
    int machine_id;
    int dev_id;
    int enable_state = 1;
    EventInfo eventInfo = EventInfo.newBuilder().event_id(id).
            event_no(event_no).
            event_genus(event_genus).
            event_name(event_name).
            event_desc(event_desc).
            enable_state(enable_state).
            event_level(event_level).
            auto_release_interval(auto_release_interval).
            guardPlan(guardPlan).
            event_type(event_type).build();
    if (event_genus.equals(sGenusMonitor)) {
      sql = "SELECT res_id FROM ti_event_monitor_ex WHERE event_id = ?";
      pstmt = conn.prepareStatement(sql);
      pstmt.setInt(1, id);
      ret = pstmt.executeQuery();
      if (ret.next()) {
        res_id = ret.getInt("res_id");
        eventInfo.setRes_id(res_id);
      }
      ret.close();
      pstmt.close();
    } else if (event_genus.equals(sGenusSio)) {
      sql = "SELECT res_id FROM ti_event_sio_ex WHERE event_id = ?";
      pstmt = conn.prepareStatement(sql);
      pstmt.setInt(1, id);
      ret = pstmt.executeQuery();
      if (ret.next()) {
        res_id = ret.getInt("res_id");
        eventInfo.setRes_id(res_id);
      }
      ret.close();
      pstmt.close();
    } else if (event_genus.equals(sGenusIa)) {
      sql = "SELECT res_id,svr_id,iaag_chn_id ti_event_ ia_ex WHERE event_id = ?";
      pstmt = conn.prepareStatement(sql);
      pstmt.setInt(1, id);
      ret = pstmt.executeQuery();
      if (ret.next()) {
        res_id = ret.getInt("res_id");
        iaag_chn_id = ret.getInt("iaag_chn_id");
        iaagId = ret.getInt("svr_id");
        eventInfo.setRes_id(res_id);
        eventInfo.setIaag_chn_id(iaag_chn_id);
        eventInfo.setIaagId(iaagId);
      }
      ret.close();
      pstmt.close();
    } else if (event_genus.equals(sGenusServer)) {
      sql = "SELECT machine_id FROM ti_event_machine_ex WHERE event_id = ?";
      pstmt = conn.prepareStatement(sql);
      pstmt.setInt(1, id);
      ret = pstmt.executeQuery();
      if (ret.next()) {
        machine_id = ret.getInt("machine_id");
        eventInfo.setMachine_id(machine_id);
      }
      ret.close();
      pstmt.close();
    } else if (event_genus.equals(sGenusDeveice)) {
      sql = "SELECT device_id FROM ti_event_device_ex WHERE event_id = ?";
      pstmt = conn.prepareStatement(sql);
      pstmt.setInt(1, id);
      ret = pstmt.executeQuery();
      if (ret.next()) {
        dev_id = ret.getInt("device_id");
        eventInfo.setDev_id(dev_id);
      }
      ret.close();
      pstmt.close();
    } else {
      System.out.println("error event_genus :" + event_genus);
      return null;
    }
    eventInfo.setEventLinkagelist(eventLinkages);
    return eventInfo;
  }

  public EventInfo addEventInfo(Connection conn, int id) throws SQLException {
    if (findEventInfo(id) != null) {
      System.out.println("has event id :" + id + "in config");
      return null;
    }
    return addEventInfo(createEventInfoFromDB(conn,id));
  }

  public EventInfo addEventInfo(EventInfo eventInfo) throws SQLException {
    if (eventInfo != null) {
      String event_genus = eventInfo.getEvent_genus();
      if (event_genus.equals(sGenusMonitor)) {
        getMonitorConfigList().put(eventInfo.getEvent_id(), eventInfo);
        getMonitorConfigList2().put(eventInfo.getMonitorConfigKey(), eventInfo);
      } else if (event_genus.equals(sGenusSio)) {
        getSioConfigList().put(eventInfo.getEvent_id(), eventInfo);
        getSioConfigList2().put(eventInfo.getSioConfigKey(), eventInfo);
      } else if (event_genus.equals(sGenusIa)) {
        getIaConfigList().put(eventInfo.getEvent_id(), eventInfo);
        getIaConfigList2().put(eventInfo.getIaConfigKey(), eventInfo);
      } else if (event_genus.equals(sGenusServer)) {
        getServerConfigList().put(eventInfo.getEvent_id(), eventInfo);
        getServerConfigList2().put(eventInfo.getServerConfigKey(), eventInfo);
      } else if (event_genus.equals(sGenusDeveice)) {
        getDeviceConfigList().put(eventInfo.getEvent_id(), eventInfo);
        getDeviceConfigList2().put(eventInfo.getDeviceConfigKey(), eventInfo);
      } else {
        System.out.println("error event_genus :" + event_genus);
        return null;
      }
      return eventInfo;
    }
    return null;
  }

  public void updateEventInfo(Connection conn, int id) {
    EventInfo eventInfo1 = findEventInfo(id);
    EventInfo eventInfo2 = findEventInfo2(id);
    if(eventInfo1 == null && eventInfo2 == null){
      System.out.println("failed found event of id:"+id);
      return;
    }
    try {
      EventInfo eventInfo3 = createEventInfoFromDB(conn, id);
      if (eventInfo3 == null) {
        System.out.println("error event id:" + id + "no event config found in db");
        return;
      }
      if(eventInfo2!=null){
        eventInfo2.copy(eventInfo3);
      }
      if(eventInfo1!=eventInfo2){
        eventInfo1.copy(eventInfo3);
      }
      eventInfo3.zero();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void deleteEventInfo(EventInfo eventInfo) {
    if (eventInfo != null) {
      String event_genus = eventInfo.getEvent_genus();
      if (event_genus.equals(sGenusMonitor)) {
        getMonitorConfigList().remove(eventInfo.getEvent_id());
        getMonitorConfigList2().remove(eventInfo.getMonitorConfigKey());
      } else if (event_genus.equals(sGenusSio)) {
        getSioConfigList2().remove(eventInfo.getSioConfigKey());
        getSioConfigList().remove(eventInfo.getEvent_id());
      } else if (event_genus.equals(sGenusIa)) {
        getIaConfigList().remove(eventInfo.getEvent_id());
        getIaConfigList2().remove(eventInfo.getIaConfigKey());
      } else if (event_genus.equals(sGenusServer)) {
        getServerConfigList().remove(eventInfo.getEvent_id());
        getServerConfigList2().remove(eventInfo.getServerConfigKey());
      } else if (event_genus.equals(sGenusDeveice)) {
        getDeviceConfigList().remove(eventInfo.getEvent_id());
        getDeviceConfigList2().remove(eventInfo.getDeviceConfigKey());
      } else {
        System.out.println("error event_genus :" + event_genus);
        return;
      }
      eventInfo.zero();
    }
  }

  public void deleteEventInfo(int id) {
    EventInfo eventInfo1 = findEventInfo(id);
    EventInfo eventInfo2 = findEventInfo2(id);
    if (eventInfo1 == eventInfo2) {
      deleteEventInfo(eventInfo1);
    } else {
      deleteEventInfo(eventInfo1);
      deleteEventInfo(eventInfo2);
    }
  }

  public void addAlarmStorm(Connection conn, int id) throws SQLException {
    getAlarmStormConfig().addAlarmStorm(conn, id);
  }

  public void updateAlarmStorm(Connection conn, int id) throws SQLException {
    getAlarmStormConfig().updateAlarmStorm(conn, id);
  }

  public void deleteAlarmStorm(int id) {
    getAlarmStormConfig().deleteAlarmStorm(id);
  }

  public void addGuardPlan(int id) {

  }

  public void updateGuardPlan(Connection conn, int id) throws SQLException {
    List<EventInfo> eventInfos = findEventListByGuardId(id);
    GuardPlan guardPlan = createGuardPlanFromDB(conn, id);
    for (EventInfo eventInfo : eventInfos) {
      eventInfo.getGuardPlan().zero();
      eventInfo.getGuardPlan().copy(guardPlan);
//      eventInfo.getGuardPlan().setGuard_plan_name(guardPlan.getGuard_plan_name());
//      eventInfo.getGuardPlan().setTime_schedule(guardPlan.getTime_schedule());
//      eventInfo.getGuardPlan().setGuard_plan_type(guardPlan.getGuard_plan_type());
//      eventInfo.getGuardPlan().setStart_time(guardPlan.getStart_time());
//      eventInfo.getGuardPlan().setEnd_time(guardPlan.getEnd_time());
//      for (int j = 0; j < eventInfo.getGuardPlan().getTimeSchedule().getTimeScheduleItems().size(); j++) {
//        TimeScheduleItem a = eventInfo.getGuardPlan().getTimeSchedule().getTimeScheduleItems().get(j);
//        TimeScheduleItem b = guardPlan.getTimeSchedule().getTimeScheduleItems().get(j);
//        a.setDayOfWeek(b.getDayOfWeek());
//        for (int k = 0; k < a.getTimePeriods().size(); k++) {
//          TimePeriod m = a.getTimePeriods().get(k);
//          TimePeriod n = b.getTimePeriods().get(k);
//          m.setEt(n.getEt());
//          m.setSt(n.getSt());
//          m.setType(n.getType());
//        }
//      }
    }
  }

  public void deleteGuardPlan(Connection conn, int id) {
    List<EventInfo> eventInfos = findEventListByGuardId(id);
    for (EventInfo eventInfo : eventInfos) {
      GuardPlan guardPlan = eventInfo.getGuardPlan();
      guardPlan.zero();
      eventInfo.setGuardPlan(null);
    }
  }

  public void addLinkage(int id) {

  }

  public void updateLinkage(Connection conn, int id) throws SQLException {
    EventLinkage eventLinkage = createLinkageFromDB(conn, id);
    List<EventInfo> eventInfos = findEventListBylinkageId(id);
    for (EventInfo eventInfo : eventInfos) {
      List<EventLinkage> eventLinkages = eventInfo.getEventLinkagelist();
      for (EventLinkage eventLinkage1 : eventLinkages) {
        if (eventLinkage1.getLinkage_id() == eventLinkage.getLinkage_id()) {
          eventLinkage1.copy(eventLinkage);
        }
      }
    }
  }

  public void deleteLinkage(int id) {
    List<EventInfo> eventInfos = findEventListBylinkageId(id);
    for (EventInfo eventInfo : eventInfos) {
      eventInfo.deleteLinkagebyId(id);
    }
  }

  @Override
  public String toString() {
    return "EventConfig{" +
            "monitorConfigList=" + monitorConfigList +
            ", iaConfigList=" + iaConfigList +
            ", sioConfigList=" + sioConfigList +
            ", serverConfigList=" + serverConfigList +
            ", deviceConfigList=" + deviceConfigList +
            ", monitorConfigList2=" + monitorConfigList2 +
            ", iaConfigList2=" + iaConfigList2 +
            ", sioConfigList2=" + sioConfigList2 +
            ", serverConfigList2=" + serverConfigList2 +
            ", deviceConfigList2=" + deviceConfigList2 +
            ", alarmStormConfig=" + alarmStormConfig +
            ", listNum=" + listNum +
            ", disListNum=" + disListNum +
            ", freshTime=" + freshTime +
            '}';
  }
}
