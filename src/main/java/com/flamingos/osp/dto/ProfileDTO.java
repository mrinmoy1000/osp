package com.flamingos.osp.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class ProfileDTO implements Serializable {

  private static final Logger logger = Logger.getLogger(ProfileDTO.class);
  private static final long serialVersionUID = 1L;

  private List<ConfigParamDTO> genders = new ArrayList<ConfigParamDTO>();
  private List<ConfigParamDTO> maritalStatus = new ArrayList<ConfigParamDTO>();

  private List<LocationDTO> locations = new ArrayList<LocationDTO>();

  private List<CategoryDTO> categories = new ArrayList<CategoryDTO>();
  
  private int paramId;

  public int getParamId() {
    return paramId;
  }

  public void setParamId(int paramId) {
    this.paramId = paramId;
  }

  public List<ConfigParamDTO> getGenders() {
    return genders;
  }

  public void setGenders(List<ConfigParamDTO> genders) {
    this.genders = genders;
  }

  public List<ConfigParamDTO> getMaritalStatus() {
    return maritalStatus;
  }

  public void setMaritalStatus(List<ConfigParamDTO> maritalStatus) {
    this.maritalStatus = maritalStatus;
  }

  public List<LocationDTO> getLocations() {
    return locations;
  }

  public void setLocations(List<LocationDTO> locations) {
    this.locations = locations;
  }

  public List<CategoryDTO> getCategories() {
    return categories;
  }

  public void setCategories(List<CategoryDTO> categories) {
    this.categories = categories;
  }


}
