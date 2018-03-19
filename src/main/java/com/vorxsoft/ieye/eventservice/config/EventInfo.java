package com.vorxsoft.ieye.eventservice.config;

import com.vorxsoft.ieye.eventservice.linkage.EventLinkage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EventInfo {
  private int event_id;
  private String event_no;
  private String event_genus;
  private String event_type;
  private String event_name;
  private String event_desc;
  private int enable_state;
  private int event_level;
  private int auto_release_interval;
  private GuardPlan guardPlan;
  private int res_id = 0;
  private int dev_id = 0;
  private int machine_id = 0;
  private int svr_id = 0;
  private int sourceId = 0;
  private int iaagId = 0;
  private int iaag_chn_id = 0;
  List<EventLinkage> eventLinkagelist;

  public EventInfo() {
    event_no = "";
    event_genus = "";
    event_type = "";
    event_name = "";
    event_desc = "";
  }

  private EventInfo(Builder builder) {
    setEvent_id(builder.event_id);
    setEvent_no(builder.event_no);
    setEvent_genus(builder.event_genus);
    setEvent_type(builder.event_type);
    setEvent_name(builder.event_name);
    setEvent_desc(builder.event_desc);
    setEnable_state(builder.enable_state);
    setEvent_level(builder.event_level);
    setAuto_release_interval(builder.auto_release_interval);
    setGuardPlan(builder.guardPlan);
    setRes_id(builder.res_id);
    setDev_id(builder.dev_id);
    setMachine_id(builder.machine_id);
    setSvr_id(builder.svr_id);
    setSourceId(builder.sourceId);
    setIaagId(builder.iaagId);
    setIaag_chn_id(builder.iaag_chn_id);
    setEventLinkagelist(builder.eventLinkagelist);
  }

  public void clear() {
    if (getGuardPlan() != null)
      getGuardPlan().zero();
    guardPlan = null;
    if (getEventLinkagelist() != null) {
      for (EventLinkage linkage : getEventLinkagelist()) {
        getEventLinkagelist().remove(linkage);
        linkage.zero();
      }
    }
    eventLinkagelist = null;
  }

  public static Builder newBuilder() {
    return new Builder();
  }


  public int getEvent_id() {
    return event_id;
  }

  public void setEvent_id(int event_id) {
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

  public String getEvent_type() {
    return event_type;
  }

  public void setEvent_type(String event_type) {
    this.event_type = event_type;
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

  public int getEnable_state() {
    return enable_state;
  }

  public void setEnable_state(int enable_state) {
    this.enable_state = enable_state;
  }

  public int getEvent_level() {
    return event_level;
  }

  public void setEvent_level(int event_level) {
    this.event_level = event_level;
  }

  public int getAuto_release_interval() {
    return auto_release_interval;
  }

  public void setAuto_release_interval(int auto_release_interval) {
    this.auto_release_interval = auto_release_interval;
  }

  public GuardPlan getGuardPlan() {
    return guardPlan;
  }

  public void setGuardPlan(GuardPlan guardPlan) {
    this.guardPlan = guardPlan;
  }

  public int getRes_id() {
    return res_id;
  }

  public void setRes_id(int res_id) {
    this.res_id = res_id;
  }

  public int getDev_id() {
    return dev_id;
  }

  public void setDev_id(int dev_id) {
    this.dev_id = dev_id;
  }

  public int getMachine_id() {
    return machine_id;
  }

  public void setMachine_id(int machine_id) {
    this.machine_id = machine_id;
  }

  public int getSvr_id() {
    return svr_id;
  }

  public void setSvr_id(int svr_id) {
    this.svr_id = svr_id;
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


  public int getEventLinkagelistSize() {
    if (getEventLinkagelist() == null)
      return 0;
    return getEventLinkagelist().size();
  }

  public void setEventLinkagelist(List<EventLinkage> eventLinkagelist) {
    this.eventLinkagelist = eventLinkagelist;
  }

  public List<EventLinkage> getEventLinkagelist() {
    return eventLinkagelist;
  }

  public static final class Builder {
    private int event_id;
    private String event_no;
    private String event_genus;
    private String event_type;
    private String event_name;
    private String event_desc;
    private int enable_state;
    private int event_level;
    private int auto_release_interval;
    private GuardPlan guardPlan;
    private int res_id;
    private int dev_id;
    private int machine_id;
    private int svr_id;
    private int sourceId;
    private int iaagId;
    private int iaag_chn_id;
    private List<EventLinkage> eventLinkagelist;


    private Builder() {
    }

    public Builder event_id(int val) {
      event_id = val;
      return this;
    }

    public Builder event_no(String val) {
      event_no = val;
      return this;
    }

    public Builder event_genus(String val) {
      event_genus = val;
      return this;
    }

    public Builder event_type(String val) {
      event_type = val;
      return this;
    }

    public Builder event_name(String val) {
      event_name = val;
      return this;
    }

    public Builder event_desc(String val) {
      event_desc = val;
      return this;
    }

    public Builder enable_state(int val) {
      enable_state = val;
      return this;
    }

    public Builder event_level(int val) {
      event_level = val;
      return this;
    }

    public Builder auto_release_interval(int val) {
      auto_release_interval = val;
      return this;
    }

    public Builder guardPlan(GuardPlan val) {
      guardPlan = val;
      return this;
    }

    public Builder res_id(int val) {
      res_id = val;
      return this;
    }

    public Builder dev_id(int val) {
      dev_id = val;
      return this;
    }

    public Builder machine_id(int val) {
      machine_id = val;
      return this;
    }

    public Builder svr_id(int val) {
      svr_id = val;
      return this;
    }

    public Builder sourceId(int val) {
      sourceId = val;
      return this;
    }

    public Builder iaagId(int val) {
      iaagId = val;
      return this;
    }

    public Builder iaag_chn_id(int val) {
      iaag_chn_id = val;
      return this;
    }

    public Builder eventLinkagelist(List<EventLinkage> val) {
      eventLinkagelist = val;
      return this;
    }

    public EventInfo build() {
      return new EventInfo(this);
    }

  }

  public MonitorConfigKey getMonitorConfigKey() {
    return MonitorConfigKey.newBuilder().
            event_type(getEvent_type()).res_id(getRes_id()).build();
  }

  public SioConfigKey getSioConfigKey() {
    return SioConfigKey.newBuilder().
            event_type(getEvent_type()).res_id(getRes_id()).build();
  }

  public IaConfigKey getIaConfigKey() {
    return IaConfigKey.newBuilder().
            event_type(getEvent_type()).res_id(getRes_id()).
            iaagId(getIaagId()).iaag_chn_id(getIaag_chn_id()).build();
  }

  public ServerConfigKey getServerConfigKey() {
    return ServerConfigKey.newBuilder().
            event_type(getEvent_type()).machine_id(getMachine_id()).build();
  }

  public DeviceConfigKey getDeviceConfigKey() {
    return DeviceConfigKey.newBuilder().
            event_type(getEvent_type()).dev_id(getDev_id()).build();
  }

  public void deleteLinkagebyId(int id) {
    Iterator<EventLinkage> it = getEventLinkagelist().iterator();
    while (it.hasNext()) {
      EventLinkage x = it.next();
      if (x.getLinkage_id() == id) {
        getEventLinkagelist().remove(x);
        return;
      }
    }
  }

  @Override
  public String toString() {
    return "EventInfo{" +
            "event_id=" + event_id +
            ", event_no='" + event_no + '\'' +
            ", event_genus='" + event_genus + '\'' +
            ", event_type='" + event_type + '\'' +
            ", event_name='" + event_name + '\'' +
            ", event_desc='" + event_desc + '\'' +
            ", enable_state=" + enable_state +
            ", event_level=" + event_level +
            ", auto_release_interval=" + auto_release_interval +
            ", guardPlan=" + guardPlan +
            ", res_id=" + res_id +
            ", dev_id=" + dev_id +
            ", machine_id=" + machine_id +
            ", svr_id=" + svr_id +
            ", sourceId=" + sourceId +
            ", iaagId=" + iaagId +
            ", iaag_chn_id=" + iaag_chn_id +
            ", eventLinkagelist=" + eventLinkagelist +
            '}';
  }

  public void zero() {
    this.event_id = 0;
    this.event_no = "";
    this.event_genus = "";
    this.event_type = "";
    this.event_name = "";
    this.event_desc = "";
    this.enable_state = 0;
    this.event_level = 0;
    this.auto_release_interval = 0;
    this.guardPlan.zero();
    this.res_id = 0;
    this.dev_id = 0;
    this.machine_id = 0;
    this.svr_id = 0;
    this.sourceId = 0;
    this.iaagId = 0;
    this.iaag_chn_id = 0;
    if (this.eventLinkagelist != null) {
      for (int i = 0; i < getEventLinkagelist().size(); i++) {
        getEventLinkagelist().get(i).zero();
        getEventLinkagelist().remove(i);
        i--;
      }
    } else {
      setEventLinkagelist(new ArrayList<>());
    }
  }

  public void copy(EventInfo other) {
    this.event_id = other.getEvent_id();
    this.event_no = other.getEvent_no();
    this.event_genus = other.getEvent_genus();
    this.event_type = other.getEvent_type();
    this.event_name = other.getEvent_name();
    this.event_desc = other.getEvent_desc();
    this.enable_state = other.getEnable_state();
    this.event_level = other.getEvent_level();
    this.auto_release_interval = other.getAuto_release_interval();
    this.guardPlan.copy(other.getGuardPlan());
    this.res_id = other.getRes_id();
    this.dev_id = other.getDev_id();
    this.machine_id = other.getMachine_id();
    this.svr_id = other.getSvr_id();
    this.sourceId = other.getSourceId();
    this.iaagId = other.getIaagId();
    this.iaag_chn_id = other.getIaag_chn_id();
    this.eventLinkagelist.clear();
    if (other.getEventLinkagelist() != null) {
      for (int i = 0; i < other.getEventLinkagelist().size(); i++) {
        EventLinkage eventLinkage = new EventLinkage();
        eventLinkage.copy(other.getEventLinkagelist().get(i));
        getEventLinkagelist().add(eventLinkage);
      }
    }
  }

}
