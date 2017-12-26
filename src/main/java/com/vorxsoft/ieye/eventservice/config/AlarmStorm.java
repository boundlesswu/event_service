package com.vorxsoft.ieye.eventservice.config;

import com.vorxsoft.ieye.eventservice.redis.AlarmStormInfo;

public class AlarmStorm {
  private int stomId;
  private String eventType;
  private int eventStom;

  public AlarmStorm() {
  }

  public AlarmStorm(int stomId, String eventType, int eventStom) {
    this.stomId = stomId;
    this.eventType = eventType;
    this.eventStom = eventStom;
  }

  private AlarmStorm(Builder builder) {
    setStomId(builder.stomId);
    setEventType(builder.eventType);
    setEventStom(builder.eventStom);
  }

  public static Builder newBuilder() {
    return new Builder();
  }


  public int getStomId() {
    return stomId;
  }

  public void setStomId(int stomId) {
    this.stomId = stomId;
  }

  public String getEventType() {
    return eventType;
  }

  public void setEventType(String eventType) {
    this.eventType = eventType;
  }

  public int getEventStom() {
    return eventStom;
  }

  public void setEventStom(int eventStom) {
    this.eventStom = eventStom;
  }

  public static final class Builder {
    private int stomId;
    private String eventType;
    private int eventStom;

    private Builder() {
    }

    public Builder stomId(int val) {
      stomId = val;
      return this;
    }

    public Builder eventType(String val) {
      eventType = val;
      return this;
    }

    public Builder eventStom(int val) {
      eventStom = val;
      return this;
    }

    public AlarmStorm build() {
      return new AlarmStorm(this);
    }
  }

  @Override
  public String toString() {
    return "AlarmStorm{" +
            "stomId=" + stomId +
            ", eventType='" + eventType + '\'' +
            ", eventStom=" + eventStom +
            '}';
  }

  public void zero() {
    this.stomId = 0;
    this.eventStom = 0;
    this.eventType = "";
  }

  public void copy(AlarmStorm other) {
    this.stomId = other.getStomId();
    this.eventType = other.getEventType();
    this.eventStom = other.getEventStom();
  }

}
