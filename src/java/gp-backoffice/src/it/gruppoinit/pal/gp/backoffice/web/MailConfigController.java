package it.gruppoinit.pal.gp.backoffice.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
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
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.Comuniassociati;
import it.gruppoinit.pal.gp.core.domain.MailConfig;
import it.gruppoinit.pal.gp.core.domain.MailConfigComuni;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.helper.MailConfigHelper;
import it.gruppoinit.pal.gp.core.domain.web.MailConfigCommand;
import it.gruppoinit.pal.gp.core.service.ComuniassociatiService;
import it.gruppoinit.pal.gp.core.service.MailConfigComuniService;
import it.gruppoinit.pal.gp.core.service.MailConfigService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;
import it.gruppoinit.pal.gp.core.service.SoftwareattiviService;
import it.gruppoinit.pal.gp.core.service.helper.MailConfigComuniDTO;
import it.gruppoinit.pal.gp.core.utils.CryptoUtils;

/**
 * 
 * @author gianpaolot
 */
@Controller
@SessionAttributes("mailconfig")
public class MailConfigController extends BaseController<MailConfig> {

    @Autowired
    private MailConfigService mailconfigService;
    @Autowired
    private ComuniassociatiService comuniassociatiService;
    @Autowired
    private SoftwareattiviService softwareattiviService;
    @Autowired
    private SoftwareService softwareService;
    CryptoUtils crypto = new CryptoUtils();
    @Autowired
    private MailConfigComuniService mailConfigComuniService;

    @RequestMapping
    public String list(Model model, HttpServletRequest request, HttpServletResponse response) {

	boolean isComuniAssociati = comuniassociatiService.isComuniassociati(ORMHelper.getIdcomune());
	Software sw = softwareService.findById(ORMHelper.getSoftware());
	List<MailConfigHelper> mailConfigHelpers = new ArrayList<MailConfigHelper>();
	List<MailConfigHelper> mailEreditatiTTConfigHelpers = new ArrayList<MailConfigHelper>();
	MailConfigHelper mailConfigHelper = null;
	MailConfigHelper mailConfigHelperEriditatiTT = null;
	if (isComuniAssociati) {
	    MailConfigHelper mailConfigHelperComuneNull = new MailConfigHelper();
	    List<MailConfig> listComuneNull = mailconfigService.findBySoftware(ORMHelper.getSoftware(), null);
	    mailConfigHelperComuneNull.setCodiceSoftware(sw.getCodice());
	    mailConfigHelperComuneNull.setSoftware(sw.getDescrizione());
	    mailConfigHelperComuneNull.setMailConfigs(listComuneNull);
	    mailConfigHelpers.add(mailConfigHelperComuneNull);
	    List<Comuniassociati> listComuniAssociati = comuniassociatiService.findByIdcomune(ORMHelper.getIdcomune());
	    for (Comuniassociati comuniassociati : listComuniAssociati) {
		List<MailConfig> list = mailconfigService.findBySoftwareAndCodiceComune(ORMHelper.getSoftware(),
			comuniassociati.getComune().getCodicecomune(), null);
		mailConfigHelper = new MailConfigHelper();
		mailConfigHelper.setCodiceSoftware(sw.getCodice());
		mailConfigHelper.setSoftware(sw.getDescrizione());
		mailConfigHelper.setCodiceComune(comuniassociati.getComune().getCodicecomune());
		mailConfigHelper.setComune(comuniassociati.getComune().getComune());
		mailConfigHelper.setMailConfigs(list);
		mailConfigHelpers.add(mailConfigHelper);
	    }
	    if (!ORMHelper.getSoftware().equals(WebConstants.SOFTWARE_TT)) {
		MailConfigHelper mailConfigHelperComuneNullTT = new MailConfigHelper();
		List<MailConfig> listComuneNullTT = mailconfigService.findBySoftware(WebConstants.SOFTWARE_TT, null);
		mailConfigHelperComuneNullTT.setCodiceSoftware(sw.getCodice());
		mailConfigHelperComuneNullTT.setSoftware(sw.getDescrizione());
		mailConfigHelperComuneNullTT.setMailConfigs(listComuneNullTT);
		mailEreditatiTTConfigHelpers.add(mailConfigHelperComuneNullTT);
		for (Comuniassociati comuniassociati : listComuniAssociati) {
		    List<MailConfig> list = mailconfigService.findBySoftwareAndCodiceComune(WebConstants.SOFTWARE_TT,
			    comuniassociati.getComune().getCodicecomune(), null);
		    mailConfigHelperEriditatiTT = new MailConfigHelper();
		    mailConfigHelperEriditatiTT.setCodiceSoftware(sw.getCodice());
		    mailConfigHelperEriditatiTT.setSoftware(sw.getDescrizione());
		    mailConfigHelperEriditatiTT.setCodiceComune(comuniassociati.getComune().getCodicecomune());
		    mailConfigHelperEriditatiTT.setComune(comuniassociati.getComune().getComune());
		    mailConfigHelperEriditatiTT.setMailConfigs(list);
		    mailEreditatiTTConfigHelpers.add(mailConfigHelperEriditatiTT);
		}
	    }
	} else {
	    mailConfigHelper = new MailConfigHelper();
	    List<MailConfig> list = mailconfigService.findBySoftware(ORMHelper.getSoftware(), null);
	    mailConfigHelper.setCodiceSoftware(sw.getCodice());
	    mailConfigHelper.setSoftware(sw.getDescrizione());
	    mailConfigHelper.setMailConfigs(list);
	    mailConfigHelpers.add(mailConfigHelper);
	}
	model.addAttribute("mailEreditatiTTConfigHelpers", mailEreditatiTTConfigHelpers);
	model.addAttribute("mailConfigHelpers", mailConfigHelpers);
	model.addAttribute("sw", sw);
	return "mailconfig/list";
    }

