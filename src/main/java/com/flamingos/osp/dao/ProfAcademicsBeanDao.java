package com.flamingos.osp.dao;

import java.util.List;

import com.flamingos.osp.bean.OspProfAcademicsBean;
import com.flamingos.osp.exception.OspDaoException;

public interface ProfAcademicsBeanDao {
  
  public void addAcademics(List<OspProfAcademicsBean> profAcademicsBeanList)
      throws OspDaoException;

}
