package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.LdpDecodificheDAO;
import it.gruppoinit.pal.gp.core.domain.LdpDecodifiche;
import it.gruppoinit.pal.gp.core.domain.PkId;

import org.springframework.stereotype.Repository;

@Repository
public class LdpDecodificheDAOImpl extends BaseDAOImpl<LdpDecodifiche, PkId> implements LdpDecodificheDAO {

    public java.lang.Class<LdpDecodifiche> getEntityClass() {

	return LdpDecodifiche.class;
    };
}
