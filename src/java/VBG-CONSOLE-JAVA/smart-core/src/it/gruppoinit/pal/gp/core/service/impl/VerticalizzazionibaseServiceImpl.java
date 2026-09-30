package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.VerticalizzazionibaseDAO;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazionibase;
import it.gruppoinit.pal.gp.core.service.VerticalizzazionibaseService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author gianpaolot
 */
@Service
public class VerticalizzazionibaseServiceImpl extends BaseServiceImpl<Verticalizzazionibase, String> implements VerticalizzazionibaseService {

    private VerticalizzazionibaseDAO verticalizzazionibaseDAO;

    @Autowired
    public void setVerticalizzazionibaseDAO(VerticalizzazionibaseDAO verticalizzazionibaseDAO) {

	this.verticalizzazionibaseDAO = verticalizzazionibaseDAO;
    }

    @Override
    protected Class<Verticalizzazionibase> getEntityClass() {

	return Verticalizzazionibase.class;
    }

    @Override
    public List<Verticalizzazionibase> findAll(Integer firstResult, Integer maxResult) {

	return verticalizzazionibaseDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(Verticalizzazionibase entity) {

	if (validateEntity(entity)) {
	    verticalizzazionibaseDAO.insert(entity);
	}
    }

    @Override
    public Verticalizzazionibase findById(String modulo) {

	return verticalizzazionibaseDAO.findById(modulo);
    }

    @Override
    public void update(Verticalizzazionibase entity) {

	if (validateEntity(entity)) {
	    verticalizzazionibaseDAO.update(entity);
	}
    }

    @Override
    public void delete(Verticalizzazionibase entity) {

	if (isDeleteAllowed(entity)) {
	    verticalizzazionibaseDAO.delete(entity);
	}
    }

    @Override
    public Boolean isConfigurataPerComuneAndSoftware(Verticalizzazionibase verticalizzazionibase) {

	return verticalizzazionibaseDAO.isConfigurataPerComuneAndSoftware(verticalizzazionibase);
    }

    @Override
    public List<Verticalizzazionibase> findAllAndCheckVerticalizzazionibaseconfigurate() {

	List<Verticalizzazionibase> list = this.findAll(null, null);
	List<Verticalizzazionibase> risultato = new ArrayList<Verticalizzazionibase>();
	for (Verticalizzazionibase verticalizzazionibase : list) {
	    Boolean isVerticalizzazioneConfigurata = this.isConfigurataPerComuneAndSoftware(verticalizzazionibase);
	    verticalizzazionibase.setFlagConfigurata(isVerticalizzazioneConfigurata);
	    risultato.add(verticalizzazionibase);
	}
	return risultato;
    }
    // protected boolean isDeleteAllowed(Verticalizzazionibase entity) {
    //
    // boolean delete = true;
    // List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
    // TODO_validare_la_delete
    // // esempio:
    // // if (entity.getList().size() > 0) {
    // // _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "NOME_TABELLA", null));
    // // }
    // if (!_ivs.isEmpty()) {
    // this.throwValidationMessages(_ivs);
    // }
    // return delete;
    // }
}
