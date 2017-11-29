package com.vorxsoft.ieye.eventservice.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EventSrcDev extends EventSrc{
  private int nDevID=0;
  private String sDevName;

  private EventSrcDev(Builder builder) {
    setnDevID(builder.nDevID);
    setsDevName(builder.sDevName);
  }

  public EventSrcDev() {
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public int getnDevID() {
    return nDevID;
  }

  public void setnDevID(int nDevID) {
    this.nDevID = nDevID;
  }

  public String getsDevName() {
    return sDevName;
  }

  public void setsDevName(String sDevName) {
    this.sDevName = sDevName;
  }

  @Override
  public boolean insert2db(Connection conn) throws SQLException {
    boolean ret = false;
    String sql = "INSERT INTO tl_event_src_dev(event_log_id,event_type,dev_id,dev_name,happen_time) VALUES (?,?,?,?,?)";
    PreparedStatement pstmt = conn.prepareStatement(sql);
    pstmt.setInt(1, getnEventLogId());
    pstmt.setString(2, getsEventType());
    pstmt.setInt(3, getnDevID());
    pstmt.setString(4, getsDevName());
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

  public static final class Builder {
    private int nDevID;
    private String sDevName;

    private Builder() {
    }

    public Builder nDevID(int val) {
      nDevID = val;
      return this;
    }

    public Builder sDevName(String val) {
      sDevName = val;
      return this;
    }

    public EventSrcDev build() {
      return new EventSrcDev(this);
    }
  }
}
