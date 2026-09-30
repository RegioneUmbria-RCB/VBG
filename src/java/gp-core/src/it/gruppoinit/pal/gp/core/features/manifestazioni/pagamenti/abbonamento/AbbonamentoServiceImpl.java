package it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.AutorizzazioniSubentriDAO;
import it.gruppoinit.pal.gp.core.dao.ComuniDAO;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.helper.RicercaAnagraficeCollegateEnum;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Autorizzazioni;
import it.gruppoinit.pal.gp.core.domain.AutorizzazioniSubentri;
import it.gruppoinit.pal.gp.core.domain.Borsellino;
import it.gruppoinit.pal.gp.core.domain.BorsellinoAccettazioni;
import it.gruppoinit.pal.gp.core.domain.BorsellinoAutorizzazioni;
import it.gruppoinit.pal.gp.core.domain.BorsellinoAutorizzazioniId;
import it.gruppoinit.pal.gp.core.domain.BorsellinoConfigComune;
import it.gruppoinit.pal.gp.core.domain.BorsellinoConfigurazione;
import it.gruppoinit.pal.gp.core.domain.BorsellinoInformative;
import it.gruppoinit.pal.gp.core.domain.BorsellinoMovimenti;
import it.gruppoinit.pal.gp.core.domain.BorsellinoMovimentiImporti;
import it.gruppoinit.pal.gp.core.domain.BorsellinoRicariche;
import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.DettPosizioneDebitoria;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.AutorizzazioniFrontRestBean;
import it.gruppoinit.pal.gp.core.exception.FunzioneBusinessRemotaException;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.AutorizzazioniDAO;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.eventi.EventoOccupanteModificato;
import it.gruppoinit.pal.gp.core.features.buslightyear.exceptions.EventAbortedException;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventPublisher;
import it.gruppoinit.pal.gp.core.features.buslightyear.model.EsitoElaborazioneEvento;
import it.gruppoinit.pal.gp.core.features.buslightyear.model.OperazioneEventoBean;
import it.gruppoinit.pal.gp.core.features.buslightyear.model.OperazioneEventoBean.CHIAMANTE;
import it.gruppoinit.pal.gp.core.features.mailtipo.MailtipoService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.auditing.AggiornamentoMessaggioLogger;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.auditing.AggiungiInformativaLogger;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.auditing.AggiungiRicaricaLogger;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.auditing.CreazioneBorsellinoLogger;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.auditing.EliminazioneMessaggioLogger;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.auditing.IAbbonamentoLogger;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.auditing.ModificaOccupanteAbbonamentoLogger;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.auditing.ModificaOccupanteAbbonamentoLogger.ModificaOccupanteAbbonamentoLoggerEnum;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.auditing.RimuoviInformativaLogger;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.auditing.RimuoviRicaricaLogger;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.auditing.SalvataggioConfigurazioneLogger;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.auditing.SubentroSuAbbonamentoLogger;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.auditing.SubentroSuAbbonamentoLogger.SubentroSuAbbonamentoLoggerMessaggiEnum;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.configurazione.AbbonamentoConfigModel;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.configurazione.AggiornaConfigurazioneBaseModel;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.configurazione.AggiungiInformativaModel;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.configurazione.AggiungiRicaricaModel;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.configurazione.ComuneModel;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.configurazione.DettaglioComuneModel;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.configurazione.IBorsellinoConfigComuneDAO;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.configurazione.IBorsellinoConfigurazioneDAO;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.configurazione.IBorsellinoInformativeDAO;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.configurazione.IBorsellinoRicaricheDAO;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.configurazione.MessaggioNodoPagNonDispModel;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.configurazione.TipoRicaricaEnum;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.eventi.EventoMovimentoInserito;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.eventi.EventoOperazioneAutorizzazione;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.exceptions.BorsellinoException;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.messaggi.MessaggioBorsellinoCreato;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.movimenti.TipoEnum;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.verticalizzazione.VerticalizzazioneAbbonamentoPosteggiServiceImpl;
import it.gruppoinit.pal.gp.core.features.manifestazioni.rest.ambulanti.auditing.CollegamentoAutorizzazioneLogger;
import it.gruppoinit.pal.gp.core.features.manifestazioni.rest.ambulanti.auditing.RicaricaBorsellinoLogger;
import it.gruppoinit.pal.gp.core.features.manifestazioni.rest.ambulanti.auditing.RimuoviRicaricaAbbonamentoAppLogger;
import it.gruppoinit.pal.gp.core.features.manifestazioni.rest.ambulanti.auditing.ScollegamentoAutorizzazioneLogger;
import it.gruppoinit.pal.gp.core.features.manifestazioni.rest.ambulanti.model.BorsellinoAppAutorizzazioni;
import it.gruppoinit.pal.gp.core.features.manifestazioni.rest.ambulanti.model.BorsellinoAppModel;
import it.gruppoinit.pal.gp.core.features.manifestazioni.rest.ambulanti.model.BorsellinoAppMovimenti;
import it.gruppoinit.pal.gp.core.features.manifestazioni.rest.ambulanti.model.StatoBorsellinoPerAnagrafe;
import it.gruppoinit.pal.gp.core.features.manifestazioni.rest.ambulanti.model.EsitoAggiornamentoBorsellino;
import it.gruppoinit.pal.gp.core.features.manifestazioni.rest.ambulanti.model.EsitoAggiornamentoRicariche;
import it.gruppoinit.pal.gp.core.features.manifestazioni.rest.ambulanti.model.EsitoOperazioneAggiornamento;
import it.gruppoinit.pal.gp.core.features.manifestazioni.rest.ambulanti.model.EsitoRicaricaBorsellino;
import it.gruppoinit.pal.gp.core.features.manifestazioni.rest.ambulanti.model.InformativeAbbonamento;
import it.gruppoinit.pal.gp.core.features.manifestazioni.rest.ambulanti.model.JSonBorsellinoAppMovimenti;
import it.gruppoinit.pal.gp.core.features.manifestazioni.rest.ambulanti.model.JsonBorsellinoAutorizzazione;
import it.gruppoinit.pal.gp.core.features.manifestazioni.rest.ambulanti.model.RicaricaBorsellinoRequest;
import it.gruppoinit.pal.gp.core.features.manifestazioni.rest.ambulanti.model.SaldoBorsellinoAppModel;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.NodoPagamentiService;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.StatiPosizioniDebitorieConverter;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.verificastato.VerificaStatoPosizioniDebitorie;
import it.gruppoinit.pal.gp.core.features.oneri.ContiService;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.AmministrazioniService;
import it.gruppoinit.pal.gp.core.service.AnagrafeService;
import it.gruppoinit.pal.gp.core.service.MercatiAppService;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;

@Service
public class AbbonamentoServiceImpl implements IAbbonamentoService {

