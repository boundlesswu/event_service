package com.vorxsoft.ieye.eventservice.util;

import com.vorxsoft.ieye.eventservice.grpc.GrpcClient;
import com.vorxsoft.ieye.eventservice.grpc.VsIAClient;

public class IauItem {
  private int dev_id;
  private String dev_no;
  private int svr_id;
  private String dev_name;
  private String dev_type;
  private String protocol_type;
  private String dev_sn;
  private int group_id;
  private String remark;
  private int chn_video;
  private String ip;
  private int port;
  private String username;
  private String password;
  VsIAClient client;

  public VsIAClient getClient() {
    return client;
  }

  public void setClient(VsIAClient client) {
    this.client = client;
  }

  public IauItem() {
  }

  private IauItem(Builder builder) {
    setDev_id(builder.dev_id);
    setDev_no(builder.dev_no);
    setSvr_id(builder.svr_id);
    setDev_name(builder.dev_name);
    setDev_type(builder.dev_type);
    setProtocol_type(builder.protocol_type);
    setDev_sn(builder.dev_sn);
    setGroup_id(builder.group_id);
    setRemark(builder.remark);
    setChn_video(builder.chn_video);
    setIp(builder.ip);
    setPort(builder.port);
    setUsername(builder.username);
    setPassword(builder.password);
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public int getDev_id() {
    return dev_id;
  }

  public void setDev_id(int dev_id) {
    this.dev_id = dev_id;
  }

  public String getDev_no() {
    return dev_no;
  }

  public void setDev_no(String dev_no) {
    this.dev_no = dev_no;
  }

  public int getSvr_id() {
    return svr_id;
  }

  public void setSvr_id(int svr_id) {
    this.svr_id = svr_id;
  }

  public String getDev_name() {
    return dev_name;
  }

  public void setDev_name(String dev_name) {
    this.dev_name = dev_name;
  }

  public String getDev_type() {
    return dev_type;
  }

  public void setDev_type(String dev_type) {
    this.dev_type = dev_type;
  }

  public String getProtocol_type() {
    return protocol_type;
  }

  public void setProtocol_type(String protocol_type) {
    this.protocol_type = protocol_type;
  }

  public String getDev_sn() {
    return dev_sn;
  }

  public void setDev_sn(String dev_sn) {
    this.dev_sn = dev_sn;
  }

  public int getGroup_id() {
    return group_id;
  }

  public void setGroup_id(int group_id) {
    this.group_id = group_id;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public int getChn_video() {
    return chn_video;
  }

  public void setChn_video(int chn_video) {
    this.chn_video = chn_video;
  }

  public String getIp() {
    return ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }

  public int getPort() {
    return port;
  }

  public void setPort(int port) {
    this.port = port;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    return super.equals(obj);
  }

  @Override
  protected Object clone() throws CloneNotSupportedException {
    return super.clone();
  }

  @Override
  public String toString() {
    return super.toString();
  }

  @Override
  protected void finalize() throws Throwable {
    super.finalize();
  }

  public static final class Builder {
    private int dev_id;
    private String dev_no;
    private int svr_id;
    private String dev_name;
    private String dev_type;
    private String protocol_type;
    private String dev_sn;
    private int group_id;
    private String remark;
    private int chn_video;
    private String ip;
    private int port;
    private String username;
    private String password;

    private Builder() {
    }

    public Builder dev_id(int val) {
      dev_id = val;
      return this;
    }

    public Builder dev_no(String val) {
      dev_no = val;
      return this;
    }

    public Builder svr_id(int val) {
      svr_id = val;
      return this;
    }

    public Builder dev_name(String val) {
      dev_name = val;
      return this;
    }

    public Builder dev_type(String val) {
      dev_type = val;
      return this;
    }

    public Builder protocol_type(String val) {
      protocol_type = val;
      return this;
    }

    public Builder dev_sn(String val) {
      dev_sn = val;
      return this;
    }

    public Builder group_id(int val) {
      group_id = val;
      return this;
    }

    public Builder remark(String val) {
      remark = val;
      return this;
    }

    public Builder chn_video(int val) {
      chn_video = val;
      return this;
    }

    public Builder ip(String val) {
      ip = val;
      return this;
    }

    public Builder port(int val) {
      port = val;
      return this;
    }

    public Builder username(String val) {
      username = val;
      return this;
    }

    public Builder password(String val) {
      password = val;
      return this;
    }

    public IauItem build() {
      return new IauItem(this);
    }
  }

}
