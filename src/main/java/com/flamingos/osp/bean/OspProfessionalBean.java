package com.flamingos.osp.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

public class OspProfessionalBean implements Serializable {

  private static final Logger logger = Logger.getLogger(OspProfessionalBean.class);
  private static final long serialVersionUID = 1L;

  private long profId;
  private long recordId;
  private String profFirstName;
  private String profMiddleName;
  private String profLastName;
  private String profEmpId;
  private Date profDob;
  private int profGender;
  private String profNationality;
  private String profPan;
  private int profMeritalStatus;
  private Date profMerriageAnniversary;
  private int dndActivatedFlag;
  private byte[] profSignature;
  private String profPhotograph;
  private String profSubscId;
  private String profPublicId;
  private double profFees;
  private String profRemark;
  private int status;
  private String actionTaken;

  private OspContactBean contact;
  private OspAddressBean address;
  private List<OspProfSpecializationBean> specializationList;
  private List<OspProfAcademicsBean> qualificationList;
  private List<OspExperienceBean> experienceList;
  private List<Integer> LstSubCategoryId;

  private Date createdTs;
  private Date updatedTs;
  private String createdBy;
  private String updatedBy;
  /**
   * @return the profId
   */
  public long getProfId() {
    return profId;
  }
  /**
   * @param profId the profId to set
   */
  public void setProfId(long profId) {
    this.profId = profId;
  }
  /**
   * @return the recordId
   */
  public long getRecordId() {
    return recordId;
  }
  /**
   * @param recordId the recordId to set
   */
  public void setRecordId(long recordId) {
    this.recordId = recordId;
  }
  /**
   * @return the profFirstName
   */
  public String getProfFirstName() {
    return profFirstName;
  }
  /**
   * @param profFirstName the profFirstName to set
   */
  public void setProfFirstName(String profFirstName) {
    this.profFirstName = profFirstName;
  }
  /**
   * @return the profMiddleName
   */
  public String getProfMiddleName() {
    return profMiddleName;
  }
  /**
   * @param profMiddleName the profMiddleName to set
   */
  public void setProfMiddleName(String profMiddleName) {
    this.profMiddleName = profMiddleName;
  }
  /**
   * @return the profLastName
   */
  public String getProfLastName() {
    return profLastName;
  }
  /**
   * @param profLastName the profLastName to set
   */
  public void setProfLastName(String profLastName) {
    this.profLastName = profLastName;
  }
  /**
   * @return the profEmpId
   */
  public String getProfEmpId() {
    return profEmpId;
  }
  /**
   * @param profEmpId the profEmpId to set
   */
  public void setProfEmpId(String profEmpId) {
    this.profEmpId = profEmpId;
  }
  /**
   * @return the profDob
   */
  public Date getProfDob() {
    return profDob;
  }
  /**
   * @param profDob the profDob to set
   */
  public void setProfDob(Date profDob) {
    this.profDob = profDob;
  }
  /**
   * @return the profGender
   */
  public int getProfGender() {
    return profGender;
  }
  /**
   * @param profGender the profGender to set
   */
  public void setProfGender(int profGender) {
    this.profGender = profGender;
  }
  /**
   * @return the profNationality
   */
  public String getProfNationality() {
    return profNationality;
  }
  /**
   * @param profNationality the profNationality to set
   */
  public void setProfNationality(String profNationality) {
    this.profNationality = profNationality;
  }
  /**
   * @return the profPan
   */
  public String getProfPan() {
    return profPan;
  }
  /**
   * @param profPan the profPan to set
   */
  public void setProfPan(String profPan) {
    this.profPan = profPan;
  }
  /**
   * @return the profMeritalStatus
   */
  public int getProfMeritalStatus() {
    return profMeritalStatus;
  }
  /**
   * @param profMeritalStatus the profMeritalStatus to set
   */
  public void setProfMeritalStatus(int profMeritalStatus) {
    this.profMeritalStatus = profMeritalStatus;
  }
  /**
   * @return the profMerriageAnniversary
   */
  public Date getProfMerriageAnniversary() {
    return profMerriageAnniversary;
  }
  /**
   * @param profMerriageAnniversary the profMerriageAnniversary to set
   */
  public void setProfMerriageAnniversary(Date profMerriageAnniversary) {
    this.profMerriageAnniversary = profMerriageAnniversary;
  }
  /**
   * @return the dndActivatedFlag
   */
  public int getDndActivatedFlag() {
    return dndActivatedFlag;
  }
  /**
   * @param dndActivatedFlag the dndActivatedFlag to set
   */
  public void setDndActivatedFlag(int dndActivatedFlag) {
    this.dndActivatedFlag = dndActivatedFlag;
  }
  /**
   * @return the profSignature
   */
  public byte[] getProfSignature() {
    return profSignature;
  }
  /**
   * @param profSignature the profSignature to set
   */
  public void setProfSignature(byte[] profSignature) {
    this.profSignature = profSignature;
  }
  /**
   * @return the profPhotograph
   */
  public String getProfPhotograph() {
    return profPhotograph;
  }
  /**
   * @param profPhotograph the profPhotograph to set
   */
  public void setProfPhotograph(String profPhotograph) {
    this.profPhotograph = profPhotograph;
  }
  /**
   * @return the profSubscId
   */
  public String getProfSubscId() {
    return profSubscId;
  }
  /**
   * @param profSubscId the profSubscId to set
   */
  public void setProfSubscId(String profSubscId) {
    this.profSubscId = profSubscId;
  }
  /**
   * @return the profPublicId
   */
  public String getProfPublicId() {
    return profPublicId;
  }
  /**
   * @param profPublicId the profPublicId to set
   */
  public void setProfPublicId(String profPublicId) {
    this.profPublicId = profPublicId;
  }
  /**
   * @return the profFees
   */
  public double getProfFees() {
    return profFees;
  }
  /**
   * @param profFees the profFees to set
   */
  public void setProfFees(double profFees) {
    this.profFees = profFees;
  }
  /**
   * @return the profRemark
   */
  public String getProfRemark() {
    return profRemark;
  }
  /**
   * @param profRemark the profRemark to set
   */
  public void setProfRemark(String profRemark) {
    this.profRemark = profRemark;
  }
  /**
   * @return the status
   */
  public int getStatus() {
    return status;
  }
  /**
   * @param status the status to set
   */
  public void setStatus(int status) {
    this.status = status;
  }
  /**
   * @return the actionTaken
   */
  public String getActionTaken() {
    return actionTaken;
  }
  /**
   * @param actionTaken the actionTaken to set
   */
  public void setActionTaken(String actionTaken) {
    this.actionTaken = actionTaken;
  }
  /**
   * @return the contact
   */
  public OspContactBean getContact() {
    return contact;
  }
  /**
   * @param contact the contact to set
   */
  public void setContact(OspContactBean contact) {
    this.contact = contact;
  }
  /**
   * @return the address
   */
  public OspAddressBean getAddress() {
    return address;
  }
  /**
   * @param address the address to set
   */
  public void setAddress(OspAddressBean address) {
    this.address = address;
  }
  /**
   * @return the specializationList
   */
  public List<OspProfSpecializationBean> getSpecializationList() {
    return specializationList;
  }
  /**
   * @param specializationList the specializationList to set
   */
  public void setSpecializationList(List<OspProfSpecializationBean> specializationList) {
    this.specializationList = specializationList;
  }
  /**
   * @return the qualificationList
   */
  public List<OspProfAcademicsBean> getQualificationList() {
    return qualificationList;
  }
  /**
   * @param qualificationList the qualificationList to set
   */
  public void setQualificationList(List<OspProfAcademicsBean> qualificationList) {
    this.qualificationList = qualificationList;
  }
  /**
   * @return the experienceList
   */
  public List<OspExperienceBean> getExperienceList() {
    return experienceList;
  }
  /**
   * @param experienceList the experienceList to set
   */
  public void setExperienceList(List<OspExperienceBean> experienceList) {
    this.experienceList = experienceList;
  }
  /**
   * @return the lstSubCategoryId
   */
  public List<Integer> getLstSubCategoryId() {
    return LstSubCategoryId;
  }
  /**
   * @param lstSubCategoryId the lstSubCategoryId to set
   */
  public void setLstSubCategoryId(List<Integer> lstSubCategoryId) {
    LstSubCategoryId = lstSubCategoryId;
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