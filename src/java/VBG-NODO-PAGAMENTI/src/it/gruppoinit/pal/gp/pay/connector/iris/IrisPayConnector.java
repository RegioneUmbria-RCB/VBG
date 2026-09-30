/**
 * 
 */
package it.gruppoinit.pal.gp.pay.connector.iris;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.net.ssl.KeyManager;
import javax.net.ssl.TrustManager;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.cxf.configuration.jsse.TLSClientParameters;
import org.apache.cxf.configuration.security.AuthorizationPolicy;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.exceptions.ValidazionePosizioniDebitorieException;
import it.gruppoinit.pal.gp.core.utils.TrustAllX509TrustManager;
import it.gruppoinit.pal.gp.core.utils.TrustManagerUtils;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.pal.gp.pay.command.PosizioniDebitorieCommand;
import it.gruppoinit.pal.gp.pay.command.RichiestaSuListaPosizioniCommand;
import it.gruppoinit.pal.gp.pay.connector.AbstractPayConnector;
import it.gruppoinit.pal.gp.pay.connector.IPayConnector;
import it.gruppoinit.pal.gp.pay.connector.iris.ws.client.caricaposizioni.ComunicazionePosizioniDebitorieOTF;
import it.gruppoinit.pal.gp.pay.connector.iris.ws.client.caricaposizioni.IdpAllineamentoPendenzeEnteOTF;
import it.gruppoinit.pal.gp.pay.connector.iris.ws.client.caricaposizioni.IdpAllineamentoPendenzeEnteOTFEsito;
import it.gruppoinit.pal.gp.pay.connector.iris.ws.client.caricaposizioni.schema.Destinatari;
import it.gruppoinit.pal.gp.pay.connector.iris.ws.client.caricaposizioni.schema.Destinatario;
import it.gruppoinit.pal.gp.pay.connector.iris.ws.client.caricaposizioni.schema.DettaglioImporto;
import it.gruppoinit.pal.gp.pay.connector.iris.ws.client.caricaposizioni.schema.DettaglioPagamentoInsertReplace;
import it.gruppoinit.pal.gp.pay.connector.iris.ws.client.caricaposizioni.schema.IdpAllineamentoPendenzeOTF;
import it.gruppoinit.pal.gp.pay.connector.iris.ws.client.caricaposizioni.schema.IdpBody;
import it.gruppoinit.pal.gp.pay.connector.iris.ws.client.caricaposizioni.schema.Mittente;
import it.gruppoinit.pal.gp.pay.connector.iris.ws.client.caricaposizioni.schema.Pendenza;
import it.gruppoinit.pal.gp.pay.connector.iris.ws.client.caricaposizioni.schema.PendenzaInsertReplace;
import it.gruppoinit.pal.gp.pay.connector.iris.ws.client.caricaposizioni.schema.PendenzaInsertReplace.InfoPagamento;
import it.gruppoinit.pal.gp.pay.connector.iris.ws.client.caricaposizioni.schema.VoceImporto;
import it.gruppoinit.pal.gp.pay.connector.iris.ws.client.caricaposizioni.schema.esito.Dettaglio;
import it.gruppoinit.pal.gp.pay.connector.iris.ws.client.caricaposizioni.schema.esito.Esito;
import it.gruppoinit.pal.gp.pay.connector.iris.ws.client.caricaposizioni.schema.esito.IdpEsitoOTF;
import it.gruppoinit.pal.gp.pay.connector.iris.ws.client.caricaposizioni.schema.esito.InfoMessaggio;
import it.gruppoinit.pal.gp.pay.connector.iris.ws.client.caricaposizioni.schema.esito.StatoMessaggio;
import it.gruppoinit.pal.gp.pay.connector.iris.ws.client.caricaposizioni.schema.header.E2EReceiver;
import it.gruppoinit.pal.gp.pay.connector.iris.ws.client.caricaposizioni.schema.header.E2ESender;
import it.gruppoinit.pal.gp.pay.connector.iris.ws.client.caricaposizioni.schema.header.HeaderE2E;
import it.gruppoinit.pal.gp.pay.connector.iris.ws.client.caricaposizioni.schema.header.HeaderTRT;
import it.gruppoinit.pal.gp.pay.connector.iris.ws.client.caricaposizioni.schema.header.IdpHeader;
import it.gruppoinit.pal.gp.pay.connector.iris.ws.client.caricaposizioni.schema.header.IdpOTF;
import it.gruppoinit.pal.gp.pay.connector.iris.ws.client.caricaposizioni.schema.header.ServiceName;
import it.gruppoinit.pal.gp.pay.connector.iris.ws.client.caricaposizioni.schema.header.TRTReceiver;
import it.gruppoinit.pal.gp.pay.connector.iris.ws.client.caricaposizioni.schema.header.TRTSender;
import it.gruppoinit.pal.gp.pay.connector.iris.ws.client.caricaposizioni.schema.include.Divisa;
import it.gruppoinit.pal.gp.pay.connector.iris.ws.client.caricaposizioni.schema.include.StatoPagamento;
import it.gruppoinit.pal.gp.pay.connector.iris.ws.client.caricaposizioni.schema.include.StatoPendenza;
import it.gruppoinit.pal.gp.pay.connector.iris.ws.client.caricaposizioni.schema.include.TipoDestinatario;
import it.gruppoinit.pal.gp.pay.connector.iris.ws.client.caricaposizioni.schema.include.TipoOperazione;
import it.gruppoinit.pal.gp.pay.connector.iris.ws.client.caricaposizioni.schema.include.TipoPagamento;
import it.gruppoinit.pal.gp.pay.connector.iris.ws.client.generaiuv.GeneraIUVRequest;
import it.gruppoinit.pal.gp.pay.connector.iris.ws.client.generaiuv.GeneraIUVResponseType;
import it.gruppoinit.pal.gp.pay.connector.iris.ws.client.generaiuv.GenerazioneIUV;
import it.gruppoinit.pal.gp.pay.connector.iris.ws.client.generazioneavvisi.GeneraAvvisoRequest;
import it.gruppoinit.pal.gp.pay.connector.iris.ws.client.generazioneavvisi.GeneraAvvisoResponseType;
import it.gruppoinit.pal.gp.pay.connector.iris.ws.client.generazioneavvisi.GenerazioneAvvisi;
import it.gruppoinit.pal.gp.pay.connector.iris.ws.client.verificaposizioni.IdpVerificaStatoPagamenti;
import it.gruppoinit.pal.gp.pay.connector.iris.ws.client.verificaposizioni.IdpVerificaStatoPagamentiEsito;
import it.gruppoinit.pal.gp.pay.connector.iris.ws.client.verificaposizioni.VerificaStatoPagamento;
import it.gruppoinit.pal.gp.pay.connector.iris.ws.client.verificaposizioni.schema.IdPagamento;
import it.gruppoinit.pal.gp.pay.connector.iris.ws.client.verificaposizioni.schema.IdpVerificaStatoPagamento;
import it.gruppoinit.pal.gp.pay.connector.iris.ws.client.verificaposizioni.schema.Pagamento;
import it.gruppoinit.pal.gp.pay.connector.iris.ws.client.verificaposizioni.schema.Voce;
import it.gruppoinit.pal.gp.pay.connector.iris.ws.client.verificaposizioni.schema.esito.IdpEsitoVerifica;
import it.gruppoinit.pal.gp.pay.connector.iris.ws.client.verificaposizioni.schema.esito.InformazioniPagamentoType;
import it.gruppoinit.pal.gp.pay.connector.iris.ws.client.verificaposizioni.schema.esito.VerificaStatoPagamentoDettagliato;
import it.gruppoinit.pal.gp.pay.domain.PayConnectorConfigParams.ConfigParamNames;
import it.gruppoinit.pal.gp.pay.domain.PayConnectorWsEndpoint;
import it.gruppoinit.pal.gp.pay.domain.PayDettaglioImporti;
import it.gruppoinit.pal.gp.pay.domain.PayPosizioniDebitorie;
import it.gruppoinit.pal.gp.pay.domain.PayProfiliEntiCreditori;
import it.gruppoinit.pal.gp.pay.domain.PayRegistrazioniContabili;
import it.gruppoinit.pal.gp.pay.domain.PaySessioniPagamento;
import it.gruppoinit.pal.gp.pay.domain.PaySoggettiDebitori;
import it.gruppoinit.pal.gp.pay.domain.PayStatoPagamenti;
import it.gruppoinit.pal.gp.pay.domain.TipiEvento;
import it.gruppoinit.pal.gp.pay.exception.PayConfigurationException;
import it.gruppoinit.pal.gp.pay.exception.PayException;
import it.gruppoinit.pal.gp.pay.exception.PayInvalidRequestException;
import it.gruppoinit.pal.gp.pay.parameters.IParameter;
import it.gruppoinit.pal.gp.pay.parameters.ParametroChiaveApplicationCodeIUV;
import it.gruppoinit.pal.gp.pay.service.PagoPAService;
import it.gruppoinit.pal.gp.pay.service.PayConnectorConfigValuesService;
import it.gruppoinit.pal.gp.pay.service.PayPosizioniDebitorieService;
import it.gruppoinit.pal.gp.pay.service.PaySessioniPagamentoService;
import it.gruppoinit.pal.gp.pay.service.PayStatoPagamentiService;
import it.gruppoinit.pal.gp.pay.service.PosizioniDebitorieCommandService;
import it.gruppoinit.pal.gp.pay.service.helper.CodiceAvvisoHelper;
import it.gruppoinit.pal.gp.pay.service.helper.IUVHelper;
import it.gruppoinit.pal.gp.pay.service.helper.ParametriConnettoreHelper;
import it.gruppoinit.pal.gp.pay.service.helper.PayConfigurationHelper;
import it.gruppoinit.pal.gp.pay.service.helper.PopolamentoDatiHelper;
import it.gruppoinit.pal.gp.pay.service.helper.RiferimentiPosizioniDebitorieHelper;
import it.gruppoinit.pal.gp.pay.service.impl.PagoPAServiceImpl;
import it.gruppoinit.pal.gp.pay.ws.schema.AttivaPagamentoOnTheFlyResponseType;
import it.gruppoinit.pal.gp.pay.ws.schema.AttivaSessionePagamentoResponseType;
import it.gruppoinit.pal.gp.pay.ws.schema.DatiPagamentoType;
import it.gruppoinit.pal.gp.pay.ws.schema.ElencoDocumentiEsitoType;
import it.gruppoinit.pal.gp.pay.ws.schema.ElencoPosizioniDebitorieEsitoType;
import it.gruppoinit.pal.gp.pay.ws.schema.ElencoStatoPosizioniType;
import it.gruppoinit.pal.gp.pay.ws.schema.EsitoDocumentoPosizioneDebitoriaType;
import it.gruppoinit.pal.gp.pay.ws.schema.EsitoOperazionePosizioneDebitoriaType;
import it.gruppoinit.pal.gp.pay.ws.schema.FormParamType;
import it.gruppoinit.pal.gp.pay.ws.schema.FormParametersType;
import it.gruppoinit.pal.gp.pay.ws.schema.RegistrazioneContabileType;
import it.gruppoinit.pal.gp.pay.ws.schema.StatoDocumentoType;
import it.gruppoinit.pal.gp.pay.ws.schema.StatoPagamentoType;
import it.gruppoinit.pal.gp.pay.ws.schema.StatoPosizioneType;
import it.gruppoinit.pal.gp.pay.ws.schema.TipoDocumentoType;

