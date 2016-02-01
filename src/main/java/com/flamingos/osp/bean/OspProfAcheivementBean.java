package com.flamingos.osp.bean;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class OspProfAcheivementBean implements Serializable {

  private static final long serialVersionUID = 1L;

  private Integer profAchvId;
  private String profAchvName;
  private String profAchvDesc;
  private String profAchvYear;
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

  public Integer getProfAchvId() {
    return profAchvId;
  }

  public void setProfAchvId(Integer profAchvId) {
    this.profAchvId = profAchvId;
  }

  public String getProfAchvName() {
    return profAchvName;
  }

  public void setProfAchvName(String profAchvName) {
    this.profAchvName = profAchvName;
  }

  public String getProfAchvDesc() {
    return profAchvDesc;
  }

  public void setProfAchvDesc(String profAchvDesc) {
    this.profAchvDesc = profAchvDesc;
  }

  public String getProfAchvYear() {
    return profAchvYear;
  }

  public void setProfAchvYear(String profAchvYear) {
    this.profAchvYear = profAchvYear;
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
