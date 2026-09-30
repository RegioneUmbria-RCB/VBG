package it.gruppoinit.pal.gp.backoffice.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
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
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.MailConfig;
import it.gruppoinit.pal.gp.core.domain.Mailtipo;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Responsabilicomuni;
import it.gruppoinit.pal.gp.core.domain.Tipimovimento;
import it.gruppoinit.pal.gp.core.domain.TipimovimentoComunicazioni;
import it.gruppoinit.pal.gp.core.domain.TipimovimentoId;
import it.gruppoinit.pal.gp.core.domain.helper.DestinatariHelper;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.domain.web.TipimovimentoComunicazioniCommand;
import it.gruppoinit.pal.gp.core.service.MailConfigService;
import it.gruppoinit.pal.gp.core.service.ResponsabilicomuniService;
import it.gruppoinit.pal.gp.core.service.TipiMovimentoService;
import it.gruppoinit.pal.gp.core.service.TipimovimentoComunicazioniService;

/**
 * 
 * @author
 */
@Controller
@SessionAttributes("tipimovimentocomunicazioni")
public class TipimovimentoComunicazioniController extends BaseController<TipimovimentoComunicazioni> {

    @Autowired
    private TipimovimentoComunicazioniService tipimovimentocomunicazioniService;
    @Autowired
    private TipiMovimentoService tipiMovimentoService;
    @Autowired
    private MailConfigService mailConfigService;
    @Autowired
    private ResponsabilicomuniService responsabiliComuniService;

    @RequestMapping
    public ModelMap list(@RequestParam("codiceTipomov") String codiceTipmov, HttpServletRequest request, HttpServletResponse response) {

	Tipimovimento tipomovimento = tipiMovimentoService.findById(new TipimovimentoId(codiceTipmov));
	List<TipimovimentoComunicazioni> tipimovimentocomunicazioniList = tipimovimentocomunicazioniService.findByTipoMov(codiceTipmov);
	ModelMap model = new ModelMap(tipimovimentocomunicazioniList);
	boolean export = createJMesaExport(request, response, tipimovimentocomunicazioniList);
	if (export) {
	    return null;
	}
	model.addAttribute("tipimovimentocomunicazioniList", tipimovimentocomunicazioniList);
	model.addAttribute("tipimovimento", tipomovimento);
	return model;
    }

    @RequestMapping
    public String create(@RequestParam("codiceTipomov") String codiceTipmov, Model model) {

	TipimovimentoComunicazioniCommand tipimovimentocomunicazioni = new TipimovimentoComunicazioniCommand();
	TipimovimentoComunicazioni entity = new TipimovimentoComunicazioni();
	Tipimovimento tipomovimento = tipiMovimentoService.findById(new TipimovimentoId(codiceTipmov));
	entity.setFunzione(WebConstants.COMUNICAZIONI_TIPO_MOV_DOPO_FIRMA_DOC);
	entity.setTipimovimento(tipomovimento);
	tipimovimentocomunicazioni.setEntity(entity);
	List<MailConfig> listMailConfig = getListMailConfig();
	fixRenderEntityProperty(tipimovimentocomunicazioni.getEntity());
	model.addAttribute("tipimovimentocomunicazioni", tipimovimentocomunicazioni);
	model.addAttribute("listMailConfig", listMailConfig);
	setPageAttributes(model);
	prepareModel(model, tipimovimentocomunicazioni);
	return "tipimovimentocomunicazioni/form";
    }

    private List<MailConfig> getListMailConfig() {

	Responsabili responsabili = getCurrentlyAuthenticatedUserDetails();
	List<Responsabilicomuni> responsabilicomuni = responsabiliComuniService.findByOperatore(responsabili);
	String[] codiceComuni = new String[responsabilicomuni.size()];
	int i = 0;
	for (Responsabilicomuni responsabilicomune : responsabilicomuni) {
	    codiceComuni[i] = responsabilicomune.getComune().getCodicecomune();
	    i++;
	}
	List<MailConfig> listMailConfig = mailConfigService.findForInvioEmailBySoftwareAndCodiceComuneAbilitati(ORMHelper.getSoftware(),
		codiceComuni);
	return listMailConfig;
    }

