package com.flamingos.osp.dao.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.flamingos.osp.bean.OspProfAcademicsBean;
import com.flamingos.osp.dao.ProfAcademicsBeanDao;
import com.flamingos.osp.exception.OspDaoException;

@Repository
public class ProfAcademicsBeanDaoImpl implements ProfAcademicsBeanDao {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Override
  public void saveAcademics(List<OspProfAcademicsBean> profAcademicsBeanList)
      throws OspDaoException {
    String query =
        "INSERT INTO OSP_PROF_ACADEMICS VALUES (:PROF_ACDMC_ID," + ":PROF_ACDMC_NAME,"
            + ":PROF_ACDMC_DESC," + ":PROF_ACDMC_UNIVERSITY, " + ":PROF_ACDMC_PASS_YEAR, "
            + ":PROF_ID, " + ":ACTIVE_STATUS, " + "" + ":CREATED_BY, " + ":CREATED_TS, "
            + "UPDATED_BY, " + "UPDATED_TS)";

    List<Object[]> inputList = new ArrayList<Object[]>();
    for (OspProfAcademicsBean academics : profAcademicsBeanList) {
      Object[] tmp =
          {academics.getProfAcdmcId(), academics.getProfAcdmcName(), academics.getProfAcdmcDesc(),
              academics.getProfAcdmcUniversity(), academics.getProfAcdmcPassYear(),
              academics.getProfId(), academics.getActiveStatus(), academics.getCreatedBy(),
              new Timestamp(new Date().getTime()), academics.getUpdatedBy(),
              academics.getUpdatedTs()};
      inputList.add(tmp);
    }
    try {
      jdbcTemplate.batchUpdate(query, inputList);
    } catch (EmptyResultDataAccessException exp) {
      throw new OspDaoException(exp);
    }

  }

}
