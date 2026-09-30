package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.CdsconvocazioniDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.domain.Cdsconvocazioni;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class CdsconvocazioniDAOImpl extends BaseDAOImpl<Cdsconvocazioni, PkId> implements CdsconvocazioniDAO {

    @Override
    public Class<Cdsconvocazioni> getEntityClass() {

	return Cdsconvocazioni.class;
    }

    @Override
    public List<Cdsconvocazioni> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
	// return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, PROP_DELLA_ENTITY,
	// DAOOrderTypeEnum.ASC);
    }
}
