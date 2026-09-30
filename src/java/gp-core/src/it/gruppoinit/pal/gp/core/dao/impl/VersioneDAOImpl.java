package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.VersioneDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.Versione;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class VersioneDAOImpl extends BaseDAOImpl<Versione, String> implements VersioneDAO {

    @Override
    public List<Versione> findAll(Integer firstResult, Integer maxResult) {

	return this.findAll(firstResult, maxResult, DAOEnum.FIND_ALL, "versione", DAOOrderTypeEnum.DESC);
    }

    @Override
    public Class<Versione> getEntityClass() {

	return Versione.class;
    }
}
