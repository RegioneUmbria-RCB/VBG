package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.ResponsabiliDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.Clmenu;
import it.gruppoinit.pal.gp.core.domain.Clpermmenu;
import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.Comuniassociati;
import it.gruppoinit.pal.gp.core.domain.Configurazione;
import it.gruppoinit.pal.gp.core.domain.Configurazioneutente;
import it.gruppoinit.pal.gp.core.domain.ConfigurazioneutenteId;
import it.gruppoinit.pal.gp.core.domain.Interventi;
import it.gruppoinit.pal.gp.core.domain.Logpermessi;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.ProtAooresponsabili;
import it.gruppoinit.pal.gp.core.domain.ProtAssegnazioni;
import it.gruppoinit.pal.gp.core.domain.ProtClassificazione;
import it.gruppoinit.pal.gp.core.domain.ProtTprofilassegnazione;
import it.gruppoinit.pal.gp.core.domain.ProtocolloFlusso;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Responsabilicomuni;
import it.gruppoinit.pal.gp.core.domain.ResponsabilicomuniId;
import it.gruppoinit.pal.gp.core.domain.Responsabiliruoli;
import it.gruppoinit.pal.gp.core.domain.Responsabilisoftware;
import it.gruppoinit.pal.gp.core.domain.ResponsabilisoftwareId;
import it.gruppoinit.pal.gp.core.domain.Ruoli;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.Tipiresponsabili;
import it.gruppoinit.pal.gp.core.domain.helper.CodiceDescrizioneBean;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.AlberoprocService;
import it.gruppoinit.pal.gp.core.service.ClmenuService;
import it.gruppoinit.pal.gp.core.service.ClpermmenuService;
import it.gruppoinit.pal.gp.core.service.ComuniService;
import it.gruppoinit.pal.gp.core.service.ComuniassociatiService;
import it.gruppoinit.pal.gp.core.service.ConfigurazioneService;
import it.gruppoinit.pal.gp.core.service.ConfigurazioneutenteService;
import it.gruppoinit.pal.gp.core.service.ProtTprofilassegnazioneService;
import it.gruppoinit.pal.gp.core.service.ProtocolloFlussoService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliService;
import it.gruppoinit.pal.gp.core.service.ResponsabilicomuniService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliruoliService;
import it.gruppoinit.pal.gp.core.service.ResponsabilisoftwareService;
import it.gruppoinit.pal.gp.core.service.RuoliService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;
import it.gruppoinit.pal.gp.core.service.TipiresponsabiliService;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;
import it.gruppoinit.pal.gp.core.service.exception.BusinessValidationException;
import it.gruppoinit.pal.gp.core.utils.Utilities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.InvalidValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class ResponsabiliServiceImpl extends BaseServiceImpl<Responsabili, PkId> implements ResponsabiliService {

    private static final Logger log = LoggerFactory.getLogger(ResponsabiliServiceImpl.class);
    private static final String ENCRYPTING_ALGORITHM = "MD5";
    private AlberoprocService alberoprocService;
    private ConfigurazioneutenteService configurazioneutenteService;
    private ResponsabiliDAO responsabiliDAO;
    private ClmenuService clmenuService;
    private ClpermmenuService clpermmenuService;
    private ComuniassociatiService comuniassociatiService;
    private ConfigurazioneService configurazioneService;
    private ProtocolloFlussoService protocolloFlussoService;
    private ResponsabiliruoliService responsabiliruoliService;
    private ResponsabilisoftwareService responsabilisoftwareService;
    private ResponsabilicomuniService responsabilicomuniService;
    private RuoliService ruoliService;
    private SoftwareService softwareService;
    private ProtTprofilassegnazioneService protTprofilassegnazioneService;
    private TipiresponsabiliService tipiresponsabiliService;
    private UserSecurityService userSecurityService;
    private ComuniService comuniService;

    @Autowired
    public void setAlberoprocService(AlberoprocService alberoprocService) {

	this.alberoprocService = alberoprocService;
    }

    @Autowired
    public void setConfigurazioneutenteService(ConfigurazioneutenteService configurazioneutenteService) {

	this.configurazioneutenteService = configurazioneutenteService;
    }

    @Autowired
    public void setResponsabiliruoliService(ResponsabiliruoliService responsabiliruoliService) {

	this.responsabiliruoliService = responsabiliruoliService;
    }

    @Autowired
    public void setResponsabilisoftwareService(ResponsabilisoftwareService responsabilisoftwareService) {

	this.responsabilisoftwareService = responsabilisoftwareService;
    }

    @Autowired
    public void setResponsabilicomuniService(ResponsabilicomuniService responsabilicomuniService) {

	this.responsabilicomuniService = responsabilicomuniService;
    }

    @Autowired
    public void setClmenuService(ClmenuService clmenuService) {

	this.clmenuService = clmenuService;
    }

    @Autowired
    public void setRuoliService(RuoliService ruoliService) {

	this.ruoliService = ruoliService;
    }

    @Autowired
    public void setSoftwareService(SoftwareService softwareService) {

	this.softwareService = softwareService;
    }

    @Autowired
    public void setClpermmenuService(ClpermmenuService clpermmenuService) {

	this.clpermmenuService = clpermmenuService;
    }

    @Autowired
    public void setComuniassociatiService(ComuniassociatiService comuniassociatiService) {

	this.comuniassociatiService = comuniassociatiService;
    }

    @Autowired
    public void setConfigurazioneService(ConfigurazioneService configurazioneService) {

	this.configurazioneService = configurazioneService;
    }

    @Autowired
    public void setProtocolloFlussoService(ProtocolloFlussoService protocolloFlussoService) {

	this.protocolloFlussoService = protocolloFlussoService;
    }

    @Autowired
    public void setProtTprofilassegnazioneService(ProtTprofilassegnazioneService protTprofilassegnazioneService) {

	this.protTprofilassegnazioneService = protTprofilassegnazioneService;
    }

    @Autowired
    public void setTipiresponsabiliService(TipiresponsabiliService tipiresponsabiliService) {

	this.tipiresponsabiliService = tipiresponsabiliService;
    }

    @Autowired
    public void setResponsabiliDAO(ResponsabiliDAO responsabiliDAO) {

	this.responsabiliDAO = responsabiliDAO;
    }

    @Autowired
    public void setUserSecurityService(UserSecurityService userSecurityService) {

	this.userSecurityService = userSecurityService;
    }

    @Autowired
    public void setComuniService(ComuniService comuniService) {

	this.comuniService = comuniService;
    }

    @Override
    public void delete(Responsabili entity) {

	/**
	 * Insieme al record responsabili vengono cancellati i record anche sulle tabelle:<br/>
	 * CLPERMMENU,RESPONSABILISOFTWARE,RESPONSABILICOMUNI,RESPONSABILIRUOLI.<br/>
	 * Queste cancellazioni avvengono grazie al cascade dell'oggetto responsabili sulle tabelle collegate.
	 */
	if (isDeleteAllowed(entity)) {
	    userSecurityService.resetObjectCached();
	    childDelete(entity);
	    responsabiliDAO.delete(entity);
	}
    }

    @Override
    protected void childDelete(Responsabili entity) {

	responsabilisoftwareService.deleteByResponsabile(entity);
	// elimino l'associazione in SCAD_OPERATORE
	List<Responsabili> resp = this.findOperatoriUsedInScadOperatore(entity.getId().getCodice());
	for (Responsabili responsabili : resp) {
	    // TODO GESTIRE
	    responsabili.setScadOperatoreId(null);
	    responsabiliDAO.update(responsabili);
	}
	// Elimino le relative configurazioni utente
	Set<Configurazioneutente> configurazioneutenteList = entity.getConfigurazioniutente();
	for (Configurazioneutente configurazioneutente : configurazioneutenteList) {
	    configurazioneutenteService.delete(configurazioneutente);
	}
    }

    @Override
    public void insert(Responsabili entity) {

	log.debug("insert: prima di eseguire dataIntegration");
	dataIntegration(entity);
	log.debug("insert: prima di eseguire checkPassword");
	checkPassword(entity, false);
	log.debug("insert: prima della validateEntity");
	if (validateEntity(entity)) {
	    log.debug("insert: prima della validateDuplicateKey");
	    if (validateDuplicateKey(entity)) {
		/*
		 * Le liste responsabilicomunis e softwareAbilitati dell'Oggetto Responsabile contengono un riferimento
		 * al Responsabile stesso quindi vanno inserite successivamente dopo avergli settato il riferimento al
		 * Responsabile
		 */
		log.debug("insert: prima della isInsertAllowed");
		if (isInsertAllowed(entity)) {
		    Set<Responsabilisoftware> responsabilisoftwareList = entity.getSoftwareAbilitati();
		    Set<Responsabilicomuni> responsabilicomuniList = entity.getResponsabilicomunis();
		    entity.setSoftwareAbilitati(null);
		    entity.setResponsabilicomunis(null);
		    log.debug("insert: prima di inserire");
		    responsabiliDAO.insert(entity);
		    responsabiliDAO.flush();
		    log.debug("insert: responsabile Inserito");
		    log.debug("insert: prima di ChildDataInsert");
		    childDataInsert(entity, responsabilisoftwareList, responsabilicomuniList, true);
		}
	    }
	}
    }

    private void childDataInsert(Responsabili entity, Set<Responsabilisoftware> responsabilisoftwareList,
	    Set<Responsabilicomuni> responsabilicomuniList, Boolean isInsert) {

	if ("1".equals(entity.getAmministratore())) {
	    responsabilisoftwareList.clear();
	    for (Responsabilisoftware temp : getListResponsabilisoftware(entity)) {
		responsabilisoftwareList.add(temp);
	    }
	} else {
	    // Verifico se è presente il software TT altrimenti lo aggiungo alla lista
	    Boolean trovatoTT = false;
	    for (Responsabilisoftware respsw : responsabilisoftwareList) {
		if (respsw.getSoftware().getCodice().equals(WebConstants.SOFTWARE_TT)) {
		    trovatoTT = true;
		}
	    }
	    if (!trovatoTT) {
		Responsabilisoftware responsabilisoftware = new Responsabilisoftware();
		responsabilisoftware.getSoftware().setCodice(WebConstants.SOFTWARE_TT);
		responsabilisoftwareList.add(responsabilisoftware);
	    }
	    for (Responsabilisoftware responsabilisoftware : responsabilisoftwareList) {
		Software software = softwareService.findById(responsabilisoftware.getSoftware().getCodice());
		responsabilisoftware.setSoftware(software);
		ResponsabilisoftwareId id = new ResponsabilisoftwareId(software.getCodice(), entity.getId().getCodice());
		responsabilisoftware.setResponsabili(entity);
		responsabilisoftware.setId(id);
	    }
	}
	for (Responsabilisoftware responsabilisoftware : responsabilisoftwareList) {
	    responsabilisoftware.setResponsabili(entity);
	    ResponsabilisoftwareId id = new ResponsabilisoftwareId();
	    id.setCodiceresponsabile(entity.getId().getCodice());
	    id.setSoftware(responsabilisoftware.getSoftware().getCodice());
	    responsabilisoftware.setId(id);
	    responsabilisoftwareService.insert(responsabilisoftware);
	    responsabiliDAO.flush();
	}
	List<Comuniassociati> comuniassociatis = comuniassociatiService.findByIdcomune(ORMHelper.getIdcomune());
	if (comuniassociatis.size() == 1) {
	    // nel caso che l'installazione non sia di tipo comuniassociati (comuniassociatis.size() == 1)
	    // allora automaticamente vie aggiunto il comune di default al responsabile
	    if (comuniassociatis.get(0) == null) {
		throw new RuntimeException("Errore nella configurazione dei comuni associati");
	    }
	    if (comuniassociatis.get(0).getComune() == null) {
		throw new RuntimeException("Errore nella configurazione dei comuni associati, comune è nullo per l'idcomune="
			+ ORMHelper.getIdcomune());
	    }
	    Comuni comune = comuniassociatis.get(0).getComune();
	    ResponsabilicomuniId id = new ResponsabilicomuniId();
	    id.setCodiceresponsabile(entity.getId().getCodice());
	    id.setCodicecomune(comune.getCodicecomune());
	    Responsabilicomuni responsabilicomuni = responsabilicomuniService.findById(id);
	    if (responsabilicomuni == null) {
		// inserisco solamente se non esiste
		responsabilicomuni = new Responsabilicomuni();
		responsabilicomuni.setResponsabile(entity);
		responsabilicomuni.setComune(comune);
		responsabilicomuni.setId(id);
		responsabilicomuniService.insert(responsabilicomuni);
	    }
	} else {
	    if (responsabilicomuniList != null) {
		for (Responsabilicomuni responsabilicomuni : responsabilicomuniList) {
		    responsabilicomuni.setResponsabile(entity);
		    ResponsabilicomuniId id = new ResponsabilicomuniId();
		    id.setCodiceresponsabile(entity.getId().getCodice());
		    id.setCodicecomune(responsabilicomuni.getComune().getCodicecomune());
		    Comuni comune = comuniService.findByCodiceComune(responsabilicomuni.getComune());
		    responsabilicomuni.setComune(comune);
		    responsabilicomuni.setId(id);
		    responsabilicomuniService.insert(responsabilicomuni);
		}
	    }
	}
	if (isInsert) {
	    Configurazioneutente stile = new Configurazioneutente();
	    ConfigurazioneutenteId id = new ConfigurazioneutenteId(entity.getId().getCodice(), WebConstants.CSS_USER_PREF_STYLE);
	    stile.setId(id);
	    stile.setResponsabile(entity);
	    stile.setValore(WebConstants.CSS_DEFAULT_STYLE);
	    configurazioneutenteService.insert(stile);
	}
    }

    /**
     * Restituisce la lista di tutti i software che possono essere attivati in una lista di Responsabilisoftware
     * 
     * @param responsabile
     * 
     */
    public Set<Responsabilisoftware> getListResponsabilisoftware(Responsabili responsabile) {

	Set<Responsabilisoftware> responsabilisoftwareList = new LinkedHashSet<Responsabilisoftware>();
	List<Software> softwares = softwareService.findSoftwareAttivi(false);
	for (Software software : softwares) {
	    Responsabilisoftware responsabilisoftware = new Responsabilisoftware();
	    ResponsabilisoftwareId idSw = new ResponsabilisoftwareId(software.getCodice(), responsabile.getId().getCodice());
	    Software sw = softwareService.findById(software.getCodice());
	    responsabilisoftware.setSoftware(sw);
	    responsabilisoftware.setResponsabili(responsabile);
	    responsabilisoftware.setId(idSw);
	    responsabilisoftwareList.add(responsabilisoftware);
	}
	return responsabilisoftwareList;
    }

    @Override
    public void update(Responsabili entity) {

	log.debug("update: prima di eseguire dataIntegration");
	dataIntegration(entity);
	log.debug("update: prima di eseguire checkPassword");
	checkPassword(entity, true);
	log.debug("update: prima di eseguire validateEntity");
	if (validateEntity(entity)) {
	    log.debug("update: prima di eseguire validateDuplicateKey");
	    if (validateDuplicateKey(entity)) {
		/*
		 * Le liste responsabilicomunis e softwareAbilitati del responsabile vengono cancellate e reinserite.
		 */
		log.debug("update: prima di eseguire isUpdateAllowed");
		if (isUpdateAllowed(entity)) {
		    Set<Responsabilisoftware> responsabilisoftwareList = entity.getSoftwareAbilitati();
		    Set<Responsabilicomuni> responsabilicomuniList = entity.getResponsabilicomunis();
		    log.debug("update: prima di eseguire la cancellazione della lista di responsabilisoftware");
		    responsabilisoftwareService.deleteByResponsabile(entity);
		    log.debug("update: prima di eseguire la cancellazione della lista di responsabilicomuni");
		    responsabilicomuniService.deleteByResponsabile(entity);
		    entity.setSoftwareAbilitati(null);
		    entity.setResponsabilicomunis(null);
		    log.debug("update: prima di aggiornare");
		    responsabiliDAO.update(entity);
		    log.debug("update: prima di childDataInsert");
		    childDataInsert(entity, responsabilisoftwareList, responsabilicomuniList, false);
		}
	    }
	}
	// all'update dell'operatore ricarico la cahce di rinnovo la cache di userSecurityService
	userSecurityService.resetObjectCached();
	// all'update dell'operatore rimuovo dalla cache i menu
	log.debug("update: prima di removeMenuFromCache");
	clmenuService.removeMenuFromCache(entity.getId().getCodice());
    }

    @Override
    public List<Responsabili> findAll(Integer firstResult, Integer maxResult) {

	return responsabiliDAO.findAll(firstResult, maxResult);
    }

    @Override
    public Responsabili findById(PkId id) {

	return responsabiliDAO.findById(id);
    }

    @Override
    public Class<Responsabili> getEntityClass() {

	return Responsabili.class;
    }

    @Override
    public List<Responsabili> findByFilter(Responsabili responsabili) {

	return responsabiliDAO.findByFilter(responsabili);
    }

    @Override
    public void saveRuoli(Responsabili responsabili, Set<Responsabiliruoli> responsabiliruolis) {

	responsabili = this.findById(responsabili.getId());
	// responsabiliDAO.evict(responsabili);
	Set<Responsabiliruoli> ruolis = responsabili.getResponsabiliruolis();
	for (Responsabiliruoli responsabiliruoli : ruolis) {
	    responsabiliruoliService.delete(responsabiliruoli);
	}
	responsabili.setResponsabiliruolis(null);
	for (Responsabiliruoli rr : responsabiliruolis) {
	    rr.getId().setCodiceresponsabile(responsabili.getId().getCodice());
	    rr.getId().setIdruolo(rr.getId().getIdruolo());
	    Ruoli ruolo = ruoliService.findById(new PkId(rr.getId().getIdruolo()));
	    rr.setRuolo(ruolo);
	    rr.setResponsabile(responsabili);
	    responsabiliruoliService.insert(rr);
	}
	userSecurityService.resetObjectCached();
    }

    @Override
    public void saveParametriprotocollo(Responsabili entity) {

	// Recupero l'oggetto salvato su DB
	Responsabili responsabileDB = responsabiliDAO.findById(new PkId(entity.getId().getCodice()));
	// Annullo la lista dei flussi protocollo
	responsabileDB.setProtocolloFlussos(null);
	// Recupero la lista dei flussi protocollo passati tramite command
	Set<ProtocolloFlusso> protocolloFlussos = entity.getProtocolloFlussos();
	Set<ProtocolloFlusso> protocolloFlussosNew = new HashSet<ProtocolloFlusso>();
	ProtocolloFlusso protocolloFlussoNew = new ProtocolloFlusso();
	if (protocolloFlussos != null && !protocolloFlussos.isEmpty()) {
	    for (ProtocolloFlusso protocolloFlusso : protocolloFlussos) {
		protocolloFlussoNew = protocolloFlussoService.findById(protocolloFlusso.getCodice());
		protocolloFlussosNew.add(protocolloFlussoNew);
	    }
	}
	responsabileDB.setProtocolloFlussos(protocolloFlussosNew);
	responsabiliDAO.update(responsabileDB);
	userSecurityService.resetObjectCached();
    }

    @Override
    public void savePermessiSW(Responsabili entity, Set<Clpermmenu> nuoviPermessi, String codiceSoftware) {

	List<Software> listSoftware = new ArrayList<Software>();
	listSoftware.add(softwareService.findById(codiceSoftware));
	savePermessi(entity, nuoviPermessi, listSoftware);
	userSecurityService.resetObjectCached();
    }

    @Override
    public void savePermessi(Responsabili entity, Set<Clpermmenu> nuoviPermessi, List<Software> listSoftware) {

	Responsabili responsabile = responsabiliDAO.findById(entity.getId());
	//Cancello tutti i permessi dei software da aggiornare
	for (Clpermmenu clpermmenu : responsabile.getMenuAbilitati()) {
	    for (Software software : listSoftware) {
		if (software.getCodice().equalsIgnoreCase(clpermmenu.getSoftware().getCodice())) {
		    clpermmenuService.delete(clpermmenu);
		    break;
		}
	    }
	}
	responsabile.getMenuAbilitati().removeAll(responsabile.getMenuAbilitati());
	responsabiliDAO.update(responsabile);
	responsabiliDAO.flush();
	if (nuoviPermessi != null) {
	    for (Clpermmenu clpermmenu : nuoviPermessi) {
		Clpermmenu nuovoPermesso = new Clpermmenu();
		Clmenu clmenu = clmenuService.findById(clpermmenu.getMenu().getId());
		nuovoPermesso.setMenu(clmenu);
		nuovoPermesso.setResponsabile(responsabile);
		nuovoPermesso.setSoftware(softwareService.findById(clpermmenu.getSoftware().getCodice()));
		clpermmenuService.insert(nuovoPermesso);
		responsabile.getMenuAbilitati().add(nuovoPermesso);
	    }
	}
	responsabiliDAO.update(responsabile);
	userSecurityService.resetObjectCached();
	// all'update dell'operatore rimuovo dalla cache i menu
	clmenuService.removeMenuFromCache(entity.getId().getCodice());
    }

    @Override
    public void insertPermesso(Responsabili responsabile, Integer codiceClmenu, String codiceSoftware) {

	Clpermmenu permesso = clpermmenuService.findByOperatoreAndClMenuAndSoftware(responsabile.getId().getCodice(), codiceClmenu, codiceSoftware);
	if (permesso == null) {
	    permesso = new Clpermmenu();
	    Clmenu clmenu = clmenuService.findById(codiceClmenu);
	    permesso.setMenu(clmenu);
	    permesso.setResponsabile(responsabile);
	    permesso.setSoftware(softwareService.findById(codiceSoftware));
	    clpermmenuService.insert(permesso);
	    responsabile.getMenuAbilitati().add(permesso);
	    responsabiliDAO.update(responsabile);
	    userSecurityService.resetObjectCached();
	    clmenuService.removeMenuFromCache(responsabile.getId().getCodice());
	}
    }

    @Override
    public void deletePermesso(Responsabili responsabile, Integer codiceClmenu, String codiceSoftware) {

	Clpermmenu permesso = clpermmenuService.findByOperatoreAndClMenuAndSoftware(responsabile.getId().getCodice(), codiceClmenu, codiceSoftware);
	if (permesso != null) {
	    clpermmenuService.delete(permesso);
	    responsabile.getMenuAbilitati().remove(permesso);
	    responsabiliDAO.update(responsabile);
	    userSecurityService.resetObjectCached();
	    clmenuService.removeMenuFromCache(responsabile.getId().getCodice());
	}
    }

    @Override
    public List<Responsabili> findAllByAbilitati() {

	return responsabiliDAO.findAllByAbilitati();
    }

    @Override
    public List<Responsabili> findResponsabiliIstruttoria(Responsabili responsabili) {

	return responsabiliDAO.findResponsabiliIstruttoria(responsabili);
    }

    @Override
    public List<Responsabili> findResponsabiliProcedimento(Responsabili responsabili) {

	return responsabiliDAO.findResponsabiliProcedimento(responsabili);
    }

    @Override
    public void saveReplicapermessi(Set<Responsabili> responsabiliSet, Responsabili responsabile) {

	Set<Clpermmenu> listaPermessiDaReplicare = responsabile.getMenuAbilitati();
	// software abilitati del responsabile master
	List<Software> softwareAbilitatiMaster = softwareService.findSoftwareAbilitati(responsabile);
	for (Responsabili responsabileDaAggiornare : responsabiliSet) {
	    // software abilitati del responsabile slave
	    List<Software> softwareAbilitatiSlave = softwareService.findSoftwareAbilitati(responsabileDaAggiornare);
	    // software Abilitati da Aggiornare
	    List<Software> softwareAbilitatiDaAggiornare = new ArrayList<Software>();
	    for (Software software : softwareAbilitatiSlave) {
		if (softwareAbilitatiMaster.contains(software)) {
		    softwareAbilitatiDaAggiornare.add(software);
		}
	    }
	    // lista dei permessi da replicare
	    Set<Clpermmenu> listaPermessiDaReplicareTemp = new HashSet<Clpermmenu>();
	    for (Clpermmenu clpermmenu : listaPermessiDaReplicare) {
		if (softwareAbilitatiDaAggiornare.contains(clpermmenu.getSoftware())) {
		    Clpermmenu nuovoPermesso = new Clpermmenu();
		    nuovoPermesso.setMenu(clpermmenu.getMenu());
		    nuovoPermesso.setSoftware(clpermmenu.getSoftware());
		    nuovoPermesso.setResponsabile(responsabileDaAggiornare);
		    listaPermessiDaReplicareTemp.add(nuovoPermesso);
		}
	    }
	    savePermessi(responsabileDaAggiornare, listaPermessiDaReplicareTemp, softwareAbilitatiDaAggiornare);
	}
	userSecurityService.resetObjectCached();
    }

    @Override
    public List<Responsabili> findAmministratori(Boolean disabilitati) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction criterio = new FilterRestriction();
	criterio.addFilterField(FilterUtils.equals("amministratore", "1", String.class));
	if (disabilitati != null) {
	    criterio.addFilterField(FilterUtils.equals("disabilitato", disabilitati, Boolean.class));
	}
	ft.addRestriction(criterio);
	ft.addOrder(FilterUtils.orderAsc("responsabile"));
	List<Responsabili> list = this.findByFilterTable(ft);
	return list;
    }

    @Override
    public List<Responsabili> findAmministratoriSoftware(Boolean disabilitati, String amministratori, String software) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction criterio = new FilterRestriction();
	criterio.addFilterField(FilterUtils.equals("amministratoresoftware", "1", String.class));
	if (disabilitati != null) {
	    criterio.addFilterField(FilterUtils.equals("disabilitato", disabilitati, Boolean.class));
	}
	if (StringUtils.isNotBlank(software)) {
	    Software softwareObject = softwareService.findById(software);
	    criterio.addFilterField(FilterUtils.equals("software", softwareObject, "softwareAbilitati", Software.class));
	}
	if (StringUtils.isNotBlank(amministratori)) {
	    criterio.addFilterField(FilterUtils.equals("amministratore", amministratori, String.class));
	}
	ft.addRestriction(criterio);
	ft.addOrder(FilterUtils.orderAsc("responsabile"));
	List<Responsabili> list = this.findByFilterTable(ft);
	return list;
    }

    @Override
    public List<Responsabili> findOperatori(Boolean disabilitati, String software) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction criterio = new FilterRestriction();
	criterio.addFilterField(FilterUtils.equals("amministratore", "0", String.class));
	criterio.addFilterField(FilterUtils.equals("amministratoresoftware", "0", String.class));
	if (StringUtils.isNotBlank(software)) {
	    Software softwareObject = softwareService.findById(software);
	    criterio.addFilterField(FilterUtils.equals("software", softwareObject, "softwareAbilitati", Software.class));
	}
	if (disabilitati != null) {
	    criterio.addFilterField(FilterUtils.equals("disabilitato", disabilitati, Boolean.class));
	}
	ft.addRestriction(criterio);
	ft.addOrder(FilterUtils.orderAsc("responsabile"));
	List<Responsabili> list = this.findByFilterTable(ft);
	return list;
    }

    protected boolean isDeleteAllowed(Responsabili entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	List<Alberoproc> alberoprocs = alberoprocService.findByResponsabileprocedimento(entity, 0, 10);
	if (!alberoprocs.isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, "", "ALBEROPROC.CODICERESPONSABILE", null));
	    delete = false;
	}
	alberoprocs = alberoprocService.findByResponsabileistruttoria(entity, 0, 10);
	if (!alberoprocs.isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, "", "ALBEROPROC.CODICERESPISTRUTTORIA", null));
	    delete = false;
	}
	alberoprocs = alberoprocService.findByOperatoreSTC(entity, 0, 10);
	if (!alberoprocs.isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, "", "ALBEROPROC.CODICEOPERATORE_STC", null));
	    delete = false;
	}
	Set<ProtClassificazione> protClassificaziones = entity.getProtClassificaziones();
	if (!protClassificaziones.isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, "", "PROT_CLASSIFICAZIONE", null));
	    delete = false;
	}
	Set<Interventi> interventis = entity.getInterventis();
	if (!interventis.isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, "", "INTERVENTI", null));
	    delete = false;
	}
	Set<ProtAssegnazioni> protAssegnazionis = entity.getProtAssegnazionis();
	if (!protAssegnazionis.isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, "", "PROT_ASSEGNAZIONI", null));
	    delete = false;
	}
	Set<ProtAooresponsabili> protAooresponsabilis = entity.getProtAooresponsabilis();
	if (!protAooresponsabilis.isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, "", "PROT_AOORESPONSABILI", null));
	    delete = false;
	}
	Set<Logpermessi> logpermessis = entity.getLogpermessis();
	if (!logpermessis.isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, "", "LOGPERMESSI", null));
	    delete = false;
	}
	// Controllo che l'operatore non sia usato nella tabella configurazione
	List<Configurazione> configurazione = configurazioneService.findbyResponsabile(entity.getId().getCodice());
	if (!configurazione.isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, "", "CONFIGURAZIONE", null));
	    delete = false;
	}
	if (!delete) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }

    /**
     * La funzione controlla se la password è stata passata e nel caso la cripta con l'algoritmo MD5
     * 
     * @param entity
     * @param isUpdate
     */
    private void checkPassword(Responsabili entity, boolean isUpdate) {

	String password = "";
	if (StringUtils.isBlank(entity.getPasswordClear())) {
	    if (isUpdate) {
		Responsabili copy = this.findById(entity.getId());
		password = copy.getPassword();
		responsabiliDAO.evict(copy);
		entity.setPassword(password);
		return;
	    }
	} else {
	    String passwordClear = entity.getPasswordClear();
	    password = Utilities.getHashText(passwordClear, ENCRYPTING_ALGORITHM, false);
	    entity.setPassword(password);
	    entity.setPasswordClear(null);
	}
    }

    /**
     * Validazione interna per valori duplicati userid all'interno di uno stesso idcomune
     * 
     * @param entity
     * @return
     */
    private boolean validateDuplicateKey(Responsabili entity) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction useridGiaPresente = new FilterRestriction();
	useridGiaPresente.addFilterField(FilterUtils.equals("userid", entity.getUserid(), String.class));
	ft.addRestriction(useridGiaPresente);
	List<Responsabili> list = responsabiliDAO.findByFilterTable(ft);
	Integer id = entity.getId().getCodice();
	if (list.size() > 0) {
	    if ((id == null)) { // sono in insert
		List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
		InvalidValue iv = new InvalidValue("validator.unique.constraint", entity.getClass(), "userid", null, entity);
		_ivs.add(iv);
		this.throwValidationMessages(_ivs);
	    } else { // sono in update
		for (Iterator<Responsabili> iterator = list.iterator(); iterator.hasNext();) {
		    Responsabili responsabili = (Responsabili) iterator.next();
		    if (!id.equals(responsabili.getId().getCodice())) {
			List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
			InvalidValue iv = new InvalidValue("validator.unique.constraint", entity.getClass(), "userid", null, entity);
			_ivs.add(iv);
			this.throwValidationMessages(_ivs);
			break;
		    }
		}
	    }
	}
	return true;
    }

    @Override
    public List<Responsabili> findByFilterTable(FilterTable filterTable) {

	return responsabiliDAO.findByFilterTable(filterTable);
    }

    @Override
    protected Responsabili customBindDomainObject(Responsabili entity) {

	if (entity == null) {
	    return null;
	}
	List<Responsabili> list = responsabiliDAO.findByFilter(entity);
	if (list.size() == 1) {
	    return list.get(0);
	}
	return null;
    }

    @Override
    public void updatePassword(Responsabili responsabile, String clearPassword, String clearNewPassword, String clearNewPasswordConfirmation) {

	Assert.notNull(responsabile, "Il parametro responsabile non può essere vuoto");
	Assert.hasLength(clearPassword, "Il parametro password non può essere vuoto");
	Assert.hasLength(clearNewPassword, "Il parametro nuova password non può essere vuoto");
	Assert.hasLength(clearNewPasswordConfirmation, "Il parametro conferma nuova password non può essere vuoto");
	responsabile = bindDomainObject(responsabile, PkId.class, "id.codice");
	clearPassword = Utilities.getHashText(clearPassword, ENCRYPTING_ALGORITHM, false);
	String responsabilePassword = StringUtils.defaultIfEmpty(responsabile.getPassword(), "");
	// Se la password immessa coincide con quella registrata 
	if (clearPassword.equals(responsabilePassword)) {
	    // se la nuova password e quella di conferma coincidono
	    if (clearNewPassword.equalsIgnoreCase(clearNewPasswordConfirmation)) {
		clearNewPassword = Utilities.getHashText(clearNewPassword, ENCRYPTING_ALGORITHM, false);
		responsabile.setPassword(clearNewPassword);
		// TODO modificare con this.update una volta risolto il problema dell'aggiornamento
		responsabiliDAO.update(responsabile);
	    } else {
		// eccezione password confirm
		throw new BusinessValidationException("La nuova password non coincide con quella di conferma");
	    }
	} else {
	    // eccezione password non corretta
	    throw new BusinessValidationException("La password immessa non corrisponde a quella dell'operatore");
	}
	userSecurityService.resetObjectCached();
    }

    protected boolean isInsertAllowed(Responsabili entity) {

	boolean insert = true;
	List<Comuniassociati> comuniassociatis = comuniassociatiService.findByIdcomune(ORMHelper.getIdcomune());
	if (comuniassociatis.size() > 1) {
	    List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	    if (entity.getResponsabilicomunis() == null || entity.getResponsabilicomunis().isEmpty()) {
		_ivs.add(new InvalidValue("service_error.comune_non_specificato", null, "", "RESPONSABILI", null));
		this.throwValidationMessages(_ivs);
	    }
	}
	return insert;
    }

    protected boolean isUpdateAllowed(Responsabili entity) {

	boolean insert = true;
	//	List<Comuniassociati> comuniassociatis = comuniassociatiService.findByIdcomune(ORMHelper.getIdcomune());
	//	if (comuniassociatis.size() > 1) {
	//	    List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	//	    if (entity.getResponsabilicomunis() == null || entity.getResponsabilicomunis().isEmpty()) {
	//		_ivs.add(new InvalidValue("service_error.comune_non_specificato", null, "", "RESPONSABILI", null));
	//		this.throwValidationMessages(_ivs);
	//	    }
	//	}
	return insert;
    }

    private void dataIntegration(Responsabili entity) {

	if (entity == null) {
	    throw new IllegalArgumentException("Il Responsabile passato è nullo");
	}
	fixMergeEntityProperties(entity);
	if (entity.getScadenzario() == null) {
	    entity.setScadenzario(Boolean.FALSE);
	}
	if (entity.getFiltrooperatorescadenz() == null) {
	    entity.setFiltrooperatorescadenz(Boolean.FALSE);
	}
	if (entity.getReadonly() == null) {
	    entity.setReadonly(Boolean.FALSE);
	}
	if (entity.getDisabilitato() == null) {
	    entity.setDisabilitato(Boolean.FALSE);
	}
	if (entity.getUpdatehelp() == null) {
	    entity.setUpdatehelp(Boolean.FALSE);
	}
	if (entity.getFlagNotificaassegnazprot() == null) {
	    entity.setFlagNotificaassegnazprot(Boolean.FALSE);
	}
	if (entity.getFlagGestioneOneri() == null) {
	    entity.setFlagGestioneOneri(Boolean.FALSE);
	}
	if (entity.getFlagModificaNumist() == null) {
	    entity.setFlagModificaNumist(Boolean.FALSE);
	}
	if (entity.getFlagBloccaOneri() == null) {
	    entity.setFlagBloccaOneri(Boolean.FALSE);
	}
	if (entity.getScadDatainizio() != null) {
	    entity.setScadenzarioprec(null);
	}
	if (entity.getFlagCancellaistanze() == null) {
	    entity.setFlagCancellaistanze(Boolean.FALSE);
	}
	if (entity.getFlagCancelladocumentistc() == null) {
	    entity.setFlagCancelladocumentistc(Boolean.FALSE);
	}
	if (StringUtils.isBlank(entity.getAmministratore())) {
	    entity.setAmministratore("0");
	}
	if (StringUtils.isBlank(entity.getAmministratoresoftware())) {
	    entity.setAmministratoresoftware("0");
	}
    }

    protected void fixMergeEntityProperties(Responsabili entity) {

	ProtTprofilassegnazione protTprofilassegnazione = protTprofilassegnazioneService.bindDomainObject(entity.getProtTprofilassegnazione(),
		PkId.class, "id.codice");
	entity.setProtTprofilassegnazione(protTprofilassegnazione);
	Tipiresponsabili tipiresponsabili = tipiresponsabiliService.bindDomainObject(entity.getTiporesponsabile(), PkId.class, "id.codice");
	entity.setTiporesponsabile(tipiresponsabili);
	// ATTENZIONE!!! Non è possibile usare l'oggetto scadOperatore percheè se settato uguale all'entità da problemi al salvataggio/modifica
	//	Responsabili scadOperatore = this.bindDomainObject(entity.getScadOperatore(), PkId.class, "id.codice");
	//	entity.setScadOperatore(scadOperatore);
	Software scadSoftware = softwareService.bindDomainObject(entity.getScadSoftware(), String.class, "codice");
	entity.setScadSoftware(scadSoftware);
    }

    @Override
    public boolean isAbilitaBloccaOneri() {

	return responsabiliDAO.isAbilitaBloccaOneri();
    }

    @Override
    public List<Responsabili> findOperatoriUsedInScadOperatore(Integer codoperatore) {

	if (codoperatore == null) {
	    return new ArrayList<Responsabili>();
	}
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("scadOperatoreId", codoperatore, Integer.class));
	ft.addRestriction(fr);
	return this.findByFilterTable(ft);
    }

    @Override
    public CodiceDescrizioneBean findDescrizioneById(Integer codiceOperatore) {

	return responsabiliDAO.findDescrizioneById(codiceOperatore);
    }

    @Override
    public Responsabili findByUserId(String userId) {

	if (StringUtils.isBlank(userId)) {
	    log.error("findByUserId: userid nulla");
	    throw new RuntimeException("Attenzione! userid nulla");
	}
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("userid", userId, String.class));
	ft.addRestriction(fr);
	List<Responsabili> list = this.findByFilterTable(ft);
	if (list.size() > 1) {
	    log.error("findByUserId: la query ha restituito più di un valore per lo userid='{}'", userId);
	    throw new RuntimeException("Attenzione! trovati più responsabili con lo stesso userid: '" + userId + "'");
	}
	if (list.isEmpty()) {
	    return null;
	}
	return list.get(0);
    }

    @Override
    public Set<Responsabilisoftware> findListResponsabilisoftware(Responsabili responsabile) {

	Set<Responsabilisoftware> responsabilisoftwareList = new LinkedHashSet<Responsabilisoftware>();
	List<Software> s = softwareService.findSoftwareAbilitati(responsabile, false);
	// List<Software> softwares = softwareService.findSoftwareAttivi(false);
	for (Software software : s) {
	    Responsabilisoftware responsabilisoftware = new Responsabilisoftware();
	    ResponsabilisoftwareId idSw = new ResponsabilisoftwareId(software.getCodice(), responsabile.getId().getCodice());
	    Software sw = softwareService.findById(software.getCodice());
	    responsabilisoftware.setSoftware(sw);
	    responsabilisoftware.setResponsabili(responsabile);
	    responsabilisoftware.setId(idSw);
	    responsabilisoftwareList.add(responsabilisoftware);
	}
	return responsabilisoftwareList;
    }

    @Override
    public Set<Responsabilicomuni> findListResponsabilicomuni(Responsabili responsabile) {

	Set<Responsabilicomuni> responsabilicomuniList = new LinkedHashSet<Responsabilicomuni>();
	List<Responsabilicomuni> coms = responsabilicomuniService.findByOperatore(responsabile);
	// List<Comuniassociati> comuniassociatiList = comuniassociatiService.findByIdcomune(ORMHelper.getIdcomune());
	for (Responsabilicomuni comuniassociati : coms) {
	    Responsabilicomuni responsabilicomuni = new Responsabilicomuni();
	    ResponsabilicomuniId idRc = new ResponsabilicomuniId();
	    idRc.setCodicecomune(comuniassociati.getId().getCodicecomune());
	    idRc.setCodiceresponsabile(responsabile.getId().getCodice());
	    responsabilicomuni.setId(idRc);
	    responsabilicomuni.setResponsabile(responsabile);
	    responsabilicomuni.setComune(comuniassociati.getComune());
	    responsabilicomuniList.add(responsabilicomuni);
	}
	return responsabilicomuniList;
    }
}
