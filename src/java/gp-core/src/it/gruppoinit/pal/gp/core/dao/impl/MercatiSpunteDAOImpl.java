package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.MercatiSpunteDAO;
import it.gruppoinit.pal.gp.core.domain.MercatiSpunte;
import it.gruppoinit.pal.gp.core.domain.PkId;

import org.springframework.stereotype.Repository;

@Repository
public class MercatiSpunteDAOImpl extends BaseDAOImpl<MercatiSpunte, PkId> implements MercatiSpunteDAO {

    @Override
    public Class<MercatiSpunte> getEntityClass() {

	return MercatiSpunte.class;
    }
}
