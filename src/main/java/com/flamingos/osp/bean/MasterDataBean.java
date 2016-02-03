package com.flamingos.osp.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.flamingos.osp.dto.CatSubCatDTO;
import com.flamingos.osp.dto.LocationDTO;
import com.flamingos.osp.exception.OSPBusinessException;
import com.flamingos.osp.exception.OSPErrorHandler;
import com.flamingos.osp.service.LocationService;
import com.flamingos.osp.service.MasterDataService;
import com.flamingos.osp.util.AppConstants;
import com.flamingos.osp.util.AppUtil;


public class MasterDataBean {
  @Autowired
  private OSPErrorHandler ospErrorHandler;
  @Autowired
  private LocationService locationService;
  @Autowired
  private MasterDataService masterDataService;
  @Autowired
  private ConfigParamBean configParamBean;

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
  private List<LocationDTO> countryStateList = new ArrayList<LocationDTO>();

  /* OSP Categories & SubCategories */
  private List<CatSubCatDTO> categoryList = new ArrayList<CatSubCatDTO>();
  private List<CatSubCatDTO> subCategoryList = new ArrayList<CatSubCatDTO>();

  public void loadMasterData() {
    Map<String, String> logMap = new HashMap<String, String>();
    logMap.put("Message", "Config Param Loading Started");
    AppUtil.writeToLog(AppConstants.MASTER_DATA_LOADING_MODULE, AppConstants.LOG_TYPE_INFO, logMap);
    try {

      /* Loading Templates */
      templateBeanList = masterDataService.getAllTemplate();
      for (TemplateBean tb : templateBeanList) {
        templateMapByName.put(tb.getTempName(), tb);
        templateMapById.put(Integer.toString(tb.getTemplateId()), tb);
      }

      /* Loading Roles */
      lstRoles = masterDataService.getAllRoles();
      if (null != lstRoles) {
        for (RoleBean oRoleBean : lstRoles) {
          roleMapById.put(oRoleBean.getRoleId(), oRoleBean);
          roleMapByName.put(oRoleBean.getRoleName(), oRoleBean);
        }
      }

      int locationTypeCountry =
          configParamBean.getParameterByCodeName(AppConstants.PARAM_CODE_LOCATION_TYPE,
              AppConstants.PARAM_CODE_LOCATION_NAME_COUNTRY).getParameterid();
      int locationTypeState =
          configParamBean.getParameterByCodeName(AppConstants.PARAM_CODE_LOCATION_TYPE,
              AppConstants.PARAM_CODE_LOCATION_NAME_STATE).getParameterid();
      int locationTypeDistrict =
          configParamBean.getParameterByCodeName(AppConstants.PARAM_CODE_LOCATION_TYPE,
              AppConstants.PARAM_CODE_LOCATION_NAME_DISTRICT).getParameterid();
      int locationTypeCity =
          configParamBean.getParameterByCodeName(AppConstants.PARAM_CODE_LOCATION_TYPE,
              AppConstants.PARAM_CODE_LOCATION_NAME_CITY_UA).getParameterid();
      int locationTypeArea =
          configParamBean.getParameterByCodeName(AppConstants.PARAM_CODE_LOCATION_TYPE,
              AppConstants.PARAM_CODE_LOCATION_NAME_AREA).getParameterid();

      /* Loading Locations */
      locationList = locationService.getLocationList();
      if (locationList.size() > 0) {
        for (LocationDTO location : locationList) {
          if (location.getLocationType() == locationTypeCountry) {
            countryList.add(location);
          } else if (location.getLocationType() == locationTypeState) {
            stateList.add(location);
          } else if (location.getLocationType() == locationTypeDistrict) {
            districtList.add(location);
          } else if (location.getLocationType() == locationTypeCity) {
            cityList.add(location);
          } else if (location.getLocationType() == locationTypeArea) {
            areaList.add(location);
          }
        }
      }
      countryStateList.addAll(countryList);

      for (LocationDTO district : districtList) {
        for (LocationDTO city : cityList) {
          if (district.getLocationId() == city.getLocationParentId()) {
            district.getChildLocations().add(city);
          }
        }

        for (LocationDTO area : areaList) {
          if (district.getLocationId() == area.getLocationParentId()) {
            district.getChildLocations().add(area);
          }
        }

      }

      for (LocationDTO state : stateList) {
        for (LocationDTO district : districtList) {
          if (state.getLocationId() == district.getLocationParentId()) {
            state.getChildLocations().add(district);
          }
     
        }
      }

      for (LocationDTO country : countryStateList) {
        for (LocationDTO state : stateList) {
          if (country.getLocationId() == state.getLocationParentId()) {
            country.getChildLocations().add(state);
          }
        }
      }



      categoryList = masterDataService.getAllCategories();
      subCategoryList = masterDataService.getAllSubCategories();

    } catch (OSPBusinessException ospEx) {
      if ("".equalsIgnoreCase(ospEx.getModuleName())) {
        ospEx.setModuleName(AppConstants.MASTER_DATA_LOADING_MODULE);
      }
      ospErrorHandler.handleOspBusinessException(ospEx);

    } catch (Exception gne) {
      ospErrorHandler.handleGenericException(AppConstants.MASTER_DATA_LOADING_MODULE, gne);
    }
  }

  public TemplateBean getTemplateByName(String name) {
    return templateMapByName.get(name);
  }

  public TemplateBean getTemplateById(String id) {
    return templateMapByName.get(id);
  }

  public List<RoleBean> getAllRoles() {
    return lstRoles;
  }

  public RoleBean getRoleByName(String roleName) {
    return roleMapByName.get(roleName);
  }

  public RoleBean getRoleById(int roleId) {
    return roleMapById.get(roleId);
  }

  public List<LocationDTO> getCountryList() {
    return countryList;
  }

  public List<LocationDTO> getStateList() {
    return stateList;
  }

  public List<LocationDTO> getDistrictList() {
    return districtList;
  }

  public List<LocationDTO> getCityList() {
    return cityList;
  }

  public List<LocationDTO> getAreaList() {
    return areaList;
  }

  public List<LocationDTO> getCountryStateList() {
    return countryStateList;
  }

  public List<CatSubCatDTO> getCategoryList() {
    return categoryList;
  }

  public List<CatSubCatDTO> getSubCategoryList() {
    return subCategoryList;
  }

}
