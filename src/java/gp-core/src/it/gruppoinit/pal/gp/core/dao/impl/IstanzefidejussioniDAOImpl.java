package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.IstanzefidejussioniDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.domain.Istanzefidejussioni;
import it.gruppoinit.pal.gp.core.domain.IstanzefidejussioniId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class IstanzefidejussioniDAOImpl extends BaseDAOImpl<Istanzefidejussioni, IstanzefidejussioniId> implements IstanzefidejussioniDAO {

    @Override
    public Class<Istanzefidejussioni> getEntityClass() {

	return Istanzefidejussioni.class;
    }

    @Override
    public List<Istanzefidejussioni> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
	// return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, PROP_DELLA_ENTITY,
	// DAOOrderTypeEnum.ASC);
    }
}
