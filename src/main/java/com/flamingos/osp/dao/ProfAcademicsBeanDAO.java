package com.flamingos.osp.dao;

import java.util.List;

import com.flamingos.osp.bean.OspProfAcademicsBean;
import com.flamingos.osp.exception.OspDaoException;

public interface ProfAcademicsBeanDAO {

  public void saveAcademics(List<OspProfAcademicsBean> profAcademicsBeanList)
      throws OspDaoException;

}
