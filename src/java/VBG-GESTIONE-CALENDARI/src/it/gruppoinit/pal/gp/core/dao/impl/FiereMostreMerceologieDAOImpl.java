package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.FiereMostreMerceologieDAO;
import it.gruppoinit.pal.gp.core.domain.FiereMostreMerceologie;
import it.gruppoinit.pal.gp.core.domain.PkId;

import org.springframework.stereotype.Repository;

@Repository
public class FiereMostreMerceologieDAOImpl extends BaseDAOImpl<FiereMostreMerceologie, PkId> implements FiereMostreMerceologieDAO {

    @Override
    public Class<FiereMostreMerceologie> getEntityClass() {

	return FiereMostreMerceologie.class;
    }
}
