package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.domain.Dyn2Modellit;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzedyn2dati;
import it.gruppoinit.pal.gp.core.domain.Istanzedyn2modellit;
import it.gruppoinit.pal.gp.core.domain.Istanzedyn2modellitId;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.ModellidinamiciHelper;
import it.gruppoinit.pal.gp.core.domain.web.IstanzeDyn2DatiCommand;
import it.gruppoinit.pal.gp.core.service.Dyn2CampiService;
import it.gruppoinit.pal.gp.core.service.Dyn2ModellidService;
import it.gruppoinit.pal.gp.core.service.Dyn2ModellitService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.Istanzedyn2datiService;
import it.gruppoinit.pal.gp.core.service.Istanzedyn2modellitService;
import it.gruppoinit.pal.gp.core.service.ModelliDinamiciFormuleService;
import it.gruppoinit.pal.gp.core.service.helper.FlashMessages;
import it.gruppoinit.pal.gp.core.utils.Dyn2Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes(value = { "helperScheda" })
public class Istanzedyn2datiController extends BaseController<Istanzedyn2dati> {

    @Autowired
    private Dyn2CampiService dyn2CampiService;
    @Autowired
    private Dyn2ModellidService dyn2ModellidService;
    @Autowired
    private Dyn2ModellitService dyn2ModellitService;
    @Autowired
    private Istanzedyn2datiService istanzedyn2datiService;
    @Autowired
    private Istanzedyn2modellitService istanzedyn2modellitService;
    @Autowired
    private IstanzeService istanzeService;
    @Autowired
    private ModelliDinamiciFormuleService modelliDinamiciFormuleService;
    private static final Logger log = LoggerFactory.getLogger(Istanzedyn2datiController.class);

    @RequestMapping
    public String viewAnteprimaModello(Model model, @RequestParam("codiceModello") Integer codiceModello, HttpServletRequest request,
	    HttpServletResponse response) {

	ModellidinamiciHelper helper = dyn2ModellitService.populateModellodinamicoForPreview(codiceModello);
	String html = dyn2ModellitService.render(helper);
	log.debug("viewAnteprimaModello: {}", html);
	model.addAttribute("modello", html);
	return "istanzedyn2dati/anteprima";
    }

    @RequestMapping
    public String viewModelliIstanza(Model model, @RequestParam("codiceIstanza") Integer codiceIstanza,
	    @RequestParam(value = "codiceModello", required = false) Integer codiceModello, HttpServletRequest request, HttpServletResponse response) {

	Istanze istanza = istanzeService.findById(new PkId(codiceIstanza));
	checkAccessoInformazioni(istanza, false);
	ModellidinamiciHelper helper = null;
	viewPageAttributes(model, request, codiceIstanza, codiceModello, helper, istanza);
	return "istanzedyn2dati/anteprima";
    }

    @RequestMapping
    public String addModelloIstanza(Model model, @RequestParam("codiceIstanza") Integer codiceIstanza,
	    @RequestParam(value = "codiceModello") Integer codiceModello, HttpServletRequest request, HttpServletResponse response) {

	Istanze istanza = istanzeService.findById(new PkId(codiceIstanza));
	checkAccessoInformazioni(istanza, true);
	Istanzedyn2modellitId id = new Istanzedyn2modellitId(codiceIstanza, codiceModello);
	Istanzedyn2modellit modt = istanzedyn2modellitService.findById(id);
	if (modt != null) {
	    throw new SecurityException("Attenzione! La scheda [" + modt.getDyn2Modellit().getDescrizione() + "] con codice [" + codiceModello
		    + "] è già stata registrata per l'istanza " + istanza.getDescrizioneIstanza());
	}
	Istanzedyn2modellit entity = new Istanzedyn2modellit();
	entity.setId(id);
	entity.setIstanza(istanza);
	Dyn2Modellit modello = dyn2ModellitService.findById(new PkId(codiceModello));
	entity.setDyn2Modellit(modello);
	try {
	    istanzedyn2modellitService.insert(entity);
	    String info = getMessageFromBundle("01", null);
	    FlashMessages.getInfos().add(info);
	} catch (Exception e) {
	    FlashMessages.getWarnings().add(e.getMessage());
	}
	return "redirect:viewModelliIstanza.htm?codiceIstanza=" + codiceIstanza + "&codiceModello=" + codiceModello + "&ts_="
		+ System.currentTimeMillis();
    }

