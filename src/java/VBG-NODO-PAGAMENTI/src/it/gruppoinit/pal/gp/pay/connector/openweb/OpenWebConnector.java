package it.gruppoinit.pal.gp.pay.connector.openweb;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.activation.DataHandler;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.transport.http.HTTPConduit;
import org.eclipse.persistence.jaxb.UnmarshallerProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.codec.Base64;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.exceptions.ValidazionePosizioniDebitorieException;
import it.gruppoinit.pal.gp.core.utils.IOUtils;
import it.gruppoinit.pal.gp.core.utils.InputStreamDataSource;
import it.gruppoinit.pal.gp.core.utils.JSONUtils;
import it.gruppoinit.pal.gp.core.utils.OAuth2UserNamePasswordSecurityRestTokenManager;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.pal.gp.pay.command.PosizioniDebitorieCommand;
import it.gruppoinit.pal.gp.pay.command.RichiestaSuListaPosizioniCommand;
import it.gruppoinit.pal.gp.pay.connector.AbstractPayConnector;
import it.gruppoinit.pal.gp.pay.connector.IPayConnector;
import it.gruppoinit.pal.gp.pay.connector.govpay.rest.model.v2.FaultBean;
import it.gruppoinit.pal.gp.pay.connector.openweb.model.DettaglioPosizione;
import it.gruppoinit.pal.gp.pay.connector.openweb.model.Dovuto;
import it.gruppoinit.pal.gp.pay.connector.openweb.model.Rata;
import it.gruppoinit.pal.gp.pay.connector.openweb.model.SoggettoPagatore;
import it.gruppoinit.pal.gp.pay.connector.openweb.model.esito.DatiPagamento;
import it.gruppoinit.pal.gp.pay.connector.openweb.model.esito.EsitiRata;
import it.gruppoinit.pal.gp.pay.connector.openweb.model.esito.EsitiRate;
import it.gruppoinit.pal.gp.pay.connector.openweb.model.esito.EsitoAvviaPagamento;
import it.gruppoinit.pal.gp.pay.connector.openweb.model.esito.EsitoCancellazione;
import it.gruppoinit.pal.gp.pay.connector.openweb.model.esito.EsitoListaPagamenti;
import it.gruppoinit.pal.gp.pay.connector.openweb.model.esito.EsitoModifica;
import it.gruppoinit.pal.gp.pay.connector.openweb.model.esito.EsitoMultiDovuto;
import it.gruppoinit.pal.gp.pay.connector.openweb.model.esito.EsitoVerificaPagamento;
import it.gruppoinit.pal.gp.pay.connector.openweb.model.esito.StatoPagamentoOpenWebEnum;
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
import it.gruppoinit.pal.gp.pay.service.PayConnectorConfigValuesService;
import it.gruppoinit.pal.gp.pay.service.PayPosizioniDebitorieService;
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
import it.gruppoinit.pal.gp.pay.ws.schema.HttpMethodType;
import it.gruppoinit.pal.gp.pay.ws.schema.ImportoPagamentoType;
import it.gruppoinit.pal.gp.pay.ws.schema.PosizioneDebitoriaType;
import it.gruppoinit.pal.gp.pay.ws.schema.RegistrazioneContabileType;
import it.gruppoinit.pal.gp.pay.ws.schema.SoggettoDebitoreType;
import it.gruppoinit.pal.gp.pay.ws.schema.StatoDocumentoType;
import it.gruppoinit.pal.gp.pay.ws.schema.StatoPagamentoType;
import it.gruppoinit.pal.gp.pay.ws.schema.StatoPosizioneType;
import it.gruppoinit.pal.gp.pay.ws.schema.TipoDocumentoType;

public class OpenWebConnector extends AbstractPayConnector implements IPayConnector {

    private static final Logger log = LoggerFactory.getLogger(OpenWebConnector.class);
    private static final int CONNECTION_TIMEOUT = 12000;
    @Autowired
    private PayConnectorConfigValuesService payConnectorConfigValuesService;
    @Autowired
    private PayStatoPagamentiService payStatoPagamentiService;
    @Autowired
    private PayPosizioniDebitorieService payPosizioniDebitorieService;
    @Autowired
    private PaySessioniPagamentoService paySessioniPagamentoService;
    @Autowired
    private PosizioniDebitorieCommandService posizioniDebitorieCommandService;

    @Override
    public ElencoPosizioniDebitorieEsitoType registraPosizioniDebitorie(PosizioniDebitorieCommand datiRegistrazioniCommand) throws PayException {

	log.debug("Caricamento posizioni debitorie begin");
	ElencoPosizioniDebitorieEsitoType result = new ElencoPosizioniDebitorieEsitoType();
	for (PayRegistrazioniContabili payReg : datiRegistrazioniCommand.getRegistrazioniPosizioni()) {
	    log.debug("Caricamento posizioni debitorie registrazione contabile {} ", payReg.getId());
	    try {
		DettaglioPosizione dp = fromRegistrazione(payReg);
		log.debug("Caricamento posizioni debitorie {} prima di invocare il servizio remoto", payReg.getId());
		EsitoMultiDovuto risposta = registraPosizioneSuOpenWeb(dp, false, getNomeFlusso(payReg, false));
		log.debug("Caricamento posizioni debitorie {} servizio remoto invocato. esito {}", payReg.getId(), risposta);
		log.debug("risposta: esito {}, errore {}, idFlusso {}, nomeflusso {}, contenutoJson {}", new Object[] { risposta.getEsito(),
			risposta.getErrore(), risposta.getIdFlusso(), risposta.getNomeFlusso(), risposta.getContentJson() });
		if (risposta.getEsito().equalsIgnoreCase("ko")) {
		    throw new PayException("Errore nell'invio al sistema OpenWeb: " + risposta.getErrore());
		}
		List<EsitiRate> esitiRate = unmarshalEsitiRate(risposta.getContentJson());
		for (EsitiRate esitiRata : esitiRate) {
		    List<EsitiRata> esiti = esitiRata.getEsitiRate();
		    for (EsitiRata e : esiti) {
			String rata = e.getRata();
			String iuv = e.getIdUnivocoVersamento();
			String numRata = rata.equalsIgnoreCase("U") ? "1" : rata;
			PayPosizioniDebitorie payPos = recuperaPosizioneDaRata(payReg.getPosizioniDebitorie(), numRata);
			EsitoOperazionePosizioneDebitoriaType esitoPos = new EsitoOperazionePosizioneDebitoriaType();
			PopolamentoDatiHelper.completaDatiDaPosizioneDebitoria(esitoPos, payPos,
				payPosizioniDebitorieService.findRiferimentiClientByPosizioneDebitoria(payPos.getId().getCodice()));
			// interpretare le risposte
			esitoPos.setIUV(iuv);
			esitoPos.setCodiceAvviso(e.getNumeroAvviso());
			esitoPos.setQrCode(e.getQrCode());
			esitoPos.setEsito(true);
			esitoPos.setStato(StatoPagamentoType.ATTIVATO_IN_PSP);
			result.getEsitoPosizione().add(esitoPos);
		    }
		}
	    } catch (Exception e) {
		log.error("Errore nella creazione delle posizioni debitorie", e);
		for (PayPosizioniDebitorie payPos : payReg.getPosizioniDebitorie()) {
		    EsitoOperazionePosizioneDebitoriaType esitoPos = new EsitoOperazionePosizioneDebitoriaType();
		    PopolamentoDatiHelper.completaDatiDaPosizioneDebitoria(esitoPos, payPos,
			    payPosizioniDebitorieService.findRiferimentiClientByPosizioneDebitoria(payPos.getId().getCodice()));
		    esitoPos.setStato(StatoPagamentoType.CON_ERRORE);
		    this.handleException(e, esitoPos);
		    result.getEsitoPosizione().add(esitoPos);
		}
	    }
	}
	log.debug("Caricamento posizioni debitorie end");
	return result;
    }

