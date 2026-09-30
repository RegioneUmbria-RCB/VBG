package it.gruppoinit.pal.gp.backoffice.web;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import it.gruppoinit.fileconverter.ConvertBinaryRequest;
import it.gruppoinit.fileconverter.ConvertBinaryResponse;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Anagrafedocumenti;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzerichiedenti;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipisoggetto;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.AnagrafeService;
import it.gruppoinit.pal.gp.core.service.AnagrafedocumentiService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.IstanzerichiedentiService;
import it.gruppoinit.pal.gp.core.service.helper.TipoWSAnagrafeAttivoEnum;
import it.gruppoinit.pal.gp.core.ws.client.FileConverterWsClient;
import it.gruppoinit.sigepro.cart.service.utils.AttachmentsUtils;

/**
 * 
 * @author Init sviluppo
 */
@Controller
@SessionAttributes("istanzerichiedenti")
public class IstanzerichiedentiController extends BaseController<Istanzerichiedenti> {

    private static final Logger log = LoggerFactory.getLogger(IstanzerichiedentiController.class);
    @Autowired
    private AnagrafeService anagrafeService;
    @Autowired
    private IstanzerichiedentiService istanzerichiedentiService;
    @Autowired
    private IstanzeService istanzeService;
    @Autowired
    private VerticalizzazioniService verticalizzazioniService;
    @Autowired
    private AnagrafedocumentiService anagrafedocumentiService;
    @Autowired
    private OggettiService oggettiService;

