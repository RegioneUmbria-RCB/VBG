package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.TempLinkallegatiDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.TempLinkallegati;

import org.springframework.stereotype.Repository;

@Repository
public class TempLinkallegatiDAOImpl extends BaseDAOImpl<TempLinkallegati, PkId> implements TempLinkallegatiDAO {

    @Override
    public Class<TempLinkallegati> getEntityClass() {

	return TempLinkallegati.class;
    }
}
