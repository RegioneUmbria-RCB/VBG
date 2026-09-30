package it.gruppoinit.pal.gp.backoffice.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.BooleanUtils;
import org.jmesa.web.GenerateTable;
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

import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.ComunicazioniT;
import it.gruppoinit.pal.gp.core.domain.ComunicazioniTMercato;
import it.gruppoinit.pal.gp.core.domain.Letteretipo;
import it.gruppoinit.pal.gp.core.domain.Mailtipo;
import it.gruppoinit.pal.gp.core.domain.Mercati;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipimovimento;
import it.gruppoinit.pal.gp.core.domain.TipoComunicazioniT;
import it.gruppoinit.pal.gp.core.domain.helper.ComunicazioniDConcessioniDTO;
import it.gruppoinit.pal.gp.core.domain.web.ComunicazioniTMercatoCommand;
import it.gruppoinit.pal.gp.core.features.firmadigitale.DocumentiDaFirmareService;
import it.gruppoinit.pal.gp.core.jmesa.ComunicazioniDConcessioniTable;
import it.gruppoinit.pal.gp.core.service.ComunicazioniDConcessioniService;
import it.gruppoinit.pal.gp.core.service.ComunicazioniDService;
import it.gruppoinit.pal.gp.core.service.ComunicazioniManagerService;
import it.gruppoinit.pal.gp.core.service.ComunicazioniTMercatoService;
import it.gruppoinit.pal.gp.core.service.ComunicazioniTService;
import it.gruppoinit.pal.gp.core.service.TipoComunicazioniTService;
import it.gruppoinit.pal.gp.core.service.helper.ComunicazioniConcessioneServiceHelper;
import it.gruppoinit.pal.gp.core.service.helper.ComunicazioniServiceHelper;

/**
 * 
 * @author
 */
@Controller
@SessionAttributes("comunicazionitmercato")
public class ComunicazioniTMercatoController extends BaseController<ComunicazioniTMercato> {

    @Autowired
    private ComunicazioniTMercatoService comunicazionitmercatoService;
    @Autowired
    private ComunicazioniTService comunicazioniTService;
    @Autowired
    private ComunicazioniDService comunicazioniDService;
    @Autowired
    private DocumentiDaFirmareService documentiDaFirmareService;
    @Autowired
    private ComunicazioniManagerService comunicazioniManagerService;
    @Autowired
    private ComunicazioniDConcessioniService comunicazioniDConcessioniService;
    @Autowired
    private TipoComunicazioniTService tipoComunicazioniTService;

    @RequestMapping
    public ModelMap list(@RequestParam("codicemercato") Integer codiceMercato, HttpServletRequest request, HttpServletResponse response) {

	List<ComunicazioniTMercato> comunicazionitmercatoList = comunicazionitmercatoService.findByCodiceMercato(codiceMercato);
	ModelMap model = new ModelMap(comunicazionitmercatoList);
	boolean export = createJMesaExport(request, response, comunicazionitmercatoList);
	if (export) {
	    return null;
	}
	model.addAttribute("comunicazionitmercatoList", comunicazionitmercatoList);
	model.addAttribute("codmercato", codiceMercato);
	return model;
    }

    @RequestMapping
    public String create(Model model) {

	ComunicazioniTMercato comunicazionitmercato = new ComunicazioniTMercato();
	fixRenderEntityProperty(comunicazionitmercato);
	model.addAttribute("comunicazionitmercato", comunicazionitmercato);
	setPageAttributes(model);
	return "comunicazionitmercato/form";
    }

