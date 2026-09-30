package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.RegIoAssegnazioniDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.RegIoAssegnazioni;

import org.springframework.stereotype.Repository;

@Repository
public class RegIoAssegnazioniDAOImpl extends BaseDAOImpl<RegIoAssegnazioni, PkId> implements RegIoAssegnazioniDAO {

    @Override
    public Class<RegIoAssegnazioni> getEntityClass() {

	return RegIoAssegnazioni.class;
    }
}
