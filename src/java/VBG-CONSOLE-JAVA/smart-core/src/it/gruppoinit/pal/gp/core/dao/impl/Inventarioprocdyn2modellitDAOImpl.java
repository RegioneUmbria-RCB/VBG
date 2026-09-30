package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.Inventarioprocdyn2modellitDAO;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocdyn2modellit;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocdyn2modellitId;

import org.springframework.stereotype.Repository;

@Repository
public class Inventarioprocdyn2modellitDAOImpl extends BaseDAOImpl<Inventarioprocdyn2modellit, Inventarioprocdyn2modellitId> implements
	Inventarioprocdyn2modellitDAO {

    @Override
    public Class<Inventarioprocdyn2modellit> getEntityClass() {

	return Inventarioprocdyn2modellit.class;
    }
}
