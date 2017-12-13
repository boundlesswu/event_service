package com.vorxsoft.ieye.eventservice.util;

import java.util.HashMap;

public class IaagMapItem {
  public IaagInfo iaagInfo;
  public HashMap<Integer,IaagChannelInfo> channels;
  public HashMap<Integer,IauItem> iaus;

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
  protected void finalize() throws Throwable {
    super.finalize();
  }

  @Override
  public String toString() {
    return "IaagMapItem{" +
            "iaagInfo=" + iaagInfo +
            ", channels=" + channels +
            ", iaus=" + iaus +
            '}';
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
  public IaagChannelInfo findChannelInfo(int channel_id){
    return (getChannels()==null)?null:getChannels().get(channel_id);
  }

  public IauItem findIauItem(int dev_id){
    return (getIaus() == null)?null:getIaus().get(dev_id);
  }
}
