package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.Istanzedyn2datiStoricoDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.domain.Istanzedyn2datiStorico;
import it.gruppoinit.pal.gp.core.domain.Istanzedyn2datiStoricoId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class Istanzedyn2datiStoricoDAOImpl extends BaseDAOImpl<Istanzedyn2datiStorico, Istanzedyn2datiStoricoId> implements Istanzedyn2datiStoricoDAO {

    @Override
    public Class<Istanzedyn2datiStorico> getEntityClass() {

	return Istanzedyn2datiStorico.class;
    }

    @Override
    public List<Istanzedyn2datiStorico> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
	// return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, PROP_DELLA_ENTITY,
	// DAOOrderTypeEnum.ASC);
    }
}
