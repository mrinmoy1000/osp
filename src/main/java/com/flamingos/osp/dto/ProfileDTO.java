package com.flamingos.osp.dto;

import java.io.Serializable;
import java.util.List;

import org.apache.log4j.Logger;

public class ProfileDTO implements Serializable {

  private static final Logger logger = Logger.getLogger(ProfileDTO.class);
  private static final long serialVersionUID = 1L;

  private List<CommonParamDTO> genders;
  private List<CommonParamDTO> maritalStatus;

  private List<LocationDTO> locations;

  public List<CommonParamDTO> getGenders() {
    return genders;
  }

  public void setGenders(List<CommonParamDTO> genders) {
    this.genders = genders;
  }

  public List<CommonParamDTO> getMaritalStatus() {
    return maritalStatus;
  }

  public void setMaritalStatus(List<CommonParamDTO> maritalStatus) {
    this.maritalStatus = maritalStatus;
  }

  public List<LocationDTO> getLocations() {
    return locations;
  }

  public void setLocations(List<LocationDTO> locations) {
    this.locations = locations;
  }


}
