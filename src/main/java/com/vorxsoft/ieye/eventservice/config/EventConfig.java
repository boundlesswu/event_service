package com.vorxsoft.ieye.eventservice.config;

import com.vorxsoft.ieye.eventservice.linkage.EventLinkage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class EventConfig {
  private HashMap<Long,EventInfo> monitorConfigList;
  private HashMap<Long,EventInfo> iaConfigList;
  private HashMap<Long,EventInfo> sioConfigList;
  private HashMap<Long,EventInfo> serverConfigList;
  private HashMap<Long,EventInfo> deviceConfigList;

  private HashMap<MonitorConfigKey,EventInfo> monitorConfigList2;
  private HashMap<IaConfigKey,EventInfo> iaConfigList2;
  private HashMap<SioConfigKey,EventInfo> sioConfigList2;
  private HashMap<ServerConfigKey,EventInfo> serverConfigList2;
  private HashMap<DeviceConfigKey,EventInfo> deviceConfigList2;

  private int listNum;
  private int disListNum;

  public Long getFreshTime() {
    return freshTime;
  }

  public void setFreshTime(Long freshTime) {
    this.freshTime = freshTime;
  }

  Long freshTime =0L;


  public int getListNum() {
    return listNum;
  }
  public void setListNum() {
    this.listNum = monitorConfigList.size()+iaConfigList.size()
        +sioConfigList.size()+serverConfigList.size()+deviceConfigList.size();
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

  public void clearConfig(){


    Iterator iter = monitorConfigList.entrySet().iterator();
    while (iter.hasNext()) {
      Map.Entry entry = (Map.Entry) iter.next();
      Object key = entry.getKey();
      Object val = entry.getValue();
      EventInfo eventInfo = (EventInfo) val;
      eventInfo.getGuardPlan().clear();
      eventInfo.getEventLinkagelist().clear();
      eventInfo.clear();
      monitorConfigList.remove(key);
    }
    monitorConfigList.clear();

    Iterator iter2 = monitorConfigList2.entrySet().iterator();
    while (iter.hasNext()) {
      Map.Entry entry = (Map.Entry) iter.next();
      Object key = entry.getKey();
      Object val = entry.getValue();
      EventInfo eventInfo = (EventInfo) val;
      eventInfo.getGuardPlan().clear();
      eventInfo.getEventLinkagelist().clear();
      eventInfo.clear();
      monitorConfigList2.remove(key);
    }
    monitorConfigList2.clear();

  }

  public void reLoadConfig(Connection conn) throws SQLException {
    clearConfig();
    loadConfig(conn);
  }

  public void loadConfig(Connection conn) throws SQLException {
    String sql="SELECT  COUNT(*) FROM ti_event WHERE enable_state = 1";
    PreparedStatement pstmt = conn.prepareStatement(sql);
    ResultSet ret = pstmt.executeQuery(sql);
    listNum =  ret.getInt("totalCount");
    sql="SELECT  COUNT(*) FROM ti_event WHERE enable_state != 1";
    pstmt = conn.prepareStatement(sql);
    ret = pstmt.executeQuery(sql);
    disListNum = ret.getInt("totalCount");
    int num =  listNum + disListNum;
    if( num <= 0) return;
    int event_id;
    String event_no;
    String event_genus;
    String event_type;
    String event_name;
    String event_desc;
    int enable_state=1;
    int event_level;
    int auto_release_interval;
    GuardPlan guardPlan = null;
    int guard_plan_id;
    int res_id;
    int machine_id;
    int dev_id;
    int sourceId=0;
    int iaagId=0;
    int iaag_chn_id=0;
    EventLinkage eventLinkage;
    MonitorConfigKey monitorConfigKey =  new MonitorConfigKey();
    IaConfigKey iaConfigKey = new IaConfigKey();
    SioConfigKey sioConfigKey =  new SioConfigKey();
    ServerConfigKey serverConfigKey = new ServerConfigKey();
    DeviceConfigKey deviceConfigKey = new DeviceConfigKey();
    sql = "SELECT event_id,event_no,event_genus,event_name,event_desc,event_level,auto_release_interval,event_type,guard_plan_id " +
        "from ti_event inner JOIN ti_guard_plan on ti_event.guard_plan_id = ti_guard_plan.guard_plan_id" +
        " where ti_event.enable_state = 1";
    pstmt = conn.prepareStatement(sql);
    ret = pstmt.executeQuery(sql);
    int i = 0;
    while(ret.next()){
      event_id = ret.getInt("event_id");
      event_no = ret.getString("event_no");
      event_genus = ret.getString("event_genus");
      event_type = ret.getString("event_type");
      event_name = ret.getString("event_name");
      event_desc = ret.getString("event_desc");
      event_level = ret.getInt("event_level");
      auto_release_interval = ret.getInt("auto_release_interval");
      guard_plan_id = ret.getInt("guard_plan_id;");
      String sql2 = "SELECT guard_plan_name,time_schedule,guard_plan_type,start_time,end_time " +
          "FROM ti_guard_plan WHERE guard_plan_id = ?";
      PreparedStatement pstmt2 = conn.prepareStatement(sql2);
      pstmt2.setString(1, String.valueOf(guard_plan_id));
      ResultSet ret2 = pstmt2.executeQuery(sql2);
      guardPlan.setGuard_plan_id(guard_plan_id);
      guardPlan.setGuard_plan_name(ret2.getString("guard_plan_name"));
      guardPlan.setGuard_plan_type(guardPlan.Long2GuardPlanType(ret2.getInt("guard_plan_type")));
      guardPlan.setTimeSchedule(ret2.getString("time_schedule"));
      guardPlan.setStart_time(ret2.getTimestamp("start_time"));
      guardPlan.setEnd_time(ret2.getTimestamp("end_time"));
      ret2.close();
      pstmt2.close();
      if(event_genus.equals( "event_monitor") ){
        sql2 ="SELECT res_id FROM ti_event_monitor_ex WHERE event_id = ?";
        pstmt2 = conn.prepareStatement(sql2);
        pstmt2.setString(1, String.valueOf(event_id));
        ret2 = pstmt2.executeQuery(sql2);
        sourceId = ret2.getInt("res_id");
        res_id = ret2.getInt("res_id");
        ret2.close();
        pstmt2.close();
        monitorConfigKey.setEvent_type(event_type);
        monitorConfigKey.setRes_id(res_id);
      }else if(event_genus .equals("event_sio") ){
        sql2 ="SELECT res_id FROM ti_event_sio_ex WHERE event_id = ?";
        pstmt2 = conn.prepareStatement(sql2);
        pstmt2.setString(1, String.valueOf(event_id));
        ret2 = pstmt2.executeQuery(sql2);
        sourceId = ret2.getInt("res_id");
        res_id = ret2.getInt("res_id");
        ret2.close();
        pstmt2.close();
        sioConfigKey.setEvent_type(event_type);
        sioConfigKey.setRes_id(res_id);
      }else if(event_genus .equals("event_ ia") ){
        sql2 ="SELECT res_id,svr_id,iaag_chn_id ti_event_ ia_ex WHERE event_id = ?";
        pstmt2 = conn.prepareStatement(sql2);
        pstmt2.setString(1, String.valueOf(event_id));
        ret2 = pstmt2.executeQuery(sql2);
        sourceId = ret2.getInt("res_id");
        res_id = ret2.getInt("res_id");
        iaag_chn_id = ret2.getInt("iaag_chn_id");
        iaagId = ret2.getInt("svr_id");
        ret2.close();
        pstmt2.close();
        iaConfigKey.setEvent_type(event_type);
        iaConfigKey.setIaag_chn_id(iaag_chn_id);
        iaConfigKey.setIaagId(iaagId);
        iaConfigKey.setRes_id(res_id);
      }else if(event_genus .equals("event_server") ){
        sql2 ="SELECT machine_id FROM ti_event_machine_ex WHERE event_id = ?";
        pstmt2 = conn.prepareStatement(sql2);
        pstmt2.setString(1, String.valueOf(event_id));
        ret2 = pstmt2.executeQuery(sql2);
        sourceId = ret2.getInt("machine_id");
        machine_id = ret2.getInt("machine_id");
        ret2.close();
        pstmt2.close();
        serverConfigKey.setEvent_type(event_type);
        serverConfigKey.setMachine_id(machine_id);
      }else if(event_genus .equals("event_device") ){
        sql2 ="SELECT device_id FROM ti_event_device_ex WHERE event_id = ?";
        pstmt2 = conn.prepareStatement(sql2);
        pstmt2.setString(1, String.valueOf(event_id));
        ret2 = pstmt2.executeQuery(sql2);
        sourceId = ret2.getInt("device_id");
        dev_id = ret2.getInt("device_id");
        ret2.close();
        pstmt2.close();
        deviceConfigKey.setEvent_type(event_type);
        deviceConfigKey.setDev_id(dev_id);
      }else{
        System.out.println("error event_genus :"+ event_genus);
        continue;
      }
      EventInfo a = new EventInfo();
      a.setEvent_id(event_id);
      a.setEvent_level(event_level);
      a.setEvent_name(event_name);
      a.setEvent_no(event_no);
      a.setEvent_type(event_type);
      a.setEvent_desc(event_desc);
      a.setEvent_genus(event_genus);
      a.setEnable_state(enable_state);
      a.setGuardPlan(guardPlan);
      a.setIaag_chn_id(iaag_chn_id);
      a.setIaagId(iaagId);
      a.setSourceId(sourceId);
      a.setAuto_release_interval(auto_release_interval);
      //linage todo
      sql2 ="SELECT linkage_id,linkage_type,arg1,arg2, arg3,arg4,arg5,arg6,arg7,arg8 " +
          "FROM ti_event_linkage WHERE event_id = ?";
      pstmt2 = conn.prepareStatement(sql2);
      pstmt2.setString(1, String.valueOf(event_id));
      ret2 = pstmt2.executeQuery(sql2);
      EventLinkage b = new EventLinkage();
      List<EventLinkage> c = new ArrayList();
      for (int j = 0; ret2.next() ; j++) {
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
        linkage_id = ret2.getInt("linkage_id");
        linkage_type = ret2.getString("linkage_type");
        arg1 = ret2.getString("arg1");
        arg2 = ret2.getString("arg2");
        arg3 = ret2.getString("arg3");
        arg4 = ret2.getString("arg4");
        arg5 = ret2.getString("arg5");
        arg6 = ret2.getString("arg6");
        arg7 = ret2.getString("arg7");
        arg8 = ret2.getString("arg8");
        b.setEvent_id(event_id);
        b.setLinkage_id(linkage_id);
        b.setLinkage_type(linkage_type);
        b.setArg1(arg1);
        b.setArg2(arg2);
        b.setArg3(arg3);
        b.setArg4(arg4);
        b.setArg5(arg5);
        b.setArg6(arg6);
        b.setArg7(arg7);
        b.setArg8(arg8);
        c.add(b);
      }
      a.setEventLinkagelist(c);
      if(event_genus.equals( "event_monitor") ){
        monitorConfigList.put((long) event_id,a);
        monitorConfigList2.put(monitorConfigKey,a);
      }else if(event_genus .equals("event_sio") ){
        sioConfigList.put((long) event_id,a);
        sioConfigList2.put(sioConfigKey,a);
      }else if(event_genus .equals("event_ia") ){
        iaConfigList.put((long) event_id,a);
        iaConfigList2.put(iaConfigKey,a);
      }else if(event_genus .equals("event_server") ){
        serverConfigList.put((long) event_id,a);
        serverConfigList2.put(serverConfigKey,a);
      }else if(event_genus .equals("event_device") ){
        deviceConfigList.put((long) event_id,a);
        deviceConfigList2.put(deviceConfigKey,a);
      }else{
        System.out.println("error event_genus :"+ event_genus);
        continue;
      }
    }
    listNum = monitorConfigList.size()+iaConfigList.size()
        +sioConfigList.size()+serverConfigList.size()+deviceConfigList.size();
    setFreshTime(System.currentTimeMillis());
  }

  public  EventInfo getMonitorConfig(MonitorConfigKey monitorConfigKey){
    return this.monitorConfigList2.get(monitorConfigKey);
  }
  public  EventInfo getSioConfig(SioConfigKey sioConfigKey){
    return this.sioConfigList2.get(sioConfigKey);
  }

  public  EventInfo getIaConfig(IaConfigKey iaConfigKey){
    return this.iaConfigList2.get(iaConfigKey);
  }

  public  EventInfo getServerConfig(ServerConfigKey serverConfigKey){
    return this.serverConfigList2.get(serverConfigKey);
  }

  public  EventInfo getDeviceConfig(DeviceConfigKey deviceConfigKey){
    return this.deviceConfigList2.get(deviceConfigKey);
  }




}