    @RequestMapping
    public ModelMap list(@RequestParam("codiceIstanza") Integer codiceIstanza, HttpServletRequest request) {

	Istanze istanza = istanzeService.findById(new PkId(codiceIstanza));
	checkAccessoInformazioni(istanza, false);
	List<Istanzerichiedenti> istanzerichiedentiList = istanzerichiedentiService.findByIstanza(istanza);
	ModelMap model = new ModelMap(istanzerichiedentiList);
	Anagrafe richiedente = anagrafeService.findById(new PkId(istanza.getRichiedente().getId().getCodice()));
	model.addAttribute("richiedente", richiedente);
	model.addAttribute("istanza", istanza);
	model.addAttribute("istanzerichiedentiList", istanzerichiedentiList);
	Boolean isParixGate = true;
	TipoWSAnagrafeAttivoEnum tipoWSAnagrafeAttivoEnum = anagrafeService.findServizioWSAnagrafeAttivo();
	if (tipoWSAnagrafeAttivoEnum.equals(TipoWSAnagrafeAttivoEnum.NESSUNO)) {
	    isParixGate = false;
	}
	//isParixGate = verticalizzazioniService.isAttiva("WSANAGRAFE_PARIX");
	// Non cambiamo nome al parametro, ma il controllo adesso verifica che sia 
	// attivo uno dei due servizi PARIX_GATE o ADRIER
	model.addAttribute("isParixGate", isParixGate);
	// Controllo per verificare presnetza della verticalizzazionei WS DURC
	boolean isWSDURC = verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_WSDURC);
	model.addAttribute("isWSDURC", isWSDURC);
	return model;
    }

    @RequestMapping
    public String create(@RequestParam("codiceIstanza") Integer codiceIstanza, Model model, HttpServletRequest request) {

	Istanzerichiedenti istanzerichiedenti = new Istanzerichiedenti();
	Istanze istanza = istanzeService.findById(new PkId(codiceIstanza));
	checkAccessoInformazioni(istanza, true);
	istanzerichiedenti.setIstanza(istanza);
	fixRenderEntityProperty(istanzerichiedenti);
	model.addAttribute("istanzerichiedenti", istanzerichiedenti);
	setPageAttributes(model);
	return "istanzerichiedenti/form";
    }

    @RequestMapping
    public String insert(Model model, @ModelAttribute("istanzerichiedenti") Istanzerichiedenti istanzerichiedenti, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	fixMergeEntityProperty(istanzerichiedenti);
	try {
	    checkAccessoInformazioni(istanzerichiedenti.getIstanza(), true);
	    istanzerichiedentiService.insert(istanzerichiedenti);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, istanzerichiedenti, e);
	    fixRenderEntityProperty(istanzerichiedenti);
	    setPageAttributes(model);
	    istanzerichiedenti.setRichiedentestorico(null);
	    istanzerichiedenti.setAnagrafeCollegatastorico(null);
	    istanzerichiedenti.setProcuratorestorico(null);
	    return "istanzerichiedenti/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + istanzerichiedenti.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	Istanzerichiedenti istanzerichiedenti = istanzerichiedentiService.findById(id);
	checkAccessoInformazioni(istanzerichiedenti.getIstanza(), false);
	fixRenderEntityProperty(istanzerichiedenti);
	model.addAttribute("istanzerichiedenti", istanzerichiedenti);
	setPageAttributes(model);
	return "istanzerichiedenti/form";
    }

    @RequestMapping
    public String insertVisuraParix(@RequestParam("codiceAnagrafe") Integer codiceAnagrafe,
	    @RequestParam(value = "codiceIstanza", required = false) Integer codiceIstanza, HttpServletRequest request) {

	try {
	    Anagrafedocumenti newDoc = anagrafeService.insertVisuraParix(codiceAnagrafe, codiceIstanza);
	    return "redirect:../anagrafe/viewDocumenti.htm?codice=" + newDoc.getId().getCodice() + "&staus_msg=01";
	} catch (Exception e) {
	    throw new RuntimeException("Si è verificato un errore nel recupero del file di visura: " + e.getMessage(), e);
	}
    }

    @RequestMapping
    public void ajaxStampaPdfParix(@RequestParam("codice") Integer codiceOggetto,
	    @RequestParam(value = "codiceIstanza", required = false) Integer codiceIstanza, HttpServletRequest request, HttpServletResponse response)
	    throws IOException {

	if (codiceIstanza != null) {
	    Istanze istanza = istanzeService.findById(new PkId(codiceIstanza));
	    checkAccessoInformazioni(istanza, true);
	}
	Oggetti xml = oggettiService.findById(new PkId(codiceOggetto));
	if (xml == null) {
	    throw new RuntimeException("L'oggetto con codice " + codiceOggetto + " non è stato trovato");
	}
	byte[] out = getHTML(xml);
	FileConverterWsClient fileConverterWService = new FileConverterWsClient();
	ConvertBinaryRequest cbr = new ConvertBinaryRequest(ORMHelper.getToken(), out, "HTM", "PDF");
	try {
	    ConvertBinaryResponse cResp = fileConverterWService.convertBinary(cbr);
	    ServletOutputStream sos = response.getOutputStream();
	    response.setContentType("application/pdf");
	    response.setContentLength(cResp.getBinaryData().length);
	    response.setHeader("Content-Disposition", "attachment;filename=\"visura.pdf\"");
	    AttachmentsUtils.writeBytesToStream(cResp.getBinaryData(), sos);
	    sos.flush();
	    sos.close();
	} catch (RemoteException e) {
	    ServletOutputStream sos = response.getOutputStream();
	    response.setContentType("text/html");
	    response.setContentLength(out.length);
	    AttachmentsUtils.writeBytesToStream(out, sos);
	    sos.flush();
	    sos.close();
	    throw new RuntimeException("Errore nella conversione del file in PDF:" + e.getMessage(), e);
	}
    }

    public static void main(String[] args) throws Exception {

	byte[] b = IOUtils.toByteArray(new FileInputStream(new File("C:\\Users\\riccardob\\Desktop\\temp\\FI-467088.xml")));
	Oggetti o = new Oggetti();
	o.setOggetto(b);
	IstanzerichiedentiController c = new IstanzerichiedentiController();
	System.out.println(new String(c.getHTML(o)));
    }

    private byte[] getHTML(Oggetti xml) {

	InputStream in = null;
	try {
	    in = this.getClass().getClassLoader().getResource("it/gruppoinit/xslt/visurainfocamere.xsl").openStream();
	    byte[] xslBytes = IOUtils.toByteArray(in);
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    TransformerFactory tFactory = TransformerFactory.newInstance();
	    Transformer transformer = tFactory.newTransformer(new StreamSource(new ByteArrayInputStream(xslBytes)));
	    transformer.transform(new StreamSource(new ByteArrayInputStream(xml.getOggetto())), new StreamResult(baos));
	    return baos.toByteArray();
	} catch (Exception e) {
	    log.error("Errore durante il caricamento del file it/gruppoinit/xslt/visurainfocamere.xsl: {}", e);
	    throw new RuntimeException("Errore durante il caricamento del file it/gruppoinit/xslt/visurainfocamere.xsl: " + e.getMessage(), e);
	} finally {
	    if (in != null) {
		try {
		    in.close();
		} catch (IOException e) {
		}
	    }
	}
    }

    @RequestMapping
    public String update(Model model, @ModelAttribute("istanzerichiedenti") Istanzerichiedenti istanzerichiedenti, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	checkAccessoInformazioni(istanzerichiedenti.getIstanza(), true);
	fixMergeEntityProperty(istanzerichiedenti);
	try {
	    if (!StringUtils.defaultIfEmpty(request.getParameter("richiedente.id.codice"), "").equals("")) {
		Integer codice = Integer.parseInt(request.getParameter("richiedente.id.codice"));
		PkId codiceId = new PkId(codice);
		Anagrafe richiedente = anagrafeService.findById(codiceId);
		istanzerichiedenti.setRichiedente(richiedente);
	    } else {
		istanzerichiedenti.setRichiedente(null);
	    }
	    if (!StringUtils.defaultIfEmpty(request.getParameter("anagrafeCollegata.id.codice"), "").equals("")) {
		Integer codice = Integer.parseInt(request.getParameter("anagrafeCollegata.id.codice"));
		PkId codiceId = new PkId(codice);
		Anagrafe richiedente = anagrafeService.findById(codiceId);
		istanzerichiedenti.setAnagrafeCollegata(richiedente);
	    } else {
		istanzerichiedenti.setAnagrafeCollegata(null);
	    }
	    if (!StringUtils.defaultIfEmpty(request.getParameter("procuratore.id.codice"), "").equals("")) {
		Integer codice = Integer.parseInt(request.getParameter("procuratore.id.codice"));
		PkId codiceId = new PkId(codice);
		Anagrafe richiedente = anagrafeService.findById(codiceId);
		istanzerichiedenti.setProcuratore(richiedente);
	    } else {
		istanzerichiedenti.setProcuratore(null);
	    }
	    istanzerichiedentiService.update(istanzerichiedenti);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, istanzerichiedenti, e);
	    fixRenderEntityProperty(istanzerichiedenti);
	    setPageAttributes(model);
	    return "istanzerichiedenti/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + istanzerichiedenti.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String delete(Model model, @ModelAttribute("istanzerichiedenti") Istanzerichiedenti istanzerichiedenti, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	Istanzerichiedenti objToDelete = istanzerichiedentiService.findById(istanzerichiedenti.getId());
	Integer codiceIstanza = objToDelete.getIstanza().getId().getCodice();
	try {
	    checkAccessoInformazioni(objToDelete.getIstanza(), true);
	    istanzerichiedentiService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderEntityProperty(istanzerichiedenti);
	    setPageAttributes(model);
	    return "istanzerichiedenti/form";
	}
	status.setComplete();
	return "redirect:list.htm?codiceIstanza=" + codiceIstanza;
    }

    @RequestMapping
    public String copiaSoggettiCollegati(@RequestParam("codiceIstanzaDestinataria") Integer codiceIstanza,
	    @RequestParam("codiceIstanzaSorgente") Integer codiceIstanzaSorgente, Model model, HttpServletRequest request) {

	Istanze istanzaDestinatario = istanzeService.findById(new PkId(codiceIstanza));
	checkAccessoInformazioni(istanzaDestinatario, true);
	Istanze istanzaSorgente = istanzeService.findById(new PkId(codiceIstanzaSorgente));
	istanzerichiedentiService.copiaIstanzeRichiedenti(istanzaSorgente, istanzaDestinatario);
	return "redirect:list.htm?codiceIstanza=" + codiceIstanza + "&status_msg=02";
    }

    @Override
    protected void fixMergeEntityProperty(Istanzerichiedenti entity) {

    }

    @Override
    protected void fixRenderEntityProperty(Istanzerichiedenti entity) {

	if (entity.getAnagrafeCollegata() == null) {
	    entity.setAnagrafeCollegata(new Anagrafe());
	}
	if (entity.getRichiedente() == null) {
	    entity.setRichiedente(new Anagrafe());
	}
	if (entity.getTiposoggetto() == null) {
	    entity.setTiposoggetto(new Tipisoggetto());
	}
	if (entity.getProcuratore() == null) {
	    entity.setProcuratore(new Anagrafe());
	}
	if (entity.getOggettoProcuratore() == null) {
	    entity.setOggettoProcuratore(new Oggetti());
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
