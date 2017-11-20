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
  private int sourceId;
  private int iaagId;
  private int iaag_chn_id;
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

  public int getSourceId() {
    return sourceId;
  }

  public void setSourceId(int sourceId) {
    this.sourceId = sourceId;
  }

  public int getIaagId() {
    return iaagId;
  }

  public void setIaagId(int iaagId) {
    this.iaagId = iaagId;
  }

  public int getIaag_chn_id() {
    return iaag_chn_id;
  }

  public void setIaag_chn_id(int iaag_chn_id) {
    this.iaag_chn_id = iaag_chn_id;
  }


  public EventLinkage[] getEventLinkagelist() {
    return eventLinkagelist;
  }

  public void setEventLinkagelist(EventLinkage[] eventLinkagelist) {
    this.eventLinkagelist = eventLinkagelist;
  }
}
