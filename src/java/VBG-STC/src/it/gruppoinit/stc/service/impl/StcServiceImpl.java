package it.gruppoinit.stc.service.impl;

import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.stc.domain.Attivita;
import it.gruppoinit.stc.domain.Configurazione;
import it.gruppoinit.stc.domain.Messaggiattivita;
import it.gruppoinit.stc.domain.Messaggipratiche;
import it.gruppoinit.stc.domain.Pratiche;
import it.gruppoinit.stc.schema.helper.SchemaConversionUtils;
import it.gruppoinit.stc.service.AttivitaService;
import it.gruppoinit.stc.service.ConfigurazioneService;
import it.gruppoinit.stc.service.Constants;
import it.gruppoinit.stc.service.MessaggiattivitaService;
import it.gruppoinit.stc.service.MessaggiattivitaService.TIPO_COLLEGAMENTO;
import it.gruppoinit.stc.service.MessaggipraticheService;
import it.gruppoinit.stc.service.PraticheService;
import it.gruppoinit.stc.service.SicurezzaService;
import it.gruppoinit.stc.service.StcService;
import it.gruppoinit.stc.service.helper.ErroriSTCHelper;
import it.gruppoinit.stc.utils.Utilities;
import it.gruppoinit.stc.ws.client.NlaWebServiceClient;
import it.init.sigepro.rte.AggiungiDocumentiNLARequest;
import it.init.sigepro.rte.AggiungiDocumentiNLAResponse;
import it.init.sigepro.rte.AggiungiDocumentiRequest;
import it.init.sigepro.rte.AggiungiDocumentiResponse;
import it.init.sigepro.rte.AllegatoBinarioNLARequest;
import it.init.sigepro.rte.AllegatoBinarioNLAResponse;
import it.init.sigepro.rte.AllegatoBinarioRequest;
import it.init.sigepro.rte.AllegatoBinarioResponse;
import it.init.sigepro.rte.CancellaAttivitaRequest;
import it.init.sigepro.rte.CheckTokenRequest;
import it.init.sigepro.rte.CheckTokenResponse;
import it.init.sigepro.rte.DirezioneSportelloRequest;
import it.init.sigepro.rte.DirezioneSportelloResponse;
import it.init.sigepro.rte.InserimentoAttivitaNLARequest;
import it.init.sigepro.rte.InserimentoAttivitaNLAResponse;
import it.init.sigepro.rte.InserimentoPraticaNLARequest;
import it.init.sigepro.rte.InserimentoPraticaNLAResponse;
import it.init.sigepro.rte.InserimentoPraticaRequest;
import it.init.sigepro.rte.InserimentoPraticaResponse;
import it.init.sigepro.rte.LoginRequest;
import it.init.sigepro.rte.LoginResponse;
import it.init.sigepro.rte.NotificaAttivitaRequest;
import it.init.sigepro.rte.NotificaAttivitaResponse;
import it.init.sigepro.rte.RichiestaPraticaCollegataDaAttivitaDestinatariaRequest;
import it.init.sigepro.rte.RichiestaPraticaCollegataDaAttivitaMittenteRequest;
import it.init.sigepro.rte.RichiestaPraticaCollegataRequest;
import it.init.sigepro.rte.RichiestaPraticaCollegataResponse;
import it.init.sigepro.rte.RichiestaPraticaNLARequest;
import it.init.sigepro.rte.RichiestaPraticaNLAResponse;
import it.init.sigepro.rte.RichiestaPraticaRequest;
import it.init.sigepro.rte.RichiestaPraticaResponse;
import it.init.sigepro.rte.RichiestaPraticheListaNLARequest;
import it.init.sigepro.rte.RichiestaPraticheListaNLAResponse;
import it.init.sigepro.rte.RichiestaPraticheListaRequest;
import it.init.sigepro.rte.RichiestaPraticheListaResponse;
import it.init.sigepro.rte.types.DettaglioPraticaType;
import it.init.sigepro.rte.types.DirezioneType;
import it.init.sigepro.rte.types.DocumentiType;
import it.init.sigepro.rte.types.ErroreType;
import it.init.sigepro.rte.types.InterventoType;
import it.init.sigepro.rte.types.ParametroType;
import it.init.sigepro.rte.types.ProcedimentoType;
import it.init.sigepro.rte.types.RiferimentiPraticaType;
import it.init.sigepro.rte.types.SportelloType;
import it.init.sigepro.rte.types.ValoreParametroType;

@Service
public class StcServiceImpl implements StcService {

    private static final String ALTRO_DATO_NOTIFICA_ATTIVITA_PRATICA_CREATA_DA_ATTIVITA = "$PRATICA_CREATA_DA_ATTIVITA$";
    private static final String ALTRODATO_NOTIFICA_ATTIVITA_REQUEST_DATI_ATTIVITA_TIPO_ATTIVITA_CODICE = "NotificaAttivitaRequest.DatiAttivita.TipoAttivita.codice";
    private static final String PREFISSO_STC_TRASFERISCE_DATO_SU_ISTANZA = "STC-TRASF-DATO-SU-IST";
    private static final Logger log = LoggerFactory.getLogger(StcServiceImpl.class);
    public static final String NOTIFICA_ATTIVITA_ALTRO_DATO_ALBEROPROC = "ALBEROPROC.SC_ID";
    /**
     * <b>$INSERIMENTO_DIRETTO$</b><br />
     * Specifica che è stato chiamato inserimentoPratica direttamente dal nodo nla mittente.
     */
    public static final String ALTRO_DATO_INSERIMENTO_DIRETTO = "$INSERIMENTO_DIRETTO$";
    /**
     * <b>$NON_INVIARE_PROCEDIMENTI$</b><br />
     * Specifica se gli endo della pratica mittente vanno sostituiti con quelli dell'attività prima che venga invocato
     * il metodo Inseriscipratica nel destinatario.<br />
     * Specifica se prima dell'inserimento attività verso il destinatario va messo solo l'endo principale della sezione
     * procedimenti della pratica oppure nulla.
     */
    public static final String ALTRO_DATO_NON_INVIARE_PROCEDIMENTI = "$NON_INVIARE_PROCEDIMENTI$";
    /**
     * <b>$notificaInteraPratica$</b><br />
     * Specifica se notificare tutti gli endoprocedimenti della pratica.<br />
     * Sovrascrive il comportamento di ALTRO_DATO_NON_INVIARE_PROCEDIMENTI
     */
    public static final String ALTRO_DATI_NOTIFICA_INTERA_PRATICA = "$notificaInteraPratica$";
    /**
     * SE STC TROVA QUESTO DATO NELL'INSERIMENTO PRATICA COPIA LA LISTA DEGLI ENDO (OLTRE A QUELLO PRINCIPALE DELLA
     * NOTIFICA ATTIVITA') VERIFICANDO IL CODICE PRESENTE NEGLI ALTRI DATI CON QUELLI PRESENTI NELLA ISTANZA. SE
     * PRESENTE NOTIFICA INTERA PRATICA ALLORA VINCE QUESTA
     */
    public static final String NOTIFICA_ATTIVITA_LISTA_ENDO_PROCEDIMENTI_DA_COPIARE = PREFISSO_STC_TRASFERISCE_DATO_SU_ISTANZA +
										      "$INSERIMENTO_PRATICA_COPIA_GLI_ENDO_IN_LISTA$";
    public static final String ALTRO_DATI_SPOSTA_ALLEGATI_IN_PRATICA = "$SPOSTA_ALLEGATI_IN_PRATICA$";
    @Autowired
    private PraticheService praticheService;
    @Autowired
    private AttivitaService attivitaService;
    @Autowired
    private ConfigurazioneService configurazioneService;
    @Autowired
    private MessaggiattivitaService messaggiattivitaService;
    @Autowired
    private MessaggipraticheService messaggipraticheService;
    @Autowired
    private NlaWebServiceClient nlaWebServiceClient;
    @Autowired
    private SicurezzaService sicurezzaService;

    @Override
    public LoginResponse login(LoginRequest request) {

	LoginResponse response = new LoginResponse();
	boolean loginSuccess = false;
	Configurazione confForSuccess = null;
	List<Configurazione> configurazioni = configurazioneService.findAll(null, null);
	for (Configurazione configurazione : configurazioni) {
	    if (configurazione.getUserid().equals(request.getUsername()) && configurazione.getPassword().equals(request.getPassword())) {
		loginSuccess = true;
		confForSuccess = configurazione;
		break;
	    }
	}
	response.setResult(loginSuccess);
	response.setToken("");
	if (loginSuccess) {
	    String token = sicurezzaService.getToken(confForSuccess);
	    response.setToken(token);
	}
	log.debug("STC - Login: user:{} token:{}", request.getUsername(), response.getToken());
	return response;
    }

    @Override
    public CheckTokenResponse checkToken(CheckTokenRequest request) {

	CheckTokenResponse response = new CheckTokenResponse();
	response.setResult(sicurezzaService.checkToken(request.getToken()));
	log.debug("STC - CheckToken:{} result:{}", request.getToken(), response.isResult());
	return response;
    }

