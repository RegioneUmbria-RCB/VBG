package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.FoArjDomandeDAO;
import it.gruppoinit.pal.gp.core.domain.FoArjDomande;
import it.gruppoinit.pal.gp.core.domain.PkId;

import org.springframework.stereotype.Repository;

@Repository
public class FoArjDomandeDAOImpl extends BaseDAOImpl<FoArjDomande, PkId> implements FoArjDomandeDAO {

    @Override
    public Class getEntityClass() {

	return FoArjDomande.class;
    }
}
