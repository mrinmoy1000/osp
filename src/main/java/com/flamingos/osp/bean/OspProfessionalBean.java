package com.flamingos.osp.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

public class OspProfessionalBean implements Serializable {

  private static final Logger LOGGER = Logger.getLogger(OspProfessionalBean.class);
  private static final long serialVersionUID = 1L;

  private Long profId;
  private Long recordId;
  private String profFirstName;
  private String profMiddleName;
  private String profLastName;
  private String profEmpId;
  private Date profDob;
  private Integer profGender;
  private String profNationality;
  private String profPan;
  private Integer profMeritalStatus;
  private Date profMerriageAnniversary;
  private Integer dndActivatedFlag;
  private Byte[] profSignature;
  private String profPhotograph;
  private String profSubscId;
  private String profPublicId;
  private Double profFees;
  private String profRemark;
  private Integer status;
  private String actionTaken;

  private OspContactBean contact;
  private OspAddressBean address;
  private List<OspProfSpecializationBean> specializationList;
  private List<OspProfAcademicsBean> qualificationList;
  private List<OspExperienceBean> experienceList;
  private List<Integer> lstSubCategoryId;
  private List<OspProfAcheivementBean> acheivements;
  private List<OspProfRegMemNoBean> registeredMemNos;

  private Date createdTs;
  private Date updatedTs;
  private String createdBy;
  private String updatedBy;
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
   * @return the recordId
   */
  public Long getRecordId() {
    return recordId;
  }
  /**
   * @param recordId the recordId to set
   */
  public void setRecordId(Long recordId) {
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
  public Integer getProfGender() {
    return profGender;
  }
  /**
   * @param profGender the profGender to set
   */
  public void setProfGender(Integer profGender) {
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
  public Integer getProfMeritalStatus() {
    return profMeritalStatus;
  }
  /**
   * @param profMeritalStatus the profMeritalStatus to set
   */
  public void setProfMeritalStatus(Integer profMeritalStatus) {
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
  public Integer getDndActivatedFlag() {
    return dndActivatedFlag;
  }
  /**
   * @param dndActivatedFlag the dndActivatedFlag to set
   */
  public void setDndActivatedFlag(Integer dndActivatedFlag) {
    this.dndActivatedFlag = dndActivatedFlag;
  }
  /**
   * @return the profSignature
   */
  public Byte[] getProfSignature() {
    return profSignature;
  }
  /**
   * @param profSignature the profSignature to set
   */
  public void setProfSignature(Byte[] profSignature) {
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
  public Double getProfFees() {
    return profFees;
  }
  /**
   * @param profFees the profFees to set
   */
  public void setProfFees(Double profFees) {
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
  public Integer getStatus() {
    return status;
  }
  /**
   * @param status the status to set
   */
  public void setStatus(Integer status) {
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
    return lstSubCategoryId;
  }
  /**
   * @param lstSubCategoryId the lstSubCategoryId to set
   */
  public void setLstSubCategoryId(List<Integer> lstSubCategoryId) {
    this.lstSubCategoryId = lstSubCategoryId;
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
  public List<OspProfAcheivementBean> getAcheivements() {
    return acheivements;
  }
  public void setAcheivements(List<OspProfAcheivementBean> acheivements) {
    this.acheivements = acheivements;
  }
  public List<OspProfRegMemNoBean> getRegisteredMemNos() {
    return registeredMemNos;
  }
  public void setRegisteredMemNos(List<OspProfRegMemNoBean> registeredMemNos) {
    this.registeredMemNos = registeredMemNos;
  }
}