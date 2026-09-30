package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.RangeRateizzazioniDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.RangeRateizzazioni;
import it.gruppoinit.pal.gp.core.service.RangeRateizzazioniService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RangeRateizzazioniServiceImpl extends BaseServiceImpl<RangeRateizzazioni, PkId> implements RangeRateizzazioniService {

    private RangeRateizzazioniDAO rangeRateizzazioniDAO;

    @Autowired
    public void setRangeRateizzazioniDAO(RangeRateizzazioniDAO rangeRateizzazioniDAO) {

	this.rangeRateizzazioniDAO = rangeRateizzazioniDAO;
    }

    @Override
    public void delete(RangeRateizzazioni entity) {

	rangeRateizzazioniDAO.delete(entity);
    }

    @Override
    public List<RangeRateizzazioni> findAll(Integer firstResult, Integer maxResult) {

	return rangeRateizzazioniDAO.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, "id.codice", DAOOrderTypeEnum.ASC);
    }

    @Override
    public RangeRateizzazioni findById(PkId id) {

	return rangeRateizzazioniDAO.findById(id);
    }

    @Override
    public void insert(RangeRateizzazioni entity) {

	if (validateEntity(entity)) {
	    rangeRateizzazioniDAO.insert(entity);
	}
    }

    @Override
    public void update(RangeRateizzazioni entity) {

	if (validateEntity(entity)) {
	    rangeRateizzazioniDAO.update(entity);
	}
    }

    @Override
    protected Class<RangeRateizzazioni> getEntityClass() {

	return RangeRateizzazioni.class;
    }
}
