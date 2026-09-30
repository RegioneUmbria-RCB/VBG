package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.ControlloDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.domain.Controllo;
import it.gruppoinit.pal.gp.core.domain.ControlloId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class ControlloDAOImpl extends BaseDAOImpl<Controllo, ControlloId> implements ControlloDAO {

    @Override
    public Class<Controllo> getEntityClass() {

	return Controllo.class;
    }

    @Override
    public List<Controllo> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
	// return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, PROP_DELLA_ENTITY,
	// DAOOrderTypeEnum.ASC);
    }
}