    private static Logger logger = LoggerFactory.getLogger(AbbonamentoServiceImpl.class);
    private IBorsellinoDAO borsellinoDAO;
    private IBorsellinoConfigurazioneDAO borsellinoConfigurazioneDAO;
    private IBorsellinoConfigComuneDAO borsellinoConfigComuneDAO;
    private VerticalizzazioniService verticalizzazioniService;
    private ContiService contiService;
    private MailtipoService mailTipoService;
    private AmministrazioniService amministrazioniService;
    private AnagrafeService anagrafeService;
    private IBorsellinoRicaricheDAO borsellinoRicaricheDAO;
    private IBorsellinoInformativeDAO borsellinoInformativeDAO;
    private NodoPagamentiService nodoPagamentiService;
    private UserSecurityService userSecurityService;
    @Autowired
    private IBorsellinoAutorizzazioniDAO borsellinoAutorizzazioniDAO;
    @Autowired
    private MercatiAppService mercatiAppService;
    @Autowired
    private AutorizzazioniDAO autorizzazioniDAO;
    @Autowired
    private AutorizzazioniSubentriDAO autorizzazioniSubentriDAO;
    @Autowired
    private ComuniDAO comuniDAO;
    @Autowired
    private IBorsellinoAccettazioniDAO borsellinoAccettazioniDAO;
    @Autowired
    private IBorsellinoMovimentiService borsellinoMovimentiService;
    @Autowired
    private IEventPublisher eventPublisher;
    @Autowired
    private IBorsellinoAutStoricoDAO borsellinoAutStoricoDAO;

    @Autowired
    public void setNodoPagamentiService(NodoPagamentiService nodoPagamentiService) {

	this.nodoPagamentiService = nodoPagamentiService;
    }

    @Autowired
    public void setAnagrafeService(AnagrafeService anagrafeService) {

	this.anagrafeService = anagrafeService;
    }

    @Autowired
    public void setContiService(ContiService contiService) {

	this.contiService = contiService;
    }

    @Autowired
    public void setMailTipoService(MailtipoService mailTipoService) {

	this.mailTipoService = mailTipoService;
    }

    @Autowired
    public void setAmministrazioniService(AmministrazioniService amministrazioniService) {

	this.amministrazioniService = amministrazioniService;
    }

    @Autowired
    public void setVerticalizzazioniService(VerticalizzazioniService verticalizzazioniService) {

	this.verticalizzazioniService = verticalizzazioniService;
    }

    @Autowired
    public void setBorsellinoDAO(IBorsellinoDAO borsellinoDAO) {

	this.borsellinoDAO = borsellinoDAO;
    }

    @Autowired
    public void setBorsellinoConfigurazioneDAO(IBorsellinoConfigurazioneDAO borsellinoConfigurazioneDAO) {

	this.borsellinoConfigurazioneDAO = borsellinoConfigurazioneDAO;
    }

    @Autowired
    public void setBorsellinoConfigComuneDAO(IBorsellinoConfigComuneDAO borsellinoConfigComuneDAO) {

	this.borsellinoConfigComuneDAO = borsellinoConfigComuneDAO;
    }

    @Autowired
    public void setBorsellinoInformativeDAO(IBorsellinoInformativeDAO borsellinoInformativeDAO) {

	this.borsellinoInformativeDAO = borsellinoInformativeDAO;
    }

    @Autowired
    public void setBorsellinoRicaricheDAO(IBorsellinoRicaricheDAO borsellinoRicaricheDAO) {

	this.borsellinoRicaricheDAO = borsellinoRicaricheDAO;
    }

    @Autowired
    public void setUserSecurityService(UserSecurityService userSecurityService) {

	this.userSecurityService = userSecurityService;
    }

    @Override
    public List<AbbonamentoTabellaModel> findAbbonamenti(RicercaBorselliniRequest filtri) {

	List<AbbonamentoTabellaModel> retVal = new ArrayList<AbbonamentoTabellaModel>();
	List<AbbonamentoTabellaModelCompleta> elenco = this.borsellinoDAO.findListaBorsellini(filtri);
	for (AbbonamentoTabellaModelCompleta e : elenco) {
	    retVal.add(AbbonamentoTabellaModel.fromAbbonamentoTabellaModelCompleta(e));
	}
	return retVal;
    }

    @Override
    public Map<Integer, SituazioneBorsellinoPerSoglia> situazioneBorsellinoPerSoglia(Set<Integer> auts, String codiceComune) {

	VerticalizzazioneAbbonamentoPosteggiServiceImpl vertBors = new VerticalizzazioneAbbonamentoPosteggiServiceImpl(verticalizzazioniService,
		this.contiService, this.mailTipoService, this.amministrazioniService, codiceComune);
	List<SituazioneBorsellinoPerSoglia> ret = this.borsellinoDAO.situazioneBorsellinoPerSoglia(auts, vertBors.sogliaAvviso());
	Map<Integer, SituazioneBorsellinoPerSoglia> borsellinoPerAut = new HashMap<Integer, SituazioneBorsellinoPerSoglia>();
	for (SituazioneBorsellinoPerSoglia b : ret) {
	    borsellinoPerAut.put(b.getId(), b);
	}
	return borsellinoPerAut;
    }

    @Override
    public void inserisci(AbbonamentoTabellaModel entity) throws BorsellinoException {

	CreazioneBorsellinoLogger auditLogger = CreazioneBorsellinoLogger.fromModel(entity,
		userSecurityService.getCurrentlyAuthenticatedUserDetails().toString());
	auditLogger.log();
	nuovoBorsellinoPerAnagrafe(entity.getIdAnagrafe(), StatoBorsellinoEnum.fromValue(entity.getStato()));
	auditLogger.logFineMetodo();
    }

    private void checkAnagrafePerInserimento(Integer idAnagrafe) throws BorsellinoException {

	if (this.existsAbbonamentoByAnagrafica(idAnagrafe)) {
	    throw new BorsellinoException("Esiste già un borsellino per questa anagrafica!");
	}
	if (idAnagrafe != null) {
	    Anagrafe a = anagrafeService.findById(new PkId(idAnagrafe));
	    if (a != null && !StringUtils.defaultIfEmpty(a.getTipoanagrafe(), "").equalsIgnoreCase(WebConstants.PERSONA_FISICA)) {
		throw new BorsellinoException(
			"I borsellini possono essere associati solamente a persone fisiche! L'anagrafica scelta è una persona giuridica: " + a);
	    }
	}
    }

    private boolean existsAbbonamentoByAnagrafica(Integer idAnagrafe) {

	return !borsellinoDAO.findByCodiceAnagrafe(idAnagrafe).isEmpty();
    }

    @Override
    public AbbonamentoConfigModel findAbbonamentoConfig(){
	return findAbbonamentoConfig(true);
    }
    
    @Override
    public AbbonamentoConfigModel findAbbonamentoConfig(boolean findComuni) {

	BorsellinoConfigurazione cfg = this.borsellinoConfigurazioneDAO.findConfigurazione();
	if (cfg == null) {
	    return new AbbonamentoConfigModel();
	}
	//1. Informazioni principali
	AbbonamentoConfigModel configurazione = AbbonamentoConfigModel.fromBorsellinoConfigurazione(cfg);
	
	if(findComuni){	
	   //2. Elenco dei comuni e conteggi ( messaggi, informative, ricariche )
	   configurazione.setComuni(this.borsellinoConfigurazioneDAO.findComuni()); 
	}
	return configurazione;
    }

    @Override
    public void salvaMessaggioNodoPagNonDisponibile(MessaggioNodoPagNonDispModel messaggio) {

	AggiornamentoMessaggioLogger auditLogger = AggiornamentoMessaggioLogger.fromModel(messaggio,
		userSecurityService.getCurrentlyAuthenticatedUserDetails().toString());
	auditLogger.log();
	for (ComuneModel comune : messaggio.getComuni()) {
	    BorsellinoConfigComune cfg = new BorsellinoConfigComune();
	    BorsellinoConfigComune trovato = this.borsellinoConfigComuneDAO.findByCodiceComune(comune.getComune());
	    if (trovato != null) {
		cfg.setId(trovato.getId());
	    }
	    cfg.setComune(new Comuni(comune.getComune()));
	    cfg.setMsgNodoPagNonDisp(messaggio.getMessaggio());
	    this.borsellinoConfigComuneDAO.saveEntity(cfg);
	}
	auditLogger.logFineMetodo();
    }

