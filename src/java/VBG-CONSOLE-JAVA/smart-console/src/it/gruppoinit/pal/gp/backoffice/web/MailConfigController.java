package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.MailConfig;
import it.gruppoinit.pal.gp.core.domain.MailConfigId;
import it.gruppoinit.pal.gp.core.domain.web.MailConfigCommand;
import it.gruppoinit.pal.gp.core.service.MailConfigService;

import javax.servlet.http.HttpServletRequest;

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
 * @author gianpaolot
 */
@Controller
@SessionAttributes("mailconfig")
public class MailConfigController extends BaseController<MailConfig> {

    @Autowired
    private MailConfigService mailconfigService;

    @RequestMapping
    public String create(Model model) {

	MailConfigCommand mailconfigCommand = new MailConfigCommand();
	MailConfigId id = new MailConfigId();
	MailConfig entity = mailconfigService.findById(id);
	mailconfigCommand.setDisplayMode(MailConfigCommand.VIEW);
	// non esiste una configurazione mail, la dobbiamo creare
	if (entity == null) {
	    entity = new MailConfig();
	    entity.setId(id);
	    mailconfigCommand.setDisplayMode(MailConfigCommand.NEW);
	    // se esiste per TT precompilo il form
	    MailConfigId idTemp = new MailConfigId(ORMHelper.getIdcomune(), "TT");
	    MailConfig mailConfig = mailconfigService.findById(idTemp);
	    if (mailConfig != null) {
		entity.setLoginname(mailConfig.getLoginname());
		entity.setLoginpass(mailConfig.getLoginpass());
		entity.setMailserver(mailConfig.getMailserver());
		entity.setPort(mailConfig.getPort());
		entity.setSenderaddress(mailConfig.getSenderaddress());
		entity.setUseauthentication(mailConfig.getUseauthentication());
		entity.setUsessl(mailConfig.getUsessl());
		//in
		entity.setInLoginname(mailConfig.getInLoginname());
		entity.setInLoginpass(mailConfig.getInLoginpass());
		entity.setInMailserver(mailConfig.getInMailserver());
		entity.setInPort(mailConfig.getInPort());
		entity.setInUseauthentication(mailConfig.getInUseauthentication());
		entity.setInUsessl(mailConfig.getInUsessl());
	    }
	}
	mailconfigCommand.setEntity(entity);
	fixRenderEntityProperty(mailconfigCommand.getEntity());
	model.addAttribute("mailconfig", mailconfigCommand);
	setPageAttributes(model);
	return "mailconfig/form";
    }

    @RequestMapping
    public String insert(Model model, @ModelAttribute("mailconfig") MailConfigCommand mailconfig, BindingResult result, SessionStatus status) {

	if (result.hasErrors()) {
	    mailconfig.setDisplayMode(MailConfigCommand.NEW);
	    model.addAttribute("mailconfig", mailconfig);
	    return "mailconfig/form";
	}
	fixMergeEntityProperty(mailconfig.getEntity());
	try {
	    mailconfigService.insert(mailconfig.getEntity());
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, mailconfig.getEntity(), true, e);
	    fixRenderEntityProperty(mailconfig.getEntity());
	    mailconfig.setDisplayMode(MailConfigCommand.NEW);
	    model.addAttribute("mailconfig", mailconfig);
	    return "mailconfig/form";
	}
	status.setComplete();
	return "redirect:create.htm?status_msg=01";
    }

    @RequestMapping
    public String update(Model model, @ModelAttribute("mailconfig") MailConfigCommand mailconfig, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	if (result.hasErrors()) {
	    mailconfig.setDisplayMode(MailConfigCommand.VIEW);
	    model.addAttribute("mailconfig", mailconfig);
	    return "mailconfig/form";
	}
	fixMergeEntityProperty(mailconfig.getEntity());
	try {
	    mailconfigService.update(mailconfig.getEntity());
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, mailconfig.getEntity(), true, e);
	    mailconfig.setDisplayMode(MailConfigCommand.VIEW);
	    model.addAttribute("mailconfig", mailconfig);
	    fixRenderEntityProperty(mailconfig.getEntity());
	    return "mailconfig/form";
	}
	return "redirect:create.htm?status_msg=02";
    }

    @Override
    protected void fixMergeEntityProperty(MailConfig entity) {

    }

    @Override
    protected void fixRenderEntityProperty(MailConfig entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
