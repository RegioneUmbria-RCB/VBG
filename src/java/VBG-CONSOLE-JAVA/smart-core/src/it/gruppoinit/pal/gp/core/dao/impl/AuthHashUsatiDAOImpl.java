package it.gruppoinit.pal.gp.core.dao.impl;

import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.AuthHashUsatiDAO;
import it.gruppoinit.pal.gp.core.domain.AuthHashUsati;

@Repository
public class AuthHashUsatiDAOImpl extends BaseDAOImpl<AuthHashUsati, String> implements AuthHashUsatiDAO {

    @Override
    public Class<AuthHashUsati> getEntityClass() {

	return AuthHashUsati.class;
    }
}
