package it.gruppoinit.pal.gp.pay.connector.mip;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.activation.DataHandler;
import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.exceptions.ValidazionePosizioniDebitorieException;
import it.gruppoinit.pal.gp.core.utils.InputStreamDataSource;
import it.gruppoinit.pal.gp.core.utils.JSONUtils;
import it.gruppoinit.pal.gp.pay.command.PosizioniDebitorieCommand;
import it.gruppoinit.pal.gp.pay.command.RichiestaSuListaPosizioniCommand;
import it.gruppoinit.pal.gp.pay.connector.AbstractPayConnector;
import it.gruppoinit.pal.gp.pay.connector.IPayConnector;
import it.gruppoinit.pal.gp.pay.connector.IPayConnectorCaricamentoMassivo;
import it.gruppoinit.pal.gp.pay.connector.mip.genova.ICaricamentoMassivoPosizioniNEXIGenovaService;
import it.gruppoinit.pal.gp.pay.connector.mip.ws.client.ApiClient;
import it.gruppoinit.pal.gp.pay.connector.mip.ws.schema.AggiornaAvvisoDati;
import it.gruppoinit.pal.gp.pay.connector.mip.ws.schema.AggiornaAvvisoRespWrapper;
import it.gruppoinit.pal.gp.pay.connector.mip.ws.schema.AnnullaAvvisoRespWrapper;
import it.gruppoinit.pal.gp.pay.connector.mip.ws.schema.AvvisoPagamento;
import it.gruppoinit.pal.gp.pay.connector.mip.ws.schema.DatiDebitore;
import it.gruppoinit.pal.gp.pay.connector.mip.ws.schema.DatiSingoloVersamento;
import it.gruppoinit.pal.gp.pay.connector.mip.ws.schema.DatiVersamento;
import it.gruppoinit.pal.gp.pay.connector.mip.ws.schema.Debito;
import it.gruppoinit.pal.gp.pay.connector.mip.ws.schema.Documento;
import it.gruppoinit.pal.gp.pay.connector.mip.ws.schema.ImportoContabile;
import it.gruppoinit.pal.gp.pay.connector.mip.ws.schema.ImportoContabileWrapper;
import it.gruppoinit.pal.gp.pay.connector.mip.ws.schema.RichiediAvvisoDati;
import it.gruppoinit.pal.gp.pay.connector.mip.ws.schema.RichiestaAvvisoDatiRespWrapper;
import it.gruppoinit.pal.gp.pay.domain.PayConnectorConfigParams.ConfigParamNames;
import it.gruppoinit.pal.gp.pay.domain.PayConnectorWsEndpoint;
import it.gruppoinit.pal.gp.pay.domain.PayDettaglioImporti;
import it.gruppoinit.pal.gp.pay.domain.PayPosizioniDebitorie;
import it.gruppoinit.pal.gp.pay.domain.PayProfiliEntiCreditori;
import it.gruppoinit.pal.gp.pay.domain.PayRegcausaliParametri;
import it.gruppoinit.pal.gp.pay.domain.PayRegistrazioniContabili;
import it.gruppoinit.pal.gp.pay.domain.PayRichieste;
import it.gruppoinit.pal.gp.pay.domain.PaySessioniPagamento;
import it.gruppoinit.pal.gp.pay.domain.PaySoggettiDebitori;
import it.gruppoinit.pal.gp.pay.domain.PayStatoPagamenti;
import it.gruppoinit.pal.gp.pay.domain.PayStatoPagamenti.StatiPagamento;
import it.gruppoinit.pal.gp.pay.domain.TipiEvento;
import it.gruppoinit.pal.gp.pay.exception.PayConfigurationException;
import it.gruppoinit.pal.gp.pay.exception.PayException;
import it.gruppoinit.pal.gp.pay.parameters.IParameter;
import it.gruppoinit.pal.gp.pay.parameters.ParametroRipartizioneImporti;
import it.gruppoinit.pal.gp.pay.scheduler.ServiziSchedulatiEnum;
import it.gruppoinit.pal.gp.pay.service.PagoPAService;
import it.gruppoinit.pal.gp.pay.service.PayConnectorConfigValuesService;
import it.gruppoinit.pal.gp.pay.service.PayPosizioniDebitorieService;
import it.gruppoinit.pal.gp.pay.service.PayRegcausaliParametriService;
import it.gruppoinit.pal.gp.pay.service.PayRichiesteService;
import it.gruppoinit.pal.gp.pay.service.PayStatoPagamentiService;
import it.gruppoinit.pal.gp.pay.service.PosizioniDebitorieCommandService;
import it.gruppoinit.pal.gp.pay.service.helper.EsitoElaborazione;
import it.gruppoinit.pal.gp.pay.service.helper.IUVHelper;
import it.gruppoinit.pal.gp.pay.service.helper.ParametriConnettoreHelper;
import it.gruppoinit.pal.gp.pay.service.helper.PayConfigurationHelper;
import it.gruppoinit.pal.gp.pay.service.helper.PopolamentoDatiHelper;
import it.gruppoinit.pal.gp.pay.ws.schema.DatiPagamentoType;
import it.gruppoinit.pal.gp.pay.ws.schema.ElencoDocumentiEsitoType;
import it.gruppoinit.pal.gp.pay.ws.schema.ElencoPosizioniDebitorieEsitoType;
import it.gruppoinit.pal.gp.pay.ws.schema.ElencoStatoPosizioniType;
import it.gruppoinit.pal.gp.pay.ws.schema.EsitoDocumentoPosizioneDebitoriaType;
import it.gruppoinit.pal.gp.pay.ws.schema.EsitoOperazionePosizioneDebitoriaType;
import it.gruppoinit.pal.gp.pay.ws.schema.RegistrazioneContabileType;
import it.gruppoinit.pal.gp.pay.ws.schema.SoggettoDebitoreType;
import it.gruppoinit.pal.gp.pay.ws.schema.StatoDocumentoType;
import it.gruppoinit.pal.gp.pay.ws.schema.StatoPagamentoType;
import it.gruppoinit.pal.gp.pay.ws.schema.StatoPosizioneType;
import it.gruppoinit.pal.gp.pay.ws.schema.TipoDocumentoType;

