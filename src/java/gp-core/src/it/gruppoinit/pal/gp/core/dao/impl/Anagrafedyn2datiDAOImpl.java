package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.Anagrafedyn2datiDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.domain.Anagrafedyn2dati;
import it.gruppoinit.pal.gp.core.domain.Anagrafedyn2datiId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class Anagrafedyn2datiDAOImpl extends BaseDAOImpl<Anagrafedyn2dati, Anagrafedyn2datiId> implements Anagrafedyn2datiDAO {

    @Override
    public Class<Anagrafedyn2dati> getEntityClass() {

	return Anagrafedyn2dati.class;
    }

    @Override
    public List<Anagrafedyn2dati> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
	// return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, PROP_DELLA_ENTITY,
	// DAOOrderTypeEnum.ASC);
    }
}
