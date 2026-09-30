package it.gruppoinit.pal.gp.backoffice.web;

import java.util.ArrayList;
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

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.domain.Conti;
import it.gruppoinit.pal.gp.core.domain.MercatiContabilitaTributi;
import it.gruppoinit.pal.gp.core.domain.MercatiFormuleCalcolo;
import it.gruppoinit.pal.gp.core.domain.MercatiUso;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.MercatiFormuleCalcoloContestoEnum;
import it.gruppoinit.pal.gp.core.domain.helper.MercatiFormuleCalcoloHelper;
import it.gruppoinit.pal.gp.core.domain.web.BaseCommand;
import it.gruppoinit.pal.gp.core.domain.web.MercatiFormuleCalcoloCommand;
import it.gruppoinit.pal.gp.core.features.manifestazioni.formule.LivelloServizioHelper;
import it.gruppoinit.pal.gp.core.service.MercatiFormuleCalcoloService;
import it.gruppoinit.pal.gp.core.service.MercatiUsoService;
import it.gruppoinit.pal.gp.core.service.helper.FlashMessages;

/**
 * 
 * @author
 */
@Controller
@SessionAttributes("mercatiformulecalcolo")
public class MercatiFormuleCalcoloController extends BaseController<MercatiFormuleCalcolo> {

    private static final Logger log = LoggerFactory.getLogger(MercatiFormuleCalcoloController.class);
    @Autowired
    private MercatiFormuleCalcoloService mercatiformulecalcoloService;
    @Autowired
    private MercatiUsoService mercatiUsoService;

    @RequestMapping
    public ModelMap list(@RequestParam("codicemercato") Integer codicemercato, @RequestParam(required = false, value = "codiceuso") Integer codiceuso,
	    HttpServletRequest request, HttpServletResponse response) {

	List<MercatiFormuleCalcoloHelper> mercatiformulecalcoloList = new ArrayList<MercatiFormuleCalcoloHelper>();
	if (codiceuso != null) {
	    mercatiformulecalcoloList = mercatiformulecalcoloService.findByMecatoUso(codiceuso);
	} else {
	    mercatiformulecalcoloList = mercatiformulecalcoloService.findByMecato(codicemercato);
	}
	ModelMap model = new ModelMap(mercatiformulecalcoloList);
	boolean export = createJMesaExport(request, response, mercatiformulecalcoloList);
	if (export) {
	    return null;
	}
	model.addAttribute("mercatiformulecalcoloList", mercatiformulecalcoloList);
	model.addAttribute("codice_uso", codiceuso);
	model.addAttribute("codice_mercato", codicemercato);
	return model;
    }

    @RequestMapping
    public String create(@RequestParam("codiceuso") Integer codiceuso, Model model) {

	MercatiFormuleCalcoloCommand mercatiformulecalcolo = new MercatiFormuleCalcoloCommand();
	mercatiformulecalcolo.setDisplayMode(BaseCommand.NEW);
	MercatiFormuleCalcolo entity = new MercatiFormuleCalcolo();
	MercatiUso uso = mercatiUsoService.findById(new PkId(codiceuso));
	entity.setMercatiUso(uso);
	mercatiformulecalcolo.setEntity(entity);
	fixRenderCommandProperty(mercatiformulecalcolo);
	model.addAttribute("mercatiformulecalcolo", mercatiformulecalcolo);
	model.addAttribute("codiceuso", codiceuso);
	setPageAttributes(model);
	setPageAttributes(model, codiceuso);
	return "mercatiformulecalcolo/form";
    }

