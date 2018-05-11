package com.vorxsoft.ieye.eventservice.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.vorxsoft.ieye.eventservice.config.EventInfo;
import com.vorxsoft.ieye.eventservice.config.IaConfigKey;
import com.vorxsoft.ieye.eventservice.config.TimePeriod;
import com.vorxsoft.ieye.eventservice.config.TimeScheduleItem;
import com.vorxsoft.ieye.eventservice.grpc.VsIAClient;
import com.vorxsoft.ieye.eventservice.redis.EventRecord;
import com.vorxsoft.ieye.proto.IACMDType;
import com.vorxsoft.ieye.proto.Point;
import com.vorxsoft.ieye.proto.Zoom;
import com.vorxsoft.ieye.proto.ZoomOrBuilder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class IaagMap {
  private HashMap<Integer, IaagMapItem> iaags;
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

  public HashMap<String, Integer> createIaagIntervalMap(int svr_id) throws SQLException{
    String sql = "SELECT a.event_type,a.interval from ti_event_ia_interval a where a.svr_id = ?";
    PreparedStatement pstmt = conn.prepareStatement(sql);
    pstmt.setInt(1, svr_id);
    HashMap<String, Integer> hash = null;
    if (hash == null) {
      hash = new HashMap<>();
    }
    try {
      ResultSet ret = pstmt.executeQuery();
      while (ret.next()) {
        String type = ret.getString(1);
        int interval = ret.getInt(2);
        hash.put(type, interval);
      }
      ret.close();
      pstmt.close();
      //return hash;
    } catch (Exception e) {
      System.out.println(e);
    } finally {
      return hash;
    }
  }

  public void load() throws SQLException {
    String sql = "SELECT a.svr_id,a.svr_no ,a.svr_name,a.machine_id,a.remark,b.ip_intranet,b.ip_extranet,b.port_intranet,b.port_extranet" +
            " FROM ti_server a INNER JOIN ti_server_main_ex b ON a.svr_id = b.svr_id " +
            " WHERE a.svr_type = 'server_iaag'AND a.enable_state = 1";
    PreparedStatement pstmt = conn.prepareStatement(sql);
    ResultSet ret = pstmt.executeQuery();
    IaagMapItem iaagMapItem = null;
    IaagInfo iaagInfo = null;
    while (ret.next()) {
      int svr_id = ret.getInt(1);
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
              intervalMap(createIaagIntervalMap(svr_id)).
              isOnLine(false).
              needSendcmd(true).
              hasSendCmd(false).
              build();
      HashMap<Integer, IauItem> iaus = createIauHashMapFromDB(svr_id);
      HashMap<Integer, IaagChannelInfo> channels = createIaagChannelFromDB(svr_id);
      iaagMapItem = IaagMapItem.newBuilder().iaagInfo(iaagInfo).iaus(iaus).channels(channels).build();
      if (getIaags() == null)
        iaags = new HashMap<>();
      iaags.put(svr_id, iaagMapItem);
    }
    ret.close();
    pstmt.close();
  }

  public IACMDType getChannelState(int id) throws SQLException {
    String sql = "SELECT chn_state  FROM tr_iaag_cam WHERE iaag_chn_id = ?";
    PreparedStatement pstmt = conn.prepareStatement(sql);
    pstmt.setInt(1, id);
    ResultSet ret = pstmt.executeQuery();
    IACMDType type = IACMDType.Stop;
    while (ret.next()) {
      type = (ret.getInt(1) == 1) ? IACMDType.Start : IACMDType.Stop;
    }
    ret.close();
    pstmt.close();
    return type;
  }

  public List<Zoom> createIaagChannelZoom(int iaag_channel_id) throws SQLException {
    String sql = "SELECT ia_rule FROM ti_iaag_channel_zone WHERE iaag_chn_id = ?";
    PreparedStatement pstmt = conn.prepareStatement(sql);
    pstmt.setInt(1, iaag_channel_id);
    List<Zoom> zooms = new ArrayList<>();
    try {
      ResultSet ret = pstmt.executeQuery();
      while (ret.next()) {
        String rule = ret.getString(1);
        JSONObject da = JSONObject.parseObject(rule);
        System.out.println(da);
        JSONArray b = da.getJSONArray("ia_rule");
        System.out.println(b);
        //JSONArray ja = JSONObject.parseArray(b.toString());
        JSONObject ez = (JSONObject) b.getJSONObject(0).get("exclusionarea");
        JSONObject rz = (JSONObject) b.getJSONObject(0).get("recognizearea");
        JSONObject jo = null;
        boolean isAnalysis = true;
        if (ez != null) {
          jo = ez;
          isAnalysis = false;
        } else if (rz != null) {
          jo = rz;
          isAnalysis = true;
        }
        if (jo == null) {
          return null;
        }
        List<Point> points = new ArrayList();
        Zoom.Builder build = Zoom.newBuilder().setIsAnalysis(isAnalysis);
        for (int i = 0; i < jo.size(); i++) {
          JSONArray aaa = jo.getJSONArray("");
          for (int j = 0; j < aaa.size(); j++) {
            JSONObject bb = (JSONObject) aaa.toArray()[j];
            float x = Float.parseFloat(bb.get("x").toString());
            float y = Float.parseFloat(bb.get("y").toString());
            Point op = Point.newBuilder().setX(x).setY(y).build();
            points.add(op);
          }
          build.addAllPoints(points);
        }
        zooms.add(build.build());
      }
      return zooms;
    } catch (Exception e) {
      System.out.println(e);
    } finally {
    }
    return zooms;
  }

  public HashMap<Integer, IaagChannelInfo> createIaagChannelFromDB(int svr_id) throws SQLException {
    String sql = "SELECT iaag_chn_id,res_id,preset_no FROM tr_iaag_cam WHERE svr_id = ?";
    PreparedStatement pstmt = conn.prepareStatement(sql);
    pstmt.setInt(1, svr_id);
    ResultSet ret = pstmt.executeQuery();
    IaagChannelInfo chanel = null;
    HashMap<Integer, IaagChannelInfo> channels = new HashMap<>();
    List<Integer> ids = new ArrayList<>();
    int iaag_chn_id = 0;
    int res_id = 0;
    while (ret.next()) {
      iaag_chn_id = ret.getInt(1);
      res_id = ret.getInt(2);
      chanel = IaagChannelInfo.newBuilder().
              iaag_chn_id(iaag_chn_id).
              res_id(res_id).
              preset_no(ret.getString(3)).
              svr_id(svr_id).
              build();
      String sql2 = "SELECT a.event_id ,a.enable_state FROM ti_event a INNER JOIN ti_event_ia_ex b on a.event_id = b.event_id " +
              "WHERE b.svr_id = ? AND b.iaag_chn_id =? AND b.res_id=? ";
      PreparedStatement pstmt2 = conn.prepareStatement(sql2);
      pstmt2.setInt(1, svr_id);
      pstmt2.setInt(2, iaag_chn_id);
      pstmt2.setInt(3, res_id);
      ResultSet ret2 = pstmt2.executeQuery();
      IACMDType iacmdType = IACMDType.Stop;
      while (ret2.next()) {
        ids.add(ret2.getInt(1));
        iacmdType = (ret2.getInt(1) == 0) ? IACMDType.Stop : IACMDType.Start;
      }
      //get channel state
      iacmdType = getChannelState(iaag_chn_id);
      chanel.setCmdType(iacmdType);
      chanel.setEvent_ids(ids);
      chanel.setNeedSendcmd(true);
      chanel.setHasSendCmd(false);
      channels.put(iaag_chn_id, chanel);
      //channels.put(svr_id,chanel);
      ret2.close();
      pstmt2.close();
      List<Zoom> zooms = createIaagChannelZoom(iaag_chn_id);
      chanel.setZooms(zooms);
    }
    ret.close();
    pstmt.close();
    return channels;
  }

  public HashMap<Integer, IauItem> createIauHashMapFromDB(int svr_id) throws SQLException {
    String sql = "SELECT a.dev_id,a.dev_no ,a.dev_name,a.protocol_type,a.dev_sn,a.group_id,a.remark," +
            "b.chn_video,b.ip,b.port,b.username,b.password" +
            " FROM ti_device a INNER JOIN ti_device_iau_ex b ON a.dev_id = b.dev_id " +
            " WHERE a.dev_type = 'device_iau' AND a.svr_id = ?";
    PreparedStatement pstmt = conn.prepareStatement(sql);
    pstmt.setInt(1, svr_id);
    ResultSet ret = pstmt.executeQuery();

    IauItem iau = null;
    HashMap<Integer, IauItem> iaus = new HashMap<>();
    while (ret.next()) {

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
      iaus.put(ret.getInt(1), iau);
    }
    ret.close();
    pstmt.close();
    return iaus;
  }

  public IaagMapItem findIaagMapItem(int svr_id) {
    return ((getIaags() == null) ? null : getIaags().get(svr_id));
  }

  public IaagMapItem findIaagMapItem(String ip, int port) {
    if (getIaags() == null) {
      return null;
    }
    Iterator iter = getIaags().entrySet().iterator();
    while (iter.hasNext()) {
      Map.Entry entry = (Map.Entry) iter.next();
      Object key = entry.getKey();
      Object val = entry.getValue();
      IaagMapItem iaag = (IaagMapItem) val;
      if (iaag.getIaagInfo().getIp_intranet().equals(ip) && iaag.getIaagInfo().getPort_intranet() == port)
        return iaag;
    }
    return null;
  }

  public IaagMapItem findIaagMapItem(String address) {
    String[] akey = address.split(":");
    String ip = akey[0];
    int port = Integer.parseInt(akey[1]);
    return findIaagMapItem(ip, port);
  }

  public VsIAClient IaClinetInit(String address) {
    IaagMapItem iaagMapItem = findIaagMapItem(address);
    if (iaagMapItem == null) return null;
    iaagMapItem.shutClient();
    return iaagMapItem.createClient();
  }

  public void dispatch() {
    Iterator iter = getIaags().entrySet().iterator();
    while (iter.hasNext()) {
      Map.Entry entry = (Map.Entry) iter.next();
      IaagMapItem iaag = (IaagMapItem) entry.getValue();
      iaag.dispatch(getConn());
    }
  }

  public void dispatchStop() {
    Iterator iter = getIaags().entrySet().iterator();
    while (iter.hasNext()) {
      Map.Entry entry = (Map.Entry) iter.next();
      IaagMapItem iaag = (IaagMapItem) entry.getValue();
      iaag.dispatchStop(getConn());
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof IaagMap)) return false;

    IaagMap iaagMap = (IaagMap) o;

    if (iaags != null ? !iaags.equals(iaagMap.iaags) : iaagMap.iaags != null) return false;
    return conn != null ? conn.equals(iaagMap.conn) : iaagMap.conn == null;
  }

  @Override
  public int hashCode() {
    int result = iaags != null ? iaags.hashCode() : 0;
    result = 31 * result + (conn != null ? conn.hashCode() : 0);
    return result;
  }

  public int getIaag_chn_id(int svr_id, int res_id) {
    if (getIaags() == null) return 0;
    IaagMapItem a = getIaags().get(svr_id);
    if (a == null) return 0;
    Iterator iter = a.getChannels().entrySet().iterator();
    while (iter.hasNext()) {
      Map.Entry entry = (Map.Entry) iter.next();
      IaagChannelInfo val = (IaagChannelInfo) entry.getValue();
      if (val.getRes_id() == res_id)
        return val.getIaag_chn_id();
    }
    return 0;
  }

  public void zero() {
    Iterator it = getIaags().entrySet().iterator();
    while (it.hasNext()) {
      Map.Entry entry = (Map.Entry) it.next();
      Object val = entry.getValue();
      IaagMapItem iaag = (IaagMapItem) val;
      getIaags().remove(iaag);
      iaag.zero();
    }
    this.conn = null;
  }
}
