package com.flamingos.osp.service;

import com.flamingos.osp.exception.OSPBusinessException;

public interface EmailService {
  public void sendMail(String templateName, String emailId, String content, String content2,
      String subject, String addresseeName) throws OSPBusinessException;
}
