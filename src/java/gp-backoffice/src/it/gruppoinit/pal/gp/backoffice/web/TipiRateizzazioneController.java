package it.gruppoinit.pal.gp.backoffice.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.features.oneri.rateizzazione.configurazione.DataInizioRateizzazioneEnum;
import it.gruppoinit.pal.gp.core.features.oneri.rateizzazione.configurazione.FrequenzaRateAmmortamentoFREnum;
import it.gruppoinit.pal.gp.core.features.oneri.rateizzazione.configurazione.OneriTipiRateizzazioneBean;
import it.gruppoinit.pal.gp.core.features.oneri.rateizzazione.configurazione.OneriTipiRateizzazioneListBean;
import it.gruppoinit.pal.gp.core.features.oneri.rateizzazione.configurazione.OneritipirateizzazioneService;
import it.gruppoinit.pal.gp.core.features.oneri.rateizzazione.configurazione.TipoAnatocismoEnum;
import it.gruppoinit.pal.gp.core.features.oneri.rateizzazione.configurazione.TipologiaRateizzazioneEnum;
import it.gruppoinit.pal.gp.core.service.SoftwareService;

@Controller
@SessionAttributes("tipirateizzazione")
public class TipiRateizzazioneController extends BaseController<OneriTipiRateizzazioneBean> {

    @Autowired
    private OneritipirateizzazioneService oneritipirateizzazioneService;
    @Autowired
    private SoftwareService softwareService;
    private int scadenzaPeriodicaFissa = 9; //è una tabella di base

    @Override
    protected void setPageAttributes(Model model) {

	model.addAttribute("iniziorateizzazionemovimento", DataInizioRateizzazioneEnum.DATA_MOVIMENTO.getValore());
	model.addAttribute("listascadenzarate", this.oneritipirateizzazioneService.getTipiScadenzaConsentiti());
	model.addAttribute("mappainiziorateizzazione", DataInizioRateizzazioneEnum.toMap().entrySet());
	model.addAttribute("mappaammortamento", TipologiaRateizzazioneEnum.toMap().entrySet());
	model.addAttribute("mappafrequenzarateFR", FrequenzaRateAmmortamentoFREnum.toMap().entrySet());
	model.addAttribute("mappatipoanatocismo", TipoAnatocismoEnum.toMap().entrySet());
	model.addAttribute("frequenzaFR", TipologiaRateizzazioneEnum.AMMORTAMENTO_FRANCESE.toString());
	model.addAttribute("scadenzaPeriodica", this.scadenzaPeriodicaFissa);
    }

    @Override
    protected void fixMergeEntityProperty(OneriTipiRateizzazioneBean entity) {

	// TODO Auto-generated method stub
    }

    @Override
    protected void fixRenderEntityProperty(OneriTipiRateizzazioneBean entity) {

	// TODO Auto-generated method stub
    }

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	List<OneriTipiRateizzazioneListBean> lista = this.oneritipirateizzazioneService.findAllByIdcomuneSoftware();
	ModelMap model = new ModelMap(lista);
	model.addAttribute("lista", lista);
	return model;
    }

    @RequestMapping
    public String create(Model model) {

	OneriTipiRateizzazioneBean tipirateizzazione = new OneriTipiRateizzazioneBean();
	fixRenderEntityProperty(tipirateizzazione);
	setPageAttributes(model);
	model.addAttribute("tipirateizzazione", tipirateizzazione);
	return "tipirateizzazione/form";
    }

    @RequestMapping
    public String insert(Model model, @ModelAttribute("tipirateizzazione") OneriTipiRateizzazioneBean tipirateizzazione, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	fixMergeEntityProperty(tipirateizzazione);
	try {
	    this.oneritipirateizzazioneService.insert(tipirateizzazione);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, tipirateizzazione, e);
	    fixRenderEntityProperty(tipirateizzazione);
	    setPageAttributes(model);
	    model.addAttribute("tipirateizzazione", tipirateizzazione);
	    return "tipirateizzazione/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + tipirateizzazione.getId() + "&status_msg=01";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	OneriTipiRateizzazioneBean tipirateizzazione = this.oneritipirateizzazioneService.findById(codice);
	setPageAttributes(model);
	model.addAttribute("tipirateizzazione", tipirateizzazione);
	return "tipirateizzazione/form";
    }

    @RequestMapping
    public String update(Model model, @ModelAttribute("tipirateizzazione") OneriTipiRateizzazioneBean tipirateizzazione, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	if (result.hasErrors()) {
	    setPageAttributes(model);
	    model.addAttribute("tipirateizzazione", tipirateizzazione);
	    return "tipirateizzazione/form";
	}
	fixMergeEntityProperty(tipirateizzazione);
	try {
	    this.oneritipirateizzazioneService.update(tipirateizzazione);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, tipirateizzazione, e);
	    fixRenderEntityProperty(tipirateizzazione);
	    setPageAttributes(model);
	    model.addAttribute("tipirateizzazione", tipirateizzazione);
	    return "tipirateizzazione/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + tipirateizzazione.getId() + "&status_msg=02";
    }

    @RequestMapping
    public String delete(Model model, @ModelAttribute("tipirateizzazione") OneriTipiRateizzazioneBean tipirateizzazione, BindingResult result,
	    SessionStatus status) {

	try {
	    this.oneritipirateizzazioneService.delete(tipirateizzazione.getId());
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, tipirateizzazione, e);
	    fixRenderEntityProperty(tipirateizzazione);
	    setPageAttributes(model);
	    return "tipirateizzazione/form";
	}
	status.setComplete();
	return "redirect:../history/back.htm?" + WebConstants.GOTO + "=%2F";
    }
}
