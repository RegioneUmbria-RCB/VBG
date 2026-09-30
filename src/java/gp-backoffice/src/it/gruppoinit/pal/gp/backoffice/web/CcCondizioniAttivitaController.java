package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.CcCondizioniAttivita;
import it.gruppoinit.pal.gp.core.service.CcCondizioniAttivitaService;
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
@SessionAttributes("cccondizioniattivita")
public class CcCondizioniAttivitaController extends BaseController<CcCondizioniAttivita> {

    @Autowired
    private CcCondizioniAttivitaService cccondizioniattivitaService;
    @Autowired
    private SoftwareService softwareService;

    // @RequestMapping
    // public ModelMap list(HttpServletRequest request, HttpServletResponse response) {
    //
    // List<CcCondizioniAttivita> cccondizioniattivitaList = cccondizioniattivitaService.findAll(null, null);
    // ModelMap model = new ModelMap(cccondizioniattivitaList);
    // boolean export = createJMesaExport(request, response, cccondizioniattivitaList);
    // if (export) {
    // return null;
    // }
    // model.addAttribute("cccondizioniattivitaList", cccondizioniattivitaList);
    // return model;
    // }
    //
    // @RequestMapping
    // public String create(Model model) {
    //
    // CcCondizioniAttivita cccondizioniattivita = new CcCondizioniAttivita();
    // cccondizioniattivita.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // fixRenderEntityProperty(cccondizioniattivita);
    // model.addAttribute("cccondizioniattivita", cccondizioniattivita);
    // setPageAttributes(model);
    // return "cccondizioniattivita/form";
    // }
    //
    // @RequestMapping
    // public String insert(@ModelAttribute("cccondizioniattivita") CcCondizioniAttivita cccondizioniattivita,
    // BindingResult result, SessionStatus status) {
    //
    // fixMergeEntityProperty(cccondizioniattivita);
    // cccondizioniattivita.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // try {
    // cccondizioniattivitaService.insert(cccondizioniattivita);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(cccondizioniattivitaService.getValidationMessages(), result, cccondizioniattivita,
    // e.getMessage());
    // fixRenderEntityProperty(cccondizioniattivita);
    // return "cccondizioniattivita/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + cccondizioniattivita.getId().getCodice() + "&status_msg=01";
    // }
    //
    // @RequestMapping
    // public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {
    //
    // PkId id = new PkId(codice);
    // CcCondizioniAttivita cccondizioniattivita = cccondizioniattivitaService.findById(id);
    // fixRenderEntityProperty(cccondizioniattivita);
    // model.addAttribute("cccondizioniattivita", cccondizioniattivita);
    // setPageAttributes(model);
    // return "cccondizioniattivita/form";
    // }
    //
    // @RequestMapping
    // public String update(@ModelAttribute("cccondizioniattivita") CcCondizioniAttivita cccondizioniattivita,
    // BindingResult result,
    // SessionStatus status, HttpServletRequest request) {
    //
    // fixMergeEntityProperty(cccondizioniattivita);
    // try {
    // cccondizioniattivitaService.update(cccondizioniattivita);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(cccondizioniattivitaService.getValidationMessages(), result, cccondizioniattivita,
    // e.getMessage());
    // fixRenderEntityProperty(cccondizioniattivita);
    // return "cccondizioniattivita/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + cccondizioniattivita.getId().getCodice() + "&status_msg=02";
    // }
    //
    // @RequestMapping
    // public String delete(@ModelAttribute("cccondizioniattivita") CcCondizioniAttivita cccondizioniattivita,
    // BindingResult result, SessionStatus status) {
    //
    // CcCondizioniAttivita objToDelete = cccondizioniattivitaService.findById(cccondizioniattivita.getId());
    // try {
    // cccondizioniattivitaService.delete(objToDelete);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(cccondizioniattivitaService.getValidationMessages(), result, objToDelete,
    // e.getMessage());
    // fixRenderEntityProperty(cccondizioniattivita);
    // return "cccondizioniattivita/form";
    // }
    // status.setComplete();
    // return "redirect:list.htm";
    // }

    @Override
    protected void fixMergeEntityProperty(CcCondizioniAttivita entity) {

    }

    @Override
    protected void fixRenderEntityProperty(CcCondizioniAttivita entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
