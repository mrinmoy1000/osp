package com.flamingos.osp.dao;


import com.flamingos.osp.bean.OspProfessionalBean;
import com.flamingos.osp.exception.OspDaoException;

public interface AddressDao {
	
	public int addAddress(OspProfessionalBean professionalBean)
			throws OspDaoException;

}
