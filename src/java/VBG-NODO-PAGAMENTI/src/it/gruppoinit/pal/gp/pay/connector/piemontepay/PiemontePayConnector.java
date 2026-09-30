/**
 * 
 */
package it.gruppoinit.pal.gp.pay.connector.piemontepay;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.xml.ws.BindingProvider;

import org.apache.commons.lang.StringUtils;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor;
import org.apache.wss4j.dom.WSConstants;
import org.apache.wss4j.dom.handler.WSHandlerConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import it.gruppoinit.pal.gp.core.exceptions.ValidazionePosizioniDebitorieException;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.pal.gp.pay.command.PosizioniDebitorieCommand;
import it.gruppoinit.pal.gp.pay.connector.AbstractPayConnector;
import it.gruppoinit.pal.gp.pay.connector.IPayConnector;
import it.gruppoinit.pal.gp.pay.connector.piemontepay.service.PiemontepayService.EsitiPPAY;
import it.gruppoinit.pal.gp.pay.connector.piemontepay.ws.client.Enti2EPaywsoSecureProxyPortType;
import it.gruppoinit.pal.gp.pay.connector.piemontepay.ws.client.schema.AggiornaPosizioniDebitorieRequest;
import it.gruppoinit.pal.gp.pay.connector.piemontepay.ws.client.schema.ComponenteImportoType;
import it.gruppoinit.pal.gp.pay.connector.piemontepay.ws.client.schema.ElencoPosizioniDaAggiornareType;
import it.gruppoinit.pal.gp.pay.connector.piemontepay.ws.client.schema.ElencoPosizioniDaAggiornareType.PosizioniDaAggiornare;
import it.gruppoinit.pal.gp.pay.connector.piemontepay.ws.client.schema.InserisciListaDiCaricoRequest;
import it.gruppoinit.pal.gp.pay.connector.piemontepay.ws.client.schema.ListaDiCarico;
import it.gruppoinit.pal.gp.pay.connector.piemontepay.ws.client.schema.ListaDiCarico.PosizioniDaInserire;
import it.gruppoinit.pal.gp.pay.connector.piemontepay.ws.client.schema.PosizioneDaAggiornareType;
import it.gruppoinit.pal.gp.pay.connector.piemontepay.ws.client.schema.PosizioneDaInserireType;
import it.gruppoinit.pal.gp.pay.connector.piemontepay.ws.client.schema.PosizioneDaInserireType.ComponentiImporto;
import it.gruppoinit.pal.gp.pay.connector.piemontepay.ws.client.schema.TestataAggiornaPosizioniDebitorie;
import it.gruppoinit.pal.gp.pay.connector.piemontepay.ws.client.schema.TestataListaCarico;
import it.gruppoinit.pal.gp.pay.connector.piemontepay.ws.common.schema.PersonaFisicaType;
import it.gruppoinit.pal.gp.pay.connector.piemontepay.ws.common.schema.PersonaGiuridicaType;
import it.gruppoinit.pal.gp.pay.connector.piemontepay.ws.common.schema.ResponseType;
import it.gruppoinit.pal.gp.pay.connector.piemontepay.ws.common.schema.ResultType;
import it.gruppoinit.pal.gp.pay.connector.piemontepay.ws.common.schema.SoggettoType;
import it.gruppoinit.pal.gp.pay.connector.piemontepay.ws.common.schema.TipoAggiornamentoType;
import it.gruppoinit.pal.gp.pay.domain.PayConnectorWsEndpoint;
import it.gruppoinit.pal.gp.pay.domain.PayDettaglioImporti;
import it.gruppoinit.pal.gp.pay.domain.PayPosizioniDebitorie;
import it.gruppoinit.pal.gp.pay.domain.PayRegistrazioniContabili;
import it.gruppoinit.pal.gp.pay.domain.PaySessioniPagamento;
import it.gruppoinit.pal.gp.pay.domain.PaySoggettiDebitori;
import it.gruppoinit.pal.gp.pay.domain.PayStatoPagamenti;
import it.gruppoinit.pal.gp.pay.domain.PayStatoPagamenti.StatiPagamento;
import it.gruppoinit.pal.gp.pay.exception.PayConfigurationException;
import it.gruppoinit.pal.gp.pay.exception.PayException;
import it.gruppoinit.pal.gp.pay.exception.PayInvalidRequestException;
import it.gruppoinit.pal.gp.pay.parameters.IParameter;
import it.gruppoinit.pal.gp.pay.service.PayPosizioniDebitorieService;
import it.gruppoinit.pal.gp.pay.service.PaySessioniPagamentoService;
import it.gruppoinit.pal.gp.pay.service.PayStatoPagamentiService;
import it.gruppoinit.pal.gp.pay.service.PosizioniDebitorieCommandService;
import it.gruppoinit.pal.gp.pay.service.helper.IUVHelper;
import it.gruppoinit.pal.gp.pay.service.helper.ParametriConnettoreHelper;
import it.gruppoinit.pal.gp.pay.service.helper.PayConfigurationHelper;
import it.gruppoinit.pal.gp.pay.service.helper.PopolamentoDatiHelper;
import it.gruppoinit.pal.gp.pay.ws.schema.AttivaSessionePagamentoResponseType;
import it.gruppoinit.pal.gp.pay.ws.schema.ElencoPosizioniDebitorieEsitoType;
import it.gruppoinit.pal.gp.pay.ws.schema.EsitoOperazionePosizioneDebitoriaType;
import it.gruppoinit.pal.gp.pay.ws.schema.FormParametersType;
import it.gruppoinit.pal.gp.pay.ws.schema.HttpMethodType;
import it.gruppoinit.pal.gp.pay.ws.schema.ImportoPagamentoType;
import it.gruppoinit.pal.gp.pay.ws.schema.PosizioneDebitoriaType;
import it.gruppoinit.pal.gp.pay.ws.schema.RegistrazioneContabileType;
import it.gruppoinit.pal.gp.pay.ws.schema.StatoPagamentoType;
import it.gruppoinit.sigeprosecurity.ws.SecurityPwdCallBackHandler;

