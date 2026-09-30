package it.gruppoinit.pal.gp.backoffice.web;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.rpc.ServiceException;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang.StringUtils;
import org.openspcoop.pdd.services.SPCoopException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import it.gruppoinit.fileconverter.ConvertRequest;
import it.gruppoinit.fileconverter.ConvertResponse;
import it.gruppoinit.fileconverter.definitions.Fileconverter;
import it.gruppoinit.fileconverter.definitions.FileconverterServiceLocator;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.StpEndoTipo1;
import it.gruppoinit.pal.gp.core.domain.StpEndoTipo2;
import it.gruppoinit.pal.gp.core.domain.helper.StpInventarioprocedimentiComparator;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.domain.web.ChiaveValoreBean;
import it.gruppoinit.pal.gp.core.domain.web.StpCommand;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.service.AlberoprocService;
import it.gruppoinit.pal.gp.core.service.CartDisponibilitaDizionarioService;
import it.gruppoinit.pal.gp.core.service.CartInvioDizionarioService;
import it.gruppoinit.pal.gp.core.service.CartProxyService;
import it.gruppoinit.pal.gp.core.service.ContenttypesService;
import it.gruppoinit.pal.gp.core.service.InventarioprocedimentiService;
import it.gruppoinit.pal.gp.core.service.StpEndoTipo1Service;
import it.gruppoinit.pal.gp.core.service.StpEndoTipo2Service;
import it.gruppoinit.sigepro.cart.schema.rfcsuap.AllegatoRichiesto;
import it.gruppoinit.sigepro.cart.schema.rfcsuap.ElencoAllegatiRichiesti;
import it.gruppoinit.sigepro.cart.schema.rfcsuap.ElencoQuadri.Quadro;
import it.gruppoinit.sigepro.cart.schema.rfcsuap.InvioSchedaEndoTipo1;
import it.gruppoinit.sigepro.cart.schema.rfcsuap.InvioSchedaEndoTipo2;
import it.gruppoinit.sigepro.cart.schema.rfcsuap.Tabella;

/**
 * Controller per la visualizzazione delle schede di spiegazione per gli Endo 1 e 2.
 * 
 * @author francescop
 * 
 */
@Controller
@SessionAttributes(value = { "stpCommand", "cartPropertiesBean" })
public class StpController extends BaseController<StpEndoTipo1> {

    private static Logger log = LoggerFactory.getLogger(StpController.class);
    @Autowired
    private CartProxyService cartProxyService;
    @Autowired
    private StpEndoTipo2Service stpEndoTipo2Service;
    @Autowired
    private AlberoprocService alberoprocService;
    @Autowired
    private InventarioprocedimentiService inventarioprocedimentiService;
    @Autowired
    private OggettiService oggettiService;
    @Autowired
    private StpEndoTipo1Service stpEndoTipo1Service;
    @Autowired
    private ContenttypesService contenttypesService;
    @Autowired
    private CartDisponibilitaDizionarioService cartDisponibilitaDizionarioVbgService;
    @Autowired
    private CartInvioDizionarioService cartInvioDizionarioService;

    @RequestMapping
    public String ajaxSchedaSpiegazioneEndo2(@RequestParam("codice") Integer codice, HttpServletRequest request, HttpServletResponse response) {

	request.setAttribute("chiamataEsterna", true);
	return schedaSpiegazioneEndo2(codice, request, response);
    }

