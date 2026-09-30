package it.gruppoinit.pal.gp.backoffice.ws;

import java.util.GregorianCalendar;
import java.util.Properties;

import javax.jws.WebService;
import javax.xml.bind.JAXBElement;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import it.gruppoinit.pal.gp.backoffice.schemas.messages.base.CategorieEventiBaseType;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipimovimento;
import it.gruppoinit.pal.gp.core.domain.TipimovimentoId;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.exception.FunzioneBusinessRemotaException;
import it.gruppoinit.pal.gp.core.features.istanze.documenti.generazionetemplate.IApplicaQRCodeService;
import it.gruppoinit.pal.gp.core.features.istanze.documenti.generazionetemplate.verticalizzazione.VerticalizzazioneQRCodeServiceImpl;
import it.gruppoinit.pal.gp.core.features.protocollazione.logic.ProtocollazioneService;
import it.gruppoinit.pal.gp.core.features.protocollazione.verticalizzazione.VerticalizzazioneProtocolloAttivoServiceImpl;
import it.gruppoinit.pal.gp.core.service.AmministrazioniService;
import it.gruppoinit.pal.gp.core.service.DocumentiistanzaService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.IstanzeeventiService;
import it.gruppoinit.pal.gp.core.service.MovimentiService;
import it.gruppoinit.pal.gp.core.service.NlaHelperService;
import it.gruppoinit.pal.gp.core.service.NlaManager;
import it.gruppoinit.pal.gp.core.service.NlaService;
import it.gruppoinit.pal.gp.core.service.TipiMovimentoService;
import it.gruppoinit.pal.gp.core.service.helper.DownloadFileSTCAsincronoHelper;
import it.gruppoinit.pal.gp.core.service.helper.NotificheAutomaticheAsincroneHelper;
import it.gruppoinit.pal.gp.core.service.helper.RiepilogoHelper;
import it.gruppoinit.pal.gp.core.service.rules.OggettiBusinessRules;
import it.gruppoinit.pal.gp.core.service.rules.SigeproBusinessRules;
import it.gruppoinit.pal.gp.core.utils.ExternalDBResolver;
import it.gruppoinit.pal.gp.core.utils.StcUtils;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.pal.gp.core.ws.BaseWS;
import it.gruppoinit.protocollo.schemas.messages.DatiAnagraficiType;
import it.gruppoinit.protocollo.schemas.messages.DatiProtocolloResponseType;
import it.gruppoinit.protocollo.schemas.messages.ObjectFactory;
import it.init.sigepro.rte.AggiungiDocumentiNLARequest;
import it.init.sigepro.rte.AggiungiDocumentiNLAResponse;
import it.init.sigepro.rte.AllegatoBinarioNLARequest;
import it.init.sigepro.rte.AllegatoBinarioNLAResponse;
import it.init.sigepro.rte.InserimentoAttivitaNLARequest;
import it.init.sigepro.rte.InserimentoAttivitaNLAResponse;
import it.init.sigepro.rte.InserimentoPraticaNLARequest;
import it.init.sigepro.rte.InserimentoPraticaNLAResponse;
import it.init.sigepro.rte.RichiestaPraticaNLARequest;
import it.init.sigepro.rte.RichiestaPraticaNLAResponse;
import it.init.sigepro.rte.RichiestaPraticheListaNLARequest;
import it.init.sigepro.rte.RichiestaPraticheListaNLAResponse;
import it.init.sigepro.rte.TestNLARequest;
import it.init.sigepro.rte.TestNLAResponse;
import it.init.sigepro.rte.XsdNlaVersion;
import it.init.sigepro.rte.definitions.Nla;
import it.init.sigepro.rte.types.ErroreType;
import it.init.sigepro.rte.types.SportelloType;
import it.init.sigepro.rte.types.ValoreParametroType;
import it.init.sigepro.rte.types.XsdTypesVersion;

@WebService(targetNamespace = "http://sigepro.init.it/rte/definitions", name = "Nla", serviceName = "NlaService", portName = "NlaSoap11", endpointInterface = "it.init.sigepro.rte.definitions.Nla")
public class NlaWS extends BaseWS implements Nla {

