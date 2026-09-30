package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.Movimentidyn2modellitDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Movimentidyn2modellit;
import it.gruppoinit.pal.gp.core.domain.Movimentidyn2modellitId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.Movimentidyn2modellitService;

import java.util.List;

import org.apache.commons.lang.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Movimentidyn2modellitServiceImpl extends BaseServiceImpl<Movimentidyn2modellit, Movimentidyn2modellitId>
	implements Movimentidyn2modellitService {

    private Movimentidyn2modellitDAO movimentidyn2modellitDAO;

    @Autowired
    public void setMovimentidyn2modellitDAO(Movimentidyn2modellitDAO movimentidyn2modellitDAO) {

	this.movimentidyn2modellitDAO = movimentidyn2modellitDAO;
    }

    @Override
    protected Class<Movimentidyn2modellit> getEntityClass() {

	return Movimentidyn2modellit.class;
    }

    @Override
    public void delete(Movimentidyn2modellit entity) {

	movimentidyn2modellitDAO.delete(entity);
    }

    @Override
    public List<Movimentidyn2modellit> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
    }

    @Override
    public Movimentidyn2modellit findById(Movimentidyn2modellitId id) {

	return movimentidyn2modellitDAO.findById(id);
    }

    @Override
    public void insert(Movimentidyn2modellit entity) {

	if (validateEntity(entity)) {
	    movimentidyn2modellitDAO.insert(entity);
	}
    }

    @Override
    public void update(Movimentidyn2modellit entity) {

	if (validateEntity(entity)) {
	    movimentidyn2modellitDAO.update(entity);
	}
    }

    @Override
    public List<Movimentidyn2modellit> findByCodiceMovimento(Integer codicemovimento) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codicemovimento", codicemovimento, Integer.class));
	ft.addOrder(FilterUtils.orderAsc("id.fkD2mtId"));
	ft.addRestriction(fr);
	return movimentidyn2modellitDAO.findByFilterTable(ft);
    }
}
