package it.gruppoinit.pal.gp.core.features.scadenzario;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.ResponsabiliScadenzario;
import it.gruppoinit.pal.gp.core.features.scadenzario.dao.IResponsabiliScadenzarioDAO;
import it.gruppoinit.pal.gp.core.features.scadenzario.service.IResponsabiliScadenzarioService;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.security.LoggedUser;
import it.gruppoinit.pal.gp.core.service.ResponsabiliService;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;
import it.gruppoinit.pal.gp.core.service.impl.BaseServiceImpl;

@Service
public class ResponsabiliScadenzarioServiceImpl extends BaseServiceImpl<ResponsabiliScadenzario, PkId> implements IResponsabiliScadenzarioService {

    IResponsabiliScadenzarioDAO responsabiliScadenzarioDAO;
    private ResponsabiliService responsabiliService;
    private UserSecurityService userSecurityService;

    @Autowired
    public void setResponsabiliScadenzarioDAO(IResponsabiliScadenzarioDAO responsabiliScadenzarioDAO) {

	this.responsabiliScadenzarioDAO = responsabiliScadenzarioDAO;
    }

    @Autowired
    public void setResponsabiliService(ResponsabiliService responsabiliService) {

	this.responsabiliService = responsabiliService;
    }

    @Autowired
    public void setUserSecurityService(UserSecurityService userSecurityService) {

	this.userSecurityService = userSecurityService;
    }

    @Override
    public void insert(ResponsabiliScadenzario entity) {

	if (validateEntity(entity)) {
	    responsabiliScadenzarioDAO.insert(entity);
	}
    }

    @Override
    public void update(ResponsabiliScadenzario entity) {

	if (validateEntity(entity)) {
	    responsabiliScadenzarioDAO.update(entity);
	}
    }

    @Override
    public void delete(ResponsabiliScadenzario entity) {

	if (isDeleteAllowed(entity)) {
	    responsabiliScadenzarioDAO.delete(entity);
	}
    }

    @Override
    public List<ResponsabiliScadenzario> findAll(Integer firstResult, Integer maxResult) {

	return responsabiliScadenzarioDAO.findAll(firstResult, maxResult);
    }

    @Override
    public ResponsabiliScadenzario findById(PkId id) {

	return responsabiliScadenzarioDAO.findById(id);
    }

    @Override
    protected Class<ResponsabiliScadenzario> getEntityClass() {

	return responsabiliScadenzarioDAO.getEntityClass();
    }

    @Override
    public String leggiParametriConfigurazioneScadenzario(String ambito, String chiave, HttpServletRequest request) {

	LoggedUser userDetail = (LoggedUser) getCurrentlyAuthenticatedUser();
	PkId idResponsabile = new PkId(userDetail.getCodiceResponsabile());
	ResponsabiliScadenzario resp = this.findByAmbitoRespChiave(ambito, idResponsabile.getCodice(), chiave);
	return resp != null ? resp.getValore() : null;
    }

    @Override
    public ResponsabiliScadenzario findByAmbitoRespChiave(String ambito, Integer responsabile, String chiave) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("ambito", ambito, String.class));
	fr.addFilterField(FilterUtils.equals("responsabileId", responsabile, Integer.class));
	fr.addFilterField(FilterUtils.equals("chiave", chiave, String.class));
	ft.addRestriction(fr);
	List<ResponsabiliScadenzario> list = responsabiliScadenzarioDAO.findByFilterTable(ft);
	if (list.size() == 0) {
	    return null;
	}
	return list.get(0);
    }

    private UserDetails getCurrentlyAuthenticatedUser() {

	return userSecurityService.getCurrentlyAuthenticatedUser();
    }

    @Override
    public String gestisciParametriConfigurazioneScadenzario(String ambito, String chiave, String valore) {

	LoggedUser userDetail = (LoggedUser) getCurrentlyAuthenticatedUser();
	PkId idResponsabile = new PkId(userDetail.getCodiceResponsabile());
	Responsabili responsabile = responsabiliService.findById(idResponsabile);
	ResponsabiliScadenzario resp = this.findByAmbitoRespChiave(ambito, idResponsabile.getCodice(), chiave);
	if (resp == null) {
	    resp = new ResponsabiliScadenzario();
	    resp.setAmbito(ambito);
	    resp.setChiave(chiave);
	    resp.setResponsabile(responsabile);
	    resp.setValore(valore);
	    this.insert(resp);
	} else {
	    valore = StringUtils.defaultIfEmpty(resp.getValore(), "");
	}
	return valore;
    }

    @Override
    public void aggiornaParametriConfigurazioneScadenzario(String ambito, String chiave, String valore) {

	LoggedUser userDetail = (LoggedUser) getCurrentlyAuthenticatedUser();
	PkId idResponsabile = new PkId(userDetail.getCodiceResponsabile());
	ResponsabiliScadenzario resp = this.findByAmbitoRespChiave(ambito, idResponsabile.getCodice(), chiave);
	if (resp != null && resp.getValore() != null && !resp.getValore().equalsIgnoreCase(valore)) {
	    resp.setValore(valore);
	    responsabiliScadenzarioDAO.update(resp);
	}
    }
}
