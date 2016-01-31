package com.flamingos.osp.dao.impl;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import com.flamingos.osp.bean.OspProfessionalBean;
import com.flamingos.osp.dao.AddressDAO;
import com.flamingos.osp.exception.OspDaoException;

@Repository
public class AddressDAOImpl implements AddressDAO {
  @Autowired
  private NamedParameterJdbcTemplate namedJdbcTemplate;

  @Override
  public void saveAddress(OspProfessionalBean professionalBean) throws OspDaoException {
    String sql =
        "INSERT INTO OSP_ADDRESS(LOCATION_ID,OTHER_AREA,LINE_1,LINE_2,ACTIVE_STATUS,CREATED_TS,CREATED_BY) VALUES(:LOCATION_ID, :OTHER_AREA, :LINE_1, :LINE_2, :ACTIVE_STATUS, :CREATED_TS, :CREATED_BY)";
    try {
      GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
      MapSqlParameterSource namedParameters = new MapSqlParameterSource();
      namedParameters.addValue("LOCATION_ID", professionalBean.getAddress().getLocationId());
      namedParameters.addValue("OTHER_AREA", professionalBean.getAddress().getOtherArea());
      namedParameters.addValue("LINE_1", professionalBean.getAddress().getLine1());
      namedParameters.addValue("LINE_2", professionalBean.getAddress().getLine2());
      namedParameters.addValue("ACTIVE_STATUS", professionalBean.getAddress().getActiveStatus());
      namedParameters.addValue("CREATED_TS", new java.sql.Date(new Date().getTime()));
      namedParameters.addValue("CREATED_BY", professionalBean.getCreatedBy());

      namedJdbcTemplate.update(sql, namedParameters, generatedKeyHolder);

      professionalBean.getAddress().setAddressId(generatedKeyHolder.getKey().intValue());

    } catch (EmptyResultDataAccessException exp) {
      throw new OspDaoException(exp);
    }
  }

}