    @Override
    public void eliminaMessaggioNodoPagNonDisponibile(MessaggioNodoPagNonDispModel messaggio) {

	EliminazioneMessaggioLogger auditLogger = EliminazioneMessaggioLogger.fromModel(messaggio,
		userSecurityService.getCurrentlyAuthenticatedUserDetails().toString());
	auditLogger.log();
	for (ComuneModel comune : messaggio.getComuni()) {
	    BorsellinoConfigComune trovato = this.borsellinoConfigComuneDAO.findByCodiceComune(comune.getComune());
	    if (trovato == null) {
		continue;
	    }
	    this.borsellinoConfigComuneDAO.delete(trovato);
	}
	auditLogger.logFineMetodo();
    }

    @Override
    public DettaglioComuneModel findAbbonamentoConfigComune(String codiceComune) {

	return this.borsellinoConfigComuneDAO.findAbbonamentoConfigComune(codiceComune);
    }

    @Override
    public EsitoAggiornamentoRicariche aggiungiRicarica(AggiungiRicaricaModel ricarica) {

	AggiungiRicaricaLogger auditLogger = AggiungiRicaricaLogger.fromModel(ricarica,
		userSecurityService.getCurrentlyAuthenticatedUserDetails().toString());
	auditLogger.log();
	List<String> comuniNonAggiornati = new ArrayList<String>();
	StringBuilder messaggi = new StringBuilder("E' già presente una ricarica di tipo ").append(TipoRicaricaEnum.LIBERO.getValore())
		.append(" per gli enti: ");
	for (BorsellinoRicariche ric : ricarica.toBorsellinoRicariche()) {
	    // Verifica se presente importo libero per ente
	    // ne può esistere solo uno libero per comune
	    if (this.borsellinoRicaricheDAO.importoLiberoConfiguratoPerComune(ric.getComune().getCodicecomune())) {
		// al momento non riporto comuini non aggiornati elimino aggiungo (sovrascrivo)
		this.borsellinoRicaricheDAO.deleteImportiLiberoPerComune(ric.getComune().getCodicecomune());
	    }
	    this.borsellinoRicaricheDAO.insert(ric);
	}
	messaggi.append(" ed il dato non e' stato aggiornato. Cancellare la vecchia configurazione e reinserirla.");
	auditLogger.logFineMetodo();
	return new EsitoAggiornamentoRicariche(comuniNonAggiornati.isEmpty(), comuniNonAggiornati.isEmpty() ? null : messaggi.toString(),
		comuniNonAggiornati);
    }

    @Override
    public void aggiungiInformativa(AggiungiInformativaModel informativa) {

	AggiungiInformativaLogger auditLogger = AggiungiInformativaLogger.fromModel(informativa,
		userSecurityService.getCurrentlyAuthenticatedUserDetails().toString());
	auditLogger.log();
	for (BorsellinoInformative info : informativa.toBorsellinoInformative()) {
	    this.borsellinoInformativeDAO.insert(info);
	}
	auditLogger.logFineMetodo();
    }

    @Override
    public void rimuoviRicarica(int id) {

	RimuoviRicaricaLogger auditLogger = RimuoviRicaricaLogger.fromId(id, userSecurityService.getCurrentlyAuthenticatedUserDetails().toString());
	auditLogger.log();
	this.borsellinoRicaricheDAO.deleteById(id);
	auditLogger.logFineMetodo();
    }

    @Override
    public void rimuoviInformativa(int id) {

	RimuoviInformativaLogger auditLogger = RimuoviInformativaLogger.fromId(id,
		userSecurityService.getCurrentlyAuthenticatedUserDetails().toString());
	auditLogger.log();
	this.borsellinoInformativeDAO.deleteById(id);
	auditLogger.logFineMetodo();
    }

    @Override
    public void salvaConfigurazioneBase(AggiornaConfigurazioneBaseModel cfg) {

	IAbbonamentoLogger auditLogger = SalvataggioConfigurazioneLogger.fromModel(cfg,
		userSecurityService.getCurrentlyAuthenticatedUserDetails().toString());
	auditLogger.log();
	if (cfg == null) {
	    throw new IllegalArgumentException("Impossibile salvare la configurazione senza passare i dati da salvare");
	}
	BorsellinoConfigurazione config = cfg.toBorsellinoConfigurazione();
	BorsellinoConfigurazione trovata = this.borsellinoConfigurazioneDAO.findConfigurazione();
	if (trovata != null) {
	    config.setId(trovata.getId());
	}
	this.borsellinoConfigurazioneDAO.saveEntity(config);
	auditLogger.logFineMetodo();
    }

    @Override
    public Integer ricaricaBorsellino(Integer idBorsellino, BigDecimal importo, String codiceComune, boolean inserisciPosizioneDebitoria)
	    throws BorsellinoException {

	DettPosizioneDebitoria pos = null;
	Borsellino borsellino = borsellinoDAO.findById(new PkId(idBorsellino));
	VerticalizzazioneAbbonamentoPosteggiServiceImpl vertBors = new VerticalizzazioneAbbonamentoPosteggiServiceImpl(verticalizzazioniService,
		this.contiService, this.mailTipoService, this.amministrazioniService, codiceComune);
	RipartizioneContiHelper ripartizione = vertBors.ripartizione();
	if (inserisciPosizioneDebitoria) {
	    try {
		pos = creaPosizionePerBorsellino(codiceComune, importo, borsellino, ripartizione);
	    } catch (FunzioneBusinessRemotaException e) {
		throw new BorsellinoException(e);
	    }
	}
	BorsellinoMovimenti mov = new BorsellinoMovimenti();
	mov.setBorsellino(borsellino);
	mov.setDettPosizioneDebitoria(pos);
	mov.setImporto(importo);
	mov.setData(Calendar.getInstance().getTime());
	mov.setTipo(TipoEnum.RICARICA.name());
	
	BigDecimal saldototale;
	if(borsellino.getSaldoTotale() == null){
	    //Mi affido a questo come saldo
	    BigDecimal saldo = AbbonamentoTabellaModel.fromBorsellino(borsellino).getCreditoResiduo();
	    if(saldo == null){
		saldo = new BigDecimal(0);
	    }	    
	    if(!inserisciPosizioneDebitoria){
		mov.setCreditoIniziale(saldo);		    
		saldototale = saldo.add(importo);
		mov.setCreditoFinale(saldototale);
	    }else{
		mov.setCreditoIniziale(null);
		mov.setCreditoFinale(null);
		saldototale = saldo;
	    }	    	    
	}else{	    
	    if(!inserisciPosizioneDebitoria){
		mov.setCreditoIniziale(borsellino.getSaldoTotale());		    
		saldototale = borsellino.getSaldoTotale().add(importo);
		mov.setCreditoFinale(saldototale);
	    }else{
		mov.setCreditoIniziale(null);
		mov.setCreditoFinale(null);
		saldototale = borsellino.getSaldoTotale();		
	    }	    	    
	}			
	borsellinoDAO.saveEntity(mov);
	
	List<RipartizioneContiRicaricheModel> listaConti = ripartizione.getListaConti();
	for (RipartizioneContiRicaricheModel rip : listaConti) {
	    BorsellinoMovimentiImporti bim = new BorsellinoMovimentiImporti();
	    bim.setBorsellinoMovimenti(mov);
	    bim.setConto(rip.getConto());
	    bim.setImporto(rip.calcolaImportoDaTotale(importo));
	    borsellinoDAO.saveEntity(bim);
	}		
	
	try {
	    eventPublisher.publishThrowOnFailure(new EventoMovimentoInserito(idBorsellino, importo, saldototale));
	} catch (EventAbortedException e) {
	    throw new RuntimeException(e);
	}
	
	return mov.getId().getCodice();
    }
    
