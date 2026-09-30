package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.FesteSagreDAO;
import it.gruppoinit.pal.gp.core.domain.FesteSagre;
import it.gruppoinit.pal.gp.core.domain.PkId;

import org.springframework.stereotype.Repository;

@Repository
public class FesteSagreDAOImpl extends BaseDAOImpl<FesteSagre, PkId> implements FesteSagreDAO {

    @Override
    public Class<FesteSagre> getEntityClass() {

	return FesteSagre.class;
    }
}
