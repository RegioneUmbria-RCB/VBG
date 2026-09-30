package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.ODestinazioni;
import it.gruppoinit.pal.gp.core.service.ODestinazioniService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 * 
 * @author 
 */
@Controller
@SessionAttributes("odestinazioni")
public class ODestinazioniController extends BaseController<ODestinazioni> {

    @Autowired
    private ODestinazioniService odestinazioniService;
    @Autowired
    private SoftwareService softwareService;

    // @RequestMapping
    // public ModelMap list(HttpServletRequest request, HttpServletResponse response) {
    //
    // List<ODestinazioni> odestinazioniList = odestinazioniService.findAll(null, null);
    // ModelMap model = new ModelMap(odestinazioniList);
    // boolean export = createJMesaExport(request, response, odestinazioniList);
    // if (export) {
    // return null;
    // }
    // model.addAttribute("odestinazioniList", odestinazioniList);
    // return model;
    // }
    //
    // @RequestMapping
    // public String create(Model model) {
    //
    // ODestinazioni odestinazioni = new ODestinazioni();
    // odestinazioni.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // fixRenderEntityProperty(odestinazioni);
    // model.addAttribute("odestinazioni", odestinazioni);
    // setPageAttributes(model);
    // return "odestinazioni/form";
    // }
    //
    // @RequestMapping
    // public String insert(@ModelAttribute("odestinazioni") ODestinazioni odestinazioni, BindingResult result,
    // SessionStatus status) {
    //
    // fixMergeEntityProperty(odestinazioni);
    // odestinazioni.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // try {
    // odestinazioniService.insert(odestinazioni);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(odestinazioniService.getValidationMessages(), result, odestinazioni, e.getMessage());
    // fixRenderEntityProperty(odestinazioni);
    // return "odestinazioni/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + odestinazioni.getId().getCodice() + "&status_msg=01";
    // }
    //
    // @RequestMapping
    // public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {
    //
    // PkId id = new PkId(codice);
    // ODestinazioni odestinazioni = odestinazioniService.findById(id);
    // fixRenderEntityProperty(odestinazioni);
    // model.addAttribute("odestinazioni", odestinazioni);
    // setPageAttributes(model);
    // return "odestinazioni/form";
    // }
    //
    // @RequestMapping
    // public String update(@ModelAttribute("odestinazioni") ODestinazioni odestinazioni, BindingResult result,
    // SessionStatus status,
    // HttpServletRequest request) {
    //
    // fixMergeEntityProperty(odestinazioni);
    // try {
    // odestinazioniService.update(odestinazioni);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(odestinazioniService.getValidationMessages(), result, odestinazioni, e.getMessage());
    // fixRenderEntityProperty(odestinazioni);
    // return "odestinazioni/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + odestinazioni.getId().getCodice() + "&status_msg=02";
    // }
    //
    // @RequestMapping
    // public String delete(@ModelAttribute("odestinazioni") ODestinazioni odestinazioni, BindingResult result,
    // SessionStatus status) {
    //
    // ODestinazioni objToDelete = odestinazioniService.findById(odestinazioni.getId());
    // try {
    // odestinazioniService.delete(objToDelete);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(odestinazioniService.getValidationMessages(), result, objToDelete, e.getMessage());
    // fixRenderEntityProperty(odestinazioni);
    // return "odestinazioni/form";
    // }
    // status.setComplete();
    // return "redirect:list.htm";
    // }

    @Override
    protected void fixMergeEntityProperty(ODestinazioni entity) {

    }

    @Override
    protected void fixRenderEntityProperty(ODestinazioni entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
