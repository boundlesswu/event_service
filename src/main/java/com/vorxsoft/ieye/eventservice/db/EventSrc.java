package com.vorxsoft.ieye.eventservice.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;

public abstract class EventSrc {
  private int nEsmLogId=0;
  private int nEventLogId=0;
  private String sEventType;
  private Timestamp tHappenTime;

  public Timestamp gettHappenTime() {
    return tHappenTime;
  }

  public void settHappenTime(Timestamp tHappenTime) {
    this.tHappenTime = tHappenTime;
  }



  abstract public boolean insert2db(Connection conn) throws SQLException;

  public EventSrc() {
  }

  public int getnEsmLogId() {
    return nEsmLogId;
  }

  public void setnEsmLogId(int nEsmLogId) {
    this.nEsmLogId = nEsmLogId;
  }

  public int getnEventLogId() {
    return nEventLogId;
  }

  public void setnEventLogId(int nEventLogId) {
    this.nEventLogId = nEventLogId;
  }

  public String getsEventType() {
    return sEventType;
  }

  public void setsEventType(String sEventType) {
    this.sEventType = sEventType;
  }

  @Override
  public String toString() {
    return "EventSrc{" +
            "nEsmLogId=" + nEsmLogId +
            ", nEventLogId=" + nEventLogId +
            ", sEventType='" + sEventType + '\'' +
            ", tHappenTime=" + tHappenTime +
            '}';
  }
}
