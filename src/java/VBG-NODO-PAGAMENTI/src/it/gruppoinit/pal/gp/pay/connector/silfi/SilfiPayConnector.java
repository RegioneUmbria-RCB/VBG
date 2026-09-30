/**
 * 
 */
package it.gruppoinit.pal.gp.pay.connector.silfi;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.util.ByteArrayDataSource;
import javax.net.ssl.KeyManager;
import javax.net.ssl.TrustManager;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.cxf.configuration.jsse.TLSClientParameters;
import org.apache.cxf.configuration.security.AuthorizationPolicy;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.exceptions.ValidazionePosizioniDebitorieException;
import it.gruppoinit.pal.gp.core.utils.TrustManagerUtils;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.pal.gp.pay.command.PosizioniDebitorieCommand;
import it.gruppoinit.pal.gp.pay.command.RichiestaSuListaPosizioniCommand;
import it.gruppoinit.pal.gp.pay.connector.AbstractPayConnector;
import it.gruppoinit.pal.gp.pay.connector.silfi.ws.client.Mip2BEWsdlPublic;
import it.gruppoinit.pal.gp.pay.connector.silfi.ws.client.schema.common.ArticoloDebitorioWs;
import it.gruppoinit.pal.gp.pay.connector.silfi.ws.client.schema.common.GetPagamentoAttesoWs;
import it.gruppoinit.pal.gp.pay.connector.silfi.ws.client.schema.common.IuvFe;
import it.gruppoinit.pal.gp.pay.connector.silfi.ws.client.schema.common.IuvFeList;
import it.gruppoinit.pal.gp.pay.connector.silfi.ws.client.schema.common.PartitaDebitoriaWs;
import it.gruppoinit.pal.gp.pay.connector.silfi.ws.client.schema.pagopabe.AnnullaPagamentiAttesiRequest;
import it.gruppoinit.pal.gp.pay.connector.silfi.ws.client.schema.pagopabe.AnnullaPagamentiAttesiResponse;
import it.gruppoinit.pal.gp.pay.connector.silfi.ws.client.schema.pagopabe.AnnullaPagamentoAttesoWs;
import it.gruppoinit.pal.gp.pay.connector.silfi.ws.client.schema.pagopabe.DatiAnnullaPagamentoAttesoWs;
import it.gruppoinit.pal.gp.pay.connector.silfi.ws.client.schema.pagopabe.DatiModificaPagamentoAttesoWs;
import it.gruppoinit.pal.gp.pay.connector.silfi.ws.client.schema.pagopabe.DatiNuovoPagamentoAttesoWs;
import it.gruppoinit.pal.gp.pay.connector.silfi.ws.client.schema.pagopabe.DatiSegnaPagatoPagamentoAttesoWs;
import it.gruppoinit.pal.gp.pay.connector.silfi.ws.client.schema.pagopabe.GetPagamentoAttesoByIuvRequest;
import it.gruppoinit.pal.gp.pay.connector.silfi.ws.client.schema.pagopabe.GetPagamentoAttesoByIuvResponse;
import it.gruppoinit.pal.gp.pay.connector.silfi.ws.client.schema.pagopabe.GetPagamentoAttesoPdfRequest;
import it.gruppoinit.pal.gp.pay.connector.silfi.ws.client.schema.pagopabe.GetPagamentoAttesoPdfResponse;
import it.gruppoinit.pal.gp.pay.connector.silfi.ws.client.schema.pagopabe.GetTokenRequest;
import it.gruppoinit.pal.gp.pay.connector.silfi.ws.client.schema.pagopabe.GetTokenResponse;
import it.gruppoinit.pal.gp.pay.connector.silfi.ws.client.schema.pagopabe.InserisciPagamentiAttesiRequest;
import it.gruppoinit.pal.gp.pay.connector.silfi.ws.client.schema.pagopabe.InserisciPagamentiAttesiResponse;
import it.gruppoinit.pal.gp.pay.connector.silfi.ws.client.schema.pagopabe.ModificaPagamentiAttesiRequest;
import it.gruppoinit.pal.gp.pay.connector.silfi.ws.client.schema.pagopabe.ModificaPagamentiAttesiResponse;
import it.gruppoinit.pal.gp.pay.connector.silfi.ws.client.schema.pagopabe.NuovoPagamentoAttesoWs;
import it.gruppoinit.pal.gp.pay.connector.silfi.ws.client.schema.pagopabe.PagamentoAttesoRispostaWs;
import it.gruppoinit.pal.gp.pay.connector.silfi.ws.client.schema.pagopabe.PagamentoAttesoWs;
import it.gruppoinit.pal.gp.pay.connector.silfi.ws.client.schema.pagopabe.SegnaPagatoPagamentiAttesiRequest;
import it.gruppoinit.pal.gp.pay.connector.silfi.ws.client.schema.pagopabe.SegnaPagatoPagamentiAttesiResponse;
import it.gruppoinit.pal.gp.pay.connector.silfi.ws.client.schema.pagopabe.SegnaPagatoPagamentoAttesoWs;
import it.gruppoinit.pal.gp.pay.connector.silfi.ws.server.esitopagamentiattesi.schema.CodiceEsitoComunicazione;
import it.gruppoinit.pal.gp.pay.domain.PayConnectorConfigParams.ConfigParamNames;
import it.gruppoinit.pal.gp.pay.domain.PayConnectorWsEndpoint;
import it.gruppoinit.pal.gp.pay.domain.PayDettaglioImporti;
import it.gruppoinit.pal.gp.pay.domain.PayPosizioniDebitorie;
import it.gruppoinit.pal.gp.pay.domain.PayProfiliEntiCreditori;
import it.gruppoinit.pal.gp.pay.domain.PayRegistrazioniContabili;
import it.gruppoinit.pal.gp.pay.domain.PaySessioniPagamento;
import it.gruppoinit.pal.gp.pay.domain.PaySoggettiDebitori;
import it.gruppoinit.pal.gp.pay.exception.PayConfigurationException;
import it.gruppoinit.pal.gp.pay.exception.PayException;
import it.gruppoinit.pal.gp.pay.parameters.IParameter;
import it.gruppoinit.pal.gp.pay.parameters.ParametroCodiceServizio;
import it.gruppoinit.pal.gp.pay.parameters.ParametroDataScadenza;
import it.gruppoinit.pal.gp.pay.parameters.ParametroDataScadenzaStampabile;
import it.gruppoinit.pal.gp.pay.service.PayConnectorConfigValuesService;
import it.gruppoinit.pal.gp.pay.service.PayPosizioniDebitorieService;
import it.gruppoinit.pal.gp.pay.service.PaySessioniPagamentoService;
import it.gruppoinit.pal.gp.pay.service.PosizioniDebitorieCommandService;
import it.gruppoinit.pal.gp.pay.service.helper.IUVHelper;
import it.gruppoinit.pal.gp.pay.service.helper.ParametriConnettoreHelper;
import it.gruppoinit.pal.gp.pay.service.helper.PayConfigurationHelper;
import it.gruppoinit.pal.gp.pay.service.helper.PopolamentoDatiHelper;
import it.gruppoinit.pal.gp.pay.service.helper.RTHelper;
import it.gruppoinit.pal.gp.pay.service.helper.RiferimentiPosizioniDebitorieHelper;
import it.gruppoinit.pal.gp.pay.ws.schema.AttivaPagamentoOnTheFlyResponseType;
import it.gruppoinit.pal.gp.pay.ws.schema.AttivaSessionePagamentoResponseType;
import it.gruppoinit.pal.gp.pay.ws.schema.DatiPagamentoType;
import it.gruppoinit.pal.gp.pay.ws.schema.ElencoDocumentiEsitoType;
import it.gruppoinit.pal.gp.pay.ws.schema.ElencoPosizioniDebitorieEsitoType;
import it.gruppoinit.pal.gp.pay.ws.schema.ElencoStatoPosizioniType;
import it.gruppoinit.pal.gp.pay.ws.schema.EsitoDocumentoPosizioneDebitoriaType;
import it.gruppoinit.pal.gp.pay.ws.schema.EsitoOperazionePosizioneDebitoriaType;
import it.gruppoinit.pal.gp.pay.ws.schema.HttpMethodType;
import it.gruppoinit.pal.gp.pay.ws.schema.ImportoPagamentoType;
import it.gruppoinit.pal.gp.pay.ws.schema.PosizioneDebitoriaType;
import it.gruppoinit.pal.gp.pay.ws.schema.RegistrazioneContabileType;
import it.gruppoinit.pal.gp.pay.ws.schema.SoggettoDebitoreType;
import it.gruppoinit.pal.gp.pay.ws.schema.StatoDocumentoType;
import it.gruppoinit.pal.gp.pay.ws.schema.StatoPagamentoType;
import it.gruppoinit.pal.gp.pay.ws.schema.StatoPosizioneType;
import it.gruppoinit.pal.gp.pay.ws.schema.TipoDocumentoType;

