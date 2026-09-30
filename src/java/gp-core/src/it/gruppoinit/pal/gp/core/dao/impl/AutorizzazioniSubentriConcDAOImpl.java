package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.AutorizzazioniSubentriConcDAO;
import it.gruppoinit.pal.gp.core.domain.AutorizzazioniSubentriConc;
import it.gruppoinit.pal.gp.core.domain.PkId;

import org.springframework.stereotype.Repository;

@Repository
public class AutorizzazioniSubentriConcDAOImpl extends BaseDAOImpl<AutorizzazioniSubentriConc, PkId> implements AutorizzazioniSubentriConcDAO {

    @Override
    public Class<AutorizzazioniSubentriConc> getEntityClass() {

	return AutorizzazioniSubentriConc.class;
    }
}