    @RequestMapping
    public String eliminaScheda(Model model, @RequestParam("codiceIstanza") Integer codiceIstanza,
	    @RequestParam(value = "codiceModello") Integer codiceModello, HttpServletRequest request, HttpServletResponse response) {

	Istanze istanza = istanzeService.findById(new PkId(codiceIstanza));
	checkAccessoInformazioni(istanza, true);
	Istanzedyn2modellitId id = new Istanzedyn2modellitId(codiceIstanza, codiceModello);
	Istanzedyn2modellit modt = istanzedyn2modellitService.findById(id);
	if (modt == null) {
	    throw new SecurityException("Attenzione! La scheda con codice modello [" + codiceModello + "] non è stata registrata per l'istanza "
		    + istanza.getDescrizioneIstanza());
	}
	Istanzedyn2modellit entity = new Istanzedyn2modellit();
	entity.setId(id);
	entity.setIstanza(istanza);
	Dyn2Modellit modello = dyn2ModellitService.findById(new PkId(codiceModello));
	entity.setDyn2Modellit(modello);
	try {
	    istanzedyn2modellitService.delete(entity);
	    String info = getMessageFromBundle("05", null);
	    FlashMessages.getInfos().add(info);
	} catch (Exception e) {
	    FlashMessages.getWarnings().add(e.getMessage());
	    return "redirect:viewModelliIstanza.htm?codiceIstanza=" + codiceIstanza + "&codiceModello=" + codiceModello + "&ts_="
		    + System.currentTimeMillis();
	}
	return "redirect:viewModelliIstanza.htm?codiceIstanza=" + codiceIstanza + "&ts_=" + System.currentTimeMillis();
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @RequestMapping
    public String salvaScheda(Model model, @ModelAttribute("helperScheda") ModellidinamiciHelper helperScheda,
	    @RequestParam("codiceIstanza") Integer codiceIstanza, @RequestParam(value = "codiceModello") Integer codiceModello,
	    HttpServletRequest request, HttpServletResponse response) {

	Istanze istanza = istanzeService.findById(new PkId(codiceIstanza));
	checkAccessoInformazioni(istanza, true);
	Istanzedyn2modellitId id = new Istanzedyn2modellitId(codiceIstanza, codiceModello);
	Istanzedyn2modellit modt = istanzedyn2modellitService.findById(id);
	if (modt == null) {
	    throw new SecurityException("Attenzione! La scheda con codice modello [" + codiceModello + "] non è stata registrata per l'istanza "
		    + istanza.getDescrizioneIstanza());
	}
	Map<String, String> valoriCampiDinamici = Dyn2Utils.valoriCampiDinamiciFromRequest(request);
	try {
	    //files uploadati da eliminare al salvataggio della scheda
	    List<Integer> uplodedDeleted = (List) request.getSession().getAttribute(WebConstants.DYN2_CAMPO_UPLOAD_DELETEFILES_SESSION_KEY);
	    //popolo il modello con i dati della request
	    Dyn2Utils.popolaModelloDaMappaDatiRequest(helperScheda, valoriCampiDinamici);
	    List<FieldError> errors = helperScheda.validaCampi();
	    if (errors.size() == 0) {
		istanzedyn2modellitService.salvaSchedaDaModel(codiceIstanza, codiceModello, helperScheda, valoriCampiDinamici, uplodedDeleted);
		if (uplodedDeleted != null) {
		    uplodedDeleted.clear();
		}
		String info = getMessageFromBundle("01", null);
		FlashMessages.getInfos().add(info);
	    } else {
		//viewPageAttributes(model, request, codiceIstanza, codiceModello, helperScheda, istanza);
		for (FieldError error : errors) {
		    FlashMessages.getWarnings().add(getContext().getMessage(error, Locale.ITALIAN));
		}
	    }
	    viewPageAttributes(model, request, codiceIstanza, codiceModello, helperScheda, istanza);
	} catch (Exception e) {
	    FlashMessages.getWarnings().add(e.getMessage());
	}
	//return "redirect:viewModelliIstanza.htm?codiceIstanza=" + codiceIstanza + "&codiceModello=" + codiceModello + "&ts_=" + System.currentTimeMillis();
	return "istanzedyn2dati/anteprima";
    }

    @Override
    protected void setPageAttributes(Model model) {

    }

    private void viewPageAttributes(Model model, HttpServletRequest request, Integer codiceIstanza, Integer codiceModello,
	    ModellidinamiciHelper helper, Istanze istanza) {

	IstanzeDyn2DatiCommand cmd = new IstanzeDyn2DatiCommand();
	List<Istanzedyn2modellit> modellis = istanzedyn2modellitService.findByIstanza(istanza.getId());
	Boolean readOnly = Boolean.FALSE;
	if (codiceModello != null) {
	    Istanzedyn2modellitId id = new Istanzedyn2modellitId(codiceIstanza, codiceModello);
	    Istanzedyn2modellit modt = istanzedyn2modellitService.findById(id);
	    if (modt == null) {
		throw new SecurityException("Attenzione! non esiste la scheda con codice [" + codiceModello + "] per l'istanza "
			+ istanza.getDescrizioneIstanza());
	    }
	    readOnly = modt.getDyn2Modellit().getFlgReadonlyWeb() == null ? Boolean.FALSE : modt.getDyn2Modellit().getFlgReadonlyWeb();
	}
	//model.addAttribute("readOnlyModello", readOnly);
	cmd.setReadOnlyModello(readOnly);
	if (modellis.size() > 0 && codiceModello == null) {
	    codiceModello = modellis.get(0).getId().getFkD2mtId();
	}
	if (codiceModello != null) {
	    if (helper == null) {
		helper = dyn2ModellitService.populateModellodinamicoForIstanza(codiceModello, codiceIstanza);
	    }
	    //model.addAttribute("helperScheda", helper);
	    cmd.setHelperScheda(helper);
	    if (helper != null) {
		String html = dyn2ModellitService.render(helper);
		//model.addAttribute("modello", html);
		cmd.setModello(html);
	    }
	    //model.addAttribute("codiceModello", codiceModello);
	    cmd.setCodiceModello(codiceModello);
	}
	//model.addAttribute("listaModelliAttivati", modellis);
	cmd.setListaModelliAttivati(modellis);
	/*
	List<Dyn2Modellit> modellidaAggiungere = dyn2ModellitService.findAll(null, null);
	List<Dyn2Modellit> daRimuovere = new ArrayList<Dyn2Modellit>();
	for (Istanzedyn2modellit istanzedyn2modellit : modellis) {
	    Dyn2Modellit modelloPresente = istanzedyn2modellit.getDyn2Modellit();
	    if (modellidaAggiungere.contains(modelloPresente)) {
		daRimuovere.add(modelloPresente);
	    }
	}
	if (daRimuovere.size() > 0) {
	    modellidaAggiungere.removeAll(daRimuovere);
	}
	model.addAttribute("listaModelliDaAggiungere", modellidaAggiungere);
	*/
	model.addAttribute("info", cmd);
    }

    @Override
    protected void fixMergeEntityProperty(Istanzedyn2dati entity) {

    }

    @Override
    protected void fixRenderEntityProperty(Istanzedyn2dati entity) {

    }
}