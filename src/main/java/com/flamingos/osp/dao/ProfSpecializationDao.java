package com.flamingos.osp.dao;



import java.util.List;

import com.flamingos.osp.bean.OspProfSpecializationBean;
import com.flamingos.osp.exception.OspDaoException;

public interface ProfSpecializationDao {

  public void saveSpecializations(List<OspProfSpecializationBean> specializationBeanList)
      throws OspDaoException;
}