    @RequestMapping
    public String insert(Model model, @ModelAttribute("mercatiformulecalcolo") MercatiFormuleCalcoloCommand mercatiformulecalcolo,
	    BindingResult result, SessionStatus status) {

	MercatiFormuleCalcolo mfc = mercatiformulecalcolo.getEntity();
	Integer idUso = mfc.getMercatiUso().getId().getCodice();
	String formula = mfc.getFormula();
	fixMergeEntityProperty(mfc);
	try {
	    mercatiformulecalcoloService.verificaFormulaCalcolo(idUso, formula);
	    mercatiformulecalcoloService.insert(mfc, mercatiformulecalcolo.getMercatiContabilitaTributi());
	} catch (Exception e) {
	    mercatiformulecalcolo.setDisplayMode(BaseCommand.NEW);
	    copyErrorsToBindingResult(result, mfc, true, e);
	    fixRenderCommandProperty(mercatiformulecalcolo);
	    setPageAttributes(model, idUso);
	    return "mercatiformulecalcolo/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + mercatiformulecalcolo.getEntity().getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	MercatiFormuleCalcoloCommand mercatiformulecalcolo = new MercatiFormuleCalcoloCommand();
	mercatiformulecalcolo.setDisplayMode(BaseCommand.VIEW);
	MercatiFormuleCalcolo entity = mercatiformulecalcoloService.findById(id);
	mercatiformulecalcolo.setEntity(entity);
	fixRenderCommandProperty(mercatiformulecalcolo);
	model.addAttribute("mercatiformulecalcolo", mercatiformulecalcolo);
	setPageAttributes(model);
	setPageAttributes(model, entity.getMercatiUso().getId().getCodice());
	return "mercatiformulecalcolo/form";
    }

    @RequestMapping
    public String update(Model model, @ModelAttribute("mercatiformulecalcolo") MercatiFormuleCalcoloCommand mercatiformulecalcolo,
	    BindingResult result, SessionStatus status, HttpServletRequest request) {

	MercatiFormuleCalcolo mfc = mercatiformulecalcolo.getEntity();
	Integer idUso = mfc.getMercatiUso().getId().getCodice();
	String formula = mfc.getFormula();
	fixMergeEntityProperty(mfc);
	try {
	    mercatiformulecalcoloService.verificaFormulaCalcolo(idUso, formula);
	    mercatiformulecalcoloService.update(mfc);
	} catch (Exception e) {
	    mercatiformulecalcolo.setDisplayMode(BaseCommand.VIEW);
	    copyErrorsToBindingResult(result, mfc, true, e);
	    fixRenderCommandProperty(mercatiformulecalcolo);
	    setPageAttributes(model, idUso);
	    return "mercatiformulecalcolo/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + mercatiformulecalcolo.getEntity().getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String delete(@ModelAttribute("mercatiformulecalcolo") MercatiFormuleCalcoloCommand mercatiformulecalcolo, BindingResult result,
	    SessionStatus status) {

	MercatiFormuleCalcolo objToDelete = mercatiformulecalcoloService.findById(mercatiformulecalcolo.getEntity().getId());
	try {
	    mercatiformulecalcoloService.delete(objToDelete);
	} catch (Exception e) {
	    mercatiformulecalcolo.setDisplayMode(BaseCommand.VIEW);
	    copyErrorsToBindingResult(result, objToDelete, true, e);
	    fixRenderCommandProperty(mercatiformulecalcolo);
	    return "mercatiformulecalcolo/form";
	}
	status.setComplete();
	return "redirect:../history/back.htm?" + WebConstants.GOTO + "=%2F";
    }

    @RequestMapping
    public String deleteSingolaFormula(@RequestParam(value = "codice") Integer codice,
	    @RequestParam(required = false, value = "codicemercato") Integer codicemercato, Model model) {

	MercatiFormuleCalcolo objToDelete = mercatiformulecalcoloService.findById(new PkId(codice));
	Integer codiceuso = objToDelete.getMercatiUso().getId().getCodice();
	try {
	    mercatiformulecalcoloService.delete(objToDelete);
	} catch (Exception e) {
	    log.debug("deleteSingoloLivello#", e);
	    FlashMessages.getWarnings().add(e.getMessage());
	    if (codicemercato != null) {
		return "redirect:list.htm?codicemercato=" + codicemercato;
	    } else {
		MercatiUso mercatiUso = mercatiUsoService.findById(new PkId(codiceuso));
		return "redirect:list.htm?codicemercato=" + mercatiUso.getMercati().getId().getCodice() + "&codiceuso=" +
		       mercatiUso.getId().getCodice();
	    }
	}
	String mess = getMessageFromBundle("02", null);
	FlashMessages.getInfos().add(mess);
	if (codicemercato != null) {
	    return "redirect:list.htm?codicemercato=" + codicemercato;
	} else {
	    MercatiUso mercatiUso = mercatiUsoService.findById(new PkId(codiceuso));
	    return "redirect:list.htm?codicemercato=" + mercatiUso.getMercati().getId().getCodice() + "&codiceuso=" + mercatiUso.getId().getCodice();
	}
    }

    private void fixRenderCommandProperty(MercatiFormuleCalcoloCommand command) {

	fixRenderEntityProperty(command.getEntity());
	fixRenderMercatiContabilitaTributiProperty(command.getMercatiContabilitaTributi());
    }

    private void fixRenderMercatiContabilitaTributiProperty(MercatiContabilitaTributi mercatiContabilitaTributi) {

	if (mercatiContabilitaTributi.getConti() == null) {
	    mercatiContabilitaTributi.setConti(new Conti());
	}
	if (mercatiContabilitaTributi.getMercatiFormuleCalcolo() == null) {
	    mercatiContabilitaTributi.setMercatiFormuleCalcolo(new MercatiFormuleCalcolo());
	}
    }

    @Override
    protected void fixMergeEntityProperty(MercatiFormuleCalcolo entity) {

	// non devo inizializzare niente
    }

    @Override
    protected void fixRenderEntityProperty(MercatiFormuleCalcolo entity) {

	if (entity.getMercatiUso() == null) {
	    entity.setMercatiUso(new MercatiUso());
	}
    }

    private void setPageAttributes(Model model, Integer codiceUso) {

	List<LivelloServizioHelper> livelloServizios = this.mercatiformulecalcoloService.findLivelliAttiviByMercatoUso(codiceUso);
	model.addAttribute("livelloServizios", livelloServizios);
	MercatiFormuleCalcoloContestoEnum[] contesti = MercatiFormuleCalcoloContestoEnum.values();
	model.addAttribute("contestiCalcolo", contesti);
    }

    @Override
    protected void setPageAttributes(Model model) {

	// non devo inizializzare niente
    }
}
