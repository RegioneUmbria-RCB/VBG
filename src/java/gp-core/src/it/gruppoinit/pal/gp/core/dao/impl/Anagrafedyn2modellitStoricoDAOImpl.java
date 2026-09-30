package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.Anagrafedyn2modellitStoricoDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.domain.Anagrafedyn2modellitStorico;
import it.gruppoinit.pal.gp.core.domain.Anagrafedyn2modellitStoricoId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class Anagrafedyn2modellitStoricoDAOImpl extends BaseDAOImpl<Anagrafedyn2modellitStorico, Anagrafedyn2modellitStoricoId> implements
	Anagrafedyn2modellitStoricoDAO {

    @Override
    public Class<Anagrafedyn2modellitStorico> getEntityClass() {

	return Anagrafedyn2modellitStorico.class;
    }

    @Override
    public List<Anagrafedyn2modellitStorico> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
	// return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, PROP_DELLA_ENTITY,
	// DAOOrderTypeEnum.ASC);
    }
}
