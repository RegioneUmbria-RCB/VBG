package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Lavoricategorie;
import it.gruppoinit.pal.gp.core.domain.Lavoritipi;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.LavoricategorieService;
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
@SessionAttributes("lavoritipi")
public class LavoritipiController extends BaseController<Lavoritipi> {

    @Autowired
    private LavoritipiService lavoritipiService;
    @Autowired
    private SoftwareService softwareService;
    @Autowired
    private LavoricategorieService lavoricategorieService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	// §§§BEGIN§§§
	Integer codiceCategoria = getRequestParameter(request, "codiceCategoria");
	Lavoricategorie categoria = null;
	List<Lavoritipi> lavoritipiList = null;
	if (codiceCategoria.intValue() != 0) {
	    PkId idCategoria = new PkId(codiceCategoria);
	    categoria = lavoricategorieService.findById(idCategoria);
	    lavoritipiList = lavoritipiService.findByLavoricategorie(categoria);
	} else {
	    lavoritipiList = lavoritipiService.findAll(null, null);
	}
	ModelMap model = new ModelMap(lavoritipiList);
	boolean export = createJMesaExport(request, response, lavoritipiList);
	if (export) {
	    return null;
	}
	model.addAttribute("categoria", categoria);
	model.addAttribute("lavoritipiList", lavoritipiList);
	return model;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String create(Model model, HttpServletRequest request) {

	// §§§BEGIN§§§
	Integer codiceCategoria = getRequestParameter(request, "codiceCategoria");
	Lavoritipi lavoritipi = new Lavoritipi();
	if (codiceCategoria.intValue() != 0) {
	    PkId idCategoria = new PkId(codiceCategoria);
	    Lavoricategorie categoria = lavoricategorieService.findById(idCategoria);
	    lavoritipi.setLavoricategorie(categoria);
	}
	lavoritipi.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
	fixRenderEntityProperty(lavoritipi);
	model.addAttribute("lavoritipi", lavoritipi);
	setPageAttributes(model);
	// §§§END§§§
	return "lavoritipi/form";
    }

    @RequestMapping
    public String insert(@ModelAttribute("lavoritipi") Lavoritipi lavoritipi, BindingResult result, SessionStatus status, HttpServletRequest request) {

	// §§§BEGIN§§§
	fixMergeEntityProperty(lavoritipi);
	lavoritipi.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
	try {
	    lavoritipiService.insert(lavoritipi);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, lavoritipi, e);
	    Integer codiceCategoria = getRequestParameter(request, "codiceCategoria");
	    if (codiceCategoria.intValue() != 0) {
		PkId idCategoria = new PkId(codiceCategoria);
		Lavoricategorie categoria = lavoricategorieService.findById(idCategoria);
		lavoritipi.setLavoricategorie(categoria);
	    }
	    fixRenderEntityProperty(lavoritipi);
	    return "lavoritipi/form";
	}
	status.setComplete();
	Integer codiceCategoria = getRequestParameter(request, "codiceCategoria");
	String codiceCategoriaStr = codiceCategoria.intValue() == 0 ? "" : String.valueOf(codiceCategoria);
	return "redirect:view.htm?codice=" + lavoritipi.getId().getCodice() + "&codiceCategoria=" + codiceCategoriaStr + "&status_msg=01";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	// §§§BEGIN§§§
	PkId id = new PkId(codice);
	Lavoritipi lavoritipi = lavoritipiService.findById(id);
	fixRenderEntityProperty(lavoritipi);
	model.addAttribute("lavoritipi", lavoritipi);
	setPageAttributes(model);
	// §§§END§§§
	return "lavoritipi/form";
    }

    @RequestMapping
    public String update(@ModelAttribute("lavoritipi") Lavoritipi lavoritipi, BindingResult result, SessionStatus status, HttpServletRequest request) {

	// §§§BEGIN§§§
	fixMergeEntityProperty(lavoritipi);
	try {
	    lavoritipiService.update(lavoritipi);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, lavoritipi, e);
	    Integer codiceCategoria = getRequestParameter(request, "codiceCategoria");
	    if (codiceCategoria.intValue() != 0) {
		PkId idCategoria = new PkId(codiceCategoria);
		Lavoricategorie categoria = lavoricategorieService.findById(idCategoria);
		lavoritipi.setLavoricategorie(categoria);
	    }
	    fixRenderEntityProperty(lavoritipi);
	    return "lavoritipi/form";
	}
	status.setComplete();
	Integer codiceCategoria = getRequestParameter(request, "codiceCategoria");
	String codiceCategoriaStr = codiceCategoria.intValue() == 0 ? "" : String.valueOf(codiceCategoria);
	return "redirect:view.htm?codice=" + lavoritipi.getId().getCodice() + "&codiceCategoria=" + codiceCategoriaStr + "&status_msg=02";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String delete(@ModelAttribute("lavoritipi") Lavoritipi lavoritipi, BindingResult result, SessionStatus status, HttpServletRequest request) {

	// §§§BEGIN§§§
	Lavoritipi objToDelete = lavoritipiService.findById(lavoritipi.getId());
	try {
	    lavoritipiService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    Integer codiceCategoria = getRequestParameter(request, "codiceCategoria");
	    if (codiceCategoria.intValue() != 0) {
		PkId idCategoria = new PkId(codiceCategoria);
		Lavoricategorie categoria = lavoricategorieService.findById(idCategoria);
		lavoritipi.setLavoricategorie(categoria);
	    }
	    fixRenderEntityProperty(lavoritipi);
	    return "lavoritipi/form";
	}
	status.setComplete();
	Integer codiceCategoria = getRequestParameter(request, "codiceCategoria");
	String codiceCategoriaStr = codiceCategoria.intValue() == 0 ? "" : String.valueOf(codiceCategoria);
	return "redirect:list.htm?codiceCategoria=" + codiceCategoriaStr;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    protected void fixMergeEntityProperty(Lavoritipi entity) {

    }

    @Override
    protected void fixRenderEntityProperty(Lavoritipi entity) {

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
