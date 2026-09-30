/**
 * 
 */
package it.gruppoinit.pal.gp.pay.connector.pagoumbria;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.message.Message;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.exceptions.ValidazionePosizioniDebitorieException;
import it.gruppoinit.pal.gp.core.utils.CryptoUtils;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.pal.gp.pay.command.PosizioniDebitorieCommand;
import it.gruppoinit.pal.gp.pay.command.RichiestaSuListaPosizioniCommand;
import it.gruppoinit.pal.gp.pay.connector.AbstractPayConnector;
import it.gruppoinit.pal.gp.pay.connector.IPayConnector;
import it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.caricaposizioni.ComunicazionePosizioniDebitorieOTF;
import it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.caricaposizioni.IdpAllineamentoPendenzeEnteOTF;
import it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.caricaposizioni.IdpAllineamentoPendenzeEnteOTFEsito;
import it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.caricaposizioni.schema.Destinatari;
import it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.caricaposizioni.schema.Destinatario;
import it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.caricaposizioni.schema.DettaglioImporto;
import it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.caricaposizioni.schema.DettaglioPagamentoInsertReplace;
import it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.caricaposizioni.schema.IdpAllineamentoPendenzeOTF;
import it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.caricaposizioni.schema.IdpBody;
import it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.caricaposizioni.schema.Mittente;
import it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.caricaposizioni.schema.Pendenza;
import it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.caricaposizioni.schema.PendenzaInsertReplace;
import it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.caricaposizioni.schema.PendenzaInsertReplace.InfoPagamento;
import it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.caricaposizioni.schema.VoceImporto;
import it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.caricaposizioni.schema.esito.Dettaglio;
import it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.caricaposizioni.schema.esito.Esito;
import it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.caricaposizioni.schema.esito.IdpEsitoOTF;
import it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.caricaposizioni.schema.esito.InfoMessaggio;
import it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.caricaposizioni.schema.esito.StatoMessaggio;
import it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.caricaposizioni.schema.header.E2EReceiver;
import it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.caricaposizioni.schema.header.E2ESender;
import it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.caricaposizioni.schema.header.HeaderE2E;
import it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.caricaposizioni.schema.header.HeaderTRT;
import it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.caricaposizioni.schema.header.IdpHeader;
import it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.caricaposizioni.schema.header.IdpOTF;
import it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.caricaposizioni.schema.header.ServiceName;
import it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.caricaposizioni.schema.header.TRTReceiver;
import it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.caricaposizioni.schema.header.TRTSender;
import it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.caricaposizioni.schema.include.Divisa;
import it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.caricaposizioni.schema.include.StatoPagamento;
import it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.caricaposizioni.schema.include.StatoPendenza;
import it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.caricaposizioni.schema.include.TipoDestinatario;
import it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.caricaposizioni.schema.include.TipoOperazione;
import it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.caricaposizioni.schema.include.TipoPagamento;
import it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.generaiuv.GeneraIUVRequest;
import it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.generaiuv.GeneraIUVResponseType;
import it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.generaiuv.GenerazioneIUV;
import it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.generazioneavvisi.GeneraAvvisoRequest;
import it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.generazioneavvisi.GeneraAvvisoResponseType;
import it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.generazioneavvisi.GenerazioneAvvisi;
import it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.verificaposizioni.IdpVerificaStatoPagamenti;
import it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.verificaposizioni.IdpVerificaStatoPagamentiEsito;
import it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.verificaposizioni.VerificaStatoPagamento;
import it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.verificaposizioni.schema.IdPagamento;
import it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.verificaposizioni.schema.IdpVerificaStatoPagamento;
import it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.verificaposizioni.schema.Pagamento;
import it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.verificaposizioni.schema.Voce;
import it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.verificaposizioni.schema.esito.IdpEsitoVerifica;
import it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.verificaposizioni.schema.esito.InformazioniPagamentoType;
import it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.verificaposizioni.schema.esito.VerificaStatoPagamentoDettagliato;
import it.gruppoinit.pal.gp.pay.domain.PayConnectorConfigParams.ConfigParamNames;
import it.gruppoinit.pal.gp.pay.domain.PayConnectorWsEndpoint;
import it.gruppoinit.pal.gp.pay.domain.PayDettaglioImporti;
import it.gruppoinit.pal.gp.pay.domain.PayPosizioniDebitorie;
import it.gruppoinit.pal.gp.pay.domain.PayProfiliEntiCreditori;
import it.gruppoinit.pal.gp.pay.domain.PayRegistrazioniContabili;
import it.gruppoinit.pal.gp.pay.domain.PaySessioniPagamento;
import it.gruppoinit.pal.gp.pay.domain.PaySoggettiDebitori;
import it.gruppoinit.pal.gp.pay.domain.PayStatoPagamenti;
import it.gruppoinit.pal.gp.pay.exception.PayConfigurationException;
import it.gruppoinit.pal.gp.pay.exception.PayException;
import it.gruppoinit.pal.gp.pay.exception.PayInvalidRequestException;
import it.gruppoinit.pal.gp.pay.parameters.IParameter;
import it.gruppoinit.pal.gp.pay.service.PagoPAService;
import it.gruppoinit.pal.gp.pay.service.PayConnectorConfigValuesService;
import it.gruppoinit.pal.gp.pay.service.PayPosizioniDebitorieService;
import it.gruppoinit.pal.gp.pay.service.PayRegistrazioniContabiliService;
import it.gruppoinit.pal.gp.pay.service.PaySessioniPagamentoService;
import it.gruppoinit.pal.gp.pay.service.PayStatoPagamentiService;
import it.gruppoinit.pal.gp.pay.service.PosizioniDebitorieCommandService;
import it.gruppoinit.pal.gp.pay.service.helper.IUVHelper;
import it.gruppoinit.pal.gp.pay.service.helper.ParametriConnettoreHelper;
import it.gruppoinit.pal.gp.pay.service.helper.PayConfigurationHelper;
import it.gruppoinit.pal.gp.pay.service.helper.PopolamentoDatiHelper;
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
public class PagoUmbriaPayConnector extends AbstractPayConnector implements IPayConnector {

    private static final String ID_PENDENZA_OTF_PREFIX = "REGOTF-";
    private static final String ID_POSIZIONE_PSP_PREFIX = "PDEB-";
    private static final String ID_PENDENZA_PREFIX = "REG-";
    private static final Logger log = LoggerFactory.getLogger(PagoUmbriaPayConnector.class);
    private static final String RECEIVER_ID = "PAGOUMBRIA";
    private static final String RECEIVER_SYS = "SIL_PAGOUMBRIA_ITR";
    private static final String FORM_PARAM_TOKEN = "token";
    private static final String VERSIONE = "01.03-02";
    private static final String DEFAULT_TIPO_VOCE_IMPORTO = "ONERI";
    private static final String DEFAULT_CODICE_VOCE_IMPORTO = "000";
    private static final int ANNI_VALIDITA_PAGAMENTO = 10;
    private ComunicazionePosizioniDebitorieOTF comunicazionePosizioniDebitorieOTFPort;
    private VerificaStatoPagamento verificaStatoPagamento;
    private GenerazioneAvvisi generazioneAvvisi;
    private GenerazioneIUV generazioneiuv;
    private SecurityTokenManager tokenManager;
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
    @Autowired
    private PayRegistrazioniContabiliService payRegistrazioniContabiliService;

