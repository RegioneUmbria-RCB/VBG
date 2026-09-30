package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.VwProvinceDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.VwProvince;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author gianpaolot
 */
@Repository
public class VwProvinceDAOImpl extends BaseDAOImpl<VwProvince, String> implements VwProvinceDAO {

    @Override
    public Class<VwProvince> getEntityClass() {

	return VwProvince.class;
    }

    @Override
    public List<VwProvince> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_ALL, "provincia", DAOOrderTypeEnum.ASC);
    }
}