/**
 * @author francol
 *
 */
public class IrisPayConnector extends AbstractPayConnector implements IPayConnector {

    private static final Logger log = LoggerFactory.getLogger(IrisPayConnector.class);
    private static final String RECEIVER_ID = "iris";
    private static final String RECEIVER_SYS = "SIL_iris_ITR";
    private static final String FORM_PARAM_TOKEN = "token";
    private static final String VERSIONE = "01.03-02";
    private static final String DEFAULT_TIPO_VOCE_IMPORTO = "ONERI";
    private static final String DEFAULT_CODICE_VOCE_IMPORTO = "000";
    private static final int ANNI_VALIDITA_PAGAMENTO = 10;
    private static final String IRIS_IUV_AUX_DIGIT = "001";
    private static final int default_connection_timeout = 12000;
    private ComunicazionePosizioniDebitorieOTF comunicazionePosizioniDebitorieOTFPort;
    private VerificaStatoPagamento verificaStatoPagamento;
    @Autowired
    private PayPosizioniDebitorieService payPosizioniDebitorieService;
    @Autowired
    private PagoPAService pagoPAService;
    @Autowired
    private PaySessioniPagamentoService paySessioniPagamentoService;
    @Autowired
    private PayStatoPagamentiService payStatoPagamentiService;
    @Autowired
    private PayConnectorConfigValuesService payConnectorConfigValuesService;
    @Autowired
    private PosizioniDebitorieCommandService posizioniDebitorieCommandService;

    @Override
    public ElencoPosizioniDebitorieEsitoType registraPosizioniDebitorie(PosizioniDebitorieCommand datiRegistrazioniCommand) throws PayException {

	return invocaAllineamentoPendenzeAsync(datiRegistrazioniCommand, false);
    }

    @Override
    public AttivaPagamentoOnTheFlyResponseType attivaPagamentoOnTheFly(PosizioniDebitorieCommand cmd) throws PayException {

	return invocaAllineamentoPendenzeSync(cmd, false);
    }

    @Override
    public ElencoStatoPosizioniType verificaStatoPagamenti(RichiestaSuListaPosizioniCommand cmd) throws PayException {

	VerificaStatoPagamento wsVerifica = this.getVerificaStatoPagamentoPort();
	IdpVerificaStatoPagamenti wsParam = this.popolaVerificaStatoPagamenti(cmd, PayConfigurationHelper.getProfiloEnteCreditore());
	IdpVerificaStatoPagamentiEsito esitoVerifica = wsVerifica.idpVerificaStatoPagamenti(wsParam);
	return popolaVerificaStatoPosizioniResponse(esitoVerifica, cmd);
    }

    @Override
    public ElencoPosizioniDebitorieEsitoType annullaPosizioniDebitorie(PosizioniDebitorieCommand datiRegistrazioniCommand, boolean pagatoOffline)
	    throws PayException {

	return invocaAllineamentoPendenzeAsync(datiRegistrazioniCommand, true);
    }

    @Override
    protected FormParametersType generaParametriSessionePagamento(PayPosizioniDebitorie posDeb, String idSessione, String digest)
	    throws PayException {

	FormParametersType form = new FormParametersType();
	FormParamType sessionParam = new FormParamType();
	sessionParam.setValue(idSessione);
	sessionParam.setParamName(FORM_PARAM_TOKEN);
	form.getParam().add(sessionParam);
	return form;
    }

    @Override
    public PaySessioniPagamento gestisciEsitoSessione(Map<String, String[]> reqParams) {

	log.debug("gestisciEsitoSessione");
	Boolean esito = null;
	String[] idSex = reqParams.get("id");
	String[] esParams = reqParams.get("esito");
	log.debug("gestisciEsitoSessione idPosizioneDebitoria: {}, esParams:  {}", idSex, esParams);
	if (idSex != null && idSex.length > 0) {
	    String id = idSex[0];
	    if (StringUtils.isNotBlank(id) && NumberUtils.isNumber(id)) {
		String idSessione = null;
		Integer idPosQs = Integer.parseInt(id);
		List<PaySessioniPagamento> sessioni = this.paySessioniPagamentoService.findSessioniAttivePerPosizioneDebitoria(idPosQs);
		if (!sessioni.isEmpty()) {
		    //se ci sono più sessioni aperte sulla stessa posizione viene aggirornato l'esito della più recente (creata per ultima)		    
		    idSessione = sessioni.get(0).getIdSessionePagamento();
		    log.debug("gestisciEsitoSessione idPosizioneDebitoria: {} sessione trovata {}", id, idSessione);
		}
		if (esParams != null && esParams.length > 0) {
		    esito = BooleanUtils.toBooleanObject(esParams[0]);
		    if (esito == null) {
			esito = BooleanUtils.toBooleanObject(esParams[0], "1", "0", null);
		    }
		}
		log.debug("gestisciEsitoSessione esito {} ", esito);
		if (StringUtils.isNotBlank(idSessione)) {
		    if (esito != null) {
			List<PaySessioniPagamento> sexs = this.paySessioniPagamentoService.findBySessionId(idSessione);
			PaySessioniPagamento sessPagamento = null;
			for (PaySessioniPagamento paySessioniPagamento : sexs) {
			    Integer idPosizioneDebitoria = null;
			    if (paySessioniPagamento.getPosizioneDebitoria() != null && paySessioniPagamento.getPosizioneDebitoria().getId() != null
				    && paySessioniPagamento.getPosizioneDebitoria().getId().getCodice() != null) {
				idPosizioneDebitoria = paySessioniPagamento.getPosizioneDebitoria().getId().getCodice();
			    }
			    paySessioniPagamento.setEsito(esito);
			    this.paySessioniPagamentoService.update(paySessioniPagamento);
			    log.debug("gestisciEsitoSessione aggiornamento della sessione avvenuto ");
			    sessPagamento = paySessioniPagamento;
			    if (!esito.booleanValue()) {
				PayPosizioniDebitorie pos = this.payPosizioniDebitorieService.findById(new PkId(idPosizioneDebitoria));
				if (pos != null && pos.getFlagOTF()) {
				    //le posizioni OTF con esito negativo vengono messe su stato ANNULLATO
				    EsitoOperazionePosizioneDebitoriaType eopd = new EsitoOperazionePosizioneDebitoriaType();
				    eopd.setIdPosizione(BigInteger.valueOf(idPosizioneDebitoria));
				    eopd.setStato(StatoPagamentoType.ANNULLATO);
				    try {
					payStatoPagamentiService.registraStatoPosizioneDebitoria(eopd, pos);
					log.debug("gestisciEsitoSessione aggiornamento dello stato pagamento della posizione debitoria avvenuta ");
				    } catch (PayException e) {
					log.error("gestisciEsitoSessione - impossibile annullare la posizione a caiusa dell'errore: ", e);
				    }
				}
			    }
			}
			if (sessPagamento != null) {
			    return sessPagamento; // ne torno una che l'url back è uguale per tutte
			}
		    }
		} else {
		    log.error("gestisciEsitoSessione - sessione non valida.");
		}
	    }
	}
	return null;
    }

    @Override
    public boolean supportaPagamentoOnTheFly() {

	return true;
    }
    //    private Set<PosizioniDebitorieCommand> raggruppaRichiestePerSoggettoDebitore(PosizioniDebitorieCommand cmd) {
    //
    //	Map<String, PosizioniDebitorieCommand> cmdMap = new HashMap<String, PosizioniDebitorieCommand>();
    //	PaySoggettiDebitori sogg = null;
    //	for (PayRegistrazioniContabili reg : cmd.getRegistrazioniPosizioni()) {
    //	    //siccome non è possibile richiedere il caricamento di debiti rateizzati in cui il soggetto debitore è diverso nelle singole rate 
    //	    //è sufficiente elaborare la prima posizione della registrazione contabile, i CF delle altre posizioni saranno sicuramente uguali
    //	    PayPosizioniDebitorie pos = (PayPosizioniDebitorie) reg.getPosizioniDebitorie().iterator().next();
    //	    sogg = pos.getSoggettoDebitore();
    //	    PosizioniDebitorieCommand subCmd = cmdMap.get(sogg.getCfPi());
    //	    if (subCmd == null) {
    //		subCmd = new PosizioniDebitorieCommand();
    //		subCmd.setRegistrazioniPosizioni(new ArrayList<PayRegistrazioniContabili>());
    //		//TODO verificare se è necessario rendere univoci id richiesta e id messaggio per ogni chiamata al WS. 
    //		//Nel caso dovranno essere sovrascritti con l'id dell'evento IO in uscita della chiamata al WS di IRIS
    //		subCmd.setIdMessaggio(cmd.getIdMessaggio());
    //		subCmd.setIdRichiesta(cmd.getIdRichiesta());
    //		cmdMap.put(sogg.getCfPi(), subCmd);
    //	    }
    //	    subCmd.getRegistrazioniPosizioni().add(reg);
    //	}
    //	return new HashSet<PosizioniDebitorieCommand>(cmdMap.values());
    //    }

