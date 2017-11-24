package com.vorxsoft.ieye.eventservice.redis;

import java.util.HashMap;

public class AlarmStormRecordMap {

  private HashMap<AlarmStormInfo, AlarmStormRecordItem> map;

  public AlarmStormRecordItem add(AlarmStormRecordItem item) {
    return map.put(item.getAlarmStormInfo(), item);
  }

  public AlarmStormRecordItem add(AlarmStormInfo info, long happenTime, String extraContent) {
    AlarmStormRecordItem item = AlarmStormRecordItem.newBuilder().alarmStormInfo(info).
            happenTime(happenTime).extraContent(extraContent).build();
    return map.put(info, item);
  }

  public AlarmStormRecordItem add(String event_type, int resourceId, long happenTime, String extraContent) {
    AlarmStormInfo info = AlarmStormInfo.newBuilder().event_type(event_type).resourceId(resourceId).build();
    AlarmStormRecordItem item = AlarmStormRecordItem.newBuilder().alarmStormInfo(info).extraContent(event_type).
            happenTime(happenTime).build();
    return map.put(info, item);
  }

  public long diffCurrentTime(AlarmStormInfo info) {
    AlarmStormRecordItem a = map.get(info);
    if (a == null) {
      System.out.println("no AlarmStormInfo: " + info + "found:");
      return Long.MIN_VALUE;
    } else {
      return a.diffTime();
    }
  }

  public long diffCurrentTime(long happenTime) {
    return System.currentTimeMillis() / 1000 - happenTime;
  }
}

