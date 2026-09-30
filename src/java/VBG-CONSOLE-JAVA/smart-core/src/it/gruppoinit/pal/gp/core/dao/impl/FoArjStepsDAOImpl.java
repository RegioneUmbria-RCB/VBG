package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.FoArjStepsDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.FoArjSteps;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class FoArjStepsDAOImpl extends BaseDAOImpl<FoArjSteps, PkId> implements FoArjStepsDAO {

    @Override
    public Class<FoArjSteps> getEntityClass() {

	return FoArjSteps.class;
    }

    @Override
    public List<FoArjSteps> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, "titolo", DAOOrderTypeEnum.ASC);
    }
}
