package com.vorxsoft.ieye.eventservice.process;

import com.vorxsoft.ieye.eventservice.config.GuardPlan;

import java.sql.Timestamp;
import java.util.Date;


public class GuardPlanProcess {
  public Date timestamp2datetime(Timestamp ts){
    Date date = new Date();
    date = ts;
    return date;
  }

  public Timestamp datetime2timestamp( Date date){
    Timestamp ts = new Timestamp(date.getTime());
    return ts;
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


  public boolean isInGuardPlan(GuardPlan plan,String happenTime){
    boolean ret =  false;
    switch (plan.getGuard_plan_type()) {
      case Permanent:
      {

       }
        break;
      case Temporary:
      {
        Timestamp st = datetime2timestamp(plan.getStart_time());
        Timestamp et = datetime2timestamp(plan.getEnd_time());
        Timestamp ct = String2timestamp(happenTime);
        if(ct.after(st) && ct.before(et)){
          ret = true;
        }else{
          ret = false;
        }
       }
       break;
    }
    return true;
  }
}
