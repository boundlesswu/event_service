package com.vorxsoft.ieye.eventservice.config;

public class DeviceConfigKey {
  private String event_type;
  private int dev_id;

  public DeviceConfigKey() {
  }

  public DeviceConfigKey(String event_type, int dev_id) {
    this.event_type = event_type;
    this.dev_id = dev_id;
  }

  private DeviceConfigKey(Builder builder) {
    setEvent_type(builder.event_type);
    setDev_id(builder.dev_id);
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

  public int getDev_id() {
    return dev_id;
  }

  public void setDev_id(int dev_id) {
    this.dev_id = dev_id;
  }

  public static final class Builder {
    private String event_type;
    private int dev_id;

    private Builder() {
    }

    public Builder event_type(String val) {
      event_type = val;
      return this;
    }

    public Builder dev_id(int val) {
      dev_id = val;
      return this;
    }

    public DeviceConfigKey build() {
      return new DeviceConfigKey(this);
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    DeviceConfigKey that = (DeviceConfigKey) o;

    if (dev_id != that.dev_id) return false;
    return event_type.equals(that.event_type);
  }

  @Override
  public int hashCode() {
    int result = event_type.hashCode();
    result = 31 * result + dev_id;
    return result;
  }

  @Override
  public String toString() {
    return "DeviceConfigKey{" +
            "event_type='" + event_type + '\'' +
            ", dev_id=" + dev_id +
            '}';
  }
}