    private ComunicazionePosizioniDebitorieOTF getComunicazionePosizioniDebitorieOTFPort() throws PayException {

	PayConnectorWsEndpoint wsCaricamentoCfg = this.getWsCaricamentoConfig();
	if (wsCaricamentoCfg == null) {
	    throw new PayConfigurationException("Il WS per il caricamento delle posizioni non è configurato per il connettorre iris.");
	}
	ComunicazionePosizioniDebitorieOTF info = this.comunicazionePosizioniDebitorieOTFPort == null
		? (ComunicazionePosizioniDebitorieOTF) this.getWSPort(wsCaricamentoCfg, ComunicazionePosizioniDebitorieOTF.class)
		: this.comunicazionePosizioniDebitorieOTFPort;
	this.prepareWsPort(info, wsCaricamentoCfg);
	this.comunicazionePosizioniDebitorieOTFPort = info;
	return info;
    }

    private VerificaStatoPagamento getVerificaStatoPagamentoPort() throws PayException {

	PayConnectorWsEndpoint wsCfg = this.getWsVerificaConfig();
	if (wsCfg == null) {
	    throw new PayConfigurationException("Il WS per la verifica dello stato dei pagamenti non è configurato per il connettorre iris.");
	}
	VerificaStatoPagamento ws = this.verificaStatoPagamento == null ? (VerificaStatoPagamento) this.getWSPort(wsCfg, VerificaStatoPagamento.class)
		: this.verificaStatoPagamento;
	this.prepareWsPort(ws, wsCfg);
	this.verificaStatoPagamento = ws;
	return ws;
    }

    private void prepareWsPort(Object wsPort, PayConnectorWsEndpoint wsCaricamentoEndpointCfg) throws PayException {

	Client client = ClientProxy.getClient(wsPort);
	/*
	 * Endpoint cxfEndpoint = client.getEndpoint(); Map<String, Object> outProps = new HashMap<String, Object>();
	 * outProps.put(WSHandlerConstants.ACTION, WSHandlerConstants.USERNAME_TOKEN + " " +
	 * WSHandlerConstants.TIMESTAMP); outProps.put(WSHandlerConstants.USER, wsCaricamentoCfg.getUtente());
	 * outProps.put(WSHandlerConstants.PASSWORD_TYPE, WSConstants.PW_TEXT); SecurityPwdCallBackHandler pwdCallback =
	 * new SecurityPwdCallBackHandler(wsCaricamentoCfg.getUtente(), wsCaricamentoCfg.getPassword());
	 * outProps.put(WSHandlerConstants.PW_CALLBACK_REF, pwdCallback); WSS4JOutInterceptor wssOut = new
	 * WSS4JOutInterceptor(outProps); cxfEndpoint.getOutInterceptors().add(wssOut);
	 */
	HTTPConduit conduit = (HTTPConduit) client.getConduit();
	if (wsCaricamentoEndpointCfg.getTimeout() != null) {
	    HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
	    httpClientPolicy.setConnectionTimeout(wsCaricamentoEndpointCfg.getTimeout());
	    httpClientPolicy.setReceiveTimeout(wsCaricamentoEndpointCfg.getTimeout());
	    conduit.setClient(httpClientPolicy);
	}
	AuthorizationPolicy authorization = getBasicAuthorization(wsCaricamentoEndpointCfg.getUtente(), wsCaricamentoEndpointCfg.getPassword());
	conduit.setAuthorization(authorization);
    }

    private ElencoPosizioniDebitorieEsitoType invocaAllineamentoPendenzeAsync(PosizioniDebitorieCommand cmd, boolean annullamento)
	    throws PayException {

	ElencoPosizioniDebitorieEsitoType fullEsito = new ElencoPosizioniDebitorieEsitoType();
	boolean esitoDefault = false;
	StatoPagamentoType statoPosDefault = StatoPagamentoType.ACQUISITO;
	String msgDefault = PayStatoPagamenti.StatiPagamento.ACQUISITO.description();
	IdpEsitoOTF esitoPsp = null;
	//Locale.setDefault(Locale.US);
	ComunicazionePosizioniDebitorieOTF port = this.getComunicazionePosizioniDebitorieOTFPort();
	try {
	    IdpAllineamentoPendenzeEnteOTF requestData = this.popolaAllineamentoPendenze(cmd, false, annullamento);
	    TipiEvento evtType = annullamento ? TipiEvento.ANNULLA_POSIZIONI_PSP : TipiEvento.ATTIVA_PAGAMENTO_OTF_PSP;
	    IdpAllineamentoPendenzeEnteOTFEsito response = port.idpAllineamentoPendenzeEnteOTF(requestData);
	    esitoPsp = response.getIdpEsitoOTF();
	    esitoDefault = true;
	    statoPosDefault = annullamento ? StatoPagamentoType.ANNULLATO : StatoPagamentoType.ATTIVATO_IN_PSP;
	    msgDefault = annullamento ? PayStatoPagamenti.StatiPagamento.ANNULLATO.description()
		    : PayStatoPagamenti.StatiPagamento.ATTIVATO_IN_PSP.description();
	} catch (Exception e) {
	    log.error("invocaAllineamentoPendenzeAsync - errore nell'invocazione WS di IRIS: idpAllineamentoPendenzeEnteOTF", e);
	    statoPosDefault = StatoPagamentoType.CON_ERRORE;
	    msgDefault = e.toString();
	} finally {
	}
	//creo elenco di esiti OK se l'invocazione WS non ha dato eccezione e ko se è fallita
	for (PayRegistrazioniContabili payReg : cmd.getRegistrazioniPosizioni()) {
	    for (PayPosizioniDebitorie payPos : payReg.getPosizioniDebitorie()) {
		EsitoOperazionePosizioneDebitoriaType esitoPos = new EsitoOperazionePosizioneDebitoriaType();
		esitoPos.setEsito(esitoDefault);
		PopolamentoDatiHelper.completaDatiDaPosizioneDebitoria(esitoPos, payPos,
			payPosizioniDebitorieService.findRiferimentiClientByPosizioneDebitoria(payPos.getId().getCodice()));
		//		esitoPos.setIdPosizione(BigInteger.valueOf(payPos.getId().getCodice()));
		//		esitoPos.setIdRegistrazioneContabile(BigInteger.valueOf(payReg.getId().getCodice()));
		//		
		//		//lo IUV viene popolato per riferimento nelle posizioni debitorie un momento prima di trasmetterle e memorizzato nel DB
		//		esitoPos.setIUV(StringUtils.defaultString(payPos.getIuv()));
		//		
		esitoPos.setStato(statoPosDefault);
		esitoPos.setMessaggio(msgDefault);
		fullEsito.getEsitoPosizione().add(esitoPos);
	    }
	}
	RiferimentiPosizioniDebitorieHelper esitiHelper = new RiferimentiPosizioniDebitorieHelper(fullEsito);
	if (esitoPsp != null) {
	    InfoMessaggio infoMessaggio = esitoPsp.getIdpBody().getInfoMessaggio();
	    EsitoOperazionePosizioneDebitoriaType esitoPosNodo = null;
	    //se esito complessivo = con errori ciclo i dettagli e 
	    if (infoMessaggio.getStato().equals(StatoMessaggio.ELABORATO_CON_ERRORI)) {
		//per ciascun dettaglio esito con errore recupero l'esito (a uso dl nodo) per idPosizionePSP e aggiorno l'esito a KO e imposto codice e messaggio di errore
		if (infoMessaggio.getEsiti() != null) {
		    List<Esito> esiti = infoMessaggio.getEsiti().getEsito();
		    if (esiti != null) {
			for (int i = 0; i < esiti.size(); i++) {
			    Esito esito = esiti.get(i);
			    if (i < fullEsito.getEsitoPosizione().size()) {
				esitoPosNodo = fullEsito.getEsitoPosizione().get(i);
				esitoPosNodo.setCodiceErrore(esito.getCodice());
				boolean boolEsito = StringUtils.isBlank(esito.getCodice());
				esitoPosNodo.setEsito(boolEsito);
				if (!boolEsito) {
				    esitoPosNodo.setStato(StatoPagamentoType.CON_ERRORE);
				}
				esitoPosNodo.setMessaggio(esito.getDescrizione());
			    }
			}
		    }
		}
		/*
		 * if (esitoPsp.getIdpBody().getInfoDettaglio() != null) { List<Dettaglio> esitiPsp =
		 * esitoPsp.getIdpBody().getInfoDettaglio().getDettaglio(); for (Dettaglio dettEsitoPsp : esitiPsp) {
		 * String idPosizionePsp = dettEsitoPsp.getId(); esitoPosNodo = (EsitoOperazionePosizioneDebitoriaType)
		 * esitiHelper.findRiferimentoPosizioneByIUV(idPosizionePsp); if (esitoPosNodo != null) {
		 * esitoPosNodo.setEsito(false); Esito esitoPU = null; if (dettEsitoPsp.getEsiti() != null &&
		 * dettEsitoPsp.getEsiti().getEsito().size() > 0) { //N esiti per ogni posizione ?????? prendo il primo
		 * esitoPU = dettEsitoPsp.getEsiti().getEsito().get(0);
		 * esitoPosNodo.setCodiceErrore(esitoPU.getCodice());
		 * esitoPosNodo.setMessaggio(esitoPU.getDescrizione()); }
		 * esitoPosNodo.setStato(StatoPagamentoType.CON_ERRORE); } else { //l'esito per questa posizione
		 * debitoria dovrebbe essere comunque già stato predisposto
		 * log.error("impossibile aggiornare l'esito dell'operazione per la posizione debitoria avente iuv: {}",
		 * idPosizionePsp); } //PayPosizioniDebitorie payPos = cmd.findPosizioneByIdPSP(idPosizionePsp); } }
		 */
		//se non ci sono dettagli sugli errori riscontrati allora imposto tutti gli esiti a KO e lo stato delle posizioni su CON_ERRORE
		else {
		    for (int i = 0; i < fullEsito.getEsitoPosizione().size(); i++) {
			esitoPosNodo = fullEsito.getEsitoPosizione().get(i);
			esitoPosNodo.setEsito(false);
			esitoPosNodo.setStato(StatoPagamentoType.CON_ERRORE);
			esitoPosNodo.setMessaggio("si è verificato un errore nell'elaborazione della posizione debitoria");
		    }
		}
	    }
	}
	return fullEsito;
    }

