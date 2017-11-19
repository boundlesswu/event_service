package com.vorxsoft.ieye.eventservice.config;

public class GuardPlan {
  private Long guard_plan_id;
  private String guard_plan_name;
  private String time_schedule;
  private Long guard_plan_type;
  private java.sql.Timestamp start_time;
  private java.sql.Timestamp end_time;

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

  public Long getGuard_plan_type() {
    return guard_plan_type;
  }

  public void setGuard_plan_type(Long guard_plan_type) {
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
}
