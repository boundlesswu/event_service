package com.vorxsoft.ieye.eventservice.redis;

import java.util.HashMap;

public class AlarmStormRecord {


  class AlarmStormRecordItem{
    public AlarmStormInfo getAlarmStormInfo() {
      return alarmStormInfo;
    }

    public void setAlarmStormInfo(AlarmStormInfo alarmStormInfo) {
      this.alarmStormInfo = alarmStormInfo;
    }

    class AlarmStormInfo{
      private String event_type;
      private int resourceId;
      private String resourceNo;
      public String getEvent_type() {
        return event_type;
      }

      public void setEvent_type(String event_type) {
        this.event_type = event_type;
      }

      public int getResourceId() {
        return resourceId;
      }

      public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
      }

      public String getResourceNo() {
        return resourceNo;
      }

      public void setResourceNo(String resourceNo) {
        this.resourceNo = resourceNo;
      }
    }
    private AlarmStormInfo alarmStormInfo;
    private long happenTime;

    public long diffTime(){
      return System.currentTimeMillis() - happenTime*1000;
    }


    public long getHappenTime() {
      return happenTime;
    }

    public void setHappenTime(long happenTime) {
      this.happenTime = happenTime;
    }
  }
  private HashMap<AlarmStormRecordItem.AlarmStormInfo,AlarmStormRecordItem>  alarmStormRecordItemHashMap;

  public AlarmStormRecordItem add(AlarmStormRecordItem item){
    return alarmStormRecordItemHashMap.put(item.getAlarmStormInfo(),item);
  }

  public long diffCurrentTime(AlarmStormRecordItem.AlarmStormInfo info){
    AlarmStormRecordItem a = alarmStormRecordItemHashMap.get(info);
    if(a == null) {
      System.out.println("no AlarmStormInfo: "+info+"found:");
      return Long.MIN_VALUE;
    }else {
      return a.diffTime();
    }
  }



  }
