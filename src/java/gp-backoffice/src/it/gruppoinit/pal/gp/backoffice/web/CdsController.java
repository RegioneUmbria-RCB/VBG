package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Cds;
import it.gruppoinit.pal.gp.core.domain.Cdsconvocazioni;
import it.gruppoinit.pal.gp.core.domain.Cdsinvitati;
import it.gruppoinit.pal.gp.core.domain.Cdsinvitati2;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.web.CdsFilter;
import it.gruppoinit.pal.gp.core.service.AmministrazioniService;
import it.gruppoinit.pal.gp.core.service.AnagrafeService;
import it.gruppoinit.pal.gp.core.service.CdsService;
import it.gruppoinit.pal.gp.core.service.CdsconvocazioniService;
import it.gruppoinit.pal.gp.core.service.Cdsinvitati2Service;
import it.gruppoinit.pal.gp.core.service.CdsinvitatiService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

/**
 * 
 * @author
 */
@Controller
@SessionAttributes(value = { "cds", "convocazione", "cdsinvitati", "cdsinvitati2", "cdsFilter" })
public class CdsController extends BaseController<Cds> {

    @Autowired
    private CdsconvocazioniService cdsconvocazioniService;
    @Autowired
    private CdsService cdsService;
    @Autowired
    private IstanzeService istanzeService;
    @Autowired
    private AmministrazioniService amministrazioniService;
    @Autowired
    private CdsinvitatiService cdsinvitatiService;
    @Autowired
    private AnagrafeService anagrafeService;
    @Autowired
    private Cdsinvitati2Service cdsinvitati2Service;
    private static final Logger log = LoggerFactory.getLogger(CdsController.class);
    private String REDIRECT_COMMISSIONI_CONFERENZE = "redirect:../commissioniediliziet/list.htm";

    @RequestMapping
    public String createSearch(Model model, HttpServletRequest request, HttpServletResponse response) {

	return REDIRECT_COMMISSIONI_CONFERENZE;
	//	CdsFilter filter = new CdsFilter();
	//	model.addAttribute("cdsFilter", filter);
	//	return "cds/search";
    }

    @RequestMapping
    public String list(Model model, @ModelAttribute("cdsFilter") CdsFilter cdsFilter, BindingResult result, SessionStatus status,
	    HttpServletRequest request, HttpServletResponse response) {

	return REDIRECT_COMMISSIONI_CONFERENZE;
	//	List<Cds> cdsList = cdsService.findByFilter(cdsFilter);
	//	boolean export = createJMesaExport(request, response, cdsList);
	//	if (export) {
	//	    return null;
	//	}
	//	model.addAttribute("cdsList", cdsList);
	//	return "cds/list";
    }

    @RequestMapping
    public String view(@RequestParam("codiceIstanza") Integer codiceIstanza,
	    @RequestParam(value = "amministrazioniVisibili", required = false) String visibile, Model model, HttpServletRequest request) {
	
	return REDIRECT_COMMISSIONI_CONFERENZE;
	//	PkId id = new PkId(codiceIstanza);
	//	Istanze istanza = istanzeService.findById(id);
	//	checkAccessoInformazioni(istanza, false);
	//	List<Cds> cdss = cdsService.findByIstanza(istanza);
	//	if (cdss.size() == 0) {
	//	    // CDS NON CREATA
	//	    return "cds/nocdserror";
	//	} else if (cdss.size() > 1) {
	//	    // NON PUO' ESSERCI PIU' DI UNA CDS PER ISTANZA
	//	    throw new RuntimeException("Non ci può essere più di una CDS per istanza");
	//	}
	//	Cds cds = cdss.get(0);
	//	//lista di amministrazioni invitate
	//	List<Cdsinvitati> listaAmministrazioniInvitate = cdsinvitatiService.findByCds(cds);
	//	//lista di responsabili invitati
	//	List<Cdsinvitati2> listaResponsabiliInvitati = cdsinvitati2Service.findByCds(cds);
	//	fixRenderEntityProperty(cds);
	//	model.addAttribute("cds", cds);
	//	model.addAttribute("listaAmministrazioniInvitate", listaAmministrazioniInvitate);
	//	model.addAttribute("listaResponsabiliInvitati", listaResponsabiliInvitati);
	//	setPageAttributes(model);
	//	return "cds/form";
    }

