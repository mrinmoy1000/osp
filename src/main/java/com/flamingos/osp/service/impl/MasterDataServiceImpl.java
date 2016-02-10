package com.flamingos.osp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.flamingos.osp.bean.RoleBean;
import com.flamingos.osp.bean.TemplateBean;
import com.flamingos.osp.dao.MasterDataLoaderDAO;
import com.flamingos.osp.dto.CategoryDTO;
import com.flamingos.osp.dto.OSPErrorDTO;
import com.flamingos.osp.dto.SubCatDTO;
import com.flamingos.osp.service.MasterDataService;

public class MasterDataServiceImpl implements MasterDataService {

  @Autowired
  MasterDataLoaderDAO masterDataLoaderDao;

  @Override
  public List<TemplateBean> getAllTemplate() throws Exception {
    return masterDataLoaderDao.getAllTemplate();
  }

  @Override
  public List<RoleBean> getAllRoles() throws Exception {
    return masterDataLoaderDao.getAllRoles();
  }

  @Override
  public List<CategoryDTO> getAllCategories() throws Exception {
    return masterDataLoaderDao.getAllCategories();
  }

  @Override
  public List<SubCatDTO> getAllSubCategories() throws Exception {
    return masterDataLoaderDao.getAllSubCategories();
  }

  @Override
  public List<OSPErrorDTO> getErrorList() throws Exception {
    return masterDataLoaderDao.getErrorList();
  }

}
