package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.OConfigurazioneDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.domain.OConfigurazione;
import it.gruppoinit.pal.gp.core.domain.OConfigurazioneId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class OConfigurazioneDAOImpl extends BaseDAOImpl<OConfigurazione, OConfigurazioneId> implements OConfigurazioneDAO {

    @Override
    public Class<OConfigurazione> getEntityClass() {

	return OConfigurazione.class;
    }

    @Override
    public List<OConfigurazione> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
	// return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, PROP_DELLA_ENTITY,
	// DAOOrderTypeEnum.ASC);
    }
}