    //
    @RequestMapping
    public String update(@ModelAttribute("cds") Cds cds, BindingResult result, SessionStatus status, HttpServletRequest request) {

	fixMergeEntityProperty(cds);
	try {
	    if (cds.getIstanze() != null) {
		checkAccessoInformazioni(cds.getIstanze(), true);
	    }
	    cdsService.update(cds);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, cds, e);
	    fixRenderEntityProperty(cds);
	    return "cds/form";
	}
	status.setComplete();
	return "redirect:view.htm?codiceIstanza=" + cds.getIstanze().getId().getCodice() + "&status_msg=02";
    }

    //
    @RequestMapping
    public String delete(@ModelAttribute("cds") Cds cds, BindingResult result, SessionStatus status) {

	Cds objToDelete = cdsService.findById(cds.getId());
	try {
	    if (objToDelete.getIstanze() != null) {
		checkAccessoInformazioni(objToDelete.getIstanze(), true);
	    }
	    cdsService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderEntityProperty(cds);
	    return "cds/form";
	}
	//status.setComplete();
	return "redirect:../history/back.htm?GoTo=%2F";
	//return "redirect:list.htm";
    }

    @RequestMapping
    public String ajaxListaconvocazioni(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request, HttpServletResponse response)
	    throws IOException {

	Cds cds = cdsService.findById(new PkId(codice));
	List<Cdsconvocazioni> convocazionis = cdsconvocazioniService.findByCds(cds);
	model.addAttribute("convocazionis", convocazionis);
	response.setContentType("text/plain");
	return "ajax/listaconvocazionicds";
    }

    @RequestMapping
    public String ajaxDettaglioConvocazione(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request,
	    HttpServletResponse response) throws IOException {

	PkId id = new PkId(codice);
	Cdsconvocazioni convocazione = cdsconvocazioniService.findById(id);
	model.addAttribute("convocazione", convocazione);
	response.setContentType("text/plain");
	return "ajax/dettaglioconvocazionecds";
    }

    @RequestMapping
    public void ajaxSetConvocazioneEffettiva(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request,
	    HttpServletResponse response) throws IOException {

	PkId id = new PkId(codice);
	Cdsconvocazioni convocazione = cdsconvocazioniService.findById(id);
	convocazione.setFlagEffettiva(Boolean.TRUE);
	try {
	    if (convocazione.getCds().getIstanze() != null) {
		checkAccessoInformazioni(convocazione.getCds().getIstanze(), true);
	    }
	    cdsconvocazioniService.update(convocazione);
	    response.setContentType("text/plain");
	} catch (Exception e) {
	    renderHTMLException(e.getMessage(), response);
	}
    }

    @RequestMapping
    public void ajaxDeleteConvocazione(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request, HttpServletResponse response)
	    throws IOException {

	PkId id = new PkId(codice);
	Cdsconvocazioni convocazione = cdsconvocazioniService.findById(id);
	try {
	    if (convocazione.getCds().getIstanze() != null) {
		checkAccessoInformazioni(convocazione.getCds().getIstanze(), true);
	    }
	    cdsconvocazioniService.delete(convocazione);
	    response.setContentType("text/plain");
	} catch (Exception e) {
	    renderHTMLException(e.getMessage(), response);
	}
    }

    @RequestMapping
    public String ajaxCreateConvocazione(@RequestParam("codicecds") Integer codicecds, Model model, HttpServletRequest request,
	    HttpServletResponse response) throws IOException {

	PkId id = new PkId(codicecds);
	Cds cds = cdsService.findById(id);
	Istanze istanza = istanzeService.bindDomainObject(cds.getIstanze(), PkId.class, "id.codice");
	checkAccessoInformazioni(istanza, true);
	Cdsconvocazioni convocazione = new Cdsconvocazioni();
	convocazione.setCds(cds);
	convocazione.setIstanze(istanza);
	convocazione.setDataconvocazione(Calendar.getInstance().getTime());
	convocazione.setOraconvocazione("08:30");
	model.addAttribute("convocazione", convocazione);
	model.addAttribute("insert", Boolean.TRUE);
	response.setContentType("text/plain");
	return "ajax/dettaglioconvocazionecds";
    }

    @RequestMapping
    public void ajaxInsertConvocazione(@ModelAttribute("convocazione") Cdsconvocazioni convocazione, Model model, HttpServletRequest request,
	    HttpServletResponse response) throws IOException {

	try {
	    if (convocazione.getCds().getIstanze() != null) {
		checkAccessoInformazioni(convocazione.getCds().getIstanze(), true);
	    }
	    cdsconvocazioniService.insert(convocazione);
	    response.setContentType("text/plain");
	} catch (Exception e) {
	    renderHTMLException(e.getMessage(), response);
	}
    }

    @RequestMapping
    public void ajaxUpdateConvocazione(@ModelAttribute("convocazione") Cdsconvocazioni convocazione, Model model, HttpServletRequest request,
	    HttpServletResponse response) throws IOException {

	try {
	    Date d = convocazione.getDataconvocazione();
	    String ora = convocazione.getOraconvocazione();
	    convocazione = cdsconvocazioniService.findById(new PkId(convocazione.getId().getCodice()));
	    if (convocazione.getIstanze() != null) {
		checkAccessoInformazioni(convocazione.getIstanze(), true);
	    }
	    convocazione.setDataconvocazione(d);
	    convocazione.setOraconvocazione(ora);
	    cdsconvocazioniService.update(convocazione);
	    response.setContentType("text/plain");
	} catch (Exception e) {
	    renderHTMLException(e.getMessage(), response);
	}
    }

    @RequestMapping
    public String ajaxInsertAmministrazioneInvitataCds(@RequestParam("codiceAmministrazione") Integer codiceAmministrazione,
	    @RequestParam("codiceCds") Integer codiceCds, Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {

	Amministrazioni amministrazione = amministrazioniService.findById(new PkId(codiceAmministrazione));
	Cds cds = cdsService.findById(new PkId(codiceCds));
	try {
	    if (cds.getIstanze() != null) {
		checkAccessoInformazioni(cds.getIstanze(), true);
	    }
	    Cdsinvitati cdsinvitati = new Cdsinvitati();
	    cdsinvitati.setAmministrazioni(amministrazione);
	    cdsinvitati.setIstanze(cds.getIstanze());
	    cdsinvitati.setCds(cds);
	    cdsinvitatiService.insert(cdsinvitati);
	    response.setContentType("text/plain");
	} catch (Exception e) {
	    renderHTMLException(e.getMessage(), response);
	}
	return "redirect:view.htm?codiceIstanza=" + cds.getIstanze().getId().getCodice() + "&amministrazioniVisibili=visibile";
    }

    @RequestMapping
    public String deleteInvitoAmministrazione(@RequestParam("codice") Integer codice, HttpServletRequest request) {

	Cdsinvitati objToDelete = cdsinvitatiService.findById(new PkId(codice));
	try {
	    if (objToDelete.getCds().getIstanze() != null) {
		checkAccessoInformazioni(objToDelete.getCds().getIstanze(), true);
	    }
	    cdsinvitatiService.delete(objToDelete);
	} catch (Exception e) {
	    log.error("Possibile anomalia nella cancellazione del record sulla tabella CDSINVITATI. \ncdsinvitatiService.delete(): Errore :" +
		      e.getMessage());
	    throw new RuntimeException(
		    "Possibile anomalia nella cancellazione del record sulla tabella CDSINVITATI. \ncdsinvitatiService.delete(): Errore :" +
				       e.getMessage());
	}
	return "redirect:view.htm?codiceIstanza=" + objToDelete.getIstanze().getId().getCodice() + "&amministrazioniVisibili=visibile";
    }

    @RequestMapping
    public String ajaxInsertAnagrafeInvitataCds(@RequestParam("codiceAnagrafe") Integer codiceAnagrafe, @RequestParam("codiceCds") Integer codiceCds,
	    Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {

	Anagrafe anagrafe = anagrafeService.findById(new PkId(codiceAnagrafe));
	Cds cds = cdsService.findById(new PkId(codiceCds));
	try {
	    if (cds.getIstanze() != null) {
		checkAccessoInformazioni(cds.getIstanze(), true);
	    }
	    Cdsinvitati2 cdsinvitati2 = new Cdsinvitati2();
	    cdsinvitati2.setAnagrafe(anagrafe);
	    cdsinvitati2.setIstanze(cds.getIstanze());
	    cdsinvitati2.setCds(cds);
	    cdsinvitati2Service.insert(cdsinvitati2);
	    response.setContentType("text/plain");
	} catch (Exception e) {
	    renderHTMLException(e.getMessage(), response);
	}
	return "redirect:view.htm?codiceIstanza=" + cds.getIstanze().getId().getCodice() + "&anagrafeVisibili=visibile";
    }

    @RequestMapping
    public String deleteInvitoAnagrafe(@RequestParam("codice") Integer codice, HttpServletRequest request) {

	Cdsinvitati2 objToDelete = cdsinvitati2Service.findById(new PkId(codice));
	try {
	    if (objToDelete.getCds().getIstanze() != null) {
		checkAccessoInformazioni(objToDelete.getCds().getIstanze(), true);
	    }
	    cdsinvitati2Service.delete(objToDelete);
	} catch (Exception e) {
	    log.error("Possibile anomalia nella cancellazione del record sulla tabella CDSINVITATI2. \ncdsinvitatiService.delete(): Errore :" +
		      e.getMessage());
	    throw new RuntimeException(
		    "Possibile anomalia nella cancellazione del record sulla tabella CDSINVITATI2. \ncdsinvitatiService.delete(): Errore :" +
				       e.getMessage());
	}
	return "redirect:view.htm?codiceIstanza=" + objToDelete.getIstanze().getId().getCodice() + "&anagrafeVisibili=visibile";
    }

    @RequestMapping
    public String ajaxCreateNoteAmministrazione(@RequestParam("codice") Integer codice, Model model, HttpServletResponse response)
	    throws IOException {

	Cdsinvitati cdsinvitati = cdsinvitatiService.findById(new PkId(codice));
	model.addAttribute("cdsinvitati", cdsinvitati);
	if (log.isDebugEnabled())
	    log.debug("call dettaglio istanze procedimenti with codice CdsInvitati: " + codice);
	response.setContentType("text/plain");
	return "ajax/dettaglioAmministrazioniCds";
    }

    @RequestMapping
    public String updateDettaglioAmministrazione(@ModelAttribute("cdsinvitati") Cdsinvitati cdsinvitati, BindingResult result, SessionStatus status,
	    HttpServletRequest request, HttpServletResponse response) throws IOException {

	try {
	    if (cdsinvitati.getCds().getIstanze() != null) {
		checkAccessoInformazioni(cdsinvitati.getCds().getIstanze(), true);
	    }
	    cdsinvitatiService.update(cdsinvitati);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, cdsinvitati, e);
	    return "cds/form";
	}
	return "redirect:view.htm?codiceIstanza=" + cdsinvitati.getIstanze().getId().getCodice() + "&amministrazioniVisibili=visibile";
    }

    @RequestMapping
    public String ajaxCreateNoteAnagrafe(@RequestParam("codice") Integer codice, Model model, HttpServletResponse response) throws IOException {

	Cdsinvitati2 cdsinvitati2 = cdsinvitati2Service.findById(new PkId(codice));
	model.addAttribute("cdsinvitati2", cdsinvitati2);
	if (log.isDebugEnabled())
	    log.debug("call dettaglio istanze procedimenti with codice CdsInvitati: " + codice);
	response.setContentType("text/plain");
	return "ajax/dettaglioAnagrafeCds";
    }

    @RequestMapping
    public String updateDettaglioAnagrafe(@ModelAttribute("cdsinvitati2") Cdsinvitati2 cdsinvitati2, BindingResult result, SessionStatus status,
	    HttpServletRequest request, HttpServletResponse response) throws IOException {

	try {
	    if (cdsinvitati2.getCds().getIstanze() != null) {
		checkAccessoInformazioni(cdsinvitati2.getCds().getIstanze(), true);
	    }
	    cdsinvitati2Service.update(cdsinvitati2);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, cdsinvitati2, e);
	    return "cds/form";
	}
	return "redirect:view.htm?codiceIstanza=" + cdsinvitati2.getIstanze().getId().getCodice() + "&anagrafeVisibili=visibile";
    }

    public void renderHTMLException(String message, HttpServletResponse response) throws IOException {

	StringBuilder sb = new StringBuilder("");
	sb.append(message);
	sb.append("");
	response.setContentType("text/plain");
	response.getWriter().write(sb.toString());
    }

    @Override
    protected void fixMergeEntityProperty(Cds entity) {

    }

    @Override
    protected void fixRenderEntityProperty(Cds entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
