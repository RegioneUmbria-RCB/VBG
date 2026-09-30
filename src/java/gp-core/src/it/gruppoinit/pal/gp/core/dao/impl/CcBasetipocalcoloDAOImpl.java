package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.CcBasetipocalcoloDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.CcBasetipocalcolo;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class CcBasetipocalcoloDAOImpl extends BaseDAOImpl<CcBasetipocalcolo, String> implements CcBasetipocalcoloDAO {

    @Override
    public Class<CcBasetipocalcolo> getEntityClass() {

	return CcBasetipocalcolo.class;
    }

    @Override
    public List<CcBasetipocalcolo> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_ALL, "tipocalcolo", DAOOrderTypeEnum.ASC);
    }
}
