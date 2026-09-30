package it.gruppoinit.pal.gp.backoffice.ws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import it.gruppoinit.pal.gp.backoffice.definitions.attivita.Attivita;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.attivita.AggiornaCampiSchedeResponse;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.attivita.SchedaDinamicaAggiuntaAdAttivitaRequest;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.attivita.SchedaDinamicaAttivitaEliminataRequest;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.attivita.SchedaDinamicaAttivitaSalvataRequest;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.attivita.SchedaDinamicaIstanzaEliminataRequest;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.attivita.SchedaDinamicaIstanzaSalvataRequest;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.base.ErroreBackofficeType;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.base.EsitoOperazioneType;
import it.gruppoinit.pal.gp.core.features.attivita.datidinamici.IGeneratoreEventiDatiDinamiciService;
import it.gruppoinit.pal.gp.core.ws.BaseWS;

@javax.jws.WebService(serviceName = "Attivita", portName = "attivitaSOAP", targetNamespace = "http://gruppoinit.it/sigepro/definitions/attivita", endpointInterface = "it.gruppoinit.pal.gp.backoffice.definitions.attivita.Attivita")
public class AttivitaWS extends BaseWS implements Attivita {

    Logger log = LoggerFactory.getLogger(AttivitaWS.class);
    //private IAttivitaService iAttivitaService;
    //private IstanzeService istanzeService;
    //private IAttivitaSnapshotService iAttivitaSnapshotService;
    @Autowired
    private IGeneratoreEventiDatiDinamiciService generatoreEventiDatiDinamiciService;
    private final static String SOFTWARE_TT = "TT";

    /*
    @Autowired
    public void setiAttivitaService(IAttivitaService iAttivitaService) {
    
    this.iAttivitaService = iAttivitaService;
    }
    
    @Autowired
    public void setIstanzeService(IstanzeService istanzeService) {
    
    this.istanzeService = istanzeService;
    }
    
    @Autowired
    public void setiAttivitaSnapshotService(IAttivitaSnapshotService iAttivitaSnapshotService) {
    
    this.iAttivitaSnapshotService = iAttivitaSnapshotService;
    }
    */
    @Override
    public AggiornaCampiSchedeResponse schedaDinamicaAttivitaSalvata(SchedaDinamicaAttivitaSalvataRequest request) {

	log.debug("schedaDinamicaAttivitaSalvata# inizio del metodo WS schedaDinamicaAttivitaSalvata");
	AggiornaCampiSchedeResponse response = new AggiornaCampiSchedeResponse();
	EsitoOperazioneType esitoOperazioneType = new EsitoOperazioneType();
	try {
	    setORMHelper(AttivitaWS.SOFTWARE_TT, request.getToken());
	    this.generatoreEventiDatiDinamiciService.generaEventoSchedaAttivitaSalvata(request.getCodiceAttivita(), request.getCodiceScheda());
	    esitoOperazioneType.setEsito(1);
	    response.setEsito(esitoOperazioneType);
	} catch (Exception e) {
	    String err = this.getRootCause(e);
	    log.error("schedaDinamicaAttivitaSalvata: token={}, codiceAttivita={}, codiceScheda={}, ERRORE={}",
		    new Object[] { request.getToken(), request.getCodiceAttivita(), request.getCodiceScheda(), err });
	    esitoOperazioneType.setEsito(0);
	    ErroreBackofficeType erroreBackofficeType = new ErroreBackofficeType();
	    erroreBackofficeType.setCodice("Errore");
	    erroreBackofficeType.setDescrizione(err);
	    esitoOperazioneType.getListaErrori().add(erroreBackofficeType);
	    response.setEsito(esitoOperazioneType);
	} finally {
	    resetThreadLocalVars();
	}
	log.debug("schedaDinamicaAttivitaSalvata# fine del metodo WS schedaDinamicaAttivitaSalvata");
	return response;
    }

