package com.vorxsoft.ieye.eventservice.config;

import java.util.ArrayList;
import java.util.List;

public class TimeScheduleItem{

  private int dayOfWeek;
  private List<TimePeriod> timePeriods = new ArrayList<TimePeriod>();

  private TimeScheduleItem(Builder builder) {
    setDayOfWeek(builder.dayOfWeek);
    setTimePeriods(builder.timePeriods);
  }

  public TimeScheduleItem() {
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public int getDayOfWeek() {
    return dayOfWeek;
  }

  public void setDayOfWeek(int dayOfWeek) {
    this.dayOfWeek = dayOfWeek;
  }

  public List<TimePeriod> getTimePeriods() {
    return timePeriods;
  }

  public void setTimePeriods(List<TimePeriod> timePeriods) {
    this.timePeriods = timePeriods;
  }

  public boolean isInTimeScheduleItem(float t){
    for (int i = 0; i < getTimePeriods().size(); i++) {
      TimePeriod period = getTimePeriods().get(i);
      if(period.isInTimePeriod(t))
        return true;
    }
    return false;
  }

  public static final class Builder {
    private int dayOfWeek;
    private List<TimePeriod> timePeriods;

    private Builder() {
    }

    public Builder dayOfWeek(int val) {
      dayOfWeek = val;
      return this;
    }

    public Builder timePeriods(List<TimePeriod> val) {
      timePeriods = val;
      return this;
    }

    public TimeScheduleItem build() {
      return new TimeScheduleItem(this);
    }
  }

  @Override
  public String toString() {
    return "TimeScheduleItem{" +
            "dayOfWeek=" + dayOfWeek +
            ", timePeriods=" + timePeriods +
            '}';
  }

  public void zero(){
    setDayOfWeek(0);
    if(getTimePeriods() != null){
      for (int i = 0; i < getTimePeriods().size(); i++) {
        getTimePeriods().remove(i);
        i--;
      }
    }else{
      setTimePeriods(new ArrayList<>());
    }
  }

  public void copy(TimeScheduleItem other){
    zero();
    setDayOfWeek(other.getDayOfWeek());
    if(other.getTimePeriods()!=null){
      for (int i = 0; i < other.getTimePeriods().size(); i++) {
        TimePeriod timePeriod  = new TimePeriod();
        timePeriod.copy(other.getTimePeriods().get(i));
        getTimePeriods().add(timePeriod);
      }
    }
  }
}