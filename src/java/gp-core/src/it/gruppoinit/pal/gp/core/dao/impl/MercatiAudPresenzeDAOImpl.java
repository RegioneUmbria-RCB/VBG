package it.gruppoinit.pal.gp.core.dao.impl;

import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.MercatiAudPresenzeDAO;
import it.gruppoinit.pal.gp.core.domain.MercatiAudPresenze;
import it.gruppoinit.pal.gp.core.domain.PkId;

@Repository
public class MercatiAudPresenzeDAOImpl extends BaseDAOImpl<MercatiAudPresenze, PkId> implements MercatiAudPresenzeDAO{

    @Override
    public Class<MercatiAudPresenze> getEntityClass() {

	return MercatiAudPresenze.class;
    }
}
