package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.FesteSagreDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.FesteSagre;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.helper.EmailHelper;
import it.gruppoinit.pal.gp.core.helper.TIPO_MANIFESTAZIONE;
import it.gruppoinit.pal.gp.core.service.ComuniService;
import it.gruppoinit.pal.gp.core.service.FesteSagreService;
import it.gruppoinit.pal.gp.core.service.MailService;
import it.gruppoinit.pal.gp.gestionecalendari.web.command.ManifestazioniSearchFilter;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FesteSagreServiceImpl extends BaseServiceImpl<FesteSagre, PkId> implements FesteSagreService {

    private static final Logger log = LoggerFactory.getLogger(FesteSagreServiceImpl.class);
    private FesteSagreDAO festeSagreDAO;
    private ComuniService comuniService;
    @Autowired
    private MailService mailService;

    @Override
    public List<FesteSagre> findByFilter(ManifestazioniSearchFilter filter) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	if (StringUtils.isNotBlank(filter.getDenominazione())) {
	    fr.addFilterField(FilterUtils.like("denominazione", filter.getDenominazione()));
	}
	if (filter.getDal() != null) {
	    fr.addFilterField(FilterUtils.greaterEqual("dal", filter.getDal(), Date.class));
	}
	if (filter.getAl() != null) {
	    fr.addFilterField(FilterUtils.smallerEqual("al", filter.getAl(), Date.class));
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
	ft.addOrder(FilterUtils.orderAsc("dal"));
	return festeSagreDAO.findByFilterTable(ft);
    }

    @Autowired
    public void setFesteSagreDAO(FesteSagreDAO festeSagreDAO) {

	this.festeSagreDAO = festeSagreDAO;
    }

    @Autowired
    public void setComuniService(ComuniService comuniService) {

	this.comuniService = comuniService;
    }

    @Override
    public void insert(FesteSagre entity) {

	if (validateEntity(entity)) {
	    Comuni comune = comuniService.findByCodiceComune(entity.getComune());
	    entity.setComune(comune);
	    Comuni comuneSvolgimento = comuniService.findByCodiceComune(entity.getComuneSvolgimento());
	    entity.setComuneSvolgimento(comuneSvolgimento);
	    festeSagreDAO.insert(entity);
	    EmailHelper e = mailService.pupolateEmail(entity.getId().getCodice(), TIPO_MANIFESTAZIONE.SAGRE_FESTE);
	    if (e != null) {
		mailService.sendEmail(e.getOggetto(), e.getCorpo());
	    } else {
		log.info("insert# Configurazione email non presente, l'email non verrà inviata...");
	    }
	}
    }

    @Override
    public void update(FesteSagre entity) {

	if (validateEntity(entity)) {
	    festeSagreDAO.update(entity);
	}
    }

    @Override
    public void delete(FesteSagre entity) {

	if (isDeleteAllowed(entity)) {
	    festeSagreDAO.delete(entity);
	}
    }

    @Override
    public FesteSagre findById(PkId id) {

	return festeSagreDAO.findById(id);
    }

    @Override
    public Class<FesteSagre> getEntityClass() {

	return FesteSagre.class;
    }
}
