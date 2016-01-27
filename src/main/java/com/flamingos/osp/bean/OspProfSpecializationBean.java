package com.flamingos.osp.bean;

import java.util.Date;

import org.apache.log4j.Logger;

public class OspProfSpecializationBean {

  private static final Logger logger = Logger.getLogger(OspProfSpecializationBean.class);
  private static final long serialVersionUID = 1L;
  public int profSpecId;
  public String profSpecName;
  public String profSpecDesc;
  public int profId;
  public int activeStatus;
  public Date createdTs;
  public Date updatedTs;
  public String createdBy;
  public String updatedBy;

  public int getProfSpecId() {
    return profSpecId;
  }

  public void setProfSpecId(int profSpecId) {
    this.profSpecId = profSpecId;
  }

  public String getProfSpecName() {
    return profSpecName;
  }

  public void setProfSpecName(String profSpecName) {
    this.profSpecName = profSpecName;
  }

  public String getProfSpecDesc() {
    return profSpecDesc;
  }

  public void setProfSpecDesc(String profSpecDesc) {
    this.profSpecDesc = profSpecDesc;
  }

  public int getProfId() {
    return profId;
  }

  public void setProfId(int profId) {
    this.profId = profId;
  }

  public int getActiveStatus() {
    return activeStatus;
  }

  public void setActiveStatus(int activeStatus) {
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
