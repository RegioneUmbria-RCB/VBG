package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.domain.Conti;
import it.gruppoinit.pal.gp.core.domain.MercatiContabilitaTributi;
import it.gruppoinit.pal.gp.core.domain.MercatiFormuleCalcolo;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.MercatiContabilitaTributiService;
import it.gruppoinit.pal.gp.core.service.MercatiFormuleCalcoloService;
import it.gruppoinit.pal.gp.core.service.helper.FlashMessages;

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
 * 
 * @author
 */
@Controller
@SessionAttributes("mercaticontabilitatributi")
public class MercatiContabilitaTributiController extends BaseController<MercatiContabilitaTributi> {

    private static final Logger log = LoggerFactory.getLogger(MercatiContabilitaTributiController.class);
    @Autowired
    private MercatiContabilitaTributiService mercaticontabilitatributiService;
    @Autowired
    private MercatiFormuleCalcoloService mercatiFormuleCalcoloService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	List<MercatiContabilitaTributi> mercaticontabilitatributiList = mercaticontabilitatributiService.findAll(null, null);
	ModelMap model = new ModelMap(mercaticontabilitatributiList);
	boolean export = createJMesaExport(request, response, mercaticontabilitatributiList);
	if (export) {
	    return null;
	}
	model.addAttribute("mercaticontabilitatributiList", mercaticontabilitatributiList);
	return model;
    }

    @RequestMapping
    public String create(@RequestParam("codiceFormula") Integer codiceFormula, Model model) {

	List<MercatiContabilitaTributi> mercatiContabilitaTributis = mercaticontabilitatributiService.findByFormulaAndWithoutDataFine(codiceFormula);
	int numeroContiPrecedenti = 0;
	if (!mercatiContabilitaTributis.isEmpty()) {
	    numeroContiPrecedenti = mercatiContabilitaTributis.size();
	}
	model.addAttribute("checkMercatiContabilitaTributi", numeroContiPrecedenti);
	MercatiFormuleCalcolo formuleCalcolo = mercatiFormuleCalcoloService.findById(new PkId(codiceFormula));
	MercatiContabilitaTributi mercaticontabilitatributi = new MercatiContabilitaTributi();
	mercaticontabilitatributi.setMercatiFormuleCalcolo(formuleCalcolo);
	fixRenderEntityProperty(mercaticontabilitatributi);
	model.addAttribute("mercaticontabilitatributi", mercaticontabilitatributi);
	setPageAttributes(model);
	return "mercaticontabilitatributi/form";
    }

    @RequestMapping
    public String insert(Model model, @ModelAttribute("mercaticontabilitatributi") MercatiContabilitaTributi mercaticontabilitatributi,
	    BindingResult result, SessionStatus status) {

	fixMergeEntityProperty(mercaticontabilitatributi);
	try {
	    mercaticontabilitatributiService.insert(mercaticontabilitatributi);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, mercaticontabilitatributi, e);
	    List<MercatiContabilitaTributi> mercatiContabilitaTributis = mercaticontabilitatributiService
		    .findByFormulaAndWithoutDataFine(mercaticontabilitatributi.getMercatiFormuleCalcolo().getId().getCodice());
	    int numeroContiPrecedenti = 0;
	    if (!mercatiContabilitaTributis.isEmpty()) {
		numeroContiPrecedenti = mercatiContabilitaTributis.size();
	    }
	    model.addAttribute("checkMercatiContabilitaTributi", numeroContiPrecedenti);
	    fixRenderEntityProperty(mercaticontabilitatributi);
	    return "mercaticontabilitatributi/form";
	}
	status.setComplete();
	return "redirect:../history/back.htm?" + WebConstants.GOTO + "=%2F";
	//return "redirect:view.htm?codice=" + mercaticontabilitatributi.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	MercatiContabilitaTributi mercaticontabilitatributi = mercaticontabilitatributiService.findById(id);
	fixRenderEntityProperty(mercaticontabilitatributi);
	model.addAttribute("mercaticontabilitatributi", mercaticontabilitatributi);
	setPageAttributes(model);
	return "mercaticontabilitatributi/form";
    }

    @RequestMapping
    public String update(@ModelAttribute("mercaticontabilitatributi") MercatiContabilitaTributi mercaticontabilitatributi, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	fixMergeEntityProperty(mercaticontabilitatributi);
	try {
	    mercaticontabilitatributiService.update(mercaticontabilitatributi);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, mercaticontabilitatributi, e);
	    fixRenderEntityProperty(mercaticontabilitatributi);
	    return "mercaticontabilitatributi/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + mercaticontabilitatributi.getId().getCodice() + "&status_msg=02";
    }

    //    @RequestMapping
    //    public String delete(@ModelAttribute("mercaticontabilitatributi") MercatiContabilitaTributi mercaticontabilitatributi, BindingResult result,
    //	    SessionStatus status) {
    //
    //	MercatiContabilitaTributi objToDelete = mercaticontabilitatributiService.findById(mercaticontabilitatributi.getId());
    //	try {
    //	    mercaticontabilitatributiService.delete(objToDelete);
    //	} catch (Exception e) {
    //	    copyErrorsToBindingResult(result, objToDelete, e);
    //	    fixRenderEntityProperty(mercaticontabilitatributi);
    //	    return "mercaticontabilitatributi/form";
    //	}
    //	status.setComplete();
    //	return "redirect:list.htm";
    //    }
    @RequestMapping
    public String deleteSingoloconto(@RequestParam(value = "codice") Integer codice, Model model) {

	MercatiContabilitaTributi objToDelete = mercaticontabilitatributiService.findById(new PkId(codice));
	try {
	    mercaticontabilitatributiService.delete(objToDelete);
	} catch (Exception e) {
	    log.debug("deleteSingoloLivello# {}", e);
	    FlashMessages.getWarnings().add(e.getMessage());
	    return "redirect:../history/back.htm?" + WebConstants.GOTO + "=%2F";
	}
	String mess = getMessageFromBundle("02", null);
	FlashMessages.getInfos().add(mess);
	return "redirect:../history/back.htm?" + WebConstants.GOTO + "=%2F";
    }

    @Override
    protected void fixMergeEntityProperty(MercatiContabilitaTributi entity) {

    }

    @Override
    protected void fixRenderEntityProperty(MercatiContabilitaTributi entity) {

	if (entity.getConti() == null) {
	    entity.setConti(new Conti());
	}
	if (entity.getMercatiFormuleCalcolo() == null) {
	    entity.setMercatiFormuleCalcolo(new MercatiFormuleCalcolo());
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