    private static final Logger log = LoggerFactory.getLogger(NlaWS.class);
    @Autowired
    private NlaService nlaService;
    @Autowired
    private ExternalDBResolver externalDBResolver;
    @Autowired
    private MovimentiService movimentiService;
    @Autowired
    private ProtocollazioneService protocollazioneService;
    @Autowired
    private NlaManager nlaManager;
    @Autowired
    private NlaHelperService nlaHelperService;
    @Autowired
    private IstanzeService istanzeService;
    @Autowired
    private NotificheAutomaticheAsincroneHelper notificheAutomaticheAsincroneHelper;
    @Autowired
    private DownloadFileSTCAsincronoHelper downloadFileSTCAsincronoHelper;
    @Autowired
    private AmministrazioniService amministrazioniService;
    @Autowired
    private DocumentiistanzaService documentiistanzaService;
    @Autowired
    private TipiMovimentoService tipiMovimentoService;
    @Autowired
    private IApplicaQRCodeService applicaQRCodeService;
    @Autowired
    private IstanzeeventiService istanzeeventiService;

    public InserimentoAttivitaNLAResponse inserimentoAttivitaNLA(InserimentoAttivitaNLARequest request) {

	String idcomunealias = request.getSportelloDestinatario().getIdEnte();
	checkSportelloPEC_O_PROTO_Client(request.getSportelloDestinatario(), true);
	boolean isPecOpRoto = checkSportelloPEC_O_PROTO_Client(request.getSportelloMittente(), false);
	if (isPecOpRoto) {
	    String softwareReale = request.getSportelloMittente().getIdSportello();
	    softwareReale = softwareReale.substring(0, softwareReale.indexOf("#"));
	    request.getSportelloMittente().setIdSportello(softwareReale);
	}
	String software = request.getSportelloDestinatario().getIdSportello();
	Properties connProps = externalDBResolver.getConnectionProperties(idcomunealias);
	setORMHelper(software, connProps.getProperty(WebConstants.TOKEN));
	InserimentoAttivitaNLAResponse response = null;
	try {
	    response = nlaService.inserimentoAttivita(request, ORMHelper.getToken(), isPecOpRoto);
	    log.debug("inserimentoAttivita: nlaService.inserimentoAttivita({}, {});", request, ORMHelper.getToken());
	    if (response != null) {
		if (response.getDettaglioAttivita() != null) {
		    // BOCCI 2015-09-24 IL PASSA PROT E' GIA' CHIAMATO NEL METODO response = nlaService.inserimentoAttivita(request, ORMHelper.getToken());
		    // NON LO RICHIAMO PIU' QUI 
		    // VERIFICO SOLAMENTE SE I RIFERIMENTI DEL PROTOCOLLO SONO STATI PASSATI O MENO
		    // SE NO CHIAMO LA PROTOCOLLAZIONE
		    // if (!nlaService.passaProt(request.getSportelloMittente(), request.getSportelloDestinatario())) {
		    Integer codiceMovimento = Integer.valueOf(response.getDettaglioAttivita().getIdAttivita());
		    movimentiService.flush();
		    aggiornaSchedeMovimento(codiceMovimento);
		    Movimenti movimento = movimentiService.findById(new PkId(codiceMovimento));
		    boolean aggiornaRiepilogo = false;
		    if (movimento.getTipomovimento() != null && movimento.getTipomovimento().getId() != null) {
			String tm = movimento.getTipomovimento().getId().getTipomovimento();
			Tipimovimento tipim = tipiMovimentoService.findById(new TipimovimentoId(tm));
			if (tipim != null) {
			    aggiornaRiepilogo = BooleanUtils.isTrue(tipim.getFlagAggiornaRiepilogo());
			}
		    }
		    if (log.isDebugEnabled()) {
			log.debug("inserimentoAttivita: prima della protocollazione del movimento {}", movimento.getId());
		    }
		    if (StringUtils.isBlank(movimento.getNumeroprotocollo())) { // SE PROTOCOLLO VUOTO ALLORA CHIAMO LA PROTOCOLLAZIONE
			boolean protocolla = false;
			Verticalizzazioniparametri vpForza = verticalizzazioniService.getVerticalizzazioniparametri(
				WebConstants.VERTICALIZZAZIONE_STC, WebConstants.VERTICALIZZAZIONE_STC_FORZAPROTOCOLLAZIONE);
			if (vpForza != null) {
			    if (StringUtils.defaultString(vpForza.getValore(), "N").equalsIgnoreCase("S")) {
				protocolla = true;
			    }
			}
			String idNodoMitt = request.getSportelloMittente().getIdNodo();
			String idNodoDest = request.getSportelloDestinatario().getIdNodo();
			String idEnteMitt = request.getSportelloMittente().getIdEnte();
			String idEnteDest = request.getSportelloDestinatario().getIdEnte();
			if (!protocolla) {
			    // protocolla = 
			    // se i nodi sono diversi ossia se non sono da modulo a  modulo es: areariservata --> suap =si mentre suap --> commercio no
			    if (!(StringUtils.defaultString(idNodoMitt).equalsIgnoreCase(StringUtils.defaultString(idNodoDest))
				    && StringUtils.defaultString(idEnteMitt).equalsIgnoreCase(StringUtils.defaultString(idEnteDest)))) {
				protocolla = true;
			    }
			}
			if (protocolla) {
			    // verifico se il nodo mittente deve protocollare. es: pordenone edilizia civilia --> verso commercio non deve protocollare in ingresso
			    Verticalizzazioniparametri vpIaNonProtocolla = verticalizzazioniService.getVerticalizzazioniparametri(
				    WebConstants.VERTICALIZZAZIONE_STC, WebConstants.VERTICALIZZAZIONE_STC_LISTA_NODI_IA_NON_PROTOCOLLA);
			    if (vpIaNonProtocolla != null) {
				if (StringUtils.isNotBlank(StringUtils.defaultString(vpIaNonProtocolla.getValore()).trim())) {
				    String cercaIn = StringUtils.defaultString(vpIaNonProtocolla.getValore()).trim();
				    protocolla = !Utilities.verificaPresenzaValoreIn(idNodoMitt, cercaIn);
				}
			    }
			}
			if (protocolla) {
			    // BOCCI 2015-10-22
			    // ultima condizione.. Questa sovrascrive tutte e non permette la protocollazione
			    // vedi http://redmine/redmine/issues/803
			    ValoreParametroType vpt = StcUtils.getCampoDaAltriDati(request,
				    NlaHelperService.ALTRO_DATO_NOTIFICA_ATTIVITA_PRATICA_CREATA_DA_ATTIVITA);
			    if (vpt != null) {
				protocolla = false;
			    }
			}
			// protocolla se forza protocollazione = true
			if (protocolla) {
			    try {
				////////////////////////////////////
				// POPOLA STRUTTURA ArrayOfMittenti
				DatiAnagraficiType amministrazioneMittente = null;
				Istanze entity = istanzeService.findById(new PkId(movimento.getIstanza().getId().getCodice()));
				boolean isProtocollo = new VerticalizzazioneProtocolloAttivoServiceImpl(verticalizzazioniService,
					entity.getComune().getCodicecomune()).isAttiva();
				if (isProtocollo) {
				    log.debug("NLA_WS.GEST_PROT: PROTOCOLLO ATTIVO CERCO LA LISTA DEI NODI");
				    Verticalizzazioniparametri lnmitt = verticalizzazioniService.getVerticalizzazioniparametriPerComune(
					    VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE,
					    VerticalizzazioneProtocolloAttivoServiceImpl.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_LISTA_NODI_SOSTITUISCI_MITTENTI,
					    entity.getComune().getCodicecomune());
				    if (lnmitt != null) {
					log.debug("NLA_WS.GEST_PROT: LA LISTA DEI NODI PRESENTE");
					if (StringUtils.isNotBlank(lnmitt.getValore())) {
					    log.debug("NLA_WS.GEST_PROT: LA LISTA DEI NODI VALORE PARAMETRO: {}", lnmitt.getValore());
					    String idnodomitt = request.getSportelloMittente().getIdNodo();
					    String identemitt = request.getSportelloMittente().getIdEnte();
					    String idsportellomitt = request.getSportelloMittente().getIdSportello();
					    log.debug("NLA_WS.GEST_PROT: LA LISTA DEI NODI VALORI: {},{},{}",
						    new String[] { idnodomitt, identemitt, idsportellomitt });
					    if (StringUtils.isNotBlank(idnodomitt) && StringUtils.isNotBlank(identemitt)
						    && StringUtils.isNotBlank(idsportellomitt)) {
						String codiceMitt = idnodomitt + "_" + identemitt + "_" + idsportellomitt;
						if (lnmitt.getValore().indexOf(codiceMitt) >= 0) {
						    String mezzo = "";
						    String trasmissione = "";
						    Verticalizzazioniparametri mezzoDefault = verticalizzazioniService
							    .getVerticalizzazioniparametriPerComune(
								    VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE,
								    VerticalizzazioneProtocolloAttivoServiceImpl.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_MEZZO_DEFAULT,
								    entity.getComune().getCodicecomune());
						    if (mezzoDefault != null) {
							mezzo = StringUtils.defaultString(mezzoDefault.getValore());
						    }
						    Verticalizzazioniparametri trasDefault = verticalizzazioniService
							    .getVerticalizzazioniparametriPerComune(
								    VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE,
								    VerticalizzazioneProtocolloAttivoServiceImpl.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_MODALITA_TRASMISSIONE_DEFAULT,
								    entity.getComune().getCodicecomune());
						    if (trasDefault != null) {
							trasmissione = StringUtils.defaultString(trasDefault.getValore());
						    }
						    it.gruppoinit.protocollo.schemas.messages.ObjectFactory pfactory = new ObjectFactory();
						    log.debug("NLA_WS.GEST_PROT: CERCO L'AMMINISTRAZIONE CON I RIFERIMENTI: {},{},{}",
							    new String[] { idnodomitt, identemitt, idsportellomitt });
						    int countTuple = amministrazioniService.countAmministrazioniSTC(idnodomitt, identemitt,
							    idsportellomitt);
						    log.debug(
							    "NLA_WS.GEST_PROT: sono presenti {} configurazioni per L'AMMINISTRAZIONE CON I RIFERIMENTI: {},{},{}",
							    new String[] { "" + countTuple, idnodomitt, identemitt, idsportellomitt });
						    Amministrazioni amm = null;
						    amm = amministrazioniService.findAmministrazioneSTC(idnodomitt, identemitt, idsportellomitt,
							    null);
						    if (countTuple != 1) {
							ValoreParametroType vpt = StcUtils.getCampoDaAltriDati(request,
								NlaHelperService.NOTIFICA_ATTIVITA_CODICE_AMMINISTRAZIONE_STC_MITTENTE);
							if (vpt != null) {
							    String codiceAmm = StringUtils.trim(StringUtils.defaultString(vpt.getCodice()));
							    log.debug(
								    "NLA_WS.GEST_PROT: La configurazione trovata nell'altro dato {} ha codice amministrazione {}",
								    new String[] {
									    NlaHelperService.NOTIFICA_ATTIVITA_CODICE_AMMINISTRAZIONE_STC_MITTENTE,
									    codiceAmm });
							    if (Utilities.isInteger(codiceAmm)) {
								amm = amministrazioniService.findById(new PkId(Integer.parseInt(codiceAmm)));
							    }
							}
						    }
						    if (amm != null) {
							log.debug("NLA_WS.GEST_PROT: AMMINISTRAZIONE TROVATA: {},{}",
								new String[] { amm.getId().toString(), amm.getAmministrazione() });
							JAXBElement<DatiAnagraficiType> jDAT = pfactory
								.createDatiAnagraficiType(new DatiAnagraficiType());
							amministrazioneMittente = jDAT.getValue();
							amministrazioneMittente.setCod(String.valueOf(amm.getId().getCodice()));
							amministrazioneMittente.setMezzo(mezzo);
							amministrazioneMittente.setModalitaTrasmissione(trasmissione);
						    }
						}
					    }
					}
				    }
				}
				/////////////////////////////////////
				DatiProtocolloResponseType datiProtocollo = protocollazioneService.protocollaMovimento(movimento,
					ORMHelper.getToken(), amministrazioneMittente);
				if (log.isDebugEnabled()) {
				    log.debug("inserimentoAttivita: Protocollazione del movimento {} effettuata", movimento.getId());
				}
				if (datiProtocollo != null) {
				    response.getDettaglioAttivita().setNumeroProtocolloGenerale(datiProtocollo.getNumeroProtocollo());
				    if (StringUtils.isNotBlank(datiProtocollo.getDataProtocollo())) {
					GregorianCalendar dataProt = Utilities.getDate(datiProtocollo.getDataProtocollo(), null);
					response.getDettaglioAttivita().setDataProtocolloGenerale(Utilities.getXMLGregorianCalendar(dataProt));
				    }
				}
				if (log.isDebugEnabled()) {
				    log.debug("inserimentoAttivita: prima della fascicolazione del movimento {}", movimento.getId());
				}
				protocollazioneService.fascicolaMovimento(ORMHelper.getToken(), movimento);
				if (log.isDebugEnabled()) {
				    log.debug("inserimentoAttivita: Fascicolazione del movimento {} effettuata", movimento.getId());
				}
			    } catch (Exception e) {
				log.warn("inserimentoAttivita: errore durante la chiamata al sistema di protocollo/fascicolo: {}", e.getMessage());
			    }
			}
		    }
		    String codiceIstanzaStr = response.getDettaglioAttivita().getIdPratica();
		    boolean scaricaAllegatiSTC = nlaService.isScaricaAllegatiFisiciPerNodo(request.getSportelloMittente());
		    notificheAsincrone(idcomunealias, connProps, codiceIstanzaStr, scaricaAllegatiSTC,
			    response.getDettaglioAttivita().getIdAttivita(), request.getSportelloMittente(), request.getDatiAttivita().getIdPratica(),
			    request.getDatiAttivita().getIdAttivita());
		    if (aggiornaRiepilogo) {
			try {
			    this.aggiornaRiepilogoIstanza(movimento);
			} catch (Exception e) {
			    log.error("Errore nella chiamata di aggiornamento riepilogo pratica: ", e);
			}
		    }
		}
	    }
	    // ECCEZIONE PER TEST
	    //throw new RuntimeException("Errore per test");
	} catch (Exception e) {
	    log.error(
		    "inserimentoAttivita(): idnodomittente={},identemittente={},idsportellomittente={},idnododestinatario={},identedestinatario={},idsportellodestinatario={}",
		    new Object[] { request.getSportelloMittente().getIdNodo(), request.getSportelloMittente().getIdEnte(),
			    request.getSportelloMittente().getIdSportello(), request.getSportelloDestinatario().getIdNodo(), idcomunealias, software,
			    e });
	    response = new InserimentoAttivitaNLAResponse();
	    ErroreType errore = nlaHelperService.populateErroreType(request.getSportelloDestinatario().getIdNodo(), idcomunealias,
		    request.getSportelloDestinatario().getIdSportello(), "NLAINSATT", e, "inserimentoAttivita");
	    response.getDettaglioErrore().add(errore);
	    try {
		istanzeeventiService.insertEventoBackoffice("ERRORE " + errore.getDescrizione(), CategorieEventiBaseType.STC_INS_ATT.value(),
			ORMHelper.getSoftware());
	    } catch (Exception e1) {
		log.error("Errore durante l'inserimento dell'evento: {}", e.getMessage(), e);
	    }
	    // throw new RuntimeException(e);
	} finally {
	    resetThreadLocalVars();
	}
	return response;
    }

