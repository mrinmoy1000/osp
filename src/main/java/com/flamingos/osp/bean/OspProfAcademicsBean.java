package com.flamingos.osp.bean;

import java.io.Serializable;
import java.util.Date;

import org.apache.log4j.Logger;

public class OspProfAcademicsBean implements Serializable {

  private static final Logger logger = Logger.getLogger(OspProfAcademicsBean.class);
  private static final long serialVersionUID = 1L;
  private Long profAcdmcId;
  private String profAcdmcName;
  private String profAcdmcDesc;
  private String profAcdmcUniversity;
  private String profAcdmcPassYear;
  private Long profId;
  private Integer activeStatus;
  private Date createdTs;
  private Date updatedTs;
  private String createdBy;
  private String updatedBy;

  /**
   * @return the profAcdmcId
   */
  public Long getProfAcdmcId() {
    return profAcdmcId;
  }

  /**
   * @param profAcdmcId the profAcdmcId to set
   */
  public void setProfAcdmcId(Long profAcdmcId) {
    this.profAcdmcId = profAcdmcId;
  }

  /**
   * @return the profAcdmcName
   */
  public String getProfAcdmcName() {
    return profAcdmcName;
  }

  /**
   * @param profAcdmcName the profAcdmcName to set
   */
  public void setProfAcdmcName(String profAcdmcName) {
    this.profAcdmcName = profAcdmcName;
  }

  /**
   * @return the profAcdmcDesc
   */
  public String getProfAcdmcDesc() {
    return profAcdmcDesc;
  }

  /**
   * @param profAcdmcDesc the profAcdmcDesc to set
   */
  public void setProfAcdmcDesc(String profAcdmcDesc) {
    this.profAcdmcDesc = profAcdmcDesc;
  }

  /**
   * @return the profAcdmcUniversity
   */
  public String getProfAcdmcUniversity() {
    return profAcdmcUniversity;
  }

  /**
   * @param profAcdmcUniversity the profAcdmcUniversity to set
   */
  public void setProfAcdmcUniversity(String profAcdmcUniversity) {
    this.profAcdmcUniversity = profAcdmcUniversity;
  }

  /**
   * @return the profAcdmcPassYear
   */
  public String getProfAcdmcPassYear() {
    return profAcdmcPassYear;
  }

  /**
   * @param profAcdmcPassYear the profAcdmcPassYear to set
   */
  public void setProfAcdmcPassYear(String profAcdmcPassYear) {
    this.profAcdmcPassYear = profAcdmcPassYear;
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
