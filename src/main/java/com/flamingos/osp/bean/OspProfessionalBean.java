package com.flamingos.osp.bean;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

public class OspProfessionalBean {
	
	private static final Logger logger = Logger.getLogger(OspProfessionalBean.class);
	private static final long serialVersionUID = 1L;
	
	public int prof_id;
	public int record_id;
	
	public String prof_first_name;
	public String prof_middle_name;
	public String prof_last_name;
	public String prof_emp_id;
	public String prof_dob;
	public String prof_gender;
	public String prof_nationality;
	public String prof_pan;
	public String prof_merital_status;
	public String prof_merriage_anniversary;
	public int dnd_activated_flag;
	public String prof_signature;
	public String prof_photograph;
	public String prof_subsc_id;
	public String prof_public_id;
	public double prof_fees;
	public String prof_remark;
	public int status;
	
	public OspContactBean contact;	
	public OspAddressBean address;
	public List<OspProfSpecializationBean> specializationList;
	public List<OspProfAcademicsBean> qualificationList;
	public List<OspExperienceBean> experienceList;
	
	public Date created_ts;
	public Date updated_ts;
	public String created_by;
	public String updated_by;
	
	public int getProf_id() {
		return prof_id;
	}
	public void setProf_id(int prof_id) {
		this.prof_id = prof_id;
	}
	public int getRecord_id() {
		return record_id;
	}
	public void setRecord_id(int record_id) {
		this.record_id = record_id;
	}
	public String getProf_first_name() {
		return prof_first_name;
	}
	public void setProf_first_name(String prof_first_name) {
		this.prof_first_name = prof_first_name;
	}
	public String getProf_middle_name() {
		return prof_middle_name;
	}
	public void setProf_middle_name(String prof_middle_name) {
		this.prof_middle_name = prof_middle_name;
	}
	public String getProf_last_name() {
		return prof_last_name;
	}
	public void setProf_last_name(String prof_last_name) {
		this.prof_last_name = prof_last_name;
	}
	public String getProf_emp_id() {
		return prof_emp_id;
	}
	public void setProf_emp_id(String prof_emp_id) {
		this.prof_emp_id = prof_emp_id;
	}
	public String getProf_dob() {
		return prof_dob;
	}
	public void setProf_dob(String prof_dob) {
		this.prof_dob = prof_dob;
	}
	public String getProf_gender() {
		return prof_gender;
	}
	public void setProf_gender(String prof_gender) {
		this.prof_gender = prof_gender;
	}
	public String getProf_nationality() {
		return prof_nationality;
	}
	public void setProf_nationality(String prof_nationality) {
		this.prof_nationality = prof_nationality;
	}
	public String getProf_pan() {
		return prof_pan;
	}
	public void setProf_pan(String prof_pan) {
		this.prof_pan = prof_pan;
	}
	public String getProf_merital_status() {
		return prof_merital_status;
	}
	public void setProf_merital_status(String prof_merital_status) {
		this.prof_merital_status = prof_merital_status;
	}
	public String getProf_merriage_anniversary() {
		return prof_merriage_anniversary;
	}
	public void setProf_merriage_anniversary(String prof_merriage_anniversary) {
		this.prof_merriage_anniversary = prof_merriage_anniversary;
	}
	public int getDnd_activated_flag() {
		return dnd_activated_flag;
	}
	public void setDnd_activated_flag(int dnd_activated_flag) {
		this.dnd_activated_flag = dnd_activated_flag;
	}
	public String getProf_signature() {
		return prof_signature;
	}
	public void setProf_signature(String prof_signature) {
		this.prof_signature = prof_signature;
	}
	public String getProf_photograph() {
		return prof_photograph;
	}
	public void setProf_photograph(String prof_photograph) {
		this.prof_photograph = prof_photograph;
	}
	public String getProf_subsc_id() {
		return prof_subsc_id;
	}
	public void setProf_subsc_id(String prof_subsc_id) {
		this.prof_subsc_id = prof_subsc_id;
	}
	public String getProf_public_id() {
		return prof_public_id;
	}
	public void setProf_public_id(String prof_public_id) {
		this.prof_public_id = prof_public_id;
	}
	public double getProf_fees() {
		return prof_fees;
	}
	public void setProf_fees(double prof_fees) {
		this.prof_fees = prof_fees;
	}
	public String getProf_remark() {
		return prof_remark;
	}
	public void setProf_remark(String prof_remark) {
		this.prof_remark = prof_remark;
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
	public void setQualificationList(List<OspProfAcademicsBean> qualificationList) {
		this.qualificationList = qualificationList;
	}
	public List<OspExperienceBean> getExperienceList() {
		return experienceList;
	}
	public void setExperienceList(List<OspExperienceBean> experienceList) {
		this.experienceList = experienceList;
	}
	public Date getCreated_ts() {
		return created_ts;
	}
	public void setCreated_ts(Date created_ts) {
		this.created_ts = created_ts;
	}
	public Date getUpdated_ts() {
		return updated_ts;
	}
	public void setUpdated_ts(Date updated_ts) {
		this.updated_ts = updated_ts;
	}
	public String getCreated_by() {
		return created_by;
	}
	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}
	public String getUpdated_by() {
		return updated_by;
	}
	public void setUpdated_by(String updated_by) {
		this.updated_by = updated_by;
	}	
	
	
	

}
