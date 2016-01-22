package com.flamingos.osp.bean;

import java.util.Date;

import org.apache.log4j.Logger;

public class OspExperienceBean {
	
	private static final Logger logger = Logger.getLogger(OspExperienceBean.class);
	private static final long serialVersionUID = 1L;
	public int prof_exp_id;
	public Date prof_exp_begin_dt;
	public Date prof_exp_end_dt;
	public String prof_exp_desc;
	public int prof_id;
	public int active_status;		
	public Date created_ts;
	public Date updated_ts;
	public String created_by;
	public String updated_by;
	
	
	

}
