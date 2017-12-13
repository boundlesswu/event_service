package com.vorxsoft.ieye.eventservice.util;

import com.vorxsoft.ieye.eventservice.config.EventInfo;
import com.vorxsoft.ieye.eventservice.config.IaConfigKey;
import com.vorxsoft.ieye.eventservice.redis.EventRecord;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

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
        " FROM ti_server a INNER JOIN ti_server_main_ex b ON a.svr_id = b.svr_id " +
        " WHERE a.svr_type = 'server_iaag'AND a.enable_state = 1";
    PreparedStatement pstmt = conn.prepareStatement(sql);
    ResultSet ret = pstmt.executeQuery();
    IaagMapItem iaagMapItem = null;
    IaagInfo iaagInfo = null;
    while (ret.next()) {
      int svr_id=ret.getInt(1);
      iaagInfo = IaagInfo.newBuilder().
          svr_id(svr_id).
          svr_no(ret.getString(2)).
          svr_name(ret.getString(3)).
          machine_id(ret.getInt(4)).
          remark(ret.getString(5)).
          ip_intranet(ret.getString(6)).
          ip_extranet(ret.getString(7)).
          port_intranet(ret.getInt(8)).
          port_extranet(ret.getInt(9)).
          build();
      HashMap<Integer, IauItem> iaus = createIauHashMapFromDB(svr_id);
      HashMap<Integer, IaagChannelInfo> channels = createIaagChannelFromDB(svr_id);
      iaagMapItem = IaagMapItem.newBuilder().iaagInfo(iaagInfo).iaus(iaus).channels(channels).build();
      if(getIaags() == null)
        iaags = new HashMap<>();
      iaags.put(svr_id,iaagMapItem);
    }
    ret.close();
    pstmt.close();
  }
   public HashMap<Integer,IaagChannelInfo> createIaagChannelFromDB(int svr_id) throws SQLException {
     String sql = "SELECT iaag_chn_id,res_id,preset_no FROM tr_iaag_cam WHERE svr_id = ?";
     PreparedStatement pstmt = conn.prepareStatement(sql);
     pstmt.setInt(1,svr_id);
     ResultSet ret = pstmt.executeQuery();
     IaagChannelInfo chanel = null;
     HashMap<Integer,IaagChannelInfo> channels = new HashMap<>();
     List<Integer> ids = new ArrayList<>();
     int iaag_chn_id = 0;
     int res_id = 0;
     while (ret.next()){
       iaag_chn_id = ret.getInt(1);
       res_id = ret.getInt(2);
       chanel = IaagChannelInfo.newBuilder().
                iaag_chn_id(iaag_chn_id).
                res_id(res_id).
                preset_no(ret.getString(3)).
                svr_id(svr_id).
                build();
       String sql2 = "SELECT a.event_id FROM ti_event a INNER JOIN ti_event_ia_ex b on a.event_id = b.event_id " +
               "WHERE b.svr_id = ? AND b.iaag_chn_id =? AND B.res_id=?";
       PreparedStatement pstmt2 = conn.prepareStatement(sql2);
       pstmt2.setInt(1,svr_id);
       pstmt2.setInt(2,iaag_chn_id);
       pstmt2.setInt(3,res_id);
       ResultSet ret2 = pstmt2.executeQuery();
       while (ret2.next()){
         ids.add(ret2.getInt(1));
       }
       chanel.setEvent_ids(ids);
       channels.put(svr_id,chanel);
       ret2.close();
       pstmt2.close();
     }
     ret.close();
     pstmt.close();
     return channels;
   }
    public HashMap<Integer,IauItem> createIauHashMapFromDB(int svr_id) throws SQLException {
        String sql = "SELECT a.dev_id,a.dev_no ,a.dev_name,a.protocol_type,a,dev_sn,a.group_id,a.remark," +
          "b.chn_video,b.ip,b.port,b.username,b.password" +
          " FROM ti_device a INNER JOIN ti_device_iau_ex b ON a.svr_id = b.svr_id " +
          " WHERE a.dev_type = 'device_iau' AND a.svr_id = ?";
      PreparedStatement pstmt = conn.prepareStatement(sql);
      pstmt.setInt(1,svr_id);
      ResultSet ret = pstmt.executeQuery();

      IauItem iau=null;
      HashMap<Integer,IauItem> iaus = new HashMap<>();
      while(ret.next()){
        iau = IauItem.newBuilder().
            dev_id(ret.getInt(1)).
            dev_no(ret.getString(2)).
            svr_id(svr_id).
            dev_name(ret.getString(3)).
            dev_type("device_iau").
            protocol_type(ret.getString(4)).
            dev_sn(ret.getString(5)).
            group_id(ret.getInt(6)).
            remark(ret.getString(7)).
            chn_video(ret.getInt(8)).
            ip(ret.getString(9)).
            port(ret.getInt(10)).
            username(ret.getString(11)).
            password(ret.getString(12)).
            build();
        iaus.put(ret.getInt(1),iau);
      }
      ret.close();
      pstmt.close();
      return iaus;
    }

  public IaagMapItem findIaagMapItem(int svr_id){
    return ((getIaags() == null)?null:getIaags().get(svr_id));
  }

  public IaagMapItem findIaagMapItem(String ip,int port){
    if(getIaags() == null){
      return null;
    }
    Iterator iter = getIaags().entrySet().iterator();
    while (iter.hasNext()) {
      Map.Entry entry = (Map.Entry) iter.next();
      Object key = entry.getKey();
      Object val = entry.getValue();
      IaagMapItem iaag = (IaagMapItem) val;
      if(iaag.getIaagInfo().getIp_intranet().equals(ip) && iaag.getIaagInfo().getPort_intranet() == port)
        return iaag;
    }
    return null;
  }

}
