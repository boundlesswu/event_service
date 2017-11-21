package com.vorxsoft.ieye.eventservice.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GuardPlan {
  private Long guard_plan_id;
  private String guard_plan_name;

  public TimeSchedule getTimeSchedule() {
    return timeSchedule;
  }
  GuardPlan(){ }
  public void setTimeSchedule(TimeSchedule timeSchedule) {
    this.timeSchedule = timeSchedule;
  }
  public void setTimeSchedule(String  time_schedule) {

    JSONObject da = JSONObject.parseObject(time_schedule);
    System.out.println(da);
    JSONArray b = da.getJSONArray("TimeSchedule");
    System.out.println(b);
    //JSONArray ja = JSONObject.parseArray(b.toString());

    for (int i = 0; i<b.size() ; i++) {
      TimeScheduleItem timeScheduleItem  = new TimeScheduleItem() ;
      JSONObject bbb = (JSONObject) b.toArray()[i];
      System.out.println(bbb);
      System.out.println(bbb.get("name"));
      timeScheduleItem.setDayOfWeek(Integer.parseInt( bbb.get("name").toString()));
      JSONArray timeArray = bbb.getJSONArray("time");
      List<TimePeriod> timePeriods = new ArrayList<>();
      for (int j = 0; j < timeArray.size(); j++) {
        JSONObject ccc = (JSONObject) timeArray.toArray()[j];
        TimePeriod  timePeriod = new TimePeriod();
        timePeriod.setType(Integer.parseInt(ccc.get("type").toString()));
        timePeriod.setSt(Integer.parseInt(ccc.get("start").toString()));
        timePeriod.setEt(Integer.parseInt(ccc.get("end").toString()));
        timePeriods.add(timePeriod);
      }
      timeScheduleItem.setTimePeriods(timePeriods);
      System.out.println(timeScheduleItem.toString());
      timeSchedule.addTimeScheduleItem(timeScheduleItem);
    }
  }
  public TimeScheduleItem getTimeScheduleItemIndexOf(int index){
    for (int i = 0; i < getTimeSchedule().getTimeScheduleItems().size(); i++) {
      TimeScheduleItem item = getTimeSchedule().getTimeScheduleItems().get(i);
      if(index == item.getDayOfWeek()){
        return item;
      }
    }
    return null;
  }


  public class TimePeriod{
    private int type;
    private float st;
    private float et;

    public boolean isInTimePeriod(float t){
      return (t <= et) && (t >= st);
    }

    public int getType() {
      return type;
    }

    public void setType(int type) {
      this.type = type;
    }

    public float getSt() {
      return st;
    }

    public void setSt(float st) {
      this.st = st;
    }

    public float getEt() {
      return et;
    }

    public void setEt(float et) {
      this.et = et;
    }
  }
  public class TimeScheduleItem{

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

    private int dayOfWeek;
    private List<TimePeriod> timePeriods = new ArrayList<TimePeriod>();
  }
  public class TimeSchedule{
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
  }
  private TimeSchedule timeSchedule = new TimeSchedule();
  private String time_schedule;
  private GuardPlanType guard_plan_type;
  private java.sql.Timestamp start_time;
  private java.sql.Timestamp end_time;
  public enum GuardPlanType{
     Permanent,Temporary;
  }
  public GuardPlanType Long2GuardPlanType(Long a ){
    if(a == 1)
      return GuardPlanType.Permanent;
    else if(a == 2)
      return GuardPlanType.Temporary;
    return GuardPlanType.Permanent;
  }
  public Long getGuard_plan_id() {
    return guard_plan_id;
  }

  public void setGuard_plan_id(Long guard_plan_id) {
    this.guard_plan_id = guard_plan_id;
  }

  public String getGuard_plan_name() {
    return guard_plan_name;
  }

  public void setGuard_plan_name(String guard_plan_name) {
    this.guard_plan_name = guard_plan_name;
  }

  public String getTime_schedule() {
    return time_schedule;
  }

  public void setTime_schedule(String time_schedule) {
    this.time_schedule = time_schedule;
  }

  public GuardPlanType getGuard_plan_type() {
    return guard_plan_type;
  }

  public void setGuard_plan_type(GuardPlanType guard_plan_type) {
    this.guard_plan_type = guard_plan_type;
  }

  public java.sql.Timestamp getStart_time() {
    return start_time;
  }

  public void setStart_time(java.sql.Timestamp start_time) {
    this.start_time = start_time;
  }

  public java.sql.Timestamp getEnd_time() {
    return end_time;
  }

  public void setEnd_time(java.sql.Timestamp end_time) {
    this.end_time = end_time;
  }

  public static void main(String[] args){
    GuardPlan guardPlan = new GuardPlan();
    String json = "{\"TimeSchedule\":[{\"name\":\"1\",\"time\":[{\"type\":0,\"start\":\"6\",\"end\":\"11\"},{\"type\":0,\"start\":\"14\",\"end\":\"19\"},{\"type\":0,\"start\":\"20\",\"end\":\"23\"}]},{\"name\":\"2\",\"time\":[{\"type\":0,\"start\":\"6\",\"end\":\"11\"},{\"type\":0,\"start\":\"14\",\"end\":\"19\"},{\"type\":0,\"start\":\"20\",\"end\":\"23\"}]},{\"name\":\"3\",\"time\":[{\"type\":0,\"start\":\"6\",\"end\":\"11\"},{\"type\":0,\"start\":\"14\",\"end\":\"19\"},{\"type\":0,\"start\":\"20\",\"end\":\"23\"}]},{\"name\":\"4\",\"time\":[{\"type\":0,\"start\":\"6\",\"end\":\"11\"},{\"type\":0,\"start\":\"14\",\"end\":\"19\"},{\"type\":0,\"start\":\"20\",\"end\":\"23\"}]},{\"name\":\"5\",\"time\":[{\"type\":0,\"start\":\"6\",\"end\":\"11\"},{\"type\":0,\"start\":\"14\",\"end\":\"19\"},{\"type\":0,\"start\":\"20\",\"end\":\"23\"}]},{\"name\":\"6\",\"time\":[{\"type\":0,\"start\":\"6\",\"end\":\"11\"},{\"type\":0,\"start\":\"14\",\"end\":\"19\"},{\"type\":0,\"start\":\"20\",\"end\":\"23\"}]},{\"name\":\"7\",\"time\":[{\"type\":0,\"start\":\"6\",\"end\":\"11\"},{\"type\":0,\"start\":\"14\",\"end\":\"19\"},{\"type\":0,\"start\":\"20\",\"end\":\"23\"}]}]}";
    guardPlan.setTimeSchedule(json);
    System.out.println(JSON.toJSON(guardPlan.getTimeSchedule()));

  }
}
