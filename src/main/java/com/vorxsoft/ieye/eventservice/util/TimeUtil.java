package com.vorxsoft.ieye.eventservice.util;

import java.sql.Time;
import java.sql.Timestamp;

public class TimeUtil {
  static public String timestmap2string(Timestamp t){
    return "test";
  }
  static public Timestamp string2timestamp(String  s) {
    //String的类型必须形如： yyyy-mm-dd hh:mm:ss[.f...] 这样的格式，中括号表示可选，否则报错！！！
    System.out.println(s);
    return Timestamp.valueOf(s);
  }
}