    private AttivaPagamentoOnTheFlyResponseType invocaAllineamentoPendenzeSync(PosizioniDebitorieCommand cmd, boolean annullamento) {

	AttivaPagamentoOnTheFlyResponseType response = new AttivaPagamentoOnTheFlyResponseType();
	//	PosizioniDebitorieCommand pdCmd = posizioniDebitorieCommandService.popolaPosizioniDebitorie(cmd.getRegistrazioniPosizioni(),
	//		cmd.getIdMessaggio(), cmd.getIdRichiesta());
	StatoPagamentoType statoPosDefault = StatoPagamentoType.ACQUISITO;
	String msgDefault = PayStatoPagamenti.StatiPagamento.ACQUISITO.description();
	boolean esitoDefault = false;
	IdpEsitoOTF esitoPU = null;
	try {
	    IdpAllineamentoPendenzeEnteOTF wsReq = this.popolaAllineamentoPendenze(cmd, true, annullamento);
	    TipiEvento evtType = annullamento ? TipiEvento.ANNULLA_POSIZIONI_PSP : TipiEvento.ATTIVA_PAGAMENTO_OTF_PSP;
	    IdpAllineamentoPendenzeEnteOTFEsito wsResp = this.getComunicazionePosizioniDebitorieOTFPort().idpAllineamentoPendenzeEnteOTF(wsReq);
	    esitoPU = wsResp.getIdpEsitoOTF();
	    statoPosDefault = annullamento ? StatoPagamentoType.ANNULLATO : StatoPagamentoType.ATTIVATO_IN_PSP;
	    msgDefault = annullamento ? PayStatoPagamenti.StatiPagamento.ANNULLATO.description()
		    : PayStatoPagamenti.StatiPagamento.ATTIVATO_IN_PSP.description();
	    esitoDefault = true;
	} catch (Exception e) {
	    log.error("invocaAllineamentoPendenzeSync - errore nell'invocazione WS PagoUmbria: idpAllineamentoPendenzeEnteOTF", e);
	} finally {
	}
	List<PayRegistrazioniContabili> rcs = cmd.getRegistrazioniPosizioni();
	AttivaSessionePagamentoResponseType sessionResp = new AttivaSessionePagamentoResponseType();
	sessionResp.setEsito(false);
	sessionResp.setDescEsito("Impossibile attivare la sessione di pagamento a cusa di errori");
	if (esitoPU.getIdpOTF() != null) {
	    sessionResp.setEsito(true);
	    sessionResp.setDescEsito("Sessione di pagamento on the fly creata correttamente");
	    sessionResp.setIdSessione(esitoPU.getIdpOTF().getIdSessioneGW());
	    PayProfiliEntiCreditori cfgEnte = PayConfigurationHelper.getProfiloEnteCreditore();
	    if (StringUtils.isNotBlank(esitoPU.getIdpOTF().getUrlGW())) {
		//correzione errore dati restituiti in ambiente di test, non compromette il funzionamento in produzione
		String urlGw = esitoPU.getIdpOTF().getUrlGW().replace("80.17.46.141:8180", "pagoumbriatest.regione.umbria.it");
		sessionResp.setPayUrl(urlGw);
	    } else {
		sessionResp.setPayUrl(cfgEnte.getPayConnector().getUrlPortalePagamenti());
	    }
	}
	for (PayRegistrazioniContabili rc : rcs) {
	    for (PayPosizioniDebitorie payPosDeb : rc.getPosizioniDebitorie()) {
		EsitoOperazionePosizioneDebitoriaType esitoPos = new EsitoOperazionePosizioneDebitoriaType();
		PopolamentoDatiHelper.completaDatiDaPosizioneDebitoria(esitoPos, payPosDeb,
			payPosizioniDebitorieService.findRiferimentiClientByPosizioneDebitoria(payPosDeb.getId().getCodice()));
		if (esitoPU != null) {
		    InfoMessaggio infoMessaggio = esitoPU.getIdpBody().getInfoMessaggio();
		    //se esito complessivo = con errori riporto l'errore nell'esito da restituire al nodo
		    if (infoMessaggio.getStato().equals(StatoMessaggio.ELABORATO_CON_ERRORI)) {
			statoPosDefault = StatoPagamentoType.CON_ERRORE;
			esitoDefault = false;
			msgDefault = PayStatoPagamenti.StatiPagamento.CON_ERRORE.description();
			List<Dettaglio> dettEsiti = esitoPU.getIdpBody().getInfoDettaglio().getDettaglio();
			for (Dettaglio dettEsito : dettEsiti) {
			    if (dettEsito.getId().equals(payPosDeb.getIdPosizionePsp())) {
				Esito esitoDettEsito = null;
				if (dettEsito.getEsiti() != null && dettEsito.getEsiti().getEsito().size() > 0) {
				    esitoDettEsito = dettEsito.getEsiti().getEsito().get(0);
				    esitoPos.setCodiceErrore(esitoDettEsito.getCodice());
				    msgDefault = esitoDettEsito.getDescrizione();
				}
			    }
			}
		    }
		}
		esitoPos.setEsito(esitoDefault);
		esitoPos.setStato(statoPosDefault);
		esitoPos.setMessaggio(msgDefault);
		response.getPosizioneInserita().add(esitoPos);
	    }
	}
	response.setSessionePagamento(sessionResp);
	return response;
    }

