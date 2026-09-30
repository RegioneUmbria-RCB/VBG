package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.LogpermessiDAO;
import it.gruppoinit.pal.gp.core.domain.Logpermessi;
import it.gruppoinit.pal.gp.core.domain.PkId;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author francescop
 */
@Repository
public class LogpermessiDAOImpl extends BaseDAOImpl<Logpermessi, PkId> implements LogpermessiDAO {

    @Override
    public Class<Logpermessi> getEntityClass() {

	return Logpermessi.class;
    }
}
