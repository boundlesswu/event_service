package com.vorxsoft.ieye.eventservice.redis;

class AlarmStormInfo {
  private String event_type;
  private int resourceId;

  private AlarmStormInfo(Builder builder) {
    setEvent_type(builder.event_type);
    setResourceId(builder.resourceId);
  }

  public static Builder newBuilder() {
    return new Builder();
  }


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

  public static final class Builder {
    private String event_type;
    private int resourceId;

    private Builder() {
    }

    public Builder event_type(String val) {
      event_type = val;
      return this;
    }

    public Builder resourceId(int val) {
      resourceId = val;
      return this;
    }

    public AlarmStormInfo build() {
      return new AlarmStormInfo(this);
    }
  }
}