package com.vorxsoft.ieye.eventservice.config;

public class ServerConfigKey{
  private String event_type;
  private int machine_id=0;

  public ServerConfigKey() {
  }

  public ServerConfigKey(String event_type, int machine_id) {
    this.event_type = event_type;
    this.machine_id = machine_id;
  }

  private ServerConfigKey(Builder builder) {
    setEvent_type(builder.event_type);
    setMachine_id(builder.machine_id);
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

  public int getMachine_id() {
    return machine_id;
  }

  public void setMachine_id(int machine_id) {
    this.machine_id = machine_id;
  }

  public static final class Builder {
    private String event_type;
    private int machine_id;

    private Builder() {
    }

    public Builder event_type(String val) {
      event_type = val;
      return this;
    }

    public Builder machine_id(int val) {
      machine_id = val;
      return this;
    }

    public ServerConfigKey build() {
      return new ServerConfigKey(this);
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    ServerConfigKey that = (ServerConfigKey) o;

    if (machine_id != that.machine_id) return false;
    return event_type.equals(that.event_type);
  }

  @Override
  public int hashCode() {
    int result = event_type.hashCode();
    result = 31 * result + machine_id;
    return result;
  }
}