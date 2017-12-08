package com.vorxsoft.ieye.eventservice.config;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class GuardPlan {
  private int guard_plan_id;
  private String guard_plan_name;
  private TimeSchedule timeSchedule;
  private String time_schedule;
  private GuardPlanType guard_plan_type;
  private Timestamp start_time;
  private Timestamp end_time;

  private GuardPlan(Builder builder) {
    setGuard_plan_id(builder.guard_plan_id);
    setGuard_plan_name(builder.guard_plan_name);
    setTimeSchedule(builder.timeSchedule);
    setTime_schedule(builder.time_schedule);
    setGuard_plan_type(builder.guard_plan_type);
    setStart_time(builder.start_time);
    setEnd_time(builder.end_time);
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public void clear(){
    timeSchedule = null;
  }

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
        timePeriod.setSt(Float.parseFloat(ccc.get("start").toString()));
        timePeriod.setEt(Float.parseFloat(ccc.get("end").toString()));
        timePeriods.add(timePeriod);
      }
      timeScheduleItem.setTimePeriods(timePeriods);
      System.out.println(timeScheduleItem.toString());
      if(getTimeSchedule() == null)
        timeSchedule = TimeSchedule.newBuilder().build();
      timeSchedule.addTimeScheduleItem(timeScheduleItem);
    }
  }
  public TimeScheduleItem getTimeScheduleItemIndexOf(int index){
    if(getTimeSchedule() == null){
      return null;
    }
    for (int i = 0; i < getTimeSchedule().getTimeScheduleItems().size(); i++) {
      TimeScheduleItem item = getTimeSchedule().getTimeScheduleItems().get(i);
      if(index == item.getDayOfWeek()){
        return item;
      }
    }
    return null;
  }

  public enum GuardPlanType{
     Permanent,Temporary;
  }
  public static GuardPlanType Long2GuardPlanType(int a){
    if(a == 1)
      return GuardPlanType.Permanent;
    else if(a == 2)
      return GuardPlanType.Temporary;
    return GuardPlanType.Permanent;
  }
  public int getGuard_plan_id() {
    return guard_plan_id;
  }

  public void setGuard_plan_id(int guard_plan_id) {
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

  public void setGuard_plan_type(int guard_plan_type) {
    setGuard_plan_type(Long2GuardPlanType(guard_plan_type));
  }

  public Timestamp getStart_time() {
    return start_time;
  }

  public void setStart_time(Timestamp start_time) {
    this.start_time = start_time;
  }

  public Timestamp getEnd_time() {
    return end_time;
  }

  public void setEnd_time(Timestamp end_time) {
    this.end_time = end_time;
  }

//  public static void main(String[] args){
//    GuardPlan guardPlan = new GuardPlan();
//    String json = "{\"TimeSchedule\":[{\"name\":\"1\",\"time\":[{\"type\":0,\"start\":\"6\",\"end\":\"11\"},{\"type\":0,\"start\":\"14\",\"end\":\"19\"},{\"type\":0,\"start\":\"20\",\"end\":\"23\"}]},{\"name\":\"2\",\"time\":[{\"type\":0,\"start\":\"6\",\"end\":\"11\"},{\"type\":0,\"start\":\"14\",\"end\":\"19\"},{\"type\":0,\"start\":\"20\",\"end\":\"23\"}]},{\"name\":\"3\",\"time\":[{\"type\":0,\"start\":\"6\",\"end\":\"11\"},{\"type\":0,\"start\":\"14\",\"end\":\"19\"},{\"type\":0,\"start\":\"20\",\"end\":\"23\"}]},{\"name\":\"4\",\"time\":[{\"type\":0,\"start\":\"6\",\"end\":\"11\"},{\"type\":0,\"start\":\"14\",\"end\":\"19\"},{\"type\":0,\"start\":\"20\",\"end\":\"23\"}]},{\"name\":\"5\",\"time\":[{\"type\":0,\"start\":\"6\",\"end\":\"11\"},{\"type\":0,\"start\":\"14\",\"end\":\"19\"},{\"type\":0,\"start\":\"20\",\"end\":\"23\"}]},{\"name\":\"6\",\"time\":[{\"type\":0,\"start\":\"6\",\"end\":\"11\"},{\"type\":0,\"start\":\"14\",\"end\":\"19\"},{\"type\":0,\"start\":\"20\",\"end\":\"23\"}]},{\"name\":\"7\",\"time\":[{\"type\":0,\"start\":\"6\",\"end\":\"11\"},{\"type\":0,\"start\":\"14\",\"end\":\"19\"},{\"type\":0,\"start\":\"20\",\"end\":\"23\"}]}]}";
//    guardPlan.setTimeSchedule(json);
//    System.out.println(JSON.toJSON(guardPlan.getTimeSchedule()));
//
//  }
public Date timestamp2datetime(Timestamp ts){
  Date date = new Date();
  date = ts;
  return date;
}

  public Timestamp datetime2timestamp( Date date){
    return new Timestamp(date.getTime());
  }

  public Timestamp String2timestamp(String time) {
    Timestamp ts = new Timestamp(System.currentTimeMillis());
    try {
      ts = Timestamp.valueOf(time);
      System.out.println(ts);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return ts;
  }

  public String stampToDate(String s){
    String res;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    long lt = new Long(s);
    Date date = new Date(lt);
    res = simpleDateFormat.format(date);
    return res;
  }


  public  int dayForWeek(String pTime) throws Exception {
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Calendar c = Calendar.getInstance();
    c.setTime(format.parse(pTime));
    int dayForWeek = 0;
    if(c.get(Calendar.DAY_OF_WEEK) == 1){
      dayForWeek = 7;
    }else{
      dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
    }
    return dayForWeek;
  }

  public float getFloatHHMM(String pTime) throws ParseException {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    //Calendar cal =Calendar.getInstance();
    Date date = simpleDateFormat.parse(pTime);
    int h = date.getHours();      //获取当前时间的小时部分
    int m = date.getMinutes();    //获取当前时间的分钟部分
    return (float) ((float)h+(float)m/60.0);
  }
  public boolean isInGuardPlan(String happenTime) throws Exception {
    boolean ret =  false;
    switch (getGuard_plan_type()) {
      case Permanent:
      {
        int day = dayForWeek(happenTime);
        TimeScheduleItem timeScheduleItem = getTimeScheduleItemIndexOf(day);
        float ct = getFloatHHMM(happenTime);
        ret = timeScheduleItem.isInTimeScheduleItem(ct);
      }
      break;
      case Temporary:
      {
        Timestamp st = datetime2timestamp(getStart_time());
        Timestamp et = datetime2timestamp(getEnd_time());
        Timestamp ct = String2timestamp(happenTime);
        if(ct.after(st) && ct.before(et)){
          ret = true;
        }else{
          ret = false;
        }
      }
      break;
    }
    return ret;
  }

  public static final class Builder {
    private int guard_plan_id;
    private String guard_plan_name;
    private TimeSchedule timeSchedule;
    private String time_schedule;
    private GuardPlanType guard_plan_type;
    private Timestamp start_time;
    private Timestamp end_time;

    private Builder() {
    }

    public Builder guard_plan_id(int val) {
      guard_plan_id = val;
      return this;
    }

    public Builder guard_plan_name(String val) {
      guard_plan_name = val;
      return this;
    }

    public Builder timeSchedule(TimeSchedule val) {
      timeSchedule = val;
      return this;
    }

    public Builder time_schedule(String val) {
      time_schedule = val;
      return this;
    }

    public Builder guard_plan_type(GuardPlanType val) {
      guard_plan_type = val;
      return this;
    }

    public Builder start_time(Timestamp val) {
      start_time = val;
      return this;
    }

    public Builder end_time(Timestamp val) {
      end_time = val;
      return this;
    }

    public GuardPlan build() {
      return new GuardPlan(this);
    }
  }
}
