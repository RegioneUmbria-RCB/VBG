package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.dossier.messages.DettaglioReportType;
import it.gruppoinit.dossier.messages.RiversaIstanzeRequest;
import it.gruppoinit.dossier.messages.RiversaIstanzeResponse;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.service.DossierService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;
import it.gruppoinit.pal.gp.core.service.helper.FlashMessages;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes(value = { "dossierCommand" })
public class DossierController extends BaseController<Istanze> {

    private static final Logger log = LoggerFactory.getLogger(DossierController.class);
    @Autowired
    private DossierService dossierService;
    @Autowired
    private ResponsabiliService responsabiliService;
    @Autowired
    private SoftwareService softwareService;

    @RequestMapping
    public String createRiversaPratiche(Model model, HttpServletRequest request) {

	Responsabili responsabile = getCurrentlyAuthenticatedUserDetails();
	List<Software> softwareAttivi = softwareService.findSoftwareAbilitati(responsabile);
	model.addAttribute("softwareAttivi", softwareAttivi);
	return "dossier/form";
    }

    @RequestMapping
    public String riversaPratiche(Model model, @RequestParam("softwareCode") String softwareCode, HttpServletRequest request) {

	RiversaIstanzeRequest riversaIstanzeRequest = new RiversaIstanzeRequest();
	riversaIstanzeRequest.setSoftware(softwareCode);
	RiversaIstanzeResponse riversaIstanzeResponse = null;
	List<String> warnings = new ArrayList<String>();
	List<String> infos = new ArrayList<String>();
	try {
	    riversaIstanzeResponse = dossierService.uploadIstanzeInDossier(riversaIstanzeRequest);
	} catch (Exception e) {
	    Responsabili responsabile = getCurrentlyAuthenticatedUserDetails();
	    List<Software> softwareAttivi = softwareService.findSoftwareAbilitati(responsabile);
	    model.addAttribute("softwareAttivi", softwareAttivi);
	    warnings.add(e.getMessage());
	    FlashMessages.setWarnings(warnings);
	    return "dossier/form";
	}
	// Gestione risposta
	if (riversaIstanzeResponse != null) {
	    // Errore generico sulla chiamata 
	    if (StringUtils.isNotBlank(riversaIstanzeResponse.getErrore())) {
		warnings.add(riversaIstanzeResponse.getErrore());
		if (riversaIstanzeResponse.getListaDettagli() != null && !riversaIstanzeResponse.getListaDettagli().isEmpty()) {
		    for (DettaglioReportType dettaglioReportType : riversaIstanzeResponse.getListaDettagli()) {
			StringBuffer buffer = new StringBuffer("");
			buffer.append(dettaglioReportType.getErrore() + ": " + dettaglioReportType.getMsg() + " ["
				+ dettaglioReportType.getSoftware() + "]");
			log.error(buffer.toString());
			warnings.add(buffer.toString());
		    }
		}
		FlashMessages.setWarnings(warnings);
	    } else {
		if (riversaIstanzeResponse.getListaDettagli() != null && !riversaIstanzeResponse.getListaDettagli().isEmpty()) {
		    for (DettaglioReportType dettaglioReportType : riversaIstanzeResponse.getListaDettagli()) {
			// Errori sull'elaborazione della chiamata per software
			if (StringUtils.isNotBlank(dettaglioReportType.getErrore())) {
			    StringBuffer buffer = new StringBuffer("");
			    buffer.append(dettaglioReportType.getErrore() + ": " + dettaglioReportType.getMsg() + " ["
				    + dettaglioReportType.getSoftware() + "]");
			    log.error(buffer.toString());
			    warnings.add(buffer.toString());
			} else {
			    // Messaggio che l'operazione è stata avviata
			    StringBuffer buffer = new StringBuffer("");
			    buffer.append(dettaglioReportType.getMsg() + ": " + dettaglioReportType.getSoftware());
			    log.debug(buffer.toString());
			    infos.add(buffer.toString());
			}
		    }
		}
		if (!warnings.isEmpty()) {
		    FlashMessages.setWarnings(infos);
		}
		if (!infos.isEmpty()) {
		    FlashMessages.setInfos(infos);
		}
	    }
	}
	return "redirect:../dossier/createRiversaPratiche.htm";
    }

    @Override
    protected void setPageAttributes(Model model) {

	// TODO Auto-generated method stub
    }

    @Override
    protected void fixMergeEntityProperty(Istanze entity) {

	// TODO Auto-generated method stub
    }

    @Override
    protected void fixRenderEntityProperty(Istanze entity) {

	// TODO Auto-generated method stub
    }
}
