package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.FoArjDomandeStepsEseguitiDAO;
import it.gruppoinit.pal.gp.core.domain.FoArjDomandeStepsEseguiti;
import it.gruppoinit.pal.gp.core.domain.PkId;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author fabrizioc
 */
@Repository
public class FoArjDomandeStepsEseguitiDAOImpl extends BaseDAOImpl<FoArjDomandeStepsEseguiti, PkId> implements FoArjDomandeStepsEseguitiDAO {

    @Override
    public Class<FoArjDomandeStepsEseguiti> getEntityClass() {

	return FoArjDomandeStepsEseguiti.class;
    }
}
