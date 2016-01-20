package com.flamingos.osp.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.flamingos.osp.dto.ConfigParamDto;
import com.flamingos.osp.exception.OSPBusinessException;
import com.flamingos.osp.exception.OSPErrorHandler;
import com.flamingos.osp.service.ConfigParamLoaderService;
import com.flamingos.osp.util.AppConstants;
import com.flamingos.osp.util.AppUtil;

public class ConfigParamBean {

	@Autowired
	private ConfigParamLoaderService configParamLoaderService;
	@Autowired
	private OSPErrorHandler ospErrorHandler;
	private static Map<String, ConfigParamDto> mapByParamId = new HashMap<String, ConfigParamDto>();
	private static List<ConfigParamDto> listParam = new ArrayList<ConfigParamDto>();
	private static Map<String, ConfigParamDto> mapByParamCodeAndName = new HashMap<String, ConfigParamDto>();
	private static Map<String, List<ConfigParamDto>> mapByParamCode = new HashMap<String, List<ConfigParamDto>>();
	private static Map<String, TemplateBean> templateMap = new HashMap<String, TemplateBean>();

	public void loadConfigParam() {
		Map<String, String> logMap = new HashMap<String, String>();
		logMap.put("Message", "Config Param Loading Started");
		AppUtil.writeToLog(AppConstants.CONFIG_LOADING_MODULE,
				AppConstants.LOG_TYPE_INFO, logMap);
		try {
			listParam = configParamLoaderService.loadConfigParam();
			for (ConfigParamDto param : listParam) {
				mapByParamId.put(Integer.toString(param.getParameter_id()),
						param);
				mapByParamCodeAndName.put(param.getCode() + param.getName(),
						param);
			}
			List<TemplateBean> templateBeanList = configParamLoaderService
					.getAllTemplate();
			for (TemplateBean tb : templateBeanList) {
				templateMap.put(tb.getTempName(), tb);
			}
		} catch (OSPBusinessException ospEx) {
			if ("".equalsIgnoreCase(ospEx.getModuleName())) {
				ospEx.setModuleName(AppConstants.CONFIG_LOADING_MODULE);
			}
			ospErrorHandler.handleOspBusinessException(ospEx);

		} catch (Exception gne) {
			ospErrorHandler.handleGenericException(
					AppConstants.CONFIG_LOADING_MODULE, gne);
		}

	}
	public TemplateBean getTemplateByName(String templateName){
		return templateMap.get(templateName);
	}

}
