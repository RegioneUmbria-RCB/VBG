package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.LinkutiliDAO;
import it.gruppoinit.pal.gp.core.domain.Linkutili;
import it.gruppoinit.pal.gp.core.domain.PkId;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author francescop
 */
@Repository
public class LinkutiliDAOImpl extends BaseDAOImpl<Linkutili, PkId> implements LinkutiliDAO {

    @Override
    public Class<Linkutili> getEntityClass() {

	return Linkutili.class;
    }
}
