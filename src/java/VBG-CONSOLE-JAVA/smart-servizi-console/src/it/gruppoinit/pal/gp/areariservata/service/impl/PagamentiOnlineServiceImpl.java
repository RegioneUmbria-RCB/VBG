package it.gruppoinit.pal.gp.areariservata.service.impl;

import it.gruppoinit.pal.gp.areariservata.schema.pagamenti.PaymentDataType;
import it.gruppoinit.pal.gp.areariservata.service.PagamentiOnlineService;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.exception.InvalidConfigurationException;
import it.gruppoinit.pal.gp.core.service.AnagrafeService;
import it.gruppoinit.pal.gp.core.service.ConfigurazioneService;
import it.gruppoinit.pal.gp.core.service.OggettiService;
import it.gruppoinit.pal.gp.core.service.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.ws.client.FileConverterWsClient;

import java.io.IOException;
import java.io.StringReader;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.beanutils.BasicDynaClass;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaClass;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

@Service
public class PagamentiOnlineServiceImpl implements PagamentiOnlineService {

    private static final String MIP_XPATH_PAYMENT_DATA_NUMERO_OPERAZIONE = "/PaymentData/NumeroOperazione";
    private static final String MIP_XPATH_PAYMENT_DATA_ID_TRANSAZIONE = "/PaymentData/IDTransazione";
    private static final String MIP_XPATH_PAYMENT_DATA_ID_ORDINE = "/PaymentData/IDOrdine";
    private AnagrafeService anagrafeService;
    private ConfigurazioneService configurazioneService;
    private OggettiService oggettiService;
    private FileConverterWsClient fileConverterWsClient = new FileConverterWsClient();

    private enum TIPO_PAGAMENTO {
	PAGAMENTI_MIP_RPCSUAP
    };

    private static Logger log = LoggerFactory.getLogger(PagamentiOnlineServiceImpl.class);
    private VerticalizzazioniService verticalizzazioniService;

    //    private FoArjDomandeService foArjDomandeService;
    //    private FoArjDomandeOneriService foArjDomandeOneriService;
    @Autowired
    public void setConfigurazioneService(ConfigurazioneService configurazioneService) {

	this.configurazioneService = configurazioneService;
    }

    @Autowired
    public void setAnagrafeService(AnagrafeService anagrafeService) {

	this.anagrafeService = anagrafeService;
    }

    //    @Autowired
    //    public void setFoArjDomandeService(FoArjDomandeService foArjDomandeService) {
    //
    //	this.foArjDomandeService = foArjDomandeService;
    //    }
    @Autowired
    public void setOggettiService(OggettiService oggettiService) {

	this.oggettiService = oggettiService;
    }

    @Autowired
    public void setVerticalizzazioniService(VerticalizzazioniService verticalizzazioniService) {

	this.verticalizzazioniService = verticalizzazioniService;
    }

