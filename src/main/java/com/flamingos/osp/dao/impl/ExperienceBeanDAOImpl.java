package com.flamingos.osp.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.flamingos.osp.bean.OspExperienceBean;
import com.flamingos.osp.bean.OspProfessionalBean;
import com.flamingos.osp.dao.ExperienceBeanDAO;
import com.flamingos.osp.exception.OspDaoException;
import com.flamingos.osp.util.AppConstants;

@Repository
public class ExperienceBeanDAOImpl implements ExperienceBeanDAO {
  @Autowired
  private JdbcTemplate jdbcTemplate;
  private String SQL_INSERT_OSP_PROF_EXPERIENCE =
      "INSERT INTO OSP_PROF_EXPERIENCE  (PROF_EXP_ID,PROF_EXP_BEGIN_DT,PROF_EXP_END_DT,PROF_EXP_DESC,PROF_ID,ACTIVE_STATUS,CREATED_BY,CREATED_TS,UPDATED_BY,UPDATED_TS)"
          + " VALUES(?,?,?,?,?,?,?,?,?,?)";

  @Override
  public void saveExperience(final OspProfessionalBean professional) throws OspDaoException {

    final List<OspExperienceBean> experiences = professional.getExperienceList();
    try {
      if (null != experiences && !experiences.isEmpty()) {
        jdbcTemplate.batchUpdate(SQL_INSERT_OSP_PROF_EXPERIENCE,
            new BatchPreparedStatementSetter() {
              @Override
              public void setValues(PreparedStatement ps, int index) throws SQLException {
                OspExperienceBean experience = experiences.get(index);
                ps.setNull(AppConstants.INT_ONE, Types.BIGINT);
                if (null != experience.getProfExpBeginDt()) {
                  ps.setDate(AppConstants.INT_TWO, new java.sql.Date(experience.getProfExpBeginDt()
                      .getTime()));
                } else {
                  ps.setNull(AppConstants.INT_TWO, Types.DATE);
                }
                if (null != experience.getProfExpEndDt()) {
                  ps.setDate(AppConstants.INT_THREE, new java.sql.Date(experience
                      .getProfExpBeginDt().getTime()));
                } else {
                  ps.setNull(AppConstants.INT_THREE, Types.DATE);
                }
                if (null != experience.getProfExpDesc()) {
                  ps.setString(AppConstants.INT_FOUR, experience.getProfExpDesc());
                } else {
                  ps.setNull(AppConstants.INT_FOUR, Types.VARCHAR);
                }
                ps.setLong(AppConstants.INT_FIVE, professional.getProfId());
                ps.setInt(AppConstants.INT_SIX, experience.getActiveStatus());
                ps.setString(AppConstants.INT_SEVEN, professional.getCreatedBy());
                ps.setTimestamp(AppConstants.INT_EIGHT,
                    new java.sql.Timestamp(new Date().getTime()));
                ps.setNull(AppConstants.INT_NINE, Types.VARCHAR);
                ps.setNull(AppConstants.INT_TEN, Types.TIMESTAMP);
              }

              @Override
              public int getBatchSize() {
                // TODO Auto-generated method stub
                return experiences.size();
              }
            });
      }
    } catch (EmptyResultDataAccessException exp) {
      throw new OspDaoException(exp);
    }
  }

}
