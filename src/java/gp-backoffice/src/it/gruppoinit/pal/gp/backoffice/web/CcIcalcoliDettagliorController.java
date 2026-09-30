package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.CcIcalcoliDettaglior;
import it.gruppoinit.pal.gp.core.service.CcIcalcoliDettagliorService;
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
@SessionAttributes("ccicalcolidettaglior")
public class CcIcalcoliDettagliorController extends BaseController<CcIcalcoliDettaglior> {

    @Autowired
    private CcIcalcoliDettagliorService ccicalcolidettagliorService;
    @Autowired
    private SoftwareService softwareService;

    // @RequestMapping
    // public ModelMap list(HttpServletRequest request, HttpServletResponse response) {
    //
    // List<CcIcalcoliDettaglior> ccicalcolidettagliorList = ccicalcolidettagliorService.findAll(null, null);
    // ModelMap model = new ModelMap(ccicalcolidettagliorList);
    // boolean export = createJMesaExport(request, response, ccicalcolidettagliorList);
    // if (export) {
    // return null;
    // }
    // model.addAttribute("ccicalcolidettagliorList", ccicalcolidettagliorList);
    // return model;
    // }
    //
    // @RequestMapping
    // public String create(Model model) {
    //
    // CcIcalcoliDettaglior ccicalcolidettaglior = new CcIcalcoliDettaglior();
    // ccicalcolidettaglior.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // fixRenderEntityProperty(ccicalcolidettaglior);
    // model.addAttribute("ccicalcolidettaglior", ccicalcolidettaglior);
    // setPageAttributes(model);
    // return "ccicalcolidettaglior/form";
    // }
    //
    // @RequestMapping
    // public String insert(@ModelAttribute("ccicalcolidettaglior") CcIcalcoliDettaglior ccicalcolidettaglior,
    // BindingResult result, SessionStatus status) {
    //
    // fixMergeEntityProperty(ccicalcolidettaglior);
    // ccicalcolidettaglior.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // try {
    // ccicalcolidettagliorService.insert(ccicalcolidettaglior);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(ccicalcolidettagliorService.getValidationMessages(), result, ccicalcolidettaglior,
    // e.getMessage());
    // fixRenderEntityProperty(ccicalcolidettaglior);
    // return "ccicalcolidettaglior/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + ccicalcolidettaglior.getId().getCodice() + "&status_msg=01";
    // }
    //
    // @RequestMapping
    // public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {
    //
    // PkId id = new PkId(codice);
    // CcIcalcoliDettaglior ccicalcolidettaglior = ccicalcolidettagliorService.findById(id);
    // fixRenderEntityProperty(ccicalcolidettaglior);
    // model.addAttribute("ccicalcolidettaglior", ccicalcolidettaglior);
    // setPageAttributes(model);
    // return "ccicalcolidettaglior/form";
    // }
    //
    // @RequestMapping
    // public String update(@ModelAttribute("ccicalcolidettaglior") CcIcalcoliDettaglior ccicalcolidettaglior,
    // BindingResult result,
    // SessionStatus status, HttpServletRequest request) {
    //
    // fixMergeEntityProperty(ccicalcolidettaglior);
    // try {
    // ccicalcolidettagliorService.update(ccicalcolidettaglior);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(ccicalcolidettagliorService.getValidationMessages(), result, ccicalcolidettaglior,
    // e.getMessage());
    // fixRenderEntityProperty(ccicalcolidettaglior);
    // return "ccicalcolidettaglior/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + ccicalcolidettaglior.getId().getCodice() + "&status_msg=02";
    // }
    //
    // @RequestMapping
    // public String delete(@ModelAttribute("ccicalcolidettaglior") CcIcalcoliDettaglior ccicalcolidettaglior,
    // BindingResult result, SessionStatus status) {
    //
    // CcIcalcoliDettaglior objToDelete = ccicalcolidettagliorService.findById(ccicalcolidettaglior.getId());
    // try {
    // ccicalcolidettagliorService.delete(objToDelete);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(ccicalcolidettagliorService.getValidationMessages(), result, objToDelete,
    // e.getMessage());
    // fixRenderEntityProperty(ccicalcolidettaglior);
    // return "ccicalcolidettaglior/form";
    // }
    // status.setComplete();
    // return "redirect:list.htm";
    // }

    @Override
    protected void fixMergeEntityProperty(CcIcalcoliDettaglior entity) {

    }

    @Override
    protected void fixRenderEntityProperty(CcIcalcoliDettaglior entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