    private void aggiornaSchedeMovimento(Integer codiceMovimento) {

	try {
	    log.debug("Prima di chiamare aggiornaSchedeMovimento");
	    movimentiService.eseguiFormuleDelleSchedeDinamiche(codiceMovimento);
	    log.debug("Fine chiamata aggiornaSchedeMovimento");
	} catch (FunzioneBusinessRemotaException e) {
	    log.error("Errore aggiornaSchedeMovimento {}", e);
	}
    }

    private void aggiornaRiepilogoIstanza(Movimenti movimento) throws Exception {

	Istanze i = movimento.getIstanza();
	if (verticalizzazioniService.isAttivaPerComuneESoftware(VerticalizzazioneQRCodeServiceImpl.VERTICALIZZAZIONE_QRCODE,
		i.getSoftware().getCodice(), i.getComune().getCodicecomune())) {
	    RiepilogoHelper rigeneraRiepilogoHlp = documentiistanzaService.rigeneraRiepilogo(i.getId().getCodice());
	    if (rigeneraRiepilogoHlp != null) {
		documentiistanzaService.updateAggiornaRiepilogo(i, rigeneraRiepilogoHlp.getContent(), rigeneraRiepilogoHlp.getNomeFile());
	    }
	}
    }

    public InserimentoPraticaNLAResponse inserimentoPraticaNLA(InserimentoPraticaNLARequest request) {

	String idcomunealias = request.getSportelloDestinatario().getIdEnte();
	checkSportelloPEC_O_PROTO_Client(request.getSportelloDestinatario(), true);
	Properties connProps = externalDBResolver.getConnectionProperties(idcomunealias);
	setORMHelper(request.getSportelloDestinatario().getIdSportello(), connProps.getProperty(WebConstants.TOKEN));
	InserimentoPraticaNLAResponse response = new InserimentoPraticaNLAResponse();
	try {
	    response = nlaManager.inserimentoPratica(request, connProps.getProperty(WebConstants.TOKEN));
	    if (response != null && response.getDettaglioPratica() != null) {
		String codiceIstanzaStr = StringUtils.defaultString(response.getDettaglioPratica().getIdPratica()).trim();
		if (Utilities.isInteger(codiceIstanzaStr)) {
		    Integer codiceIstanza = Integer.valueOf(codiceIstanzaStr);
		    Istanze istanze = istanzeService.findById(new PkId(codiceIstanza));
		    if (istanze != null) {
			// NON è TRA LE PRATICHE CON ERRORE
			boolean scaricaAllegatiSTC = nlaService.isScaricaAllegatiFisiciPerNodo(request.getSportelloMittente());
			notificheAsincrone(idcomunealias, connProps, codiceIstanzaStr, scaricaAllegatiSTC, null, request.getSportelloMittente(),
				request.getDettaglioPratica().getIdPratica(), null);
		    }
		}
	    }
	} catch (Exception e) {
	    log.error("inserimentoPratica():" + e.getMessage(), e);
	    ErroreType errore = nlaHelperService.populateErroreType(request.getSportelloDestinatario().getIdNodo(), idcomunealias,
		    request.getSportelloDestinatario().getIdSportello(), "NLAINSPRA", e, "inserimentoPratica");
	    response.getDettaglioErrore().add(errore);
	    // throw new RuntimeException(e.getMessage());
	} finally {
	    resetThreadLocalVars();
	}
	return response;
    }

