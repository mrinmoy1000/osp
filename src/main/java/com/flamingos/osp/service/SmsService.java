package com.flamingos.osp.service;

import com.flamingos.osp.exception.OSPBusinessException;

public interface SmsService {
  public void sendSms(String sender, String templateName, String content)
      throws OSPBusinessException;
}
