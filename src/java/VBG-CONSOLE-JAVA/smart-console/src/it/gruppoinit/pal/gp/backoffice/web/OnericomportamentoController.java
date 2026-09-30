package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.Onericomportamento;
import it.gruppoinit.pal.gp.core.service.OnericomportamentoService;
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
@SessionAttributes("onericomportamento")
public class OnericomportamentoController extends BaseController<Onericomportamento> {

    @Autowired
    private OnericomportamentoService onericomportamentoService;
    @Autowired
    private SoftwareService softwareService;

    // @RequestMapping
    // public ModelMap list(HttpServletRequest request, HttpServletResponse response) {
    //
    // List<Onericomportamento> onericomportamentoList = onericomportamentoService.findAll(null, null);
    // ModelMap model = new ModelMap(onericomportamentoList);
    // boolean export = createJMesaExport(request, response, onericomportamentoList);
    // if (export) {
    // return null;
    // }
    // model.addAttribute("onericomportamentoList", onericomportamentoList);
    // return model;
    // }
    //
    // @RequestMapping
    // public String create(Model model) {
    //
    // Onericomportamento onericomportamento = new Onericomportamento();
    // onericomportamento.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // fixRenderEntityProperty(onericomportamento);
    // model.addAttribute("onericomportamento", onericomportamento);
    // setPageAttributes(model);
    // return "onericomportamento/form";
    // }
    //
    // @RequestMapping
    // public String insert(@ModelAttribute("onericomportamento") Onericomportamento onericomportamento, BindingResult
    // result, SessionStatus status) {
    //
    // fixMergeEntityProperty(onericomportamento);
    // onericomportamento.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // try {
    // onericomportamentoService.insert(onericomportamento);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(onericomportamentoService.getValidationMessages(), result, onericomportamento,
    // e.getMessage());
    // fixRenderEntityProperty(onericomportamento);
    // return "onericomportamento/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + onericomportamento.getId().getCodice() + "&status_msg=01";
    // }
    //
    // @RequestMapping
    // public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {
    //
    // PkId id = new PkId(codice);
    // Onericomportamento onericomportamento = onericomportamentoService.findById(id);
    // fixRenderEntityProperty(onericomportamento);
    // model.addAttribute("onericomportamento", onericomportamento);
    // setPageAttributes(model);
    // return "onericomportamento/form";
    // }
    //
    // @RequestMapping
    // public String update(@ModelAttribute("onericomportamento") Onericomportamento onericomportamento, BindingResult
    // result, SessionStatus status,
    // HttpServletRequest request) {
    //
    // fixMergeEntityProperty(onericomportamento);
    // try {
    // onericomportamentoService.update(onericomportamento);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(onericomportamentoService.getValidationMessages(), result, onericomportamento,
    // e.getMessage());
    // fixRenderEntityProperty(onericomportamento);
    // return "onericomportamento/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + onericomportamento.getId().getCodice() + "&status_msg=02";
    // }
    //
    // @RequestMapping
    // public String delete(@ModelAttribute("onericomportamento") Onericomportamento onericomportamento, BindingResult
    // result, SessionStatus status) {
    //
    // Onericomportamento objToDelete = onericomportamentoService.findById(onericomportamento.getId());
    // try {
    // onericomportamentoService.delete(objToDelete);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(onericomportamentoService.getValidationMessages(), result, objToDelete,
    // e.getMessage());
    // fixRenderEntityProperty(onericomportamento);
    // return "onericomportamento/form";
    // }
    // status.setComplete();
    // return "redirect:list.htm";
    // }

    @Override
    protected void fixMergeEntityProperty(Onericomportamento entity) {

    }

    @Override
    protected void fixRenderEntityProperty(Onericomportamento entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