/**
 * Connettore di pagamento per integrazione con il sistema Silfi
 * 
 * @author the lion
 *
 */
@Component
public class SilfiPayConnector extends AbstractPayConnector {

    private static final String NAZIONE_IT = "IT";
    private static final int DEFAULT_CONNECTION_TIMEOUT = 20000;
    private static final Logger log = LoggerFactory.getLogger(SilfiPayConnector.class);
    private static final String SILFI_MOTIVAZIONE_ANNULLAMENTO = "Annullamento richiesto dal sitema VBG";
    private static final String SILFI_MOTIVAZIONE_PAGATO_OFFLINE = "Impostato come pagato dal sitema VBG";
    private static final String SESSION_PARAM_TOKEN = "token";
    private static final String SESSION_PARAM_ENTE = "codiceEnte";
    private static final String SESSION_PARAM_SERVIZIO = "codiceServizio";

    public enum NaturaGiuridica {

	PERSONA_FISICA("F"), //
	PERSONA_GIURIDICA("G");

	private String val;

	private NaturaGiuridica(String name) {

	    this.val = name;
	}

	public String value() {

	    return this.val;
	}
    }

    private Mip2BEWsdlPublic mip2bePort = null;
    @Autowired
    private PayConnectorConfigValuesService payConnectorConfigValuesService;
    @Autowired
    private PayPosizioniDebitorieService payPosizioniDebitorieService;
    @Autowired
    private PaySessioniPagamentoService paySessioniPagamentoService;
    @Autowired
    private PosizioniDebitorieCommandService posizioniDebitorieCommandService;

    @Override
    public ElencoPosizioniDebitorieEsitoType registraPosizioniDebitorie(PosizioniDebitorieCommand datiRegistrazioniCommand) throws PayException {

	String maxPosizioniParam = this.payConnectorConfigValuesService.getValoreParametroConfigurazione(ConfigParamNames.MAX_POSIZIONI);
	int maxPosizioni = 100;//default limit 
	int numPosizioni = 0;
	if (NumberUtils.isDigits(maxPosizioniParam)) {
	    maxPosizioni = Integer.parseInt(maxPosizioniParam);
	} else {
	    log.warn("registraPosizioniDebitorie - il limite massimo di posizioni configurato contiene un valore non numerico: {}", maxPosizioni);
	}
	ElencoPosizioniDebitorieEsitoType tempEsiti = new ElencoPosizioniDebitorieEsitoType();
	List<ElencoPosizioniDebitorieEsitoType> tempEsitiList = new ArrayList<>();
	InserisciPagamentiAttesiRequest insReq = new InserisciPagamentiAttesiRequest();
	List<InserisciPagamentiAttesiRequest> insReqs = new ArrayList<>();
	for (PayRegistrazioniContabili reg : datiRegistrazioniCommand.getRegistrazioniPosizioni()) {
	    boolean isRateizzato = reg.getPosizioniDebitorie().size() > 1;
	    for (PayPosizioniDebitorie pos : reg.getPosizioniDebitorie()) {
		//predispongo l'esito da restituire al nodo pagamenti
		EsitoOperazionePosizioneDebitoriaType esitoNodo = new EsitoOperazionePosizioneDebitoriaType();
		PopolamentoDatiHelper.completaDatiDaPosizioneDebitoria(esitoNodo, pos,
			payPosizioniDebitorieService.findRiferimentiClientByPosizioneDebitoria(pos.getId().getCodice()));
		DatiNuovoPagamentoAttesoWs datiPag = new DatiNuovoPagamentoAttesoWs();// this.caricaRegistrazioneContabile(payReg,
		// popolo i dati della posizione debitoria da trasmettere
		NuovoPagamentoAttesoWs nuovoPag = new NuovoPagamentoAttesoWs();
		String codiceVersamento = posizioniDebitorieCommandService.findCodiceVersamentoFromPosizioneDebitoria(pos);
		nuovoPag.setDatiSpecificiRiscossione(codiceVersamento);
		PagamentoAttesoWs pagPos = this.popolaPagamentoAtteso(pos, isRateizzato);
		nuovoPag.setPagamentoAttesoWs(pagPos);
		datiPag.setNuovoPagamentoAttesoWs(nuovoPag);
		if (numPosizioni == maxPosizioni) {
		    tempEsitiList.add(tempEsiti);
		    tempEsiti = new ElencoPosizioniDebitorieEsitoType();
		    insReqs.add(insReq);
		    insReq = new InserisciPagamentiAttesiRequest();
		    numPosizioni = 0;
		}
		tempEsiti.getEsitoPosizione().add(esitoNodo);
		insReq.getDatiPagamentoAttesoWs().add(datiPag);
		numPosizioni++;
	    }
	}
	insReqs.add(insReq);
	tempEsitiList.add(tempEsiti);
	for (int i = 0; i < insReqs.size(); i++) {
	    insReq = insReqs.get(i);
	    tempEsiti = tempEsitiList.get(i);
	    int count = 0;
	    String msg = null;
	    try {
		msg = "invocazione del servizio mip2BE inserisciPagamentiAttesi in corso";
		log.info("registraPosizioniDebitorie - {}", msg);
		Mip2BEWsdlPublic wsPort = this.getMip2BEPort(this.getWsCaricamentoConfig());
		InserisciPagamentiAttesiResponse resp = wsPort.inserisciPagamentiAttesi(insReq);
		if (StringUtils.isNotBlank(resp.getCodiceErrore()) || StringUtils.isNotBlank(resp.getDescrizioneErrore())) {
		    log.error("Errore nella richiesta di inserimento pagamenti attesi {}: {}", resp.getCodiceErrore(), resp.getDescrizioneErrore());
		    // in caso di errore generale non verifico gli esiti uno per uno
		    for (EsitoOperazionePosizioneDebitoriaType esito : tempEsiti.getEsitoPosizione()) {
			esito.setCodiceErrore(resp.getCodiceErrore());
			esito.setEsito(false);
			esito.setMessaggio(resp.getDescrizioneErrore());
		    }
		} else {
		    RiferimentiPosizioniDebitorieHelper esitiHelper = new RiferimentiPosizioniDebitorieHelper(tempEsiti);
		    for (PagamentoAttesoRispostaWs esitoSilfi : resp.getPagamentoAttesoRispostaWs()) {
			PkId posId = this.parseIdBoPagamentoAtteso(esitoSilfi.getIdBoPagamentoAtteso());
			EsitoOperazionePosizioneDebitoriaType esito = (EsitoOperazionePosizioneDebitoriaType) esitiHelper
				.findRiferimentoPosizioneById(BigInteger.valueOf(posId.getCodice()));
			PayPosizioniDebitorie payPos = datiRegistrazioniCommand.findPosizioneById(posId.getCodice());
			if (esito != null && payPos != null) {
			    if (this.isCodiceEsitoOk(esitoSilfi.getCodiceEsito())) {
				esito.setEsito(true);
				esito.setIUV(esitoSilfi.getIuv());
				//TODO l'id_posizione_psp dovrebbe essere modificato dal nodo e non dal connettore
				payPos.setIdPosizionePsp(esitoSilfi.getIdBoPagamentoAtteso());
				esito.setStato(StatoPagamentoType.ATTIVATO_IN_PSP);
				count++;
			    } else {
				esito.setCodiceErrore(esitoSilfi.getCodiceEsito());
				esito.setMessaggio(esitoSilfi.getDescrizioneEsito());
				esito.setEsito(false);
			    }
			} else {
			    log.error(
				    "registraPosizioniDebitorie - impossibile ricollegare lo iuv dell'esito restituito da Silfi ad una posizione debitoria da caricare");
			}
		    }
		}
		if (count == 0) {
		    msg = "nessuna posizione debitoria è stata caricata con successo";
		    log.error(msg);
		} else if (count < tempEsiti.getEsitoPosizione().size()) {
		    msg = MessageFormat.format("sono state caricate con successo {0} posizioni debitorie su {1}", count,
			    tempEsiti.getEsitoPosizione().size());
		    log.error(msg);
		} else {
		    msg = MessageFormat.format("tutte le {0} posizioni debitorie sono state caricate con successo", count);
		    log.debug(msg);
		}
	    } catch (Exception e) {
		log.error("registraPosizioniDebitorie - errore nella chiamata al WS inserisciPagamentiAttesi", e);
		for (EsitoOperazionePosizioneDebitoriaType esito : tempEsiti.getEsitoPosizione()) {
		    this.handleException(e, esito);
		    esito.setIUV(null);
		}
	    }
	}
	//riunisco gli elenchi di esiti delle singole chiamate in un unico eleco da restituire al chiamante
	ElencoPosizioniDebitorieEsitoType retEsiti = new ElencoPosizioniDebitorieEsitoType();
	for (ElencoPosizioniDebitorieEsitoType esiti : tempEsitiList) {
	    retEsiti.getEsitoPosizione().addAll(esiti.getEsitoPosizione());
	}
	return retEsiti;
    }

