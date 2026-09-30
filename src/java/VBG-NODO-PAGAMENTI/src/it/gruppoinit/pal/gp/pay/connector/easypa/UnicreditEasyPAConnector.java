package it.gruppoinit.pal.gp.pay.connector.easypa;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.util.ByteArrayDataSource;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import it.gov.digitpa.schemas._2011.pagamenti.v_6_2_0.CtRicevutaTelematica;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.exceptions.ValidazionePosizioniDebitorieException;
import it.gruppoinit.pal.gp.core.utils.IOAuth2Params;
import it.gruppoinit.pal.gp.core.utils.OAuth2SecurityRestTokenManager;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.pal.gp.pay.command.PosizioniDebitorieCommand;
import it.gruppoinit.pal.gp.pay.command.RichiestaSuListaPosizioniCommand;
import it.gruppoinit.pal.gp.pay.connector.AbstractPayConnector;
import it.gruppoinit.pal.gp.pay.connector.IPayConnector;
import it.gruppoinit.pal.gp.pay.connector.easypa.ws.gestoreposizioni.schema.GestorePosizioni;
import it.gruppoinit.pal.gp.pay.connector.easypa.ws.gestoreposizioni.schema.InserimentoPosizioneInputType;
import it.gruppoinit.pal.gp.pay.connector.easypa.ws.gestoreposizioni.schema.InserimentoPosizioneOutputType;
import it.gruppoinit.pal.gp.pay.connector.easypa.ws.gestoreposizioni.schema.InserimentoPosizioneRequest;
import it.gruppoinit.pal.gp.pay.connector.easypa.ws.gestoreposizioni.schema.InserimentoPosizioneResponse;
import it.gruppoinit.pal.gp.pay.connector.easypa.ws.gestoreposizioni.schema.head.GestorePosizioniHeader;
import it.gruppoinit.pal.gp.pay.connector.easypa.ws.rest.client.EasyPaClient;
import it.gruppoinit.pal.gp.pay.connector.easypa.ws.rest.modellazione.DettaglioPosizioneResponse;
import it.gruppoinit.pal.gp.pay.connector.easypa.ws.rest.modellazione.RispostaRicevutaTelematica;
import it.gruppoinit.pal.gp.pay.connector.easypa.ws.rest.security.EasyPaSecurityRequestParams;
import it.gruppoinit.pal.gp.pay.connector.easypa.ws.schema.DatiSingoloVersamentoListaType;
import it.gruppoinit.pal.gp.pay.connector.easypa.ws.schema.IuvListaType;
import it.gruppoinit.pal.gp.pay.connector.easypa.ws.schema.PaInviaCarrelloPosizioni;
import it.gruppoinit.pal.gp.pay.connector.easypa.ws.schema.PaInviaCarrelloPosizioniListaType;
import it.gruppoinit.pal.gp.pay.connector.easypa.ws.schema.PaInviaCarrelloPosizioniOutputType;
import it.gruppoinit.pal.gp.pay.connector.easypa.ws.schema.PaInviaCarrelloPosizioniRequest;
import it.gruppoinit.pal.gp.pay.connector.easypa.ws.schema.PaInviaCarrelloPosizioniResponse;
import it.gruppoinit.pal.gp.pay.connector.easypa.ws.schema.head.PaInviaCarrelloPosizioniHeader;
import it.gruppoinit.pal.gp.pay.domain.PayConnectorConfigParams.ConfigParamNames;
import it.gruppoinit.pal.gp.pay.domain.PayConnectorWsEndpoint;
import it.gruppoinit.pal.gp.pay.domain.PayDettaglioImporti;
import it.gruppoinit.pal.gp.pay.domain.PayPosizioniDebitorie;
import it.gruppoinit.pal.gp.pay.domain.PayProfiliEntiCreditori;
import it.gruppoinit.pal.gp.pay.domain.PayRegistrazioniContabili;
import it.gruppoinit.pal.gp.pay.domain.PaySessioniPagamento;
import it.gruppoinit.pal.gp.pay.domain.PaySoggettiDebitori;
import it.gruppoinit.pal.gp.pay.domain.PayStatoPagamenti;
import it.gruppoinit.pal.gp.pay.domain.PayStatoPagamenti.StatiPagamento;
import it.gruppoinit.pal.gp.pay.exception.PayConfigurationException;
import it.gruppoinit.pal.gp.pay.exception.PayException;
import it.gruppoinit.pal.gp.pay.parameters.IParameter;
import it.gruppoinit.pal.gp.pay.parameters.ParametroInviaSoloPosizioniDiSoggettiConMail;
import it.gruppoinit.pal.gp.pay.parameters.ParametroTipoRiferimentoCreditore;
import it.gruppoinit.pal.gp.pay.service.AvvisiPagoPAService;
import it.gruppoinit.pal.gp.pay.service.PayConnectorConfigValuesService;
import it.gruppoinit.pal.gp.pay.service.PayPosizioniDebitorieService;
import it.gruppoinit.pal.gp.pay.service.PaySessioniPagamentoService;
import it.gruppoinit.pal.gp.pay.service.PayStatoPagamentiService;
import it.gruppoinit.pal.gp.pay.service.PosizioniDebitorieCommandService;
import it.gruppoinit.pal.gp.pay.service.helper.AgidEsitiPagamentoEnum;
import it.gruppoinit.pal.gp.pay.service.helper.IUVHelper;
import it.gruppoinit.pal.gp.pay.service.helper.ParametriConnettoreHelper;
import it.gruppoinit.pal.gp.pay.service.helper.PayConfigurationHelper;
import it.gruppoinit.pal.gp.pay.service.helper.PopolamentoDatiHelper;
import it.gruppoinit.pal.gp.pay.service.helper.RTHelper;
import it.gruppoinit.pal.gp.pay.ws.client.bollettinopagopa.BollettinoPagoPAClient;
import it.gruppoinit.pal.gp.pay.ws.schema.AttivaPagamentoOnTheFlyResponseType;
import it.gruppoinit.pal.gp.pay.ws.schema.AttivaSessionePagamentoResponseType;
import it.gruppoinit.pal.gp.pay.ws.schema.DatiPagamentoType;
import it.gruppoinit.pal.gp.pay.ws.schema.ElencoDocumentiEsitoType;
import it.gruppoinit.pal.gp.pay.ws.schema.ElencoPosizioniDebitorieEsitoType;
import it.gruppoinit.pal.gp.pay.ws.schema.ElencoStatoPosizioniType;
import it.gruppoinit.pal.gp.pay.ws.schema.EsitoDocumentoPosizioneDebitoriaType;
import it.gruppoinit.pal.gp.pay.ws.schema.EsitoOperazionePosizioneDebitoriaType;
import it.gruppoinit.pal.gp.pay.ws.schema.HttpMethodType;
import it.gruppoinit.pal.gp.pay.ws.schema.RegistrazioneContabileType;
import it.gruppoinit.pal.gp.pay.ws.schema.SoggettoDebitoreType;
import it.gruppoinit.pal.gp.pay.ws.schema.StatoDocumentoType;
import it.gruppoinit.pal.gp.pay.ws.schema.StatoPagamentoType;
import it.gruppoinit.pal.gp.pay.ws.schema.StatoPosizioneType;
import it.gruppoinit.pal.gp.pay.ws.schema.TipoDocumentoType;
import it.gruppoinit.schemas.messages.utilitypagopa.BollettinopagopaRequest;