    @RequestMapping
    public String insert(@ModelAttribute("comunicazionitmercato") ComunicazioniTMercato comunicazionitmercato, BindingResult result,
	    SessionStatus status) {

	fixMergeEntityProperty(comunicazionitmercato);
	try {
	    comunicazionitmercatoService.insert(comunicazionitmercato);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, comunicazionitmercato, e);
	    fixRenderEntityProperty(comunicazionitmercato);
	    return "comunicazionitmercato/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + comunicazionitmercato.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request, HttpServletResponse response) {

	PkId id = new PkId(codice);
	ComunicazioniTMercatoCommand comunicazionitmercato = new ComunicazioniTMercatoCommand();
	ComunicazioniTMercato entity = comunicazionitmercatoService.findById(id);
	setAttributeViewPage(model, comunicazionitmercato, entity, request, response);
	//	ComunicazioniT comunicazioniT = comunicazioniTService.findById(new PkId(entity.getComunicazioniT().getId().getCodice()));
	//	comunicazionitmercato.setComunicazioniT(comunicazioniT);
	//	fixRenderEntityProperty(entity);
	//	fixRenderEntityProperty(comunicazioniT);
	//	comunicazionitmercato.setEntity(entity);
	//	model.addAttribute("comunicazionitmercato", comunicazionitmercato);
	//	//
	//	String listaFirmatariContent = comunicazioniTService.findListaFirmatariToHtml(comunicazioniT.getId().getCodice());
	//	//
	//	boolean isMettiallafirma = BooleanUtils.isTrue(comunicazioniT.getFlagMettiallafirma());
	//	GenerateTable<ComunicazioniDConcessioniDTO> table = new ComunicazioniDConcessioniTable(comunicazioniT.getId().getCodice(), isMettiallafirma,
	//		comunicazioniDConcessioniService, documentiDaFirmareService);
	//	String htmlTable = table.createJMesaList(request, response, "label.lista_comunicazioni.title", "comunicazionid_id", false);
	//	if (htmlTable == null) {
	//	    return null;
	//	}
	//	model.addAttribute("htmltable", htmlTable);
	//	model.addAttribute("listaFirmatariContent", listaFirmatariContent);
	setPageAttributes(model);
	return "comunicazionitmercato/form";
    }

    private String setAttributeViewPage(Model model, ComunicazioniTMercatoCommand comunicazionitmercato, ComunicazioniTMercato entity,
	    HttpServletRequest request, HttpServletResponse response) {

	ComunicazioniT comunicazioniT = comunicazioniTService.findById(new PkId(entity.getComunicazioniT().getId().getCodice()));
	comunicazionitmercato.setComunicazioniT(comunicazioniT);
	fixRenderEntityProperty(entity);
	fixRenderEntityProperty(comunicazioniT);
	comunicazionitmercato.setEntity(entity);
	model.addAttribute("comunicazionitmercato", comunicazionitmercato);
	//
	String listaFirmatariContent = comunicazioniTService.findListaFirmatariToHtml(comunicazioniT.getId().getCodice());
	//
	boolean isMettiallafirma = BooleanUtils.isTrue(comunicazioniT.getFlagMettiallafirma());
	GenerateTable<ComunicazioniDConcessioniDTO> table = new ComunicazioniDConcessioniTable(comunicazioniT.getId().getCodice(), isMettiallafirma,
		comunicazioniDConcessioniService, documentiDaFirmareService);
	String htmlTable = table.createJMesaList(request, response, "label.lista_comunicazioni.title", "comunicazionid_id", false);
	if (htmlTable == null) {
	    return null;
	}
	model.addAttribute("htmltable", htmlTable);
	model.addAttribute("listaFirmatariContent", listaFirmatariContent);
	return null;
    }

