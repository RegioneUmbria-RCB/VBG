/**
 * 
 */
package it.gruppoinit.pal.gp.pay.connector.payer;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.util.ByteArrayDataSource;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.xml.bind.JAXBException;
import javax.xml.ws.soap.SOAPFaultException;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.cxf.jaxrs.ext.multipart.MultipartBody;
import org.apache.cxf.jaxrs.impl.MetadataMap;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.seda.payer.ext.Client;
import com.seda.payer.ext.util.SedaExtException;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.exceptions.ValidazionePosizioniDebitorieException;
import it.gruppoinit.pal.gp.core.utils.IOUtils;
import it.gruppoinit.pal.gp.core.utils.JSONUtils;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.pal.gp.pay.command.PosizioniDebitorieCommand;
import it.gruppoinit.pal.gp.pay.command.RichiestaSuListaPosizioniCommand;
import it.gruppoinit.pal.gp.pay.connector.AbstractPayConnector;
import it.gruppoinit.pal.gp.pay.connector.payer.schema.AccountingData;
import it.gruppoinit.pal.gp.pay.connector.payer.schema.AnagraficaPagatore;
import it.gruppoinit.pal.gp.pay.connector.payer.schema.CommitNotifica;
import it.gruppoinit.pal.gp.pay.connector.payer.schema.EnteDestinatario;
import it.gruppoinit.pal.gp.pay.connector.payer.schema.EntiDestinatari;
import it.gruppoinit.pal.gp.pay.connector.payer.schema.EsitoCaricamentoPagamento;
import it.gruppoinit.pal.gp.pay.connector.payer.schema.EsitoCreaIuv;
import it.gruppoinit.pal.gp.pay.connector.payer.schema.EsitoPositivo;
import it.gruppoinit.pal.gp.pay.connector.payer.schema.EsitoRecuperaPagamento;
import it.gruppoinit.pal.gp.pay.connector.payer.schema.EsitoRecuproIuvDaRichiesta;
import it.gruppoinit.pal.gp.pay.connector.payer.schema.IdentificativoImporto;
import it.gruppoinit.pal.gp.pay.connector.payer.schema.ImportiContabili;
import it.gruppoinit.pal.gp.pay.connector.payer.schema.ImportoContabile;
import it.gruppoinit.pal.gp.pay.connector.payer.schema.Iuv;
import it.gruppoinit.pal.gp.pay.connector.payer.schema.Pagamento;
import it.gruppoinit.pal.gp.pay.connector.payer.schema.PagamentoInAttesaRequest;
import it.gruppoinit.pal.gp.pay.connector.payer.schema.PaymentData;
import it.gruppoinit.pal.gp.pay.connector.payer.schema.PaymentRequest;
import it.gruppoinit.pal.gp.pay.connector.payer.schema.ResponseErr;
import it.gruppoinit.pal.gp.pay.connector.payer.schema.ServiceData;
import it.gruppoinit.pal.gp.pay.connector.payer.schema.UserData;
import it.gruppoinit.pal.gp.pay.connector.payer.web.client.ApiClient;
import it.gruppoinit.pal.gp.pay.connector.payer.web.client.AutenticazioneHeaderGenerator;
import it.gruppoinit.pal.gp.pay.domain.PayConnectorConfigParams.ConfigParamNames;
import it.gruppoinit.pal.gp.pay.domain.PayConnectorWsEndpoint;
import it.gruppoinit.pal.gp.pay.domain.PayDettaglioImporti;
import it.gruppoinit.pal.gp.pay.domain.PayPagamenti;
import it.gruppoinit.pal.gp.pay.domain.PayPosizioniDebitorie;
import it.gruppoinit.pal.gp.pay.domain.PayProfiliEntiCreditori;
import it.gruppoinit.pal.gp.pay.domain.PayRegistrazioniContabili;
import it.gruppoinit.pal.gp.pay.domain.PaySessioniPagamento;
import it.gruppoinit.pal.gp.pay.domain.PaySoggettiDebitori;
import it.gruppoinit.pal.gp.pay.domain.PayStatoPagamenti.StatiPagamento;
import it.gruppoinit.pal.gp.pay.domain.TipiEvento;
import it.gruppoinit.pal.gp.pay.exception.PayConfigurationException;
import it.gruppoinit.pal.gp.pay.exception.PayException;
import it.gruppoinit.pal.gp.pay.parameters.IParameter;
import it.gruppoinit.pal.gp.pay.parameters.ParametroCodiceTassonomia;
import it.gruppoinit.pal.gp.pay.service.AvvisiPagoPAService;
import it.gruppoinit.pal.gp.pay.service.PagoPAService;
import it.gruppoinit.pal.gp.pay.service.PayConnectorConfigValuesService;
import it.gruppoinit.pal.gp.pay.service.PayPagamentiService;
import it.gruppoinit.pal.gp.pay.service.PayPosizioniDebitorieService;
import it.gruppoinit.pal.gp.pay.service.PayRichiesteService;
import it.gruppoinit.pal.gp.pay.service.PaySessioniPagamentoService;
import it.gruppoinit.pal.gp.pay.service.PosizioniDebitorieCommandService;
import it.gruppoinit.pal.gp.pay.service.helper.IUVHelper;
import it.gruppoinit.pal.gp.pay.service.helper.ParametriConnettoreHelper;
import it.gruppoinit.pal.gp.pay.service.helper.PayConfigurationHelper;
import it.gruppoinit.pal.gp.pay.service.helper.PopolamentoDatiHelper;
import it.gruppoinit.pal.gp.pay.service.helper.RTHelper;
import it.gruppoinit.pal.gp.pay.service.impl.PayPagamentiServiceImpl;
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
import it.gruppoinit.pal.gp.pay.ws.schema.StatoDocumentoType;
import it.gruppoinit.pal.gp.pay.ws.schema.StatoPagamentoType;
import it.gruppoinit.pal.gp.pay.ws.schema.StatoPosizioneType;
import it.gruppoinit.pal.gp.pay.ws.schema.TipoDocumentoType;
import it.gruppoinit.schemas.messages.utilitypagopa.BollettinopagopaRequest;

/**
 * Connettore di pagamento per il sistema PayER fase 1 che supporta solo il pagamento OTF
 * 
 * @author lion
 *
 */
public class PayerConnector extends AbstractPayConnector {

