package com.vorxsoft.ieye.eventservice.redis;

import com.vorxsoft.ieye.eventservice.util.TimeUtil;

class AlarmStormRecordItem {
  private int alarmStormId;
  private String event_type;
  private long happenTime;

  public AlarmStormRecordItem() {
  }

  public long diffTime(String happenTime) {
    return (TimeUtil.string2timestamplong(happenTime) - this.happenTime) / 1000;
    //return (System.currentTimeMillis() - this.happenTime)/1000;
  }
  @Override
  public String toString() {
    return "AlarmStormRecordItem{" +
            "alarmStormId=" + alarmStormId +
            ", event_type='" + event_type + '\'' +
            ", happenTime=" + happenTime +
            '}';
  }

  public String getEvent_type() {
    return event_type;
  }

  public void setEvent_type(String event_type) {
    this.event_type = event_type;
  }

  private AlarmStormRecordItem(Builder builder) {
    setAlarmStormId(builder.alarmStormId);
    setEvent_type(builder.event_type);
    setHappenTime(builder.happenTime);
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public int getAlarmStormId() {
    return alarmStormId;
  }

  public void setAlarmStormId(int alarmStormId) {
    this.alarmStormId = alarmStormId;
  }

  public long getHappenTime() {
    return happenTime;
  }

  public void setHappenTime(long happenTime) {
    this.happenTime = happenTime;
  }

  public static final class Builder {
    private int alarmStormId;
    private String event_type;
    private long happenTime;

    private Builder() {
    }

    public Builder alarmStormId(int val) {
      alarmStormId = val;
      return this;
    }

    public Builder event_type(String val) {
      event_type = val;
      return this;
    }

    public Builder happenTime(long val) {
      happenTime = val;
      return this;
    }

    public AlarmStormRecordItem build() {
      return new AlarmStormRecordItem(this);
    }
  }
  public void copy(AlarmStormRecordItem other){
    this.alarmStormId = other.getAlarmStormId();
    this.event_type = other.getEvent_type();
    this.happenTime = other.getHappenTime();
  }
}