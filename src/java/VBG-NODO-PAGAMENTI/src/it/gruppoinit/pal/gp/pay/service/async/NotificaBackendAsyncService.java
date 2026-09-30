package it.gruppoinit.pal.gp.pay.service.async;

import java.math.BigInteger;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBException;

import org.apache.commons.lang.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.transport.http.HTTPConduit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.utils.JSONUtils;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.pal.gp.pay.domain.PayConnectorConfigParams.ConfigParamNames;
import it.gruppoinit.pal.gp.pay.domain.PayPagamenti;
import it.gruppoinit.pal.gp.pay.domain.PayPosizioniDebitorie;
import it.gruppoinit.pal.gp.pay.domain.PaySoggettiDebitori;
import it.gruppoinit.pal.gp.pay.domain.PayStatoPagamenti;
import it.gruppoinit.pal.gp.pay.exception.PayConfigurationException;
import it.gruppoinit.pal.gp.pay.service.ConfigurazionePagamentiService;
import it.gruppoinit.pal.gp.pay.service.PayConnectorConfigValuesService;
import it.gruppoinit.pal.gp.pay.service.PayPagamentiService;
import it.gruppoinit.pal.gp.pay.service.PayPosizioniDebitorieService;
import it.gruppoinit.pal.gp.pay.service.PaySoggettiDebitoriService;
import it.gruppoinit.pal.gp.pay.service.PayStatoPagamentiService;
import it.gruppoinit.pal.gp.pay.service.helper.PayConfigurationHelper;
import it.gruppoinit.pal.gp.pay.service.helper.PopolamentoDatiHelper;
import it.gruppoinit.pal.gp.pay.service.impl.PayPagamentiServiceImpl;
import it.gruppoinit.pal.gp.pay.ws.schema.DettaglioStatoPosizioneType;
import it.gruppoinit.pal.gp.pay.ws.schema.ElencoStatoPosizioniType;
import it.gruppoinit.pal.gp.pay.ws.schema.EsitoType;
import it.gruppoinit.pal.gp.pay.ws.schema.StatoPagamentoType;
import it.gruppoinit.pal.gp.pay.ws.schema.StatoPosizioneType;
import it.gruppoinit.pal.gp.pay.ws.schema.VerificaStatoPosizioniResponseType;
import it.gruppoinit.sigeprosecurity.ws.ISecurityClient;
import it.gruppoinit.sigeprosecurity.ws.SecurityConfig;

public class NotificaBackendAsyncService extends BaseAsync {

    protected static final Logger log = LoggerFactory.getLogger(NotificaBackendAsyncService.class);
    private Integer idPosizioneDebitoria;
    private String cfEnteCreditore;
    private PayConnectorConfigValuesService payConnectorConfigValuesService;
    private PayPosizioniDebitorieService payPosizioniDebitorieService;
    private PayPagamentiService payPagamentiService;
    private PayStatoPagamentiService payStatoPagamentiService;
    private PaySoggettiDebitoriService paySoggettiDebitoriService;
    private ConfigurazionePagamentiService configurazionePagamentiService;
    private ISecurityClient securityClient;

    public NotificaBackendAsyncService(ApplicationContext context, Integer idPosizioneDebitoria, String cfEnteCreditore) {

	this.applicationContext = context;
	this.idPosizioneDebitoria = idPosizioneDebitoria;
	this.cfEnteCreditore = cfEnteCreditore;
	this.payPosizioniDebitorieService = getBeanOfType(PayPosizioniDebitorieService.class);
	this.payConnectorConfigValuesService = getBeanOfType(PayConnectorConfigValuesService.class);
	this.payPagamentiService = getBeanOfType(PayPagamentiService.class);
	this.payStatoPagamentiService = getBeanOfType(PayStatoPagamentiService.class);
	this.configurazionePagamentiService = getBeanOfType(ConfigurazionePagamentiService.class);
	this.securityClient = getBeanOfType(ISecurityClient.class);
	this.paySoggettiDebitoriService = getBeanOfType(PaySoggettiDebitoriService.class);
    }

    @Override
    public String getIdOperazione() {

	return "NotificaBackendAsyncService[" + cfEnteCreditore + "-" + idPosizioneDebitoria + "-" + UUID.randomUUID().toString() + "]";
    }

