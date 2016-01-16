package com.flamingos.osp.util;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;

public class AppUtil {

	public static final Logger LOGGER = Logger.getLogger(AppUtil.class);

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void writeToLog(String moduleName, String loggingType,
			Map infoToLog) {
		StringBuffer sb = new StringBuffer();
		Set<Map.Entry<String, String>> entrySet = infoToLog.entrySet();
		sb.append("Module Name:" + moduleName + AppConstants.PIPE_SEPARATOR);
		for (Entry entry : entrySet) {
			sb.append(entry.getKey() + ":" + entry.getValue()
					+ AppConstants.PIPE_SEPARATOR);
		}
		if(AppConstants.LOG_TYPE_INFO.equalsIgnoreCase(loggingType)){
			LOGGER.info(sb);
		}else if(AppConstants.LOG_TYPE_DEBUG.equalsIgnoreCase(loggingType)){
			LOGGER.debug(sb);
		}else if(AppConstants.LOG_TYPE_ERROR.equalsIgnoreCase(loggingType)){
			LOGGER.error(sb);
		}
	}

}
