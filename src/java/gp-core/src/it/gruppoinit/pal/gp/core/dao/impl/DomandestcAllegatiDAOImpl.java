package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.DomandestcAllegatiDAO;
import it.gruppoinit.pal.gp.core.domain.DomandestcAllegati;
import it.gruppoinit.pal.gp.core.domain.PkId;

import org.springframework.stereotype.Repository;

@Repository
public class DomandestcAllegatiDAOImpl extends BaseDAOImpl<DomandestcAllegati, PkId> implements DomandestcAllegatiDAO {

    @Override
    public Class<DomandestcAllegati> getEntityClass() {

	return DomandestcAllegati.class;
    }
}
