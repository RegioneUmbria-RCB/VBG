package it.gruppoinit.pal.gp.pay.connector.payer.web;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.seda.payer.ext.Client;

import it.gruppoinit.pal.gp.core.utils.IOUtils;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.pal.gp.pay.connector.payer.PayerConnector;
import it.gruppoinit.pal.gp.pay.connector.payer.schema.Commit;
import it.gruppoinit.pal.gp.pay.connector.payer.schema.CommitMsg;
import it.gruppoinit.pal.gp.pay.connector.payer.schema.Esito;
import it.gruppoinit.pal.gp.pay.connector.payer.schema.PaymentData;
import it.gruppoinit.pal.gp.pay.connector.payer.schema.ResponseErr;
import it.gruppoinit.pal.gp.pay.domain.PayConnectorConfigParams.ConfigParamNames;
import it.gruppoinit.pal.gp.pay.domain.PayConnectorWsEndpoint;
import it.gruppoinit.pal.gp.pay.domain.PayPosizioniDebitorie;
import it.gruppoinit.pal.gp.pay.domain.PayProfiliEntiCreditori;
import it.gruppoinit.pal.gp.pay.exception.PayConfigurationException;
import it.gruppoinit.pal.gp.pay.exception.PayException;
import it.gruppoinit.pal.gp.pay.service.ConfigurazionePagamentiService;
import it.gruppoinit.pal.gp.pay.service.PayConnectorConfigValuesService;
import it.gruppoinit.pal.gp.pay.service.PayConnectorService;
import it.gruppoinit.pal.gp.pay.service.PayPosizioniDebitorieService;
import it.gruppoinit.pal.gp.pay.service.PayProfiliEntiCreditoriService;
import it.gruppoinit.pal.gp.pay.service.PayStatoPagamentiService;
import it.gruppoinit.pal.gp.pay.service.helper.PayConfigurationHelper;
import it.gruppoinit.pal.gp.pay.service.helper.PopolamentoDatiHelper;
import it.gruppoinit.pal.gp.pay.ws.schema.DatiPagamentoType;
import it.gruppoinit.pal.gp.pay.ws.schema.StatoPagamentoType;
import it.gruppoinit.pal.gp.pay.ws.schema.StatoPosizioneType;

@Controller
public class PayerController {

    private static final Logger log = LoggerFactory.getLogger(PayerController.class);
    private static final int WINDOW_MINUTES = 10;
    @Autowired
    private PayConnectorConfigValuesService payConfigValuesService;
    @Autowired
    private PayProfiliEntiCreditoriService payProfiliEntiCreditoriService;
    @Autowired
    private PayPosizioniDebitorieService payPosizioniDebitorieService;
    @Autowired
    private PayStatoPagamentiService payStatoPagamentiService;
    @Autowired
    private ConfigurazionePagamentiService configurazionePagamentiService;
    @Autowired
    private PayConnectorService payConnectorService;