    @Override
    public Pratiche[] notificaAttivitaGestionePratiche(NotificaAttivitaRequest request) {

	log.debug("STC - notificaAttivitaGestionePratiche");
	// DB: verifica token //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	sicurezzaService.checkToken(request.getToken());
	Pratiche[] pratiche = new Pratiche[2];
	// DB: verifica nodi ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	Configurazione nodoMitt = this.verificaSportelloMitt(request.getSportelloMittente());
	Configurazione nodoDest = this.verificaSportelloDest(request.getSportelloDestinatario());
	// XML: estrazione procedimento principale da NotificaAttivitaRequest
	SchemaConversionUtils schemaConversionUtils = new SchemaConversionUtils();
	ProcedimentoType procedimentoPrincipale = schemaConversionUtils.getProcedimentoPrincipale(request.getDatiAttivita().getProcedimenti());
	// DB: verifica esistenza pratica NLA-MIT //////////////////////////////////////////////////////////////////////////////////////////////
	Pratiche praticaMitTemp = new Pratiche();
	praticaMitTemp.setConfigurazioneByFkidnodo(nodoMitt);
	praticaMitTemp.setIdente(request.getSportelloMittente().getIdEnte());
	praticaMitTemp.setIdsportello(request.getSportelloMittente().getIdSportello());
	praticaMitTemp.setIdpratica(request.getDatiAttivita().getIdPratica());
	log.debug("STC - Verifica esistenza pratica NLA-MIT su DB STC(idnodo={},idente={},idsportello={},idpratica={})",
		new Object[] { praticaMitTemp.getConfigurazioneByFkidnodo().getIdnodo(), praticaMitTemp.getIdente(), praticaMitTemp.getIdsportello(),
			praticaMitTemp.getIdpratica() });
	Pratiche praticaMit = praticheService.findByUniqueKey(praticaMitTemp);
	Pratiche praticaDest = null;
	if (praticaMit == null) {
	    log.debug("STC - Pratica NLA-MIT non presente su DB STC");
	    // WS: richiestaPratica NLA-MIT ////////////////////////////////////////////////////////////////////////////////
	    RichiestaPraticaNLARequest nlaRequest = new RichiestaPraticaNLARequest();
	    RiferimentiPraticaType rifPraticaType = new RiferimentiPraticaType();
	    rifPraticaType.setIdPratica(request.getDatiAttivita().getIdPratica());
	    nlaRequest.setSportelloDestinatario(request.getSportelloMittente());
	    nlaRequest.setSportelloMittente(request.getSportelloDestinatario());
	    nlaRequest.setToken(request.getToken());
	    boolean isCopiaAltriEndo = false;
	    for (ParametroType parametroType : request.getDatiAttivita().getAltriDati()) {
		if (parametroType != null && NOTIFICA_ATTIVITA_LISTA_ENDO_PROCEDIMENTI_DA_COPIARE.equals(parametroType.getNome())) {
		    rifPraticaType.getAltriDati().add(parametroType);
		    List<ValoreParametroType> listVP = parametroType.getValore();
		    for (ValoreParametroType vpt : listVP) {
			if (vpt != null && StringUtils.isNotBlank(vpt.getCodice())) {
			    isCopiaAltriEndo = true;
			}
		    }
		}
	    }
	    nlaRequest.setRifPratica(rifPraticaType);
	    log.debug("STC - Chiamata a WS richiestaPratica NLA-MIT(idnodo={},idente={},idsportello={},idpratica={}, isCopiaAltriEndo={})",
		    new Object[] { nlaRequest.getSportelloDestinatario().getIdNodo(), nlaRequest.getSportelloDestinatario().getIdEnte(),
			    nlaRequest.getSportelloDestinatario().getIdSportello(), nlaRequest.getRifPratica().getIdPratica(), isCopiaAltriEndo });
	    RichiestaPraticaNLAResponse nlaRichPraticaResponse = nlaWebServiceClient.richiestaPratica(nlaRequest, nodoMitt);
	    if (!nlaRichPraticaResponse.getDettaglioErrore().isEmpty() || nlaRichPraticaResponse.getDettaglioPratica() == null
		    || nlaRichPraticaResponse.getDettaglioPratica().getDettaglioPratica() == null) {
		// pratica mittente non trovata(assurdo! forse il nodo mittente ha passato un idnodo sbagliato)
		String descErr = "";
		if (!nlaRichPraticaResponse.getDettaglioErrore().isEmpty()) {
		    descErr = nlaRichPraticaResponse.getDettaglioErrore().get(0).getDescrizione();
		}
		log.error("STC - notificaAttivita(): pratica mittente non trovata [possibile errore di configurazione] {}", descErr);
		throw new RuntimeException("STC - Errore durante chiamata a metodo richiestaPratica NLA-MIT(idnodo=" +
					   nlaRequest.getSportelloDestinatario().getIdNodo() + ",idente=" +
					   nlaRequest.getSportelloDestinatario().getIdEnte() + ",idsportello=" +
					   nlaRequest.getSportelloDestinatario().getIdSportello() + ",idpratica=" +
					   nlaRequest.getRifPratica().getIdPratica() + ")");
	    }
	    // DB: inserimento pratica NLA-MIT //////////////////////////////////////////////////////////////////////////////////
	    Pratiche privatePraticaMit = schemaConversionUtils.getPratica(nlaRichPraticaResponse);
	    log.debug(
		    "STC - Recuperata pratica da NLA-MIT tramite WS richiestaPratica(idnodo={},idente={},idsportello={},idpratica={},numpratica={})",
		    new String[] { nlaRequest.getSportelloDestinatario().getIdNodo(), nlaRequest.getSportelloDestinatario().getIdEnte(),
			    nlaRequest.getSportelloDestinatario().getIdSportello(), privatePraticaMit.getIdpratica(),
			    privatePraticaMit.getNumpratica() });
	    privatePraticaMit.setIdente(request.getSportelloMittente().getIdEnte());
	    privatePraticaMit.setIdsportello(request.getSportelloMittente().getIdSportello());
	    privatePraticaMit.setConfigurazioneByFkidnodo(nodoMitt);
	    log.debug("STC - Inserimento pratica NLA-MIT su DB STC");
	    praticheService.insert(privatePraticaMit);
	    praticaMit = privatePraticaMit;
	    praticaDest = this.gestionePraticaDestinatario(request, nodoDest, praticaMit,
		    nlaRichPraticaResponse.getDettaglioPratica().getDettaglioPratica());
	} else {
	    log.debug("STC - Pratica NLA-MIT trovata su DB STC");
	    // DB: verifica esistenza pratica NLA-DEST /////////////////////////////////////////////////////////////////////////////
	    log.debug("STC - Verifica esistenza pratica NLA-DEST su DB STC");
	    Set<Messaggipratiche> messaggiPratiche = praticaMit.getMessaggipratichesForFkidrichiesta();
	    boolean praticaDestPresente = false;
	    if (!messaggiPratiche.isEmpty()) {
		Pratiche privatePraticaDest = null;
		for (Messaggipratiche messaggipratica : messaggiPratiche) {
		    privatePraticaDest = messaggipratica.getPraticheByFkidrisposta();
		    if (privatePraticaDest.getConfigurazioneByFkidnodo().getIdnodo().toString().equals(request.getSportelloDestinatario().getIdNodo())
			    && privatePraticaDest.getIdente().equals(request.getSportelloDestinatario().getIdEnte())
			    && privatePraticaDest.getIdsportello().equals(request.getSportelloDestinatario().getIdSportello())
			    && this.verificaPraticaDestinatario(privatePraticaDest, praticaMit, procedimentoPrincipale)) {
			praticaDestPresente = true;
			praticaDest = privatePraticaDest;
			break;
		    }
		}
	    }
	    if (!praticaDestPresente) {
		messaggiPratiche = praticaMit.getMessaggipratichesForFkidrisposta();
		if (!messaggiPratiche.isEmpty()) {
		    Pratiche privatePraticaDest = null;
		    for (Messaggipratiche messaggipratica : messaggiPratiche) {
			privatePraticaDest = messaggipratica.getPraticheByFkidrichiesta();
			if (privatePraticaDest.getConfigurazioneByFkidnodo().getIdnodo().toString()
				.equals(request.getSportelloDestinatario().getIdNodo())
				&& privatePraticaDest.getIdente().equals(request.getSportelloDestinatario().getIdEnte())
				&& privatePraticaDest.getIdsportello().equals(request.getSportelloDestinatario().getIdSportello())
				&& this.verificaPraticaDestinatario(privatePraticaDest, praticaMit, procedimentoPrincipale)) {
			    praticaDestPresente = true;
			    praticaDest = privatePraticaDest;
			    break;
			}
		    }
		}
	    }
	    if (!praticaDestPresente) {
		log.debug("STC - Pratica NLA-DEST non trovata su DB STC");
		RichiestaPraticaNLARequest nlaRequest = new RichiestaPraticaNLARequest();
		RiferimentiPraticaType rifPraticaType = new RiferimentiPraticaType();
		rifPraticaType.setIdPratica(request.getDatiAttivita().getIdPratica());
		nlaRequest.setSportelloDestinatario(request.getSportelloMittente());
		nlaRequest.setSportelloMittente(request.getSportelloDestinatario());
		nlaRequest.setToken(request.getToken());
		boolean isCopiaAltriEndo = false;
		for (ParametroType parametroType : request.getDatiAttivita().getAltriDati()) {
		    if (parametroType != null && NOTIFICA_ATTIVITA_LISTA_ENDO_PROCEDIMENTI_DA_COPIARE.equals(parametroType.getNome())) {
			rifPraticaType.getAltriDati().add(parametroType);
			List<ValoreParametroType> listVP = parametroType.getValore();
			for (ValoreParametroType vpt : listVP) {
			    if (vpt != null && StringUtils.isNotBlank(vpt.getCodice())) {
				isCopiaAltriEndo = true;
			    }
			}
		    }
		}
		nlaRequest.setRifPratica(rifPraticaType);
		log.debug("STC - Chiamata a WS richiestaPratica NLA-MIT(idnodo={},idente={},idsportello={},idpratica={}, isCopiaAltriEndo={})",
			new Object[] { nlaRequest.getSportelloDestinatario().getIdNodo(), nlaRequest.getSportelloDestinatario().getIdEnte(),
				nlaRequest.getSportelloDestinatario().getIdSportello(), nlaRequest.getRifPratica().getIdPratica(),
				isCopiaAltriEndo });
		RichiestaPraticaNLAResponse nlaRichPraticaResponse = nlaWebServiceClient.richiestaPratica(nlaRequest, nodoMitt);
		if (!nlaRichPraticaResponse.getDettaglioErrore().isEmpty()) {
		    // pratica mittente non trovata(assurdo!)
		    log.error(nlaRichPraticaResponse.getDettaglioErrore().get(0).getDescrizione());
		    throw new RuntimeException("STC - Errore durante chiamata a metodo richiestaPratica NLA-MIT(idnodo=" +
					       nlaRequest.getSportelloDestinatario().getIdNodo() + ",idente=" +
					       nlaRequest.getSportelloDestinatario().getIdEnte() + ",idsportello=" +
					       nlaRequest.getSportelloDestinatario().getIdSportello() + ",idpratica=" +
					       nlaRequest.getRifPratica().getIdPratica() + ")");
		}
		praticaDest = this.gestionePraticaDestinatario(request, nodoDest, praticaMit,
			nlaRichPraticaResponse.getDettaglioPratica().getDettaglioPratica());
	    }
	}
	pratiche[0] = praticaMit;
	pratiche[1] = praticaDest;
	return pratiche;
    }

