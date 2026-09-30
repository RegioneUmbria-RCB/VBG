package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.CatastoDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.Catasto;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class CatastoDAOImpl extends BaseDAOImpl<Catasto, String> implements CatastoDAO {

    @Override
    public Class<Catasto> getEntityClass() {

	return Catasto.class;
    }

    @Override
    public List<Catasto> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_ALL, "descrizione", DAOOrderTypeEnum.ASC);
    }
}
