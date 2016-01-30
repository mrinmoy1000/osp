package com.flamingos.osp.dto;

import java.io.Serializable;

public class UserQualificationDTO implements Serializable {

  private static final long serialVersionUID = 1L;
  private String instituteName;
  private String year;

  public String getInstituteName() {
    return instituteName;
  }

  public void setInstituteName(String instituteName) {
    this.instituteName = instituteName;
  }

  public String getYear() {
    return year;
  }

  public void setYear(String year) {
    this.year = year;
  }

}
