package it.gruppoinit.pal.gp.backoffice.web;

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

import it.gruppoinit.pal.gp.core.domain.Peopleprocsportelli;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.helper.PeopleprocsportelliHelper;
import it.gruppoinit.pal.gp.core.security.LoggedUser;
import it.gruppoinit.pal.gp.core.service.PeopleprocsportelliService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;
import it.gruppoinit.pal.gp.core.service.helper.FlashMessages;

/**
 * 
 * @author
 */
@Controller
@SessionAttributes(value = { "peopleprocsportelli", "peopleprocsportelliHelper" })
public class PeopleprocsportelliController extends BaseController<Peopleprocsportelli> {

    @Autowired
    private PeopleprocsportelliService peopleprocsportelliService;
    @Autowired
    private ResponsabiliService responsabiliService;
    @Autowired
    private SoftwareService softwareService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	List<Peopleprocsportelli> peopleprocsportelliList = peopleprocsportelliService.findAll(null, null);
	ModelMap model = new ModelMap(peopleprocsportelliList);
	model.addAttribute("peopleprocsportelliList", peopleprocsportelliList);
	return model;
    }

    @RequestMapping
    public String create(Model model) {

	setPageAttributes(model);
	PeopleprocsportelliHelper peopleprocsportelliHelper = new PeopleprocsportelliHelper();
	model.addAttribute("peopleprocsportelliHelper", peopleprocsportelliHelper);
	return "peopleprocsportelli/formCreazione";
    }

    @RequestMapping
    public String insert(@ModelAttribute("peopleprocsportelliHelper") PeopleprocsportelliHelper peopleprocsportelliHelper, BindingResult result,
	    SessionStatus status) {

	try {
	    peopleprocsportelliService.creaRecord(peopleprocsportelliHelper);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, peopleprocsportelliHelper, e);
	    FlashMessages.getWarnings().add("Errore in inserimento: " + e.getMessage());
	    return "redirect:create.htm?status_msg=03";
	}
	status.setComplete();
	return "redirect:list.htm?status_msg=01";
    }

    @RequestMapping
    public void ajaxEliminaRiga(@RequestParam("idRiga") Integer idRiga, Model model, HttpServletRequest request, HttpServletResponse response)
	    throws Exception {

	Peopleprocsportelli entity = peopleprocsportelliService.findById(new PkId(idRiga));
	try {
	    peopleprocsportelliService.delete(entity);
	    response.getOutputStream().write("OK".getBytes());
	} catch (Exception e) {
	    response.getOutputStream().write(e.getMessage().getBytes());
	}
    }

    @Override
    protected void fixMergeEntityProperty(Peopleprocsportelli entity) {

    }

    @Override
    protected void fixRenderEntityProperty(Peopleprocsportelli entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

	LoggedUser userDetail = (LoggedUser) getCurrentlyAuthenticatedUser();
	PkId idResponsabile = new PkId(userDetail.getCodiceResponsabile());
	Responsabili responsabile = responsabiliService.findById(idResponsabile);
	List<Software> listSoftware = softwareService.findSoftwareAbilitati(responsabile);
	//Rimosso il software TT
	Software softwareTT = softwareService.findById("TT");
	listSoftware.remove(softwareTT);
	model.addAttribute("listSoftware", listSoftware);
    }
}
