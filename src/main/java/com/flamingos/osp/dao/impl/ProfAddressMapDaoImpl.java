package com.flamingos.osp.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.flamingos.osp.bean.OspProfessionalBean;
import com.flamingos.osp.dao.ProfAddressMapDao;
import com.flamingos.osp.exception.OspDaoException;

public class ProfAddressMapDaoImpl implements ProfAddressMapDao {
  @Autowired
  private NamedParameterJdbcTemplate namedJdbcTemplate;

  @Override
  public void addAddressMap(OspProfessionalBean professionalBean) throws OspDaoException {

    String insertOspProfAddressMap =
        "INSERT INTO OSP_PROF_ADDRESS_MAP VALUES (:PROF_ID," + ":ADDRESS_ID," + ":ACTIVE_STATUS)";

    Map<String, Object> OspProfAddrssMap = new HashMap<String, Object>();
    OspProfAddrssMap.put("PROF_ID", professionalBean.getProfId());
    OspProfAddrssMap.put("ADDRESS_ID", professionalBean.getAddress().getAddressId());
    OspProfAddrssMap.put("ACTIVE_STATUS", 1);
    try {
      namedJdbcTemplate.update(insertOspProfAddressMap, OspProfAddrssMap);
    } catch (RuntimeException exp) {
      throw new OspDaoException(exp);
    }
  }

}