    /**
     * a partire dalla pratica del destinatario cicla le attività e per ognuna recupera l'attività collegata(del
     * mittente). se questa attività collegata appartiene alla pratica del mittente allora confronta l'idprocedimento
     * con il procedimentoPrincipale passato al metodo (caso del suap che attiva due procedimenti che creano
     * nell'edilizia due pratiche)
     * 
     * @param pra
     *            pratica del destinatario
     * 
     * @param procedimentoPrincipale
     *            procedimento principale del mittente
     * @return
     */
    private boolean verificaPraticaDestinatario(Pratiche praDest, Pratiche praMitt, ProcedimentoType procedimentoPrincipale) {

	boolean praticaDestPresente = false;
	if (procedimentoPrincipale != null && StringUtils.isNotBlank(procedimentoPrincipale.getCodice())) {
	    Set<Attivita> listaAttivita = praDest.getAttivitas();
	    for (Attivita attivitaDest : listaAttivita) {
		//per ogni attività della pratDest recupero l'att collegata tramite msgatt(per fk_rich e fk_dest), verifico che appartenga alla prat mitt, 
		//se è vero confronto i procedimenti
		//prima faccio il confronto tra tutti con il procPrincipale se non lo trovo rieseguo il controllo per verificare se c'è un null
		for (Messaggiattivita msgRich : attivitaDest.getMessaggiattivitasForFkidrichiesta()) {
		    Attivita attColl = msgRich.getAttivitaByFkidrisposta();
		    if (attColl.getPratiche().getId() == praMitt.getId() && procedimentoPrincipale.getCodice().equals(attColl.getIdprocedimento())) {
			praticaDestPresente = true;
			break;
		    }
		}
		if (!praticaDestPresente) {
		    for (Messaggiattivita msgRisp : attivitaDest.getMessaggiattivitasForFkidrisposta()) {
			// Qua arriva null
			if (msgRisp.getAttivitaByFkidrichiesta() != null) {
			    Attivita attColl = msgRisp.getAttivitaByFkidrichiesta();
			    if (attColl.getPratiche().getId() == praMitt.getId()
				    && procedimentoPrincipale.getCodice().equals(attColl.getIdprocedimento())) {
				praticaDestPresente = true;
				break;
			    }
			}
		    }
		}
		// controllo se c'è un idprocedimento null
		if (!praticaDestPresente) {
		    for (Messaggiattivita msgRich : attivitaDest.getMessaggiattivitasForFkidrichiesta()) {
			Attivita attColl = msgRich.getAttivitaByFkidrisposta();
			if (attColl.getPratiche().getId() == praMitt.getId() && StringUtils.isBlank(attColl.getIdprocedimento())) {
			    praticaDestPresente = true;
			    break;
			}
		    }
		    if (!praticaDestPresente) {
			for (Messaggiattivita msgRisp : attivitaDest.getMessaggiattivitasForFkidrisposta()) {
			    // Qua arriva null
			    if (msgRisp.getAttivitaByFkidrichiesta() != null) {
				Attivita attColl = msgRisp.getAttivitaByFkidrichiesta();
				if (attColl.getPratiche().getId() == praMitt.getId() && StringUtils.isBlank(attColl.getIdprocedimento())) {
				    praticaDestPresente = true;
				    break;
				}
			    }
			}
		    }
		}
	    }
	} else {
	    praticaDestPresente = true;
	}
	return praticaDestPresente;
    }

