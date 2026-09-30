package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.backoffice.web.util.Month;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.DocumentiContabilita;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.DocumenticontabilitaFilter;
import it.gruppoinit.pal.gp.core.domain.web.DocumenticontabilitaCommand;
import it.gruppoinit.pal.gp.core.service.DocumentiContabilitaService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;

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

/**
 * 
 * @author
 */
@Controller
@SessionAttributes(value = { "documenticontabilita" })
public class DocumentiContabilitaController extends BaseController<DocumentiContabilita> {

    @Autowired
    private DocumentiContabilitaService documenticontabilitaService;
    @Autowired
    private SoftwareService softwareService;

    @RequestMapping
    public String searchDocumenti(@RequestParam(required = false, value = "codice") Integer codice, HttpServletRequest request, Model model) {

	DocumentiContabilita entity = new DocumentiContabilita();
	DocumenticontabilitaFilter filter = new DocumenticontabilitaFilter();
	DocumenticontabilitaCommand documentiContabilita = new DocumenticontabilitaCommand(entity, filter);
	documentiContabilita.setIsInserimentoVeloce(true);
	model.addAttribute("documenticontabilita", documentiContabilita);
	List<Month> listaMesi = Month.getMonth();
	model.addAttribute("listaMesi", listaMesi);
	if (codice != null) {
	    model.addAttribute("codiceDocInserito", codice);
	}
	return "documenticontabilita/search";
    }

    @RequestMapping
    public ModelMap list(@ModelAttribute("documenticontabilita") DocumenticontabilitaCommand documenticontabilita, BindingResult result,
	    SessionStatus status, HttpServletRequest request, HttpServletResponse response) {

	//List<DocumentiContabilita> documenticontabilitaList = new ArrayList<DocumentiContabilita>();
	List<DocumentiContabilita> documenticontabilitaList = documenticontabilitaService.findByFilter(documenticontabilita.getFilter(), null, null);
	ModelMap model = new ModelMap(documenticontabilitaList);
	boolean export = createJMesaExport(request, response, documenticontabilitaList);
	if (export) {
	    return null;
	}
	model.addAttribute("documenticontabilitaList", documenticontabilitaList);
	return model;
    }

    @RequestMapping
    public String create(Model model) {

	DocumentiContabilita entity = new DocumentiContabilita();
	DocumenticontabilitaCommand documenticontabilita = new DocumenticontabilitaCommand();
	entity.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
	fixRenderEntityProperty(entity);
	documenticontabilita.setEntity(entity);
	documenticontabilita.setIsInserimentoVeloce(false);
	model.addAttribute("documenticontabilita", documenticontabilita);
	setPageAttributes(model);
	return "documenticontabilita/form";
    }

    @RequestMapping
    public String insert(@ModelAttribute("documenticontabilita") DocumenticontabilitaCommand documenticontabilita, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	DocumentiContabilita entity = documenticontabilita.getEntity();
	fixMergeEntityProperty(entity);
	entity.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
	try {
	    documenticontabilitaService.insert(entity);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, documenticontabilita.getEntity(), true, e);
	    fixRenderEntityProperty(entity);
	    documenticontabilita.setEntity(entity);
	    if (documenticontabilita.getIsInserimentoVeloce()) {
		request.setAttribute("_displaySchedeAttivita", "display:;");
		request.setAttribute("_sezioneDatiPiu", "sezioneDatiMeno");
		return "documenticontabilita/search";
	    } else {
		return "documenticontabilita/form";
	    }
	}
	status.setComplete();
	if (documenticontabilita.getIsInserimentoVeloce()) {
	    documenticontabilita.setEntity(entity);
	    return "redirect:searchDocumenti.htm?codice=" + entity.getId().getCodice() + "&status_msg=01";
	} else {
	    return "redirect:view.htm?codice=" + entity.getId().getCodice() + "&status_msg=01";
	}
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	DocumenticontabilitaCommand documenticontabilita = new DocumenticontabilitaCommand();
	DocumentiContabilita entity = documenticontabilitaService.findById(id);
	documenticontabilita.setEntity(entity);
	fixRenderEntityProperty(documenticontabilita.getEntity());
	model.addAttribute("documenticontabilita", documenticontabilita);
	setPageAttributes(model);
	return "documenticontabilita/form";
    }

    @RequestMapping
    public String update(@ModelAttribute("documenticontabilita") DocumenticontabilitaCommand documenticontabilita, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	DocumentiContabilita entity = documenticontabilita.getEntity();
	fixMergeEntityProperty(entity);
	try {
	    documenticontabilitaService.update(entity);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, documenticontabilita.getEntity(), true, e);
	    fixRenderEntityProperty(documenticontabilita.getEntity());
	    return "documenticontabilita/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + entity.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String delete(Model model, @ModelAttribute("documenticontabilita") DocumenticontabilitaCommand documenticontabilita, BindingResult result,
	    SessionStatus status) {

	DocumentiContabilita objToDelete = documenticontabilitaService.findById(documenticontabilita.getEntity().getId());
	try {
	    documenticontabilitaService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, documenticontabilita.getEntity(), true, e);
	    fixRenderEntityProperty(documenticontabilita.getEntity());
	    return "documenticontabilita/form";
	}
	model.addAttribute("documenticontabilita", documenticontabilita);
	status.setComplete();
	return "redirect:searchDocumenti.htm";
    }

    @Override
    protected void fixMergeEntityProperty(DocumentiContabilita entity) {

    }

    @Override
    protected void fixRenderEntityProperty(DocumentiContabilita entity) {

	if (entity.getOggetti() == null) {
	    entity.setOggetti(new Oggetti());
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