    @SuppressWarnings("unchecked")
    @RequestMapping
    public String schedaSpiegazioneEndo2(@RequestParam("codice") Integer codice, HttpServletRequest request, HttpServletResponse response) {

	if (log.isDebugEnabled()) {
	    log.debug("StpController schedaSpiegazioneEndo2: Codice Endo= " + codice);
	}
	StpCommand stpCommand = new StpCommand();
	StpEndoTipo2 stpEndoTipo2 = stpEndoTipo2Service.findbyStpCodice(codice, StpEndoTipo2Service.TIPO_ENDO);
	Alberoproc alberoproc = stpEndoTipo2.getAlberoproc();
	if (alberoproc != null) {
	    /*
	     * Viene settato il software poichè la funzionalità non viene chiamata utilizzando il menù
	     */
	    ORMHelper.setSoftware(alberoproc.getSoftware().getCodice());
	    stpCommand.setNomeAttivita(alberoproc.getScDescrizione());
	    String scCodice = alberoproc.getScCodice();
	    String scCodicePadre = scCodice.substring(0, scCodice.length() - 2);
	    Alberoproc alberoprocPadre = alberoprocService.findByScCodice(scCodicePadre);
	    if (alberoprocPadre != null) {
		stpCommand.setTipologiaEndoprocedimento(alberoprocPadre.getScDescrizione());
	    }
	}
	InvioSchedaEndoTipo2 invioSchedaEndoTipo2 = null;
	Oggetti oggetti = oggettiService.findById(new PkId(stpEndoTipo2.getOggetti().getId().getCodice()));
	if (oggetti != null && oggetti.getId() != null && oggetti.getId().getCodice() != null) {
	    String xmlString = "";
	    try {
		xmlString = new String(oggetti.getOggetto(), "UTF-8");
	    } catch (UnsupportedEncodingException e1) {
		log.error("ERRORE(UnsupportedEncoding) schedaSpiegazioneEndo2 in StpController: " + e1.getMessage());
	    }
	    StringWriter writer = new StringWriter();
	    writer.append(xmlString);
	    if (xmlString.startsWith("<?xml version")) {
		writer.getBuffer().replace(0, 38, "");
	    }
	    try {
		JAXBContext jaxbContext = JAXBContext.newInstance(InvioSchedaEndoTipo2.class);
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		byte[] content = null;
		content = writer.toString().getBytes("UTF-8");
		InputStream inputStream = new ByteArrayInputStream(content);
		XMLInputFactory inputFactory = XMLInputFactory.newInstance();
		XMLStreamReader xmlStreamReader = inputFactory.createXMLStreamReader(inputStream);
		invioSchedaEndoTipo2 = (InvioSchedaEndoTipo2) unmarshaller.unmarshal(xmlStreamReader);
	    } catch (Exception e) {
		log.error("ERRORE schedaSpiegazioneEndo2 in StpController: {}", e.getMessage());
		//throw new RuntimeException(e);
	    }
	}
	stpCommand.setInvioSchedaEndoTipo2(invioSchedaEndoTipo2);
	if (invioSchedaEndoTipo2 != null) {
	    List<ChiaveValoreBean<Inventarioprocedimenti, Boolean>> inventarioprocedimentisPrima = new ArrayList<ChiaveValoreBean<Inventarioprocedimenti, Boolean>>();
	    if (invioSchedaEndoTipo2.getParteRegionaleSchedaEndoTipo2().getElencoEndoRegionaliPrevistiPrima() != null) {
		List<Serializable> idEndo1ListPrima = invioSchedaEndoTipo2.getParteRegionaleSchedaEndoTipo2().getElencoEndoRegionaliPrevistiPrima()
			.getEndoTipo1AndEndoObbligatorio();
		for (int i = 0; i < idEndo1ListPrima.size(); i += 2) {
		    Serializable idEndo1 = idEndo1ListPrima.get(i);
		    Serializable obbligatorio = idEndo1ListPrima.get(i + 1);
		    if (idEndo1 instanceof BigInteger) {
			StpEndoTipo1 stpEndoTipo1 = stpEndoTipo1Service.findbyStpCodice(((BigInteger) idEndo1).intValue());
			if (stpEndoTipo1 != null) {
			    Inventarioprocedimenti inventarioprocedimenti = stpEndoTipo1.getInventarioprocedimenti();
			    ChiaveValoreBean<Inventarioprocedimenti, Boolean> record = new ChiaveValoreBean<Inventarioprocedimenti, Boolean>();
			    record.setChiave(inventarioprocedimenti);
			    if (obbligatorio != null) {
				if (obbligatorio instanceof Boolean) {
				    record.setValore((Boolean) obbligatorio);
				} else {
				    record.setValore(Boolean.FALSE);
				}
			    } else {
				record.setValore(Boolean.FALSE);
			    }
			    inventarioprocedimentisPrima.add(record);
			}
		    }
		}
		Collections.sort(inventarioprocedimentisPrima, new StpInventarioprocedimentiComparator());
	    }
	    stpCommand.setInventarioprocedimentisPrima(inventarioprocedimentisPrima);
	    List<ChiaveValoreBean<Inventarioprocedimenti, Boolean>> inventarioprocedimentisDopo = new ArrayList<ChiaveValoreBean<Inventarioprocedimenti, Boolean>>();
	    if (invioSchedaEndoTipo2.getParteRegionaleSchedaEndoTipo2().getElencoEndoRegionaliPrevistiDopo() != null) {
		List<Serializable> idEndo1ListDopo = invioSchedaEndoTipo2.getParteRegionaleSchedaEndoTipo2().getElencoEndoRegionaliPrevistiDopo()
			.getEndoTipo1AndEndoObbligatorio();
		for (int i = 0; i < idEndo1ListDopo.size(); i += 2) {
		    Serializable idEndo1 = idEndo1ListDopo.get(i);
		    Serializable obbligatorio = idEndo1ListDopo.get(i + 1);
		    if (idEndo1 instanceof BigInteger) {
			StpEndoTipo1 stpEndoTipo1 = stpEndoTipo1Service.findbyStpCodice(((BigInteger) idEndo1).intValue());
			if (stpEndoTipo1 != null) {
			    Inventarioprocedimenti inventarioprocedimenti = stpEndoTipo1.getInventarioprocedimenti();
			    ChiaveValoreBean<Inventarioprocedimenti, Boolean> record = new ChiaveValoreBean<Inventarioprocedimenti, Boolean>();
			    record.setChiave(inventarioprocedimenti);
			    if (obbligatorio != null) {
				if (obbligatorio instanceof Boolean) {
				    record.setValore((Boolean) obbligatorio);
				} else {
				    record.setValore(Boolean.FALSE);
				}
			    } else {
				record.setValore(Boolean.FALSE);
			    }
			    inventarioprocedimentisDopo.add(record);
			}
		    }
		}
		Collections.sort(inventarioprocedimentisDopo, new StpInventarioprocedimentiComparator());
	    }
	    stpCommand.setInventarioprocedimentisDopo(inventarioprocedimentisDopo);
	    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	    if (invioSchedaEndoTipo2.getParteRegionaleSchedaEndoTipo2().getDataInizioValidita() != null) {
		stpCommand.setDataInizioValidita(
			sdf.format(invioSchedaEndoTipo2.getParteRegionaleSchedaEndoTipo2().getDataInizioValidita().toGregorianCalendar().getTime()));
	    }
	    if (invioSchedaEndoTipo2.getParteRegionaleSchedaEndoTipo2().getDataFineValidita() != null) {
		stpCommand.setDataFineValidita(
			sdf.format(invioSchedaEndoTipo2.getParteRegionaleSchedaEndoTipo2().getDataFineValidita().toGregorianCalendar().getTime()));
	    }
	    request.setAttribute("stpCommand", stpCommand);
	    // List<StpTipologieEndo1> listaAdempimenti = stpTipologieEndo1Service.findAll(null, null);
	    Map<String, String> adempimentiMap = getAdempimentiNormativeMap();
	    request.setAttribute("adempimentiMap", adempimentiMap);
	    return "stp/schedaSpiegazioneEndo2";
	} else {
	    try {
		response.getWriter().write("");
	    } catch (IOException e) {
		log.error("ERRORE schedaSpiegazioneEndo2", e);
	    }
	    return null;
	}
    }

