package it.gruppoinit.pal.gp.backoffice.web;

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

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.OrdineEnum;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzecollegate;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Statiistanza;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzecollegateHelper;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.features.istanze.istanzecollegate.IstanzecollegateService;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.StatiistanzaService;
import it.gruppoinit.pal.gp.core.service.VwIstanzecollegateService;
import it.gruppoinit.pal.gp.core.utils.Utilities;

/**
 * 
 * @author gianpaolot
 */
@Controller
@SessionAttributes(value = { "istanzecollegate", "istanzeCommand" })
public class IstanzecollegateController extends BaseController<Istanzecollegate> {

    @Autowired
    private IstanzecollegateService istanzecollegateService;
    @Autowired
    private IstanzeService istanzeService;
    @Autowired
    private VwIstanzecollegateService vwIstanzecollegateService;
    @Autowired
    private VerticalizzazioniService verticalizzazioniService;
    @Autowired
    private StatiistanzaService statiistanzaService;
    private static final Logger log = LoggerFactory.getLogger(IstanzecollegateController.class);

    @RequestMapping
    public ModelMap list(@RequestParam("codiceIstanza") Integer codiceistanza, Model modela, HttpServletRequest request,
	    HttpServletResponse response) {

	// §§§BEGIN§§§
	Istanze istanza = istanzeService.findById(new PkId(codiceistanza));
	if (istanza != null) {
	    checkAccessoInformazioni(istanza, false);
	}
	List<IstanzecollegateHelper> istanzecollegateHelperList = vwIstanzecollegateService.findIstanzecollegateByIstanza(istanza);
	ModelMap model = new ModelMap(istanzecollegateHelperList);
	// Recupero le informazione per creare lo schema riassuntivo
	IstanzecollegateHelper istanzecollegateHelperPrecedentiAndSuccessive = istanzecollegateService.getSchemaPrecedentiAndSuccessive(istanza);
	model.addAttribute("istanza", istanza);
	model.addAttribute("istanzecollegateHelperList", istanzecollegateHelperList);
	// Lista delle istanze a cui l'istanza in esame è stata collegata
	model.addAttribute("listaIstanzecollegateSuccessive", istanzecollegateHelperPrecedentiAndSuccessive.getListaIstanzeSuccessive());
	// Lista di istanze che sono state collegate all'istanza in esame
	model.addAttribute("listaIstanzecollegatePrecedenti", istanzecollegateHelperPrecedentiAndSuccessive.getListaIstanzePrecedenti());
	request.getSession().setAttribute("tmpStatoIstanza", Utilities.generaPassword(20));
	requestAttr(request);
	return model;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    private void requestAttr(HttpServletRequest request) {

	List<Statiistanza> statiistanzas = statiistanzaService.findByStatocomportamentoAperte(false);
	request.setAttribute("statiistanzas", statiistanzas);
	Boolean visualizzaCambioStato = Boolean.FALSE;
	if (verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_COMPORTAMENTI_ISTANZE)) {
	    Verticalizzazioniparametri vp = verticalizzazioniService.getVerticalizzazioniparametri(
		    WebConstants.VERTICALIZZAZIONE_COMPORTAMENTI_ISTANZE,
		    WebConstants.VERTICALIZZAZIONE_COMPORTAMENTI_ISTANZE_VISUALIZZA_CAMBIO_STATO_IST_COLLEGATE);
	    if (vp != null && StringUtils.isNotBlank(vp.getValore()) && "1".equals(vp.getValore())) {
		visualizzaCambioStato = Boolean.TRUE;
	    }
	}
	request.setAttribute("VIS_CAMBIO_STATO", visualizzaCambioStato);
    }

