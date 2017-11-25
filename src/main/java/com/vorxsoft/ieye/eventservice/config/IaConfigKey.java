package com.vorxsoft.ieye.eventservice.config;

public class  IaConfigKey {
  private String event_type;
  private int res_id;
  private int iaagId;
  private int iaag_chn_id;

  public IaConfigKey() {
  }
  public IaConfigKey(String event_type, int res_id, int iaagId, int iaag_chn_id) {
    this.event_type = event_type;
    this.res_id = res_id;
    this.iaagId = iaagId;
    this.iaag_chn_id = iaag_chn_id;
  }

  private IaConfigKey(Builder builder) {
    setEvent_type(builder.event_type);
    setRes_id(builder.res_id);
    setIaagId(builder.iaagId);
    setIaag_chn_id(builder.iaag_chn_id);
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

  public int getIaagId() {
    return iaagId;
  }

  public void setIaagId(int iaagId) {
    this.iaagId = iaagId;
  }

  public int getIaag_chn_id() {
    return iaag_chn_id;
  }

  public void setIaag_chn_id(int iaag_chn_id) {
    this.iaag_chn_id = iaag_chn_id;
  }

  public static final class Builder {
    private String event_type;
    private int res_id;
    private int iaagId;
    private int iaag_chn_id;

    private Builder() {
    }

    public Builder event_type(String val) {
      event_type = val;
      return this;
    }

    public Builder res_id(int val) {
      res_id = val;
      return this;
    }

    public Builder iaagId(int val) {
      iaagId = val;
      return this;
    }

    public Builder iaag_chn_id(int val) {
      iaag_chn_id = val;
      return this;
    }

    public IaConfigKey build() {
      return new IaConfigKey(this);
    }
  }
}