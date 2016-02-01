package com.flamingos.osp.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.flamingos.osp.dto.CatSubCatDTO;
import com.flamingos.osp.dto.LocationDTO;
import com.flamingos.osp.exception.OSPErrorHandler;
import com.flamingos.osp.service.LocationService;

public class MasterDataBean {
  @Autowired
  private OSPErrorHandler ospErrorHandler;
  @Autowired
  private LocationService locationService;

  /* Communication Templates */
  private List<TemplateBean> templateBeanList = new ArrayList<TemplateBean>();
  private Map<String, TemplateBean> templateMapByName = new HashMap<String, TemplateBean>();
  private Map<String, TemplateBean> templateMapById = new HashMap<String, TemplateBean>();

  /* OSP Role */
  private List<RoleBean> lstRoles = null;
  private Map<String, RoleBean> roleMapByName = new HashMap<String, RoleBean>();
  private Map<Integer, RoleBean> roleMapById = new HashMap<Integer, RoleBean>();

  /* OSP Locations */
  private List<LocationDTO> locationList = new ArrayList<LocationDTO>();
  private List<LocationDTO> countryList = new ArrayList<LocationDTO>();
  private List<LocationDTO> stateList = new ArrayList<LocationDTO>();
  private List<LocationDTO> districtList = new ArrayList<LocationDTO>();
  private List<LocationDTO> cityList = new ArrayList<LocationDTO>();
  private List<LocationDTO> areaList = new ArrayList<LocationDTO>();

  /* OSP Categories & SubCategories */
  private List<CatSubCatDTO> categoryList = new ArrayList<CatSubCatDTO>();
  private List<CatSubCatDTO> subCategoryList = new ArrayList<CatSubCatDTO>();

}
