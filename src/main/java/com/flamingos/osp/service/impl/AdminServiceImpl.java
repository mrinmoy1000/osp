package com.flamingos.osp.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.flamingos.osp.bean.ConfigParamBean;
import com.flamingos.osp.bean.OspExperienceBean;
import com.flamingos.osp.bean.OspProfAcademicsBean;
import com.flamingos.osp.bean.OspProfSpecializationBean;
import com.flamingos.osp.bean.OspProfessionalBean;
import com.flamingos.osp.dao.ProfessionalDAO;
import com.flamingos.osp.dto.ConfigParamDTO;
import com.flamingos.osp.dto.OspProfessionalDTO;
import com.flamingos.osp.exception.OSPBusinessException;
import com.flamingos.osp.exception.OspDaoException;
import com.flamingos.osp.exception.OspServiceException;
import com.flamingos.osp.service.AdminService;
import com.flamingos.osp.util.AppConstants;

@Transactional(propagation = Propagation.REQUIRED)
@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private ConfigParamBean configParamBean;

	@Autowired
	ProfessionalDAO profDao;

	ConfigParamDTO userStatusBean = null;

	@Override
	public String approveProfile(OspProfessionalBean professional, HttpServletRequest request)
			throws OSPBusinessException {
			
		try {
			
			professional.getActionTaken();
			
			profDao.approveProfile(professional, 1);
		} catch (OspDaoException ex) {
			throw new OSPBusinessException(AppConstants.ADMIN_APPROVE_PROFILE_MODULE,
					AppConstants.ADMIN_APPROVE_PROFILE_MODULE_EXCEPTION_ERRCODE,
					AppConstants.ADMIN_APPROVE_PROFILE_MODULE_EXCEPTION_ERRDESC, ex);

		}

		return null;

	}

	@Override
	public OspProfessionalDTO professionalDetails(int profId) throws OSPBusinessException {
		OspProfessionalDTO profDetails = null;
		List<OspProfSpecializationBean> specializationList = null;
		List<OspProfAcademicsBean> qualificationList = null;
		List<OspExperienceBean> experienceList = null;
		try {
			specializationList = profDao.getProfSpecializationList(profId);
			qualificationList = profDao.getProfQualificationList(profId);
			experienceList = profDao.getProfExperienceList(profId);
			profDetails = profDao.getProfessionalDetails(profId);
			if (profDetails != null) {
				profDetails.setExperienceList(experienceList);
				profDetails.setQualificationList(qualificationList);
				profDetails.setSpecializationList(specializationList);
			}
		} catch (OspDaoException exp) {
			throw new OSPBusinessException(AppConstants.ADMIN_FETCH_PROFILE_MODULE,
					AppConstants.ADMIN_FETCH_PROFILE_MODULE_EXCEPTION_ERRCODE,
					AppConstants.ADMIN_FETCH_PROFILE_MODULE_EXCEPTION_ERRDESC, exp);

		}
		return profDetails;
	}

	@Override
	public List<OspProfessionalDTO> allProfessionalDetails() throws OSPBusinessException {
		List<OspProfessionalDTO> profDetailsList = null;
		try {
			profDetailsList = profDao.getAllProfessionalDetails();
		} catch (OspDaoException exp) {
			throw new OSPBusinessException(AppConstants.ADMIN_FETCH_PROFILE_MODULE,
					AppConstants.ADMIN_FETCH_PROFILE_MODULE_EXCEPTION_ERRCODE,
					AppConstants.ADMIN_FETCH_PROFILE_MODULE_EXCEPTION_ERRDESC, exp);
		}
		return profDetailsList;

	}

}
