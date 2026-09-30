package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.Dyn2CampiproprietaDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.domain.Dyn2Campiproprieta;
import it.gruppoinit.pal.gp.core.domain.Dyn2CampiproprietaId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class Dyn2CampiproprietaDAOImpl extends BaseDAOImpl<Dyn2Campiproprieta, Dyn2CampiproprietaId> implements Dyn2CampiproprietaDAO {

    @Override
    public Class<Dyn2Campiproprieta> getEntityClass() {

	return Dyn2Campiproprieta.class;
    }

    @Override
    public List<Dyn2Campiproprieta> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
	// return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, PROP_DELLA_ENTITY,
	// DAOOrderTypeEnum.ASC);
    }
}
