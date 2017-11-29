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
}