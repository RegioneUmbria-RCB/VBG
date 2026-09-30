package it.gruppoinit.pal.gp.areariservata.web;

import it.gruppoinit.pal.gp.areariservata.web.util.UrlBackHelper;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.FoArjDomande;
import it.gruppoinit.pal.gp.core.domain.FoArjServizi;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.FoArjDomandeService;
import it.gruppoinit.pal.gp.core.service.FoArjServiziService;
import it.gruppoinit.pal.gp.core.service.MessagesService;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Secured("ROLE_USER")
@Controller
public class DomandeController extends BaseController {

    private static final Logger log = LoggerFactory.getLogger(DomandeController.class);
    @Autowired
    private FoArjDomandeService foArjDomandeService;
    @Autowired
    private MessagesService messagesService;
    @Autowired
    private UserSecurityService userSecurityService;
    @Autowired
    private FoArjServiziService foArjServiziService;

    @RequestMapping
    public String list(Model model) {

	log.debug("list");
	Anagrafe user = (Anagrafe) userSecurityService.getCurrentlyAuthenticatedUserDetails();
	List<FoArjDomande> list = foArjDomandeService.findDomandePerUtente(user.getId().getCodice(), null, null);
	model.addAttribute("domande", list);
	return "domande/list";
    }

    @RequestMapping
    public String listPerServizio(Model model, HttpServletRequest request, @RequestParam("idServizio") Integer idServizio) {

	log.debug("listPerServizio idServizio=[{}]", idServizio);
	Anagrafe user = (Anagrafe) userSecurityService.getCurrentlyAuthenticatedUserDetails();
	FoArjServizi servizio = foArjServiziService.findById(new PkId(idServizio));
	if (servizio == null) {
	    log.error("listPerServizio: il servizio con codice=[{}] non esiste", idServizio);
	    throw new RuntimeException(messagesService.getMessage("error.servizio-non-trovato"));
	}
	List<FoArjDomande> list = foArjDomandeService.findDomandePerUtenteEServizio(user.getId().getCodice(), idServizio, null, null);
	model.addAttribute("domande", list);
	UrlBackHelper.set("listPerServizio.htm?idServizio=" + idServizio, request);
	return "domande/list";
    }

    @RequestMapping
    public String delete(Model model, HttpServletRequest request, @RequestParam(value = "id") Integer id) {

	log.debug("delete id=[{}]", id);
	FoArjDomande domanda = foArjDomandeService.findById(new PkId(id));
	if (domanda == null) {
	    log.error("delete: la domanda con id=[{}] non esiste", id);
	    throw new RuntimeException(messagesService.getMessage("error.record-non-trovato"));
	}
	foArjDomandeService.delete(domanda);
	return "redirect:" + UrlBackHelper.get("list.htm", request);
    }
}
