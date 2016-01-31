package com.flamingos.osp.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.flamingos.osp.bean.ConfigParamBean;
import com.flamingos.osp.bean.OspAddressBean;
import com.flamingos.osp.bean.OspContactBean;
import com.flamingos.osp.bean.OspExperienceBean;
import com.flamingos.osp.bean.OspProfAcademicsBean;
import com.flamingos.osp.bean.OspProfSpecializationBean;
import com.flamingos.osp.bean.OspProfessionalBean;

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

  @RequestMapping(value="/showproflist", method=RequestMethod.GET)
   public OspProfessionalBean showListProfessionals(){
   OspProfessionalBean profBean=new OspProfessionalBean();
   OspAddressBean address=new OspAddressBean();
   address.setActiveStatus(1);
   address.setAddressId(14);
   address.setCreatedBy("");
   address.setCreatedTs(new Date());
   address.setLine1("Abc");
   address.setLine2(null);
   address.setLocationId(140);
   address.setOtherArea("");
   address.setPinCode("700125");
   address.setUpdatedBy("");
   address.setUpdatedTs(new Date());
   OspContactBean contact=new OspContactBean();
   contact.setActiveStatus(1);
   contact.setContactEmail("abc@gmail.com");
   contact.setContactId(12);
   contact.setContactPhone("111111");
   contact.setContactType(10);
   contact.setCreatedBy("");
   contact.setCreatedTs(new Date());
   contact.setUpdatedBy("");
   contact.setUpdatedTs(new Date());
   OspExperienceBean exBean=new OspExperienceBean();
   exBean.setActiveStatus(1);
   exBean.setCreatedBy("");
   exBean.setCreatedTs(new Date());
   exBean.setProfExpBeginDt(new Date());
   exBean.setProfExpDesc("");
   exBean.setProfExpEndDt(new Date());
   exBean.setProfExpId(0L);
   exBean.setProfId(1L);
   exBean.setUpdatedBy("");
   exBean.setUpdatedTs(new Date());
   OspProfAcademicsBean academic=new OspProfAcademicsBean();
   academic.setActiveStatus(1);
   academic.setCreatedBy("");
   academic.setCreatedTs(new Date());
   academic.setProfAcdmcDesc("");
   academic.setProfAcdmcId(0L);
   academic.setProfAcdmcName("");
   academic.setProfAcdmcPassYear("");
   academic.setProfAcdmcUniversity("");
   academic.setProfId(14L);
   academic.setUpdatedBy("");
   academic.setUpdatedTs(new Date());
   OspProfSpecializationBean specialization=new OspProfSpecializationBean();
   specialization.setActiveStatus(1);
   specialization.setCreatedBy("");
   specialization.setCreatedTs(new Date());
   specialization.setProfId(14L);
   specialization.setProfSpecDesc("");
   specialization.setProfSpecId(12L);
   specialization.setProfSpecName("");
   specialization.setUpdatedBy("");
   specialization.setUpdatedTs(new Date());
   List<OspExperienceBean> experienceList=new ArrayList<OspExperienceBean>();
   experienceList.add(exBean);
   List<OspProfAcademicsBean> qualificationList=new ArrayList<OspProfAcademicsBean>();
   qualificationList.add(academic);
   List<OspProfSpecializationBean> specializationList=new ArrayList<OspProfSpecializationBean>();
   specializationList.add(specialization);
   
   List<Integer> intList=new ArrayList<Integer>();
   intList.add(1);
   intList.add(2);
   profBean.setActionTaken("");
   profBean.setAddress(address);
   profBean.setContact(contact);
   profBean.setCreatedBy("");
   profBean.setCreatedTs(new Date());
   profBean.setDndActivatedFlag(0);
   profBean.setExperienceList(experienceList);
   profBean.setLstSubCategoryId(intList);
   profBean.setProfDob(new Date());
   profBean.setProfEmpId(null);
   profBean.setProfFees(20.02);
   profBean.setProfFirstName("Abc");
   profBean.setProfGender(0);
   profBean.setProfId(0L);
   profBean.setProfLastName("Abc");
   profBean.setProfMeritalStatus(0);
   profBean.setProfMerriageAnniversary(new Date());
   profBean.setProfMiddleName("");
   profBean.setProfNationality("Indian");
   profBean.setProfPan("ABCD123");
   profBean.setProfPhotograph("");
   profBean.setProfPublicId(null);
   profBean.setProfRemark("");
   profBean.setProfSignature(null);
   profBean.setProfSubscId("18:19");
   profBean.setQualificationList(qualificationList);
   profBean.setRecordId(0L);
   profBean.setSpecializationList(specializationList);
   profBean.setStatus(1);
   profBean.setUpdatedBy("");
   profBean.setUpdatedTs(new Date());
   return profBean;
   }
}
