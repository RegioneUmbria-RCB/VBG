package it.gruppoinit.pal.gp.core.dao.impl;

/**
 * @author gianpaolot
 */
import it.gruppoinit.pal.gp.core.dao.ProtocolloRegistriDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.ProtocolloRegistri;

import org.springframework.stereotype.Repository;

@Repository
public class ProtocolloRegistriDAOImpl extends BaseDAOImpl<ProtocolloRegistri, PkId> implements ProtocolloRegistriDAO {

    @Override
    public Class<ProtocolloRegistri> getEntityClass() {

	return ProtocolloRegistri.class;
    }
}
