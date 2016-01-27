package com.flamingos.osp.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

public class OspProfessionalBean implements Serializable {

	private static final Logger logger = Logger
			.getLogger(OspProfessionalBean.class);
	private static final long serialVersionUID = 1L;

	public int profId;
	public int recordId;
	public String profFirstName;
	public String profMiddleName;
	public String profLastName;
	public String profEmpId;
	public String profDob;
	public String profGender;
	public String profNationality;
	public String profPan;
	public String profMeritalStatus;
	public String profMerriageAnniversary;
	public int dndActivatedFlag;
	public String profSignature;
	public String profPhotograph;
	public String profSubscId;
	public String profPublicId;
	public double profFees;
	public String profRemark;
	public int status;

	public OspContactBean contact;
	public OspAddressBean address;
	public List<OspProfSpecializationBean> specializationList;
	public List<OspProfAcademicsBean> qualificationList;
	public List<OspExperienceBean> experienceList;

	public Date createdTs;
	public Date updatedTs;
	public String createdBy;
	public String updatedBy;

	public int getProfId() {
		return profId;
	}

	public void setProfId(int profId) {
		this.profId = profId;
	}

	public int getRecordId() {
		return recordId;
	}

	public void setRecord_id(int recordId) {
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

	public String getProfDob() {
		return profDob;
	}

	public void setProfDob(String profDob) {
		this.profDob = profDob;
	}

	public String getProfGender() {
		return profGender;
	}

	public void setProfGender(String profGender) {
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

	public String getProfMeritalStatus() {
		return profMeritalStatus;
	}

	public void setProfMeritalStatus(String profMeritalStatus) {
		this.profMeritalStatus = profMeritalStatus;
	}

	public String getProfMerriageAnniversary() {
		return profMerriageAnniversary;
	}

	public void setProfMerriageAnniversary(String profMerriageAnniversary) {
		this.profMerriageAnniversary = profMerriageAnniversary;
	}

	public int getDndActivatedFlag() {
		return dndActivatedFlag;
	}

	public void setDndActivatedFlag(int dndActivatedFlag) {
		this.dndActivatedFlag = dndActivatedFlag;
	}

	public String getProfSignature() {
		return profSignature;
	}

	public void setProfSignature(String profSignature) {
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

	public void setSpecializationList(
			List<OspProfSpecializationBean> specializationList) {
		this.specializationList = specializationList;
	}

	public List<OspProfAcademicsBean> getQualificationList() {
		return qualificationList;
	}

	public void setQualificationList(
			List<OspProfAcademicsBean> qualificationList) {
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