    public static Map<String, String> getAdempimentiNormativeMap() {

	Map<String, String> a = new HashMap<String, String>();
	a.put("1", "Esercizio di attività");
	a.put("2", "Edilizi");
	a.put("3", "Ambientali");
	a.put("4", "Igienico-sanitari");
	a.put("5", "Prevenzione incendi");
	a.put("6", "Sicurezza");
	a.put("7", "Esercizio di attività");
	a.put("8", "Denuncia inizio attività per preparazione e/o somministrazione di alimenti e bevande");
	a.put("9", "Altri endoprocedimenti da inserire nel caso specifico");
	return a;
    }

    @RequestMapping
    public String ajaxSchedaSpiegazioneEndo1(@RequestParam("codice") Integer codice, HttpServletRequest request, HttpServletResponse response) {

	request.setAttribute("chiamataEsterna", true);
	return schedaSpiegazioneEndo1(codice, request, response);
    }

    @SuppressWarnings("unchecked")
    @RequestMapping
    public String schedaSpiegazioneEndo1(@RequestParam("codice") Integer codice, HttpServletRequest request, HttpServletResponse response) {

	if (log.isDebugEnabled()) {
	    log.debug("StpController schedaSpiegazioneEndo2: Codice Endo= " + codice);
	}
	StpCommand stpCommand = new StpCommand();
	StpEndoTipo1 stpEndoTipo1 = stpEndoTipo1Service.findbyStpCodice(codice);
	Inventarioprocedimenti inventarioprocedimenti = stpEndoTipo1.getInventarioprocedimenti();
	/*
	 * Viene settato il software poichè la funzionalità non viene chiamata utilizzando il menù
	 */
	ORMHelper.setSoftware(inventarioprocedimenti.getSoftware().getCodice());
	stpCommand.setInventarioprocedimenti(inventarioprocedimenti);
	Oggetti oggetti = oggettiService.findById(new PkId(stpEndoTipo1.getOggetti().getId().getCodice()));
	String xmlString = "";
	try {
	    xmlString = new String(oggetti.getOggetto(), "UTF-8");
	} catch (UnsupportedEncodingException e1) {
	    log.error("ERRORE(UnsupportedEncoding) schedaSpiegazioneEndo1 in StpController: " + e1.getMessage());
	}
	StringWriter writer = new StringWriter();
	writer.append(xmlString);
	writer.getBuffer().replace(0, 38, "");
	InvioSchedaEndoTipo1 invioSchedaEndoTipo1 = null;
	try {
	    JAXBContext jaxbContext = JAXBContext.newInstance(InvioSchedaEndoTipo1.class);
	    Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
	    byte[] content = null;
	    content = writer.toString().getBytes("UTF-8");
	    InputStream inputStream = new ByteArrayInputStream(content);
	    XMLInputFactory inputFactory = XMLInputFactory.newInstance();
	    XMLStreamReader xmlStreamReader = inputFactory.createXMLStreamReader(inputStream);
	    invioSchedaEndoTipo1 = (InvioSchedaEndoTipo1) unmarshaller.unmarshal(xmlStreamReader);
	} catch (Exception e) {
	    log.error("ERRORE schedaSpiegazioneEndo1 in StpController: {}", e.getMessage());
	    throw new RuntimeException(e);
	}
	stpCommand.setInvioSchedaEndoTipo1(invioSchedaEndoTipo1);
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	if (!EntityUtils.isNestedPropertyBlank(invioSchedaEndoTipo1, "parteRegionaleSchedaEndoTipo1.dataInizioValidita")) {
	    stpCommand.setDataInizioValidita(
		    sdf.format(invioSchedaEndoTipo1.getParteRegionaleSchedaEndoTipo1().getDataInizioValidita().toGregorianCalendar().getTime()));
	}
	if (!EntityUtils.isNestedPropertyBlank(invioSchedaEndoTipo1, "parteRegionaleSchedaEndoTipo1.dataFineValidita")) {
	    stpCommand.setDataFineValidita(
		    sdf.format(invioSchedaEndoTipo1.getParteRegionaleSchedaEndoTipo1().getDataFineValidita().toGregorianCalendar().getTime()));
	}
	request.setAttribute("codice", codice);
	request.setAttribute("stpCommand", stpCommand);
	Map<String, String> adempimentiMap = getAdempimentiNormativeMap();
	request.setAttribute("adempimentiMap", adempimentiMap);
	return "stp/schedaSpiegazioneEndo1";
    }

