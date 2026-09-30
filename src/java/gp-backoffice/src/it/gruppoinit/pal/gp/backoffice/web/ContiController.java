/**
 * 
 */
package it.gruppoinit.pal.gp.backoffice.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;

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

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Conti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioni;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.NodoPagamentiService;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.VerticalizzazioneNodoPagamentiServiceImpl;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.rest.ConnettoriListType;
import it.gruppoinit.pal.gp.core.features.oneri.ContiService;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;
import it.gruppoinit.pal.gp.core.utils.Utilities;

/**
 * @author francescop
 * 
 */
@Controller
@SessionAttributes("conti")
public class ContiController extends BaseController<Conti> {

    private static final String VERTICALIZZAZIONI_NODO_PAGAMENTI_ATTIVA = "verticalizzazioni_nodoPagamenti_attiva";
    @Autowired
    private ContiService contiService;
    @Autowired
    private SoftwareService softwareService;
    @Autowired
    private NodoPagamentiService nodoPagamentiService;
    @Autowired
    private VerticalizzazioniService verticalizzazioniService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	// §§§BEGIN§§§
	List<Conti> contiList = contiService.findAll(null, null);
	ModelMap model = new ModelMap(contiList);
	boolean export = createJMesaExport(request, response, contiList);
	if (export)
	    return null;
	model.addAttribute("contiList", contiList);
	return model;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String delete(@ModelAttribute("conti") Conti conti, BindingResult result, SessionStatus status) {

	// §§§BEGIN§§§
	Conti objToDelete = contiService.findById(conti.getId());
	try {
	    contiService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderEntityProperty(conti);
	    return "conti/form";
	}
	status.setComplete();
	return "redirect:list.htm";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String insert(@ModelAttribute("conti") Conti conti, BindingResult result, SessionStatus status) {

	// §§§BEGIN§§§
	fixMergeEntityProperty(conti);
	try {
	    contiService.insert(conti);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, conti, e);
	    fixRenderEntityProperty(conti);
	    return "conti/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + conti.getId().getCodice() + "&status_msg=01";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String update(@ModelAttribute("conti") Conti conti, BindingResult result, SessionStatus status, HttpServletRequest request) {

	// §§§BEGIN§§§
	fixMergeEntityProperty(conti);
	try {
	    contiService.update(conti);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, conti, e);
	    fixRenderEntityProperty(conti);
	    return "conti/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + conti.getId().getCodice() + "&status_msg=02";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String create(Model model) {

	// §§§BEGIN§§§
	Conti conti = new Conti();
	// conti.setIva(WebConstants.CONST_IVA);
	conti.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
	fixRenderEntityProperty(conti);
	model.addAttribute("conti", conti);
	setPageAttributes(model);
	return "conti/form";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	// §§§BEGIN§§§
	PkId id = new PkId(codice);
	Conti conti = contiService.findById(id);
	fixRenderEntityProperty(conti);
	model.addAttribute("conti", conti);
	setPageAttributes(model);
	return "conti/form";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    protected void fixMergeEntityProperty(Conti entity) {

    }

    @Override
    protected void fixRenderEntityProperty(Conti entity) {

	if (entity.getSoftware() == null) {
	    entity.setSoftware(new Software());
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

	Verticalizzazioni verticalizzazioni_nodoPagamenti_attiva = verticalizzazioniService
		.findByModulo(VerticalizzazioneNodoPagamentiServiceImpl.NOME_VERTICALIZZAZIONE);
	if (verticalizzazioni_nodoPagamenti_attiva != null) {
	    if (verticalizzazioni_nodoPagamenti_attiva.getAttivo() == 1) {
		model.addAttribute(VERTICALIZZAZIONI_NODO_PAGAMENTI_ATTIVA, true);
	    } else {
		model.addAttribute(VERTICALIZZAZIONI_NODO_PAGAMENTI_ATTIVA, false);
	    }
	} else {
	    model.addAttribute(VERTICALIZZAZIONI_NODO_PAGAMENTI_ATTIVA, false);
	}
    }

    @RequestMapping
    public void ajaxGestisciMappatturaNodo(Model model, HttpServletRequest request, HttpServletResponse response) throws JAXBException, IOException {

	try {
	    List<ConnettoriListType> listaConnettori = nodoPagamentiService.getMappaturaConnettore();
	    String richiesta = "";
	    if (listaConnettori.isEmpty()) {
		richiesta = "{\"messaggio\":\"Nessuna mappatura trovata\"}";
	    } else {
		response.setContentType("application/json");
		richiesta = Utilities.marshalJsonObject(listaConnettori, ConnettoriListType.class, false, Utilities.JAXB_ENCODING_UTF_8);
	    }
	    response.getOutputStream().write(richiesta.getBytes());
	    response.getOutputStream().flush();
	} catch (Exception e) {
	    String msg = "{\"errore\":\"Si è verificato un errore nella configurazione.\"}";
	    response.setStatus(500);
	    response.getOutputStream().write(msg.getBytes());
	}
    }
}