    @Override
    public Integer rimborsoBorsellino(Integer idBorsellino, BigDecimal importo, String codiceComune)
	    throws BorsellinoException {

	DettPosizioneDebitoria pos = null;
	Borsellino borsellino = borsellinoDAO.findById(new PkId(idBorsellino));
	VerticalizzazioneAbbonamentoPosteggiServiceImpl vertBors = new VerticalizzazioneAbbonamentoPosteggiServiceImpl(verticalizzazioniService,
		this.contiService, this.mailTipoService, this.amministrazioniService, codiceComune);
	RipartizioneContiHelper ripartizione = vertBors.ripartizione();
	BorsellinoMovimenti mov = new BorsellinoMovimenti();
	mov.setBorsellino(borsellino);
	mov.setDettPosizioneDebitoria(pos);
	mov.setImporto(importo.negate());
	mov.setData(Calendar.getInstance().getTime());
	mov.setTipo(TipoEnum.RIMBORSO.name());
	
	BigDecimal saldototale;
	if(borsellino.getSaldoTotale() == null){
	    //Mi affido a questo come saldo
	    BigDecimal saldo = AbbonamentoTabellaModel.fromBorsellino(borsellino).getCreditoResiduo();
	    mov.setCreditoIniziale(saldo);
	    
	    saldototale = saldo.add(importo.negate());
	    mov.setCreditoFinale(saldototale);
	}else{
	    mov.setCreditoIniziale(borsellino.getSaldoTotale());
	    
	    saldototale = borsellino.getSaldoTotale().add(importo.negate());
	    mov.setCreditoFinale(saldototale);
	}			
	borsellinoDAO.saveEntity(mov);
	
	List<RipartizioneContiRicaricheModel> listaConti = ripartizione.getListaConti();
	for (RipartizioneContiRicaricheModel rip : listaConti) {
	    BorsellinoMovimentiImporti bim = new BorsellinoMovimentiImporti();
	    bim.setBorsellinoMovimenti(mov);
	    bim.setConto(rip.getConto());
	    bim.setImporto(rip.calcolaImportoDaTotale(importo));
	    borsellinoDAO.saveEntity(bim);
	}
	
	
	try {
	    eventPublisher.publishThrowOnFailure(new EventoMovimentoInserito(idBorsellino, importo, saldototale));
	} catch (EventAbortedException e) {
	    throw new RuntimeException(e);
	}
	
	return mov.getId().getCodice();
    }

    private DettPosizioneDebitoria creaPosizionePerBorsellino(String codiceComune, BigDecimal importo, Borsellino borsellino,
	    RipartizioneContiHelper ripartizione) throws FunzioneBusinessRemotaException {

	return nodoPagamentiService.creaPosizionePerBorsellino(codiceComune, importo, borsellino, ripartizione);
    }

    @Override
    public void aggiornaStato(Integer id, StatoBorsellinoEnum sb) {

	Borsellino borsellino = this.borsellinoDAO.findById(new PkId(id));
	borsellino.setStato(sb.getValore());
	this.borsellinoDAO.update(borsellino);
    }

    @Override
    public AbbonamentoTabellaModel findAbbonamentoByAnagrafica(Integer codiceanagrafe) throws BorsellinoException {

	Borsellino b = null;
	List<Borsellino> bs = this.borsellinoDAO.findByCodiceAnagrafe(codiceanagrafe);
	if (bs.isEmpty()) {
	    // borsellino da creare //
	    Anagrafe a = anagrafeService.findById(new PkId(codiceanagrafe));
	    AbbonamentoTabellaModel abb = new AbbonamentoTabellaModel();
	    abb.setIdAnagrafe(codiceanagrafe);
	    abb.setNominativo(a.getDescrizioneRichiedenteBreve());
	    return abb;
	}
	for (Borsellino borsellino : bs) {
	    b = borsellino;
	    if (b.getStato().equalsIgnoreCase(StatoBorsellinoEnum.ATTIVO.name())) {
		// se c'è prendo quello attivo
		// altrimenti il primo non attivo
		break;
	    }
	}
	AbbonamentoTabellaModel abbonamento = AbbonamentoTabellaModel.fromBorsellino(b);
	if(b != null && abbonamento != null){
	    abbonamento.setAutorizzazionihistory(borsellinoAutStoricoDAO.findAutStoricoByBorsellino(b.getId().getCodice()));
	}
	return abbonamento;
    }

    @Override
    public List<String> selectStatoBorsellino() {

	List<String> stati = new ArrayList<String>();
	StatoBorsellinoEnum[] sbe = StatoBorsellinoEnum.values();
	for (int i = 0; i < sbe.length; i++) {
	    stati.add(sbe[i].getValore());
	}
	return stati;
    }

    @Override
    public AbbonamentoTabellaModel findAbbonamentoById(Integer idBorsellino) {

	return AbbonamentoTabellaModel.fromBorsellino(borsellinoDAO.findById(new PkId(idBorsellino)));
    }

    @Override
    public List<AutorizzazioniModel> findAllAutorizzazioni() {

	return this.borsellinoDAO.findAllAutorizzazioni();
    }

    @Override
    public List<Integer> findBorselliniPerAutorizzazione(String autorizzazione) {

	return borsellinoDAO.findBorselliniPerAutorizzazione(autorizzazione);
    }

    @Override
    public BorsellinoAppModel getBorsellino(Integer codiceAnagrafe) throws BorsellinoException {

	Borsellino b = borsellinoDAO.findBorsellinoAttivoByCodiceAnagrafe(codiceAnagrafe);
	if (b == null) {
	    return emptyBorsellino();
	}
	return getBorsellinoInternal(b);
    }
    
    @Override
    public BorsellinoAppModel getBorsellinoFromId(Integer idBorsellino) throws BorsellinoException {

	Borsellino b = borsellinoDAO.findById(new PkId(idBorsellino));
	if (b == null) {
	    return emptyBorsellino();
	}
	return getBorsellinoInternal(b);
    }
    
    @Override
    public List<BorsellinoAppMovimenti> getMovimentiFilteredPaginated(Integer codiceAnagrafe, Date dalladata, Date alladata, 
	    Integer firstresults, Integer maxresults, List<TipoEnum> tipoenums){
	try {
	    Borsellino b = borsellinoDAO.findBorsellinoAttivoByCodiceAnagrafe(codiceAnagrafe);
	    List<BorsellinoAppMovimenti> ret = new ArrayList<BorsellinoAppMovimenti>();
	    if (b == null) {
		    return ret;
	    }

	    List<BorsellinoMovimenti> mov = borsellinoMovimentiService.findByBorsellino(b.getId().getCodice(), dalladata, alladata, firstresults, maxresults, tipoenums);
	    for (BorsellinoMovimenti borsellinoMovimenti : mov) {
		ret.add(BorsellinoAppMovimenti.fromBorsellinoMovimenti(borsellinoMovimenti, nodoPagamentiService));
	    }
	    return ret;
	    
	} catch (BorsellinoException e) {
	    throw new RuntimeException(e);
	}
    }