    /*
     * popola la request del WS AllineamentoPendenze TODO annullamento
     */
    private IdpAllineamentoPendenzeEnteOTF popolaAllineamentoPendenze(PosizioniDebitorieCommand cmd, boolean onTheFly, boolean annullamento)
	    throws PayException {

	IdpAllineamentoPendenzeEnteOTF allineamentoPendenzeEnteOTF = new IdpAllineamentoPendenzeEnteOTF();
	IdpAllineamentoPendenzeOTF allineamentoPendenzeOTF = new IdpAllineamentoPendenzeOTF();
	allineamentoPendenzeOTF.setVersione(VERSIONE);
	allineamentoPendenzeEnteOTF.setIdpAllineamentoPendenzeOTF(allineamentoPendenzeOTF);
	//sezione header
	PayProfiliEntiCreditori enteCfg = PayConfigurationHelper.getProfiloEnteCreditore();
	IdpHeader header = this.popolaIdpHeader(enteCfg);
	allineamentoPendenzeOTF.setIdpHeader(header);
	//sezione OTF Header per pagamenti on the fly
	if (onTheFly && !annullamento) {
	    PayPosizioniDebitorie posDeb = null;
	    if (cmd.getRegistrazioniPosizioni().size() > 0) {
		PayRegistrazioniContabili regcont = cmd.getRegistrazioniPosizioni().get(0);
		if (regcont.getPosizioniDebitorie().size() > 0) {
		    posDeb = regcont.getPosizioniDebitorie().iterator().next();
		}
	    }
	    IdpOTF otf = new IdpOTF();
	    String urlCallback = enteCfg.getUrlEsitoPagamento() + "&id=";
	    if (posDeb != null) {
		urlCallback += posDeb.getId().getCodice();
	    }
	    otf.setURLBACK(urlCallback);
	    if (StringUtils.isNotBlank(enteCfg.getUrlAnnullamentoPagamento())) {
		urlCallback = enteCfg.getUrlAnnullamentoPagamento() + "&id=";
		if (posDeb != null) {
		    urlCallback += posDeb.getId().getCodice();
		}
	    }
	    otf.setURLCANCEL(urlCallback);
	    otf.setOFFLINEPAYMENTMETHODS(Boolean.FALSE);
	    //TODO dati soggetto facoltativi ?
	    /*
	     * DATIVERSANTEType datiVersante = new DATIVERSANTEType();
	     * datiVersante.setIDFISCALE(soggDebitore.getCfPi()); otf.setDATIVERSANTE(datiVersante);
	     */
	    allineamentoPendenzeOTF.setIdpOTF(otf);
	}
	//sezione body con i dati delle posizioni da caricare
	IdpBody body = new IdpBody();
	allineamentoPendenzeOTF.setIdpBody(body);
	List<PayRegistrazioniContabili> registrazioni = cmd.getRegistrazioniPosizioni();
	//	if (registrazioni.size() > 1 && onTheFly) {
	//	    throw new PayInvalidRequestException(
	//		    "Non è possibile attivare il pagamento on the fly di più registrazioni contabili contemporaneamente.");	
	//	}
	for (PayRegistrazioniContabili payReg : registrazioni) {
	    //dati della registrazione contabile
	    Pendenza pendenza = null;
	    String codiceVersamento = posizioniDebitorieCommandService
		    .findCodiceVersamentoFromPosizioneDebitoria(payReg.getPosizioniDebitorie().iterator().next());
	    if (!annullamento) {
		pendenza = createPendenza(payReg, enteCfg, annullamento, codiceVersamento);
		pendenza.setIdPendenza(payReg.getId().getCodice().toString());
		if (StringUtils.isNotEmpty(payReg.getNote())) {
		    pendenza.setNote(payReg.getNote());
		}
		PendenzaInsertReplace insert = new PendenzaInsertReplace();
		pendenza.setInsert(insert);
		insert.setDescrizioneCausale(payReg.getDescrizione());
		insert.setDataCreazione(header.getTRT().getXMLCrtDt());
		Date now = new Date();
		GregorianCalendar cal = (GregorianCalendar) GregorianCalendar.getInstance();
		cal.setTime(now);
		cal.roll(Calendar.YEAR, ANNI_VALIDITA_PAGAMENTO);
		insert.setDataPrescrizione(Utilities.getXMLGregorianCalendar(cal));
		cal = new GregorianCalendar();
		cal.set(Calendar.YEAR, payReg.getAnno());
		insert.setAnnoRiferimento(Utilities.getXMLGregorianCalendar(cal));
		insert.setDataEmissione(Utilities.getXMLGregorianCalendar(now));
		insert.setDivisa(Divisa.EUR);
		insert.setStato(StatoPendenza.APERTA);
		InfoPagamento infoP = new InfoPagamento();
		Set<PayPosizioniDebitorie> posizioni = payReg.getPosizioniDebitorie();
		infoP.setTipoPagamento(posizioni.size() > 1 ? TipoPagamento.PAGAMENTO_A_RATE : TipoPagamento.PAGAMENTO_UNICO);
		insert.getInfoPagamento().add(infoP);
		PaySoggettiDebitori paySogg = null;
		BigDecimal totRegistrazione = BigDecimal.ZERO;
		//dati delle singole posizioni (rate) che devono essere una sola per il pagamento on the fly
		//		if (posizioni.size() > 1 && onTheFly) {
		//		    throw new PayInvalidRequestException(
		//			    "Non è possibile attivare il pagamento on the fly di più posizioni debitorie contemporaneamente.");
		//		}
		for (PayPosizioniDebitorie payPos : posizioni) {
		    if (paySogg == null) {
			paySogg = payPos.getSoggettoDebitore();
		    } else {
			//le posizioni debitorie caricate insieme devono far riferimento allo stesso soggetto debitore
			if (!paySogg.getCfPi().equals(payPos.getSoggettoDebitore().getCfPi())) {
			    throw new PayInvalidRequestException(
				    "Le posizioni debitorie caricate nella stessa registrazione devono avere lo stesso soggetto debitore.");
			}
		    }
		    DettaglioPagamentoInsertReplace dpir = new DettaglioPagamentoInsertReplace();
		    dpir.setStato(StatoPagamento.NON_PAGATO);
		    dpir.setCausalePagamento(payPos.getDescrizioneCausale());
		    cal = (GregorianCalendar) GregorianCalendar.getInstance();
		    if (!onTheFly) {
			cal.setTime(payPos.getDataRegistrazione());
		    } else {
			cal.setTime(now);
			cal.add(Calendar.DATE, -1);
		    }
		    dpir.setDataInizioValidita(Utilities.getXMLGregorianCalendar(cal));
		    cal.roll(Calendar.YEAR, ANNI_VALIDITA_PAGAMENTO);
		    if (payPos.getDataScadenza() != null) {
			dpir.setDataScadenza(Utilities.getXMLGregorianCalendar(payPos.getDataScadenza()));
		    } else {
			dpir.setDataScadenza(Utilities.getXMLGregorianCalendar(cal));
		    }
		    dpir.setDataFineValidita(Utilities.getXMLGregorianCalendar(cal));
		    //generazione IUV, Codice Avviso e QRCODE
		    gestisciRiferimentiPagamento(enteCfg, payReg, payPos, dpir);
		    //scrivo nella posizione debitoria l'id trasmesso al PSP
		    this.payPosizioniDebitorieService.aggiornaPosizioneDebitoria(payPos, null, null);
		    BigDecimal totPosizione = BigDecimal.ZERO;
		    Set<PayDettaglioImporti> payImporti = payPos.getDettagliImporto();
		    dpir.setDettaglioImporto(new DettaglioImporto());
		    for (PayDettaglioImporti payImporto : payImporti) {
			totPosizione = totPosizione.add(payImporto.getImporto());
			VoceImporto voce = new VoceImporto();
			voce.setImporto(payImporto.getImporto());
			if (StringUtils.isNotBlank(payImporto.getNumeroAccertamento())) {
			    voce.setAccertamento(payImporto.getNumeroAccertamento());
			}
			String tipoVoce = DEFAULT_TIPO_VOCE_IMPORTO;
			String codiceVoce = payImporto.getDatiRiscossione();
			if (payImporto.getNumeroSottoAccertamento() != null) {
			    voce.setCapitoloBilancio(payImporto.getNumeroSottoAccertamento());
			}
			if (StringUtils.isBlank(codiceVoce)) {
			    //iris da "errore database" se manca il codice voce importo anche se non è obbligatorio a livello di xsd 
			    //perciò in mancanza di dati impostiamo un valore di default
			    codiceVoce = DEFAULT_CODICE_VOCE_IMPORTO;
			}
			voce.setCodice(codiceVoce);
			voce.setTipo(tipoVoce);
			voce.setDescrizione(payImporto.getDescCausale());
			dpir.getDettaglioImporto().getVoce().add(voce);
		    }
		    infoP.getDettaglioPagamento().add(dpir);
		    dpir.setImporto(totPosizione);
		    totRegistrazione = totRegistrazione.add(totPosizione);
		}
		insert.setImportoTotale(totRegistrazione);
		Destinatari dests = new Destinatari();
		pendenza.setDestinatari(dests);
		if (paySogg != null) {
		    Destinatario dest = createDestinatario(paySogg);
		    dests.getDestinatario().add(dest);
		} else {
		    throw new PayInvalidRequestException("Soggetto debitore non specificato");
		}
		body.getPendenza().add(pendenza);
	    }
	    //annullamento
	    else {
		for (PayPosizioniDebitorie payPos : payReg.getPosizioniDebitorie()) {
		    pendenza = createPendenza(payReg, enteCfg, annullamento, codiceVersamento);
		    PaySoggettiDebitori paySogg = payPos.getSoggettoDebitore();
		    if (paySogg != null) {
			Destinatari dests = new Destinatari();
			pendenza.setDestinatari(dests);
			Destinatario dest = createDestinatario(paySogg);
			dests.getDestinatario().add(dest);
		    }
		    //pendenza.setIdPendenza(payPos.getIuv());
		    pendenza.setIdPendenza(payReg.getId().getCodice().toString());
		    body.getPendenza().add(pendenza);
		}
	    }
	}
	return allineamentoPendenzeEnteOTF;
    }

    private Pendenza createPendenza(PayRegistrazioniContabili payReg, PayProfiliEntiCreditori enteCfg, boolean annullamento, String tipoPendenza) {

	Pendenza pendenza = new Pendenza();
	pendenza.setTipoOperazione(annullamento ? TipoOperazione.DELETE : TipoOperazione.INSERT);
	pendenza.setTipoPendenza(tipoPendenza);
	Mittente mittente = new Mittente();
	mittente.setId(enteCfg.getCfCodiceProfiloPSP());
	Amministrazioni ammin = enteCfg.getAmministrazione();
	mittente.setDescrizione(ammin.getAmministrazione());
	pendenza.setMittente(mittente);
	return pendenza;
    }

    private Destinatario createDestinatario(PaySoggettiDebitori paySogg) {

	Destinatario dest = new Destinatario();
	dest.setTipo(TipoDestinatario.CITTADINO);
	dest.setId(paySogg.getCfPi());
	StringBuilder sbName = new StringBuilder();
	if (StringUtils.isNotBlank(paySogg.getNome())) {
	    sbName.append(paySogg.getNome());
	}
	if (sbName.length() > 0) {
	    sbName.append(" ");
	}
	if (StringUtils.isNotBlank(paySogg.getCognome())) {
	    sbName.append(paySogg.getCognome());
	}
	dest.setDescrizione(sbName.toString());
	return dest;
    }

    private IdpHeader popolaIdpHeader(PayProfiliEntiCreditori enteCfg) {

	IdpHeader header = new IdpHeader();
	HeaderTRT trt = new HeaderTRT();
	trt.setMsgId(UUID.randomUUID().toString().replace("-", "_")); //guid
	/*
	 * l'id messaggio deve essere quello di un nuovo PayIoEventi che verrà creato per ogni singola invocazione del
	 * WS del PSP, tutti gli ioeventi di chiamata al PSP devono avere lo stesso codice comunicazione dell'IOEventi
	 * passato come argomento che rappresenta invece l'evento di invocazione iniziale del servizio di caricamento
	 * delle posizioni esposto dal nodo pagamenti
	 */
	//trt.setMsgId(cmd.getIdMessaggio());
	trt.setServiceName(ServiceName.IDP_ALLINEAMENTO_PENDENZE);
	Date now = new Date();
	trt.setXMLCrtDt(Utilities.getXMLGregorianCalendar(now));
	TRTSender sender = new TRTSender();
	sender.setSenderId(enteCfg.getCfCodiceProfiloPSP());
	sender.setSenderSys(enteCfg.getIdAppPSP());
	trt.setSender(sender);
	TRTReceiver receiver = new TRTReceiver();
	receiver.setReceiverId(RECEIVER_ID);
	receiver.setReceiverSys(RECEIVER_SYS);
	trt.setReceiver(receiver);
	header.setTRT(trt);
	HeaderE2E e2e = new HeaderE2E();
	e2e.setE2EMsgId(trt.getMsgId());
	e2e.setE2EMsgId(trt.getMsgId());
	e2e.setE2ESrvcNm(trt.getServiceName().value());
	E2ESender e2eSender = new E2ESender();
	e2eSender.setE2ESndrId(sender.getSenderId());
	e2eSender.setE2ESndrSys(sender.getSenderSys());
	e2e.setSender(e2eSender);
	e2e.setXMLCrtDt(trt.getXMLCrtDt());
	E2EReceiver e2eReceiver = new E2EReceiver();
	e2eReceiver.setE2ERcvrId(receiver.getReceiverId());
	e2eReceiver.setE2ERcvrSys(receiver.getReceiverSys());
	e2e.setReceiver(e2eReceiver);
	header.setE2E(e2e);
	return header;
    }

