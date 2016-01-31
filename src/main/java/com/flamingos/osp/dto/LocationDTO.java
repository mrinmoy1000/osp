package com.flamingos.osp.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class LocationDTO implements Serializable {

  private static final long serialVersionUID = 1L;

  private long locationId;
  private String locationName;
  private int locationType;
  private long locationParentId;
  @JsonIgnore
  private String locationCode;
  @JsonInclude(Include.NON_EMPTY)
  private List<LocationDTO> childLocations = new ArrayList<LocationDTO>();

  public long getLocationId() {
    return locationId;
  }

  public void setLocationId(long locationId) {
    this.locationId = locationId;
  }

  public String getLocationName() {
    return locationName;
  }

  public void setLocationName(String locationName) {
    this.locationName = locationName;
  }

  public int getLocationType() {
    return locationType;
  }

  public void setLocationType(int locationType) {
    this.locationType = locationType;
  }

  public long getLocationParentId() {
    return locationParentId;
  }

  public void setLocationParentId(long locationParentId) {
    this.locationParentId = locationParentId;
  }

  public List<LocationDTO> getChildLocations() {
    return childLocations;
  }

  public void setChildLocations(List<LocationDTO> childLocations) {
    this.childLocations = childLocations;
  }

  public String getLocationCode() {
    return locationCode;
  }

  public void setLocationCode(String locationCode) {
    this.locationCode = locationCode;
  }



}
