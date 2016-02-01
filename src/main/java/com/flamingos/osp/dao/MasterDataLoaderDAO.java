package com.flamingos.osp.dao;

import java.util.List;

import com.flamingos.osp.bean.RoleBean;
import com.flamingos.osp.bean.TemplateBean;
import com.flamingos.osp.dto.CatSubCatDTO;

public interface MasterDataLoaderDAO {
  public List<TemplateBean> getAllTemplate() throws Exception;

  public List<RoleBean> getAllRoles() throws Exception;

  public List<CatSubCatDTO> getAllCategories() throws Exception;

  public List<CatSubCatDTO> getAllSubCategories() throws Exception;

}
