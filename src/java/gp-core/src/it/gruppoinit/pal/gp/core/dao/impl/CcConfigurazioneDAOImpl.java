package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.CcConfigurazioneDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.domain.CcConfigurazione;
import it.gruppoinit.pal.gp.core.domain.CcConfigurazioneId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class CcConfigurazioneDAOImpl extends BaseDAOImpl<CcConfigurazione, CcConfigurazioneId> implements CcConfigurazioneDAO {

    @Override
    public Class<CcConfigurazione> getEntityClass() {

	return CcConfigurazione.class;
    }

    @Override
    public List<CcConfigurazione> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
	// return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, PROP_DELLA_ENTITY,
	// DAOOrderTypeEnum.ASC);
    }
}
