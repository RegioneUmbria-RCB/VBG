package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.VerticalizzazioniparametribaseDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametribase;
import it.gruppoinit.pal.gp.core.domain.VerticalizzazioniparametribaseId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author gianpaolot
 */
@Repository
public class VerticalizzazioniparametribaseDAOImpl extends BaseDAOImpl<Verticalizzazioniparametribase, VerticalizzazioniparametribaseId> implements
	VerticalizzazioniparametribaseDAO {

    @Override
    public Class<Verticalizzazioniparametribase> getEntityClass() {

	return Verticalizzazioniparametribase.class;
    }

    @Override
    public List<Verticalizzazioniparametribase> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_ALL, null, null);
    }
}
