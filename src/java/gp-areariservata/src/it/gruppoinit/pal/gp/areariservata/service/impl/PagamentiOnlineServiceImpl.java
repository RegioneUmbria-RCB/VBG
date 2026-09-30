package it.gruppoinit.pal.gp.areariservata.service.impl;

import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
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
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import it.gruppoinit.pal.gp.areariservata.schema.pagamenti.PaymentDataType;
import it.gruppoinit.pal.gp.areariservata.service.PagamentiOnlineService;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.domain.FoArjDomande;
import it.gruppoinit.pal.gp.core.domain.FoArjDomandeOneri;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.exception.InvalidConfigurationException;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.AnagrafeService;
import it.gruppoinit.pal.gp.core.service.ConfigurazioneService;
import it.gruppoinit.pal.gp.core.service.FoArjDomandeOneriService;
import it.gruppoinit.pal.gp.core.service.FoArjDomandeService;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.pal.gp.core.ws.client.FileConverterWsClient;

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
    private FoArjDomandeService foArjDomandeService;
    private FoArjDomandeOneriService foArjDomandeOneriService;

    @Autowired
    public void setConfigurazioneService(ConfigurazioneService configurazioneService) {

	this.configurazioneService = configurazioneService;
    }

    @Autowired
    public void setAnagrafeService(AnagrafeService anagrafeService) {

	this.anagrafeService = anagrafeService;
    }

    @Autowired
    public void setFoArjDomandeService(FoArjDomandeService foArjDomandeService) {

	this.foArjDomandeService = foArjDomandeService;
    }

    @Autowired
    public void setOggettiService(OggettiService oggettiService) {

	this.oggettiService = oggettiService;
    }

    @Autowired
    public void setVerticalizzazioniService(VerticalizzazioniService verticalizzazioniService) {

	this.verticalizzazioniService = verticalizzazioniService;
    }

    @Autowired
    public void setFoArjDomandeOneriService(FoArjDomandeOneriService foArjDomandeOneriService) {

	this.foArjDomandeOneriService = foArjDomandeOneriService;
    }

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

	TIPO_PAGAMENTO tp = findTipoPagamento();
	if (tp != null) {
	    FoArjDomande fad = foArjDomandeService.findById(new PkId(codiceDomanda));
	    switch (tp) {
	    case PAGAMENTI_MIP_RPCSUAP:
		if (log.isDebugEnabled()) {
		    log.debug("calculateUrlPagamentoOnline# pagamenti online di tipo PAGAMENTI_MIP_RPCSUAP");
		}
		return mipPaymentRequest(fad, currentAppPath, emailPagamentoOnline);
	    }
	}
	return null;
    }

    private String getProp(DynaBean d, String property) {

	String result = (String) d.get(property);
	return StringUtils.defaultIfEmpty(result, "");
    }

    private String mipPaymentRequest(FoArjDomande domanda, String currentAppPath, String emailPagamentoOnline) {

	throw new NotImplementedException();
    }

    private String populateUrlBackMIP(FoArjDomande domanda, String currentAppPath) {

	return currentAppPath + "/nuovaistanzaoneri/pagamentiOnlineBack.htm";
    }

    private String populateImportoMIP(FoArjDomande domanda) {

	List<FoArjDomandeOneri> oneri = foArjDomandeOneriService.findByIdDomanda(domanda.getId().getCodice());
	String importoOnere = "000";
	BigDecimal totaleImporti = BigDecimal.ZERO;
	for (FoArjDomandeOneri fado : oneri) {
	    if (BooleanUtils.isTrue(fado.getFlagOnline())) {
		if (!BooleanUtils.isTrue(fado.getFlagStato())) {
		    totaleImporti = totaleImporti.add(fado.getImporto());
		}
	    }
	}
	if (totaleImporti.compareTo(BigDecimal.ZERO) > 0) {
	    totaleImporti = totaleImporti.setScale(2, BigDecimal.ROUND_DOWN);
	    DecimalFormat df = new DecimalFormat();
	    df.setMaximumFractionDigits(2);
	    df.setMinimumFractionDigits(2);
	    df.setGroupingUsed(false);
	    String result = df.format(totaleImporti);
	    result = result.replace(".", "");
	    return result.replace(",", "");
	}
	return importoOnere;
    }

    private String populateNumeroOperazioneMIP(FoArjDomande domanda) {

	List<FoArjDomandeOneri> oneri = foArjDomandeOneriService.findByIdDomanda(domanda.getId().getCodice());
	String numerooperazione = domanda.getId().getCodice() + "-" + Utilities.getToday("MMddHHmmssS");
	for (FoArjDomandeOneri fado : oneri) {
	    if (BooleanUtils.isTrue(fado.getFlagOnline())) {
		if (!BooleanUtils.isTrue(fado.getFlagStato())) {
		    fado.setIdNumOperazOnline(numerooperazione);
		    foArjDomandeOneriService.update(fado);
		}
	    }
	}
	return numerooperazione;
    }

    //    private String populateUrlNotificaMIP(FoArjDomande domanda, String currentAppPath, String numeroOrdine) {
    //
    //	String qs = "idcomunealias=" + ORMHelper.getIdcomuneAlias() + "&amp;software=" + ORMHelper.getIdcomuneAlias() + "&amp;numOrdine="
    //		+ numeroOrdine + "&amp;ts_=" + System.currentTimeMillis();
    //	qs = FileUtils.getLinkForFile(qs);
    //	return currentAppPath + "/servlets/pagamenti?" + qs;
    //    }
    private String populateUrlErroreMIP(FoArjDomande domanda, String currentAppPath) {

	return currentAppPath + "/nuovaistanzaoneri/pagamentiOnlineErr.htm";
    }

    private String populateUrlRitornoMIP(FoArjDomande domanda, String currentAppPath) {

	return currentAppPath + "/nuovaistanzaoneri/pagamentiOnlineOK.htm";
    }

    private String getParametroMip(String nomeparametro, boolean obbligatorio) {

	Verticalizzazioniparametri p = verticalizzazioniService.getVerticalizzazioniparametri("PAGAMENTI_MIP_RPCSUAP", nomeparametro);
	if (p != null) {
	    if (StringUtils.isNotBlank(p.getValore())) {
		return p.getValore().trim();
	    }
	}
	if (obbligatorio) {
	    log.error(
		    "getParametroMip# Non sono stati configurati correttamente i parametri per il sistema di pagamento online: valore mancante del parametro " +
		      nomeparametro);
	    throw new InvalidConfigurationException(
		    " Non sono stati configurati correttamente i parametri per il sistema di pagamento online: valore mancante del parametro " +
						    nomeparametro);
	} else {
	    return "";
	}
    }

    @Override
    public void updateRientroPagamenti(Integer codiceDomanda, Map<String, String[]> requestParamMap) {

	TIPO_PAGAMENTO tp = findTipoPagamento();
	if (tp != null) {
	    FoArjDomande fad = foArjDomandeService.findById(new PkId(codiceDomanda));
	    switch (tp) {
	    case PAGAMENTI_MIP_RPCSUAP:
		if (log.isDebugEnabled()) {
		    log.debug("updateRientroPagamenti# pagamenti online di tipo PAGAMENTI_MIP_RPCSUAP");
		}
		updateRientropagamentiMIP(fad, requestParamMap);
	    }
	}
    }

    private void updateRientropagamentiMIP(FoArjDomande domanda, Map<String, String[]> requestParamMap) {

	throw new NotImplementedException();
    }

    @Override
    public void updateStatoPagamenti(Integer codiceDomanda, Integer identificativo, Map<String, String[]> requestParamMap) {

	throw new NotImplementedException();
    }

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

    private String getValueFromXml(String xml, String xpathParam)
	    throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {

	InputSource source = new InputSource(new StringReader(xml));
	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	DocumentBuilder db = dbf.newDocumentBuilder();
	Document document = db.parse(source);
	XPathFactory xpathFactory = XPathFactory.newInstance();
	XPath xpath = xpathFactory.newXPath();
	String result = xpath.evaluate(xpathParam, document);
	return result;
    }

    private DynaBean populateMIPCfg(FoArjDomande domanda) {

	DynaBean cfg = null;
	try {
	    cfg = mipCfgDynaClass.newInstance();
	    String portaleId = getParametroMip("PORTALEID", true);
	    cfg.set("portaleId", portaleId);
	    String idServizio = getParametroMip("IDSERVIZIO", true);
	    cfg.set("idServizio", idServizio);
	    String chiaveSegreta = getParametroMip("PASSWORD_CHIAMATE", true);
	    cfg.set("chiaveSegreta", chiaveSegreta);
	    String urlServerPagamento = getParametroMip("URLSERVERPAGAMENTO", true);
	    cfg.set("urlServerPagamento", urlServerPagamento);
	    String componenteSimbolo = getParametroMip("IDENTIFICATIVO_COMPONENTE", true);
	    cfg.set("componenteSimbolo", componenteSimbolo);
	    String emailPortale = getParametroMip("EMAIL_PORTALE", false);
	    cfg.set("emailPortale", emailPortale);
	    String windowMinutes = getParametroMip("WINDOW_MINUTES", false);
	    cfg.set("windowMinutes", windowMinutes);
	    String indirizzoProxy = getParametroMip("INDIRIZZOPROXY", false);
	    cfg.set("indirizzoProxy", indirizzoProxy);
	    String portaProxy = getParametroMip("PORTAPROXY", false);
	    cfg.set("portaProxy", portaProxy);
	    if (log.isDebugEnabled()) {
		log.debug("populateMIPCfg# ConfigurazioniCaricate: {}", ReflectionToStringBuilder.toString(cfg, ToStringStyle.MULTI_LINE_STYLE));
	    }
	} catch (IllegalAccessException e) {
	    e.printStackTrace();
	    throw new RuntimeException(e);
	} catch (InstantiationException e) {
	    e.printStackTrace();
	    throw new RuntimeException(e);
	}
	return cfg;
    }

    private DynaProperty[] propertiesMIP = { new DynaProperty("portaleId", String.class), new DynaProperty("idServizio", String.class),
	    new DynaProperty("chiaveSegreta", String.class), new DynaProperty("urlServerPagamento", String.class),
	    new DynaProperty("componenteSimbolo", String.class), new DynaProperty("emailPortale", String.class),
	    new DynaProperty("windowMinutes", String.class), new DynaProperty("indirizzoProxy", String.class),
	    new DynaProperty("portaProxy", String.class) };
    private DynaClass mipCfgDynaClass = new BasicDynaClass("MipCfgDC", null, propertiesMIP);
}
