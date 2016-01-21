package com.flamingos.osp.service.impl;

import javax.mail.internet.AddressException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import com.flamingos.osp.email.EmailGateway;
import com.flamingos.osp.email.Mail;
import com.flamingos.osp.exception.OspServiceException;
import com.flamingos.osp.service.Emailservice;

@Service
@Configuration
@PropertySource("classpath:osp.properties")
public class EmailserviceImpl implements Emailservice {
  @Value("${mail.smtp.sender.from}")
  private String mailFrom;

  public void sendMail(String templateName, String toemailId, String url, String subject)
      throws OspServiceException {
    try {
      Mail mail = new Mail();
      mail.setTemplateName(templateName);
      mail.setMailTo(toemailId);
      mail.setMailContent(url);
      mail.setMailFrom(mailFrom);
      mail.setMailSubject(subject);
      EmailGateway emailGateway = new EmailGateway();
      emailGateway.sendMail(mail);
    } catch (AddressException ae) {
      throw new OspServiceException(ae);
    }
  }
}
