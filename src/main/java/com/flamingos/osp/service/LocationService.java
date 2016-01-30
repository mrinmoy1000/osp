package com.flamingos.osp.service;

import java.util.List;

import com.flamingos.osp.dto.LocationDTO;
import com.flamingos.osp.exception.OSPBusinessException;

public interface LocationService {
  public List<LocationDTO> getLocationList() throws OSPBusinessException;

}
