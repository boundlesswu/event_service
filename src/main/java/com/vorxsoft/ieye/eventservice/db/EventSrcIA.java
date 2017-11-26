package com.vorxsoft.ieye.eventservice.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EventSrcIA extends EventSrc {
  private int nSvrID = 0;
  private String sSvrName = "";
  private int nResID = 0;
  private String sResName = "";

  public String getsResName() {
    return sResName;
  }

  public void setsResName(String sResName) {
    this.sResName = sResName;
  }

  private EventSrcIA(Builder builder) {
    setnSvrID(builder.nSvrID);
    setsSvrName(builder.sSvrName);
    setnResID(builder.nResID);
    setsResName(builder.sResName);
    setnResName(builder.nResName);
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public int getnSvrID() {
    return nSvrID;
  }

  public void setnSvrID(int nSvrID) {
    this.nSvrID = nSvrID;
  }

  public String getsSvrName() {
    return sSvrName;
  }

  public void setsSvrName(String sSvrName) {
    this.sSvrName = sSvrName;
  }

  public int getnResID() {
    return nResID;
  }

  public void setnResID(int nResID) {
    this.nResID = nResID;
  }

  public String getnResName() {
    return nResName;
  }

  public void setnResName(String nResName) {
    this.nResName = nResName;
  }

  public EventSrcIA() {
  }

  private String nResName = "";

  @Override
  boolean insert2db(Connection conn) throws SQLException {
    boolean ret = false;
    String sql = "INSERT INTO tl_event_src_ia(event_log_id,event_type,svr_id," +
        "svr_name,res_id,res_name,happen_time) VALUES (?,?,?,?,?,?,?)";
    PreparedStatement pstmt = conn.prepareStatement(sql);
    pstmt.setInt(1, getnEventLogId());
    pstmt.setString(2, getsEventType());
    pstmt.setInt(3, getnSvrID());
    pstmt.setString(4, getsSvrName());
    pstmt.setInt(5, getnResID());
    pstmt.setString(6, getsResName());
    pstmt.setTimestamp(7, gettHappenTime());

    if (pstmt.executeUpdate() > 0) {
      ret = true;
      ResultSet rs = pstmt.getGeneratedKeys();
      setnEsmLogId(rs.getInt(1));
      rs.close();
    }
    pstmt.close();
    return ret;
  }

  public static final class Builder {
    private int nSvrID;
    private String sSvrName;
    private int nResID;
    private String sResName;
    private String nResName;

    private Builder() {
    }

    public Builder nSvrID(int val) {
      nSvrID = val;
      return this;
    }

    public Builder sSvrName(String val) {
      sSvrName = val;
      return this;
    }

    public Builder nResID(int val) {
      nResID = val;
      return this;
    }

    public Builder sResName(String val) {
      sResName = val;
      return this;
    }

    public Builder nResName(String val) {
      nResName = val;
      return this;
    }

    public EventSrcIA build() {
      return new EventSrcIA(this);
    }
  }
}
