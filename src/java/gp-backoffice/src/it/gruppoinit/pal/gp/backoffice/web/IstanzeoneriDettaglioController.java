package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.IstanzeoneriDettaglio;
import it.gruppoinit.pal.gp.core.service.IstanzeoneriDettaglioService;
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
@SessionAttributes("istanzeoneridettaglio")
public class IstanzeoneriDettaglioController extends BaseController<IstanzeoneriDettaglio> {

    @Autowired
    private IstanzeoneriDettaglioService istanzeoneridettaglioService;
    @Autowired
    private SoftwareService softwareService;

    //
    // @RequestMapping
    // public ModelMap list(HttpServletRequest request, HttpServletResponse response) {
    //
    // List<IstanzeoneriDettaglio> istanzeoneridettaglioList = istanzeoneridettaglioService.findAll(null, null);
    // ModelMap model = new ModelMap(istanzeoneridettaglioList);
    // boolean export = createJMesaExport(request, response, istanzeoneridettaglioList);
    // if (export) {
    // return null;
    // }
    // model.addAttribute("istanzeoneridettaglioList", istanzeoneridettaglioList);
    // return model;
    // }
    //
    // @RequestMapping
    // public String create(Model model) {
    //
    // IstanzeoneriDettaglio istanzeoneridettaglio = new IstanzeoneriDettaglio();
    // istanzeoneridettaglio.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // fixRenderEntityProperty(istanzeoneridettaglio);
    // model.addAttribute("istanzeoneridettaglio", istanzeoneridettaglio);
    // setPageAttributes(model);
    // return "istanzeoneridettaglio/form";
    // }
    //
    // @RequestMapping
    // public String insert(@ModelAttribute("istanzeoneridettaglio") IstanzeoneriDettaglio istanzeoneridettaglio,
    // BindingResult result,
    // SessionStatus status) {
    //
    // fixMergeEntityProperty(istanzeoneridettaglio);
    // istanzeoneridettaglio.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // try {
    // istanzeoneridettaglioService.insert(istanzeoneridettaglio);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(istanzeoneridettaglioService.getValidationMessages(), result, istanzeoneridettaglio,
    // e.getMessage());
    // fixRenderEntityProperty(istanzeoneridettaglio);
    // return "istanzeoneridettaglio/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + istanzeoneridettaglio.getId().getCodice() + "&status_msg=01";
    // }
    //
    // @RequestMapping
    // public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {
    //
    // PkId id = new PkId(codice);
    // IstanzeoneriDettaglio istanzeoneridettaglio = istanzeoneridettaglioService.findById(id);
    // fixRenderEntityProperty(istanzeoneridettaglio);
    // model.addAttribute("istanzeoneridettaglio", istanzeoneridettaglio);
    // setPageAttributes(model);
    // return "istanzeoneridettaglio/form";
    // }
    //
    // @RequestMapping
    // public String update(@ModelAttribute("istanzeoneridettaglio") IstanzeoneriDettaglio istanzeoneridettaglio,
    // BindingResult result,
    // SessionStatus status, HttpServletRequest request) {
    //
    // fixMergeEntityProperty(istanzeoneridettaglio);
    // try {
    // istanzeoneridettaglioService.update(istanzeoneridettaglio);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(istanzeoneridettaglioService.getValidationMessages(), result, istanzeoneridettaglio,
    // e.getMessage());
    // fixRenderEntityProperty(istanzeoneridettaglio);
    // return "istanzeoneridettaglio/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + istanzeoneridettaglio.getId().getCodice() + "&status_msg=02";
    // }
    //
    // @RequestMapping
    // public String delete(@ModelAttribute("istanzeoneridettaglio") IstanzeoneriDettaglio istanzeoneridettaglio,
    // BindingResult result,
    // SessionStatus status) {
    //
    // IstanzeoneriDettaglio objToDelete = istanzeoneridettaglioService.findById(istanzeoneridettaglio.getId());
    // try {
    // istanzeoneridettaglioService.delete(objToDelete);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(istanzeoneridettaglioService.getValidationMessages(), result, objToDelete,
    // e.getMessage());
    // fixRenderEntityProperty(istanzeoneridettaglio);
    // return "istanzeoneridettaglio/form";
    // }
    // status.setComplete();
    // return "redirect:list.htm";
    // }

    @Override
    protected void fixMergeEntityProperty(IstanzeoneriDettaglio entity) {

    }

    @Override
    protected void fixRenderEntityProperty(IstanzeoneriDettaglio entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
