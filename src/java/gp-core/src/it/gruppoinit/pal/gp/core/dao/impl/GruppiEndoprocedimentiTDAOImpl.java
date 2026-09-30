package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.GruppiEndoprocedimentiTDAO;
import it.gruppoinit.pal.gp.core.domain.GruppiEndoprocedimentiT;
import it.gruppoinit.pal.gp.core.domain.PkId;

import org.springframework.stereotype.Repository;

@Repository
public class GruppiEndoprocedimentiTDAOImpl extends BaseDAOImpl<GruppiEndoprocedimentiT, PkId> implements GruppiEndoprocedimentiTDAO {

    @Override
    public Class<GruppiEndoprocedimentiT> getEntityClass() {

	return GruppiEndoprocedimentiT.class;
    }
}
