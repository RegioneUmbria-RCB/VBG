package it.gruppoinit.pal.gp.backoffice.web;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.activation.DataHandler;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.Documentiistanza;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Ruoli;
import it.gruppoinit.pal.gp.core.domain.helper.CodiceDescrizioneBean;
import it.gruppoinit.pal.gp.core.domain.web.ChiaveValoreBean;
import it.gruppoinit.pal.gp.core.exception.FunzioneBusinessRemotaException;
import it.gruppoinit.pal.gp.core.exception.InvalidConfigurationException;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.ContenttypesService;
import it.gruppoinit.pal.gp.core.service.DocumentiistanzaService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.ProtTipidocumentoMetadatiService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliService;
import it.gruppoinit.pal.gp.core.service.RuoliService;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.pal.gp.core.ws.client.DOCERWSClient;
import it.kdm.docer.sdk.classes.xsd.StreamDescriptor;

@Controller
public class AjaxDocerController extends BaseController<Comuni> {

    @Autowired
    private ContenttypesService contenttypesService;
    @Autowired
    private VerticalizzazioniService verticalizzazioniService;
    @Autowired
    private ResponsabiliService responsabiliService;
    @Autowired
    private RuoliService ruoliService;
    @Autowired
    private DocumentiistanzaService documentiistanzaService;
    @Autowired
    private IstanzeService istanzeService;
    @Autowired
    private ProtTipidocumentoMetadatiService protTipidocumentoMetadatiService;

    @RequestMapping
    public void controllaEsistenzaGruppo(@RequestParam("codiceGruppo") String codiceGruppo, @RequestParam("codiceComune") String codiceComune,
	    HttpServletRequest request, HttpServletResponse response) throws IOException {

	DOCERWSClient dc = new DOCERWSClient(codiceComune, ORMHelper.getSoftware(), verticalizzazioniService);
	String token = null;
	boolean present = false;
	response.setContentType("text/plain");
	try {
	    token = getTokenDocer(dc, request);
	    present = dc.isGroupPresent(token, codiceGruppo);
	} catch (InvalidConfigurationException e) {
	    response.getWriter().write(e.getMessage());
	    e.printStackTrace();
	    return;
	} catch (FunzioneBusinessRemotaException e) {
	    response.getWriter().write(e.getMessage());
	    e.printStackTrace();
	    return;
	}
	if (present) {
	    response.getWriter().write("true");
	} else {
	    response.getWriter().write("false");
	}
    }

    @RequestMapping
    public String ajaxCercaDocumenti(Model model, @RequestParam("tipoDocumento") String tipoDocumento, @RequestParam("nomeFile") String nomeFile,
	    @RequestParam("descrizioneFile") String descrizioneFile, @RequestParam("registroId") String registroId,
	    @RequestParam("annoProtocollo") String annoProtocollo, @RequestParam("numeroProtocollo") String numeroProtocollo,
	    @RequestParam("keywords") String keywords, @RequestParam("codiceComune") String codiceComune, HttpServletRequest request,
	    HttpServletResponse response) throws IOException {

	DOCERWSClient dc = new DOCERWSClient(codiceComune, ORMHelper.getSoftware(), verticalizzazioniService);
	String token = null;
	List<ChiaveValoreBean<String, String>> present = new ArrayList<ChiaveValoreBean<String, String>>();
	response.setContentType("text/html");
	String errore = "";
	try {
	    token = getTokenDocer(dc, request);
	    List<String> kwds = new ArrayList<String>();
	    if (StringUtils.isNotBlank(keywords)) {
		String[] spl = keywords.split(",");
		for (String k : spl) {
		    kwds.add(k.trim());
		}
	    }
	    List<ChiaveValoreBean<String, String>> metadatis = new ArrayList<ChiaveValoreBean<String, String>>();
	    Enumeration<String> parameterNames = (Enumeration<String>) request.getParameterNames();
	    while (parameterNames.hasMoreElements()) {
		String name = parameterNames.nextElement();
		if (StringUtils.isNotBlank(name)) {
		    if (name.startsWith("INITMD_")) {
			String value = StringUtils.defaultString(request.getParameter(name)).trim();
			if (StringUtils.isNotBlank(value)) {
			    if (name.equals("INITMD_DATAPRATICA")) {
				GregorianCalendar c = Utilities.getDate(value, "dd/MM/yyyy");
				try {
				    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				    value = sdf.format(c.getTime());
				    value = value + "T00:00:00.000+02:00";
				} catch (Exception e) {
				}
			    }
			    ChiaveValoreBean<String, String> cvb = new ChiaveValoreBean<String, String>();
			    cvb.setChiave(name);
			    cvb.setValore(value);
			    metadatis.add(cvb);
			}
		    }
		}
	    }
	    present = dc.cercaDocumenti(token, nomeFile, tipoDocumento, descrizioneFile, numeroProtocollo, annoProtocollo, registroId, kwds,
		    metadatis);
	} catch (InvalidConfigurationException e) {
	    errore = e.getMessage();
	} catch (FunzioneBusinessRemotaException e) {
	    errore = e.getMessage();
	}
	model.addAttribute("errore", errore);
	model.addAttribute("docs", present);
	return "documentiistanza/ajaxListaDocumentiDocer";
    }

