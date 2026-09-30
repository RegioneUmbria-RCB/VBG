package it.gruppoinit.pal.gp.backoffice.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.MetadatiDizBase;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.ProtTipidocumentoMetadati;
import it.gruppoinit.pal.gp.core.domain.ProtTipidocumentoMetadatiId;
import it.gruppoinit.pal.gp.core.domain.ProtocolloTipidocumento;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Responsabilicomuni;
import it.gruppoinit.pal.gp.core.domain.Responsabilisoftware;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.MetadatiDizBaseService;
import it.gruppoinit.pal.gp.core.service.ProtTipidocumentoMetadatiService;
import it.gruppoinit.pal.gp.core.service.ProtocolloTipidocumentoService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliService;

@Controller
@SessionAttributes("protocollotipidocumento")
public class ProtocolloTipidocumentoController extends BaseController<ProtocolloTipidocumento> {

    @Autowired
    private ProtocolloTipidocumentoService protocolloTipidocumentoService;
    @Autowired
    private ProtTipidocumentoMetadatiService protTipidocumentoMetadatiService;
    @Autowired
    private ResponsabiliService responsabiliService;
    @Autowired
    private VerticalizzazioniService verticalizzazioniService;
    @Autowired
    private MetadatiDizBaseService metadatiDizBaseService;

    @RequestMapping
    public String list(Model model, HttpServletRequest request, HttpServletResponse response) {

	List<ProtocolloTipidocumento> protocolloTipidocumentoList = protocolloTipidocumentoService.findAllOrederByComuneAndSoftware(null, null);
	boolean export = createJMesaExport(request, response, protocolloTipidocumentoList);
	if (export)
	    return null;
	model.addAttribute("protocolloTipidocumentoList", protocolloTipidocumentoList);
	return "protocollotipidocumento/list";
    }

    @RequestMapping
    public String create(Model model) {

	ProtocolloTipidocumento protocolloTipidocumento = new ProtocolloTipidocumento();
	fixRenderEntityProperty(protocolloTipidocumento);
	Responsabili responsabile = getCurrentlyAuthenticatedUserDetails();
	Set<Responsabilicomuni> responsabilicomunis = responsabiliService.findListResponsabilicomuni(responsabile);
	Set<Responsabilisoftware> responsabilisoftwares = responsabiliService.findListResponsabilisoftware(responsabile);
	model.addAttribute("responsabilicomunis", responsabilicomunis);
	model.addAttribute("responsabilisoftwares", responsabilisoftwares);
	model.addAttribute("protocollotipidocumento", protocolloTipidocumento);
	setPageAttributes(model);
	return "protocollotipidocumento/form";
    }

