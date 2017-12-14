package com.vorxsoft.ieye.eventservice.util;

import com.vorxsoft.ieye.eventservice.grpc.VsIAClient;

public class IaagInfo {
  private int svr_id;
  private String svr_no;
  private String svr_name;
  private int machine_id;
  private String ip_extranet;
  private String ip_intranet;
  private int port_extranet;
  private int port_intranet;
  private String remark;
  private String preset_no;

  public IaagInfo() {
  }

  private IaagInfo(Builder builder) {
    setSvr_id(builder.svr_id);
    setSvr_no(builder.svr_no);
    setSvr_name(builder.svr_name);
    setMachine_id(builder.machine_id);
    setIp_extranet(builder.ip_extranet);
    setIp_intranet(builder.ip_intranet);
    setPort_extranet(builder.port_extranet);
    setPort_intranet(builder.port_intranet);
    setRemark(builder.remark);
    setPreset_no(builder.preset_no);
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public int getSvr_id() {
    return svr_id;
  }

  public void setSvr_id(int svr_id) {
    this.svr_id = svr_id;
  }

  public String getSvr_no() {
    return svr_no;
  }

  public void setSvr_no(String svr_no) {
    this.svr_no = svr_no;
  }

  public String getSvr_name() {
    return svr_name;
  }

  public void setSvr_name(String svr_name) {
    this.svr_name = svr_name;
  }

  public int getMachine_id() {
    return machine_id;
  }

  public void setMachine_id(int machine_id) {
    this.machine_id = machine_id;
  }

  public String getIp_extranet() {
    return ip_extranet;
  }

  public void setIp_extranet(String ip_extranet) {
    this.ip_extranet = ip_extranet;
  }

  public String getIp_intranet() {
    return ip_intranet;
  }

  public void setIp_intranet(String ip_intranet) {
    this.ip_intranet = ip_intranet;
  }

  public int getPort_extranet() {
    return port_extranet;
  }

  public void setPort_extranet(int port_extranet) {
    this.port_extranet = port_extranet;
  }

  public int getPort_intranet() {
    return port_intranet;
  }

  public void setPort_intranet(int port_intranet) {
    this.port_intranet = port_intranet;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public String getPreset_no() {
    return preset_no;
  }

  public void setPreset_no(String preset_no) {
    this.preset_no = preset_no;
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    return super.equals(obj);
  }

  @Override
  protected Object clone() throws CloneNotSupportedException {
    return super.clone();
  }

  @Override
  public String toString() {
    return super.toString();
  }

  @Override
  protected void finalize() throws Throwable {
    super.finalize();
  }

  public static final class Builder {
    private int svr_id;
    private String svr_no;
    private String svr_name;
    private int machine_id;
    private String ip_extranet;
    private String ip_intranet;
    private int port_extranet;
    private int port_intranet;
    private String remark;
    private String preset_no;

    private Builder() {
    }

    public Builder svr_id(int val) {
      svr_id = val;
      return this;
    }

    public Builder svr_no(String val) {
      svr_no = val;
      return this;
    }

    public Builder svr_name(String val) {
      svr_name = val;
      return this;
    }

    public Builder machine_id(int val) {
      machine_id = val;
      return this;
    }

    public Builder ip_extranet(String val) {
      ip_extranet = val;
      return this;
    }

    public Builder ip_intranet(String val) {
      ip_intranet = val;
      return this;
    }

    public Builder port_extranet(int val) {
      port_extranet = val;
      return this;
    }

    public Builder port_intranet(int val) {
      port_intranet = val;
      return this;
    }

    public Builder remark(String val) {
      remark = val;
      return this;
    }

    public Builder preset_no(String val) {
      preset_no = val;
      return this;
    }

    public IaagInfo build() {
      return new IaagInfo(this);
    }
  }

  //  public static IaagInfo getiaagbyResno(String resno, ConnectionPool connPool) {
//    Connection conn = null;
//    PreparedStatement st = null;
//    ResultSet rs = null;
//    IaagInfo iaagInfo = new IaagInfo();
//    String cmd = "select a.svr_id,a.preset_no,c.ip_intranet,c.port_manage  from tr_iaag_cam a LEFT JOIN ti_resource b on a.res_id =b.res_id LEFT JOIN ti_server_media_ex c ON a.svr_id =c.svr_id WHERE b.res_no =?";
//
//    try {
//      conn = connPool.getConnection();
//      st = conn.prepareStatement(cmd);
//      st.setString(1, resno);
//      rs = st.executeQuery();
//      if (rs.next()) {
//        iaagInfo.setSvr_id(rs.getInt(1));
//        iaagInfo.setPreset_no(rs.getString(2));
//        iaagInfo.setIp_intranet(rs.getString(3));
//        iaagInfo.setPort_manage(rs.getInt(4));
//      }
//    } catch (SQLException e) {
//      // e.printStackTrace();
//      logger.error("DBPlatformServer error!", e);
//    } finally {
//      connPool.returnConnection(conn);
//      try {
//        if (st != null) {
//          st.close();
//          st = null;
//        }
//        if (rs != null) {
//          rs.close();
//          rs = null;
//        }
//      } catch (SQLException ignore) {
//      }
//    }
//    return iaagInfo;
//  }
}
