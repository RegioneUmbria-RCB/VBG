package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.MercatiResponsabiliDAO;
import it.gruppoinit.pal.gp.core.domain.MercatiResponsabili;
import it.gruppoinit.pal.gp.core.domain.PkId;

import org.springframework.stereotype.Repository;

@Repository
public class MercatiResponsabiliDAOImpl extends BaseDAOImpl<MercatiResponsabili, PkId> implements MercatiResponsabiliDAO {

    @Override
    public Class<MercatiResponsabili> getEntityClass() {

	return MercatiResponsabili.class;
    }
}