    @RequestMapping
    public String ajaxMetadatiDocumenti(Model model, @RequestParam("tipoDocumento") String tipoDocumento,
	    @RequestParam("codiceComune") String codiceComune, @RequestParam("pSoftware") String pSoftware, HttpServletRequest request,
	    HttpServletResponse response) throws IOException, InvalidConfigurationException, FunzioneBusinessRemotaException {

	List<CodiceDescrizioneBean> l = protTipidocumentoMetadatiService.findByTipoDocumentoCodice(tipoDocumento, codiceComune, pSoftware);
	model.addAttribute("mds", l);
	return "documentiistanza/ajaxMetadatiDocumenti";
    }

    @RequestMapping
    public void ajaxDownload(HttpServletRequest request, HttpServletResponse response, @RequestParam("docnum") String docnum,
	    @RequestParam(value = "nomefile", required = false) String nomefile, @RequestParam("codiceComune") String codiceComune)
	    throws IOException, InvalidConfigurationException, FunzioneBusinessRemotaException {

	DOCERWSClient dc = new DOCERWSClient(codiceComune, ORMHelper.getSoftware(), verticalizzazioniService);
	if (StringUtils.isBlank(nomefile)) {
	    String token = null;
	    ChiaveValoreBean<String, String> doc = new ChiaveValoreBean<String, String>();
	    response.setContentType("text/html");
	    token = getTokenDocer(dc, request);
	    doc = dc.cercaDocumento(token, docnum);
	    nomefile = doc.getValore();
	}
	String token = null;
	token = getTokenDocer(dc, request);
	StreamDescriptor sd = dc.downloadFile(token, docnum);
	if (sd == null) {
	    throw new RuntimeException("file non trovato");
	}
	DataHandler dh = sd.getHandler();
	response.setHeader("Pragma", "public");
	response.setHeader("Cache-Control", "max-age=0");
	response.setHeader("Content-Disposition", "attachment; filename=\"" + nomefile + "\"");
	response.setHeader("Content-transfer-encoding", "binary");
	String cType = contenttypesService.findMimeTypeByFileName(nomefile);
	response.setContentType(cType);
	if (sd.getByteSize() != null) {
	    response.setContentLength(sd.getByteSize().intValue());
	}
	ServletOutputStream out = response.getOutputStream();
	dh.writeTo(out);
    }

    @RequestMapping
    public void ajaxCreaDocumento(@RequestParam("docnum") String docnum, @RequestParam(value = "nomefile") String nomefile,
	    @RequestParam(value = "codiceIstanza") Integer codiceIstanza, HttpServletRequest request, HttpServletResponse response)
	    throws IOException, InvalidConfigurationException, FunzioneBusinessRemotaException {

	response.setContentType("text/plain");
	try {
	    Istanze i = istanzeService.findById(new PkId(codiceIstanza));
	    checkAccessoInformazioni(i, true);
	    Documentiistanza d = new Documentiistanza();
	    d.setIstanza(i);
	    d.setIdDocer(docnum);
	    d.setDocumento(nomefile);
	    d.setNote("Documento archiviato in DOCER con id: " + docnum);
	    d.setData(Calendar.getInstance().getTime());
	    documentiistanzaService.insert(d);
	} catch (Exception e) {
	    response.getWriter().write(e.getMessage());
	}
	response.getWriter().write("OK");
    }

