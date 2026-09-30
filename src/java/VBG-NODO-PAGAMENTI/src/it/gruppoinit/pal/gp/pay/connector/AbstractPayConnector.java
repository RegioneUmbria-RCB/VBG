/**
 * 
 */
package it.gruppoinit.pal.gp.pay.connector;

import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.net.UnknownServiceException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.xml.ws.WebServiceException;
import javax.xml.ws.soap.SOAPFaultException;

import org.apache.commons.lang.StringUtils;
import org.apache.cxf.configuration.security.AuthorizationPolicy;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transport.http.HTTPException;
import org.apache.cxf.transport.http.auth.HttpAuthHeader;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.pay.command.GenerazioneFattureCommand;
import it.gruppoinit.pal.gp.pay.command.PosizioniDebitorieCommand;
import it.gruppoinit.pal.gp.pay.command.RichiestaSuListaPosizioniCommand;
import it.gruppoinit.pal.gp.pay.domain.PayConnectorConfig;
import it.gruppoinit.pal.gp.pay.domain.PayConnectorWsEndpoint;
import it.gruppoinit.pal.gp.pay.domain.PayPosizioniDebitorie;
import it.gruppoinit.pal.gp.pay.exception.PayConfigurationException;
import it.gruppoinit.pal.gp.pay.exception.PayException;
import it.gruppoinit.pal.gp.pay.exception.PayInvalidRequestException;
import it.gruppoinit.pal.gp.pay.scheduler.IServizioSchedulato;
import it.gruppoinit.pal.gp.pay.scheduler.ServiziSchedulatiEnum;
import it.gruppoinit.pal.gp.pay.scheduler.ServizioSchedulatoCaricamentoMassivo;
import it.gruppoinit.pal.gp.pay.scheduler.ServizioSchedulatoTracciati;
import it.gruppoinit.pal.gp.pay.service.ConfigurazionePagamentiService;
import it.gruppoinit.pal.gp.pay.service.PayConnectorConfigValuesService;
import it.gruppoinit.pal.gp.pay.service.PayPagamentiService;
import it.gruppoinit.pal.gp.pay.service.PayPosizioniDebitorieService;
import it.gruppoinit.pal.gp.pay.service.PayProfiliEntiCreditoriService;
import it.gruppoinit.pal.gp.pay.service.helper.IUVHelper;
import it.gruppoinit.pal.gp.pay.service.helper.PayConfigurationHelper;
import it.gruppoinit.pal.gp.pay.service.impl.ElaborazioneCaricamentoMassivoServiceImpl;
import it.gruppoinit.pal.gp.pay.service.impl.ElaborazioneTraccatiPagoPAServiceImpl;
import it.gruppoinit.pal.gp.pay.ws.schema.AttivaPagamentoOnTheFlyResponseType;
import it.gruppoinit.pal.gp.pay.ws.schema.AttivaSessionePagamentoResponseType;
import it.gruppoinit.pal.gp.pay.ws.schema.ElencoDocumentiEsitoType;
import it.gruppoinit.pal.gp.pay.ws.schema.ElencoStatoPosizioniType;
import it.gruppoinit.pal.gp.pay.ws.schema.EsitoOperazionePosizioneDebitoriaType;
import it.gruppoinit.pal.gp.pay.ws.schema.FormParametersType;
import it.gruppoinit.pal.gp.pay.ws.schema.StatoPagamentoType;

/**
 * Implementazione di base dei connettori del nodo dei pagamenti. Definisce tutti i setter e getter necessari per
 * l'inizializzazione del connettore.
 * 
 * @author francol
 *
 */
public abstract class AbstractPayConnector implements IPayConnector {