public class MIPConnector extends AbstractPayConnector implements IPayConnector, IPayConnectorCaricamentoMassivo {

    private static final String CONTO_NON_DEFINITO = "_CONTO_NON_DEFINITO";

    private enum SCHED_CARICAMENTO_MASSIVO_STRATEGIA_ENUM {
	GENOVA_NEXI
    }

    private static final Logger log = LoggerFactory.getLogger(MIPConnector.class);
    private PayConnectorConfigValuesService payConnectorConfigValuesService;
    private PayStatoPagamentiService pagamenPayStatoPagamentiService;
    private PayPosizioniDebitorieService payPosizioniDebitorieService;
    private PayRichiesteService payRichiesteService;
    private PagoPAService pagoPAService;
    private PosizioniDebitorieCommandService posizioniDebitorieCommandService;
    private ICaricamentoMassivoPosizioniNEXIGenovaService caricamentoMassivoPosizioniNEXIGenovaService;
    private PayRegcausaliParametriService payRegcausaliParametriService;

    @Autowired
    public void setPayConnectorConfigValuesService(PayConnectorConfigValuesService payConnectorConfigValuesService) {

	this.payConnectorConfigValuesService = payConnectorConfigValuesService;
    }

    @Autowired
    public void setPagamenPayStatoPagamentiService(PayStatoPagamentiService pagamenPayStatoPagamentiService) {

	this.pagamenPayStatoPagamentiService = pagamenPayStatoPagamentiService;
    }

    @Autowired
    public void setPayPosizioniDebitorieService(PayPosizioniDebitorieService payPosizioniDebitorieService) {

	this.payPosizioniDebitorieService = payPosizioniDebitorieService;
    }

    @Autowired
    public void setPayRichiesteService(PayRichiesteService payRichiesteService) {

	this.payRichiesteService = payRichiesteService;
    }

    @Autowired
    public void setPagoPAService(PagoPAService pagoPAService) {

	this.pagoPAService = pagoPAService;
    }

    @Autowired
    public void setPosizioniDebitorieCommandService(PosizioniDebitorieCommandService posizioniDebitorieCommandService) {

	this.posizioniDebitorieCommandService = posizioniDebitorieCommandService;
    }

    @Autowired
    public void setCaricamentoMassivoPosizioniNEXIGenovaService(
	    ICaricamentoMassivoPosizioniNEXIGenovaService caricamentoMassivoPosizioniNEXIGenovaService) {

	this.caricamentoMassivoPosizioniNEXIGenovaService = caricamentoMassivoPosizioniNEXIGenovaService;
    }

    @Autowired
    public void setPayRegcausaliParametriService(PayRegcausaliParametriService payRegcausaliParametriService) {

	this.payRegcausaliParametriService = payRegcausaliParametriService;
    }

