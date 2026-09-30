package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.TipiorariodettaglioDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipiorariodettaglio;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author lucap
 */
@Repository
public class TipiorariodettaglioDAOImpl extends BaseDAOImpl<Tipiorariodettaglio, PkId> implements TipiorariodettaglioDAO {

    @Override
    public Class<Tipiorariodettaglio> getEntityClass() {

	return Tipiorariodettaglio.class;
    }
}
