package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.CcIcalcolotot;
import it.gruppoinit.pal.gp.core.domain.CcValiditacoefficienti;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.CcIcalcolototService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;

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

/**
 * 
 * @author
 */
@Controller
@SessionAttributes("ccicalcolotot")
public class CcIcalcolototController extends BaseController<CcIcalcolotot> {

    @Autowired
    private CcIcalcolototService ccicalcolototService;
    @Autowired
    private IstanzeService istanzeService;

    @RequestMapping
    public ModelMap list(@RequestParam("codiceIstanza") Integer codiceIstanza, HttpServletRequest request, HttpServletResponse response) {

	Istanze istanze = istanzeService.findById(new PkId(codiceIstanza));
	List<CcIcalcolotot> ccicalcolototList = ccicalcolototService.findByIstanza(istanze);
	ModelMap model = new ModelMap(ccicalcolototList);
	boolean export = createJMesaExport(request, response, ccicalcolototList);
	if (export) {
	    return null;
	}
	model.addAttribute("istanze", istanze);
	model.addAttribute("ccicalcolototList", ccicalcolototList);
	return model;
    }

    @RequestMapping
    public String create(@RequestParam("CodiceIstanza") Integer codiceistanza, Model model) {

	Istanze istanze = istanzeService.findById(new PkId(codiceistanza));
	CcIcalcolotot ccicalcolotot = new CcIcalcolotot();
	ccicalcolotot.setIstanze(istanze);
	fixRenderEntityProperty(ccicalcolotot);
	model.addAttribute("istanze", istanze);
	model.addAttribute("ccicalcolotot", ccicalcolotot);
	setPageAttributes(model);
	return "ccicalcolotot/form";
    }

    @RequestMapping
    public String insert(Model model, @ModelAttribute("ccicalcolotot") CcIcalcolotot ccicalcolotot, BindingResult result, SessionStatus status) {

	fixMergeEntityProperty(ccicalcolotot);
	try {
	    ccicalcolototService.insert(ccicalcolotot);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, ccicalcolotot, e);
	    model.addAttribute("istanze", ccicalcolotot.getIstanze());
	    fixRenderEntityProperty(ccicalcolotot);
	    return "ccicalcolotot/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + ccicalcolotot.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	CcIcalcolotot ccicalcolotot = ccicalcolototService.findById(id);
	fixRenderEntityProperty(ccicalcolotot);
	model.addAttribute("istanze", ccicalcolotot.getIstanze());
	model.addAttribute("ccicalcolotot", ccicalcolotot);
	setPageAttributes(model);
	return "ccicalcolotot/form";
    }

    @RequestMapping
    public String update(Model model, @ModelAttribute("ccicalcolotot") CcIcalcolotot ccicalcolotot, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	fixMergeEntityProperty(ccicalcolotot);
	try {
	    ccicalcolototService.update(ccicalcolotot);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, ccicalcolotot, e);
	    fixRenderEntityProperty(ccicalcolotot);
	    model.addAttribute("istanze", ccicalcolotot.getIstanze());
	    return "ccicalcolotot/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + ccicalcolotot.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String delete(Model model, @ModelAttribute("ccicalcolotot") CcIcalcolotot ccicalcolotot, BindingResult result, SessionStatus status) {

	CcIcalcolotot objToDelete = ccicalcolototService.findById(ccicalcolotot.getId());
	try {
	    ccicalcolototService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, ccicalcolotot, e);
	    fixRenderEntityProperty(ccicalcolotot);
	    model.addAttribute("istanze", ccicalcolotot.getIstanze());
	    return "ccicalcolotot/form";
	}
	status.setComplete();
	return "redirect:list.htm?codiceIstanza=" + ccicalcolotot.getIstanze().getId().getCodice();
    }

    @Override
    protected void fixMergeEntityProperty(CcIcalcolotot entity) {

    }

    @Override
    protected void fixRenderEntityProperty(CcIcalcolotot entity) {

	//	if (entity.getOccBasetipointervento() == null) {
	//	    entity.setOccBasetipointervento(new OccBasetipointervento());
	//	}
	//	if (entity.getOccBasedestinazioni() == null) {
	//	    entity.setOccBasedestinazioni(new OccBasedestinazioni());
	//	}
	//	if (entity.getCcBasetipocalcolo() == null) {
	//	    entity.setCcBasetipocalcolo(new CcBasetipocalcolo());
	//	}
	if (entity.getCcValiditacoefficienti() == null) {
	    entity.setCcValiditacoefficienti(new CcValiditacoefficienti());
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