    private BorsellinoAppModel getBorsellinoInternal(Borsellino borsellino) {

	BorsellinoAppModel ret = BorsellinoAppModel.fromBorsellino(borsellino);
	setAutorizzazioni(borsellino, ret);
	ret.getMovimenti().addAll(getMovimentiComune(borsellino.getId().getCodice()));
	ret.impostaSaldo();
	return ret;
    }

    private void setAutorizzazioni(Borsellino borsellino, BorsellinoAppModel ret) {

	Set<Integer> autCollegate = new HashSet<Integer>();
	List<BorsellinoAutorizzazioni> auts = borsellinoAutorizzazioniDAO.findAutorizzazioniByBorsellino(borsellino.getId().getCodice());
	for (BorsellinoAutorizzazioni ba : auts) {
	    autCollegate.add(ba.getId().getFkIdAutorizzazioni());
	    ret.getAutorizzazioniCollegate().add(BorsellinoAppAutorizzazioni.fromAutorizzazione(ba.getAutorizzazione()));
	}
	List<AutorizzazioniFrontRestBean> result = mercatiAppService.findAutorizzazioniHelperByUtente(borsellino.getAnagrafe(), false, null, true);
	for (AutorizzazioniFrontRestBean autsFB : result) {
	    if (!autCollegate.contains(autsFB.getId())) {
		ret.getAutorizzazioniCollegabili()
			.add(BorsellinoAppAutorizzazioni.fromAutorizzazione(autorizzazioniDAO.findById(new PkId(autsFB.getId()))));
	    }
	}
    }

    @Override
    public BorsellinoAppMovimenti getMovimentoComune(Integer idBorsellino, Integer idMovimento) {

	List<BorsellinoAppMovimenti> movimentiComune = this.getMovimentiComune(idBorsellino);
	for (BorsellinoAppMovimenti borsellinoAppMovimenti : movimentiComune) {
	    if (borsellinoAppMovimenti.getId().equals(idMovimento)) {
		return borsellinoAppMovimenti;
	    }
	}
	return null;
    }

    private List<BorsellinoAppMovimenti> getMovimentiComune(Integer idBorsellino) {

	List<BorsellinoAppMovimenti> ret = new ArrayList<BorsellinoAppMovimenti>();
	List<BorsellinoMovimenti> mov = borsellinoMovimentiService.findByBorsellino(idBorsellino);
	for (BorsellinoMovimenti borsellinoMovimenti : mov) {
	    if (!(borsellinoMovimenti.getTipo().equalsIgnoreCase(TipoEnum.STORNO.name()) || borsellinoMovimenti.getMovimentoStornoId() != null)) {
		// nell'app ambulanti faccio veder solo i movimenti non stornati
		ret.add(BorsellinoAppMovimenti.fromBorsellinoMovimenti(borsellinoMovimenti, nodoPagamentiService));
	    }
	}
	return ret;
    }

    private BorsellinoAppModel emptyBorsellino() {

	return new BorsellinoAppModel();
    }

    @Override
    public EsitoAggiornamentoBorsellino collegaAutorizzazione(String uuidBorsellino, Integer idAutorizzazione) throws BorsellinoException {

	JsonBorsellinoAutorizzazione request = new JsonBorsellinoAutorizzazione(uuidBorsellino, idAutorizzazione);
	CollegamentoAutorizzazioneLogger auditLogger = CollegamentoAutorizzazioneLogger.fromModel(request,
		userSecurityService.getCurrentlyAuthenticatedUserDetails().toString());
	auditLogger.log();
	EsitoOperazioneAggiornamento esito = this.borsellinoAutorizzazioniDAO.collegaAutorizzazione(uuidBorsellino, idAutorizzazione);
	try {
	    eventPublisher.publishThrowOnFailure(new EventoOperazioneAutorizzazione(null, uuidBorsellino, idAutorizzazione, TipoOperazioneBorsAutEnum.INSERIMENTO));
	} catch (EventAbortedException e) {
	    throw new RuntimeException(e);
	}
	auditLogger.logFineMetodo();
	return new EsitoAggiornamentoBorsellino(esito.isEsito(), esito.getMessaggio());
    }

    @Override
    public EsitoAggiornamentoBorsellino rimuoviAutorizzazione(String uuidBorsellino, Integer idAutorizzazione) throws BorsellinoException {

	JsonBorsellinoAutorizzazione request = new JsonBorsellinoAutorizzazione(uuidBorsellino, idAutorizzazione);
	ScollegamentoAutorizzazioneLogger auditLogger = ScollegamentoAutorizzazioneLogger.fromModel(request,
		userSecurityService.getCurrentlyAuthenticatedUserDetails().toString());
	auditLogger.log();
	//Il borsellino serve come ulteriore verifica che sia stata passata l'autorizzazione corretta
	//ed il borsellino corretto, essendo chiamate che potrebbero arrivare tramite API autenticate
	Borsellino b = borsellinoDAO.findByUuid(uuidBorsellino);
	BorsellinoAutorizzazioni autDaRimuovere = borsellinoAutorizzazioniDAO
		.findById(new BorsellinoAutorizzazioniId(ORMHelper.getIdcomune(), b.getId().getCodice(), idAutorizzazione));
	if (autDaRimuovere == null) {
	    return new EsitoAggiornamentoBorsellino(false, "Nessuna autorizzazione rimossa");
	}		
	this.borsellinoAutorizzazioniDAO.scollegaAutorizzazione(idAutorizzazione);
	try {
	    eventPublisher.publishThrowOnFailure(new EventoOperazioneAutorizzazione(b.getId().getCodice(), uuidBorsellino, idAutorizzazione, TipoOperazioneBorsAutEnum.CANCELLAZIONE));
	} catch (EventAbortedException e) {
	    throw new RuntimeException(e);
	}
	auditLogger.logFineMetodo();
	return new EsitoAggiornamentoBorsellino(true, "Autorizzazione rimossa");
    }