    @Override
    public ElencoPosizioniDebitorieEsitoType registraPosizioniDebitorie(PosizioniDebitorieCommand datiRegistrazioniCommand) throws PayException {

	ElencoPosizioniDebitorieEsitoType fullEsito = new ElencoPosizioniDebitorieEsitoType();
	boolean esitoDefault = false;
	StatoPagamentoType statoPosDefault = StatoPagamentoType.ACQUISITO;
	String msgDefault = PayStatoPagamenti.StatiPagamento.ACQUISITO.description();
	ComunicazionePosizioniDebitorieOTF port = this.getComunicazionePosizioniDebitorieOTFPort();
	//creo elenco di esiti OK se l'invocazione WS non ha dato eccezione e ko se è fallita
	for (PayRegistrazioniContabili payReg : datiRegistrazioniCommand.getRegistrazioniPosizioni()) {
	    boolean rateizzato = payReg.getPosizioniDebitorie().size() > 1;
	    for (PayPosizioniDebitorie payPos : payReg.getPosizioniDebitorie()) {
		EsitoOperazionePosizioneDebitoriaType esitoPos = new EsitoOperazionePosizioneDebitoriaType();
		esitoPos.setEsito(esitoDefault);
		PopolamentoDatiHelper.completaDatiDaPosizioneDebitoria(esitoPos, payPos,
			payPosizioniDebitorieService.findRiferimentiClientByPosizioneDebitoria(payPos.getId().getCodice()));
		esitoPos.setStato(statoPosDefault);
		esitoPos.setMessaggio(msgDefault);
		try {
		    IdpAllineamentoPendenzeEnteOTF requestData = this.popolaInserimentoPosizioneDebitoria(payPos, rateizzato);
		    IdpAllineamentoPendenzeEnteOTFEsito response = port.idpAllineamentoPendenzeEnteOTF(requestData);
		    esitoPos.setEsito(true);
		    esitoPos.setStato(StatoPagamentoType.ATTIVATO_IN_PSP);
		    esitoPos.setMessaggio(PayStatoPagamenti.StatiPagamento.ATTIVATO_IN_PSP.description());
		    gestisciVerificaErroreEsitoRegistrazionePosizione(payPos, esitoPos, response.getIdpEsitoOTF());
		} catch (Exception e) {
		    log.error("Errore nell' inserimento della pendenza: " + payPos.getIdPosizionePsp(), e);
		    this.handleException(e, esitoPos);
		}
		fullEsito.getEsitoPosizione().add(esitoPos);
	    }
	}
	return fullEsito;
    }

