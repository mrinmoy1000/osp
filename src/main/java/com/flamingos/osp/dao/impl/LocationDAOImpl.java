package com.flamingos.osp.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.flamingos.osp.dao.LocationDAO;
import com.flamingos.osp.dto.LocationDTO;
import com.flamingos.osp.exception.OSPBusinessException;
import com.flamingos.osp.mapper.LocationMapper;
import com.flamingos.osp.util.AppConstants;

@Repository
public class LocationDAOImpl extends BaseDAOImpl implements LocationDAO {

  @Value("${query_osp_location_select}")
  private String QUERY_OSP_LOCATION_SELECT;

  @Override
  public List<LocationDTO> getLocationList() throws OSPBusinessException {
    List<LocationDTO> locationList = new ArrayList<LocationDTO>();
    try {
      Object[] values = new Object[] {1};
      locationList =
          getJdbcTemplate().query(QUERY_OSP_LOCATION_SELECT, values, new LocationMapper());
      if (!(locationList.size() > 0)) {
        throw new OSPBusinessException(AppConstants.FETCH_LOCATION_MODULE,
            AppConstants.DB_NO_RECORD_FOUND_ERRCODE, AppConstants.DB_NO_RECORD_FOUND_ERRMSG, null);
      }
    } catch (Exception e) {
      throw new OSPBusinessException(AppConstants.FETCH_LOCATION_MODULE,
          AppConstants.DB_GENERIC_ERRCODE, e.getMessage(), e);
    }

    return locationList;
  }
}
