package com.vorxsoft.ieye.eventservice.config;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class AlarmStormConfig {
  private HashMap<String,AlarmStorm> alarmStormConfigList;

  public Long getFreshTime() {
    return FreshTime;
  }

  public void setFreshTime(Long freshTime) {
    FreshTime = freshTime;
  }

  private Long FreshTime = 0L;
  public void loadAlarmStormConfig(Connection conn) throws SQLException {
    String sql="SELECT  storm_id ,event_type,event_stom FROM ti_event_stom";
    PreparedStatement pstmt = conn.prepareStatement(sql);
    ResultSet ret = pstmt.executeQuery(sql);
    int stom_id = 0;
    String event_type = null;
    int event_stom = 0;
    AlarmStorm a = new AlarmStorm();
    while(ret.next()) {
      stom_id = ret.getInt("storm_id");
      event_type = ret.getString("event_type");
      event_stom = ret.getInt("event_stom");
      a.setStomId(stom_id);
      a.setEventType(event_type);
      a.setEventStom(event_stom);
      alarmStormConfigList.put(event_type,a);
    }
    ret.close();
    pstmt.close();
    setFreshTime(System.currentTimeMillis());
    return;
  }
  public AlarmStorm getAlarmStorm(String type){
    return alarmStormConfigList.get(type);
  }
}
