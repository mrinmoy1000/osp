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
import org.springframework.web.bind.annotation.RestController;

import com.flamingos.osp.bean.ConfigParamBean;
import com.flamingos.osp.bean.OspProfessionalBean;
import com.flamingos.osp.dto.CommonParamDTO;
import com.flamingos.osp.dto.ConfigParamDTO;
import com.flamingos.osp.dto.LocationDTO;
import com.flamingos.osp.dto.ProfileDTO;
import com.flamingos.osp.service.ProfessionalService;
import com.flamingos.osp.util.AppConstants;

@RestController
@RequestMapping(value = "/professional")
public class ProfessionalController {

  private static final Logger logger = Logger.getLogger(ProfessionalController.class);

  @Autowired
  private ProfessionalService profService;
  @Autowired
  private ConfigParamBean configParamBean;

  @RequestMapping(value = "/addProfile",  method = RequestMethod.GET)
  public ProfileDTO addProfile(HttpServletRequest request) throws Exception {
    ProfileDTO profileDto = new ProfileDTO();
    List<ConfigParamDTO> genderList=configParamBean.getParamByCode(AppConstants.PARAM_CODE_USER_GENDER);
    List<ConfigParamDTO> maritialStatusList=configParamBean.getParamByCode(AppConstants.PARAM_CODE_MARITIAL_STATUS);
    profileDto.setGenders(genderList);
    profileDto.setMaritalStatus(maritialStatusList);
    List<LocationDTO> countryList=configParamBean.getCountryList();
    List<LocationDTO> stateList=configParamBean.getStateList();
    for(LocationDTO country:countryList){
      for(LocationDTO state:stateList){
        if(country.getLocationId()==state.getLocationParentId()){
          country.getChildLocations().add(state);
        }
      }
    }
    profileDto.setLocations(countryList);

    return profileDto;
  }

  @RequestMapping(value = "/saveProfile", produces = "application/json",
      method = RequestMethod.POST, consumes = "application/json")
  public ResponseEntity<String> saveProfile(@RequestBody OspProfessionalBean professionalBean,
      HttpServletRequest request) throws Exception {
    String successMessage = profService.saveProfile(professionalBean, request);

    return new ResponseEntity<String>(successMessage, HttpStatus.CREATED);
  }



}
