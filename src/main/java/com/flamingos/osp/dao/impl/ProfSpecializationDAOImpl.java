package com.flamingos.osp.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.flamingos.osp.bean.OspProfSpecializationBean;
import com.flamingos.osp.bean.OspProfessionalBean;
import com.flamingos.osp.dao.ProfSpecializationAO;
import com.flamingos.osp.exception.OspDaoException;
import com.flamingos.osp.util.AppConstants;

@Repository
public class ProfSpecializationDAOImpl implements ProfSpecializationAO {
  @Autowired
  private JdbcTemplate jdbcTemplate;

  private String SQL_INSERT_PROF_SPECIALIZATION =
      "INSERT INTO OSP_PROF_SPECIALIZATIONS (PROF_SPEC_ID,PROF_SPEC_NAME,PROF_SPEC_DESC,PROF_ID,ACTIVE_STATUS,CREATED_BY,CREATED_TS,UPDATED_BY,UPDATED_TS) VALUES(?,?,?,?,?,?,?,?,?)";

  @Override
  public void saveSpecializations(final OspProfessionalBean professional) throws OspDaoException {

    final List<OspProfSpecializationBean> specializations = professional.getSpecializationList();
    if (null != specializations && !specializations.isEmpty()) {
      try {
        jdbcTemplate.batchUpdate(SQL_INSERT_PROF_SPECIALIZATION,
            new BatchPreparedStatementSetter() {

              @Override
              public void setValues(PreparedStatement ps, int index) throws SQLException {
                OspProfSpecializationBean specialization = specializations.get(index);
                ps.setNull(AppConstants.INT_ONE, Types.BIGINT);
                if (null != specialization.getProfSpecName()) {
                  ps.setString(AppConstants.INT_TWO, specialization.getProfSpecName());
                } else {
                  ps.setNull(AppConstants.INT_TWO, Types.VARCHAR);
                }
                if (null != specialization.getProfSpecDesc()) {
                  ps.setString(AppConstants.INT_THREE, specialization.getProfSpecDesc());
                } else {
                  ps.setNull(AppConstants.INT_THREE, Types.VARCHAR);
                }

                ps.setLong(AppConstants.INT_FOUR, professional.getProfId());
                ps.setInt(AppConstants.INT_FIVE, specialization.getActiveStatus());
                ps.setString(AppConstants.INT_SIX, professional.getCreatedBy());
                ps.setTimestamp(AppConstants.INT_SEVEN,
                    new java.sql.Timestamp(new Date().getTime()));
                ps.setNull(AppConstants.INT_EIGHT, Types.VARCHAR);
                ps.setNull(AppConstants.INT_NINE, Types.TIMESTAMP);
              }

              @Override
              public int getBatchSize() {
                // TODO Auto-generated method stub
                return specializations.size();
              }
            });
      } catch (EmptyResultDataAccessException exp) {
        throw new OspDaoException(exp);
      }
    }
  }
}