    @RequestMapping
    public String insert(Model model, @ModelAttribute("protocollotipidocumento") ProtocolloTipidocumento protocolloTipidocumento,
	    BindingResult result, SessionStatus status) {

	fixMergeEntityProperty(protocolloTipidocumento);
	try {
	    protocolloTipidocumentoService.insert(protocolloTipidocumento);
	} catch (Exception e) {
	    Responsabili responsabile = getCurrentlyAuthenticatedUserDetails();
	    Set<Responsabilicomuni> responsabilicomunis = responsabiliService.findListResponsabilicomuni(responsabile);
	    Set<Responsabilisoftware> responsabilisoftwares = responsabiliService.findListResponsabilisoftware(responsabile);
	    model.addAttribute("responsabilicomunis", responsabilicomunis);
	    model.addAttribute("responsabilisoftwares", responsabilisoftwares);
	    copyErrorsToBindingResult(result, protocolloTipidocumento, e);
	    fixRenderEntityProperty(protocolloTipidocumento);
	    return "protocollotipidocumento/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + protocolloTipidocumento.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	ProtocolloTipidocumento protocolloTipidocumento = protocolloTipidocumentoService.findById(new PkId(codice));
	fixRenderEntityProperty(protocolloTipidocumento);
	model.addAttribute("protocollotipidocumento", protocolloTipidocumento);
	setPageAttributes(model);
	return "protocollotipidocumento/form";
    }

    @RequestMapping
    public String update(Model model, @ModelAttribute("protocollotipidocumento") ProtocolloTipidocumento protocolloTipidocumento,
	    BindingResult result, SessionStatus status, HttpServletRequest request) {

	// all'oggetto del dominio
	fixMergeEntityProperty(protocolloTipidocumento);
	try {
	    protocolloTipidocumentoService.update(protocolloTipidocumento);
	} catch (Exception e) {
	    Responsabili responsabile = getCurrentlyAuthenticatedUserDetails();
	    Set<Responsabilicomuni> responsabilicomunis = responsabiliService.findListResponsabilicomuni(responsabile);
	    Set<Responsabilisoftware> responsabilisoftwares = responsabiliService.findListResponsabilisoftware(responsabile);
	    model.addAttribute("responsabilicomunis", responsabilicomunis);
	    model.addAttribute("responsabilisoftwares", responsabilisoftwares);
	    copyErrorsToBindingResult(result, protocolloTipidocumento, e);
	    fixRenderEntityProperty(protocolloTipidocumento);
	    return "protocollotipidocumento/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + protocolloTipidocumento.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public void ajaxEliminaMetadato(@RequestParam("codice") Integer codice, @RequestParam("metadatoId") String metadatoId, Model model,
	    HttpServletRequest request, HttpServletResponse response) throws IOException {

	ProtTipidocumentoMetadatiId id = new ProtTipidocumentoMetadatiId(codice, metadatoId);
	ProtTipidocumentoMetadati protocolloTipidocumento = protTipidocumentoMetadatiService.findById(id);
	response.setContentType("text/plain");
	if (null == protocolloTipidocumento) {
	    response.getWriter().write("Errore non è possibile eliminare un dato nullo.");
	    return;
	}
	try {
	    protTipidocumentoMetadatiService.delete(protocolloTipidocumento);
	    response.getWriter().write("OK");
	    return;
	} catch (Exception e) {
	    response.getWriter().write("Errore nella cancellazione del dato: " + e.getMessage());
	}
    }

    @RequestMapping
    public void ajaxAggiungiMetadato(@RequestParam("codice") Integer codice, @RequestParam("metadatoId") String metadatoId, Model model,
	    HttpServletRequest request, HttpServletResponse response) throws IOException {

	ProtTipidocumentoMetadatiId id = new ProtTipidocumentoMetadatiId(codice, metadatoId);
	ProtTipidocumentoMetadati p = new ProtTipidocumentoMetadati();
	p.setId(id);
	response.setContentType("text/plain");
	try {
	    protTipidocumentoMetadatiService.insert(p);
	    response.getWriter().write("OK");
	    return;
	} catch (Exception e) {
	    response.getWriter().write("Errore nell'inserimento del dato: " + e.getMessage());
	}
    }

    @RequestMapping
    public String ajaxPopolaTabellaMetadati(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	List<ProtTipidocumentoMetadati> protocolloTipidocumento = protTipidocumentoMetadatiService.findByProtTipiDocumento(codice);
	model.addAttribute("list", protocolloTipidocumento);
	List<MetadatiDizBase> mdbs = metadatiDizBaseService.findAll(null, null);
	List<MetadatiDizBase> mdbs2 = new ArrayList<MetadatiDizBase>();
	for (MetadatiDizBase md : mdbs) {
	    boolean trovato = false;
	    for (ProtTipidocumentoMetadati ptm : protocolloTipidocumento) {
		if (ptm.getId().getFkidmetadatidizbase().equalsIgnoreCase(md.getId())) {
		    trovato = true;
		}
	    }
	    if (!trovato) {
		mdbs2.add(md);
	    }
	}
	model.addAttribute("listMDB", mdbs2);
	return "protocollotipidocumento/ajaxMetadati";
    }

    @RequestMapping
    public String delete(Model model, @ModelAttribute("protocollotipidocumento") ProtocolloTipidocumento protocolloTipidocumento,
	    BindingResult result, SessionStatus status) {

	ProtocolloTipidocumento objToDelete = protocolloTipidocumentoService.findById(protocolloTipidocumento.getId());
	try {
	    protocolloTipidocumentoService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, protocolloTipidocumento, e);
	    fixRenderEntityProperty(protocolloTipidocumento);
	    return "protocollotipidocumento/form";
	}
	status.setComplete();
	return "redirect:list.htm";
    }

    @Override
    protected void setPageAttributes(Model model) {

	// boolean docERAttivo = verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_PROTOCOLLO_DOCER);
	// model.addAttribute("isDocErAttivo", docERAttivo);
    }

    @Override
    protected void fixMergeEntityProperty(ProtocolloTipidocumento entity) {

	// Implementato nel service
    }

    @Override
    protected void fixRenderEntityProperty(ProtocolloTipidocumento entity) {

	if (entity.getSoftware() == null) {
	    entity.setSoftware(new Software());
	}
	if (entity.getComune() == null) {
	    entity.setComune(new Comuni());
	}
    }
}
