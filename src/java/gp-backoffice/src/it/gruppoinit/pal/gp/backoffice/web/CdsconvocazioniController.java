package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.Cdsconvocazioni;
import it.gruppoinit.pal.gp.core.service.CdsconvocazioniService;
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
@SessionAttributes("cdsconvocazioni")
public class CdsconvocazioniController extends BaseController<Cdsconvocazioni> {

    @Autowired
    private CdsconvocazioniService cdsconvocazioniService;
    @Autowired
    private SoftwareService softwareService;

    // @RequestMapping
    // public ModelMap list(HttpServletRequest request, HttpServletResponse response) {
    //
    // List<Cdsconvocazioni> cdsconvocazioniList = cdsconvocazioniService.findAll(null, null);
    // ModelMap model = new ModelMap(cdsconvocazioniList);
    // boolean export = createJMesaExport(request, response, cdsconvocazioniList);
    // if (export) {
    // return null;
    // }
    // model.addAttribute("cdsconvocazioniList", cdsconvocazioniList);
    // return model;
    // }
    //
    // @RequestMapping
    // public String create(Model model) {
    //
    // Cdsconvocazioni cdsconvocazioni = new Cdsconvocazioni();
    // cdsconvocazioni.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // fixRenderEntityProperty(cdsconvocazioni);
    // model.addAttribute("cdsconvocazioni", cdsconvocazioni);
    // setPageAttributes(model);
    // return "cdsconvocazioni/form";
    // }
    //
    // @RequestMapping
    // public String insert(@ModelAttribute("cdsconvocazioni") Cdsconvocazioni cdsconvocazioni, BindingResult result,
    // SessionStatus status) {
    //
    // fixMergeEntityProperty(cdsconvocazioni);
    // cdsconvocazioni.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // try {
    // cdsconvocazioniService.insert(cdsconvocazioni);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(cdsconvocazioniService.getValidationMessages(), result, cdsconvocazioni,
    // e.getMessage());
    // fixRenderEntityProperty(cdsconvocazioni);
    // return "cdsconvocazioni/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + cdsconvocazioni.getId().getCodice() + "&status_msg=01";
    // }
    //
    // @RequestMapping
    // public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {
    //
    // PkId id = new PkId(codice);
    // Cdsconvocazioni cdsconvocazioni = cdsconvocazioniService.findById(id);
    // fixRenderEntityProperty(cdsconvocazioni);
    // model.addAttribute("cdsconvocazioni", cdsconvocazioni);
    // setPageAttributes(model);
    // return "cdsconvocazioni/form";
    // }
    //
    // @RequestMapping
    // public String update(@ModelAttribute("cdsconvocazioni") Cdsconvocazioni cdsconvocazioni, BindingResult result,
    // SessionStatus status,
    // HttpServletRequest request) {
    //
    // fixMergeEntityProperty(cdsconvocazioni);
    // try {
    // cdsconvocazioniService.update(cdsconvocazioni);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(cdsconvocazioniService.getValidationMessages(), result, cdsconvocazioni,
    // e.getMessage());
    // fixRenderEntityProperty(cdsconvocazioni);
    // return "cdsconvocazioni/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + cdsconvocazioni.getId().getCodice() + "&status_msg=02";
    // }
    //
    // @RequestMapping
    // public String delete(@ModelAttribute("cdsconvocazioni") Cdsconvocazioni cdsconvocazioni, BindingResult result,
    // SessionStatus status) {
    //
    // Cdsconvocazioni objToDelete = cdsconvocazioniService.findById(cdsconvocazioni.getId());
    // try {
    // cdsconvocazioniService.delete(objToDelete);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(cdsconvocazioniService.getValidationMessages(), result, objToDelete, e.getMessage());
    // fixRenderEntityProperty(cdsconvocazioni);
    // return "cdsconvocazioni/form";
    // }
    // status.setComplete();
    // return "redirect:list.htm";
    // }

    @Override
    protected void fixMergeEntityProperty(Cdsconvocazioni entity) {

    }

    @Override
    protected void fixRenderEntityProperty(Cdsconvocazioni entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
