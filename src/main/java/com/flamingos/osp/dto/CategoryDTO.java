package com.flamingos.osp.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class CategoryDTO implements Serializable {

  private static final long serialVersionUID = -7711894580442445802L;

  private Integer catId;
  @JsonIgnore
  private String catName;
  private String displayName;
  @JsonInclude(Include.NON_EMPTY)
  private List<SubCatDTO> subCategoryList = new ArrayList<SubCatDTO>();

  public Integer getCatId() {
    return catId;
  }

  public void setCatId(Integer catId) {
    this.catId = catId;
  }

  public String getCatName() {
    return catName;
  }

  public void setCatName(String catName) {
    this.catName = catName;
  }

  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  public List<SubCatDTO> getSubCategoryList() {
    return subCategoryList;
  }

  public void setSubCategoryList(List<SubCatDTO> subCategoryList) {
    this.subCategoryList = subCategoryList;
  }

}
