package com.flamingos.osp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import com.flamingos.osp.bean.ConfigParamBean;
import com.flamingos.osp.email.EmailGateway;
import com.flamingos.osp.exception.OspServiceException;
import com.flamingos.osp.service.SmsService;
import com.flamingos.osp.sms.SmS;
import com.flamingos.osp.sms.SmsGateWay;

@Service("smsService")
@Configuration
@PropertySource("classpath:osp.properties")
public class SmsServiceImpl implements SmsService {
	@Value("${sms.userid}")
	private String userId;
	@Value("${sms.password}")
	private String password;
	@Autowired
	private SmsGateWay smsGateway;
	@Autowired
	private ConfigParamBean configParamBean;

	@Override
	public void sendSms(String recipient, String templateName)
			throws OspServiceException {

		SmS sms = new SmS();
		sms.setTemplateName(configParamBean.getTemplateByName(templateName)
				.getTempFilePath());
		//sms.setMessage(content);
		sms.setRecipient(recipient);
		sms.setUsername(userId);
		sms.setPassword(password);
		String status = smsGateway.sendSms(sms);

		if (status.equals("Error")) {
			throw new OspServiceException("Error");
		}

	}

}
