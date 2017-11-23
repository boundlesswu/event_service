package com.vorxsoft.ieye.eventservice.redis;

import redis.clients.jedis.Jedis;

import java.util.Map;

public class eventRecordMap {
  public String getsResNo() {
    return sResNo;
  }

  public void setsResNo(String sResNo) {
    this.sResNo = sResNo;
  }

  public String getsDevNo() {
    return sDevNo;
  }

  public void setsDevNo(String sDevNo) {
    this.sDevNo = sDevNo;
  }

  public String getsEventName() {
    return sEventName;
  }

  public void setsEventName(String sEventName) {
    this.sEventName = sEventName;
  }

  public String getsEventType() {
    return sEventType;
  }

  public void setsEventType(String sEventType) {
    this.sEventType = sEventType;
  }

  public String getsHappentime() {
    return sHappentime;
  }

  public void setsHappentime(String sHappentime) {
    this.sHappentime = sHappentime;
  }

  public int getnEventID() {
    return nEventID;
  }

  public void setnEventID(int nEventID) {
    this.nEventID = nEventID;
  }

  public String getsEventDesc() {
    return sEventDesc;
  }

  public void setsEventDesc(String sEventDesc) {
    this.sEventDesc = sEventDesc;
  }

  public String getsDevName() {
    return sDevName;
  }

  public void setsDevName(String sDevName) {
    this.sDevName = sDevName;
  }

  public String getsResName() {
    return sResName;
  }

  public void setsResName(String sResName) {
    this.sResName = sResName;
  }

  public String getsPicpath() {
    return sPicpath;
  }

  public void setsPicpath(String sPicpath) {
    this.sPicpath = sPicpath;
  }

  public String getsEventlevel() {
    return sEventlevel;
  }

  public void setsEventlevel(String sEventlevel) {
    this.sEventlevel = sEventlevel;
  }

  public int getnEventlogID() {
    return nEventlogID;
  }

  public void setnEventlogID(int nEventlogID) {
    this.nEventlogID = nEventlogID;
  }

  public String getsExtraDesc() {
    return sExtraDesc;
  }

  public void setsExtraDesc(String sExtraDesc) {
    this.sExtraDesc = sExtraDesc;
  }

  private String sResNo;                //资源no
  private String sDevNo;				//设备编号
  private String sEventName;            //事件名称
  private String sEventType;            //事件类型
  private String sHappentime;           //事件时间
  private int  nEventID;              //事件ID（可不处理）
  private String sEventDesc;          //事件描述
  private String sDevName;              //设备名称（可不赋值）
  private  String sResName;              //资源名称（可不赋值）
  private String sPicpath;              //事件图片
  private String sEventlevel;          //事件级别
  private int  nEventlogID;          //事件logID
  private String sExtraDesc;         //额外描述

  public void alarmMap2eventMap(Map<String, String> map,Jedis jedis,long count){
    String key = "event_" + count;
    jedis.hmset(key,map);
  }
}
