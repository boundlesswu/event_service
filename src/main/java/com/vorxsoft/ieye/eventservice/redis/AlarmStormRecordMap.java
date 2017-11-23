package com.vorxsoft.ieye.eventservice.redis;

import java.util.HashMap;

public class AlarmStormRecordMap {


  class AlarmStormRecordItem{
    public AlarmStormInfo getAlarmStormInfo() {
      return alarmStormInfo;
    }

    public void setAlarmStormInfo(AlarmStormInfo alarmStormInfo) {
      this.alarmStormInfo = alarmStormInfo;
    }

    public String getExtraContent() {
      return extraContent;
    }

    public void setExtraContent(String extraContent) {
      this.extraContent = extraContent;
    }

    class AlarmStormInfo{
      private String event_type;
      private int resourceId;
      //private String resourceNo;
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

//      public String getResourceNo() {
//        return resourceNo;
//      }
//
//      public void setResourceNo(String resourceNo) {
//        this.resourceNo = resourceNo;
//      }
    }
    private AlarmStormInfo alarmStormInfo;
    private long happenTime;
    private String extraContent;

    public long diffTime(){
      return System.currentTimeMillis()/1000 - happenTime;
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

  public AlarmStormRecordItem add(AlarmStormRecordItem.AlarmStormInfo info, long happenTime,String extraContent ){
    AlarmStormRecordItem item = new AlarmStormRecordItem();
    item.setAlarmStormInfo(info);
    item.setHappenTime(happenTime);
    item.setExtraContent(extraContent);
    return alarmStormRecordItemHashMap.put(item.getAlarmStormInfo(),item);
  }

  public AlarmStormRecordItem add(String event_type,int resourceId,long happenTime,String extraContent){
    AlarmStormRecordItem item = new AlarmStormRecordItem();
    AlarmStormRecordItem.AlarmStormInfo info = null;
    info.setEvent_type(event_type);
    info.setResourceId(resourceId);
    //info.setResourceNo(resourceNo);
    item.setAlarmStormInfo(info);
    item.setHappenTime(happenTime);
    item.setExtraContent(extraContent);
    return alarmStormRecordItemHashMap.put(info,item);
  }
  
  public long diffCurrentTime(AlarmStormRecordItem.AlarmStormInfo info) {
    AlarmStormRecordItem a = alarmStormRecordItemHashMap.get(info);
    if (a == null) {
      System.out.println("no AlarmStormInfo: " + info + "found:");
      return Long.MIN_VALUE;
    } else {
      return a.diffTime();
    }
  }
  public long diffCurrentTime(long happenTime){
      return System.currentTimeMillis()/1000 - happenTime;
  }
}

