package it.gruppoinit.pal.gp.backoffice.web;

import java.util.Set;

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

import it.gruppoinit.pal.gp.core.domain.Cds;
import it.gruppoinit.pal.gp.core.domain.Cdsatti;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.service.CdsService;
import it.gruppoinit.pal.gp.core.service.CdsattiService;
import it.gruppoinit.pal.gp.core.utils.Utilities;

/**
 * 
 * @author gianpaolot
 */
@Controller
@SessionAttributes("cdsatti")
public class CdsattiController extends BaseController<Cdsatti> {

    @Autowired
    private CdsattiService cdsattiService;
    @Autowired
    private CdsService cdsService;
    @Autowired
    private OggettiService oggettiService;

    @RequestMapping
    public ModelMap list(@RequestParam("codiceCds") Integer codiceCds, HttpServletRequest request, HttpServletResponse response) {

	Cds cds = cdsService.findById(new PkId(codiceCds));
	Set<Cdsatti> cdsattiList = cds.getCdsattis();
	ModelMap model = new ModelMap(cdsattiList);
	boolean export = createJMesaExport(request, response, cdsattiList);
	if (export) {
	    return null;
	}
	model.addAttribute("cds", cds);
	model.addAttribute("cdsattiList", cdsattiList);
	return model;
    }

    @RequestMapping
    public String create(@RequestParam("codiceCds") Integer codiceCds, Model model) {

	Cdsatti cdsatti = new Cdsatti();
	Cds cds = cdsService.findById(new PkId(codiceCds));
	cdsatti.setOra(Utilities.getOrariosistema());
	cdsatti.setCds(cds);
	fixRenderEntityProperty(cdsatti);
	model.addAttribute("cds", cds);
	model.addAttribute("cdsatti", cdsatti);
	setPageAttributes(model);
	return "cdsatti/form";
    }

    @RequestMapping
    public String insert(@ModelAttribute("cdsatti") Cdsatti cdsatti, BindingResult result, SessionStatus status) {

	if (EntityUtils.getNestedProperty(cdsatti, "oggetti") == null) {
	    Oggetti oggetti = oggettiService.findById(new PkId(cdsatti.getOggetti().getId().getCodice()));
	    cdsatti.setOggetti(oggetti);
	}
	fixMergeEntityProperty(cdsatti);
	try {
	    cdsattiService.insert(cdsatti);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, cdsatti, e);
	    fixRenderEntityProperty(cdsatti);
	    return "cdsatti/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + cdsatti.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	Cdsatti cdsatti = cdsattiService.findById(id);
	fixRenderEntityProperty(cdsatti);
	model.addAttribute("cdsatti", cdsatti);
	setPageAttributes(model);
	return "cdsatti/form";
    }

    @RequestMapping
    public String update(@ModelAttribute("cdsatti") Cdsatti cdsatti, BindingResult result, SessionStatus status, HttpServletRequest request) {

	if (EntityUtils.getNestedProperty(cdsatti, "oggetti") == null) {
	    Oggetti oggetti = oggettiService.findById(new PkId(cdsatti.getOggetti().getId().getCodice()));
	    cdsatti.setOggetti(oggetti);
	}
	fixMergeEntityProperty(cdsatti);
	try {
	    cdsattiService.update(cdsatti);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, cdsatti, e);
	    fixRenderEntityProperty(cdsatti);
	    return "cdsatti/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + cdsatti.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String delete(@ModelAttribute("cdsatti") Cdsatti cdsatti, BindingResult result, SessionStatus status) {

	Cdsatti objToDelete = cdsattiService.findById(cdsatti.getId());
	try {
	    cdsattiService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderEntityProperty(cdsatti);
	    return "cdsatti/form";
	}
	status.setComplete();
	return "redirect:list.htm?codiceCds=" + objToDelete.getCds().getId().getCodice();
    }

    @Override
    protected void fixMergeEntityProperty(Cdsatti entity) {

    }

    @Override
    protected void fixRenderEntityProperty(Cdsatti entity) {

	if (entity.getCds() == null) {
	    entity.setCds(new Cds());
	}
	if (entity.getOggetti() == null) {
	    entity.setOggetti(new Oggetti());
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