    @RequestMapping
    public String update(Model model, @ModelAttribute("comunicazionitmercato") ComunicazioniTMercatoCommand comunicazionitmercato,
	    BindingResult result, SessionStatus status, HttpServletRequest request, HttpServletResponse response) {

	fixMergeEntityProperty(comunicazionitmercato.getEntity());
	try {
	    comunicazionitmercatoService.updateComunicazione(comunicazionitmercato.getEntity(), comunicazionitmercato.getComunicazioniT(),
		    comunicazionitmercato.getFlagbloccaconfigurazione());
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, comunicazionitmercato, true, e);
	    ComunicazioniTMercato entity = comunicazionitmercatoService.findById(new PkId(comunicazionitmercato.getEntity().getId().getCodice()));
	    setAttributeViewPage(model, comunicazionitmercato, entity, request, response);
	    fixRenderEntityProperty(comunicazionitmercato.getEntity());
	    return "comunicazionitmercato/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + comunicazionitmercato.getEntity().getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String elaboraComunicazione(@ModelAttribute("comunicazionitmercato") ComunicazioniTMercatoCommand comunicazionitmercato,
	    BindingResult result, SessionStatus status, HttpServletRequest request) {

	fixMergeEntityProperty(comunicazionitmercato.getEntity());
	try {
	    ComunicazioniServiceHelper comunicazioniServiceHelper = new ComunicazioniConcessioneServiceHelper(comunicazioniManagerService,
		    comunicazioniDConcessioniService);
	    comunicazioniServiceHelper.elaboraComunicazione(comunicazionitmercato.getComunicazioniT());
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, comunicazionitmercato, true, e);
	    fixRenderEntityProperty(comunicazionitmercato.getEntity());
	    return "comunicazionitmercato/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + comunicazionitmercato.getEntity().getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String delete(@ModelAttribute("comunicazionitmercato") ComunicazioniTMercatoCommand comunicazionitmercato, BindingResult result,
	    SessionStatus status) {

	ComunicazioniTMercato objToDelete = comunicazionitmercatoService.findById(comunicazionitmercato.getEntity().getId());
	try {
	    comunicazionitmercatoService.deleteComunicazione(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, true, e);
	    fixRenderEntityProperty(comunicazionitmercato.getEntity());
	    return "comunicazionitmercato/form";
	}
	status.setComplete();
	return "redirect:list.htm?codicemercato=" + comunicazionitmercato.getEntity().getMercati().getId().getCodice() + "&status_msg=02";
    }

    @Override
    protected void fixMergeEntityProperty(ComunicazioniTMercato entity) {

    }

    @Override
    protected void fixRenderEntityProperty(ComunicazioniTMercato entity) {

	if (entity.getComunicazioniT() == null) {
	    entity.setComunicazioniT(new ComunicazioniT());
	}
	if (entity.getMercati() == null) {
	    entity.setMercati(new Mercati());
	}
	if (entity.getComunicazioniT() != null) {
	    if (entity.getComunicazioniT().getAmministrazioni() == null) {
		entity.getComunicazioniT().setAmministrazioni(new Amministrazioni());
	    }
	    if (entity.getComunicazioniT().getLetteretipo() == null) {
		entity.getComunicazioniT().setLetteretipo(new Letteretipo());
	    }
	    if (entity.getComunicazioniT().getMailtipo() == null) {
		entity.getComunicazioniT().setMailtipo(new Mailtipo());
	    }
	    if (entity.getComunicazioniT().getTipimovimento() == null) {
		entity.getComunicazioniT().setTipimovimento(new Tipimovimento());
	    }
	    if (entity.getComunicazioniT().getTipoComunicazioniT() == null) {
		entity.getComunicazioniT().setTipoComunicazioniT(new TipoComunicazioniT());
	    }
	}
    }

    protected void fixRenderEntityProperty(ComunicazioniT entity) {

	if (entity.getAmministrazioni() == null) {
	    entity.setAmministrazioni(new Amministrazioni());
	}
	if (entity.getLetteretipo() == null) {
	    entity.setLetteretipo(new Letteretipo());
	}
	if (entity.getMailtipo() == null) {
	    entity.setMailtipo(new Mailtipo());
	}
	if (entity.getTipimovimento() == null) {
	    entity.setTipimovimento(new Tipimovimento());
	}
	if (entity.getTipoComunicazioniT() == null) {
	    entity.setTipoComunicazioniT(new TipoComunicazioniT());
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