    private Pratiche gestionePraticaDestinatario(NotificaAttivitaRequest request, Configurazione nodoDest, Pratiche praticaMit,
	    DettaglioPraticaType dettaglioPraticaMittente) {

	Pratiche praticaDest = null;
	SchemaConversionUtils schemaConversionUtils = new SchemaConversionUtils();
	// DB: Inserimento messaggipratica NLA-MIT
	if (log.isDebugEnabled()) {
	    log.debug("STC - Inserimento messaggipratica NLA-MIT su DB STC");
	}
	Messaggipratiche messaggipratiche = new Messaggipratiche();
	messaggipratiche.setPraticheByFkidrichiesta(praticaMit);
	messaggipraticheService.insert(messaggipratiche);
	// WS: richiestaPratica NLA-DEST
	RichiestaPraticaNLAResponse nlaDestRichPraticaResponse = null;
	if (request.getRifPraticaDestinatario() != null) {
	    RichiestaPraticaNLARequest nlaDestRequest = new RichiestaPraticaNLARequest();
	    nlaDestRequest.setRifPratica(request.getRifPraticaDestinatario());
	    nlaDestRequest.setSportelloDestinatario(request.getSportelloDestinatario());
	    nlaDestRequest.setSportelloMittente(request.getSportelloMittente());
	    nlaDestRequest.setToken(request.getToken());
	    if (log.isDebugEnabled()) {
		log.debug("STC - Chiamata a WS richiestaPratica NLA-DEST(idnodo={},idente={},idsportello={},idpratica={})",
			new String[] { nlaDestRequest.getSportelloDestinatario().getIdNodo(), nlaDestRequest.getSportelloDestinatario().getIdEnte(),
				nlaDestRequest.getSportelloDestinatario().getIdSportello(), nlaDestRequest.getRifPratica().getIdPratica() });
	    }
	    nlaDestRichPraticaResponse = nlaWebServiceClient.richiestaPratica(nlaDestRequest, nodoDest);
	}
	if (nlaDestRichPraticaResponse != null && nlaDestRichPraticaResponse.getDettaglioErrore().isEmpty()) {
	    // pratica trovata su NLA-DEST 
	    praticaDest = schemaConversionUtils.getPratica(nlaDestRichPraticaResponse);
	} else {
	    // codificare errore pratica non trovata. se altro errore lanciare eccezione.
	    if (nlaDestRichPraticaResponse != null && !nlaDestRichPraticaResponse.getDettaglioErrore().isEmpty() && log.isDebugEnabled()) {
		log.debug("STC - Errore ritornato da chiamata a WS richiestaPratica NLA-DEST(idnodo={},Errore={})",
			new Object[] { nodoDest.getIdnodo(), nlaDestRichPraticaResponse.getDettaglioErrore().get(0).getDescrizione() });
	    }
	    // WS: inserisciPratica NLA-DEST
	    // ricerco se nella lista dei parametri dell'attività è stato configurato una voce dell'albero dell'NLA
	    // destinatario. Se è presente allora l'associo alla busta per l'inserimento pratica
	    List<ParametroType> altriDatiattivita = request.getDatiAttivita().getAltriDati();
	    for (ParametroType parametroType : altriDatiattivita) {
		if (NOTIFICA_ATTIVITA_ALTRO_DATO_ALBEROPROC.equals(parametroType.getNome())) {
		    InterventoType interventoOld = dettaglioPraticaMittente.getIntervento();
		    dettaglioPraticaMittente.getAltriDati().add(parametroType);
		    InterventoType intervento = new InterventoType();
		    List<ValoreParametroType> listVP = parametroType.getValore();
		    if (listVP != null && !listVP.isEmpty()) {
			String interventoParam = StringUtils.defaultString(listVP.get(0).getCodice()).trim();
			log.error("STC {} ", interventoParam);
			if (!"-1".equals(interventoParam)) {
			    // se ho passato -1 
			    intervento.setCodice(interventoParam);
			} else {
			    intervento.setCodice(interventoOld.getCodice());
			}
		    }
		    intervento.setDescrizione(interventoOld.getDescrizione());
		    dettaglioPraticaMittente.setIntervento(intervento);
		    break;
		}
	    }
	    boolean nonInviareEndo = false;
	    for (ParametroType parametroType : altriDatiattivita) {
		if (ALTRO_DATO_NON_INVIARE_PROCEDIMENTI.equals(parametroType.getNome())) {
		    nonInviareEndo = true;
		    break;
		}
	    }
	    boolean notificaInteraPratica = false;
	    for (ParametroType parametroType : request.getDatiAttivita().getAltriDati()) {
		if (ALTRO_DATI_NOTIFICA_INTERA_PRATICA.equals(parametroType.getNome())) {
		    notificaInteraPratica = true;
		    break;
		}
	    }
	    // Sezione riferita la  TIPIMOV_STC_MAPPING.flagAllegaDocPratica
	    boolean allegaDocPratica = false;
	    for (ParametroType parametroType : request.getDatiAttivita().getAltriDati()) {
		if (ALTRO_DATI_SPOSTA_ALLEGATI_IN_PRATICA.equals(parametroType.getNome())) {
		    allegaDocPratica = true;
		    break;
		}
	    }
	    boolean isCopiaAltriEndo = false;
	    for (ParametroType parametroType : request.getDatiAttivita().getAltriDati()) {
		if (NOTIFICA_ATTIVITA_LISTA_ENDO_PROCEDIMENTI_DA_COPIARE.equals(parametroType.getNome())) {
		    List<ValoreParametroType> listVP = parametroType.getValore();
		    for (ValoreParametroType vpt : listVP) {
			if (vpt != null && StringUtils.isNotBlank(vpt.getCodice())) {
			    isCopiaAltriEndo = true;
			}
		    }
		}
	    }
	    InserimentoPraticaNLARequest praticaNLARequest = new InserimentoPraticaNLARequest();
	    praticaNLARequest.setSportelloDestinatario(request.getSportelloDestinatario());
	    praticaNLARequest.setSportelloMittente(request.getSportelloMittente());
	    praticaNLARequest.setToken(request.getToken());
	    if (log.isDebugEnabled()) {
		log.debug("STC - isNotificaInterapratica {}, isNonInviareEndo {}, isCopiaAltriEndo {}",
			new Object[] { notificaInteraPratica, nonInviareEndo, isCopiaAltriEndo });
	    }
	    if (notificaInteraPratica || isCopiaAltriEndo) {
		if (nonInviareEndo) {
		    // XML: rimuovo tutti i procedimenti della pratica mittente (COMPRESI GLI ALLEGATI)
		    dettaglioPraticaMittente.getProcedimenti().removeAll(dettaglioPraticaMittente.getProcedimenti());
		    //XML: sostituisco nella pratica i procedimenti con quelli dell'attività (NON HANNO GLI ALLEGATI)
		}
	    } else {
		//XML: rimuovo tutti gli allegati della pratica perchè quelli da passare sono nell'attività
		dettaglioPraticaMittente.getDocumenti().removeAll(dettaglioPraticaMittente.getDocumenti());
		// XML: rimuovo tutti i procedimenti della pratica mittente (COMPRESI GLI ALLEGATI)
		dettaglioPraticaMittente.getProcedimenti().removeAll(dettaglioPraticaMittente.getProcedimenti());
		if (!nonInviareEndo) {
		    //XML: sostituisco nella pratica i procedimenti con quelli dell'attività (NON HANNO GLI ALLEGATI)
		    dettaglioPraticaMittente.getProcedimenti().addAll(request.getDatiAttivita().getProcedimenti());
		}
	    }
	    if (allegaDocPratica) {
		log.debug("gestionePraticaDestinatario# Allegata documenti alla pratica : {}. I documenti sono associati ai documenti dell'istanza",
			allegaDocPratica);
		List<DocumentiType> docs = request.getDatiAttivita().getDocumenti();
		if (docs != null && !docs.isEmpty()) {
		    // nel caso notificaInteraPratica allora devo controllare che non inviamo documenti duplicati
		    if (notificaInteraPratica) {
			log.debug(
				"gestionePraticaDestinatario# Notifica intera pratica : {}. Vado ad aggiungere alla lista dei documenti sono quelli non presenti nella request dettaglioPraticaMittente",
				notificaInteraPratica);
			// Possiamo aggiungerli senza controllare se sono già presenti perchè quando spuntiamo notifica intera pratica da interfaccia web non
			// ci permette di selezionare come da inviare quelli dell'istanza (istanza + endo).
			dettaglioPraticaMittente.getDocumenti().addAll(docs);
			request.getDatiAttivita().getDocumenti().removeAll(request.getDatiAttivita().getDocumenti());
		    } else {
			log.debug(
				"gestionePraticaDestinatario# Notifica intera pratica : {}.Aggiungo tutti i documenti di NotificaAttivitaRequest nella sezione di dettaglioPraticaMittente ",
				notificaInteraPratica);
			dettaglioPraticaMittente.getDocumenti().addAll(docs);
			request.getDatiAttivita().getDocumenti().removeAll(request.getDatiAttivita().getDocumenti());
		    }
		}
	    }
	    //XML BOCCI (2012-05-10): è stato deciso insieme a Chiocci che in caso che non sia un inserimento pratica diretto (domanda on-line) 
	    // vanno tolti gli oneri della pratica in modo che il destinatario della pratica non abbia i riferimenti agli oneri:
	    // 
	    dettaglioPraticaMittente.getOneri().removeAll(dettaglioPraticaMittente.getOneri());
	    //..
	    praticaNLARequest.setDettaglioPratica(dettaglioPraticaMittente);
	    praticaNLARequest.setRifPraticaDestinatario(request.getRifPraticaDestinatario());
	    populateAltriDatiConCodiceAttivita(praticaNLARequest, request);
	    if (log.isDebugEnabled()) {
		log.debug("STC - Chiamata a WS inserisciPratica NLA-DEST(idnodo={},idente={},idsportello={})",
			new String[] { praticaNLARequest.getSportelloDestinatario().getIdNodo(),
				praticaNLARequest.getSportelloDestinatario().getIdEnte(),
				praticaNLARequest.getSportelloDestinatario().getIdSportello() });
	    }
	    InserimentoPraticaNLAResponse praticaNLAResponse = nlaWebServiceClient.inserisciPratica(praticaNLARequest, nodoDest);
	    List<ErroreType> errori = praticaNLAResponse.getDettaglioErrore();
	    if (errori != null && !errori.isEmpty()) {
		log.error("STC - Errore ritornato da chiamata a WS inserisciPratica: {}", decodeErrors(errori));
		throw new RuntimeException("STC - Errore ritornato da NLA destinatario[" + request.getSportelloDestinatario().getIdNodo() +
					   "] durante inserimento pratica: " + decodeErrors(errori));
	    }
	    // BOCCI 2015-10-22 http://redmine/redmine/issues/803 
	    // INSERIMENTO ATTIVITA': ATTIVARE DATO AGGIUNTIVO NEL CASO L'ATTIVITA' CREA ANCHE LA PRATICA DESTINATARIA
	    altriDatiInserimentoAttivita(request);
	    praticaDest = schemaConversionUtils.getPratica(praticaNLAResponse);
	}
	// DB: inserimento pratica NLA-DEST
	if (log.isDebugEnabled()) {
	    log.debug("STC - Inserimento pratica NLA-DEST su DB STC");
	}
	praticaDest.setConfigurazioneByFkidnodo(nodoDest);
	praticaDest.setIdente(request.getSportelloDestinatario().getIdEnte());
	praticaDest.setIdsportello(request.getSportelloDestinatario().getIdSportello());
	// potrebbe verificarsi il caso che la pratica non sia stata trovata tra i collegamenti di messaggipratiche e/o
	// messaggi attività ma che esista comunque sulla tabella PRATICHE
	// verifico l'esistenza e se esiste non inserisco nuovamente
	Pratiche praticaDestTemp = new Pratiche();
	praticaDestTemp.setConfigurazioneByFkidnodo(nodoDest);
	praticaDestTemp.setIdente(praticaDest.getIdente());
	praticaDestTemp.setIdsportello(praticaDest.getIdsportello());
	praticaDestTemp.setIdpratica(praticaDest.getIdpratica());
	Pratiche privatePraticaDest = praticheService.findByUniqueKey(praticaDestTemp);
	// DB: aggiornamento messaggipratica NLA-DEST
	if (log.isDebugEnabled()) {
	    log.debug("STC - Aggiornamento messaggipratica NLA-DEST su DB STC");
	}
	if (null == privatePraticaDest) {
	    praticheService.insert(praticaDest);
	    messaggipratiche.setPraticheByFkidrisposta(praticaDest);
	    messaggipraticheService.update(messaggipratiche);
	    return praticaDest;
	} else {
	    praticheService.update(privatePraticaDest);
	    messaggipratiche.setPraticheByFkidrisposta(privatePraticaDest);
	    messaggipraticheService.update(messaggipratiche);
	    return privatePraticaDest;
	}
    }

