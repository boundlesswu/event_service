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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    IauItem iauItem = (IauItem) o;

    if (dev_id != iauItem.dev_id) return false;
    if (svr_id != iauItem.svr_id) return false;
    if (group_id != iauItem.group_id) return false;
    if (chn_video != iauItem.chn_video) return false;
    if (port != iauItem.port) return false;
    if (dev_no != null ? !dev_no.equals(iauItem.dev_no) : iauItem.dev_no != null) return false;
    if (dev_name != null ? !dev_name.equals(iauItem.dev_name) : iauItem.dev_name != null) return false;
    if (dev_type != null ? !dev_type.equals(iauItem.dev_type) : iauItem.dev_type != null) return false;
    if (protocol_type != null ? !protocol_type.equals(iauItem.protocol_type) : iauItem.protocol_type != null)
      return false;
    if (dev_sn != null ? !dev_sn.equals(iauItem.dev_sn) : iauItem.dev_sn != null) return false;
    if (remark != null ? !remark.equals(iauItem.remark) : iauItem.remark != null) return false;
    if (ip != null ? !ip.equals(iauItem.ip) : iauItem.ip != null) return false;
    if (username != null ? !username.equals(iauItem.username) : iauItem.username != null) return false;
    return password != null ? password.equals(iauItem.password) : iauItem.password == null;
  }

  @Override
  public int hashCode() {
    int result = dev_id;
    result = 31 * result + (dev_no != null ? dev_no.hashCode() : 0);
    result = 31 * result + svr_id;
    result = 31 * result + (dev_name != null ? dev_name.hashCode() : 0);
    result = 31 * result + (dev_type != null ? dev_type.hashCode() : 0);
    result = 31 * result + (protocol_type != null ? protocol_type.hashCode() : 0);
    result = 31 * result + (dev_sn != null ? dev_sn.hashCode() : 0);
    result = 31 * result + group_id;
    result = 31 * result + (remark != null ? remark.hashCode() : 0);
    result = 31 * result + chn_video;
    result = 31 * result + (ip != null ? ip.hashCode() : 0);
    result = 31 * result + port;
    result = 31 * result + (username != null ? username.hashCode() : 0);
    result = 31 * result + (password != null ? password.hashCode() : 0);
    return result;
  }
}
