package it.gruppoinit.pal.gp.backoffice.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.jmesa.web.GenerateTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.IstanzeAccessoAttiLog;
import it.gruppoinit.pal.gp.core.domain.IstanzeAccessoAttiLogId;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.domain.web.IstanzeAccessoAttiFilter;
import it.gruppoinit.pal.gp.core.jmesa.IstanzeAccessoAttiLogTable;
import it.gruppoinit.pal.gp.core.service.AnagrafeService;
import it.gruppoinit.pal.gp.core.service.IstanzeAccessoAttiLogService;

/**
 * 
 * @author
 */
@Controller
@SessionAttributes(value = { "istanzeaccessoattilog", "istanzeAccessoAttiFilter" })
public class IstanzeAccessoAttiLogController extends BaseController<IstanzeAccessoAttiLog> {

    @Autowired
    private IstanzeAccessoAttiLogService istanzeaccessoattilogService;
    @Autowired
    private AnagrafeService anagrafeService;
    private String ISTANZEACCESSOATTI_FILTER_IN_SESSION = "ISTANZEACCESSOATTI_FILTER_IN_SESSION";

    @RequestMapping
    public String createSearch(Model model, @RequestParam(value = "resetAttrs", required = false) String resetAttrs,
	    @RequestParam(value = "modalita_ricerca", required = false) Integer modalita_ricerca, HttpServletRequest request,
	    HttpServletResponse response) {

	IstanzeAccessoAttiFilter istanzeAccessoAttiFilter = new IstanzeAccessoAttiFilter();
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_ORDINAMENTO_ISTANZEACCESSOATTI, DAOOrderTypeEnum.ASC.toString(), request);
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_CAMPI_ORDINAMENTO_ISTANZEACCESSOATTI, "anagrafe.descrizioneRichiedente",
		request);
	String orderField = (String) request.getAttribute(WebConstants.CONF_UTENTE_CAMPI_ORDINAMENTO_ISTANZEACCESSOATTI);
	istanzeAccessoAttiFilter.setOrderBy(orderField);
	if (StringUtils.isBlank(resetAttrs)) {
	    IstanzeAccessoAttiFilter _accessoAttiFilter = (IstanzeAccessoAttiFilter) request.getSession(false)
		    .getAttribute("istanzeAccessoAttiFilter");
	    if (_accessoAttiFilter != null) {
		istanzeAccessoAttiFilter = _accessoAttiFilter;
	    }
	}
	if (request.getSession().getAttribute(ISTANZEACCESSOATTI_FILTER_IN_SESSION) != null) {
	    request.getSession().removeAttribute(ISTANZEACCESSOATTI_FILTER_IN_SESSION);
	}
	model.addAttribute("istanzeAccessoAttiFilter", istanzeAccessoAttiFilter);
	if (modalita_ricerca != null) {
	    model.addAttribute("TIPO_SEARCH_AUT", modalita_ricerca);
	}
	return "istanzeaccessoattilog/search";
    }

    @RequestMapping
    public String list(Model model, @ModelAttribute("istanzeAccessoAttiFilter") IstanzeAccessoAttiFilter istanzeAccessoAttiFilter,
	    @RequestParam(value = "modalita_ricerca", required = false) Integer modalita_ricerca, HttpServletRequest request,
	    HttpServletResponse response) {

	if (request.getSession().getAttribute(ISTANZEACCESSOATTI_FILTER_IN_SESSION) == null) {
	    request.getSession().setAttribute(ISTANZEACCESSOATTI_FILTER_IN_SESSION, istanzeAccessoAttiFilter);
	} else {
	    istanzeAccessoAttiFilter = (IstanzeAccessoAttiFilter) request.getSession().getAttribute(ISTANZEACCESSOATTI_FILTER_IN_SESSION);
	}
	fixfilter(istanzeAccessoAttiFilter);
	String htmlTable = "";
	GenerateTable<IstanzeAccessoAttiLog> istanzeAccessoAttiTable = new IstanzeAccessoAttiLogTable(istanzeAccessoAttiFilter, modalita_ricerca);
	htmlTable = istanzeAccessoAttiTable.createJMesaList(request, response, "label.istanzeaccessoatti", "istanzeaccessoatti_id", true);
	if (htmlTable == null) {
	    return null;
	}
	model.addAttribute("htmlTable", htmlTable);
	model.addAttribute("istanzeAccessoAttiFilter", istanzeAccessoAttiFilter);
	model.addAttribute("TIPO_SEARCH_AUT", WebConstants.SEARCH_AUT_DEFAULT);
	return "istanzeaccessoattilog/list";
    }

    private void fixfilter(IstanzeAccessoAttiFilter istanzeAccessoAttiFilter) {

	if (EntityUtils.getNestedProperty(istanzeAccessoAttiFilter.getAnagrafe(), "id.codice") != null) {
	    Anagrafe anagrafe = anagrafeService.findById(istanzeAccessoAttiFilter.getAnagrafe().getId());
	    if (anagrafe != null) {
		istanzeAccessoAttiFilter.setAnagrafe(anagrafe);
	    }
	}
    }

    @RequestMapping
    public String insert(@ModelAttribute("istanzeaccessoattilog") IstanzeAccessoAttiLog istanzeaccessoattilog, BindingResult result,
	    SessionStatus status) {

	fixMergeEntityProperty(istanzeaccessoattilog);
	try {
	    istanzeaccessoattilogService.insert(istanzeaccessoattilog);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, istanzeaccessoattilog, e);
	    fixRenderEntityProperty(istanzeaccessoattilog);
	    return "istanzeaccessoattilog/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + istanzeaccessoattilog.getId().toString() + "&status_msg=01";
    }

    @RequestMapping
    public String view(@RequestParam("codice") String uuid, Model model, HttpServletRequest request) {

	IstanzeAccessoAttiLogId id = new IstanzeAccessoAttiLogId(uuid);
	IstanzeAccessoAttiLog istanzeaccessoattilog = istanzeaccessoattilogService.findById(id);
	fixRenderEntityProperty(istanzeaccessoattilog);
	model.addAttribute("istanzeaccessoattilog", istanzeaccessoattilog);
	setPageAttributes(model);
	return "istanzeaccessoattilog/form";
    }

    @RequestMapping
    public String update(@ModelAttribute("istanzeaccessoattilog") IstanzeAccessoAttiLog istanzeaccessoattilog, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	fixMergeEntityProperty(istanzeaccessoattilog);
	try {
	    istanzeaccessoattilogService.update(istanzeaccessoattilog);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, istanzeaccessoattilog, e);
	    fixRenderEntityProperty(istanzeaccessoattilog);
	    return "istanzeaccessoattilog/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + istanzeaccessoattilog.getId().getUuid() + "&status_msg=02";
    }

    @RequestMapping
    public String delete(@ModelAttribute("istanzeaccessoattilog") IstanzeAccessoAttiLog istanzeaccessoattilog, BindingResult result,
	    SessionStatus status) {

	IstanzeAccessoAttiLog objToDelete = istanzeaccessoattilogService.findById(istanzeaccessoattilog.getId());
	try {
	    istanzeaccessoattilogService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderEntityProperty(istanzeaccessoattilog);
	    return "istanzeaccessoattilog/form";
	}
	status.setComplete();
	return "redirect:list.htm";
    }

    @Override
    protected void fixMergeEntityProperty(IstanzeAccessoAttiLog entity) {

    }

    @Override
    protected void fixRenderEntityProperty(IstanzeAccessoAttiLog entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
