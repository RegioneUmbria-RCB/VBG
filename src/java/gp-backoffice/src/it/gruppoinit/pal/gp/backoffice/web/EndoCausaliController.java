/**
 * 
 */
package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.EndoCausali;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.RegistrazioniCausali;
import it.gruppoinit.pal.gp.core.service.EndoCausaliService;
import it.gruppoinit.pal.gp.core.service.InventarioprocedimentiService;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

/**
 * @author lucap
 * 
 */
@Controller
@SessionAttributes("endoCausali")
public class EndoCausaliController extends BaseController<EndoCausali> {

    private static final Logger log = LoggerFactory.getLogger(EndoCausaliController.class);
    @Autowired
    private EndoCausaliService endoCausaliService;
    @Autowired
    private InventarioprocedimentiService inventarioprocedimentiService;

    @RequestMapping
    public ModelMap list(@RequestParam("inventarioprocedimenti.id.codice") Integer codiceInventarioprocedimenti, HttpServletRequest request,
	    HttpServletResponse response) {

	if (codiceInventarioprocedimenti == null) {
	    log.error("Il parametro Codice Inventarioprocedimenti è obbligatorio");
	    throw new SecurityException("Il parametro Codice Inventarioprocedimenti è obbligatorio");
	}
	Inventarioprocedimenti inventarioprocedimenti = inventarioprocedimentiService.findById(new PkId(codiceInventarioprocedimenti));
	EndoCausali entity = new EndoCausali();
	entity.setInventarioprocedimenti(inventarioprocedimenti);
	List<EndoCausali> endoCausaliList = endoCausaliService.findByInventarioprocedimenti(entity);
	ModelMap model = new ModelMap(endoCausaliList);
	boolean export = createJMesaExport(request, response, endoCausaliList);
	if (export)
	    return null;
	model.addAttribute("endoCausaliList", endoCausaliList);
	model.addAttribute("inventarioprocedimenti", inventarioprocedimenti);
	return model;
    }

    @RequestMapping
    public String delete(@ModelAttribute("endoCausali") EndoCausali endoCausali, BindingResult result, SessionStatus status) {

	Integer codiceInventarioprocedimenti = endoCausali.getInventarioprocedimenti().getId().getCodice();
	EndoCausali objToDelete = endoCausaliService.findById(endoCausali.getId());
	try {
	    endoCausaliService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderEntityProperty(endoCausali);
	    return "endocausali/form";
	}
	status.setComplete();
	return "redirect:list.htm?inventarioprocedimenti.id.codice=" + codiceInventarioprocedimenti;
    }

    @RequestMapping
    public String insert(@ModelAttribute("endoCausali") EndoCausali endoCausali, BindingResult result, SessionStatus status) {

	// se necessario inserire parte di codice che deve ricercare altri attribbuti da settare
	// all'oggetto del dominio
	fixMergeEntityProperty(endoCausali);
	try {
	    endoCausaliService.insert(endoCausali);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, endoCausali, e);
	    fixRenderEntityProperty(endoCausali);
	    return "endocausali/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + endoCausali.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String update(@ModelAttribute("endoCausali") EndoCausali endoCausali, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	// se necessario inserire parte di codice che deve ricercare altri attribbuti da settare
	// all'oggetto del dominio
	fixMergeEntityProperty(endoCausali);
	try {
	    endoCausaliService.update(endoCausali);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, endoCausali, e);
	    fixRenderEntityProperty(endoCausali);
	    return "endocausali/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + endoCausali.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String create(@RequestParam("inventarioprocedimenti.id.codice") Integer codiceInventarioprocedimenti, Model model) {

	EndoCausali endoCausali = new EndoCausali();
	PkId idInventarioprocedimenti = new PkId(codiceInventarioprocedimenti);
	Inventarioprocedimenti inventarioprocedimenti = inventarioprocedimentiService.findById(idInventarioprocedimenti);
	endoCausali.setInventarioprocedimenti(inventarioprocedimenti);
	fixRenderEntityProperty(endoCausali);
	model.addAttribute("endoCausali", endoCausali);
	setPageAttributes(model);
	return "endocausali/form";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	EndoCausali endoCausali = endoCausaliService.findById(id);
	fixRenderEntityProperty(endoCausali);
	model.addAttribute("endoCausali", endoCausali);
	setPageAttributes(model);
	return "endocausali/form";
    }

    @Override
    protected void fixMergeEntityProperty(EndoCausali entity) {

	if (entity.getRegistrazioniCausali() != null && entity.getRegistrazioniCausali().getId() != null
		&& entity.getRegistrazioniCausali().getId().getCodice() == null) {
	    entity.setRegistrazioniCausali(null);
	}
	if (entity.getInventarioprocedimenti() != null && entity.getInventarioprocedimenti().getId() != null
		&& entity.getInventarioprocedimenti().getId().getCodice() == null) {
	    entity.setInventarioprocedimenti(null);
	}
    }

    @Override
    protected void fixRenderEntityProperty(EndoCausali entity) {

	if (entity.getRegistrazioniCausali() == null) {
	    entity.setRegistrazioniCausali(new RegistrazioniCausali());
	}
	if (entity.getInventarioprocedimenti() == null) {
	    entity.setInventarioprocedimenti(new Inventarioprocedimenti());
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
