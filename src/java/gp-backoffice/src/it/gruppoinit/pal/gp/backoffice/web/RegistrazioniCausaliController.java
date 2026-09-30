/**
 * 
 */
package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.RegistrazioniCausali;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.service.RegistrazioniCausaliService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

/**
 * @author francescop
 * 
 */
@Controller
@SessionAttributes("registrazioniCausali")
public class RegistrazioniCausaliController extends BaseController<RegistrazioniCausali> {

    // private static final Logger log = LoggerFactory.getLogger(RegistrazioniCausaliController.class);
    @Autowired
    private RegistrazioniCausaliService registrazioniCausaliService;
    @Autowired
    private SoftwareService softwareService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	// §§§BEGIN§§§
	List<RegistrazioniCausali> registrazioniCausaliList = registrazioniCausaliService.findAll(null, null);
	ModelMap model = new ModelMap(registrazioniCausaliList);
	boolean export = createJMesaExport(request, response, registrazioniCausaliList);
	if (export)
	    return null;
	model.addAttribute("registrazioniCausaliList", registrazioniCausaliList);
	return model;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String delete(Model model, @ModelAttribute("registrazioniCausali") RegistrazioniCausali registrazioniCausali, BindingResult result,
	    SessionStatus status) {

	// §§§BEGIN§§§
	Integer codice = registrazioniCausali.getId().getCodice();
	RegistrazioniCausali objToDelete = registrazioniCausaliService.findById(registrazioniCausali.getId());
	try {
	    registrazioniCausaliService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    Map<String, String> map = new HashMap<String, String>();
	    map.put("codice", codice.toString());
	    model.addAttribute("commandName", "registrazioniCausali");
	    model.addAttribute("method", "view.htm");
	    model.addAttribute("queryStringParams", map);
	    return "error";
	}
	status.setComplete();
	return "redirect:list.htm";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String insert(@ModelAttribute("registrazioniCausali") RegistrazioniCausali registrazioniCausali, BindingResult result, SessionStatus status) {

	// §§§BEGIN§§§
	fixMergeEntityProperty(registrazioniCausali);
	try {
	    registrazioniCausaliService.insert(registrazioniCausali);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, registrazioniCausali, e);
	    fixRenderEntityProperty(registrazioniCausali);
	    return "registrazionicausali/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + registrazioniCausali.getId().getCodice() + "&status_msg=01";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String update(@ModelAttribute("registrazioniCausali") RegistrazioniCausali registrazioniCausali, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	// §§§BEGIN§§§
	fixMergeEntityProperty(registrazioniCausali);
	try {
	    registrazioniCausaliService.update(registrazioniCausali);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, registrazioniCausali, e);
	    fixRenderEntityProperty(registrazioniCausali);
	    return "registrazionicausali/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + registrazioniCausali.getId().getCodice() + "&status_msg=02";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String create(Model model) {

	// §§§BEGIN§§§
	RegistrazioniCausali registrazioniCausali = new RegistrazioniCausali();
	registrazioniCausali.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
	fixRenderEntityProperty(registrazioniCausali);
	model.addAttribute("registrazioniCausali", registrazioniCausali);
	setPageAttributes(model);
	return "registrazionicausali/form";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	// §§§BEGIN§§§
	PkId id = new PkId(codice);
	RegistrazioniCausali registrazioniCausali = registrazioniCausaliService.findById(id);
	fixRenderEntityProperty(registrazioniCausali);
	model.addAttribute("registrazioniCausali", registrazioniCausali);
	setPageAttributes(model);
	return "registrazionicausali/form";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    protected void setPageAttributes(Model model) {

    }

    @Override
    protected void fixMergeEntityProperty(RegistrazioniCausali entity) {

	if (entity.getOrdine() == null) {
	    entity.setOrdine(0);
	}
    }

    @Override
    protected void fixRenderEntityProperty(RegistrazioniCausali entity) {

	if (entity.getSoftware() == null) {
	    entity.setSoftware(new Software());
	}
    }
}
