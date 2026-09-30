package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.CcCondizioniAttivitaDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.domain.CcCondizioniAttivita;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class CcCondizioniAttivitaDAOImpl extends BaseDAOImpl<CcCondizioniAttivita, PkId> implements CcCondizioniAttivitaDAO {

    @Override
    public Class<CcCondizioniAttivita> getEntityClass() {

	return CcCondizioniAttivita.class;
    }

    @Override
    public List<CcCondizioniAttivita> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
	// return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, PROP_DELLA_ENTITY,
	// DAOOrderTypeEnum.ASC);
    }
}
