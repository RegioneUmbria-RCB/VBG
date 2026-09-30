package it.gruppoinit.pal.gp.core.features.movimenti.scadenze;

import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.ScadenzeEliminate;
import it.gruppoinit.pal.gp.core.domain.ScadenzeEliminateId;

@Repository
public class ScadenzeEliminateDAOImpl extends BaseDAOImpl<ScadenzeEliminate, ScadenzeEliminateId> implements IScadenzeEliminateDAO {

    @Override
    public Class<ScadenzeEliminate> getEntityClass() {

	return ScadenzeEliminate.class;
    }
}
