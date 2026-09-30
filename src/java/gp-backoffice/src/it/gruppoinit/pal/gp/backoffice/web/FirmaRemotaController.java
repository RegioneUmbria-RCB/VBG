package it.gruppoinit.pal.gp.backoffice.web;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.domain.FirmeRemote;
import it.gruppoinit.pal.gp.core.features.firmadigitale.firmaremota.FirmaRemotaClientService;
import it.gruppoinit.pal.gp.core.features.firmadigitale.firmaremota.client.RecuperaParametriRequest;
import it.gruppoinit.pal.gp.core.features.firmadigitale.firmaremota.client.RecuperaParametriResponse;
import it.gruppoinit.pal.gp.core.features.firmadigitale.firmaremota.configurazione.FirmeRemoteService;
import it.gruppoinit.pal.gp.core.features.firmadigitale.firmaremota.model.FirmaRemotaListModel;
import it.gruppoinit.pal.gp.core.features.firmadigitale.firmaremota.model.FirmaRemotaModel;
import it.gruppoinit.pal.gp.core.features.firmadigitale.firmaremota.model.FirmaRemotaParametroComparator;
import it.gruppoinit.pal.gp.core.features.firmadigitale.firmaremota.model.ProviderFirmaModel;
import it.gruppoinit.pal.gp.core.service.exception.BusinessValidationException;

@Controller
@SessionAttributes(value = { "firmaRemotaListModel", "firmaremota", "providers" })
public class FirmaRemotaController extends BaseJsonController<FirmeRemote> {

    private static final Logger logger = LoggerFactory.getLogger(FirmaRemotaController.class);
    private static final String FORM = "firmaremota/form";
    @Autowired
    private FirmeRemoteService firmeRemoteService;
    @Autowired
    private FirmaRemotaClientService firmaRemotaClientService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	List<FirmaRemotaListModel> firme = this.firmeRemoteService.listaFirmeIntegrate();
	ModelMap model = new ModelMap(firme);
	model.addAttribute("firmaRemotaListModel", firme);
	return model;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String create(Model model) {

	List<ProviderFirmaModel> providers = this.firmeRemoteService.getFirmeIntegrate();
	model.addAttribute("providers", providers);
	model.addAttribute("firmaremota", new FirmaRemotaModel());
	setPageAttributes(model);
	return FORM;
    }

    @RequestMapping(method = RequestMethod.POST)
    public String insert(Model model, @ModelAttribute("firmaremota") FirmaRemotaModel firma, BindingResult result, SessionStatus status) {

	try {
	    int id = this.firmeRemoteService.insert(firma);
	    status.setComplete();
	    return "redirect:view.htm?codice=" + id + "&status_msg=01";
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, firma, true, e);
	    model.addAttribute("firmaremota", firma);
	    return "redirect:create.htm";
	}
    }

    @RequestMapping(method = RequestMethod.GET)
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	FirmaRemotaModel firma = this.firmeRemoteService.getFirmaRemota(codice);
	Collections.sort(firma.getParametri(), new FirmaRemotaParametroComparator());
	List<ProviderFirmaModel> providers = this.firmeRemoteService.getFirmeIntegrate();
	model.addAttribute("providers", providers);
	model.addAttribute("firmaremota", firma);
	setPageAttributes(model);
	return FORM;
    }

    @RequestMapping(method = RequestMethod.POST)
    public String update(@RequestParam("codice") Integer codice, Model model, @ModelAttribute("firmaremota") FirmaRemotaModel firma,
	    BindingResult result, SessionStatus status, HttpServletRequest request) {

	try {
	    firma.setId(codice);
	    this.firmeRemoteService.updateFirma(firma);
	    status.setComplete();
	    return "redirect:view.htm?codice=" + firma.getId() + "&status_msg=02";
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, firma, true, e);
	    model.addAttribute("firmaremota", firma);
	    return FORM;
	}
    }

    @RequestMapping(method = RequestMethod.POST)
    public String delete(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request, HttpServletResponse response) {

	try {
	    this.firmeRemoteService.delete(codice);
	} catch (Exception e) {
	    if (e instanceof BusinessValidationException) {
		copyErrorsToFlashMessages(null, false, "", e);
		return "redirect:view.htm?codice=" + codice + "&status_msg=03";
	    }
	}
	return "redirect:../history/back.htm?" + WebConstants.GOTO + "=%2F";
    }

    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RequestMapping(method = RequestMethod.POST)
    public void jsonRecuperaParametri(Model model, HttpServletRequest request, HttpServletResponse response) throws JAXBException, IOException {

	try {
	    logger.debug("Inizio chiamata al metodo jsonRecuperaParametri");
	    RecuperaParametriRequest parametriRequest = (RecuperaParametriRequest) fromJson(request.getInputStream(), RecuperaParametriRequest.class);
	    RecuperaParametriResponse parametriResponse = this.firmaRemotaClientService.recuperaParametri(parametriRequest);
	    response.setContentType("application/json");
	    String jsonParametriResponse = toJson(parametriResponse, true);
	    logger.debug("Fine chiamata al metodo jsonRecuperaParametri {}", jsonParametriResponse);
	    response.getOutputStream().write(jsonParametriResponse.getBytes("utf-8"));
	} catch (Exception ex) {
	    logger.error(ex.getMessage());
	    super.writeJsonError(response, ex.getMessage());
	}
    }
}