    /*
     * BOCCI 2015-10-22 http://redmine/redmine/issues/803 
     * INSERIMENTO ATTIVITA': ATTIVARE DATO AGGIUNTIVO NEL CASO L'ATTIVITA' CREA ANCHE LA PRATICA DESTINATARIA
     */
    private void altriDatiInserimentoAttivita(NotificaAttivitaRequest request) {

	if (request.getDatiAttivita() != null && request.getDatiAttivita().getAltriDati() != null) {
	    ParametroType pt = new ParametroType();
	    pt.setNome(ALTRO_DATO_NOTIFICA_ATTIVITA_PRATICA_CREATA_DA_ATTIVITA);
	    ValoreParametroType vpt = new ValoreParametroType();
	    vpt.setCodice(ALTRO_DATO_NOTIFICA_ATTIVITA_PRATICA_CREATA_DA_ATTIVITA);
	    vpt.setCodice(ALTRO_DATO_NOTIFICA_ATTIVITA_PRATICA_CREATA_DA_ATTIVITA);
	    pt.getValore().add(vpt);
	    request.getDatiAttivita().getAltriDati().add(pt);
	}
    }

    /**
     * Riprende il valore di request.getDatiAttivita().getTipoAttivita().getCodice() e lo inserisce nella sezione altri
     * dati di InserimentoPraticaNLARequest con nomeparametro
     * {@link #ALTRODATO_NOTIFICA_ATTIVITA_REQUEST_DATI_ATTIVITA_TIPO_ATTIVITA_CODICE}
     * praticaNLARequest.getDettaglioPratica().getAltriDati()
     * 
     * @param praticaNLARequest
     * @param request
     */
    private void populateAltriDatiConCodiceAttivita(InserimentoPraticaNLARequest praticaNLARequest, NotificaAttivitaRequest request) {

	if (request != null) {
	    if (request.getDatiAttivita() != null) {
		if (request.getDatiAttivita().getTipoAttivita() != null) {
		    if (StringUtils.isNotBlank(request.getDatiAttivita().getTipoAttivita().getCodice())) {
			String tipoAttivita = request.getDatiAttivita().getTipoAttivita().getCodice();
			if (praticaNLARequest.getDettaglioPratica() != null) {
			    if (praticaNLARequest.getDettaglioPratica().getAltriDati() != null) {
				ParametroType p = new ParametroType();
				p.setNome(ALTRODATO_NOTIFICA_ATTIVITA_REQUEST_DATI_ATTIVITA_TIPO_ATTIVITA_CODICE);
				ValoreParametroType vp = new ValoreParametroType();
				vp.setCodice(tipoAttivita);
				vp.setDescrizione(tipoAttivita);
				p.getValore().add(vp);
				praticaNLARequest.getDettaglioPratica().getAltriDati().add(p);
			    }
			}
		    }
		}
		if (request.getDatiAttivita().getAltriDati() != null && !request.getDatiAttivita().getAltriDati().isEmpty()) {
		    for (ParametroType pttype : request.getDatiAttivita().getAltriDati()) {
			String nome = StringUtils.defaultString(pttype.getNome());
			if (nome.startsWith(PREFISSO_STC_TRASFERISCE_DATO_SU_ISTANZA)) {
			    praticaNLARequest.getDettaglioPratica().getAltriDati().add(pttype);
			}
		    }
		}
	    }
	}
    }

    @Override
    public AllegatoBinarioResponse allegatoBinario(AllegatoBinarioRequest request) {

	sicurezzaService.checkToken(request.getToken());
	verificaSportelloMitt(request.getSportelloMittente());
	Configurazione nodoDest = verificaSportelloDest(request.getSportelloDestinatario());
	AllegatoBinarioResponse response = new AllegatoBinarioResponse();
	AllegatoBinarioNLARequest nlaRequest = new AllegatoBinarioNLARequest();
	nlaRequest.setToken(request.getToken());
	nlaRequest.setRiferimentiAllegato(request.getRiferimentiAllegato());
	nlaRequest.setSportelloDestinatario(request.getSportelloDestinatario());
	nlaRequest.setSportelloMittente(request.getSportelloMittente());
	AllegatoBinarioNLAResponse nlaResponse = nlaWebServiceClient.richiestaAllegato(nlaRequest, nodoDest);
	response.setBinaryData(nlaResponse.getBinaryData());
	response.setFileName(nlaResponse.getFileName());
	response.setMimeType(nlaResponse.getMimeType());
	return response;
    }

    @Override
    public RichiestaPraticaResponse richiestaPratica(RichiestaPraticaRequest request) {

	sicurezzaService.checkToken(request.getToken());
	verificaSportelloMitt(request.getSportelloMittente());
	Configurazione nodoDest = verificaSportelloDest(request.getSportelloDestinatario());
	RichiestaPraticaResponse response = new RichiestaPraticaResponse();
	RichiestaPraticaNLARequest nlaRequest = new RichiestaPraticaNLARequest();
	nlaRequest.setRifPratica(request.getRifPratica());
	nlaRequest.setToken(request.getToken());
	nlaRequest.setSportelloDestinatario(request.getSportelloDestinatario());
	nlaRequest.setSportelloMittente(request.getSportelloMittente());
	RichiestaPraticaNLAResponse nlaResponse = nlaWebServiceClient.richiestaPratica(nlaRequest, nodoDest);
	response.setDettaglioPratica(nlaResponse.getDettaglioPratica());
	if (!nlaResponse.getDettaglioErrore().isEmpty()) {
	    response.getDettaglioErrore().addAll(nlaResponse.getDettaglioErrore());
	}
	return response;
    }

    /**
     * metodo per la verifica della presenza dei nodi su DB.<br/>
     * se uno dei nodi non è presente il metodo lancia una RuntimeException
     * 
     * @param nodoMit
     *            nodo mittente
     * @param nodoDest
     *            nodo destinatario
     */
    private Configurazione verificaSportelloMitt(SportelloType sportelloMitt) {

	Integer idNodoMitt = null;
	try {
	    idNodoMitt = Integer.valueOf(sportelloMitt.getIdNodo());
	} catch (NumberFormatException e) {
	    log.error("STC - Sportello mittente inesistente(idnodo={})", sportelloMitt.getIdNodo());
	    throw new RuntimeException("Sportello mittente inesistente (idnodo=" + sportelloMitt.getIdNodo() + ")");
	}
	Configurazione nodoMitt = configurazioneService.findById(idNodoMitt);
	if (nodoMitt == null) {
	    log.error("STC - Sportello mittente inesistente(idnodo={})", idNodoMitt);
	    throw new RuntimeException("Sportello mittente inesistente (idnodo=" + idNodoMitt + ")");
	}
	return nodoMitt;
    }

    private Configurazione verificaSportelloDest(SportelloType sportelloDest) {

	Integer idNodoDest = null;
	try {
	    idNodoDest = Integer.valueOf(sportelloDest.getIdNodo());
	} catch (NumberFormatException e) {
	    log.error("STC - Sportello destinatario inesistente(idnodo={})", sportelloDest.getIdNodo());
	    throw new RuntimeException("Sportello destinatario inesistente (idnodo=" + sportelloDest.getIdNodo() + ")");
	}
	Configurazione nodoDest = configurazioneService.findById(idNodoDest);
	if (nodoDest == null) {
	    log.error("STC - Sportello destinatario inesistente(idnodo={})", idNodoDest);
	    throw new RuntimeException("Sportello destinatario inesistente (idnodo=" + idNodoDest + ")");
	}
	return nodoDest;
    }

    @Override
    public DirezioneSportelloResponse direzioneSportello(DirezioneSportelloRequest request) {

	sicurezzaService.checkToken(request.getToken());
	DirezioneSportelloResponse response = new DirezioneSportelloResponse();
	DirezioneType direzioneType = new DirezioneType();
	Configurazione nodo = verificaSportelloMitt(request.getSportello());
	direzioneType.setCodice(nodo.getIddirezione());
	direzioneType.setDescrizione(nodo.getDirezione());
	response.setDirezione(direzioneType);
	return response;
    }

