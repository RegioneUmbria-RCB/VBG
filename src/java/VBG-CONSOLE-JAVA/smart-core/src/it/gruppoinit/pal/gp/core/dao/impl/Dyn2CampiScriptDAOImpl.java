package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.Dyn2CampiScriptDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.domain.Dyn2CampiScript;
import it.gruppoinit.pal.gp.core.domain.Dyn2CampiScriptId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class Dyn2CampiScriptDAOImpl extends BaseDAOImpl<Dyn2CampiScript, Dyn2CampiScriptId> implements Dyn2CampiScriptDAO {

    @Override
    public Class<Dyn2CampiScript> getEntityClass() {

	return Dyn2CampiScript.class;
    }

    @Override
    public List<Dyn2CampiScript> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
	// return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, PROP_DELLA_ENTITY,
	// DAOOrderTypeEnum.ASC);
    }
}