    @RequestMapping
    public String create(Model model) {

	MailConfigCommand mailconfigCommand = new MailConfigCommand();
	Software sw = softwareService.findById(ORMHelper.getSoftware());
	MailConfig entity = new MailConfig();
	entity.setSoftware(sw);
	mailconfigCommand.setEntity(entity);
	fixRenderEntityProperty(mailconfigCommand.getEntity());
	model.addAttribute("mailconfig", mailconfigCommand);
	setPageAttributes(model);
	return "mailconfig/form";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	MailConfigCommand mailconfigCommand = new MailConfigCommand();
	MailConfig entity = mailconfigService.findById(id);
	mailconfigCommand.setEntity(entity);
	fixRenderEntityProperty(entity);
	model.addAttribute("mailconfig", mailconfigCommand);
	model.addAttribute("dettaglioMailConfig", true);
	setPageAttributes(model);
	return "mailconfig/form";
    }

    @RequestMapping
    public String insert(Model model, @ModelAttribute("mailconfig") MailConfigCommand mailconfig, BindingResult result, SessionStatus status) {

	fixMergeEntityProperty(mailconfig.getEntity());
	try {
	    MailConfig entity = mailconfig.getEntity();
	    if (StringUtils.isNotEmpty(mailconfig.getNewLoginpass())) {
		entity.setLoginpass(crypto.encrypt(CryptoUtils.DEFAULT_SECRET_KEY, mailconfig.getNewLoginpass()));
	    }
	    if (StringUtils.isNotEmpty(mailconfig.getNewInLoginpass())) {
		entity.setInLoginpass(crypto.encrypt(CryptoUtils.DEFAULT_SECRET_KEY, mailconfig.getNewInLoginpass()));
	    }
	    mailconfigService.insert(entity);
	    mailConfigComuniService.insert(entity);
	} catch (Exception e) {
	    copyErrorsToFlashMessages(mailconfig.getEntity(), true, "mailconfig", e);
	    return "redirect:create.htm?status_msg=03";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + mailconfig.getEntity().getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String update(Model model, @ModelAttribute("mailconfig") MailConfigCommand mailconfig, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	fixMergeEntityProperty(mailconfig.getEntity());
	try {
	    MailConfig entity = mailconfig.getEntity();
	    if (StringUtils.isNotEmpty(mailconfig.getNewLoginpass())) {
		entity.setLoginpass(crypto.encrypt(CryptoUtils.DEFAULT_SECRET_KEY, mailconfig.getNewLoginpass()));
	    }
	    if (StringUtils.isNotEmpty(mailconfig.getNewInLoginpass())) {
		entity.setInLoginpass(crypto.encrypt(CryptoUtils.DEFAULT_SECRET_KEY, mailconfig.getNewInLoginpass()));
	    }
	    mailconfigService.update(entity);
	} catch (Exception e) {
	    copyErrorsToFlashMessages(mailconfig.getEntity(), true, "mailconfig", e);
	    return "redirect:view.htm?codice=" + mailconfig.getEntity().getId().getCodice() + "&status_msg=03";
	}
	return "redirect:view.htm?codice=" + mailconfig.getEntity().getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String delete(Model model, @ModelAttribute("mailconfig") MailConfigCommand mailconfig, BindingResult result, SessionStatus status) {

	MailConfig objToDelete = mailconfigService.findById(mailconfig.getEntity().getId());
	Integer codice = objToDelete.getId().getCodice();
	try {
	    mailconfigService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToFlashMessages(mailconfig.getEntity(), true, "mailconfig", e);
	    return "redirect:view.htm?codice=" + codice + "&status_msg=03";
	}
	status.setComplete();
	return "redirect:list.htm";
    }

    @RequestMapping
    public void ajaxSendmail(@RequestParam("codice") Integer codice, HttpServletResponse response) throws IOException {

	try {
	    MailConfig mailConfig = mailconfigService.findById(new PkId(codice));
	    response.setContentType("text/plain");
	    response.getWriter().write(mailConfig.getDescrizione() + "#@#" + StringUtils.defaultIfEmpty(mailConfig.getSenderaddress(), ""));
	} catch (Exception e) {
	    response.setContentType("text/plain");
	    response.getWriter().write(e.getMessage());
	}
    }

    @RequestMapping
    public String ajaxDettaglioComuni(Model model, @RequestParam("codice") Integer codice, HttpServletResponse response) {

	List<MailConfigComuniDTO> mailConfigComuni = mailConfigComuniService.findByMailConfigId(codice);
	model.addAttribute("mailConfigComuni", mailConfigComuni);
	return "mailconfig/ajaxComuniAssociati";
    }

    @RequestMapping
    public void ajaxAssegnaComune(Model model, @RequestParam("codice") Integer codice, @RequestParam("codicecomune") String codiceComune,
	    HttpServletResponse response) throws Exception {

	List<Comuni> comuni = mailConfigComuniService.findComuniByMailConfigId(codice);
	String result = "inserito";
	boolean trovato = false;
	try {
	    for (Comuni comune : comuni) {
		if (StringUtils.equals(comune.getCodicecomune(), codiceComune)) {
		    trovato = true;
		    break;
		}
	    }
	    if (trovato) {
		result = "il comune è già associato all'account";
	    } else {
		MailConfigComuni entity = new MailConfigComuni();
		Comuni comune = new Comuni(codiceComune);
		MailConfig mailConfig = mailconfigService.findById(new PkId(codice));
		entity.setComune(comune);
		entity.setMailConfig(mailConfig);
		mailConfigComuniService.insert(entity);
	    }
	} catch (Exception e) {
	    result = "Si è verificato un errore durante l'inserimento del dato! (dettaglio: " + e.getMessage();
	}
	response.setContentType("text/plain");
	response.getOutputStream().write(result.getBytes());
    }

    @RequestMapping
    public void ajaxEliminaComune(@RequestParam("codice") Integer codice, HttpServletResponse response) throws Exception {

	String result = "eliminato";
	try {
	    mailConfigComuniService.delete(codice);
	} catch (Exception e) {
	    result = "Si è verificato un errore durante l'eliminazione del dato! (dettaglio: " + e.getMessage();
	}
	response.setContentType("text/plain");
	response.getOutputStream().write(result.getBytes());
    }

    @Override
    protected void fixMergeEntityProperty(MailConfig entity) {

    }

    @Override
    protected void fixRenderEntityProperty(MailConfig entity) {

	//	if (entity.getComuni() != null && org.apache.commons.lang.StringUtils.isBlank(entity.getComuni().getCodicecomune())) {
	//	    entity.setComuni(new Comuni());
	//	}
	if (entity.getComuni() == null) {
	    entity.setComuni(new Comuni());
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

	boolean isComuniAssociati = comuniassociatiService.isComuniassociati(ORMHelper.getIdcomune());
	model.addAttribute("isComuniAssociati", isComuniAssociati);
    }
}