    @Override
    public ElencoPosizioniDebitorieEsitoType registraPosizioniDebitorie(PosizioniDebitorieCommand datiRegistrazioniCommand) throws PayException {

	ApiClient apiClient = inizializzaClient();
	ElencoPosizioniDebitorieEsitoType result = new ElencoPosizioniDebitorieEsitoType();
	for (PayRegistrazioniContabili payReg : datiRegistrazioniCommand.getRegistrazioniPosizioni()) {
	    for (PayPosizioniDebitorie payPos : payReg.getPosizioniDebitorie()) {
		RichiediAvvisoDati avvisoDati = new RichiediAvvisoDati();
		popolaRichiediAvvisoDati(payPos, avvisoDati);
		EsitoOperazionePosizioneDebitoriaType esitoPos = new EsitoOperazionePosizioneDebitoriaType();
		esitoPos.setStato(StatoPagamentoType.ACQUISITO);
		esitoPos.setIdPosizione(BigInteger.valueOf(payPos.getId().getCodice()));
		esitoPos.setIdRegistrazioneContabile(BigInteger.valueOf(payPos.getRegistrazioneContabile().getId().getCodice()));
		try {
		    String jsonReqBody = JSONUtils.marshal(avvisoDati, true);
		    //		    RichiestaAvvisoDatiRespWrapper resp = apiClient.call(
		    //			    this.getWsCaricamentoConfig().getEndpointUrl() + "/mip/avvisiPagamento/richiediAvviso", "POST", jsonReqBody, true,
		    //			    RichiestaAvvisoDatiRespWrapper.class);
		    String urlWs = this.getWsCaricamentoConfig().getEndpointUrl() + "/richiediAvviso";
		    RichiestaAvvisoDatiRespWrapper resp = apiClient.call(urlWs, "POST", jsonReqBody, true, RichiestaAvvisoDatiRespWrapper.class);
		    log.debug("RichiediAvviso {}", resp);
		    if (resp != null) {
			if ("00".equalsIgnoreCase(resp.getRichiediAvvisoDati().getEsitoOperazione().getCodice())) {
			    esitoPos.setCodiceAvviso(resp.getRichiediAvvisoDati().getDatiVersamento().getCodiceAvviso());
			    esitoPos.setIUV(resp.getRichiediAvvisoDati().getDatiVersamento().getIuv());
			    esitoPos.setEsito(true);
			    payPos.setIuv(esitoPos.getIUV());
			    payPos.setCodiceAvviso(esitoPos.getCodiceAvviso());
			    esitoPos.setQrCode(this.pagoPAService.generaQRCode(payPos));
			    esitoPos.setStato(StatoPagamentoType.ATTIVATO_IN_PSP);
			    esitoPos.setMessaggio(StatiPagamento.ATTIVATO_IN_PSP.description());
			    if (resp.getRichiediAvvisoDati().getPdFAvvisatura() != null) {
				String docName = "Avviso_" + resp.getRichiediAvvisoDati().getDatiVersamento().getCodiceAvviso() + ".pdf";
				byte[] avviso = Base64.decodeBase64(resp.getRichiediAvvisoDati().getPdFAvvisatura());
				DataHandler dataHandler = new DataHandler(new InputStreamDataSource(new ByteArrayInputStream(avviso)));
				EsitoDocumentoPosizioneDebitoriaType esitoDoc = new EsitoDocumentoPosizioneDebitoriaType();
				esitoDoc.setIdPosizione(BigInteger.valueOf(payPos.getId().getCodice()));
				esitoDoc.setCodiceAvviso(resp.getRichiediAvvisoDati().getDatiVersamento().getCodiceAvviso());
				esitoDoc.setIdRegistrazioneContabile(BigInteger.valueOf(payPos.getRegistrazioneContabile().getId().getCodice()));
				esitoDoc.setIUV(resp.getRichiediAvvisoDati().getDatiVersamento().getIuv());
				esitoDoc.setEsito(true);
				esitoDoc.setNomeDocumento(docName);
				esitoDoc.setDocumento(dataHandler);
				esitoDoc.setTipoDocumento(TipoDocumentoType.AVVISO);
				esitoDoc.setStatoDocumento(StatoDocumentoType.DISPONIBILE);
				payRichiesteService.registraEsitoRichiestaDocumentoTrans(esitoDoc, payPos);
			    }
			} else {
			    esitoPos.setEsito(false);
			    esitoPos.setStato(StatoPagamentoType.CON_ERRORE);
			    esitoPos.setMessaggio(resp.getRichiediAvvisoDati().getEsitoOperazione().getCodice() + " - " +
						  resp.getRichiediAvvisoDati().getEsitoOperazione().getDescrizione());
			    log.error("Errore nella creazione dell'avviso: {}", payPos.getIdPosizionePsp());
			}
		    } else {
			esitoPos.setEsito(false);
			esitoPos.setStato(StatoPagamentoType.CON_ERRORE);
			esitoPos.setMessaggio("Errore non specificato response nulla");
			log.error("Errore nella creazione dell'avviso: {}", payPos.getIdPosizionePsp());
		    }
		    result.getEsitoPosizione().add(esitoPos);
		} catch (Exception e) {
		    log.error("Errore nell' inserimento della pendenza: " + payPos.getIdPosizionePsp(), e);
		    esitoPos.setEsito(false);
		    esitoPos.setStato(StatoPagamentoType.CON_ERRORE);
		    this.handleException(e, esitoPos);
		    result.getEsitoPosizione().add(esitoPos);
		}
	    }
	}
	return result;
    }

    @Override
    public void modificaDataScadenzaPosizioneDebitoria(Integer idPosizioneDebitoria, Date nuovaDataScadenza) throws PayException {

	ApiClient apiClient = inizializzaClient();
	PayPosizioniDebitorie payPos = payPosizioniDebitorieService.findById(new PkId(idPosizioneDebitoria));
	log.debug("modificaDataScadenzaPagamentoAvviso popolo i dati del AggiornaAvvisoDati {}-{}", idPosizioneDebitoria, nuovaDataScadenza);
	AggiornaAvvisoDati request = this.popolaAggiornaAvvisoDati(payPos, nuovaDataScadenza);
	try {
	    String jsonBodyReq = JSONUtils.marshal(request, false);
	    AggiornaAvvisoRespWrapper response = null;
	    try {
		log.debug("modificaDataScadenzaPagamentoAvviso prima di invocare il ws AggiornaAvvisoDati {}-{}", idPosizioneDebitoria,
			nuovaDataScadenza);
		//		response = apiClient.call(this.getWsAvvisoConfig().getEndpointUrl() + "/mip/avvisiPagamento/aggiornaAvviso", "PUT", jsonBodyReq, true,
		//			AggiornaAvvisoRespWrapper.class);
		//		if (response != null && StringUtils.equals(response.getAggiornaAvvisoResp().getEsitoOperazione().getCodice(), "00")) {
		//		    String path = this.getWsAvvisoConfig().getEndpointUrl() +
		//				  "/MIP_DettaglioDebitoAvviso/avvisiPagamento/dettaglioAvvisoDaIdFiscaleECodiceAvviso?identificativoFiscale=" +
		//				  payPos.getSoggettoDebitore().getCfPi() + //
		//				  "&codiceAvviso=" + response.getAggiornaAvvisoResp().getCodiceAvviso() + //
		//				  "&ottieniPdf=" + true;
		String urlWs = this.getWsCaricamentoConfig().getEndpointUrl() + "/aggiornaAvviso";
		response = apiClient.call(urlWs, "PUT", jsonBodyReq, true, AggiornaAvvisoRespWrapper.class);
		if (response != null && StringUtils.equals(response.getAggiornaAvvisoResp().getEsitoOperazione().getCodice(), "00")) {
		    String path = this.getWsAvvisoConfig().getEndpointUrl() + "/dettaglioAvvisoDaIdFiscaleECodiceAvviso?identificativoFiscale=" +
				  payPos.getSoggettoDebitore().getCfPi() + //
				  "&codiceAvviso=" + response.getAggiornaAvvisoResp().getCodiceAvviso() + //
				  "&ottieniPdf=" + true;
		    AvvisoPagamento avvisoPagamento = apiClient.call(path, "GET", null, true, AvvisoPagamento.class);
		    if (avvisoPagamento != null && avvisoPagamento.getPdfAvviso() != null) {
			byte[] avviso = avvisoPagamento.getPdfAvviso().getFile();
			String nomeFile = avvisoPagamento.getPdfAvviso().getNomeFile();
			DataHandler dataHandler = new DataHandler(new InputStreamDataSource(new ByteArrayInputStream(avviso)));
			EsitoDocumentoPosizioneDebitoriaType esitoDoc = new EsitoDocumentoPosizioneDebitoriaType();
			esitoDoc.setEsito(true);
			esitoDoc.setNomeDocumento(nomeFile);
			esitoDoc.setDocumento(dataHandler);
			esitoDoc.setTipoDocumento(TipoDocumentoType.AVVISO);
			esitoDoc.setStatoDocumento(StatoDocumentoType.DISPONIBILE);
			PayRichieste rich = this.payRichiesteService.registraRichiestaPerPosizioneDebitoriaTrans(payPos, TipiEvento.INVIA_AVVISO);
			payPos.impostaRichiestaCorrente(rich);
			payRichiesteService.registraEsitoRichiestaDocumentoTrans(esitoDoc, payPos);
		    }
		}
	    } catch (Exception e) {
		String messaggio = "Errore nella modifica della data di scadenza del pagamento dell'avviso " + payPos.getId() +
				   ". Si sono verificati problemi a raggiungere il servizio AggiornaAvvisoDati a causa di " + e.getMessage();
		log.error(messaggio);
		throw new PayException(messaggio, e);
	    }
	    if (response != null && !StringUtils.equalsIgnoreCase(response.getAggiornaAvvisoResp().getEsitoOperazione().getCodice(), "00")) {
		String messaggio = "Errore nella modifica della data di scadenza del pagamento dell'avviso " + payPos.getId() + //
				   ". Il codice di errore restituito è (" + response.getAggiornaAvvisoResp().getEsitoOperazione().getCodice() +
				   ")==>" + response.getAggiornaAvvisoResp().getEsitoOperazione().getDescrizione();
		log.error(messaggio);
		throw new PayException(messaggio);
	    }
	} catch (JAXBException e) {
	    log.error("Errore di marshal della classe AggiornaAvvisoDati: {}", e.getMessage());
	}
	log.debug("AggiornaAvvisoDati data scadenza modificata con successo {}-{}", idPosizioneDebitoria, nuovaDataScadenza);
    }

