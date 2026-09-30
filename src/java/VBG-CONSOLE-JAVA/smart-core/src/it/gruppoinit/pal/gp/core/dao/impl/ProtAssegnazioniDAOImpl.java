package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.ProtAssegnazioniDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.ProtAssegnazioni;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author francescop
 */
@Repository
public class ProtAssegnazioniDAOImpl extends BaseDAOImpl<ProtAssegnazioni, PkId> implements ProtAssegnazioniDAO {

    @Override
    public Class<ProtAssegnazioni> getEntityClass() {

	return ProtAssegnazioni.class;
    }
}
