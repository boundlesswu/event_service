package com.vorxsoft.ieye.eventservice.util;

import java.sql.Timestamp;

public class TimeUtil {
  static public String timestmap2string(Timestamp t){
    return "test";
  }
  static public Timestamp string2timestamp(String  s) {
    Timestamp t = new Timestamp(Integer.getInteger(s));
    return t;
  }
}
