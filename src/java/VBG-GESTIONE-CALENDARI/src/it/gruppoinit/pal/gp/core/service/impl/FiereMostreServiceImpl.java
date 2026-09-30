package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.FiereMostreDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.FiereMostre;
import it.gruppoinit.pal.gp.core.domain.FiereMostreMerceologie;
import it.gruppoinit.pal.gp.core.domain.FiereMostrePeriodi;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.helper.EmailHelper;
import it.gruppoinit.pal.gp.core.helper.TIPO_MANIFESTAZIONE;
import it.gruppoinit.pal.gp.core.service.FiereMostreMerceologieService;
import it.gruppoinit.pal.gp.core.service.FiereMostrePeriodiService;
import it.gruppoinit.pal.gp.core.service.FiereMostreService;
import it.gruppoinit.pal.gp.core.service.MailService;
import it.gruppoinit.pal.gp.gestionecalendari.web.command.FiereMostreMerceologieCommand;
import it.gruppoinit.pal.gp.gestionecalendari.web.command.FiereMostrePeriodiCommand;
import it.gruppoinit.pal.gp.gestionecalendari.web.command.ManifestazioniSearchFilter;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FiereMostreServiceImpl extends BaseServiceImpl<FiereMostre, PkId> implements FiereMostreService {

    private static final Logger log = LoggerFactory.getLogger(FiereMostreServiceImpl.class);
    private FiereMostreDAO fiereMostreDAO;
    private FiereMostrePeriodiService fiereMostrePeriodiService;
    private FiereMostreMerceologieService fiereMostreMerceologieService;
    @Autowired
    private MailService mailService;

    @Override
    public List<FiereMostre> findByFilter(ManifestazioniSearchFilter filter) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	if (StringUtils.isNotBlank(filter.getDenominazione())) {
	    fr.addFilterField(FilterUtils.like("denominazione", filter.getDenominazione()));
	}
	if (filter.getDal() != null) {
	    fr.addFilterField(FilterUtils.greaterEqual("dal", filter.getDal(), "fiereMostrePeriodis", Date.class));
	}
	if (filter.getAl() != null) {
	    fr.addFilterField(FilterUtils.smallerEqual("al", filter.getAl(), "fiereMostrePeriodis", Date.class));
	}
	if (StringUtils.isNotBlank(filter.getLuogoSvolgimento())) {
	    fr.addFilterField(FilterUtils.like("luogoSvolgimento", filter.getLuogoSvolgimento()));
	}
	if (StringUtils.isNotBlank(filter.getOrganizzatore())) {
	    fr.addFilterField(FilterUtils.like("organizzatore", filter.getOrganizzatore()));
	}
	if (StringUtils.isNotBlank(filter.getTipologia())) {
	    fr.addFilterField(FilterUtils.like("tipologia", filter.getTipologia()));
	}
	if (StringUtils.isNotBlank(filter.getCodicecomune())) {
	    fr.addFilterField(FilterUtils.equals("codicecomune", filter.getCodicecomune(), "comuneSvolgimento", String.class));
	}
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("dataInserimento"));
	//ft.addOrder(FilterUtils.orderAsc("dal", "fiereMostrePeriodis"));
	return fiereMostreDAO.findByFilterTable(ft);
    }

    @Override
    public void insert(FiereMostre entity) {

	if (validateEntity(entity)) {
	    fiereMostreDAO.insert(entity);
	}
    }

    @Override
    public void insert(FiereMostre entity, FiereMostrePeriodi periodo, FiereMostreMerceologie merceologia) {

	if (validateEntity(entity)) {
	    fiereMostreDAO.insert(entity);
	    periodo.setFiereMostre(entity);
	    fiereMostrePeriodiService.insert(periodo);
	    merceologia.setFiereMostre(entity);
	    fiereMostreMerceologieService.insert(merceologia);
	    EmailHelper e = mailService.pupolateEmail(entity.getId().getCodice(), TIPO_MANIFESTAZIONE.FIERE_MOSTRE);
	    if (e != null) {
		mailService.sendEmail(e.getOggetto(), e.getCorpo());
	    } else {
		log.info("insert# Configurazione email non presente, l'email non verrà inviata...");
	    }
	}
    }

    @Override
    public void insert(FiereMostre entity, List<FiereMostrePeriodi> periodi, List<FiereMostreMerceologie> merceologie) {

	if (validateEntity(entity)) {
	    fiereMostreDAO.insert(entity);
	    for (FiereMostrePeriodi periodo : periodi) {
		periodo.setFiereMostre(entity);
		fiereMostrePeriodiService.insert(periodo);
	    }
	    for (FiereMostreMerceologie merceologia : merceologie) {
		merceologia.setFiereMostre(entity);
		fiereMostreMerceologieService.insert(merceologia);
	    }
	}
    }

    @Override
    public void update(FiereMostre entity) {

	if (validateEntity(entity)) {
	    fiereMostreDAO.update(entity);
	}
    }

    @Override
    public void update(FiereMostre entity, List<FiereMostrePeriodiCommand> periodi, FiereMostrePeriodi periodo,
	    List<FiereMostreMerceologieCommand> merceologie, FiereMostreMerceologie merceologia) {

	if (validateEntity(entity)) {
	    // delete periodi
	    for (FiereMostrePeriodi _periodo : entity.getFiereMostrePeriodis()) {
		fiereMostrePeriodiService.delete(_periodo);
	    }
	    // delete merceologie
	    for (FiereMostreMerceologie _merceologia : entity.getFiereMostreMerceologies()) {
		fiereMostreMerceologieService.delete(_merceologia);
	    }
	    // update entity
	    entity.setFiereMostrePeriodis(null);
	    entity.setFiereMostreMerceologies(null);
	    fiereMostreDAO.update(entity);
	    // add periodi
	    for (FiereMostrePeriodiCommand __periodo : periodi) {
		if (__periodo.getDal() != null && __periodo.getAl() != null) {
		    FiereMostrePeriodi fmp = new FiereMostrePeriodi();
		    fmp.setDal(__periodo.getDal());
		    fmp.setAl(__periodo.getAl());
		    fmp.setFiereMostre(entity);
		    fiereMostrePeriodiService.insert(fmp);
		}
	    }
	    if (periodo.getDal() != null && periodo.getAl() != null) {
		periodo.setFiereMostre(entity);
		fiereMostrePeriodiService.insert(periodo);
	    }
	    // add merceologie
	    for (FiereMostreMerceologieCommand __merceologia : merceologie) {
		if (StringUtils.isNotBlank(__merceologia.getMerceologia())) {
		    FiereMostreMerceologie fmm = new FiereMostreMerceologie();
		    fmm.setMerceologia(__merceologia.getMerceologia());
		    fmm.setFiereMostre(entity);
		    fiereMostreMerceologieService.insert(fmm);
		}
	    }
	    if (StringUtils.isNotBlank(merceologia.getMerceologia())) {
		merceologia.setFiereMostre(entity);
		fiereMostreMerceologieService.insert(merceologia);
	    }
	}
    }

    @Override
    public void delete(FiereMostre entity) {

	if (isDeleteAllowed(entity)) {
	    fiereMostreDAO.delete(entity);
	}
    }

    @Override
    public FiereMostre findById(PkId id) {

	return fiereMostreDAO.findById(id);
    }

    @Override
    public Class<FiereMostre> getEntityClass() {

	return FiereMostre.class;
    }

    @Autowired
    public void setFiereMostreDAO(FiereMostreDAO fiereMostreDAO) {

	this.fiereMostreDAO = fiereMostreDAO;
    }

    @Autowired
    public void setFiereMostrePeriodiService(FiereMostrePeriodiService fiereMostrePeriodiService) {

	this.fiereMostrePeriodiService = fiereMostrePeriodiService;
    }

    @Autowired
    public void setFiereMostreMerceologieService(FiereMostreMerceologieService fiereMostreMerceologieService) {

	this.fiereMostreMerceologieService = fiereMostreMerceologieService;
    }
}
