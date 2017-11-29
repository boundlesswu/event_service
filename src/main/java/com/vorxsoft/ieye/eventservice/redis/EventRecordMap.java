package com.vorxsoft.ieye.eventservice.redis;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;
import com.vorxsoft.ieye.proto.EventWithLinkage;
import com.vorxsoft.ieye.proto.Events;
import com.vorxsoft.ieye.proto.ReportEventRequest;
import com.vorxsoft.ieye.proto.ReportLinkageRequest;

import java.util.Iterator;
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

  public  ReportEventRequest convert2ReportEventRequest(){
    ReportEventRequest req = ReportEventRequest.newBuilder().setSBusinessID("00000000").build();
    Iterator<EventRecord> it = getEventRecords().iterator();
    while(it.hasNext()) {
      EventRecord record = it.next();
      if(record.isbSend2cms()){
        Events  event = record.covert2Events();
        req.getEventsList().add(event);
      }
    }
    return req;
  }

  public  String convert2jsonString() throws InvalidProtocolBufferException {
    ReportEventRequest req = ReportEventRequest.newBuilder().setSBusinessID("00000000").build();
    Iterator<EventRecord> it = getEventRecords().iterator();
    while(it.hasNext()) {
      EventRecord record = it.next();
      if(record.isbSend2mq()){
        Events event = record.covert2Events();
        req.getEventsList().add(event);
      }
    }
    return JsonFormat.printer().print(req.toBuilder());
  }

  public ReportLinkageRequest convert2ReportLinkageRequest(){
    ReportLinkageRequest req = ReportLinkageRequest.newBuilder().setSBusinessID("00000000").build();
    Iterator<EventRecord> it = getEventRecords().iterator();
    while(it.hasNext()) {
      EventRecord record = it.next();
      if(record.isbSend2blg()){
        EventWithLinkage eventWithLinkage = record.covert2EventWithLinkage();
        req.getEventWithLinkagesList().add(eventWithLinkage);
      }
    }
    return req;
  }
}
