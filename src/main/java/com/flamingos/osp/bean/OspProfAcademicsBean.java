package com.flamingos.osp.bean;

import java.util.Date;

import org.apache.log4j.Logger;

public class OspProfAcademicsBean {
	
	private static final Logger logger = Logger.getLogger(OspProfAcademicsBean.class);
	private static final long serialVersionUID = 1L;
	public int prof_acdmc_id;
	public String prof_acdmc_name;
	public String prof_acdmc_desc;
	public String prof_acdmc_university;
	public String prof_acdmc_pass_year;
	public int prof_id;
	public String active_status;	
	public Date created_ts;
	public Date updated_ts;
	public String created_by;
	public String updated_by;	
	
}