    private static final Logger log = LoggerFactory.getLogger(AbstractPayConnector.class);
    private String wsEndpointUrl;
    private String wsUser;
    private String wsPassword;
    private Integer wsTimeout;
    private String idInstallazione;
    private PayConnectorWsEndpoint wsCaricamento;
    private PayConnectorWsEndpoint wsAnnullamento;
    private PayConnectorWsEndpoint wsVerifica;
    private PayConnectorWsEndpoint wsAttivaSessione;
    private PayConnectorWsEndpoint wsAvviso;
    private PayConnectorWsEndpoint wsSecurity;
    private PayConnectorWsEndpoint wsNotifica;
    private PayConnectorWsEndpoint wsRicevuta;
    private PayConnectorWsEndpoint wsFattura;
    private PayConnectorWsEndpoint wsIuv;
    private PayConnectorWsEndpoint wsCaricamentoMassivo;
    private PayConnectorWsEndpoint wsSincronizzaDebitiPerSoggetto;
    private String name;
    private String code;
    @Autowired
    private PayConnectorConfigValuesService payConnectorConfigValuesService;
    @Autowired
    private PayPagamentiService payPagamentiService;
    @Autowired
    private PayProfiliEntiCreditoriService profiliEntiCreditoriService;
    @Autowired
    private ConfigurazionePagamentiService configurazionePagamentiService;
    @Autowired
    private PayPosizioniDebitorieService payPosizioniDebitorieService;

    @Override
    @Deprecated
    public String getWsEndpointUrl() {

	return wsEndpointUrl;
    }

    @Override
    @Deprecated
    public void setWsEndpointUrl(String wsEndpointUrl) {

	this.wsEndpointUrl = wsEndpointUrl;
    }

    @Override
    @Deprecated
    public String getWsUser() {

	return wsUser;
    }

    @Override
    @Deprecated
    public void setWsUser(String wsUser) {

	this.wsUser = wsUser;
    }

    @Override
    @Deprecated
    public String getWsPassword() {

	return wsPassword;
    }

    @Override
    @Deprecated
    public void setWsPassword(String wsPassword) {

	this.wsPassword = wsPassword;
    }

    @Override
    @Deprecated
    public Integer getWsTimeout() {

	return wsTimeout;
    }

    @Override
    @Deprecated
    public void setWsTimeout(Integer wsTimeout) {

	this.wsTimeout = wsTimeout;
    }

    @Override
    public String getIdInstallazione() {

	return idInstallazione;
    }

    @Override
    public void setIdInstallazione(String idInstallazione) {

	this.idInstallazione = idInstallazione;
    }

    /**
     * implementazione di base per la generazione dell'id sessione da ridefinire nelle sottoclassi se necessario
     */
    @Override
    public String generaIdMessaggio() {

	UUID uuid = UUID.randomUUID();
	return uuid.toString();
    }

    @Override
    public String generaIdPosizioneDebitoria(PayPosizioniDebitorie pos) {

	StringBuilder sb = new StringBuilder();
	if (pos != null && pos.getId() != null) {
	    if (StringUtils.isNotBlank(getIdInstallazione())) {
		sb.append(getIdInstallazione()).append(PkId.TO_STRING_ID_SEPARATOR);
	    }
	    sb.append(PkId.toStringId(pos.getId()));
	}
	return sb.toString();
    }

    public abstract IUVHelper generaIUV(PayPosizioniDebitorie pos, String identificativoCausalePerCalcolo);

    @Override
    public PkId parseIdPosizioneDebitoria(String posId) {

	PkId retId = null;
	if (StringUtils.isNotBlank(posId)) {
	    posId = posId.trim();
	    if (posId.startsWith(this.getIdInstallazione())) {
		posId = posId.substring(this.getIdInstallazione().length() + 1);
	    }
	    retId = PkId.fromStringId(posId);
	}
	return retId;
    }

    @Override
    public boolean supportaPagamentoOnTheFly() {

	return false;
    }

    @Override
    public boolean supportaVerificaPagamento() {

	return this.wsVerifica != null && StringUtils.isNotBlank(this.wsVerifica.getEndpointUrl());
    }

    @Override
    public boolean supportaRendicontazionePagamenti() {

	return this.wsNotifica != null && StringUtils.isNotBlank(this.wsNotifica.getEndpointUrl());
    }

    @Override
    public boolean supportaAvvisoPagamento() {

	return this.wsAvviso != null && StringUtils.isNotBlank(this.wsAvviso.getEndpointUrl());
    }

    @Override
    public boolean supportaRicevutaTelematica() {

	return this.wsRicevuta != null && StringUtils.isNotBlank(this.wsRicevuta.getEndpointUrl());
    }

    @Override
    public boolean supportaGenerazioneFattura() {

	return this.wsFattura != null && StringUtils.isNotBlank(this.wsFattura.getEndpointUrl());
    }

