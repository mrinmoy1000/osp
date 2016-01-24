package com.flamingos.osp.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.flamingos.osp.dto.ConfigParamDto;
import com.flamingos.osp.exception.OSPBusinessException;
import com.flamingos.osp.exception.OSPErrorHandler;
import com.flamingos.osp.service.ConfigParamLoaderService;
import com.flamingos.osp.util.AppConstants;
import com.flamingos.osp.util.AppUtil;

@Component
public class ConfigParamBean {

  @Autowired
  private ConfigParamLoaderService configParamLoaderService;
  @Autowired
  private OSPErrorHandler ospErrorHandler;
  private Map<String, ConfigParamDto> mapByParamId = new HashMap<String, ConfigParamDto>();
  private List<ConfigParamDto> listParam = new ArrayList<ConfigParamDto>();
  private List<TemplateBean> templateBeanList = new ArrayList<TemplateBean>();
  private Map<String, ConfigParamDto> mapByParamCodeAndName = new HashMap<String, ConfigParamDto>();
  private Map<String, List<ConfigParamDto>> mapByParamCode = new HashMap<String, List<ConfigParamDto>>();
  private Map<String, TemplateBean> templateMapByName = new HashMap<String, TemplateBean>();
  private Map<String, TemplateBean> templateMapById = new HashMap<String, TemplateBean>();

  public void loadConfigParam() {
    Map<String, String> logMap = new HashMap<String, String>();
    logMap.put("Message", "Config Param Loading Started");
    AppUtil.writeToLog(AppConstants.CONFIG_LOADING_MODULE, AppConstants.LOG_TYPE_INFO, logMap);
    try {
      listParam = configParamLoaderService.loadConfigParam();
      for (ConfigParamDto param : listParam) {
        mapByParamId.put(Integer.toString(param.getParameterid()), param);
        mapByParamCodeAndName.put(param.getCode() +"__"+param.getName(), param);
        if(mapByParamCode.containsKey(param.getCode())){
          List<ConfigParamDto> existlistparamDto=mapByParamCode.get(param.getCode());
          existlistparamDto.add(param);
        }else{
          List<ConfigParamDto> newlistparamDto=new ArrayList<ConfigParamDto>();
          newlistparamDto.add(param);
          mapByParamCode.put(param.getCode(), newlistparamDto);
        }
      }
      templateBeanList = configParamLoaderService.getAllTemplate();
      for (TemplateBean tb : templateBeanList) {
        templateMapByName.put(tb.getTempName(), tb);
        templateMapById.put(Integer.toString(tb.getTemplateId()), tb);
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

  public TemplateBean getTemplateByName(String name) {
    return templateMapByName.get(name);
  }

  public TemplateBean getTemplateById(String id) {
    return templateMapByName.get(id);
  }

  public ConfigParamDto getParameterByCodeName(String paramCode, String paramName) {
    String key = paramCode + "__" + paramName;
    return getMapByParamCodeAndName().get(key);
  }

  public Map<String, ConfigParamDto> getMapByParamCodeAndName() {
    return mapByParamCodeAndName;
  }

}
