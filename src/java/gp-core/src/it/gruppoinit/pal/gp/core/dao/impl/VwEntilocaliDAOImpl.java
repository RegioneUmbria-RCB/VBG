package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.VwEntilocaliDAO;
import it.gruppoinit.pal.gp.core.domain.VwEntilocali;

import org.springframework.stereotype.Repository;

@Repository
public class VwEntilocaliDAOImpl extends BaseDAOImpl<VwEntilocali, String> implements VwEntilocaliDAO {

    @Override
    public Class<VwEntilocali> getEntityClass() {

	return VwEntilocali.class;
    }
}