    /**
     * Di default la generazione della fattura non è ritenuta indispensabile per il corretto caricamento delle posizioni
     * debitorie. I connettori che invece hanno questa esigenza dovranno fare l'override del metodo in modo da
     * restituire true.
     */
    @Override
    public boolean isGenerazioneFatturaObbligatoria() {

	return false;
    }

    @Override
    public AttivaPagamentoOnTheFlyResponseType attivaPagamentoOnTheFly(PosizioniDebitorieCommand cmd) throws PayException {

	throw new UnsupportedOperationException(
		"Il pagamento on the fly delle posizioni debitorie non è supportato nel connettore " + getConnectorName());
    }

    @Override
    public ElencoStatoPosizioniType verificaStatoPagamenti(RichiestaSuListaPosizioniCommand cmd) throws PayException {

	throw new UnsupportedOperationException(
		"Il servizio di verifica dello stato del pagamento non supportata nel connettore " + getConnectorName());
    }

    @Override
    public ElencoStatoPosizioniType rendicontazionePagamenti(RichiestaSuListaPosizioniCommand cmd) throws PayException {

	throw new UnsupportedOperationException("Il servizio di rendicontazione dei pagamenti non è supportato nel connettore " + getConnectorName());
    }

    /**
     * implementazione di base dell'attivazione della sessione di pagamento che va bene per i connettori che non
     * necessitano di invocare specifici servizi per l'attivazione del pagamento. Se invece è necessario invocare un
     * servizio specifico o applicare logiche differenti il metodo dovrà essere ridefinito nelle sottoclassi.
     */
    @Override
    public AttivaSessionePagamentoResponseType attivaSessionePagamento(PayPosizioniDebitorie payPosizioneDebitoria) throws PayException {

	AttivaSessionePagamentoResponseType sessResp = new AttivaSessionePagamentoResponseType();
	if (payPosizioneDebitoria == null) {
	    throw new PayInvalidRequestException("Dati della richiesta di attivazione della sessione mancanti");
	}
	if (payPosizioneDebitoria.getId() == null || payPosizioneDebitoria.getId().getCodice() == null) {
	    throw new PayInvalidRequestException("Dati della posizione debitoria mancanti");
	}
	sessResp.setEsito(true);
	sessResp.setDescEsito("sessione di pagamento attivata");
	String idSessione = this.generaIdSessionePagamento(payPosizioneDebitoria);
	sessResp.setIdSessione(idSessione);
	sessResp.setSecurityDigest(this.generaDigestSessionePagamento(payPosizioneDebitoria, idSessione));
	sessResp.setFormParams(this.generaParametriSessionePagamento(payPosizioneDebitoria, idSessione, sessResp.getSecurityDigest()));
	sessResp.setPayUrl(PayConfigurationHelper.getProfiloEnteCreditore().getPayConnector().getUrlPortalePagamenti());
	return sessResp;
    }

    @Override
    public ElencoDocumentiEsitoType inviaAvvisiPagamento(PosizioniDebitorieCommand cmd) throws PayException {

	throw new UnsupportedOperationException("L'invio degli avvisi di pagamento non è supportato nel connettore " + getConnectorName());
    }

    @Override
    public ElencoDocumentiEsitoType generaFatture(GenerazioneFattureCommand cmd) throws PayException {

	throw new UnsupportedOperationException("La generazione delle fatture non è supportata nel connettore " + getConnectorName());
    }

    @Override
    public ElencoDocumentiEsitoType scaricaRicevuteTelematiche(RichiestaSuListaPosizioniCommand cmd) throws PayException {

	throw new UnsupportedOperationException(
		"Il download delle ricevute telematiche di pagamento non è supportato nel connettore " + getConnectorName());
    }

    @Override
    public PayConnectorWsEndpoint getWsCaricamentoConfig() {

	return this.wsCaricamento;
    }

    @Override
    public void setWsCaricamentoConfig(PayConnectorWsEndpoint config) {

	this.wsCaricamento = config;
    }

    @Override
    public void setWsAnnullamentoConfig(PayConnectorWsEndpoint config) {

	this.wsAnnullamento = config;
    }

