package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.TipimovimentooneriDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Tipimovimentooneri;
import it.gruppoinit.pal.gp.core.domain.TipimovimentooneriId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author gianpaolot
 */
@Repository
public class TipimovimentooneriDAOImpl extends BaseDAOImpl<Tipimovimentooneri, TipimovimentooneriId> implements TipimovimentooneriDAO {

    @Override
    public Class<Tipimovimentooneri> getEntityClass() {

	return Tipimovimentooneri.class;
    }

    @Override
    public List<Tipimovimentooneri> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_ALL, null, null);
    }
}
