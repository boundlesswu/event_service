package com.vorxsoft.ieye.eventservice.redis;

import java.sql.*;

public class TrafficFlowrateLog {

  public TrafficFlowrateLog(int eventLogId, int eventId,
                            String direction, String vehicleType,
                            Timestamp datetime, int flowrate) {
    this.eventLogId = eventLogId;
    this.eventId = eventId;
    this.direction = string2TrafficDirection(direction);
    this.vehicleType = string2VehicleType(vehicleType);
    this.datetime = datetime;
    this.flowrate = flowrate;
  }

  private TrafficFlowrateLog(Builder builder) {
    setEventLogId(builder.eventLogId);
    setEventId(builder.eventId);
    setDirection(builder.direction);
    setVehicleType(builder.vehicleType);
    setDatetime(builder.datetime);
    setFlowrate(builder.flowrate);
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public enum TrafficDirection {
    unknon("UNKNOWM",0),up("UP", 1), down("DOWN", 2);
    private String name;
    private int index;

    private TrafficDirection(String name, int index) {
      this.name = name;
      this.index = index;
    }

    public int getIndex() {
      return index;
    }
  }

 public TrafficDirection string2TrafficDirection(String s){
    if(s.equals("up")) return TrafficDirection.up;
    if(s.equals("down")) return TrafficDirection.down;
    else  return TrafficDirection.unknon;
 }

 public VehicleType string2VehicleType(String s){
   if(s.equals("all"))  return VehicleType.all;
   if(s.equals("pedestrian"))  return VehicleType.pedestrian;
   if(s.equals("twoWheel"))  return VehicleType.twoWheel;
   if(s.equals("threeWheel"))  return VehicleType.threeWheel;
   if(s.equals("miniCar"))  return VehicleType.miniCar;
   if(s.equals("largeBus"))  return VehicleType.largeBus;
   if(s.equals("truck"))  return VehicleType.truck;
   if(s.equals("dangerousChemicalsCar"))  return VehicleType.dangerousChemicalsCar;
   return VehicleType.other;
 }

  public enum VehicleType {
    all("ALL", 0),
    pedestrian("PEDESTRIAN", 1),
    twoWheel("TWOWHEEL", 2),
    threeWheel("THREEWHEEL", 3),
    car("CAR", 4),
    miniCar("MINICAR", 5),
    largeBus("LARGEBUS", 6),
    truck("TRUCK", 7),
    dangerousChemicalsCar("Dangerous chemicals car", 8),
    other("OTHER",10000);
    private String name;
    private int index;

    private VehicleType(String name, int index) {
      this.name = name;
      this.index = index;
    }

    public int getIndex() {
      return index;
    }
  }

  private int eventLogId;
  private int eventId;
  private TrafficDirection direction;
  private VehicleType vehicleType;
  private Timestamp datetime;
  private int flowrate;

  public int getEventLogId() {
    return eventLogId;
  }

  public void setEventLogId(int eventLogId) {
    this.eventLogId = eventLogId;
  }

  public int getEventId() {
    return eventId;
  }

  public void setEventId(int eventId) {
    this.eventId = eventId;
  }

  public TrafficDirection getDirection() {
    return direction;
  }

  public void setDirection(TrafficDirection direction) {
    this.direction = direction;
  }

  public VehicleType getVehicleType() {
    return vehicleType;
  }

  public void setVehicleType(VehicleType vehicleType) {
    this.vehicleType = vehicleType;
  }

  public Timestamp getDatetime() {
    return datetime;
  }

  public void setDatetime(Timestamp datetime) {
    this.datetime = datetime;
  }

  public int getFlowrate() {
    return flowrate;
  }

  public void setFlowrate(int flowrate) {
    this.flowrate = flowrate;
  }

  public static final class Builder {
    private int eventLogId;
    private int eventId;
    private TrafficDirection direction;
    private VehicleType vehicleType;
    private Timestamp datetime;
    private int flowrate;

    private Builder() {
    }

    public Builder eventLogId(int val) {
      eventLogId = val;
      return this;
    }

    public Builder eventId(int val) {
      eventId = val;
      return this;
    }

    public Builder direction(TrafficDirection val) {
      direction = val;
      return this;
    }

    public Builder vehicleType(VehicleType val) {
      vehicleType = val;
      return this;
    }

    public Builder datetime(Timestamp val) {
      datetime = val;
      return this;
    }

    public Builder flowrate(int val) {
      flowrate = val;
      return this;
    }

    public TrafficFlowrateLog build() {
      return new TrafficFlowrateLog(this);
    }
  }

  public void insert2db(Connection conn) throws SQLException {
    int ret = 0;
    String sql = "INSERT INTO tl_traffic_flowrate(event_log_id,event_id,direction,vehicle_type," +
            "happen_time,flowrate) VALUES (?,?,?,?,?,?)";
    PreparedStatement pstmt = conn.prepareStatement(sql);
    pstmt.setInt(1, getEventLogId());
    pstmt.setInt(1, getEventId());
    pstmt.setInt(1, getDirection().getIndex());
    pstmt.setInt(1, getVehicleType().getIndex());
    pstmt.setTimestamp(6, getDatetime());
    pstmt.setInt(1, getFlowrate());
    pstmt.executeUpdate();
  }
}