    public RichiestaPraticaNLAResponse richiestaPraticaNLA(RichiestaPraticaNLARequest request) {

	String idcomunealias = request.getSportelloDestinatario().getIdEnte();
	RichiestaPraticaNLAResponse response = null;
	///// 
	// boolean isPecClientMitt = checkSportelloPECClient(request.getSportelloMittente(), false);
	String software = request.getSportelloDestinatario().getIdSportello();
	boolean isPecClientDest = checkSportelloPEC_O_PROTO_Client(request.getSportelloDestinatario(), false);
	if (isPecClientDest) {
	    software = software.substring(0, software.indexOf("#"));
	    // software = software.replaceAll(WebConstants.PEC_CLIENT_IDSPORTELLO_SUFFIX, "");
	    if (log.isDebugEnabled()) {
		log.debug("allegatoBinarioNLA# la chiamata è effettuata dal pec client setto il nuovo software a {}", software);
	    }
	}
	//// 
	Properties connProps = externalDBResolver.getConnectionProperties(idcomunealias);
	setORMHelper(software, connProps.getProperty(WebConstants.TOKEN));
	try {
	    response = nlaService.richiestaPratica(request);
	} catch (Exception e) {
	    log.error("richiestaPratica(): {}", e.getMessage(), e);
	    ErroreType errore = nlaHelperService.populateErroreType(request.getSportelloDestinatario().getIdNodo(), idcomunealias,
		    request.getSportelloDestinatario().getIdSportello(), "NLARICHPRA", e, "richiestaPratica");
	    response = new RichiestaPraticaNLAResponse();
	    response.getDettaglioErrore().add(errore);
	    // throw new RuntimeException(e);
	} finally {
	    resetThreadLocalVars();
	}
	return response;
    }

