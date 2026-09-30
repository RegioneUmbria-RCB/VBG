package it.gruppoinit.pal.gp.backoffice.web;

import java.io.IOException;
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

import it.gruppoinit.pal.gp.core.domain.ComunicazioniD;
import it.gruppoinit.pal.gp.core.domain.ComunicazioniT;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.firmadigitale.DocumentiDaFirmareService;
import it.gruppoinit.pal.gp.core.service.ComunicazioniDService;
import it.gruppoinit.pal.gp.core.service.ComunicazioniTService;

/**
 * 
 * @author
 */
@Controller
@SessionAttributes("comunicazionit")
public class ComunicazioniTController extends BaseController<ComunicazioniT> {

    @Autowired
    private ComunicazioniTService comunicazionitService;
    @Autowired
    private ComunicazioniDService comunicazioniDService;
    @Autowired
    private DocumentiDaFirmareService documentiDaFirmareService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	List<ComunicazioniT> comunicazionitList = comunicazionitService.findAll(null, null);
	ModelMap model = new ModelMap(comunicazionitList);
	boolean export = createJMesaExport(request, response, comunicazionitList);
	if (export) {
	    return null;
	}
	model.addAttribute("comunicazionitList", comunicazionitList);
	return model;
    }

    @RequestMapping
    public String create(Model model) {

	ComunicazioniT comunicazionit = new ComunicazioniT();
	fixRenderEntityProperty(comunicazionit);
	model.addAttribute("comunicazionit", comunicazionit);
	setPageAttributes(model);
	return "comunicazionit/form";
    }

    @RequestMapping
    public String insert(@ModelAttribute("comunicazionit") ComunicazioniT comunicazionit, BindingResult result, SessionStatus status) {

	fixMergeEntityProperty(comunicazionit);
	try {
	    comunicazionitService.insert(comunicazionit);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, comunicazionit, e);
	    fixRenderEntityProperty(comunicazionit);
	    return "comunicazionit/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + comunicazionit.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	ComunicazioniT comunicazionit = comunicazionitService.findById(id);
	fixRenderEntityProperty(comunicazionit);
	model.addAttribute("comunicazionit", comunicazionit);
	setPageAttributes(model);
	return "comunicazionit/form";
    }

    @RequestMapping
    public String update(@ModelAttribute("comunicazionit") ComunicazioniT comunicazionit, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	fixMergeEntityProperty(comunicazionit);
	try {
	    comunicazionitService.update(comunicazionit);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, comunicazionit, e);
	    fixRenderEntityProperty(comunicazionit);
	    return "comunicazionit/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + comunicazionit.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String delete(@ModelAttribute("comunicazionit") ComunicazioniT comunicazionit, BindingResult result, SessionStatus status) {

	ComunicazioniT objToDelete = comunicazionitService.findById(comunicazionit.getId());
	try {
	    comunicazionitService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderEntityProperty(comunicazionit);
	    return "comunicazionit/form";
	}
	status.setComplete();
	return "redirect:list.htm";
    }

    @RequestMapping
    public void ajaxReportDocDaFirmare(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request, HttpServletResponse response)
	    throws IOException {

	ComunicazioniD rec = comunicazioniDService.findById(new PkId(codice));
	if (rec == null) {
	    response.getWriter().write("Non è stato trovato nessun record. Controllare il dato " + codice);
	    return;
	}
	Integer codiceOggetto = null;
	if (rec.getOggetti() != null) {
	    if (rec.getOggetti().getId() != null) {
		if (rec.getOggetti().getId().getCodice() != null) {
		    codiceOggetto = rec.getOggetti().getId().getCodice();
		}
	    }
	}
	if (codiceOggetto == null) {
	    response.getWriter().write("Non è stato trovato nessun allegato da firmare. Codice oggetto è nullo");
	    return;
	}
	String result = documentiDaFirmareService.findReportHTMLOggettoDaFirmare(codiceOggetto);
	response.getWriter().write(result);
	return;
    }

    @Override
    protected void fixMergeEntityProperty(ComunicazioniT entity) {

    }

    @Override
    protected void fixRenderEntityProperty(ComunicazioniT entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
