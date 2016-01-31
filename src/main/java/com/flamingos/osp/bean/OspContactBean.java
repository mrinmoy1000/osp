package com.flamingos.osp.bean;

import java.io.Serializable;
import java.util.Date;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class OspContactBean implements Serializable {

  private static final Logger logger = Logger.getLogger(OspContactBean.class);
  private static final long serialVersionUID = 1L;
  public int contactId;
  public int contactType;
  public String contactPhone;
  public String contactEmail;
  public int activeStatus;
  public Date createdTs;
  public Date updatedTs;
  public String createdBy;
  public String updatedBy;

  public int getContactId() {
    return contactId;
  }

  public void setContactId(int contactId) {
    this.contactId = contactId;
  }

  public int getContactType() {
    return contactType;
  }

  public void setContactType(int contactType) {
    this.contactType = contactType;
  }

  public String getContactPhone() {
    return contactPhone;
  }

  public void setContactPhone(String contactPhone) {
    this.contactPhone = contactPhone;
  }

  public String getContactEmail() {
    return contactEmail;
  }

  public void setContactEmail(String contactEmail) {
    this.contactEmail = contactEmail;
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
