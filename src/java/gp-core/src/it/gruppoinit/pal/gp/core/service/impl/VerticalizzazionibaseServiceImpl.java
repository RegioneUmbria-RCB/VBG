package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.VerticalizzazionibaseDAO;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazionibase;
import it.gruppoinit.pal.gp.core.service.VerticalizzazionibaseService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
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
	Collections.sort(risultato, new Comparator<Verticalizzazionibase>() {

	    @Override
	    public int compare(Verticalizzazionibase o1, Verticalizzazionibase o2) {

		boolean isO1Configurata = BooleanUtils.isTrue(o1.getFlagConfigurata());
		boolean isO2Configurata = BooleanUtils.isTrue(o2.getFlagConfigurata());
		if (isO1Configurata != isO2Configurata) {
		    return Boolean.valueOf(isO2Configurata).compareTo(Boolean.valueOf(isO1Configurata));
		}
		String moduloO1 = StringUtils.defaultIfEmpty(o1.getModulo(), "ZZZZZZZZZZZZZ");
		String moduloO2 = StringUtils.defaultIfEmpty(o2.getModulo(), "ZZZZZZZZZZZZZ");
		return moduloO1.compareTo(moduloO2);
	    }
	});
	return risultato;
    }
}
