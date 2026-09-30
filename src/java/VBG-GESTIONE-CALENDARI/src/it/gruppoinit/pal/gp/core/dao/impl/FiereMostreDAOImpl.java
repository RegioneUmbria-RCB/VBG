package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.FiereMostreDAO;
import it.gruppoinit.pal.gp.core.domain.FiereMostre;
import it.gruppoinit.pal.gp.core.domain.PkId;

import org.springframework.stereotype.Repository;

@Repository
public class FiereMostreDAOImpl extends BaseDAOImpl<FiereMostre, PkId> implements FiereMostreDAO {

    @Override
    public Class<FiereMostre> getEntityClass() {

	return FiereMostre.class;
    }
}
