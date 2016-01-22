package com.flamingos.osp.bean;

import java.util.Date;

import org.apache.log4j.Logger;

public class OspProfSpecializationBean {
	
	private static final Logger logger = Logger.getLogger(OspProfSpecializationBean.class);
	private static final long serialVersionUID = 1L;
	public int prof_spec_id;
	public String prof_spec_name;
	public String prof_spec_desc;
	public int prof_id;
	public int active_status;
	public Date created_ts;
	public Date updated_ts;
	public String created_by;
	public String updated_by;

}