    @Override
    public ElencoStatoPosizioniType verificaStatoPagamenti(RichiestaSuListaPosizioniCommand cmd) throws PayException {

	ApiClient apiClient = inizializzaClient();
	ElencoStatoPosizioniType result = new ElencoStatoPosizioniType();
	for (PayPosizioniDebitorie payPos : cmd.getPosizioni()) {
	    StatoPosizioneType retStatus = new StatoPosizioneType();
	    retStatus.setEsito(true);
	    PopolamentoDatiHelper.completaDatiDaPosizioneDebitoria(retStatus, payPos,
		    payPosizioniDebitorieService.findRiferimentiClientByPosizioneDebitoria(payPos.getId().getCodice()));
	    if (StringUtils.isNotBlank(payPos.getCodiceAvviso())) {
		try {
		    //		    String path = this.getWsVerificaConfig().getEndpointUrl() +
		    //				  "/MIP_DettaglioDebitoAvviso/debitiUtente/dettaglioDebitoDaIdFiscaleECodiceAvviso?identificativoFiscale=" +
		    //				  payPos.getSoggettoDebitore().getCfPi() + "&codiceAvviso=" + payPos.getCodiceAvviso();
		    String path = this.getWsVerificaConfig().getEndpointUrl() + "/dettaglioDebitoDaIdFiscaleECodiceAvviso?identificativoFiscale=" +
				  payPos.getSoggettoDebitore().getCfPi() + "&codiceAvviso=" + payPos.getCodiceAvviso();
		    Debito debito = apiClient.call(path, "GET", null, true, Debito.class);
		    log.debug("debito {}", debito);
		    if (debito.getEsitoPagamento() != null) {
			switch (debito.getEsitoPagamento()) {
			case OK:
			case CODE9:
			    retStatus.setStato(StatoPagamentoType.NOTIFICATO_DA_PSP);
			    DatiPagamentoType pagamenti = new DatiPagamentoType();
			    pagamenti.setIuv(debito.getIuv());
			    pagamenti.setImportoPagato(BigDecimal.valueOf(debito.getImportoPagato()));
			    if (debito.getCausale() != null) {
				pagamenti.setDescrizioneCausale(debito.getCausale().trim());
			    }
			    pagamenti.setNote(debito.getServizio());
			    if (debito.getDataPagamento() != null) {
				GregorianCalendar gregorianCalendar = new GregorianCalendar();
				gregorianCalendar.setTime(debito.getDataPagamento());
				XMLGregorianCalendar xmlData = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
				pagamenti.setDataOraPagamento(xmlData);
			    }
			    pagamenti.setRiferimentiPagamento(debito.getNumeroDocumento());
			    SoggettoDebitoreType paySoggettiDebitori = new SoggettoDebitoreType();
			    paySoggettiDebitori.setCap(payPos.getSoggettoDebitore().getCap());
			    paySoggettiDebitori.setCfpi(debito.getDebitore());
			    paySoggettiDebitori.setCivico(payPos.getSoggettoDebitore().getCivico());
			    paySoggettiDebitori.setNome(payPos.getSoggettoDebitore().getNome());
			    paySoggettiDebitori.setEmail(payPos.getSoggettoDebitore().getEmail());
			    paySoggettiDebitori.setLocalita(payPos.getSoggettoDebitore().getLocalita());
			    paySoggettiDebitori.setProvincia(payPos.getSoggettoDebitore().getProvincia());
			    paySoggettiDebitori.setStato(payPos.getSoggettoDebitore().getStato());
			    paySoggettiDebitori.setVia(payPos.getSoggettoDebitore().getVia());
			    pagamenti.setSoggettoPagatore(paySoggettiDebitori);
			    retStatus.setDatiPagamento(pagamenti);
			    break;
			case KO:
			    break;
			default:
			    break;
			}
		    }
		} catch (Exception e) {
		    retStatus.setEsito(false);
		    this.handleException(e, retStatus);
		}
	    }
	    result.getStatoPosizioni().add(retStatus);
	}
	return result;
    }

