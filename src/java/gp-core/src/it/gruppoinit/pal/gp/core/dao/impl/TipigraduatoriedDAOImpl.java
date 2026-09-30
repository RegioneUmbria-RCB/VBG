package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.TipigraduatoriedDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipigraduatoried;

import org.springframework.stereotype.Repository;

@Repository
public class TipigraduatoriedDAOImpl extends BaseDAOImpl<Tipigraduatoried, PkId> implements TipigraduatoriedDAO {

    @Override
    public Class<Tipigraduatoried> getEntityClass() {

	return Tipigraduatoried.class;
    }
}
