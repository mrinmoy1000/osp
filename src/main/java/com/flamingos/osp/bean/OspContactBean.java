package com.flamingos.osp.bean;

import java.util.Date;

import org.apache.log4j.Logger;

public class OspContactBean {
	
	private static final Logger logger = Logger.getLogger(OspContactBean.class);
	private static final long serialVersionUID = 1L;
	public int contact_id;
	public int contact_type;
	public String contact_phone;
	public String contact_email;
	public int active_status;
	public Date created_ts;
	public Date updated_ts;
	public String created_by;
	public String updated_by;
	
	
	
	
	

}
