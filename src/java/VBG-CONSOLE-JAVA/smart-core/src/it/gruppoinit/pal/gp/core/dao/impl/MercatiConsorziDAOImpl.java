package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.MercatiConsorziDAO;
import it.gruppoinit.pal.gp.core.domain.MercatiConsorzi;
import it.gruppoinit.pal.gp.core.domain.PkId;

import org.springframework.stereotype.Repository;

@Repository
public class MercatiConsorziDAOImpl extends BaseDAOImpl<MercatiConsorzi, PkId> implements MercatiConsorziDAO {

    @Override
    public Class<MercatiConsorzi> getEntityClass() {

	return MercatiConsorzi.class;
    }
}
