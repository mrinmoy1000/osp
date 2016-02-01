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
public class OspPublicationDetails implements Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private Long publicationDescId;
  private Long profPublicationId;
  private String publicationDesc;
  private String createdBy;
  private Date createdTs;
  private String updatedBy;
  private Date updatedTs;

  /**
   * @return the publicationDescId
   */
  public Long getPublicationDescId() {
    return publicationDescId;
  }

  /**
   * @param publicationDescId the publicationDescId to set
   */
  public void setPublicationDescId(Long publicationDescId) {
    this.publicationDescId = publicationDescId;
  }

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
   * @return the publicationDesc
   */
  public String getPublicationDesc() {
    return publicationDesc;
  }

  /**
   * @param publicationDesc the publicationDesc to set
   */
  public void setPublicationDesc(String publicationDesc) {
    this.publicationDesc = publicationDesc;
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
