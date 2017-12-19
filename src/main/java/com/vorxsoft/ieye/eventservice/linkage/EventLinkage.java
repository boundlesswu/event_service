package com.vorxsoft.ieye.eventservice.linkage;

import com.google.protobuf.ProtocolStringList;
import com.vorxsoft.ieye.proto.Linkage;

import java.util.ArrayList;
import java.util.Iterator;

public class EventLinkage {
  private int linkage_id;
  private int event_id;
  private String linkage_type;
  private String arg1;
  private String arg2;
  private String arg3;
  private String arg4;
  private String arg5;
  private String arg6;
  private String arg7;
  private String arg8;

  public EventLinkage() {
    this.linkage_id = 0;
    this.event_id = 0;
    this.linkage_type = "";
    this.arg1 = "";
    this.arg2 = "";
    this.arg3 = "";
    this.arg4 = "";
    this.arg5 = "";
    this.arg6 = "";
    this.arg7 = "";
    this.arg8 = "";
  }

  private EventLinkage(Builder builder) {
    setLinkage_id(builder.linkage_id);
    setEvent_id(builder.event_id);
    setLinkage_type(builder.linkage_type);
    setArg1(builder.arg1);
    setArg2(builder.arg2);
    setArg3(builder.arg3);
    setArg4(builder.arg4);
    setArg5(builder.arg5);
    setArg6(builder.arg6);
    setArg7(builder.arg7);
    setArg8(builder.arg8);
  }

  public static Builder newBuilder() {
    return new Builder();
  }


  public int getLinkage_id() {
    return linkage_id;
  }

  public void setLinkage_id(int linkage_id) {
    this.linkage_id = linkage_id;
  }

  public int getEvent_id() {
    return event_id;
  }

  public void setEvent_id(int event_id) {
    this.event_id = event_id;
  }

  public String getLinkage_type() {
    return linkage_type;
  }

  public void setLinkage_type(String linkage_type) {
    this.linkage_type = linkage_type;
  }

  public String getArg1() {
    return arg1;
  }

  public void setArg1(String arg1) {
    this.arg1 = arg1;
  }

  public String getArg2() {
    return arg2;
  }

  public void setArg2(String arg2) {
    this.arg2 = arg2;
  }

  public String getArg3() {
    return arg3;
  }

  public void setArg3(String arg3) {
    this.arg3 = arg3;
  }

  public String getArg4() {
    return arg4;
  }

  public void setArg4(String arg4) {
    this.arg4 = arg4;
  }

  public String getArg5() {
    return arg5;
  }

  public void setArg5(String arg5) {
    this.arg5 = arg5;
  }

  public String getArg6() {
    return arg6;
  }

  public void setArg6(String arg6) {
    this.arg6 = arg6;
  }

  public String getArg7() {
    return arg7;
  }

  public void setArg7(String arg7) {
    this.arg7 = arg7;
  }

  public String getArg8() {
    return arg8;
  }

  public void setArg8(String arg8) {
    this.arg8 = arg8;
  }

  public static final class Builder {
    private int linkage_id;
    private int event_id;
    private String linkage_type;
    private String arg1;
    private String arg2;
    private String arg3;
    private String arg4;
    private String arg5;
    private String arg6;
    private String arg7;
    private String arg8;

    private Builder() {
    }

    public Builder linkage_id(int val) {
      linkage_id = val;
      return this;
    }

    public Builder event_id(int val) {
      event_id = val;
      return this;
    }

    public Builder linkage_type(String val) {
      linkage_type = val;
      return this;
    }

    public Builder arg1(String val) {
      arg1 = val;
      return this;
    }

    public Builder arg2(String val) {
      arg2 = val;
      return this;
    }

    public Builder arg3(String val) {
      arg3 = val;
      return this;
    }

    public Builder arg4(String val) {
      arg4 = val;
      return this;
    }

    public Builder arg5(String val) {
      arg5 = val;
      return this;
    }

    public Builder arg6(String val) {
      arg6 = val;
      return this;
    }

    public Builder arg7(String val) {
      arg7 = val;
      return this;
    }

    public Builder arg8(String val) {
      arg8 = val;
      return this;
    }

    public EventLinkage build() {
      return new EventLinkage(this);
    }
  }
  //public com.vorxsoft.ieye.proto.Linkage
  public Linkage convert2linkage(){
    Linkage.Builder linkageBulder = Linkage.newBuilder().setNLinkageID(getLinkage_id()).
            setNEventID(getEvent_id()).setSLinkageType(getLinkage_type());
    if(arg1 != null && arg1.length() > 0){
      linkageBulder.addSArgs(arg1);
      //linkageBulder.setSArgs(0,arg1);
    }
    if(arg2 != null && arg2.length() > 0){
      linkageBulder.addSArgs(arg2);
      //linkageBulder.setSArgs(1,arg2);
    }
    if(arg3 != null && arg3.length() > 0){
      linkageBulder.addSArgs(arg3);
      //linkageBulder.setSArgs(2,arg3);
    }
    if(arg4 != null && arg4.length() > 0){
      linkageBulder.addSArgs(arg4);
      //linkageBulder.setSArgs(3,arg4);
    }
    if(arg5 != null && arg5.length() > 0){
      linkageBulder.addSArgs(arg5);
      //linkageBulder.setSArgs(4,arg5);
    }
    if(arg6 != null && arg6.length() > 0){
      linkageBulder.addSArgs(arg6);
      //linkageBulder.setSArgs(5,arg6);
    }
    if(arg7 != null && arg7.length() > 0){
      linkageBulder.addSArgs(arg7);
      //linkageBulder.setSArgs(6,arg7);
    }
    if(arg8 != null && arg8.length() > 0){
      linkageBulder.addSArgs(arg8);
      //linkageBulder.setSArgs(7,arg8);
    }
    return linkageBulder.build();
  }

  @Override
  public String toString() {
    return "EventLinkage{" +
            "linkage_id=" + linkage_id +
            ", event_id=" + event_id +
            ", linkage_type='" + linkage_type + '\'' +
            ", arg1='" + arg1 + '\'' +
            ", arg2='" + arg2 + '\'' +
            ", arg3='" + arg3 + '\'' +
            ", arg4='" + arg4 + '\'' +
            ", arg5='" + arg5 + '\'' +
            ", arg6='" + arg6 + '\'' +
            ", arg7='" + arg7 + '\'' +
            ", arg8='" + arg8 + '\'' +
            '}';
  }
}