    @SuppressWarnings("unchecked")
    @RequestMapping
    public String downloadAllegatoQuadro(@RequestParam("codice") Integer codice, @RequestParam("nomefile") String nomefile,
	    @RequestParam("tipo") String tipo, HttpServletRequest request, HttpServletResponse response) {

	StpEndoTipo1 stpEndoTipo1 = stpEndoTipo1Service.findbyStpCodice(codice);
	Oggetti oggetti = oggettiService.findById(new PkId(stpEndoTipo1.getOggetti().getId().getCodice()));
	String xmlString = "";
	try {
	    xmlString = new String(oggetti.getOggetto(), "UTF-8");
	} catch (UnsupportedEncodingException e1) {
	    log.error("ERRORE(UnsupportedEncoding) schedaSpiegazioneEndo1 in StpController: " + e1.getMessage());
	}
	StringWriter writer = new StringWriter();
	writer.append(xmlString);
	writer.getBuffer().replace(0, 38, "");
	InvioSchedaEndoTipo1 invioSchedaEndoTipo1 = null;
	try {
	    JAXBContext jaxbContext = JAXBContext.newInstance(InvioSchedaEndoTipo1.class);
	    Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
	    byte[] content = null;
	    content = writer.toString().getBytes("UTF-8");
	    InputStream inputStream = new ByteArrayInputStream(content);
	    XMLInputFactory inputFactory = XMLInputFactory.newInstance();
	    XMLStreamReader xmlStreamReader = inputFactory.createXMLStreamReader(inputStream);
	    invioSchedaEndoTipo1 = (InvioSchedaEndoTipo1) unmarshaller.unmarshal(xmlStreamReader);
	} catch (Exception e) {
	    log.error("ERRORE schedaSpiegazioneEndo1 in StpController: {}", e.getMessage());
	    throw new RuntimeException(e);
	}
	List<Quadro> list = new ArrayList<Quadro>();
	byte[] fileByte = null;
	if (tipo.equals(WebConstants.STP_QUADRO5)) {
	    list = invioSchedaEndoTipo1.getParteRegionaleSchedaEndoTipo1().getElencoQuadriStandard5().getQuadro();
	    for (Quadro quadro : list) {
		if (quadro.getTestoQuadro().getNomeFile().equalsIgnoreCase(nomefile)) {
		    fileByte = quadro.getTestoQuadro().getDatiFile();
		}
	    }
	} else if (tipo.equals(WebConstants.STP_QUADRO5_ALLEGATI)) {
	    list = invioSchedaEndoTipo1.getParteRegionaleSchedaEndoTipo1().getElencoQuadriStandard5().getQuadro();
	    for (Quadro quadro : list) {
		ElencoAllegatiRichiesti elencoAllegatiRichiesti = quadro.getElencoAllegatiRichiestiQuadro();
		if (elencoAllegatiRichiesti != null) {
		    for (AllegatoRichiesto allegatoRichiesto : elencoAllegatiRichiesti.getAllegatoRichiesto()) {
			if (allegatoRichiesto.getTemplateAllegato().getNomeFile().equalsIgnoreCase(nomefile)) {
			    fileByte = allegatoRichiesto.getTemplateAllegato().getDatiFile();
			}
		    }
		}
	    }
	}
	if (tipo.equals(WebConstants.STP_QUADRO6)) {
	    list = invioSchedaEndoTipo1.getParteRegionaleSchedaEndoTipo1().getElencoQuadriStandard6().getQuadro();
	    for (Quadro quadro : list) {
		if (quadro.getTestoQuadro().getNomeFile().equalsIgnoreCase(nomefile)) {
		    fileByte = quadro.getTestoQuadro().getDatiFile();
		}
	    }
	} else if (tipo.equals(WebConstants.STP_QUADRO6_ALLEGATI)) {
	    list = invioSchedaEndoTipo1.getParteRegionaleSchedaEndoTipo1().getElencoQuadriStandard6().getQuadro();
	    for (Quadro quadro : list) {
		ElencoAllegatiRichiesti elencoAllegatiRichiesti = quadro.getElencoAllegatiRichiestiQuadro();
		if (elencoAllegatiRichiesti != null) {
		    for (AllegatoRichiesto allegatoRichiesto : elencoAllegatiRichiesti.getAllegatoRichiesto()) {
			if (allegatoRichiesto.getTemplateAllegato().getNomeFile().equalsIgnoreCase(nomefile)) {
			    fileByte = allegatoRichiesto.getTemplateAllegato().getDatiFile();
			}
		    }
		}
	    }
	}
	String mimetype = contenttypesService.findMimeTypeByFileName(nomefile);
	response.setContentType(mimetype);
	response.setHeader("Content-Disposition", "attachment;filename=\"" + nomefile + "\"");
	response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
	response.setHeader("Pragma", "public");
	response.setDateHeader("Expires", (System.currentTimeMillis() + 1000));
	response.setContentLength(fileByte.length);
	try {
	    ServletOutputStream out = response.getOutputStream();
	    out.write(fileByte);
	    out.flush();
	} catch (IOException e) {
	    e.printStackTrace();
	}
	return null;
    }

