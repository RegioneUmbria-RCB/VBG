package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.Anagrafedyn2datiStoricoDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.domain.Anagrafedyn2datiStorico;
import it.gruppoinit.pal.gp.core.domain.Anagrafedyn2datiStoricoId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class Anagrafedyn2datiStoricoDAOImpl extends BaseDAOImpl<Anagrafedyn2datiStorico, Anagrafedyn2datiStoricoId> implements
	Anagrafedyn2datiStoricoDAO {

    @Override
    public Class<Anagrafedyn2datiStorico> getEntityClass() {

	return Anagrafedyn2datiStorico.class;
    }

    @Override
    public List<Anagrafedyn2datiStorico> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
	// return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, PROP_DELLA_ENTITY,
	// DAOOrderTypeEnum.ASC);
    }
}