    private static final Logger log = LoggerFactory.getLogger(PayerConnector.class);
    private static final String FUNZIONE_PAGAMENTO = "PAGAMENTO";
    private static final String VALUTA_EUR = "EUR";
    private static final String SEPARATORE_CUSALE = "\r\n";
    public static final String FORM_PARAM_BUFFER = "buffer";
    public static final String YYYY_M_MDD_H_HMMSS = "yyyyMMddHHmmss";
    private static final String MSG_ERRORE_POSIZIONE_GIA_ANNULLATA = "risulta già revocata";
    private final String suffixUrl = "pagamenti/pagamento";
    @Autowired
    private PayConnectorConfigValuesService payConnectorConfigValuesService;
    @Autowired
    private PayPosizioniDebitorieService payPosizioniDebitorieService;
    @Autowired
    private PaySessioniPagamentoService paySessioniPagamentoService;
    @Autowired
    private PagoPAService pagoPAService;
    @Autowired
    private PayRichiesteService payRichiesteService;
    @Autowired
    private AvvisiPagoPAService avvisiPagoPAService;
    @Autowired
    private PayPagamentiService payPagamentiService;
    @Autowired
    private PosizioniDebitorieCommandService posizioniDebitorieCommandService;

    public static enum statoPagamentoType {

	IN_ATTESA("IN ATTESA"),
	IN_CORSO("IN CORSO"),
	PAGATO("PAGATO"),
	PAGATO_ALTRO_SISTEMA("PAGATO ALTRO SISTEMA"),
	SCADUTO("SCADUTO"),
	FALLITO("FALLITO"),
	CANCELLATO("CANCELLATO");

	private String value;

	private statoPagamentoType(String v) {

	    this.value = v;
	}
	
	

	public String getValue() {

	    return this.value;
	}
	
	public static statoPagamentoType fromString(String stato) {
	    for (statoPagamentoType tipo : values()) {
		if (tipo.getValue().equalsIgnoreCase(stato)) {
		    return tipo;
		}
	    }
	    return null;
	}
    }

    @Override
    public boolean supportaAttivaSessionePagamento() {

	return false;
    }

    @Override
    public boolean supportaModificaDataScadenza() {

	return true;
    }

    @Override
    public boolean supportaPagamentoOnTheFly() {

	return true;
    }

    @Override
    public boolean supportaRicevutaTelematica() {

	return false;
    }

    @Override
    public boolean supportaVerificaPagamento() {

	return true;
    }

    @Override
    public ElencoPosizioniDebitorieEsitoType registraPosizioniDebitorie(PosizioniDebitorieCommand datiRegistrazioniCommand) throws PayException {

	ElencoPosizioniDebitorieEsitoType retEsito = new ElencoPosizioniDebitorieEsitoType();
	ApiClient client = getCilent();
	for (PayRegistrazioniContabili payReg : datiRegistrazioniCommand.getRegistrazioniPosizioni()) {
	    //ottenere gli iuv dall'api  creaIUV
	    int tot_iuv = payReg.getPosizioniDebitorie().size();
	    Form f = new Form();
	    f.param("versione", "1");
	    f.param("tipo_risposta", "json");
	    f.param("tot_iuv", String.valueOf(tot_iuv));
	    try {
		log.debug("Chiamata creaIuv Payer l3 in corso ... ");
		EsitoCreaIuv esitoCreaIuv = client.call(this.getWsCaricamentoConfig().getEndpointUrl() + "IUV", "POST",
			MediaType.APPLICATION_FORM_URLENCODED_TYPE, f, EsitoCreaIuv.class);
		if (StringUtils.equalsIgnoreCase("Success", esitoCreaIuv.getCodiceRisposta())) {
		    log.debug("Chiamata creaIuv Payer l3 successo");
		    String urlRecuperoIuv = esitoCreaIuv.getUrl();
		    log.debug("Chiamata recupero informazioni iuv Payer l3 in corso ... ");
		    EsitoRecuproIuvDaRichiesta esitoRecuproIuvDaRichiesta = client.call("https://" + urlRecuperoIuv, "GET",
			    MediaType.APPLICATION_FORM_URLENCODED_TYPE, null, EsitoRecuproIuvDaRichiesta.class);
		    if (StringUtils.equalsIgnoreCase("Success", esitoRecuproIuvDaRichiesta.getCodiceRisposta())) {
			log.debug("Chiamata recupero informazioni iuv Payer l3 successo ");
			// int i = 0;
			for (PayPosizioniDebitorie payPos : payReg.getPosizioniDebitorie()) {
			    EsitoOperazionePosizioneDebitoriaType esitoPos = new EsitoOperazionePosizioneDebitoriaType();
			    esitoPos.setEsito(false);
			    esitoPos.setStato(StatoPagamentoType.CON_ERRORE);
			    PopolamentoDatiHelper.completaDatiDaPosizioneDebitoria(esitoPos, payPos,
				    payPosizioniDebitorieService.findRiferimentiClientByPosizioneDebitoria(payPos.getId().getCodice()));
			    PagamentoInAttesaRequest req = new PagamentoInAttesaRequest();
			    for (Iuv esitoIuv : esitoRecuproIuvDaRichiesta.getIuv()) {
				req.setCodiceAvviso(esitoIuv.getCodiceAvviso());
				req.setIuv(esitoIuv.getIuv());
			    }
			    popolaPagamentoInAttesa(payPos, req, false);
			    MultipartBody body = getRequestBody(req);
			    log.debug("Chiamata caricamento pagamento Payer l3 in corso ... ");
			    EsitoCaricamentoPagamento esitoCaricamentoPagamento = client.call(
				    this.getWsCaricamentoConfig().getEndpointUrl() + suffixUrl, "POST", MediaType.MULTIPART_FORM_DATA_TYPE, body,
				    EsitoCaricamentoPagamento.class);
			    if (StringUtils.equalsIgnoreCase("Success", esitoCaricamentoPagamento.getCodiceRisposta())) {
				log.debug("Chiamata caricamento pagamento Payer l3 successo ");
				String urlRecuperaPagamento = "https://" + esitoCaricamentoPagamento.getUrl();
				EsitoRecuperaPagamento esitoRecuperaPagamento = client.call(urlRecuperaPagamento, "GET",
					MediaType.APPLICATION_JSON_TYPE, null, EsitoRecuperaPagamento.class);
				if (esitoRecuperaPagamento.getPagamento() != null) {
				    for (Pagamento pagamento : esitoRecuperaPagamento.getPagamento()) {
					esitoPos.setIUV(pagamento.getIuv());
					esitoPos.setCodiceAvviso(pagamento.getCodiceAvviso());
				    }
				}
				esitoPos.setIdPosizione(BigInteger.valueOf(payPos.getId().getCodice()));
				esitoPos.setIdRegistrazioneContabile(BigInteger.valueOf(payPos.getRegistrazioneContabile().getId().getCodice()));
				esitoPos.setEsito(true);
				payPos.setCodiceAvviso(esitoPos.getCodiceAvviso());
				esitoPos.setQrCode(this.pagoPAService.generaQRCode(payPos));
				esitoPos.setStato(StatoPagamentoType.ATTIVATO_IN_PSP);
				esitoPos.setMessaggio(StatiPagamento.ATTIVATO_IN_PSP.description());
			    } else {
				esitoPos.setIdPosizione(BigInteger.valueOf(payPos.getId().getCodice()));
				esitoPos.setIdRegistrazioneContabile(BigInteger.valueOf(payPos.getRegistrazioneContabile().getId().getCodice()));
				esitoPos.setEsito(false);
				esitoPos.setStato(StatoPagamentoType.CON_ERRORE);
				esitoPos.setMessaggio("");
				log.error("Errore nel recupero della posizione: {}", payPos.getIdPosizionePsp());
			    }
			    retEsito.getEsitoPosizione().add(esitoPos);
			    //i++;
			}
		    } else {
			throw new PayException("Errore nel recuperare gli iuv generati");
		    }
		} else {
		    throw new PayException("Errore nella creazione iuv");
		}
	    } catch (Exception e) {
		log.error("ERRORE: {}", e);
		throw new PayException(e);
	    }
	}
	return retEsito;
    }

