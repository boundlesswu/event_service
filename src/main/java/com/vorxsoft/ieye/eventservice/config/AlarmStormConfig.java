package com.vorxsoft.ieye.eventservice.config;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class AlarmStormConfig {
  public HashMap<String, AlarmStorm> getAlarmStormConfigList() {
    return alarmStormConfigList;
  }

  public void setAlarmStormConfigList(HashMap<String, AlarmStorm> alarmStormConfigList) {
    this.alarmStormConfigList = alarmStormConfigList;
  }

  private HashMap<String,AlarmStorm> alarmStormConfigList;

  public Long getFreshTime() {
    return FreshTime;
  }

  public void setFreshTime(Long freshTime) {
    FreshTime = freshTime;
  }

  private Long FreshTime = 0L;
  public void load(Connection conn) throws SQLException {
    String sql="SELECT  stom_id ,event_type,event_stom FROM ti_event_stom";
    PreparedStatement pstmt = conn.prepareStatement(sql);
    ResultSet ret = pstmt.executeQuery();
    int stom_id = 0;
    String event_type = null;
    int event_stom = 0;
    AlarmStorm a = new AlarmStorm();
    while(ret.next()) {
      stom_id = ret.getInt("stom_id");
      event_type = ret.getString("event_type");
      event_stom = ret.getInt("event_stom");
      a.setStomId(stom_id);
      a.setEventType(event_type);
      a.setEventStom(event_stom);
      if(getAlarmStormConfigList() == null)
        alarmStormConfigList = new HashMap<>();
      getAlarmStormConfigList().put(event_type,a);
    }
    ret.close();
    pstmt.close();
    setFreshTime(System.currentTimeMillis());
    return;
  }

  private AlarmStorm findAlarmStorm(int id){
    Iterator iter = getAlarmStormConfigList().entrySet().iterator();
    while (iter.hasNext()) {
      Map.Entry entry = (Map.Entry) iter.next();
      Object key = entry.getKey();
      Object val = entry.getValue();
      AlarmStorm alarmStorm = (AlarmStorm) val;
      if (alarmStorm.getStomId() == id)
        return alarmStorm;
    }

    return null;
  }

  public void  deleteAlarmStorm(int id){
    AlarmStorm alarmStorm = findAlarmStorm(id);
    if(alarmStorm == null){
      System.out.println("alarm storm id :\"+id+\"iz not found in AlarmStorm list");
      return ;
    }
    getAlarmStormConfigList().remove(alarmStorm.getEventType());
    alarmStorm = null;
  }

  public void updateAlarmStorm(Connection conn,int id) throws SQLException {
    AlarmStorm alarmStorm =  createAlarmStormFromDB(conn,id);
    if(alarmStorm == null){
      return;
    }
    AlarmStorm alarmStorm2 =  findAlarmStorm(id);
    if(alarmStorm2 == null){
      return ;
    }
    getAlarmStormConfigList().remove(alarmStorm2);
    getAlarmStormConfigList().put(alarmStorm.getEventType(),alarmStorm);
    alarmStorm2 = null;
  }

  void addAlarmStorm(Connection conn,int id) throws SQLException {
    AlarmStorm alarmStorm = createAlarmStormFromDB(conn, id);
    if(alarmStorm == null){
      System.out.println("alarm storm id :"+id+"iz empty");
      return ;
    }
    if (getAlarmStormConfigList() == null)
      alarmStormConfigList = new HashMap<>();
    getAlarmStormConfigList().put(alarmStorm.getEventType(), alarmStorm);
  }

  public AlarmStorm createAlarmStormFromDB(Connection conn,int id) throws SQLException {
    String sql = "SELECT event_type,event_stom FROM ti_event_stom WHERE  stom_id = ?";
    PreparedStatement pstmt = conn.prepareStatement(sql);
    pstmt.setString(1, String.valueOf(id));
    ResultSet ret = pstmt.executeQuery(sql);
    AlarmStorm alarmStorm = null;
    if (ret.next()) {
      alarmStorm = AlarmStorm.newBuilder().stomId(id).
          eventType(ret.getString("event_type")).
          eventStom(ret.getInt("event_stom")).build();
    }
    ret.close();
    pstmt.close();
    return alarmStorm;
  }

  public void clearList(HashMap<String,AlarmStorm> list){
    Iterator iter = list.entrySet().iterator();
    while (iter.hasNext()) {
      Map.Entry entry = (Map.Entry) iter.next();
      Object key = entry.getKey();
      list.remove(key);
    }
    list.clear();
  }

  public void clear(){
    clearList(getAlarmStormConfigList());
    setAlarmStormConfigList(null);
  }
  public AlarmStorm getAlarmStorm(String type){
    return alarmStormConfigList.get(type);
  }
}
