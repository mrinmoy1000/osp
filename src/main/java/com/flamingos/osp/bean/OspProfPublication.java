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
public class OspProfPublication implements Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private Long profPublicationId;
  private String profPublicationName;
  private Long profId;
  private boolean activeStatus;
  private List<OspPublicationDetails> lstPublicationDetails;
  private String createdBy;
  private Date createdTs;
  private String updatedBy;
  private Date updatedTs;

  /**
   * @return the profPublicationId
   */
  public Long getProfPublicationId() {
    return profPublicationId;
  }

  /**
   * @param profPublicationId the profPublicationId to set
   */
  public void setProfPublicationId(Long profPublicationId) {
    this.profPublicationId = profPublicationId;
  }

  /**
   * @return the profPublicationName
   */
  public String getProfPublicationName() {
    return profPublicationName;
  }

  /**
   * @param profPublicationName the profPublicationName to set
   */
  public void setProfPublicationName(String profPublicationName) {
    this.profPublicationName = profPublicationName;
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
   * @return the lstPublicationDetails
   */
  public List<OspPublicationDetails> getLstPublicationDetails() {
    return lstPublicationDetails;
  }

  /**
   * @param lstPublicationDetails the lstPublicationDetails to set
   */
  public void setLstPublicationDetails(List<OspPublicationDetails> lstPublicationDetails) {
    this.lstPublicationDetails = lstPublicationDetails;
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