    private MultipartBody getRequestBody(PagamentoInAttesaRequest req) throws JAXBException, IOException {

	List<Attachment> attachments = new ArrayList<>();
	String json = JSONUtils.marshal(req, false);
	MultivaluedMap<String, String> formData = new MetadataMap<>();
	formData.add("Content-Type", "application/json");
	formData.add("Content-ID", "pagamento");
	formData.add("Content-Disposition", "form-data;name=\"pagamento\";filename=\"pagamento_in_attesa.json\"");
	Attachment att = new Attachment(formData, json);
	Attachment versione = new Attachment("versione", MediaType.TEXT_PLAIN, "1");
	Attachment tipoRisposta = new Attachment("tipo_risposta", MediaType.TEXT_PLAIN, "json");
	attachments.add(att);
	attachments.add(versione);
	attachments.add(tipoRisposta);
	return new MultipartBody(attachments, MediaType.MULTIPART_FORM_DATA_TYPE, true);
    }

    private ApiClient getCilent() {

	String chiave = payConnectorConfigValuesService.getValoreParametroConfigurazione(ConfigParamNames.PAYER_CLIENT_KEY);
	String secret = payConnectorConfigValuesService.getValoreParametroConfigurazione(ConfigParamNames.PAYER_CLIENT_SECRET);
	return new ApiClient(secret, chiave);
    }

    @Override
    public ElencoPosizioniDebitorieEsitoType annullaPosizioniDebitorie(PosizioniDebitorieCommand datiRegistrazioniCommand, boolean pagatoOffline)
	    throws PayException {

	ElencoPosizioniDebitorieEsitoType retEsito = new ElencoPosizioniDebitorieEsitoType();
	for (PayRegistrazioniContabili payReg : datiRegistrazioniCommand.getRegistrazioniPosizioni()) {
	    for (PayPosizioniDebitorie payPos : payReg.getPosizioniDebitorie()) {
		//popolamento request body per l'annullamento della posizione
		Form f = new Form();
		f.param("versione", "1");
		f.param("tipo_risposta", "json");
		EsitoOperazionePosizioneDebitoriaType esitoPos = new EsitoOperazionePosizioneDebitoriaType();
		PopolamentoDatiHelper.completaDatiDaPosizioneDebitoria(esitoPos, payPos,
			payPosizioniDebitorieService.findRiferimentiClientByPosizioneDebitoria(payPos.getId().getCodice()));
		try {
		    //invocazione api payer
		    ApiClient client = getCilent();
		    EsitoCaricamentoPagamento esitoCaricamentoPagamento = client.call(
			    this.getWsAnnullamentoConfig().getEndpointUrl() + suffixUrl + "/" + payPos.getIuv(), "DELETE",
			    MediaType.APPLICATION_FORM_URLENCODED_TYPE, f, EsitoCaricamentoPagamento.class);
		    if (StringUtils.equalsIgnoreCase("Success", esitoCaricamentoPagamento.getCodiceRisposta())) {
			esitoPos.setEsito(true);
			esitoPos.setStato(pagatoOffline ? StatoPagamentoType.PAGATO_OFFLINE_ANNULLATO : StatoPagamentoType.ANNULLATO);
			esitoPos.setMessaggio("Posizione debitoria annullata con successo");
		    } else {
			esitoPos.setEsito(false);
			esitoPos.setStato(StatoPagamentoType.ATTIVATO_IN_PSP);
			esitoPos.setMessaggio("Annullamento della posizione debitoria non riuscito");
		    }
		} catch (Exception e) {
		    log.error("annullaPosizioniDebitorie - Errore nell'invocazione di DigitBusFeed.RimuoviPosizione: ", e);
		    handleException(e, esitoPos);
		    if (checkErrorePosizioneGiaAnnullata(e)) {
			esitoPos.setEsito(true);
			esitoPos.setMessaggio("Posizione già annullata in precedenza");
		    }
		}
		retEsito.getEsitoPosizione().add(esitoPos);
	    }
	}
	return retEsito;
    }

