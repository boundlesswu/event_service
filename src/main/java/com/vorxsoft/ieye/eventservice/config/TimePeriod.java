package com.vorxsoft.ieye.eventservice.config;

public class TimePeriod{
  private int type;
  private float st;
  private float et;

  private TimePeriod(Builder builder) {
    setType(builder.type);
    setSt(builder.st);
    setEt(builder.et);
  }

  public TimePeriod() {
  }

  public static Builder newBuilder() {
    return new Builder();
  }

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

  public static final class Builder {
    private int type;
    private float st;
    private float et;

    private Builder() {
    }

    public Builder type(int val) {
      type = val;
      return this;
    }

    public Builder st(float val) {
      st = val;
      return this;
    }

    public Builder et(float val) {
      et = val;
      return this;
    }

    public TimePeriod build() {
      return new TimePeriod(this);
    }
  }

  @Override
  public String toString() {
    return "TimePeriod{" +
            "type=" + type +
            ", st=" + st +
            ", et=" + et +
            '}';
  }
  public void copy(TimePeriod other){
    setEt(other.getEt());
    setSt(other.getSt());
    setType(other.getType());
  }
}