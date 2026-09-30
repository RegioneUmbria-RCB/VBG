package it.gruppoinit.pal.gp.backoffice.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.AlberoprocBolkestein;
import it.gruppoinit.pal.gp.core.domain.Mercati;
import it.gruppoinit.pal.gp.core.domain.MercatiD;
import it.gruppoinit.pal.gp.core.domain.MercatiUso;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.web.AlberoprocBolkesteinCommand;
import it.gruppoinit.pal.gp.core.features.manifestazioni.configurazione.MercatiDService;
import it.gruppoinit.pal.gp.core.service.AlberoprocBolkesteinService;
import it.gruppoinit.pal.gp.core.service.AlberoprocService;
import it.gruppoinit.pal.gp.core.service.MercatiService;
import it.gruppoinit.pal.gp.core.service.MercatiUsoService;
import it.gruppoinit.pal.gp.core.service.helper.FlashMessages;

/**
 * 
 * @author
 */
@Controller
@SessionAttributes("alberoprocbolkestein")
public class AlberoprocBolkesteinController extends BaseController<AlberoprocBolkestein> {

    @Autowired
    private AlberoprocBolkesteinService alberoprocbolkesteinService;

    @Autowired
    private AlberoprocService alberoprocService;
    @Autowired
    private MercatiService mercatiService;
    @Autowired
    private MercatiUsoService mercatiUsoService;
    @Autowired
    private MercatiDService mercatiDService;