    private it.gruppoinit.pal.gp.pay.connector.iris.ws.client.verificaposizioni.schema.header.IdpHeader popolaIdpHeaderVerifica(
	    PayProfiliEntiCreditori enteCfg, String idMessaggio) {

	it.gruppoinit.pal.gp.pay.connector.iris.ws.client.verificaposizioni.schema.header.IdpHeader header = new it.gruppoinit.pal.gp.pay.connector.iris.ws.client.verificaposizioni.schema.header.IdpHeader();
	it.gruppoinit.pal.gp.pay.connector.iris.ws.client.verificaposizioni.schema.header.HeaderTRT trt = new it.gruppoinit.pal.gp.pay.connector.iris.ws.client.verificaposizioni.schema.header.HeaderTRT();
	/*
	 * l'id messaggio deve essere quello di un nuovo PayIoEventi che verrà creato per ogni singola invocazione del
	 * WS del PSP, tutti gli ioeventi di chiamata al PSP devono avere lo stesso codice comunicazione dell'IOEventi
	 * passato come argomento che rappresenta invece l'evento di invocazione iniziale del servizio di caricamento
	 * delle posizioni esposto dal nodo pagamenti
	 */
	//trt.setMsgId(cmd.getIdMessaggio());
	trt.setServiceName(it.gruppoinit.pal.gp.pay.connector.iris.ws.client.verificaposizioni.schema.header.ServiceName.IDP_INFORMATIVA_PAGAMENTO);
	Date now = new Date();
	trt.setXMLCrtDt(Utilities.getXMLGregorianCalendar(now));
	trt.setMsgId(idMessaggio.replaceAll("-", "_")); // OBBLIGATORIO
	it.gruppoinit.pal.gp.pay.connector.iris.ws.client.verificaposizioni.schema.header.TRTSender sender = new it.gruppoinit.pal.gp.pay.connector.iris.ws.client.verificaposizioni.schema.header.TRTSender();
	sender.setSenderId(enteCfg.getCfCodiceProfiloPSP());
	sender.setSenderSys(enteCfg.getIdAppPSP());
	trt.setSender(sender);
	it.gruppoinit.pal.gp.pay.connector.iris.ws.client.verificaposizioni.schema.header.TRTReceiver receiver = new it.gruppoinit.pal.gp.pay.connector.iris.ws.client.verificaposizioni.schema.header.TRTReceiver();
	receiver.setReceiverId(RECEIVER_ID);
	receiver.setReceiverSys(RECEIVER_SYS);
	trt.setReceiver(receiver);
	header.setTRT(trt);
	it.gruppoinit.pal.gp.pay.connector.iris.ws.client.verificaposizioni.schema.header.HeaderE2E e2e = new it.gruppoinit.pal.gp.pay.connector.iris.ws.client.verificaposizioni.schema.header.HeaderE2E();
	e2e.setXMLCrtDt(Utilities.getXMLGregorianCalendar(now));
	e2e.setE2EMsgId(trt.getMsgId());
	e2e.setE2ESrvcNm(trt.getServiceName().value());
	it.gruppoinit.pal.gp.pay.connector.iris.ws.client.verificaposizioni.schema.header.E2ESender e2eSender = new it.gruppoinit.pal.gp.pay.connector.iris.ws.client.verificaposizioni.schema.header.E2ESender();
	e2eSender.setE2ESndrId(sender.getSenderId());
	e2eSender.setE2ESndrSys(sender.getSenderSys());
	e2e.setSender(e2eSender);
	it.gruppoinit.pal.gp.pay.connector.iris.ws.client.verificaposizioni.schema.header.E2EReceiver e2eReceiver = new it.gruppoinit.pal.gp.pay.connector.iris.ws.client.verificaposizioni.schema.header.E2EReceiver();
	e2eReceiver.setE2ERcvrId(receiver.getReceiverId());
	e2eReceiver.setE2ERcvrSys(receiver.getReceiverSys());
	e2e.setReceiver(e2eReceiver);
	header.setE2E(e2e);
	return header;
    }

    private IdpVerificaStatoPagamenti popolaVerificaStatoPagamenti(RichiestaSuListaPosizioniCommand cmd, PayProfiliEntiCreditori enteCfg)
	    throws PayConfigurationException {

	IdpVerificaStatoPagamenti retVal = new IdpVerificaStatoPagamenti();
	IdpVerificaStatoPagamento verificaParam = new IdpVerificaStatoPagamento();
	retVal.setIdpVerificaStatoPagamento(verificaParam);
	String msgId = System.currentTimeMillis() + "";
	it.gruppoinit.pal.gp.pay.connector.iris.ws.client.verificaposizioni.schema.header.IdpHeader header = this.popolaIdpHeaderVerifica(enteCfg,
		msgId);
	verificaParam.setIdpHeader(header);
	it.gruppoinit.pal.gp.pay.connector.iris.ws.client.verificaposizioni.schema.IdpVerificaStatoPagamento.IdpBody body = new it.gruppoinit.pal.gp.pay.connector.iris.ws.client.verificaposizioni.schema.IdpVerificaStatoPagamento.IdpBody();
	verificaParam.setIdpBody(body);
	body.setRichiestaInformazioniPagamento(Boolean.TRUE);
	List<PayPosizioniDebitorie> payPosizioni = cmd.getPosizioni();
	for (PayPosizioniDebitorie payPos : payPosizioni) {
	    IdPagamento idPagamento = new IdPagamento();
	    idPagamento.setValue(payPos.getIdPosizionePsp());
	    String tipoPendenza = posizioniDebitorieCommandService.findCodiceVersamentoFromPosizioneDebitoria(payPos);
	    idPagamento.setTipoPendenza(tipoPendenza);
	    body.getIdPagamento().add(idPagamento);
	}
	verificaParam.setVersione(IrisPayConnector.VERSIONE);
	return retVal;
    }

    private ElencoStatoPosizioniType popolaVerificaStatoPosizioniResponse(IdpVerificaStatoPagamentiEsito response,
	    RichiestaSuListaPosizioniCommand cmd) throws PayException {

	ElencoStatoPosizioniType retVal = null;
	if (response != null) {
	    retVal = new ElencoStatoPosizioniType();
	    IdpEsitoVerifica esitoVerifica = response.getIdpEsitoVerifica();
	    IdpEsitoVerifica.IdpBody bodyEsito = esitoVerifica.getIdpBody();
	    //se l'esito è valorizzato nella response significa che c'è stato qualche errore
	    if (bodyEsito.getEsito() != null) {
		StringBuilder sbErr = new StringBuilder();
		sbErr.append("Errore ").append(bodyEsito.getEsito().getCodice());
		if (StringUtils.isNotBlank(bodyEsito.getEsito().getDescrizione())) {
		    sbErr.append(":").append(bodyEsito.getEsito().getDescrizione());
		}
		if (StringUtils.isNotBlank(bodyEsito.getEsito().getElemento())) {
		    sbErr.append(", elemento: ").append(bodyEsito.getEsito().getElemento());
		}
		//rilancio eccezione: il service chiamante la gestirà restituendo esito KO ad indicare il fallimento della chiamata al WS 
		//ma i dati delle posizioni richieste saranno comunque restituiti al chiamante leggendoli dal DB come per i connettori che non supportano il WS di verificastato
		throw new PayException(sbErr.toString());
	    } else {
		List<InformazioniPagamentoType> infoStati = bodyEsito.getInformazioniPagamento();
		if (infoStati != null && infoStati.size() > 0) {
		    for (InformazioniPagamentoType infoStato : infoStati) {
			StatoPosizioneType outputStatoPos = this.popolaEsitoVerificaStatoPosizione(infoStato, cmd);
			retVal.getStatoPosizioni().add(outputStatoPos);
		    }
		}
	    }
	}
	return retVal;
    }