    @Override
    public ElencoPosizioniDebitorieEsitoType annullaPosizioniDebitorie(PosizioniDebitorieCommand datiRegistrazioniCommand, boolean pagatoOffline)
	    throws PayException {

	PayProfiliEntiCreditori enteCfg = PayConfigurationHelper.getProfiloEnteCreditore();
	ElencoPosizioniDebitorieEsitoType retEsiti = new ElencoPosizioniDebitorieEsitoType();
	AnnullaPagamentiAttesiRequest annReq = new AnnullaPagamentiAttesiRequest();
	SegnaPagatoPagamentiAttesiRequest pagatoReq = new SegnaPagatoPagamentiAttesiRequest();
	for (PayRegistrazioniContabili payReg : datiRegistrazioniCommand.getRegistrazioniPosizioni()) {
	    for (PayPosizioniDebitorie payPos : payReg.getPosizioniDebitorie()) {
		String codiceServizio = getCodiceServizio(payPos);
		if (!pagatoOffline) {
		    DatiAnnullaPagamentoAttesoWs richAnnull = new DatiAnnullaPagamentoAttesoWs();
		    richAnnull.setMotivazione(pagatoOffline ? SILFI_MOTIVAZIONE_PAGATO_OFFLINE : SILFI_MOTIVAZIONE_ANNULLAMENTO);
		    AnnullaPagamentoAttesoWs annPag = new AnnullaPagamentoAttesoWs();
		    annPag.setCodiceEnte(enteCfg.getCfCodiceProfiloPSP());
		    annPag.setCodiceServizio(codiceServizio);
		    annPag.setIuv(payPos.getIuv());
		    richAnnull.setAnnullaPagamentoAttesoWs(annPag);
		    annReq.getDatiPagamentoAttesoWs().add(richAnnull);
		} else {
		    DatiSegnaPagatoPagamentoAttesoWs richPagato = new DatiSegnaPagatoPagamentoAttesoWs();
		    SegnaPagatoPagamentoAttesoWs pagato = new SegnaPagatoPagamentoAttesoWs();
		    pagato.setCodiceEnte(enteCfg.getCfCodiceProfiloPSP());
		    pagato.setCodiceServizio(codiceServizio);
		    pagato.setIuv(payPos.getIuv());
		    DatiPagamentoType datiPagIn = datiRegistrazioniCommand.findDatiPagamentoByIdPosizione(payPos.getId().getCodice());
		    if (datiPagIn != null) {
			XMLGregorianCalendar dataPag = datiPagIn.getDataOraPagamento();
			if (dataPag == null) {
			    dataPag = Utilities.getXMLGregorianCalendar(new Date());
			}
			pagato.setDataPagamento(dataPag);
			pagato.setImporto(datiPagIn.getImportoPagato());
			pagato.setCanalePagamento(datiPagIn.getModalitaPagamento());
		    }
		    richPagato.setSegnaPagatoPagamentoAttesoWs(pagato);
		    pagatoReq.getDatiPagamentoAttesoWs().add(richPagato);
		}
		EsitoOperazionePosizioneDebitoriaType esitoOp = new EsitoOperazionePosizioneDebitoriaType();
		PopolamentoDatiHelper.completaDatiDaPosizioneDebitoria(esitoOp, payPos,
			payPosizioniDebitorieService.findRiferimentiClientByPosizioneDebitoria(payPos.getId().getCodice()));
		retEsiti.getEsitoPosizione().add(esitoOp);
	    }
	}
	int count = 0;
	String msg = null;
	try {
	    msg = "invocazione del servizio mip2BE annullaPagamentiAttesi in corso";
	    log.info("annullaPosizioniDebitorie - {}", msg);
	    Mip2BEWsdlPublic wsPort = this.getMip2BEPort(this.getWsAnnullamentoConfig());
	    List<PagamentoAttesoRispostaWs> esitiSilfi = null;
	    String codErrore = null;
	    String descErrore = null;
	    if (!pagatoOffline) {
		AnnullaPagamentiAttesiResponse resp = wsPort.annullaPagamentiAttesi(annReq);
		esitiSilfi = resp.getPagamentoAttesoRispostaWs();
		codErrore = resp.getCodiceErrore();
		descErrore = resp.getDescrizioneErrore();
	    } else {
		SegnaPagatoPagamentiAttesiResponse pagatoResp = wsPort.segnaPagatoPagamentiAttesi(pagatoReq);
		esitiSilfi = pagatoResp.getPagamentoAttesoRispostaWs();
		codErrore = pagatoResp.getCodiceErrore();
		descErrore = pagatoResp.getDescrizioneErrore();
	    }
	    if (StringUtils.isNotBlank(codErrore) || StringUtils.isNotBlank(descErrore)) {
		// in caso di errore generale non verifico gli esiti uno per uno, sono tutti KO
		for (EsitoOperazionePosizioneDebitoriaType esito : retEsiti.getEsitoPosizione()) {
		    esito.setCodiceErrore(codErrore);
		    esito.setEsito(false);
		    esito.setMessaggio(descErrore);
		}
	    } else {
		RiferimentiPosizioniDebitorieHelper esitiHelper = new RiferimentiPosizioniDebitorieHelper(retEsiti);
		for (PagamentoAttesoRispostaWs esitoSilfi : esitiSilfi) {
		    EsitoOperazionePosizioneDebitoriaType esito = (EsitoOperazionePosizioneDebitoriaType) esitiHelper
			    .findRiferimentoPosizioneByIUV(esitoSilfi.getIuv());
		    if (esito != null) {
			if (this.isCodiceEsitoOk(esitoSilfi.getCodiceEsito())) {
			    esito.setEsito(true);
			    esito.setStato(pagatoOffline ? StatoPagamentoType.PAGATO_OFFLINE_ANNULLATO : StatoPagamentoType.ANNULLATO);
			    count++;
			} else {
			    esito.setCodiceErrore(esitoSilfi.getCodiceEsito());
			    esito.setMessaggio(esitoSilfi.getDescrizioneEsito());
			    esito.setEsito(false);
			}
		    } else {
			log.error(
				"annullaPosizioniDebitorie - impossibile ricollegare lo iuv dell'esito restituito da Silfi ad una posizione debitoria da annullare. IUV: {}, idBo: {}",
				esitoSilfi.getIuv(), esitoSilfi.getIdBoPagamentoAtteso());
		    }
		}
	    }
	    if (count == 0) {
		msg = pagatoOffline ? "impostata come pagata" : "annullata";
		msg = "nessuna posizione debitoria è stata " + msg;
	    } else if (count < retEsiti.getEsitoPosizione().size()) {
		msg = pagatoOffline ? "impostate come pagate" : "annullate";
		msg = MessageFormat.format("sono state {0} {1} posizioni debitorie su {2}", msg, count, retEsiti.getEsitoPosizione().size());
	    } else {
		msg = pagatoOffline ? "impostate come pagate" : "annullate";
		msg = MessageFormat.format("tutte le {0} posizioni debitorie sono state {1}", count, msg);
	    }
	    log.info("annullaPosizioniDebitorie() - {}", msg);
	} catch (Exception e) {
	    log.error("annullaPosizioniDebitorie - errore nella chiamata al WS annullaPagamentiAttesi", e);
	    for (EsitoOperazionePosizioneDebitoriaType esito : retEsiti.getEsitoPosizione()) {
		this.handleException(e, esito);
	    }
	}
	return retEsiti;
    }

