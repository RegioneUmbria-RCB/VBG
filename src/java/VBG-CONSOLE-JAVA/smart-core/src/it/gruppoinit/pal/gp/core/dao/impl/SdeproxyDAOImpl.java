package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.SdeproxyDAO;
import it.gruppoinit.pal.gp.core.domain.Sdeproxy;

import org.springframework.stereotype.Repository;

@Repository
public class SdeproxyDAOImpl extends BaseDAOImpl<Sdeproxy, String> implements SdeproxyDAO {

    @Override
    public Class<Sdeproxy> getEntityClass() {

	return Sdeproxy.class;
    }
}