    @Override
    public EsitoRicaricaBorsellino ricaricaBorsellino(RicaricaBorsellinoRequest request, Integer codiceAnagrafe) {

	RicaricaBorsellinoLogger auditLogger = RicaricaBorsellinoLogger.fromModel(request, codiceAnagrafe,
		userSecurityService.getCurrentlyAuthenticatedUserDetails().toString());
	auditLogger.log();
	List<String> errori = validaRequest(request);
	if (!errori.isEmpty()) {
	    EsitoRicaricaBorsellino ret = new EsitoRicaricaBorsellino(null);
	    String messaggio = toStringMesssage(errori);
	    ret.setEsito(new EsitoAggiornamentoBorsellino(false, messaggio));
	    auditLogger.logError(messaggio);
	    auditLogger.logFineMetodo();
	    return ret;
	}
	List<Borsellino> bs = borsellinoDAO.findByCodiceAnagrafe(codiceAnagrafe);
	// metodo di ricerca che tira fuori se presente per anagrafe e dovrebbe tirare fuori
	// o il borsellino attivo o il borsellino non attivo o null
	EsitoRicaricaBorsellino ret = new EsitoRicaricaBorsellino("");
	Borsellino b = null;
	if (bs.isEmpty()) {
	    // borsellino da creare //
	    try {
		b = nuovoBorsellinoPerAnagrafe(codiceAnagrafe, StatoBorsellinoEnum.ATTIVO);
	    } catch (BorsellinoException e) {
		String messaggio = "Si è verificato un problema nella fase di ricarica " + e.getMessage();
		logger.error(messaggio, e);
		auditLogger.logError(messaggio);
		ret.setEsito(new EsitoAggiornamentoBorsellino(false, messaggio));
	    }
	}
	for (Borsellino borsellino : bs) {
	    b = borsellino;
	    if (b.getStato().equalsIgnoreCase(StatoBorsellinoEnum.ATTIVO.name())) {
		// se c'è prendo quello attivo
		// altrimenti il primo non attivo
		break;
	    }
	}
	// ricarica borsellino esistente
	BorsellinoMovimenti mov = null;
	if (b != null) {
	    ret = new EsitoRicaricaBorsellino(b.getUuid());
	    try {
		mov = ricaricaBorsellino(b, request);
		ret.setIdRicarica(mov.getId().getCodice());
		ret.setPosizioneDebitoria(nodoPagamentiService.populatePosizioneDebitoriaBorsellino(mov.getDettPosizioneDebitoriaId()));
		ret.setEsito(new EsitoAggiornamentoBorsellino(true, "Ricarica effettuata"));
	    } catch (BorsellinoException e) {
		String messaggio = "Si è verificato un problema nella fase di ricarica " + e.getMessage();
		auditLogger.logError(messaggio);
		ret.setEsito(new EsitoAggiornamentoBorsellino(false, messaggio));
	    }
	} else {
	    ret = new EsitoRicaricaBorsellino(null);
	    ret.setEsito(new EsitoAggiornamentoBorsellino(false, "Borsellino non trovato per il codiceanagrafe " + codiceAnagrafe));
	}
	auditLogger.logFineMetodo();
	return ret;
    }

    private Borsellino nuovoBorsellinoPerAnagrafe(Integer codiceAnagrafe, StatoBorsellinoEnum stato) throws BorsellinoException {

	checkAnagrafePerInserimento(codiceAnagrafe);
	Borsellino b = new Borsellino();
	b.setAnagrafe(borsellinoDAO.getById(Anagrafe.class, codiceAnagrafe));
	b.setDataCreazione(Calendar.getInstance().getTime());
	b.setDescrizione(new MessaggioBorsellinoCreato(b.getAnagrafe()).getTestoMessaggio());
	b.setStato(stato.name());
	b.setUuid(UUID.randomUUID().toString());
	borsellinoDAO.insert(b);
	collegaAutorizzazioniUtente(b);
	return b;
    }

    private void collegaAutorizzazioniUtente(Borsellino b) throws BorsellinoException {

	BorsellinoConfigurazione cfg = borsellinoConfigurazioneDAO.findConfigurazione();
	if (cfg != null && cfg.getTipoInstallazione().equalsIgnoreCase(ComportamentoEnum.OPERATORE.name())) {
	    logger.debug("Collego le autorizzazioni al borsellino {} - {}", b.getId().getCodice(), b.getDescrizione());
	    List<AutorizzazioniFrontRestBean> auts = mercatiAppService.findAutorizzazioniHelperByUtente(b.getAnagrafe(), false, null, true);
	    for (AutorizzazioniFrontRestBean a : auts) {
		logger.debug("Collego l'autorizzazione {}-{} - al borsellino {}", new Object[] { a.getId(), a.getNumero(), b.getId() });
		List<BorsellinoAutorizzazioni> bAuts = borsellinoAutorizzazioniDAO.findByIdAutorizzazione(a.getId());
		for (BorsellinoAutorizzazioni autDaCancellare : bAuts) {
		    logger.debug("Autorizzazione {}-{} già presente la rimuovo dal borsellino {} per associarla al borsellino {}",
			    new Object[] { a.getId(), a.getNumero(), autDaCancellare.getBorsellino().getId(), b.getId() });
		    rimuoviAutorizzazione(autDaCancellare.getBorsellino().getUuid(), autDaCancellare.getId().getFkIdAutorizzazioni());
		}
		collegaAutorizzazione(b.getUuid(), a.getId());
		logger.debug("Autorizzazione {}-{} collegata al borsellino {}", new Object[] { a.getId(), a.getNumero(), b.getId() });
	    }
	}
    }

    private BorsellinoMovimenti ricaricaBorsellino(Borsellino b, RicaricaBorsellinoRequest request) throws BorsellinoException {

	// 1. accetta le informative
	inserisciInformative(request.getIdInformative(), b);
	// 2. effettua ricarica (inserisce posizione debitoria)
	Integer movimentoRicarica = this.ricaricaBorsellino(b.getId().getCodice(), request.getImporto(), request.getCodiceComune(), true);
	return borsellinoMovimentiService.findById(new PkId(movimentoRicarica));
    }

    private void inserisciInformative(List<InformativeAbbonamento> idInformative, Borsellino b) {

	if (!idInformative.isEmpty()) {
	    for (InformativeAbbonamento info : idInformative) {
		BorsellinoAccettazioni e = new BorsellinoAccettazioni();
		e.setBorsellino(b);
		e.setDataAccettazione(Calendar.getInstance().getTime());
		e.setInformativa(borsellinoAccettazioniDAO.getById(BorsellinoInformative.class, info.getId()));
		borsellinoAccettazioniDAO.insert(e);
	    }
	}
    }

    private String toStringMesssage(List<String> errori) {

	StringBuilder errore = new StringBuilder("Si sono verificati i seguenti errori: ");
	for (String e : errori) {
	    if (StringUtils.isNotBlank(e)) {
		errore.append("\n").append(e).append(", ");
	    }
	}
	logger.error("{}", errore);
	return errore.toString();
    }

    private List<String> validaRequest(RicaricaBorsellinoRequest request) {

	List<String> errs = new ArrayList<String>();
	if (request == null) {
	    errs.add("Richiesta non valida");
	    return errs;
	}
	if (StringUtils.isBlank(request.getCodiceComune())) {
	    errs.add("Comune non specificato");
	} else {
	    Comuni c = comuniDAO.findById(request.getCodiceComune());
	    if (c == null) {
		errs.add("Comune specificato non valido");
	    }
	}
	if (request.getImporto() == null || request.getImporto().equals(BigDecimal.ZERO) || request.getImporto().intValue() < 1) {
	    errs.add("Importo non valido");
	}
	return errs;
    }

