package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.Istanzedyn2modellitStoricoDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.domain.Istanzedyn2modellitStorico;
import it.gruppoinit.pal.gp.core.domain.Istanzedyn2modellitStoricoId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class Istanzedyn2modellitStoricoDAOImpl extends BaseDAOImpl<Istanzedyn2modellitStorico, Istanzedyn2modellitStoricoId> implements
	Istanzedyn2modellitStoricoDAO {

    @Override
    public Class<Istanzedyn2modellitStorico> getEntityClass() {

	return Istanzedyn2modellitStorico.class;
    }

    @Override
    public List<Istanzedyn2modellitStorico> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
	// return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, PROP_DELLA_ENTITY,
	// DAOOrderTypeEnum.ASC);
    }
}
