/**
 * 
 */
package com.flamingos.osp.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author Mrinmoy
 *
 */
public class OspProfPresentation implements Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private Long profPresentationId;
  private String profPresentationName;
  private Long profId;
  private boolean activeStatus;
  private List<OspPresentationDesc> lstPresentationDesc;
  private String createdBy;
  private Date createdTs;
  private String updatedBy;
  private Date updatedTs;

  /**
   * @return the profPresentationId
   */
  public Long getProfPresentationId() {
    return profPresentationId;
  }

  /**
   * @param profPresentationId the profPresentationId to set
   */
  public void setProfPresentationId(Long profPresentationId) {
    this.profPresentationId = profPresentationId;
  }

  /**
   * @return the profPresentationName
   */
  public String getProfPresentationName() {
    return profPresentationName;
  }

  /**
   * @param profPresentationName the profPresentationName to set
   */
  public void setProfPresentationName(String profPresentationName) {
    this.profPresentationName = profPresentationName;
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
  public boolean isActiveStatus() {
    return activeStatus;
  }

  /**
   * @param activeStatus the activeStatus to set
   */
  public void setActiveStatus(boolean activeStatus) {
    this.activeStatus = activeStatus;
  }

  /**
   * @return the lstPresentationDesc
   */
  public List<OspPresentationDesc> getLstPresentationDesc() {
    return lstPresentationDesc;
  }

  /**
   * @param lstPresentationDesc the lstPresentationDesc to set
   */
  public void setLstPresentationDesc(List<OspPresentationDesc> lstPresentationDesc) {
    this.lstPresentationDesc = lstPresentationDesc;
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

}