    private String getNomeFlusso(PayRegistrazioniContabili payReg, boolean isOTF) {

	if (isOTF) {
	    return "0";// 1. usare come nome flusso lo "0" così mi segnali che avvierai il pagamento a breve 
	    //e io non faccio altre operazioni tra cui ad esempio la notifica su appIO qualora ci sia il connettore attivo
	}
	return payReg.getId().getIdcomune() + "-" + payReg.getId().getCodice();
    }

    private PayPosizioniDebitorie recuperaPosizioneDaRata(Set<PayPosizioniDebitorie> posizioniDebitorie, String numRata) throws PayException {

	for (PayPosizioniDebitorie payPosizioniDebitorie : posizioniDebitorie) {
	    if (numRata.equalsIgnoreCase(String.valueOf(payPosizioniDebitorie.getNumRata()))) {
		return payPosizioniDebitorie;
	    }
	}
	throw new PayException("Non è stato possibili individuare la posizione debitoria con rata " + numRata);
    }

    private EsitoMultiDovuto registraPosizioneSuOpenWeb(DettaglioPosizione dp, boolean isOTF, String nomeFlusso) throws PayException {

	WebClient client = getClient(getWsCaricamentoConfig(), "invia_multidovuto");
	Form f = new Form() //
		.param("applicazione", getNomeApplicazione()) //
		.param("numero", String.valueOf(dp.getRate().size())) // SEMPRE UN PAGAMENTO per registrazione contabile
		.param("nome_flusso", nomeFlusso) // Valorizzare a “0” perchiamate con flussoa singolo pagamento(APP)
		.param("caricamento_da_confermare", "false") //
		// .param("cancellabile", isOTF ? "1" : "0") // Saccavini meglio non implementarlo di default sempre cancellabile = 0 ovvero l'utente non lo può cancellare dal carrello PAGOPA
		.param("content_json", contentJSONZIPBase64(dp)); //
	Response response = client.post(f);
	Integer status = response.getStatus();
	if (status.equals(200) || status.equals(201)) {
	    log.debug("{}-{}", status, response.getStatusInfo().getReasonPhrase());
	    log.debug("url ws [registraPosizioneSuOpenWeb]: {} ", client.getCurrentURI());
	    InputStream is = ((InputStream) response.getEntity());
	    try {
		JAXBContext jc = JAXBContext.newInstance(EsitoMultiDovuto.class);
		Unmarshaller unmarshaller = jc.createUnmarshaller();
		unmarshaller.setProperty(UnmarshallerProperties.MEDIA_TYPE, MediaType.APPLICATION_JSON);
		unmarshaller.setProperty(UnmarshallerProperties.JSON_INCLUDE_ROOT, false);
		return unmarshaller.unmarshal(new StreamSource(is), EsitoMultiDovuto.class).getValue();
	    } catch (JAXBException e) {
		log.debug("[registraPosizioneSuOpenWeb] Errore nel umarshalling: " + e.getMessage(), e);
		throw new PayException(e);
	    }
	} else {
	    log.debug("uri ws [registraPosizioneSuOpenWeb]: {} ", client.getCurrentURI());
	    gestisciErroreHttp(response, "avvioPagament");
	}
	throw new PayException("Errore nella registrazione della posizione debitoria ");
    }

    private String unzipToString(String base64String) throws IOException {

	ByteArrayInputStream in = new ByteArrayInputStream(Base64.decode(base64String.getBytes()));
	ZipInputStream zis = new ZipInputStream(new BufferedInputStream(in));
	ByteArrayOutputStream baos = new ByteArrayOutputStream();
	byte[] buffer = new byte[1024];
	int read = 0;
	ZipEntry entry = null;
	while ((entry = zis.getNextEntry()) != null) {
	    while ((read = zis.read(buffer, 0, buffer.length)) > 0) {
		baos.write(buffer, 0, read);
	    }
	}
	return baos.toString();
    }

    @SuppressWarnings("unchecked")
    private List<EsitiRate> unmarshalEsitiRate(String contentJson) throws IOException, JAXBException {

	String contenuto = unzipToString(contentJson);
	log.debug("unmarshalEsitiRate contenuto {}", contenuto);
	return (List<EsitiRate>) JSONUtils.unmarshal(EsitiRate.class, contenuto, false);
    }

    private String contentJSONZIPBase64(DettaglioPosizione dp) throws PayException {

	String ret;
	try {
	    ret = JSONUtils.marshal(dp, false);
	    log.debug("DettaglioPosizione {}", ret);
	    ret = IOUtils.compressToBase64(ret, "content_json");
	    log.debug("contentJSONZIPBase64 {}", ret);
	    return ret;
	} catch (JAXBException | IOException e) {
	    throw new PayException(e);
	}
    }

    private DettaglioPosizione fromRegistrazione(PayRegistrazioniContabili payReg) {

	Set<PayPosizioniDebitorie> posizioniDebitorie = payReg.getPosizioniDebitorie();
	PaySoggettiDebitori s = null;
	for (PayPosizioniDebitorie payPos : posizioniDebitorie) {
	    s = payPos.getSoggettoDebitore();
	}
	DettaglioPosizione dp = new DettaglioPosizione();
	log.debug("Popolo soggetto pagatore");
	SoggettoPagatore p = fromSoggettoDebitore(s);
	log.debug("Popolato soggetto pagatore {}", p);
	dp.setPagatore(p);
	setRateFromPosizioniDebitorie(payReg.getPosizioniDebitorie(), dp);
	return dp;
    }

    private void setRateFromPosizioniDebitorie(Set<PayPosizioniDebitorie> posizioniDebitorie, DettaglioPosizione dp) {

	log.debug("setRateFromPosizioniDebitorie ");
	// tipo rata == U per rata unica
	// 1,2,3,4,5
	boolean isRataUnica = posizioniDebitorie.size() == 1;
	for (PayPosizioniDebitorie payPos : posizioniDebitorie) {
	    Rata r = new Rata();
	    log.debug("Popolo rata {} - popolo scadenza ", payPos.getNumRata());
	    // r.setIdUnivocoVersamento("-");
	    r.setScadenza(toAAAAMMGGDate(payPos.getDataScadenza()));
	    if (isRataUnica) {
		r.setTipoRata("U");
	    } else {
		r.setTipoRata(String.valueOf(payPos.getNumRata()));
	    }
	    log.debug("Popolo rata {} - tipo rata {}", payPos.getNumRata(), r.getTipoRata());
	    popolaDovuti(payPos, r);
	    dp.getRate().add(r);
	}
    }

    private void popolaDovuti(PayPosizioniDebitorie payPos, Rata r) {

	Set<PayDettaglioImporti> dettagliImporto = payPos.getDettagliImporto();
	for (PayDettaglioImporti pd : dettagliImporto) {
	    log.debug("Popolo dovuti rata {} ", payPos.getNumRata());
	    Dovuto d = new Dovuto();
	    d.setAccertamento(pd.getNumeroAccertamento());
	    log.debug("Popolo dovuti rata {} - accertamento", payPos.getNumRata());
	    if (pd.getAnnoAccertamento() != null) {
		d.setAnnoCompetenza(pd.getAnnoAccertamento());
		log.debug("Popolo dovuti rata {} - anno accertamento", payPos.getNumRata());
	    }
	    d.setImporto(pd.getImporto().doubleValue());
	    log.debug("Popolo dovuti rata {} - importo", payPos.getNumRata());
	    d.setCausale(StringUtils.left(payPos.getRegistrazioneContabile().getDescrizione(), 180));
	    log.debug("Popolo dovuti rata {} - causale", payPos.getNumRata());
	    d.setTipoDovuto(pd.getDatiRiscossione());
	    log.debug("Popolo dovuti rata {} - dati riscossione", payPos.getNumRata());
	    if (payPos.getIdPosizionePsp() == null) {
		payPos.setIdPosizionePsp(this.generaIdPosizioneDebitoria(payPos));
	    }
	    d.setIdUnivocoDovuto(payPos.getIdPosizionePsp());
	    log.debug("Popolo dovuti rata {} - idUnivoco ", payPos.getNumRata());
	    r.getDovuti().add(d);
	}
    }

