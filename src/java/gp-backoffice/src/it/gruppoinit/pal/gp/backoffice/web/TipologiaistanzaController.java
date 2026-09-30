/**
 * 
 */
package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.Tipologiaistanza;
import it.gruppoinit.pal.gp.core.service.SoftwareService;
import it.gruppoinit.pal.gp.core.service.TipologiaistanzaService;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

@Controller
@SessionAttributes("tipologiaistanza")
public class TipologiaistanzaController extends BaseController<Tipologiaistanza> {

    @Autowired
    private TipologiaistanzaService tipologiaistanzaService;
    @Autowired
    private SoftwareService softwareService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	List<Tipologiaistanza> tipologiaistanzaList = tipologiaistanzaService.findAll(null, null);
	ModelMap model = new ModelMap(tipologiaistanzaList);
	boolean export = createJMesaExport(request, response, tipologiaistanzaList);
	if (export)
	    return null;
	model.addAttribute("tipologiaistanzaList", tipologiaistanzaList);
	return model;
    }

    @RequestMapping
    public String delete(@ModelAttribute("tipologiaistanza") Tipologiaistanza tipologiaistanza, BindingResult result, SessionStatus status) {

	Tipologiaistanza objToDelete = tipologiaistanzaService.findById(tipologiaistanza.getId());
	try {
	    tipologiaistanzaService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderEntityProperty(tipologiaistanza);
	    return "tipologiaistanza/form";
	}
	status.setComplete();
	return "redirect:list.htm";
    }

    @RequestMapping
    public String insert(@ModelAttribute("tipologiaistanza") Tipologiaistanza tipologiaistanza, BindingResult result, SessionStatus status) {

	// recupero il software corrente e lo setto in letteretipo
	Software software = softwareService.findById(ORMHelper.getSoftware());
	tipologiaistanza.setSoftware(software);
	fixMergeEntityProperty(tipologiaistanza);
	try {
	    tipologiaistanzaService.insert(tipologiaistanza);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, tipologiaistanza, e);
	    fixRenderEntityProperty(tipologiaistanza);
	    return "tipologiaistanza/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + tipologiaistanza.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String update(@ModelAttribute("tipologiaistanza") Tipologiaistanza tipologiaistanza, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	// recupero il software corrente e lo setto in letteretipo
	Software software = softwareService.findById(ORMHelper.getSoftware());
	tipologiaistanza.setSoftware(software);
	fixMergeEntityProperty(tipologiaistanza);
	try {
	    tipologiaistanzaService.update(tipologiaistanza);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, tipologiaistanza, e);
	    fixRenderEntityProperty(tipologiaistanza);
	    return "ntipologiaistanza/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + tipologiaistanza.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String create(Model model) {

	Tipologiaistanza tipologiaistanza = new Tipologiaistanza();
	tipologiaistanza.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
	fixRenderEntityProperty(tipologiaistanza);
	model.addAttribute("tipologiaistanza", tipologiaistanza);
	setPageAttributes(model);
	return "tipologiaistanza/form";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	Tipologiaistanza tipologiaistanza = tipologiaistanzaService.findById(id);
	fixRenderEntityProperty(tipologiaistanza);
	model.addAttribute("tipologiaistanza", tipologiaistanza);
	setPageAttributes(model);
	return "tipologiaistanza/form";
    }

    @Override
    protected void fixMergeEntityProperty(Tipologiaistanza entity) {

	if (entity.getSoftware() != null && (entity.getSoftware().getCodice() == null || entity.getSoftware().getCodice().equals(""))) {
	    entity.setSoftware(null);
	}
    }

    @Override
    protected void fixRenderEntityProperty(Tipologiaistanza entity) {

	if (entity.getSoftware() == null) {
	    entity.setSoftware(new Software());
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

	// TODO Auto-generated method stub
    }
}
