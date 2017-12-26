package com.vorxsoft.ieye.eventservice.util;


import com.vorxsoft.ieye.eventservice.grpc.VsIAClient;
import com.vorxsoft.ieye.proto.*;
import com.vorxsoft.ieye.proto.ResInfo;

import java.sql.Connection;
import java.util.*;

public class IaagMapItem {
  public IaagInfo iaagInfo;
  public HashMap<Integer, IaagChannelInfo> channels;
  public HashMap<Integer, IauItem> iaus;
  VsIAClient client;

  public void sethasSendCmd() {
    Iterator it = getChannels().entrySet().iterator();
    while (it.hasNext()) {
      Map.Entry entry = (Map.Entry) it.next();
      IaagChannelInfo channal = (IaagChannelInfo) entry.getValue();
      channal.setHasSendCmd(true);
    }
  }

  public void redispatch(Connection conn) {
    dispatch(conn, false, false);
  }

  public void dispatch(Connection conn) {
    dispatch(conn, true, false);
  }

  public void dispatchStop(Connection conn) {
    dispatch(conn, false, true);
  }

  private void dispatch(Connection conn, boolean ischeckstat, boolean issendStop) {
    if (getClient() == null)
      return;
    if (getChannels() == null || getChannels().size() == 0)
      return;
    SentIACMDRequest.Builder builer = SentIACMDRequest.newBuilder();
    SentIACMDRequest.Builder builer2 = SentIACMDRequest.newBuilder();
    //start list
    List<ResInfo> resInfos = new ArrayList<>();
    //stop list
    List<ResInfo> resInfos2 = new ArrayList<>();
    Iterator it = getChannels().entrySet().iterator();
    while (it.hasNext()) {
      Map.Entry entry = (Map.Entry) it.next();
      Object key = entry.getKey();
      Object val = entry.getValue();
      IaagChannelInfo channal = (IaagChannelInfo) val;
      if (ischeckstat) {
        if (channal.isNeedSendcmd() && !channal.isHasSendCmd()) {
          ResInfo a = channal.convert2ResInfo(conn);
          if (channal.getCmdType() == IACMDType.Start) {
            resInfos.add(a);
          } else if (channal.getCmdType() == IACMDType.Stop) {
            resInfos2.add(a);
          }
        }
      } else if (issendStop) { //stop all channel
        ResInfo a = channal.convert2ResInfo(conn);
        resInfos2.add(a);
      } else {
        ResInfo a = channal.convert2ResInfo(conn);
        if (channal.getCmdType() == IACMDType.Start) {
          resInfos.add(a);
        } else if (channal.getCmdType() == IACMDType.Stop) {
          resInfos2.add(a);
        }
      }
    }
    if (resInfos.size() > 0) {
      SentIACMDRequest req = builer.setCmdId(0).setCmdType(IACMDType.Start).addAllResInfoList(resInfos).build();
      getClient().sentIACMD(req);
    }
    if (resInfos2.size() > 0) {
      SentIACMDRequest req2 = builer2.setCmdId(1).setCmdType(IACMDType.Stop).addAllResInfoList(resInfos2).build();
      getClient().sentIACMD(req2);
    }
    sethasSendCmd();
  }

  public VsIAClient getClient() {
    return client;
  }

  public void setClient(VsIAClient client) {
    this.client = client;
  }

  public IaagMapItem() {
  }

  private IaagMapItem(Builder builder) {
    setIaagInfo(builder.iaagInfo);
    setChannels(builder.channels);
    setIaus(builder.iaus);
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public IaagInfo getIaagInfo() {
    return iaagInfo;
  }

  public void setIaagInfo(IaagInfo iaagInfo) {
    this.iaagInfo = iaagInfo;
  }

  public HashMap<Integer, IaagChannelInfo> getChannels() {
    return channels;
  }

  public void setChannels(HashMap<Integer, IaagChannelInfo> channels) {
    this.channels = channels;
  }

  public HashMap<Integer, IauItem> getIaus() {
    return iaus;
  }

  public void setIaus(HashMap<Integer, IauItem> iaus) {
    this.iaus = iaus;
  }


  public static final class Builder {
    private IaagInfo iaagInfo;
    private HashMap<Integer, IaagChannelInfo> channels;
    private HashMap<Integer, IauItem> iaus;

    private Builder() {
    }

    public Builder iaagInfo(IaagInfo val) {
      iaagInfo = val;
      return this;
    }

    public Builder channels(HashMap<Integer, IaagChannelInfo> val) {
      channels = val;
      return this;
    }

    public Builder iaus(HashMap<Integer, IauItem> val) {
      iaus = val;
      return this;
    }

    public IaagMapItem build() {
      return new IaagMapItem(this);
    }
  }

  public IaagChannelInfo findChannelInfo(int channel_id) {
    return (getChannels() == null) ? null : getChannels().get(channel_id);
  }

  public IauItem findIauItem(int dev_id) {
    return (getIaus() == null) ? null : getIaus().get(dev_id);
  }

  public void shutClient() {
    if (getClient() != null) {
      getClient().shut();
      setClient(null);
    }
  }

  public VsIAClient createClient() {
    //VsIAClient vsIAClient = new VsIAClient(getIaagInfo().getSvr_name(),getIaagInfo().getIp_intranet(),getIaagInfo().getPort_intranet());
    VsIAClient vsIAClient = new VsIAClient(getIaagInfo().getSvr_name(), getIaagInfo().getIp_intranet(), getIaagInfo().getPort_intranet() + 2);
    setClient(vsIAClient);
    return vsIAClient;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof IaagMapItem)) return false;

    IaagMapItem that = (IaagMapItem) o;

    if (iaagInfo != null ? !iaagInfo.equals(that.iaagInfo) : that.iaagInfo != null) return false;
    if (channels != null ? !channels.equals(that.channels) : that.channels != null) return false;
    if (iaus != null ? !iaus.equals(that.iaus) : that.iaus != null) return false;
    return client != null ? client.equals(that.client) : that.client == null;
  }

  @Override
  public int hashCode() {
    int result = iaagInfo != null ? iaagInfo.hashCode() : 0;
    result = 31 * result + (channels != null ? channels.hashCode() : 0);
    result = 31 * result + (iaus != null ? iaus.hashCode() : 0);
    result = 31 * result + (client != null ? client.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "IaagMapItem{" +
            "iaagInfo=" + iaagInfo +
            ", channels=" + channels +
            ", iaus=" + iaus +
            ", client=" + client +
            '}';
  }

  public void zero() {
    this.iaagInfo.zero();
    Iterator it = getChannels().entrySet().iterator();
    while (it.hasNext()) {
      Map.Entry entry = (Map.Entry) it.next();
      Object val = entry.getValue();
      IaagChannelInfo channelInfo = (IaagChannelInfo) val;
      channelInfo.zero();
      getChannels().remove(channelInfo);
    }
    it = getIaus().entrySet().iterator();
    while (it.hasNext()) {
      Map.Entry entry = (Map.Entry) it.next();
      Object val = entry.getValue();
      IauItem iau = (IauItem) val;
      iau.zero();
      getIaus().remove(iau);
    }
    this.client.zero();
  }
}
