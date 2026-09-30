package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.StpTipologieEndo2DAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Azioni;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.StpTipologieEndo2;
import it.gruppoinit.pal.gp.core.service.AzioniService;
import it.gruppoinit.pal.gp.core.service.StpTipologieEndo2Service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StpTipologieEndo2ServiceImpl extends BaseServiceImpl<StpTipologieEndo2, PkId> implements StpTipologieEndo2Service {

    private AzioniService azioniService;
    private StpTipologieEndo2DAO stpTipologieEndo2DAO;
    private static final Logger log = LoggerFactory.getLogger(StpTipologieEndo2ServiceImpl.class);

    @Autowired
    public void setAzioniService(AzioniService azioniService) {

	this.azioniService = azioniService;
    }

    @Autowired
    public void setStpTipologieEndo2DAO(StpTipologieEndo2DAO stpTipologieEndo2DAO) {

	this.stpTipologieEndo2DAO = stpTipologieEndo2DAO;
    }

    @Override
    public void insert(StpTipologieEndo2 entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    stpTipologieEndo2DAO.insert(entity);
	}
    }

    @Override
    public void update(StpTipologieEndo2 entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    stpTipologieEndo2DAO.update(entity);
	}
    }

    @Override
    public void delete(StpTipologieEndo2 entity) {

	if (isDeleteAllowed(entity)) {
	    stpTipologieEndo2DAO.delete(entity);
	}
    }

    @Override
    public List<StpTipologieEndo2> findAll(Integer firstResult, Integer maxResult) {

	return stpTipologieEndo2DAO.findAll(null, null, DAOEnum.FIND_BY_IDCOMUNE, "descrizione", DAOOrderTypeEnum.ASC);
    }

    @Override
    public StpTipologieEndo2 findById(PkId id) {

	return stpTipologieEndo2DAO.findById(id);
    }

    @Override
    protected Class<StpTipologieEndo2> getEntityClass() {

	return StpTipologieEndo2.class;
    }

    @Override
    public boolean updateStpTipologieEndo2AndValidateConfiguration(String[] codiceAzioni, String[] codiciTipologieEndo2,
	    String[] descrizioneTipologieEndo2) {

	if (log.isDebugEnabled()) {
	    log.debug("updateStpTipologieEndo2AndValidateConfiguration# Inizio aggiornamento configurazione azioni per tipologie endo 2.....");
	}
	boolean isConfigurato = true;
	for (int i = 0; i < codiciTipologieEndo2.length; i++) {
	    // Controllo se già esiste una StpTipologieEndo2 con il codice passato
	    if (log.isDebugEnabled()) {
		log.debug("updateStpTipologieEndo2AndValidateConfiguration# Cerco StpTipologieEndo2 con codice: {} idcomune: {} ", new Object[] {
			codiciTipologieEndo2[i], ORMHelper.getIdcomune() });
	    }
	    StpTipologieEndo2 stpTipologieEndo2 = this.findById(new PkId(Integer.parseInt(codiciTipologieEndo2[i].trim())));
	    // stpTipologieEndo2 con codice passato non esiste nel db
	    if (stpTipologieEndo2 == null) {
		if (log.isDebugEnabled()) {
		    log.debug(
			    "updateStpTipologieEndo2AndValidateConfiguration#StpTipologieEndo2 con codice: {} idcomune: {} non presente, creo nuovo oggetto da inserire con il codice passato",
			    new Object[] { codiciTipologieEndo2[i], ORMHelper.getIdcomune() });
		}
		stpTipologieEndo2 = new StpTipologieEndo2();
		stpTipologieEndo2.setDescrizione(descrizioneTipologieEndo2[i].trim());
		stpTipologieEndo2.setId(new PkId(Integer.parseInt(codiciTipologieEndo2[i].trim())));
		// Controllo nel form di configurazione ho passato l'azione.
		if (log.isDebugEnabled()) {
		    log.debug("updateStpTipologieEndo2AndValidateConfiguration# Controllo nel form di configurazione ho passato l'azione.");
		}
		if (!codiceAzioni[i].equals("#")) {
		    if (log.isDebugEnabled()) {
			log.debug("updateStpTipologieEndo2AndValidateConfiguration# Pasatta azione con codice {}", codiceAzioni[i].trim());
		    }
		    // Trovo l'azione e la setto al tipo endo
		    Azioni azioni = azioniService.findById(Integer.parseInt(codiceAzioni[i].trim()));
		    stpTipologieEndo2.setAzioni(azioni);
		} else {
		    if (log.isDebugEnabled()) {
			log.warn("updateStpTipologieEndo2AndValidateConfiguration# Azione non passata, la configurazione non sarà completa.");
		    }
		    isConfigurato = false;
		}
		if (log.isDebugEnabled()) {
		    log.debug("updateStpTipologieEndo2AndValidateConfiguration# Inserisco stp tipologia endo con codice: {}, idcomune: {} ",
			    new Object[] { codiciTipologieEndo2[i], ORMHelper.getIdcomune() });
		}
		this.insert(stpTipologieEndo2);
		if (log.isDebugEnabled()) {
		    log.debug("updateStpTipologieEndo2AndValidateConfiguration#Stp tipologia endo inserita");
		}
	    } else {// // stpTipologieEndo2 con codice passato  esiste nel db
		log.debug("updateStpTipologieEndo2AndValidateConfiguration#StpTipologieEndo2 con codice: {} idcomune: {}  presente", new Object[] {
			codiciTipologieEndo2[i], ORMHelper.getIdcomune() });
		//setto solo l'azione se viene passata
		if (!codiceAzioni[i].equals("#")) {
		    if (log.isDebugEnabled()) {
			log.debug("updateStpTipologieEndo2AndValidateConfiguration# Pasatta azione con codice {}", codiceAzioni[i].trim());
		    }
		    // Trovo l'azione e la setto al tipo endo
		    Azioni azioni = azioniService.findById(Integer.parseInt(codiceAzioni[i].trim()));
		    stpTipologieEndo2.setAzioni(azioni);
		} else {
		    if (log.isDebugEnabled()) {
			log.warn("updateStpTipologieEndo2AndValidateConfiguration# Azione non passata, la configurazione non sarà completa.");
		    }
		    isConfigurato = false;
		}
		if (log.isDebugEnabled()) {
		    log.debug("updateStpTipologieEndo2AndValidateConfiguration# Update stp tipologia endo con codice: {}, idcomune: {} ",
			    new Object[] { codiciTipologieEndo2[i], ORMHelper.getIdcomune() });
		}
		update(stpTipologieEndo2);
		stpTipologieEndo2DAO.flush();
		if (log.isDebugEnabled()) {
		    log.debug("updateStpTipologieEndo2AndValidateConfiguration#Stp tipologia endo Update");
		}
	    }
	    if (log.isDebugEnabled()) {
		log.debug("updateStpTipologieEndo2AndValidateConfiguration#Fine aggiornamento configurazione azioni per tipologie endo 2.....");
	    }
	}
	return isConfigurato;
    }

    private void dataIntegration(StpTipologieEndo2 entity) {

	fixMergeEntityProperties(entity);
    }

    @Override
    protected void fixMergeEntityProperties(StpTipologieEndo2 entity) {

	Azioni azioni = azioniService.bindDomainObject(entity.getAzioni(), Integer.class, "azId");
	entity.setAzioni(azioni);
    }
}
