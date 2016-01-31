package com.flamingos.osp.dao;

import com.flamingos.osp.bean.OspProfessionalBean;
import com.flamingos.osp.exception.OspDaoException;

public interface ExperienceBeanDAO {

  public void saveExperience(OspProfessionalBean professional) throws OspDaoException;

}
