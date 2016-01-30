package com.flamingos.osp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.flamingos.osp.dao.TicketDao;
import com.flamingos.osp.dto.Professional;

public class TicketService {

  @Autowired
  TicketDao ticketDao;

  public List<Professional> getProfessinalList(String professionType) {
    return ticketDao.fetchAllProfessional(professionType);

  }

}
