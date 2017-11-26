package com.vorxsoft.ieye.eventservice.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EventSrcMachine extends EventSrc{
  private int nMachineID=0;
  private String sMachineName;

  private EventSrcMachine(Builder builder) {
    setnMachineID(builder.nMachineID);
    setsMachineName(builder.sMachineName);
  }

  public static Builder newBuilder() {
    return new Builder();
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

  @Override
  boolean insert2db(Connection conn) throws SQLException {
    boolean ret = false;
    String sql = "INSERT INTO tl_event_src_machine(event_log_id,event_type,machine_id,machine_name,happen_time) VALUES (?,?,?,?,?)";
    PreparedStatement pstmt = conn.prepareStatement(sql);
    pstmt.setInt(1, getnEventLogId());
    pstmt.setString(2, getsEventType());
    pstmt.setInt(3, getnMachineID());
    pstmt.setString(4, getsMachineName());
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
    private int nMachineID;
    private String sMachineName;

    private Builder() {
    }

    public Builder nMachineID(int val) {
      nMachineID = val;
      return this;
    }

    public Builder sMachineName(String val) {
      sMachineName = val;
      return this;
    }

    public EventSrcMachine build() {
      return new EventSrcMachine(this);
    }
  }
}
