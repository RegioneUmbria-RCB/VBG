package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.VwScadenzarioDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.VwScadenzario;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class VwScadenzarioDAOImpl extends BaseDAOImpl<VwScadenzario, PkId> implements VwScadenzarioDAO {

    @Override
    public Class<VwScadenzario> getEntityClass() {

	return VwScadenzario.class;
    }
}
