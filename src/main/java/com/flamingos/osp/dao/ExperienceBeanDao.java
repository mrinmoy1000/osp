package com.flamingos.osp.dao;

import java.util.List;

import com.flamingos.osp.bean.OspExperienceBean;
import com.flamingos.osp.exception.OSPBusinessException;
import com.flamingos.osp.exception.OspDaoException;

public interface ExperienceBeanDao {

  public void saveExperience(List<OspExperienceBean> experienceList) throws OspDaoException;

}
