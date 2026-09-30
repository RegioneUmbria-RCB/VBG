package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.ConfApplicativeDAO;
import it.gruppoinit.pal.gp.core.domain.ConfApplicative;
import it.gruppoinit.pal.gp.core.domain.ConfApplicativeId;

import org.springframework.stereotype.Repository;

@Repository
public class ConfApplicativeDAOImpl extends BaseDAOImpl<ConfApplicative, ConfApplicativeId> implements ConfApplicativeDAO {

    @Override
    public Class<ConfApplicative> getEntityClass() {

	return ConfApplicative.class;
    }
}
