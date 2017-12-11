package com.vorxsoft.ieye.eventservice.config;

public class  MonitorConfigKey {
  private String event_type;
  private int res_id;

  MonitorConfigKey(){

  }
  public MonitorConfigKey(String event_type,int res_id ){
    this.event_type = event_type;
    this.res_id = res_id;
  }

  private MonitorConfigKey(Builder builder) {
    setEvent_type(builder.event_type);
    setRes_id(builder.res_id);
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

  public int getRes_id() {
    return res_id;
  }


  public void setRes_id(int res_id) {
    this.res_id = res_id;
  }

  public static final class Builder {
    private String event_type;
    private int res_id;

    private Builder() {
    }

    public Builder event_type(String val) {
      event_type = val;
      return this;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;

      Builder builder = (Builder) o;

      if (res_id != builder.res_id) return false;
      return event_type.equals(builder.event_type);
    }

    @Override
    public int hashCode() {
      int result = event_type.hashCode();
      result = 31 * result + res_id;
      return result;
    }

    public Builder res_id(int val) {
      res_id = val;
      return this;
    }

    public MonitorConfigKey build() {
      return new MonitorConfigKey(this);
    }
  }
}