    @Override
    public ElencoStatoPosizioniType verificaStatoPagamenti(RichiestaSuListaPosizioniCommand cmd) throws PayException {

	PayProfiliEntiCreditori ente = PayConfigurationHelper.getProfiloEnteCreditore();
	if (ente == null) {
	    throw new PayConfigurationException("profilo ente non impostato");
	}
	PayConnectorWsEndpoint wsVerifica = this.getWsVerificaConfig();
	if (wsVerifica == null) {
	    throw new PayConfigurationException(
		    "endpoint per la verifica dello stato dei pagamenti non configurato per il connettore " + this.getConnectorName());
	}
	ElencoStatoPosizioniType ret = new ElencoStatoPosizioniType();
	Mip2BEWsdlPublic port = null;
	try {
	    port = this.getMip2BEPort(wsVerifica);
	} catch (Exception e) {
	    log.error("Errore nell'invocazione della porta: " + wsVerifica.getEndpointUrl(), e);
	    for (PayPosizioniDebitorie pos : cmd.getPosizioni()) {
		StatoPosizioneType esitoNodo = new StatoPosizioneType();
		PopolamentoDatiHelper.completaDatiDaPosizioneDebitoria(esitoNodo, pos,
			payPosizioniDebitorieService.findRiferimentiClientByPosizioneDebitoria(pos.getId().getCodice()));
		esitoNodo.setErroreTemporaneo(true);
		esitoNodo.setCodiceErrore("001");
		esitoNodo.setMessaggio(e.getMessage());
		ret.getStatoPosizioni().add(esitoNodo);		
	    }
	    return ret;
	}
	if (port != null) {
	    // e' necessario fare una chiamata per al WS per ogni posizione da verificare
	    for (PayPosizioniDebitorie pos : cmd.getPosizioni()) {
		String statoCorrente = pos.recuperaStatoCorrente().getStato();
		StatoPosizioneType esitoNodo = new StatoPosizioneType();
		PopolamentoDatiHelper.completaDatiDaPosizioneDebitoria(esitoNodo, pos,
			payPosizioniDebitorieService.findRiferimentiClientByPosizioneDebitoria(pos.getId().getCodice()));
		ret.getStatoPosizioni().add(esitoNodo);
		GetPagamentoAttesoByIuvRequest req = new GetPagamentoAttesoByIuvRequest();
		req.setCodiceEnte(ente.getCfCodiceProfiloPSP());
		req.setCodiceServizio(getCodiceServizio(pos));
		req.setIuv(pos.getIuv());
		try {
		    GetPagamentoAttesoByIuvResponse resp = port.getPagamentoAttesoByIuv(req);
		    if (StringUtils.isBlank(resp.getCodiceErrore()) && StringUtils.isBlank(resp.getDescrizioneErrore())) {
			GetPagamentoAttesoWs datiPos = resp.getPagamentoAttesoWs();
			if (datiPos != null) {
			    // chiamata con esito OK
			    esitoNodo.setEsito(true);
			    // ci ridà il codice avviso e lo recuperiamo
			    esitoNodo.setCodiceAvviso(datiPos.getNumeroAvviso());
			    DatiPagamentoType datiPag = null;
			    if (datiPos.isAnnullato()) {
				esitoNodo.setStato(StatoPagamentoType.ANNULLATO);
			    } else if (datiPos.isPagabile()) {
				esitoNodo.setStato(StatoPagamentoType.ATTIVATO_IN_PSP);
			    } else if (datiPos.isPagato()) {
				esitoNodo.setStato(StatoPagamentoType.NOTIFICATO_DA_PSP);
				datiPag = new DatiPagamentoType();
			    } else if (datiPos.isIncassato() || datiPos.isRendicontato()) {
				esitoNodo.setStato(StatoPagamentoType.RENDICONTATO_DA_IC);
				datiPag = new DatiPagamentoType();
			    }
			    if (datiPag != null) {
				if (datiPos.getRicevutaTelematicaWs() != null
					&& StringUtils.isNotBlank(datiPos.getRicevutaTelematicaWs().getRtXml())) {
				    String decoded = Utilities.decodeBase64Binary(datiPos.getRicevutaTelematicaWs().getRtXml());
				    datiPag = RTHelper.popolaDatiPagamentoDaReceiptORicevutaTelematica(decoded, null);
				    DataSource ds = new ByteArrayDataSource(decoded, ContentType.APPLICATION_OCTET_STREAM.getMimeType());
				    DataHandler ricevutaXML = new DataHandler(ds);
				    datiPag.setRicevutaXml(ricevutaXML);
				}
				datiPag.setDataOraPagamento(datiPos.getDataPagamento());
				datiPag.setModalitaPagamento(datiPos.getCanalePagamentoExtraPagoPa());
				esitoNodo.setDatiPagamento(datiPag);
			    }
			    StringBuilder sb = new StringBuilder("La posizione debitoria con iuv ").append(pos.getIuv()).append(" è nello stato ")
				    .append(esitoNodo.getStato().name());
			    log.info("verificaStatoPagamenti - invocazione di getPagamentoAttesoByIuv avvenuta con successo: {}", sb);
			} else {
			    String msg = "dati sullo stato della posizione mancanti nella response di getPagamentoAttesoByIuv per lo iuv " +
					 pos.getIuv();
			    log.error("verificaStatoPagamenti - {}", msg);
			}
		    } else {
			esitoNodo.setCodiceErrore(resp.getCodiceErrore());
			esitoNodo.setMessaggio(resp.getDescrizioneErrore());
			StringBuilder sb = new StringBuilder("la chiamata al servizio getPagamentoAttesoByIuv ha restituito errore ");
			if (resp.getCodiceErrore() != null) {
			    sb.append(resp.getCodiceErrore()).append(" ");
			}
			log.error("verificaStatoPagamenti - {}", sb);
		    }
		} catch (Exception e) {
		    log.error("verificaStatoPagamenti - eccezione nella chiamata al servizio getPagamentoAttesoByIuv per la posizione " +
			      pos.getIuv() + ": " + e.getMessage(),
			    e);
		    this.handleException(e, esitoNodo);
		    if (BooleanUtils.isTrue(esitoNodo.isErroreTemporaneo())) {
			esitoNodo.setCodiceErrore("002");	
			esitoNodo.setStato(StatoPagamentoType.fromValue(statoCorrente));
		    }
		}
	    }
	}
	return ret;
    }

