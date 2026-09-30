package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.MercatiCategorieDAO;
import it.gruppoinit.pal.gp.core.domain.MercatiCategorie;
import it.gruppoinit.pal.gp.core.domain.PkId;

import org.springframework.stereotype.Repository;

@Repository
public class MercatiCategorieDAOImpl extends BaseDAOImpl<MercatiCategorie, PkId> implements MercatiCategorieDAO {

    @Override
    public Class<MercatiCategorie> getEntityClass() {

	return MercatiCategorie.class;
    }
}
