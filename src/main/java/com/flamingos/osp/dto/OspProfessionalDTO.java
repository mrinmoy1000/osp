package com.flamingos.osp.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.flamingos.osp.bean.OspAddressBean;
import com.flamingos.osp.bean.OspContactBean;
import com.flamingos.osp.bean.OspExperienceBean;
import com.flamingos.osp.bean.OspProfAcademicsBean;
import com.flamingos.osp.bean.OspProfSpecializationBean;

@JsonInclude(Include.NON_NULL)
public class OspProfessionalDTO {

  private int profId;
  private int recordId;
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

  private String returnStatus;

  private String returnMessage;

  public String getReturnStatus() {
    return returnStatus;
  }

  public void setReturnStatus(String returnStatus) {
    this.returnStatus = returnStatus;
  }

  public String getReturnMessage() {
    return returnMessage;
  }

  public void setReturnMessage(String returnMessage) {
    this.returnMessage = returnMessage;
  }

  private OspContactBean contact;
  private OspAddressBean address;
  private List<OspProfSpecializationBean> specializationList;
  private List<OspProfAcademicsBean> qualificationList;
  private List<OspExperienceBean> experienceList;

  private Date createdTs;
  private Date updatedTs;
  private String createdBy;
  private String updatedBy;

  public int getProfId() {
    return profId;
  }

  public void setProfId(int profId) {
    this.profId = profId;
  }

  public int getRecordId() {
    return recordId;
  }

  public void setRecordId(int recordId) {
    this.recordId = recordId;
  }

  public String getProfFirstName() {
    return profFirstName;
  }

  public void setProfFirstName(String profFirstName) {
    this.profFirstName = profFirstName;
  }

  public String getProfMiddleName() {
    return profMiddleName;
  }

  public void setProfMiddleName(String profMiddleName) {
    this.profMiddleName = profMiddleName;
  }

  public String getProfLastName() {
    return profLastName;
  }

  public void setProfLastName(String profLastName) {
    this.profLastName = profLastName;
  }

  public String getProfEmpId() {
    return profEmpId;
  }

  public void setProfEmpId(String profEmpId) {
    this.profEmpId = profEmpId;
  }

  public Date getProfDob() {
    return profDob;
  }

  public void setProfDob(Date profDob) {
    this.profDob = profDob;
  }

  public int getProfGender() {
    return profGender;
  }

  public void setProfGender(int profGender) {
    this.profGender = profGender;
  }

  public String getProfNationality() {
    return profNationality;
  }

  public void setProfNationality(String profNationality) {
    this.profNationality = profNationality;
  }

  public String getProfPan() {
    return profPan;
  }

  public void setProfPan(String profPan) {
    this.profPan = profPan;
  }

  public int getProfMeritalStatus() {
    return profMeritalStatus;
  }

  public void setProfMeritalStatus(int profMeritalStatus) {
    this.profMeritalStatus = profMeritalStatus;
  }

  public Date getProfMerriageAnniversary() {
    return profMerriageAnniversary;
  }

  public void setProfMerriageAnniversary(Date profMerriageAnniversary) {
    this.profMerriageAnniversary = profMerriageAnniversary;
  }

  public int getDndActivatedFlag() {
    return dndActivatedFlag;
  }

  public void setDndActivatedFlag(int dndActivatedFlag) {
    this.dndActivatedFlag = dndActivatedFlag;
  }

  public byte[] getProfSignature() {
    return profSignature;
  }

  public void setProfSignature(byte[] profSignature) {
    this.profSignature = profSignature;
  }

  public String getProfPhotograph() {
    return profPhotograph;
  }

  public void setProfPhotograph(String profPhotograph) {
    this.profPhotograph = profPhotograph;
  }

  public String getProfSubscId() {
    return profSubscId;
  }

  public void setProfSubscId(String profSubscId) {
    this.profSubscId = profSubscId;
  }

  public String getProfPublicId() {
    return profPublicId;
  }

  public void setProfPublicId(String profPublicId) {
    this.profPublicId = profPublicId;
  }

  public double getProfFees() {
    return profFees;
  }

  public void setProfFees(double profFees) {
    this.profFees = profFees;
  }

  public String getProfRemark() {
    return profRemark;
  }

  public void setProfRemark(String profRemark) {
    this.profRemark = profRemark;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public OspContactBean getContact() {
    return contact;
  }

  public void setContact(OspContactBean contact) {
    this.contact = contact;
  }

  public OspAddressBean getAddress() {
    return address;
  }

  public void setAddress(OspAddressBean address) {
    this.address = address;
  }

  public List<OspProfSpecializationBean> getSpecializationList() {
    return specializationList;
  }

  public void setSpecializationList(List<OspProfSpecializationBean> specializationList) {
    this.specializationList = specializationList;
  }

  public List<OspProfAcademicsBean> getQualificationList() {
    return qualificationList;
  }

  public void setQualificationList(List<OspProfAcademicsBean> qualificationList) {
    this.qualificationList = qualificationList;
  }

  public List<OspExperienceBean> getExperienceList() {
    return experienceList;
  }

  public void setExperienceList(List<OspExperienceBean> experienceList) {
    this.experienceList = experienceList;
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
