package com.flamingos.osp.dao;

import java.util.List;

import com.flamingos.osp.dto.LocationDTO;
import com.flamingos.osp.exception.OSPBusinessException;

public interface LocationDAO {

  public List<LocationDTO> getLocationList() throws OSPBusinessException;

}
