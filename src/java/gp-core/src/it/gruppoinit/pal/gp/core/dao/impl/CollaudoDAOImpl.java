package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.CollaudoDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.domain.Collaudo;
import it.gruppoinit.pal.gp.core.domain.CollaudoId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class CollaudoDAOImpl extends BaseDAOImpl<Collaudo, CollaudoId> implements CollaudoDAO {

    @Override
    public Class<Collaudo> getEntityClass() {

	return Collaudo.class;
    }

    @Override
    public List<Collaudo> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
	// return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, PROP_DELLA_ENTITY,
	// DAOOrderTypeEnum.ASC);
    }
}
