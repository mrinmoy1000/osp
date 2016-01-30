package com.flamingos.osp.dao.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.flamingos.osp.bean.OspProfSpecializationBean;
import com.flamingos.osp.dao.ProfSpecializationDao;
import com.flamingos.osp.exception.OspDaoException;

@Repository
public class ProfSpecializationDaoImpl implements ProfSpecializationDao {
  @Autowired
  private JdbcTemplate jdbcTemplate;


  @Override
  public void saveSpecializations(List<OspProfSpecializationBean> specializationBeanList)
      throws OspDaoException {

    String query =
        "INSERT INTO OSP_PROF_SPECIALIZATIONS VALUES (:PROF_SPEC_ID," + ":PROF_SPEC_NAME,"
            + ":PROF_SPEC_DESC," + ":PROF_ID, " + ":ACTIVE_STATUS, " + ":CREATED_BY, "
            + ":CREATED_TS, " + "" + ":UPDATED_BY, " + ":UPDATED_TS)";

    List<Object[]> inputList = new ArrayList<Object[]>();
    for (OspProfSpecializationBean specialization : specializationBeanList) {
      Object[] tmp =
          {specialization.getProfSpecId(), specialization.getProfSpecName(),
              specialization.getProfSpecDesc(), specialization.getProfId(),
              specialization.getActiveStatus(), specialization.getCreatedBy(),
              new Timestamp(new Date().getTime()), specialization.getUpdatedBy(),
              specialization.getUpdatedTs()};
      inputList.add(tmp);
    }
    try {
      jdbcTemplate.batchUpdate(query, inputList);
    } catch (EmptyResultDataAccessException exp) {
      throw new OspDaoException(exp);
    }
  }

}
