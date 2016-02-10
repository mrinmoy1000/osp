package com.flamingos.osp.dao;



import com.flamingos.osp.bean.OspProfessionalBean;
import com.flamingos.osp.exception.OspDaoException;

public interface ProfSpecializationDAO {

  public void saveSpecializations(OspProfessionalBean professional) throws OspDaoException;
}