    @RequestMapping
    public String addCollegamento(@RequestParam(required = false, value = "codiceIstanzaDaCollegare") Integer codiceIstanzeDacollegare,
	    @RequestParam(required = false, value = "lista_istanze_da_collegare") String lista_istanze_da_collegare,
	    @RequestParam("codiceIstanza") Integer codiceIstanze, Model model, @ModelAttribute("istanza") Istanze istanza, BindingResult result,
	    SessionStatus status) {

	// §§§BEGIN§§§
	Istanze istanze = istanzeService.findById(new PkId(codiceIstanze));
	checkAccessoInformazioni(istanze, true);
	try {
	    if (StringUtils.isNotBlank(lista_istanze_da_collegare)) {
		istanzecollegateService.insertCollegamentoMultiplo(lista_istanze_da_collegare, istanze, true);
	    } else {
		Istanze istanzeDacollegare = istanzeService.findById(new PkId(codiceIstanzeDacollegare));
		istanzecollegateService.insertCollegamento(istanzeDacollegare, istanze, true);
	    }
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, istanza, false, e);
	    log.error("addCollegamento(): {}", e.getMessage());
	    Map<String, Object> map = new HashMap<String, Object>();
	    map.put(WebConstants.GOTO, "%2F");
	    model.addAttribute("commandName", "istanza");
	    model.addAttribute("method", "../history/back.htm");
	    model.addAttribute("queryStringParams", map);
	    return "error";
	}
	return "redirect:../history/back.htm?" + WebConstants.GOTO + "=%2F";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String addCollegamentoPrecedente(@RequestParam(required = false, value = "codiceIstanzaDaCollegare") Integer codiceIstanzeDacollegare,
	    @RequestParam(required = false, value = "lista_istanze_da_collegare") String lista_istanze_da_collegare,
	    @RequestParam("codiceIstanza") Integer codiceIstanze, Model model, @ModelAttribute("istanza") Istanze istanza, BindingResult result,
	    SessionStatus status) {

	// §§§BEGIN§§§
	Istanze istanze = istanzeService.findById(new PkId(codiceIstanze));
	checkAccessoInformazioni(istanze, true);
	try {
	    if (StringUtils.isNotBlank(lista_istanze_da_collegare)) {
		istanzecollegateService.insertCollegamentoPrecedenteMultiplo(lista_istanze_da_collegare, istanze, true);
	    } else {
		Istanze istanzeDacollegare = istanzeService.findById(new PkId(codiceIstanzeDacollegare));
		istanzecollegateService.insertCollegamentoPrecedente(istanzeDacollegare, istanze, true);
	    }
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, istanza, false, e);
	    log.error("addCollegamento(): {}", e.getMessage());
	    Map<String, Object> map = new HashMap<String, Object>();
	    map.put(WebConstants.GOTO, "%2F");
	    model.addAttribute("commandName", "istanza");
	    model.addAttribute("method", "../history/back.htm");
	    model.addAttribute("queryStringParams", map);
	    return "error";
	}
	return "redirect:../history/back.htm?" + WebConstants.GOTO + "=%2F";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String deleteCollegamento(@RequestParam("codiceIstanzaDaScollegare") Integer codiceIstanzaDaScollegare,
	    @RequestParam("progressivo") Integer progressivo, @RequestParam("ordine") Integer ordine,
	    @RequestParam("codiceIstanza") Integer codiceIstanza) {

	// §§§BEGIN§§§
	Istanze istanze = istanzeService.findById(new PkId(codiceIstanza));
	checkAccessoInformazioni(istanze, true);
	Istanze istanza = istanzeService.findById(new PkId(codiceIstanzaDaScollegare));
	Istanzecollegate objToDelete = istanzecollegateService.findByIstanzaAndProgressivoAndOrdine(istanza, progressivo, ordine);
	try {
	    istanzecollegateService.deleteCollegamento(objToDelete);
	} catch (Exception e) {
	    log.error(
		    "Possibile anomalia nella cancellazione dei dati sulla tabella ISTANZECOLLEGATE. \nIstanzecollegateServiceImpl.deleteCollegamento: Errore :" +
			    e.getMessage());
	    throw new RuntimeException(
		    "Possibile anomalia nella cancellazione dei dati sulla tabella ISTANZECOLLEGATE. \nIstanzecollegateServiceImpl.deleteCollegamento: Errore :" +
			    e.getMessage());
	}
	return "redirect:list.htm?codiceIstanza=" + codiceIstanza + "&status_msg=02";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String updateModificaStatoPratiche(@RequestParam("codicestato") String codicestato, @RequestParam("istanze") String istanze,
	    @RequestParam("tmpStato") String tmpStato, @RequestParam("codiceIstanza") Integer codiceIstanza) {

	Set<Integer> istanzes = new HashSet<Integer>();
	String[] istanze2 = StringUtils.split(istanze, "|");
	for (String i : istanze2) {
	    if (Utilities.isInteger(i)) {
		istanzes.add(Integer.parseInt(i));
	    }
	}
	String status = "02";
	try {
	    istanzeService.updateStatoIstanze(istanzes, codicestato);
	} catch (Exception e) {
	    status = "03";
	    log.error(
		    "Possibile anomalia nella modifica del campo ordine  sulla tabella ISTANZECOLLEGATE. \nIstanzecollegateServiceImpl.updateOrdine: Errore :" +
			    e.getMessage());
	    throw new RuntimeException(
		    "Possibile anomalia nella modifica del campo ordine  sulla tabella ISTANZECOLLEGATE. \nIstanzecollegateServiceImpl.updateOrdine: Errore :" +
			    e.getMessage());
	}
	return "redirect:list.htm?codiceIstanza=" + codiceIstanza + "&status_msg=" + status;
    }

    @RequestMapping
    public String upColumn(@RequestParam("codiceIstanzaCollegata") Integer codiceIstanzaCollegata, @RequestParam("progressivo") Integer progressivo,
	    @RequestParam("ordine") Integer ordine, @RequestParam("codiceIstanza") Integer codiceIstanza) {

	// §§§BEGIN§§§
	Istanze istanze = istanzeService.findById(new PkId(codiceIstanza));
	checkAccessoInformazioni(istanze, true);
	Istanze istanza = istanzeService.findById(new PkId(codiceIstanzaCollegata));
	try {
	    Istanzecollegate istanzecollegate = istanzecollegateService.findByIstanzaAndProgressivoAndOrdine(istanza, progressivo, ordine);
	    istanzecollegateService.updateOrdine(istanzecollegate, OrdineEnum.UP);
	} catch (Exception e) {
	    log.error(
		    "Possibile anomalia nella modifica del campo ordine  sulla tabella ISTANZECOLLEGATE. \nIstanzecollegateServiceImpl.updateOrdine: Errore :" +
			    e.getMessage());
	    throw new RuntimeException(
		    "Possibile anomalia nella modifica del campo ordine  sulla tabella ISTANZECOLLEGATE. \nIstanzecollegateServiceImpl.updateOrdine: Errore :" +
			    e.getMessage());
	}
	return "redirect:list.htm?codiceIstanza=" + codiceIstanza + "&status_msg=02";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String downColumn(@RequestParam("codiceIstanzaCollegata") Integer codiceIstanzaCollegata, @RequestParam("progressivo") Integer progressivo,
	    @RequestParam("ordine") Integer ordine, @RequestParam("codiceIstanza") Integer codiceIstanza) {

	// §§§BEGIN§§§
	Istanze istanze = istanzeService.findById(new PkId(codiceIstanza));
	checkAccessoInformazioni(istanze, true);
	Istanze istanza = istanzeService.findById(new PkId(codiceIstanzaCollegata));
	try {
	    Istanzecollegate istanzecollegate = istanzecollegateService.findByIstanzaAndProgressivoAndOrdine(istanza, progressivo, ordine);
	    istanzecollegateService.updateOrdine(istanzecollegate, OrdineEnum.DOWN);
	} catch (Exception e) {
	    log.error(
		    "Possibile anomalia nella modifica del campo ordine  sulla tabella ISTANZECOLLEGATE. \nIstanzecollegateServiceImpl.updateOrdine: Errore :" +
			    e.getMessage());
	    throw new RuntimeException(
		    "Possibile anomalia nella modifica del campo ordine  sulla tabella ISTANZECOLLEGATE. \nIstanzecollegateServiceImpl.updateOrdine: Errore :" +
			    e.getMessage());
	}
	return "redirect:list.htm?codiceIstanza=" + codiceIstanza + "&status_msg=02";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    protected void fixMergeEntityProperty(Istanzecollegate entity) {

	if (entity.getIstanza() != null && StringUtils.isBlank(entity.getIstanza().getId().getIdcomune())) {
	    entity.setIstanza(null);
	}
	if (entity.getIstanzaDacollegare() != null && StringUtils.isBlank(entity.getIstanzaDacollegare().getId().getIdcomune())) {
	    entity.setIstanzaDacollegare(null);
	}
    }

    @Override
    protected void fixRenderEntityProperty(Istanzecollegate entity) {

	if (EntityUtils.getNestedProperty(entity, "istanze") == null) {
	    entity.setIstanza(new Istanze());
	}
	if (EntityUtils.getNestedProperty(entity, "istanzaDacollegare") == null) {
	    entity.setIstanzaDacollegare(new Istanze());
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
