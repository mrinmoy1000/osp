package com.flamingos.osp.service;

import java.util.List;

import com.flamingos.osp.bean.RoleBean;
import com.flamingos.osp.bean.TemplateBean;
import com.flamingos.osp.dto.CategoryDTO;
import com.flamingos.osp.dto.SubCatDTO;

public interface MasterDataService {
  public List<TemplateBean> getAllTemplate() throws Exception;

  public List<RoleBean> getAllRoles() throws Exception;

  public List<CategoryDTO> getAllCategories() throws Exception;

  public List<SubCatDTO> getAllSubCategories() throws Exception;

}
