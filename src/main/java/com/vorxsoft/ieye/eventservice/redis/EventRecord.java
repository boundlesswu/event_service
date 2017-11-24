package com.vorxsoft.ieye.eventservice.redis;

import com.vorxsoft.ieye.eventservice.linkage.EventLinkage;

public class EventRecord {
  public EventRecord() {
    this.sMachineName = "";
    this.sSvrName = "";
    this.nResID = 0;
    this.nDevID = 0;
    this.nMachineID = 0;
    this.sResNo = "";                //资源no
    this.sDevNo = "";                //设备编号
    this.nSvrID = 0;                   //服务id
    this.sEventName = "";            //事件名称
    this.sEventGenus = "";            //事件大类
    this.sEventType = "";            //事件类型
    this.sHappentime = "";           //事件时间
    this.nEventID = 0;              //事件ID（可不处理）
    this.sEventDesc = "";          //事件描述
    this.sDevName = "";              //设备名称（可不赋值）
    this.sResName = "";              //资源名称（可不赋值）
    this.sPicpath = "";              //事件图片
    this.sEventlevel = 0;          //事件级别
    this.nEventlogID = 0;          //事件logID
    this.sExtraDesc = "";         //额外描述
    this.eventLinkage = new EventLinkage();    //联动信息
  }