    @RequestMapping
    public String downloadAllegatoTabella(@RequestParam("codice") Integer codice, @RequestParam("nomeFile") String nomeFile,
	    HttpServletRequest request, HttpServletResponse response) {

	StpEndoTipo2 stpEndoTipo2 = stpEndoTipo2Service.findbyStpCodice(codice, StpEndoTipo2Service.TIPO_ENDO);
	Oggetti oggetti = oggettiService.findById(new PkId(stpEndoTipo2.getOggetti().getId().getCodice()));
	String xmlString = "";
	try {
	    xmlString = new String(oggetti.getOggetto(), "UTF-8");
	} catch (UnsupportedEncodingException e1) {
	    log.error("ERRORE(UnsupportedEncoding) schedaSpiegazioneEndo1 in StpController: " + e1.getMessage());
	}
	StringWriter writer = new StringWriter();
	writer.append(xmlString);
	writer.getBuffer().replace(0, 38, "");
	InvioSchedaEndoTipo2 invioSchedaEndoTipo2 = null;
	try {
	    JAXBContext jaxbContext = JAXBContext.newInstance(InvioSchedaEndoTipo2.class);
	    Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
	    byte[] content = null;
	    content = writer.toString().getBytes("UTF-8");
	    InputStream inputStream = new ByteArrayInputStream(content);
	    XMLInputFactory inputFactory = XMLInputFactory.newInstance();
	    XMLStreamReader xmlStreamReader = inputFactory.createXMLStreamReader(inputStream);
	    invioSchedaEndoTipo2 = (InvioSchedaEndoTipo2) unmarshaller.unmarshal(xmlStreamReader);
	} catch (Exception e) {
	    log.error("ERRORE schedaSpiegazioneEndo1 in StpController: {}", e.getMessage());
	    throw new RuntimeException(e);
	}
	Tabella tabella = invioSchedaEndoTipo2.getParteRegionaleSchedaEndoTipo2().getTabella();
	byte[] fileByte = tabella.getDichiarazioni().getDatiFile();
	String mimetype = tabella.getDichiarazioni().getContentType();
	response.setContentType(mimetype);
	response.setHeader("Content-Disposition", "attachment;filename=\"" + tabella.getDichiarazioni().getNomeFile() + "\"");
	response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
	response.setHeader("Pragma", "public");
	response.setDateHeader("Expires", (System.currentTimeMillis() + 1000));
	response.setContentLength(fileByte.length);
	try {
	    ServletOutputStream out = response.getOutputStream();
	    out.write(fileByte);
	    out.flush();
	} catch (IOException e) {
	    e.printStackTrace();
	}
	return null;
    }

    @RequestMapping
    public String pannellocontrollo(Model model, @RequestParam("tipo") String tipo, HttpServletRequest request, HttpServletResponse response) {

	String codice = request.getParameter("codice");
	cartProxyService.aggiornaConfigurazioni();
	if (log.isDebugEnabled()) {
	    log.debug("StpController pannellocontrollo: codice= " + codice + " e tipo= " + tipo);
	}
	if (tipo.equalsIgnoreCase(CartProxyService.TipoEndo.TIPO_1.toString())) {
	    model.addAttribute("view", "true");
	    model.addAttribute("endo", "false");
	    model.addAttribute("codice", Integer.parseInt(codice));
	}
	if (tipo.equalsIgnoreCase(CartProxyService.TipoEndo.TIPO_2.toString())) {
	    StpEndoTipo2 stpEndoTipo2 = stpEndoTipo2Service.findbyAlberoproc(Integer.parseInt(codice));
	    if (stpEndoTipo2 != null) {
		if (stpEndoTipo2.getTipo().equals(StpEndoTipo2Service.TIPO_ENDO)) {
		    model.addAttribute("view", "true");
		    model.addAttribute("endo", "true");
		} else {
		    model.addAttribute("view", "false");
		    model.addAttribute("endo", "false");
		}
	    } else {
		model.addAttribute("endo", "false");
		model.addAttribute("view", "false");
	    }
	    model.addAttribute("codice", Integer.parseInt(codice));
	}
	if (tipo.equalsIgnoreCase(CartProxyService.TipoEndo.CONTROLLO.toString())) {
	    model.addAttribute("view", "true");
	    model.addAttribute("endo", "false");
	}
	if (codice != null) {
	    model.addAttribute("codice", Integer.parseInt(codice));
	}
	model.addAttribute("tipo", tipo);
	return "stp/pannellocontrollo";
    }

    @RequestMapping
    public String verificaSchedeEndo(Model model, HttpServletRequest request, HttpServletResponse response) {

	List<StpEndoTipo1> schedeendo1 = stpEndoTipo1Service.verificaSchedeEndo1();
	boolean export = createJMesaExport(request, response, schedeendo1);
	if (export) {
	    return null;
	}
	List<StpEndoTipo2> schedeendo2 = stpEndoTipo2Service.verificaSchedeEndo2();
	export = createJMesaExport(request, response, schedeendo2);
	if (export) {
	    return null;
	}
	if (log.isDebugEnabled()) {
	    log.debug("StpController verificaSchedeEndo");
	}
	model.addAttribute("listaSchede1", schedeendo1);
	model.addAttribute("listaSchede2", schedeendo2);
	return "stp/listaschedeendo";
    }

    @RequestMapping
    public String richiediDownloadSchede(@RequestParam("endoTipo") String endoTipo, HttpServletRequest request, HttpServletResponse response)
	    throws IOException {

	StringBuffer buff = new StringBuffer();
	try {
	    if (endoTipo.equalsIgnoreCase(CartProxyService.TipoEndo.TIPO_1.toString())) {
		List<StpEndoTipo1> schedeendo1 = stpEndoTipo1Service.verificaSchedeEndo1();
		for (StpEndoTipo1 stpEndoTipo1 : schedeendo1) {
		    cartProxyService.inviaRichiestaScheda(endoTipo, CartProxyService.TipoRichiesta.INVIO.toString(),
			    stpEndoTipo1.getCodiceStp().toString());
		}
	    }
	    if (endoTipo.equalsIgnoreCase(CartProxyService.TipoEndo.TIPO_2.toString())) {
		List<StpEndoTipo2> schedeendo2 = stpEndoTipo2Service.verificaSchedeEndo2();
		for (StpEndoTipo2 stpEndoTipo2 : schedeendo2) {
		    cartProxyService.inviaRichiestaScheda(endoTipo, CartProxyService.TipoRichiesta.INVIO.toString(),
			    stpEndoTipo2.getCodiceStp().toString());
		}
	    }
	} catch (Exception e) {
	    buff.append("KO");
	    log.error("ERRORE: StpController - richiediDownloadSchede");
	    return "redirect:verificaSchedeEndo.htm?status_msg=03";
	}
	return "redirect:verificaSchedeEndo.htm?status_msg=02";
    }

