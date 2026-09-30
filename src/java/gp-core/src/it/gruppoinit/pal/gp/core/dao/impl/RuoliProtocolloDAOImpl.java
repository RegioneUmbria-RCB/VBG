package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.RuoliProtocolloDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.RuoliProtocollo;

import org.springframework.stereotype.Repository;

@Repository
public class RuoliProtocolloDAOImpl extends BaseDAOImpl<RuoliProtocollo, PkId> implements RuoliProtocolloDAO {

    @Override
    public Class<RuoliProtocollo> getEntityClass() {

	return RuoliProtocollo.class;
    }
}
