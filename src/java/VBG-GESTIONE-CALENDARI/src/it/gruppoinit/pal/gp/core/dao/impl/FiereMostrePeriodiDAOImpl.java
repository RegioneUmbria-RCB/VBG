package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.FiereMostrePeriodiDAO;
import it.gruppoinit.pal.gp.core.domain.FiereMostrePeriodi;
import it.gruppoinit.pal.gp.core.domain.PkId;

import org.springframework.stereotype.Repository;

@Repository
public class FiereMostrePeriodiDAOImpl extends BaseDAOImpl<FiereMostrePeriodi, PkId> implements FiereMostrePeriodiDAO {

    @Override
    public Class<FiereMostrePeriodi> getEntityClass() {

	return FiereMostrePeriodi.class;
    }
}