    @Override
    public AggiornaCampiSchedeResponse schedaDinamicaAttivitaEliminata(SchedaDinamicaAttivitaEliminataRequest request) {

	log.debug("schedaDinamicaAttivitaEliminata# inizio del metodo WS schedaDinamicaAttivitaEliminata");
	AggiornaCampiSchedeResponse response = new AggiornaCampiSchedeResponse();
	EsitoOperazioneType esitoOperazioneType = new EsitoOperazioneType();
	try {
	    setORMHelper(AttivitaWS.SOFTWARE_TT, request.getToken());
	    this.generatoreEventiDatiDinamiciService.generaEventoSchedaDinamicaAttivitaEliminata(request.getCodiceAttivita(),
		    request.getCodiceScheda(), request.getIdCampiDinamiciDaEliminare());
	    esitoOperazioneType.setEsito(1);
	    response.setEsito(esitoOperazioneType);
	} catch (Exception e) {
	    String err = this.getRootCause(e);
	    log.error("schedaDinamicaAttivitaEliminata: token={}, codiceAttivita={}, codiceScheda={}, ERRORE={}",
		    new Object[] { request.getToken(), request.getCodiceAttivita(), request.getCodiceScheda(), err });
	    esitoOperazioneType.setEsito(0);
	    ErroreBackofficeType erroreBackofficeType = new ErroreBackofficeType();
	    erroreBackofficeType.setCodice("Errore");
	    erroreBackofficeType.setDescrizione(err);
	    esitoOperazioneType.getListaErrori().add(erroreBackofficeType);
	    response.setEsito(esitoOperazioneType);
	} finally {
	    resetThreadLocalVars();
	}
	log.debug("schedaDinamicaAttivitaEliminata# fine del metodo WS schedaDinamicaAttivitaEliminata");
	return response;
    }

    @Override
    public AggiornaCampiSchedeResponse schedaDinamicaIstanzaSalvata(SchedaDinamicaIstanzaSalvataRequest request) {

	log.debug("schedaDinamicaIstanzaSalvata# inizio del metodo WS schedaDinamicaIstanzaSalvata");
	AggiornaCampiSchedeResponse response = new AggiornaCampiSchedeResponse();
	EsitoOperazioneType esitoOperazioneType = new EsitoOperazioneType();
	try {
	    setORMHelper(AttivitaWS.SOFTWARE_TT, request.getToken());
	    this.generatoreEventiDatiDinamiciService.generaEventoSchedaDinamicaIstanzaSalvata(request.getCodiceIstanza(), request.getCodiceScheda());
	    esitoOperazioneType.setEsito(1);
	    response.setEsito(esitoOperazioneType);
	} catch (Exception e) {
	    String err = this.getRootCause(e);
	    log.error("schedaDinamicaIstanzaSalvata: token={}, codiceIstanza={}, codiceScheda={}, ERRORE={}",
		    new Object[] { request.getToken(), request.getCodiceIstanza(), request.getCodiceScheda(), err });
	    esitoOperazioneType.setEsito(0);
	    ErroreBackofficeType erroreBackofficeType = new ErroreBackofficeType();
	    erroreBackofficeType.setCodice("Errore");
	    erroreBackofficeType.setDescrizione(err);
	    esitoOperazioneType.getListaErrori().add(erroreBackofficeType);
	    response.setEsito(esitoOperazioneType);
	} finally {
	    resetThreadLocalVars();
	}
	log.debug("schedaDinamicaIstanzaSalvata# fine del metodo WS schedaDinamicaIstanzaSalvata");
	return response;
    }