    @Override
    public PayConnectorWsEndpoint getWsAnnullamentoConfig() {

	return this.wsAnnullamento;
    }

    @Override
    public void setWsVerificaConfig(PayConnectorWsEndpoint config) {

	this.wsVerifica = config;
    }

    @Override
    public PayConnectorWsEndpoint getWsVerificaConfig() {

	return this.wsVerifica;
    }

    @Override
    public void setWsAttivaSessioneConfig(PayConnectorWsEndpoint config) {

	this.wsAttivaSessione = config;
    }

    @Override
    public PayConnectorWsEndpoint getWsAttivaSessioneConfig() {

	return this.wsAttivaSessione;
    }

    @Override
    public void setWsAvvisoConfig(PayConnectorWsEndpoint config) {

	this.wsAvviso = config;
    }

    @Override
    public PayConnectorWsEndpoint getWsAvvisoConfig() {

	return this.wsAvviso;
    }

    @Override
    public void setWsSecurityConfig(PayConnectorWsEndpoint config) {

	this.wsSecurity = config;
    }

    @Override
    public PayConnectorWsEndpoint getWsSecurityConfig() {

	return this.wsSecurity;
    }

    @Override
    public void setWsNotificaConfig(PayConnectorWsEndpoint config) {

	this.wsNotifica = config;
    }

    @Override
    public PayConnectorWsEndpoint getWsNotificaConfig() {

	return this.wsNotifica;
    }

    @Override
    public void setWsRicevutaConfig(PayConnectorWsEndpoint config) {

	this.wsRicevuta = config;
    }

    @Override
    public PayConnectorWsEndpoint getWsRicevutaConfig() {

	return this.wsRicevuta;
    }

    @Override
    public void setWsFatturaConfig(PayConnectorWsEndpoint config) {

	this.wsFattura = config;
    }

    @Override
    public PayConnectorWsEndpoint getWsFatturaConfig() {

	return this.wsFattura;
    }

    @Override
    public void setWsIuvConfig(PayConnectorWsEndpoint config) {

	this.wsIuv = config;
    }

    @Override
    public PayConnectorWsEndpoint getWsIuvConfig() {

	return this.wsIuv;
    }

    public void setWsCaricamentoMassivoConfig(PayConnectorWsEndpoint config) {

	this.wsCaricamentoMassivo = config;
    }

    public PayConnectorWsEndpoint getWsCaricamentoMassivoConfig() {

	return this.wsCaricamentoMassivo;
    }

    @Override
    public String getConnectorName() {

	return this.name;
    }

    @Override
    public void setConnectorName(String conName) {

	this.name = conName;
    }

    @Override
    public String getConnectorCode() {

	return this.code;
    }

    @Override
    public void setConnectorCode(String code) {

	this.code = code;
    }
    
    @Override
    public void setWsSincronizzaDebitiPerSoggettoConfig(PayConnectorWsEndpoint config) {

	this.wsSincronizzaDebitiPerSoggetto = config;
    }

    @Override
    public PayConnectorWsEndpoint getWsSincronizzaDebitiPerSoggettoConfig() {

	return this.wsSincronizzaDebitiPerSoggetto;
    }

    protected String generaIdSessionePagamento(PayPosizioniDebitorie posDeb) throws PayException {

	String idPrefix = PkId.toStringId(posDeb.getId());
	UUID uuid = UUID.randomUUID();
	return idPrefix + "_" + uuid.toString();
    }

    protected FormParametersType generaParametriSessionePagamento(PayPosizioniDebitorie posDeb, String idSessione, String digest)
	    throws PayException {

	return new FormParametersType();
    }

    protected String generaDigestSessionePagamento(PayPosizioniDebitorie payPos, String idSessionePagamento) throws PayException {

	return null;
    }