    @Override
    public ElencoStatoPosizioniType verificaStatoPagamenti(RichiestaSuListaPosizioniCommand cmd) throws PayException {

	ElencoStatoPosizioniType retEsiti = new ElencoStatoPosizioniType();
	for (PayPosizioniDebitorie payPos : cmd.getPosizioni()) {
	    StatoPosizioneType retStatus = new StatoPosizioneType();
	    retStatus.setEsito(false);
	    PopolamentoDatiHelper.completaDatiDaPosizioneDebitoria(retStatus, payPos,
		    payPosizioniDebitorieService.findRiferimentiClientByPosizioneDebitoria(payPos.getId().getCodice()));
	    if (BooleanUtils.isTrue(payPos.getFlagOTF())) {
		// nel caso delle posizioni OTF non ho un servizio di verifica Stato e ritorno lo stato attuale
		retStatus.setEsito(true);
	    } else {
		try {
		    ApiClient client = getCilent();
		    EsitoRecuperaPagamento esitoRecuperaPagamento = client.call(
			    this.getWsVerificaConfig().getEndpointUrl() + suffixUrl + "/" + payPos.getIuv(), "GET", null, null,
			    EsitoRecuperaPagamento.class);
		    List<Pagamento> pagamenti = esitoRecuperaPagamento.getPagamento();
		    if (pagamenti != null) {
			for (Pagamento pagamento : pagamenti) {
			    if (pagamento.getStato().equals(statoPagamentoType.IN_ATTESA.value)
				    || pagamento.getStato().equals(statoPagamentoType.IN_CORSO.value)) {
				retStatus.setStato(StatoPagamentoType.ATTIVATO_IN_PSP);
				retStatus.setEsito(true);
			    } else if (pagamento.getStato().equals(statoPagamentoType.PAGATO.value)
				    || pagamento.getStato().equals(statoPagamentoType.PAGATO_ALTRO_SISTEMA.value)) {
				retStatus = this.scaricaRicevutaXmlWs(payPos, esitoRecuperaPagamento);
				if (retStatus.isEsito()) {
				    retStatus.setEsito(true);
				    retStatus.setStato(StatoPagamentoType.NOTIFICATO_DA_PSP);
				    this.payRichiesteService.registraRichiestaPerPosizioneDebitoriaTrans(payPos,
					    TipiEvento.RENDICONTAZIONE_PAGAMENTO_PSP);
				}
			    } else if (pagamento.getStato().equals(statoPagamentoType.CANCELLATO.value)) {
				retStatus.setStato(StatoPagamentoType.ANNULLATO);
				retStatus.setEsito(true);
			    }
			}
			retStatus.setEsito(true);
		    } else {
			retStatus.setMessaggio(
				"Errore nell'invocazione del servizio Payer L3 per la verifica dello stato del pagamento: identificativo di pagamento sconosciuto " +
					       payPos.getIuv());
		    }
		} catch (Exception e) {
		    log.error("verificaStatoPagamenti - Errore nell'invocazione di Payer L3: ", e);
		    retStatus.setMessaggio(
			    "Errore nell'invocazione del servizio Payer L3 per la verifica dello stato del pagamento: " + e.getMessage());
		    retStatus.setEsito(false);
		}
	    }
	    retEsiti.getStatoPosizioni().add(retStatus);
	}
	return retEsiti;
    }

    @Override
    public void modificaDataScadenzaPosizioneDebitoria(Integer idPosizioneDebitoria, Date nuovaDataScadenza) throws PayException {

	PayPosizioniDebitorie payPos = payPosizioniDebitorieService.findById(new PkId(idPosizioneDebitoria));
	log.debug("modificaDataScadenzaPagamentoAvviso prima di create il client PAYER L3 {}-{}", idPosizioneDebitoria, nuovaDataScadenza);
	log.debug("modificaDataScadenzaPagamentoAvviso popolo i dati del AggiornaAvvisoDati {}-{}", idPosizioneDebitoria, nuovaDataScadenza);
	PagamentoInAttesaRequest pagInAttesaReq = new PagamentoInAttesaRequest();
	this.popolaAggiornaDataScadenza(payPos, nuovaDataScadenza, pagInAttesaReq);
	log.debug("modificaDataScadenzaPagamento prima di invocare il ws AggiornaAvvisoDati {}-{}", idPosizioneDebitoria, nuovaDataScadenza);
	try {
	    ApiClient client = getCilent();
	    MultipartBody body = getRequestBody(pagInAttesaReq);
	    EsitoPositivo esitoPositivo = client.call(this.getWsCaricamentoConfig().getEndpointUrl() + suffixUrl + "/" + payPos.getIuv(), "POST",
		    MediaType.MULTIPART_FORM_DATA_TYPE, body, EsitoPositivo.class);
	    if (StringUtils.equalsIgnoreCase("Success", esitoPositivo.getCodiceRisposta())) {
		log.info("data scadenza per la posizione id " + payPos.getId().getCodice() + " è stato modificata con successo");
	    }
	} catch (Exception e) {
	    log.error("ERRORE: {}", e);
	}
    }

