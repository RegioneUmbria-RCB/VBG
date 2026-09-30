package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.CampigraduatoriaDAO;
import it.gruppoinit.pal.gp.core.domain.Campigraduatoria;
import it.gruppoinit.pal.gp.core.domain.PkId;

import org.springframework.stereotype.Repository;

/**
 * @author lucap
 * 
 */
@Repository
public class CampigraduatoriaDAOImpl extends BaseDAOImpl<Campigraduatoria, PkId> implements CampigraduatoriaDAO {

    @Override
    public Class<Campigraduatoria> getEntityClass() {

	return Campigraduatoria.class;
    }
}
