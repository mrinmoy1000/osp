package com.flamingos.osp.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.flamingos.osp.dto.LocationDTO;

public class LocationMapper implements RowMapper<LocationDTO> {

  @Override
  public LocationDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
    LocationDTO locationDto = null;
    if (null != rs) {
      locationDto = new LocationDTO();
      locationDto.setLocationCode(rs.getString("LOCATION_CODE"));
      locationDto.setLocationId(rs.getLong("LOCATION_ID"));
      locationDto.setLocationName(rs.getString("LOCATION_NAME"));
      locationDto.setLocationParentId(rs.getLong("PARENT_ID"));
      locationDto.setLocationType(rs.getInt("LOCATION_TYPE"));
    }
    return locationDto;
  }

}
