package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Lavoritipi;
import it.gruppoinit.pal.gp.core.domain.LavoritipiCausalioneri;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.Tipicausalioneri;
import it.gruppoinit.pal.gp.core.domain.Tipiunitamisura;
import it.gruppoinit.pal.gp.core.service.LavoritipiCausalioneriService;
import it.gruppoinit.pal.gp.core.service.LavoritipiService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
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
 * @author Riccardo Bocci
 */
//DAELIMINARE @Controller
@SessionAttributes("lavoritipicausalioneri")
public class LavoritipiCausalioneriController extends BaseController<LavoritipiCausalioneri> {

    @Autowired
    private LavoritipiCausalioneriService lavoritipicausalioneriService;
    @Autowired
    private SoftwareService softwareService;
    @Autowired
    private LavoritipiService lavoritipiService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	// §§§BEGIN§§§
	Integer codiceLavoro = getRequestParameter(request, "codiceLavoro");
	List<LavoritipiCausalioneri> lavoritipicausalioneriList = null;
	Lavoritipi lavoritipi = null;
	if (codiceLavoro.intValue() != 0) {
	    PkId idLavoro = new PkId(codiceLavoro);
	    lavoritipi = lavoritipiService.findById(idLavoro);
	    lavoritipicausalioneriList = lavoritipicausalioneriService.findByLavoritipi(lavoritipi);
	} else {
	    lavoritipicausalioneriList = lavoritipicausalioneriService.findAll(null, null);
	}
	ModelMap model = new ModelMap(lavoritipicausalioneriList);
	boolean export = createJMesaExport(request, response, lavoritipicausalioneriList);
	if (export) {
	    return null;
	}
	model.addAttribute("lavoritipi", lavoritipi);
	model.addAttribute("lavoritipicausalioneriList", lavoritipicausalioneriList);
	return model;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String create(Model model, HttpServletRequest request) {

	// §§§BEGIN§§§
	LavoritipiCausalioneri lavoritipicausalioneri = new LavoritipiCausalioneri();
	lavoritipicausalioneri.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
	Integer codiceLavoro = getRequestParameter(request, "codiceLavoro");
	if (codiceLavoro.intValue() != 0) {
	    PkId idLavoro = new PkId(codiceLavoro);
	    Lavoritipi lavoritipi = lavoritipiService.findById(idLavoro);
	    lavoritipicausalioneri.setLavoritipi(lavoritipi);
	}
	fixRenderEntityProperty(lavoritipicausalioneri);
	model.addAttribute("lavoritipicausalioneri", lavoritipicausalioneri);
	setPageAttributes(model);
	// §§§END§§§
	return "lavoritipicausalioneri/form";
    }