public class UnicreditEasyPAConnector extends AbstractPayConnector implements IPayConnector {

    private static final Logger log = LoggerFactory.getLogger(UnicreditEasyPAConnector.class);
    @Autowired
    private PayPosizioniDebitorieService payPosizioniDebitorieService;
    @Autowired
    private PayStatoPagamentiService payStatoPagamentiService;
    @Autowired
    private PayConnectorConfigValuesService payConnectorConfigValuesService;
    @Autowired
    private AvvisiPagoPAService avvisiPagoPAService;
    @Autowired
    private PaySessioniPagamentoService paySessioniPagamentoService;
    @Autowired
    private PosizioniDebitorieCommandService posizioniDebitorieCommandService;

    @Override
    public ElencoPosizioniDebitorieEsitoType registraPosizioniDebitorie(PosizioniDebitorieCommand datiRegistrazioniCommand) throws PayException {

	log.debug("registraPosizioniDebitorie ");
	ElencoPosizioniDebitorieEsitoType results = new ElencoPosizioniDebitorieEsitoType();
	InserimentoPosizioneRequest richiesta = new InserimentoPosizioneRequest();
	GestorePosizioni ppayWs = this.getGestorePosizioniPort(this.getWsCaricamentoConfig());
	GestorePosizioniHeader header = popolateHeaderPosizioni(this.getWsCaricamentoConfig());
	for (PayRegistrazioniContabili payRegCont : datiRegistrazioniCommand.getRegistrazioniPosizioni()) { //MASSIMO UNA POSIZIONE DEBITORIA IN QUESTA RELEASE
	    for (PayPosizioniDebitorie payPosDeb : payRegCont.getPosizioniDebitorie()) {
		boolean invia = true;
		String inviaSoloPosizioniConMail = posizioniDebitorieCommandService.findValoreUnicoParametroFromPosizioneDebitoria(payPosDeb,
			new ParametroInviaSoloPosizioniDiSoggettiConMail());
		boolean inviaSoloDestinatariConMail = StringUtils.defaultString(inviaSoloPosizioniConMail, "0").equalsIgnoreCase("1");
		if (inviaSoloDestinatariConMail && StringUtils.isBlank(payPosDeb.getSoggettoDebitore().getEmail())) {
		    invia = false;
		    log.debug("registraPosizioniDebitorie la causale è impostata per saltare l'invio di destinatari senza mail. getId()={}",
			    payPosDeb.getId());
		}
		EsitoOperazionePosizioneDebitoriaType esitoPos = new EsitoOperazionePosizioneDebitoriaType();
		esitoPos.setEsito(true);
		esitoPos.setStato(StatoPagamentoType.ACQUISITO);
		esitoPos.setMessaggio(StatiPagamento.ACQUISITO.description());
		esitoPos.setIdPosizione(BigInteger.valueOf(payPosDeb.getId().getCodice()));
		if (payRegCont.getId() != null && payRegCont.getId().getCodice() != null) {
		    esitoPos.setIdRegistrazioneContabile(BigInteger.valueOf(payRegCont.getId().getCodice()));
		}
		String idPosizionePsp = this.generaIdPosizioneDebitoria(payPosDeb);
		payPosDeb.setIdPosizionePsp(idPosizionePsp);
		this.payPosizioniDebitorieService.aggiornaPosizioneDebitoria(payPosDeb, null, null);
		if (invia) {
		    log.debug("registraPosizioniDebitorie payPosDeb.getId()={}", payPosDeb.getId());
		    InserimentoPosizioneInputType input = new InserimentoPosizioneInputType();
		    String codiceVersamento = posizioniDebitorieCommandService.findCodiceVersamentoFromPosizioneDebitoria(payPosDeb);
		    input.setCodiceServizio(Integer.valueOf(codiceVersamento)); // codice_servizio 
		    //		Servizio		Codice servizio	Numero servizio
		    //		COSAP			COSAP1		1
		    //		DISTRIBUTORI		DSTRBT		4
		    //		MEZZI PUBBLICITARI	CRRMPU		7
		    //		COSAP DISTRIBUTORI	CSPDST		13
		    input.setIdentificativoBeneficiario(PayConfigurationHelper.getProfiloEnteCreditore().getCfEnteQrcodePagopa());
		    popolaDatiSoggettoDebitoreGestorePosizioni(input, payPosDeb.getSoggettoDebitore());
		    input.setCausale(StringUtils.left(payPosDeb.getDescrizioneCausale(), 140));
		    input.setCodiceRiferimentoCreditore(idPosizionePsp);
		    log.debug("registraPosizioniDebitorie idPosizionePsp={}", idPosizionePsp);
		    if (payPosDeb.getDataScadenza() != null) {
			input.setDataScadenzaPagamento(Utilities.getXMLGregorianCalendar(payPosDeb.getDataScadenza()));
		    }
		    input.setTipoRiferimentoCreditore(getTipoRiferimentoCreditore(payPosDeb));
		    // Può essere String fissa va in chiave con identificativo e sia univoco con il campo successivo setCodiceRiferimentoCreditore. Dei 35 caratteri del tracciato, 
		    // attualmente, sono significativi solamente i primi 8. 
		    // I primi 8 caratteri formano la chiave univoca verificata al momento del caricamento posizione
		    BigDecimal totImportoPosizione = BigDecimal.ZERO;
		    for (PayDettaglioImporti voceImporto : payPosDeb.getDettagliImporto()) { // MASSIMO 5 OCCORRENZE
			totImportoPosizione = totImportoPosizione.add(voceImporto.getImporto());
		    }
		    input.setImporto(totImportoPosizione);
		    input.setCkey5(PkId.toStringId(payRegCont.getId()));
		    input.setCkey6(PkId.toStringId(payPosDeb.getId()));
		    //predispongo gli esiti OK da modificare in caso di errore nella chiamata al servizio
		    esitoPos.setStato(StatoPagamentoType.TRASMESSO_A_PSP);
		    esitoPos.setMessaggio(StatiPagamento.TRASMESSO_A_PSP.description());
		    richiesta.setInserimentoPosizioneInput(input);
		    InserimentoPosizioneResponse result = null;
		    try {
			log.debug("registraPosizioniDebitorie prima di invocare ppayWs={}", idPosizionePsp);
			result = ppayWs.inserimentoPosizione(header, richiesta);
		    } catch (Exception e) {
			log.error("errore nell'invocazione del servizio inserisciListaDiCarico: ", e);
			result = new InserimentoPosizioneResponse();
			InserimentoPosizioneOutputType output = new InserimentoPosizioneOutputType();
			output.setEsito(EasyPAConstants.EsitiEasyPA.KO.name());
			output.setCodiceErrore(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
			output.setDescrizione(e.getMessage());
			result.setInserimentoPosizioneOutput(output);
		    }
		    String esito = result.getInserimentoPosizioneOutput().getEsito();
		    log.debug("registraPosizioniDebitorie esito={}", esito);
		    if (!esito.equalsIgnoreCase(EasyPAConstants.EsitiEasyPA.OK.name())) {
			//gestione errore restituito dalla chiamata al servizio
			esitoPos.setEsito(false);
			esitoPos.setStato(StatoPagamentoType.CON_ERRORE);
			esitoPos.setCodiceErrore(result.getInserimentoPosizioneOutput().getCodiceErrore());
			esitoPos.setMessaggio(result.getInserimentoPosizioneOutput().getDescrizione());
		    } else {
			//  AGGIORNA DATI DELLA POSIZIONE DEBITORIA CON CODICEAVVISO, IUV, QRCODE, ECC...
			String iuv = result.getInserimentoPosizioneOutput().getIdentificativoUnivocoVersamento();
			String numeroAvviso = result.getInserimentoPosizioneOutput().getCodiceIdentificativoPresentazione();
			esitoPos.setIUV(iuv);
			esitoPos.setCodiceAvviso(numeroAvviso);
			log.debug("registraPosizioniDebitorie iuv={},numeroAvviso={}", iuv, numeroAvviso);
			esitoPos.setEsito(true);
			esitoPos.setStato(StatoPagamentoType.ATTIVATO_IN_PSP);
			esitoPos.setMessaggio(StatiPagamento.ATTIVATO_IN_PSP.description());
		    }
		}
		results.getEsitoPosizione().add(esitoPos);
	    }
	}
	return results;
    }

    private String getTipoRiferimentoCreditore(PayPosizioniDebitorie payPosDeb) throws PayException {

	String tipoRiferimentoCreditore = posizioniDebitorieCommandService.findValoreUnicoParametroFromPosizioneDebitoria(payPosDeb,
		new ParametroTipoRiferimentoCreditore());
	return tipoRiferimentoCreditore;
    }

    private void popolaDatiSoggettoDebitoreGestorePosizioni(InserimentoPosizioneInputType input, PaySoggettiDebitori soggettoDebitore) {

	input.setAnagraficaDebitore(formatNomeCognome(soggettoDebitore.getNome(), soggettoDebitore.getCognome()));
	input.setCapDebitore(soggettoDebitore.getCap());
	input.setEmailDebitore(soggettoDebitore.getEmail());
	input.setIdentificativoDebitore(soggettoDebitore.getCfPi());
	input.setIndirizzoDebitore(soggettoDebitore.getVia());
	input.setLocalitaDebitore(soggettoDebitore.getLocalita());
	input.setProvinciaDebitore(soggettoDebitore.getProvincia());
	if (StringUtils.defaultString(soggettoDebitore.getCfPi()).length() == 11) {
	    input.setTipoIdDebitore(EasyPAConstants.TipoIdDebitoreAnagrafe.G.name());
	} else {
	    input.setTipoIdDebitore(EasyPAConstants.TipoIdDebitoreAnagrafe.F.name());
	}
    }

    private GestorePosizioniHeader popolateHeaderPosizioni(PayConnectorWsEndpoint wsCaricamentoConfig) {

	GestorePosizioniHeader h = new GestorePosizioniHeader();
	h.setUser(wsCaricamentoConfig.getUtente());
	h.setPassword(wsCaricamentoConfig.getPassword());
	return h;
    }

    @Override
    public AttivaPagamentoOnTheFlyResponseType attivaPagamentoOnTheFly(PosizioniDebitorieCommand cmd) throws PayException {

	log.debug("attivaPagamentoOnTheFly ");
	// ElencoPosizioniDebitorieEsitoType results = new ElencoPosizioniDebitorieEsitoType();
	PaInviaCarrelloPosizioni ppayWs = this.getPaInviaCarrelloSoapPort(this.getWsAttivaSessioneConfig());
	AttivaPagamentoOnTheFlyResponseType result = new AttivaPagamentoOnTheFlyResponseType();
	AttivaSessionePagamentoResponseType attivaSessioneOTF = new AttivaSessionePagamentoResponseType();
	result.setSessionePagamento(attivaSessioneOTF);
	List<PayRegistrazioniContabili> rcs = cmd.getRegistrazioniPosizioni();
	try {
	    log.debug("attivaPagamentoOnTheFly populateHeader");
	    PaInviaCarrelloPosizioniHeader header = populateHeader(this.getWsAttivaSessioneConfig());
	    PaInviaCarrelloPosizioniRequest richiesta = new PaInviaCarrelloPosizioniRequest();
	    attivaSessioneOTF.setEsito(true);
	    int i = 0;
	    for (PayRegistrazioniContabili payRegCont : rcs) {
		for (PayPosizioniDebitorie payPosDeb : payRegCont.getPosizioniDebitorie()) {
		    log.debug("attivaPagamentoOnTheFly payPosDeb={}", payPosDeb.getId());
		    PaInviaCarrelloPosizioniListaType posizione = new PaInviaCarrelloPosizioniListaType();
		    popolaDatiSoggettoDebitore(posizione, payPosDeb.getSoggettoDebitore());
		    String idPosizionePsp = this.generaIdPosizioneDebitoria(payPosDeb);
		    String codiceVersamento = posizioniDebitorieCommandService.findCodiceVersamentoFromPosizioneDebitoria(payPosDeb);
		    posizione.setCodiceServizio(codiceVersamento);//solamente le causali configurate come spontanee
		    posizione.setIdentificativoBeneficiario(PayConfigurationHelper.getProfiloEnteCreditore().getCfEnteQrcodePagopa());
		    payPosDeb.setIdPosizionePsp(idPosizionePsp);
		    log.debug("attivaPagamentoOnTheFly idPosizionePsp={}", idPosizionePsp);
		    if (payPosDeb.getDataScadenza() != null) {
			Calendar c = Calendar.getInstance();
			c.setTime(payPosDeb.getDataScadenza());
			c.add(Calendar.DATE, 1);
			posizione.setDataScadenzaPagamento(Utilities.formatDateWithPattern(c.getTime(), Utilities.DATE_FORMAT_YYYY_MM_DD));
		    }
		    this.payPosizioniDebitorieService.aggiornaPosizioneDebitoria(payPosDeb, null, null);
		    posizione.setSpontaneo(EasyPAConstants.TipoPagamento.Y.name());
		    posizione.setTipoFirmaRicevuta(String.valueOf(EasyPAConstants.TipoFirma.Firma_non_richiesta.valore()));
		    posizione.setTipoRiferimentoCreditore("OTF");
		    posizione.setCodiceRiferimentoCreditore(idPosizionePsp);
		    //		    posizione.setTipoRiferimentoCreditore(getTipoRiferimentoCreditore(payRegCont, payPosDeb));Non valorizzate invece i tag tipo e codice riferimento creditore che nello spontaneo non vengono trattati
		    //		    posizione.setCodiceRiferimentoCreditore(idPosizionePsp); Non valorizzate invece i tag tipo e codice riferimento creditore che nello spontaneo non vengono trattati
		    // Può essere String fissa va in chiave con identificativo e sia univoco con il campo successivo setCodiceRiferimentoCreditore. Dei 35 caratteri del tracciato, 
		    // attualmente, sono significativi solamente i primi 8. 
		    // I primi 8 caratteri formano la chiave univoca verificata al momento del caricamento posizione
		    BigDecimal totImportoPosizione = BigDecimal.ZERO;
		    for (PayDettaglioImporti voceImporto : payPosDeb.getDettagliImporto()) { // MASSIMO 5 OCCORRENZE
			totImportoPosizione = totImportoPosizione.add(voceImporto.getImporto());
			DatiSingoloVersamentoListaType importo = new DatiSingoloVersamentoListaType();
			importo.setCausaleVersamento(
				"(" + idPosizionePsp + "|" + voceImporto.getId().getCodice() + ")" + voceImporto.getDescCausale());
			importo.setCommissioniCaricoPa(BigDecimal.ZERO); // 
			importo.setImportoSingoloVersamento(voceImporto.getImporto());
			posizione.getDatiSingoloVersamento().add(importo);
		    }
		    posizione.setImportoPagamento(totImportoPosizione);
		    //predispongo gli esiti OK da modificare in caso di errore nella chiamata al servizio
		    EsitoOperazionePosizioneDebitoriaType esitoPos = new EsitoOperazionePosizioneDebitoriaType();
		    esitoPos.setEsito(true);
		    esitoPos.setStato(StatoPagamentoType.TRASMESSO_A_PSP);
		    esitoPos.setMessaggio(StatiPagamento.TRASMESSO_A_PSP.description());
		    esitoPos.setIdPosizione(BigInteger.valueOf(payPosDeb.getId().getCodice()));
		    Set<String> riferimentiClientPosizione = payPosizioniDebitorieService
				.findRiferimentiClientByPosizioneDebitoria(payPosDeb.getId().getCodice());
		    if (!riferimentiClientPosizione.isEmpty()) {
			esitoPos.getRiferimentoClient().addAll(riferimentiClientPosizione);
		    }
		    if (payRegCont.getId() != null && payRegCont.getId().getCodice() != null) {
			esitoPos.setIdRegistrazioneContabile(BigInteger.valueOf(payRegCont.getId().getCodice()));
		    }
		    result.getPosizioneInserita().add(i++, esitoPos);
		    richiesta.getPaInviaCarrelloPosizioniLista().add(posizione);
		}
	    }
	    PaInviaCarrelloPosizioniOutputType ppayResult = null;
	    try {
		log.debug("attivaPagamentoOnTheFly idPosizionePsp prima di chiamare ppayWs.paInviaCarrelloPosizioni");
		PaInviaCarrelloPosizioniResponse paInviaCarrelloPosizioniResponse = ppayWs.paInviaCarrelloPosizioni(header, richiesta);
		ppayResult = paInviaCarrelloPosizioniResponse.getPaInviaCarrelloPosizioniOutput();
		log.debug("attivaPagamentoOnTheFly idPosizionePsp dopo chiamata ppayWs.paInviaCarrelloPosizioni");
	    } catch (Exception e) {
		log.error("errore nell'invocazione del servizio paInviaCarrelloPosizioni: {}", e.getMessage(), e);
		throw new PayException(e);
	    }
	    if ("OK".equalsIgnoreCase(ppayResult.getEsito())) {
		log.debug("attivaPagamentoOnTheFly esito OK");
		// COLLEGA GLI IUV ALLE POSIZIONI DEBITORIE
		List<IuvListaType> iuvLista = ppayResult.getIuvLista();
		for (int a = 0; a < iuvLista.size(); a++) {
		    EsitoOperazionePosizioneDebitoriaType eopt = result.getPosizioneInserita().get(a); // gli iuv sono ordinati per ordine di inserimento??' unico modo
		    eopt.setStato(StatoPagamentoType.ATTIVATO_IN_PSP);
		    eopt.setMessaggio(StatiPagamento.ATTIVATO_IN_PSP.description());
		    eopt.setIUV(iuvLista.get(a).getIdentificativoUnivocoVersamento());
		}
		String urlEsito = PayConfigurationHelper.getProfiloEnteCreditore().getUrlEsitoPagamento();
		String url = ppayResult.getUrl() + "?idTransazione=" + ppayResult.getIdTransazione() + "&urlReturn=" +
			     URLEncoder.encode(urlEsito, "UTF-8");
		log.debug("url = {}", url);
		attivaSessioneOTF.setHttpMethodRequired(HttpMethodType.GET);
		attivaSessioneOTF.setPayUrl(url);
		attivaSessioneOTF.setIdSessione(ppayResult.getIdTransazione());
	    } else {
		String codiceErrore = ppayResult.getCodiceErrore();
		String descrizione = ppayResult.getDescrizione();
		log.error("errore nell'invocazione del servizio paInviaCarrelloPosizioni:({}) - {}", codiceErrore, descrizione);
		throw new PayException("Errore nella creazione del carrello (" + codiceErrore + ") " + descrizione);
	    }
	} catch (Exception e) {
	    List<EsitoOperazionePosizioneDebitoriaType> esiti = new ArrayList<>();
	    attivaSessioneOTF.setEsito(false);
	    attivaSessioneOTF.setDescEsito("Errore nel creazione della posizione OTF");
	    for (PayRegistrazioniContabili payRegCont : rcs) {
		for (PayPosizioniDebitorie pd : payRegCont.getPosizioniDebitorie()) {
		    EsitoOperazionePosizioneDebitoriaType esitoKO = new EsitoOperazionePosizioneDebitoriaType();
		    esitoKO.setIdPosizione(BigInteger.valueOf(pd.getId().getCodice()));
		    PopolamentoDatiHelper.completaDatiDaPosizioneDebitoria(esitoKO, pd,
			    payPosizioniDebitorieService.findRiferimentiClientByPosizioneDebitoria(pd.getId().getCodice()));
		    esitoKO.setEsito(false);
		    esitoKO.setStato(StatoPagamentoType.CON_ERRORE);
		    this.handleException(e, esitoKO);
		    esiti.add(esitoKO);
		}
	    }
	    result.getPosizioneInserita().addAll(esiti);
	}
	return result;
    }

    @Override
    public AttivaSessionePagamentoResponseType attivaSessionePagamento(PayPosizioniDebitorie payPos) throws PayException {

	log.debug("attivaSessionePagamento ");
	AttivaSessionePagamentoResponseType sesResp = new AttivaSessionePagamentoResponseType();
	if (payPos != null && payPos.getId() != null && payPos.getId().getCodice() != null) {
	    log.debug("attivaSessionePagamento payPos={}", payPos.getId());
	    PaInviaCarrelloPosizioni ppayWs = this.getPaInviaCarrelloSoapPort(this.getWsAttivaSessioneConfig());
	    PaInviaCarrelloPosizioniRequest richiesta = new PaInviaCarrelloPosizioniRequest();
	    sesResp.setEsito(true);
	    PaInviaCarrelloPosizioniHeader header = populateHeader(this.getWsAttivaSessioneConfig());
	    PaInviaCarrelloPosizioniListaType posizione = new PaInviaCarrelloPosizioniListaType();
	    popolaDatiSoggettoDebitore(posizione, payPos.getSoggettoDebitore());
	    String codiceVersamento = posizioniDebitorieCommandService.findCodiceVersamentoFromPosizioneDebitoria(payPos);
	    posizione.setCodiceServizio(codiceVersamento);//solamente le causali configurate come spontanee
	    posizione.setIdentificativoBeneficiario(PayConfigurationHelper.getProfiloEnteCreditore().getCfEnteQrcodePagopa());
	    if (payPos.getDataScadenza() != null) {
		Calendar c = Calendar.getInstance();
		c.setTime(payPos.getDataScadenza());
		c.add(Calendar.DATE, 1);
		posizione.setDataScadenzaPagamento(Utilities.formatDateWithPattern(c.getTime(), Utilities.DATE_FORMAT_YYYY_MM_DD));
	    }
	    posizione.setSpontaneo(EasyPAConstants.TipoPagamento.N.name());
	    posizione.setTipoFirmaRicevuta(String.valueOf(EasyPAConstants.TipoFirma.Firma_non_richiesta.valore()));
	    posizione.setTipoRiferimentoCreditore("");
	    posizione.setCodiceRiferimentoCreditore(payPos.getIdPosizionePsp());
	    posizione.setIdentificativoUnivocoVersamento(payPos.getIuv());
	    // I primi 8 caratteri formano la chiave univoca verificata al momento del caricamento posizione
	    BigDecimal totImportoPosizione = BigDecimal.ZERO;
	    for (PayDettaglioImporti voceImporto : payPos.getDettagliImporto()) { // MASSIMO 5 OCCORRENZE
		totImportoPosizione = totImportoPosizione.add(voceImporto.getImporto());
		DatiSingoloVersamentoListaType importo = new DatiSingoloVersamentoListaType();
		importo.setCausaleVersamento(
			"(" + payPos.getIdPosizionePsp() + "|" + voceImporto.getId().getCodice() + ")" + voceImporto.getDescCausale());
		importo.setCommissioniCaricoPa(BigDecimal.ZERO); // 
		importo.setImportoSingoloVersamento(voceImporto.getImporto());
		posizione.getDatiSingoloVersamento().add(importo);
	    }
	    posizione.setImportoPagamento(totImportoPosizione);
	    richiesta.getPaInviaCarrelloPosizioniLista().add(posizione);
	    PaInviaCarrelloPosizioniOutputType ppayResult = null;
	    try {
		log.debug("attivaSessionePagamento payPos={} prima di chiamare ppayWs.paInviaCarrelloPosizioni", payPos.getId());
		PaInviaCarrelloPosizioniResponse paInviaCarrelloPosizioniResponse = ppayWs.paInviaCarrelloPosizioni(header, richiesta);
		ppayResult = paInviaCarrelloPosizioniResponse.getPaInviaCarrelloPosizioniOutput();
		if ("OK".equalsIgnoreCase(ppayResult.getEsito())) {
		    log.debug("attivaSessionePagamento payPos={} esito OK", payPos.getId());
		    // COLLEGA GLI IUV ALLE POSIZIONI DEBITORIE
		    String urlEsito = PayConfigurationHelper.getProfiloEnteCreditore().getUrlEsitoPagamento();
		    String url = ppayResult.getUrl() + "?idTransazione=" + ppayResult.getIdTransazione() + "&urlReturn=" +
				 URLEncoder.encode(urlEsito, "UTF-8");
		    log.debug("attivaSessionePagamento url = {}", url);
		    sesResp.setPayUrl(url);
		    sesResp.setHttpMethodRequired(HttpMethodType.GET);
		    sesResp.setIdSessione(ppayResult.getIdTransazione());
		    sesResp.setEsito(true);
		} else {
		    String codiceErrore = ppayResult.getCodiceErrore();
		    String descrizione = ppayResult.getDescrizione();
		    log.error("errore nell'invocazione del servizio paInviaCarrelloPosizioni:({}) - {}", codiceErrore, descrizione);
		    throw new PayException("Errore nella creazione del carrello (" + codiceErrore + ") " + descrizione);
		}
	    } catch (Exception e) {
		log.error("errore nell'invocazione del servizio paInviaCarrelloPosizioni: {}", e.getMessage(), e);
		log.debug("[Attiva Sessione Pagamento] chiamata al client falita", e);
		sesResp.setDescEsito(e.getMessage());
		sesResp.setEsito(false);
	    }
	}
	return sesResp;
    }

    @Override
    public ElencoStatoPosizioniType rendicontazionePagamenti(RichiestaSuListaPosizioniCommand cmd) throws PayException {

	//i dati dei pagamenti vengono recuperati dalle ricevute digitali XML uno alla volta
	ElencoStatoPosizioniType retStati = new ElencoStatoPosizioniType();
	for (PayPosizioniDebitorie pos : cmd.getPosizioni()) {
	    StatoPosizioneType retEsito = this.scaricaRicevutaXmlWs(pos);
	    retStati.getStatoPosizioni().add(retEsito);
	}
	return retStati;
    }

    private StatoPosizioneType scaricaRicevutaXmlWs(PayPosizioniDebitorie payPos) {

	log.debug("scaricaRicevutaXmlWs");
	StatoPosizioneType esito = new StatoPosizioneType();
	PopolamentoDatiHelper.completaDatiDaPosizioneDebitoria(esito, payPos,
		payPosizioniDebitorieService.findRiferimentiClientByPosizioneDebitoria(payPos.getId().getCodice()));
	try {
	    EasyPaClient client = this.getDownloadRicevutaXMLPort();
	    log.debug("scaricaRicevutaXmlWs prima di chiamare il client.getRicevutaTelematicaXML {}", payPos.getIuv());
	    RispostaRicevutaTelematica risposta = client.getRicevutaTelematicaXML(payPos.getIuv());
	    CtRicevutaTelematica ricevutaObj = risposta.getRt();
	    //	    0 Pagamento eseguito
	    //	    1 Pagamento non eseguito
	    //	    2 Pagamento parzialmente eseguito
	    //	    3 Decorrenza termini
	    //	    4 Decorrenza termini parziale
	    if (ricevutaObj.getDatiPagamento().getCodiceEsitoPagamento().equals(AgidEsitiPagamentoEnum.PAGAMENTO_ESEGUITO.getValore()) || ricevutaObj
		    .getDatiPagamento().getCodiceEsitoPagamento().equals(AgidEsitiPagamentoEnum.PAGAMENTO_PARZIALMENTE_ESEGUITO.getValore())) {
		DatiPagamentoType datiPag = RTHelper.popolaDatiPagamentoDaRicevutaTelematica(ricevutaObj, null);
		DataSource ds = new ByteArrayDataSource(risposta.getRtAsString(), ContentType.APPLICATION_OCTET_STREAM.getMimeType());
		DataHandler ricevutaXML = new DataHandler(ds);
		datiPag.setRicevutaXml(ricevutaXML);
		esito.setEsito(true);
		esito.setDatiPagamento(datiPag);
		esito.setStato(StatoPagamentoType.RENDICONTATO_DA_IC);
		log.debug(
			"scaricaRicevutaWs - il servizio downloadDatiRicevuta i dati della ricevuta XML sono stati a recuperati per la posizione {}",
			PkId.toStringId(payPos.getId()));
	    } else {
		String messaggio = "La ricevuta ha tornato lo stato " +
				   AgidEsitiPagamentoEnum.fromValore(ricevutaObj.getDatiPagamento().getCodiceEsitoPagamento());
		esito.setEsito(false);
		esito.setMessaggio(messaggio);
		esito.setStato(StatoPagamentoType.CON_ERRORE);
		log.debug("scaricaRicevutaWs - {}", messaggio);
	    }
	} catch (Exception e) {
	    this.handleException(e, esito);
	    log.error("scaricaRicevutaWs", e);
	}
	return esito;
    }

    @Override
    public ElencoPosizioniDebitorieEsitoType annullaPosizioniDebitorie(PosizioniDebitorieCommand datiRegistrazioniCommand, boolean pagatoOffline)
	    throws PayException {

	// Nei doc non sono riportati metodi di annullamento per cui a livello applicativo cui a livello funzionale verranno 
	// annullate nel nostro middleware senza comunicarlo a UNICREDIT con messaggio specifico.
	ElencoPosizioniDebitorieEsitoType results = new ElencoPosizioniDebitorieEsitoType();
	List<PayRegistrazioniContabili> registrazioni = datiRegistrazioniCommand.getRegistrazioniPosizioni();
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
		esitoPos.setIdPosizione(BigInteger.valueOf(pos.getId().getCodice()));
		PopolamentoDatiHelper.completaDatiDaPosizioneDebitoria(esitoPos, pos,
			payPosizioniDebitorieService.findRiferimentiClientByPosizioneDebitoria(pos.getId().getCodice()));
		esitoPos.setIdRegistrazioneContabile(BigInteger.valueOf(reg.getId().getCodice()));
		results.getEsitoPosizione().add(esitoPos);
	    }
	}
	//gestione errore restituito dalla chiamata al servizio
	for (EsitoOperazionePosizioneDebitoriaType esitoNodo : results.getEsitoPosizione()) {
	    esitoNodo.setEsito(true);
	    StatoPagamentoType newStatus = pagatoOffline ? StatoPagamentoType.PAGATO_OFFLINE_ANNULLATO : StatoPagamentoType.ANNULLATO;
	    StatiPagamento statusDesc = pagatoOffline ? StatiPagamento.PAGATO_OFFLINE_ANNULLATO : StatiPagamento.ANNULLATO;
	    esitoNodo.setStato(newStatus);
	    esitoNodo.setMessaggio(statusDesc.description());
	}
	return results;
    }

    @Override
    public PaySessioniPagamento gestisciEsitoSessione(Map<String, String[]> reqParams) {

	log.debug("gestisciEsitoSessione ");
	List<PaySessioniPagamento> sex = new ArrayList<>();
	String[] idSessioneVals = reqParams.get("idTransazione");
	String[] esitoVal = reqParams.get("stato");
	log.debug("gestisciEsitoSessione.idSessioneVals {}", idSessioneVals);
	log.debug("gestisciEsitoSessione.esitoVal {}", idSessioneVals);
	String idSessione = null;
	String esito = null;
	if (idSessioneVals != null && idSessioneVals.length > 0) {
	    idSessione = idSessioneVals[0];
	}
	if (esitoVal != null && esitoVal.length > 0) {
	    esito = esitoVal[0];
	}
	if (StringUtils.isNotBlank(idSessione)) {
	    sex = this.paySessioniPagamentoService.findBySessionId(idSessione);
	}
	if (!sex.isEmpty()) {
	    for (PaySessioniPagamento paySessioniPagamento : sex) {
		paySessioniPagamento.setEsito("OK".equals(esito));
		this.paySessioniPagamentoService.update(paySessioniPagamento);
	    }
	    return sex.get(0); // ne ritorno una perché la redirect è sempre quella
	}
	return null;
    }

    private void popolaDatiSoggettoDebitore(PaInviaCarrelloPosizioniListaType posizione, PaySoggettiDebitori soggettoDebitore) {

	posizione.setAnagraficaPagatore(formatNomeCognome(soggettoDebitore.getNome(), soggettoDebitore.getCognome()));
	posizione.setCapPagatore(soggettoDebitore.getCap());
	posizione.setEmailPagatore(soggettoDebitore.getEmail());
	posizione.setIdentificativoPagatore(soggettoDebitore.getCfPi());
	posizione.setIndirizzoPagatore(soggettoDebitore.getVia());
	posizione.setLocalitaPagatore(soggettoDebitore.getLocalita());
	posizione.setProvinciaPagatore(soggettoDebitore.getProvincia());
	if (StringUtils.defaultString(soggettoDebitore.getCfPi()).length() == 11) {
	    posizione.setTipoIdPagatore(EasyPAConstants.TipoIdDebitoreAnagrafe.G.name());
	} else {
	    posizione.setTipoIdPagatore(EasyPAConstants.TipoIdDebitoreAnagrafe.F.name());
	}
    }

    private PaInviaCarrelloPosizioniHeader populateHeader(PayConnectorWsEndpoint caricaPosWs) {

	PaInviaCarrelloPosizioniHeader ob = new PaInviaCarrelloPosizioniHeader();
	ob.setUser(caricaPosWs.getUtente());
	ob.setPassword(caricaPosWs.getPassword());
	ob.setAbiCode(this.payConnectorConfigValuesService.getValoreParametroConfigurazione(ConfigParamNames.CODICE_ABI_ENTE_CREDITORE));
	return ob;
    }

    private PaInviaCarrelloPosizioni getPaInviaCarrelloSoapPort(PayConnectorWsEndpoint caricaPosWsOTF) {

	JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
	factory.setServiceClass(PaInviaCarrelloPosizioni.class);
	//codice per compatibilità con vecchia modalita di configurazione degli endpoint dei servizi da eliminare dopo che test ok sul nuovo
	factory.setAddress(caricaPosWsOTF.getEndpointUrl());// <- must be /soap there, otherwise 404
	PaInviaCarrelloPosizioni info = (PaInviaCarrelloPosizioni) factory.create();
	Client client = ClientProxy.getClient(info);
	if (caricaPosWsOTF.getTimeout() != null) {
	    HTTPConduit conduit = (HTTPConduit) client.getConduit();
	    HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
	    httpClientPolicy.setConnectionTimeout(caricaPosWsOTF.getTimeout());
	    httpClientPolicy.setReceiveTimeout(caricaPosWsOTF.getTimeout());
	    conduit.setClient(httpClientPolicy);
	}
	return info;
    }

    private GestorePosizioni getGestorePosizioniPort(PayConnectorWsEndpoint caricaPosWs) {

	JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
	factory.setServiceClass(GestorePosizioni.class);
	//codice per compatibilità con vecchia modalita di configurazione degli endpoint dei servizi da eliminare dopo che test ok sul nuovo
	factory.setAddress(caricaPosWs.getEndpointUrl());// <- must be /soap there, otherwise 404
	GestorePosizioni info = (GestorePosizioni) factory.create();
	Client client = ClientProxy.getClient(info);
	if (caricaPosWs.getTimeout() != null) {
	    HTTPConduit conduit = (HTTPConduit) client.getConduit();
	    HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
	    httpClientPolicy.setConnectionTimeout(caricaPosWs.getTimeout());
	    httpClientPolicy.setReceiveTimeout(caricaPosWs.getTimeout());
	    conduit.setClient(httpClientPolicy);
	}
	return info;
    }

    @Override
    public boolean supportaPagamentoOnTheFly() {

	return true;
    }

    private enum StatiPagamentoEasyPA {
	PARZIALMENTE_INCASSATO,
	INCASSATO,
	REVOCATO,
	STORNATO,
	DA_INCASSARE,
	ERRATO,
	NON_DEFINITO
    }

    @Override
    public ElencoStatoPosizioniType verificaStatoPagamenti(RichiestaSuListaPosizioniCommand cmd) throws PayException {

	log.debug("verificaStatoPagamenti ");
	// 1) verificare la request
	ElencoStatoPosizioniType result = new ElencoStatoPosizioniType();
	EasyPaClient client = this.getVerificaStatoPagamentoPort();
	for (PayPosizioniDebitorie payPos : cmd.getPosizioni()) {
	    log.debug("verificaStatoPagamenti payPos {}", payPos.getId());
	    StatoPosizioneType retStatus = new StatoPosizioneType();
	    retStatus.setEsito(false);
	    PopolamentoDatiHelper.completaDatiDaPosizioneDebitoria(retStatus, payPos,
		    payPosizioniDebitorieService.findRiferimentiClientByPosizioneDebitoria(payPos.getId().getCodice()));
	    try {
		// 3) fare la richiesta di verificaStato
		// 4) popolare elenco stato posizioni debitorie es EasyPa Connector
		log.debug("verificaStatoPagamenti payPos {} client.getDettaglioPosizione {}", payPos.getId(), payPos.getIuv());
		DettaglioPosizioneResponse dettaglioPosizione = client.getDettaglioPosizione(payPos.getIuv());
		log.debug("dettaglioPosizione: {}", dettaglioPosizione);
		// String stato = StringUtils.defaultString(dettaglioPosizione.getStato());
		StatiPagamentoEasyPA stato = fromValue(StringUtils.defaultString(dettaglioPosizione.getStato()));
		log.debug("verificaStatoPagamenti payPos {} stato {}", payPos.getId(), stato);
		// PARZIALMENTE_INCASSATO, INCASSATO, REVOCATO, STORNATO, DA_INCASSARE, ERRATO
		if (stato.equals(StatiPagamentoEasyPA.INCASSATO) || stato.equals(StatiPagamentoEasyPA.PARZIALMENTE_INCASSATO)) {
		    log.debug("verificaStatoPagamenti payPos {} stato {}", payPos.getId(), stato);
		    retStatus.setStato(StatoPagamentoType.NOTIFICATO_DA_PSP);
		    // Popolo dati pagamento
		    DatiPagamentoType pagamenti = popolaDatiPagamento(dettaglioPosizione);
		    retStatus.setDatiPagamento(pagamenti);
		} else {
		    if (stato.equals(StatiPagamentoEasyPA.DA_INCASSARE)) {
			// gestire errore o non incassato
			retStatus.setStato(StatoPagamentoType.ATTIVATO_IN_PSP);
			if (BooleanUtils.isTrue(payPos.getFlagOTF())) {
			    // attualmente non esiste un metodo per la verifica del carrello pagato e demando il client
			    // a ripetere la chiamata fino a che no torna esito
			    // in questo caso ad esempio nel front dovrà essere attivato il bottone per annullare un pagamento 
			    // di cui si sa per certo che non sia andato a buon fine.
			}
		    } else {
			log.error("Posizione debitoria {} tornato stato non codificato {}", payPos.getId(), dettaglioPosizione.getStato());
			// retStatus.setStato(StatoPagamentoType.CON_ERRORE);
		    }
		}
		result.getStatoPosizioni().add(retStatus);
	    } catch (Exception e) {
		this.handleException(e, retStatus);
	    }
	}
	return result;
    }

    public static StatiPagamentoEasyPA fromValue(String text) {

	String testoDaConfrontare = text.replace(" ", "").replace("_", "").toLowerCase();
	for (StatiPagamentoEasyPA b : StatiPagamentoEasyPA.values()) {
	    if (String.valueOf(b.name().replace(" ", "").replace("_", "").toLowerCase()).equalsIgnoreCase(testoDaConfrontare)) {
		return b;
	    }
	}
	return StatiPagamentoEasyPA.NON_DEFINITO;
    }

    @Override
    public ElencoDocumentiEsitoType inviaAvvisiPagamento(PosizioniDebitorieCommand cmd) throws PayException {

	log.debug("inviaAvvisiPagamento ");
	PayConnectorWsEndpoint wsAvviso = this.getWsAvvisoConfig();
	PayProfiliEntiCreditori ente = PayConfigurationHelper.getProfiloEnteCreditore();
	if (wsAvviso == null) {
	    throw new PayConfigurationException(
		    "l'endpoint per il servizio di generazione degli avvisi non è configurato per il connettore " + this.getConnectorName());
	}
	ElencoDocumentiEsitoType retEsiti = new ElencoDocumentiEsitoType();
	String msg = null;
	BollettinoPagoPAClient client = new BollettinoPagoPAClient(wsAvviso);
	if (client != null) {
	    for (PayRegistrazioniContabili reg : cmd.getRegistrazioniPosizioni()) {
		for (PayPosizioniDebitorie pos : reg.getPosizioniDebitorie()) {
		    log.debug("inviaAvvisiPagamento pos {}", pos.getId());
		    msg = "invocazione del servizio getPagamentoAttesoPdf in corso";
		    EsitoDocumentoPosizioneDebitoriaType esitoDoc = new EsitoDocumentoPosizioneDebitoriaType();
		    PopolamentoDatiHelper.completaDatiDaPosizioneDebitoria(esitoDoc, pos,
			    payPosizioniDebitorieService.findRiferimentiClientByPosizioneDebitoria(pos.getId().getCodice()));
		    esitoDoc.setTipoDocumento(TipoDocumentoType.AVVISO);
		    esitoDoc.setStatoDocumento(StatoDocumentoType.DISPONIBILE);
		    try {
			log.debug("inviaAvvisiPagamento pos {}", pos.getId());
			BollettinopagopaRequest request = avvisiPagoPAService.popolaAvvisoRequestDaPosizioneDebitoria(pos, ente);
			log.debug("inviaAvvisiPagamento prima di chiamare client.generaAvviso pos {}", pos.getId());
			DataHandler generaAvviso = client.generaAvviso(request);
			if (generaAvviso != null) {
			    log.debug("inviaAvvisiPagamento client.generaAvviso pos {} OK", pos.getId());
			    msg = "l'avviso di pagamento in PDF è stato scaricato correttamente";
			    esitoDoc.setEsito(true);
			    esitoDoc.setDocumento(generaAvviso);
			    esitoDoc.setNomeDocumento("AVVISO_" + pos.getIuv() + ".pdf");
			}
		    } catch (Exception e) {
			this.handleException(e, esitoDoc);
			msg = "impossibile scaricare l'avviso PDF a causa dell'errore: " + e.toString();
			log.error("inviaAvvisiPagamento - {}", msg, e);
		    }
		    esitoDoc.setMessaggio(msg);
		    log.debug("inviaAvvisiPagamento - {}", msg);
		    retEsiti.getEsitoPosizione().add(esitoDoc);
		}
	    }
	}
	return retEsiti;
    }

    private DatiPagamentoType popolaDatiPagamento(DettaglioPosizioneResponse dettaglioPosizione) {

	DatiPagamentoType pagamenti = new DatiPagamentoType();
	// pagamenti.setModalitaPagamento(dettaglioPosizione.get); 
	// Accediamo al primo elemento della lista perchè paghiamo una sola posizione 
	pagamenti.setImportoPagato(new BigDecimal(dettaglioPosizione.getImportoPagamento()));
	BigDecimal importoCommissioni = BigDecimal.ZERO;
	if (dettaglioPosizione.getImportoCommissioniPa() != null) {
	    importoCommissioni = new BigDecimal(dettaglioPosizione.getImportoCommissioniPa());
	}
	pagamenti.setImportoCommissioni(importoCommissioni);
	pagamenti.setDescrizioneCausale(dettaglioPosizione.getCausaleVersamento());
	// pagamenti.setNote(ctDatiSingoloVersamentoRPT.getDatiSpecificiRiscossione());
	// pagamenti.setDataOraPagamento(ctRichiestaPagamentoTelematico.getDatiVersamento().getDataEsecuzionePagamento());
	SoggettoDebitoreType paySoggettiDebitori = fromDettaglioPosizione(dettaglioPosizione);
	pagamenti.setSoggettoPagatore(paySoggettiDebitori);
	return pagamenti;
    }

    private SoggettoDebitoreType fromDettaglioPosizione(DettaglioPosizioneResponse dettaglioPosizione) {

	SoggettoDebitoreType paySoggettiDebitori = new SoggettoDebitoreType();
	String anagraficaPagatore = dettaglioPosizione.getAnagraficaPagatore();
	paySoggettiDebitori.setNome(anagraficaPagatore);
	paySoggettiDebitori.setCap(dettaglioPosizione.getCapPagatore());
	paySoggettiDebitori.setCfpi(dettaglioPosizione.getIdentificativoPagatore());
	paySoggettiDebitori.setCivico(dettaglioPosizione.getCivicoPagatore());
	paySoggettiDebitori.setEmail(dettaglioPosizione.getEmailPagatore());
	paySoggettiDebitori.setLocalita(dettaglioPosizione.getLocalitaPagatore());
	paySoggettiDebitori.setProvincia(dettaglioPosizione.getProvinciaPagatore());
	paySoggettiDebitori.setStato(dettaglioPosizione.getCodiceNazionePagatore());
	paySoggettiDebitori.setVia(dettaglioPosizione.getIndirizzoPagatore());
	return paySoggettiDebitori;
    }

    @Override
    public void validateRichiestaInserimentoPosizioniDebitorie(RegistrazioneContabileType rc) throws ValidazionePosizioniDebitorieException {

	//TODO verificare se il limite è sul numero di rate massime in un debito o sul numero massimo di dettagli importo in una rata
	if (rc.getRate().getRata().size() > 5) {
	    throw new ValidazionePosizioniDebitorieException("Non è possibile inviare al serviuzio di pagamenti (" + this.getConnectorName() +
							     ") una posizione debitoria con più di 5 rate. Riferimento posizione debitoria: " +
							     rc.getDescrizione());
	}
	validaSoggettoDebitoreType(rc);
    }

    private void validaSoggettoDebitoreType(RegistrazioneContabileType rc) throws ValidazionePosizioniDebitorieException {

	String rif = "Riferimento debito: " + rc.getDescrizione();
	String nome = formatNomeCognome(rc.getSoggettoDebitore().getNome(), rc.getSoggettoDebitore().getCognome());
	if (StringUtils.length(nome) > 50) {
	    throw new ValidazionePosizioniDebitorieException("Il nominativo del soggetto debitore non può essere maggiore di 50 caratteri. " + rif);
	}
	if (StringUtils.defaultString(rc.getSoggettoDebitore().getCap()).length() > 5) {
	    throw new ValidazionePosizioniDebitorieException("Il cap del soggetto debitore non può essere maggiore di 5 caratteri. " + rif);
	}
	if (StringUtils.defaultString(rc.getSoggettoDebitore().getVia()).length() > 50) {
	    throw new ValidazionePosizioniDebitorieException("L'indirizzo del soggetto debitore non può essere maggiore di 50 caratteri. " + rif);
	}
	if (StringUtils.defaultString(rc.getSoggettoDebitore().getLocalita()).length() > 35) {
	    throw new ValidazionePosizioniDebitorieException(
		    "La località/città del soggetto debitore non può essere maggiore di 35 caratteri. " + rif);
	}
	if (StringUtils.defaultString(rc.getSoggettoDebitore().getProvincia()).length() > 2) {
	    throw new ValidazionePosizioniDebitorieException(
		    "La sighla provincia del soggetto debitore non può essere maggiore di 2 caratteri. " + rif);
	}
    }

    private String formatNomeCognome(String nome, String cognome) {

	StringBuilder sb = new StringBuilder();
	if (StringUtils.isNotBlank(cognome)) {
	    sb.append(cognome).append(" ");
	}
	//il nome c'è comunque e contiene la reagione sociale nel caso si tratti di persona giuridica
	sb.append(nome);
	return sb.toString();
    }

    private EasyPaClient getVerificaStatoPagamentoPort() throws PayException {

	PayConnectorWsEndpoint wsCfg = this.getWsVerificaConfig();
	if (wsCfg == null) {
	    throw new PayConfigurationException("Il WS per la verifica dello stato dei pagamenti non è configurato per il connettorre EasyPa.");
	}
	OAuth2SecurityRestTokenManager mgr = this.getSecurityTokenManager();
	String token = mgr.getSecurityToken();
	String bearer = "Bearer";// FISSO NON CALCOLATO CHE TORNA bearer minuscolo e non funziona StringUtils.defaultIfBlank(mgr.getTokenType(), "Bearer"); 
	return new EasyPaClient(token, bearer, wsCfg);
    }

    private EasyPaClient getDownloadRicevutaXMLPort() throws PayException {

	PayConnectorWsEndpoint wsCfg = this.getWsRicevutaConfig();
	if (wsCfg == null) {
	    throw new PayConfigurationException("Il WS per download della ricevuta telematica i non è configurato per il connettorre EasyPa.");
	}
	OAuth2SecurityRestTokenManager mgr = this.getSecurityTokenManager();
	String token = mgr.getSecurityToken();
	String bearer = StringUtils.defaultIfBlank(mgr.getTokenType(), "Bearer");
	return new EasyPaClient(token, bearer, this.getWsVerificaConfig());
    }

    private OAuth2SecurityRestTokenManager getSecurityTokenManager() throws PayException {

	String grantType = this.payConnectorConfigValuesService.getValoreParametroConfigurazione(ConfigParamNames.AUTH_GRANT_TYPE);
	String codiceIstituto = this.payConnectorConfigValuesService.getValoreParametroConfigurazione(ConfigParamNames.AUTH_CODICE_ISTITUTO);
	String codiceEnte = this.payConnectorConfigValuesService.getValoreParametroConfigurazione(ConfigParamNames.AUTH_CODICE_ENTE);
	String idEnte = this.payConnectorConfigValuesService.getValoreParametroConfigurazione(ConfigParamNames.AUTH_ID_ENTE);
	String idDominio = this.payConnectorConfigValuesService.getValoreParametroConfigurazione(ConfigParamNames.AUTH_ID_DOMINIO);
	IOAuth2Params params = new EasyPaSecurityRequestParams(grantType, codiceIstituto, codiceEnte, idEnte, idDominio);
	return new OAuth2SecurityRestTokenManager(getWsSecurityConfig(), params);
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
    public boolean supportaMolteCausaliRaggruppate() {

	return true;
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