    private StatoPosizioneType popolaEsitoVerificaStatoPosizione(
	    it.gruppoinit.pal.gp.pay.connector.iris.ws.client.verificaposizioni.schema.esito.InformazioniPagamentoType infoVerificaStato,
	    RichiestaSuListaPosizioniCommand cmd) throws PayException {

	StatoPosizioneType retStatus = new StatoPosizioneType();
	List<PayPosizioniDebitorie> posizioni = cmd.getPosizioni();
	boolean otf = false;
	for (PayPosizioniDebitorie payPosizioniDebitorie : posizioni) {
	    if (infoVerificaStato.getIdPagamento().equalsIgnoreCase(payPosizioniDebitorie.getIuv())) {
		retStatus.setIdPosizione(BigInteger.valueOf(payPosizioniDebitorie.getId().getCodice()));
		otf = BooleanUtils.isTrue(payPosizioniDebitorie.getFlagOTF());
	    }
	}
	retStatus.setIUV(infoVerificaStato.getIdPagamento());
	VerificaStatoPagamentoDettagliato veriStato = infoVerificaStato.getStato();
	DatiPagamentoType dp = null;
	if (infoVerificaStato.getPagamento() != null) {
	    dp = popolaDatiPagamento(infoVerificaStato.getPagamento());
	}
	StatoPagamentoType localStatus = null;
	String messaggio = "";
	// La chiamata a verifica stato del connettore PAGOUMBRIA  e IRIS dovrebbe annullare il pagamento se la posizione è OTF e se lo stato tornato è 
	// POSIZIONE_NON_PAGATA o POSIZIONE_NON_PRESENTE
	if (!otf) {
	    switch (veriStato) {
	    case POSIZIONE_PAGATA:
	    case POSIZIONE_PAGATA_SBF:
		//il pagamento risulta eseguito
		localStatus = StatoPagamentoType.NOTIFICATO_DA_PSP;
		messaggio = "Pagamento eseguito";
		retStatus.setDatiPagamento(dp);
		break;
	    case POSIZIONE_NON_PAGATA:
	    case POSIZIONE_CON_PAG_IN_CORSO:
	    case POSIZIONE_CON_DOC_EMESSO:
		localStatus = StatoPagamentoType.ATTIVATO_IN_PSP;
		messaggio = "Pagamento non eseguito";
		break;
	    case POSIZIONE_NON_PAGABILE:
	    case POSIZIONE_NON_PRESENTE:
		localStatus = StatoPagamentoType.CON_ERRORE;
		messaggio = "Posizione non presente";
		break;
	    default:
		break;
	    }
	} else {
	    switch (veriStato) {
	    case POSIZIONE_PAGATA:
	    case POSIZIONE_PAGATA_SBF:
		//il pagamento risulta eseguito
		localStatus = StatoPagamentoType.NOTIFICATO_DA_PSP;
		messaggio = "Pagamento eseguito";
		retStatus.setDatiPagamento(dp);
		break;
	    case POSIZIONE_CON_PAG_IN_CORSO:
		localStatus = StatoPagamentoType.ATTIVATO_IN_PSP;
		messaggio = "Pagamento in corso";
		break;
	    /**
	     * 
	     * <PRE>
	     * I valori ammessi per lo stato del pagamento così come dichiarati nel XSD del servizio VerificaStatoPagamento sono i seguenti:
	    POSIZIONE_NON_PRESENTE
	    POSIZIONE_NON_PAGATA
	    POSIZIONE_NON_PAGABILE
	    POSIZIONE_PAGATA
	    POSIZIONE_PAGATA_SBF
	    POSIZIONE_CON_PAG_IN_CORSO
	    POSIZIONE_CON_DOC_EMESSO
	    Lo stato che indica che una posizione è stata pagata è: POSIZIONE_PAGATA .
	    Gli stati POSIZIONE_PAGATA_SBF e POSIZIONE_CON_PAG_IN_CORSO indicano che la transazione è ancora in corso e bisogna continuare a interrogare il servizio per quello IUV per conoscerne l'esito.
	    Lo stato POSIZIONE_NON_PAGATA indica che la posizione esiste ma non è stata pagata mentre lo stato POSIZIONE_NON_PRESENTE indica che lo IUV richiesto non esiste. Nello scenario OTF lo stato  POSIZIONE_NON_PRESENTE significa semplicemente che l'utente può ritentare il pagamento di quello IUV così come per lo stato POSIZIONE_NON_PAGATA.
	    Gli altri stati non sono previsti per lo scenario OTF.
	     * </PRE>
	     */
	    case POSIZIONE_CON_DOC_EMESSO:
	    case POSIZIONE_NON_PAGATA:
	    case POSIZIONE_NON_PAGABILE:
	    case POSIZIONE_NON_PRESENTE:
		localStatus = StatoPagamentoType.ANNULLATO;
		messaggio = "Posizione annullata per stato " + veriStato + " tornato da connettore con scenario OTF";
		break;
	    default:
		break;
	    }
	}
	retStatus.setStato(localStatus);
	retStatus.setMessaggio(messaggio);
	return retStatus;
    }

    private DatiPagamentoType popolaDatiPagamento(Pagamento respPagamento) {

	DatiPagamentoType pagamento = new DatiPagamentoType();
	String iuv = respPagamento.getRiferimentoPagamento().getIdPagamento();
	pagamento.setIuv(iuv);
	pagamento.setDataOraPagamento(respPagamento.getDataOraPagamento());
	pagamento.setImportoPagato(respPagamento.getImporto());
	pagamento.setDescrizioneCausale(respPagamento.getDescrizioneCausale());
	if (respPagamento.getTransazione() != null) {
	    pagamento.setDataOraAutorizzazione(respPagamento.getTransazione().getDataOraAutorizzazione());
	    pagamento.setDataOraInizioTransazione(respPagamento.getTransazione().getDataOraTransazione());
	    if (respPagamento.getTransazione().getCanalePagamento() != null) {
		pagamento.setIdPSP(respPagamento.getTransazione().getCanalePagamento().getTipo());
		pagamento.setRagioneSocialePSP(respPagamento.getTransazione().getCanalePagamento().getDescrizione());
	    }
	    pagamento.setRiferimentiPagamento(respPagamento.getTransazione().getCodiceAutorizzazione());
	    pagamento.setImportoTransato(respPagamento.getTransazione().getImportoTransato());
	    if (respPagamento.getTransazione().getMezzoPagamento() != null) {
		pagamento.setModalitaPagamento(respPagamento.getTransazione().getMezzoPagamento().getTipo());
	    }
	    BigDecimal importoCommissioni = BigDecimal.ZERO;
	    if (respPagamento.getTransazione().getDettaglioImportoTransato() != null
		    && !respPagamento.getTransazione().getDettaglioImportoTransato().getVoce().isEmpty()) {
		List<Voce> v = respPagamento.getTransazione().getDettaglioImportoTransato().getVoce();
		for (Voce voce : v) {
		    if (voce != null && voce.getTipo()
			    .equals(it.gruppoinit.pal.gp.pay.connector.iris.ws.client.verificaposizioni.schema.TipoVoce.IMPORTO_COMMISSIONI)) {
			importoCommissioni = importoCommissioni.add(voce.getImporto());
		    }
		}
	    }
	    pagamento.setImportoCommissioni(importoCommissioni);
	    pagamento.setIur(respPagamento.getTransazione().getIdTransazione());
	}
	pagamento.setNote(respPagamento.getNote());
	return pagamento;
    }

    @Override
    public ElencoDocumentiEsitoType inviaAvvisiPagamento(PosizioniDebitorieCommand cmd) throws PayException {

	log.debug("inviaAvvisiPagamento");
	PayConnectorWsEndpoint wsAvviso = this.getWsAvvisoConfig();
	PayProfiliEntiCreditori ente = PayConfigurationHelper.getProfiloEnteCreditore();
	if (wsAvviso == null) {
	    log.debug("inviaAvvisiPagamento endpoint getWsAvvisoConfig non configurato");
	    throw new PayConfigurationException(
		    "l'endpoint per il servizio di generazione degli avvisi non è configurato per il connettore " + this.getConnectorName());
	}
	ElencoDocumentiEsitoType retEsiti = new ElencoDocumentiEsitoType();
	String msg = null;
	for (PayRegistrazioniContabili reg : cmd.getRegistrazioniPosizioni()) {
	    for (PayPosizioniDebitorie pos : reg.getPosizioniDebitorie()) {
		msg = "invocazione del servizio generaAvviso in corso";
		log.debug("inviaAvvisiPagamento elaboro la posizione debitoria {}", pos.getId().getCodice());
		EsitoDocumentoPosizioneDebitoriaType esitoDoc = new EsitoDocumentoPosizioneDebitoriaType();
		PopolamentoDatiHelper.completaDatiDaPosizioneDebitoria(esitoDoc, pos,
			payPosizioniDebitorieService.findRiferimentiClientByPosizioneDebitoria(pos.getId().getCodice()));
		esitoDoc.setTipoDocumento(TipoDocumentoType.AVVISO);
		esitoDoc.setStatoDocumento(StatoDocumentoType.DISPONIBILE);
		log.debug("inviaAvvisiPagamento recupero il client per la posizione debitoria {}, url {}", pos.getId().getCodice(),
			wsAvviso.getEndpointUrl());
		try {
		    GenerazioneAvvisi client = this.getGenerazioneAvvisiPort(wsAvviso);
		    log.debug("inviaAvvisiPagamento invoco il metodo getAvviso {}-{}-{}", ente.getCfCodiceProfiloPSP(), pos.getCodiceAvviso(),
			    pos.getId().getCodice());
		    GeneraAvvisoRequest request = new GeneraAvvisoRequest();
		    request.setIdentificativoDominio(ente.getCfEnteQrcodePagopa());
		    request.setIdentificativoUnivocoVersamento(pos.getIuv());
		    GeneraAvvisoResponseType response = client.generaAvviso(request);
		    if (response.getFault() != null) {
			log.error(response.getFault().getFaultString() + ":" + response.getFault().getFaultDescription());
			throw new PayException("Errore nel download dell'avviso: " + response.getFault().getFaultString() + ":" +
					       response.getFault().getFaultDescription());
		    }
		    log.debug("inviaAvvisiPagamento chiamata effettuata con successo per la posizione debitoria {}", pos.getId().getCodice());
		    esitoDoc.setEsito(true);
		    esitoDoc.setNomeDocumento("AVVISO_" + pos.getIuv() + ".pdf");
		    esitoDoc.setDocumento(response.getBody().getAvvisoAnalogico());
		} catch (PayException e) {
		    this.handleException(e, esitoDoc);
		    msg = "impossibile scaricare l'avviso PDF a causa dell'errore: " + e.toString();
		    log.error("inviaAvvisiPagamento - {}", msg, e);
		}
		esitoDoc.setMessaggio(msg);
		log.info("inviaAvvisiPagamento - {}", msg);
		retEsiti.getEsitoPosizione().add(esitoDoc);
	    }
	}
	return retEsiti;
    }

