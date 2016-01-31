package com.flamingos.osp.dao;

import com.flamingos.osp.bean.OspProfessionalBean;
import com.flamingos.osp.exception.OspDaoException;

public interface ProfContactMapDAO {
  public void saveContactMap(OspProfessionalBean professionalBean) throws OspDaoException;
}
