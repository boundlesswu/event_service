package com.vorxsoft.ieye.eventservice.util;

import java.util.HashMap;

public class IaagMapItem {
  public IaagInfo iaagInfo;
  public HashMap<Integer,IaagChannelInfo> channels;
  public HashMap<Integer,IauItem> iaus;
  public IaagMapItem() {
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
}
