package com.flamingos.osp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.flamingos.osp.bean.ConfigParamBean;
import com.flamingos.osp.email.EmailGateway;
import com.flamingos.osp.email.Mail;
import com.flamingos.osp.exception.OSPBusinessException;
import com.flamingos.osp.service.EmailService;
import com.flamingos.osp.util.AppConstants;

@Service("emailService")
public class EmailServiceImpl implements EmailService {
  @Value("${mail.smtp.sender.from}")
  private String mailFrom;
  @Autowired
  private EmailGateway emailGateway;
  @Autowired
  private ConfigParamBean configParamBean;

  public void sendMail(String templateName, String toemailId, String content,String content2, String subject,
      String addresseeName) throws OSPBusinessException {
    try {
      Mail mail = new Mail();
      mail.setTemplateName(configParamBean.getTemplateByName(templateName).getTempFilePath());
      mail.setMailTo(toemailId);
      mail.setVerifyURL(content);
      mail.setGenerateURL(content2);
      mail.setMailFrom(mailFrom);
      mail.setMailSubject(subject);
      mail.setFirstName(addresseeName);
      emailGateway.sendMail(mail);
    } catch (Exception e) {
      throw new OSPBusinessException(AppConstants.EMAIL_SENDING_MODULE,
          AppConstants.EMAIL_EXCEPTION_ERRCODE, AppConstants.EMAIL_EXCEPTION_ERRDESC, e);
    }
  }
}
