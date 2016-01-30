package com.flamingos.osp.dto;

import java.io.Serializable;

public class UserExperienceDTO implements Serializable {

  private static final long serialVersionUID = 1L;
  private String fromYear;
  private String toYear;

  public String getFromYear() {
    return fromYear;
  }

  public void setFromYear(String fromYear) {
    this.fromYear = fromYear;
  }

  public String getToYear() {
    return toYear;
  }

  public void setToYear(String toYear) {
    this.toYear = toYear;
  }

}