  private EventRecord(Builder builder) {
    setnResID(builder.nResID);
    setsResNo(builder.sResNo);
    setsResName(builder.sResName);
    setnDevID(builder.nDevID);
    setsDevNo(builder.sDevNo);
    setsDevName(builder.sDevName);
    setnMachineID(builder.nMachineID);
    setsMachineName(builder.sMachineName);
    setnSvrID(builder.nSvrID);
    setsSvrName(builder.sSvrName);
    setnEventID(builder.nEventID);
    setsEventName(builder.sEventName);
    setsEventGenus(builder.sEventGenus);
    setsEventType(builder.sEventType);
    setsHappentime(builder.sHappentime);
    setsEventDesc(builder.sEventDesc);
    setsPicpath(builder.sPicpath);
    setsEventlevel(builder.sEventlevel);
    setnEventlogID(builder.nEventlogID);
    setsExtraDesc(builder.sExtraDesc);
    setEventLinkage(builder.eventLinkage);
    setbSend2mq(builder.bSend2mq);
    setbSend2cms(builder.bSend2cms);
    setbSend2blg(builder.bSend2blg);
    setbInsert2log(builder.bInsert2log);
    setbInsert2srcLog(builder.bInsert2srcLog);
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public String getsResNo() {
    return sResNo;
  }

  public void setsResNo(String sResNo) {
    this.sResNo = sResNo;
  }

  public String getsDevNo() {
    return sDevNo;
  }

  public void setsDevNo(String sDevNo) {
    this.sDevNo = sDevNo;
  }

  public String getsEventName() {
    return sEventName;
  }

  public void setsEventName(String sEventName) {
    this.sEventName = sEventName;
  }

  public String getsEventType() {
    return sEventType;
  }

  public void setsEventType(String sEventType) {
    this.sEventType = sEventType;
  }

  public String getsHappentime() {
    return sHappentime;
  }

  public void setsHappentime(String sHappentime) {
    this.sHappentime = sHappentime;
  }

  public int getnEventID() {
    return nEventID;
  }

  public void setnEventID(int nEventID) {
    this.nEventID = nEventID;
  }

  public String getsEventDesc() {
    return sEventDesc;
  }

  public void setsEventDesc(String sEventDesc) {
    this.sEventDesc = sEventDesc;
  }

  public String getsDevName() {
    return sDevName;
  }

  public void setsDevName(String sDevName) {
    this.sDevName = sDevName;
  }

  public String getsResName() {
    return sResName;
  }

  public void setsResName(String sResName) {
    this.sResName = sResName;
  }

  public String getsPicpath() {
    return sPicpath;
  }

  public void setsPicpath(String sPicpath) {
    this.sPicpath = sPicpath;
  }

  public int getsEventlevel() {
    return sEventlevel;
  }

  public void setsEventlevel(int sEventlevel) {
    this.sEventlevel = sEventlevel;
  }

  public int getnEventlogID() {
    return nEventlogID;
  }

  public void setnEventlogID(int nEventlogID) {
    this.nEventlogID = nEventlogID;
  }

  public String getsExtraDesc() {
    return sExtraDesc;
  }

  public void setsExtraDesc(String sExtraDesc) {
    this.sExtraDesc = sExtraDesc;
  }

  public int getnSvrID() {
    return nSvrID;
  }

  public void setnSvrID(int nSvrID) {
    this.nSvrID = nSvrID;
  }

  public String getsEventGenus() {
    return sEventGenus;
  }

  public void setsEventGenus(String sEventGenus) {
    this.sEventGenus = sEventGenus;
  }

  private int nResID;              //资源id
  private String sResNo;                //资源no
  private String sResName;              //资源名称（可不赋值）

  private int nDevID;                  //设备id
  private String sDevNo;                //设备编号
  private String sDevName;              //设备名称（可不赋值）

  private int nMachineID;               //机器Id
  private String sMachineName;          //机器名称

  private int nSvrID;                   //智能分析接入服务器id
  private String sSvrName;              //智能分析接入服务器名称

  private int nEventID;              //事件ID（可不处理）
  private String sEventName;            //事件名称
  private String sEventGenus;            //事件大类
  private String sEventType;            //事件类型
  private String sHappentime;           //事件时间
  private String sEventDesc;          //事件描述
  private String sPicpath;              //事件图片
  private int sEventlevel;          //事件级别
  private int nEventlogID;          //事件logID
  private String sExtraDesc;         //额外描述
  private EventLinkage eventLinkage;

  boolean bSend2mq = false;
  boolean bSend2cms = false;
  boolean bSend2blg = false;
  boolean bInsert2log = false;
  boolean bInsert2srcLog = false;

  public int getnResID() {
    return nResID;
  }

  public void setnResID(int nResID) {
    this.nResID = nResID;
  }

  public int getnDevID() {
    return nDevID;
  }

  public void setnDevID(int nDevID) {
    this.nDevID = nDevID;
  }

  public int getnMachineID() {
    return nMachineID;
  }

  public void setnMachineID(int nMachineID) {
    this.nMachineID = nMachineID;
  }

  public String getsMachineName() {
    return sMachineName;
  }

  public void setsMachineName(String sMachineName) {
    this.sMachineName = sMachineName;
  }

  public String getsSvrName() {
    return sSvrName;
  }

  public void setsSvrName(String sSvrName) {
    this.sSvrName = sSvrName;
  }

  public EventLinkage getEventLinkage() {
    return eventLinkage;
  }

  public void setEventLinkage(EventLinkage eventLinkage) {
    this.eventLinkage = eventLinkage;
  }

  public boolean isbSend2mq() {
    return bSend2mq;
  }

  public void setbSend2mq(boolean bSend2mq) {
    this.bSend2mq = bSend2mq;
  }

  public boolean isbSend2cms() {
    return bSend2cms;
  }

  public void setbSend2cms(boolean bSend2cms) {
    this.bSend2cms = bSend2cms;
  }

  public boolean isbSend2blg() {
    return bSend2blg;
  }

  public void setbSend2blg(boolean bSend2blg) {
    this.bSend2blg = bSend2blg;
  }

  public boolean isbInsert2log() {
    return bInsert2log;
  }

  public void setbInsert2log(boolean bInsert2log) {
    this.bInsert2log = bInsert2log;
  }

  public boolean isbInsert2srcLog() {
    return bInsert2srcLog;
  }

  public void setbInsert2srcLog(boolean bInsert2srcLog) {
    this.bInsert2srcLog = bInsert2srcLog;
  }

  public static final class Builder {
    private String sResNo;
    private String sDevNo;
    private int nSvrID;
    private String sSvrName;
    private String sEventName;
    private String sEventGenus;
    private String sEventType;
    private String sHappentime;
    private int nEventID;
    private String sEventDesc;
    private String sDevName;
    private int nMachineID;
    private String sMachineName;
    private String sResName;
    private int nDevID;
    private String sPicpath;
    private int sEventlevel;
    private int nEventlogID;
    private String sExtraDesc;
    private EventLinkage eventLinkage;
    private boolean bSend2mq;
    private boolean bSend2cms;
    private boolean bSend2blg;
    private boolean bInsert2log;
    private boolean bInsert2srcLog;
    private int nResID;

    public Builder() {
    }

    public Builder sResNo(String val) {
      sResNo = val;
      return this;
    }

    public Builder sDevNo(String val) {
      sDevNo = val;
      return this;
    }

    public Builder nSvrID(int val) {
      nSvrID = val;
      return this;
    }

    public Builder sSvrName(String val) {
      sSvrName = val;
      return this;
    }

    public Builder sEventName(String val) {
      sEventName = val;
      return this;
    }

    public Builder sEventGenus(String val) {
      sEventGenus = val;
      return this;
    }

    public Builder sEventType(String val) {
      sEventType = val;
      return this;
    }

    public Builder sHappentime(String val) {
      sHappentime = val;
      return this;
    }

    public Builder nEventID(int val) {
      nEventID = val;
      return this;
    }

    public Builder sEventDesc(String val) {
      sEventDesc = val;
      return this;
    }

    public Builder sDevName(String val) {
      sDevName = val;
      return this;
    }

    public Builder nMachineID(int val) {
      nMachineID = val;
      return this;
    }

    public Builder sMachineName(String val) {
      sMachineName = val;
      return this;
    }

    public Builder sResName(String val) {
      sResName = val;
      return this;
    }

    public Builder nDevID(int val) {
      nDevID = val;
      return this;
    }

    public Builder sPicpath(String val) {
      sPicpath = val;
      return this;
    }

    public Builder sEventlevel(int val) {
      sEventlevel = val;
      return this;
    }

    public Builder nEventlogID(int val) {
      nEventlogID = val;
      return this;
    }

    public Builder sExtraDesc(String val) {
      sExtraDesc = val;
      return this;
    }

    public Builder eventLinkage(EventLinkage val) {
      eventLinkage = val;
      return this;
    }

    public Builder bSend2mq(boolean val) {
      bSend2mq = val;
      return this;
    }

    public Builder bSend2cms(boolean val) {
      bSend2cms = val;
      return this;
    }

    public Builder bSend2blg(boolean val) {
      bSend2blg = val;
      return this;
    }

    public Builder bInsert2log(boolean val) {
      bInsert2log = val;
      return this;
    }

    public Builder bInsert2srcLog(boolean val) {
      bInsert2srcLog = val;
      return this;
    }

    public EventRecord build() {
      return new EventRecord(this);
    }

    public Builder nResID(int val) {
      nResID = val;
      return this;
    }
  }
}
