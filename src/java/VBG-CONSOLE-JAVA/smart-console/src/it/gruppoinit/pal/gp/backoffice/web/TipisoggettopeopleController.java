package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.Tipisoggettopeople;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 * 
 * @author gianpaolot
 */
@Controller
@SessionAttributes("tipisoggettopeople")
public class TipisoggettopeopleController extends BaseController<Tipisoggettopeople> {

    //    @Autowired
    //    private TipisoggettopeopleService tipisoggettopeopleService;
    //    @Autowired
    //    private SoftwareService softwareService;
    //
    //    @RequestMapping
    //    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {
    //
    //	List<Tipisoggettopeople> tipisoggettopeopleList = tipisoggettopeopleService.findAll(null, null);
    //	ModelMap model = new ModelMap(tipisoggettopeopleList);
    //	boolean export = createJMesaExport(request, response, tipisoggettopeopleList);
    //	if (export) {
    //	    return null;
    //	}
    //	model.addAttribute("tipisoggettopeopleList", tipisoggettopeopleList);
    //	return model;
    //    }
    //
    //    @RequestMapping
    //    public String create(Model model) {
    //
    //	Tipisoggettopeople tipisoggettopeople = new Tipisoggettopeople();
    //	tipisoggettopeople.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    //	fixRenderEntityProperty(tipisoggettopeople);
    //	model.addAttribute("tipisoggettopeople", tipisoggettopeople);
    //	setPageAttributes(model);
    //	return "tipisoggettopeople/form";
    //    }
    //
    //    @RequestMapping
    //    public String insert(@ModelAttribute("tipisoggettopeople") Tipisoggettopeople tipisoggettopeople, BindingResult result, SessionStatus status) {
    //
    //	fixMergeEntityProperty(tipisoggettopeople);
    //	tipisoggettopeople.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    //	try {
    //	    tipisoggettopeopleService.insert(tipisoggettopeople);
    //	} catch (Exception e) {
    //	    copyErrorsToBindingResult(tipisoggettopeopleService.getValidationMessages(), result, tipisoggettopeople, e.getMessage());
    //	    fixRenderEntityProperty(tipisoggettopeople);
    //	    return "tipisoggettopeople/form";
    //	}
    //	status.setComplete();
    //	return "redirect:view.htm?codice=" + tipisoggettopeople.getId().getCodice() + "&status_msg=01";
    //    }
    //
    //    @RequestMapping
    //    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {
    //
    //	PkId id = new PkId(codice);
    //	Tipisoggettopeople tipisoggettopeople = tipisoggettopeopleService.findById(id);
    //	fixRenderEntityProperty(tipisoggettopeople);
    //	model.addAttribute("tipisoggettopeople", tipisoggettopeople);
    //	setPageAttributes(model);
    //	return "tipisoggettopeople/form";
    //    }
    //
    //    @RequestMapping
    //    public String update(@ModelAttribute("tipisoggettopeople") Tipisoggettopeople tipisoggettopeople, BindingResult result, SessionStatus status,
    //	    HttpServletRequest request) {
    //
    //	fixMergeEntityProperty(tipisoggettopeople);
    //	try {
    //	    tipisoggettopeopleService.update(tipisoggettopeople);
    //	} catch (Exception e) {
    //	    copyErrorsToBindingResult(tipisoggettopeopleService.getValidationMessages(), result, tipisoggettopeople, e.getMessage());
    //	    fixRenderEntityProperty(tipisoggettopeople);
    //	    return "tipisoggettopeople/form";
    //	}
    //	status.setComplete();
    //	return "redirect:view.htm?codice=" + tipisoggettopeople.getId().getCodice() + "&status_msg=02";
    //    }
    //
    //    @RequestMapping
    //    public String delete(@ModelAttribute("tipisoggettopeople") Tipisoggettopeople tipisoggettopeople, BindingResult result, SessionStatus status) {
    //
    //	Tipisoggettopeople objToDelete = tipisoggettopeopleService.findById(tipisoggettopeople.getId());
    //	try {
    //	    tipisoggettopeopleService.delete(objToDelete);
    //	} catch (Exception e) {
    //	    copyErrorsToBindingResult(tipisoggettopeopleService.getValidationMessages(), result, objToDelete, e.getMessage());
    //	    fixRenderEntityProperty(tipisoggettopeople);
    //	    return "tipisoggettopeople/form";
    //	}
    //	status.setComplete();
    //	return "redirect:list.htm";
    //    }
    @Override
    protected void fixMergeEntityProperty(Tipisoggettopeople entity) {

    }

    @Override
    protected void fixRenderEntityProperty(Tipisoggettopeople entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
