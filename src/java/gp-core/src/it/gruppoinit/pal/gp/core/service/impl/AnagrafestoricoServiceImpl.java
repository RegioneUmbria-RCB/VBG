package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.AnagrafestoricoDAO;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Anagrafestorico;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.service.AnagrafestoricoService;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnagrafestoricoServiceImpl extends BaseServiceImpl<Anagrafestorico, PkId> implements AnagrafestoricoService {

    private static final Logger log = LoggerFactory.getLogger(AnagrafestoricoServiceImpl.class);
    private AnagrafestoricoDAO anagrafestoricoDAO;
    private UserSecurityService userSecurityService;

    @Autowired
    public void setAnagrafestoricoDAO(AnagrafestoricoDAO anagrafestoricoDAO) {

	this.anagrafestoricoDAO = anagrafestoricoDAO;
    }

    @Autowired
    public void setUserSecurityService(UserSecurityService userSecurityService) {

	this.userSecurityService = userSecurityService;
    }

    @Override
    public void insert(Anagrafestorico entity) {

	Responsabili resp = getResponsabileModifica();
	entity.setResponsabili(resp);
	anagrafestoricoDAO.insert(entity);
    }

    @Override
    public void update(Anagrafestorico entity) {

	Responsabili resp = getResponsabileModifica();
	entity.setResponsabili(resp);
	anagrafestoricoDAO.update(entity);
    }

    @Override
    public void delete(Anagrafestorico entity) {

	anagrafestoricoDAO.delete(entity);
    }

    @Override
    public List<Anagrafestorico> findAll(Integer firstResult, Integer maxResult) {

	return anagrafestoricoDAO.findAll(firstResult, maxResult);
    }

    @Override
    public Anagrafestorico findById(PkId id) {

	return anagrafestoricoDAO.findById(id);
    }

    @Override
    protected Class<Anagrafestorico> getEntityClass() {

	return getEntityClass();
    }

    @Override
    public Anagrafestorico findStoricoId(Anagrafe entity, Date data) {

	return anagrafestoricoDAO.findStoricoId(entity, data);
    }

    @Override
    public Anagrafestorico findUltimoAnagrafestoricoByAnagrafe(Anagrafe entity) {

	return anagrafestoricoDAO.findUltimoAnagrafestoricoByAnagrafe(entity);
    }

    @Override
    public List<Anagrafestorico> findStoricoByAnagrafe(Anagrafe entity) {

	return anagrafestoricoDAO.findStoricoByAnagrafe(entity);
    }

    @Override
    public void ricalcoloStoricoAnagrafiche(Anagrafestorico anagrafeStorico) {

	anagrafestoricoDAO.ricalcoloStoricoAnagrafiche(anagrafeStorico);
    }

    private Responsabili getResponsabileModifica() {

	Object resp = userSecurityService.getCurrentlyAuthenticatedUserDetails();
	if (resp instanceof Responsabili && EntityUtils.getNestedProperty(resp, "id.codice") != null) {
	    return (Responsabili) resp;
	} else {
	    log.warn(
		    "Non è presente un responsabile che ha effettuato la modifica dell' anagrafica, la modifica potrebbe essere stata effettuata da un sistema esterno");
	}
	return null;
    }
}
