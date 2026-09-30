package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.CategorieEventiMailDAO;
import it.gruppoinit.pal.gp.core.domain.CategorieEventiMail;
import it.gruppoinit.pal.gp.core.domain.PkId;

import org.springframework.stereotype.Repository;

@Repository
public class CategorieEventiMailDAOImpl extends BaseDAOImpl<CategorieEventiMail, PkId> implements CategorieEventiMailDAO {

    @Override
    public Class<CategorieEventiMail> getEntityClass() {

	return CategorieEventiMail.class;
    }
}
