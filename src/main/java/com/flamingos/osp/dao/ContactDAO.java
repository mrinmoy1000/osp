package com.flamingos.osp.dao;

import com.flamingos.osp.bean.OspProfessionalBean;
import com.flamingos.osp.exception.OSPBusinessException;
import com.flamingos.osp.exception.OspDaoException;

public interface ContactDAO {

  public void saveContact(OspProfessionalBean professionalBean) throws OspDaoException;

}
