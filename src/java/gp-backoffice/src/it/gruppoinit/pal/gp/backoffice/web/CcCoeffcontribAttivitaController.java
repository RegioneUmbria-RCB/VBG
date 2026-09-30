package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.CcCoeffcontribAttivita;
import it.gruppoinit.pal.gp.core.service.CcCoeffcontribAttivitaService;
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
@SessionAttributes("cccoeffcontribattivita")
public class CcCoeffcontribAttivitaController extends BaseController<CcCoeffcontribAttivita> {

    @Autowired
    private CcCoeffcontribAttivitaService cccoeffcontribattivitaService;
    @Autowired
    private SoftwareService softwareService;

    // @RequestMapping
    // public ModelMap list(HttpServletRequest request, HttpServletResponse response) {
    //
    // List<CcCoeffcontribAttivita> cccoeffcontribattivitaList = cccoeffcontribattivitaService.findAll(null, null);
    // ModelMap model = new ModelMap(cccoeffcontribattivitaList);
    // boolean export = createJMesaExport(request, response, cccoeffcontribattivitaList);
    // if (export) {
    // return null;
    // }
    // model.addAttribute("cccoeffcontribattivitaList", cccoeffcontribattivitaList);
    // return model;
    // }
    //
    // @RequestMapping
    // public String create(Model model) {
    //
    // CcCoeffcontribAttivita cccoeffcontribattivita = new CcCoeffcontribAttivita();
    // cccoeffcontribattivita.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // fixRenderEntityProperty(cccoeffcontribattivita);
    // model.addAttribute("cccoeffcontribattivita", cccoeffcontribattivita);
    // setPageAttributes(model);
    // return "cccoeffcontribattivita/form";
    // }
    //
    // @RequestMapping
    // public String insert(@ModelAttribute("cccoeffcontribattivita") CcCoeffcontribAttivita cccoeffcontribattivita,
    // BindingResult result,
    // SessionStatus status) {
    //
    // fixMergeEntityProperty(cccoeffcontribattivita);
    // cccoeffcontribattivita.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // try {
    // cccoeffcontribattivitaService.insert(cccoeffcontribattivita);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(cccoeffcontribattivitaService.getValidationMessages(), result, cccoeffcontribattivita,
    // e.getMessage());
    // fixRenderEntityProperty(cccoeffcontribattivita);
    // return "cccoeffcontribattivita/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + cccoeffcontribattivita.getId().getCodice() + "&status_msg=01";
    // }
    //
    // @RequestMapping
    // public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {
    //
    // PkId id = new PkId(codice);
    // CcCoeffcontribAttivita cccoeffcontribattivita = cccoeffcontribattivitaService.findById(id);
    // fixRenderEntityProperty(cccoeffcontribattivita);
    // model.addAttribute("cccoeffcontribattivita", cccoeffcontribattivita);
    // setPageAttributes(model);
    // return "cccoeffcontribattivita/form";
    // }
    //
    // @RequestMapping
    // public String update(@ModelAttribute("cccoeffcontribattivita") CcCoeffcontribAttivita cccoeffcontribattivita,
    // BindingResult result,
    // SessionStatus status, HttpServletRequest request) {
    //
    // fixMergeEntityProperty(cccoeffcontribattivita);
    // try {
    // cccoeffcontribattivitaService.update(cccoeffcontribattivita);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(cccoeffcontribattivitaService.getValidationMessages(), result, cccoeffcontribattivita,
    // e.getMessage());
    // fixRenderEntityProperty(cccoeffcontribattivita);
    // return "cccoeffcontribattivita/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + cccoeffcontribattivita.getId().getCodice() + "&status_msg=02";
    // }
    //
    // @RequestMapping
    // public String delete(@ModelAttribute("cccoeffcontribattivita") CcCoeffcontribAttivita cccoeffcontribattivita,
    // BindingResult result,
    // SessionStatus status) {
    //
    // CcCoeffcontribAttivita objToDelete = cccoeffcontribattivitaService.findById(cccoeffcontribattivita.getId());
    // try {
    // cccoeffcontribattivitaService.delete(objToDelete);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(cccoeffcontribattivitaService.getValidationMessages(), result, objToDelete,
    // e.getMessage());
    // fixRenderEntityProperty(cccoeffcontribattivita);
    // return "cccoeffcontribattivita/form";
    // }
    // status.setComplete();
    // return "redirect:list.htm";
    // }

    @Override
    protected void fixMergeEntityProperty(CcCoeffcontribAttivita entity) {

    }

    @Override
    protected void fixRenderEntityProperty(CcCoeffcontribAttivita entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