    @Override
    public EsitoOperazioneAggiornamento rimuoviRicarica(String uuid, Integer idRicarica) {

	if (idRicarica == null || StringUtils.isBlank(uuid)) {
	    return new EsitoOperazioneAggiornamento(false, "Le informazioni inserite non sono corrette");
	}
	BorsellinoMovimenti mov = borsellinoMovimentiService.findById(new PkId(idRicarica));
	if (mov == null // movimento non nullo 
		|| StringUtils.isBlank(mov.getBorsellino().getUuid()) // uuid del borsellino nullo		
		|| !StringUtils.defaultString(mov.getBorsellino().getUuid()).equalsIgnoreCase(uuid) // uuid ricarica non è del borsellino
		|| !mov.getTipo().equalsIgnoreCase(TipoEnum.RICARICA.name())) { // non è un movimento di ricarica	    
	    return new EsitoOperazioneAggiornamento(false, "Le informazioni inserite non sono corrette");
	}
	if (mov.getDettPosizioneDebitoriaId() != null) {
	    try {
		VerificaStatoPosizioniDebitorie statoPos = nodoPagamentiService
			.aggiornaStatoPagamentoByIdDettPosizioneDebitoria(mov.getDettPosizioneDebitoriaId());
		boolean statoChiusoPositivamente = new StatiPosizioniDebitorieConverter()
			.isStatoChiusoPositivamente(statoPos.getStatoAttuale().getCodiceStato());
		if (statoChiusoPositivamente) {
		    logger.error(
			    "Errore nella cancellazione della ricarica con posizione debitoria {} del movimento di ricarica {} lo stato risulta pagato",
			    mov.getDettPosizioneDebitoriaId(), mov.getId());
		    return new EsitoOperazioneAggiornamento(false, "Non e' possibile cancellare una ricarica con stato pagato");
		}
	    } catch (FunzioneBusinessRemotaException e1) {
		logger.error("Errore nel recupero dello stato di pagamento per la posizione debitoria " + mov.getDettPosizioneDebitoriaId() +
			     " del movimento di ricarica " + mov.getId() + ", errore: " + e1.getMessage(),
			e1);
		return new EsitoOperazioneAggiornamento(false,
			"Non e' stato possibile recuperare le informazioni sullo stato di pagamento della posizione debitoria: " + e1.getMessage());
	    }
	}
	String messaggio = null;
	boolean esito = true;
	RimuoviRicaricaAbbonamentoAppLogger auditLogger = new RimuoviRicaricaAbbonamentoAppLogger(
		new JSonBorsellinoAppMovimenti(uuid, BorsellinoAppMovimenti.fromBorsellinoMovimenti(mov, nodoPagamentiService)),
		userSecurityService.getCurrentlyAuthenticatedUserDetails().toString());
	auditLogger.log();
	if (mov.getDettPosizioneDebitoriaId() != null) {
	    try {
		nodoPagamentiService.annullaPosizioneDebitoria(mov.getDettPosizioneDebitoriaId());
	    } catch (FunzioneBusinessRemotaException e) {
		// annulla la posizione debitoria
		messaggio = "Errore nell'annullamento della posizione debitoria: " + e.getMessage();
		esito = false;
		auditLogger.logError(messaggio);
	    }
	}
	// non rimuovo la ricarica in quando la devo ritrovare tra i movimenti con posizioni annullate nel back
	// Task 23349: BACKEND ABBONAMENTO: ANNULLANDO UN APOSIZIONE DEBITORIA SU APP SPUNTISTI SU BACK SPARISCE E NON COMPARE ANNULLATA
	auditLogger.logFineMetodo();
	return new EsitoOperazioneAggiornamento(esito, messaggio);
    }

    @Override
    public void gestisciSubentroAutorizzazione(Integer idAutorizzazioneSubentri) throws BorsellinoException {

	// Se faccio un subentro l'autorizzazione collegata ad un borsellino la sposto automaticamente su borsellino nuova anagrafe se (Operatore)
	AutorizzazioniSubentri subentro = autorizzazioniSubentriDAO.findById(new PkId(idAutorizzazioneSubentri));
	Integer codiceAutorizzazioneSubentrata = subentro.getAutorizzazioni().getId().getCodice();
	Autorizzazioni autSubentrata = autorizzazioniDAO.findById(new PkId(codiceAutorizzazioneSubentrata));
	SubentroSuAbbonamentoLogger auditLogger = new SubentroSuAbbonamentoLogger(autSubentrata,
		userSecurityService.getCurrentlyAuthenticatedUserDetails().toString());
	auditLogger.log();
	Integer codiceAnagrafeSubentrante = autSubentrata.getOccupante().getId().getCodice();
	Anagrafe subentrante = anagrafeService.findById(new PkId(codiceAnagrafeSubentrante));
	List<BorsellinoAutorizzazioni> borselliniCollegatiAllAutorizzazione = borsellinoAutorizzazioniDAO
		.findByIdAutorizzazione(codiceAutorizzazioneSubentrata);
	for (BorsellinoAutorizzazioni borsellinoAutorizzazioni : borselliniCollegatiAllAutorizzazione) {
	    if (!verificaAnagraficaBorsellino(borsellinoAutorizzazioni.getBorsellino().getAnagrafeID(), codiceAnagrafeSubentrante)) { // li sgancio solo se non sono collegati al subentrante
		// In ogni caso la sgancio dal borsellino del vecchio titolare (o cedente in affitto)
		rimuoviAutorizzazione(borsellinoAutorizzazioni.getBorsellino().getUuid(), codiceAutorizzazioneSubentrata);
	    }
	}
	BorsellinoConfigurazione cfg = borsellinoConfigurazioneDAO.findConfigurazione();
	if (cfg != null && cfg.getTipoInstallazione().equalsIgnoreCase(ComportamentoEnum.OPERATORE.name())) {
	    collegaAlBorsellino(subentrante.getId().getCodice(), codiceAutorizzazioneSubentrata);
	    auditLogger.addMessaggio(SubentroSuAbbonamentoLoggerMessaggiEnum.AUTORIZZAZIONE_COLLEGATA_A_BORSELLINO).log();
	    // SE NON PRESENTE UN BORSELLINO NON NE POSSO CREARE UNO PER L'ANAGRAFE DEL SUBENTRANTE (POTREBBE ESSERE UNA PERSONA GIURIDICA
	    // IL BORSELLINO LO DEVE CREARE ENTRANDO NELL'APP O E' GIA' CREATO
	}
	auditLogger.logFineMetodo();
    }

    @Override
    public void collegaAlBorsellino(Integer codiceOccupante, Integer idAutorizzazione) throws BorsellinoException {

	Borsellino bAttivo = null;
	bAttivo = borsellinoDAO.findBorsellinoAttivoByCodiceAnagrafe(codiceOccupante);
	if (bAttivo != null) {
	    // ho trovato il borsellino per l'occupante collego l'autorizzazione
	    this.collegaAutorizzazione(bAttivo.getUuid(), idAutorizzazione);
	} else {
	    // non ho trovato il borsellino per l'occupante lo cerco per le anagrafiche collegate
	    Set<Integer> codiciAnagraficheCollegate = anagrafeService.findCodiciAnagraficheCollegate(codiceOccupante,
		    RicercaAnagraficeCollegateEnum.SOLO_ISTANZE_CON_AUTORIZZAZIONI_MERCATI);
	    for (Integer codiceAnagrafe : codiciAnagraficheCollegate) {
		if (!codiceAnagrafe.equals(codiceOccupante)) { // codiceOccupante lo ho già verificato
		    bAttivo = borsellinoDAO.findBorsellinoAttivoByCodiceAnagrafe(codiceAnagrafe);
		    if (bAttivo != null) {
			this.collegaAutorizzazione(bAttivo.getUuid(), idAutorizzazione);
			break;
		    }
		}
	    }
	}
    }

    /**
     * Il metodo verifica i CF /PIVA delle due anagrafiche potrebbero essere la stessa anagrafica PF/PG. lo verifico sul
     * campo codice fiscale
     * 
     * @param anagrafeBorsellino
     * @param codiceAnagrafeSubentrante
     * @return
     */
    private boolean verificaAnagraficaBorsellino(Integer anagrafeBorsellino, Integer codiceAnagrafeSubentrante) {

	Anagrafe utenteBorsellino = anagrafeService.findById(new PkId(anagrafeBorsellino));
	Anagrafe subentrante = anagrafeService.findById(new PkId(codiceAnagrafeSubentrante));
	String cf = StringUtils.defaultIfEmpty(utenteBorsellino.getCodicefiscale(), "-1111azazaa").toLowerCase().trim();
	String cfSub = StringUtils.defaultIfEmpty(subentrante.getCodicefiscale(), "-2222babababzb").toLowerCase().trim();
	return cf.equalsIgnoreCase(cfSub);
    }

