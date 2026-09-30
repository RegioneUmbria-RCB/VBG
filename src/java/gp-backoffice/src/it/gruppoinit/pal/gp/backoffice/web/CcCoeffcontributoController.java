package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Aree;
import it.gruppoinit.pal.gp.core.domain.CcCoeffcontributo;
import it.gruppoinit.pal.gp.core.domain.CcCondizioniAttivita;
import it.gruppoinit.pal.gp.core.domain.CcDestinazioni;
import it.gruppoinit.pal.gp.core.domain.CcTipointervento;
import it.gruppoinit.pal.gp.core.domain.CcValiditacoefficienti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.service.CcCoeffcontributoService;
import it.gruppoinit.pal.gp.core.service.CcDestinazioniService;
import it.gruppoinit.pal.gp.core.service.CcTipointerventoService;
import it.gruppoinit.pal.gp.core.service.CcValiditacoefficientiService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;

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

/**
 * 
 * @author
 */
@Controller
@SessionAttributes("cccoeffcontributo")
public class CcCoeffcontributoController extends BaseController<CcCoeffcontributo> {

    @Autowired
    private CcCoeffcontributoService cccoeffcontributoService;
    @Autowired
    private CcDestinazioniService ccDestinazioniService;
    @Autowired
    private CcValiditacoefficientiService ccValiditacoefficientiService;
    @Autowired
    private SoftwareService softwareService;
    @Autowired
    private CcTipointerventoService ccTipointerventoService;

    @RequestMapping
    public ModelMap list(@RequestParam("codiceCoefficiente") Integer codice, HttpServletRequest request, HttpServletResponse response) {

	CcValiditacoefficienti ccValiditacoefficienti = ccValiditacoefficientiService.findById(new PkId(codice));
	List<CcCoeffcontributo> cccoeffcontributoList = cccoeffcontributoService.findByValiditaCoefficiente(ccValiditacoefficienti);
	ModelMap model = new ModelMap(cccoeffcontributoList);
	boolean export = createJMesaExport(request, response, cccoeffcontributoList);
	if (export) {
	    return null;
	}
	model.addAttribute("cccoeffcontributoList", cccoeffcontributoList);
	model.addAttribute("ccValiditacoefficienti", ccValiditacoefficienti);
	return model;
    }

    @RequestMapping
    public String create(@RequestParam("codiceCoefficiente") Integer codice, Model model) {

	CcValiditacoefficienti ccValiditacoefficienti = ccValiditacoefficientiService.findById(new PkId(codice));
	CcCoeffcontributo cccoeffcontributo = new CcCoeffcontributo();
	cccoeffcontributo.setCcValiditacoefficienti(ccValiditacoefficienti);
	cccoeffcontributo.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
	//valori delle oggetti da selezionare sulle combobox
	List<CcDestinazioni> ccDestinazionis = ccDestinazioniService.findAll(null, null);
	List<CcTipointervento> ccTipointerventos = ccTipointerventoService.findAll(null, null);
	model.addAttribute("ccDestinazionis", ccDestinazionis);
	model.addAttribute("ccTipointerventos", ccTipointerventos);
	fixRenderEntityProperty(cccoeffcontributo);
	model.addAttribute("cccoeffcontributo", cccoeffcontributo);
	setPageAttributes(model);
	return "cccoeffcontributo/form";
    }

