package com.vorxsoft.ieye.eventservice.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class IaagMap {
  private HashMap<Integer,IaagMapItem> iaags;
  private Connection conn;

  public IaagMap() {
  }

  public HashMap<Integer, IaagMapItem> getIaags() {
    return iaags;
  }

  public void setIaags(HashMap<Integer, IaagMapItem> iaags) {
    this.iaags = iaags;
  }

  public Connection getConn() {
    return conn;
  }

  public void setConn(Connection conn) {
    this.conn = conn;
  }

  public void load() throws SQLException {
    String sql = "SELECT a.svr_id,a.svr_no ,a.svr_name,a.machine_id,a,remark,b.ip_intranet,b.ip_extranet,b.port_intranet,b.port_extranet" +
            "  ti_server a INNER JOIN ti_server_main_ex b ON a.svr_id = b.svr_id " +
            " WHERE a.svr_type = 'server_iaag'AND a.enable_state = 1";
    PreparedStatement pstmt = conn.prepareStatement(sql);
    ResultSet ret = pstmt.executeQuery();
    IaagMapItem iaagMapItem = null;
    IaagInfo iaagInfo = null;
    while (ret.next()){
      if(iaagInfo == null){
        iaagInfo = IaagInfo.newBuilder().svr_id()
      }
      if(iaagMapItem == null)
        iaagMapItem = IaagMapItem.newBuilder().build();
    }
  }
}
