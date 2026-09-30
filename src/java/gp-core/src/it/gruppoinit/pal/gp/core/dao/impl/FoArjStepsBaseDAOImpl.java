package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.FoArjStepsBaseDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.FoArjStepsBase;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class FoArjStepsBaseDAOImpl extends BaseDAOImpl<FoArjStepsBase, String> implements FoArjStepsBaseDAO {

    @Override
    public Class<FoArjStepsBase> getEntityClass() {

	return FoArjStepsBase.class;
    }

    @Override
    public List<FoArjStepsBase> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_ALL, "ordineDefault", DAOOrderTypeEnum.ASC);
    }
}
