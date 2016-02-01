package com.flamingos.osp.dao;

import com.flamingos.osp.bean.OspProfessionalBean;
import com.flamingos.osp.exception.OspDaoException;

public interface ProfAcademicsBeanDAO {

  public void saveAcademics(OspProfessionalBean professional) throws OspDaoException;

}