    @Override
    public AttivaPagamentoOnTheFlyResponseType attivaPagamentoOnTheFly(PosizioniDebitorieCommand cmd) throws PayException {

	PayConnectorWsEndpoint wsSess = this.getWsAttivaSessioneConfig();
	if (wsSess == null) {
	    throw new PayConfigurationException(
		    "endpoint per l'attivazione della sessione non configurato per il connettore " + this.getConnectorName());
	}
	PayProfiliEntiCreditori ente = PayConfigurationHelper.getProfiloEnteCreditore();
	if (ente == null) {
	    throw new PayException("profilo ente non impostato");
	}
	int count = 0;
	for (PayRegistrazioniContabili reg : cmd.getRegistrazioniPosizioni()) {
	    count += reg.getPosizioniDebitorie().size();
	}
	if (count > 5) {
	    throw new PayException("Non è possibile creare pagamenti per più di 5 posizioni");
	}
	AttivaSessionePagamentoResponseType attivaSessioneOTF = new AttivaSessionePagamentoResponseType();
	AttivaPagamentoOnTheFlyResponseType result = new AttivaPagamentoOnTheFlyResponseType();
	result.setSessionePagamento(attivaSessioneOTF);
	List<PayPosizioniDebitorie> posDeb = new ArrayList<>();
	try {
	    // registra posizioni debitorie
	    List<PayRegistrazioniContabili> payRegistrazioniContabilis = cmd.getRegistrazioniPosizioni();
	    InserisciPagamentiAttesiRequest insReq = new InserisciPagamentiAttesiRequest();
	    for (PayRegistrazioniContabili reg : payRegistrazioniContabilis) {
		for (PayPosizioniDebitorie pd : reg.getPosizioniDebitorie()) {
		    posDeb.add(pd);
		    DatiNuovoPagamentoAttesoWs datiPag = new DatiNuovoPagamentoAttesoWs();// this.caricaRegistrazioneContabile(payReg,
		    // popolo i dati della posizione debitoria da trasmettere
		    NuovoPagamentoAttesoWs nuovoPag = new NuovoPagamentoAttesoWs();
		    String codiceVersamento = posizioniDebitorieCommandService.findCodiceVersamentoFromPosizioneDebitoria(pd);
		    nuovoPag.setDatiSpecificiRiscossione(codiceVersamento);
		    PagamentoAttesoWs pagPos = this.popolaPagamentoAtteso(pd, false);
		    nuovoPag.setPagamentoAttesoWs(pagPos);
		    datiPag.setNuovoPagamentoAttesoWs(nuovoPag);
		    EsitoOperazionePosizioneDebitoriaType esitoNodo = new EsitoOperazionePosizioneDebitoriaType();
		    PopolamentoDatiHelper.completaDatiDaPosizioneDebitoria(esitoNodo, pd,
			    payPosizioniDebitorieService.findRiferimentiClientByPosizioneDebitoria(pd.getId().getCodice()));
		    result.getPosizioneInserita().add(esitoNodo);
		    insReq.getDatiPagamentoAttesoWs().add(datiPag);
		}
	    }
	    String codiceServizio = getCodiceServizioDaPosizioni(posDeb);
	    log.debug("Prima di invocare l'inserimento dei pagamenti attesi");
	    Mip2BEWsdlPublic wsPort = this.getMip2BEPort(this.getWsCaricamentoConfig());
	    InserisciPagamentiAttesiResponse resp = wsPort.inserisciPagamentiAttesi(insReq);
	    boolean errore = false;
	    if (StringUtils.isNotBlank(resp.getCodiceErrore()) || StringUtils.isNotBlank(resp.getDescrizioneErrore())) {
		log.error("Errore nella richiesta di inserimento pagamenti attesi {}: {}", resp.getCodiceErrore(), resp.getDescrizioneErrore());
		// in caso di errore generale non verifico gli esiti uno per uno
		for (EsitoOperazionePosizioneDebitoriaType esito : result.getPosizioneInserita()) {
		    esito.setCodiceErrore(resp.getCodiceErrore());
		    esito.setEsito(false);
		    esito.setMessaggio(resp.getDescrizioneErrore());
		    esito.setStato(StatoPagamentoType.CON_ERRORE);
		}
		errore = true;
	    } else {
		RiferimentiPosizioniDebitorieHelper esitiHelper = new RiferimentiPosizioniDebitorieHelper(result.getPosizioneInserita());
		for (PagamentoAttesoRispostaWs esitoSilfi : resp.getPagamentoAttesoRispostaWs()) {
		    PkId posId = this.parseIdBoPagamentoAtteso(esitoSilfi.getIdBoPagamentoAtteso());
		    EsitoOperazionePosizioneDebitoriaType esito = (EsitoOperazionePosizioneDebitoriaType) esitiHelper
			    .findRiferimentoPosizioneById(BigInteger.valueOf(posId.getCodice()));
		    PayPosizioniDebitorie payPos = cmd.findPosizioneById(posId.getCodice());
		    if (esito != null && payPos != null) {
			if (this.isCodiceEsitoOk(esitoSilfi.getCodiceEsito())) {
			    esito.setEsito(true);
			    esito.setIUV(esitoSilfi.getIuv());
			    payPos.setIdPosizionePsp(esitoSilfi.getIdBoPagamentoAtteso());
			    payPos.setIuv(esitoSilfi.getIuv());
			    esito.setStato(StatoPagamentoType.ATTIVATO_IN_PSP);			    
			    this.payPosizioniDebitorieService.aggiornaPosizioneDebitoria(payPos, null, null);
			} else {
			    esito.setStato(StatoPagamentoType.CON_ERRORE);
			    esito.setCodiceErrore(esitoSilfi.getCodiceEsito());
			    esito.setMessaggio(esitoSilfi.getDescrizioneEsito());
			    esito.setEsito(false);
			    errore = true;
			}
		    } else {
			log.error(
				"registraPosizioniDebitorie - impossibile ricollegare lo iuv dell'esito restituito da Silfi ad una posizione debitoria da caricare");
		    }
		}
	    }
	    // TODO IN CASO DI ERRORE ALLORA DEVO GENERARE ANNULLA POSIZIONE DEBITORIA SE NE HO CREATA QUALCUNA CON IUV
	    if (!errore) {
		GetTokenResponse token = attivaRichiestaPagamentoOnline(posDeb, ente, wsSess, codiceServizio);
		if (StringUtils.isNotBlank(token.getCodiceErrore())) {
		    throw new PayException("Errore nell'attivazione di richiesta pagamento SILFI " + token.getCodiceErrore());
		}
		attivaSessioneOTF.setEsito(true);
		attivaSessioneOTF.setIdSessione(token.getToken());
		attivaSessioneOTF.setPayUrl(
			getPayUrl(ente.getPayConnector().getUrlPortalePagamenti(), token.getToken(), ente.getCfCodiceProfiloPSP(), codiceServizio));
		attivaSessioneOTF.setHttpMethodRequired(HttpMethodType.GET);		
	    }
	} catch (Exception e) {
	    log.error("Errore nell'attivazione del pagamento OTF SILFI {}", e.getMessage(), e);
	    attivaSessioneOTF.setEsito(false);
	    attivaSessioneOTF.setDescEsito("Errore nel creazione della posizione OTF");
	    for (EsitoOperazionePosizioneDebitoriaType esitoKO : result.getPosizioneInserita()) {
		esitoKO.setEsito(false);
		this.handleException(e, esitoKO);
	    }
	}
	return result;
    }

    private GetTokenResponse attivaRichiestaPagamentoOnline(List<PayPosizioniDebitorie> payPosizioniDebitorie, PayProfiliEntiCreditori ente,
	    PayConnectorWsEndpoint wsSess, String codiceServizio) throws PayException {

	Mip2BEWsdlPublic wsPort = this.getMip2BEPort(wsSess);
	GetTokenRequest req = new GetTokenRequest();
	req.setCodiceEnte(ente.getCfCodiceProfiloPSP());
	req.setCodiceServizio(codiceServizio);
	req.setUrlRitorno(ente.getUrlEsitoPagamento());
	IuvFeList iuvList = new IuvFeList();
	for (PayPosizioniDebitorie pd : payPosizioniDebitorie) {
	    IuvFe iuv = new IuvFe();
	    iuv.setIuv(pd.getIuv());
	    iuvList.getIuvFe().add(iuv);
	}
	req.setIuvFeList(iuvList);
	return wsPort.getToken(req);
    }
    //    /**
    //     * Al momento non lo utilizziamo, utilizziamo sempre il metodo per la verifica dello stato
    //     * 
    //     * @param token
    //     * @param ente
    //     * @param wsSess
    //     * @return
    //     * @throws PayException
    //     */
    //    private GetEsitoPagamentoResponse esitoPagamentoOnline(String token, PayProfiliEntiCreditori ente, PayConnectorWsEndpoint wsSess)
    //	    throws PayException {
    //
    //	Mip2BEWsdlPublic wsPort = this.getMip2BEPort(wsSess);
    //	GetEsitoPagamentoRequest req = new GetEsitoPagamentoRequest();
    //	req.setToken(token);
    //	req.setCodiceEnte(ente.getCfCodiceProfiloPSP());
    //	req.setCodiceServizio(ente.getIdAppPSP());
    //	return wsPort.getEsitoPagamento(req);
    //    }

    public AttivaSessionePagamentoResponseType attivaSessionePagamento(PayPosizioniDebitorie payPosizioneDebitoria) throws PayException {

	PayConnectorWsEndpoint wsSess = this.getWsAttivaSessioneConfig();
	if (wsSess == null) {
	    throw new PayConfigurationException(
		    "endpoint per l'attivazione della sessione non configurato per il connettore " + this.getConnectorName());
	}
	PayProfiliEntiCreditori ente = PayConfigurationHelper.getProfiloEnteCreditore();
	if (ente == null) {
	    throw new PayException("profilo ente non impostato");
	}
	AttivaSessionePagamentoResponseType result = new AttivaSessionePagamentoResponseType();
	try {
	    List<PayPosizioniDebitorie> l = new ArrayList<>();
	    l.add(payPosizioneDebitoria);
	    String codiceServizio = getCodiceServizio(payPosizioneDebitoria);
	    GetTokenResponse resp = attivaRichiestaPagamentoOnline(l, ente, wsSess, codiceServizio);
	    if (StringUtils.isBlank(resp.getCodiceErrore())) {
		result.setEsito(true);
		result.setIdSessione(resp.getToken());
		result.setPayUrl(
			getPayUrl(ente.getPayConnector().getUrlPortalePagamenti(), resp.getToken(), ente.getCfCodiceProfiloPSP(), codiceServizio));
		result.setHttpMethodRequired(HttpMethodType.GET);
		log.debug("sessione creata con successo, token {}", resp.getToken());
	    } else {
		String msg = "il servizio getToken ha restituito codice errore " + resp.getCodiceErrore();
		log.error("attivaSessionePagamento - {}", msg);
		result.setDescEsito(msg);
	    }
	} catch (Exception e) {
	    String msg = "la chiamata al servizio getToken ha lanciato un eccezione" + e.toString();
	    log.error("attivaSessionePagamento - " + msg, e);
	    result.setDescEsito(msg);
	}
	return result;
    }

