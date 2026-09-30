package it.gruppoinit.pal.gp.backoffice.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import it.gruppoinit.pal.gp.core.constants.BackofficeNETConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.ConfigurazioneCalcoli;
import it.gruppoinit.pal.gp.core.features.configurazionecalcoli.CalcoloListItem;
import it.gruppoinit.pal.gp.core.features.configurazionecalcoli.IConfigurazioneCalcoliService;

@Controller
public class ConfigurazioneCalcoliController extends BaseJsonController<ConfigurazioneCalcoli> {

    private IConfigurazioneCalcoliService configurazioneCalcoliService;

    @Autowired
    public void setConfigurazioneCalcoliService(IConfigurazioneCalcoliService configurazioneCalcoliService) {

	this.configurazioneCalcoliService = configurazioneCalcoliService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	String uriBack = "/configurazionecalcoli/list.htm?software=" + ORMHelper.getSoftware();
	List<CalcoloListItem> calcoli = this.configurazioneCalcoliService.findAll();
	for (CalcoloListItem calcolo : calcoli) {
	    String url = "../../ConfiguratoreCalcoli/IndexV" + calcolo.getVersione() + ".html?Token=";
	    url += ORMHelper.getToken();
	    url += "&Id=" + calcolo.getId();
	    String urlFinale = BackofficeNETConstants.getUrlTo(request, url, uriBack, ORMHelper.getSoftware(), false);
	    calcolo.setUrl(urlFinale);
	}
	ModelMap model = new ModelMap(calcoli);
	model.addAttribute("calcoliList", calcoli);
	String urlNuovoCalcolo = "../../ConfiguratoreCalcoli/IndexV2.html?Token=";
	urlNuovoCalcolo += ORMHelper.getToken();
	String urlFinale = BackofficeNETConstants.getUrlTo(request, urlNuovoCalcolo, uriBack, ORMHelper.getSoftware(), false);
	model.addAttribute("urlNuovoCalcolo", urlFinale);
	return model;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String elimina(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request, HttpServletResponse response) {

	try {
	    this.configurazioneCalcoliService.deleteById(codice);
	} catch (Exception e) {
	    return "redirect:list.htm?software=" + ORMHelper.getSoftware() + "&status_msg=03";
	}
	return "redirect:list.htm?software=" + ORMHelper.getSoftware() + "&status_msg=01";
    }

    @RequestMapping
    public void ajaxAggiornaDescrizione(Model model, @RequestParam("codice") Integer codice, @RequestParam("descrizione") String descrizione,
	    HttpServletRequest erquest, HttpServletResponse response) throws IOException {

	this.configurazioneCalcoliService.aggiornaDescrizione(codice, descrizione);
	response.setContentType("application/json");
	response.getOutputStream().write("{}".getBytes());
	response.getOutputStream().flush();
    }
}