    @RequestMapping
    public String insert(@ModelAttribute("lavoritipicausalioneri") LavoritipiCausalioneri lavoritipicausalioneri, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	// §§§BEGIN§§§
	fixMergeEntityProperty(lavoritipicausalioneri);
	lavoritipicausalioneri.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
	try {
	    lavoritipicausalioneriService.insert(lavoritipicausalioneri);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, lavoritipicausalioneri, e);
	    Integer codiceLavoro = getRequestParameter(request, "codiceLavoro");
	    if (codiceLavoro.intValue() != 0) {
		PkId idLavoro = new PkId(codiceLavoro);
		Lavoritipi lavoritipi = lavoritipiService.findById(idLavoro);
		lavoritipicausalioneri.setLavoritipi(lavoritipi);
	    }
	    fixRenderEntityProperty(lavoritipicausalioneri);
	    return "lavoritipicausalioneri/form";
	}
	status.setComplete();
	Integer codiceLavoro = getRequestParameter(request, "codiceLavoro");
	String codiceLavoroStr = codiceLavoro.intValue() == 0 ? "" : String.valueOf(codiceLavoro);
	return "redirect:view.htm?codice=" + lavoritipicausalioneri.getId().getCodice() + "&codiceLavoro=" + codiceLavoroStr + "&status_msg=01";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	// §§§BEGIN§§§
	LavoritipiCausalioneri lavoritipicausalioneri = lavoritipicausalioneriService.findById(new PkId(codice));
	fixRenderEntityProperty(lavoritipicausalioneri);
	model.addAttribute("lavoritipicausalioneri", lavoritipicausalioneri);
	setPageAttributes(model);
	// §§§END§§§
	return "lavoritipicausalioneri/form";
    }

    @RequestMapping
    public String update(@ModelAttribute("lavoritipicausalioneri") LavoritipiCausalioneri lavoritipicausalioneri, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	// §§§BEGIN§§§
	fixMergeEntityProperty(lavoritipicausalioneri);
	try {
	    lavoritipicausalioneriService.update(lavoritipicausalioneri);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, lavoritipicausalioneri, e);
	    Integer codiceLavoro = getRequestParameter(request, "codiceLavoro");
	    if (codiceLavoro.intValue() != 0) {
		PkId idLavoro = new PkId(codiceLavoro);
		Lavoritipi lavoritipi = lavoritipiService.findById(idLavoro);
		lavoritipicausalioneri.setLavoritipi(lavoritipi);
	    }
	    fixRenderEntityProperty(lavoritipicausalioneri);
	    return "lavoritipicausalioneri/form";
	}
	status.setComplete();
	Integer codiceLavoro = getRequestParameter(request, "codiceLavoro");
	String codiceLavoroStr = codiceLavoro.intValue() == 0 ? "" : String.valueOf(codiceLavoro);
	return "redirect:view.htm?codice=" + lavoritipicausalioneri.getId().getCodice() + "&codiceLavoro=" + codiceLavoroStr + "&status_msg=02";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String delete(@ModelAttribute("lavoritipicausalioneri") LavoritipiCausalioneri lavoritipicausalioneri, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	// §§§BEGIN§§§
	LavoritipiCausalioneri objToDelete = lavoritipicausalioneriService.findById(lavoritipicausalioneri.getId());
	try {
	    lavoritipicausalioneriService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    Integer codiceLavoro = getRequestParameter(request, "codiceLavoro");
	    if (codiceLavoro.intValue() != 0) {
		PkId idLavoro = new PkId(codiceLavoro);
		Lavoritipi lavoritipi = lavoritipiService.findById(idLavoro);
		lavoritipicausalioneri.setLavoritipi(lavoritipi);
	    }
	    fixRenderEntityProperty(lavoritipicausalioneri);
	    return "lavoritipicausalioneri/form";
	}
	status.setComplete();
	Integer codiceLavoro = getRequestParameter(request, "codiceLavoro");
	String codiceLavoroStr = codiceLavoro.intValue() == 0 ? "" : String.valueOf(codiceLavoro);
	return "redirect:list.htm?codiceLavoro=" + codiceLavoroStr;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    protected void fixMergeEntityProperty(LavoritipiCausalioneri entity) {

	if (entity.getTipiunitamisura() != null && entity.getTipiunitamisura().getId() != null
		&& entity.getTipiunitamisura().getId().getCodice() == null) {
	    entity.setTipiunitamisura(null);
	}
	if (entity.getTipicausalioneri() != null && entity.getTipicausalioneri().getId() != null
		&& entity.getTipicausalioneri().getId().getCodice() == null) {
	    entity.setTipicausalioneri(null);
	}
	if (entity.getLavoritipi() != null && entity.getLavoritipi().getId() != null && entity.getLavoritipi().getId().getCodice() == null) {
	    entity.setLavoritipi(null);
	}
    }

    @Override
    protected void fixRenderEntityProperty(LavoritipiCausalioneri entity) {

	if (entity.getTipiunitamisura() == null) {
	    entity.setTipiunitamisura(new Tipiunitamisura());
	}
	if (entity.getSoftware() == null) {
	    entity.setSoftware(new Software());
	}
	if (entity.getTipicausalioneri() == null) {
	    entity.setTipicausalioneri(new Tipicausalioneri());
	}
	if (entity.getLavoritipi() == null) {
	    entity.setLavoritipi(new Lavoritipi());
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

    }

    private Integer getRequestParameter(HttpServletRequest request, String paramName) {

	String stringParam = (String) request.getParameter(paramName);
	Integer integerParam = 0;
	if (StringUtils.isNotBlank(stringParam)) {
	    integerParam = Integer.parseInt(stringParam);
	}
	return integerParam;
    }
}