    @Override
    public AttivaPagamentoOnTheFlyResponseType attivaPagamentoOnTheFly(PosizioniDebitorieCommand pdCmd) throws PayException {

	AttivaPagamentoOnTheFlyResponseType response = new AttivaPagamentoOnTheFlyResponseType();
	StatoPagamentoType statoPosDefault = StatoPagamentoType.ACQUISITO;
	String msgDefault = PayStatoPagamenti.StatiPagamento.ACQUISITO.description();
	boolean esitoDefault = false;
	IdpEsitoOTF esitoPU = null;
	try {
	    //POTREBBE ESSERE GUID
	    IdpAllineamentoPendenzeEnteOTF wsReq = this.popolaAllineamentoPendenzeOTF(pdCmd.getRegistrazioniPosizioni());
	    IdpAllineamentoPendenzeEnteOTFEsito wsResp = this.getComunicazionePosizioniDebitorieOTFPort().idpAllineamentoPendenzeEnteOTF(wsReq);
	    esitoPU = wsResp.getIdpEsitoOTF();
	    statoPosDefault = StatoPagamentoType.ATTIVATO_IN_PSP;
	    msgDefault = PayStatoPagamenti.StatiPagamento.ATTIVATO_IN_PSP.description();
	    esitoDefault = true;
	} catch (Exception e) {
	    log.error("invocaAllineamentoPendenzeSync - errore nell'invocazione WS PagoUmbria: idpAllineamentoPendenzeEnteOTF{}", e.getMessage(), e);
	    throw new PayException("Errore nella creazione della posizione OTF " + e.getMessage(), e);
	}
	List<PayRegistrazioniContabili> rcs = pdCmd.getRegistrazioniPosizioni();
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
			    if (dettEsito.getId().equals(payPosDeb.getIuv())) {
				Esito esitoDettEsito = null;
				if (dettEsito.getEsiti() != null && !dettEsito.getEsiti().getEsito().isEmpty()) {
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

	return gestisciAnnullamento(datiRegistrazioniCommand, pagatoOffline);
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

    private ComunicazionePosizioniDebitorieOTF getComunicazionePosizioniDebitorieOTFPort() throws PayException {

	PayConnectorWsEndpoint wsCaricamentoCfg = this.getWsCaricamentoConfig();
	if (wsCaricamentoCfg == null) {
	    throw new PayConfigurationException("Il WS per il caricamento delle posizioni non è configurato per il connettorre PagoUmbria.");
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
	    throw new PayConfigurationException("Il WS per la verifica dello stato dei pagamenti non è configurato per il connettorre PagoUmbria.");
	}
	VerificaStatoPagamento ws = this.verificaStatoPagamento == null ? (VerificaStatoPagamento) this.getWSPort(wsCfg, VerificaStatoPagamento.class)
		: this.verificaStatoPagamento;
	this.prepareWsPort(ws, wsCfg);
	this.verificaStatoPagamento = ws;
	return ws;
    }

    private GenerazioneAvvisi getGenerazioneAvvisiPort() throws PayException {

	PayConnectorWsEndpoint wsCfg = this.getWsAvvisoConfig();
	if (wsCfg == null) {
	    throw new PayConfigurationException("Il WS per la generazione dei avvisi non è configurato per il connettorre PagoUmbria.");
	}
	GenerazioneAvvisi ws = this.generazioneAvvisi == null ? (GenerazioneAvvisi) this.getWSPort(wsCfg, GenerazioneAvvisi.class)
		: this.generazioneAvvisi;
	this.prepareWsPort(ws, wsCfg);
	this.generazioneAvvisi = ws;
	return ws;
    }

    private GenerazioneIUV getGenerazioneIUVPort() throws PayException {

	PayConnectorWsEndpoint wsCfg = this.getWsIuvConfig();
	if (wsCfg == null) {
	    throw new PayConfigurationException("Il WS per la generazione dei iuv non è configurato per il connettorre PagoUmbria.");
	}
	GenerazioneIUV ws = this.generazioneiuv == null ? (GenerazioneIUV) this.getWSPort(wsCfg, GenerazioneIUV.class) : this.generazioneiuv;
	this.prepareWsPort(ws, wsCfg);
	this.generazioneiuv = ws;
	return ws;
    }

    private void prepareWsPort(Object wsPort, PayConnectorWsEndpoint wsCaricamentoCfg) throws PayException {

	Client client = ClientProxy.getClient(wsPort);
	HTTPConduit conduit = (HTTPConduit) client.getConduit();
	if (wsCaricamentoCfg.getTimeout() != null) {
	    HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
	    httpClientPolicy.setConnectionTimeout(wsCaricamentoCfg.getTimeout());
	    httpClientPolicy.setReceiveTimeout(wsCaricamentoCfg.getTimeout());
	    conduit.setClient(httpClientPolicy);
	}
	//imposto il token di autenticazione OAuth nell'header HTTP Authorization
	Map<String, List<String>> headers = new HashMap<>();
	String token = this.getSecurityTokenManager().getSecurityToken();
	headers.put("Authorization", Arrays.asList("Bearer " + token));
	client.getRequestContext().put(Message.PROTOCOL_HEADERS, headers);
    }

    private SecurityTokenManager getSecurityTokenManager() {

	if (tokenManager == null) {
	    tokenManager = new SecurityTokenManager();
	}
	return tokenManager;
    }

    /*
     * popola la request del WS pagamento OTF
     */
    private IdpAllineamentoPendenzeEnteOTF popolaAllineamentoPendenzeOTF(List<PayRegistrazioniContabili> registrazioni) throws PayException {

	IdpAllineamentoPendenzeEnteOTF allineamentoPendenzeEnteOTF = new IdpAllineamentoPendenzeEnteOTF();
	IdpAllineamentoPendenzeOTF allineamentoPendenzeOTF = new IdpAllineamentoPendenzeOTF();
	allineamentoPendenzeOTF.setVersione(VERSIONE);
	allineamentoPendenzeEnteOTF.setIdpAllineamentoPendenzeOTF(allineamentoPendenzeOTF);
	//sezione header
	PayProfiliEntiCreditori enteCfg = PayConfigurationHelper.getProfiloEnteCreditore();
	IdpHeader header = this.popolaIdpHeader(enteCfg);
	allineamentoPendenzeOTF.setIdpHeader(header);
	//sezione OTF Header per pagamenti on the fly
	PayPosizioniDebitorie posDeb = null;
	if (!registrazioni.isEmpty()) {
	    PayRegistrazioniContabili regcont = registrazioni.get(0);
	    if (!regcont.getPosizioniDebitorie().isEmpty()) {
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
	String offLinePaymentMethods = this.payConnectorConfigValuesService
		.getValoreParametroConfigurazione(ConfigParamNames.OFFLINE_PAYMENT_METHODS);
	log.debug("offLinePaymentMethods {}", offLinePaymentMethods);
	otf.setOFFLINEPAYMENTMETHODS(StringUtils.defaultString(offLinePaymentMethods, "false").equalsIgnoreCase("true"));
	allineamentoPendenzeOTF.setIdpOTF(otf);
	//sezione body con i dati delle posizioni da caricare
	IdpBody body = new IdpBody();
	allineamentoPendenzeOTF.setIdpBody(body);
	for (PayRegistrazioniContabili payReg : registrazioni) {
	    //dati della registrazione contabile
	    String codiceVersamento = posizioniDebitorieCommandService
		    .findCodiceVersamentoFromPosizioneDebitoria(payReg.getPosizioniDebitorie().iterator().next());
	    Pendenza pendenza = null;
	    pendenza = createPendenza(enteCfg, codiceVersamento, TipoOperazione.INSERT);
	    pendenza.setIdPendenza(getIdPendenzaOTF(payReg));
	    pendenza.setNote(payReg.getNote());
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
		cal.setTime(now);
		cal.add(Calendar.DATE, -1);
		dpir.setDataInizioValidita(Utilities.getXMLGregorianCalendar(cal));
		cal.roll(Calendar.YEAR, ANNI_VALIDITA_PAGAMENTO);
		if (payPos.getDataScadenza() != null) {
		    dpir.setDataScadenza(Utilities.getXMLGregorianCalendar(payPos.getDataScadenza()));
		} else {
		    dpir.setDataScadenza(Utilities.getXMLGregorianCalendar(cal));
		}
		dpir.setDataFineValidita(cal); //  OLTRE AL QUALE IL PAGAMENTO VIENE INVALIDATO
		//generazione IUV, Codice Avviso e QRCODE
		GeneraIUVRequest generaIUVRequest = new GeneraIUVRequest();
		generaIUVRequest.setIdentificativoDominio(enteCfg.getCfEnteQrcodePagopa());
		generaIUVRequest.setTipoDebito(codiceVersamento);
		GenerazioneIUV generazioneIUVClient = this.getGenerazioneIUVPort();
		GeneraIUVResponseType generaIUVResponseType = generazioneIUVClient.generaIUV(generaIUVRequest);
		dpir.setIdPagamento(generaIUVResponseType.getBody().getElencoIdentificativi().getIdentificativoUnivocoVersamento());
		payPos.setIdPosizionePsp(getIdPosizionePsp(payPos));
		payPos.setIuv(generaIUVResponseType.getBody().getElencoIdentificativi().getIdentificativoUnivocoVersamento());
		payPos.setCodiceAvviso(generaIUVResponseType.getBody().getElencoIdentificativi().getNumeroAvviso());
		payPos.setQrCode(this.pagoPAService.generaQRCode(payPos));
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
			//PagoUmbria da "errore database" se manca il codice voce importo anche se non è obbligatorio a livello di xsd 
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
	return allineamentoPendenzeEnteOTF;
    }

    private Pendenza createPendenza(PayProfiliEntiCreditori enteCfg, String tipoPendenza, TipoOperazione tipoOperazione) {

	Pendenza pendenza = new Pendenza();
	pendenza.setTipoOperazione(tipoOperazione);
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
	dest.setDescrizione(sistemaLunghezza70Chars(sbName.toString()));
	return dest;
    }

    // pagoumbria ha limite di 70 sul richiedente della posizione debitoria
    // in accordo con Petesse abbiamo deciso di limitare a 70 la stringa del richiedente
    private String sistemaLunghezza70Chars(String nominativo) {

	if (StringUtils.isBlank(nominativo)) {
	    return nominativo;
	}
	if (nominativo.trim().length() <= 70) {
	    return nominativo;
	}
	return nominativo.trim().substring(0, 69);
    }

    private IdpHeader popolaIdpHeader(PayProfiliEntiCreditori enteCfg) {

	IdpHeader header = new IdpHeader();
	HeaderTRT trt = new HeaderTRT();
	trt.setMsgId(UUID.randomUUID().toString()); //GUID
	/*
	 * l'id messaggio deve essere quello di un nuovo PayIoEventi che verrà creato per ogni singola invocazione del
	 * WS del PSP, tutti gli ioeventi di chiamata al PSP devono avere lo stesso codice comunicazione dell'IOEventi
	 * passato come argomento che rappresenta invece l'evento di invocazione iniziale del servizio di caricamento
	 * delle posizioni esposto dal nodo pagamenti
	 */
	trt.setServiceName(ServiceName.IDP_ALLINEAMENTO_PENDENZE);
	Date now = new Date();
	// l'xsd definisce il campo come anySimpleType verificare che XMLGregorianCalendar sia accettato
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
	e2e.setE2ESrvcNm(trt.getServiceName().value());
	E2ESender e2eSender = new E2ESender();
	e2eSender.setE2ESndrId(sender.getSenderId());
	e2eSender.setE2ESndrSys(sender.getSenderSys());
	e2e.setSender(e2eSender);
	E2EReceiver e2eReceiver = new E2EReceiver();
	e2eReceiver.setE2ERcvrId(receiver.getReceiverId());
	e2eReceiver.setE2ERcvrSys(receiver.getReceiverSys());
	e2e.setReceiver(e2eReceiver);
	header.setE2E(e2e);
	return header;
    }

    private it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.verificaposizioni.schema.header.IdpHeader popolaIdpHeaderVerifica(
	    PayProfiliEntiCreditori enteCfg) {

	it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.verificaposizioni.schema.header.IdpHeader header = new it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.verificaposizioni.schema.header.IdpHeader();
	it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.verificaposizioni.schema.header.HeaderTRT trt = new it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.verificaposizioni.schema.header.HeaderTRT();
	/*
	 * l'id messaggio deve essere quello di un nuovo PayIoEventi che verrà creato per ogni singola invocazione del
	 * WS del PSP, tutti gli ioeventi di chiamata al PSP devono avere lo stesso codice comunicazione dell'IOEventi
	 * passato come argomento che rappresenta invece l'evento di invocazione iniziale del servizio di caricamento
	 * delle posizioni esposto dal nodo pagamenti
	 */
	//trt.setMsgId(cmd.getIdMessaggio());
	trt.setServiceName(
		it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.verificaposizioni.schema.header.ServiceName.IDP_INFORMATIVA_PAGAMENTO);
	Date now = new Date();
	// l'xsd definisce il campo come anySimpleType verificare che XMLGregorianCalendar sia accettato
	trt.setXMLCrtDt(Utilities.getXMLGregorianCalendar(now));
	it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.verificaposizioni.schema.header.TRTSender sender = new it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.verificaposizioni.schema.header.TRTSender();
	sender.setSenderId(enteCfg.getCfCodiceProfiloPSP());
	sender.setSenderSys(enteCfg.getIdAppPSP());
	trt.setSender(sender);
	it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.verificaposizioni.schema.header.TRTReceiver receiver = new it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.verificaposizioni.schema.header.TRTReceiver();
	receiver.setReceiverId(RECEIVER_ID);
	receiver.setReceiverSys(RECEIVER_SYS);
	trt.setReceiver(receiver);
	header.setTRT(trt);
	it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.verificaposizioni.schema.header.HeaderE2E e2e = new it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.verificaposizioni.schema.header.HeaderE2E();
	e2e.setE2EMsgId(trt.getMsgId());
	e2e.setE2ESrvcNm(trt.getServiceName().value());
	it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.verificaposizioni.schema.header.E2ESender e2eSender = new it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.verificaposizioni.schema.header.E2ESender();
	e2eSender.setE2ESndrId(sender.getSenderId());
	e2eSender.setE2ESndrSys(sender.getSenderSys());
	e2e.setSender(e2eSender);
	it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.verificaposizioni.schema.header.E2EReceiver e2eReceiver = new it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.verificaposizioni.schema.header.E2EReceiver();
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
	it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.verificaposizioni.schema.header.IdpHeader header = this
		.popolaIdpHeaderVerifica(enteCfg);
	verificaParam.setIdpHeader(header);
	it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.verificaposizioni.schema.IdpVerificaStatoPagamento.IdpBody body = new it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.verificaposizioni.schema.IdpVerificaStatoPagamento.IdpBody();
	verificaParam.setIdpBody(body);
	body.setRichiestaInformazioniPagamento(Boolean.TRUE);
	List<PayPosizioniDebitorie> payPosizioni = cmd.getPosizioni();
	for (PayPosizioniDebitorie payPos : payPosizioni) {
	    IdPagamento idPagamento = new IdPagamento();
	    idPagamento.setValue(payPos.getIuv());
	    String tipoPendenza = posizioniDebitorieCommandService.findCodiceVersamentoFromPosizioneDebitoria(payPos);
	    idPagamento.setTipoPendenza(tipoPendenza);
	    body.getIdPagamento().add(idPagamento);
	}
	verificaParam.setVersione(PagoUmbriaPayConnector.VERSIONE);
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
		if (infoStati != null && !infoStati.isEmpty()) {
		    for (it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.verificaposizioni.schema.esito.InformazioniPagamentoType infoStato : infoStati) {
			StatoPosizioneType outputStatoPos = this.popolaEsitoVerificaStatoPosizione(infoStato, cmd);
			retVal.getStatoPosizioni().add(outputStatoPos);
		    }
		}
	    }
	}
	return retVal;
    }

    private StatoPosizioneType popolaEsitoVerificaStatoPosizione(
	    it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.verificaposizioni.schema.esito.InformazioniPagamentoType infoVerificaStato,
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
	    case POSIZIONE_NON_PAGATA: // se non pagata lascio lo stato ATTIVATO_IN_PSP #Ticket#2025091110000175	
	    case POSIZIONE_CON_PAG_IN_CORSO:
	    case POSIZIONE_CON_DOC_EMESSO:
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
			    .equals(it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.verificaposizioni.schema.TipoVoce.IMPORTO_COMMISSIONI)) {
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

    class SecurityTokenManager {

	private static final long DEFAULT_TOKEN_DURATION_MILLIS = 60 * 60 * 1000;
	private long tokenGenerationTime = 0;
	private String token = null;
	private long tokenDurationMillis = 0;

	SecurityTokenManager() {

	    this.tokenDurationMillis = DEFAULT_TOKEN_DURATION_MILLIS;
	}

	SecurityTokenManager(long tokenDuration) {

	    this.tokenDurationMillis = tokenDuration;
	}

	public String getSecurityToken() throws PayException {

	    if (!checkTokenValidity()) {
		this.token = refreshToken();
	    }
	    return this.token;
	}

	public boolean checkTokenValidity() {

	    if (token == null) {
		return false;
	    } else {
		long nowMillis = new Date().getTime();
		return nowMillis - tokenGenerationTime < tokenDurationMillis;
	    }
	}

	private String refreshToken() throws PayException {

	    PayConnectorWsEndpoint securityService = getWsSecurityConfig();
	    if (securityService == null) {
		throw new PayConfigurationException(
			"Impossibile generare iul token di sicurezza: non è configurtato l'endpont per il servizio apposito.");
	    }
	    String tkn = null;
	    try {
		URL secUrl = new URL(securityService.getEndpointUrl());
		HttpURLConnection conn = (HttpURLConnection) secUrl.openConnection();
		conn.setDoOutput(true);
		conn.setDoInput(true);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		String encodedKeys = securityService.getUtente() + ":" + securityService.getPassword();
		encodedKeys = CryptoUtils.base64Encode(encodedKeys.getBytes());
		conn.setRequestProperty("Authorization", "Basic " + encodedKeys);
		//conn.connect();
		String reqParams = "grant_type=client_credentials";
		OutputStream outStream = conn.getOutputStream();
		DataOutputStream out = new DataOutputStream(outStream);
		out.writeBytes(reqParams);
		out.flush();
		out.close();
		int responseCode = conn.getResponseCode();
		if (responseCode == HttpURLConnection.HTTP_OK) {
		    BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
		    ByteArrayOutputStream baos = new ByteArrayOutputStream();
		    byte[] buf = new byte[1024];
		    int read = 0;
		    while ((read = bis.read(buf)) > -1) {
			baos.write(buf, 0, read);
		    }
		    String jsonOut = new String(baos.toByteArray(), "UTF-8");
		    JSONObject json = new JSONObject(jsonOut);
		    this.tokenDurationMillis = json.getInt("expires_in") * 1000;
		    this.token = json.getString("access_token");
		    this.tokenGenerationTime = new Date().getTime();
		    tkn = this.token;
		}
	    } catch (Exception e) {
		String msg = "errore nella generazione di un nuovo token di sicurezza";
		log.error("SecurityTokenManager.refreshToken() - " + msg, e);
		throw new PayException(msg, e);
	    }
	    return tkn;
	}
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
		log.debug("inviaAvvisiPagamento recupero il client per la posizione debitoria {}", pos.getId().getCodice());
		try {
		    GenerazioneAvvisi client = this.getGenerazioneAvvisiPort();
		    log.debug("inviaAvvisiPagamento invoco il metodo getAvviso {}-{}-{}", ente.getCfCodiceProfiloPSP(), pos.getCodiceAvviso(),
			    pos.getId().getCodice());
		    GeneraAvvisoRequest request = new GeneraAvvisoRequest();
		    request.setIdentificativoDominio(ente.getCfEnteQrcodePagopa());
		    request.setIdentificativoUnivocoVersamento(pos.getIuv());
		    GeneraAvvisoResponseType response = client.generaAvviso(request);
		    if (response.getFault() != null) {
			log.error("Errore in chiamata generaAvviso {}:{}", response.getFault().getFaultString(),
				response.getFault().getFaultDescription());
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

    /*
     * popola la request del WS AllineamentoPendenze registrazione posizione
     */
    private IdpAllineamentoPendenzeEnteOTF popolaInserimentoPosizioneDebitoria(PayPosizioniDebitorie pos, boolean rateizzato) throws PayException {

	IdpAllineamentoPendenzeEnteOTF allineamentoPendenzeEnteOTF = new IdpAllineamentoPendenzeEnteOTF();
	IdpAllineamentoPendenzeOTF allineamentoPendenzeOTF = new IdpAllineamentoPendenzeOTF();
	allineamentoPendenzeOTF.setVersione(VERSIONE);
	allineamentoPendenzeEnteOTF.setIdpAllineamentoPendenzeOTF(allineamentoPendenzeOTF);
	//sezione header
	PayProfiliEntiCreditori enteCfg = PayConfigurationHelper.getProfiloEnteCreditore();
	IdpHeader header = this.popolaIdpHeader(enteCfg);
	allineamentoPendenzeOTF.setIdpHeader(header);
	//sezione body con i dati delle posizioni da caricare
	IdpBody body = new IdpBody();
	allineamentoPendenzeOTF.setIdpBody(body);
	//dati della registrazione contabile
	String codiceVersamento = posizioniDebitorieCommandService.findCodiceVersamentoFromPosizioneDebitoria(pos);
	Pendenza pendenza = null;
	pendenza = createPendenza(enteCfg, codiceVersamento, TipoOperazione.INSERT);
	pendenza.setIdPendenza(getIdPendenza(pos));
	pendenza.setNote(pos.getRegistrazioneContabile().getNote());
	PendenzaInsertReplace insert = new PendenzaInsertReplace();
	pendenza.setInsert(insert);
	insert.setDescrizioneCausale(pos.getRegistrazioneContabile().getDescrizione());
	insert.setDataCreazione(header.getTRT().getXMLCrtDt());
	Date now = new Date();
	GregorianCalendar cal = (GregorianCalendar) GregorianCalendar.getInstance();
	cal.setTime(now);
	cal.roll(Calendar.YEAR, ANNI_VALIDITA_PAGAMENTO);
	insert.setDataPrescrizione(Utilities.getXMLGregorianCalendar(cal));
	cal = new GregorianCalendar();
	cal.set(Calendar.YEAR, pos.getRegistrazioneContabile().getAnno());
	insert.setAnnoRiferimento(Utilities.getXMLGregorianCalendar(cal));
	insert.setDataEmissione(Utilities.getXMLGregorianCalendar(now));
	insert.setDivisa(Divisa.EUR);
	insert.setStato(StatoPendenza.APERTA);
	InfoPagamento infoP = new InfoPagamento();
	infoP.setTipoPagamento(TipoPagamento.PAGAMENTO_UNICO);
	insert.getInfoPagamento().add(infoP);
	BigDecimal totRegistrazione = BigDecimal.ZERO;
	DettaglioPagamentoInsertReplace dpir = new DettaglioPagamentoInsertReplace();
	dpir.setStato(StatoPagamento.NON_PAGATO);
	dpir.setCausalePagamento(pos.getDescrizioneCausale());
	if (rateizzato) {
	    dpir.setCausalePagamento(pos.getDescrizioneCausale() + " rata " + pos.getNumRata());
	}
	cal = (GregorianCalendar) GregorianCalendar.getInstance();
	cal.setTime(pos.getDataRegistrazione());
	dpir.setDataInizioValidita(Utilities.getXMLGregorianCalendar(cal));
	cal.roll(Calendar.YEAR, ANNI_VALIDITA_PAGAMENTO);
	if (pos.getDataScadenza() != null) {
	    dpir.setDataScadenza(Utilities.getXMLGregorianCalendar(pos.getDataScadenza()));
	} else {
	    dpir.setDataScadenza(Utilities.getXMLGregorianCalendar(cal));
	}
	dpir.setDataFineValidita(cal); //  OLTRE AL QUALE IL PAGAMENTO VIENE INVALIDATO
	//generazione IUV, Codice Avviso e QRCODE
	GeneraIUVRequest generaIUVRequest = new GeneraIUVRequest();
	generaIUVRequest.setIdentificativoDominio(enteCfg.getCfEnteQrcodePagopa());
	generaIUVRequest.setTipoDebito(codiceVersamento);
	GenerazioneIUV generazioneIUVClient = this.getGenerazioneIUVPort();
	GeneraIUVResponseType generaIUVResponseType = generazioneIUVClient.generaIUV(generaIUVRequest);
	dpir.setIdPagamento(generaIUVResponseType.getBody().getElencoIdentificativi().getIdentificativoUnivocoVersamento());
	pos.setIdPosizionePsp(getIdPosizionePsp(pos));
	pos.setIuv(generaIUVResponseType.getBody().getElencoIdentificativi().getIdentificativoUnivocoVersamento());
	pos.setCodiceAvviso(generaIUVResponseType.getBody().getElencoIdentificativi().getNumeroAvviso());
	pos.setQrCode(this.pagoPAService.generaQRCode(pos));
	//scrivo nella posizione debitoria l'id trasmesso al PSP
	this.payPosizioniDebitorieService.aggiornaPosizioneDebitoria(pos, null, null);
	BigDecimal totPosizione = BigDecimal.ZERO;
	Set<PayDettaglioImporti> payImporti = pos.getDettagliImporto();
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
		//PagoUmbria da "errore database" se manca il codice voce importo anche se non è obbligatorio a livello di xsd 
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
	insert.setImportoTotale(totRegistrazione);
	Destinatari dests = new Destinatari();
	dests.getDestinatario().add(createDestinatario(pos.getSoggettoDebitore()));
	pendenza.setDestinatari(dests);
	body.getPendenza().add(pendenza);
	return allineamentoPendenzeEnteOTF;
    }

    private void gestisciVerificaErroreEsitoRegistrazionePosizione(PayPosizioniDebitorie payPos, EsitoOperazionePosizioneDebitoriaType esitoPos,
	    IdpEsitoOTF esitoPsp) {

	if (esitoPsp != null) {
	    InfoMessaggio infoMessaggio = esitoPsp.getIdpBody().getInfoMessaggio();
	    //se esito complessivo = con errori ciclo i dettagli e 	    
	    if (infoMessaggio.getStato().equals(StatoMessaggio.ELABORATO_CON_ERRORI)) {
		log.error("Si sono verificati errori nell'operazione per la posizione debitoria {}", payPos.getId());
		//per ciascun dettaglio esito con errore recupero l'esito (a uso dl nodo) per idPosizionePSP e aggiorno l'esito a KO e imposto codice e messaggio di errore
		boolean erroreTrovato = false;
		esitoPos.setEsito(false);
		esitoPos.setStato(StatoPagamentoType.CON_ERRORE);
		if (infoMessaggio.getEsiti() != null && !infoMessaggio.getEsiti().getEsito().isEmpty()) {
		    List<Esito> esiti = infoMessaggio.getEsiti().getEsito();
		    Esito esito = esiti.get(0);
		    erroreTrovato = true;
		    esitoPos.setCodiceErrore(esito.getCodice());
		    esitoPos.setMessaggio(esito.getDescrizione());
		    log.error("dettaglio errore nell'operazione per la posizione debitoria {}, esito.getCodice() {}, esito.getDescrizione() {}",
			    payPos.getId(), esito.getCodice(), esito.getDescrizione());
		}
		if (!erroreTrovato && esitoPsp.getIdpBody().getInfoDettaglio() != null
			&& !esitoPsp.getIdpBody().getInfoDettaglio().getDettaglio().isEmpty()) {
		    List<Dettaglio> esitiPsp = esitoPsp.getIdpBody().getInfoDettaglio().getDettaglio();
		    Dettaglio dettaglio = esitiPsp.get(0);
		    if (dettaglio != null) {
			erroreTrovato = true;
			if (dettaglio.getEsiti() != null && !dettaglio.getEsiti().getEsito().isEmpty()) { //N esiti per ogni posizione ?????? prendo il primo
			    Esito esitoPU = dettaglio.getEsiti().getEsito().get(0);
			    esitoPos.setCodiceErrore(esitoPU.getCodice());
			    esitoPos.setMessaggio(esitoPU.getDescrizione() + " (" + esitoPU.getCodice() + ")");
			} else {
			    esitoPos.setCodiceErrore(dettaglio.getStato().value());
			    esitoPos.setMessaggio(dettaglio.getStato().value());
			}
			log.error("dettaglio errore nell'operazione per la posizione debitoria {}, esito.getCodice() {}, esito.getDescrizione() {}",
				payPos.getId(), esitoPos.getCodiceErrore(), esitoPos.getMessaggio());
		    }
		}
		//se non ci sono dettagli sugli errori riscontrati allora imposto tutti gli esiti a KO e lo stato delle posizioni su CON_ERRORE
		if (!erroreTrovato) {
		    log.error("dettaglio errore nell'operazione per la posizione debitoria {} - ERRORE GENERICO", payPos.getId());
		    esitoPos.setMessaggio("si è verificato un errore nell'elaborazione della posizione debitoria");
		}
	    }
	}
	PopolamentoDatiHelper.completaDatiDaPosizioneDebitoria(esitoPos, payPos,
		payPosizioniDebitorieService.findRiferimentiClientByPosizioneDebitoria(payPos.getId().getCodice()));
    }

    private ElencoPosizioniDebitorieEsitoType gestisciAnnullamento(PosizioniDebitorieCommand datiRegistrazioniCommand, boolean pagatoOffline)
	    throws PayException {

	//	quando cancello
	//	se (1 registrazioneContabile ... n posizioni debitorie) (vecchia modalita' di rateizzazione) allora uso il nuovo metodo che annulla (cancella la pendenza) solo se tutte le altre posizioni sono annullate
	//	se (1 registrazioneContabile ... 1 posizione debitoria) (nuova modalita' inserisco per ogni rata una pendenza) allora annulla perche' non devo fare controlli
	//	in caso di otf annullo tutte le posizioni
	// per ogni posizione verificare se la reg contabile ne gestisce più
	List<PayRegistrazioniContabili> registrazioniPosizioni = datiRegistrazioniCommand.getRegistrazioniPosizioni();
	ElencoPosizioniDebitorieEsitoType fullEsito = new ElencoPosizioniDebitorieEsitoType();
	for (PayRegistrazioniContabili payReg : registrazioniPosizioni) {
	    int numPosizioni = payRegistrazioniContabiliService.countPosizioniByIdRegistrazione(payReg.getId().getCodice());
	    log.debug("gestisciAnnullamento per la registrazione contabile {} sono presenti {} posizioni debitorie ", payReg.getId(), numPosizioni);
	    if (numPosizioni == 1) {
		// se no effettua la DELETE		
		fullEsito.getEsitoPosizione().add(annullaPendenza(payReg, pagatoOffline));
	    } else {
		// se si effettua la replace eliminando la singola posizione debitoria
		fullEsito.getEsitoPosizione().add(annullaRegistrazioneConPosizioneMultipla(payReg, pagatoOffline));
	    }
	}
	return fullEsito;
    }

    private EsitoOperazionePosizioneDebitoriaType annullaPendenza(PayRegistrazioniContabili payReg, boolean pagatoOffline) throws PayException {

	IdpEsitoOTF esitoPsp = null;
	log.debug("annullaPendenza per la registrazione contabile {}, pagatoOffline {}", payReg.getId(), pagatoOffline);
	ComunicazionePosizioniDebitorieOTF port = this.getComunicazionePosizioniDebitorieOTFPort();
	PayPosizioniDebitorie payPos = payPosizioniDebitorieService
		.findById(new PkId(payReg.getPosizioniDebitorie().iterator().next().getId().getCodice()));
	EsitoOperazionePosizioneDebitoriaType esitoPos = new EsitoOperazionePosizioneDebitoriaType();
	esitoPos.setEsito(true);
	PopolamentoDatiHelper.completaDatiDaPosizioneDebitoria(esitoPos, payPos,
		payPosizioniDebitorieService.findRiferimentiClientByPosizioneDebitoria(payPos.getId().getCodice()));
	try {
	    IdpAllineamentoPendenzeEnteOTF requestData = this.popolaAllineamentoPendenzePerAnnullamento(payReg, payPos);
	    IdpAllineamentoPendenzeEnteOTFEsito response = port.idpAllineamentoPendenzeEnteOTF(requestData);
	    log.debug("annullaPendenza per la registrazione contabile {} SERVIZIO idpAllineamentoPendenzeEnteOTF INVOCATO", payReg.getId());
	    esitoPsp = response.getIdpEsitoOTF();
	    if (pagatoOffline) {
		esitoPos.setStato(StatoPagamentoType.PAGATO_OFFLINE_ANNULLATO);
		esitoPos.setMessaggio(PayStatoPagamenti.StatiPagamento.PAGATO_OFFLINE_ANNULLATO.description());
	    } else {
		esitoPos.setStato(StatoPagamentoType.ANNULLATO);
		esitoPos.setMessaggio(PayStatoPagamenti.StatiPagamento.ANNULLATO.description());
	    }
	    gestisciVerificaErroreEsitoRegistrazionePosizione(payPos, esitoPos, esitoPsp);
	} catch (Exception e) {
	    log.error("annullaPendenza - errore nell'invocazione WS PagoUmbria: idpAllineamentoPendenzeEnteOTF", e);
	    esitoPos.setEsito(false);
	    esitoPos.setStato(StatoPagamentoType.CON_ERRORE);
	    esitoPos.setMessaggio(e.toString());
	}
	return esitoPos;
    }

    private IdpAllineamentoPendenzeEnteOTF popolaAllineamentoPendenzePerAnnullamento(PayRegistrazioniContabili payReg, PayPosizioniDebitorie payPos)
	    throws PayException {

	IdpAllineamentoPendenzeEnteOTF allineamentoPendenzeEnteOTF = new IdpAllineamentoPendenzeEnteOTF();
	IdpAllineamentoPendenzeOTF allineamentoPendenzeOTF = new IdpAllineamentoPendenzeOTF();
	allineamentoPendenzeOTF.setVersione(VERSIONE);
	allineamentoPendenzeEnteOTF.setIdpAllineamentoPendenzeOTF(allineamentoPendenzeOTF);
	//sezione header
	PayProfiliEntiCreditori enteCfg = PayConfigurationHelper.getProfiloEnteCreditore();
	IdpHeader header = this.popolaIdpHeader(enteCfg);
	allineamentoPendenzeOTF.setIdpHeader(header);
	//sezione body con i dati delle posizioni da caricare
	IdpBody body = new IdpBody();
	allineamentoPendenzeOTF.setIdpBody(body);
	//dati della registrazione contabile
	String codiceVersamento = null;
	try {
	    codiceVersamento = posizioniDebitorieCommandService
		    .findCodiceVersamentoFromPosizioneDebitoria(payReg.getPosizioniDebitorie().iterator().next());
	} catch (PayConfigurationException e) {
	    throw new PayException(e);
	}
	Pendenza pendenza = createPendenza(enteCfg, codiceVersamento, TipoOperazione.DELETE);
	PaySoggettiDebitori paySogg = payPos.getSoggettoDebitore();
	Destinatari dests = new Destinatari();
	pendenza.setDestinatari(dests);
	Destinatario dest = createDestinatario(paySogg);
	dests.getDestinatario().add(dest);
	String idPendenza = calcolaIdPendenza(payPos);
	pendenza.setIdPendenza(idPendenza);
	body.getPendenza().add(pendenza);
	return allineamentoPendenzeEnteOTF;
    }

    private String calcolaIdPendenza(PayPosizioniDebitorie payPos) {

	if (payPos.getIdPosizionePsp().startsWith(ID_POSIZIONE_PSP_PREFIX)) {
	    // sono quelle create con la nuova modalità
	    if (BooleanUtils.isTrue(payPos.getFlagOTF())) {
		return getIdPendenzaOTF(payPos.getRegistrazioneContabile());
	    }
	    return getIdPendenza(payPos);
	} else {
	    return payPos.getRegistrazioneContabile().getId().getCodice() + ""; // le vecchie pendenze venivano create con l'id della registrazione contabile
	}
    }

    private EsitoOperazionePosizioneDebitoriaType annullaRegistrazioneConPosizioneMultipla(PayRegistrazioniContabili payReg, boolean pagatoOffline)
	    throws PayException {

	log.debug("annullaRegistrazioneConPosizioneMultipla per la registrazione contabile {}, pagatoOffline {}", payReg.getId(), pagatoOffline);
	List<Integer> idPosizioniDebitorie = payRegistrazioniContabiliService.findIdPosizioniByIdRegistrazione(payReg.getId().getCodice());
	PayPosizioniDebitorie daAnnullare = payPosizioniDebitorieService
		.findById(new PkId(payReg.getPosizioniDebitorie().iterator().next().getId().getCodice()));
	if (BooleanUtils.isTrue(daAnnullare.getFlagOTF()) || daAnnullare.getIdPosizionePsp().startsWith(ID_POSIZIONE_PSP_PREFIX)) {
	    log.debug(
		    "annullaRegistrazioneConPosizioneMultipla annullo la posizione perchè OTF o nuova gestione per la registrazione contabile {}, pagatoOffline {} - idPosizioneDaAnnullare {} - flagOTF {} - idPosizionePSP {}",
		    payReg.getId(), pagatoOffline, daAnnullare.getId(), daAnnullare.getFlagOTF(), daAnnullare.getIdPosizionePsp());
	    // se OTF o nuova gestione annullo la pendenza per OTF devo infatti annullare in blocco come facevo prima della nuova logica	    
	    return annullaPendenza(payReg, pagatoOffline);
	}
	boolean possoAnnullare = true;
	// VECCHIA GESTIONE LE POSIZIONI SONO REGISTRATE CON IDPENDENZA = IDREGISTRAZIONE CONTABILE E DUNQUE SE ANNULLO LA POSIZIONE ANNULLEREI TUTTE LE ALTRE
	// QUINDI ESEGUO LA LOGICA CHE ESEGUO LA REPLACE DELLE PAGATE OTF o DELLE ANNULLATE
	// SE PAGATE O ANCORA APERTE INVECE LASCIO LA RIGA ED ESEGUO LA REPLACE
	log.debug("annullaRegistrazioneConPosizioneMultipla per la registrazione contabile {} presenti le posizioni debitorie {}", payReg.getId(),
		idPosizioniDebitorie);
	// PRENDO LA POSIZIONE DEBITORIA CORRENTE (DA ANNULLARE)
	// VERIFICO SE LE ALTRE POSIZIONI SONO DA ANNULLARE	
	for (Integer idPosizione : idPosizioniDebitorie) {
	    log.debug("annullaRegistrazioneConPosizioneMultipla per la registrazione contabile {} verifico la posizione {}", payReg.getId(),
		    idPosizione);
	    if (!idPosizione.equals(daAnnullare.getId().getCodice())) { // escludo la posizione debitoria che devo annullare / pagare OFFLINE
		PayStatoPagamenti statoPos = payStatoPagamentiService
			.getStatoPosizioneDebitoria(payPosizioniDebitorieService.findById(new PkId(idPosizione)));
		StatoPagamentoType stato = StatoPagamentoType.fromValue(statoPos.getStato());
		log.debug("annullaRegistrazioneConPosizioneMultipla per la registrazione contabile {} lo stato della posizione {} è {}",
			payReg.getId(), idPosizione, stato);
		if (verificaPendenzeNonAnnullabili(stato)) {
		    possoAnnullare = false; // significa che ci sono pendenze PAGABILI O PAGATE ALLORA NON POSSO CANCELLARE LA POSIZIONE DEBITORIA
		}
	    }
	}
	// Se sono annullate allora annullo la pendenza
	if (possoAnnullare) {
	    log.debug(
		    "annullaRegistrazioneConPosizioneMultipla per la registrazione contabile {} posso annullare in quanto tutte le posizioni {} sono in uno stato conpatibile con annullato ",
		    payReg.getId(), idPosizioniDebitorie);
	    return annullaPendenza(payReg, pagatoOffline);
	}
	// EFFETTUO LA REPLACE
	return annullaReplacePendenzaDaPosizione(daAnnullare, pagatoOffline);
    }

    private EsitoOperazionePosizioneDebitoriaType annullaReplacePendenzaDaPosizione(PayPosizioniDebitorie daAnnullare, boolean pagatoOffline)
	    throws PayException {

	EsitoOperazionePosizioneDebitoriaType esitoPos = new EsitoOperazionePosizioneDebitoriaType();
	esitoPos.setEsito(true);
	PopolamentoDatiHelper.completaDatiDaPosizioneDebitoria(esitoPos, daAnnullare,
		payPosizioniDebitorieService.findRiferimentiClientByPosizioneDebitoria(daAnnullare.getId().getCodice()));
	try {
	    IdpAllineamentoPendenzeEnteOTF replace = popolaPendenzaForReplacePosizioniConVecchiaLogica(daAnnullare);
	    ComunicazionePosizioniDebitorieOTF port = this.getComunicazionePosizioniDebitorieOTFPort();
	    IdpAllineamentoPendenzeEnteOTFEsito response = port.idpAllineamentoPendenzeEnteOTF(replace);
	    esitoPos.setEsito(true);
	    if (pagatoOffline) {
		esitoPos.setStato(StatoPagamentoType.PAGATO_OFFLINE_ANNULLATO);
		esitoPos.setMessaggio(PayStatoPagamenti.StatiPagamento.PAGATO_OFFLINE_ANNULLATO.description());
	    } else {
		esitoPos.setStato(StatoPagamentoType.ANNULLATO);
		esitoPos.setMessaggio(PayStatoPagamenti.StatiPagamento.ANNULLATO.description());
	    }
	    gestisciVerificaErroreEsitoRegistrazionePosizione(daAnnullare, esitoPos, response.getIdpEsitoOTF());
	} catch (Exception e) {
	    log.error("Errore nell' inserimento della pendenza: {}", daAnnullare.getIdPosizionePsp());
	    this.handleException(e, esitoPos);
	}
	return esitoPos;
    }

    private IdpAllineamentoPendenzeEnteOTF popolaPendenzaForReplacePosizioniConVecchiaLogica(PayPosizioniDebitorie daAnnullare) throws PayException {

	IdpAllineamentoPendenzeEnteOTF allineamentoPendenzeEnteOTF = new IdpAllineamentoPendenzeEnteOTF();
	IdpAllineamentoPendenzeOTF allineamentoPendenzeOTF = new IdpAllineamentoPendenzeOTF();
	allineamentoPendenzeOTF.setVersione(VERSIONE);
	allineamentoPendenzeEnteOTF.setIdpAllineamentoPendenzeOTF(allineamentoPendenzeOTF);
	//sezione header
	PayProfiliEntiCreditori enteCfg = PayConfigurationHelper.getProfiloEnteCreditore();
	IdpHeader header = this.popolaIdpHeader(enteCfg);
	allineamentoPendenzeOTF.setIdpHeader(header);
	//sezione body con i dati delle posizioni da caricare
	IdpBody body = new IdpBody();
	allineamentoPendenzeOTF.setIdpBody(body);
	PayRegistrazioniContabili payReg = payRegistrazioniContabiliService
		.findById(new PkId(daAnnullare.getRegistrazioneContabile().getId().getCodice()));
	List<PayPosizioniDebitorie> posizioni = payPosizioniDebitorieService
		.findByIdRegistrazioneContabile(daAnnullare.getRegistrazioneContabile().getId().getCodice());
	log.debug("Replace tot posizioni per registrazione contabile {} = {}", daAnnullare.getRegistrazioneContabile().getId(), posizioni.size());
	//dati della registrazione contabile
	String codiceVersamento = posizioniDebitorieCommandService.findCodiceVersamentoFromPosizioneDebitoria(daAnnullare);
	Pendenza pendenza = createPendenza(enteCfg, codiceVersamento, TipoOperazione.REPLACE);
	pendenza.setIdPendenza(payReg.getId().getCodice().toString()); // la vecchia logica inseriva l'identificativo pendenza con id registrazione contabile
	pendenza.setNote(payReg.getNote());
	PendenzaInsertReplace replace = new PendenzaInsertReplace();
	replace.setDescrizioneCausale(payReg.getDescrizione());
	replace.setDataCreazione(payReg.getDataRegistrazione());
	GregorianCalendar cal = (GregorianCalendar) GregorianCalendar.getInstance();
	cal.setTime(payReg.getDataRegistrazione());
	cal.roll(Calendar.YEAR, ANNI_VALIDITA_PAGAMENTO);
	replace.setDataPrescrizione(Utilities.getXMLGregorianCalendar(cal));
	cal = new GregorianCalendar();
	cal.set(Calendar.YEAR, payReg.getAnno());
	replace.setAnnoRiferimento(Utilities.getXMLGregorianCalendar(cal));
	replace.setDataEmissione(Utilities.getXMLGregorianCalendar(payReg.getDataRegistrazione()));
	replace.setDivisa(Divisa.EUR);
	replace.setStato(StatoPendenza.APERTA);
	InfoPagamento infoP = new InfoPagamento();
	infoP.setTipoPagamento(posizioni.size() > 1 ? TipoPagamento.PAGAMENTO_A_RATE : TipoPagamento.PAGAMENTO_UNICO);
	BigDecimal totRegistrazione = BigDecimal.ZERO;
	for (PayPosizioniDebitorie payPos : posizioni) {
	    log.debug("Replace posizione {}-{}", payPos.getId(), payPos.getIdPosizionePsp());
	    if (!payPos.getId().getCodice().equals(daAnnullare.getId().getCodice())) { // con la replace rimuovo la posizione da annullare
		PayStatoPagamenti statoPos = payStatoPagamentiService
			.getStatoPosizioneDebitoria(payPosizioniDebitorieService.findById(new PkId(payPos.getId().getCodice())));
		StatoPagamentoType stato = StatoPagamentoType.fromValue(statoPos.getStato());
		log.debug("Replace posizione {}-{} STATO {}", payPos.getId(), payPos.getIdPosizionePsp(), stato);
		if (!isStatoAnnullato(stato)) {
		    // rimuovo con la replace le posizioni con stato annullato o con errore
		    // lascio solo le pagate e le pagabili	
		    DettaglioPagamentoInsertReplace dpir = new DettaglioPagamentoInsertReplace();
		    if (isStatoPagatoPagoPA(stato)) {
			dpir.setStato(StatoPagamento.PAGATO);
		    } else {
			dpir.setStato(StatoPagamento.NON_PAGATO);
		    }
		    log.debug("Replace posizione {}-{} - dpir.getStato() {}", payPos.getId(), payPos.getIdPosizionePsp(), dpir.getStato());
		    dpir.setCausalePagamento(payPos.getDescrizioneCausale());
		    cal = (GregorianCalendar) GregorianCalendar.getInstance();
		    cal.setTime(payPos.getDataRegistrazione());
		    dpir.setDataInizioValidita(Utilities.getXMLGregorianCalendar(cal));
		    cal.roll(Calendar.YEAR, ANNI_VALIDITA_PAGAMENTO);
		    if (payPos.getDataScadenza() != null) {
			dpir.setDataScadenza(Utilities.getXMLGregorianCalendar(payPos.getDataScadenza()));
		    } else {
			dpir.setDataScadenza(Utilities.getXMLGregorianCalendar(cal));
		    }
		    dpir.setDataFineValidita(cal); // OLTRE AL QUALE IL PAGAMENTO VIENE INVALIDATO
		    dpir.setIdPagamento(payPos.getIuv());
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
			    //PagoUmbria da "errore database" se manca il codice voce importo anche se non è obbligatorio a livello di xsd 
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
	    }
	}
	replace.getInfoPagamento().add(infoP);
	replace.setImportoTotale(totRegistrazione);
	pendenza.setReplace(replace);
	Destinatari dests = new Destinatari();
	dests.getDestinatario().add(createDestinatario(daAnnullare.getSoggettoDebitore()));
	pendenza.setDestinatari(dests);
	body.getPendenza().add(pendenza);
	return allineamentoPendenzeEnteOTF;
    }

    protected boolean verificaPendenzeNonAnnullabili(StatoPagamentoType stato) {

	// Se pagata o annullata allora annullabile
	return !(isStatoAnnullato(stato) /* IN QUESTO CASO ANCHE SE PAGATE LE CONSIDERO ANNULLABILI*/);
    }

    protected boolean isStatoAnnullato(StatoPagamentoType stato) {

	return stato.equals(StatoPagamentoType.ANNULLAMENTO_RICHIESTO) || stato.equals(StatoPagamentoType.ANNULLATO)
		|| stato.equals(StatoPagamentoType.PAGATO_OFFLINE_ANNULLATO) || stato.equals(StatoPagamentoType.PAGATO_OFFLINE_DA_ANNULLARE)
		|| isStatoErrore(stato);
    }

    protected boolean isStatoErrore(StatoPagamentoType stato) {

	return stato.equals(StatoPagamentoType.CON_ERRORE) || stato.equals(StatoPagamentoType.NON_ACQUISITO)
		|| stato.equals(StatoPagamentoType.ACQUISITO);
    }

    protected boolean isStatoPagato(StatoPagamentoType stato) {

	return isStatoPagatoPagoPA(stato) || stato.equals(StatoPagamentoType.PAGATO_OFFLINE_ANNULLATO)
		|| stato.equals(StatoPagamentoType.PAGATO_OFFLINE_DA_ANNULLARE);
    }

    protected boolean isStatoPagatoPagoPA(StatoPagamentoType stato) {

	return stato.equals(StatoPagamentoType.NOTIFICATO_DA_PSP) || stato.equals(StatoPagamentoType.RENDICONTATO_DA_IC);
    }

    @Override
    public void validateRichiestaInserimentoPosizioniDebitorie(RegistrazioneContabileType registrazioneContabile)
	    throws ValidazionePosizioniDebitorieException {

	//TODO validazioni per prevenire errori del connettore
    }

    @Override
    public boolean supportaAttivaSessionePagamento() {

	return false;
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

    private String getIdPosizionePsp(PayPosizioniDebitorie payPos) {

	return ID_POSIZIONE_PSP_PREFIX + PkId.toStringId(payPos.getId());
    }

    private String getIdPendenza(PayPosizioniDebitorie payPos) {

	return ID_PENDENZA_PREFIX + PkId.toStringId(payPos.getId());
    }

    private String getIdPendenzaOTF(PayRegistrazioniContabili payReg) {

	return ID_PENDENZA_OTF_PREFIX + PkId.toStringId(payReg.getId());
    }
}