/**
 * Connettore di pagamento per il PSP PiemontePAY
 * 
 * @author francol
 *
 */
public class PiemontePayConnector extends AbstractPayConnector implements IPayConnector {

    private static final Logger log = LoggerFactory.getLogger(PiemontePayConnector.class);
    //@Autowired
    private Enti2EPaywsoSecureProxyPortType ppayServicePort;
    @Autowired
    private PayPosizioniDebitorieService payPosizioniDebitorieService;
    @Autowired
    private PayStatoPagamentiService payStatoPagamentiService;
    @Autowired
    private PaySessioniPagamentoService paySessioniPagamentoService;
    @Autowired
    private PosizioniDebitorieCommandService posizioniDebitorieCommandService;

    @Override
    public ElencoPosizioniDebitorieEsitoType registraPosizioniDebitorie(PosizioniDebitorieCommand datiRegistrazioniCommand) throws PayException {

	ElencoPosizioniDebitorieEsitoType results = new ElencoPosizioniDebitorieEsitoType();
	Enti2EPaywsoSecureProxyPortType ppayWs = this.getPiemontepayWsPort2();
	InserisciListaDiCaricoRequest insertReq = new InserisciListaDiCaricoRequest();
	TestataListaCarico testata = new TestataListaCarico();
	testata.setCFEnteCreditore(PayConfigurationHelper.getProfiloEnteCreditore().getCfCodiceProfiloPSP());
	testata.setIdMessaggio(UUID.randomUUID().toString());
	BigDecimal totImporto = BigDecimal.ZERO;
	int numPosizioni = 0;
	//il codice versamento deve essere lo stesso per tutte le registrazioni che si trasmettono ==> valido che tutte le registrazioni contabili abbiano la stessa causale di registrazione
	Date dataScadenza = null;
	ListaDiCarico listaDiCarico = new ListaDiCarico();
	PosizioniDaInserire posizioni = new PosizioniDaInserire();
	listaDiCarico.setPosizioniDaInserire(posizioni);
	insertReq.setTestata(testata);
	insertReq.setListaDiCarico(listaDiCarico);
	String codiceVersamento = posizioniDebitorieCommandService.findCodiceVersamentoFromCommand(datiRegistrazioniCommand);
	for (PayRegistrazioniContabili payRegCont : datiRegistrazioniCommand.getRegistrazioniPosizioni()) {
	    for (PayPosizioniDebitorie payPosDeb : payRegCont.getPosizioniDebitorie()) {
		if (dataScadenza == null) {
		    dataScadenza = payPosDeb.getDataScadenza();
		} else if (!payPosDeb.getDataScadenza().equals(dataScadenza)) {
		    throw new PayInvalidRequestException("tutte le registrazioni di pagamento devono avere la stessa data scadenza");
		}
		PosizioneDaInserireType pos = new PosizioneDaInserireType();
		payPosDeb.setIdPosizionePsp(this.generaIdPosizioneDebitoria(payPosDeb));
		pos.setIdPosizioneDebitoria(payPosDeb.getIdPosizionePsp());
		//aggiornamento della posizione debitoria con l'id passato al PSP
		this.payPosizioniDebitorieService.aggiornaPosizioneDebitoria(payPosDeb, null, null);
		pos.setAnnoRiferimento(payRegCont.getAnno());
		if (payPosDeb.getDataScadenza() != null) {
		    pos.setDataScadenza(Utilities.getXMLGregorianCalendar(payPosDeb.getDataScadenza()));
		}
		if (payPosDeb.getDataRegistrazione() != null) {
		    pos.setDataInizioValidita(Utilities.getXMLGregorianCalendar(payPosDeb.getDataRegistrazione()));
		}
		//TODO calcolare la data fine validità
		pos.setDescrizioneRata("rata n." + payPosDeb.getNumRata());
		BigDecimal totImportoPosizione = BigDecimal.ZERO;
		//componenti importo
		ComponentiImporto ci = new ComponentiImporto();
		for (PayDettaglioImporti voceImporto : payPosDeb.getDettagliImporto()) {
		    ComponenteImportoType cit = new ComponenteImportoType();
		    cit.setCausaleDescrittiva(voceImporto.getDescCausale());
		    cit.setImporto(voceImporto.getImporto());
		    if (StringUtils.isNotBlank(voceImporto.getDatiRiscossione())) {
			cit.setDatiSpecificiRiscossione(voceImporto.getDatiRiscossione());
		    }
		    if (voceImporto.getAnnoAccertamento() != null) {
			cit.setAnnoAccertamento(voceImporto.getAnnoAccertamento());
		    }
		    if (StringUtils.isNotBlank(voceImporto.getNumeroAccertamento())) {
			cit.setNumeroAccertamento(voceImporto.getNumeroAccertamento());
		    }
		    ci.getComponenteImporto().add(cit);
		    totImportoPosizione = totImportoPosizione.add(voceImporto.getImporto());
		}
		pos.setComponentiImporto(ci);
		pos.setImportoTotale(totImportoPosizione);
		totImporto = totImporto.add(totImportoPosizione);
		numPosizioni++;
		//soggetto debitore
		pos.setSoggettoPagatore(popolateSoggettoDebitore(payPosDeb.getSoggettoDebitore()));
		pos.setDescrizioneCausaleVersamento(Utilities.trimToLength(payRegCont.getDescrizione(), 100));
		pos.setNotePerIlPagatore(Utilities.trimToLength(payRegCont.getNote(), 1000));
		posizioni.getPosizioneDaInserire().add(pos);
		//predispongo gli esiti OK da modificare in caso di errore nella chiamata al servizio
		EsitoOperazionePosizioneDebitoriaType esitoPos = new EsitoOperazionePosizioneDebitoriaType();
		esitoPos.setEsito(true);
		esitoPos.setStato(StatoPagamentoType.TRASMESSO_A_PSP);
		esitoPos.setMessaggio(StatiPagamento.TRASMESSO_A_PSP.description());
		esitoPos.setIdPosizione(BigInteger.valueOf(payPosDeb.getId().getCodice()));
		if (payRegCont.getId() != null && payRegCont.getId().getCodice() != null) {
		    esitoPos.setIdRegistrazioneContabile(BigInteger.valueOf(payRegCont.getId().getCodice()));
		}
		results.getEsitoPosizione().add(esitoPos);
	    }
	}
	testata.setCodiceVersamento(codiceVersamento);
	testata.setImportoTotaleListaDiCarico(totImporto);
	testata.setNumeroPosizioniDebitorie(numPosizioni);
	ResultType ppayResult = null;
	try {
	    ResponseType response = ppayWs.inserisciListaDiCarico(insertReq);
	    ppayResult = response.getResult();
	} catch (Exception e) {
	    log.error("errore nell'invocazione del servizio inserisciListaDiCarico: ", e);
	    ppayResult = new ResultType();
	    ppayResult.setCodice(EsitiPPAY.CODE_100.errorCode());
	    ppayResult.setMessaggio(e.toString());
	}
	EsitiPPAY esito = EsitiPPAY.fromValue(ppayResult.getCodice());
	if (esito != EsitiPPAY.CODE_000 && esito != EsitiPPAY.CODE_050) {
	    //gestione errore restituito dalla chiamata al servizio
	    for (EsitoOperazionePosizioneDebitoriaType esitoNodo : results.getEsitoPosizione()) {
		esitoNodo.setEsito(false);
		esitoNodo.setStato(StatoPagamentoType.CON_ERRORE);
		esitoNodo.setCodiceErrore(esito.errorCode());
		esitoNodo.setMessaggio(ppayResult.getMessaggio());
	    }
	}
	return results;
    }