    //    @RequestMapping
    //    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {
    //
    //	List<AlberoprocBolkestein> alberoprocbolkesteinList = alberoprocbolkesteinService.findAll(null, null);
    //	ModelMap model = new ModelMap(alberoprocbolkesteinList);
    //	boolean export = createJMesaExport(request, response, alberoprocbolkesteinList);
    //	if (export) {
    //	    return null;
    //	}
    //	model.addAttribute("alberoprocbolkesteinList", alberoprocbolkesteinList);
    //	return model;
    //    }
    //    @RequestMapping
    //    public String create(@RequestParam("codiceAlberoproc") Integer codiceAlberoproc, Model model) {
    //
    //	AlberoprocBolkestein alberoprocbolkestein = new AlberoprocBolkestein();
    //	//alberoprocbolkestein.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    //	PkId id = new PkId(codiceAlberoproc);
    //	Alberoproc alberoproc = alberoprocService.findById(id);
    //	List<Mercati> mercatis = mercatiService.findAllMercatiAttivi(null, null);
    //	fixRenderEntityProperty(alberoprocbolkestein);
    //	model.addAttribute("alberoprocbolkestein", alberoprocbolkestein);
    //	model.addAttribute("alberoproc", alberoproc);
    //	model.addAttribute("mercatis", mercatis);
    //	setPageAttributes(model);
    //	return "alberoprocbolkestein/form";
    //    }
    @RequestMapping
    public String insert(@RequestParam("idposteggio") Integer idposteggio,
	    @ModelAttribute("alberoprocbolkestein") AlberoprocBolkesteinCommand alberoprocbolkestein, BindingResult result, SessionStatus status) {

	AlberoprocBolkestein apb = new AlberoprocBolkestein();
	Alberoproc alberoproc = alberoprocService.findById(new PkId(alberoprocbolkestein.getCodiceAlberoproc()));
	Mercati mercato = mercatiService.findById(new PkId(alberoprocbolkestein.getEntity().getId().getCodice()));
	MercatiUso uso = mercatiUsoService.findById(new PkId(alberoprocbolkestein.getMercatiUso().getId().getCodice()));
	MercatiD posteggio = mercatiDService.findById(new PkId(idposteggio));
	apb.setAlberoproc(alberoproc);
	apb.setMercati(mercato);
	apb.setMercatiUso(uso);
	apb.setMercatiD(posteggio);
	fixMergeEntityProperty(apb);
	try {
	    alberoprocbolkesteinService.insert(apb);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, apb, e);
	    fixRenderEntityProperty(apb);
	    return "alberoprocbolkestein/form";
	    //	    return "redirect:view.htm?codiceAlberoproc=" + alberoprocbolkestein.getCodiceAlberoproc() + "&codiceMercato="
	    //		    + alberoprocbolkestein.getEntity().getId().getCodice() + "&codiceUso=" + alberoprocbolkestein.getMercatiUso().getId().getCodice();
	}
	status.setComplete();
	return "redirect:view.htm?codiceAlberoproc=" + alberoprocbolkestein.getCodiceAlberoproc() + "&codiceMercato="
		+ alberoprocbolkestein.getEntity().getId().getCodice() + "&codiceUso=" + alberoprocbolkestein.getMercatiUso().getId().getCodice();
    }

    @RequestMapping
    public String view(@RequestParam("codiceAlberoproc") Integer codiceAlberoproc,
	    @RequestParam(value = "codiceMercato", required = false) Integer codiceMercato,
	    @RequestParam(value = "codiceUso", required = false) Integer codiceUso, Model model, HttpServletRequest request) {

	AlberoprocBolkesteinCommand alberoprocbolkestein = new AlberoprocBolkesteinCommand();
	setCommandAttributes(alberoprocbolkestein);
	Mercati entity = mercatiService.findById(new PkId(codiceMercato));
	MercatiUso uso = mercatiUsoService.findById(new PkId(codiceUso));
	alberoprocbolkestein.setCodiceAlberoproc(codiceAlberoproc);
	alberoprocbolkestein.setMercatiUso(uso);
	alberoprocbolkestein.setEntity(entity);
	alberoprocbolkesteinService.populateCommand(alberoprocbolkestein);
	model.addAttribute("alberoprocbolkestein", alberoprocbolkestein);
	model.addAttribute("codiceAlberoproc", codiceAlberoproc);
	fixRenderCommandProperty(alberoprocbolkestein);
	setPageAttributes(model);
	return "alberoprocbolkestein/form";
    }

    //    @RequestMapping
    //    public String update(@ModelAttribute("alberoprocbolkestein") AlberoprocBolkestein alberoprocbolkestein, BindingResult result,
    //	    SessionStatus status, HttpServletRequest request) {
    //
    //	fixMergeEntityProperty(alberoprocbolkestein);
    //	try {
    //	    alberoprocbolkesteinService.update(alberoprocbolkestein);
    //	} catch (Exception e) {
    //	    //copyErrorsToBindingResult(alberoprocbolkesteinService.getValidationMessages(), result, alberoprocbolkestein, e.getMessage());
    //	    fixRenderEntityProperty(alberoprocbolkestein);
    //	    return "alberoprocbolkestein/form";
    //	}
    //	status.setComplete();
    //	return "redirect:view.htm?codice=" + alberoprocbolkestein.getId().getCodice() + "&status_msg=02";
    //    }
    @RequestMapping
    public String delete(@RequestParam("codiceAlberoprocbolkestein") Integer codiceAlberoprocbolkestein,
	    @ModelAttribute("alberoprocbolkestein") AlberoprocBolkesteinCommand alberoprocbolkestein, BindingResult result, SessionStatus status) {

	//AlberoprocBolkestein objToDelete = alberoprocbolkesteinService.findByAlberoProcAndMercatoAndUsoAndPosteggio(alberoprocbolkestein.getCodiceAlberoproc(), alberoprocbolkestein.getEntity().getId().getCodice(), alberoprocbolkestein.getMercatiUso().getId().getCodice(), idposteggio);
	AlberoprocBolkestein objToDelete = alberoprocbolkesteinService.findById(new PkId(codiceAlberoprocbolkestein));
	try {
	    alberoprocbolkesteinService.delete(objToDelete);
	} catch (Exception e) {
	    //copyErrorsToBindingResult(alberoprocbolkesteinService.getValidationMessages(), result, objToDelete, e.getMessage());
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderEntityProperty(objToDelete);
	    return "alberoprocbolkestein/form";
	    //	    return "redirect:view.htm?codiceAlberoproc=" + alberoprocbolkestein.getCodiceAlberoproc() + "&codiceMercato="
	    //		    + alberoprocbolkestein.getEntity().getId().getCodice() + "&codiceUso=" + alberoprocbolkestein.getMercatiUso().getId().getCodice();
	}
	status.setComplete();
	return "redirect:view.htm?codiceAlberoproc=" + alberoprocbolkestein.getCodiceAlberoproc() + "&codiceMercato="
		+ alberoprocbolkestein.getEntity().getId().getCodice() + "&codiceUso=" + alberoprocbolkestein.getMercatiUso().getId().getCodice();
    }

    @RequestMapping
    public String disattivaTutti(@ModelAttribute("alberoprocbolkestein") AlberoprocBolkesteinCommand alberoprocbolkestein, BindingResult result,
	    SessionStatus status) {

	try {
	    alberoprocbolkesteinService.deleteByAlberoProcAndMercatoAndUso(alberoprocbolkestein.getCodiceAlberoproc(), alberoprocbolkestein
		    .getEntity().getId().getCodice(), alberoprocbolkestein.getMercatiUso().getId().getCodice());
	} catch (Exception e) {
	    //copyErrorsToBindingResult(alberoprocbolkesteinService.getValidationMessages(), result, objToDelete, e.getMessage());
	    FlashMessages.getWarnings().add(e.getMessage());
	    //fixRenderEntityProperty(objToDelete);
	    return "redirect:view.htm?codiceAlberoproc=" + alberoprocbolkestein.getCodiceAlberoproc() + "&codiceMercato="
		    + alberoprocbolkestein.getEntity().getId().getCodice() + "&codiceUso=" + alberoprocbolkestein.getMercatiUso().getId().getCodice();
	}
	status.setComplete();
	return "redirect:view.htm?codiceAlberoproc=" + alberoprocbolkestein.getCodiceAlberoproc() + "&codiceMercato="
		+ alberoprocbolkestein.getEntity().getId().getCodice() + "&codiceUso=" + alberoprocbolkestein.getMercatiUso().getId().getCodice();
    }

    @RequestMapping
    public String attivaTutti(@ModelAttribute("alberoprocbolkestein") AlberoprocBolkesteinCommand alberoprocbolkestein, BindingResult result,
	    SessionStatus status) {

	try {
	    alberoprocbolkesteinService.insertByAlberoProcAndMercatoAndUso(alberoprocbolkestein.getCodiceAlberoproc(), alberoprocbolkestein
		    .getEntity().getId().getCodice(), alberoprocbolkestein.getMercatiUso().getId().getCodice());
	} catch (Exception e) {
	    //copyErrorsToBindingResult(alberoprocbolkesteinService.getValidationMessages(), result, objToDelete, e.getMessage());
	    //fixRenderEntityProperty(objToDelete);
	    FlashMessages.getWarnings().add(e.getMessage());
	    return "redirect:view.htm?codiceAlberoproc=" + alberoprocbolkestein.getCodiceAlberoproc() + "&codiceMercato="
		    + alberoprocbolkestein.getEntity().getId().getCodice() + "&codiceUso=" + alberoprocbolkestein.getMercatiUso().getId().getCodice();
	}
	status.setComplete();
	return "redirect:view.htm?codiceAlberoproc=" + alberoprocbolkestein.getCodiceAlberoproc() + "&codiceMercato="
		+ alberoprocbolkestein.getEntity().getId().getCodice() + "&codiceUso=" + alberoprocbolkestein.getMercatiUso().getId().getCodice();
    }

    @Override
    protected void fixMergeEntityProperty(AlberoprocBolkestein entity) {

    }

    @Override
    protected void fixRenderEntityProperty(AlberoprocBolkestein entity) {

	if (entity.getAlberoproc() == null) {
	    entity.setAlberoproc(new Alberoproc());
	}
	if (entity.getMercati() == null) {
	    entity.setMercati(new Mercati());
	}
	if (entity.getMercatiD() == null) {
	    entity.setMercatiD(new MercatiD());
	}
	if (entity.getMercatiUso() == null) {
	    entity.setMercatiUso(new MercatiUso());
	}
    }

    private void fixRenderCommandProperty(AlberoprocBolkesteinCommand command) {

	if (command.getEntity() == null) {
	    command.setEntity(new Mercati());
	}
	if (command.getMercatiUso() == null) {
	    command.setMercatiUso(new MercatiUso());
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

    }

    private void setCommandAttributes(AlberoprocBolkesteinCommand command) {

    }
}