    public RichiestaPraticheListaNLAResponse richiestaPraticheListaNLA(RichiestaPraticheListaNLARequest request) {

	String idcomunealias = request.getSportelloDestinatario().getIdEnte();
	checkSportelloPEC_O_PROTO_Client(request.getSportelloDestinatario(), true);
	Properties connProps = externalDBResolver.getConnectionProperties(idcomunealias);
	setORMHelper(request.getSportelloDestinatario().getIdSportello(), connProps.getProperty(WebConstants.TOKEN));
	RichiestaPraticheListaNLAResponse response = null;
	try {
	    response = nlaService.richiestaPraticheLista(request);
	} catch (Exception e) {
	    log.error("richiestaPraticheLista(): {}", e.getMessage(), e);
	    ErroreType errore = nlaHelperService.populateErroreType(request.getSportelloDestinatario().getIdNodo(), idcomunealias,
		    request.getSportelloDestinatario().getIdSportello(), "NLARICHPRALISTA", e, "richiestaPraticheLista");
	    response = new RichiestaPraticheListaNLAResponse();
	    response.getDettaglioErrore().add(errore);
	    // throw new RuntimeException(e);
	} finally {
	    resetThreadLocalVars();
	}
	return response;
    }

    public AllegatoBinarioNLAResponse allegatoBinarioNLA(AllegatoBinarioNLARequest request) {

	String idcomunealias = request.getSportelloDestinatario().getIdEnte();
	String software = request.getSportelloDestinatario().getIdSportello();
	boolean isPecOPROTOClient = checkSportelloPEC_O_PROTO_Client(request.getSportelloDestinatario(), false);
	if (isPecOPROTOClient) {
	    software = software.substring(0, software.indexOf("#"));
	    if (log.isDebugEnabled()) {
		log.debug("allegatoBinarioNLA# la chiamata è effettuata dal pec client setto il nuovo software a {}", software);
	    }
	}
	Properties connProps = externalDBResolver.getConnectionProperties(idcomunealias);
	setORMHelper(software, connProps.getProperty(WebConstants.TOKEN));
	AllegatoBinarioNLAResponse response = null;
	try {
	    response = nlaService.richiestaAllegato(request);
	} catch (Exception e) {
	    log.error("allegatoBinario(): {}", e.getMessage(), e);
	    throw new RuntimeException(e);
	} finally {
	    resetThreadLocalVars();
	}
	return response;
    }

