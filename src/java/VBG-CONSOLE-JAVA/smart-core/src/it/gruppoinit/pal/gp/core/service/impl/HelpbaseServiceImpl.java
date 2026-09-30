package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.HelpbaseDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.domain.Helpbase;
import it.gruppoinit.pal.gp.core.domain.HelpbaseId;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.service.HelpbaseService;
import it.gruppoinit.pal.gp.core.service.VerticalizzazioniService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HelpbaseServiceImpl extends BaseServiceImpl<Helpbase, HelpbaseId> implements HelpbaseService {

    private HelpbaseDAO helpbaseDAO;
    private VerticalizzazioniService verticalizzazioniService;

    @Autowired
    public void setHelpbaseDAO(HelpbaseDAO helpbaseDAO) {

	this.helpbaseDAO = helpbaseDAO;
    }

    @Autowired
    public void setVerticalizzazioniService(VerticalizzazioniService verticalizzazioniService) {

	this.verticalizzazioniService = verticalizzazioniService;
    }

    @Override
    protected Class<Helpbase> getEntityClass() {

	return Helpbase.class;
    }

    @Override
    public void delete(Helpbase entity) {

	throw new NotImplementedException();
    }

    @Override
    public List<Helpbase> findAll(Integer firstResult, Integer maxResult) {

	return helpbaseDAO.findAll(firstResult, maxResult);
    }

    @Override
    public Helpbase findById(HelpbaseId id) {

	return helpbaseDAO.findById(id);
    }

    @Override
    public void insert(Helpbase entity) {

	helpbaseDAO.insert(entity);
    }

    @Override
    public void update(Helpbase entity) {

	helpbaseDAO.update(entity);
    }

    @Override
    public Helpbase findByContentAndSoftwares(String contentType, String software) {

	return helpbaseDAO.findByContentAndSoftwares(contentType, software);
    }

    @Override
    public boolean isAllowedChangeHelpBase(Integer codiceResponsabile, String contentType) {

	// 1-controllo se esiste la verticalizzazione HELPBASE
	// 1.1-se esiste ed è attiva recupero il parametro CODICEOPERATORE
	// 1.2-confronto il codice recuperato con quello passato al metodo
	Verticalizzazioniparametri id = verticalizzazioniService.getVerticalizzazioniparametri("HELPBASE", "CODICEOPERATORE");
	if (id != null) {
	    String codice = id.getValore();
	    if (codice != null && codice.equals(String.valueOf(codiceResponsabile))) {
		return true;
	    }
	}
	return false;
    }
}
