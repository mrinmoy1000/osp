package com.flamingos.osp.bean;

import java.io.Serializable;
import java.util.Date;

import org.apache.log4j.Logger;

public class OspProfSpecializationBean implements Serializable {

  private static final Logger logger = Logger.getLogger(OspProfSpecializationBean.class);
  private static final long serialVersionUID = 1L;
  private Long profSpecId;
  private String profSpecName;
  private String profSpecDesc;
  private Long profId;
  private Integer activeStatus;
  private Date createdTs;
  private Date updatedTs;
  private String createdBy;
  private String updatedBy;

  /**
   * @return the profSpecId
   */
  public Long getProfSpecId() {
    return profSpecId;
  }

  /**
   * @param profSpecId the profSpecId to set
   */
  public void setProfSpecId(Long profSpecId) {
    this.profSpecId = profSpecId;
  }

  /**
   * @return the profSpecName
   */
  public String getProfSpecName() {
    return profSpecName;
  }

  /**
   * @param profSpecName the profSpecName to set
   */
  public void setProfSpecName(String profSpecName) {
    this.profSpecName = profSpecName;
  }

  /**
   * @return the profSpecDesc
   */
  public String getProfSpecDesc() {
    return profSpecDesc;
  }

  /**
   * @param profSpecDesc the profSpecDesc to set
   */
  public void setProfSpecDesc(String profSpecDesc) {
    this.profSpecDesc = profSpecDesc;
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
