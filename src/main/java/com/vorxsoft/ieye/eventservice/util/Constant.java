package com.vorxsoft.ieye.eventservice.util;

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
    if(type.equals("event_monitor")) return sGenusSio;
    if(type.equals("event_motion_detect")) return sGenusSio;
    if(type.equals("event_video_lose")) return sGenusSio;
    if(type.equals("event_video_occlusion")) return sGenusSio;
    if(type.equals("event_face_snapshot")) return sGenusSio;
    if(type.equals("event_face_recognize")) return sGenusSio;
    if(type.equals("event_steal_move")) return sGenusSio;
    if(type.equals("event_perimeter_alarm")) return sGenusSio;

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
}