    protected void handleException(Throwable exc, EsitoOperazionePosizioneDebitoriaType esitoOp) {

	if (exc != null && esitoOp != null) {
	    esitoOp.setEsito(false);
	    esitoOp.setStato(StatoPagamentoType.CON_ERRORE);
	    Throwable rootExc = this.getRootCause(exc);
	    StringBuilder sb = new StringBuilder("Errore");
	    if (rootExc instanceof SocketException || // 
		    rootExc instanceof UnknownHostException || // 
		    rootExc instanceof UnknownServiceException || // 
		    rootExc instanceof SocketTimeoutException || //
		    rootExc instanceof WebServiceException || //
		    rootExc instanceof MalformedURLException || rootExc instanceof HTTPException) {
		esitoOp.setErroreTemporaneo(true);
		sb.append(" temporaneo");
	    } else if (rootExc instanceof SOAPFaultException) {
		SOAPFaultException fault = (SOAPFaultException) rootExc;
		if (fault.getFault() != null) {
		    esitoOp.setCodiceErrore(fault.getFault().getFaultCode());
		}
	    } else if (exc instanceof PayException) {
		PayException payExc = (PayException) exc;
		esitoOp.setErroreTemporaneo(payExc.isResumable());
		esitoOp.setCodiceErrore(payExc.getErrorCode());
	    } else {
		log.error("Errore temporaneo non gestito " + esitoOp, rootExc);
		esitoOp.setErroreTemporaneo(true);
		sb.append(" temporaneo non gestito [").append(rootExc).append("]");
	    }
	    sb.append(": ").append(exc.toString());
	    esitoOp.setMessaggio(sb.toString());
	}
    }

    Throwable getRootCause(Throwable t) {

	while (t.getCause() != null) {
	    t = t.getCause();
	}
	return t;
    }

    protected AuthorizationPolicy getBasicAuthorization(String userName, String password) throws IllegalArgumentException {

	if (StringUtils.isBlank(userName) || StringUtils.isBlank(password)) {
	    throw new IllegalArgumentException("Parametri non validi");
	}
	AuthorizationPolicy authorizationPolicy = new AuthorizationPolicy();
	authorizationPolicy.setUserName(userName);
	authorizationPolicy.setPassword(password);
	authorizationPolicy.setAuthorizationType(HttpAuthHeader.AUTH_TYPE_BASIC);
	return authorizationPolicy;
    }

    @Override
    public void refreshWs(PayConnectorConfig cfg) {

	this.setWsCaricamentoConfig(cfg.getWsCaricamento());
	this.setWsAnnullamentoConfig(cfg.getWsAnnullamento());
	this.setWsVerificaConfig(cfg.getWsVerifica());
	this.setWsNotificaConfig(cfg.getWsNotifica());
	this.setWsAvvisoConfig(cfg.getWsAvviso());
	this.setWsFatturaConfig(cfg.getWsFattura());
	this.setWsRicevutaConfig(cfg.getWsRicevuta());
	this.setWsAttivaSessioneConfig(cfg.getWsAttivaSessione());
	this.setWsSecurityConfig(cfg.getWsSecurity());
	this.setWsIuvConfig(cfg.getWsIUV());
	this.setWsCaricamentoMassivoConfig(cfg.getWsCaricamentoMassivo());
	this.setWsSincronizzaDebitiPerSoggettoConfig(cfg.getWsSincronizzaDebitiPerSoggetto());
    }

    public boolean supportaPagamentoOffLine() {

	return true;
    }

    public boolean supportaRataUnica() {

	return false;
    }

    public abstract boolean supportaAttivaSessionePagamento();

    public abstract boolean supportaModificaDataScadenza();

    public boolean supportaModificaDataFineValidita() {

	return false;
    }

    public abstract boolean supportaMolteCausaliRaggruppate();

    @Override
    public void modificaDataScadenzaPosizioneDebitoria(Integer idPosizioneDebitoria, Date nuovaDataScadenza) throws PayException {

	// non fa niente se il connettore lo supporta allora lo deve implementare
	throw new PayException("Metodo non implementato per il connettore. Verificare il metodo supportaModificaDataScadenza");
    }

    public void modificaDataFineValiditaPosizioneDebitoria(Integer idPosizioneDebitoria, Date nuovaDataFineValidita) throws PayException {

	// non fa niente se il connettore lo supporta allora lo deve implementare
	throw new PayException("Metodo non implementato per il connettore. Verificare il metodo supportaModificaDataFineValidita");
    }

