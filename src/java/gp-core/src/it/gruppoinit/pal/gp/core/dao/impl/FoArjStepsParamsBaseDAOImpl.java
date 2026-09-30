package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.FoArjStepsParamsBaseDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.FoArjStepsParamsBase;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class FoArjStepsParamsBaseDAOImpl extends BaseDAOImpl<FoArjStepsParamsBase, Integer> implements FoArjStepsParamsBaseDAO {

    @Override
    public Class<FoArjStepsParamsBase> getEntityClass() {

	return FoArjStepsParamsBase.class;
    }

    @Override
    public List<FoArjStepsParamsBase> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_ALL, "chiave", DAOOrderTypeEnum.ASC);
    }
}
