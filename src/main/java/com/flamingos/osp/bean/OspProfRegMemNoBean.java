package com.flamingos.osp.bean;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class OspProfRegMemNoBean implements Serializable {
  private static final long serialVersionUID = 1L;

  private Integer regMemId;
  private String regMemType;
  private String regAuth;
  private String regYear;
  private Long profId;
  private Integer activeStatus;
  @JsonIgnore
  private String createdBy;
  @JsonIgnore
  private Date createdTs;
  @JsonIgnore
  private String updatedBy;
  @JsonIgnore
  private Date updatedTs;

  public Integer getRegMemId() {
    return regMemId;
  }

  public void setRegMemId(Integer regMemId) {
    this.regMemId = regMemId;
  }

  public String getRegMemType() {
    return regMemType;
  }

  public void setRegMemType(String regMemType) {
    this.regMemType = regMemType;
  }

  public String getRegAuth() {
    return regAuth;
  }

  public void setRegAuth(String regAuth) {
    this.regAuth = regAuth;
  }

  public String getRegYear() {
    return regYear;
  }

  public void setRegYear(String regYear) {
    this.regYear = regYear;
  }

  public Long getProfId() {
    return profId;
  }

  public void setProfId(Long profId) {
    this.profId = profId;
  }

  public Integer getActiveStatus() {
    return activeStatus;
  }

  public void setActiveStatus(Integer activeStatus) {
    this.activeStatus = activeStatus;
  }

  public String getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  public Date getCreatedTs() {
    return createdTs;
  }

  public void setCreatedTs(Date createdTs) {
    this.createdTs = createdTs;
  }

  public String getUpdatedBy() {
    return updatedBy;
  }

  public void setUpdatedBy(String updatedBy) {
    this.updatedBy = updatedBy;
  }

  public Date getUpdatedTs() {
    return updatedTs;
  }

  public void setUpdatedTs(Date updatedTs) {
    this.updatedTs = updatedTs;
  }

}