    @Override
    public AggiornaCampiSchedeResponse schedaDinamicaIstanzaEliminata(SchedaDinamicaIstanzaEliminataRequest request) {

	log.debug("schedaDinamicaIstanzaEliminata# inizio del metodo WS schedaDinamicaIstanzaSalvata");
	AggiornaCampiSchedeResponse response = new AggiornaCampiSchedeResponse();
	EsitoOperazioneType esitoOperazioneType = new EsitoOperazioneType();
	try {
	    setORMHelper(AttivitaWS.SOFTWARE_TT, request.getToken());
	    this.generatoreEventiDatiDinamiciService.generaEventoSchedaDinamicaIstanzaEliminata(request.getCodiceIstanza(),
		    request.getCodiceScheda());
	    esitoOperazioneType.setEsito(1);
	    response.setEsito(esitoOperazioneType);
	} catch (Exception e) {
	    String err = this.getRootCause(e);
	    log.error("schedaDinamicaIstanzaEliminata: token={}, codiceIstanza={}, codiceScheda={}, ERRORE={}",
		    new Object[] { request.getToken(), request.getCodiceIstanza(), request.getCodiceScheda(), err });
	    esitoOperazioneType.setEsito(0);
	    ErroreBackofficeType erroreBackofficeType = new ErroreBackofficeType();
	    erroreBackofficeType.setCodice("Errore");
	    erroreBackofficeType.setDescrizione(err);
	    esitoOperazioneType.getListaErrori().add(erroreBackofficeType);
	    response.setEsito(esitoOperazioneType);
	} finally {
	    resetThreadLocalVars();
	}
	log.debug("schedaDinamicaIstanzaEliminata# fine del metodo WS schedaDinamicaIstanzaEliminata");
	return response;
    }

