package com.flamingos.osp.bean;

import java.util.Date;

import org.apache.log4j.Logger;

public class OspProfAcademicsBean {

  private static final Logger logger = Logger.getLogger(OspProfAcademicsBean.class);
  private static final long serialVersionUID = 1L;
  public int profAcdmcId;
  public String profAcdmcName;
  public String profAcdmcDesc;
  public String profAcdmcUniversity;
  public String profAcdmcPassYear;
  public int profId;
  public String activeStatus;
  public Date createdTs;
  public Date updatedTs;
  public String createdBy;
  public String updatedBy;

  public int getProfAcdmcId() {
    return profAcdmcId;
  }

  public void setProfAcdmcId(int profAcdmcId) {
    this.profAcdmcId = profAcdmcId;
  }

  public String getProfAcdmcName() {
    return profAcdmcName;
  }

  public void setProfAcdmcName(String profAcdmcName) {
    this.profAcdmcName = profAcdmcName;
  }

  public String getProfAcdmcDesc() {
    return profAcdmcDesc;
  }

  public void setProfAcdmcDesc(String profAcdmcDesc) {
    this.profAcdmcDesc = profAcdmcDesc;
  }

  public String getProfAcdmcUniversity() {
    return profAcdmcUniversity;
  }

  public void setProfAcdmcUniversity(String profAcdmcUniversity) {
    this.profAcdmcUniversity = profAcdmcUniversity;
  }

  public String getProfAcdmcPassYear() {
    return profAcdmcPassYear;
  }

  public void setProfAcdmcPassYear(String profAcdmcPassYear) {
    this.profAcdmcPassYear = profAcdmcPassYear;
  }

  public int getProfId() {
    return profId;
  }

  public void setProfId(int profId) {
    this.profId = profId;
  }

  public String getActiveStatus() {
    return activeStatus;
  }

  public void setActiveStatus(String activeStatus) {
    this.activeStatus = activeStatus;
  }

  public Date getCreatedTs() {
    return createdTs;
  }

  public void setCreatedTs(Date createdTs) {
    this.createdTs = createdTs;
  }

  public Date getUpdatedTs() {
    return updatedTs;
  }

  public void setUpdatedTs(Date updatedTs) {
    this.updatedTs = updatedTs;
  }

  public String getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  public String getUpdatedBy() {
    return updatedBy;
  }

  public void setUpdatedBy(String updatedBy) {
    this.updatedBy = updatedBy;
  }

}
