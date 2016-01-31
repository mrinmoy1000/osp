package com.flamingos.osp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.flamingos.osp.bean.ConfigParamBean;

@RestController
@RequestMapping("/ticket")
public class TicketController {
  @Autowired
  ConfigParamBean configParamBean;

  // @Autowired
  // TicketService ticketService;
  //
  @RequestMapping(value = "/generate", method = RequestMethod.GET)
  public ResponseEntity<String> generateTicket() {
    configParamBean.loadConfigParam();
    return new ResponseEntity<String>("Generate", HttpStatus.OK);
  }
  //
  // @RequestMapping(value="/showproflist/{proftype}", method=RequestMethod.GET)
  // public @ResponseBody List<Professional> showListProfessionals(@PathVariable String proftype){
  // List<Professional> pList=ticketService.getProfessinalList(proftype);
  // return pList;
  // }

}