    @Override
    public AttivaPagamentoOnTheFlyResponseType attivaPagamentoOnTheFly(PosizioniDebitorieCommand cmd) throws PayException {

	AttivaPagamentoOnTheFlyResponseType resp = new AttivaPagamentoOnTheFlyResponseType();
	if (log.isInfoEnabled()) {
	    log.info("attivaPagamentoOnTheFly - elaborazione richiesta di attivazione pagamento on the fly in corso");
	}
	String k1 = this.payConnectorConfigValuesService.getValoreParametroConfigurazione(ConfigParamNames.PAYER_HASH_PRIMARY_KEY);
	if (StringUtils.isBlank(k1)) {
	    throw new PayConfigurationException(
		    "parametro" + ConfigParamNames.PAYER_HASH_PRIMARY_KEY.name() + " del connettore " + this.getConnectorName() + " non configurato");
	}
	String k2 = this.payConnectorConfigValuesService.getValoreParametroConfigurazione(ConfigParamNames.PAYER_HASH_SECONDARY_KEY);
	if (StringUtils.isBlank(k2)) {
	    throw new PayConfigurationException("parametro" + ConfigParamNames.PAYER_HASH_SECONDARY_KEY.name() + " del connettore " +
						this.getConnectorName() + " non configurato");
	}
	String urlNotifica = this.payConnectorConfigValuesService.getValoreParametroConfigurazione(ConfigParamNames.URL_RICEZIONE_NOTIFICHE);
	if (StringUtils.isBlank(urlNotifica)) {
	    throw new PayConfigurationException("parametro" + ConfigParamNames.URL_RICEZIONE_NOTIFICHE.name() + " del connettore " +
						this.getConnectorName() + " non configurato");
	}
	String codUfficio = this.payConnectorConfigValuesService.getValoreParametroConfigurazione(ConfigParamNames.PAYER_CODICE_UFFICIO);
	String tipoUfficio = this.payConnectorConfigValuesService.getValoreParametroConfigurazione(ConfigParamNames.PAYER_TIPO_UFFICIO);
	String codUtente = this.payConnectorConfigValuesService.getValoreParametroConfigurazione(ConfigParamNames.PAYER_CODICE_UTENTE);
	if (StringUtils.isBlank(codUtente)) {
	    throw new PayConfigurationException(
		    "parametro" + ConfigParamNames.PAYER_CODICE_UTENTE.name() + " del connettore " + this.getConnectorName() + " non configurato");
	}
	String tipoServizio = this.payConnectorConfigValuesService.getValoreParametroConfigurazione(ConfigParamNames.PAYER_TIPOLOGIA_SERVIZIO);
	if (StringUtils.isBlank(tipoServizio)) {
	    throw new PayConfigurationException("parametro" + ConfigParamNames.PAYER_TIPOLOGIA_SERVIZIO.name() + " del connettore " +
						this.getConnectorName() + " non configurato");
	}
	PayProfiliEntiCreditori ente = PayConfigurationHelper.getProfiloEnteCreditore();
	if (StringUtils.isBlank(ente.getIdAppPSP())) {
	    throw new PayConfigurationException(
		    "identificativo dell'applicazione (ID_APP_PSP) non configurato per il connettore " + this.getConnectorName());
	}
	if (StringUtils.isBlank(ente.getPayConnector().getUrlPortalePagamenti())) {
	    throw new PayConfigurationException("URL del portale dei pagamenti non configurato per il connettore " + this.getConnectorName());
	}
	PayConnectorWsEndpoint wsAttivaSessione = this.getWsAttivaSessioneConfig();
	if (wsAttivaSessione == null) {
	    throw new PayConfigurationException(
		    "servizio per il caricamento delle posizioni debitorie non configurato per il connettore " + this.getConnectorName());
	}
	String errMsg = null;
	Client payerClient = null;
	try {
	    payerClient = new Client(k1, k2, ente.getIdAppPSP());
	} catch (SedaExtException e) {
	    errMsg = "errore nella creazione del client per la comunicazione con PayER dovuto a: " + e.getMessage();
	    log.error("attivaPagamentoOnTheFly - " + errMsg);
	}
	/*
	 * se il client è stato inizializzato correttamente viene caricata una unica posizione debitoria 
	 * che somma gli importi di tutte le posizioni di tutte le registrazioni contabili passate in input
	 */
	BigDecimal importoPos = BigDecimal.ZERO;
	Integer anno = null;
	PaySoggettiDebitori sogg = null;
	StringBuilder sbCausale = new StringBuilder();
	String numeroDocumento = "";
	String codiceTassonomia = posizioniDebitorieCommandService.findValoreUnicoParametroFromCommand(cmd, new ParametroCodiceTassonomia());
	String idSessione = UUID.randomUUID().toString();
	for (PayRegistrazioniContabili reg : cmd.getRegistrazioniPosizioni()) {
	    for (PayPosizioniDebitorie pos : reg.getPosizioniDebitorie()) {
		//predispongo gli oggetti esito da restituire 
		EsitoOperazionePosizioneDebitoriaType esitoPos = new EsitoOperazionePosizioneDebitoriaType();
		numeroDocumento += pos.getId().getCodice() + "|";
		resp.getPosizioneInserita().add(esitoPos);
		pos.setIdPosizionePsp(idSessione);
		PopolamentoDatiHelper.completaDatiDaPosizioneDebitoria(esitoPos, pos,
			payPosizioniDebitorieService.findRiferimentiClientByPosizioneDebitoria(pos.getId().getCodice()));
		this.payPosizioniDebitorieService.aggiornaPosizioneDebitoria(pos, null, null);
		if (errMsg == null) {
		    //validazione 1: tutte le posizioni devono essere dello stasso anno
		    if (anno != null) {
			if (!anno.equals(pos.getAnno())) {
			    errMsg = "tutte le posizioni debitorie da pagare devono eessere riferite allo stesso anno";
			}
		    } else {
			anno = pos.getAnno();
		    }
		    //validazione 2: tutte le posizioni devono essere riferite allo stesso soggetto
		    if (errMsg == null) {
			if (sogg != null) {
			    if (!sogg.getCfPi().equals(pos.getSoggettoDebitore().getCfPi())) {
				errMsg = "tutte le posizioni debitorie da pagare devono essere riferite allo stesso soggetto debitore";
			    }
			} else {
			    sogg = pos.getSoggettoDebitore();
			}
		    }
		    //Eventuali altre validazioni specifiche per l'OTF
		}
		if (errMsg == null) {
		    for (PayDettaglioImporti dettImporto : pos.getDettagliImporto()) {
			importoPos = importoPos.add(dettImporto.getImporto());
		    }
		}
	    }
	    if (errMsg == null) {
		if (sbCausale.length() > 0) {
		    sbCausale.append(SEPARATORE_CUSALE);
		}
		sbCausale.append(reg.getDescrizione());
	    }
	    if (errMsg == null) {
		if (StringUtils.isBlank(codiceTassonomia)) {
		    errMsg = "é obbligatorio fornire il codice tassonomia";
		}
	    }
	}
	if (errMsg == null) {
	    //1. popolo la struttura dati del buffer coi dati della posizione da caricare
	    PaymentRequest req = new PaymentRequest();
	    req.setPortaleID(ente.getIdAppPSP());
	    req.setFunzione(FUNZIONE_PAGAMENTO);
	    req.setUrlDiRitorno(ente.getUrlEsitoPagamento() + "&idSessione=" + idSessione);
	    req.setUrlBack(ente.getUrlAnnullamentoPagamento() + "&idSessione=" + idSessione);
	    req.setUrlDiNotifica(urlNotifica);
	    UserData user = new UserData();
	    user.setIdentificativoUtente(sogg.getCfPi());
	    user.setEmailUtente(sogg.getEmail());
	    req.setUserData(user);
	    ServiceData servData = new ServiceData();
	    servData.setCodiceEnte(ente.getCfCodiceProfiloPSP());
	    servData.setCodiceUfficio(codUfficio);
	    servData.setTipoUfficio(tipoUfficio);
	    servData.setCodiceUtente(codUtente);
	    servData.setTipologiaServizio(tipoServizio);
	    servData.setNumeroDocumento(numeroDocumento);
	    servData.setNumeroOperazione(idSessione);
	    servData.setAnnoDocumento(anno);
	    servData.setValuta(VALUTA_EUR);
	    servData.setCodiceTassonomia(codiceTassonomia);
	    AccountingData accData = new AccountingData();
	    ImportiContabili importiContabili = new ImportiContabili();
	    ImportoContabile importo = new ImportoContabile();
	    importo.setIdentificativo(IdentificativoImporto.IC1);
	    importo.setValore(importoPos.multiply(new BigDecimal(100)).intValue());
	    importiContabili.getImportiContabili().add(importo);
	    accData.setImportiContabili(importiContabili);
	    servData.setImporto(importo.getValore());
	    req.setServiceData(servData);
	    EnteDestinatario ed = new EnteDestinatario();
	    ed.setCodiceEntePortaleEsterno(ente.getCfCodiceProfiloPSP());
	    ed.setDescrEntePortaleEsterno(ente.getAmministrazione().getAmministrazione());
	    ed.setCausale(sbCausale.toString());
	    ed.setValore(importo.getValore());
	    ed.setCodiceTassonomia(codiceTassonomia);
	    EntiDestinatari eds = new EntiDestinatari();
	    eds.getEnteDestinatario().add(ed);
	    accData.setEntiDestinatari(eds);
	    req.setAccountingData(accData);
	    req.setCommitNotifica(CommitNotifica.S);
	    //2. marshall XML della richiesta di pagamento OTF
	    String operazione = "marshall della richiesta di pagamento OTF";
	    try {
		String buffer = IOUtils.marshallObject(req);
		log.info("attivaPagamentoOnTheFly - struttura della richiesta da inviare a PayER: {}", buffer);
		//3. cripto il buffer con l'API di PayER
		operazione = "criptazione dei dati della richiesta da trasmettere a PayER";
		buffer = payerClient.getBufferPaymentRequest(buffer);
		log.info("attivaPagamentoOnTheFly - struttura della richiesta da inviare a PayER payerClient.getBufferPaymentRequest: {}", buffer);
		//registro pay io eventi
		operazione = "invio della richiesta di pagamento a PayER";
		//4. post HTTP del buffer coi dati della richiesta criptati verso PayER
		HttpClient httpCli = HttpClients.createDefault();
		HttpPost post = new HttpPost(wsAttivaSessione.getEndpointUrl());
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair(FORM_PARAM_BUFFER, buffer));
		post.setEntity(new UrlEncodedFormEntity(params, StandardCharsets.UTF_8));
		//PayER richiederebbe di trasmettere lo user agent originario da cui è partita la richiesta ma per ora non siamo in grado di passare questa informazione
		post.setHeader("User-Agent", "");
		operazione = "lettura del RID restituito da PayER";
		HttpResponse httpRes = httpCli.execute(post);
		if (httpRes.getStatusLine().getStatusCode() != HttpStatus.SC_OK || httpRes.getEntity() == null) {
		    throw new PayException("l'invio della richiesta di pagamento a PayER ha restituito esito " +
					   httpRes.getStatusLine().getStatusCode() + " " + httpRes.getStatusLine().getReasonPhrase(),
			    true);
		}
		String entity = EntityUtils.toString(httpRes.getEntity());
		if (httpRes.getEntity() == null || StringUtils.isBlank(entity)) {
		    throw new PayException("l'invio della richiesta di pagamento a PayER ha restituito un RID vuoto ", true);
		}
		//5. recupero il RID 
		log.info("entity  {}", entity);
		if (entity.indexOf("ResponseErr") > 0) {
		    ResponseErr err = (ResponseErr) IOUtils.unMarshallString(entity, ResponseErr.class);
		    throw new PayException(err.getError());
		}
		buffer = entity;
		log.info("attivaPagamentoOnTheFly - dati della richiesta di pagamento inviati correttamente a PayER. RID ottenuto: {}", buffer);
		//6. creo il buffer da restituire nei parametri della sessione di pagamento
		buffer = payerClient.getBufferRID(buffer);
		log.info("attivaPagamentoOnTheFly - dati della richiesta di pagamento inviati a PayER. RID ottenuto: {}", buffer);
		AttivaSessionePagamentoResponseType session = new AttivaSessionePagamentoResponseType();
		session.setEsito(true);
		String urlPagamenti = ente.getPayConnector().getUrlPortalePagamenti() + "?" + FORM_PARAM_BUFFER + "=" + buffer;
		session.setPayUrl(urlPagamenti);
		session.setIdSessione(idSessione);
		//poiché il buffer è un messaggio XML lo trasmettiamo in post per non consentirne la lettura dall'URL come parametro get
		session.setHttpMethodRequired(HttpMethodType.GET);
		//		FormParametersType formParams = new FormParametersType();
		//		FormParamType param = new FormParamType();
		//		param.setValue(buffer);
		//		param.setParamName(FORM_PARAM_BUFFER);
		//		formParams.getParam().add(param);
		//		session.setFormParams(formParams);
		resp.setSessionePagamento(session);
		//aggiorno i dati delle posizioni con l'id posizione psp e i dati degli esiti con lo stato della posizione
		for (EsitoOperazionePosizioneDebitoriaType esitoPos : resp.getPosizioneInserita()) {
		    esitoPos.setStato(StatoPagamentoType.ATTIVATO_IN_PSP);
		    esitoPos.setEsito(true);
		}
	    } catch (Exception e) {
		errMsg = "errore durante l'operazione di " + operazione;
		log.error("attivaPagamentoOnTheFly - " + errMsg, e);
		for (EsitoOperazionePosizioneDebitoriaType esitoPos : resp.getPosizioneInserita()) {
		    this.handleException(e, esitoPos);
		}
	    }
	} else {
	    for (EsitoOperazionePosizioneDebitoriaType esitoPos : resp.getPosizioneInserita()) {
		esitoPos.setStato(StatoPagamentoType.CON_ERRORE);
		esitoPos.setEsito(false);
		esitoPos.setMessaggio(errMsg);
	    }
	}
	return resp;
    }

    private void popolaPagamentoInAttesa(PayPosizioniDebitorie payPos, PagamentoInAttesaRequest req, boolean isAggiornadata) throws PayException {

	String codicetassonomia = posizioniDebitorieCommandService.findValoreUnicoParametroFromPosizioneDebitoria(payPos,
		new ParametroCodiceTassonomia());
	String idPosizionePsp = this.generaIdPosizioneDebitoria(payPos);
	AnagraficaPagatore anagraficaPagatore = new AnagraficaPagatore();
	PaySoggettiDebitori soggettoDebitore = payPos.getSoggettoDebitore();
	anagraficaPagatore.setCapPagatore(soggettoDebitore.getCap());
	anagraficaPagatore.setCodicePagatore(soggettoDebitore.getCfPi());
	anagraficaPagatore.setEmailPagatore(soggettoDebitore.getEmail());
	anagraficaPagatore.setIndirizzoPagatore(StringUtils.left(soggettoDebitore.getIndirizzoCompleto(), 70));
	anagraficaPagatore.setLocalitaPagatore(soggettoDebitore.getLocalita());
	String debitore = "";
	if (soggettoDebitore.getCfPi().length() == 16) {
	    debitore = soggettoDebitore.getNome() + " " + soggettoDebitore.getCognome();
	    anagraficaPagatore.setTipoCodicePagatore("CF");
	} else {
	    debitore = soggettoDebitore.getNome();
	    anagraficaPagatore.setTipoCodicePagatore("PI");
	}
	anagraficaPagatore.setNomeCognomePagatore(StringUtils.left(debitore, 50));
	anagraficaPagatore.setCellPagatore("");
	anagraficaPagatore.setStatoPagatore("");
	anagraficaPagatore.setNumeroCivicoPagatore(soggettoDebitore.getCivico());
	anagraficaPagatore.setProvinciaPagatore(soggettoDebitore.getProvincia());
	req.setAnagraficaPagatore(anagraficaPagatore);
	req.setCausaleVersamento(StringUtils.left(payPos.getRegistrazioneContabile().getDescrizione(), 105));
	req.setDataScadenza(Utilities.getDataRfc3339(payPos.getDataScadenza()));
	BigDecimal importo = new BigDecimal(0);
	for (PayDettaglioImporti payDetImp : payPos.getDettagliImporto()) {
	    importo = importo.add(payDetImp.getImporto());
	}
	req.setIdFlusso("");
	if (isAggiornadata) {
	    req.setDataInizio(Utilities.getDataRfc3339(payPos.getDataRegistrazione()));
	} else {
	    req.setDataInizio(Utilities.getDataRfc3339(new Date()));
	}
	req.setImportoVersamentoConPenale("");
	req.setNote("");
	req.setPosizione("");
	req.setCodiceTassonomia(codicetassonomia);
	req.setImportoVersamento(String.valueOf(importo));
	req.setNumeroDocumento(idPosizionePsp);
	req.setNumeroOperazione(String.valueOf(payPos.getId().getCodice()));
	req.setStato(statoPagamentoType.IN_ATTESA.getValue());
	if (req.getIuv() == null && req.getCodiceAvviso() == null) {
	    req.setCodiceAvviso(payPos.getCodiceAvviso());
	    req.setIuv(payPos.getIuv());
	}
	req.setRata(0);
	req.setScadenzaBloccante("N");
	if (log.isDebugEnabled()) {
	    log.debug(IOUtils.marshallObject(req));
	}
    }

    private boolean checkErrorePosizioneGiaAnnullata(Throwable sfe) {

	boolean annullato = false;
	if (sfe != null && sfe instanceof SOAPFaultException && sfe.getMessage().contains(MSG_ERRORE_POSIZIONE_GIA_ANNULLATA)) {
	    annullato = true;
	}
	return annullato;
    }

    private StatoPosizioneType scaricaRicevutaXmlWs(PayPosizioniDebitorie payPos, EsitoRecuperaPagamento esitoRecuperaPagamento) {

	StatoPosizioneType esito = new StatoPosizioneType();
	PopolamentoDatiHelper.completaDatiDaPosizioneDebitoria(esito, payPos,
		payPosizioniDebitorieService.findRiferimentiClientByPosizioneDebitoria(payPos.getId().getCodice()));
	try {
	    if (StringUtils.isNotBlank(esitoRecuperaPagamento.getRt())) {
		DatiPagamentoType datiPag = RTHelper.popolaDatiPagamentoDaReceiptORicevutaTelematica(esitoRecuperaPagamento.getRt(), null);
		DataSource ds = new ByteArrayDataSource(esitoRecuperaPagamento.getRt().getBytes(),
			ContentType.APPLICATION_OCTET_STREAM.getMimeType());
		DataHandler ricevutaXML = new DataHandler(ds);
		datiPag.setRicevutaXml(ricevutaXML);
		esito.setEsito(true);
		esito.setDatiPagamento(datiPag);
		esito.setStato(StatoPagamentoType.RENDICONTATO_DA_IC);
		if (log.isInfoEnabled()) {
		    log.info(" i dati della ricevuta XML sono stati a recuperati per la posizione " + PkId.toStringId(payPos.getId()));
		}
	    } else {
		esito.setEsito(false);
		esito.setMessaggio("i dati della ricevuta non sono disponibili");
		if (log.isInfoEnabled()) {
		    log.info(" i dati della ricevuta non sono disponibili");
		}
	    }
	} catch (Exception e) {
	    this.handleException(e, esito);
	    log.error("scaricaRicevutaWs", e);
	}
	return esito;
    }

    private void popolaAggiornaDataScadenza(PayPosizioniDebitorie payPos, Date nuovaDataScadenza, PagamentoInAttesaRequest req) throws PayException {

	// PayProfiliEntiCreditori profiloEnte = PayConfigurationHelper.getProfiloEnteCreditore();
	popolaPagamentoInAttesa(payPos, req, true);
	req.setDataScadenza(Utilities.getDataRfc3339(nuovaDataScadenza));
    }

    public static void main(String[] args) {

	PagamentoInAttesaRequest req2 = new PagamentoInAttesaRequest();
	req2.setCausaleVersamento("causale");
	System.out.println(IOUtils.marshallObject(req2));
	String pData = "<PaymentData><PortaleID>URFSER</PortaleID><NumeroOperazione>65e0d3c1-f1b6-4717-816d-34095741710f</NumeroOperazione><IDOrdine>b32e61ce-9065-4492-89bf-a932f663b4ad</IDOrdine><DataOraOrdine>20210503152554</DataOraOrdine><IDTransazione/><DataOraTransazione>10000101000000</DataOraTransazione><SistemaPagamento>PY</SistemaPagamento><SistemaPagamentoD>PayER</SistemaPagamentoD><CircuitoAutorizzativo>NNPA</CircuitoAutorizzativo><CircuitoAutorizzativoD> Nodo Nazionale Pagamenti</CircuitoAutorizzativoD><ImportoTransato>100</ImportoTransato><ImportoCommissioni>0</ImportoCommissioni><ImportoCommissioniEnte>0</ImportoCommissioniEnte><Esito>KO</Esito><EsitoD>Autorizzazione negata dal circuito</EsitoD><Autorizzazione/><DatiSpecifici/></PaymentData>";
	Object unMarshallString = IOUtils.unMarshallString(pData, PaymentData.class);
	PaymentData data = (PaymentData) unMarshallString;
	if (StringUtils.isNotBlank(data.getDataOraOrdine())) {
	    Date d = Utilities.getDate(data.getDataOraOrdine(), YYYY_M_MDD_H_HMMSS);
	    Utilities.getXMLGregorianCalendar(d);
	}
	String a = "<ResponseErr><error><![CDATA[com.seda.payer.ext - <ErrorsPaymentRequest><err>Xml node \"ServiceData/NumeroDocumento\" value has invalid lenght. Maximum lenght: 20</err></ErrorsPaymentRequest>]]></error><RID>11dc4157-9040-4453-a5a6-6bef56b52515</RID></ResponseErr>";
	unMarshallString = IOUtils.unMarshallString(a, ResponseErr.class);
	PaymentRequest req = new PaymentRequest();
	req.setPortaleID("portale");
	req.setFunzione(FUNZIONE_PAGAMENTO);
	req.setUrlDiRitorno("");
	req.setUrlBack("");
	req.setUrlDiNotifica("");
	UserData user = new UserData();
	user.setIdentificativoUtente("");
	user.setEmailUtente("");
	req.setUserData(user);
	ServiceData servData = new ServiceData();
	servData.setCodiceEnte("");
	servData.setCodiceUfficio("");
	servData.setTipoUfficio("");
	servData.setCodiceUtente("");
	servData.setTipologiaServizio("");
	servData.setValuta(VALUTA_EUR);
	req.setServiceData(servData);
	AccountingData accData = new AccountingData();
	ImportiContabili importiContabili = new ImportiContabili();
	ImportoContabile importo = new ImportoContabile();
	importo.setIdentificativo(IdentificativoImporto.IC1);
	importo.setValore(100);
	importiContabili.getImportiContabili().add(importo);
	accData.setImportiContabili(importiContabili);
	EnteDestinatario ed = new EnteDestinatario();
	ed.setCodiceEntePortaleEsterno("");
	ed.setDescrEntePortaleEsterno("");
	ed.setCausale("");
	ed.setValore(importo.getValore());
	EntiDestinatari eds = new EntiDestinatari();
	eds.getEnteDestinatario().add(ed);
	accData.setEntiDestinatari(eds);
	req.setAccountingData(accData);
	req.setCommitNotifica(CommitNotifica.S);
	//2. marshall XML della richiesta di pagamento OTF
	// String operazione = "marshall della richiesta di pagamento OTF";
	String buffer = IOUtils.marshallObject(req);
	System.out.println(buffer);
    }

    @Override
    public PaySessioniPagamento gestisciEsitoSessione(Map<String, String[]> reqParams) {

	log.debug("gestisciEsitoSessione ");
	List<PaySessioniPagamento> sex = new ArrayList<>();
	String[] idSessioneVals = reqParams.get("idSessione");
	String idSessione = null;
	String esito = null;
	if (idSessioneVals != null && idSessioneVals.length > 0) {
	    idSessione = idSessioneVals[0];
	}
	log.debug("gestisciEsitoSessione.idSessioneVals {}", idSessione);
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

    @Override
    public void validateRichiestaInserimentoPosizioniDebitorie(RegistrazioneContabileType registrazioneContabile)
	    throws ValidazionePosizioniDebitorieException {

	// TODO Auto-generated method stub
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

    public void notificaPagamentoDaPayer(String rt, String iuv, String chiave, String firma, String dataOra) throws PayException {

	log.debug("notificaPagamentoDaPayer iuv: {}, chiave: {}, firma: {}, dataOra: {}", new Object[] { iuv, chiave, firma, dataOra });
	verificaChiamataNotifica(chiave, firma, dataOra);
	if (StringUtils.isBlank(rt) || StringUtils.isBlank(iuv)) {
	    throw new PayException("Dati non corretti iu:" + iuv + ", rt: " + rt);
	}
	PayPosizioniDebitorie pos = this.payPosizioniDebitorieService.findByIUV(iuv);
	if (pos == null) {
	    log.error("notificaPagamentoDaPayer posizioni non trovata iuv: {}, chiave: {}, firma: {}, dataOra: {}",
		    new Object[] { iuv, chiave, dataOra });
	    throw new PayException("Posizione debitoria non trovata con iuv " + iuv);
	}
	String rtDecoded = null;
	StatoPagamentoType statoPag = StatoPagamentoType.RENDICONTATO_DA_IC;
	log.debug("notificaPagamentoDaPayer: rt {}", rt);
	rtDecoded = Utilities.decodeBase64Binary(rt);
	log.debug("notificaPagamentoDaPayer: rtDecoded {}", rtDecoded);
	DatiPagamentoType datiPagXml = RTHelper.popolaDatiPagamentoDaReceiptORicevutaTelematica(rtDecoded, null);
	PayPagamenti datiPag = PayPagamentiServiceImpl.populateDomainObject(datiPagXml, pos, statoPag);
	this.payPagamentiService.registraAvvenutoPagamento(datiPag, pos, rtDecoded);
    }

    private void verificaChiamataNotifica(String chiave, String firma, String dataOra) throws PayException {

	PayConnectorWsEndpoint wsNotificaConfig = getWsRicevutaConfig();
	if (wsNotificaConfig == null) {
	    throw new PayException("Non è stato configurato l'indirizzo del ws notifica.");
	}
	if (!chiave.equals(wsNotificaConfig.getUtente())) {
	    throw new PayException("La chiamata non è ammessa credenziali non valide");
	}
	// TODO VALIDARE la firma
	// APIKEY + APISECRET + int(dataora-richiesta)
	try {
	    AutenticazioneHeaderGenerator.validaChiamataFirma(chiave, wsNotificaConfig.getPassword(), dataOra, firma);
	} catch (Exception e) {
	    throw new PayException(e);
	}
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
