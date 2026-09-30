package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.GruppiIstruttori;
import it.gruppoinit.pal.gp.core.domain.GruppiIstruttoriResp;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.service.GruppiIstruttoriRespService;
import it.gruppoinit.pal.gp.core.service.GruppiIstruttoriService;

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
@SessionAttributes("gruppiistruttoriresp")
public class GruppiIstruttoriRespController extends BaseController<GruppiIstruttoriResp> {

    @Autowired
    private GruppiIstruttoriRespService gruppiistruttorirespService;
    @Autowired
    private GruppiIstruttoriService gruppiIstruttoriService;

    @RequestMapping
    public ModelMap list(@RequestParam("codice") Integer codice, HttpServletRequest request, HttpServletResponse response) {

	List<GruppiIstruttoriResp> gruppiistruttorirespList = gruppiistruttorirespService.findByGruppoIstruttori(codice, true, true, null, null);
	ModelMap model = new ModelMap(gruppiistruttorirespList);
	boolean export = createJMesaExport(request, response, gruppiistruttorirespList);
	if (export) {
	    return null;
	}
	model.addAttribute("gruppiistruttorirespList", gruppiistruttorirespList);
	model.addAttribute("codicegruppo", codice);
	return model;
    }

    @RequestMapping
    public String create(@RequestParam("codice") Integer codice, Model model) {

	GruppiIstruttori gruppiIstruttori = gruppiIstruttoriService.findById(new PkId(codice));
	GruppiIstruttoriResp gruppiistruttoriresp = new GruppiIstruttoriResp();
	gruppiistruttoriresp.setGruppiIstruttori(gruppiIstruttori);
	fixRenderEntityProperty(gruppiistruttoriresp);
	model.addAttribute("gruppiistruttoriresp", gruppiistruttoriresp);
	model.addAttribute("codicegruppo", codice);
	setPageAttributes(model);
	return "gruppiistruttoriresp/form";
    }

    @RequestMapping
    public String insert(@ModelAttribute("gruppiistruttoriresp") GruppiIstruttoriResp gruppiistruttoriresp, BindingResult result, SessionStatus status) {

	fixMergeEntityProperty(gruppiistruttoriresp);
	try {
	    gruppiistruttorirespService.insert(gruppiistruttoriresp);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, gruppiistruttoriresp, e);
	    fixRenderEntityProperty(gruppiistruttoriresp);
	    return "gruppiistruttoriresp/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + gruppiistruttoriresp.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	GruppiIstruttoriResp gruppiistruttoriresp = gruppiistruttorirespService.findById(id);
	fixRenderEntityProperty(gruppiistruttoriresp);
	model.addAttribute("gruppiistruttoriresp", gruppiistruttoriresp);
	model.addAttribute("codicegruppo", gruppiistruttoriresp.getGruppiIstruttori().getId().getCodice());
	setPageAttributes(model);
	return "gruppiistruttoriresp/form";
    }

    @RequestMapping
    public String update(@ModelAttribute("gruppiistruttoriresp") GruppiIstruttoriResp gruppiistruttoriresp, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	fixMergeEntityProperty(gruppiistruttoriresp);
	try {
	    gruppiistruttorirespService.update(gruppiistruttoriresp);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, gruppiistruttoriresp, e);
	    fixRenderEntityProperty(gruppiistruttoriresp);
	    return "gruppiistruttoriresp/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + gruppiistruttoriresp.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String delete(@ModelAttribute("gruppiistruttoriresp") GruppiIstruttoriResp gruppiistruttoriresp, BindingResult result, SessionStatus status) {

	GruppiIstruttoriResp objToDelete = gruppiistruttorirespService.findById(gruppiistruttoriresp.getId());
	try {
	    gruppiistruttorirespService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, gruppiistruttoriresp, e);
	    fixRenderEntityProperty(gruppiistruttoriresp);
	    return "gruppiistruttoriresp/form";
	}
	status.setComplete();
	return "redirect:list.htm?codice=" + objToDelete.getId().getCodice();
    }

    //    @RequestMapping
    //    public String deleteByCodice(@RequestParam("codice") Integer codice,Model model,@ModelAttribute("gruppiistruttoriresp") GruppiIstruttoriResp gruppiistruttoriresp, BindingResult result, SessionStatus status) {
    //
    //	GruppiIstruttoriResp objToDelete = gruppiistruttorirespService.findById(gruppiistruttoriresp.getId());
    //	try {
    //	    gruppiistruttorirespService.delete(objToDelete);
    //	} catch (Exception e) {
    //	    copyErrorsToBindingResult(result, gruppiistruttoriresp, e);
    //	    fixRenderEntityProperty(gruppiistruttoriresp);
    //	    return "gruppiistruttoriresp/form";
    //	}
    //	status.setComplete();
    //	return "redirect:list.htm?codice=" + objToDelete.getId().getCodice();
    //    }
    @Override
    protected void fixMergeEntityProperty(GruppiIstruttoriResp entity) {

    }

    @Override
    protected void fixRenderEntityProperty(GruppiIstruttoriResp entity) {

	if (entity.getGruppiIstruttori() == null) {
	    entity.setGruppiIstruttori(new GruppiIstruttori());
	}
	if (entity.getResponsabili() == null) {
	    entity.setResponsabili(new Responsabili());
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
