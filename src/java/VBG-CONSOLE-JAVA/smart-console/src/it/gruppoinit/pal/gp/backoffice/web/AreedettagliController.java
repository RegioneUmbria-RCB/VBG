package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.Aree;
import it.gruppoinit.pal.gp.core.domain.Areedettagli;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Stradario;
import it.gruppoinit.pal.gp.core.service.AreeService;
import it.gruppoinit.pal.gp.core.service.AreedettagliService;
import it.gruppoinit.pal.gp.core.service.StradarioService;

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
@Controller
@SessionAttributes("areedettagli")
public class AreedettagliController extends BaseController<Areedettagli> {

    @Autowired
    private AreedettagliService areedettagliService;
    @Autowired
    private StradarioService stradarioService;
    @Autowired
    private AreeService areeService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	Integer codiceArea = getRequestParameter(request, "codiceArea");
	Integer codiceStradario = getRequestParameter(request, "codiceStradario");
	List<Areedettagli> areedettagliList = null;
	Aree area = null;
	Stradario stradario = null;
	if (codiceArea.intValue() != 0) {
	    PkId idArea = new PkId(codiceArea);
	    area = areeService.findById(idArea);
	    areedettagliList = areedettagliService.findByAree(area);
	} else if (codiceStradario.intValue() != 0) {
	    PkId idStradario = new PkId(codiceStradario);
	    stradario = stradarioService.findById(idStradario);
	    areedettagliList = areedettagliService.findByStradario(stradario);
	} else {
	    areedettagliList = areedettagliService.findAll(null, null);
	}
	ModelMap model = new ModelMap();
	boolean export = createJMesaExport(request, response, areedettagliList);
	if (export) {
	    return null;
	}
	model.addAttribute("area", area);
	model.addAttribute("stradario", stradario);
	model.addAttribute("areedettagliList", areedettagliList);
	return model;
    }

    @RequestMapping
    public String create(Model model, HttpServletRequest request) {

	Integer codiceArea = getRequestParameter(request, "codiceArea");
	Integer codiceStradario = getRequestParameter(request, "codiceStradario");
	Aree area = null;
	Stradario stradario = null;
	if (codiceArea.intValue() != 0) {
	    PkId idArea = new PkId(codiceArea);
	    area = areeService.findById(idArea);
	} else if (codiceStradario.intValue() != 0) {
	    PkId idStradario = new PkId(codiceStradario);
	    stradario = stradarioService.findById(idStradario);
	}
	Areedettagli areedettagli = new Areedettagli();
	areedettagli.setStradario(stradario);
	areedettagli.setAree(area);
	fixRenderEntityProperty(areedettagli);
	model.addAttribute("areedettagli", areedettagli);
	setPageAttributes(model);
	return "areedettagli/form";
    }

    @RequestMapping
    public String insert(@ModelAttribute("areedettagli") Areedettagli areedettagli, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	Integer codiceArea = getRequestParameter(request, "codiceArea");
	Integer codiceStradario = getRequestParameter(request, "codiceStradario");
	fixMergeEntityProperty(areedettagli);
	try {
	    areedettagliService.insert(areedettagli);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, areedettagli, e);
	    fixRenderEntityProperty(areedettagli);
	    return "areedettagli/form";
	}
	status.setComplete();
	String codiceAreaStr = codiceArea.intValue() == 0 ? "" : String.valueOf(codiceArea);
	String codiceStradarioStr = codiceStradario.intValue() == 0 ? "" : String.valueOf(codiceStradario);
	return "redirect:view.htm?codiceArea=" + codiceAreaStr + "&codiceStradario=" + codiceStradarioStr + "&codice="
		+ areedettagli.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	Areedettagli areedettagli = areedettagliService.findById(id);
	fixRenderEntityProperty(areedettagli);
	model.addAttribute("areedettagli", areedettagli);
	setPageAttributes(model);
	return "areedettagli/form";
    }

    @RequestMapping
    public String update(@ModelAttribute("areedettagli") Areedettagli areedettagli, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	Integer codiceArea = getRequestParameter(request, "codiceArea");
	Integer codiceStradario = getRequestParameter(request, "codiceStradario");
	fixMergeEntityProperty(areedettagli);
	try {
	    areedettagliService.update(areedettagli);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, areedettagli, e);
	    fixRenderEntityProperty(areedettagli);
	    return "areedettagli/form";
	}
	status.setComplete();
	String codiceAreaStr = codiceArea.intValue() == 0 ? "" : String.valueOf(codiceArea);
	String codiceStradarioStr = codiceStradario.intValue() == 0 ? "" : String.valueOf(codiceStradario);
	return "redirect:view.htm?codiceArea=" + codiceAreaStr + "&codiceStradario=" + codiceStradarioStr + "&codice="
		+ areedettagli.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String delete(@ModelAttribute("areedettagli") Areedettagli areedettagli, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	Integer codiceArea = getRequestParameter(request, "codiceArea");
	Integer codiceStradario = getRequestParameter(request, "codiceStradario");
	Areedettagli objToDelete = areedettagliService.findById(areedettagli.getId());
	try {
	    areedettagliService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderEntityProperty(areedettagli);
	    return "areedettagli/form";
	}
	status.setComplete();
	String codiceAreaStr = codiceArea.intValue() == 0 ? "" : String.valueOf(codiceArea);
	String codiceStradarioStr = codiceStradario.intValue() == 0 ? "" : String.valueOf(codiceStradario);
	return "redirect:list.htm?codiceArea=" + codiceAreaStr + "&codiceStradario=" + codiceStradarioStr;
    }

    @Override
    protected void fixMergeEntityProperty(Areedettagli entity) {

	if (entity.getAree() != null && entity.getAree().getId() != null && entity.getAree().getId().getCodice() == null) {
	    entity.setAree(null);
	}
	if (entity.getStradario() != null && entity.getStradario().getId() != null && entity.getStradario().getId().getCodice() == null) {
	    entity.setStradario(null);
	}
    }

    @Override
    protected void fixRenderEntityProperty(Areedettagli entity) {

	if (entity.getAree() == null) {
	    entity.setAree(new Aree());
	}
	if (entity.getStradario() == null) {
	    entity.setStradario(new Stradario());
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
