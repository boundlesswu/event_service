package com.vorxsoft.ieye.eventservice.config;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class AlarmStormConfig {
  private HashMap<String,AlarmStorm> alarmStormConfigList;
  public void loadAlarmStormConfig(Connection conn) throws SQLException {
    String sql="SELECT  storm_id ,event_type,event_stom FROM ti_event_stom";
    PreparedStatement pstmt = conn.prepareStatement(sql);
    ResultSet ret = pstmt.executeQuery(sql);
    Long stom_id = 0L;
    String event_type = null;
    Long event_stom = 0L;
    AlarmStorm a = new AlarmStorm();
    while(ret.next()) {
      stom_id = ret.getLong("storm_id");
      event_type = ret.getString("event_type");
      event_stom = ret.getLong("event_stom");
      a.setStom_id(stom_id);
      a.setEvent_type(event_type);
      a.setEvent_stom(event_stom);
      alarmStormConfigList.put(event_type,a);
    }
    ret.close();
    pstmt.close();
    return;
  }
  public AlarmStorm getAlarmStorm(String type){
    return alarmStormConfigList.get(type);
  }
}
