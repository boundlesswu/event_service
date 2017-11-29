package com.vorxsoft.ieye.eventservice.redis;

class AlarmStormRecordItem {
  private AlarmStormRecordItem(Builder builder) {
    setAlarmStormInfo(builder.alarmStormInfo);
    setHappenTime(builder.happenTime);
    setExtraContent(builder.extraContent);
  }

  public static Builder newBuilder() {
    return new Builder();
  }

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


  private AlarmStormInfo alarmStormInfo;
  private long happenTime;
  private String extraContent;

  public long diffTime() {
    return System.currentTimeMillis() / 1000 - happenTime;
  }


  public long getHappenTime() {
    return happenTime;
  }

  public void setHappenTime(long happenTime) {
    this.happenTime = happenTime;
  }

  public static final class Builder {
    private AlarmStormInfo alarmStormInfo;
    private long happenTime;
    private String extraContent;

    private Builder() {
    }

    public Builder alarmStormInfo(AlarmStormInfo val) {
      alarmStormInfo = val;
      return this;
    }

    public Builder happenTime(long val) {
      happenTime = val;
      return this;
    }

    public Builder extraContent(String val) {
      extraContent = val;
      return this;
    }

    public AlarmStormRecordItem build() {
      return new AlarmStormRecordItem(this);
    }
  }
}