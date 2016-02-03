package com.flamingos.osp.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.flamingos.osp.dto.CategoryDTO;
import com.flamingos.osp.dto.ConfigParamDTO;
import com.flamingos.osp.dto.LocationDTO;
import com.flamingos.osp.exception.OSPBusinessException;
import com.flamingos.osp.exception.OSPErrorHandler;
import com.flamingos.osp.service.ConfigParamLoaderService;
import com.flamingos.osp.service.LocationService;
import com.flamingos.osp.service.MasterDataService;
import com.flamingos.osp.util.AppConstants;
import com.flamingos.osp.util.AppUtil;

@Component
public class ConfigParamBean {

  @Autowired
  private ConfigParamLoaderService configParamLoaderService;
  @Autowired
  private OSPErrorHandler ospErrorHandler;

  /* OSP Parameters */
  private List<ConfigParamDTO> listParam = new ArrayList<ConfigParamDTO>();
  private Map<String, ConfigParamDTO> mapByParamId = new HashMap<String, ConfigParamDTO>();
  private Map<String, ConfigParamDTO> mapByParamCodeAndName = new HashMap<String, ConfigParamDTO>();
  private Map<String, List<ConfigParamDTO>> mapByParamCode =
      new HashMap<String, List<ConfigParamDTO>>();

  public void loadConfigParam() {
    Map<String, String> logMap = new HashMap<String, String>();
    logMap.put("Message", "Config Param Loading Started");
    AppUtil.writeToLog(AppConstants.CONFIG_LOADING_MODULE, AppConstants.LOG_TYPE_INFO, logMap);
    try {
      listParam = configParamLoaderService.loadConfigParam();
      for (ConfigParamDTO param : listParam) {
        mapByParamId.put(Integer.toString(param.getParameterid()), param);
        mapByParamCodeAndName.put(param.getCode() + "__" + param.getName(), param);
        if (mapByParamCode.containsKey(param.getCode())) {
          List<ConfigParamDTO> existlistparamDto = mapByParamCode.get(param.getCode());
          existlistparamDto.add(param);
        } else {
          List<ConfigParamDTO> newlistparamDto = new ArrayList<ConfigParamDTO>();
          newlistparamDto.add(param);
          mapByParamCode.put(param.getCode(), newlistparamDto);
        }
      }

    } catch (OSPBusinessException ospEx) {
      if ("".equalsIgnoreCase(ospEx.getModuleName())) {
        ospEx.setModuleName(AppConstants.CONFIG_LOADING_MODULE);
      }
      ospErrorHandler.handleOspBusinessException(ospEx);

    } catch (Exception gne) {
      ospErrorHandler.handleGenericException(AppConstants.CONFIG_LOADING_MODULE, gne);
    }

  }

  public ConfigParamDTO getParameterByCodeName(String paramCode, String paramName) {
    String key = paramCode + "__" + paramName;
    return getMapByParamCodeAndName().get(key);
  }

  public Map<String, ConfigParamDTO> getMapByParamCodeAndName() {
    return mapByParamCodeAndName;
  }

  public List<ConfigParamDTO> getParamByCode(String paramCode) {
    return mapByParamCode.get(paramCode);
  }

}