    public static void main(String[] args) {

	String software = "SS" + WebConstants.AZIONI_PROTOCOLLO_CLIENT_IDSPORTELLO_SUFFIX;
	software = software.substring(0, software.indexOf("#"));
	System.out.println(software);
    }

    @Override
    public AggiungiDocumentiNLAResponse aggiungiDocumentiNLA(AggiungiDocumentiNLARequest request) {

	String idcomunealias = request.getSportelloDestinatario().getIdEnte();
	checkSportelloPEC_O_PROTO_Client(request.getSportelloDestinatario(), true);
	Properties connProps = externalDBResolver.getConnectionProperties(idcomunealias);
	setORMHelper(request.getSportelloDestinatario().getIdSportello(), connProps.getProperty(WebConstants.TOKEN));
	AggiungiDocumentiNLAResponse response = null;
	try {
	    OggettiBusinessRules oggettiBusinessRules = new OggettiBusinessRules();
	    oggettiBusinessRules.setInsert(true);
	    SigeproBusinessRules.setClassRules(OggettiBusinessRules.class, oggettiBusinessRules);
	    response = nlaService.aggiungiDocumenti(request);
	} catch (Exception e) {
	    log.error("aggiungiDocumentiNLA(): {}", e.getMessage(), e);
	    throw new RuntimeException(e);
	} finally {
	    SigeproBusinessRules.buildDefaultRules();
	    resetThreadLocalVars();
	}
	return response;
    }