    @Override
    public ElencoPosizioniDebitorieEsitoType annullaPosizioniDebitorie(PosizioniDebitorieCommand datiRegistrazioniCommand, boolean pagatoOffline)
	    throws PayInvalidRequestException, PayConfigurationException {

	ElencoPosizioniDebitorieEsitoType results = new ElencoPosizioniDebitorieEsitoType();
	List<PayRegistrazioniContabili> registrazioni = datiRegistrazioniCommand.getRegistrazioniPosizioni();
	Enti2EPaywsoSecureProxyPortType ppayWs = this.getPiemontepayWsPort2();
	AggiornaPosizioniDebitorieRequest deleteReq = new AggiornaPosizioniDebitorieRequest();
	TestataAggiornaPosizioniDebitorie testata = new TestataAggiornaPosizioniDebitorie();
	testata.setIdMessaggio(UUID.randomUUID().toString());
	testata.setCFEnteCreditore(PayConfigurationHelper.getProfiloEnteCreditore().getCfCodiceProfiloPSP());
	ElencoPosizioniDaAggiornareType elencoPos = new ElencoPosizioniDaAggiornareType();
	PosizioniDaAggiornare pda = new PosizioniDaAggiornare();
	elencoPos.setPosizioniDaAggiornare(pda);
	List<PosizioneDaAggiornareType> posizioni = pda.getPosizioneDaAggiornare();
	deleteReq.setElencoPosizioniDaAggiornare(elencoPos);
	int numPosizioni = 0;
	String codiceVersamento = posizioniDebitorieCommandService.findCodiceVersamentoFromCommand(datiRegistrazioniCommand);
	for (PayRegistrazioniContabili reg : registrazioni) {
	    for (PayPosizioniDebitorie pos : reg.getPosizioniDebitorie()) {
		PayStatoPagamenti actualStatus = this.payStatoPagamentiService.getStatoPosizioneDebitoria(pos);
		StatoPagamentoType statusVal = null;
		if (actualStatus != null) {
		    try {
			statusVal = StatoPagamentoType.fromValue(actualStatus.getStato());
		    } catch (Exception e) {
			log.error("stato pagamento non valido: " + actualStatus.getStato());
		    }
		}
		EsitoOperazionePosizioneDebitoriaType esitoPos = new EsitoOperazionePosizioneDebitoriaType();
		esitoPos.setEsito(true);
		esitoPos.setStato(statusVal);
		esitoPos.setMessaggio(statusVal != null ? StatiPagamento.fromValue(statusVal.name()).description()
			: "stato della posizione debitoria non definito");
		PopolamentoDatiHelper.completaDatiDaPosizioneDebitoria(esitoPos, pos,
			payPosizioniDebitorieService.findRiferimentiClientByPosizioneDebitoria(pos.getId().getCodice()));
		results.getEsitoPosizione().add(esitoPos);
		PosizioneDaAggiornareType posda = new PosizioneDaAggiornareType();
		posda.setTipoAggiornamento(TipoAggiornamentoType.ANNULLAMENTO);
		posda.setIdPosizioneDebitoria(pos.getIdPosizionePsp());
		posizioni.add(posda);
		numPosizioni++;
	    }
	}
	testata.setCodiceVersamento(codiceVersamento);
	testata.setNumeroPosizioniDebitorie(numPosizioni);
	deleteReq.setTestata(testata);
	ResultType ppayResult = null;
	try {
	    ResponseType response = ppayWs.aggiornaPosizioniDebitorie(deleteReq);
	    ppayResult = response.getResult();
	} catch (Exception e) {
	    log.error("errore nell'invocazione del servizio annullaPosizioniDebitorie: ", e);
	    ppayResult = new ResultType();
	    ppayResult.setCodice(EsitiPPAY.CODE_100.errorCode());
	    ppayResult.setMessaggio(e.toString());
	}
	EsitiPPAY esito = decodificaEsito(ppayResult.getCodice());
	//gestione errore restituito dalla chiamata al servizio
	for (EsitoOperazionePosizioneDebitoriaType esitoNodo : results.getEsitoPosizione()) {
	    if (esito != EsitiPPAY.CODE_000 && esito != EsitiPPAY.CODE_050) {
		esitoNodo.setEsito(false);
		esitoNodo.setCodiceErrore(ppayResult.getCodice());
		esitoNodo.setMessaggio(ppayResult.getMessaggio());
	    } else {
		esitoNodo.setEsito(true);
		StatoPagamentoType newStatus = pagatoOffline ? StatoPagamentoType.PAGATO_OFFLINE_DA_ANNULLARE
			: StatoPagamentoType.ANNULLAMENTO_RICHIESTO;
		StatiPagamento statusDesc = pagatoOffline ? StatiPagamento.PAGATO_OFFLINE_DA_ANNULLARE : StatiPagamento.ANNULLAMENTO_RICHIESTO;
		esitoNodo.setStato(newStatus);
		esitoNodo.setMessaggio(statusDesc.description());
	    }
	}
	return results;
    }

