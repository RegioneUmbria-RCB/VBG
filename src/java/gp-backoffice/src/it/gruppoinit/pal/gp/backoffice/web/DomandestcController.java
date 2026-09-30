package it.gruppoinit.pal.gp.backoffice.web;

import java.util.Date;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Domandestc;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.jmesa.DomandestcTable;
import it.gruppoinit.pal.gp.core.service.DomandestcService;
import it.gruppoinit.pal.gp.core.service.NlaManager;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;
import it.gruppoinit.pal.gp.core.utils.LoggerCancellazioni;
import it.gruppoinit.pal.gp.core.utils.Utilities;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jmesa.web.GenerateTable;
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
 * @author gianpaolot
 */
@Controller
@SessionAttributes("domandestc")
public class DomandestcController extends BaseController<Domandestc> {

    @Autowired
    private UserSecurityService userSecurityService;
    @Autowired
    private DomandestcService domandestcService;
    @Autowired
    private NlaManager nlaManager;

    @RequestMapping
    public String list(Model model, HttpServletRequest request, HttpServletResponse response) {

	GenerateTable<Domandestc> domandeTable = new DomandestcTable();
	String htmlTable = domandeTable.createJMesaList(request, response, "label.lista_domandestc", "domandestc_id", true);
	if (htmlTable == null) {
	    return null;
	}
	model.addAttribute("htmltable", htmlTable);
	model.addAttribute("domandestc", new Domandestc());
	return "domandestc/list";
    }

    @RequestMapping
    public String listFiltrate(Model model, @ModelAttribute("domandestc") Domandestc filter, BindingResult result, SessionStatus status,
	    HttpServletRequest request, HttpServletResponse response) {

	GenerateTable<Domandestc> domandeTable = new DomandestcTable(filter);
	String htmlTable = domandeTable.createJMesaList(request, response, "label.lista_domandestc", "domandestc_id", true);
	if (htmlTable == null) {
	    return null;
	}
	model.addAttribute("htmltable", htmlTable);
	model.addAttribute("domandestc", new Domandestc());
	return "domandestc/list";
    }

