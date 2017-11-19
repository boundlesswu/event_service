package com.vorxsoft.ieye.eventservice.config;

public class EventContacts {
  private Long contact_id;
  private String name;
  private String phone_number;
  private String email;
  private String cellphone;

  public Long getContact_id() {
    return contact_id;
  }

  public void setContact_id(Long contact_id) {
    this.contact_id = contact_id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPhone_number() {
    return phone_number;
  }

  public void setPhone_number(String phone_number) {
    this.phone_number = phone_number;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getCellphone() {
    return cellphone;
  }

  public void setCellphone(String cellphone) {
    this.cellphone = cellphone;
  }
}
