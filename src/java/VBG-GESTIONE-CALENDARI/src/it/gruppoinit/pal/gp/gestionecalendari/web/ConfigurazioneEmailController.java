package it.gruppoinit.pal.gp.gestionecalendari.web;

import it.gruppoinit.pal.gp.core.domain.ConfigurazioneEmail;
import it.gruppoinit.pal.gp.core.helper.EntityUtils;
import it.gruppoinit.pal.gp.core.service.ConfigurazioneEmailService;
import it.gruppoinit.pal.gp.gestionecalendari.web.validator.ConfigurazioneEmailValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

/**
 * 
 * @author
 */
@Controller
@SessionAttributes("configurazioneemail")
public class ConfigurazioneEmailController extends BaseController {

    private static final Logger log = LoggerFactory.getLogger(ConfigurazioneEmailController.class);
    @Autowired
    private ConfigurazioneEmailService configurazioneemailService;

    @RequestMapping
    public String createOrview(Model model, HttpServletRequest request, HttpServletResponse response) {

	log.info("createOrview");
	ConfigurazioneEmail configurazioneEmail = configurazioneemailService.findByInstallazione();
	if (configurazioneEmail != null) {
	    model.addAttribute("create", false);
	    model.addAttribute("configurazioneemail", configurazioneEmail);
	} else {
	    model.addAttribute("create", true);
	    model.addAttribute("configurazioneemail", new ConfigurazioneEmail());
	}
	return "configurazioneemail/form";
    }

    @RequestMapping
    public String salva(Model model, @ModelAttribute("configurazioneemail") ConfigurazioneEmail configurazioneEmail, BindingResult result,
	    SessionStatus status, HttpServletRequest request, HttpServletResponse response) {

	log.info("salva");
	try {
	    new ConfigurazioneEmailValidator().validate(configurazioneEmail, result);
	    if (result.hasErrors()) {
		return "configurazioneemail/form";
	    }
	    if (EntityUtils.isNestedPropertyBlank(configurazioneEmail, "id.codice")) {
		configurazioneemailService.insert(configurazioneEmail);
	    } else {
		configurazioneemailService.update(configurazioneEmail);
	    }
	    status.setComplete();
	    return "redirect:createOrview.htm?&ret=0";
	} catch (Exception e) {
	    log.error("salva", e);
	    addErrors(model, e);
	    return "configurazioneemail/form";
	}
    }

    private void addErrors(Model model, Exception e) {

	model.addAttribute("error", StringUtils.defaultIfEmpty(e.getMessage(), getMessageFromBundle("error.exception")));
    }
}
