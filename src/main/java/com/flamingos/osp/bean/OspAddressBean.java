package com.flamingos.osp.bean;

import java.util.Date;

import org.apache.log4j.Logger;

public class OspAddressBean {
	
	private static final Logger logger = Logger.getLogger(OspAddressBean.class);
	private static final long serialVersionUID = 1L;
	
	public int address_id;
	public int location_id;
	public String other_area;
	public String line_1;
	public String line_2;
	public String pin_code;
	public int active_status;
	public Date created_ts;
	public Date updated_ts;
	public String created_by;
	public String updated_by;

}
