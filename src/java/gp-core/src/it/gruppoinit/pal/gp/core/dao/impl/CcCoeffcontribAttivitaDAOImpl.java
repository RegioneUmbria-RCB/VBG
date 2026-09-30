package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.CcCoeffcontribAttivitaDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.domain.CcCoeffcontribAttivita;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class CcCoeffcontribAttivitaDAOImpl extends BaseDAOImpl<CcCoeffcontribAttivita, PkId> implements CcCoeffcontribAttivitaDAO {

    @Override
    public Class<CcCoeffcontribAttivita> getEntityClass() {

	return CcCoeffcontribAttivita.class;
    }

    @Override
    public List<CcCoeffcontribAttivita> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
	// return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, PROP_DELLA_ENTITY,
	// DAOOrderTypeEnum.ASC);
    }
}
