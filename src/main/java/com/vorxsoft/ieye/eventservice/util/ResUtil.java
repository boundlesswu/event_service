package com.vorxsoft.ieye.eventservice.util;

import java.sql.Connection;

public interface ResUtil  {

  void init(Connection conn);
  ResInfo getResInfo(int id);
  ResInfo getResInfo(String no);
  String getResName(int id);
  String getResName(String no);
  String getResNo(int id);
  int getResId(String no);
  int getResDevId(int id);
  String getResDevNo(int  id);
  String getResUid(int id);

  SvrInfo getSvrInfo(int id);
  SvrInfo getSvrInfo(String no);
  String getSvrName(int id);
  String getSvrName(String no) ;

  String getMachineName(int id);
  String getMachineName(String no);

  String getDevName(int id);
  String getDevName(String no);

}
