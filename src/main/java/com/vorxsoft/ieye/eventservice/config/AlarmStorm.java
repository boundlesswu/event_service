package com.vorxsoft.ieye.eventservice.config;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AlarmStorm {
  private Long stom_id;
  private String event_type;
  private Long event_stom;


  public Long getStom_id() {
    return stom_id;
  }

  public void setStom_id(Long stom_id) {
    this.stom_id = stom_id;
  }

  public String getEvent_type() {
    return event_type;
  }

  public void setEvent_type(String event_type) {
    this.event_type = event_type;
  }

  public Long getEvent_stom() {
    return event_stom;
  }

  public void setEvent_stom(Long event_stom) {
    this.event_stom = event_stom;
  }


}
