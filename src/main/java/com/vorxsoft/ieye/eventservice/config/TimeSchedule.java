package com.vorxsoft.ieye.eventservice.config;

import java.util.ArrayList;
import java.util.List;

public class TimeSchedule{
  private TimeSchedule(Builder builder) {
    setTimeScheduleItems(builder.timeScheduleItems);
  }

  public TimeSchedule() {
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public List<TimeScheduleItem> getTimeScheduleItems() {
    return timeScheduleItems;
  }

  public void setTimeScheduleItems(List<TimeScheduleItem> timeScheduleItems) {
    this.timeScheduleItems = timeScheduleItems;
  }
  public void addTimeScheduleItem(TimeScheduleItem timeScheduleItem) {
    if(this.timeScheduleItems == null)
      this.timeScheduleItems = new ArrayList<>();
    this.timeScheduleItems.add(timeScheduleItem);
  }

  private List<TimeScheduleItem> timeScheduleItems = new ArrayList<TimeScheduleItem>(7);

  public static final class Builder {
    private List<TimeScheduleItem> timeScheduleItems;

    private Builder() {
    }

    public Builder timeScheduleItems(List<TimeScheduleItem> val) {
      timeScheduleItems = val;
      return this;
    }

    public TimeSchedule build() {
      return new TimeSchedule(this);
    }
  }

  @Override
  public String toString() {
    return "TimeSchedule{" +
            "timeScheduleItems=" + timeScheduleItems +
            '}';
  }

  public void zero(){
    //private List<TimeScheduleItem> timeScheduleItems = new
    if(getTimeScheduleItems() != null){
      for (int i = 0; i < getTimeScheduleItems().size(); i++) {
        getTimeScheduleItems().get(i).zero();
        getTimeScheduleItems().remove(i);
        i--;
      }
    }else{
      setTimeScheduleItems(new ArrayList<>());
    }
  }

  public void copy(TimeSchedule other){
    zero();
    if(other.getTimeScheduleItems()!=null)
    for (int i = 0; i < other.getTimeScheduleItems().size(); i++) {
      TimeScheduleItem timeScheduleItem = new TimeScheduleItem();
      timeScheduleItem.copy(other.getTimeScheduleItems().get(i));
      getTimeScheduleItems().add(timeScheduleItem);
    }
  }
}