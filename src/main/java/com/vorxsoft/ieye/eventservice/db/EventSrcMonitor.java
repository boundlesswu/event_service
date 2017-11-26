package com.vorxsoft.ieye.eventservice.db;

import java.sql.*;

public class EventSrcMonitor extends EventSrc {
  private int nResID;
  private String sResName;

  private EventSrcMonitor(Builder builder) {
    setnResID(builder.nResID);
    setsResName(builder.sResName);
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  @Override
  public boolean insert2db(Connection conn) throws SQLException {
    boolean ret = false;
    String sql = "INSERT INTO tl_event_src_monitor(event_log_id,event_type,res_id,res_name,happen_time) VALUES (?,?,?,?,?)";
    PreparedStatement pstmt = conn.prepareStatement(sql);
    pstmt.setInt(1, getnEventLogId());
    pstmt.setString(2, getsEventType());
    pstmt.setInt(3, getnResID());
    pstmt.setString(4, getsResName());
    pstmt.setTimestamp(5, gettHappenTime());

    if (pstmt.executeUpdate() > 0) {
      ret = true;
      ResultSet rs = pstmt.getGeneratedKeys();
      setnEsmLogId(rs.getInt(1));
      rs.close();
    }
    pstmt.close();
    return ret;
  }

  public EventSrcMonitor() {
  }

  public int getnResID() {
    return nResID;
  }

  public void setnResID(int nResID) {
    this.nResID = nResID;
  }

  public String getsResName() {
    return sResName;
  }

  public void setsResName(String sResName) {
    this.sResName = sResName;
  }

  public static final class Builder {
    private int nResID;
    private String sResName;

    private Builder() {
    }

    public Builder nResID(int val) {
      nResID = val;
      return this;
    }

    public Builder sResName(String val) {
      sResName = val;
      return this;
    }

    public EventSrcMonitor build() {
      return new EventSrcMonitor(this);
    }
  }
}