    @Override
    public RichiestaPraticaCollegataResponse richiestaPraticaCollegata(RichiestaPraticaCollegataRequest request) {

	sicurezzaService.checkToken(request.getToken());
	Configurazione nodoMitt = verificaSportelloMitt(request.getSportelloMittente());
	Pratiche example = new Pratiche();
	example.setConfigurazioneByFkidnodo(nodoMitt);
	example.setIdente(request.getSportelloMittente().getIdEnte());
	example.setIdsportello(request.getSportelloMittente().getIdSportello());
	example.setIdpratica(request.getIdPraticaMitt());
	if (log.isDebugEnabled()) {
	    log.debug("STC - Verifica esistenza pratica per NLA-MIT(idnodo={},idente={},idsportello={},idpratica={})",
		    new String[] { nodoMitt.getIdnodo().toString(), example.getIdente(), example.getIdsportello(), example.getIdpratica(), });
	}
	Pratiche praticaMitt = praticheService.findByUniqueKey(example);
	RichiestaPraticaCollegataResponse response = new RichiestaPraticaCollegataResponse();
	if (praticaMitt == null) {
	    ErroreType errore = new ErroreType();
	    errore.setNumeroErrore(Constants.ERRORE_PRATICA_ATTIVITA_NON_TROVATA);
	    errore.setDescrizione("Pratica non trovata in STC");
	    response.getDettaglioErrore().add(errore);
	    return response;
	}
	// dovrebbe esserci solamente una pratica collegata se chi chiama questo metodo è una pratica creata tramite STC
	// DOMANDA ma se chi utilizza questo metodo è chi ha creato altre pratiche es. SUAP con più endoprocedimenti ?
	// al momento ciclo e ne prendo una
	boolean praticaCollegataTrovata = false;
	Pratiche praticaCollegata = null;
	Set<Messaggipratiche> messaggis = praticaMitt.getMessaggipratichesForFkidrisposta();
	for (Messaggipratiche messaggipratiche : messaggis) {
	    praticaCollegata = messaggipratiche.getPraticheByFkidrichiesta();
	    if (praticaCollegata != null) {
		if (praticaCollegata.getConfigurazioneByFkidnodo().getIdnodo().toString().equals(request.getSportelloDestinatario().getIdNodo())
			&& praticaCollegata.getIdente().equals(request.getSportelloDestinatario().getIdEnte())
			&& praticaCollegata.getIdsportello().equals(request.getSportelloDestinatario().getIdSportello())) {
		    ProcedimentoType pt = new ProcedimentoType();
		    pt.setCodice(request.getIdProcedimentoMitt());
		    if (verificaPraticaDestinatario(praticaCollegata, praticaMitt, pt)) {
			praticaCollegataTrovata = true;
			break;
		    }
		}
	    }
	}
	if (!praticaCollegataTrovata) {
	    messaggis = praticaMitt.getMessaggipratichesForFkidrichiesta();
	    for (Messaggipratiche messaggipratiche : messaggis) {
		praticaCollegata = messaggipratiche.getPraticheByFkidrisposta();
		if (praticaCollegata != null) {
		    if (praticaCollegata.getConfigurazioneByFkidnodo().getIdnodo().toString().equals(request.getSportelloDestinatario().getIdNodo())
			    && praticaCollegata.getIdente().equals(request.getSportelloDestinatario().getIdEnte())
			    && praticaCollegata.getIdsportello().equals(request.getSportelloDestinatario().getIdSportello())) {
			ProcedimentoType pt = new ProcedimentoType();
			pt.setCodice(request.getIdProcedimentoMitt());
			if (verificaPraticaDestinatario(praticaCollegata, praticaMitt, pt)) {
			    praticaCollegataTrovata = true;
			    break;
			}
		    }
		}
	    }
	}
	if (!praticaCollegataTrovata) {
	    ErroreType errore = new ErroreType();
	    errore.setNumeroErrore(Constants.ERRORE_PRATICA_NON_COLLEGATA);
	    errore.setDescrizione("Pratica collegata non trovata in STC");
	    response.getDettaglioErrore().add(errore);
	    return response;
	}
	RichiestaPraticaCollegataResponse.Dettaglio dettaglio = new RichiestaPraticaCollegataResponse.Dettaglio();
	SportelloType sportello = new SportelloType();
	sportello.setIdEnte(praticaCollegata.getIdente());
	sportello.setIdSportello(praticaCollegata.getIdsportello());
	sportello.setIdNodo(praticaCollegata.getConfigurazioneByFkidnodo().getIdnodo().toString());
	dettaglio.setSportello(sportello);
	RichiestaPraticaRequest nlaRequest = new RichiestaPraticaRequest();
	RiferimentiPraticaType riferimentiPraticaDest = new RiferimentiPraticaType();
	riferimentiPraticaDest.setIdPratica(praticaCollegata.getIdpratica());
	riferimentiPraticaDest.setNumeroPratica(praticaCollegata.getNumpratica());
	nlaRequest.setRifPratica(riferimentiPraticaDest);
	nlaRequest.setToken(request.getToken());
	nlaRequest.setSportelloDestinatario(sportello);
	nlaRequest.setSportelloMittente(request.getSportelloMittente());
	RichiestaPraticaResponse nlaResponse = this.richiestaPratica(nlaRequest);
	// gestisco gli errori della chiamata a this.richiestaPratica
	if (!nlaResponse.getDettaglioErrore().isEmpty()) {
	    response.getDettaglioErrore().addAll(nlaResponse.getDettaglioErrore());
	    return response;
	}
	dettaglio.setDettaglioPratica(nlaResponse.getDettaglioPratica().getDettaglioPratica());
	response.setDettaglio(dettaglio);
	return response;
    }

    @Override
    public RichiestaPraticaCollegataResponse richiestaPraticaCollegataDaAttivitaMittente(RichiestaPraticaCollegataDaAttivitaMittenteRequest request) {

	Attivita attivitaMittente = this.attivitaService.findBySportelloTypeAndIdAttivita(request.getSportello(), request.getIdAttivita());
	if (attivitaMittente == null) {
	    return attivitaNonTrovata(request.getIdAttivita(), request.getSportello(), true);
	}
	return richiestaPraticaCollegataDaAttivita(attivitaMittente, true, request.getToken(), request.getSportello());
    }

    @Override
    public RichiestaPraticaCollegataResponse richiestaPraticaCollegataDaAttivitaDestinataria(
	    RichiestaPraticaCollegataDaAttivitaDestinatariaRequest request) {

	Attivita attivitaDestinataria = this.attivitaService.findBySportelloTypeAndIdAttivita(request.getSportello(), request.getIdAttivita());
	if (attivitaDestinataria == null) {
	    return attivitaNonTrovata(request.getIdAttivita(), request.getSportello(), false);
	}
	return richiestaPraticaCollegataDaAttivita(attivitaDestinataria, false, request.getToken(), request.getSportello());
    }