    @RequestMapping
    public String insertPratica(@RequestParam("codice") Integer codice, Model model, @ModelAttribute("domandestc") Domandestc domandestc,
	    BindingResult result, SessionStatus status, HttpServletRequest request, HttpServletResponse response) {

	PkId id = new PkId(codice);
	domandestc = domandestcService.findById(id);
	try {
	    fixMergeEntityProperty(domandestc);
	    nlaManager.inserimentoPraticaDaLocale(domandestc, ORMHelper.getToken());
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, domandestc, e);
	    GenerateTable<Domandestc> domandeTable = new DomandestcTable();
	    String htmlTable = domandeTable.createJMesaList(request, response, "label.lista_domandestc", "domandestc_id", false);
	    if (htmlTable == null) {
		return null;
	    }
	    model.addAttribute("htmltable", htmlTable);
	    return "domandestc/list";
	}
	fixRenderEntityProperty(domandestc);
	model.addAttribute("domandestc", domandestc);
	setPageAttributes(model);
	return "redirect:list.htm";
    }

    //    @RequestMapping
    //    public String create(Model model) {
    //
    //	Domandestc domandestc = new Domandestc();
    //	
    //	fixRenderEntityProperty(domandestc);
    //	model.addAttribute("domandestc", domandestc);
    //	setPageAttributes(model);
    //	return "domandestc/form";
    //    }
    //
    //    @RequestMapping
    //    public String insert(@ModelAttribute("domandestc") Domandestc domandestc, BindingResult result, SessionStatus status) {
    //
    //	fixMergeEntityProperty(domandestc);
    //	
    //	try {
    //	    domandestcService.insert(domandestc);
    //	} catch (Exception e) {
    //	    copyErrorsToBindingResult(domandestcService.getValidationMessages(), result, domandestc, e.getMessage());
    //	    fixRenderEntityProperty(domandestc);
    //	    return "domandestc/form";
    //	}
    //	status.setComplete();
    //	return "redirect:view.htm?codice=" + domandestc.getId().getCodice() + "&status_msg=01";
    //    }
    //
    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	Domandestc domandestc = domandestcService.findById(id);
	fixRenderEntityProperty(domandestc);
	model.addAttribute("domandestc", domandestc);
	setPageAttributes(model);
	return "domandestc/form";
    }

    //
    //    @RequestMapping
    //    public String update(@ModelAttribute("domandestc") Domandestc domandestc, BindingResult result, SessionStatus status, HttpServletRequest request) {
    //
    //	fixMergeEntityProperty(domandestc);
    //	try {
    //	    domandestcService.update(domandestc);
    //	} catch (Exception e) {
    //	    copyErrorsToBindingResult(domandestcService.getValidationMessages(), result, domandestc, e.getMessage());
    //	    fixRenderEntityProperty(domandestc);
    //	    return "domandestc/form";
    //	}
    //	status.setComplete();
    //	return "redirect:view.htm?codice=" + domandestc.getId().getCodice() + "&status_msg=02";
    //    }
    //
    @RequestMapping
    public String delete(@RequestParam("codice") Integer codice, Model model, @ModelAttribute("domandestc") Domandestc domandestc,
	    BindingResult result, SessionStatus status, HttpServletRequest request, HttpServletResponse response) {

	Responsabili userlogged = (Responsabili) userSecurityService.getCurrentlyAuthenticatedUserDetails();
	if (userlogged == null) {
	    throw new SecurityException("Nessun operatore trovato nel contesto della sicurezza.");
	}
	Domandestc objToDelete = domandestcService.findById(new PkId(codice));
	String domandaStcString = "{ID:" + objToDelete.getId().toString() + "} ";
	domandaStcString += "CFRICHIEDENTE:" + objToDelete.getCodicefiscaleRichiedente();
	domandaStcString += ", IDDOMANDAMITT:" + objToDelete.getIdDomandamitt();
	domandaStcString += ", [IDNODO:" + objToDelete.getIdNodo();
	domandaStcString += ", IDENTE:" + objToDelete.getIdEntemitt();
	domandaStcString += ", IDSPORTELLO:" + objToDelete.getIdSportellomitt() + "]";
	domandaStcString += ", NUMEROISTANZA:" + objToDelete.getNumeroistanza();
	domandaStcString += ", RICHIEDENTE:" + objToDelete.getRichiedente();
	boolean isCancellaIstanze = userlogged.getFlagCancellaistanze() == null ? false : userlogged.getFlagCancellaistanze().booleanValue();
	if (!isCancellaIstanze) {
	    Responsabili r = getCurrentlyAuthenticatedUserDetails();
	    LoggerCancellazioni.log("#ERRORE_CANCELLAZIONEDOMANDASTC#In data " + Utilities.formatDate(new Date(), true) + " l'operatore "
		    + r.getResponsabile() + " ha cancellato la domandastc [" + domandaStcString + "]");
	    throw new SecurityException("Non si ha le abilitazioni per cancellare le istanze.");
	}
	try {
	    domandestcService.delete(objToDelete);
	    Responsabili r = getCurrentlyAuthenticatedUserDetails();
	    LoggerCancellazioni.log("#CANCELLAZIONEDOMANDASTC#In data " + Utilities.formatDate(new Date(), true) + " l'operatore "
		    + r.getResponsabile() + " ha cancellato la domandastc [" + domandaStcString + "]");
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, domandestc, e);
	    GenerateTable<Domandestc> domandeTable = new DomandestcTable();
	    String htmlTable = domandeTable.createJMesaList(request, response, "label.lista_domandestc", "domandestc_id", false);
	    if (htmlTable == null) {
		return null;
	    }
	    model.addAttribute("htmltable", htmlTable);
	    return "domandestc/list";
	}
	model.addAttribute("domandestc", domandestc);
	setPageAttributes(model);
	status.setComplete();
	return "redirect:list.htm";
    }

    @Override
    protected void fixMergeEntityProperty(Domandestc entity) {

	//	if (EntityUtils.isNestedPropertyBlank(entity.getRichiedente(), "id.codice")) {
	//	    entity.setRichiedente(null);
	//	}
    }

    @Override
    protected void fixRenderEntityProperty(Domandestc entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
