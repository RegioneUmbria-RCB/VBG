package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.HelpDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.domain.Help;
import it.gruppoinit.pal.gp.core.domain.HelpId;
import it.gruppoinit.pal.gp.core.domain.Helpbase;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.HelpService;
import it.gruppoinit.pal.gp.core.service.HelpbaseService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HelpServiceImpl extends BaseServiceImpl<Help, HelpId> implements HelpService {

    private HelpDAO helpDAO;
    private VerticalizzazioniService verticalizzazioniService;
    private ResponsabiliService responsabiliService;
    private HelpbaseService helpbaseService;

    @Autowired
    public void setHelpDAO(HelpDAO helpDAO) {

	this.helpDAO = helpDAO;
    }

    @Autowired
    public void setVerticalizzazioniService(VerticalizzazioniService verticalizzazioniService) {

	this.verticalizzazioniService = verticalizzazioniService;
    }

    @Autowired
    public void setResponsabiliService(ResponsabiliService responsabiliService) {

	this.responsabiliService = responsabiliService;
    }

    @Autowired
    public void setHelpbaseService(HelpbaseService helpbaseService) {

	this.helpbaseService = helpbaseService;
    }

    @Override
    protected Class<Help> getEntityClass() {

	return Help.class;
    }

    @Override
    public void delete(Help entity) {

	throw new NotImplementedException();
    }

    @Override
    public List<Help> findAll(Integer firstResult, Integer maxResult) {

	return helpDAO.findAll(null, null);
    }

    @Override
    public Help findById(HelpId id) {

	return helpDAO.findById(id);
    }

    @Override
    public void insert(Help entity) {

	if (validateEntity(entity))
	    helpDAO.insert(entity);
    }

    @Override
    public void update(Help entity) {

	if (validateEntity(entity))
	    helpDAO.update(entity);
    }

    @Override
    public boolean existHelp(Integer codiceResponsabile, String contentType) {

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
	// 2-controllo se l'operatore con il codice passato al metodo può scrivere l'help
	PkId respId = new PkId(codiceResponsabile);
	Responsabili responsabili = responsabiliService.findById(respId);
	if (responsabili.getUpdatehelp() != null) {
	    if (responsabili.getUpdatehelp().booleanValue() == true) {
		return true;
	    }
	}
	// 3-controllo se esiste un record in HELP e HELPBASE con il contenttype passato al metodo
	// per l'idcomune e il software correnti ma anche per software TT
	HelpId helpId = new HelpId(contentType, 0);
	Help help = this.findById(helpId);
	if (help != null && help.getHelptext() != null) {
	    return true;
	}
	Helpbase helpbase = helpbaseService.findByContentAndSoftwares(contentType, null);
	if (helpbase != null && helpbase.getHelptext() != null) {
	    return true;
	}
	return false;
    }

    @Override
    public boolean isAllowedChangeHelp(Integer codiceResponsabile) {

	// 2-controllo se l'operatore con il codice passato al metodo può scrivere l'help
	PkId respId = new PkId(codiceResponsabile);
	Responsabili responsabili = responsabiliService.findById(respId);
	if (responsabili.getUpdatehelp() != null) {
	    return responsabili.getUpdatehelp().booleanValue();
	} else {
	    return false;
	}
    }

    @Override
    public Help findByContentAndSoftwares(String contentType, String software) {

	return helpDAO.findByContentAndSoftwares(contentType, software);
    }
}
