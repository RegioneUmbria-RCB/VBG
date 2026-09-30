/**
 * 
 */
package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.MercatiUso;
import it.gruppoinit.pal.gp.core.domain.RegistrazioniCausali;
import it.gruppoinit.pal.gp.core.domain.RegistrazioniFilter;
import it.gruppoinit.pal.gp.core.domain.web.RegistrazioniInOutCommand;
import it.gruppoinit.pal.gp.core.service.AlberoprocService;
import it.gruppoinit.pal.gp.core.service.AnagrafeService;
import it.gruppoinit.pal.gp.core.service.MercatiUsoService;
import it.gruppoinit.pal.gp.core.service.RegistrazioniCausaliService;
import it.gruppoinit.pal.gp.core.service.RegistrazioniFilterService;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

/**
 * @author francescop
 * 
 */
@Controller
@SessionAttributes(value = { "registrazioniFilter", "registrazioniInOutCommand" })
public class AccertamentiController extends BaseController<RegistrazioniFilter> {

    @Autowired
    private RegistrazioniFilterService registrazioniFilterService;
    @Autowired
    private AlberoprocService alberoprocService;
    @Autowired
    private RegistrazioniCausaliService registrazioniCausaliService;
    @Autowired
    private AnagrafeService anagrafeService;
    @Autowired
    private MercatiUsoService mercatiUsoService;

    @RequestMapping
    public String create(Model model) {

	// §§§BEGIN§§§
	RegistrazioniFilter registrazioniFilter = new RegistrazioniFilter();
	RegistrazioniInOutCommand regIoCommand = new RegistrazioniInOutCommand();
	regIoCommand.setFilter(registrazioniFilter);
	fixRenderEntityProperty(registrazioniFilter);
	model.addAttribute("registrazioniCausaliList", registrazioniCausaliService.findAll(null, null));
	model.addAttribute("registrazioniFilter", registrazioniFilter);
	model.addAttribute("registrazioniInOutCommand", regIoCommand);
	setPageAttributes(model);
	return "accertamenti/accertamentiSearch";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String search(@ModelAttribute("registrazioniFilter") RegistrazioniFilter registrazioniFilter, BindingResult result, SessionStatus status,
	    Model model, HttpServletRequest request, HttpServletResponse response) {

	// §§§BEGIN§§§
	registrazioniFilterService.setBindingResult(result);
	if (registrazioniFilter.getAlberoproc().getId().getCodice() != null) {
	    registrazioniFilter.setAlberoproc(alberoprocService.findById(registrazioniFilter.getAlberoproc().getId()));
	}
	if (registrazioniFilter.getRegistrazioniCausali().getId().getCodice() != null) {
	    RegistrazioniCausali registrazioniCausali = registrazioniCausaliService.findById(registrazioniFilter.getRegistrazioniCausali().getId());
	    registrazioniFilter.setRegistrazioniCausali(registrazioniCausali);
	}
	if (registrazioniFilter.getAnagrafe().getId().getCodice() != null) {
	    Anagrafe anagrafe = anagrafeService.findById(registrazioniFilter.getAnagrafe().getId());
	    registrazioniFilter.setAnagrafe(anagrafe);
	}
	if (registrazioniFilter.getMercatiUso().getId().getCodice() != null) {
	    MercatiUso mercatiUso = mercatiUsoService.findById(registrazioniFilter.getMercatiUso().getId());
	    registrazioniFilter.setMercatiUso(mercatiUso);
	}
	List<RegistrazioniFilter> list = registrazioniFilterService.searchRegistrazioni(registrazioniFilter);
	boolean export = createJMesaExport(request, response, list);
	if (export)
	    return null;
	fixRenderEntityProperty(registrazioniFilter);
	model.addAttribute("registrazioniFilter", registrazioniFilter);
	model.addAttribute("registrazioniFilterList", list);
	model.addAttribute("enum", registrazioniFilter.getRaggruppamentoEnum());
	return "accertamenti/list";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    protected void fixMergeEntityProperty(RegistrazioniFilter entity) {

    }

    @Override
    protected void fixRenderEntityProperty(RegistrazioniFilter entity) {

	if (entity.getAlberoproc() == null) {
	    entity.setAlberoproc(new Alberoproc());
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