    @RequestMapping
    public void ajaxControlloMessaggi(@RequestParam("textToSearch") String textToSearch, HttpServletResponse response) throws IOException {

	String[] answer = null;
	String descrEcc = "Non ci sono messaggi.";
	StringBuffer buff = new StringBuffer("");
	if (textToSearch.equalsIgnoreCase(CartProxyService.MessaggiAzioni.DisponibilitaDizionario.toString())) {
	    try {
		answer = cartProxyService.getMessaggiPerServizio(CartProxyService.MessaggiAzioni.DisponibilitaDizionario);
	    } catch (Exception e) {
		gestisciEccezione(textToSearch, buff, e, descrEcc);
	    }
	}
	if (textToSearch.equalsIgnoreCase(CartProxyService.MessaggiAzioni.InvioDizionario.toString())) {
	    try {
		answer = cartProxyService.getMessaggiPerServizio(CartProxyService.MessaggiAzioni.InvioDizionario);
	    } catch (Exception e) {
		gestisciEccezione(textToSearch, buff, e, descrEcc);
	    }
	}
	if (textToSearch.equalsIgnoreCase(CartProxyService.MessaggiAzioni.DisponibilitaSchedaEC.toString())) {
	    try {
		answer = cartProxyService.getMessaggiPerServizio(CartProxyService.MessaggiAzioni.DisponibilitaSchedaEC);
	    } catch (Exception e) {
		gestisciEccezione(textToSearch, buff, e, descrEcc);
	    }
	}
	if (textToSearch.equalsIgnoreCase(CartProxyService.MessaggiAzioni.InvioSchedaEC.toString())) {
	    try {
		answer = cartProxyService.getMessaggiPerServizio(CartProxyService.MessaggiAzioni.InvioSchedaEC);
	    } catch (Exception e) {
		gestisciEccezione(textToSearch, buff, e, descrEcc);
	    }
	}
	if (textToSearch.equalsIgnoreCase(CartProxyService.MessaggiAzioni.InvioSchedaEP.toString())) {
	    try {
		answer = cartProxyService.getMessaggiPerServizio(CartProxyService.MessaggiAzioni.InvioSchedaEP);
	    } catch (Exception e) {
		gestisciEccezione(textToSearch, buff, e, descrEcc);
	    }
	}
	if (textToSearch.equalsIgnoreCase(CartProxyService.MessaggiAzioni.DisponibilitaSchedaEP.toString())) {
	    try {
		answer = cartProxyService.getMessaggiPerServizio(CartProxyService.MessaggiAzioni.DisponibilitaSchedaEP);
	    } catch (Exception e) {
		gestisciEccezione(textToSearch, buff, e, descrEcc);
	    }
	}
	if (answer == null) {
	    buff.append("KO-" + descrEcc);
	    response.getWriter().write(buff.toString());
	} else {
	    for (int i = 0; i < answer.length; i++) {
		buff.append("#");
		buff.append(answer[i]);
	    }
	    response.getWriter().write(buff.toString());
	}
    }

    /**
     * @param textToSearch
     * @param buff
     * @param e
     * @return
     */
    private void gestisciEccezione(String textToSearch, StringBuffer buff, Exception e, String descrEcc) {

	if (e instanceof SPCoopException) {
	    descrEcc = ((SPCoopException) e).getDescrizioneEccezione();
	} else {
	    descrEcc = e.getMessage();
	}
	log.error("ERRORE: StpController - controlloMessaggi '{}', errore: {} ", textToSearch, descrEcc);
	// answer = null;
    }

    @RequestMapping
    public void ajaxElaboraMessaggio(@RequestParam("code") String code, HttpServletResponse response) throws IOException {

	StringBuffer buff = new StringBuffer();
	try {
	    cartProxyService.elaboraMessaggio(code);
	    buff.append("OK");
	} catch (Exception e) {
	    buff.append("KO-" + e.getMessage());
	    log.error("ERRORE: StpController - elaboraMessaggio: IdMessaggio: {} \nErrore: {}", code, e.getMessage());
	    // throw new IOException(e);
	}
	response.getWriter().write(buff.toString());
    }

    @RequestMapping
    public void ajaxCancellaMessaggio(@RequestParam("code") String code, HttpServletResponse response) throws IOException {

	StringBuffer buff = new StringBuffer();
	try {
	    cartProxyService.deleteMessaggio(code);
	    buff.append("OK");
	} catch (Exception e) {
	    buff.append("KO");
	    log.error("ERRORE: StpController - cancellaMessaggio: IdMessaggio " + code);
	}
	response.getWriter().write(buff.toString());
    }

    @RequestMapping
    public void ajaxInvioDizionario(HttpServletResponse response) throws IOException {

	StringBuffer buff = new StringBuffer();
	try {
	    cartProxyService.inviaRichiestaDizionario();
	    buff.append("OK");
	} catch (Exception e) {
	    buff.append("KO");
	    log.error("ERRORE: StpController - invioDizionario {}", e.getMessage());
	}
	response.getWriter().write(buff.toString());
    }