    private String getPayUrl(String urlPortalePagamenti, String token, String cfCodiceProfiloPSP, String codiceServizio) {

	return urlPortalePagamenti + "?" + SESSION_PARAM_TOKEN + "=" + token + "&" + SESSION_PARAM_ENTE + "=" + cfCodiceProfiloPSP + "&" +
	       SESSION_PARAM_SERVIZIO + "=" + codiceServizio + "&reloadSession=true";
    }

    @Override
    public PaySessioniPagamento gestisciEsitoSessione(Map<String, String[]> reqParams) {

	List<PaySessioniPagamento> sex = new ArrayList<>();
	String[] idSessioneVals = reqParams.get("token");
	log.debug("idSessioneVals {}", (Object[]) idSessioneVals);
	String idSessione = null;
	String esito = null;
	if (idSessioneVals != null && idSessioneVals.length > 0) {
	    idSessione = idSessioneVals[0];
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

    @Override
    public ElencoDocumentiEsitoType inviaAvvisiPagamento(PosizioniDebitorieCommand cmd) throws PayException {

	PayConnectorWsEndpoint wsAvviso = this.getWsAvvisoConfig();
	PayProfiliEntiCreditori ente = PayConfigurationHelper.getProfiloEnteCreditore();
	if (wsAvviso == null) {
	    throw new PayConfigurationException(
		    "l'endpoint per il servizio di generazione degli avvisi non è configurato per il connettore " + this.getConnectorName());
	}
	ElencoDocumentiEsitoType retEsiti = new ElencoDocumentiEsitoType();
	Mip2BEWsdlPublic wsPort = null;
	String msg = null;
	try {
	    wsPort = this.getMip2BEPort(getWsAvvisoConfig());
	} catch (PayException e) {
	    for (PayRegistrazioniContabili reg : cmd.getRegistrazioniPosizioni()) {
		for (PayPosizioniDebitorie pos : reg.getPosizioniDebitorie()) {
		    EsitoDocumentoPosizioneDebitoriaType esitoDoc = new EsitoDocumentoPosizioneDebitoriaType();
		    PopolamentoDatiHelper.completaDatiDaPosizioneDebitoria(esitoDoc, pos,
			    payPosizioniDebitorieService.findRiferimentiClientByPosizioneDebitoria(pos.getId().getCodice()));
		    esitoDoc.setTipoDocumento(TipoDocumentoType.AVVISO);
		    esitoDoc.setStatoDocumento(StatoDocumentoType.NON_DISPONIBILE);
		    retEsiti.getEsitoPosizione().add(esitoDoc);
		}
	    }
	}
	if (wsPort != null) {
	    for (PayRegistrazioniContabili reg : cmd.getRegistrazioniPosizioni()) {
		for (PayPosizioniDebitorie pos : reg.getPosizioniDebitorie()) {
		    msg = "invocazione del servizio getPagamentoAttesoPdf in corso";
		    EsitoDocumentoPosizioneDebitoriaType esitoDoc = new EsitoDocumentoPosizioneDebitoriaType();
		    PopolamentoDatiHelper.completaDatiDaPosizioneDebitoria(esitoDoc, pos,
			    payPosizioniDebitorieService.findRiferimentiClientByPosizioneDebitoria(pos.getId().getCodice()));
		    esitoDoc.setTipoDocumento(TipoDocumentoType.AVVISO);
		    esitoDoc.setStatoDocumento(StatoDocumentoType.DISPONIBILE);
		    GetPagamentoAttesoPdfRequest avvisoReq = new GetPagamentoAttesoPdfRequest();
		    avvisoReq.setCodiceEnte(ente.getCfCodiceProfiloPSP());
		    avvisoReq.setCodiceServizio(getCodiceServizio(pos));
		    avvisoReq.setIuv(pos.getIuv());
		    try {
			GetPagamentoAttesoPdfResponse avvisoResp = wsPort.getPagamentoAttesoPdf(avvisoReq);
			if (StringUtils.isNotBlank(avvisoResp.getCodiceErrore())) {
			    msg = "l'invocazione del servizio getPagamentoAttesoPdf ha restituito il codice errore " + avvisoResp.getCodiceErrore();
			    esitoDoc.setCodiceErrore(avvisoResp.getCodiceErrore());
			    esitoDoc.setMessaggio(msg);
			    esitoDoc.setStatoDocumento(StatoDocumentoType.NON_DISPONIBILE);
			    esitoDoc.setEsito(false);
			} else {
			    if (avvisoResp.getStreampdf() != null) {
				msg = "l'avviso di pagamento in PDF è stato scaricato correttamente";
				esitoDoc.setEsito(true);
				esitoDoc.setDocumento(avvisoResp.getStreampdf());
				esitoDoc.setNomeDocumento("AVVISO_" + pos.getIuv() + ".pdf");
			    } else {
				msg = "l'avviso di pagamento in PDF non è stato restituito dal servizio del connettore " + this.getConnectorName();
				esitoDoc.setEsito(false);
				esitoDoc.setStatoDocumento(StatoDocumentoType.NON_DISPONIBILE);
			    }
			}
		    } catch (Exception e) {
			this.handleException(e, esitoDoc);
			msg = "impossibile scaricare l'avviso PDF a causa dell'errore: " + e.toString();
			log.error("inviaAvvisiPagamento - " + msg, e);
		    }
		    esitoDoc.setMessaggio(msg);
		    log.info("inviaAvvisiPagamento - {}", msg);
		    retEsiti.getEsitoPosizione().add(esitoDoc);
		}
	    }
	}
	return retEsiti;
    }

    @Override
    public void validateRichiestaInserimentoPosizioniDebitorie(RegistrazioneContabileType registrazioneContabile)
	    throws ValidazionePosizioniDebitorieException {

	if (registrazioneContabile != null) {
	    SoggettoDebitoreType sogg = registrazioneContabile.getSoggettoDebitore();
	    // 6. Obbligatori per soggetto sono NOME, COGNOME, CF, NATURA GIURIDICA e non anche i dati di localizzazione (es S.F.D.)
	    if (StringUtils.isBlank(sogg.getNome())) {
		throw new ValidazionePosizioniDebitorieException("denominazione del soggetto debitore obbligatoria");
	    }
	    if (StringUtils.isBlank(sogg.getCfpi())) {
		throw new ValidazionePosizioniDebitorieException("Codice fiscale o Partita IVA del soggetto debitore obbligatoria");
	    }
	    //Il numero civico 
	    if (sogg.getCivico() != null && sogg.getCivico().length() == 0) {
		throw new ValidazionePosizioniDebitorieException("il numero civico se specificato non può essere vuoto");
	    }
	    // L’indirizzo email 
	    if (sogg.getEmail() != null && sogg.getEmail().length() == 0) {
		throw new ValidazionePosizioniDebitorieException("indirizzo email se specificato non può essere vuoto");
	    }
	    // validazione formale cap // MYSQL PUO' SALVARE VALORI ''
	    //	    if (sogg.getCap() != null && sogg.getCap().length() == 0) {
	    //		throw new ValidazionePosizioniDebitorieException("il cap se specilficato non può essere vuoto");
	    //	    }
	    for (PosizioneDebitoriaType rata : registrazioneContabile.getRate().getRata()) {
		// Data scadenza pagamento superata
		if (rata.getDataScadenza() != null) {
		    Date d = Utilities.getDate(rata.getDataScadenza());
		    if (d.compareTo(new Date()) < 0) {
			throw new ValidazionePosizioniDebitorieException("la data di scadenza è già passata");
		    }
		}
		for (ImportoPagamentoType importo : rata.getImporto().getComponenteImporto()) {
		    // numeroAccertamento -> codice accertamento obbligatorio
		    if (StringUtils.isBlank(importo.getNumeroAccertamento())) {
			throw new ValidazionePosizioniDebitorieException("il numero Accertamento è obbligatorio");
		    }
		    //descrizione causdale obbligatoria
		    if (StringUtils.isBlank(importo.getNumeroAccertamento())) {
			throw new ValidazionePosizioniDebitorieException("la descrizione dei dettagli importo è obbligatoria");
		    }
		    //datiRiscossione -> codice entrata maxlength 4
		    if (importo.getDatiRiscossione() != null && importo.getDatiRiscossione().length() != 4
			    && importo.getDatiRiscossione().length() > 4) {
			throw new ValidazionePosizioniDebitorieException(
				"i dati riscossione nei dettagli importo se specificati devono essere lunghi esattamente 4 caratteri");
		    }
		}
	    }
	}
    }

    private boolean isCodiceEsitoOk(String codiceEsito) {

	return CodiceEsitoComunicazione.SUCCESS.value().equals(codiceEsito);
    }

    private PagamentoAttesoWs popolaPagamentoAtteso(PayPosizioniDebitorie pos, boolean isRateizzato) throws PayException {

	log.debug("popolo la richiesta di inserimento pagamenti attesi per la posizione {}", pos.getId().getCodice());
	PagamentoAttesoWs pa = new PagamentoAttesoWs();
	PayProfiliEntiCreditori enteCfg = PayConfigurationHelper.getProfiloEnteCreditore();
	pa.setCodiceEnte(enteCfg.getCfCodiceProfiloPSP());
	pa.setCodiceServizio(getCodiceServizio(pos));
	pa.setDataInizioValidita(Utilities.getXMLGregorianCalendar(pos.getDataRegistrazione()));
	// se la causale specifica la data di scadenza (es sanzioni) imposto quella
	String dataScadenza = posizioniDebitorieCommandService.findValoreUnicoParametroFromPosizioneDebitoria(pos, new ParametroDataScadenza());
	if (dataScadenza != null) {
	    log.debug("data scadenza presa da parametri causale {}", dataScadenza);
	    try {
		Date dtScadenza = new SimpleDateFormat(Utilities.DATE_FORMAT_PATTERN).parse(dataScadenza);
		pa.setDataScadenza(Utilities.getXMLGregorianCalendar(dtScadenza));
	    } catch (ParseException e) {
		throw new IllegalArgumentException("DataScadenza non valida: " + dataScadenza);
	    }
	} else if (pos.getDataScadenza() != null) {
	    pa.setDataScadenza(Utilities.getXMLGregorianCalendar(pos.getDataScadenza()));
	}
	// o setDataScadenzaStampabile o setDataScadenzaNote una delle due è obbligatoria
	String dataScadenzaStampabile = posizioniDebitorieCommandService.findValoreUnicoParametroFromPosizioneDebitoria(pos,
		new ParametroDataScadenzaStampabile());
	if (StringUtils.isNotBlank(dataScadenzaStampabile)) {
	    log.debug("data scadenza stampabile presa da parametri causale {}", dataScadenzaStampabile);
	    pa.setDataScadenzaNote(dataScadenzaStampabile); // ENTRO 30 GIORNI DALLA DATA DI NOTIFICA
	} else {
	    if (pos.getDataScadenza() != null) {
		pa.setDataScadenzaStampabile(Utilities.getXMLGregorianCalendar(pos.getDataScadenza()));
	    } else {
		pa.setDataScadenzaStampabile(pa.getDataScadenza());
	    }
	}
	String idPosizionePsp = generaIdentificativoPagamentoAtteso(pos.getId());
	pa.setIdBoPagamentoAtteso(idPosizionePsp);
	PaySoggettiDebitori sogg = pos.getSoggettoDebitore();
	if (StringUtils.isNotBlank(sogg.getCap())) { // MYSQL PUO' SALVARE VALORI ''
	    pa.setAnagCap(sogg.getCap());
	}
	pa.setAnagCivico(sogg.getCivico());
	pa.setAnagCfPiva(sogg.getCfPi());
	NaturaGiuridica ng = sogg.getCfPi().length() == 16 ? NaturaGiuridica.PERSONA_FISICA : NaturaGiuridica.PERSONA_GIURIDICA;
	pa.setAnagNaturaGiuridica(ng.value());
	StringBuilder sbNome = new StringBuilder(sogg.getNome());
	if (StringUtils.isNotBlank(sogg.getCognome())) {
	    sbNome.append(" ").append(sogg.getCognome());
	}
	pa.setAnagDenominazione(Utilities.trimToLength(sbNome.toString(), 70));
	pa.setAnagEmail(sogg.getEmail());
	pa.setAnagIndirizzo(sogg.getVia());
	pa.setAnagLocalita(sogg.getLocalita());
	pa.setAnagNazione(this.getCodificaNazione(sogg.getStato()));
	pa.setAnagProvincia(sogg.getProvincia());
	String causale = pos.getDescrizioneCausale();
	if (isRateizzato) {
	    causale = pos.getDescrizioneCausale() + " rata " + pos.getNumRata();
	}
	pa.setCausale(StringUtils.left(causale, 100));
	PartitaDebitoriaWs pd = new PartitaDebitoriaWs();
	pd.setIdBoPartitaDebitoria(PkId.toStringId(pos.getId()));
	pd.setDescrizione(pa.getCausale());
	BigDecimal importo = BigDecimal.ZERO;
	for (PayDettaglioImporti rigaImporto : pos.getDettagliImporto()) {
	    importo = importo.add(rigaImporto.getImporto());
	    ArticoloDebitorioWs art = new ArticoloDebitorioWs();
	    art.setImporto(formatImporto(rigaImporto.getImporto()));
	    art.setCodiceAccertamento(rigaImporto.getNumeroAccertamento());
	    art.setDescrizione(rigaImporto.getDescCausale());
	    art.setCodiceEntrata(rigaImporto.getDatiRiscossione());
	    art.setCodiceCapitolo(rigaImporto.getNumeroSottoAccertamento());
	    pd.getArticoliDebitoriWs().add(art);
	}
	pd.setImporto(this.formatImporto(importo));
	pa.getPartiteDebitorieWs().add(pd);
	pa.setImporto(this.formatImporto(importo));
	pa.setVisibileSol(true);
	return pa;
    }

    private String generaIdentificativoPagamentoAtteso(PkId idPayPos) {

	return idPayPos.getIdcomune() + // 
		PkId.TO_STRING_ID_SEPARATOR + // 
		idPayPos.getCodice() + // 
		PkId.TO_STRING_ID_SEPARATOR + //
		System.currentTimeMillis(); // rendo univoco l'identificativo che in test vengono bruciati gl identificativi delle posizioni debitorie
    }

    private PkId parseIdBoPagamentoAtteso(String idBoPagamentoAtteso) {

	log.debug("parse idBoPagamentoAtteso {}", idBoPagamentoAtteso);
	String[] split = idBoPagamentoAtteso.split(PkId.TO_STRING_ID_SEPARATOR);
	log.debug("parse split {}", (Object[]) split);
	String idcomune = split[0];
	Integer codicePosDeb = Integer.parseInt(split[1]);
	return new PkId(idcomune, codicePosDeb);
    }

    private String getCodificaNazione(String nazione) {

	log.debug("{}", nazione);
	return NAZIONE_IT;
    }

    private Mip2BEWsdlPublic getMip2BEPort(PayConnectorWsEndpoint wsConfig) throws PayException {

	Mip2BEWsdlPublic info = this.mip2bePort == null ? (Mip2BEWsdlPublic) this.getWSPort(wsConfig, Mip2BEWsdlPublic.class) : this.mip2bePort;
	this.prepareWsPort(info, wsConfig);
	this.mip2bePort = info;
	return info;
    }

    private String formatImporto(BigDecimal input) {

	return input.toPlainString();
    }

    private void prepareWsPort(Object wsPort, PayConnectorWsEndpoint endpointCfg) throws PayException {

	Client client = ClientProxy.getClient(wsPort);
	HTTPConduit conduit = (HTTPConduit) client.getConduit();
	String trustStorelocation = this.payConnectorConfigValuesService.getValoreParametroConfigurazione(ConfigParamNames.SSL_TRUST_STORE_LOCATION);
	log.debug("prepareWsPort creazione del client {}", endpointCfg.getEndpointUrl());
	if (StringUtils.isNotBlank(trustStorelocation)) {
	    String trustStorePassw = this.payConnectorConfigValuesService.getValoreParametroConfigurazione(ConfigParamNames.SSL_TRUST_STORE_PASSWORD);
	    String keyStoreLocation = this.payConnectorConfigValuesService.getValoreParametroConfigurazione(ConfigParamNames.SSL_KEY_STORE_LOCATION);
	    String keyStorePassw = this.payConnectorConfigValuesService.getValoreParametroConfigurazione(ConfigParamNames.SSL_KEY_STORE_PASSWORD);
	    log.debug("prepareWsPort trustStore configurato {}, keystore configurato {}", trustStorelocation, keyStoreLocation);
	    TLSClientParameters tlsCP = new TLSClientParameters();
	    try {
		KeyStore keyStore = KeyStore.getInstance("JKS");
		keyStore.load(new FileInputStream(keyStoreLocation), keyStorePassw.toCharArray());
		KeyManager[] myKeyManagers = TrustManagerUtils.getKeyManagers(keyStore, keyStorePassw);
		tlsCP.setKeyManagers(myKeyManagers);
		KeyStore trustStore = KeyStore.getInstance("JKS");
		trustStore.load(new FileInputStream(trustStorelocation), trustStorePassw.toCharArray());
		TrustManager[] myTrustStoreKeyManagers = TrustManagerUtils.getTrustManagers(trustStore);
		tlsCP.setTrustManagers(myTrustStoreKeyManagers);
		conduit.setTlsClientParameters(tlsCP);
	    } catch (IOException | GeneralSecurityException e) {
		log.error("Errore nella creazione del client soap " + e.getMessage(), e);
		throw new PayException("errore nella creazione del client SOAP", e);
	    }
	}
	if (endpointCfg.getTimeout() != null) {
	    HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
	    httpClientPolicy.setConnectionTimeout(DEFAULT_CONNECTION_TIMEOUT);
	    httpClientPolicy.setReceiveTimeout(endpointCfg.getTimeout());
	    conduit.setClient(httpClientPolicy);
	}
	if (StringUtils.isNotBlank(endpointCfg.getUtente())) {
	    AuthorizationPolicy authorization = getBasicAuthorization(endpointCfg.getUtente(), endpointCfg.getPassword());
	    conduit.setAuthorization(authorization);
	}
    }

    @Override
    public void modificaDataScadenzaPosizioneDebitoria(Integer idPosizioneDebitoria, Date nuovaDataScadenza) throws PayException {

	log.debug("modificaDataScadenzaPosizioneDebitoria {}-{}", idPosizioneDebitoria, nuovaDataScadenza);
	//	DOC SILFI Una richiesta di modifica può essere eseguita se rispetta i seguenti vincoli:
	//	    - il codice identificativo del Pagamento Atteso è esistente
	//	    - il Pagamento Atteso non è in corso.
	//	    - il Pagamento Atteso non è:
	//	    - pagato
	//	    - annullato
	//	    - se il Servizio non ha il flag contabilità attivo, nella richiesta non devono essere
	//	    presenti né partite debitorie né articoli debitori, pena un errore specifico.
	//	    - se il servizio ha il flag contabilità attivo, è obbligatorio passare i dati delle partite
	//	    debitorie e degli articoli debitori che compongono il pagamento atteso.
	//	    le informazioni sui totali degli importi devono sempre essere coerenti:
	//	    - l'importo del Pagamento Atteso deve essere pari alla somma degli importi
	//	    delle partite collegate
	//	    - l'importo di ogni partita deve essere pari alla somma degli importi degli articoli
	//	    collegati
	//	    - devono essere rispettati i vincoli previsti dai modelli dati coinvolti (campi obbligatori,
	//	    tipi dati, reference etc)
	//	    - deve essere presente per ogni richiesta una motivazione
	//	    Possono essere modificate solo le seguenti informazioni del Pagamento Atteso:
	//	    - importo
	//	    - descrizione
	//	    - partite e articoli
	//	    - data di inizio validità
	//	    - data di scadenza
	//	    - visibilità verso il sol
	// RECUPERA LA POSIZIONE DEBITORIA
	PayPosizioniDebitorie payPos = payPosizioniDebitorieService.findById(new PkId(idPosizioneDebitoria));
	log.debug("modificaDataScadenzaPosizioneDebitoria prima di create il client Mip2BEWsdlPublic {}-{}", idPosizioneDebitoria, nuovaDataScadenza);
	Mip2BEWsdlPublic wsPort = this.getMip2BEPort(this.getWsCaricamentoConfig());
	ModificaPagamentiAttesiRequest request = new ModificaPagamentiAttesiRequest();
	DatiModificaPagamentoAttesoWs datiPagamento = new DatiModificaPagamentoAttesoWs();
	log.debug("modificaDataScadenzaPosizioneDebitoria popolo i dati del pagamentoAtteso {}-{}", idPosizioneDebitoria, nuovaDataScadenza);
	boolean isRateizzato = payPos.getRegistrazioneContabile().getPosizioniDebitorie().size() > 1;
	PagamentoAttesoWs datiModificati = this.popolaPagamentoAtteso(payPos, isRateizzato);
	datiModificati.setIuv(payPos.getIuv());
	datiModificati.setDataScadenza(Utilities.getXMLGregorianCalendar(nuovaDataScadenza));
	if (datiModificati.getDataScadenzaStampabile() != null) { // evitare errore (ERR_BE_PA_GEN_043)==>Data scadenza stampabile del pagamento successiva alla data di scadenza effettiva
	    datiModificati.setDataScadenzaStampabile(datiModificati.getDataScadenza());
	}
	datiPagamento.setModificaPagamentoAttesoWs(datiModificati);
	datiPagamento.setMotivazione("Modifica della data di scadenza del pagamento");
	request.getDatiPagamentoAttesoWs().add(datiPagamento);
	ModificaPagamentiAttesiResponse response = null;
	try {
	    log.debug("modificaDataScadenzaPosizioneDebitoria prima di invocare il ws pagamentoAttesi {}-{}", idPosizioneDebitoria,
		    nuovaDataScadenza);
	    response = wsPort.modificaPagamentiAttesi(request);
	} catch (Exception e) {
	    String messaggio = "Errore nella modifica della data di scadenza della posizione debitoria " + payPos.getId() +
			       ". Si sono verificati problemi a raggiungere il servizio modificaPagamentiAttesi a causa di " + e.getMessage();
	    log.error(messaggio);
	    throw new PayException(messaggio, e);
	}
	if (StringUtils.isNotBlank(response.getCodiceErrore())) {
	    String messaggio = "Errore nella modifica della data di scadenza della posizione debitoria " + payPos.getId() + //
			       ". Il codice di errore restituito è (" + response.getCodiceErrore() + ")==>" + response.getDescrizioneErrore();
	    log.error(messaggio);
	    throw new PayException(messaggio);
	}
	List<PagamentoAttesoRispostaWs> pagamentoAttesoRispostaWs = response.getPagamentoAttesoRispostaWs();
	for (PagamentoAttesoRispostaWs r : pagamentoAttesoRispostaWs) {
	    if (!r.getCodiceEsito().equalsIgnoreCase("SUCCESS")) {
		String messaggio = "Non è stato possibile modificare la posizione debitoria " + payPos.getId() + //
				   ". Il codice di errore restituito è (" + r.getCodiceEsito() + //
				   ")==>" + r.getDescrizioneEsito();
		log.error(messaggio);
		throw new PayException(messaggio);
	    }
	}
	log.debug("modificaDataScadenzaPosizioneDebitoria data scadenza modificata con successo {}-{}", idPosizioneDebitoria, nuovaDataScadenza);
    }

    @Override
    public boolean supportaAttivaSessionePagamento() {

	return true;
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
    public boolean supportaMolteCausaliRaggruppate() {

	return true;
    }

    @Override
    public List<IParameter> getListaParametriRichiesti() {

	return ParametriConnettoreHelper.getMappaParametriConnettore(this.getClass().getName());
    }

    @Override
    public IUVHelper generaIUV(PayPosizioniDebitorie posDeb, String identificativoCausalePerCalcolo) {

	return null;
    }

    private String getCodiceServizioDaPosizioni(List<PayPosizioniDebitorie> poss) throws PayException {

	Set<String> servizi = new HashSet<>();
	String riferimentiPos = null;
	for (PayPosizioniDebitorie payPos : poss) {
	    servizi.add(getCodiceServizio(payPos));
	}
	if (servizi.size() != 1) {
	    log.error("Non è stato configurato correttamente il codiceServizio per le posizioni {}, servizi configurati {}", riferimentiPos, servizi);
	    throw new PayException("Non è stato configurato correttamente il codiceServizio per le posizioni: " + riferimentiPos +
				   ", servizi configurati " + servizi);
	}
	return servizi.iterator().next();
    }

    private String getCodiceServizio(PayPosizioniDebitorie pos) throws PayException {

	String ret = posizioniDebitorieCommandService.findValoreUnicoParametroFromPosizioneDebitoria(pos, new ParametroCodiceServizio());
	if (StringUtils.isBlank(ret)) {
	    log.error("Non è stato configurato correttamente il codiceServizio per la posizione {}", pos.getId());
	    throw new PayException("Non è stato configurato correttamente il codiceServizio rif: " + pos.getId());
	}
	return ret;
    }
}