    // TODO FIXME C'E' UN METODO UGUALE IN NLA_HELPER_SERVICEIMPL
    private boolean checkSportelloPEC_O_PROTO_Client(SportelloType sportello, boolean isThrowException) {

	if (sportello != null) {
	    if (sportello.getIdSportello().endsWith(WebConstants.PEC_CLIENT_IDSPORTELLO_SUFFIX)) {
		if (isThrowException) {
		    throw new NotImplementedException("metodo non implementato");
		}
		return true;
	    } else if (sportello.getIdSportello().endsWith(WebConstants.AZIONI_PROTOCOLLO_CLIENT_IDSPORTELLO_SUFFIX)) {
		if (isThrowException) {
		    throw new NotImplementedException("metodo non implementato");
		}
		return true;
	    }
	}
	return false;
    }

    public TestNLAResponse testNLA(TestNLARequest request) {

	TestNLAResponse response = new TestNLAResponse();
	response.setNlaXsdVersion(XsdNlaVersion.V_1_13);
	response.setTypesXsdVersion(XsdTypesVersion.V_1_13);
	return response;
    }

    private void notificheAsincrone(String idcomunealias, Properties connProps, String codiceIstanzaStr, boolean scaricaAllegatiSTC,
	    String codiceMovimentoStr, SportelloType sportelloMittente, String idPraticaMittente, String idAttivitaMittente) {

	log.debug("notificheAsincrone: Inizio");
	if (StringUtils.isNotBlank(codiceIstanzaStr)) {
	    Integer codiceIstanza = null;
	    try {
		codiceIstanza = Integer.parseInt(codiceIstanzaStr.trim());
		notificheAutomaticheAsincroneHelper.eseguiTaskPerIstanza(codiceIstanza, idcomunealias, ORMHelper.getIdcomune(),
			ORMHelper.getSoftware(), connProps.getProperty(WebConstants.TOKEN), ORMHelper.getHibernateSFKeyUrl());
	    } catch (Exception e) {
		log.error("Errore nell'esecuzione del task notificheAutomaticheAsincroneHelper per l'istanza {}-{}: {}",
			new Object[] { ORMHelper.getIdcomune(), codiceIstanzaStr, e });
	    }
	    if (scaricaAllegatiSTC) {
		try {
		    downloadFileSTCAsincronoHelper.eseguiTaskPerIstanza(codiceIstanza, idcomunealias, ORMHelper.getIdcomune(),
			    ORMHelper.getSoftware(), connProps.getProperty(WebConstants.TOKEN), ORMHelper.getHibernateSFKeyUrl(), sportelloMittente,
			    idPraticaMittente, null);
		} catch (Exception e) {
		    log.error("Errore nell'esecuzione del task downloadFileSTCAsincronoHelper.eseguiTaskPerIstanza per l'istanza {}-{}: {}",
			    new Object[] { ORMHelper.getIdcomune(), codiceIstanzaStr, e });
		}
	    } else {
		//EventoDocumentoIstanzaStcDisponibile
		log.debug("notificheAsincrone - EventoDocumentoIstanzaStcDisponibile");
		applicaQRCodeService.applicaQRCode(codiceIstanza, null, true);
		//this.eventPublisher.publish(new EventoDocumentoIstanzaStcDisponibile(codiceIstanza));
	    }
	}
	if (StringUtils.isNotBlank(codiceMovimentoStr)) {
	    if (scaricaAllegatiSTC) {
		Integer codiceMovimentoI = null;
		try {
		    codiceMovimentoI = Integer.parseInt(codiceMovimentoStr.trim());
		    downloadFileSTCAsincronoHelper.eseguiTaskPerMovimento(codiceMovimentoI, idcomunealias, ORMHelper.getIdcomune(),
			    ORMHelper.getSoftware(), connProps.getProperty(WebConstants.TOKEN), ORMHelper.getHibernateSFKeyUrl(), sportelloMittente,
			    idPraticaMittente, idAttivitaMittente);
		} catch (Exception e) {
		    log.error("Errore nell'esecuzione del task downloadFileSTCAsincronoHelper.eseguiTaskPerMovimento per il movimento {}-{}: {}",
			    new Object[] { ORMHelper.getIdcomune(), codiceMovimentoStr, e });
		}
	    } else {
		//EventoDocumentoMovimentoStcDisponibile
		log.debug("notificheAsincrone - EventoDocumentoMovimentoStcDisponibile");
		applicaQRCodeService.applicaQRCode(Integer.parseInt(codiceMovimentoStr.trim()), null, false);
		//this.eventPublisher.publish(new EventoDocumentoMovimentoStcDisponibile(Integer.parseInt(codiceMovimentoStr.trim())));
	    }
	}
	log.debug("notificheAsincrone: Fine");
    }
}
