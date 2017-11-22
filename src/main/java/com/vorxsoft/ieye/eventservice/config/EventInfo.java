package com.vorxsoft.ieye.eventservice.config;

import com.vorxsoft.ieye.eventservice.linkage.EventLinkage;

public class EventInfo {

  private Long event_id;
  private String event_no;
  private String event_genus;
  private String event_type;
  private String event_name;
  private String event_desc;
  private Long enable_state;
  private Long event_level;
  private Long auto_release_interval;
  private GuardPlan guardPlan;
  private long res_id=0;
  private long dev_id=0;
  private long machine_id=0;
  private long svr_id=0;
  private long sourceId=0;
  private long iaagId=0;
  private long iaag_chn_id=0;
  private EventLinkage[] eventLinkagelist;


  public Long getEvent_id() {
    return event_id;
  }

  public void setEvent_id(Long event_id) {
    this.event_id = event_id;
  }

  public String getEvent_no() {
    return event_no;
  }

  public void setEvent_no(String event_no) {
    this.event_no = event_no;
  }

  public String getEvent_genus() {
    return event_genus;
  }

  public void setEvent_genus(String event_genus) {
    this.event_genus = event_genus;
  }

  public String getEvent_name() {
    return event_name;
  }

  public void setEvent_name(String event_name) {
    this.event_name = event_name;
  }

  public String getEvent_desc() {
    return event_desc;
  }

  public void setEvent_desc(String event_desc) {
    this.event_desc = event_desc;
  }

  public Long getEnable_state() {
    return enable_state;
  }

  public void setEnable_state(Long enable_state) {
    this.enable_state = enable_state;
  }

  public Long getEvent_level() {
    return event_level;
  }

  public void setEvent_level(Long event_level) {
    this.event_level = event_level;
  }

  public Long getAuto_release_interval() {
    return auto_release_interval;
  }

  public void setAuto_release_interval(Long auto_release_interval) {
    this.auto_release_interval = auto_release_interval;
  }

  public String getEvent_type() {
    return event_type;
  }

  public void setEvent_type(String event_type) {
    this.event_type = event_type;
  }

  public GuardPlan getGuardPlan() {
    return guardPlan;
  }

  public void setGuardPlan(GuardPlan guardPlan) {
    this.guardPlan = guardPlan;
  }


  public EventLinkage[] getEventLinkagelist() {
    return eventLinkagelist;
  }

  public void setEventLinkagelist(EventLinkage[] eventLinkagelist) {
    this.eventLinkagelist = eventLinkagelist;
  }

  public long getRes_id() {
    return res_id;
  }

  public void setRes_id(long res_id) {
    this.res_id = res_id;
  }

  public long getDev_id() {
    return dev_id;
  }

  public void setDev_id(long dev_id) {
    this.dev_id = dev_id;
  }

  public long getMachine_id() {
    return machine_id;
  }

  public void setMachine_id(long machine_id) {
    this.machine_id = machine_id;
  }

  public long getSvr_id() {
    return svr_id;
  }

  public void setSvr_id(long svr_id) {
    this.svr_id = svr_id;
  }

  public long getSourceId() {
    return sourceId;
  }

  public void setSourceId(long sourceId) {
    this.sourceId = sourceId;
  }

  public long getIaagId() {
    return iaagId;
  }

  public void setIaagId(long iaagId) {
    this.iaagId = iaagId;
  }

  public long getIaag_chn_id() {
    return iaag_chn_id;
  }

  public void setIaag_chn_id(long iaag_chn_id) {
    this.iaag_chn_id = iaag_chn_id;
  }
}