    private static String toAAAAMMGGDate(Date dataScadenza) {

	if (dataScadenza == null) {
	    return null;
	}
	return Utilities.formatDateWithPattern(dataScadenza, "yyyy-MM-dd");
    }

    private SoggettoPagatore fromSoggettoDebitore(PaySoggettiDebitori s) {

	SoggettoPagatore ret = new SoggettoPagatore();
	ret.setCf(s.getCfPi());
	if (StringUtils.isNotBlank(s.getNome()) && StringUtils.isNotBlank(s.getCognome())) {
	    ret.setNome(s.getNome());
	    ret.setCognome(s.getCognome());
	} else {
	    ret.setCognome(s.getNome());
	}
	ret.setTipoPersona(determinaTipoPersona(s.getCfPi()));
	ret.setEmail(s.getEmail());
	return ret;
    }

    @Override
    public AttivaPagamentoOnTheFlyResponseType attivaPagamentoOnTheFly(PosizioniDebitorieCommand cmd) throws PayException {

	AttivaPagamentoOnTheFlyResponseType result = new AttivaPagamentoOnTheFlyResponseType();
	EsitoOperazionePosizioneDebitoriaType esitoKO = new EsitoOperazionePosizioneDebitoriaType();
	AttivaSessionePagamentoResponseType attivaSessioneOTF = new AttivaSessionePagamentoResponseType();
	List<EsitoOperazionePosizioneDebitoriaType> esiti = new ArrayList<>();
	result.setSessionePagamento(attivaSessioneOTF);
	List<PayRegistrazioniContabili> payRegistrazioniContabilis = cmd.getRegistrazioniPosizioni();
	List<PayPosizioniDebitorie> payPosizioniDebitorie = new ArrayList<>();
	// va creato uno IUV e poi avviato il pagamento per quello IUV
	// 1. REGISTRARE COME FATTO CON registraPosizioneSuOpenWeb
	// 2. REGISTRARE COME FATTO CON registraPosizioneSuOpenWeb (ATTENZIONE FA FATTO UN SOLO IUV ANCHE SE LE POSIZIONI SONO PIU'
	// 3. RECUPERARE LO IUV  e passarlo ad attivaPagamentoOnTheFlyOpenWeb
	PaySoggettiDebitori s = null;
	for (PayRegistrazioniContabili prc : payRegistrazioniContabilis) {
	    payPosizioniDebitorie.addAll(prc.getPosizioniDebitorie());
	}
	String urlEsitoPagamento = null;
	for (PayPosizioniDebitorie pd : payPosizioniDebitorie) {
	    if (s == null) {
		s = pd.getSoggettoDebitore();
	    }
	    if (StringUtils.isBlank(urlEsitoPagamento)) {
		urlEsitoPagamento = pd.getProfiloEnte().getUrlEsitoPagamento();
	    }
	}
	try {
	    String idSessione = ORMHelper.getIdcomune() + "_" + UUID.randomUUID().toString();
	    urlEsitoPagamento += "?idSessione=" + idSessione;
	    DettaglioPosizione dp = popolaPosizioniPerOTF(payPosizioniDebitorie, idSessione);
	    EsitoMultiDovuto risposta = registraPosizioneSuOpenWeb(dp, true, "0");
	    log.debug("Caricamento posizioni debitorie OTF servizio remoto invocato risposta {}", risposta);
	    if (risposta.getEsito().equalsIgnoreCase("ko")) {
		throw new PayException("Errore nell'invio al sistema OpenWeb: " + risposta.getErrore());
	    }
	    String iuv = null;
	    String codiceAvviso = null;
	    List<EsitiRate> esitiR = unmarshalEsitiRate(risposta.getContentJson());
	    // La rata è sempre una
	    for (EsitiRate esitiRata : esitiR) {
		List<EsitiRata> esitiRate = esitiRata.getEsitiRate();
		for (EsitiRata e : esitiRate) {
		    iuv = e.getIdUnivocoVersamento();
		    codiceAvviso = e.getNumeroAvviso();
		}
	    }
	    EsitoAvviaPagamento pagamentoImmediato = attivaPagamentoOnTheFlyOpenWeb(iuv, urlEsitoPagamento, fromSoggettoDebitore(s), true);
	    log.debug("pagamentoImmediato: esito {}", pagamentoImmediato);
	    if (!pagamentoImmediato.getEsito().equalsIgnoreCase("ok")) {
		throw new PayException("Errore nella creazione del pagamento immediato: " + pagamentoImmediato.getErrore());
	    }
	    attivaSessioneOTF.setPayUrl(pagamentoImmediato.getUrlRedirect());
	    attivaSessioneOTF.setEsito(true);
	    attivaSessioneOTF.setIdSessione(idSessione);
	    attivaSessioneOTF.setHttpMethodRequired(HttpMethodType.GET);
	    for (PayPosizioniDebitorie pd : payPosizioniDebitorie) {
		EsitoOperazionePosizioneDebitoriaType esitoPos = new EsitoOperazionePosizioneDebitoriaType();
		PopolamentoDatiHelper.completaDatiDaPosizioneDebitoria(esitoPos, pd,
			payPosizioniDebitorieService.findRiferimentiClientByPosizioneDebitoria(pd.getId().getCodice()));
		esitoPos.setEsito(true);
		esitoPos.setStato(StatoPagamentoType.ATTIVATO_IN_PSP);
		esitoPos.setMessaggio("Posizione OTF creata con successo");
		esitoPos.setIUV(iuv + univocitaIUVOTF(pd));
		esitoPos.setCodiceAvviso(codiceAvviso);
		esiti.add(esitoPos);
	    }
	} catch (Exception e) {
	    log.error("Errore nell'attivazione del pagamento OTF", e);
	    attivaSessioneOTF.setEsito(false);
	    attivaSessioneOTF.setDescEsito("Errore nel creazione della posizione OTF");
	    for (PayPosizioniDebitorie pd : payPosizioniDebitorie) {
		esitoKO.setEsito(false);
		this.handleException(e, esitoKO);
		PopolamentoDatiHelper.completaDatiDaPosizioneDebitoria(esitoKO, pd,
			payPosizioniDebitorieService.findRiferimentiClientByPosizioneDebitoria(pd.getId().getCodice()));
		esiti.add(esitoKO);
	    }
	}
	result.getPosizioneInserita().addAll(esiti);
	return result;
    }

    private DettaglioPosizione popolaPosizioniPerOTF(List<PayPosizioniDebitorie> posizioniDebitorie, String idSessione) {

	PaySoggettiDebitori s = null;
	for (PayPosizioniDebitorie payPos : posizioniDebitorie) {
	    s = payPos.getSoggettoDebitore();
	}
	DettaglioPosizione dp = new DettaglioPosizione();
	SoggettoPagatore p = fromSoggettoDebitore(s);
	dp.setPagatore(p);
	setRateFromPosizioniDebitorieOTF(posizioniDebitorie, dp);
	return dp;
    }

    private void setRateFromPosizioniDebitorieOTF(List<PayPosizioniDebitorie> posizioniDebitorie, DettaglioPosizione dp) {

	Rata r = new Rata();
	Date dataScadenza = null;
	r.setTipoRata("U");
	String idPosizionePsp = null;
	for (PayPosizioniDebitorie payPos : posizioniDebitorie) {
	    if (StringUtils.isBlank(idPosizionePsp)) {
		idPosizionePsp = this.generaIdPosizioneDebitoria(payPos);
	    }
	    payPos.setIdPosizionePsp(idPosizionePsp);
	    dataScadenza = payPos.getDataScadenza();
	    popolaDovuti(payPos, r);
	}
	r.setScadenza(toAAAAMMGGDate(dataScadenza));
	dp.getRate().add(r);
    }

