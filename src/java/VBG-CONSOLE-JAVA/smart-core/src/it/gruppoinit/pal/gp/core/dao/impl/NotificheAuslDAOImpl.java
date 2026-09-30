package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.NotificheAuslDAO;
import it.gruppoinit.pal.gp.core.domain.NotificheAusl;
import it.gruppoinit.pal.gp.core.domain.NotificheAuslId;

import org.springframework.stereotype.Repository;

@Repository
public class NotificheAuslDAOImpl extends BaseDAOImpl<NotificheAusl, NotificheAuslId> implements NotificheAuslDAO {

    @Override
    public Class<NotificheAusl> getEntityClass() {

	return NotificheAusl.class;
    }
}
