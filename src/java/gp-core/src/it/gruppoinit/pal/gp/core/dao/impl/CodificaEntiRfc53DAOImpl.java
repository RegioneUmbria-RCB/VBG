package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.CodificaEntiRfc53DAO;
import it.gruppoinit.pal.gp.core.domain.CodificaEntiRfc53;

import org.springframework.stereotype.Repository;

@Repository
public class CodificaEntiRfc53DAOImpl extends BaseDAOImpl<CodificaEntiRfc53, String> implements CodificaEntiRfc53DAO {

    @Override
    public Class<CodificaEntiRfc53> getEntityClass() {

	return CodificaEntiRfc53.class;
    }
}
