package com.flamingos.osp.bean;

import java.io.Serializable;
import java.util.Date;

import org.apache.log4j.Logger;

public class OspExperienceBean implements Serializable {

  private static final Logger logger = Logger.getLogger(OspExperienceBean.class);
  private static final long serialVersionUID = 1L;
  private int profExpId;
  private Date profExpBeginDt;
  private Date profExpEndDt;
  private String profExpDesc;
  private int profId;
  private int activeStatus;
  private Date createdTs;
  private Date updatedTs;
  private String createdBy;
  private String updatedBy;

  public int getProfExpId() {
    return profExpId;
  }

  public void setProfExpId(int profExpId) {
    this.profExpId = profExpId;
  }

  public Date getProfExpBeginDt() {
    return profExpBeginDt;
  }

  public void setProfExpBeginDt(Date profExpBeginDt) {
    this.profExpBeginDt = profExpBeginDt;
  }

  public Date getProfExpEndDt() {
    return profExpEndDt;
  }

  public void setProfExpEndDt(Date profExpEndDt) {
    this.profExpEndDt = profExpEndDt;
  }

  public String getProfExpDesc() {
    return profExpDesc;
  }

  public void setProfExpDesc(String profExpDesc) {
    this.profExpDesc = profExpDesc;
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
