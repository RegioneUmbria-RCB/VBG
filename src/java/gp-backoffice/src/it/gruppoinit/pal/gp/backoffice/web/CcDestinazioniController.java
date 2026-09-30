package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.CcDestinazioni;
import it.gruppoinit.pal.gp.core.service.CcDestinazioniService;
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
@SessionAttributes("ccdestinazioni")
public class CcDestinazioniController extends BaseController<CcDestinazioni> {

    @Autowired
    private CcDestinazioniService ccdestinazioniService;
    @Autowired
    private SoftwareService softwareService;

    // @RequestMapping
    // public ModelMap list(HttpServletRequest request, HttpServletResponse response) {
    //
    // List<CcDestinazioni> ccdestinazioniList = ccdestinazioniService.findAll(null, null);
    // ModelMap model = new ModelMap(ccdestinazioniList);
    // boolean export = createJMesaExport(request, response, ccdestinazioniList);
    // if (export) {
    // return null;
    // }
    // model.addAttribute("ccdestinazioniList", ccdestinazioniList);
    // return model;
    // }
    //
    // @RequestMapping
    // public String create(Model model) {
    //
    // CcDestinazioni ccdestinazioni = new CcDestinazioni();
    // ccdestinazioni.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // fixRenderEntityProperty(ccdestinazioni);
    // model.addAttribute("ccdestinazioni", ccdestinazioni);
    // setPageAttributes(model);
    // return "ccdestinazioni/form";
    // }
    //
    // @RequestMapping
    // public String insert(@ModelAttribute("ccdestinazioni") CcDestinazioni ccdestinazioni, BindingResult result,
    // SessionStatus status) {
    //
    // fixMergeEntityProperty(ccdestinazioni);
    // ccdestinazioni.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // try {
    // ccdestinazioniService.insert(ccdestinazioni);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(ccdestinazioniService.getValidationMessages(), result, ccdestinazioni, e.getMessage());
    // fixRenderEntityProperty(ccdestinazioni);
    // return "ccdestinazioni/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + ccdestinazioni.getId().getCodice() + "&status_msg=01";
    // }
    //
    // @RequestMapping
    // public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {
    //
    // PkId id = new PkId(codice);
    // CcDestinazioni ccdestinazioni = ccdestinazioniService.findById(id);
    // fixRenderEntityProperty(ccdestinazioni);
    // model.addAttribute("ccdestinazioni", ccdestinazioni);
    // setPageAttributes(model);
    // return "ccdestinazioni/form";
    // }
    //
    // @RequestMapping
    // public String update(@ModelAttribute("ccdestinazioni") CcDestinazioni ccdestinazioni, BindingResult result,
    // SessionStatus status,
    // HttpServletRequest request) {
    //
    // fixMergeEntityProperty(ccdestinazioni);
    // try {
    // ccdestinazioniService.update(ccdestinazioni);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(ccdestinazioniService.getValidationMessages(), result, ccdestinazioni, e.getMessage());
    // fixRenderEntityProperty(ccdestinazioni);
    // return "ccdestinazioni/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + ccdestinazioni.getId().getCodice() + "&status_msg=02";
    // }
    //
    // @RequestMapping
    // public String delete(@ModelAttribute("ccdestinazioni") CcDestinazioni ccdestinazioni, BindingResult result,
    // SessionStatus status) {
    //
    // CcDestinazioni objToDelete = ccdestinazioniService.findById(ccdestinazioni.getId());
    // try {
    // ccdestinazioniService.delete(objToDelete);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(ccdestinazioniService.getValidationMessages(), result, objToDelete, e.getMessage());
    // fixRenderEntityProperty(ccdestinazioni);
    // return "ccdestinazioni/form";
    // }
    // status.setComplete();
    // return "redirect:list.htm";
    // }

    @Override
    protected void fixMergeEntityProperty(CcDestinazioni entity) {

    }

    @Override
    protected void fixRenderEntityProperty(CcDestinazioni entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
