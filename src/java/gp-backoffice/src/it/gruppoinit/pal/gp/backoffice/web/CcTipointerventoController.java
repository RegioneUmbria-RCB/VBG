package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.CcTipointervento;
import it.gruppoinit.pal.gp.core.domain.OccBasetipointervento;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.CcTipointerventoService;
import it.gruppoinit.pal.gp.core.service.OccBasetipointerventoService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

/**
 * 
 * @author
 */
@Controller
@SessionAttributes("cctipointervento")
public class CcTipointerventoController extends BaseController<CcTipointervento> {

    @Autowired
    private CcTipointerventoService cctipointerventoService;
    @Autowired
    private SoftwareService softwareService;
    @Autowired
    private OccBasetipointerventoService occBasetipointerventoService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	List<CcTipointervento> cctipointerventoList = cctipointerventoService.findAll(null, null);
	ModelMap model = new ModelMap(cctipointerventoList);
	boolean export = createJMesaExport(request, response, cctipointerventoList);
	if (export) {
	    return null;
	}
	model.addAttribute("cctipointerventoList", cctipointerventoList);
	return model;
    }

    @RequestMapping
    public String create(Model model) {

	CcTipointervento cctipointervento = new CcTipointervento();
	cctipointervento.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
	fixRenderEntityProperty(cctipointervento);
	List<OccBasetipointervento> listInterventiBase = occBasetipointerventoService.findAll(null, null);
	model.addAttribute("listInterventiBase", listInterventiBase);
	model.addAttribute("cctipointervento", cctipointervento);
	setPageAttributes(model);
	return "cctipointervento/form";
    }

    @RequestMapping
    public String insert(Model model, @ModelAttribute("cctipointervento") CcTipointervento cctipointervento, BindingResult result,
	    SessionStatus status) {

	fixMergeEntityProperty(cctipointervento);
	cctipointervento.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
	if (cctipointervento.getOccBasetipointervento() != null && StringUtils.isNotBlank(cctipointervento.getOccBasetipointervento().getId())) {
	    OccBasetipointervento occBasetipointervento = occBasetipointerventoService.findById(cctipointervento.getOccBasetipointervento().getId());
	    cctipointervento.setOccBasetipointervento(occBasetipointervento);
	}
	try {
	    cctipointerventoService.insert(cctipointervento);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, cctipointervento, e);
	    fixRenderEntityProperty(cctipointervento);
	    List<OccBasetipointervento> listInterventiBase = occBasetipointerventoService.findAll(null, null);
	    model.addAttribute("listInterventiBase", listInterventiBase);
	    return "cctipointervento/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + cctipointervento.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	CcTipointervento cctipointervento = cctipointerventoService.findById(id);
	fixRenderEntityProperty(cctipointervento);
	model.addAttribute("cctipointervento", cctipointervento);
	List<OccBasetipointervento> listInterventiBase = occBasetipointerventoService.findAll(null, null);
	model.addAttribute("listInterventiBase", listInterventiBase);
	setPageAttributes(model);
	return "cctipointervento/form";
    }

    @RequestMapping
    public String update(Model model, @ModelAttribute("cctipointervento") CcTipointervento cctipointervento, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	fixMergeEntityProperty(cctipointervento);
	if (cctipointervento.getOccBasetipointervento() != null && StringUtils.isNotBlank(cctipointervento.getOccBasetipointervento().getId())) {
	    OccBasetipointervento occBasetipointervento = occBasetipointerventoService.findById(cctipointervento.getOccBasetipointervento().getId());
	    cctipointervento.setOccBasetipointervento(occBasetipointervento);
	}
	try {
	    cctipointerventoService.update(cctipointervento);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, cctipointervento, e);
	    fixRenderEntityProperty(cctipointervento);
	    List<OccBasetipointervento> listInterventiBase = occBasetipointerventoService.findAll(null, null);
	    model.addAttribute("listInterventiBase", listInterventiBase);
	    return "cctipointervento/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + cctipointervento.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String delete(Model model, @ModelAttribute("cctipointervento") CcTipointervento cctipointervento, BindingResult result,
	    SessionStatus status) {

	CcTipointervento objToDelete = cctipointerventoService.findById(cctipointervento.getId());
	try {
	    cctipointerventoService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, cctipointervento, e);
	    fixRenderEntityProperty(cctipointervento);
	    List<OccBasetipointervento> listInterventiBase = occBasetipointerventoService.findAll(null, null);
	    model.addAttribute("listInterventiBase", listInterventiBase);
	    return "cctipointervento/form";
	}
	status.setComplete();
	return "redirect:list.htm";
    }

    @Override
    protected void fixMergeEntityProperty(CcTipointervento entity) {

    }

    @Override
    protected void fixRenderEntityProperty(CcTipointervento entity) {

	if (entity.getOccBasetipointervento() == null) {
	    entity.setOccBasetipointervento(new OccBasetipointervento());
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
