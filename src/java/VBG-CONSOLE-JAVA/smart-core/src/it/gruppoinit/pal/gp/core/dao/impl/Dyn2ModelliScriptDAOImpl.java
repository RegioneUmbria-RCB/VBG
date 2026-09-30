package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.Dyn2ModelliScriptDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.domain.Dyn2ModelliScript;
import it.gruppoinit.pal.gp.core.domain.Dyn2ModelliScriptId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class Dyn2ModelliScriptDAOImpl extends BaseDAOImpl<Dyn2ModelliScript, Dyn2ModelliScriptId> implements Dyn2ModelliScriptDAO {

    @Override
    public Class<Dyn2ModelliScript> getEntityClass() {

	return Dyn2ModelliScript.class;
    }

    @Override
    public List<Dyn2ModelliScript> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
	// return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, PROP_DELLA_ENTITY,
	// DAOOrderTypeEnum.ASC);
    }
}