    @RequestMapping
    public void creaGruppo(@RequestParam("codiceGruppo") String codiceGruppo, @RequestParam("descrizioneGruppo") String descrizioneGruppo,
	    @RequestParam("codiceComune") String codiceComune, HttpServletRequest request, HttpServletResponse response) throws IOException {

	DOCERWSClient dc = new DOCERWSClient(codiceComune, ORMHelper.getSoftware(), verticalizzazioniService);
	String token = null;
	boolean present = false;
	response.setContentType("text/plain");
	try {
	    token = getTokenDocer(dc, request);
	    present = dc.createGroup(token, codiceGruppo, descrizioneGruppo);
	} catch (InvalidConfigurationException e) {
	    response.getWriter().write(e.getMessage());
	    e.printStackTrace();
	    return;
	} catch (FunzioneBusinessRemotaException e) {
	    response.getWriter().write(e.getMessage());
	    e.printStackTrace();
	    return;
	}
	if (present) {
	    response.getWriter().write("true");
	} else {
	    response.getWriter().write("false");
	}
    }

    @RequestMapping
    public void controllaEsistenzaUtente(@RequestParam("codiceUtente") String codiceUtente, @RequestParam("codiceComune") String codiceComune,
	    HttpServletRequest request, HttpServletResponse response) throws IOException {

	DOCERWSClient dc = new DOCERWSClient(codiceComune, ORMHelper.getSoftware(), verticalizzazioniService);
	String token = null;
	boolean present = false;
	response.setContentType("text/plain");
	try {
	    token = getTokenDocer(dc, request);
	    present = dc.isUserPresent(token, codiceUtente);
	} catch (InvalidConfigurationException e) {
	    response.getWriter().write(e.getMessage());
	    e.printStackTrace();
	    return;
	} catch (FunzioneBusinessRemotaException e) {
	    response.getWriter().write(e.getMessage());
	    e.printStackTrace();
	    return;
	}
	if (present) {
	    response.getWriter().write("true");
	} else {
	    response.getWriter().write("false");
	}
    }

    @RequestMapping
    public void creaUtente(@RequestParam("codiceUtente") String codiceUtente, @RequestParam("descrizioneUtente") String descrizioneUtente,
	    @RequestParam("emailUtente") String emailUtente, @RequestParam("passwordUtente") String passwordUtente,
	    @RequestParam("codiceComune") String codiceComune, HttpServletRequest request, HttpServletResponse response) throws IOException {

	DOCERWSClient dc = new DOCERWSClient(codiceComune, ORMHelper.getSoftware(), verticalizzazioniService);
	String token = null;
	boolean present = false;
	response.setContentType("text/plain");
	try {
	    token = getTokenDocer(dc, request);
	    present = dc.createUser(token, codiceUtente, descrizioneUtente, emailUtente, passwordUtente);
	} catch (InvalidConfigurationException e) {
	    response.getWriter().write(e.getMessage());
	    e.printStackTrace();
	    return;
	} catch (FunzioneBusinessRemotaException e) {
	    response.getWriter().write(e.getMessage());
	    e.printStackTrace();
	    return;
	}
	if (present) {
	    response.getWriter().write("true");
	} else {
	    response.getWriter().write("false");
	}
    }

    @RequestMapping
    public void creaRuoloUtente(@RequestParam("codiceUtente") Integer codiceUtente, @RequestParam("idRuolo") String idRuolo,
	    @RequestParam("codiceComune") String codiceComune, HttpServletRequest request, HttpServletResponse response) throws IOException {

	Responsabili re = responsabiliService.findById(new PkId(codiceUtente));
	response.setContentType("text/plain");
	if (StringUtils.isBlank(re.getCodUteDocer())) {
	    response.getWriter().write("L'utente non sembra essere censito in DOCER. Verificare le credenziali nella maschera dei responsabili");
	    return;
	}
	Ruoli r = ruoliService.findById(new PkId(Integer.valueOf(idRuolo)));
	if (StringUtils.isBlank(r.getCodDocer())) {
	    response.getWriter().write("Il ruolo non sembra essere censito in DOCER. Verificare la configurazione nella maschera dei ruoli");
	    return;
	}
	DOCERWSClient dc = new DOCERWSClient(codiceComune, ORMHelper.getSoftware(), verticalizzazioniService);
	String token = null;
	boolean esito = false;
	try {
	    token = getTokenDocer(dc, request);
	    esito = dc.aggiungiGruppoAUser(token, re.getCodUteDocer(), r.getCodDocer());
	} catch (InvalidConfigurationException e) {
	    response.getWriter().write(e.getMessage());
	    e.printStackTrace();
	    return;
	} catch (FunzioneBusinessRemotaException e) {
	    response.getWriter().write(e.getMessage());
	    e.printStackTrace();
	    return;
	}
	if (esito) {
	    response.getWriter().write("true");
	} else {
	    response.getWriter().write("false");
	}
    }

