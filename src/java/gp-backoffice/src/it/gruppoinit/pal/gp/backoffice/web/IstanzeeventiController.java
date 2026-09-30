package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzeeventi;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.web.IstanzeeventiFilter;
import it.gruppoinit.pal.gp.core.service.IstanzeeventiService;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.BooleanUtils;
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
 * @author fabrizioc
 */
@Controller
@SessionAttributes("istanzeeventi")
public class IstanzeeventiController extends BaseController<Istanzeeventi> {

    @Autowired
    private IstanzeeventiService istanzeeventiService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, @RequestParam(value = "codiceIstanza", required = false) Integer codiceIstanza,
	    @RequestParam(value = "codicemovimento", required = false) Integer codiceMov,
	    @RequestParam(value = "flagLetto", required = false) Boolean flagLetto, HttpServletResponse response) {

	IstanzeeventiFilter filter = new IstanzeeventiFilter();
	Istanze istanze = new Istanze();
	istanze.getId().setCodice(codiceIstanza);
	Movimenti movimento = new Movimenti();
	movimento.getId().setCodice(codiceMov);
	filter.setIstanze(istanze);
	filter.setMovimenti(movimento);
	filter.setFlagLetto(flagLetto);
	List<Istanzeeventi> istanzeeventiList = istanzeeventiService.findByFilter(filter, null, null);
	ModelMap model = new ModelMap(istanzeeventiList);
	model.addAttribute("istanzeeventiList", istanzeeventiList);
	return model;
    }

    @RequestMapping
    public String create(Model model) {

	Istanzeeventi istanzeeventi = new Istanzeeventi();
	fixRenderEntityProperty(istanzeeventi);
	model.addAttribute("istanzeeventi", istanzeeventi);
	setPageAttributes(model);
	return "istanzeeventi/form";
    }

    @RequestMapping
    public String insert(@ModelAttribute("istanzeeventi") Istanzeeventi istanzeeventi, BindingResult result, SessionStatus status) {

	fixMergeEntityProperty(istanzeeventi);
	try {
	    istanzeeventiService.insert(istanzeeventi);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, istanzeeventi, e);
	    fixRenderEntityProperty(istanzeeventi);
	    return "istanzeeventi/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + istanzeeventi.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	Istanzeeventi istanzeeventi = istanzeeventiService.findById(id);
	fixRenderEntityProperty(istanzeeventi);
	model.addAttribute("istanzeeventi", istanzeeventi);
	setPageAttributes(model);
	return "istanzeeventi/form";
    }

    @RequestMapping
    public String update(@ModelAttribute("istanzeeventi") Istanzeeventi istanzeeventi, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	fixMergeEntityProperty(istanzeeventi);
	try {
	    istanzeeventiService.update(istanzeeventi);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, istanzeeventi, e);
	    fixRenderEntityProperty(istanzeeventi);
	    return "istanzeeventi/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + istanzeeventi.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String delete(@ModelAttribute("istanzeeventi") Istanzeeventi istanzeeventi, BindingResult result, SessionStatus status) {

	Istanzeeventi objToDelete = istanzeeventiService.findById(istanzeeventi.getId());
	try {
	    istanzeeventiService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderEntityProperty(istanzeeventi);
	    return "istanzeeventi/form";
	}
	status.setComplete();
	return "redirect:list.htm";
    }

    @RequestMapping
    public void ajaxChangeFlagLetto(@RequestParam("codice") Integer codice, HttpServletResponse response) throws Exception {

	Istanzeeventi istanzeeventi = istanzeeventiService.findById(new PkId(codice));
	if (istanzeeventi.getIstanze() != null) {
	    Istanze istanza = null;
	    if (istanzeeventi.getIstanze().getId() != null) {
		if (istanzeeventi.getIstanze().getId().getCodice() != null) {
		    istanza = istanzeeventi.getIstanze();
		}
	    }
	    if (istanzeeventi.getMovimenti() != null) {
		if (istanzeeventi.getMovimenti().getIstanza() != null) {
		    if (istanzeeventi.getMovimenti().getIstanza().getId() != null) {
			if (istanzeeventi.getMovimenti().getIstanza().getId().getCodice() != null) {
			    istanza = istanzeeventi.getMovimenti().getIstanza();
			}
		    }
		}
	    }
	    if (istanza != null) {
		try {
		    checkAccessoInformazioni(istanza, true);
		} catch (Exception e) {
		    response.setStatus(500);
		    response.getWriter().write(getMessageFromBundle("label.erroreaggiornamento", null) + ": " + e.getMessage());
		    return;
		}
	    }
	}
	if (BooleanUtils.isFalse(istanzeeventi.getFlagLetto())) {
	    istanzeeventi.setFlagLetto(Boolean.TRUE);
	} else {
	    istanzeeventi.setFlagLetto(Boolean.FALSE);
	}
	istanzeeventiService.update(istanzeeventi);
	response.getWriter().write(getMessageFromBundle("label.datoaggiornato", null));
    }

    @Override
    protected void fixMergeEntityProperty(Istanzeeventi entity) {

    }

    @Override
    protected void fixRenderEntityProperty(Istanzeeventi entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
