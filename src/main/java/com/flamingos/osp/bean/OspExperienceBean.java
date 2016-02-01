package com.flamingos.osp.bean;

import java.io.Serializable;
import java.util.Date;

import org.apache.log4j.Logger;

public class OspExperienceBean implements Serializable {

  private static final Logger logger = Logger.getLogger(OspExperienceBean.class);
  private static final long serialVersionUID = 1L;
  private Long profExpId;
  private Date profExpBeginDt;
  private Date profExpEndDt;
  private String profExpDesc;
  private Long profId;
  private Integer activeStatus;
  private Date createdTs;
  private Date updatedTs;
  private String createdBy;
  private String updatedBy;

  /**
   * @return the profExpId
   */
  public Long getProfExpId() {
    return profExpId;
  }

  /**
   * @param profExpId the profExpId to set
   */
  public void setProfExpId(Long profExpId) {
    this.profExpId = profExpId;
  }

  /**
   * @return the profExpBeginDt
   */
  public Date getProfExpBeginDt() {
    return profExpBeginDt;
  }

  /**
   * @param profExpBeginDt the profExpBeginDt to set
   */
  public void setProfExpBeginDt(Date profExpBeginDt) {
    this.profExpBeginDt = profExpBeginDt;
  }

  /**
   * @return the profExpEndDt
   */
  public Date getProfExpEndDt() {
    return profExpEndDt;
  }

  /**
   * @param profExpEndDt the profExpEndDt to set
   */
  public void setProfExpEndDt(Date profExpEndDt) {
    this.profExpEndDt = profExpEndDt;
  }

  /**
   * @return the profExpDesc
   */
  public String getProfExpDesc() {
    return profExpDesc;
  }

  /**
   * @param profExpDesc the profExpDesc to set
   */
  public void setProfExpDesc(String profExpDesc) {
    this.profExpDesc = profExpDesc;
  }

  /**
   * @return the profId
   */
  public Long getProfId() {
    return profId;
  }

  /**
   * @param profId the profId to set
   */
  public void setProfId(Long profId) {
    this.profId = profId;
  }

  /**
   * @return the activeStatus
   */
  public Integer getActiveStatus() {
    return activeStatus;
  }

  /**
   * @param activeStatus the activeStatus to set
   */
  public void setActiveStatus(Integer activeStatus) {
    this.activeStatus = activeStatus;
  }

  /**
   * @return the createdTs
   */
  public Date getCreatedTs() {
    return createdTs;
  }

  /**
   * @param createdTs the createdTs to set
   */
  public void setCreatedTs(Date createdTs) {
    this.createdTs = createdTs;
  }

  /**
   * @return the updatedTs
   */
  public Date getUpdatedTs() {
    return updatedTs;
  }

  /**
   * @param updatedTs the updatedTs to set
   */
  public void setUpdatedTs(Date updatedTs) {
    this.updatedTs = updatedTs;
  }

  /**
   * @return the createdBy
   */
  public String getCreatedBy() {
    return createdBy;
  }

  /**
   * @param createdBy the createdBy to set
   */
  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  /**
   * @return the updatedBy
   */
  public String getUpdatedBy() {
    return updatedBy;
  }

  /**
   * @param updatedBy the updatedBy to set
   */
  public void setUpdatedBy(String updatedBy) {
    this.updatedBy = updatedBy;
  }
}
