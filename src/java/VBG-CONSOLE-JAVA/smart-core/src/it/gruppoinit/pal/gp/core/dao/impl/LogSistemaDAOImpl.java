package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.LogSistemaDAO;
import it.gruppoinit.pal.gp.core.domain.LogSistema;
import it.gruppoinit.pal.gp.core.domain.PkId;

import org.springframework.stereotype.Repository;

@Repository
public class LogSistemaDAOImpl extends BaseDAOImpl<LogSistema, PkId> implements LogSistemaDAO {

    @Override
    public Class<LogSistema> getEntityClass() {

	return LogSistema.class;
    }
}
