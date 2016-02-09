package com.flamingos.osp.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.flamingos.osp.bean.ConfigParamBean;
import com.flamingos.osp.bean.MasterDataBean;
import com.flamingos.osp.bean.OspProfessionalBean;
import com.flamingos.osp.dto.ConfigParamDTO;
import com.flamingos.osp.dto.OspProfessionalDTO;
import com.flamingos.osp.dto.ProfileDTO;
import com.flamingos.osp.exception.OSPBusinessException;
import com.flamingos.osp.service.AdminService;
import com.flamingos.osp.service.ProfessionalService;
import com.flamingos.osp.util.AppConstants;

@RestController
@RequestMapping(value = "/professional")
public class ProfessionalController {
  @Autowired
  AdminService adminService;
  private static final Logger logger = Logger.getLogger(ProfessionalController.class);

  @Autowired
  private ProfessionalService profService;
  @Autowired
  private ConfigParamBean configParamBean;
  @Autowired
  private MasterDataBean masterDataBean;

  @RequestMapping(value = "/addProfile", method = RequestMethod.GET, produces = "application/json")
  public ResponseEntity<ProfileDTO> addProfile(HttpServletRequest request) throws Exception {
    ProfileDTO profileDto = new ProfileDTO();
    List<ConfigParamDTO> genderList =
        configParamBean.getParamByCode(AppConstants.PARAM_CODE_USER_GENDER);
    List<ConfigParamDTO> maritialStatusList =
        configParamBean.getParamByCode(AppConstants.PARAM_CODE_MARITIAL_STATUS);
    profileDto.setGenders(genderList);
    profileDto.setMaritalStatus(maritialStatusList);
    profileDto.setLocations(masterDataBean.getCountryStateList());
    profileDto.setCategories(masterDataBean.getCategoryList());
    return new ResponseEntity<ProfileDTO>(profileDto, HttpStatus.OK);
  }

  @RequestMapping(value = "/saveProfile",// produces = "application/json",
      method = RequestMethod.POST/* , consumes = "application/json" */)
  public ResponseEntity<Long> saveProfile(@RequestBody OspProfessionalBean professionalBean,
      HttpServletRequest request) throws Exception {
    profService.saveProfile(professionalBean, request);

    return new ResponseEntity<Long>(professionalBean.getProfId(), HttpStatus.CREATED);
  }

  @RequestMapping(value = "/viewProfile", method = RequestMethod.GET)
  public ResponseEntity<OspProfessionalDTO> getProfessionalDetails(
      @RequestParam(value = "profId") Long id) {
    logger.debug(" Entering AdminController.getProfessionalDetails");
    OspProfessionalDTO prof = null;
    try {
      prof = adminService.professionalDetails(id);
      return new ResponseEntity<OspProfessionalDTO>(prof, HttpStatus.OK);
    } catch (OSPBusinessException e) {
      logger.error(" Exception occured in AdminController.getProfessionalDetails" + e.getMessage());
      prof = new OspProfessionalDTO();
      prof.setReturnStatus(AppConstants.FAILURE);
      prof.setReturnMessage(e.getMessage());
      return new ResponseEntity<OspProfessionalDTO>(prof, HttpStatus.OK);

    } finally {
      logger.debug(" Exiting AdminController.getProfessionalDetails");
    }

  }
  
  @RequestMapping(value = "/viewProfilebyRecord", method = RequestMethod.GET)
  public ResponseEntity<OspProfessionalDTO> getProfessionalDetailsbyRecordID(@RequestParam(value = "recordId") Long id) {
    logger.debug(" Entering ProfessionalController.getProfessionalDetails");
    OspProfessionalDTO prof = null;
    try {
      prof = profService.professionalDetailsbyRecordID(id);
      return new ResponseEntity<OspProfessionalDTO>(prof, HttpStatus.OK);
    } catch (OSPBusinessException e) {
      logger.error(" Exception occured in ProfessionalController.getProfessionalDetails" + e.getMessage());
      prof = new OspProfessionalDTO();
      prof.setReturnStatus(AppConstants.FAILURE);
      prof.setReturnMessage(AppConstants.NOT_FOUND);
      return new ResponseEntity<OspProfessionalDTO>(prof, HttpStatus.OK);

    } finally {
      logger.debug(" Exiting ProfessionalController.getProfessionalDetails");
    }

  }
}
