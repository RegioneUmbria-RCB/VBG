package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.Cdsinvitati2DAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.domain.Cdsinvitati2;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class Cdsinvitati2DAOImpl extends BaseDAOImpl<Cdsinvitati2, PkId> implements Cdsinvitati2DAO {

    @Override
    public Class<Cdsinvitati2> getEntityClass() {

	return Cdsinvitati2.class;
    }

    @Override
    public List<Cdsinvitati2> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
	// return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, PROP_DELLA_ENTITY,
	// DAOOrderTypeEnum.ASC);
    }
}
