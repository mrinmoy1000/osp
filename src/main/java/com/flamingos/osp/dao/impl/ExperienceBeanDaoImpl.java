package com.flamingos.osp.dao.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.flamingos.osp.bean.OspExperienceBean;
import com.flamingos.osp.dao.ExperienceBeanDao;
import com.flamingos.osp.exception.OspDaoException;

public class ExperienceBeanDaoImpl implements ExperienceBeanDao {
  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Override
  public void addExperience(List<OspExperienceBean> experienceList) throws OspDaoException {


    String query =
        "INSERT INTO OSP_PROF_EXPERIENCE VALUES (:PROF_EXP_ID," + ":PROF_EXP_BEGIN_DT,"
            + ":PROF_EXP_END_DT," + ":PROF_EXP_DESC, " + ":PROF_ID, " + ":ACTIVE_STATUS, "
            + ":CREATED_BY, " + ":CREATED_TS, " + ":UPDATED_BY, " + ":UPDATED_TS)";

    List<Object[]> inputList = new ArrayList<Object[]>();
    for (OspExperienceBean experience : experienceList) {
      Object[] tmp =
          {experience.getProfExpId(), experience.getProfExpBeginDt(), experience.getProfExpEndDt(),
              experience.getProfExpDesc(), experience.getProfId(), experience.getActiveStatus(),
              experience.getCreatedBy(), new Timestamp(new Date().getTime()),
              experience.getUpdatedBy(), experience.getUpdatedTs()};
      inputList.add(tmp);
    }
    try {
      jdbcTemplate.batchUpdate(query, inputList);
    } catch (RuntimeException exp) {
      throw new OspDaoException(exp);
    }
  }

}
