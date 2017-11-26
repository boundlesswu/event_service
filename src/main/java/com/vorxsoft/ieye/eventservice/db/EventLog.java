package com.vorxsoft.ieye.eventservice.db;

import java.sql.Timestamp;

public class EventLog {
  private int nEventLogId = 0;
  private String sEventGenus="";
  private String sEventType = "";
  private String sEventName = "";
  private String sEventDesc = "";
  private int nEventlevel = 0;
  private Timestamp tHappenTime;

  private EventLog(Builder builder) {
    setnEventLogId(builder.nEventLogId);
    setsEventGenus(builder.sEventGenus);
    setsEventType(builder.sEventType);
    setsEventName(builder.sEventName);
    setsEventDesc(builder.sEventDesc);
    setnEventlevel(builder.nEventlevel);
    settHappenTime(builder.tHappenTime);
    settEndTime(builder.tEndTime);
    setsExtraDesc(builder.sExtraDesc);
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public int getnEventLogId() {
    return nEventLogId;
  }

  public void setnEventLogId(int nEventLogId) {
    this.nEventLogId = nEventLogId;
  }

  public String getsEventGenus() {
    return sEventGenus;
  }

  public void setsEventGenus(String sEventGenus) {
    this.sEventGenus = sEventGenus;
  }

  public String getsEventType() {
    return sEventType;
  }

  public void setsEventType(String sEventType) {
    this.sEventType = sEventType;
  }

  public String getsEventName() {
    return sEventName;
  }

  public void setsEventName(String sEventName) {
    this.sEventName = sEventName;
  }

  public String getsEventDesc() {
    return sEventDesc;
  }

  public void setsEventDesc(String sEventDesc) {
    this.sEventDesc = sEventDesc;
  }

  public int getnEventlevel() {
    return nEventlevel;
  }

  public void setnEventlevel(int nEventlevel) {
    this.nEventlevel = nEventlevel;
  }

  public Timestamp gettHappenTime() {
    return tHappenTime;
  }

  public void settHappenTime(Timestamp tHappenTime) {
    this.tHappenTime = tHappenTime;
  }

  public Timestamp gettEndTime() {
    return tEndTime;
  }

  public void settEndTime(Timestamp tEndTime) {
    this.tEndTime = tEndTime;
  }

  public String getsExtraDesc() {
    return sExtraDesc;
  }

  public void setsExtraDesc(String sExtraDesc) {
    this.sExtraDesc = sExtraDesc;
  }

  private Timestamp tEndTime;
  private String sExtraDesc ="";

  public EventLog() {
  }

  public static final class Builder {
    private int nEventLogId;
    private String sEventGenus;
    private String sEventType;
    private String sEventName;
    private String sEventDesc;
    private int nEventlevel;
    private Timestamp tHappenTime;
    private Timestamp tEndTime;
    private String sExtraDesc;

    private Builder() {
    }

    public Builder nEventLogId(int val) {
      nEventLogId = val;
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

    public Builder sEventName(String val) {
      sEventName = val;
      return this;
    }

    public Builder sEventDesc(String val) {
      sEventDesc = val;
      return this;
    }

    public Builder nEventlevel(int val) {
      nEventlevel = val;
      return this;
    }

    public Builder tHappenTime(Timestamp val) {
      tHappenTime = val;
      return this;
    }

    public Builder tEndTime(Timestamp val) {
      tEndTime = val;
      return this;
    }

    public Builder sExtraDesc(String val) {
      sExtraDesc = val;
      return this;
    }

    public EventLog build() {
      return new EventLog(this);
    }
  }
}