    @RequestMapping
    public String insert(Model model, @ModelAttribute("cccoeffcontributo") CcCoeffcontributo cccoeffcontributo, BindingResult result,
	    SessionStatus status) {

	cccoeffcontributo.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
	if (EntityUtils.getNestedProperty(cccoeffcontributo.getCcDestinazioni(), "id.codice") != null) {
	    CcDestinazioni ccDestinazioni = ccDestinazioniService.findById(new PkId(cccoeffcontributo.getCcDestinazioni().getId().getCodice()));
	    cccoeffcontributo.setCcDestinazioni(ccDestinazioni);
	}
	if (EntityUtils.getNestedProperty(cccoeffcontributo.getCcTipointervento(), "id.codice") != null) {
	    CcTipointervento ccTipointervento = ccTipointerventoService
		    .findById(new PkId(cccoeffcontributo.getCcTipointervento().getId().getCodice()));
	    cccoeffcontributo.setCcTipointervento(ccTipointervento);
	}
	fixMergeEntityProperty(cccoeffcontributo);
	try {
	    cccoeffcontributoService.insert(cccoeffcontributo);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, cccoeffcontributo, e);
	    fixRenderEntityProperty(cccoeffcontributo);
	    //valori delle oggetti da selezionare sulle combobox
	    List<CcDestinazioni> ccDestinazionis = ccDestinazioniService.findAll(null, null);
	    List<CcTipointervento> ccTipointerventos = ccTipointerventoService.findAll(null, null);
	    model.addAttribute("ccDestinazionis", ccDestinazionis);
	    model.addAttribute("ccTipointerventos", ccTipointerventos);
	    return "cccoeffcontributo/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + cccoeffcontributo.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	CcCoeffcontributo cccoeffcontributo = cccoeffcontributoService.findById(id);
	if (EntityUtils.getNestedProperty(cccoeffcontributo.getCcDestinazioni(), "id.codice") != null) {
	    CcDestinazioni ccDestinazioni = ccDestinazioniService.findById(new PkId(cccoeffcontributo.getCcDestinazioni().getId().getCodice()));
	    cccoeffcontributo.setCcDestinazioni(ccDestinazioni);
	}
	if (EntityUtils.getNestedProperty(cccoeffcontributo.getCcTipointervento(), "id.codice") != null) {
	    CcTipointervento ccTipointervento = ccTipointerventoService
		    .findById(new PkId(cccoeffcontributo.getCcTipointervento().getId().getCodice()));
	    cccoeffcontributo.setCcTipointervento(ccTipointervento);
	}
	fixRenderEntityProperty(cccoeffcontributo);
	//valori delle oggetti da selezionare sulle combobox
	List<CcDestinazioni> ccDestinazionis = ccDestinazioniService.findAll(null, null);
	List<CcTipointervento> ccTipointerventos = ccTipointerventoService.findAll(null, null);
	model.addAttribute("ccDestinazionis", ccDestinazionis);
	model.addAttribute("ccTipointerventos", ccTipointerventos);
	model.addAttribute("cccoeffcontributo", cccoeffcontributo);
	setPageAttributes(model);
	return "cccoeffcontributo/form";
    }

    @RequestMapping
    public String update(Model model, @ModelAttribute("cccoeffcontributo") CcCoeffcontributo cccoeffcontributo, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	fixMergeEntityProperty(cccoeffcontributo);
	try {
	    cccoeffcontributoService.update(cccoeffcontributo);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, cccoeffcontributo, e);
	    fixRenderEntityProperty(cccoeffcontributo);
	    //valori delle oggetti da selezionare sulle combobox
	    List<CcDestinazioni> ccDestinazionis = ccDestinazioniService.findAll(null, null);
	    List<CcTipointervento> ccTipointerventos = ccTipointerventoService.findAll(null, null);
	    model.addAttribute("ccDestinazionis", ccDestinazionis);
	    model.addAttribute("ccTipointerventos", ccTipointerventos);
	    return "cccoeffcontributo/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + cccoeffcontributo.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String delete(Model model, @ModelAttribute("cccoeffcontributo") CcCoeffcontributo cccoeffcontributo, BindingResult result,
	    SessionStatus status) {

	CcCoeffcontributo objToDelete = cccoeffcontributoService.findById(cccoeffcontributo.getId());
	try {
	    cccoeffcontributoService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, cccoeffcontributo, e);
	    //valori delle oggetti da selezionare sulle combobox
	    List<CcDestinazioni> ccDestinazionis = ccDestinazioniService.findAll(null, null);
	    List<CcTipointervento> ccTipointerventos = ccTipointerventoService.findAll(null, null);
	    model.addAttribute("ccDestinazionis", ccDestinazionis);
	    model.addAttribute("ccTipointerventos", ccTipointerventos);
	    fixRenderEntityProperty(cccoeffcontributo);
	    return "cccoeffcontributo/form";
	}
	status.setComplete();
	return "redirect:list.htm?codiceCoefficiente=" + cccoeffcontributo.getCcValiditacoefficienti().getId().getCodice();
    }

    @Override
    protected void fixMergeEntityProperty(CcCoeffcontributo entity) {

    }

    @Override
    protected void fixRenderEntityProperty(CcCoeffcontributo entity) {

	if (entity.getAree() == null) {
	    entity.setAree(new Aree());
	}
	if (entity.getCcCondizioniAttivita() == null) {
	    entity.setCcCondizioniAttivita(new CcCondizioniAttivita());
	}
	if (entity.getCcDestinazioni() == null) {
	    entity.setCcDestinazioni(new CcDestinazioni());
	}
	if (entity.getCcTipointervento() == null) {
	    entity.setCcTipointervento(new CcTipointervento());
	}
	if (entity.getCcValiditacoefficienti() == null) {
	    entity.setCcValiditacoefficienti(new CcValiditacoefficienti());
	}
	if (entity.getSoftware() == null) {
	    entity.setSoftware(new Software());
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
