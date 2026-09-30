package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.BandiinputDAO;
import it.gruppoinit.pal.gp.core.domain.Bandiinput;
import it.gruppoinit.pal.gp.core.domain.PkId;

import org.springframework.stereotype.Repository;

@Repository
public class BandiinputDAOImpl extends BaseDAOImpl<Bandiinput, PkId> implements BandiinputDAO {

    @Override
    public Class<Bandiinput> getEntityClass() {

	return Bandiinput.class;
    }
}
