package com.flamingos.osp.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ticket")
public class TicketController {
  // @Autowired
  // TicketService ticketService;
  //
  // @RequestMapping(value="/generate", method=RequestMethod.GET)
  // public ResponseEntity<String> generateTicket(){
  // return new ResponseEntity<String>("Generate", HttpStatus.OK);
  // }
  //
  // @RequestMapping(value="/showproflist/{proftype}", method=RequestMethod.GET)
  // public @ResponseBody List<Professional> showListProfessionals(@PathVariable String proftype){
  // List<Professional> pList=ticketService.getProfessinalList(proftype);
  // return pList;
  // }

}