    protected Object getWSPort(PayConnectorWsEndpoint cfg, Class<?> serviceClass) {

	JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
	factory.setServiceClass(serviceClass);
	factory.setAddress(cfg.getEndpointUrl());
	Object port = factory.create();
	if (cfg.getTimeout() != null) {
	    Client client = ClientProxy.getClient(port);
	    HTTPConduit conduit = (HTTPConduit) client.getConduit();
	    HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
	    // httpClientPolicy.setConnectionTimeout(default_connection_timeout); // default 30 sec
	    // <attribute name="ConnectionTimeout" type="{http://cxf.apache.org/configuration/parameterized-types}ParameterizedUInt" default="30000" />
	    // <attribute name="ReceiveTimeout" type="{http://cxf.apache.org/configuration/parameterized-types}ParameterizedUInt" default="60000" />
	    httpClientPolicy.setReceiveTimeout(cfg.getTimeout());
	    conduit.setClient(httpClientPolicy);
	}
	return port;
    }

    /**
     * In base a PayConnector_config_fk_ws_iuv: <BR/>
     * - se uguale a ws_caricamento allora ==> CONTESTUALE APERTURA <BR>
     * - se diverso da ws_caricamento ==> METODO_DEDICATO<BR>
     * - ALTRIMENTI INTERNA
     * 
     * @return
     */
    private GenerazioneIUV modalitaGenerazioneIUV() {

	if (this.getWsIuvConfig() == null) {
	    return GenerazioneIUV.INTERNA;
	}
	Integer codiceCaricamento = null;
	if (this.getWsCaricamentoConfig() != null && this.getWsCaricamentoConfig().getId() != null) {
	    codiceCaricamento = this.getWsCaricamentoConfig().getId().getId();
	}
	if (this.getWsIuvConfig().getId().getId().equals(codiceCaricamento)) {
	    return GenerazioneIUV.CONTESTUALE_APERTURA_POSIZIONE;
	}
	return GenerazioneIUV.METODO_DEDICATO;
    }

    @Override
    public String getIUV(PayPosizioniDebitorie payPos, String identificativoCausalePerCalcolo) {

	switch (modalitaGenerazioneIUV()) {
	case CONTESTUALE_APERTURA_POSIZIONE:
	    return null;
	default:
	    return generaIUV(payPos, identificativoCausalePerCalcolo).getIUV();
	}
    }

    @Override
    public List<ServiziSchedulatiEnum> getListaServiziSchedulatiSupportati() {

	return new ArrayList<>();
    }

    @Override
    public IServizioSchedulato getServizioSchedulatoPerTipo(ServiziSchedulatiEnum tipoServizio) {

	switch (tipoServizio) {
	case ELABORAZIONE_TRACCIATI_PAGOPA:
	    try {
		return new ServizioSchedulatoTracciati(new ElaborazioneTraccatiPagoPAServiceImpl(this, //
			payConnectorConfigValuesService //
			, payPagamentiService //
			, profiliEntiCreditoriService //
			, configurazionePagamentiService //
			, payPosizioniDebitorieService));
	    } catch (PayConfigurationException e) {
		throw new RuntimeException(e);
	    }
	case CARICAMENTO_MASSIVO_POSIZIONI:
	    try {
		return new ServizioSchedulatoCaricamentoMassivo(new ElaborazioneCaricamentoMassivoServiceImpl(this, //
			payConnectorConfigValuesService, //
			profiliEntiCreditoriService, // 
			configurazionePagamentiService));
	    } catch (PayConfigurationException e) {
		throw new RuntimeException(e);
	    }
	}
	throw new IllegalArgumentException("Tipo servizio non gestito: " + tipoServizio);
    }

    @Override
    public boolean supportaCaricamentoMassivo() {

	// di default i connettori non supportano il caricamento massivo
	return this.wsCaricamentoMassivo != null && StringUtils.isNotBlank(this.wsCaricamentoMassivo.getEndpointUrl());
    }
    
    @Override
    public boolean supportaSincronizzaDebitiPerSoggetto() {

	log.info("metodo supportaSincronizzaDebitiPerSoggetto");
	log.info("risultato: {}" , (this.wsSincronizzaDebitiPerSoggetto != null && StringUtils.isNotBlank(this.wsSincronizzaDebitiPerSoggetto.getEndpointUrl())));
	return this.wsSincronizzaDebitiPerSoggetto != null && StringUtils.isNotBlank(this.wsSincronizzaDebitiPerSoggetto.getEndpointUrl());
    }
}
