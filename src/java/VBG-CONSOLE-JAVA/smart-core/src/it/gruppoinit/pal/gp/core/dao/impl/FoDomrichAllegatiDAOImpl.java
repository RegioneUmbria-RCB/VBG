package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.FoDomrichAllegatiDAO;
import it.gruppoinit.pal.gp.core.domain.FoDomrichAllegati;
import it.gruppoinit.pal.gp.core.domain.PkId;

import org.springframework.stereotype.Repository;

@Repository
public class FoDomrichAllegatiDAOImpl extends BaseDAOImpl<FoDomrichAllegati, PkId> implements FoDomrichAllegatiDAO {

    @Override
    public Class<FoDomrichAllegati> getEntityClass() {

	return FoDomrichAllegati.class;
    }
}