    private Enti2EPaywsoSecureProxyPortType getPiemontepayWsPort2() {

	JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
	factory.setServiceClass(Enti2EPaywsoSecureProxyPortType.class);
	PayConnectorWsEndpoint caricaPosWs = this.getWsCaricamentoConfig();
	//codice per compatibilità con vecchia modalita di configurazione degli endpoint dei servizi da eliminare dopo che test ok sul nuovo
	if (caricaPosWs == null) {
	    caricaPosWs = new PayConnectorWsEndpoint();
	    caricaPosWs.setEndpointUrl(this.getWsEndpointUrl());
	    caricaPosWs.setUtente(this.getWsUser());
	    caricaPosWs.setPassword(this.getWsPassword());
	    caricaPosWs.setTimeout(this.getWsTimeout());
	}
	factory.setAddress(caricaPosWs.getEndpointUrl());// <- must be /soap there, otherwise 404
	Enti2EPaywsoSecureProxyPortType info = (Enti2EPaywsoSecureProxyPortType) factory.create();
	BindingProvider bp = (BindingProvider) info;
	bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, caricaPosWs.getEndpointUrl());
	Client client = ClientProxy.getClient(info);
	Endpoint cxfEndpoint = client.getEndpoint();
	Map<String, Object> outProps = new HashMap<String, Object>();
	outProps.put(WSHandlerConstants.ACTION, WSHandlerConstants.USERNAME_TOKEN + " " + WSHandlerConstants.TIMESTAMP);
	outProps.put(WSHandlerConstants.USER, caricaPosWs.getUtente());
	outProps.put(WSHandlerConstants.PASSWORD_TYPE, WSConstants.PW_TEXT);
	SecurityPwdCallBackHandler pwdCallback = new SecurityPwdCallBackHandler(caricaPosWs.getUtente(), caricaPosWs.getPassword());
	outProps.put(WSHandlerConstants.PW_CALLBACK_REF, pwdCallback);
	WSS4JOutInterceptor wssOut = new WSS4JOutInterceptor(outProps);
	cxfEndpoint.getOutInterceptors().add(wssOut);
	if (caricaPosWs.getTimeout() != null) {
	    HTTPConduit conduit = (HTTPConduit) client.getConduit();
	    HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
	    httpClientPolicy.setConnectionTimeout(caricaPosWs.getTimeout());
	    httpClientPolicy.setReceiveTimeout(caricaPosWs.getTimeout());
	    conduit.setClient(httpClientPolicy);
	}
	return info;
    }

    private SoggettoType popolateSoggettoDebitore(PaySoggettiDebitori paySogg) {

	SoggettoType retSogg = null;
	if (paySogg != null) {
	    retSogg = new SoggettoType();
	    retSogg.setCAP(Utilities.trimToLength(paySogg.getCap(), 16));
	    retSogg.setCivico(Utilities.trimToLength(paySogg.getCivico(), 16));
	    retSogg.setEMail(Utilities.trimToLength(paySogg.getEmail(), 256));
	    retSogg.setIdentificativoUnivocoFiscale(paySogg.getCfPi());
	    retSogg.setIndirizzo(Utilities.trimToLength(paySogg.getVia(), 70));
	    retSogg.setLocalita(Utilities.trimToLength(paySogg.getLocalita(), 35));
	    retSogg.setNazione(decodificaNazione(paySogg.getStato()));
	    if (StringUtils.isBlank(paySogg.getCognome())) {
		PersonaGiuridicaType pg = new PersonaGiuridicaType();
		pg.setRagioneSociale(Utilities.trimToLength(paySogg.getNome(), 70));
		retSogg.setPersonaGiuridica(pg);
	    } else {
		PersonaFisicaType pf = new PersonaFisicaType();
		pf.setCognome(Utilities.trimToLength(paySogg.getCognome(), 70));
		pf.setNome(Utilities.trimToLength(paySogg.getNome(), 70));
		retSogg.setPersonaFisica(pf);
	    }
	    retSogg.setProvincia(Utilities.trimToLength(paySogg.getProvincia(), 35));
	}
	return retSogg;
    }

    @Override
    protected String generaDigestSessionePagamento(PayPosizioniDebitorie payPos, String idSessionePagamento) throws PayException {

	return new CommonPiemontePayUtils().generaDigestSessionePagamento(payPos, idSessionePagamento);
    }

    @Override
    protected FormParametersType generaParametriSessionePagamento(PayPosizioniDebitorie posDeb, String idSessione, String digest)
	    throws PayException {

	return new CommonPiemontePayUtils().generaParametriSessionePagamento(posDeb, idSessione, digest);
    }

    /**
     * l'id messagio in PPAY deve essere lungo al massimo 35 caratteri l'UUID del nodo pagamenti è di 36 perciò si
     * eliminano i trattini separatori
     */
    @Override
    public String generaIdMessaggio() {

	String genId = super.generaIdMessaggio();
	genId = genId.replace("-", "");
	return genId;
    }

    private String decodificaNazione(String input) {

	if (input != null && input.length() > 2) {
	    //TODO decodificare il nome della nazione restituendo il codice
	    if (input.equalsIgnoreCase("italia")) {
		input = "IT";
	    } else {
		input = null;
	    }
	}
	return input;
    }

    private EsitiPPAY decodificaEsito(String codiceEsito) {

	EsitiPPAY retEsito = null;
	if (StringUtils.isNotBlank(codiceEsito)) {
	    try {
		retEsito = EsitiPPAY.fromValue(codiceEsito);
	    } catch (IllegalArgumentException e) {
		retEsito = EsitiPPAY.CODE_999;
	    }
	}
	return retEsito;
    }

    @Override
    /**
     * L'URL contiene la seguente query string
     * esitoSessionePagamento/toppay?idPagamento=CSI-T_L219TS_859070&iuv=20243392783254826&descEsito=FALLITO&codEsito=100&source=pagamentoIUV
     */
    public PaySessioniPagamento gestisciEsitoSessione(Map<String, String[]> reqParams) {

	String[] idPagamento = reqParams.get("idPagamento");
	String[] codEsiti = reqParams.get("codEsito");
	String[] descEsiti = reqParams.get("descEsito");
	String[] iuv = reqParams.get("iuv");
	Boolean esitoOk = null;
	if (log.isDebugEnabled()) {
	    for (String s : idPagamento) {
		log.debug("idPagamento {}", s);
	    }
	    for (String s : descEsiti) {
		log.debug("descEsiti {}", s);
	    }
	    for (String s : codEsiti) {
		log.debug("codEsiti {}", s);
	    }
	    for (String s : iuv) {
		log.debug("iuv {}", s);
	    }
	}
	if (codEsiti != null && codEsiti.length > 0) {
	    String esitoStr = codEsiti[0];
	    log.debug("esitoStr {}", esitoStr);
	    esitoOk = esitoStr.equals(EsitiPPAY.CODE_000.errorCode());
	}
	List<PaySessioniPagamento> sessioni = new ArrayList<>();
	if (idPagamento != null && idPagamento.length > 0) {
	    String idPosizionePsp = idPagamento[0];
	    log.debug("idPosizionePsp {}", idPosizionePsp);
	    PayPosizioniDebitorie posDebs = payPosizioniDebitorieService.findByIdPosizionePSP(idPosizionePsp);
	    if (posDebs != null) {
		sessioni = this.paySessioniPagamentoService.findSessioniPerPosizioneDebitoria(posDebs.getId().getCodice());
		log.debug("sessioni {}", sessioni.size());
	    }
	}
	for (PaySessioniPagamento sessione : sessioni) {
	    if (log.isDebugEnabled()) {
		log.debug("sessione codice {}, esito {}", sessione.getId().getCodice(), sessione.getEsito());
	    }
	    sessione.setEsito(esitoOk);
	    this.paySessioniPagamentoService.update(sessione);
	}
	if (sessioni.isEmpty()) {
	    log.warn("sessione non trovata");
	    for (String s : idPagamento) {
		log.warn("idPagamento {}", s);
	    }
	    for (String s : descEsiti) {
		log.warn("descEsiti {}", s);
	    }
	    for (String s : codEsiti) {
		log.warn("codEsiti {}", s);
	    }
	    for (String s : iuv) {
		log.warn("iuv {}", s);
	    }
	    return null;
	}
	return sessioni.get(0);
    }

    @Override
    public void validateRichiestaInserimentoPosizioniDebitorie(RegistrazioneContabileType registrazioneContabile)
	    throws ValidazionePosizioniDebitorieException {

	List<PosizioneDebitoriaType> rate = registrazioneContabile.getRate().getRata();
	for (int i = 0; i < rate.size(); i++) {
	    PosizioneDebitoriaType rata = rate.get(i);
	    String ref = PopolamentoDatiHelper.getRiferimentoPosizioneDebitoria(rata, registrazioneContabile,
		    rata.getNumeroRata() != null ? rata.getNumeroRata().intValue() : i + 1);
	    //numero accertamento
	    List<ImportoPagamentoType> ci = rata.getImporto().getComponenteImporto();
	    for (ImportoPagamentoType importo : ci) {
		if (StringUtils.isBlank(importo.getNumeroAccertamento())) {
		    throw new ValidazionePosizioniDebitorieException(
			    "Numero accertamento della componente importo mancante. Riferimento posizione debitoria: " + ref);
		}
	    }
	}
    }

    @Override
    public boolean supportaAttivaSessionePagamento() {

	return true;
    }

    @Override
    public boolean supportaModificaDataScadenza() {

	return false;
    }

    @Override
    public AttivaSessionePagamentoResponseType attivaSessionePagamento(PayPosizioniDebitorie payPos) throws PayException {

	log.debug("uso il servizio della classe astratta per la sessione di pagamento ");
	AttivaSessionePagamentoResponseType response = super.attivaSessionePagamento(payPos);
	response.setHttpMethodRequired(HttpMethodType.POST);
	return response;
    }

    @Override
    public boolean supportaMolteCausaliRaggruppate() {

	return false;
    }

    @Override
    public List<IParameter> getListaParametriRichiesti() {

	return ParametriConnettoreHelper.getMappaParametriConnettore(this.getClass().getName());
    }

    @Override
    public IUVHelper generaIUV(PayPosizioniDebitorie pos, String identificativoCausalePerCalcolo) {

	return null;
    }
}
