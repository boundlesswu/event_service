package com.vorxsoft.ieye.eventservice.process;

import com.vorxsoft.ieye.eventservice.config.EventConfig;

public class LoadConfig {
  private EventConfig eventConfig;

  LoadConfig() {

  }

  public EventConfig getEventConfig() {
    return eventConfig;
  }

  public void setEventConfig(EventConfig eventConfig) {
    this.eventConfig = eventConfig;
  }
}
