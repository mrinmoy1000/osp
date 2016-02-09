package com.flamingos.osp.dto;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class SubCatDTO implements Serializable {

  private static final long serialVersionUID = -7711894580442445802L;

  private Integer catId;
  private Integer subCatId;
  @JsonIgnore
  private String subCatName;
  private String displayName;

  public Integer getCatId() {
    return catId;
  }

  public void setCatId(Integer catId) {
    this.catId = catId;
  }

  public Integer getSubCatId() {
    return subCatId;
  }

  public void setSubCatId(Integer subCatId) {
    this.subCatId = subCatId;
  }

  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  public String getSubCatName() {
    return subCatName;
  }

  public void setSubCatName(String subCatName) {
    this.subCatName = subCatName;
  }


}