    @RequestMapping
    public void rimuoviRuoloUtente(@RequestParam("codiceUtente") Integer codiceUtente, @RequestParam("idRuolo") String idRuolo,
	    @RequestParam("codiceComune") String codiceComune, HttpServletRequest request, HttpServletResponse response) throws IOException {

	Responsabili re = responsabiliService.findById(new PkId(codiceUtente));
	response.setContentType("text/plain");
	if (StringUtils.isBlank(re.getCodUteDocer())) {
	    response.getWriter().write("L'utente non sembra essere censito in DOCER. Verificare le credenziali nella maschera dei responsabili");
	    return;
	}
	Ruoli r = ruoliService.findById(new PkId(Integer.valueOf(idRuolo)));
	if (StringUtils.isBlank(r.getCodDocer())) {
	    response.getWriter().write("Il ruolo non sembra essere censito in DOCER. Verificare la configurazione nella maschera dei ruoli");
	    return;
	}
	DOCERWSClient dc = new DOCERWSClient(codiceComune, ORMHelper.getSoftware(), verticalizzazioniService);
	String token = null;
	boolean esito = false;
	try {
	    token = getTokenDocer(dc, request);
	    esito = dc.rimuoviGruppoAUser(token, re.getCodUteDocer(), r.getCodDocer());
	} catch (InvalidConfigurationException e) {
	    response.getWriter().write(e.getMessage());
	    e.printStackTrace();
	    return;
	} catch (FunzioneBusinessRemotaException e) {
	    response.getWriter().write(e.getMessage());
	    e.printStackTrace();
	    return;
	}
	if (esito) {
	    response.getWriter().write("true");
	} else {
	    response.getWriter().write("false");
	}
    }

    @RequestMapping
    public void controllaEsistenzaRuoliUtente(@RequestParam("codiceUtente") Integer codiceUtente, @RequestParam("listaruoli") String listaruoli,
	    @RequestParam("codiceComune") String codiceComune, HttpServletRequest request, HttpServletResponse response) throws IOException {

	Responsabili re = responsabiliService.findById(new PkId(codiceUtente));
	response.setContentType("text/plain");
	if (StringUtils.isBlank(re.getCodUteDocer())) {
	    response.getWriter().write("L'utente non sembra essere censito in DOCER. Verificare le credenziali nella maschera dei responsabili");
	    return;
	}
	DOCERWSClient dc = new DOCERWSClient(codiceComune, ORMHelper.getSoftware(), verticalizzazioniService);
	String token = null;
	String[] ruoli = listaruoli.split(",");
	Map<String, String> ruoliDocer = new HashMap<String, String>();
	for (String idruolo : ruoli) {
	    Ruoli r = ruoliService.findById(new PkId(Integer.valueOf(idruolo)));
	    if (StringUtils.isNotBlank(r.getCodDocer())) {
		ruoliDocer.put(idruolo, r.getCodDocer());
	    }
	}
	List<String> gruppiUtente = new ArrayList<String>();
	try {
	    token = getTokenDocer(dc, request);
	    gruppiUtente = dc.getGroupsOfUser(token, re.getCodUteDocer());
	} catch (InvalidConfigurationException e) {
	    response.getWriter().write(e.getMessage());
	    e.printStackTrace();
	    return;
	} catch (FunzioneBusinessRemotaException e) {
	    response.getWriter().write(e.getMessage());
	    e.printStackTrace();
	    return;
	}
	if (gruppiUtente.size() > 0 || ruoliDocer.size() > 0) {
	    String result = "#OK#";
	    for (Map.Entry<String, String> e : ruoliDocer.entrySet()) {
		String key = e.getKey();
		String value = e.getValue();
		boolean trovato = false;
		for (String id : gruppiUtente) {
		    if (id.equals(value)) {
			trovato = true;
			break;
		    }
		}
		if (trovato) {
		    result += key + "!!!OK|";
		} else {
		    result += key + "!!!KO|";
		}
	    }
	    response.getWriter().write(result);
	} else {
	    response.getWriter().write("#KO#");
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

    }

    @Override
    protected void fixMergeEntityProperty(Comuni entity) {

    }

    @Override
    protected void fixRenderEntityProperty(Comuni entity) {

    }
}
