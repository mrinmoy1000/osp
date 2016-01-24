package com.flamingos.osp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.flamingos.osp.bean.ConfigParamBean;
import com.flamingos.osp.exception.OSPBusinessException;
import com.flamingos.osp.service.SmsService;
import com.flamingos.osp.sms.SmS;
import com.flamingos.osp.sms.SmsGateWay;
import com.flamingos.osp.util.AppConstants;

@Service("smsService")
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
  public void sendSms(String recipient, String templateName, String content)
      throws OSPBusinessException {
    try {
      SmS sms = new SmS();
      sms.setTemplateName(configParamBean.getTemplateByName(templateName).getTempFilePath());
      sms.setMessage(content);
      sms.setRecipient(recipient);
      sms.setUsername(userId);
      sms.setPassword(password);
      String status = smsGateway.sendSms(sms);

      if (status.equals("Error")) {
        throw new OSPBusinessException(AppConstants.SMS_SENDING_MODULE,
            AppConstants.SMS_EXCEPTION_ERRCODE, AppConstants.SMS_EXCEPTION_ERRDESC);
      }
    } catch (Exception e) {
      throw new OSPBusinessException(AppConstants.SMS_SENDING_MODULE,
          AppConstants.SMS_EXCEPTION_ERRCODE, AppConstants.SMS_EXCEPTION_ERRDESC, e);

    }

  }

}