    @Override
    public void process() throws AsyncProcessException {

	try {
	    configurazionePagamentiService.configuraRequestPerEnteCreditore(cfEnteCreditore);
	} catch (PayConfigurationException e) {
	    throw new AsyncProcessException(e);
	}
	String urlCallBack = this.payConnectorConfigValuesService.getValoreParametroConfigurazione(ConfigParamNames.URL_CALLBACK_CAMBIO_STATO);
	log.debug("{} - Inizio notifica posizione urlCallBack {}", idOperazione, urlCallBack);
	if (StringUtils.isEmpty(urlCallBack)) {
	    return;
	}
	log.debug("{} - verifico lo stato {}", idOperazione, idPosizioneDebitoria);
	VerificaStatoPosizioniResponseType stati = this.getStatoPagamenti(idPosizioneDebitoria);
	if (null == stati) {
	    log.warn("{} - Posizione debitoria {} non ACQUISITA ==> NON NOTIFICO IL MESSAGGIO", idOperazione, idPosizioneDebitoria);
	    return;
	}
	log.debug("{} - Notifica posizione {}, url da chiamare: {}", idOperazione, idPosizioneDebitoria, urlCallBack);
	SecurityConfig securityConfig = SecurityConfig.fromPayConnectorConfigValuesService(payConnectorConfigValuesService);
	String token = this.securityClient.loginAPP(securityConfig);
	WebClient client = WebClient.create(urlCallBack).path("{cf_ente_creditore}",
		PayConfigurationHelper.getProfiloEnteCreditore().getCfCodiceProfilo());
	HTTPConduit conduit = WebClient.getConfig(client).getHttpConduit();
	conduit.getClient().setConnectionTimeout(20000);
	conduit.getClient().setReceiveTimeout(20000);
	client.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
	client.header("Authorization", token);
	String body = null;
	try {
	    body = JSONUtils.marshal(stati, true);
	    log.debug("{} - Inizio notifica posizione {}, messaggio: {}", idOperazione, idPosizioneDebitoria, body);
	} catch (JAXBException e) {
	    throw new AsyncProcessException(e);
	}
	client.post(body);
	log.debug("{} - Fine notifica posizione {}:  {}", idOperazione, idPosizioneDebitoria, body);
    }

    private VerificaStatoPosizioniResponseType getStatoPagamenti(Integer idPosizioneDebitoria) {

	if (idPosizioneDebitoria == null) {
	    return null;
	}
	VerificaStatoPosizioniResponseType response = new VerificaStatoPosizioniResponseType();
	response.setEsito(EsitoType.OK);
	PayPosizioniDebitorie payPos = this.payPosizioniDebitorieService.findById(new PkId(idPosizioneDebitoria));
	StatoPosizioneType statoPos = new StatoPosizioneType();
	if (payPos == null) {
	    return null;
	}
	ElencoStatoPosizioniType elencoStati = new ElencoStatoPosizioniType();
	PopolamentoDatiHelper.completaDatiDaPosizioneDebitoria(statoPos, payPos,
		payPosizioniDebitorieService.findRiferimentiClientByPosizioneDebitoria(idPosizioneDebitoria));
	PayStatoPagamenti payStato = payStatoPagamentiService.getStatoPosizioneDebitoria(payPos);
	payPos.impostaStatoCorrente(payStato);
	statoPos.setStato(StatoPagamentoType.fromValue(payStato.getStato()));
	statoPos.setMessaggio(payStato.getDescStato());
	PayPagamenti payPagam = this.payPagamentiService.getPagamentoByPosizioneDebitoria(payPos);
	if (payPagam != null) {
	    PaySoggettiDebitori pagatore = null;
	    if (payPagam.getSoggettoPagatore() != null && payPagam.getSoggettoPagatore().getId() != null
		    && payPagam.getSoggettoPagatore().getId().getCodice() != null) {
		pagatore = this.paySoggettiDebitoriService.findById(new PkId(payPagam.getSoggettoPagatore().getId().getCodice()));
	    }
	    statoPos.setDatiPagamento(PayPagamentiServiceImpl.populateSchemaObject(payPagam, pagatore));
	}
	elencoStati.getStatoPosizioni().add(statoPos);
	List<StatoPosizioneType> sps = elencoStati.getStatoPosizioni();
	for (StatoPosizioneType spt : sps) {
	    BigInteger idPosizione = spt.getIdPosizione();
	    List<PayStatoPagamenti> cronologiaPosizioneDebitoria = payStatoPagamentiService.getCronologiaPosizioneDebitoria(idPosizione.intValue());
	    for (PayStatoPagamenti psp : cronologiaPosizioneDebitoria) {
		DettaglioStatoPosizioneType dsp = new DettaglioStatoPosizioneType();
		dsp.setStato(StatoPagamentoType.fromValue(psp.getStato()));
		dsp.setDescrizioneStato(psp.getDescStato());
		dsp.setDataStato(Utilities.getXMLGregorianCalendar(psp.getDataEvento()));
		spt.getCronologiaStatiPosizione().add(dsp);
	    }
	}
	response.setStatoPosizioni(elencoStati);
	if (elencoStati.getStatoPosizioni().isEmpty()) {
	    return null;
	}
	return response;
    }
}
