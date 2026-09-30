package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.TipimovimentoDisDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.TipimovimentoDis;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class TipimovimentoDisDAOImpl extends BaseDAOImpl<TipimovimentoDis, PkId> implements TipimovimentoDisDAO {

    @Override
    public Class<TipimovimentoDis> getEntityClass() {

	return TipimovimentoDis.class;
    }

    @Override
    public List<TipimovimentoDis> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
	//
    }
}
