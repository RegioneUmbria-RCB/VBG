package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.ManifAreePubblicheDAO;
import it.gruppoinit.pal.gp.core.domain.ManifAreePubbliche;
import it.gruppoinit.pal.gp.core.domain.PkId;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class ManifAreePubblicheDAOImpl extends BaseDAOImpl<ManifAreePubbliche, PkId> implements ManifAreePubblicheDAO {

    @Override
    public Class<ManifAreePubbliche> getEntityClass() {

	return ManifAreePubbliche.class;
    }
}
