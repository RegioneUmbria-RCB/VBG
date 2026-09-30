package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.AnagrafeImpresaDAO;
import it.gruppoinit.pal.gp.core.domain.AnagrafeImpresa;
import it.gruppoinit.pal.gp.core.domain.PkId;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class AnagrafeImpresaDAOImpl extends BaseDAOImpl<AnagrafeImpresa, PkId> implements AnagrafeImpresaDAO {

    @Override
    public Class<AnagrafeImpresa> getEntityClass() {

	return AnagrafeImpresa.class;
    }
}