    //    @Autowired
    //    public void setFoArjDomandeOneriService(FoArjDomandeOneriService foArjDomandeOneriService) {
    //
    //	this.foArjDomandeOneriService = foArjDomandeOneriService;
    //    }
    //
    private TIPO_PAGAMENTO findTipoPagamento() {

	boolean isPagamentiOnline = verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_SISTEMAPAGAMENTI_ATTIVO_FO);
	if (isPagamentiOnline) {
	    String tipoPagamento = "";
	    Verticalizzazioniparametri vp = verticalizzazioniService.getVerticalizzazioniparametri(
		    WebConstants.VERTICALIZZAZIONE_SISTEMAPAGAMENTI_ATTIVO_FO, WebConstants.VERTICALIZZAZIONE_SISTEMAPAGAMENTI_ATTIVO_FO_TIPOSISTEMA);
	    if (vp != null) {
		if (StringUtils.isNotBlank(vp.getValore())) {
		    tipoPagamento = vp.getValore();
		}
	    }
	    if (tipoPagamento.equalsIgnoreCase("PAGAMENTI_MIP_RPCSUAP")) {
		return TIPO_PAGAMENTO.PAGAMENTI_MIP_RPCSUAP;
	    }
	} else {
	    log.error("findTipoPagamento# Nessun sistema di pagamento online configurato");
	    throw new InvalidConfigurationException("Nessun sistema di pagamento online configurato");
	}
	return null;
    }

    @Override
    public String updateCalculateUrlPagamentoOnline(Integer codiceDomanda, String currentAppPath, String emailPagamentoOnline) {

	//	TIPO_PAGAMENTO tp = findTipoPagamento();
	//	if (tp != null) {
	//	    FoArjDomande fad = foArjDomandeService.findById(new PkId(codiceDomanda));
	//	    switch (tp) {
	//	    case PAGAMENTI_MIP_RPCSUAP:
	//		if (log.isDebugEnabled()) {
	//		    log.debug("calculateUrlPagamentoOnline# pagamenti online di tipo PAGAMENTI_MIP_RPCSUAP");
	//		}
	//		return mipPaymentRequest(fad, currentAppPath, emailPagamentoOnline);
	//	    }
	//	}
	//	return null;
	return null;
    }

    private String getProp(DynaBean d, String property) {

	String result = (String) d.get(property);
	return StringUtils.defaultIfEmpty(result, "");
    }

    //    private String mipPaymentRequest(FoArjDomande domanda, String currentAppPath, String emailPagamentoOnline) {
    //
    //	if (log.isDebugEnabled()) {
    //	    log.debug("mipPaymentRequest# recupero i parametri dalle verticalizzazioni");
    //	}
    //	DynaBean cfg = populateMIPCfg(domanda);
    //	String portaleId = getProp(cfg, "portaleId");
    //	String idServizio = getProp(cfg, "idServizio");
    //	String chiaveSegreta = getProp(cfg, "chiaveSegreta");
    //	String urlServerPagamento = getProp(cfg, "urlServerPagamento");
    //	String componenteSimbolo = getProp(cfg, "componenteSimbolo");
    //	String emailPortale = getProp(cfg, "emailPortale");
    //	String numeroOperazione = populateNumeroOperazioneMIP(domanda);
    //	String importo = populateImportoMIP(domanda);
    //	String urlRitorno = populateUrlRitornoMIP(domanda, currentAppPath);
    //	String urlErrore = populateUrlErroreMIP(domanda, currentAppPath);
    //	// String urlNotifica = populateUrlNotificaMIP(domanda, currentAppPath, numeroOperazione);
    //	String urlBack = populateUrlBackMIP(domanda, currentAppPath);
    //	if (StringUtils.isBlank(emailPagamentoOnline)) {
    //	    throw new RuntimeException("L'utente registrato non ha nessuna email definita");
    //	}
    //	String idUtente = ORMHelper.getIdcomuneAlias() + "-" + ORMHelper.getIdcomune() + "-" + domanda.getAnagrafe().getId().getCodice();
    //	String cfPiva = StringUtils.defaultString(domanda.getAnagrafe().getCodicefiscale()).trim();
    //	if (StringUtils.isNotBlank(domanda.getAnagrafe().getPartitaiva())) {
    //	    if (StringUtils.isNotBlank(cfPiva)) {
    //		cfPiva += "-";
    //	    }
    //	    cfPiva += domanda.getAnagrafe().getPartitaiva();
    //	}
    //	String pr = "<PaymentRequest>";
    //	pr += "    <PortaleID>" + portaleId + "</PortaleID>";
    //	pr += "    <Funzione>PAGAMENTO</Funzione>";
    //	pr += "    <URLDiRitorno>" + urlRitorno + "</URLDiRitorno>";
    //	pr += "    <URLDiErrore>" + urlErrore + "</URLDiErrore>";
    //	pr += "    <URLBack>" + urlBack + "</URLBack>";
    //	// pr += "    <URLDiNotifica>" + urlNotifica + "</URLDiNotifica>";
    //	pr += "    <CommitNotifica>N</CommitNotifica>";
    //	pr += "    <EmailPortale>" + emailPortale + "</EmailPortale>";
    //	pr += "    <NotificaEsitiNegativi>S</NotificaEsitiNegativi>";
    //	pr += "    <RitornaDatiSpecifici>S</RitornaDatiSpecifici>";
    //	pr += "    <PaymentData>";
    //	pr += "        <NumeroOperazione>" + numeroOperazione + "</NumeroOperazione>";
    //	pr += "        <Valuta>EUR</Valuta>";
    //	pr += "        <Importo>" + importo + "</Importo>";
    //	pr += "        <ImportoCommissioni />";
    //	pr += "        <CalcoloCommissioni>S</CalcoloCommissioni>";
    //	pr += "    </PaymentData>";
    //	pr += "    <UserData>";
    //	pr += "        <EmailUtente>" + emailPagamentoOnline + "</EmailUtente>";
    //	pr += "        <IdentificativoUtente>" + idUtente + "</IdentificativoUtente>";
    //	pr += "        <UserID>" + cfPiva + "</UserID>";
    //	pr += "        <TokenID />";
    //	pr += "    </UserData>";
    //	pr += "    <ServiceData>";
    //	pr += "        <IDServizio>" + idServizio + "</IDServizio>";
    //	pr += "    </ServiceData>";
    //	pr += "</PaymentRequest>";
    //	if (log.isDebugEnabled()) {
    //	    log.debug("mipPaymentRequest# PaymentRequest: {}", pr);
    //	}
    //	// chiaveSegreta = "CHIAVE";
    //	PayServerClient osc = new PayServerClient(chiaveSegreta, PayServerClient.PS2S_KT_CLEAR);
    //	// url di navigazione
    //	osc.ServerURL(urlServerPagamento + "Request2RID.jsp");
    //	// Prepara il buffer di protocollo
    //	int ret = osc.PS2S_PC_Request2RID(pr, componenteSimbolo, new java.util.Date());
    //	if (ret != Constant.RESULT_SUCCESS) {
    //	    log.error("mipPaymentRequest ret={}, desc={}", ret, Constant.GetDescr(ret));
    //	    throw new RuntimeException("Errore durante la chiamata al sistema di pagamento, " + urlServerPagamento);
    //	}
    //	try {
    //	    String buffer = java.net.URLEncoder.encode(osc.PS2S_NetBuffer(), "utf-8");
    //	    if (log.isDebugEnabled()) {
    //		log.debug("mipPaymentRequest# buffer generato: {}", buffer);
    //	    }
    //	    return urlServerPagamento + "pagamentoesterno.do?buffer=" + buffer;
    //	} catch (Exception ex) {
    //	    throw new RuntimeException("Errore 0002 ", ex);
    //	}
    //    }
    //    private String populateUrlBackMIP(FoArjDomande domanda, String currentAppPath) {
    //
    //	return currentAppPath + "/nuovaistanzaoneri/pagamentiOnlineBack.htm";
    //    }
    //
    //    private String populateImportoMIP(FoArjDomande domanda) {
    //
    //	List<FoArjDomandeOneri> oneri = foArjDomandeOneriService.findByIdDomanda(domanda.getId().getCodice());
    //	String importoOnere = "000";
    //	BigDecimal totaleImporti = BigDecimal.ZERO;
    //	for (FoArjDomandeOneri fado : oneri) {
    //	    if (BooleanUtils.isTrue(fado.getFlagOnline())) {
    //		if (!BooleanUtils.isTrue(fado.getFlagStato())) {
    //		    totaleImporti = totaleImporti.add(fado.getImporto());
    //		}
    //	    }
    //	}
    //	if (totaleImporti.compareTo(BigDecimal.ZERO) > 0) {
    //	    totaleImporti = totaleImporti.setScale(2, BigDecimal.ROUND_DOWN);
    //	    DecimalFormat df = new DecimalFormat();
    //	    df.setMaximumFractionDigits(2);
    //	    df.setMinimumFractionDigits(2);
    //	    df.setGroupingUsed(false);
    //	    String result = df.format(totaleImporti);
    //	    result = result.replace(".", "");
    //	    return result.replace(",", "");
    //	}
    //	return importoOnere;
    //    }
    //
    //    private String populateNumeroOperazioneMIP(FoArjDomande domanda) {
    //
    //	List<FoArjDomandeOneri> oneri = foArjDomandeOneriService.findByIdDomanda(domanda.getId().getCodice());
    //	String numerooperazione = domanda.getId().getCodice() + "-" + Utilities.getToday("MMddHHmmssS");
    //	for (FoArjDomandeOneri fado : oneri) {
    //	    if (BooleanUtils.isTrue(fado.getFlagOnline())) {
    //		if (!BooleanUtils.isTrue(fado.getFlagStato())) {
    //		    fado.setIdNumOperazOnline(numerooperazione);
    //		    foArjDomandeOneriService.update(fado);
    //		}
    //	    }
    //	}
    //	return numerooperazione;
    //    }
    //
    //    //    private String populateUrlNotificaMIP(FoArjDomande domanda, String currentAppPath, String numeroOrdine) {
    //    //
    //    //	String qs = "idcomunealias=" + ORMHelper.getIdcomuneAlias() + "&amp;software=" + ORMHelper.getIdcomuneAlias() + "&amp;numOrdine="
    //    //		+ numeroOrdine + "&amp;ts_=" + System.currentTimeMillis();
    //    //	qs = FileUtils.getLinkForFile(qs);
    //    //	return currentAppPath + "/servlets/pagamenti?" + qs;
    //    //    }
    //    private String populateUrlErroreMIP(FoArjDomande domanda, String currentAppPath) {
    //
    //	return currentAppPath + "/nuovaistanzaoneri/pagamentiOnlineErr.htm";
    //    }
    //
    //    private String populateUrlRitornoMIP(FoArjDomande domanda, String currentAppPath) {
    //
    //	return currentAppPath + "/nuovaistanzaoneri/pagamentiOnlineOK.htm";
    //    }
    private String getParametroMip(String nomeparametro, boolean obbligatorio) {

	Verticalizzazioniparametri p = verticalizzazioniService.getVerticalizzazioniparametri("PAGAMENTI_MIP_RPCSUAP", nomeparametro);
	if (p != null) {
	    if (StringUtils.isNotBlank(p.getValore())) {
		return p.getValore().trim();
	    }
	}
	if (obbligatorio) {
	    log.error("getParametroMip# Non sono stati configurati correttamente i parametri per il sistema di pagamento online: valore mancante del parametro "
		    + nomeparametro);
	    throw new InvalidConfigurationException(
		    " Non sono stati configurati correttamente i parametri per il sistema di pagamento online: valore mancante del parametro "
			    + nomeparametro);
	} else {
	    return "";
	}
    }

    @Override
    public void updateRientroPagamenti(Integer codiceDomanda, Map<String, String[]> requestParamMap) {

	//	TIPO_PAGAMENTO tp = findTipoPagamento();
	//	if (tp != null) {
	//	    FoArjDomande fad = foArjDomandeService.findById(new PkId(codiceDomanda));
	//	    switch (tp) {
	//	    case PAGAMENTI_MIP_RPCSUAP:
	//		if (log.isDebugEnabled()) {
	//		    log.debug("updateRientroPagamenti# pagamenti online di tipo PAGAMENTI_MIP_RPCSUAP");
	//		}
	//		updateRientropagamentiMIP(fad, requestParamMap);
	//	    }
	//	}
    }

    //    private void updateRientropagamentiMIP(FoArjDomande domanda, Map<String, String[]> requestParamMap) {
    //
    //	String[] bufferRequestArr = requestParamMap.get("buffer");
    //	String bufferInReq = bufferRequestArr[0];
    //	if (StringUtils.isBlank(bufferInReq)) {
    //	    throw new RuntimeException("updateRientropagamentiMIP#: Errore ");
    //	}
    //	DynaBean cfg = populateMIPCfg(domanda);
    //	//	String portaleId = getProp(cfg, "portaleId");
    //	//	String idServizio = getProp(cfg, "idServizio");
    //	String chiaveSegreta = getProp(cfg, "chiaveSegreta");
    //	String urlServerPagamento = getProp(cfg, "urlServerPagamento");
    //	String componenteSimbolo = getProp(cfg, "componenteSimbolo");
    //	//	String emailPortale = getProp(cfg, "emailPortale");
    //	String indirizzoProxy = getProp(cfg, "indirizzoProxy");
    //	String portaProxy = getProp(cfg, "portaProxy");
    //	String windowMinutes = getProp(cfg, "windowMinutes");
    //	int wm = 20;
    //	if (StringUtils.isNotBlank(windowMinutes)) {
    //	    try {
    //		wm = Integer.parseInt(windowMinutes);
    //	    } catch (NumberFormatException e) {
    //		log.error("updateRientroPagamentiMIP# errata configurazione del parametro WINDOW_MINUTES della verticalizzazione PAGAMENTI_MIP_RPCSUAP");
    //	    }
    //	}
    //	PayServerClient secretC = new PayServerClient(chiaveSegreta, PayServerClient.PS2S_KT_CLEAR);
    //	String bufferDatiXML = "";
    //	secretC.ServerURL(urlServerPagamento + "PID2Data.jsp");
    //	secretC.ProxyServer(indirizzoProxy);
    //	secretC.ProxyPort(portaProxy);
    //	String bt = "";
    //	String errorDescr = "";
    //	try {
    //	    bt = java.net.URLDecoder.decode(bufferInReq, "utf-8");
    //	} catch (UnsupportedEncodingException e) {
    //	    bt = java.net.URLDecoder.decode(bufferInReq);
    //	}
    //	int ret = secretC.PS2S_PC_PID2Data(bt, componenteSimbolo, new java.util.Date(), wm);
    //	errorDescr = statoPagamentoMIP(domanda, secretC, errorDescr, ret);
    //	if (StringUtils.isNotBlank(errorDescr)) {
    //	    throw new RuntimeException(errorDescr);
    //	}
    //    }
    //
    //    private String statoPagamentoMIP(FoArjDomande domanda, PayServerClient secretC, String errorDescr, int ret) {
    //
    //	String bufferDatiXML = "";
    //	switch (ret) {
    //	case 0:
    //	    bufferDatiXML = secretC.PS2S_DataBuffer();
    //	    if (log.isDebugEnabled()) {
    //		log.debug("updateRientropagamentiMIP# bufferDatiXML: {}", bufferDatiXML);
    //	    }
    //	    String numeroOperazione = null;
    //	    try {
    //		numeroOperazione = getValueFromXml(bufferDatiXML, MIP_XPATH_PAYMENT_DATA_NUMERO_OPERAZIONE);
    //	    } catch (Exception e) {
    //		log.error("updateRientropagamentiMIP# Non è stato possibile recuperare il tag [{}] dalla struttura: [{}]",
    //			MIP_XPATH_PAYMENT_DATA_NUMERO_OPERAZIONE, bufferDatiXML);
    //		throw new RuntimeException(e);
    //	    }
    //	    if (log.isDebugEnabled()) {
    //		log.debug("updateRientropagamentiMIP# numerooperazione: {}", numeroOperazione);
    //	    }
    //	    String idTransazione = "";
    //	    try {
    //		idTransazione = getValueFromXml(bufferDatiXML, MIP_XPATH_PAYMENT_DATA_ID_TRANSAZIONE);
    //	    } catch (Exception e) {
    //		log.error("updateRientropagamentiMIP# Non è stato possibile recuperare il tag [{}] dalla struttura: [{}]",
    //			MIP_XPATH_PAYMENT_DATA_ID_TRANSAZIONE, bufferDatiXML);
    //		throw new RuntimeException(e);
    //	    }
    //	    if (log.isDebugEnabled()) {
    //		log.debug("updateRientropagamentiMIP# idTransazione: {}", idTransazione);
    //	    }
    //	    foArjDomandeOneriService.updatePagamentiOnlineSetPagato(domanda, numeroOperazione, idTransazione);
    //	    String xmlRicevuta = generateXmlRicevuta(domanda, bufferDatiXML, TIPO_PAGAMENTO.PAGAMENTI_MIP_RPCSUAP, numeroOperazione);
    //	    Oggetti oxml = new Oggetti();
    //	    oxml.setNomefile("RicevutaMIP.xml");
    //	    byte[] bytesRicevuta = null;
    //	    try {
    //		bytesRicevuta = xmlRicevuta.getBytes("utf-8");
    //	    } catch (UnsupportedEncodingException e1) {
    //		bytesRicevuta = xmlRicevuta.getBytes();
    //	    }
    //	    oxml.setOggetto(bytesRicevuta);
    //	    oggettiService.insert(oxml);
    //	    byte[] binaryData = null;
    //	    Oggetti oggettiPdf = null;
    //	    //try {
    //	    try {
    //		binaryData = conversioneXslMIP(xmlRicevuta);
    //	    } catch (FileNotFoundException e1) {
    //		log.error("updateRientropagamentiMIP# non è stato possibile generare l'html dalla ricevuta xml: [{}]", xmlRicevuta, e1);
    //	    } catch (IOException e1) {
    //		log.error("updateRientropagamentiMIP# non è stato possibile generare l'html dalla ricevuta xml: [{}]", xmlRicevuta, e1);
    //	    } catch (TransformerException e1) {
    //		log.error("updateRientropagamentiMIP# non è stato possibile generare l'html dalla ricevuta xml: [{}]", xmlRicevuta, e1);
    //	    }
    //	    if (binaryData != null) {
    //		ConvertBinaryRequest req = new ConvertBinaryRequest(ORMHelper.getToken(), binaryData, "HTML", "PDF");
    //		ConvertBinaryResponse response;
    //		try {
    //		    response = fileConverterWsClient.convertBinary(req);
    //		    oggettiPdf = new Oggetti();
    //		    oggettiPdf.setNomefile("Ricevuta.pdf");
    //		    oggettiPdf.setOggetto(response.getBinaryData());
    //		    oggettiService.insert(oggettiPdf);
    //		} catch (RemoteException e) {
    //		    log.error("updateRientropagamentiMIP# errore nella conversione della ricevuta xml in PDF", e);
    //		}
    //		foArjDomandeOneriService.updatePagamentiOnlineSetRicevute(domanda, numeroOperazione, oxml, oggettiPdf);
    //	    }
    //	    break;
    //	case PayServerClient.pPS2S_COMPERROR:
    //	    errorDescr = "Fallita inizializzazione applicazione ";
    //	    break;
    //	case PayServerClient.pPS2S_DATAERROR:
    //	    errorDescr = "Impossibile estrarre buffer dati";
    //	    break;
    //	case PayServerClient.pPS2S_DATEERROR:
    //	    errorDescr = "Data non accettabile";
    //	    break;
    //	case PayServerClient.pPS2S_HASHERROR:
    //	    errorDescr = "Fallita verifica hash";
    //	    break;
    //	case PayServerClient.pPS2S_HASHNOTFOUND:
    //	    errorDescr = "Impossibile estrarre buffer hash";
    //	    break;
    //	case PayServerClient.pPS2S_CREATEHASHERROR:
    //	    errorDescr = "Impossibile creare buffer hash";
    //	    break;
    //	case PayServerClient.pPS2S_TIMEELAPSED:
    //	    errorDescr = "Finestra temporale scaduta";
    //	    break;
    //	case PayServerClient.pPS2S_XMLERROR:
    //	    errorDescr = "Documento xml non valido";
    //	    break;
    //	}
    //	return errorDescr;
    //    }
    //
    //    @Override
    //    public void updateStatoPagamenti(Integer codiceDomanda, Integer identificativo, Map<String, String[]> requestParamMap) {
    //
    //	TIPO_PAGAMENTO tp = findTipoPagamento();
    //	if (tp != null) {
    //	    FoArjDomande fad = foArjDomandeService.findById(new PkId(codiceDomanda));
    //	    FoArjDomandeOneri fado = foArjDomandeOneriService.findById(new PkId(identificativo));
    //	    switch (tp) {
    //	    case PAGAMENTI_MIP_RPCSUAP:
    //		if (log.isDebugEnabled()) {
    //		    log.debug("updateStatoPagamenti# pagamenti online di tipo PAGAMENTI_MIP_RPCSUAP");
    //		}
    //		updateStatoPagamentoMIP(fad, fado, requestParamMap);
    //	    }
    //	}
    //    }
    //
    //    private void updateStatoPagamentoMIP(FoArjDomande domanda, FoArjDomandeOneri fado, Map<String, String[]> requestParamMap) {
    //
    //	DynaBean cfg = populateMIPCfg(domanda);
    //	String portaleId = getProp(cfg, "portaleId");
    //	String chiaveSegreta = getProp(cfg, "chiaveSegreta");
    //	String urlServerPagamento = getProp(cfg, "urlServerPagamento");
    //	String componenteSimbolo = getProp(cfg, "componenteSimbolo");
    //	//	String emailPortale = getProp(cfg, "emailPortale");
    //	String indirizzoProxy = getProp(cfg, "indirizzoProxy");
    //	String portaProxy = getProp(cfg, "portaProxy");
    //	String windowMinutes = getProp(cfg, "windowMinutes");
    //	int wm = 20;
    //	if (StringUtils.isNotBlank(windowMinutes)) {
    //	    try {
    //		wm = Integer.parseInt(windowMinutes);
    //	    } catch (NumberFormatException e) {
    //		log.error("updateRientroPagamentiMIP# errata configurazione del parametro WINDOW_MINUTES della verticalizzazione PAGAMENTI_MIP_RPCSUAP");
    //	    }
    //	}
    //	PayServerClient secretC = new PayServerClient(chiaveSegreta, PayServerClient.PS2S_KT_CLEAR);
    //	secretC.ServerURL(urlServerPagamento + "Request2RID.jsp");
    //	secretC.ProxyServer(indirizzoProxy);
    //	secretC.ProxyPort(portaProxy);
    //	String bufferData = "<PaymentStatus>";
    //	bufferData += "<PortaleID>" + portaleId + "</PortaleID>";
    //	bufferData += "<NumeroOperazione>" + fado.getIdNumOperazOnline() + "</NumeroOperazione>";
    //	bufferData += "<RitornaDatiSpecifici>N</RitornaDatiSpecifici>";
    //	bufferData += "</PaymentStatus>";
    //	int ret = secretC.PS2S_PC_Request2RID(bufferData, componenteSimbolo, new java.util.Date());
    //	if (ret != Constant.RESULT_SUCCESS) {
    //	    throw new RuntimeException("Non è stato possibile contattare il sistema di pagamento");
    //	}
    //	try {
    //	    String bufferDatiXML = "";
    //	    bufferData = java.net.URLEncoder.encode(secretC.PS2S_NetBuffer(), "utf-8");
    //	    URLWorker oURLWorker = new URLWorker();
    //	    oURLWorker.setURL(urlServerPagamento + "PaymentStatusRequest.jsp?buffer=" + bufferData);
    //	    oURLWorker.setProxyServer(indirizzoProxy); // eventuale
    //	    oURLWorker.setProxyPort(portaProxy); // eventuale
    //	    if (!oURLWorker.doGet()) {
    //		throw new RuntimeException("Non è stato possibile contattare il sistema di pagamento");
    //	    }
    //	    String sID = oURLWorker.getBufferOut();
    //	    secretC.ServerURL(urlServerPagamento + "PID2Data.jsp");
    //	    secretC.ProxyServer(indirizzoProxy);
    //	    secretC.ProxyPort(portaProxy);
    //	    ret = secretC.PS2S_PC_PID2Data(sID, componenteSimbolo, new java.util.Date(), wm);
    //	    String errorDescr = "";
    //	    errorDescr = statoPagamentoMIP(domanda, secretC, errorDescr, ret);
    //	    if (ret == 0) {
    //		bufferDatiXML = secretC.PS2S_DataBuffer();
    //	    }
    //	} catch (Exception ex) {
    //	    throw new RuntimeException("Errore nel recupero dello stato pagamenti: " + ex.getMessage(), ex);
    //	}
    //    }
    //
    //    private byte[] conversioneXslMIP(String xml) throws FileNotFoundException, IOException, TransformerException {
    //
    //	byte[] htmlx = null;
    //	try {
    //	    htmlx = xml.getBytes("utf-8");
    //	} catch (UnsupportedEncodingException e) {
    //	    htmlx = xml.getBytes();
    //	}
    //	InputStream in = WebConstants.class.getClassLoader().getResourceAsStream("RicevutaPagamentiMIP.xsl");
    //	byte[] xslBytes = IOUtils.toByteArray(in);
    //	ByteArrayOutputStream baos = new ByteArrayOutputStream();
    //	TransformerFactory tFactory = TransformerFactory.newInstance();
    //	Transformer transformer = tFactory.newTransformer(new StreamSource(new ByteArrayInputStream(xslBytes)));
    //	transformer.transform(new StreamSource(new ByteArrayInputStream(htmlx)), new StreamResult(baos));
    //	return baos.toByteArray();
    //    }
    //
    //    private String generateXmlRicevuta(FoArjDomande domanda, String xmlDatiPagamento, TIPO_PAGAMENTO tipoPagamento, String numeroOperazione) {
    //
    //	ConfigurazioneId id = new ConfigurazioneId(WebConstants.SOFTWARE_TT);
    //	Configurazione conf = configurazioneService.findById(id);
    //	String denominazioneSportello = conf.getDenominazione();
    //	RicevutaType ricevuta = new RicevutaType();
    //	ricevuta.setDescrizioneEnte(denominazioneSportello);
    //	ricevuta.setCodiceDomanda(domanda.getId().getCodice());
    //	if (ORMHelper.getIdcomuneAlias().equalsIgnoreCase(ORMHelper.getIdcomune())) {
    //	    ricevuta.setCodiceEnte(ORMHelper.getIdcomune() + "-" + ORMHelper.getSoftware());
    //	} else {
    //	    ricevuta.setCodiceEnte(ORMHelper.getIdcomuneAlias() + "-" + ORMHelper.getIdcomune() + "-" + ORMHelper.getSoftware());
    //	}
    //	DatiPagamento dp = new DatiPagamento();
    //	switch (tipoPagamento) {
    //	case PAGAMENTI_MIP_RPCSUAP:
    //	    PaymentDataType mpd = new PaymentDataType();
    //	    try {
    //		mpd = populateMIPPaymentData(xmlDatiPagamento);
    //	    } catch (Exception e) {
    //		e.printStackTrace();
    //	    }
    //	    dp.setMipPaymentData(mpd);
    //	    break;
    //	default:
    //	    dp.setNonDefinito(xmlDatiPagamento);
    //	}
    //	ricevuta.setDatiPagamento(dp);
    //	RiferimentiUtenteType anagrafe = new RiferimentiUtenteType();
    //	Anagrafe richiedente = anagrafeService.findById(new PkId(domanda.getAnagrafe().getId().getCodice()));
    //	anagrafe.setNome(richiedente.getNome());
    //	anagrafe.setCognome(richiedente.getNominativo());
    //	anagrafe.setCodiceFiscale(richiedente.getCodicefiscale());
    //	if (richiedente.getDatanascita() != null) {
    //	    Calendar c = GregorianCalendar.getInstance();
    //	    c.setTime(richiedente.getDatanascita());
    //	    XMLGregorianCalendar dataNascita = Utilities.getXMLGregorianCalendar((GregorianCalendar) c);
    //	    anagrafe.setDataNascita(dataNascita);
    //	}
    //	LocalizzazioneType loca = new LocalizzazioneType();
    //	if (StringUtils.isNotBlank(richiedente.getIndirizzo())) {
    //	    loca.setIndirizzo(richiedente.getIndirizzo());
    //	}
    //	loca.setCap(richiedente.getCap());
    //	loca.setProvincia(richiedente.getProvincia());
    //	if (richiedente.getComuneResidenza() != null) {
    //	    loca.setComune(richiedente.getComuneResidenza().getComune());
    //	}
    //	anagrafe.setIndirizzo(loca);
    //	ricevuta.setAnagrafe(anagrafe);
    //	List<FoArjDomandeOneri> oneris = foArjDomandeOneriService.findByIdDomanda(domanda.getId().getCodice());
    //	for (FoArjDomandeOneri fado : oneris) {
    //	    if (BooleanUtils.isTrue(fado.getFlagOnline())) {
    //		if (StringUtils.defaultIfEmpty(numeroOperazione, "").equalsIgnoreCase(StringUtils.defaultIfEmpty(fado.getIdNumOperazOnline(), ""))) {
    //		    ListaOneriPagatiType lop = new ListaOneriPagatiType();
    //		    String descrizione = "";
    //		    if (fado.getInventarioprocedimenti() != null) {
    //			descrizione = fado.getInventarioprocedimenti().getProcedimento() + " - ";
    //		    } else {
    //			descrizione = "Intervento selezionato - ";
    //		    }
    //		    if (fado.getTipicausalioneri() != null) {
    //			descrizione += fado.getTipicausalioneri().getCoDescrizione();
    //		    }
    //		    lop.setDescrizione(descrizione);
    //		    lop.setImporto(fado.getImporto());
    //		    lop.setIdentificativo(fado.getId().getCodice());
    //		    ricevuta.getListaOneriPagati().add(lop);
    //		}
    //	    }
    //	}
    //	String output = Utilities.marshallObject(ricevuta);
    //	if (log.isDebugEnabled()) {
    //	    log.debug("populateMIPPaymentData# xmlPaymentData: {}", output);
    //	}
    //	return output;
    //    }
    //
    private PaymentDataType populateMIPPaymentData(String xmlDatiPagamento) throws Exception {

	PaymentDataType result = new PaymentDataType();
	result.setSistemaPagamento(getValueFromXml(xmlDatiPagamento, "/PaymentData/SistemaPagamento"));
	result.setSistemaPagamentoD(getValueFromXml(xmlDatiPagamento, "/PaymentData/SistemaPagamentoD"));
	result.setEsito(getValueFromXml(xmlDatiPagamento, "/PaymentData/Esito"));
	result.setEsitoD(getValueFromXml(xmlDatiPagamento, "/PaymentData/EsitoD"));
	result.setPortaleId(getValueFromXml(xmlDatiPagamento, "/PaymentData/PortaleID"));
	result.setNumeroOperazione(getValueFromXml(xmlDatiPagamento, MIP_XPATH_PAYMENT_DATA_NUMERO_OPERAZIONE));
	result.setIDOrdine(getValueFromXml(xmlDatiPagamento, MIP_XPATH_PAYMENT_DATA_ID_ORDINE));
	result.setIDTransazione(getValueFromXml(xmlDatiPagamento, MIP_XPATH_PAYMENT_DATA_ID_TRANSAZIONE));
	result.setAutorizzazione(getValueFromXml(xmlDatiPagamento, "/PaymentData/Autorizzazione"));
	result.setCircuitoAutorizzativo(getValueFromXml(xmlDatiPagamento, "/PaymentData/CircuitoAutorizzativo"));
	result.setCircuitoAutorizzativoD(getValueFromXml(xmlDatiPagamento, "/PaymentData/CircuitoAutorizzativoD"));
	result.setCircuitoSelezionato(getValueFromXml(xmlDatiPagamento, "/PaymentData/CircuitoSelezionato"));
	result.setCircuitoSelezionatoD(getValueFromXml(xmlDatiPagamento, "/PaymentData/CircuitoSelezionatoD"));
	result.setImportoAutorizzato(getValueFromXml(xmlDatiPagamento, "/PaymentData/ImportoAutorizzato"));
	result.setImportoCommissioni(getValueFromXml(xmlDatiPagamento, "/PaymentData/ImportoCommissioni"));
	result.setImportoTransato(getValueFromXml(xmlDatiPagamento, "/PaymentData/ImportoTransato"));
	result.setDatiSpecifici(getValueFromXml(xmlDatiPagamento, "/PaymentData/DatiSpecifici"));
	result.setDataOraOrdine(getValueFromXml(xmlDatiPagamento, "/PaymentData/DataOraOrdine"));
	result.setDataOra(getValueFromXml(xmlDatiPagamento, "/PaymentData/DataOra"));
	result.setDataOraTransazione(getValueFromXml(xmlDatiPagamento, "/PaymentData/DataOraTransazione"));
	return result;
    }

    private String getValueFromXml(String xml, String xpathParam) throws ParserConfigurationException, SAXException, IOException,
	    XPathExpressionException {

	InputSource source = new InputSource(new StringReader(xml));
	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	DocumentBuilder db = dbf.newDocumentBuilder();
	Document document = db.parse(source);
	XPathFactory xpathFactory = XPathFactory.newInstance();
	XPath xpath = xpathFactory.newXPath();
	String result = xpath.evaluate(xpathParam, document);
	return result;
    }

    //    private DynaBean populateMIPCfg(FoArjDomande domanda) {
    //
    //	DynaBean cfg = null;
    //	try {
    //	    cfg = mipCfgDynaClass.newInstance();
    //	    String portaleId = getParametroMip("PORTALEID", true);
    //	    cfg.set("portaleId", portaleId);
    //	    String idServizio = getParametroMip("IDSERVIZIO", true);
    //	    cfg.set("idServizio", idServizio);
    //	    String chiaveSegreta = getParametroMip("PASSWORD_CHIAMATE", true);
    //	    cfg.set("chiaveSegreta", chiaveSegreta);
    //	    String urlServerPagamento = getParametroMip("URLSERVERPAGAMENTO", true);
    //	    cfg.set("urlServerPagamento", urlServerPagamento);
    //	    String componenteSimbolo = getParametroMip("IDENTIFICATIVO_COMPONENTE", true);
    //	    cfg.set("componenteSimbolo", componenteSimbolo);
    //	    String emailPortale = getParametroMip("EMAIL_PORTALE", false);
    //	    cfg.set("emailPortale", emailPortale);
    //	    String windowMinutes = getParametroMip("WINDOW_MINUTES", false);
    //	    cfg.set("windowMinutes", windowMinutes);
    //	    String indirizzoProxy = getParametroMip("INDIRIZZOPROXY", false);
    //	    cfg.set("indirizzoProxy", indirizzoProxy);
    //	    String portaProxy = getParametroMip("PORTAPROXY", false);
    //	    cfg.set("portaProxy", portaProxy);
    //	    if (log.isDebugEnabled()) {
    //		log.debug("populateMIPCfg# ConfigurazioniCaricate: {}", ReflectionToStringBuilder.toString(cfg, ToStringStyle.MULTI_LINE_STYLE));
    //	    }
    //	} catch (IllegalAccessException e) {
    //	    e.printStackTrace();
    //	    throw new RuntimeException(e);
    //	} catch (InstantiationException e) {
    //	    e.printStackTrace();
    //	    throw new RuntimeException(e);
    //	}
    //	return cfg;
    //    }
    private DynaProperty[] propertiesMIP = { new DynaProperty("portaleId", String.class), new DynaProperty("idServizio", String.class),
	    new DynaProperty("chiaveSegreta", String.class), new DynaProperty("urlServerPagamento", String.class),
	    new DynaProperty("componenteSimbolo", String.class), new DynaProperty("emailPortale", String.class),
	    new DynaProperty("windowMinutes", String.class), new DynaProperty("indirizzoProxy", String.class),
	    new DynaProperty("portaProxy", String.class) };
    private DynaClass mipCfgDynaClass = new BasicDynaClass("MipCfgDC", null, propertiesMIP);
}
