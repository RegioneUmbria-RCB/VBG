package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.IstanzecalcolocanoniT;
import it.gruppoinit.pal.gp.core.features.oneri.calcolo.canoni.IstanzecalcolocanoniTService;
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
@SessionAttributes("istanzecalcolocanonit")
public class IstanzecalcolocanoniTController extends BaseController<IstanzecalcolocanoniT> {

    @Autowired
    private IstanzecalcolocanoniTService istanzecalcolocanonitService;
    @Autowired
    private SoftwareService softwareService;

    //
    // @RequestMapping
    // public ModelMap list(HttpServletRequest request, HttpServletResponse response) {
    //
    // List<IstanzecalcolocanoniT> istanzecalcolocanonitList = istanzecalcolocanonitService.findAll(null, null);
    // ModelMap model = new ModelMap(istanzecalcolocanonitList);
    // boolean export = createJMesaExport(request, response, istanzecalcolocanonitList);
    // if (export) {
    // return null;
    // }
    // model.addAttribute("istanzecalcolocanonitList", istanzecalcolocanonitList);
    // return model;
    // }
    //
    // @RequestMapping
    // public String create(Model model) {
    //
    // IstanzecalcolocanoniT istanzecalcolocanonit = new IstanzecalcolocanoniT();
    // istanzecalcolocanonit.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // fixRenderEntityProperty(istanzecalcolocanonit);
    // model.addAttribute("istanzecalcolocanonit", istanzecalcolocanonit);
    // setPageAttributes(model);
    // return "istanzecalcolocanonit/form";
    // }
    //
    // @RequestMapping
    // public String insert(@ModelAttribute("istanzecalcolocanonit") IstanzecalcolocanoniT istanzecalcolocanonit,
    // BindingResult result,
    // SessionStatus status) {
    //
    // fixMergeEntityProperty(istanzecalcolocanonit);
    // istanzecalcolocanonit.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // try {
    // istanzecalcolocanonitService.insert(istanzecalcolocanonit);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(istanzecalcolocanonitService.getValidationMessages(), result, istanzecalcolocanonit,
    // e.getMessage());
    // fixRenderEntityProperty(istanzecalcolocanonit);
    // return "istanzecalcolocanonit/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + istanzecalcolocanonit.getId().getCodice() + "&status_msg=01";
    // }
    //
    // @RequestMapping
    // public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {
    //
    // PkId id = new PkId(codice);
    // IstanzecalcolocanoniT istanzecalcolocanonit = istanzecalcolocanonitService.findById(id);
    // fixRenderEntityProperty(istanzecalcolocanonit);
    // model.addAttribute("istanzecalcolocanonit", istanzecalcolocanonit);
    // setPageAttributes(model);
    // return "istanzecalcolocanonit/form";
    // }
    //
    // @RequestMapping
    // public String update(@ModelAttribute("istanzecalcolocanonit") IstanzecalcolocanoniT istanzecalcolocanonit,
    // BindingResult result,
    // SessionStatus status, HttpServletRequest request) {
    //
    // fixMergeEntityProperty(istanzecalcolocanonit);
    // try {
    // istanzecalcolocanonitService.update(istanzecalcolocanonit);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(istanzecalcolocanonitService.getValidationMessages(), result, istanzecalcolocanonit,
    // e.getMessage());
    // fixRenderEntityProperty(istanzecalcolocanonit);
    // return "istanzecalcolocanonit/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + istanzecalcolocanonit.getId().getCodice() + "&status_msg=02";
    // }
    //
    // @RequestMapping
    // public String delete(@ModelAttribute("istanzecalcolocanonit") IstanzecalcolocanoniT istanzecalcolocanonit,
    // BindingResult result,
    // SessionStatus status) {
    //
    // IstanzecalcolocanoniT objToDelete = istanzecalcolocanonitService.findById(istanzecalcolocanonit.getId());
    // try {
    // istanzecalcolocanonitService.delete(objToDelete);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(istanzecalcolocanonitService.getValidationMessages(), result, objToDelete,
    // e.getMessage());
    // fixRenderEntityProperty(istanzecalcolocanonit);
    // return "istanzecalcolocanonit/form";
    // }
    // status.setComplete();
    // return "redirect:list.htm";
    // }
    @Override
    protected void fixMergeEntityProperty(IstanzecalcolocanoniT entity) {

    }

    @Override
    protected void fixRenderEntityProperty(IstanzecalcolocanoniT entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
