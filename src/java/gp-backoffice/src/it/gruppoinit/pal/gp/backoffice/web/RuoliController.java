package it.gruppoinit.pal.gp.backoffice.web;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.Amministrazioniruoli;
import it.gruppoinit.pal.gp.core.domain.AmministrazioniruoliId;
import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Responsabiliruoli;
import it.gruppoinit.pal.gp.core.domain.ResponsabiliruoliId;
import it.gruppoinit.pal.gp.core.domain.Ruoli;
import it.gruppoinit.pal.gp.core.domain.RuoliProtocollo;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.helper.CodiceDescrizioneBean;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.AmministrazioniService;
import it.gruppoinit.pal.gp.core.service.ComuniService;
import it.gruppoinit.pal.gp.core.service.RuoliUtentiEnum;
import it.gruppoinit.pal.gp.core.service.ResponsabiliService;
import it.gruppoinit.pal.gp.core.service.RuoliProtocolloService;
import it.gruppoinit.pal.gp.core.service.RuoliService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;
import it.gruppoinit.pal.gp.core.service.helper.RuoliProtocolloHelper;

@Controller
@SessionAttributes(value = { "ruolo" })
public class RuoliController extends BaseController<Ruoli> {

    @Autowired
    private RuoliService ruoliService;
    @Autowired
    private AmministrazioniService amministrazioniService;
    @Autowired
    private ResponsabiliService responsabiliService;
    @Autowired
    private VerticalizzazioniService verticalizzazioniService;
    @Autowired
    private RuoliProtocolloService ruoliProtocolloService;
    @Autowired
    private ComuniService comuniService;
    @Autowired
    private SoftwareService softwareService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	List<Ruoli> ruoliList = ruoliService.findAll(null, null);
	ModelMap model = new ModelMap(ruoliList);
	boolean export = createJMesaExport(request, response, ruoliList);
	if (export)
	    return null;
	model.addAttribute("ruoliList", ruoliList);
	return model;
    }

    @RequestMapping
    public String delete(Model model, @ModelAttribute("ruolo") Ruoli ruolo, BindingResult result, SessionStatus status) {

	Ruoli objToDelete = ruoliService.findById(ruolo.getId());
	try {
	    ruoliService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderEntityProperty(ruolo);
	    return "ruoli/form";
	}
	status.setComplete();
	return "redirect:list.htm";
    }

    @RequestMapping
    public String insert(@ModelAttribute("ruolo") Ruoli ruolo, BindingResult result, SessionStatus status) {

	fixMergeEntityProperty(ruolo);
	try {
	    ruoliService.insert(ruolo);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, ruolo, e);
	    fixRenderEntityProperty(ruolo);
	    return "ruoli/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + ruolo.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String update(@ModelAttribute("ruolo") Ruoli ruolo, BindingResult result, SessionStatus status, HttpServletRequest request) {

	fixMergeEntityProperty(ruolo);
	try {
	    ruoliService.update(ruolo);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, ruolo, e);
	    fixRenderEntityProperty(ruolo);
	    return "ruoli/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + ruolo.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String create(Model model) {

	Ruoli ruolo = new Ruoli();
	fixRenderEntityProperty(ruolo);
	model.addAttribute("ruolo", ruolo);
	setPageAttributes(model);
	return "ruoli/form";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	Ruoli ruolo = ruoliService.findById(id);
	fixRenderEntityProperty(ruolo);
	model.addAttribute("ruolo", ruolo);
	setPageAttributes(model);
	return "ruoli/form";
    }

    @RequestMapping
    public String ruoliResponsabili(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	Ruoli ruolo = ruoliService.findById(id);
	fixRenderEntityProperty(ruolo);
	model.addAttribute("ruolo", ruolo);
	setPageAttributes(model);
	return "ruoli/form";
    }

    @RequestMapping
    public String createRuoliResponsabili(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	Ruoli ruolo = ruoliService.findById(id);
	List<Responsabili> responsabili = responsabiliService.findAllByAbilitati();
	Set<Responsabiliruoli> ruolisResp = ruolo.getResponsabiliruolis();
	for (Responsabili responsabile : responsabili) {
	    for (Responsabiliruoli responsabiliruoli : ruolisResp) {
		Responsabili resp = responsabiliruoli.getResponsabile();
		if (responsabile.getId().getCodice().compareTo(resp.getId().getCodice()) == 0) {
		    responsabile.setResponsabileRuoloTransient(true);
		    break;
		}
	    }
	}
	fixRenderEntityProperty(ruolo);
	model.addAttribute("ruolo", ruolo);
	model.addAttribute("responsabili", responsabili);
	setPageAttributes(model);
	return "ruoli/ruoliresponsabili";
    }

    @RequestMapping
    public String saveRuoliResponsabili(@ModelAttribute("ruolo") Ruoli entity, BindingResult result, SessionStatus status, Model model,
	    HttpServletRequest request) {

	PkId idRuolo = new PkId(entity.getId().getCodice());
	Ruoli ruolo = ruoliService.findById(idRuolo);
	fixMergeEntityProperty(ruolo);
	String[] responsabilisId = request.getParameterValues("responsabilis");
	Set<Responsabiliruoli> responsabiliruolis = new HashSet<Responsabiliruoli>();
	if (responsabilisId != null) {
	    for (int i = 0; i < responsabilisId.length; i++) {
		Responsabili resp = responsabiliService.findById(new PkId(Integer.parseInt(responsabilisId[i])));
		ResponsabiliruoliId id = new ResponsabiliruoliId();
		id.setCodiceresponsabile(resp.getId().getCodice());
		id.setIdruolo(ruolo.getId().getCodice());
		id.setIdcomune(ORMHelper.getIdcomune());
		Responsabiliruoli responsabiliruoli = new Responsabiliruoli();
		responsabiliruoli.setResponsabile(resp);
		responsabiliruoli.setRuolo(ruolo);
		responsabiliruoli.setId(id);
		responsabiliruolis.add(responsabiliruoli);
	    }
	}
	try {
	    ruoliService.saveRuoliResponsabili(ruolo, responsabiliruolis);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, ruolo, e);
	    fixRenderEntityProperty(ruolo);
	    return "ruoli/ruoliresponsabili";
	}
	status.setComplete();
	return "redirect:createRuoliResponsabili.htm?codice=" + ruolo.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String createRuoliAmministrazioni(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	Ruoli ruolo = ruoliService.findById(id);
	List<Amministrazioni> amministrazioni = amministrazioniService.findByAmministrazioniInterne();
	Set<Amministrazioniruoli> ruolisAmm = ruolo.getRuoliamministrazionis();
	for (Amministrazioni amministrazione : amministrazioni) {
	    for (Amministrazioniruoli amministrazioniruoli : ruolisAmm) {
		Amministrazioni amm = amministrazioniruoli.getAmministrazioni();
		if (amministrazione.getId().getCodice().compareTo(amm.getId().getCodice()) == 0) {
		    amministrazione.setAmministrazioneRuoloTransient(true);
		    break;
		}
	    }
	}
	fixRenderEntityProperty(ruolo);
	model.addAttribute("ruolo", ruolo);
	model.addAttribute("amministrazioni", amministrazioni);
	setPageAttributes(model);
	return "ruoli/ruoliamministrazioni";
    }

    @RequestMapping
    public String saveRuoliAmministrazioni(@ModelAttribute("ruolo") Ruoli entity, BindingResult result, SessionStatus status, Model model,
	    HttpServletRequest request) {

	PkId idRuolo = new PkId(entity.getId().getCodice());
	Ruoli ruolo = ruoliService.findById(idRuolo);
	fixMergeEntityProperty(ruolo);
	String[] amministrazioniId = request.getParameterValues("amministrazionis");
	Set<Amministrazioniruoli> amministrazioniruolis = new HashSet<Amministrazioniruoli>();
	if (amministrazioniId != null) {
	    for (int i = 0; i < amministrazioniId.length; i++) {
		Amministrazioni amm = amministrazioniService.findById(new PkId(Integer.parseInt(amministrazioniId[i])));
		AmministrazioniruoliId id = new AmministrazioniruoliId();
		id.setCodiceamministrazione(amm.getId().getCodice());
		id.setIdruolo(ruolo.getId().getCodice());
		id.setIdcomune(ORMHelper.getIdcomune());
		Amministrazioniruoli amministrazioniruoli = new Amministrazioniruoli();
		amministrazioniruoli.setAmministrazioni(amm);
		amministrazioniruoli.setRuoli(ruolo);
		amministrazioniruoli.setId(id);
		amministrazioniruolis.add(amministrazioniruoli);
	    }
	}
	try {
	    ruoliService.saveRuoliAmministrazioni(ruolo, amministrazioniruolis);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, ruolo, e);
	    fixRenderEntityProperty(ruolo);
	    return "ruoli/ruoliamministrazioni";
	}
	status.setComplete();
	return "redirect:createRuoliAmministrazioni.htm?codice=" + ruolo.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String configurazioniProtocollo(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	Ruoli ruolo = ruoliService.findById(id);
	fixRenderEntityProperty(ruolo);
	model.addAttribute("ruolo", ruolo);
	List<RuoliProtocolloHelper> h = ruoliProtocolloService.findHelperByRuolo(codice);
	model.addAttribute("helpers", h);
	setPageAttributes(model);
	return "ruoli/configurazioniprotocollo";
    }

    @RequestMapping
    public String saveConfigruazioniProtocollo(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	Ruoli ruolo = ruoliService.findById(id);
	String[] rifs = request.getParameterValues("rif");
	List<RuoliProtocollo> rps = new ArrayList<RuoliProtocollo>();
	for (String rif : rifs) {
	    String value = request.getParameter(rif);
	    if (StringUtils.isNotBlank(value)) {
		RuoliProtocollo rp = new RuoliProtocollo();
		String vs[] = rif.split("_");
		String codiceComune = vs[0];
		String software = vs[1];
		Comuni c = comuniService.findById(codiceComune);
		Software s = softwareService.findById(software);
		rp.setComune(c);
		rp.setSoftware(s);
		rp.setRuolo(ruolo);
		rp.setRuoloExt(value);
		rps.add(rp);
	    }
	}
	if (rps.size() > 0) {
	    ruoliProtocolloService.insertRuoliProtocollo(codice, rps);
	}
	return "redirect:../ruoli/configurazioniProtocollo.htm?codice=" + codice + "&status_msg=01";
    }

    @Override
    protected void fixMergeEntityProperty(Ruoli entity) {

    }

    @Override
    protected void fixRenderEntityProperty(Ruoli entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

	RuoliUtentiEnum[] listaRuoli = RuoliUtentiEnum.values();
	List<CodiceDescrizioneBean> cdbs = new ArrayList<CodiceDescrizioneBean>();
	for (RuoliUtentiEnum r : listaRuoli) {
	    CodiceDescrizioneBean cdb = new CodiceDescrizioneBean();
	    cdb.setCodice(r.name());
	    cdb.setDescrizione(r.getDescrizione());
	    cdbs.add(cdb);
	}
	model.addAttribute("listaRuoli", cdbs);
	boolean docERAttivo = verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_PROTOCOLLO_DOCER);
	model.addAttribute("isDocErAttivo", docERAttivo);
    }
}
