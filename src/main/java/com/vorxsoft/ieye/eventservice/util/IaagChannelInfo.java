package com.vorxsoft.ieye.eventservice.util;

import com.sun.org.apache.regexp.internal.RE;
import com.sun.prism.paint.Stop;
import com.vorxsoft.ieye.proto.IACMDType;
import com.vorxsoft.ieye.proto.ResInfo;
import java.sql.Connection;
//import redis.clients.jedis.Connection;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;

public class IaagChannelInfo {
  private int iaag_chn_id;
  private int svr_id;
  private int res_id;
  private String preset_no="";
  //通道状态 0-停止;1-正在分析;2-分析异常 3-未知
  private int chn_state;
  private int dev_id;
  private List<Integer> event_ids;
  boolean needSendcmd;
  boolean hasSendCmd;
  private IACMDType cmdType;

  public IaagChannelInfo() {
    this.iaag_chn_id =  0;
    this.svr_id = 0;
    this.res_id = 0;
    this.preset_no ="";
    this.chn_state = 0;
    this.dev_id = 0;
    this.event_ids = new ArrayList<>();
    this.needSendcmd = false;
    this.hasSendCmd = false;
    cmdType = IACMDType.Stop;
  }

  public com.vorxsoft.ieye.proto.ResInfo convert2ResInfo(Connection conn){
    ResUtil resUtil = new ResUtilImpl();
    resUtil.init(conn);
    com.vorxsoft.ieye.proto.ResInfo resInfo = ResInfo.newBuilder().
            setResourceId(getRes_id()).
            setResourceNo(resUtil.getResNo(getRes_id())).
            setDeviceId(resUtil.getResDevId(getRes_id())).
            setDeviceNo(resUtil.getResDevNo(getRes_id())).
            setResourceUid(resUtil.getResUid(getRes_id())).
            build();
     return resInfo;
  }

  private IaagChannelInfo(Builder builder) {
    setIaag_chn_id(builder.iaag_chn_id);
    setSvr_id(builder.svr_id);
    setRes_id(builder.res_id);
    setPreset_no(builder.preset_no);
    setChn_state(builder.chn_state);
    setDev_id(builder.dev_id);
    setEvent_ids(builder.event_ids);
    needSendcmd = builder.needSendcmd;
    hasSendCmd = builder.hasSendCmd;
    cmdType = builder.cmdType;
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
            ", needSendcmd=" + needSendcmd +
            ", hasSendCmd=" + hasSendCmd +
            ", cmdType=" + cmdType +
            '}';
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public boolean isNeedSendcmd() {
    return needSendcmd;
  }

  public void setNeedSendcmd(boolean needSendcmd) {
    this.needSendcmd = needSendcmd;
  }

  public boolean isHasSendCmd() {
    return hasSendCmd;
  }

  public void setHasSendCmd(boolean hasSendCmd) {
    this.hasSendCmd = hasSendCmd;
  }

  public IACMDType getCmdType() {
    return cmdType;
  }

  public void setCmdType(IACMDType cmdType) {
    this.cmdType = cmdType;
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

  public static final class Builder {
    private int iaag_chn_id;
    private int svr_id;
    private int res_id;
    private String preset_no;
    private int chn_state;
    private int dev_id;
    private List<Integer> event_ids;
    private boolean needSendcmd;
    private boolean hasSendCmd;
    private IACMDType cmdType;

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

    public Builder needSendcmd(boolean val) {
      needSendcmd = val;
      return this;
    }

    public Builder hasSendCmd(boolean val) {
      hasSendCmd = val;
      return this;
    }

    public Builder cmdType(IACMDType val) {
      cmdType = val;
      return this;
    }

    public IaagChannelInfo build() {
      return new IaagChannelInfo(this);
    }
  }

  public void store2redis(Jedis jedis){
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof IaagChannelInfo)) return false;

    IaagChannelInfo that = (IaagChannelInfo) o;

    if (iaag_chn_id != that.iaag_chn_id) return false;
    if (svr_id != that.svr_id) return false;
    if (res_id != that.res_id) return false;
    if (chn_state != that.chn_state) return false;
    if (dev_id != that.dev_id) return false;
    if (needSendcmd != that.needSendcmd) return false;
    if (hasSendCmd != that.hasSendCmd) return false;
    if (preset_no != null ? !preset_no.equals(that.preset_no) : that.preset_no != null) return false;
    if (event_ids != null ? !event_ids.equals(that.event_ids) : that.event_ids != null) return false;
    return cmdType == that.cmdType;
  }

  @Override
  public int hashCode() {
    int result = iaag_chn_id;
    result = 31 * result + svr_id;
    result = 31 * result + res_id;
    result = 31 * result + (preset_no != null ? preset_no.hashCode() : 0);
    result = 31 * result + chn_state;
    result = 31 * result + dev_id;
    result = 31 * result + (event_ids != null ? event_ids.hashCode() : 0);
    result = 31 * result + (needSendcmd ? 1 : 0);
    result = 31 * result + (hasSendCmd ? 1 : 0);
    result = 31 * result + (cmdType != null ? cmdType.hashCode() : 0);
    return result;
  }

  public void zero() {
    this.iaag_chn_id = 0;
    this.svr_id = 0;
    this.res_id = 0;
    this.preset_no = "";
    this.chn_state = 0;
    this.dev_id = 0;
    for (Integer a : this.event_ids) {
      event_ids.remove(a);
      a = 0;
    }
    this.needSendcmd = false;
    this.hasSendCmd = false;
    cmdType = IACMDType.Stop;
  }

}