    @RequestMapping
    public void testcart(HttpServletResponse response) throws IOException {

	try {
	    // cartRichiestaDizionarioService.inviaRichiestaDizionario();
	    String[] messages = cartDisponibilitaDizionarioVbgService.getAllMessagesId();
	    for (String message : messages) {
		cartDisponibilitaDizionarioVbgService.elaboraMessaggio(message);
	    }
	} catch (SPCoopException e) {
	    System.out.println(e);
	}
	try {
	    String[] messages = cartInvioDizionarioService.getAllMessagesId();
	    for (String message : messages) {
		cartInvioDizionarioService.elaboraMessaggio(message);
	    }
	} catch (SPCoopException e) {
	    System.out.println(e);
	}
    }

    @RequestMapping
    public void ajaxInvioScheda(@RequestParam("tiporichiesta") String tiporichiesta, @RequestParam("codice") String codice,
	    @RequestParam("tipo") String tipo, HttpServletResponse response) throws IOException {

	StringBuffer buff = new StringBuffer();
	try {
	    if (tipo.equalsIgnoreCase(CartProxyService.TipoEndo.TIPO_1.toString())) {
		Integer id = Integer.parseInt(codice);
		Inventarioprocedimenti inventarioprocedimenti = inventarioprocedimentiService.findById(new PkId(id));
		StpEndoTipo1 stpEndoTipo1 = stpEndoTipo1Service.findByInventarioProcedimenti(inventarioprocedimenti);
		if (stpEndoTipo1 != null) {
		    cartProxyService.inviaRichiestaScheda(tipo, tiporichiesta, stpEndoTipo1.getCodiceStp().toString());
		    buff.append("OK");
		} else {
		    buff.append("KO");
		}
	    }
	    if (tipo.equalsIgnoreCase(CartProxyService.TipoEndo.TIPO_2.toString())) {
		Integer id = Integer.parseInt(codice);
		StpEndoTipo2 stpEndoTipo2 = stpEndoTipo2Service.findbyAlberoproc(id);
		if (stpEndoTipo2 != null) {
		    cartProxyService.inviaRichiestaScheda(tipo, tiporichiesta, stpEndoTipo2.getCodiceStp().toString());
		    buff.append("OK");
		} else {
		    buff.append("KO");
		}
	    }
	} catch (Exception e) {
	    buff.append("KO");
	    log.error("ERRORE: StpController - invioScheda");
	}
	response.getWriter().write(buff.toString());
    }

    @RequestMapping
    public void ajaxCancellaTuttiMessaggi(HttpServletResponse response) throws IOException {

	StringBuffer buff = new StringBuffer();
	try {
	    cartProxyService.deleteTuttiMessaggi();
	    buff.append("OK");
	} catch (Exception e) {
	    buff.append("KO");
	    log.error("ERRORE: StpController - cancellaTuttiMessaggi");
	}
	response.getWriter().write(buff.toString());
    }

    @RequestMapping
    public void ajaxElaboraTuttiMessaggi(HttpServletResponse response) throws IOException {

	StringBuffer buff = new StringBuffer();
	try {
	    cartProxyService.elaboraTuttiMessaggi();
	    buff.append("OK");
	} catch (Exception e) {
	    buff.append("KO");
	    log.error("ERRORE: StpController - elaboraTuttiMessaggi");
	}
	response.getWriter().write(buff.toString());
    }

    @RequestMapping
    public void ajaxElaboraTuttiMessaggiTipo(@RequestParam("tipoMessaggio") String tipoMessaggio, HttpServletResponse response) throws IOException {

	StringBuffer buff = new StringBuffer();
	try {
	    cartProxyService.elaboraTuttiMessaggiPerServizio(cartProxyService.getAzione(tipoMessaggio));
	    buff.append("OK");
	} catch (Exception e) {
	    buff.append("KO");
	    log.error("ERRORE: StpController - ajaxElaboraTuttiMessaggiTipo: tipoMessaggio: {} \nErrore: {}", tipoMessaggio, e.getMessage());
	    throw new IOException(e);
	}
	response.getWriter().write(buff.toString());
    }

    @RequestMapping
    public void ajaxAggiornaDizionario(@RequestParam("code") String code, HttpServletResponse response) throws IOException {

	StringBuffer buff = new StringBuffer();
	try {
	    cartProxyService.aggiornaDizionario(code);
	    buff.append("OK");
	} catch (Exception e) {
	    buff.append("KO");
	    log.error("ERRORE: StpController - aggiornaDizionario");
	}
	response.getWriter().write(buff.toString());
    }

    @RequestMapping
    public void ajaxDownloadPDFSchedaSpiegazioneEndo2(@RequestParam("codice") Integer codice,
	    @RequestParam(value = "tipo", required = false) String tipo, HttpServletRequest request, HttpServletResponse response) {

	downloadSchedaSiegazione(codice, tipo, CartProxyService.TipoEndo.TIPO_2.toString(), request, response);
    }

    @RequestMapping
    public void ajaxDownloadPDFSchedaSpiegazioneEndo1(@RequestParam("codice") Integer codice,
	    @RequestParam(value = "tipo", required = false) String tipo, HttpServletRequest request, HttpServletResponse response) {

	downloadSchedaSiegazione(codice, tipo, CartProxyService.TipoEndo.TIPO_1.toString(), request, response);
    }