    @Override
    public InserimentoPraticaResponse inserimentoPratica(InserimentoPraticaRequest request) {

	sicurezzaService.checkToken(request.getToken());
	Configurazione nodoMitt = this.verificaSportelloMitt(request.getSportelloMittente());
	Configurazione nodoDest = this.verificaSportelloDest(request.getSportelloDestinatario());
	InserimentoPraticaResponse response = new InserimentoPraticaResponse();
	// inserisco la pratica del mittente nel db stc
	SchemaConversionUtils schemaConversionUtils = new SchemaConversionUtils();
	Pratiche praticaMitTemp = new Pratiche();
	praticaMitTemp.setConfigurazioneByFkidnodo(nodoMitt);
	praticaMitTemp.setIdente(request.getSportelloMittente().getIdEnte());
	praticaMitTemp.setIdsportello(request.getSportelloMittente().getIdSportello());
	praticaMitTemp.setIdpratica(request.getDettaglioPratica().getIdPratica());
	praticaMitTemp.setNumpratica(request.getDettaglioPratica().getNumeroPratica());
	if (log.isDebugEnabled()) {
	    log.debug("STC - inserimentoPratica() - Verifica esistenza pratica NLA-MIT su DB STC(idnodo={},idente={},idsportello={},idpratica={})",
		    new Object[] { nodoMitt.getIdnodo(), praticaMitTemp.getIdente(), praticaMitTemp.getIdsportello(),
			    praticaMitTemp.getIdpratica() });
	}
	Pratiche praticaMit = praticheService.findByUniqueKey(praticaMitTemp);
	if (praticaMit != null) {
	    response.getDettaglioErrore().add(ErroriSTCHelper.praticaMittenteEsistente(praticaMit));
	    return response;
	}
	if (log.isDebugEnabled()) {
	    log.debug(
		    "STC - inserimentoPratica() - Inserimento pratica NLA-MIT su DB STC(idnodo={},idente={},idsportello={},idpratica={},numpratica={})",
		    new Object[] { nodoMitt.getIdnodo(), praticaMitTemp.getIdente(), praticaMitTemp.getIdsportello(), praticaMitTemp.getIdpratica(),
			    praticaMitTemp.getNumpratica() });
	}
	praticaMit = schemaConversionUtils.getPratica(request);
	praticaMit.setConfigurazioneByFkidnodo(nodoMitt);
	praticaMit.setIdente(request.getSportelloMittente().getIdEnte());
	praticaMit.setIdsportello(request.getSportelloMittente().getIdSportello());
	praticheService.insert(praticaMit);
	// chiamo il nodo destinatario per inserire la pratica
	InserimentoPraticaNLARequest nlaRequest = new InserimentoPraticaNLARequest();
	nlaRequest.setDettaglioPratica(request.getDettaglioPratica());
	// aggiungo il parametro per specificare che questo inserimento pratica è stato chiamato direttamente dal nodo nla-mitt
	ParametroType paramInsDiretto = new ParametroType();
	paramInsDiretto.setNome(StcServiceImpl.ALTRO_DATO_INSERIMENTO_DIRETTO);
	ValoreParametroType vpt = new ValoreParametroType();
	vpt.setCodice(StcServiceImpl.ALTRO_DATO_INSERIMENTO_DIRETTO);
	paramInsDiretto.getValore().add(vpt);
	nlaRequest.getDettaglioPratica().getAltriDati().add(paramInsDiretto);
	//
	nlaRequest.setSportelloDestinatario(request.getSportelloDestinatario());
	nlaRequest.setSportelloMittente(request.getSportelloMittente());
	nlaRequest.setToken(request.getToken());
	InserimentoPraticaNLAResponse nlaResponse = nlaWebServiceClient.inserisciPratica(nlaRequest, nodoDest);
	response.setDettaglioPratica(nlaResponse.getDettaglioPratica());
	response.getDettaglioErrore().addAll(nlaResponse.getDettaglioErrore());
	if (nlaResponse.getDettaglioErrore() == null || nlaResponse.getDettaglioErrore().isEmpty()) {
	    // inserisco la pratica del destinatario nel db stc
	    Pratiche praticaDest = schemaConversionUtils.getPratica(nlaResponse);
	    praticaDest.setConfigurazioneByFkidnodo(nodoDest);
	    praticaDest.setIdente(request.getSportelloDestinatario().getIdEnte());
	    praticaDest.setIdsportello(request.getSportelloDestinatario().getIdSportello());
	    if (log.isDebugEnabled()) {
		log.debug(
			"STC - inserimentoPratica() - Inserimento pratica NLA-DEST su DB STC(idnodo={},idente={},idsportello={},idpratica={},numpratica={})",
			new Object[] { praticaDest.getConfigurazioneByFkidnodo().getIdnodo(), praticaDest.getIdente(), praticaDest.getIdsportello(),
				praticaDest.getIdpratica(), praticaDest.getNumpratica() });
	    }
	    praticheService.insert(praticaDest);
	    // collego le pratiche inserite
	    if (log.isDebugEnabled()) {
		log.debug("STC - inserimentoPratica() - Collegamento pratiche su DB STC");
	    }
	    Messaggipratiche messaggipratiche = new Messaggipratiche();
	    messaggipratiche.setPraticheByFkidrichiesta(praticaMit);
	    messaggipratiche.setPraticheByFkidrisposta(praticaDest);
	    messaggipraticheService.insert(messaggipratiche);
	} else {
	    throw new RuntimeException("STC - Inserimento Pratica: " + decodeErrors(nlaResponse.getDettaglioErrore()));
	}
	return response;
    }

    private String decodeErrors(List<ErroreType> errors) {

	StringBuilder privateErrors = new StringBuilder();
	if (errors != null && !errors.isEmpty()) {
	    for (ErroreType erroreType : errors) {
		privateErrors.append(" ").append(erroreType.getNumeroErrore()).append(": ").append(erroreType.getDescrizione()).append(" ");
	    }
	}
	return privateErrors.toString();
    }

    @Override
    public RichiestaPraticheListaResponse richiestaPraticheLista(RichiestaPraticheListaRequest request) {

	sicurezzaService.checkToken(request.getToken());
	this.verificaSportelloMitt(request.getSportelloMittente());
	Configurazione nodoDest = this.verificaSportelloDest(request.getSportelloDestinatario());
	RichiestaPraticheListaNLARequest nlaRequest = new RichiestaPraticheListaNLARequest();
	nlaRequest.setFiltriPratica(request.getFiltriPratica());
	nlaRequest.setFiltriUtenteConnesso(request.getFiltriUtenteConnesso());
	nlaRequest.setSportelloDestinatario(request.getSportelloDestinatario());
	nlaRequest.setSportelloMittente(request.getSportelloMittente());
	nlaRequest.setToken(request.getToken());
	RichiestaPraticheListaNLAResponse nlaResponse = nlaWebServiceClient.richiestaPraticheLista(nlaRequest, nodoDest);
	RichiestaPraticheListaResponse response = new RichiestaPraticheListaResponse();
	response.getDettaglioErrore().addAll(nlaResponse.getDettaglioErrore());
	response.getDettaglioPratica().addAll(nlaResponse.getDettaglioPratica());
	return response;
    }

    @Override
    public AggiungiDocumentiResponse aggiungiDocumenti(AggiungiDocumentiRequest request) {

	sicurezzaService.checkToken(request.getToken());
	this.verificaSportelloMitt(request.getSportelloMittente());
	Configurazione nodoDest = this.verificaSportelloDest(request.getSportelloDestinatario());
	AggiungiDocumentiNLARequest nlaRequest = new AggiungiDocumentiNLARequest();
	nlaRequest.setToken(request.getToken());
	nlaRequest.setIdPraticaDest(request.getIdPraticaDest());
	nlaRequest.setSportelloMittente(request.getSportelloMittente());
	nlaRequest.setSportelloDestinatario(request.getSportelloDestinatario());
	nlaRequest.getDocumenti().addAll(request.getDocumenti());
	AggiungiDocumentiNLAResponse nlaResponse = nlaWebServiceClient.aggiungiDocumenti(nlaRequest, nodoDest);
	AggiungiDocumentiResponse response = new AggiungiDocumentiResponse();
	response.getDettaglioErrore().addAll(nlaResponse.getDettaglioErrore());
	return response;
    }

    @Override
    public Messaggiattivita notificaAttivitaCollegaAttivita(NotificaAttivitaRequest request, Pratiche praticaMitt, Pratiche praticaDest) {

	// solo se idprocedimento<>''
	// - se non esistono record con attivita dest con idattivita nullo lo inserisco altrimenti ritorno quello giÃ  creato
	// - inserisce attivita mittente
	// - inserisce attivita destinataria con riferimenti vuoti e idprocedimento
	// inserisce i collegamenti tra le attivita
	SchemaConversionUtils schemaConversionUtils = new SchemaConversionUtils();
	ProcedimentoType procedimentoPrincipale = schemaConversionUtils.getProcedimentoPrincipale(request.getDatiAttivita().getProcedimenti());
	String idProcedimento = null;
	log.debug("STC - Verifica esistenza attività NLA-MIT su DB STC");
	Attivita example = new Attivita();
	example.setIdattivita(request.getDatiAttivita().getIdAttivita());
	if (procedimentoPrincipale != null) {
	    idProcedimento = procedimentoPrincipale.getCodice();
	    example.setIdprocedimento(procedimentoPrincipale.getCodice());
	}
	Messaggiattivita messaggiattivita = new Messaggiattivita();
	//setto la pratica mittente per la query
	example.setPratiche(praticaMitt);
	Attivita privateAttivita = attivitaService.findByUniqueKey(example);
	if (privateAttivita == null) {
	    // attivita' gia' presente
	    log.debug("STC - Attività NLA-MIT non trovata su DB STC");
	    // DB: inserimento attivitÃ  NLA-MIT //////////////////////////////////////////////////////////////////////////////////////////////
	    log.debug("STC - Inserimento attività NLA-MIT su DB STC");
	    Attivita attivita = schemaConversionUtils.getAttivita(request);
	    attivita.setPratiche(praticaMitt);
	    attivitaService.insert(attivita);
	    messaggiattivita.setAttivitaByFkidrichiesta(attivita);
	} else {
	    messaggiattivita.setAttivitaByFkidrichiesta(privateAttivita);
	}
	Attivita attivitaDest = schemaConversionUtils.getNuovaAttivita();
	attivitaDest.setIdprocedimento(idProcedimento);
	attivitaDest.setPratiche(praticaDest);
	attivitaDest.setDatasistema(GregorianCalendar.getInstance().getTime());
	attivitaService.insert(attivitaDest);
	// DB: aggiornamento messaggiattvita NLA-DEST ///////////////////////////////////////////////////////////////////////////////////////
	log.debug("STC - Aggiornamento messaggiattvita NLA-DEST su DB STC");
	messaggiattivita.setAttivitaByFkidrisposta(attivitaDest);
	messaggiattivitaService.insert(messaggiattivita);
	return messaggiattivita;
    }

