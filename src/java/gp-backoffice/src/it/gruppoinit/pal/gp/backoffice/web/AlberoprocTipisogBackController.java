package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.AlberoprocTipisogBack;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipisoggetto;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.service.AlberoprocService;
import it.gruppoinit.pal.gp.core.service.AlberoprocTipisogBackService;
import it.gruppoinit.pal.gp.core.service.helper.FlashMessages;

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
@SessionAttributes("alberoproctipisogback")
public class AlberoprocTipisogBackController extends BaseController<AlberoprocTipisogBack> {

    @Autowired
    private AlberoprocTipisogBackService alberoproctipisogbackService;
    @Autowired
    private AlberoprocService alberoprocService;

    @RequestMapping
    public ModelMap list(@RequestParam("codiceAlberoproc") Integer codiceAlberoproc, HttpServletRequest request, HttpServletResponse response) {

	List<AlberoprocTipisogBack> alberoproctipisogbackList = alberoproctipisogbackService.findByAlberoproc(codiceAlberoproc);
	Alberoproc alberoproc = alberoprocService.findById(new PkId(codiceAlberoproc));
	ModelMap model = new ModelMap(alberoproctipisogbackList);
	boolean export = createJMesaExport(request, response, alberoproctipisogbackList);
	if (export) {
	    return null;
	}
	model.addAttribute("alberoproctipisogbackList", alberoproctipisogbackList);
	model.addAttribute("alberoproc", alberoproc);
	return model;
    }

    @RequestMapping
    public String create(@RequestParam("codiceAlberoproc") Integer codiceAlberoproc, Model model) {

	Alberoproc alberoproc = alberoprocService.findById(new PkId(codiceAlberoproc));
	AlberoprocTipisogBack alberoproctipisogback = new AlberoprocTipisogBack();
	alberoproctipisogback.setAlberoproc(alberoproc);
	fixRenderEntityProperty(alberoproctipisogback);
	model.addAttribute("alberoproctipisogback", alberoproctipisogback);
	setPageAttributes(model);
	return "alberoproctipisogback/form";
    }

    @RequestMapping
    public String insert(@ModelAttribute("alberoproctipisogback") AlberoprocTipisogBack alberoproctipisogback, BindingResult result,
	    SessionStatus status) {

	fixMergeEntityProperty(alberoproctipisogback);
	try {
	    alberoproctipisogbackService.insert(alberoproctipisogback);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, alberoproctipisogback, e);
	    fixRenderEntityProperty(alberoproctipisogback);
	    return "alberoproctipisogback/form";
	}
	status.setComplete();
	//return "redirect:view.htm?codice=" + alberoproctipisogback.getId().getCodice()+ "&status_msg=01"; 
	return "redirect:list.htm?codiceAlberoproc=" + alberoproctipisogback.getAlberoproc().getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	AlberoprocTipisogBack alberoproctipisogback = alberoproctipisogbackService.findById(id);
	fixRenderEntityProperty(alberoproctipisogback);
	model.addAttribute("alberoproctipisogback", alberoproctipisogback);
	setPageAttributes(model);
	return "alberoproctipisogback/form";
    }

    @RequestMapping
    public String update(@ModelAttribute("alberoproctipisogback") AlberoprocTipisogBack alberoproctipisogback, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	fixMergeEntityProperty(alberoproctipisogback);
	try {
	    alberoproctipisogbackService.update(alberoproctipisogback);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, alberoproctipisogback, e);
	    fixRenderEntityProperty(alberoproctipisogback);
	    return "alberoproctipisogback/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + alberoproctipisogback.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String delete(@ModelAttribute("alberoproctipisogback") AlberoprocTipisogBack alberoproctipisogback, BindingResult result,
	    SessionStatus status) {

	AlberoprocTipisogBack objToDelete = alberoproctipisogbackService.findById(alberoproctipisogback.getId());
	try {
	    alberoproctipisogbackService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, alberoproctipisogback, e);
	    fixRenderEntityProperty(alberoproctipisogback);
	    return "alberoproctipisogback/form";
	}
	status.setComplete();
	return "redirect:list.htm";
    }

    @RequestMapping
    public String deleteRecord(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request, SessionStatus status) {

	AlberoprocTipisogBack objToDelete = alberoproctipisogbackService.findById(new PkId(codice));
	try {
	    alberoproctipisogbackService.delete(objToDelete);
	} catch (Exception e) {
	    FlashMessages.getWarnings().add(e.getMessage());
	    //	    copyErrorsToBindingResult(result, alberoproctipisogback, e);
	    //	    fixRenderEntityProperty(alberoproctipisogback);
	    return "alberoproctipisogback/list";
	}
	status.setComplete();
	return "redirect:list.htm?codiceAlberoproc=" + objToDelete.getAlberoproc().getId().getCodice();
    }

    @Override
    protected void fixMergeEntityProperty(AlberoprocTipisogBack entity) {

    }

    @Override
    protected void fixRenderEntityProperty(AlberoprocTipisogBack entity) {

	if (EntityUtils.getNestedProperty(entity.getAlberoproc(), "id.codice") == null) {
	    entity.setAlberoproc(new Alberoproc());
	}
	if (EntityUtils.getNestedProperty(entity.getTipisoggetto(), "id.codice") == null) {
	    entity.setTipisoggetto(new Tipisoggetto());
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