    /**
     * @param codice
     * @param tipo
     * @param request
     * @param response
     */
    private void downloadSchedaSiegazione(Integer codice, String tipo, String tipoEndo, HttpServletRequest request, HttpServletResponse response) {

	HttpClient client = new HttpClient();
	String thisServletUrl = request.getRequestURL().toString();
	if (log.isDebugEnabled()) {
	    log.debug("servletUrl: {}", thisServletUrl);
	}
	thisServletUrl = thisServletUrl.substring(0, thisServletUrl.lastIndexOf("/"));
	if (log.isDebugEnabled()) {
	    log.debug("base servletUrl: {}", thisServletUrl);
	}
	String urlSchedaSpiegazioneEndo = "/ajaxSchedaSpiegazioneEndo2.htm";
	if (tipoEndo.equalsIgnoreCase(CartProxyService.TipoEndo.TIPO_1.toString())) {
	    urlSchedaSpiegazioneEndo = "/ajaxSchedaSpiegazioneEndo1.htm";
	}
	String httpClientMethodCall = thisServletUrl +
		urlSchedaSpiegazioneEndo +
		"?codice=" +
		codice +
		"&" +
		WebConstants.TOKEN +
		"=" +
		(String) request.getSession().getAttribute(WebConstants.TOKEN) +
		"&" +
		WebConstants.SOFTWARE +
		"=" +
		ORMHelper.getSoftware();
	if (log.isDebugEnabled()) {
	    log.debug("httpClientMethodCall: {}", httpClientMethodCall);
	}
	GetMethod method = new GetMethod(httpClientMethodCall);
	method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(3, false));
	try {
	    // Execute the method.
	    int statusCode = client.executeMethod(method);
	    if (statusCode != HttpStatus.SC_OK) {
		log.error("Method failed: {}", method.getStatusLine());
	    }
	    // Read the response body.
	    InputStream responseBody = method.getResponseBodyAsStream();
	    String htmlDaStampare = convertStreamToString(responseBody);
	    if (log.isDebugEnabled()) {
		log.debug("htmlDaStampare: \n{}", htmlDaStampare);
	    }
	    htmlDaStampare = htmlDaStampare.substring(htmlDaStampare.indexOf("<body>"));
	    if (log.isDebugEnabled()) {
		log.debug("htmlDaStampare body: \n{}", htmlDaStampare);
	    }
	    FileconverterServiceLocator locator = new FileconverterServiceLocator();
	    URL fileConverterURL = new URL(WebConstants.getSecurityParamValue(WebConstants.SecurityParams.WSHOSTURL_FILECONVERTER));
	    Fileconverter port = locator.getfileconverterSoap11(fileConverterURL);
	    if (log.isDebugEnabled()) {
		log.debug("Fileconverter alla URL: {}", fileConverterURL);
	    }
	    ConvertRequest convertRequest = new ConvertRequest();
	    convertRequest.setToken((String) request.getSession().getAttribute(WebConstants.TOKEN));
	    convertRequest.setContent(htmlDaStampare);
	    convertRequest.setContentType("HTML");
	    if (StringUtils.isNotBlank(tipo)) {
		if (log.isDebugEnabled()) {
		    log.debug("Fileconverter tipo output: {}", tipo);
		}
		convertRequest.setConversionType(tipo);
	    } else {
		convertRequest.setConversionType("PDF");
	    }
	    if (log.isDebugEnabled()) {
		log.debug("Chiamata a Fileconverter");
	    }
	    ConvertResponse cResponse = port.convert(convertRequest);
	    if (log.isDebugEnabled()) {
		log.debug("Chiamata a Fileconverter effettuata");
		log.debug("ConvertResponse.mimeType: {}", cResponse.getMimeType());
		log.debug("ConvertResponse.fileName: {}", cResponse.getFileName());
		log.debug("ConvertResponse.contentLength: {}", cResponse.getBinaryData().length);
	    }
	    response.setHeader("Pragma", "public");
	    response.setHeader("Cache-Control", "max-age=0");
	    response.setHeader("Content-Disposition", "attachment; filename=" + cResponse.getFileName());
	    response.setHeader("Content-transfer-encoding", "binary");
	    response.setContentType(cResponse.getMimeType());
	    response.setContentLength(cResponse.getBinaryData().length);
	    ServletOutputStream out = response.getOutputStream();
	    out.write(cResponse.getBinaryData());
	    out.flush();
	} catch (HttpException e) {
	    log.error("Fatal protocol violation: {}", e.getMessage());
	    throw new RuntimeException(e.getMessage());
	} catch (IOException e) {
	    log.error("Fatal transport error: {}", e.getMessage());
	    throw new RuntimeException(e.getMessage());
	} catch (ServiceException e) {
	    log.error("Fatal transport error: {}", e.getMessage());
	    throw new RuntimeException(e.getMessage());
	} finally {
	    // Release the connection.
	    method.releaseConnection();
	}
    }

    private String convertStreamToString(InputStream is) throws IOException {

	/*
	 * To convert the InputStream to String we use the BufferedReader.readLine() method. We iterate until the
	 * BufferedReader return null which means there's no more data to read. Each line will appended to a
	 * StringBuilder and returned as String.
	 */
	if (is != null) {
	    StringBuilder sb = new StringBuilder();
	    String line;
	    try {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
		while ((line = reader.readLine()) != null) {
		    sb.append(line).append("\n");
		}
	    } finally {
		is.close();
	    }
	    return sb.toString();
	} else {
	    return "";
	}
    }

    @Override
    protected void fixMergeEntityProperty(StpEndoTipo1 entity) {

    }

    @Override
    protected void fixRenderEntityProperty(StpEndoTipo1 entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
