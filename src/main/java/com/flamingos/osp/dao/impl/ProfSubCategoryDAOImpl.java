/**
 * 
 */
package com.flamingos.osp.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.flamingos.osp.bean.OspProfessionalBean;
import com.flamingos.osp.dao.ProfSubCategoryDAO;
import com.flamingos.osp.exception.OspDaoException;
import com.flamingos.osp.util.AppConstants;

/**
 * @author Mrinmoy
 *
 */
@Repository
public class ProfSubCategoryDAOImpl implements ProfSubCategoryDAO {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Value("${query_osp_prof_sub_cat_map_insert}")
  private String SQL_INSERT_PROF_SUB_CAT;

  @Override
  public void saveProfessionalSubCategory(final OspProfessionalBean professionalBean)
      throws OspDaoException {
    final List<Integer> lstSubCatIds = professionalBean.getLstSubCategoryId();
    if (null != lstSubCatIds && !lstSubCatIds.isEmpty()) {
      jdbcTemplate.batchUpdate(SQL_INSERT_PROF_SUB_CAT, new BatchPreparedStatementSetter() {

        @Override
        public void setValues(PreparedStatement ps, int index) throws SQLException {
          ps.setLong(AppConstants.INT_ONE, professionalBean.getProfId());
          ps.setInt(AppConstants.INT_TWO, lstSubCatIds.get(index));
          ps.setInt(AppConstants.INT_THREE, AppConstants.INT_ONE);
        }

        @Override
        public int getBatchSize() {
          return lstSubCatIds.size();
        }
      });
    }
  }

}
