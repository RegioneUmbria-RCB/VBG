package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.Azioni;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.StpTipologieEndo2;
import it.gruppoinit.pal.gp.core.service.AzioniService;
import it.gruppoinit.pal.gp.core.service.StpTipologieEndo2Service;

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
@SessionAttributes(value = { "stptipologieendo2" })
public class StpTipologieEndo2Controller extends BaseController<StpTipologieEndo2> {

    @Autowired
    private StpTipologieEndo2Service stpTipologieEndo2Service;
    @Autowired
    private AzioniService azioniService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	List<StpTipologieEndo2> stpTipologieEndo2s = stpTipologieEndo2Service.findAll(null, null);
	ModelMap model = new ModelMap(stpTipologieEndo2s);
	boolean export = createJMesaExport(request, response, stpTipologieEndo2s);
	if (export)
	    return null;
	model.addAttribute("stpTipologieEndo2s", stpTipologieEndo2s);
	return model;
    }

    //    @RequestMapping
    //    public String insert(Model model, @ModelAttribute("stptipologieendo2") StpTipologieEndo2 stpTipologieEndo2, BindingResult result,
    //	    SessionStatus status) {
    //
    //	fixMergeEntityProperty(stpTipologieEndo2);
    //	try {
    //	    stpTipologieEndo2Service.insert(stpTipologieEndo2);
    //	} catch (Exception e) {
    //	    copyErrorsToBindingResult(result, stpTipologieEndo2, false, e);
    //	    fixRenderEntityProperty(stpTipologieEndo2);
    //	    model.addAttribute("stptipologieendo2", stpTipologieEndo2);
    //	    return "stptipologieendo2s/form";
    //	}
    //	status.setComplete();
    //	return "redirect:view.htm?codice=" + stpTipologieEndo2.getId().getCodice() + "&status_msg=01";
    //    }
    @RequestMapping
    public String update(Model model, @ModelAttribute("stptipologieendo2") StpTipologieEndo2 stpTipologieEndo2, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	// Recupero l'azione
	if (stpTipologieEndo2 != null && stpTipologieEndo2.getAzioni().getAzAzione() != null) {
	    Azioni azione = azioniService.findById(stpTipologieEndo2.getAzioni().getAzId());
	    stpTipologieEndo2.setAzioni(azione);
	}
	fixMergeEntityProperty(stpTipologieEndo2);
	try {
	    stpTipologieEndo2Service.update(stpTipologieEndo2);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, stpTipologieEndo2, false, e);
	    fixRenderEntityProperty(stpTipologieEndo2);
	    List<Azioni> azionis = azioniService.findAll(null, null);
	    model.addAttribute("azionis", azionis);
	    model.addAttribute("stptipologieendo2", stpTipologieEndo2);
	    return "stptipologieendo2/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + stpTipologieEndo2.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	StpTipologieEndo2 stpTipologieEndo2 = stpTipologieEndo2Service.findById(new PkId(codice));
	fixRenderEntityProperty(stpTipologieEndo2);
	model.addAttribute("stptipologieendo2", stpTipologieEndo2);
	List<Azioni> azionis = azioniService.findAll(null, null);
	model.addAttribute("azionis", azionis);
	setPageAttributes(model);
	return "stptipologieendo2/form";
    }

    @Override
    protected void setPageAttributes(Model model) {

    }

    @Override
    protected void fixMergeEntityProperty(StpTipologieEndo2 entity) {

    }

    @Override
    protected void fixRenderEntityProperty(StpTipologieEndo2 entity) {

	if (entity.getAzioni() == null) {
	    entity.setAzioni(new Azioni());
	}
    }
}
