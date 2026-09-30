package it.gruppoinit.pal.gp.core.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.dao.ComuniassociatiDAO;
import it.gruppoinit.pal.gp.core.dao.MailConfigComuniDAO;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.Comuniassociati;
import it.gruppoinit.pal.gp.core.domain.MailConfig;
import it.gruppoinit.pal.gp.core.domain.MailConfigComuni;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.service.MailConfigComuniService;
import it.gruppoinit.pal.gp.core.service.helper.MailConfigComuniDTO;

@Service
public class MailConfigComuniServiceImpl extends BaseServiceImpl<MailConfigComuni, PkId> implements MailConfigComuniService {

    private MailConfigComuniDAO mailConfigComuniDAO;
    private ComuniassociatiDAO comuniassociatiDAO;

    @Autowired
    public void setMailConfigComuniDAO(MailConfigComuniDAO mailConfigComuniDAO) {

	this.mailConfigComuniDAO = mailConfigComuniDAO;
    }

    @Autowired
    public void setComuniassociatiDAO(ComuniassociatiDAO comuniassociatiDAO) {

	this.comuniassociatiDAO = comuniassociatiDAO;
    }

    @Override
    public void insert(MailConfigComuni entity) {

	if (validateEntity(entity)) {
	    mailConfigComuniDAO.insert(entity);
	}
    }

    @Override
    public void update(MailConfigComuni entity) {

	if (validateEntity(entity)) {
	    mailConfigComuniDAO.update(entity);
	}
    }

    @Override
    public void delete(MailConfigComuni entity) {

	if (validateEntity(entity)) {
	    mailConfigComuniDAO.delete(entity);
	}
    }

    @Override
    public List<MailConfigComuni> findAll(Integer firstResult, Integer maxResult) {

	return mailConfigComuniDAO.findAll(firstResult, maxResult);
    }

    @Override
    public MailConfigComuni findById(PkId id) {

	return mailConfigComuniDAO.findById(id);
    }

    @Override
    protected Class<MailConfigComuni> getEntityClass() {

	return MailConfigComuni.class;
    }

    @Override
    public List<Comuni> findComuniByMailConfigId(Integer id) {

	List<MailConfigComuni> mailConfigComuni = mailConfigComuniDAO.findByMailConfigId(id);
	List<Comuni> comuni = new ArrayList<Comuni>();
	for (MailConfigComuni mailConfigComune : mailConfigComuni) {
	    comuni.add(mailConfigComune.getComune());
	}
	return comuni;
    }

    @Override
    public List<Comuni> findComuneByDescrizione(String descComune) {

	return null;
    }

    @Override
    public List<MailConfigComuniDTO> findByMailConfigId(Integer id) {

	List<MailConfigComuni> mailConfigComuni = mailConfigComuniDAO.findByMailConfigId(id);
	List<MailConfigComuniDTO> mailConfigComuniDTO = new ArrayList<MailConfigComuniDTO>();
	for (MailConfigComuni mailConfigComune : mailConfigComuni) {
	    MailConfigComuniDTO m = new MailConfigComuniDTO();
	    m.setId(mailConfigComune.getId().getCodice());
	    m.setCodiceComune(mailConfigComune.getComune().getCodicecomune());
	    m.setComune(mailConfigComune.getComune().getComune());
	    mailConfigComuniDTO.add(m);
	}
	return mailConfigComuniDTO;
    }

    @Override
    public void insert(MailConfig mailConfig) {

	List<Comuniassociati> comuniassociati = comuniassociatiDAO.findByIdcomune(ORMHelper.getIdcomune());
	for (Comuniassociati comuniassociato : comuniassociati) {
	    MailConfigComuni mcfgc = new MailConfigComuni();
	    mcfgc.setComune(comuniassociato.getComune());
	    mcfgc.setMailConfig(mailConfig);
	    this.insert(mcfgc);
	}
    }

    @Override
    public void delete(Integer codice) {

	MailConfigComuni mcfgc = new MailConfigComuni();
	mcfgc.setId(new PkId(codice));
	this.delete(mcfgc);
    }

    @Override
    public void deleteByMailConfigId(Integer id) {

	List<MailConfigComuni> mailConfigComuni = mailConfigComuniDAO.findByMailConfigId(id);
	for (MailConfigComuni mailConfigComune : mailConfigComuni) {
	    this.delete(mailConfigComune);
	}
    }

    @Override
    public List<MailConfig> findAccountByResposabile(Responsabili responsabili) {

	Set<MailConfigComuni> mailConfigComuni = mailConfigComuniDAO.findListaByResponsabile(responsabili);
	List<MailConfig> m = new ArrayList<MailConfig>();
	for (MailConfigComuni mailConfigComune : mailConfigComuni) {
	    m.add(mailConfigComune.getMailConfig());
	}
	return m;
    }
}