    private GenerazioneAvvisi getGenerazioneAvvisiPort(PayConnectorWsEndpoint wsAvvisoConfig) throws PayException {

	GenerazioneAvvisi ws = (GenerazioneAvvisi) this.getWSPort(wsAvvisoConfig, GenerazioneAvvisi.class);
	log.debug("inviaAvvisiPagamento client porta creata setto le proprietà");
	Client client = ClientProxy.getClient(ws);
	HTTPConduit conduit = (HTTPConduit) client.getConduit();
	String keyStoreLocation = this.payConnectorConfigValuesService.getValoreParametroConfigurazione(ConfigParamNames.SSL_KEY_STORE_LOCATION);
	if (StringUtils.isNotBlank(keyStoreLocation)) {
	    log.debug("inviaAvvisiPagamento client presente la proprieta keystore location {}", keyStoreLocation);
	    String keyStorePassw = this.payConnectorConfigValuesService.getValoreParametroConfigurazione(ConfigParamNames.SSL_KEY_STORE_PASSWORD);
	    if (log.isDebugEnabled()) {
		log.debug("inviaAvvisiPagamento client presente la proprieta keystore password?{}, {}", StringUtils.isNotBlank(keyStoreLocation),
			StringUtils.repeat("*", StringUtils.defaultString(keyStoreLocation).length()));
	    }
	    TLSClientParameters tlsCP = new TLSClientParameters();
	    try {
		KeyStore keyStore = KeyStore.getInstance("JKS");
		keyStore.load(new FileInputStream(keyStoreLocation), keyStorePassw.toCharArray());
		KeyManager[] myKeyManagers = TrustManagerUtils.getKeyManagers(keyStore, keyStorePassw);
		tlsCP.setKeyManagers(myKeyManagers);
		tlsCP.setTrustManagers(new TrustManager[] { new TrustAllX509TrustManager() });
		conduit.setTlsClientParameters(tlsCP);
	    } catch (IOException | GeneralSecurityException e) {
		log.error("getGenerazioneAvvisiPort: Errore nel settaggio dei parametri client " + e.getMessage(), e);
		throw new PayException("errore nella creazione del client SOAP", e);
	    }
	}
	if (wsAvvisoConfig.getTimeout() != null) {
	    HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
	    httpClientPolicy.setConnectionTimeout(default_connection_timeout);
	    httpClientPolicy.setReceiveTimeout(wsAvvisoConfig.getTimeout());
	    conduit.setClient(httpClientPolicy);
	}
	log.debug("inviaAvvisiPagamento client porta creata ritorno il client");
	return ws;
    }

    @Override
    public void validateRichiestaInserimentoPosizioniDebitorie(RegistrazioneContabileType registrazioneContabile)
	    throws ValidazionePosizioniDebitorieException {

	//TODO validazioni per prevenire errori del connettore
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
    /**
     * IUV con AUX digit di tipo 0 IUV: <IUV base (13n)><IUV check digit (2n)> CODICE AVVISO: 0<application code
     * (2n)><IUV base (13n)><IUV check digit (2n)> dove IUV base = <codice tributo (3n)><progressivo univoco per codice
     * tributo> e application code = "01" poiché utilizziamo il CODICE_AVVISO configurato nel nodo per impostare il
     * codice tributo non possiamo usarlo come vero application code perciò facciamo questa implementazione specifica in
     * cui risolviamo il problema passando l'AUX DIGIT direttamente = "001"
     */
    public IUVHelper generaIUV(PayPosizioniDebitorie posDeb, String identificativoCausaleVersamento) {

	StringBuilder iuv = new StringBuilder();
	PayProfiliEntiCreditori profEnte = PayConfigurationHelper.getProfiloEnteCreditore();
	if (profEnte != null && posDeb != null) {
	    //L'AUX_DIGIT va solo sul codice avviso e per IRIS sarà 001
	    //13 caratteri numerici univoci per idcomune
	    //composti da 3 cifre che codificano l'id della causale di versamento (per ora configurata fissa in PAY_PROFILI_ENTI_CREDITORI.APPLICATION_CODE)
	    String appCodeDaCausale = "";
	    ParametroChiaveApplicationCodeIUV parametroChiaveApplicationCodeIUV = new ParametroChiaveApplicationCodeIUV();
	    try {
		appCodeDaCausale = posizioniDebitorieCommandService.findValoreUnicoParametroFromPosizioneDebitoria(posDeb,
			parametroChiaveApplicationCodeIUV);
	    } catch (PayException e) {
		throw new RuntimeException("Errore nel recupero del parametro " + parametroChiaveApplicationCodeIUV.getNomeParametro() +
					   " per la posizione debitoria " + posDeb,
			e);
	    }
	    Integer appCode = null;
	    log.debug("appCodeDaCausale {}", appCodeDaCausale);
	    if (StringUtils.isNotBlank(appCodeDaCausale) && Utilities.isInteger(appCodeDaCausale)) {
		appCode = Integer.parseInt(appCodeDaCausale);
	    } else {
		log.debug("appCode lo prendo da profili ente ");
		appCode = profEnte.getApplicationCode() != null ? profEnte.getApplicationCode() : 0;
	    }
	    log.debug("appCode {}", appCode);
	    iuv.append(StringUtils.leftPad(appCode.toString(), 3, '0'));
	    //e 10 che codificano l'id della posizione debitoria
	    iuv.append(StringUtils.leftPad(Integer.toString(posDeb.getId().getCodice()), 10, '0'));
	    //2 caratteri numerici di controllo calcolati come resto della divisione per 93 della parte precedente dello IUV. 
	    BigInteger checkDigit = new BigInteger(iuv.toString());
	    checkDigit = checkDigit.remainder(BigInteger.valueOf(PagoPAServiceImpl.IUV_CHECK_DIGIT_DIVISOR));
	    iuv.append(StringUtils.leftPad(checkDigit.toString(), 2, '0'));
	}
	return new IUVHelper(iuv.toString(), IRIS_IUV_AUX_DIGIT, "");
    }

    private void prepareIUVWsPort(Object wsPort, PayConnectorWsEndpoint endpointCfg) throws PayException {

	Client client = ClientProxy.getClient(wsPort);
	HTTPConduit conduit = (HTTPConduit) client.getConduit();
	String keyStoreLocation = this.payConnectorConfigValuesService.getValoreParametroConfigurazione(ConfigParamNames.SSL_KEY_STORE_LOCATION);
	if (StringUtils.isNotBlank(keyStoreLocation)) {
	    String keyStorePassw = this.payConnectorConfigValuesService.getValoreParametroConfigurazione(ConfigParamNames.SSL_KEY_STORE_PASSWORD);
	    TLSClientParameters tlsCP = new TLSClientParameters();
	    try {
		KeyStore keyStore = KeyStore.getInstance("JKS");
		keyStore.load(new FileInputStream(keyStoreLocation), keyStorePassw.toCharArray());
		KeyManager[] myKeyManagers = TrustManagerUtils.getKeyManagers(keyStore, keyStorePassw);
		tlsCP.setKeyManagers(myKeyManagers);
		tlsCP.setTrustManagers(new TrustManager[] { new TrustAllX509TrustManager() });
		conduit.setTlsClientParameters(tlsCP);
	    } catch (IOException | GeneralSecurityException e) {
		throw new PayException("errore nella creazione del client SOAP", e);
	    }
	}
	if (endpointCfg.getTimeout() != null) {
	    HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
	    httpClientPolicy.setConnectionTimeout(default_connection_timeout);
	    httpClientPolicy.setReceiveTimeout(endpointCfg.getTimeout());
	    conduit.setClient(httpClientPolicy);
	}
    }

    /**
     * Nel caso il servizio utilizzi la generazione dello IUV da parte di IRIS allora esiste la configurazione
     * getWsIuvConfig. Se non configurato allora lo calcoliamo noi. E' sempre preferibile chiamare un servizio esterno
     * che genera gli IUV.
     * 
     * @param enteCfg
     * @param payReg
     * @param payPos
     * @param dpir
     * @throws PayException
     */
    private void gestisciRiferimentiPagamento(PayProfiliEntiCreditori enteCfg, PayRegistrazioniContabili payReg, PayPosizioniDebitorie payPos,
	    DettaglioPagamentoInsertReplace dpir) throws PayException {

	PayConnectorWsEndpoint wsIuvConfig = getWsIuvConfig();
	if (wsIuvConfig != null) {
	    GeneraIUVRequest generaIUVRequest = new GeneraIUVRequest();
	    generaIUVRequest.setIdentificativoDominio(enteCfg.getCfEnteQrcodePagopa());
	    String tipoPendenza = posizioniDebitorieCommandService.findCodiceVersamentoFromPosizioneDebitoria(payPos);
	    generaIUVRequest.setTipoDebito(tipoPendenza);
	    GenerazioneIUV generazioneIUVClient = this.getGenerazioneIUVPort(wsIuvConfig);
	    GeneraIUVResponseType generaIUVResponseType = generazioneIUVClient.generaIUV(generaIUVRequest);
	    dpir.setIdPagamento(generaIUVResponseType.getBody().getElencoIdentificativi().getIdentificativoUnivocoVersamento());
	    payPos.setIdPosizionePsp(generaIUVResponseType.getBody().getElencoIdentificativi().getIdentificativoUnivocoVersamento());
	    payPos.setIuv(generaIUVResponseType.getBody().getElencoIdentificativi().getIdentificativoUnivocoVersamento());
	    payPos.setCodiceAvviso(generaIUVResponseType.getBody().getElencoIdentificativi().getNumeroAvviso());
	    payPos.setQrCode(this.pagoPAService.generaQRCode(payPos));
	} else {
	    String iuv = getIUV(payPos, null);
	    dpir.setIdPagamento(iuv);
	    payPos.setIdPosizionePsp(iuv);
	    payPos.setIuv(iuv);
	    payPos.setCodiceAvviso(new CodiceAvvisoHelper(iuv, IRIS_IUV_AUX_DIGIT, "").getCodiceAvviso());
	    payPos.setQrCode(this.pagoPAService.generaQRCode(payPos));
	}
    }

    private GenerazioneIUV getGenerazioneIUVPort(PayConnectorWsEndpoint wsCfg) throws PayException {

	if (wsCfg == null) {
	    throw new PayConfigurationException("Il WS per la generazione dei iuv non è configurato per il connettorre PagoUmbria.");
	}
	GenerazioneIUV ws = (GenerazioneIUV) this.getWSPort(wsCfg, GenerazioneIUV.class);
	this.prepareIUVWsPort(ws, wsCfg);
	return ws;
    }

    @Override
    public boolean supportaMolteCausaliRaggruppate() {

	return true;
    }

    @Override
    public List<IParameter> getListaParametriRichiesti() {

	return ParametriConnettoreHelper.getMappaParametriConnettore(this.getClass().getName());
    }
}
