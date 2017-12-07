package com.vorxsoft.ieye.eventservice.util;

public class IaagInfo {
  private int svr_id;
  private int enable_state;
  private String ip_extranet;
  private String ip_intranet;
  private int port_manage;
  private String preset_no;


  public IaagInfo() {
  }

  private IaagInfo(Builder builder) {
    setSvr_id(builder.svr_id);
    setEnable_state(builder.enable_state);
    setIp_extranet(builder.ip_extranet);
    setIp_intranet(builder.ip_intranet);
    setPort_manage(builder.port_manage);
    setPreset_no(builder.preset_no);
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public int getSvr_id() {
    return svr_id;
  }

  public void setSvr_id(int svr_id) {
    this.svr_id = svr_id;
  }

  public int getEnable_state() {
    return enable_state;
  }

  public void setEnable_state(int enable_state) {
    this.enable_state = enable_state;
  }

  public String getIp_extranet() {
    return ip_extranet;
  }

  public void setIp_extranet(String ip_extranet) {
    this.ip_extranet = ip_extranet;
  }

  public String getIp_intranet() {
    return ip_intranet;
  }

  public void setIp_intranet(String ip_intranet) {
    this.ip_intranet = ip_intranet;
  }

  public int getPort_manage() {
    return port_manage;
  }

  public void setPort_manage(int port_manage) {
    this.port_manage = port_manage;
  }

  public String getPreset_no() {
    return preset_no;
  }

  public void setPreset_no(String preset_no) {
    this.preset_no = preset_no;
  }

  @Override
  public String toString() {
    return "IaagInfo{" +
            "svr_id=" + svr_id +
            ", enable_state=" + enable_state +
            ", ip_extranet='" + ip_extranet + '\'' +
            ", ip_intranet='" + ip_intranet + '\'' +
            ", port_manage=" + port_manage +
            ", preset_no='" + preset_no + '\'' +
            '}';
  }

  public static final class Builder {
    private int svr_id;
    private int enable_state;
    private String ip_extranet;
    private String ip_intranet;
    private int port_manage;
    private String preset_no;

    private Builder() {
    }

    public Builder svr_id(int val) {
      svr_id = val;
      return this;
    }

    public Builder enable_state(int val) {
      enable_state = val;
      return this;
    }

    public Builder ip_extranet(String val) {
      ip_extranet = val;
      return this;
    }

    public Builder ip_intranet(String val) {
      ip_intranet = val;
      return this;
    }

    public Builder port_manage(int val) {
      port_manage = val;
      return this;
    }

    public Builder preset_no(String val) {
      preset_no = val;
      return this;
    }

    public IaagInfo build() {
      return new IaagInfo(this);
    }
  }
}
