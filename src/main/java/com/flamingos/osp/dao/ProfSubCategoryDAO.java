/**
 * 
 */
package com.flamingos.osp.dao;

import com.flamingos.osp.bean.OspProfessionalBean;
import com.flamingos.osp.exception.OspDaoException;

/**
 * @author Mrinmoy
 *
 */
public interface ProfSubCategoryDAO {
  void saveProfessionalSubCategory(OspProfessionalBean professionalBean) throws OspDaoException;
}
