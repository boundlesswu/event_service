package com.vorxsoft.ieye.eventservice.redis;

import java.util.List;

public class EventRecordMap {
  public List<EventRecord> getEventRecords() {
    return eventRecords;
  }

  public void setEventRecords(List<EventRecord> eventRecords) {
    this.eventRecords = eventRecords;
  }

  private List<EventRecord> eventRecords;

  public void add(EventRecord record) {
    eventRecords.add(record);
  }

  public EventRecord getone(){
    return eventRecords.remove(eventRecords.size()-1);
  }


}