    @Override
    public EsitoElaborazioneEvento checkPossoModificareOccupante(Integer idAutOConc, Integer nuovoOccupante) {

	List<OperazioneEventoBean> warnings = new ArrayList<OperazioneEventoBean>();
	List<OperazioneEventoBean> errors = new ArrayList<OperazioneEventoBean>();
	Autorizzazioni aut = autorizzazioniDAO.findById(new PkId(idAutOConc));
	List<BorsellinoAutorizzazioni> borselliniCollegatiAllAutorizzazione = borsellinoAutorizzazioniDAO.findByIdAutorizzazione(idAutOConc);
	for (BorsellinoAutorizzazioni borsellinoAutorizzazioni : borselliniCollegatiAllAutorizzazione) {
	    if (!borsellinoAutorizzazioni.getBorsellino().getAnagrafeID().equals(nuovoOccupante)) {
		String messaggio = "L'autorizzazione " + aut.getTransientEstremiAut() + " sara' rimossa dal borsellino di " +
				   borsellinoAutorizzazioni.getBorsellino().getAnagrafe().getDescrizioneRichiedente();
		OperazioneEventoBean ope = new OperazioneEventoBean(String.valueOf(idAutOConc), messaggio, CHIAMANTE.ABBONAMENTI_MODIFICA_OCCUPANTE);
		warnings.add(ope);
	    }
	}
	return new EsitoElaborazioneEvento(warnings, errors);
    }

    @Override
    public void gestisciModificaOccupanteAutorizzazione(EventoOccupanteModificato e) throws BorsellinoException {

	// Se modifico l'occupante dell'autorizzazione collegata ad un borsellino la sposto automaticamente su borsellino nuova anagrafe se (Operatore)
	Autorizzazioni aut = autorizzazioniDAO.findById(new PkId(e.getIdAuOConc()));
	ModificaOccupanteAbbonamentoLogger auditLogger = new ModificaOccupanteAbbonamentoLogger(aut,
		userSecurityService.getCurrentlyAuthenticatedUserDetails().toString());
	auditLogger.log();
	Integer codiceAnagrafeNuovoOccupante = e.getNuovoOccupante();
	Anagrafe subentrante = anagrafeService.findById(new PkId(codiceAnagrafeNuovoOccupante));
	List<BorsellinoAutorizzazioni> borselliniCollegatiAllAutorizzazione = borsellinoAutorizzazioniDAO.findByIdAutorizzazione(e.getIdAuOConc());
	for (BorsellinoAutorizzazioni borsellinoAutorizzazioni : borselliniCollegatiAllAutorizzazione) {
	    if (!verificaAnagraficaBorsellino(borsellinoAutorizzazioni.getBorsellino().getAnagrafeID(), codiceAnagrafeNuovoOccupante)) { // li sgancio solo se non sono collegati al subentrante
		// In ogni caso la sgancio dal borsellino del vecchio titolare (o cedente in affitto)
		rimuoviAutorizzazione(borsellinoAutorizzazioni.getBorsellino().getUuid(), e.getIdAuOConc());
	    }
	}
	BorsellinoConfigurazione cfg = borsellinoConfigurazioneDAO.findConfigurazione();
	if (cfg != null && cfg.getTipoInstallazione().equalsIgnoreCase(ComportamentoEnum.OPERATORE.name())) {
	    collegaAlBorsellino(subentrante.getId().getCodice(), e.getIdAuOConc());
	    auditLogger.addMessaggio(ModificaOccupanteAbbonamentoLoggerEnum.AUTORIZZAZIONE_COLLEGATA_A_BORSELLINO).log();
	    // SE NON PRESENTE UN BORSELLINO NON NE POSSO CREARE UNO PER L'ANAGRAFE DEL SUBENTRANTE (POTREBBE ESSERE UNA PERSONA GIURIDICA
	    // IL BORSELLINO LO DEVE CREARE ENTRANDO NELL'APP O E' GIA' CREATO
	}
	auditLogger.logFineMetodo();
    }

    @Override
    public String findBorsellinoUUID(Integer codiceAnagrafe) throws BorsellinoException {

	Borsellino b = borsellinoDAO.findBorsellinoAttivoByCodiceAnagrafe(codiceAnagrafe);
	if (b != null) {
	    return b.getUuid();
	}
	return null;
    }

    @Override
    public StatoBorsellinoPerAnagrafe checkStatoBorsellinoPerAnagrafe(Integer codiceAnagrafe) {

	List<Borsellino> bs = this.borsellinoDAO.findByCodiceAnagrafe(codiceAnagrafe);
	boolean almenoUno = false;
	for (Borsellino borsellino : bs) {
	    almenoUno = true;
	    if (borsellino.getStato().equalsIgnoreCase(StatoBorsellinoEnum.ATTIVO.name())) {
		// se ce ne è almeno uno attivo per questa anagrafica torno che è attivo
		return new StatoBorsellinoPerAnagrafe(StatoBorsellinoEnum.ATTIVO.name());
	    }
	}
	if (!almenoUno) {
	    return new StatoBorsellinoPerAnagrafe("NON_PRESENTE");
	}
	return new StatoBorsellinoPerAnagrafe(StatoBorsellinoEnum.NONATTIVO.name());
    }

    @Override
    public List<Integer> findBorselliniPerIdAutorizzazione(Integer idAutorizzazione) {

	List<Integer> ret = new ArrayList<Integer>();
	List<BorsellinoAutorizzazioni> bs = borsellinoAutorizzazioniDAO.findByIdAutorizzazione(idAutorizzazione);
	for (BorsellinoAutorizzazioni ba : bs) {
	    ret.add(ba.getId().getFkIdBorsellino());
	}
	return ret;
    }
    
    @Override
    public SaldoBorsellinoAppModel findSaldoBorsellinoForApp(Integer codiceAnagrafe) throws BorsellinoException{
	BorsellinoConfigurazione cfg = this.borsellinoConfigurazioneDAO.findConfigurazione();
	Borsellino b = borsellinoDAO.findBorsellinoAttivoByCodiceAnagrafe(codiceAnagrafe);
	if (b == null) {
	    SaldoBorsellinoAppModel saldo = new SaldoBorsellinoAppModel();
	    saldo.setAttuale(new BigDecimal(0));
	    saldo.setLimite(cfg.getImportomassimo());
	    saldo.setMaxRicaricabile(saldo.getLimite() != null ? saldo.getLimite() : null);
	    return saldo;
	}	
	
	SaldoBorsellinoAppModel saldo = new SaldoBorsellinoAppModel();
	saldo.setLimite(cfg.getImportomassimo());
	saldo.setAttuale(b.getSaldoTotale() != null ? b.getSaldoTotale() : new BigDecimal(0));
	
	if(saldo.getAttuale() == null || saldo.getLimite() == null){
	    saldo.setMaxRicaricabile(null);
	}else if(saldo.getLimite().compareTo(saldo.getAttuale()) <= 0){
	    saldo.setMaxRicaricabile(new BigDecimal(0));
	}else{
	    saldo.setMaxRicaricabile(saldo.getLimite().subtract(saldo.getAttuale()));
	}
	
	return saldo;
    }
}
