package com.vorxsoft.ieye.eventservice.redis;

import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Map;

public class EventRecordMap {
  private List<EventRecord> eventRecords;

  public void add(EventRecord record) {
    eventRecords.add(record);
  }

  public EventRecord getone(){
    return eventRecords.remove(eventRecords.size()-1);
  }

}
