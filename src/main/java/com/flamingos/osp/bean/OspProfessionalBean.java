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
	

}
