package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.Info;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.InfoService;
import it.gruppoinit.pal.gp.core.service.OggettiService;

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
 * @author lucap
 */
@Controller
@SessionAttributes("info")
public class InfoController extends BaseController<Info> {

    @Autowired
    private InfoService infoService;
    @Autowired
    private OggettiService oggettiService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	List<Info> infoList = infoService.findAll(null, null);
	ModelMap model = new ModelMap(infoList);
	boolean export = createJMesaExport(request, response, infoList);
	if (export) {
	    return null;
	}
	model.addAttribute("infoList", infoList);
	return model;
    }

    @RequestMapping
    public String create(Model model) {

	Info info = new Info();
	fixRenderEntityProperty(info);
	model.addAttribute("info", info);
	setPageAttributes(model);
	return "info/form";
    }

    @RequestMapping
    public String insert(@ModelAttribute("info") Info info, BindingResult result, SessionStatus status) {

	// recupero l'oggetto corrente e lo inserisco in info
	Oggetti oggetto = oggettiService.findById(info.getOggetti().getId());
	info.setOggetti(oggetto);
	if (oggetto != null) {
	    info.setNomefile(oggetto.getNomefile());
	}
	fixMergeEntityProperty(info);
	try {
	    infoService.insert(info);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, info, e);
	    fixRenderEntityProperty(info);
	    return "info/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + info.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	Info info = infoService.findById(id);
	fixRenderEntityProperty(info);
	model.addAttribute("info", info);
	setPageAttributes(model);
	return "info/form";
    }

    @RequestMapping
    public String update(@ModelAttribute("info") Info info, BindingResult result, SessionStatus status, HttpServletRequest request) {

	// recupero l'oggetto corrente e lo inserisco in info
	Oggetti oggetto = oggettiService.findById(info.getOggetti().getId());
	info.setOggetti(oggetto);
	if (oggetto != null) {
	    info.setNomefile(oggetto.getNomefile());
	} else {
	    info.setNomefile(null);
	}
	fixMergeEntityProperty(info);
	try {
	    infoService.update(info);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, info, e);
	    fixRenderEntityProperty(info);
	    return "info/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + info.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String delete(@ModelAttribute("info") Info info, BindingResult result, SessionStatus status) {

	Info objToDelete = infoService.findById(info.getId());
	try {
	    infoService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderEntityProperty(info);
	    return "info/form";
	}
	status.setComplete();
	return "redirect:list.htm";
    }

    @Override
    protected void fixMergeEntityProperty(Info entity) {

	if (entity.getOggetti() != null && entity.getOggetti().getId() != null && entity.getOggetti().getId().getCodice() == null) {
	    entity.setOggetti(null);
	}
    }

    @Override
    protected void fixRenderEntityProperty(Info entity) {

	if (entity.getOggetti() == null) {
	    entity.setOggetti(new Oggetti());
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
