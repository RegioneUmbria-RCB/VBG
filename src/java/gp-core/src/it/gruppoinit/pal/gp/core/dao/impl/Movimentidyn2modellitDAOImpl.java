package it.gruppoinit.pal.gp.core.dao.impl;

import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.Movimentidyn2modellitDAO;
import it.gruppoinit.pal.gp.core.domain.Movimentidyn2modellit;
import it.gruppoinit.pal.gp.core.domain.Movimentidyn2modellitId;

@Repository
public class Movimentidyn2modellitDAOImpl extends BaseDAOImpl<Movimentidyn2modellit, Movimentidyn2modellitId> implements Movimentidyn2modellitDAO {

    @Override
    public Class<Movimentidyn2modellit> getEntityClass() {

	return Movimentidyn2modellit.class;
    }
}
