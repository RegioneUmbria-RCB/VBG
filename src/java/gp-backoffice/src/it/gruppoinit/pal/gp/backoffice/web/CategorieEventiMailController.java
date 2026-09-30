package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.CategorieEventiMail;
import it.gruppoinit.pal.gp.core.domain.Categorieeventibase;
import it.gruppoinit.pal.gp.core.domain.MailConfig;
import it.gruppoinit.pal.gp.core.domain.Mailtipo;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Responsabilicomuni;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.features.mailtipo.MailtipoService;
import it.gruppoinit.pal.gp.core.service.CategorieEventiMailService;
import it.gruppoinit.pal.gp.core.service.CategorieeventibaseService;
import it.gruppoinit.pal.gp.core.service.MailConfigService;
import it.gruppoinit.pal.gp.core.service.ResponsabilicomuniService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

@Controller
@SessionAttributes("categorieEventiMail")
public class CategorieEventiMailController extends BaseController<CategorieEventiMail> {
    
    private static final Logger log = LoggerFactory.getLogger(CategorieEventiMailController.class);

    @Autowired
    private CategorieeventibaseService categorieeventibaseService;
    @Autowired
    private CategorieEventiMailService categorieEventiMailService;
    @Autowired
    private MailConfigService mailConfigService;
    @Autowired
    private MailtipoService mailtipoService;
    @Autowired
    private ResponsabilicomuniService responsabiliComuniService;
    @Autowired
    private SoftwareService softwareService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	List<CategorieEventiMail> categorieEventiMailList = categorieEventiMailService.findAll(null, null);
	ModelMap model = new ModelMap(categorieEventiMailList);
	boolean export = createJMesaExport(request, response, categorieEventiMailList);
	if (export) {
	    return null;
	}
	model.addAttribute("categorieEventiMailList", categorieEventiMailList);
	return model;
    }

    @RequestMapping
    public String create(Model model) {

	CategorieEventiMail categorieEventiMail = new CategorieEventiMail();
	fixRenderEntityProperty(categorieEventiMail);
	model.addAttribute("categorieEventiMail", categorieEventiMail);	
	setPageAttributes(model);
	return "categorieeventimail/form";
    }
    @RequestMapping
    public String createCategorieEventi(Model model, @ModelAttribute("categorieEventiMail") CategorieEventiMail categorieEventiMail, HttpServletRequest request){
	
	String sftwr = request.getParameter("cemsftw");
	if(!StringUtils.isBlank(sftwr)){
	    
	    if("azzera".equals(sftwr)){
		categorieEventiMail.setSoftware(null);
	    }else{
		Software softwareentity = softwareService.findById(sftwr);
		if (softwareentity == null) {
		    throw new RuntimeException("Software non identificato");
		}
		categorieEventiMail.setSoftware(softwareentity);
		if (categorieEventiMail.getMailtipo() != null && categorieEventiMail.getMailtipo().getId() != null
			&& categorieEventiMail.getMailtipo().getId().getCodice() != null) {
		    Mailtipo mailtipo = mailtipoService.findById(new PkId(categorieEventiMail.getMailtipo().getId().getCodice()));
		    if (mailtipo.getSoftware() != null && !"TT".equals(mailtipo.getSoftware().getCodice())
			    && !sftwr.equals(mailtipo.getSoftware().getCodice())) {
			categorieEventiMail.setMailtipo(null);
		    }
		}
		if (categorieEventiMail.getMailConfig() != null && categorieEventiMail.getMailConfig().getId() != null
			&& categorieEventiMail.getMailConfig().getId().getCodice() != null) {
		    MailConfig mailconfig = mailConfigService.findById(new PkId(categorieEventiMail.getMailConfig().getId().getCodice()));
		    if (mailconfig.getSoftware() != null && !"TT".equals(mailconfig.getSoftware().getCodice())
			    && !sftwr.equals(mailconfig.getSoftware().getCodice())) {
			categorieEventiMail.setMailConfig(null);
		    }
		}
	    }	    	    
	}
	
	fixRenderEntityProperty(categorieEventiMail);
	setPageAttributes(model);
	return "categorieeventimail/form";
    }
        

    @RequestMapping
    public String insert(Model model, @ModelAttribute("categorieEventiMail") CategorieEventiMail categorieEventiMail, BindingResult result,
	    SessionStatus status) {	
	fixMergeEntityProperty(categorieEventiMail);
	try {
	    if(categorieEventiMail.getSoftware() != null &&  StringUtils.isBlank(categorieEventiMail.getSoftware().getCodice())){
		categorieEventiMail.setSoftware(null);
	    }
	    categorieEventiMailService.insert(categorieEventiMail);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, categorieEventiMail, e);
	    setPageAttributes(model);
	    fixRenderEntityProperty(categorieEventiMail);
	    return "categorieeventimail/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + categorieEventiMail.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	CategorieEventiMail categorieEventiMail = categorieEventiMailService.findById(id);
	
	String sftwr = request.getParameter("cemsftw");
	if(!StringUtils.isBlank(sftwr)){	    
	    if("azzera".equals(sftwr)){
		categorieEventiMail.setSoftware(null);
	    }else{
		Software softwareentity = softwareService.findById(sftwr);
		if (softwareentity == null) {
		    throw new RuntimeException("Software non identificato");
		}
		categorieEventiMail.setSoftware(softwareentity);
		if (categorieEventiMail.getMailtipo() != null && categorieEventiMail.getMailtipo().getSoftware() != null
			&& !"TT".equals(categorieEventiMail.getMailtipo().getSoftware().getCodice())
			&& !sftwr.equals(categorieEventiMail.getMailtipo().getSoftware().getCodice())) {
		    categorieEventiMail.setMailtipo(null);
		}
		if (categorieEventiMail.getMailConfig() != null && categorieEventiMail.getMailConfig().getSoftware() != null
			&& !"TT".equals(categorieEventiMail.getMailConfig().getSoftware().getCodice())
			&& !sftwr.equals(categorieEventiMail.getMailConfig().getSoftware().getCodice())) {
		    categorieEventiMail.setMailConfig(null);
		}
	    }	    	    	    
	}		
	fixRenderEntityProperty(categorieEventiMail);
	model.addAttribute("categorieEventiMail", categorieEventiMail);
	setPageAttributes(model);
	return "categorieeventimail/form";
    }

    @RequestMapping
    public String update(Model model, @ModelAttribute("categorieEventiMail") CategorieEventiMail categorieEventiMail, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {
	
	fixMergeEntityProperty(categorieEventiMail);
	try {
	    if(categorieEventiMail.getSoftware() != null &&  StringUtils.isBlank(categorieEventiMail.getSoftware().getCodice())){
		categorieEventiMail.setSoftware(null);
	    }
	    categorieEventiMailService.update(categorieEventiMail);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, categorieEventiMail, e);
	    setPageAttributes(model);
	    fixRenderEntityProperty(categorieEventiMail);
	    return "categorieeventimail/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + categorieEventiMail.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String delete(Model model, @ModelAttribute("categorieEventiMail") CategorieEventiMail categorieEventiMail, BindingResult result,
	    SessionStatus status) {

	CategorieEventiMail objToDelete = categorieEventiMailService.findById(categorieEventiMail.getId());
	try {
	    categorieEventiMailService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    setPageAttributes(model);
	    fixRenderEntityProperty(categorieEventiMail);
	    return "categorieeventimail/form";
	}
	status.setComplete();
	return "redirect:list.htm";
    }

    @Override
    protected void setPageAttributes(Model model) {

	List<Categorieeventibase> s = categorieeventibaseService.findAll(null, null);
	model.addAttribute("categorieeventibase", s);
	Set<String> destinatari = new HashSet<String>();
	destinatari.add(CategorieEventiMailService.TIPO_DESTINATARIO.OPERATORE.name());
	destinatari.add(CategorieEventiMailService.TIPO_DESTINATARIO.RESPONSABILEPROC.name());
	destinatari.add(CategorieEventiMailService.TIPO_DESTINATARIO.ISTRUTTORE.name());
	model.addAttribute("destinataris", destinatari);
	Map<String, String> triggerType = new HashMap<String, String>();
	triggerType.put("contains", "contiene");
	triggerType.put("startsWith", "inizia con");
	triggerType.put("endsWith", "finisce con");
	model.addAttribute("triggerTypes", triggerType);
	model.addAttribute("listMailConfig", getListMailConfig(model));
    }
    
    private List<MailConfig> getListMailConfig(Model model) {
	
	try{
	   CategorieEventiMail cem = (CategorieEventiMail)model.asMap().get("categorieEventiMail");
	   List<MailConfig> list; 
	   if(cem.getSoftware() != null && !StringUtils.isBlank(cem.getSoftware().getCodice())){
	       list = new ArrayList<MailConfig>();
	       list.addAll(mailConfigService.findBySoftware(cem.getSoftware().getCodice(), null));
	       if(!"TT".equals(cem.getSoftware().getCodice())){
		   list.addAll(mailConfigService.findBySoftware("TT", null));
	       }
	   }else{
	       list = mailConfigService.findAll(null, null);
	   }
	   return list;
	}catch(Exception e){
	    log.error("errore durante mailConfigService.findAll", e);
	    return new ArrayList<MailConfig>();
	}

    }

    @Override
    protected void fixMergeEntityProperty(CategorieEventiMail entity) {

    }

    @Override
    protected void fixRenderEntityProperty(CategorieEventiMail entity) {

	if (entity.getCategorieeventibase() == null) {
	    entity.setCategorieeventibase(new Categorieeventibase());
	}
	if (entity.getMailtipo() == null) {
	    entity.setMailtipo(new Mailtipo());
	}
	if(entity.getMailConfig() == null) {
	    entity.setMailConfig(new MailConfig());
	}
	if(entity.getSoftware() == null){
	    entity.setSoftware(new Software());
	    entity.getSoftware().setCodice(null);
	}
    }
}