    private EsitoAvviaPagamento attivaPagamentoOnTheFlyOpenWeb(String iuv, String urlEsitoPagamento, SoggettoPagatore soggetto, boolean isOTF)
	    throws PayException {

	WebClient client = getClient(getWsAttivaSessioneConfig(), "avvia_pagamento");
	Form f = new Form() //
		.param("applicazione", getNomeApplicazione()) //
		.param("iuv", iuv) //
		.param("url_ok", urlEsitoPagamento) // 
		.param("url_ko", urlEsitoPagamento) //
		.param("sso", "0") //
		.param("trash_pag", isOTF ? "1" : "0") //
		.param("tipo_persona", soggetto.getTipoPersona()) //
		.param("cf", soggetto.getCf()) //
		.param("cognome", soggetto.getCognome()) //
		.param("email", soggetto.getEmail());
	if (StringUtils.isNotBlank(soggetto.getNome())) {
	    f.param("nome", soggetto.getNome());
	}
	if (log.isDebugEnabled()) {
	    log.debug("attivaPagamentoOnTheFlyOpenWeb: applicazione {},iuv {},url_ok {},url_ko, {},sso {},trash_pag {}," +
		      "tipo_persona {},cf {},cognome {},email {},nome {}",
		    new Object[] { getNomeApplicazione(), iuv, urlEsitoPagamento, urlEsitoPagamento, "0", isOTF ? "1" : "0",
			    soggetto.getTipoPersona(), soggetto.getCf(), soggetto.getCognome(), soggetto.getEmail(), soggetto.getNome() });
	}
	Response response = client.post(f);
	Integer status = response.getStatus();
	if (status.equals(200) || status.equals(201)) {
	    log.debug("{}", status, response.getStatusInfo().getReasonPhrase());
	    log.debug("url ws [attivaPagamentoOnTheFlyOpenWeb]: {} ", client.getCurrentURI());
	    InputStream is = ((InputStream) response.getEntity());
	    try {
		JAXBContext jc = JAXBContext.newInstance(EsitoAvviaPagamento.class);
		Unmarshaller unmarshaller = jc.createUnmarshaller();
		unmarshaller.setProperty(UnmarshallerProperties.MEDIA_TYPE, MediaType.APPLICATION_JSON);
		unmarshaller.setProperty(UnmarshallerProperties.JSON_INCLUDE_ROOT, false);
		return unmarshaller.unmarshal(new StreamSource(is), EsitoAvviaPagamento.class).getValue();
	    } catch (JAXBException e) {
		log.debug("[attivaPagamentoOnTheFlyOpenWeb] Errore nel umarshalling: {}", e.getMessage());
		throw new PayException(e);
	    }
	} else {
	    log.debug("uri ws [attivaPagamentoOnTheFlyOpenWeb]: {} ", client.getCurrentURI());
	    gestisciErroreHttp(response, "attivaPagamentoOnTheFlyOpenWeb");
	}
	throw new PayException("Errore nella avvio pagamento della posizione debitoria ");
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
		msg = "invocazione del servizio getPagamentoAttesoPdf in corso";
		log.debug("inviaAvvisiPagamento elaboro la posizione debitoria {}", pos.getId().getCodice());
		EsitoDocumentoPosizioneDebitoriaType esitoDoc = new EsitoDocumentoPosizioneDebitoriaType();
		PopolamentoDatiHelper.completaDatiDaPosizioneDebitoria(esitoDoc, pos,
			payPosizioniDebitorieService.findRiferimentiClientByPosizioneDebitoria(pos.getId().getCodice()));
		if (!StringUtils.isBlank(pos.getIuv())) {
		    esitoDoc.setTipoDocumento(TipoDocumentoType.AVVISO);
		    esitoDoc.setStatoDocumento(StatoDocumentoType.DISPONIBILE);
		    log.debug("inviaAvvisiPagamento recupero il client per la posizione debitoria {}", pos.getId().getCodice());
		    try {
			log.debug("inviaAvvisiPagamento invoco il metodo getAvviso {}-{}-{}", ente.getCfCodiceProfiloPSP(), pos.getCodiceAvviso(),
				pos.getId().getCodice());
			InputStream avviso = scaricaAvvisoOpenWeb(pos.getIuv());
			log.debug("inviaAvvisiPagamento chiamata effettuata con successo per la posizione debitoria {}", pos.getId().getCodice());
			esitoDoc.setEsito(true);
			esitoDoc.setNomeDocumento("AVVISO_" + pos.getIuv() + ".pdf");
			esitoDoc.setDocumento(new DataHandler(new InputStreamDataSource(avviso)));
		    } catch (PayException e) {
			this.handleException(e, esitoDoc);
			esitoDoc.setStatoDocumento(StatoDocumentoType.NON_DISPONIBILE);
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

    private InputStream scaricaAvvisoOpenWeb(String iuv) throws PayException {

	WebClient client = getClient(getWsAttivaSessioneConfig(), "scarica_avviso");
	Form f = new Form() //
		.param("applicazione", getNomeApplicazione()) //
		.param("iuv", iuv);
	Response response = client.post(f);
	Integer status = response.getStatus();
	if (status.equals(200) || status.equals(201)) {
	    log.debug("{}", status, response.getStatusInfo().getReasonPhrase());
	    log.debug("url ws [scaricaAvvisoOpenWeb]: {} ", client.getCurrentURI());
	    return ((InputStream) response.getEntity());
	} else {
	    log.debug("uri ws [scaricaAvvisoOpenWeb]: {} ", client.getCurrentURI());
	    gestisciErroreHttp(response, "scaricaAvvisoOpenWeb");
	}
	throw new PayException("Errore nel download avviso della posizione debitoria con iuv: " + iuv);
    }

    @Override
    public ElencoPosizioniDebitorieEsitoType annullaPosizioniDebitorie(PosizioniDebitorieCommand datiRegistrazioniCommand, boolean pagatoOffline)
	    throws PayException {

	log.debug("annullaPosizioniDebitorie begin");
	ElencoPosizioniDebitorieEsitoType result = new ElencoPosizioniDebitorieEsitoType();
	for (PayRegistrazioniContabili payReg : datiRegistrazioniCommand.getRegistrazioniPosizioni()) {
	    for (PayPosizioniDebitorie payPos : payReg.getPosizioniDebitorie()) {
		log.debug("chiedo annullamento per posizione debitoria {}, pagataOFFLine{}", payPos.getId(), pagatoOffline);
		EsitoOperazionePosizioneDebitoriaType esito = new EsitoOperazionePosizioneDebitoriaType();
		esito.setIdPosizione(BigInteger.valueOf(payPos.getId().getCodice()));
		PayStatoPagamenti payStato = this.payStatoPagamentiService.getStatoPosizioneDebitoria(payPos);
		esito.setStato(StatoPagamentoType.fromValue(payStato.getStato()));
		log.debug("stato attuale della posizione debitoria {}={}", payPos.getId(), esito.getStato());
		if (BooleanUtils.isFalse(payPos.getFlagOTF())) {
		    try {
			EsitoCancellazione esitoCancellazione = annullaPosizioneSuOpenWeb(payPos);
			log.debug("Prima di chiamare ws annulla per la posizione debitoria {}", payPos.getId());
			String esitoOperazione = esitoCancellazione.getEsito();
			log.debug("Esito ws annulla per la posizione debitoria {}={}", payPos.getId(), esitoOperazione);
			if (!esitoOperazione.equalsIgnoreCase("ok")) {
			    // gestire caso di errore
			    StringBuilder messaggioErrore = new StringBuilder("Errore nella cancellazione della posizione debitoria ") //
				    .append("[")//
				    .append(payPos.getId())//
				    .append("]: ")//
				    .append(esitoCancellazione.getErrore());
			    throw new PayException(messaggioErrore.toString());
			}
			// interpretare le risposte
			esito.setStato(pagatoOffline ? StatoPagamentoType.PAGATO_OFFLINE_ANNULLATO : StatoPagamentoType.ANNULLATO);
			esito.setEsito(true);
			PopolamentoDatiHelper.completaDatiDaPosizioneDebitoria(esito, payPos,
				payPosizioniDebitorieService.findRiferimentiClientByPosizioneDebitoria(payPos.getId().getCodice()));
		    } catch (Exception e) {
			this.handleException(e, esito);
		    }
		} else {
		    // possibile solo per modello3 ovvero posizioni non OTF
		    esito.setStato(StatoPagamentoType.ANNULLATO);
		    esito.setEsito(true);
		}
		result.getEsitoPosizione().add(esito);
	    }
	}
	return result;
    }

    private EsitoCancellazione annullaPosizioneSuOpenWeb(PayPosizioniDebitorie payPos) throws PayException {

	WebClient client = getClient(getWsCaricamentoConfig(), "cancella_dovuto");
	String codiceVersamento = posizioniDebitorieCommandService.findCodiceVersamentoFromPosizioneDebitoria(payPos);
	Form f = new Form() //
		.param("applicazione", getNomeApplicazione()) //
		.param("tipo_dovuto", codiceVersamento) //
		.param("id_univoco_dovuto", payPos.getIdPosizionePsp())//
		.param("iuv", payPos.getIuv()) //
	;// Permette di rendere cancellabile il dovutoda parte dell’utente dal carrello
	Response response = client.post(f);
	Integer status = response.getStatus();
	if (status.equals(200) || status.equals(201)) {
	    log.debug("{}", status, response.getStatusInfo().getReasonPhrase());
	    log.debug("url ws [annullaPosizioneSuOpenWeb]: {} ", client.getCurrentURI());
	    InputStream is = ((InputStream) response.getEntity());
	    try {
		JAXBContext jc = JAXBContext.newInstance(EsitoCancellazione.class);
		Unmarshaller unmarshaller = jc.createUnmarshaller();
		unmarshaller.setProperty(UnmarshallerProperties.MEDIA_TYPE, MediaType.APPLICATION_JSON);
		unmarshaller.setProperty(UnmarshallerProperties.JSON_INCLUDE_ROOT, false);
		return unmarshaller.unmarshal(new StreamSource(is), EsitoCancellazione.class).getValue();
	    } catch (JAXBException e) {
		log.debug("[annullaPosizioneSuOpenWeb] Errore nel umarshalling: {}", e.getMessage());
		throw new PayException(e);
	    }
	} else {
	    log.debug("uri ws [registraPosizioneSuOpenWeb]: {} ", client.getCurrentURI());
	    gestisciErroreHttp(response, "avvioPagament");
	}
	throw new PayException("Errore nella cancellazione della posizione debitoria " + payPos.getId());
    }

    @Override
    public ElencoStatoPosizioniType verificaStatoPagamenti(RichiestaSuListaPosizioniCommand cmd) throws PayException {

	ElencoStatoPosizioniType result = new ElencoStatoPosizioniType();
	for (PayPosizioniDebitorie payPos : cmd.getPosizioni()) {
	    log.debug("Verifica stato pos {}", payPos.getId());
	    StatoPosizioneType retStatus = new StatoPosizioneType();
	    retStatus.setEsito(true);
	    PopolamentoDatiHelper.completaDatiDaPosizioneDebitoria(retStatus, payPos,
		    payPosizioniDebitorieService.findRiferimentiClientByPosizioneDebitoria(payPos.getId().getCodice()));
	    log.debug("Verifica stato pos {} dati recuperati", payPos.getId());
	    try {
		EsitoVerificaPagamento statoPagamento = null;
		statoPagamento = verificaPagamento(payPos);
		log.debug("esito pagamento posizione {} - {}", payPos.getId(), statoPagamento);
		if (statoPagamento != null) {
		    gestisciStatoPagamento(payPos, statoPagamento, retStatus);
		}
	    } catch (Exception e) {
		log.error("Errore nella verifica dello stato per la posizione " + payPos.getId(), e);
		retStatus.setEsito(false);
		this.handleException(e, retStatus);
	    }
	    result.getStatoPosizioni().add(retStatus);
	}
	return result;
    }

    private EsitoListaPagamenti verificaPagamentoList(PayPosizioniDebitorie payPos) throws PayException {

	WebClient client = getClient(getWsVerificaConfig(), "stato_pagamenti");
	String codiceVersamento = posizioniDebitorieCommandService.findCodiceVersamentoFromPosizioneDebitoria(payPos);
	Form f = new Form() //
		.param("applicazione", getNomeApplicazione()) //
		.param("tipo_dovuto", codiceVersamento) //
		.param("id_univoco_dovuto", payPos.getIdPosizionePsp())//
		.param("iuv", payPos.getIuv().replace(univocitaIUVOTF(payPos), ""))//
	;
	Response response = client.post(f);
	Integer status = response.getStatus();
	if (status.equals(200) || status.equals(201)) {
	    log.debug("Verifica stato pagamenti {}-{}", status, response.getStatusInfo().getReasonPhrase());
	    log.debug("url ws [verificaStatoPagamento]: {} ", client.getCurrentURI());
	    InputStream is = ((InputStream) response.getEntity());
	    try {
		JAXBContext jc = JAXBContext.newInstance(EsitoListaPagamenti.class);
		Unmarshaller unmarshaller = jc.createUnmarshaller();
		unmarshaller.setProperty(UnmarshallerProperties.MEDIA_TYPE, MediaType.APPLICATION_JSON);
		unmarshaller.setProperty(UnmarshallerProperties.JSON_INCLUDE_ROOT, false);
		return unmarshaller.unmarshal(new StreamSource(is), EsitoListaPagamenti.class).getValue();
	    } catch (JAXBException e) {
		log.error("[verificaStatoPagamento] Errore nel umarshalling: " + e.getMessage(), e);
		throw new PayException(e);
	    }
	} else {
	    log.error("uri ws [verificaStatoPagamento]: {} ", client.getCurrentURI());
	    gestisciErroreHttp(response, "verificaStatoPagamento");
	}
	throw new PayException("Errore nella verifica dello stato della posizione debitoria " + payPos.getId());
    }

    @Override
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
	AttivaSessionePagamentoResponseType attivaSessioneOTF = new AttivaSessionePagamentoResponseType();
	String urlEsitoPagamento = payPosizioneDebitoria.getProfiloEnte().getUrlEsitoPagamento();
	String idSessione = ORMHelper.getIdcomune() + "_" + UUID.randomUUID().toString();
	urlEsitoPagamento += "?idSessione=" + idSessione;
	try {
	    EsitoAvviaPagamento pagamentoImmediato = attivaPagamentoOnTheFlyOpenWeb(payPosizioneDebitoria.getIuv(), urlEsitoPagamento,
		    fromSoggettoDebitore(payPosizioneDebitoria.getSoggettoDebitore()), false);
	    if (!pagamentoImmediato.getEsito().equalsIgnoreCase("ok")) {
		throw new PayException("Errore nella creazione del pagamento immediato: " + pagamentoImmediato.getErrore());
	    }
	    attivaSessioneOTF.setPayUrl(pagamentoImmediato.getUrlRedirect());
	    attivaSessioneOTF.setEsito(true);
	    attivaSessioneOTF.setIdSessione(idSessione);
	    attivaSessioneOTF.setHttpMethodRequired(HttpMethodType.GET);
	} catch (Exception e) {
	    String msg = "la chiamata al servizio getToken ha lanciato un eccezione" + e.toString();
	    log.error("attivaSessionePagamento - " + msg, e);
	    attivaSessioneOTF.setDescEsito(msg);
	}
	return attivaSessioneOTF;
    }

    private void gestisciStatoPagamento(PayPosizioniDebitorie payPos, EsitoVerificaPagamento statoPagamento, StatoPosizioneType retStatus)
	    throws PayException {

	PopolamentoDatiHelper.completaDatiDaPosizioneDebitoria(retStatus, payPos,
		payPosizioniDebitorieService.findRiferimentiClientByPosizioneDebitoria(payPos.getId().getCodice()));
	log.debug("pos deb {} pagato {}", payPos.getId(), statoPagamento.getPagato());
	int pagato = 0;
	if (statoPagamento.getPagato() != null) {
	    pagato = statoPagamento.getPagato().intValue();
	}
	String stato = statoPagamento.getStato();
	log.debug("pos deb {} stato {}", payPos.getId(), stato);
	retStatus.setStatoPagamentoNativo(stato);
	if (pagato == 1) {
	    EsitoListaPagamenti verificaPagamentoList = verificaPagamentoList(payPos);
	    log.debug("Verifica pagamenti posizione {}: {}", payPos.getId(), verificaPagamentoList);
	    if (verificaPagamentoList.getEsito().equalsIgnoreCase("ok")) {
		List<DatiPagamento> listaPagamenti = verificaPagamentoList.getListaPagamenti();
		if (!listaPagamenti.isEmpty()) {
		    DatiPagamento dp = listaPagamenti.get(0);
		    log.debug("Verifica pagamenti posizione {}: processo i dati pagamento(0){}", payPos.getId(), dp);
		    retStatus.setStato(StatoPagamentoType.RENDICONTATO_DA_IC);
		    String iur = dp.getIdentificativoRiversamento();
		    String metodoPagamento = dp.getMetodoPagamento();
		    String istitutoCredito = dp.getIstitutoCredito();
		    String importoPagato = dp.getImportoPagato();
		    SoggettoDebitoreType sogg = new SoggettoDebitoreType();
		    sogg.setCfpi(dp.getCfPagatore());
		    sogg.setEmail(dp.getEmailPagatore());
		    sogg.setNome(dp.getNomePagatore());
		    sogg.setCognome(dp.getCognomePagatore());
		    DatiPagamentoType datiPag = new DatiPagamentoType();
		    datiPag.setSoggettoPagatore(sogg);
		    datiPag.setDataOraPagamento(recuperaData(statoPagamento.getDataPagamento()));
		    datiPag.setIur(iur);
		    datiPag.setIuv(dp.getIuv());
		    datiPag.setModalitaPagamento(metodoPagamento);
		    datiPag.setRagioneSocialePSP(istitutoCredito);
		    if (Utilities.isNumeric(importoPagato, ".")) {
			BigDecimal ip = BigDecimal.valueOf(Double.parseDouble(importoPagato));
			datiPag.setImportoPagato(ip);
		    }
		    retStatus.setDatiPagamento(datiPag);
		    log.debug("PayPos: {} dati pagamento processati", payPos.getId());
		}
	    }
	    return;
	}
	StatoPagamentoOpenWebEnum statoEnum = StatoPagamentoOpenWebEnum.fromValue(stato);
	switch (statoEnum) {
	case ELIMINATO_DA_UFFICIO:
	case ANNULLATO:
	    retStatus.setStato(StatoPagamentoType.ANNULLATO);
	    break;
	case PAGAMENTO_NON_ESEGUITO:
	    if (BooleanUtils.isTrue(payPos.getFlagOTF())) {
		retStatus.setStato(StatoPagamentoType.ANNULLATO);
	    }
	    break;
	default:
	    break;
	}
	retStatus.setStatoPagamentoNativo(stato);
    }

    private XMLGregorianCalendar recuperaData(String dataPagamento) {

	if (StringUtils.isBlank(dataPagamento)) {
	    return null;
	}
	return Utilities.getXMLGregorianCalendar(Utilities.getDate(dataPagamento, "yyyy-MM-dd"));
    }

    private EsitoVerificaPagamento verificaPagamento(PayPosizioniDebitorie payPos) throws PayException {

	WebClient client = getClient(getWsVerificaConfig(), "verifica_pagamento");
	String iuv = payPos.getIuv().replace(univocitaIUVOTF(payPos), "");
	String codiceVersamento = posizioniDebitorieCommandService.findCodiceVersamentoFromPosizioneDebitoria(payPos);
	Form f = new Form() //
		.param("applicazione", getNomeApplicazione()) //
		.param("tipo_dovuto", codiceVersamento) //
		.param("id_univoco_dovuto", payPos.getIdPosizionePsp())//
		.param("iuv", iuv)//
		.param("rt", "1")//
		.param("pdf", "0")//
		.param("mbd", "0");
	Response response = client.post(f);
	Integer status = response.getStatus();
	if (status.equals(200) || status.equals(201)) {
	    log.debug("{}-{}", status, response.getStatusInfo().getReasonPhrase());
	    log.debug("url ws [verificaStatoPagamento]: {} ", client.getCurrentURI());
	    InputStream is = ((InputStream) response.getEntity());
	    try {
		JAXBContext jc = JAXBContext.newInstance(EsitoVerificaPagamento.class);
		Unmarshaller unmarshaller = jc.createUnmarshaller();
		unmarshaller.setProperty(UnmarshallerProperties.MEDIA_TYPE, MediaType.APPLICATION_JSON);
		unmarshaller.setProperty(UnmarshallerProperties.JSON_INCLUDE_ROOT, false);
		return unmarshaller.unmarshal(new StreamSource(is), EsitoVerificaPagamento.class).getValue();
	    } catch (JAXBException e) {
		log.debug("[verificaStatoPagamento] Errore nel umarshalling: {}", e.getMessage());
		throw new PayException(e);
	    }
	} else {
	    log.error("uri ws [verificaStatoPagamento]: {} ", client.getCurrentURI());
	    gestisciErroreHttp(response, "verificaStatoPagamento");
	}
	throw new PayException("Errore nella verifica dello stato della posizione debitoria " + payPos.getId());
    }

    private String univocitaIUVOTF(PayPosizioniDebitorie payPos) {

	return "---" + payPos.getId().getIdcomune() + "-" + payPos.getId().getCodice();
    }

    @Override
    public void modificaDataScadenzaPosizioneDebitoria(Integer idPosizioneDebitoria, Date nuovaDataScadenza) throws PayException {

	log.debug("modificaDataScadenzaPosizioneDebitoria {}-{}", idPosizioneDebitoria, nuovaDataScadenza);
	PayPosizioniDebitorie payPos = payPosizioniDebitorieService.findById(new PkId(idPosizioneDebitoria));
	log.debug("modificaDataScadenzaPosizioneDebitoria prima di create il client aggiornaPosizioneDebitoria {}-{}", idPosizioneDebitoria,
		nuovaDataScadenza);
	EsitoModifica response = modificaDataScadenzaOpenWeb(payPos, nuovaDataScadenza);
	if (!response.getEsito().equalsIgnoreCase("ok")) {
	    throw new PayException(
		    "Errore nella modifica della data per la posizione debitoria " + idPosizioneDebitoria + ". Dettaglio: " + response.getErrore());
	}
    }

    private EsitoModifica modificaDataScadenzaOpenWeb(PayPosizioniDebitorie payPos, Date nuovaDataScadenza) throws PayException {

	BigDecimal importoPos = BigDecimal.ZERO;
	for (PayDettaglioImporti payImporto : payPos.getDettagliImporto()) {
	    importoPos = importoPos.add(payImporto.getImporto());
	}
	WebClient client = getClient(getWsAnnullamentoConfig(), "modifica_dovuto");
	String codiceVersamento = posizioniDebitorieCommandService.findCodiceVersamentoFromPosizioneDebitoria(payPos);
	Form f = new Form() //
		.param("applicazione", getNomeApplicazione()) //
		.param("tipo_dovuto", codiceVersamento) //
		.param("id_univoco_dovuto", payPos.getIdPosizionePsp())//
		.param("iuv", payPos.getIuv())//
		.param("importo", importoPos.toString())//
		.param("scadenza", Utilities.formatDateWithPattern(nuovaDataScadenza, "dd/MM/yyyy"));//
	Response response = client.post(f);
	Integer status = response.getStatus();
	if (status.equals(200) || status.equals(201)) {
	    log.debug("{}", status, response.getStatusInfo().getReasonPhrase());
	    log.debug("url ws [modificaDataScadenza]: {} ", client.getCurrentURI());
	    InputStream is = ((InputStream) response.getEntity());
	    try {
		JAXBContext jc = JAXBContext.newInstance(EsitoModifica.class);
		Unmarshaller unmarshaller = jc.createUnmarshaller();
		unmarshaller.setProperty(UnmarshallerProperties.MEDIA_TYPE, MediaType.APPLICATION_JSON);
		unmarshaller.setProperty(UnmarshallerProperties.JSON_INCLUDE_ROOT, false);
		return unmarshaller.unmarshal(new StreamSource(is), EsitoModifica.class).getValue();
	    } catch (JAXBException e) {
		log.debug("[modificaDataScadenza] Errore nel umarshalling: {}", e.getMessage());
		throw new PayException(e);
	    }
	} else {
	    log.debug("uri ws [modificaDataScadenza]: {} ", client.getCurrentURI());
	    gestisciErroreHttp(response, "modificaDataScadenza");
	}
	throw new PayException("Errore della modifica di data scadenza della  posizione debitoria " + payPos.getId());
    }

    private String getNomeApplicazione() {

	return "pagamenti";
    }

    @Override
    public PaySessioniPagamento gestisciEsitoSessione(Map<String, String[]> reqParams) {

	List<PaySessioniPagamento> sessioneTrovata = new ArrayList<>();
	String[] iuvs = reqParams.get("iuv");
	String[] esitoVal = reqParams.get("esito");
	String[] motivoVal = reqParams.get("messaggio");
	String[] idSessioneVal = reqParams.get("idSessione");
	String iuv = null;
	String esito = null;
	String motivo = null;
	if (esitoVal != null && esitoVal.length > 0) {
	    esito = esitoVal[0];
	}
	if (motivoVal != null && motivoVal.length > 0) {
	    motivo = motivoVal[0];
	}
	String idSessione = null;
	if (idSessioneVal != null && idSessioneVal.length > 0) {
	    idSessione = idSessioneVal[0];
	}
	if (iuvs != null && iuvs.length > 0) {
	    iuv = iuvs[0];
	}
	log.debug("idSessione {}, esito {}, iuv {}, motivo {}", new Object[] { idSessione, esito, iuv, motivo });
	if (StringUtils.isNotBlank(idSessione)) {
	    sessioneTrovata = this.paySessioniPagamentoService.findBySessionId(idSessione);
	}
	if (sessioneTrovata.isEmpty()) {
	    PayPosizioniDebitorie payPos = payPosizioniDebitorieService.findByIUV(iuv);
	    if (payPos != null) {
		sessioneTrovata = this.paySessioniPagamentoService.findSessioniAttivePerPosizioneDebitoria(payPos.getId().getCodice());
	    }
	}
	boolean isEsitoSuccess = "ok".equalsIgnoreCase(esito);
	log.debug("idSessione={}, esito={}, motivo={}", idSessione, esito, motivo);
	if (!sessioneTrovata.isEmpty()) {
	    for (PaySessioniPagamento paySessioniPagamento : sessioneTrovata) {
		PayPosizioniDebitorie posizioneDebitoria = payPosizioniDebitorieService
			.findById(new PkId(paySessioniPagamento.getPosizioneDebitoria().getId().getCodice()));
		if (BooleanUtils.isTrue(posizioneDebitoria.getFlagOTF()) && !isEsitoSuccess) {
		    // annullo le posizioni debitorie
		    log.debug("annullo la posizione debitoria con ID = {}", posizioneDebitoria.getId());
		    this.payPosizioniDebitorieService.aggiornaPosizioneDebitoria(posizioneDebitoria, StatiPagamento.ANNULLATO,
			    "Posizione OTF annullata da gestisci sessione con esito " + esito);
		}
		paySessioniPagamento.setEsito("1".equals(esito));
		this.paySessioniPagamentoService.update(paySessioniPagamento);
	    }
	    return sessioneTrovata.get(0); // ne ritorno una perché la redirect è sempre quella
	}
	return null;
    }

    @Override
    public void validateRichiestaInserimentoPosizioniDebitorie(RegistrazioneContabileType registrazioneContabile)
	    throws ValidazionePosizioniDebitorieException {

	List<PosizioneDebitoriaType> rate = registrazioneContabile.getRate().getRata();
	GregorianCalendar now = new GregorianCalendar();
	if (rate.size() < 1) {
	    throw new ValidazionePosizioniDebitorieException(
		    "Numero di rate deve essere uguale o maggiore di 1. Riferimento posizione debitoria: " + registrazioneContabile.getDescrizione());
	}
	for (PosizioneDebitoriaType rata : rate) {
	    if (rata.getDataScadenza() == null || rata.getDataScadenza().toGregorianCalendar().compareTo(now) < 0) {
		throw new ValidazionePosizioniDebitorieException(
			"La data di scadenza è obbligatoria e maggiore di data odierna. Riferimento posizione debitoria: " +
								 registrazioneContabile.getDescrizione());
	    }
	    for (ImportoPagamentoType importoPagamento : rata.getImporto().getComponenteImporto()) {
		//		if (importoPagamento.getNumeroAccertamento() == null) {
		//		    throw new ValidazionePosizioniDebitorieException(
		//			    "Il numero accertamento è obbligatorio. Riferimento posizione debitoria: " + registrazioneContabile.getDescrizione());
		//		}
		if (importoPagamento.getDescrizioneCausale() == null) {
		    throw new ValidazionePosizioniDebitorieException(
			    "La causale è obbligatoria.Riferimento posizione debitoria: " + registrazioneContabile.getDescrizione());
		}
	    }
	}
	SoggettoDebitoreType sd = registrazioneContabile.getSoggettoDebitore();
	if (StringUtils.isBlank(sd.getNome()) || StringUtils.isBlank(sd.getCfpi())) {
	    throw new ValidazionePosizioniDebitorieException(
		    "Non sono state specificate le informazioni postali del debitore (Cognome e codicefiscale/P.iva): " +
							     registrazioneContabile.getDescrizione());
	}
    }

    @Override
    public boolean supportaPagamentoOnTheFly() {

	return true;
    }

    @Override
    public boolean supportaPagamentoOffLine() {

	return false;
    }

    @Override
    public boolean supportaAttivaSessionePagamento() {

	return true;
    }

    @Override
    public boolean supportaModificaDataScadenza() {

	return true;
    }

    private OAuth2UserNamePasswordSecurityRestTokenManager getSecurityTokenManager() {

	PayConnectorWsEndpoint wsSecurityConfig = getWsSecurityConfig();
	String grantType = StringUtils
		.defaultIfEmpty(this.payConnectorConfigValuesService.getValoreParametroConfigurazione(ConfigParamNames.AUTH_GRANT_TYPE), "password");
	OpenWebSecurityParams params = new OpenWebSecurityParams(wsSecurityConfig.getUtente(), wsSecurityConfig.getPassword(), grantType);
	return new OAuth2UserNamePasswordSecurityRestTokenManager(wsSecurityConfig, params);
    }

    private WebClient getClient(PayConnectorWsEndpoint payConnectorWsEndpoint, String nomeMetodo) throws PayException {

	log.debug("creo il client a url {}{}", payConnectorWsEndpoint.getEndpointUrl(), nomeMetodo);
	OAuth2UserNamePasswordSecurityRestTokenManager mgr = getSecurityTokenManager();
	WebClient client = WebClient.create(payConnectorWsEndpoint.getEndpointUrl() + nomeMetodo);
	String token = mgr.getToken();
	client.type(MediaType.APPLICATION_FORM_URLENCODED).accept(MediaType.APPLICATION_JSON).encoding("UTF-8").header("Authorization",
		mgr.getTokenType() + " " + token);
	HTTPConduit conduit = WebClient.getConfig(client).getHttpConduit();
	conduit.getClient().setConnectionTimeout(CONNECTION_TIMEOUT);
	conduit.getClient().setReceiveTimeout(payConnectorWsEndpoint.getTimeout());
	return client;
    }

    private PayException gestisciErroreHttp(Response response, String methodName) {

	String errorMessage = MessageFormat.format("[{0}]CHIAMATA NON RIUSCITA RESPONSE STATUS: {1}", methodName,
		response.getStatusInfo().getReasonPhrase());
	PayException payException = null;
	InputStream is = ((InputStream) response.getEntity());
	if (response.getMediaType() != null && MediaType.APPLICATION_JSON.equals(response.getMediaType().toString())) {
	    FaultBean errore = null;
	    try {
		JAXBContext jc = JAXBContext.newInstance(FaultBean.class);
		Unmarshaller unmarshaller = jc.createUnmarshaller();
		unmarshaller.setProperty(UnmarshallerProperties.MEDIA_TYPE, MediaType.APPLICATION_JSON);
		unmarshaller.setProperty(UnmarshallerProperties.JSON_INCLUDE_ROOT, false);
		errore = unmarshaller.unmarshal(new StreamSource(is), FaultBean.class).getValue();
		log.error(errorMessage);
		log.error(errore.getDettaglio());
		StringBuilder s = new StringBuilder();
		s.append(errore.getDescrizione());
		if (StringUtils.isNotEmpty(errore.getDettaglio())) {
		    s.append(" : ").append(errore.getDettaglio());
		}
		payException = new PayException(s.toString());
		payException.setErrorCode(errore.getCodice());
		return payException;
	    } catch (JAXBException e) {
		return new PayException("Errore nel umarshalling dell' oggetto FaultBean:" + methodName, e);
	    }
	} else {
	    String text = null;
	    try (Scanner scanner = new Scanner(is, StandardCharsets.UTF_8.name())) {
		text = scanner.useDelimiter("\\A").next();
	    }
	    log.error("gestisciErroreHttp: {}", text);
	    payException = new PayException(text);
	    payException.setErrorCode(String.valueOf(response.getStatus()));
	    return payException;
	}
    }

    private String determinaTipoPersona(String codiceFiscale) {

	if ((StringUtils.length(codiceFiscale) == 11)) {
	    return "G";
	}
	return "F";
    }

    public static void main(String[] args) throws IOException, JAXBException {

	Dovuto d = new Dovuto();
	String marshalJsonObject = Utilities.marshalJsonObject(d, Dovuto.class, false, "UTF-8");
	System.out.println(marshalJsonObject);
	//	// System.out.println(toAAAAMMGGDate(Calendar.getInstance().getTime()));
	//	String base64String = "UEsDBBQACAgIAJuBR1UAAAAAAAAAAAAAAAAMAAAAY29udGVudF9qc29utZBfa8IwFMW/SslzlTRq3fo0NxnIfBisvmyMcJdmEkhzS5r6MPG770bdH0EfOhih0Jx7z7m/my1rYA0BvWbFlgXToGy0b9EBK9g9S5nDmkqsIiVoa4EkheujarsGo/BO/8vH1bxcXuVlPpmP+eiBdF2DsVRqtLqhb2gC26XMUxArXo7T6BZHrajdVLJzZoMK5YYYoNYuICtcZ23KWgWVdh+xV3AhBhkf8CmZKtx0wfzk7e/kYrqSYIM3p8Hf5fK5lIupyGWW51ncAboWbFxqFl1JfJYIsPfXDfroyoY8ZaCU9uGLbk8zHGec+sA5lArrRocDaqzt0jNkb2gt9ia7JdcJz0hcBJqIXkCtMiAd0tEeW+iNNjfehGCSyiStXnua5w0kg+TpbjFLKDX5Ffz9mhN+EX903Qu/Oow/rPEvO5zhF/xP/K90PgFQSwcIvwEZkUkBAAB3AwAAUEsBAhQAFAAICAgAm4FHVb8BGZFJAQAAdwMAAAwAAAAAAAAAAAAAAAAAAAAAAGNvbnRlbnRfanNvblBLBQYAAAAAAQABADoAAACDAQAAAAA=";
	//	//	base64String = "UEsDBBQAAAAIAEZNP1QYsy1r+AAAABcDAAAKAAAAYXJyYXlfanNvbs2RP0/DMBDF51bqd4DMGS5H/iC2ToiJDp2oIsvUFrLU2OA4Bon0u3OOGyoFhnapOtjWPf/O76y3Wcxn37RmiTWfTHfNq7TJww2kUeNOUrUJRcSiyElMsiQ9KEqwTitvtoZ5aVveSO1MQCpEwCqHorjLc0R2bCEnaQ3j3qt2QAGyKX37SwvjO6fGScZZhiun3g0L99FRKKscoUPndLgjtn5Zs6cKS5YVyYHcx7MePVtnlX7jTNB3G07FFzXqbrebAh92a4QMr66Wj8+rZQ+A/Z/vBAnuqzIHLMoeAch3dK1p26f/J5GdkASenwSelQReIonyWpJYzOsfUEsBAj8AFAAAAAgARk0/VBizLWv4AAAAFwMAAAoAJAAAAAAAAAAgAAAAAAAAAGFycmF5X2pzb24KACAAAAAAAAEAGAClRpFvfhbYAaVGkW9+FtgBZPJKrlgT2AFQSwUGAAAAAAEAAQBcAAAAIAEAAAAA";
	//	String contenuto = "[{\"row_number\":0,\"rate\":[{\"rata\":\"U\",\"id_univoco_versamento\":\"02722280174467480\",\"numero_avviso\":\"302722280174467480\",\"dovuti\":[{\"tipo_dovuto\":\"ed_altri\",\"id_univoco_dovuto\":\"TZT_I726_1661\"},{\"tipo_dovuto\":\"ed_bollo\",\"id_univoco_dovuto\":\"TZT_I726_1661\"},{\"tipo_dovuto\":\"ed_scia_nononerosa\",\"id_univoco_dovuto\":\"TZT_I726_1661\"},{\"tipo_dovuto\":\"ed_diritti_scia_onerosa\",\"id_univoco_dovuto\":\"TZT_I726_1661\"}],\"stringa_datamatrix\":null,\"stringa_qrcode\":\"PAGOPA|002|302722280174467480|00050800523|38300\"}]}]";
	//	List<EsitiRate> s = (List<EsitiRate>) JSONUtils.unmarshal(EsitiRate.class, contenuto, false);
	//	//	OpenWebConnector c = new OpenWebConnector();
	//	//	List<EsitiRate> s = (List<EsitiRate>) c.unmarshalEsitiRate(base64String);
	//	System.out.println(s);
	//	for (EsitiRate esitiRata : s) {
	//	    List<EsitiRata> esitiRate = esitiRata.getEsitiRate();
	//	    for (EsitiRata e : esitiRate) {
	//		System.out.println(e.getIdUnivocoVersamento());
	//		System.out.println(e.getNumeroAvviso());
	//	    }
	//	}
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