    @RequestMapping("/payer/notificaPagamento")
    public void notificaPagamento(@RequestParam("buffer") String buffer, @RequestParam("idProfilo") String idProfilo, Model model,
	    HttpServletRequest request, HttpServletResponse response) throws IOException {

	String operation = "ricezione notifica di pagamento da PayER in corso";
	log.info("notificaPagamento - {}", operation);
	log.info("buffer {}", buffer);
	log.info("idProfilo {}", idProfilo);
	String errMsg = null;
	String k1 = null;
	String k2 = null;
	PayProfiliEntiCreditori profilo = null;
	int minutes = WINDOW_MINUTES;
	try {
	    profilo = this.payProfiliEntiCreditoriService.findByCfCodiceProfilo(idProfilo);
	    this.configurazionePagamentiService.configuraRequestPerEnteCreditore(profilo);
	    PayConfigurationHelper.setProfiloEnteCreditore(profilo);
	    k1 = this.payConfigValuesService.getValoreParametroConfigurazione(ConfigParamNames.PAYER_HASH_PRIMARY_KEY);
	    if (StringUtils.isBlank(k1)) {
		throw new PayConfigurationException("parametro" + ConfigParamNames.PAYER_HASH_PRIMARY_KEY.name() + " del connettore " +
						    profilo.getPayConnector().getDescrizione() + " non configurato");
	    }
	    k2 = this.payConfigValuesService.getValoreParametroConfigurazione(ConfigParamNames.PAYER_HASH_SECONDARY_KEY);
	    if (StringUtils.isBlank(k2)) {
		throw new PayConfigurationException("parametro" + ConfigParamNames.PAYER_HASH_SECONDARY_KEY.name() + " del connettore " +
						    profilo.getPayConnector().getDescrizione() + " non configurato");
	    }
	    String minutesParam = this.payConfigValuesService.getValoreParametroConfigurazione(ConfigParamNames.PAYER_WINDOW_MINUTES);
	    if (NumberUtils.isDigits(minutesParam)) {
		minutes = Integer.parseInt(minutesParam);
	    }
	} catch (PayConfigurationException e1) {
	    errMsg = "id profilo ente non riconosciuto: " + idProfilo;
	}
	CommitMsg commit = new CommitMsg();
	if (errMsg == null) {
	    Client payerClient = null;
	    operation = "creazione del client per la comunicazione con PayER";
	    try {
		payerClient = new Client(k1, k2, profilo.getIdAppPSP());
		operation = "recupero dei dati del pagamento da PayER";
		String bufferPid = payerClient.getBufferPID(buffer);
		log.info("paymenbufferPidtXml {}", bufferPid);
		String bufferDaRichiedere = getBuffer(bufferPid);
		operation = "decifratura dei dati di pagamento criptati";
		String paymentXml = payerClient.getPaymentData(bufferDaRichiedere, minutes);
		log.info("notificaPagamento - recuperati dati di pagamento da payer per buffer {} : {}", buffer, paymentXml);
		operation = "unmarshall dei dati xml del pagamento";
		PaymentData data = (PaymentData) IOUtils.unMarshallString(paymentXml, PaymentData.class);
		commit.setIdOrdine(data.getIdOrdine());
		commit.setNumeroOperazione(data.getNumeroOperazione());
		commit.setPortaleId(data.getPortaleId());
		if (!data.getPortaleId().equals(profilo.getIdAppPSP())) {
		    errMsg = "ID portale non riconosciuto";
		} else {
		    List<PayPosizioniDebitorie> posizioni = this.payPosizioniDebitorieService.findAllByIdPosizionePSP(data.getNumeroOperazione());
		    log.info("notificaPagamento - identificate {} posizioni debitorie associate al numero operazione {}", posizioni.size(),
			    data.getNumeroOperazione());
		    if (posizioni.size() > 0) {
			if (data.getEsito().equals(Esito.OK)) {
			    for (PayPosizioniDebitorie pos : posizioni) {
				log.info("notificaPagamento - recuperata posizione debitoria {}", pos.getId());
				//aggiorno lo stato delle posizioni debitorie e salvo i dati di pagamento nel DB
				StatoPosizioneType esito = new StatoPosizioneType();
				log.info("notificaPagamento - prima di completaDatiPosizioneDebitoria {}", pos.getId());
				PopolamentoDatiHelper.completaDatiDaPosizioneDebitoria(esito, pos,
					payPosizioniDebitorieService.findRiferimentiClientByPosizioneDebitoria(pos.getId().getCodice()));
				log.info("notificaPagamento - dopo di completaDatiPosizioneDebitoria {}", pos.getId());
				esito.setStato(StatoPagamentoType.NOTIFICATO_DA_PSP);
				log.info("notificaPagamento - esito.setStato(StatoPagamentoType.NOTIFICATO_DA_PSP); {}", pos.getId());
				esito.setEsito(true);
				log.info("notificaPagamento - esito.setEsito(true); {}", pos.getId());
				DatiPagamentoType datiPag = new DatiPagamentoType();
				if (StringUtils.isNotBlank(data.getDataOraTransazione())) {
				    log.info("notificaPagamento - StringUtils.isNotBlank(data.getDataOraTransazione() {}", pos.getId());
				    Date d = Utilities.getDate(data.getDataOraTransazione(), PayerConnector.YYYY_M_MDD_H_HMMSS);
				    log.info("notificaPagamento - Date d {}", d);
				    datiPag.setDataOraPagamento(Utilities.getXMLGregorianCalendar(d));
				    datiPag.setDataOraAutorizzazione(datiPag.getDataOraPagamento());
				}
				if (StringUtils.isNotBlank(data.getDataOraOrdine())) {
				    log.info("notificaPagamento - StringUtils.isNotBlank(data.getDataOraOrdine() {}", pos.getId());
				    Date d = Utilities.getDate(data.getDataOraOrdine(), PayerConnector.YYYY_M_MDD_H_HMMSS);
				    log.info("notificaPagamento - Date d {}", d);
				    datiPag.setDataOraInizioTransazione(Utilities.getXMLGregorianCalendar(d));
				}
				log.info("notificaPagamento - data.getSistemaPagamento()");
				datiPag.setIdPSP(data.getSistemaPagamento());
				log.info("notificaPagamento - data.getSistemaPagamentoD()");
				datiPag.setRagioneSocialePSP(data.getSistemaPagamentoD());
				log.info("notificaPagamento - data.getCircuitoAutorizzativoD()");
				datiPag.setModalitaPagamento(data.getCircuitoAutorizzativoD());
				log.info("notificaPagamento - data.getIdTransazione()");
				datiPag.setRiferimentiPagamento(data.getIdTransazione());
				log.info("notificaPagamento - data.getAutorizzazione()");
				datiPag.setNote(data.getAutorizzazione());
				log.info("notificaPagamento - BigDecimal.valueOf(data.getImportoTransato(), 2)");
				BigDecimal importo = BigDecimal.valueOf(data.getImportoTransato(), 2);
				log.info("notificaPagamento - importo {}", importo);
				datiPag.setImportoPagato(importo);
				log.info("notificaPagamento - setImportoTransato {}", importo);
				datiPag.setImportoTransato(importo);
				int totCommissioni = 0;
				if (data.getImportoCommissioni() != null) {
				    log.info("notificaPagamento - 1 totCommissioni {}", totCommissioni);
				    totCommissioni = totCommissioni + data.getImportoCommissioni();
				    log.info("notificaPagamento - 2 totCommissioni {}", totCommissioni);
				}
				if (data.getImportoCommissioniEnte() != null) {
				    log.info("notificaPagamento - 3 totCommissioni {}", totCommissioni);
				    totCommissioni = totCommissioni + data.getImportoCommissioniEnte();
				    log.info("notificaPagamento - 4 totCommissioni {}", totCommissioni);
				}
				log.info("notificaPagamento - totCommissioni {}", totCommissioni);
				importo = BigDecimal.valueOf(totCommissioni, 2);
				log.info("notificaPagamento - setImportoCommissioni");
				datiPag.setImportoCommissioni(importo);
				log.info("notificaPagamento - setDatiPagamento");
				esito.setDatiPagamento(datiPag);
				log.info("notificaPagamento - prima di acquisire i dati di pagamento {}", pos.getId());
				this.payStatoPagamentiService.registraStatoPosizioneDebitoria(esito, pos);
				log.info("notificaPagamento - acquisiti i dati di pagamento e aggiornato lo stato per la posizione debitoria {}",
					pos.getId());
			    }
			} else if (data.getEsito().equals(Esito.KO)) {
			    for (PayPosizioniDebitorie pos : posizioni) {
				StatoPosizioneType esito = new StatoPosizioneType();
				PopolamentoDatiHelper.completaDatiDaPosizioneDebitoria(esito, pos,
					payPosizioniDebitorieService.findRiferimentiClientByPosizioneDebitoria(pos.getId().getCodice()));
				esito.setStato(StatoPagamentoType.ANNULLATO);
				esito.setEsito(false);
				esito.setMessaggio(data.getEsitoD());
				this.payStatoPagamentiService.registraStatoPosizioneDebitoria(esito, pos);
			    }
			    log.info("notificaPagamento - la notifica con errore ignorata perché l'esito ricevuto è {}", data.getEsitoD());
			} else if (data.getEsito().equals(Esito.OP)) {
			    log.info("notificaPagamento - la notifica sarà ignorata perché l'esito ricevuto è {}-{}", data.getEsito(),
				    data.getEsitoD());
			} else {
			    data.getEsito().equals(Esito.UK); // Transazione non presente
			    // errore?? 
			    log.info("notificaPagamento - la notifica sarà ignorata perché l'esito ricevuto è {}-{}", data.getEsito(),
				    data.getEsitoD());
			}
		    } else {
			errMsg = "nessuna posizione debitoria associata al numero operazione " + data.getNumeroOperazione();
		    }
		}
	    } catch (Exception e) {
		errMsg = "errore durante l'operazione di " + operation + " dovuto a: " + e.getMessage();
		log.error("notificaPagamento - {}", errMsg);
	    }
	}
	commit.setCommit(errMsg == null ? Commit.OK : Commit.NOK);
	String commitStr = IOUtils.marshallObject(commit);
	log.info("notificaPagamento - messaggio di commit restituito {}", commitStr);
	response.addHeader("Content-Type", "text/xml");
	response.getOutputStream().write(commitStr.getBytes());
    }

    private String getBuffer(String bufferPID) throws ClientProtocolException, IOException, PayException {

	HttpClient httpCli = HttpClients.createDefault();
	PayConnectorWsEndpoint wsNotificaConfig = this.payConnectorService.getPayConnectorInstance().getWsNotificaConfig();
	HttpPost post = new HttpPost(wsNotificaConfig.getEndpointUrl());
	List<NameValuePair> params = new ArrayList<NameValuePair>();
	params.add(new BasicNameValuePair(PayerConnector.FORM_PARAM_BUFFER, bufferPID));
	post.setEntity(new UrlEncodedFormEntity(params, StandardCharsets.UTF_8));
	post.setHeader("User-Agent", "");
	HttpResponse httpRes = httpCli.execute(post);
	if (httpRes.getStatusLine().getStatusCode() != HttpStatus.SC_OK || httpRes.getEntity() == null) {
	    throw new PayException("l'invio della richiesta di pagamento a PayER ha restituito esito " + httpRes.getStatusLine().getStatusCode() +
				   " " + httpRes.getStatusLine().getReasonPhrase(),
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
	return entity;
    }
}