    @RequestMapping
    public String insert(Model model, @ModelAttribute("tipimovimentocomunicazioni") TipimovimentoComunicazioniCommand tipimovimentocomunicazioni,
	    BindingResult result, SessionStatus status) {

	fixMergeEntityProperty(tipimovimentocomunicazioni.getEntity());
	try {
	    tipimovimentocomunicazioniService.insert(tipimovimentocomunicazioni.getEntity());
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, tipimovimentocomunicazioni.getEntity(), true, e);
	    prepareModel(model, tipimovimentocomunicazioni);
	    fixRenderEntityProperty(tipimovimentocomunicazioni.getEntity());
	    return "tipimovimentocomunicazioni/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + tipimovimentocomunicazioni.getEntity().getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	TipimovimentoComunicazioniCommand tipimovimentocomunicazioni = new TipimovimentoComunicazioniCommand();
	PkId id = new PkId(codice);
	TipimovimentoComunicazioni entity = tipimovimentocomunicazioniService.findById(id);
	tipimovimentocomunicazioni.setEntity(entity);
	fixRenderEntityProperty(tipimovimentocomunicazioni.getEntity());
	List<MailConfig> listMailConfig = getListMailConfig();
	model.addAttribute("tipimovimentocomunicazioni", tipimovimentocomunicazioni);
	model.addAttribute("listMailConfig", listMailConfig);
	prepareModel(model, tipimovimentocomunicazioni);
	return "tipimovimentocomunicazioni/form";
    }

    @RequestMapping
    public String update(Model model, @ModelAttribute("tipimovimentocomunicazioni") TipimovimentoComunicazioniCommand tipimovimentocomunicazioni,
	    BindingResult result, SessionStatus status, HttpServletRequest request) {

	fixMergeEntityProperty(tipimovimentocomunicazioni.getEntity());
	try {
	    tipimovimentocomunicazioniService.update(tipimovimentocomunicazioni.getEntity());
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, tipimovimentocomunicazioni.getEntity(), true, e);
	    prepareModel(model, tipimovimentocomunicazioni);
	    fixRenderEntityProperty(tipimovimentocomunicazioni.getEntity());
	    return "tipimovimentocomunicazioni/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + tipimovimentocomunicazioni.getEntity().getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String delete(Model model, @ModelAttribute("tipimovimentocomunicazioni") TipimovimentoComunicazioniCommand tipimovimentocomunicazioni,
	    BindingResult result, SessionStatus status) {

	TipimovimentoComunicazioni objToDelete = tipimovimentocomunicazioniService.findById(tipimovimentocomunicazioni.getEntity().getId());
	try {
	    tipimovimentocomunicazioniService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, true, e);
	    prepareModel(model, tipimovimentocomunicazioni);
	    fixRenderEntityProperty(tipimovimentocomunicazioni.getEntity());
	    return "tipimovimentocomunicazioni/form";
	}
	status.setComplete();
	return "redirect:list.htm?codiceTipomov=" + objToDelete.getTipimovimento().getId().getTipomovimento();
    }

    @Override
    protected void fixMergeEntityProperty(TipimovimentoComunicazioni entity) {

    }

    @Override
    protected void fixRenderEntityProperty(TipimovimentoComunicazioni entity) {

	if (EntityUtils.getNestedProperty(entity.getMailtipo(), "id.codice") == null) {
	    entity.setMailtipo(new Mailtipo());
	}
	if (EntityUtils.getNestedProperty(entity.getTipimovimento(), "id.tipomovimento") == null) {
	    entity.setTipimovimento(new Tipimovimento());
	}
	if (EntityUtils.getNestedProperty(entity.getMailConfig(), "id.codice") == null) {
	    entity.setMailConfig(new MailConfig());
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

    }

    private void prepareModel(Model model, TipimovimentoComunicazioniCommand tipimovimentocomunicazioni) {

	String codiceDest = "";
	if (StringUtils.isNotBlank(tipimovimentocomunicazioni.getEntity().getDestinatariMail())) {
	    codiceDest = tipimovimentocomunicazioni.getEntity().getDestinatariMail();
	}
	List<DestinatariHelper> destinatariHelpers = DestinatariHelper.getTipiDestinatari(codiceDest);
	model.addAttribute("destinatariHelpers", destinatariHelpers);
    }
}
