package com.vorxsoft.ieye.eventservice.process;

import com.vorxsoft.ieye.eventservice.config.GuardPlan;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class GuardPlanProcess {
//  public Date timestamp2datetime(Timestamp ts){
//    Date date = new Date();
//    date = ts;
//    return date;
//  }
//
//  public Timestamp datetime2timestamp( Date date){
//    Timestamp ts = new Timestamp(date.getTime());
//    return ts;
//  }
//
//  public Timestamp String2timestamp(String time) {
//    Timestamp ts = new Timestamp(System.currentTimeMillis());
//    try {
//      ts = Timestamp.valueOf(time);
//      System.out.println(ts);
//    } catch (Exception e) {
//      e.printStackTrace();
//    }
//    return ts;
//  }
//
//  public String stampToDate(String s){
//    String res;
//    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//    long lt = new Long(s);
//    Date date = new Date(lt);
//    res = simpleDateFormat.format(date);
//    return res;
//  }
//
//
//  public  int dayForWeek(String pTime) throws Exception {
//    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//    Calendar c = Calendar.getInstance();
//    c.setTime(format.parse(pTime));
//    int dayForWeek = 0;
//    if(c.get(Calendar.DAY_OF_WEEK) == 1){
//      dayForWeek = 7;
//    }else{
//      dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
//    }
//    return dayForWeek;
//  }
//
//  public float getFloatHHMM(String pTime) throws ParseException {
//    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//    //Calendar cal =Calendar.getInstance();
//    Date date = simpleDateFormat.parse(pTime);
//    int h = date.getHours();      //获取当前时间的小时部分
//    int m = date.getMinutes();    //获取当前时间的分钟部分
//    return (float) ((float)h+(float)m/60.0);
//  }
//
//
//
//  public boolean isInGuardPlan(GuardPlan plan,String happenTime) throws Exception {
//    boolean ret =  false;
//    switch (plan.getGuard_plan_type()) {
//      case Permanent:
//      {
//        int day = dayForWeek(happenTime);
//        GuardPlan.TimeScheduleItem timeScheduleItem = plan.getTimeScheduleItemIndexOf(day);
//        float ct = getFloatHHMM(happenTime);
//        ret = timeScheduleItem.isInTimeScheduleItem(ct);
//       }
//        break;
//      case Temporary:
//      {
//        Timestamp st = datetime2timestamp(plan.getStart_time());
//        Timestamp et = datetime2timestamp(plan.getEnd_time());
//        Timestamp ct = String2timestamp(happenTime);
//        if(ct.after(st) && ct.before(et)){
//          ret = true;
//        }else{
//          ret = false;
//        }
//       }
//       break;
//    }
//    return ret;
//  }
}
