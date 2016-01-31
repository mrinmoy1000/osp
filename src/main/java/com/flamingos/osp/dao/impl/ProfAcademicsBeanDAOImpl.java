package com.flamingos.osp.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLType;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlTypeValue;
import org.springframework.stereotype.Repository;

import com.flamingos.osp.bean.OspProfAcademicsBean;
import com.flamingos.osp.bean.OspProfessionalBean;
import com.flamingos.osp.dao.ProfAcademicsBeanDAO;
import com.flamingos.osp.exception.OspDaoException;
import com.flamingos.osp.util.AppConstants;

@Repository
public class ProfAcademicsBeanDAOImpl implements ProfAcademicsBeanDAO {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  String SQL_INSERT_PROF_ACADEMICS =
      "INSERT INTO OSP_PROF_ACADEMICS (PROF_ACDMC_ID,PROF_ACDMC_NAME,PROF_ACDMC_DESC,PROF_ACDMC_UNIVERSITY,PROF_ACDMC_PASS_YEAR,PROF_ID,ACTIVE_STATUS,CREATED_BY,CREATED_TS,UPDATED_BY,UPDATED_TS)"
          + " VALUES (?,?,?,?,?,?,?,?,?,?,?)";

  @Override
  public void saveAcademics(final OspProfessionalBean professional) throws OspDaoException {

    final List<OspProfAcademicsBean> qualifications = professional.getQualificationList();
    if (null != qualifications && !qualifications.isEmpty()) {
      try {
        jdbcTemplate.batchUpdate(SQL_INSERT_PROF_ACADEMICS, new BatchPreparedStatementSetter() {

          @Override
          public void setValues(PreparedStatement ps, int index) throws SQLException {
            OspProfAcademicsBean academic = qualifications.get(index);
            ps.setNull(AppConstants.INT_ONE, Types.BIGINT);
            if (null != academic.getProfAcdmcName()) {
              ps.setString(AppConstants.INT_TWO, academic.getProfAcdmcName());
            } else {
              ps.setNull(AppConstants.INT_TWO, Types.VARCHAR);
            }
            if (null != academic.getProfAcdmcDesc()) {
              ps.setString(AppConstants.INT_THREE, academic.getProfAcdmcDesc());
            } else {
              ps.setNull(AppConstants.INT_THREE, Types.VARCHAR);
            }
            if (null != academic.getProfAcdmcUniversity()) {
              ps.setString(AppConstants.INT_FOUR, academic.getProfAcdmcUniversity());
            } else {
              ps.setNull(AppConstants.INT_FOUR, Types.VARCHAR);
            }
            if (null != academic.getProfAcdmcPassYear()) {
              ps.setString(AppConstants.INT_FIVE, academic.getProfAcdmcPassYear());
            } else {
              ps.setNull(AppConstants.INT_FIVE, Types.VARCHAR);
            }
            ps.setLong(AppConstants.INT_SIX, professional.getProfId());
            ps.setInt(AppConstants.INT_SEVEN, academic.getActiveStatus());
            ps.setString(AppConstants.INT_EIGHT, professional.getCreatedBy());
            ps.setTimestamp(AppConstants.INT_NINE, new java.sql.Timestamp(new Date().getTime()));
            ps.setNull(AppConstants.INT_TEN, Types.VARCHAR);
            ps.setNull(AppConstants.INT_ELEVEN, Types.TIMESTAMP);
          }

          @Override
          public int getBatchSize() {
            return qualifications.size();
          }
        });
      } catch (EmptyResultDataAccessException exp) {
        throw new OspDaoException(exp);
      }

    }
  }
}
