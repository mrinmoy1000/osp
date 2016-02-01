/**
 * 
 */
package com.flamingos.osp.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Mrinmoy
 *
 */
public class OspPresentationDesc implements Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private Long presentationDescId;
  private Long profPresentationId;
  private String presentationDesc;
  private String createdBy;
  private Date createdTs;
  private String updatedBy;
  private Date updatedTs;

  /**
   * @return the presentationDescId
   */
  public Long getPresentationDescId() {
    return presentationDescId;
  }

  /**
   * @param presentationDescId the presentationDescId to set
   */
  public void setPresentationDescId(Long presentationDescId) {
    this.presentationDescId = presentationDescId;
  }

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
   * @return the presentationDesc
   */
  public String getPresentationDesc() {
    return presentationDesc;
  }

  /**
   * @param presentationDesc the presentationDesc to set
   */
  public void setPresentationDesc(String presentationDesc) {
    this.presentationDesc = presentationDesc;
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
