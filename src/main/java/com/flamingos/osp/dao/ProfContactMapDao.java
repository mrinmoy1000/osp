package com.flamingos.osp.dao;

import com.flamingos.osp.bean.OspProfessionalBean;
import com.flamingos.osp.exception.OspDaoException;

public interface ProfContactMapDao {
	public void addContactMap(OspProfessionalBean professionalBean)
			throws OspDaoException;
}