    @Override
    public AggiornaCampiSchedeResponse schedaDinamicaAggiuntaAdAttivita(SchedaDinamicaAggiuntaAdAttivitaRequest request) {

	log.debug("schedaDinamicaAggiuntaAdAttivita# inizio del metodo WS schedaDinamicaAggiuntaAdAttivita");
	AggiornaCampiSchedeResponse response = new AggiornaCampiSchedeResponse();
	EsitoOperazioneType esitoOperazioneType = new EsitoOperazioneType();
	try {
	    setORMHelper(AttivitaWS.SOFTWARE_TT, request.getToken());
	    this.generatoreEventiDatiDinamiciService.generaEventoSchedaAttivitaAggiunta(request.getCodiceAttivita(), request.getCodiceScheda());
	    esitoOperazioneType.setEsito(1);
	    response.setEsito(esitoOperazioneType);
	} catch (Exception e) {
	    String err = this.getRootCause(e);
	    log.error("schedaDinamicaAggiuntaAdAttivita: token={}, codiceAttivita={}, codiceScheda={}, ERRORE={}",
		    new Object[] { request.getToken(), request.getCodiceAttivita(), request.getCodiceScheda(), err });
	    esitoOperazioneType.setEsito(0);
	    ErroreBackofficeType erroreBackofficeType = new ErroreBackofficeType();
	    erroreBackofficeType.setCodice("Errore");
	    erroreBackofficeType.setDescrizione(err);
	    esitoOperazioneType.getListaErrori().add(erroreBackofficeType);
	    response.setEsito(esitoOperazioneType);
	} finally {
	    resetThreadLocalVars();
	}
	log.debug("schedaDinamicaAggiuntaAdAttivita# fine del metodo WS schedaDinamicaAggiuntaAdAttivita");
	return response;
    }
    /*
    //Il metodo viene richiamato dalla parte .NET quando:
    //	- Viene aggiunta una nuova scheda all'attività
    //	- Viene salvata una scheda dell'istanza collegata ad una attività
    //	- Viene eliminata una scheda dell'istanza collegata ad una attività
    @Override
    public AggiornaCampiSchedeResponse aggiornaCampiSchede(AggiornaCampiSchedeRequestType parameters) {
    
    if (log.isDebugEnabled()) {
        log.debug("aggiornaCampiSchede# entro nel metodo WS aggiornaCampiSchede");
    }
    //log.debug("aggiornaCampiSchede#Sotware ORMHELPER : {}", ORMHelper.getSoftware());
    AggiornaCampiSchedeResponse response = new AggiornaCampiSchedeResponse();
    //	setORMHelper(parameters.getSoftware(), parameters.getToken());
    // Dalla chiamata WS nella request viene sempre passato software TT ( scritto arcode sul codice .NET, controlalto con NICOLA GARGAGLI)
    // Per la gestione delle ATTIVITA TEMPORANEE (PROV RAVENNA) al salvataggio di un campo delle schede dimaniche di un istanza andremo a recuperare
    // sulle verticalizzazioni il parametro per calcolare la data fine atttivita, passando sempre TT avremo problemi se tali configurazioni 
    // sono specifiche per SOFTWARE. 
    // Recupero il software reale o dall'istanza se popolato il parametro in request o dall'iattivita dall'istanza rappresnetativa della stessa  
    String codSoftware = WebConstants.SOFTWARE_TT;
    if (parameters.getCodiceIstanza() != null) {
        log.debug("aggiornaCampiSchede# Recupero il software dall'istanza con codice {}", parameters.getCodiceIstanza());
        Istanze istanzaPerSoftware = istanzeService.findById(new PkId(parameters.getCodiceIstanza()));
        codSoftware = istanzaPerSoftware.getSoftware().getCodice();
        log.debug("aggiornaCampiSchede# Imposto il software recupertato dall'istanza: {}", codSoftware);
    } else {
        log.debug("aggiornaCampiSchede# Recupero il software dall'istanza rappresentativa dell'attivita {}", parameters.getCodiceAttivita());
        IAttivita ia = iAttivitaService.findById(new PkId(parameters.getCodiceAttivita()));
        Istanze istanzaPerSoftware = istanzeService.findById(new PkId(ia.getIstanza().getId().getCodice()));
        codSoftware = istanzaPerSoftware.getSoftware().getCodice();
        log.debug("aggiornaCampiSchede# Imposto il software recupertato dall'istanza: {}", codSoftware);
    }
    setORMHelper(codSoftware, parameters.getToken());
    EsitoOperazioneType esitoOperazioneType = new EsitoOperazioneType();
    try {
        if (log.isDebugEnabled()) {
    	log.debug("aggiornaCampiSchede# Inizio aggiornamento schede.......");
        }
        iAttivitaService.updateCampiSchedeDinamiche(parameters.getCodiceAttivita(), parameters.getCodiceScheda());
        if (log.isDebugEnabled()) {
    	log.debug("aggiornaCampiSchede# Fine aggiornamento schede.......");
        }
        if (log.isDebugEnabled()) {
    	log.debug("snapShot# Popolo l'esito......");
        }
        esitoOperazioneType.setEsito(1);
        response.setEsito(esitoOperazioneType);
        if (log.isDebugEnabled()) {
    	log.debug("snapShot# Popolato l'esito....");
        }
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////// OPERAZIONI DI SNAPSHOT ////////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        if (log.isDebugEnabled()) {
    	log.debug("snapShot# Eseguo in un thread separato le operazioni di snap shot");
        }
        if (parameters.getCodiceIstanza() != null) {
    	if (log.isDebugEnabled()) {
    	    log.debug("snapShot# Eseguo operazione iAttivitaService.updateSnapShot({})", parameters.getCodiceIstanza());
    	}
    	iAttivitaSnapshotService.updateRutineSnapShot(false, parameters.getCodiceIstanza());
    	//		taskExecutor.execute(new IattivitaShapshotTask(iAttivitaService, istanzeService, iAttivitaSnapshotService, false, parameters
    	//			.getCodiceIstanza(), ORMHelper.getIdcomuneAlias(), ORMHelper.getIdcomune(), ORMHelper.getSoftware(), ORMHelper.getToken()));
        } else {
    	IAttivita iAttivita = iAttivitaService.findById(new PkId(parameters.getCodiceAttivita()));
    	if (log.isDebugEnabled()) {
    	    log.debug("snapShot# Eseguo operazione iAttivitaService.updateSnapShotCopia({}, {})",
    		    new Object[] { iAttivita.getIstanza().getId().getCodice(), parameters.getCodiceAttivita() });
    	}
    	Istanze istanza = istanzeService.findIstanzaOrigineAttivita(parameters.getCodiceAttivita());
    	iAttivitaSnapshotService.updateRutineSnapShot(false, istanza.getId().getCodice());
    	//		taskExecutor.execute(new IattivitaShapshotTask(iAttivitaService, istanzeService, iAttivitaSnapshotService, false, iAttivita
    	//			.getIstanza().getId().getCodice(), ORMHelper.getIdcomuneAlias(), ORMHelper.getIdcomune(), ORMHelper.getSoftware(), ORMHelper
    	//			.getToken()));
        }
        log.debug(
    	    "aggiornaCampiSchede# E' stato fatto un aggiornamento sulle schede dinamiche, devo verificare se cambia la data fine dell'attività a cui è collegata");
        IAttivita iAttivita = iAttivitaService.findById(new PkId(parameters.getCodiceAttivita()));
        if (EntityUtils.getNestedProperty(iAttivita, "id.codice") != null) {
    	Date dataFine = iAttivitaService.calcoloDataFineAttivita(iAttivita);
    	iAttivita.setDataFine(dataFine);
    	iAttivitaService.update(iAttivita);
    	// caso particolare che la data di fine validità di un attività viene immessa nelle schede dinamiche dell'istanza
    	// dopo aver creato l'attività
    	iAttivitaSnapshotService.updateElaboraSnapshotAttivita(parameters.getCodiceAttivita());
        }
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////// END ////////////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    } catch (Exception e) {
        String err = this.getRootCause(e);
        log.error("snapShot: software={}, token={}, codiceIstanza={}, ERRORE={}",
    	    new Object[] { parameters.getSoftware(), parameters.getToken(), parameters.getCodiceIstanza(), err });
        esitoOperazioneType.setEsito(0);
        ErroreBackofficeType erroreBackofficeType = new ErroreBackofficeType();
        erroreBackofficeType.setCodice("Errore");
        erroreBackofficeType.setDescrizione(err);
        esitoOperazioneType.getListaErrori().add(erroreBackofficeType);
        response.setEsito(esitoOperazioneType);
    } finally {
        resetThreadLocalVars();
    }
    return response;
    }
    
    //Il metodo viene chiamato dalla parte .NET al momento in cui viene salvata una scheda dinamica dell'attività; non viene richiamato nessun metodo
    //al momento della cancellazione della scheda dinamica dall'attività in quanto viene fatto tutto sotto transazione lato .NET
    @Override
    public SnapShotResponse snapShot(SnapShotRequestType parameters) {
    
    if (log.isDebugEnabled()) {
        log.debug("snapShot# entro nel metodo WS snapShot");
    }
    SnapShotResponse response = new SnapShotResponse();
    setORMHelper(parameters.getSoftware(), parameters.getToken());
    if (log.isDebugEnabled()) {
        log.debug("snapShot# ricerco l'attivita con codice {} ", parameters.getCodiceAttivita());
    }
    //Integer codiceIstanza = null;
    EsitoOperazioneType esitoOperazioneType = new EsitoOperazioneType();
    IAttivita iAttivita = new IAttivita();
    try {
        iAttivita = iAttivitaService.findById(new PkId(parameters.getCodiceAttivita()));
        // codiceIstanza = iAttivita.getIstanza().getId().getCodice();
        //iAttivitaService.updateSnapShot(codiceIstanza);
        Istanze istanza = istanzeService.findById(new PkId(iAttivita.getIstanza().getId().getCodice()));
        iAttivita.setIstanza(istanza);
        iAttivitaService.updateSnapShotCopia(iAttivita);
        iAttivitaService.updateSnapShotCopiaDatiDinamici(iAttivita);
        if (log.isDebugEnabled()) {
    	log.debug("snapShot# Popolo l'esito");
        }
        esitoOperazioneType.setEsito(0);
        response.setEsito(esitoOperazioneType);
    } catch (Exception e) {
        String err = this.getRootCause(e);
        log.error("snapShot: software={}, token={}, codiceattivita={}, ERRORE={}",
    	    new Object[] { parameters.getSoftware(), parameters.getToken(), iAttivita.getId().getCodice(), err });
        esitoOperazioneType.setEsito(500);
        ErroreBackofficeType erroreBackofficeType = new ErroreBackofficeType();
        erroreBackofficeType.setCodice("Errore");
        erroreBackofficeType.setDescrizione(err);
        esitoOperazioneType.getListaErrori().add(erroreBackofficeType);
        response.setEsito(esitoOperazioneType);
    } finally {
        resetThreadLocalVars();
    }
    return response;
    }
    */
}