    @Override
    public ElencoPosizioniDebitorieEsitoType annullaPosizioniDebitorie(PosizioniDebitorieCommand datiRegistrazioniCommand, boolean pagatoOffline)
	    throws PayException {

	ApiClient apiClient = inizializzaClient();
	PayProfiliEntiCreditori profiloEnte = PayConfigurationHelper.getProfiloEnteCreditore();
	ElencoPosizioniDebitorieEsitoType result = new ElencoPosizioniDebitorieEsitoType();
	for (PayRegistrazioniContabili payReg : datiRegistrazioniCommand.getRegistrazioniPosizioni()) {
	    for (PayPosizioniDebitorie payPos : payReg.getPosizioniDebitorie()) {
		EsitoOperazionePosizioneDebitoriaType esito = new EsitoOperazionePosizioneDebitoriaType();
		esito.setIdPosizione(BigInteger.valueOf(payPos.getId().getCodice()));
		PayStatoPagamenti payStato = this.pagamenPayStatoPagamentiService.getStatoPosizioneDebitoria(payPos);
		esito.setStato(StatoPagamentoType.fromValue(payStato.getStato()));
		esito.setEsito(true);
		PopolamentoDatiHelper.completaDatiDaPosizioneDebitoria(esito, payPos,
			payPosizioniDebitorieService.findRiferimentiClientByPosizioneDebitoria(payPos.getId().getCodice()));
		if (StringUtils.isNotBlank(payPos.getCodiceAvviso())) {
		    String codiceVersamento = posizioniDebitorieCommandService.findCodiceVersamentoFromPosizioneDebitoria(payPos);
		    //		    String url = this.getWsAnnullamentoConfig().getEndpointUrl() + "/mip/avvisiPagamento/annullaAvviso?" + // 
		    //				 "&IDENTE=" + profiloEnte.getCfCodiceProfiloPSP() + //
		    //				 "&codiceAvviso=" + payPos.getCodiceAvviso() + //
		    //				 "&tipologiaEntrata=" + codiceVersamento;
		    String url = this.getWsAnnullamentoConfig().getEndpointUrl() + "/annullaAvviso?" + // 
				 "&IDENTE=" + profiloEnte.getCfCodiceProfiloPSP() + //
				 "&codiceAvviso=" + payPos.getCodiceAvviso() + //
				 "&tipologiaEntrata=" + codiceVersamento;
		    AnnullaAvvisoRespWrapper resp = apiClient.call(url, "DELETE", null, true, AnnullaAvvisoRespWrapper.class);
		    if ("00".equalsIgnoreCase(resp.getAnnullaAvvisoResp().getEsitoOperazione().getCodice())) {
			esito.setStato(pagatoOffline ? StatoPagamentoType.PAGATO_OFFLINE_ANNULLATO : StatoPagamentoType.ANNULLATO);
			esito.setMessaggio(resp.getAnnullaAvvisoResp().getEsitoOperazione().getDescrizione());
			esito.setEsito(true);
		    } else {
			esito.setIdPosizione(BigInteger.valueOf(payPos.getId().getCodice()));
			esito.setIdRegistrazioneContabile(BigInteger.valueOf(payPos.getRegistrazioneContabile().getId().getCodice()));
			esito.setEsito(false);
			esito.setStato(StatoPagamentoType.CON_ERRORE);
			esito.setMessaggio(resp.getAnnullaAvvisoResp().getEsitoOperazione().getCodice() + " - " +
					   resp.getAnnullaAvvisoResp().getEsitoOperazione().getDescrizione());
			log.error("Errore nell'annulamento dell'avviso: {}", payPos.getIdPosizionePsp());
		    }
		} else {
		    esito.setStato(pagatoOffline ? StatoPagamentoType.PAGATO_OFFLINE_ANNULLATO : StatoPagamentoType.ANNULLATO);
		    esito.setMessaggio("Posizione senza codice avviso annullata");
		    esito.setEsito(true);
		}
		result.getEsitoPosizione().add(esito);
	    }
	}
	return result;
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
	ApiClient apiClient = inizializzaClient();
	String msg = null;
	for (PayRegistrazioniContabili reg : cmd.getRegistrazioniPosizioni()) {
	    for (PayPosizioniDebitorie pos : reg.getPosizioniDebitorie()) {
		msg = "invocazione del servizio getPagamentoAttesoPdf in corso";
		log.debug("inviaAvvisiPagamento elaboro la posizione debitoria {}", pos.getId().getCodice());
		EsitoDocumentoPosizioneDebitoriaType esitoDoc = new EsitoDocumentoPosizioneDebitoriaType();
		PopolamentoDatiHelper.completaDatiDaPosizioneDebitoria(esitoDoc, pos,
			payPosizioniDebitorieService.findRiferimentiClientByPosizioneDebitoria(pos.getId().getCodice()));
		if (!StringUtils.isBlank(pos.getCodiceAvviso())) {
		    esitoDoc.setTipoDocumento(TipoDocumentoType.AVVISO);
		    esitoDoc.setIdPosizione(BigInteger.valueOf(pos.getId().getCodice()));
		    esitoDoc.setIdRegistrazioneContabile(BigInteger.valueOf(pos.getRegistrazioneContabile().getId().getCodice()));
		    log.debug("inviaAvvisiPagamento recupero il client per la posizione debitoria {}", pos.getId().getCodice());
		    try {
			log.debug("inviaAvvisiPagamento invoco il metodo dettaglioAvvisoDaIdFiscaleECodiceAvviso {}-{}-{}",
				ente.getCfCodiceProfiloPSP(), pos.getCodiceAvviso(), pos.getId().getCodice());
			//			String path = this.getWsAvvisoConfig().getEndpointUrl() +
			//				      "/MIP_DettaglioDebitoAvviso/avvisiPagamento/dettaglioAvvisoDaIdFiscaleECodiceAvviso?identificativoFiscale=" +
			//				      pos.getSoggettoDebitore().getCfPi() + "&codiceAvviso=" + pos.getCodiceAvviso() + "&ottieniPdf=" + true;
			String path = this.getWsAvvisoConfig().getEndpointUrl() + "/dettaglioAvvisoDaIdFiscaleECodiceAvviso?identificativoFiscale=" +
				      pos.getSoggettoDebitore().getCfPi() + "&codiceAvviso=" + pos.getCodiceAvviso() + "&ottieniPdf=" + true;
			AvvisoPagamento avvisoPagamento = apiClient.call(path, "GET", null, true, AvvisoPagamento.class);
			if (avvisoPagamento != null && avvisoPagamento.getPdfAvviso() != null) {
			    byte[] avviso = avvisoPagamento.getPdfAvviso().getFile();
			    String nomeFile = avvisoPagamento.getPdfAvviso().getNomeFile();
			    DataHandler dataHandler = new DataHandler(new InputStreamDataSource(new ByteArrayInputStream(avviso)));
			    esitoDoc.setEsito(true);
			    esitoDoc.setNomeDocumento(nomeFile);
			    esitoDoc.setDocumento(dataHandler);
			    esitoDoc.setTipoDocumento(TipoDocumentoType.AVVISO);
			    esitoDoc.setStatoDocumento(StatoDocumentoType.DISPONIBILE);
			} else {
			    esitoDoc.setEsito(false);
			    esitoDoc.setErroreTemporaneo(true);
			    esitoDoc.setTipoDocumento(TipoDocumentoType.AVVISO);
			    esitoDoc.setStatoDocumento(StatoDocumentoType.NON_DISPONIBILE);
			    msg = "Impossibile richiedere l'avviso di pagamento in quanto non è stato trovato avviso pagamento dal chiamata all'api";
			}
			log.debug("inviaAvvisiPagamento chiamata effettuata con successo per la posizione debitoria {}", pos.getId().getCodice());
		    } catch (Exception e) {
			this.handleException(e, esitoDoc);
			msg = "impossibile scaricare l'avviso PDF a causa dell'errore: " + e.toString();
			log.error("inviaAvvisiPagamento - {}", msg, e);
		    }
		} else {
		    esitoDoc.setEsito(false);
		    esitoDoc.setErroreTemporaneo(true);
		    esitoDoc.setTipoDocumento(TipoDocumentoType.AVVISO);
		    esitoDoc.setStatoDocumento(StatoDocumentoType.NON_DISPONIBILE);
		    msg = "Impossibile richiedere l'avviso di pagamento in quanto non è ancora presente il codice avviso per la posizione debitoria";
		}
		esitoDoc.setMessaggio(msg);
		log.info("inviaAvvisiPagamento - {}", msg);
		retEsiti.getEsitoPosizione().add(esitoDoc);
	    }
	}
	return retEsiti;
    }

