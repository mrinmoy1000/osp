package com.flamingos.osp.service.impl;

import javax.mail.internet.AddressException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import com.flamingos.osp.bean.ConfigParamBean;
import com.flamingos.osp.email.EmailGateway;
import com.flamingos.osp.email.Mail;
import com.flamingos.osp.exception.OspServiceException;
import com.flamingos.osp.service.EmailService;

@Service("emailService")
@Configuration
@PropertySource("classpath:osp.properties")
public class EmailServiceImpl implements EmailService {
  @Value("${mail.smtp.sender.from}")
  private String mailFrom;
  @Autowired
  private EmailGateway emailGateway;
  @Autowired
  private ConfigParamBean configParamBean;

  public void sendMail(String templateName, String toemailId, String url, String subject)
      throws OspServiceException {
    try {
      Mail mail = new Mail();
      mail.setTemplateName(configParamBean.getTemplateByName(templateName).getTempFilePath());
      mail.setMailTo(toemailId);
      mail.setMailContent(url);
      mail.setMailFrom(mailFrom);
      mail.setMailSubject(subject);     
      emailGateway.sendMail(mail);
    } catch (AddressException ae) {
      throw new OspServiceException(ae);
    }
  }
}
