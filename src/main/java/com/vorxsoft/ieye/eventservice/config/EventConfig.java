package com.vorxsoft.ieye.eventservice.config;

import com.vorxsoft.ieye.eventservice.linkage.EventLinkage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class EventConfig {
  public HashMap<Long, EventInfo> getMonitorConfigList() {
    return monitorConfigList;
  }

  public HashMap<Long, EventInfo> getIaConfigList() {
    return iaConfigList;
  }

  public HashMap<Long, EventInfo> getSioConfigList() {
    return sioConfigList;
  }

  public HashMap<Long, EventInfo> getServerConfigList() {
    return serverConfigList;
  }

  public HashMap<Long, EventInfo> getDeviceConfigList() {
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

  public void setMonitorConfigList(HashMap<Long, EventInfo> monitorConfigList) {
    this.monitorConfigList = monitorConfigList;
  }

  public void setIaConfigList(HashMap<Long, EventInfo> iaConfigList) {
    this.iaConfigList = iaConfigList;
  }

  public void setSioConfigList(HashMap<Long, EventInfo> sioConfigList) {
    this.sioConfigList = sioConfigList;
  }

  public void setServerConfigList(HashMap<Long, EventInfo> serverConfigList) {
    this.serverConfigList = serverConfigList;
  }

  public void setDeviceConfigList(HashMap<Long, EventInfo> deviceConfigList) {
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

  private HashMap<Long, EventInfo> monitorConfigList;
  private HashMap<Long, EventInfo> iaConfigList;
  private HashMap<Long, EventInfo> sioConfigList;
  private HashMap<Long, EventInfo> serverConfigList;
  private HashMap<Long, EventInfo> deviceConfigList;

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
      eventInfo.clear();
      list.remove(key);
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
      eventInfo.clear();
      list.remove(key);
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
      eventInfo.clear();
      list.remove(key);
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
      eventInfo.clear();
      list.remove(key);
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
      eventInfo.clear();
      list.remove(key);
    }
    list.clear();
  }

  public void clearConfigList(HashMap<Long, EventInfo> list) {
    Iterator iter = list.entrySet().iterator();
    while (iter.hasNext()) {
      Map.Entry entry = (Map.Entry) iter.next();
      Object key = entry.getKey();
      Object val = entry.getValue();
      EventInfo eventInfo = (EventInfo) val;
      eventInfo.clear();
      list.remove(key);
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

    setDeviceConfigList(null);
    setDeviceConfigList2(null);
    setIaConfigList(null);
    setIaConfigList2(null);
    setMonitorConfigList(null);
    setMonitorConfigList2(null);
    setServerConfigList(null);
    setServerConfigList2(null);
    setSioConfigList(null);
    setSioConfigList2(null);

    alarmStormConfig.clear();
  }

  public void reLoadConfig(Connection conn) throws SQLException {
    clearConfig();
    loadConfig(conn);
  }

  public synchronized void loadConfig(Connection conn) throws SQLException {
    String sql = "SELECT  COUNT(*) FROM ti_event WHERE enable_state = 1";
    PreparedStatement pstmt = conn.prepareStatement(sql);
    ResultSet ret = pstmt.executeQuery(sql);
    listNum = ret.getInt("totalCount");
    sql = "SELECT  COUNT(*) FROM ti_event WHERE enable_state != 1";
    pstmt = conn.prepareStatement(sql);
    ret = pstmt.executeQuery(sql);
    disListNum = ret.getInt("totalCount");
    int num = listNum + disListNum;
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

    sql = "SELECT event_id,event_no,event_genus,event_name,event_desc,event_level,auto_release_interval,event_type,guard_plan_id " +
        "from ti_event inner JOIN ti_guard_plan on ti_event.guard_plan_id = ti_guard_plan.guard_plan_id" +
        " where ti_event.enable_state = 1";
    pstmt = conn.prepareStatement(sql);
    ret = pstmt.executeQuery(sql);
    int i = 0;
    while (ret.next()) {
      event_id = ret.getInt("event_id");
      event_no = ret.getString("event_no");
      event_genus = ret.getString("event_genus");
      event_type = ret.getString("event_type");
      event_name = ret.getString("event_name");
      event_desc = ret.getString("event_desc");
      event_level = ret.getInt("event_level");
      auto_release_interval = ret.getInt("auto_release_interval");
      guard_plan_id = ret.getInt("guard_plan_id;");
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
      eventInfo.setSourceId(sourceId);
      eventInfo.setAuto_release_interval(auto_release_interval);

      if (event_genus.equals("event_monitor")) {
        sql = "SELECT res_id FROM ti_event_monitor_ex WHERE event_id = ?";
        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, String.valueOf(event_id));
        ret = pstmt.executeQuery(sql);
        sourceId = ret.getInt("res_id");
        res_id = ret.getInt("res_id");
        ret.close();
        pstmt.close();
        MonitorConfigKey monitorConfigKey = MonitorConfigKey.newBuilder().
            event_type(event_type).res_id(res_id).build();
        eventInfo.setRes_id(res_id);
        if (getMonitorConfigList() == null)
          monitorConfigList = new HashMap<Long, EventInfo>();
        if (getMonitorConfigList2() == null)
          monitorConfigList2 = new HashMap<MonitorConfigKey, EventInfo>();
        monitorConfigList.put((long) event_id, eventInfo);
        monitorConfigList2.put(monitorConfigKey, eventInfo);
      } else if (event_genus.equals("event_sio")) {
        sql = "SELECT res_id FROM ti_event_sio_ex WHERE event_id = ?";
        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, String.valueOf(event_id));
        ret = pstmt.executeQuery(sql);
        sourceId = ret.getInt("res_id");
        res_id = ret.getInt("res_id");
        ret.close();
        pstmt.close();
        SioConfigKey sioConfigKey = SioConfigKey.newBuilder().event_type(event_type).res_id(res_id).build();
        ;
        if (getSioConfigList() == null)
          sioConfigList = new HashMap<Long, EventInfo>();
        if (getSioConfigList2() == null)
          sioConfigList2 = new HashMap<SioConfigKey, EventInfo>();
        sioConfigList.put((long) event_id, eventInfo);
        sioConfigList2.put(sioConfigKey, eventInfo);
      } else if (event_genus.equals("event_ ia")) {
        sql = "SELECT res_id,svr_id,iaag_chn_id ti_event_ ia_ex WHERE event_id = ?";
        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, String.valueOf(event_id));
        ret = pstmt.executeQuery(sql);
        sourceId = ret.getInt("res_id");
        res_id = ret.getInt("res_id");
        iaag_chn_id = ret.getInt("iaag_chn_id");
        iaagId = ret.getInt("svr_id");
        ret.close();
        pstmt.close();
        eventInfo.setIaag_chn_id(iaag_chn_id);
        eventInfo.setIaagId(iaagId);
        IaConfigKey iaConfigKey = IaConfigKey.newBuilder().event_type(event_desc).iaag_chn_id(iaag_chn_id).
            iaagId(iaagId).res_id(res_id).build();
        if (getIaConfigList() == null)
          iaConfigList = new HashMap<Long, EventInfo>();
        if (getIaConfigList2() == null)
          iaConfigList2 = new HashMap<IaConfigKey, EventInfo>();
        iaConfigList.put((long) event_id, eventInfo);
        iaConfigList2.put(iaConfigKey, eventInfo);
      } else if (event_genus.equals("event_server")) {
        sql = "SELECT machine_id FROM ti_event_machine_ex WHERE event_id = ?";
        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, String.valueOf(event_id));
        ret = pstmt.executeQuery(sql);
        sourceId = ret.getInt("machine_id");
        machine_id = ret.getInt("machine_id");
        ret.close();
        pstmt.close();
        ServerConfigKey serverConfigKey = ServerConfigKey.newBuilder().
            event_type(event_type).machine_id(machine_id).build();
        if (getServerConfigList() == null)
          serverConfigList = new HashMap<Long, EventInfo>();
        if (getServerConfigList2() == null)
          serverConfigList2 = new HashMap<ServerConfigKey, EventInfo>();
        serverConfigList.put((long) event_id, eventInfo);
        serverConfigList2.put(serverConfigKey, eventInfo);
      } else if (event_genus.equals("event_device")) {
        sql = "SELECT device_id FROM ti_event_device_ex WHERE event_id = ?";
        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, String.valueOf(event_id));
        ret = pstmt.executeQuery(sql);
        sourceId = ret.getInt("device_id");
        dev_id = ret.getInt("device_id");
        ret.close();
        pstmt.close();
        DeviceConfigKey deviceConfigKey = DeviceConfigKey.newBuilder().
            event_type(event_type).dev_id(dev_id).build();
        if (getDeviceConfigList() == null)
          deviceConfigList = new HashMap<Long, EventInfo>();
        if (getDeviceConfigList2() == null)
          deviceConfigList2 = new HashMap<DeviceConfigKey, EventInfo>();
        deviceConfigList.put((long) event_id, eventInfo);
        deviceConfigList2.put(deviceConfigKey, eventInfo);
      } else {
        System.out.println("error event_genus :" + event_genus);
        continue;
      }
    }
    alarmStormConfig.load(conn);

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
    iter = iaConfigList2.entrySet().iterator();
    eventInfo = findEventInfo2(iter, id);
    if (eventInfo != null)
      return eventInfo;
    iter = iaConfigList2.entrySet().iterator();
    eventInfo = findEventInfo2(iter, id);
    if (eventInfo != null)
      return eventInfo;
    iter = iaConfigList2.entrySet().iterator();
    eventInfo = findEventInfo2(iter, id);
    if (eventInfo != null)
      return eventInfo;
    iter = iaConfigList2.entrySet().iterator();
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
    iter = iaConfigList2.entrySet().iterator();
    List<EventInfo> eventInfos2 = findEventListByGuardId(iter, guardPlanId);
    iter = iaConfigList2.entrySet().iterator();
    List<EventInfo> eventInfos3 = findEventListByGuardId(iter, guardPlanId);
    iter = iaConfigList2.entrySet().iterator();
    List<EventInfo> eventInfos4 = findEventListByGuardId(iter, guardPlanId);
    iter = iaConfigList2.entrySet().iterator();
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
    iter = iaConfigList2.entrySet().iterator();
    List<EventInfo> eventInfos3 = findEventListBylinkageId(iter, lingkageId);
    iter = iaConfigList2.entrySet().iterator();
    List<EventInfo> eventInfos4 = findEventListBylinkageId(iter, lingkageId);
    iter = iaConfigList2.entrySet().iterator();
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
    pstmt.setString(1, String.valueOf(id));
    ResultSet ret = pstmt.executeQuery(sql);
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
    ret.close();
    pstmt.close();
    return guardPlan;
  }

  public List<EventLinkage> createLinkageListFromDB(Connection conn, int event_id) throws SQLException {
    String sql = "SELECT linkage_id,linkage_type,arg1,arg2, arg3,arg4,arg5,arg6,arg7,arg8 " +
        "FROM ti_event_linkage WHERE event_id = ?";
    PreparedStatement pstmt = conn.prepareStatement(sql);
    pstmt.setString(1, String.valueOf(event_id));

    ResultSet ret = pstmt.executeQuery(sql);

    if (!ret.next()) {
      ret.close();
      pstmt.close();
      //System.out.println("no event id :" + event_id + "linkage  info found ");
      return null;
    }
    List<EventLinkage> eventLinkages = new ArrayList();
    for (int j = 0; ret.next(); j++) {
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
    ResultSet ret = pstmt.executeQuery(sql);

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
    String sql = "SELECT event_no,event_genus,event_name,event_desc,event_level,auto_release_interval,event_type,guard_plan_id " +
        "from ti_event inner JOIN ti_guard_plan on ti_event.guard_plan_id = ti_guard_plan.guard_plan_id" +
        " where ti_event.enable_state = 1 and event_id = ?";
    PreparedStatement pstmt = conn.prepareStatement(sql);
    pstmt.setString(1, String.valueOf(id));
    ResultSet ret = pstmt.executeQuery(sql);
    if (!ret.next()) {
      ret.close();
      pstmt.close();
      System.out.println("no event id :" + id + "event config info found ");
      return null;
    }
    int event_id = id;
    String event_no = ret.getString("event_no");
    String event_genus = ret.getString("event_genus");
    String event_type = ret.getString("event_type");
    String event_name = ret.getString("event_name");
    String event_desc = ret.getString("event_desc");
    int event_level = ret.getInt("event_level");
    int auto_release_interval = ret.getInt("auto_release_interval");
    int guard_plan_id = ret.getInt("guard_plan_id;");
    ret.close();
    pstmt.close();
    GuardPlan guardPlan = createGuardPlanFromDB(conn, guard_plan_id);
    if (guardPlan == null) {
      ret.close();
      pstmt.close();
      System.out.println("no guardPlan id :" + id + " config info found ");
      return null;
    }
    //linage
    List<EventLinkage> eventLinkages = createLinkageListFromDB(conn, event_id);
    if (eventLinkages == null) {
      ret.close();
      pstmt.close();
      //System.out.println("no event id :" + event_id + "linkage  info found ");
      return null;
    }

    int res_id;
    int iaag_chn_id = 0;
    int iaagId = 0;
    int machine_id;
    int dev_id;
    int enable_state = 1;
    EventInfo eventInfo = EventInfo.newBuilder().event_id(event_id).
        event_no(event_no).
        event_genus(event_genus).
        event_name(event_name).
        event_desc(event_desc).
        enable_state(enable_state).
        event_level(event_level).
        auto_release_interval(auto_release_interval).
        guardPlan(guardPlan).
        event_type(event_type).build();
    if (event_genus.equals("event_monitor")) {
      sql = "SELECT res_id FROM ti_event_monitor_ex WHERE event_id = ?";
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, String.valueOf(event_id));
      ret = pstmt.executeQuery(sql);
      res_id = ret.getInt("res_id");
      eventInfo.setRes_id(res_id);
      ret.close();
      pstmt.close();
    } else if (event_genus.equals("event_sio")) {
      sql = "SELECT res_id FROM ti_event_sio_ex WHERE event_id = ?";
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, String.valueOf(event_id));
      ret = pstmt.executeQuery(sql);
      res_id = ret.getInt("res_id");
      eventInfo.setRes_id(res_id);
      ret.close();
      pstmt.close();
    } else if (event_genus.equals("event_ ia")) {
      sql = "SELECT res_id,svr_id,iaag_chn_id ti_event_ ia_ex WHERE event_id = ?";
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, String.valueOf(event_id));
      ret = pstmt.executeQuery(sql);
      res_id = ret.getInt("res_id");
      iaag_chn_id = ret.getInt("iaag_chn_id");
      iaagId = ret.getInt("svr_id");
      eventInfo.setRes_id(res_id);
      eventInfo.setIaag_chn_id(iaag_chn_id);
      eventInfo.setIaagId(iaagId);
      ret.close();
      pstmt.close();
    } else if (event_genus.equals("event_server")) {
      sql = "SELECT machine_id FROM ti_event_machine_ex WHERE event_id = ?";
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, String.valueOf(event_id));
      ret = pstmt.executeQuery(sql);
      machine_id = ret.getInt("machine_id");
      eventInfo.setMachine_id(machine_id);
      ret.close();
      pstmt.close();
    } else if (event_genus.equals("event_device")) {
      sql = "SELECT device_id FROM ti_event_device_ex WHERE event_id = ?";
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, String.valueOf(event_id));
      ret = pstmt.executeQuery(sql);
      dev_id = ret.getInt("device_id");
      eventInfo.setDev_id(dev_id);
      ret.close();
      pstmt.close();
    } else {
      System.out.println("error event_genus :" + event_genus);
      return null;
    }
    return eventInfo;
  }

  public EventInfo addEventInfo(Connection conn, int id) throws SQLException {
    if (findEventInfo(id) != null) {
      System.out.println("has event id :" + id + "in config");
      return null;
    }
    String sql = "SELECT event_no,event_genus,event_name,event_desc,event_level,auto_release_interval,event_type,guard_plan_id " +
        "from ti_event inner JOIN ti_guard_plan on ti_event.guard_plan_id = ti_guard_plan.guard_plan_id" +
        " where ti_event.enable_state = 1 and event_id = ?";
    PreparedStatement pstmt = conn.prepareStatement(sql);
    pstmt.setString(1, String.valueOf(id));
    ResultSet ret = pstmt.executeQuery(sql);
    int event_id = id;
    String event_no = ret.getString("event_no");
    String event_genus = ret.getString("event_genus");
    String event_type = ret.getString("event_type");
    String event_name = ret.getString("event_name");
    String event_desc = ret.getString("event_desc");
    int event_level = ret.getInt("event_level");
    int auto_release_interval = ret.getInt("auto_release_interval");
    int guard_plan_id = ret.getInt("guard_plan_id;");
    ret.close();
    pstmt.close();

    //guard plan
    GuardPlan guardPlan = createGuardPlanFromDB(conn, guard_plan_id);
    if (guardPlan == null) {
      return null;
    }
    //linage
    List<EventLinkage> eventLinkages = createLinkageListFromDB(conn, event_id);
    if (eventLinkages == null) {
      guardPlan = null;
      return null;
    }

    int res_id;
    int iaag_chn_id = 0;
    int iaagId = 0;
    int machine_id;
    int dev_id;
    int enable_state = 1;
    MonitorConfigKey monitorConfigKey = MonitorConfigKey.newBuilder().build();
    SioConfigKey sioConfigKey = SioConfigKey.newBuilder().build();
    IaConfigKey iaConfigKey = IaConfigKey.newBuilder().build();
    ServerConfigKey serverConfigKey = ServerConfigKey.newBuilder().build();
    DeviceConfigKey deviceConfigKey = DeviceConfigKey.newBuilder().build();
    EventInfo eventInfo = EventInfo.newBuilder().event_id(event_id).
        event_no(event_no).
        event_genus(event_genus).
        event_name(event_name).
        event_desc(event_desc).
        enable_state(enable_state).
        event_level(event_level).
        auto_release_interval(auto_release_interval).
        guardPlan(guardPlan).
        event_type(event_type).build();
    if (event_genus.equals("event_monitor")) {
      sql = "SELECT res_id FROM ti_event_monitor_ex WHERE event_id = ?";
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, String.valueOf(event_id));
      ret = pstmt.executeQuery(sql);
      res_id = ret.getInt("res_id");
      eventInfo.setRes_id(res_id);
      ret.close();
      pstmt.close();
      monitorConfigKey.setEvent_type(event_type);
      monitorConfigKey.setRes_id(res_id);
      if (getMonitorConfigList() == null)
        monitorConfigList = new HashMap<Long, EventInfo>();
      if (getMonitorConfigList2() == null)
        monitorConfigList2 = new HashMap<MonitorConfigKey, EventInfo>();
      monitorConfigList.put((long) event_id, eventInfo);
      monitorConfigList2.put(monitorConfigKey, eventInfo);
    } else if (event_genus.equals("event_sio")) {
      sql = "SELECT res_id FROM ti_event_sio_ex WHERE event_id = ?";
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, String.valueOf(event_id));
      ret = pstmt.executeQuery(sql);
      res_id = ret.getInt("res_id");
      eventInfo.setRes_id(res_id);
      ret.close();
      pstmt.close();
      sioConfigKey.setEvent_type(event_type);
      sioConfigKey.setRes_id(res_id);
      if (getSioConfigList() == null)
        sioConfigList = new HashMap<Long, EventInfo>();
      if (getSioConfigList2() == null)
        sioConfigList2 = new HashMap<SioConfigKey, EventInfo>();
      sioConfigList.put((long) event_id, eventInfo);
      sioConfigList2.put(sioConfigKey, eventInfo);
    } else if (event_genus.equals("event_ ia")) {
      sql = "SELECT res_id,svr_id,iaag_chn_id ti_event_ ia_ex WHERE event_id = ?";
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, String.valueOf(event_id));
      ret = pstmt.executeQuery(sql);
      res_id = ret.getInt("res_id");
      iaag_chn_id = ret.getInt("iaag_chn_id");
      iaagId = ret.getInt("svr_id");
      eventInfo.setRes_id(res_id);
      eventInfo.setIaag_chn_id(iaag_chn_id);
      eventInfo.setIaagId(iaagId);
      ret.close();
      pstmt.close();
      iaConfigKey.setEvent_type(event_type);
      iaConfigKey.setIaag_chn_id(iaag_chn_id);
      iaConfigKey.setIaagId(iaagId);
      iaConfigKey.setRes_id(res_id);
      if (getIaConfigList() == null)
        iaConfigList = new HashMap<Long, EventInfo>();
      if (getIaConfigList2() == null)
        iaConfigList2 = new HashMap<IaConfigKey, EventInfo>();
      iaConfigList.put((long) event_id, eventInfo);
      iaConfigList2.put(iaConfigKey, eventInfo);
    } else if (event_genus.equals("event_server")) {
      sql = "SELECT machine_id FROM ti_event_machine_ex WHERE event_id = ?";
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, String.valueOf(event_id));
      ret = pstmt.executeQuery(sql);
      machine_id = ret.getInt("machine_id");
      eventInfo.setMachine_id(machine_id);
      ret.close();
      pstmt.close();
      serverConfigKey.setEvent_type(event_type);
      serverConfigKey.setMachine_id(machine_id);
      if (getServerConfigList() == null)
        serverConfigList = new HashMap<Long, EventInfo>();
      if (getServerConfigList2() == null)
        serverConfigList2 = new HashMap<ServerConfigKey, EventInfo>();
      serverConfigList.put((long) event_id, eventInfo);
      serverConfigList2.put(serverConfigKey, eventInfo);
    } else if (event_genus.equals("event_device")) {
      sql = "SELECT device_id FROM ti_event_device_ex WHERE event_id = ?";
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, String.valueOf(event_id));
      ret = pstmt.executeQuery(sql);
      dev_id = ret.getInt("device_id");
      eventInfo.setDev_id(dev_id);
      ret.close();
      pstmt.close();
      deviceConfigKey.setEvent_type(event_type);
      deviceConfigKey.setDev_id(dev_id);
      if (getDeviceConfigList() == null)
        deviceConfigList = new HashMap<Long, EventInfo>();
      if (getDeviceConfigList2() == null)
        deviceConfigList2 = new HashMap<DeviceConfigKey, EventInfo>();
      deviceConfigList.put((long) event_id, eventInfo);
      deviceConfigList2.put(deviceConfigKey, eventInfo);
    } else {
      System.out.println("error event_genus :" + event_genus);
      return null;
    }
    return eventInfo;
  }

  public void updateEventInfo(Connection conn, int id) {
    EventInfo eventInfo1 = findEventInfo(id);
    EventInfo eventInfo2 = findEventInfo2(id);
    try {
      EventInfo eventInfo3 = createEventInfoFromDB(conn, id);
      if (eventInfo3 == null) {
        System.out.println("error event id:" + id + "no event config found in db");
        return;
      }
      eventInfo1 = eventInfo3;
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void deleteEventInfo(int id) {
    EventInfo eventInfo1 = findEventInfo(id);
    EventInfo eventInfo2 = findEventInfo2(id);
    if (eventInfo1 != null) {
      String event_genus = eventInfo1.getEvent_genus();
      if (event_genus.equals("event_monitor")) {
        getMonitorConfigList().remove(eventInfo1.getEvent_id());
        getMonitorConfigList2().remove(eventInfo2.getMonitorConfigKey());
      } else if (event_genus.equals("event_sio")) {
        getSioConfigList().remove(eventInfo1.getEvent_id());
        getSioConfigList2().remove(eventInfo2.getSioConfigKey());
      } else if (event_genus.equals("event_ ia")) {
        getIaConfigList().remove(eventInfo1.getEvent_id());
        getIaConfigList2().remove(eventInfo2.getIaConfigKey());
      } else if (event_genus.equals("event_server")) {
        getServerConfigList().remove(eventInfo1.getEvent_id());
        getServerConfigList2().remove(eventInfo2.getServerConfigKey());
      } else if (event_genus.equals("event_device")) {
        getDeviceConfigList().remove(eventInfo1.getEvent_id());
        getDeviceConfigList2().remove(eventInfo2.getDeviceConfigKey());
      } else {
        System.out.println("error event_genus :" + event_genus);
        return;
      }
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
    for (int i = 0; i < eventInfos.size(); i++) {
      eventInfos.get(i).getGuardPlan().setGuard_plan_name(guardPlan.getGuard_plan_name());
      eventInfos.get(i).getGuardPlan().setTime_schedule(guardPlan.getTime_schedule());
      eventInfos.get(i).getGuardPlan().setGuard_plan_type(guardPlan.getGuard_plan_type());
      eventInfos.get(i).getGuardPlan().setStart_time(guardPlan.getStart_time());
      eventInfos.get(i).getGuardPlan().setEnd_time(guardPlan.getEnd_time());
      for (int j = 0; j < eventInfos.get(i).getGuardPlan().getTimeSchedule().getTimeScheduleItems().size(); j++) {
        TimeScheduleItem a = eventInfos.get(i).getGuardPlan().getTimeSchedule().getTimeScheduleItems().get(j);
        TimeScheduleItem b = guardPlan.getTimeSchedule().getTimeScheduleItems().get(j);
        a.setDayOfWeek(b.getDayOfWeek());
        for (int k = 0; k < a.getTimePeriods().size(); k++) {
          TimePeriod m = a.getTimePeriods().get(k);
          TimePeriod n = b.getTimePeriods().get(k);
          m.setEt(n.getEt());
          m.setSt(n.getSt());
          m.setType(n.getType());
        }
      }
    }
  }

  public void deleteGuardPlay(Connection conn, int id) {
    List<EventInfo> eventInfos = findEventListByGuardId(id);
    for (int i = 0; i < eventInfos.size(); i++) {
      eventInfos.get(i).setGuardPlan(null);
    }
  }

  public void addLinkage(int id) {

  }

  public void updateLinkage(Connection conn, int id) throws SQLException {
    EventLinkage eventLinkage = createLinkageFromDB(conn,id);
    List<EventInfo> eventInfos = findEventListBylinkageId(id);
    for (int i = 0; i < eventInfos.size(); i++) {
      List<EventLinkage> eventLinkages = eventInfos.get(i).getEventLinkagelist();
      for (int j = 0; j < eventLinkages.size(); j++) {
        EventLinkage eventLinkage1 = eventLinkages.get(j);
        if(eventLinkage1.getLinkage_id() == eventLinkage.getLinkage_id() ){
          eventLinkage1.setEvent_id(eventLinkage.getEvent_id());
          eventLinkage1.setLinkage_id(eventLinkage.getLinkage_id());
          eventLinkage1.setLinkage_type(eventLinkage.getLinkage_type());
          eventLinkage1.setArg1(eventLinkage.getArg1());
          eventLinkage1.setArg2(eventLinkage.getArg2());
          eventLinkage1.setArg3(eventLinkage.getArg3());
          eventLinkage1.setArg4(eventLinkage.getArg4());
          eventLinkage1.setArg5(eventLinkage.getArg5());
          eventLinkage1.setArg6(eventLinkage.getArg6());
          eventLinkage1.setArg7(eventLinkage.getArg7());
          eventLinkage1.setArg8(eventLinkage.getArg8());
        }
      }
    }
  }

  public void deleteLinkage(int id) {
    List<EventInfo> eventInfos = findEventListBylinkageId(id);
    for (int i = 0; i < eventInfos.size(); i++) {
      eventInfos.get(i).deleteLinkagebyId(id);
    }
  }
}
