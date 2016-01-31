package com.flamingos.osp.dao;



import java.util.List;

import com.flamingos.osp.bean.OspProfSpecializationBean;
import com.flamingos.osp.exception.OspDaoException;

public interface ProfSpecializationAO {

  public void saveSpecializations(List<OspProfSpecializationBean> specializationBeanList)
      throws OspDaoException;
}