    @Override
    public PaySessioniPagamento gestisciEsitoSessione(Map<String, String[]> reqParams) {

	// Auto-generated method stub
	return null;
    }

    @Override
    public void validateRichiestaInserimentoPosizioniDebitorie(RegistrazioneContabileType registrazioneContabile)
	    throws ValidazionePosizioniDebitorieException {

	// Auto-generated method stub
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
    public boolean supportaAvvisoPagamento() {

	return true;
    }

    private Map<String, ApiClient> mappaClient = new HashMap<>();

    private ApiClient inizializzaClient() throws PayConfigurationException {

	PayProfiliEntiCreditori profiloEnte = PayConfigurationHelper.getProfiloEnteCreditore();
	String keyClient = profiloEnte.getId().toString();
	ApiClient clientInizializzato = mappaClient.get(keyClient);
	log.debug("clientInizializzato {}-{}", mappaClient, clientInizializzato);
	if (clientInizializzato == null) {
	    log.debug("clientInizializzato è nullo");
	    PayConnectorWsEndpoint securityService = getWsSecurityConfig();
	    clientInizializzato = new ApiClient(securityService.getEndpointUrl(), securityService.getPassword(), securityService.getUtente(), null,
		    null, payConnectorConfigValuesService);
	}
	mappaClient.put(keyClient, clientInizializzato);
	return clientInizializzato;
    }

    private void popolaRichiediAvvisoDati(PayPosizioniDebitorie payPos, RichiediAvvisoDati avvisoDati) throws PayConfigurationException {

	PayProfiliEntiCreditori profiloEnte = PayConfigurationHelper.getProfiloEnteCreditore();
	avvisoDati.setIdEnte(profiloEnte.getCfCodiceProfiloPSP());
	avvisoDati.setUrLNotifica(profiloEnte.getUrlEsitoPagamento());
	avvisoDati.setFlagPDFRT("N");
	avvisoDati.setFlagPDFAvvisatura("N"); // Indica se si vuole ricevere nella risposta il PDF dell'Avviso di Pagamento (in base64). 
					      //Valori ammessi: S = presenza del PDF nella risposta, N = assenza del PDF nella risposta
	avvisoDati.setFlagEmailAvviso("N"); // invia la mail
	PaySoggettiDebitori soggettoDebitore = payPos.getSoggettoDebitore();
	DatiDebitore datiDebitore = new DatiDebitore();
	if (soggettoDebitore.getEmail() != null) {
	    datiDebitore.setEmail(soggettoDebitore.getEmail());
	}
	if (soggettoDebitore.getCfPi().length() == 16) {
	    datiDebitore.setTipoIdentificativo("F");
	    datiDebitore.setCognome(soggettoDebitore.getCognome());
	} else {
	    datiDebitore.setTipoIdentificativo("G");
	}
	datiDebitore.setIdentificativoUtente(soggettoDebitore.getCfPi());
	datiDebitore.setNome(soggettoDebitore.getNome());
	avvisoDati.setDatiDebitore(datiDebitore);
	DatiVersamento datiVersamento = popolaDatiVersamento(payPos);
	avvisoDati.setDatiVersamento(datiVersamento);
    }

    private DatiVersamento popolaDatiVersamento(PayPosizioniDebitorie payPos) throws PayConfigurationException {

	String idPosizionePsp = this.generaIdPosizioneDebitoria(payPos);
	payPos.setIdPosizionePsp(idPosizionePsp);
	DatiVersamento datiVersamento = new DatiVersamento();
	datiVersamento.setTipoPagamento(1);
	String codEntrata = posizioniDebitorieCommandService.findCodiceVersamentoFromPosizioneDebitoria(payPos);
	SimpleDateFormat dataScadenza = new SimpleDateFormat("yyyy-MM-dd");
	if (payPos.getDataScadenza() != null) {
	    datiVersamento.setDataScadenzaAvviso(dataScadenza.format(payPos.getDataScadenza()));
	    datiVersamento.setDataScadenzaPagamento(dataScadenza.format(payPos.getDataScadenza()));
	}
	datiVersamento.setDescrizionePagamento(payPos.getRegistrazioneContabile().getDescrizione());
	DatiSingoloVersamento datiSingoloVersamento = new DatiSingoloVersamento();
	Documento documento = new Documento();
	documento.setTipologiaEntrata(codEntrata);
	documento.setNumeroDocumento(idPosizionePsp);
	SimpleDateFormat annoDocumento = new SimpleDateFormat("yyyy");
	documento.setAnnoDocumento(annoDocumento.format(payPos.getDataRegistrazione()));
	ParametroRipartizioneImporti parametroRipartizioneImporti = new ParametroRipartizioneImporti();
	Collection<PayDettaglioImporti> dettagli = payPos.getDettagliImporto();
	if (dettagli == null || dettagli.isEmpty()) {
	    String msg = String.format("Nessun dettaglio importo per la posizione: %s", payPos.getId());
	    throw new PayConfigurationException(msg);
	}
	BigDecimal importoTotale = BigDecimal.ZERO;
	Map<Integer, String> ripartizioneCache = new HashMap<>();
	String parametroRicercato = parametroRipartizioneImporti.getNomeParametro();
	//DA RIVEDERE QUALE DESC CAUSALE PRENDE?
	datiSingoloVersamento.setCausale(payPos.getRegistrazioneContabile().getDescrizione());
	// TUTTI HANNO LA STESSA CODICE_ENTRATA -> CODICE_VERSAMENTO E DIVERSI ID CAUSALI(NELLA TABELLA PAY_REGISTRAZIONI_CAUSALI) ? 
	Map<String, Integer> mappaAccertamentiImporti = new HashMap<String, Integer>();
	for (PayDettaglioImporti dettaglio : dettagli) {
	    String ripartizione = risolviRipartizione(dettaglio, parametroRicercato, ripartizioneCache);
	    importoTotale = importoTotale.add(dettaglio.getImporto());
	    if (StringUtils.isBlank(ripartizione)) {
		log.debug("Ripartizione sottoconti non trovata per l'onere. Avvio logica generale.");
		aggiungiAllaMappa(dettaglio.getNumeroAccertamento(), centesimi(dettaglio.getImporto()), mappaAccertamentiImporti);
		continue;
	    }
	    log.debug("Ripartizione sottoconti trovata. Ripartizione: {}", ripartizione);
	    Map<String, List<BigDecimal>> contoImportiMap = ripartisciImportiPerSottoconti(ripartizione);
	    BigDecimal sommaRipartizioni = BigDecimal.ZERO;
	    for (Entry<String, List<BigDecimal>> entry : contoImportiMap.entrySet()) {
		if (StringUtils.isBlank(entry.getKey()))
		    continue;
		for (BigDecimal importoRipartizioneSingolo : entry.getValue()) {
		    aggiungiAllaMappa(entry.getKey(), centesimi(importoRipartizioneSingolo), mappaAccertamentiImporti);
		    // Voce sottoconto deve essere univoco se ci sono dei partizione con due voce si fa la somma
		    sommaRipartizioni = sommaRipartizioni.add(importoRipartizioneSingolo);
		}
	    }
	    if (sommaRipartizioni.compareTo(dettaglio.getImporto()) != 0) {
		String msg = String.format(
			"Importo onere non coerente: la somma delle ripartizioni sui sottoconti non corrisponde al totale. Posizione debitoria: %s & pay_dettaglio_importi: %s",
			payPos.getId(), dettaglio.getId());
		log.error(msg);
		throw new PayConfigurationException(msg);
	    }
	}
	documento.setImportiContabili(importiContabiliDallaMappa(mappaAccertamentiImporti));
	datiSingoloVersamento.setImporto(centesimi(importoTotale));
	datiSingoloVersamento.setDocumento(documento);
	datiVersamento.setImportoTotale(centesimi(importoTotale));
	datiSingoloVersamento.setStato(0);
	datiVersamento.setDatiSingoloVersamento(datiSingoloVersamento);
	return datiVersamento;
    }

    private List<ImportoContabileWrapper> importiContabiliDallaMappa(Map<String, Integer> mappaAccertamentiImporti) {

	List<ImportoContabileWrapper> importiContabili = new ArrayList<>();
	mappaAccertamentiImporti.forEach((k, v) -> {
	    log.info("importiContabiliDallaMappa==> Key: {}, Value: {}", k, v);
	    importiContabili.add(buildWrapper(k, v));
	});
	return importiContabili;
    }

    private void aggiungiAllaMappa(String numeroAccertamento, int valore, Map<String, Integer> mappaAccertamentiImporti) {

	String key = StringUtils.defaultString(numeroAccertamento, CONTO_NON_DEFINITO);
	log.debug("aggiungiAllaMappa {}-{} PRIMA {}", numeroAccertamento, valore, mappaAccertamentiImporti);
	Integer v = mappaAccertamentiImporti.get(key);
	if (v == null) {
	    v = Integer.valueOf(valore);
	} else {
	    v = v.intValue() + valore;
	}
	mappaAccertamentiImporti.put(key, v);
	log.debug("aggiungiAllaMappa {}-{} DOPO {}", numeroAccertamento, valore, mappaAccertamentiImporti);
    }

    private AggiornaAvvisoDati popolaAggiornaAvvisoDati(PayPosizioniDebitorie payPos, Date nuovaDataScadenza) throws PayConfigurationException {

	AggiornaAvvisoDati aggiornaAvvisoDati = new AggiornaAvvisoDati();
	PayProfiliEntiCreditori profiloEnte = PayConfigurationHelper.getProfiloEnteCreditore();
	aggiornaAvvisoDati.setIdEnte(profiloEnte.getCfCodiceProfiloPSP());
	aggiornaAvvisoDati.setCodiceAvviso(payPos.getCodiceAvviso());
	DatiVersamento datiVersamento = popolaDatiVersamento(payPos);
	DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
	String strDate = dateFormat.format(nuovaDataScadenza);
	datiVersamento.setDataScadenzaAvviso(strDate);
	aggiornaAvvisoDati.setDatiVersamento(datiVersamento);
	return aggiornaAvvisoDati;
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

    @Override
    public List<ServiziSchedulatiEnum> getListaServiziSchedulatiSupportati() {

	List<ServiziSchedulatiEnum> servizi = new ArrayList<>();
	servizi.add(ServiziSchedulatiEnum.CARICAMENTO_MASSIVO_POSIZIONI);
	return servizi;
    }

    @Override
    public EsitoElaborazione elaboraCaricamentoMassivoPosizioni(Map<String, String> params) {

	if (params.get(ConfigParamNames.SCHED_CARICAM_MASS_STRATEGIA.name())
		.equalsIgnoreCase(SCHED_CARICAMENTO_MASSIVO_STRATEGIA_ENUM.GENOVA_NEXI.name())) {
	    return caricamentoMassivoPosizioniNEXIGenovaService.elaboraCaricamentoMassivoPosizioni(params, this);
	}
	return new EsitoElaborazione(false, "Non implementato ancora");
    }

    private Map<String, List<BigDecimal>> ripartisciImportiPerSottoconti(String input) {

	Map<String, List<BigDecimal>> result = new LinkedHashMap<>();
	if (input == null || StringUtils.isBlank(input))
	    return result;
	for (String entry : input.split(";")) {
	    entry = entry.trim();
	    if (entry.isEmpty()) {
		continue;
	    }
	    String[] parts = entry.split("\\|\\|");
	    if (parts.length != 2) {
		throw new IllegalArgumentException("Invalid entry: " + entry);
	    }
	    String key = parts[0].trim();
	    String normalized = parts[1].trim().replace(".", "").replace(",", ".");
	    BigDecimal value = new BigDecimal(normalized);
	    result.computeIfAbsent(key, k -> new ArrayList<>()).add(value);
	}
	return result;
    }

    private static final String NO_RIPARTIZIONE = "";

    private String risolviRipartizione(PayDettaglioImporti dettaglio, String parametroRicercato, Map<Integer, String> cache) {

	if (dettaglio.getCausaleRegistrazione() == null || dettaglio.getCausaleRegistrazione().getId() == null
		|| dettaglio.getCausaleRegistrazione().getId().getCodice() == null) {
	    return null;
	}
	Integer codice = dettaglio.getCausaleRegistrazione().getId().getCodice();
	String cached = cache.computeIfAbsent(codice, c -> {
	    return payRegcausaliParametriService.findByCausale(c).stream().filter(p -> parametroRicercato.equals(p.getChiave()))
		    .map(PayRegcausaliParametri::getValore).findFirst().orElse(NO_RIPARTIZIONE);
	});
	return cached.isEmpty() ? null : cached;
    }

    private ImportoContabileWrapper buildWrapper(String identificativo, int valore) {

	ImportoContabile ic = new ImportoContabile();
	ic.setValore(valore);
	if (identificativo != null && !identificativo.equalsIgnoreCase(CONTO_NON_DEFINITO)) {
	    ic.setIdentificativo(identificativo);
	}
	ImportoContabileWrapper w = new ImportoContabileWrapper();
	w.setImportocontabile(ic);
	return w;
    }

    private static final BigDecimal CENTS_MULTIPLIER = BigDecimal.valueOf(100);

    private static int centesimi(BigDecimal euri) {

	return euri.multiply(CENTS_MULTIPLIER).intValueExact();
    }
}
