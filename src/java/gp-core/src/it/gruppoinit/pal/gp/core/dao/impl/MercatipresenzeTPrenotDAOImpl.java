package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.MercatipresenzeTPrenotDAO;
import it.gruppoinit.pal.gp.core.domain.MercatipresenzeTPrenot;
import it.gruppoinit.pal.gp.core.domain.PkId;

import org.springframework.stereotype.Repository;

@Repository
public class MercatipresenzeTPrenotDAOImpl extends BaseDAOImpl<MercatipresenzeTPrenot, PkId> implements MercatipresenzeTPrenotDAO {

    @Override
    public Class<MercatipresenzeTPrenot> getEntityClass() {

	return MercatipresenzeTPrenot.class;
    }
}
