package it.gruppoinit.pal.gp.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.dao.ComuniassociatiDAO;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Comuniassociati;
import it.gruppoinit.pal.gp.core.domain.ComuniassociatiId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Responsabilicomuni;
import it.gruppoinit.pal.gp.core.exception.OperatoreNonHaComuniConfiguratiException;
import it.gruppoinit.pal.gp.core.features.comuniassociati.ComuneAssociato;
import it.gruppoinit.pal.gp.core.service.ComuniassociatiService;
import it.gruppoinit.pal.gp.core.service.ResponsabilicomuniService;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;

@Service
public class ComuniassociatiServiceImpl extends BaseServiceImpl<Comuniassociati, ComuniassociatiId> implements ComuniassociatiService {

    private ComuniassociatiDAO comuniassociatiDAO;
    private ResponsabilicomuniService responsabilicomuniService;
    private UserSecurityService userSecurityService;

    @Autowired
    public void setComuniassociatiDAO(ComuniassociatiDAO comuniassociatiDAO) {

	this.comuniassociatiDAO = comuniassociatiDAO;
    }

    @Autowired
    public void setResponsabilicomuniService(ResponsabilicomuniService responsabilicomuniService) {

	this.responsabilicomuniService = responsabilicomuniService;
    }

    @Autowired
    public void setUserSecurityService(UserSecurityService userSecurityService) {

	this.userSecurityService = userSecurityService;
    }

    @Override
    protected Class<Comuniassociati> getEntityClass() {

	return Comuniassociati.class;
    }

    @Override
    public void delete(Comuniassociati entity) {

	throw new UnsupportedOperationException();
    }

    @Override
    public List<Comuniassociati> findAll(Integer firstResult, Integer maxResult) {

	return comuniassociatiDAO.findAll(firstResult, maxResult);
    }

    @Override
    public Comuniassociati findById(ComuniassociatiId id) {

	return comuniassociatiDAO.findById(id);
    }

    @Override
    public void insert(Comuniassociati entity) {

	//throw new UnsupportedOperationException();
	if (validateEntity(entity)) {
	    comuniassociatiDAO.insert(entity);
	}
    }

    @Override
    public void update(Comuniassociati entity) {

	//throw new UnsupportedOperationException();
	if (validateEntity(entity)) {
	    comuniassociatiDAO.update(entity);
	}
    }

    @Override
    public List<Comuniassociati> findByIdcomune(String idcomune) {

	return comuniassociatiDAO.findByIdcomune(idcomune);
    }

    @Override
    public List<Responsabilicomuni> checkComuniAbilitatiPerResponsabile() {

	List<Comuniassociati> comuniassociatis = this.findByIdcomune(ORMHelper.getIdcomune());
	if (comuniassociatis.size() > 0) {
	    // se la lista tornata dai comuni ha più di un record allora l'installazione è di tipo COMUNIASSOCIATI e
	    // posso controllare se l'operatore ha abilitati i comuni
	    Responsabili responsabile = (Responsabili) userSecurityService.getCurrentlyAuthenticatedUserDetails();
	    if (null == responsabile) {
		throw new SecurityException();
	    }
	    List<Responsabilicomuni> listaComuni = responsabilicomuniService.findByOperatore(responsabile);
	    if (listaComuni == null || listaComuni.size() == 0) {
		throw new OperatoreNonHaComuniConfiguratiException();
	    }
	    return listaComuni;
	}
	return new ArrayList<Responsabilicomuni>();
    }

    @Override
    public Boolean isComuniassociati(String idcomune) {

	Boolean risultato = false;
	List<Comuniassociati> list = comuniassociatiDAO.findByIdcomune(idcomune);
	if (!list.isEmpty() && list.size() > 1) {
	    return true;
	}
	return risultato;
    }

    @Override
    public List<ComuneAssociato> findAll() {

	List<Comuniassociati> comuni = comuniassociatiDAO.findByIdcomune(ORMHelper.getIdcomune());
	if (comuni.isEmpty()) {
	    return new ArrayList<ComuneAssociato>();
	}
	List<ComuneAssociato> elenco = new ArrayList<ComuneAssociato>();
	for (Comuniassociati comune : comuni) {
	    elenco.add(ComuneAssociato.FromComuniassociati(comune));
	}
	return elenco;
    }
}
