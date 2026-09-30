package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.CanoniConfigurazioneDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.domain.CanoniConfigurazione;
import it.gruppoinit.pal.gp.core.domain.CanoniConfigurazioneId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class CanoniConfigurazioneDAOImpl extends BaseDAOImpl<CanoniConfigurazione, CanoniConfigurazioneId> implements CanoniConfigurazioneDAO {

    @Override
    public Class<CanoniConfigurazione> getEntityClass() {

	return CanoniConfigurazione.class;
    }

    @Override
    public List<CanoniConfigurazione> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
	// return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, PROP_DELLA_ENTITY,
	// DAOOrderTypeEnum.ASC);
    }
}
