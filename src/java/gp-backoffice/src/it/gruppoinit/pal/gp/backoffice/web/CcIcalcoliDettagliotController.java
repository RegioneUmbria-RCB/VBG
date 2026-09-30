package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.CcIcalcoliDettagliot;
import it.gruppoinit.pal.gp.core.service.CcIcalcoliDettagliotService;
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
@SessionAttributes("ccicalcolidettagliot")
public class CcIcalcoliDettagliotController extends BaseController<CcIcalcoliDettagliot> {

    @Autowired
    private CcIcalcoliDettagliotService ccicalcolidettagliotService;
    @Autowired
    private SoftwareService softwareService;

    // @RequestMapping
    // public ModelMap list(HttpServletRequest request, HttpServletResponse response) {
    //
    // List<CcIcalcoliDettagliot> ccicalcolidettagliotList = ccicalcolidettagliotService.findAll(null, null);
    // ModelMap model = new ModelMap(ccicalcolidettagliotList);
    // boolean export = createJMesaExport(request, response, ccicalcolidettagliotList);
    // if (export) {
    // return null;
    // }
    // model.addAttribute("ccicalcolidettagliotList", ccicalcolidettagliotList);
    // return model;
    // }
    //
    // @RequestMapping
    // public String create(Model model) {
    //
    // CcIcalcoliDettagliot ccicalcolidettagliot = new CcIcalcoliDettagliot();
    // ccicalcolidettagliot.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // fixRenderEntityProperty(ccicalcolidettagliot);
    // model.addAttribute("ccicalcolidettagliot", ccicalcolidettagliot);
    // setPageAttributes(model);
    // return "ccicalcolidettagliot/form";
    // }
    //
    // @RequestMapping
    // public String insert(@ModelAttribute("ccicalcolidettagliot") CcIcalcoliDettagliot ccicalcolidettagliot,
    // BindingResult result, SessionStatus status) {
    //
    // fixMergeEntityProperty(ccicalcolidettagliot);
    // ccicalcolidettagliot.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // try {
    // ccicalcolidettagliotService.insert(ccicalcolidettagliot);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(ccicalcolidettagliotService.getValidationMessages(), result, ccicalcolidettagliot,
    // e.getMessage());
    // fixRenderEntityProperty(ccicalcolidettagliot);
    // return "ccicalcolidettagliot/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + ccicalcolidettagliot.getId().getCodice() + "&status_msg=01";
    // }
    //
    // @RequestMapping
    // public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {
    //
    // PkId id = new PkId(codice);
    // CcIcalcoliDettagliot ccicalcolidettagliot = ccicalcolidettagliotService.findById(id);
    // fixRenderEntityProperty(ccicalcolidettagliot);
    // model.addAttribute("ccicalcolidettagliot", ccicalcolidettagliot);
    // setPageAttributes(model);
    // return "ccicalcolidettagliot/form";
    // }
    //
    // @RequestMapping
    // public String update(@ModelAttribute("ccicalcolidettagliot") CcIcalcoliDettagliot ccicalcolidettagliot,
    // BindingResult result,
    // SessionStatus status, HttpServletRequest request) {
    //
    // fixMergeEntityProperty(ccicalcolidettagliot);
    // try {
    // ccicalcolidettagliotService.update(ccicalcolidettagliot);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(ccicalcolidettagliotService.getValidationMessages(), result, ccicalcolidettagliot,
    // e.getMessage());
    // fixRenderEntityProperty(ccicalcolidettagliot);
    // return "ccicalcolidettagliot/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + ccicalcolidettagliot.getId().getCodice() + "&status_msg=02";
    // }
    //
    // @RequestMapping
    // public String delete(@ModelAttribute("ccicalcolidettagliot") CcIcalcoliDettagliot ccicalcolidettagliot,
    // BindingResult result, SessionStatus status) {
    //
    // CcIcalcoliDettagliot objToDelete = ccicalcolidettagliotService.findById(ccicalcolidettagliot.getId());
    // try {
    // ccicalcolidettagliotService.delete(objToDelete);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(ccicalcolidettagliotService.getValidationMessages(), result, objToDelete,
    // e.getMessage());
    // fixRenderEntityProperty(ccicalcolidettagliot);
    // return "ccicalcolidettagliot/form";
    // }
    // status.setComplete();
    // return "redirect:list.htm";
    // }

    @Override
    protected void fixMergeEntityProperty(CcIcalcoliDettagliot entity) {

    }

    @Override
    protected void fixRenderEntityProperty(CcIcalcoliDettagliot entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
