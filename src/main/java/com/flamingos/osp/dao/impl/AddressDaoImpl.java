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
import com.flamingos.osp.dao.AddressDao;
import com.flamingos.osp.exception.OspDaoException;
@Repository
public class AddressDaoImpl implements AddressDao {
  @Autowired
  private NamedParameterJdbcTemplate namedJdbcTemplate;

  @Override
  public void addAddress(OspProfessionalBean professionalBean) throws OspDaoException {
    String sql =
        "INSERT INTO OSP_CONTACT(LOCATION_ID,OTHER_AREA,LINE_1,LINE_2,ACTIVE_STATUS,CREATED_TS,CREATED_BY) VALUES(:LOCATION_ID, :OTHER_AREA, :LINE_1, :LINE_2, :ACTIVE_STATUS, :CREATED_TS, :CREATED_BY)";
    try {
      GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
      MapSqlParameterSource namedParameters = new MapSqlParameterSource();
      namedParameters.addValue("LOCATION_ID", professionalBean.getContact().getContactType());
      namedParameters.addValue("OTHER_AREA", professionalBean.getContact().getContactPhone());
      namedParameters.addValue("LINE_1", professionalBean.getContact().getContactEmail());
      namedParameters.addValue("LINE_2", professionalBean.getContact().getActiveStatus());
      namedParameters.addValue("ACTIVE_STATUS", new Timestamp(new Date().getTime()));
      namedParameters.addValue("CREATED_TS", professionalBean.getContact().getCreatedBy());      
      namedParameters.addValue("CREATED_BY", professionalBean.getContact().getCreatedBy());

      namedJdbcTemplate.update(sql, namedParameters, generatedKeyHolder);

      professionalBean.getAddress().setAddressId(generatedKeyHolder.getKey().intValue());
      
    } catch (EmptyResultDataAccessException exp) {
      throw new OspDaoException(exp);
    }        
  }

}