    @Override
    public NotificaAttivitaResponse notificaAttivitaGestioneAttivita(NotificaAttivitaRequest request, Messaggiattivita messaggiattivita) {

	log.debug("STC - notificaAttivitaGestioneAttivita");
	messaggiattivita = messaggiattivitaService.findById(messaggiattivita.getId());
	// DB: verifica nodi ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	Configurazione nodoDest = this.verificaSportelloDest(request.getSportelloDestinatario());
	// XML: estrazione procedimento principale da NotificaAttivitaRequest
	SchemaConversionUtils schemaConversionUtils = new SchemaConversionUtils();
	// XML: estrazione parametro $NON_INVIARE_PROCEDIMENTI$
	boolean nonInviareEndo = false;
	for (ParametroType parametroType : request.getDatiAttivita().getAltriDati()) {
	    if (ALTRO_DATO_NON_INVIARE_PROCEDIMENTI.equals(parametroType.getNome())) {
		nonInviareEndo = true;
		break;
	    }
	}
	// DB: inserimento messaggiattvita NLA-MIT ///////////////////////////////////////////////////////////////////////////////////////
	log.debug("STC - Inserimento messaggiattvita NLA-MIT su DB STC");
	// WS: inserisciAttivita NLA-DEST ////////////////////////////////////////////////////////////////////////////////////////////////
	InserimentoAttivitaNLARequest inserimentoAttivitaNLARequest = schemaConversionUtils.getInserimentoAttivitaFromNotificaAttivita(request);
	Attivita attmitt = messaggiattivita.getAttivitaByFkidrichiesta();
	Attivita attivitaDest = messaggiattivita.getAttivitaByFkidrisposta();
	Pratiche praticaMitt = attmitt.getPratiche();
	Pratiche praticaDest = attivitaDest.getPratiche();
	if (StringUtils.isBlank(attmitt.getIdprocedimento())) {
	    // BOCCI-CHIOCCI 26/02/2014: SE  IDPROCEDIMENTO DELL'ATTIVITA' MITTENTE E' NULLO DEVO CERCARE 
	    // NELLA STORIA DELLE COMUNICAZIONI DELLE DUE PRATICHE TRA MESSAGGIATTIVITA' I MESSAGGI DOVE IL MITTENTE E' LA PRATICA ALTRA E DESTINATARIO E' LA PRATICA MIA CON IDPROCEDIMENTO NULL
	    // SE TROVO UNA RIGA ALLORA L'ENDO DA INVIARE SARA' QUELLO
	    String idProcedimentoPrecedentiComunicazioni = attivitaService.findIdProcedimentoPrecedentiComunicazioni(praticaMitt.getId(),
		    praticaDest.getId());
	    if (StringUtils.isNotBlank(idProcedimentoPrecedentiComunicazioni)) {
		ProcedimentoType p = new ProcedimentoType();
		p.setDescrizione(idProcedimentoPrecedentiComunicazioni);
		p.setCodice(idProcedimentoPrecedentiComunicazioni);
		p.setPrincipale(Boolean.TRUE);
		inserimentoAttivitaNLARequest.getDatiAttivita().getProcedimenti().add(p);
	    }
	}
	// XML: setto l'id pratica a quello della pratica dell'NLA-DEST
	inserimentoAttivitaNLARequest.getDatiAttivita().setIdPratica(praticaDest.getIdpratica());
	RiferimentiPraticaType rifPraticaMitt = new RiferimentiPraticaType();
	rifPraticaMitt.setIdPratica(praticaMitt.getIdpratica());
	rifPraticaMitt.setNumeroPratica(praticaMitt.getNumpratica());
	if (praticaMitt.getDatapratica() != null) {
	    GregorianCalendar dataPratica = new GregorianCalendar();
	    dataPratica.setTime(praticaMitt.getDatapratica());
	    rifPraticaMitt.setDataPratica(Utilities.getXMLGregorianCalendar(dataPratica));
	}
	rifPraticaMitt.setNumeroProtocolloGenerale(praticaMitt.getNumprotgen());
	GregorianCalendar dataProtPratica = new GregorianCalendar();
	if (praticaMitt.getDataprotgen() != null) {
	    dataProtPratica.setTime(praticaMitt.getDataprotgen());
	    rifPraticaMitt.setDataProtocolloGenerale(Utilities.getXMLGregorianCalendar(dataProtPratica));
	}
	inserimentoAttivitaNLARequest.setRifPraticaMittente(rifPraticaMitt);
	if (nonInviareEndo) {
	    //XML: rimuovo dall'attivitÃ  da inserire nel destinatario tutti gli endo del mittente
	    inserimentoAttivitaNLARequest.getDatiAttivita().getProcedimenti()
		    .removeAll(inserimentoAttivitaNLARequest.getDatiAttivita().getProcedimenti());
	}
	log.debug("STC - Chiamata a WS inserisciAttivita NLA-DEST(idnodo={})", nodoDest.getIdnodo());
	InserimentoAttivitaNLAResponse inserimentoAttivitaResponse = nlaWebServiceClient.inserisciAttivita(inserimentoAttivitaNLARequest, nodoDest);
	List<ErroreType> errori = inserimentoAttivitaResponse.getDettaglioErrore();
	if (errori != null && !errori.isEmpty()) {
	    String error = "STC - Errore ritornato da NLA destinatario [" + request.getSportelloDestinatario().getIdNodo() +
			   "] durante inserimento attività: " + decodeErrors(errori);
	    log.error(error);
	    throw new RuntimeException(error);
	}
	// DB: inserimento messaggiattvita NLA-DEST /////////////////////////////////////////////////////////////////////////////////////////
	log.debug("STC - Inserimento messaggiattvita NLA-DEST su DB STC");
	attivitaDest = schemaConversionUtils.getAttivita(inserimentoAttivitaResponse, attivitaDest);
	attivitaService.update(attivitaDest);
	NotificaAttivitaResponse response = schemaConversionUtils.getNotificaAttivitaFromAttivita(attivitaDest, praticaDest);
	//fabrizioc: veicolo la sezione altridati dal nodo dest al nodo mitt
	response.getDettaglioattivita().getAltriDati().addAll(inserimentoAttivitaResponse.getDettaglioAttivita().getAltriDati());
	log.debug("STC - Notifica attività ...completata");
	return response;
    }

    @Override
    public Object cancellaAttivita(CancellaAttivitaRequest request) {

	return null;
    }

    private RichiestaPraticaCollegataResponse attivitaNonTrovata(String idAttivita, SportelloType sportello, boolean isMittente) {

	RichiestaPraticaCollegataResponse response = new RichiestaPraticaCollegataResponse();
	ErroreType et = new ErroreType();
	String msg = "Attività " + (isMittente ? "mittente" : "destinataria") + " con codice " + idAttivita + " non trovata nello sportello " +
		     ReflectionToStringBuilder.toString(sportello, ToStringStyle.SIMPLE_STYLE);
	log.error(msg);
	et.setNumeroErrore("STC_ATTIVITA_NON_TROVATA");
	et.setDescrizione(msg);
	response.getDettaglioErrore().add(et);
	return response;
    }

    private RichiestaPraticaCollegataResponse richiestaPraticaCollegataDaAttivita(Attivita attivita, boolean isMittente, String tokenRichiesta,
	    SportelloType sportelloRichiesta) {

	int idAttivita = attivita.getId();
	List<Messaggiattivita> messaggi = null;
	Pratiche pratica = null;
	if (isMittente) {
	    // Io ho creato/collegato la pratica vado a vedere nei messaggi dove sono richiesta
	    messaggi = messaggiattivitaService.findByIdAttivita(idAttivita, TIPO_COLLEGAMENTO.RICHIESTA);
	    pratica = messaggi.get(0).getAttivitaByFkidrisposta().getPratiche();
	} else {
	    // Io sono stato creato/collegato da una pratica vado a vedere nei messaggi dove sono risposta
	    messaggi = messaggiattivitaService.findByIdAttivita(idAttivita, TIPO_COLLEGAMENTO.RISPOSTA);
	    pratica = messaggi.get(0).getAttivitaByFkidrichiesta().getPratiche();
	}
	RichiestaPraticaCollegataResponse response = new RichiestaPraticaCollegataResponse();
	RichiestaPraticaCollegataResponse.Dettaglio dettaglio = new RichiestaPraticaCollegataResponse.Dettaglio();
	SportelloType sportello = new SportelloType();
	sportello.setIdEnte(pratica.getIdente());
	sportello.setIdSportello(pratica.getIdsportello());
	sportello.setIdNodo(pratica.getConfigurazioneByFkidnodo().getIdnodo().toString());
	dettaglio.setSportello(sportello);
	RichiestaPraticaRequest nlaRequest = new RichiestaPraticaRequest();
	RiferimentiPraticaType riferimentiPraticaDest = new RiferimentiPraticaType();
	riferimentiPraticaDest.setIdPratica(pratica.getIdpratica());
	riferimentiPraticaDest.setNumeroPratica(pratica.getNumpratica());
	nlaRequest.setRifPratica(riferimentiPraticaDest);
	nlaRequest.setToken(tokenRichiesta);
	nlaRequest.setSportelloDestinatario(sportello);
	nlaRequest.setSportelloMittente(sportelloRichiesta);
	RichiestaPraticaResponse nlaResponse = this.richiestaPratica(nlaRequest);
	// gestisco gli errori della chiamata a this.richiestaPratica
	if (!nlaResponse.getDettaglioErrore().isEmpty()) {
	    response.getDettaglioErrore().addAll(nlaResponse.getDettaglioErrore());
	    return response;
	}
	dettaglio.setDettaglioPratica(nlaResponse.getDettaglioPratica().getDettaglioPratica());
	response.setDettaglio(dettaglio);
	return response;
    }
}
