package com.vorxsoft.ieye.eventservice.util;

import java.util.ArrayList;
import java.util.List;

public class IaagChannelInfo {
  private int iaag_chn_id;
  private int svr_id;
  private int res_id;
  private String preset_no;
  //通道状态 0-停止;1-正在分析;2-分析异常 3-未知
  private int chn_state;
  private int dev_id;
  private List<Integer> event_ids;

  public IaagChannelInfo() {
    this.iaag_chn_id =  0;
    this.svr_id = 0;
    this.res_id = 0;
    this.preset_no ="";
    this.chn_state = 0;
    this.dev_id = 0;
    this.event_ids = new ArrayList<>();
  }

  private IaagChannelInfo(Builder builder) {
    setIaag_chn_id(builder.iaag_chn_id);
    setSvr_id(builder.svr_id);
    setRes_id(builder.res_id);
    setPreset_no(builder.preset_no);
    setChn_state(builder.chn_state);
    setDev_id(builder.dev_id);
    setEvent_ids(builder.event_ids);
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public int getIaag_chn_id() {
    return iaag_chn_id;
  }

  public void setIaag_chn_id(int iaag_chn_id) {
    this.iaag_chn_id = iaag_chn_id;
  }

  public int getSvr_id() {
    return svr_id;
  }

  public void setSvr_id(int svr_id) {
    this.svr_id = svr_id;
  }

  public int getRes_id() {
    return res_id;
  }

  public void setRes_id(int res_id) {
    this.res_id = res_id;
  }

  public String getPreset_no() {
    return preset_no;
  }

  public void setPreset_no(String preset_no) {
    this.preset_no = preset_no;
  }

  public int getChn_state() {
    return chn_state;
  }

  public void setChn_state(int chn_state) {
    this.chn_state = chn_state;
  }

  public int getDev_id() {
    return dev_id;
  }

  public void setDev_id(int dev_id) {
    this.dev_id = dev_id;
  }

  public List<Integer> getEvent_ids() {
    return event_ids;
  }

  public void setEvent_ids(List<Integer> event_ids) {
    this.event_ids = event_ids;
  }

  @Override
  public String toString() {
    return "IaagChannelInfo{" +
            "iaag_chn_id=" + iaag_chn_id +
            ", svr_id=" + svr_id +
            ", res_id=" + res_id +
            ", preset_no='" + preset_no + '\'' +
            ", chn_state=" + chn_state +
            ", dev_id=" + dev_id +
            ", event_ids=" + event_ids +
            '}';
  }

  public static final class Builder {
    private int iaag_chn_id;
    private int svr_id;
    private int res_id;
    private String preset_no;
    private int chn_state;
    private int dev_id;
    private List<Integer> event_ids;

    private Builder() {
    }

    public Builder iaag_chn_id(int val) {
      iaag_chn_id = val;
      return this;
    }

    public Builder svr_id(int val) {
      svr_id = val;
      return this;
    }

    public Builder res_id(int val) {
      res_id = val;
      return this;
    }

    public Builder preset_no(String val) {
      preset_no = val;
      return this;
    }

    public Builder chn_state(int val) {
      chn_state = val;
      return this;
    }

    public Builder dev_id(int val) {
      dev_id = val;
      return this;
    }

    public Builder event_ids(List<Integer> val) {
      event_ids = val;
      return this;
    }

    public IaagChannelInfo build() {
      return new IaagChannelInfo(this);
    }
  }
}
