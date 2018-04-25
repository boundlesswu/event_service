package com.vorxsoft.ieye.eventservice.util;

import com.vorxsoft.ieye.proto.VSEventType;

import static com.vorxsoft.ieye.proto.VSEventType.*;

public class Constant {
  public static final String sGenusSio = new String("event_sio");
  public static final String sGenusMonitor = new String("event_monitor");
  public static final String sGenusIa = new String("event_ia");
  public static final String sGenusDeveice = new String("event_device");
  public static final String sGenusServer = new String("event_server");
  public static final String sLinkageClient = new String("linkage_client");
  public static final String sLinkageWall = new String("linkage_wall");
  public static final String sLinkagePreset = new String("linkage_preset");
  public static final String sLinkageCruise = new String("linkage_cruise");
  public static final String sLinkageSio = new String("linkage_sio");
  public static final String sLinkageRecord = new String("linkage_record");
  public static final String sLinkageSms = new String("linkage_sms");
  public static final String sLinkageSnapshot = new String("linkage_snapshot");
  public static final String sLinkageEmail = new String("linkage_email");

  public static String eventType2Genus(String type){
    if (type.equals("event_monitor")) return sGenusMonitor;
    if (type.equals("event_motion_detect")) return sGenusMonitor;
    if (type.equals("event_video_lose")) return sGenusMonitor;
    if (type.equals("event_video_occlusion")) return sGenusMonitor;
    if (type.equals("event_face_snapshot")) return sGenusMonitor;
    if (type.equals("event_face_recognize")) return sGenusMonitor;
    if (type.equals("event_steal_move")) return sGenusMonitor;
    if (type.equals("event_perimeter_alarm")) return sGenusMonitor;

    if(type.equals("event_sio")) return sGenusSio;
    if(type.equals("event_sio_common")) return sGenusSio;
    if(type.equals("event_sio_smoke")) return sGenusSio;
    if(type.equals("event_sio_temperature")) return sGenusSio;
    if(type.equals("event_sio_fire")) return sGenusSio;
    if(type.equals("event_sio_gas")) return sGenusSio;
    if(type.equals("event_sio_infrared")) return sGenusSio;
    if(type.equals("event_sio_vibration")) return sGenusSio;

    if(type.equals("event_ia")) return sGenusIa;
    if(type.equals("event_ia_traffic_stop")) return sGenusIa;
    if(type.equals("event_ia_traffic_human")) return sGenusIa;
    if(type.equals("event_ia_traffic_converse")) return sGenusIa;
    if(type.equals("event_ia_traffic_condition")) return sGenusIa;
    if(type.equals("event_ia_traffic_lowspeed")) return sGenusIa;
    if(type.equals("event_ia_traffic_flowrate")) return sGenusIa;
    if(type.equals("event_ia_traffic_smoke")) return sGenusIa;
    if(type.equals("event_ia_traffic_abandoned")) return sGenusIa;
    if(type.equals("event_ia_traffic_jam")) return sGenusIa;

    if(type.equals("event_server")) return sGenusServer;
    if(type.equals("event_svr_offline")) return sGenusServer;
    if(type.equals("event_svr_cpu")) return sGenusServer;
    if(type.equals("event_svr_ram")) return sGenusServer;
    if(type.equals("event_svr_disk_err")) return sGenusServer;
    if(type.equals("event_svr_disk_full")) return sGenusServer;
    if(type.equals("event_svr_record_err")) return sGenusServer;

    if(type.equals("event_device")) return sGenusDeveice;
    if(type.equals("event_dev_offline")) return sGenusDeveice;
    if(type.equals("event_dev_cpu")) return sGenusDeveice;
    if(type.equals("event_dev_ram")) return sGenusDeveice;
    if(type.equals("event_dev_disk_err")) return sGenusDeveice;
    if(type.equals("event_dev_disk_full")) return sGenusDeveice;
    if(type.equals("event_dev_record_err")) return sGenusDeveice;

    return null;
  }
  public static VSEventType eventType2VSEventType(String type){
    if (type.equals("event_monitor")) return event_monitor;
    if (type.equals("event_motion_detect")) return event_motion_detect;
    if (type.equals("event_video_lose")) return event_video_lose;
    if (type.equals("event_video_occlusion")) return event_video_occlusion;
    if (type.equals("event_face_snapshot")) return event_face_snapshot;
    if (type.equals("event_face_recognize")) return event_face_recognize;
    if (type.equals("event_steal_move")) return event_steal_move;
    if (type.equals("event_perimeter_alarm")) return event_perimeter_alarm;

    if(type.equals("event_sio")) return event_sio;
    if(type.equals("event_sio_common")) return event_sio_common;
    if(type.equals("event_sio_smoke")) return event_sio_smoke;
    if(type.equals("event_sio_temperature")) return event_sio_temperature;
    if(type.equals("event_sio_fire")) return event_sio_fire;
    if(type.equals("event_sio_gas")) return event_sio_gas;
    if(type.equals("event_sio_infrared")) return event_sio_infrared;
    if(type.equals("event_sio_vibration")) return event_sio_vibration;

    if(type.equals("event_ia")) return event_ia;
    if(type.equals("event_ia_traffic_stop")) return event_ia_traffic_stop;
    if(type.equals("event_ia_traffic_human")) return event_ia_traffic_human;
    if(type.equals("event_ia_traffic_converse")) return event_ia_traffic_converse;
    if(type.equals("event_ia_traffic_condition")) return event_ia_traffic_condition;
    if(type.equals("event_ia_traffic_lowspeed")) return event_ia_traffic_lowspeed;
    if(type.equals("event_ia_traffic_flowrate")) return event_ia_traffic_flowrate;
    if(type.equals("event_ia_traffic_smoke")) return event_ia_traffic_smoke;
    if(type.equals("event_ia_traffic_abandoned")) return event_ia_traffic_abandoned;
    if(type.equals("event_ia_traffic_jam")) return event_ia_traffic_jam;

    if(type.equals("event_server")) return event_server;
    if(type.equals("event_svr_offline")) return event_svr_offline;
    if(type.equals("event_svr_cpu")) return event_svr_cpu;
    if(type.equals("event_svr_ram")) return event_svr_ram;
    if(type.equals("event_svr_disk_err")) return event_svr_disk_err;
    if(type.equals("event_svr_disk_full")) return event_svr_disk_full;
    if(type.equals("event_svr_record_err")) return event_svr_record_err;

    if(type.equals("event_device")) return event_device;
    if(type.equals("event_dev_offline")) return event_dev_offline;
    if(type.equals("event_dev_cpu")) return event_dev_cpu;
    if(type.equals("event_dev_ram")) return event_dev_ram;
    if(type.equals("event_dev_disk_err")) return event_dev_disk_err;
    if(type.equals("event_dev_disk_full")) return event_dev_disk_full;
    if(type.equals("event_dev_record_err")) return event_dev_record_err;

    return event_null;
  }
}
