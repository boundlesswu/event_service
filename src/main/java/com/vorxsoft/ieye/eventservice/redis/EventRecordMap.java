package com.vorxsoft.ieye.eventservice.redis;

import java.util.List;

public class EventRecordMap {
  private List<EventRecord> eventRecords;

  public void add(EventRecord record) {
    eventRecords.add(record);
  }

  public EventRecord getone(){
    return eventRecords.remove(eventRecords.size()-1);
  }


}
