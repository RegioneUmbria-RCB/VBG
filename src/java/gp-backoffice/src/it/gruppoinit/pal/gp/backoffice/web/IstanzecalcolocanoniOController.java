package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.IstanzecalcolocanoniO;
import it.gruppoinit.pal.gp.core.features.oneri.calcolo.canoni.IstanzecalcolocanoniOService;
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
@SessionAttributes("istanzecalcolocanonio")
public class IstanzecalcolocanoniOController extends BaseController<IstanzecalcolocanoniO> {

    @Autowired
    private IstanzecalcolocanoniOService istanzecalcolocanonioService;
    @Autowired
    private SoftwareService softwareService;

    //
    // @RequestMapping
    // public ModelMap list(HttpServletRequest request, HttpServletResponse response) {
    //
    // List<IstanzecalcolocanoniO> istanzecalcolocanonioList = istanzecalcolocanonioService.findAll(null, null);
    // ModelMap model = new ModelMap(istanzecalcolocanonioList);
    // boolean export = createJMesaExport(request, response, istanzecalcolocanonioList);
    // if (export) {
    // return null;
    // }
    // model.addAttribute("istanzecalcolocanonioList", istanzecalcolocanonioList);
    // return model;
    // }
    //
    // @RequestMapping
    // public String create(Model model) {
    //
    // IstanzecalcolocanoniO istanzecalcolocanonio = new IstanzecalcolocanoniO();
    // istanzecalcolocanonio.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // fixRenderEntityProperty(istanzecalcolocanonio);
    // model.addAttribute("istanzecalcolocanonio", istanzecalcolocanonio);
    // setPageAttributes(model);
    // return "istanzecalcolocanonio/form";
    // }
    //
    // @RequestMapping
    // public String insert(@ModelAttribute("istanzecalcolocanonio") IstanzecalcolocanoniO istanzecalcolocanonio,
    // BindingResult result,
    // SessionStatus status) {
    //
    // fixMergeEntityProperty(istanzecalcolocanonio);
    // istanzecalcolocanonio.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // try {
    // istanzecalcolocanonioService.insert(istanzecalcolocanonio);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(istanzecalcolocanonioService.getValidationMessages(), result, istanzecalcolocanonio,
    // e.getMessage());
    // fixRenderEntityProperty(istanzecalcolocanonio);
    // return "istanzecalcolocanonio/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + istanzecalcolocanonio.getId().getCodice() + "&status_msg=01";
    // }
    //
    // @RequestMapping
    // public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {
    //
    // PkId id = new PkId(codice);
    // IstanzecalcolocanoniO istanzecalcolocanonio = istanzecalcolocanonioService.findById(id);
    // fixRenderEntityProperty(istanzecalcolocanonio);
    // model.addAttribute("istanzecalcolocanonio", istanzecalcolocanonio);
    // setPageAttributes(model);
    // return "istanzecalcolocanonio/form";
    // }
    //
    // @RequestMapping
    // public String update(@ModelAttribute("istanzecalcolocanonio") IstanzecalcolocanoniO istanzecalcolocanonio,
    // BindingResult result,
    // SessionStatus status, HttpServletRequest request) {
    //
    // fixMergeEntityProperty(istanzecalcolocanonio);
    // try {
    // istanzecalcolocanonioService.update(istanzecalcolocanonio);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(istanzecalcolocanonioService.getValidationMessages(), result, istanzecalcolocanonio,
    // e.getMessage());
    // fixRenderEntityProperty(istanzecalcolocanonio);
    // return "istanzecalcolocanonio/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + istanzecalcolocanonio.getId().getCodice() + "&status_msg=02";
    // }
    //
    // @RequestMapping
    // public String delete(@ModelAttribute("istanzecalcolocanonio") IstanzecalcolocanoniO istanzecalcolocanonio,
    // BindingResult result,
    // SessionStatus status) {
    //
    // IstanzecalcolocanoniO objToDelete = istanzecalcolocanonioService.findById(istanzecalcolocanonio.getId());
    // try {
    // istanzecalcolocanonioService.delete(objToDelete);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(istanzecalcolocanonioService.getValidationMessages(), result, objToDelete,
    // e.getMessage());
    // fixRenderEntityProperty(istanzecalcolocanonio);
    // return "istanzecalcolocanonio/form";
    // }
    // status.setComplete();
    // return "redirect:list.htm";
    // }

    @Override
    protected void fixMergeEntityProperty(IstanzecalcolocanoniO entity) {

    }

    @Override
    protected void fixRenderEntityProperty(IstanzecalcolocanoniO entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
