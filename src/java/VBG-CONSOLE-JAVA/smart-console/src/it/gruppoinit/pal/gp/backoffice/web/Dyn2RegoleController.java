package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Dyn2Campi;
import it.gruppoinit.pal.gp.core.domain.Dyn2Espressioni;
import it.gruppoinit.pal.gp.core.domain.Dyn2Modellit;
import it.gruppoinit.pal.gp.core.domain.Dyn2Regole;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.Dyn2RegoleSyntaxError;
import it.gruppoinit.pal.gp.core.domain.web.Dyn2CampiCommand;
import it.gruppoinit.pal.gp.core.domain.web.Dyn2RegoleCommand;
import it.gruppoinit.pal.gp.core.service.Dyn2EspressioniService;
import it.gruppoinit.pal.gp.core.service.Dyn2ModellitService;
import it.gruppoinit.pal.gp.core.service.Dyn2RegoleService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;
import it.gruppoinit.pal.gp.core.service.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.helper.FlashMessages;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.BooleanUtils;
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
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * @author Lion
 */
@Controller
@SessionAttributes("regolaCommand")
public class Dyn2RegoleController extends BaseController<Dyn2Regole> {

    @Autowired
    private Dyn2RegoleService dyn2RegoleService;
    @Autowired
    private Dyn2EspressioniService dyn2EspressioniService;
    @Autowired
    private Dyn2ModellitService dyn2ModellitService; 
    @Autowired
    private VerticalizzazioniService verticalizzazioniService;
    @Autowired
    private SoftwareService softwareService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	// §§§BEGIN§§§
	List<Dyn2Regole> regoleList = dyn2RegoleService.findAll(null, null);
	ModelMap model = new ModelMap(regoleList);
	boolean export = createJMesaExport(request, response, regoleList);
	if (export) {
	    return null;
	}
	model.addAttribute("regoleList", regoleList);
	return model;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String create(Model model, @RequestParam(value = "popup", required = false) Boolean popup,
	    @RequestParam(value = "dyn2campi", required = false) Dyn2RegoleCommand cmd, HttpServletRequest request) {

	// §§§BEGIN§§§
	Dyn2Regole regola = new Dyn2Regole();
	regola.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
	// Commentati perchè al salvattaggio della regola restituiva un errore in quanto prova ad inserire
	// un espressione vuota.
	//Set<Dyn2Espressioni> exprs = regola.getDyn2Espressionis();
	//exprs.add(new Dyn2Espressioni());
	if (cmd == null) {
	    cmd = new Dyn2RegoleCommand();
	}
	cmd.setEntity(regola);
	cmd.setPopup(popup);
	cmd.setRegola(regola);
	model.addAttribute("regolaCommand", cmd);
	setPageAttributes(model);
	// §§§END§§§
	return "dyn2regole/form";
    }

    @RequestMapping
    public String popupcreate(Model model, @RequestParam(value = "callerId", required = false) Integer callerId,
	    @RequestParam(value = "callerModT", required = false) Integer d2mtId, HttpServletRequest request) {

	Dyn2RegoleCommand cmd = new Dyn2RegoleCommand();
	cmd.setCallerId(callerId);
	cmd.setPopup(Boolean.TRUE);
	Dyn2Modellit d2mt = null;
	if(d2mtId != null){
	    d2mt = new Dyn2Modellit();
	    d2mt.getId().setCodice(d2mtId);
	}
	cmd.setDyn2Modellit(d2mt);
	return create(model, true, cmd, request);
    }

    @RequestMapping
    public String insert(Model model, @ModelAttribute("regolaCommand") Dyn2RegoleCommand cmd, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	// §§§BEGIN§§§
	Dyn2Regole regola = cmd.getRegola();
	regola.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
	regola.getDyn2Espressionis().addAll(cmd.getEspressioni());
	fixMergeEntityProperty(regola);
	try {
	    dyn2RegoleService.insert(regola);
	    Integer newbornId = regola.getId().getCodice();
	    cmd.setNewId(newbornId);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, cmd, true, e);
	    fixRenderEntityProperty(regola);
	    return "dyn2regole/form";
	}
	StringBuilder sbUrl = new StringBuilder("redirect:");
	if (BooleanUtils.isTrue(cmd.getPopup())) {
	    sbUrl.append("popup");
	}
	sbUrl.append("view.htm?codice=").append(regola.getId().getCodice());
	if (cmd.getNewId() != null) {
	    sbUrl.append("&newId=").append(cmd.getNewId());
	}
	if (cmd.getCallerId() != null) {
	    sbUrl.append("&callerId=").append(cmd.getCallerId());
	}
	if (cmd.getDyn2ModellotId() != null) {
	    sbUrl.append("&callerModT=").append(cmd.getDyn2ModellotId());
	}
	sbUrl.append("&status_msg=01");
	return sbUrl.toString();
	// §§§END§§§
    }

    @RequestMapping
    public String popupinsert(Model model, @ModelAttribute("regolaCommand") Dyn2RegoleCommand cmd, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	return insert(model, cmd, result, status, request);
    }

    private String popupcloseRedirect(Integer codiceregola, String status_msg, String popupCaller) {

	return "popupview.htm?codice=" + codiceregola + "&popup=true&status_msg=" + status_msg + "&popupCaller=" + popupCaller + "&done=true";
    }

    @RequestMapping
    public String view(@RequestParam(value = "codice") Integer codice, @RequestParam(value = "callerId", required = false) Integer callerId,
	    @RequestParam(value = "dyn2campi", required = false) Dyn2RegoleCommand cmd, Model model, HttpServletRequest request) {

	// §§§BEGIN§§§
	PkId id = new PkId(codice);
	Dyn2Regole regola = dyn2RegoleService.findById(id);
	Set<Dyn2Espressioni> exprs = regola.getDyn2Espressionis();
	/**
	 * for (Dyn2Espressioni dyn2Espressioni : exprs) { dyn2Espressioni.setDyn2Campi(new Dyn2Campi()); }
	 **/
	fixRenderEntityProperty(regola);
	if (cmd == null) {
	    cmd = new Dyn2RegoleCommand();
	}
	cmd.setCallerId(callerId);
	cmd.setRegola(regola);
	// Fa la fix render degli elementi Dyn2Espressioni presenti nella lista
	// e li setta al commnand
	fixRenderListEspressioni(exprs, cmd);
	// Nel caso siamo al primo acecsso e non ci sono ancora record; pre 
	//imposto una riga delle espressioni vuota
	if (cmd.getEspressioni().isEmpty()) {
	    List<Dyn2Espressioni> espressioni = new ArrayList<Dyn2Espressioni>();
	    espressioni.add(new Dyn2Espressioni());
	    cmd.setEspressioni(espressioni);
	}
	//cmd.getEspressioni().addAll(exprs);
	//cmd.getEspressioni().add(new Dyn2Espressioni());
	model.addAttribute("regolaCommand", cmd);
	setPageAttributes(model);
	// §§§END§§§
	return "dyn2regole/form";
    }

    @RequestMapping
    public String popupview(@RequestParam(value = "codice") Integer codice, @RequestParam(value = "callerId", required = false) Integer callerId,
	    @RequestParam(value = "newId", required = false) Integer newId, @RequestParam(value = "callerModT", required = false) Integer d2mtId,
	    Model model, HttpServletRequest request) {

	Dyn2RegoleCommand cmd = new Dyn2RegoleCommand();
	cmd.setPopup(Boolean.TRUE);
	cmd.setCallerId(callerId);
	cmd.setNewId(newId);
	cmd.setDyn2ModellotId(d2mtId);
	return view(codice, callerId, cmd, model, request);
    }

    @RequestMapping
    public String popupaddEspressione(Model model, @ModelAttribute("regolaCommand") Dyn2RegoleCommand cmd, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	return addEspressione(model, cmd, result, status, request);
    }

    @RequestMapping
    public String addEspressione(Model model, @ModelAttribute("regolaCommand") Dyn2RegoleCommand cmd, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	// §§§BEGIN§§§
	//PkId id = new PkId(codice);
	//Dyn2Regole regola = dyn2RegoleService.findById(id);
	Set<Dyn2Espressioni> exprs = cmd.getRegola().getDyn2Espressionis();
	// Creo una nuova espressione vuota e la aggiungo alla lista delle
	// espressioni già presnet enella regola
	Dyn2Espressioni dyn2Espressioni = new Dyn2Espressioni();
	dyn2Espressioni.setDyn2Regole(cmd.getRegola());
	dyn2Espressioni.setDyn2Campi(new Dyn2Campi());
	exprs.add(dyn2Espressioni);
	cmd.getRegola().setDyn2Espressionis(exprs);
	cmd.getEspressioni().add(dyn2Espressioni);
	//	Dyn2RegoleCommand cmd = new Dyn2RegoleCommand();
	//	cmd.setRegola(regola);
	// Fa la fix render degli elementi Dyn2Espressioni presenti nella lista
	// e li setta al commnand
	//fixRenderListEspressioni(exprs, cmd);
	model.addAttribute("regolaCommand", cmd);
	setPageAttributes(model);
	// §§§END§§§
	return "dyn2regole/form";
    }

    @RequestMapping
    public String update(Model model, @ModelAttribute("regolaCommand") Dyn2RegoleCommand cmd, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	// §§§BEGIN§§§
	Dyn2Regole regola = cmd.getRegola();
	// Commentato , perde ordinamento, se si inserisce più di una regola alla volta
	//regola.getDyn2Espressionis().addAll(cmd.getEspressioni());
	List<Dyn2Espressioni> espressionis = cmd.getEspressioni();
	for (Dyn2Espressioni dyn2Espressioni : espressionis) {
	    regola.getDyn2Espressionis().add(dyn2Espressioni);
	}
	fixMergeEntityProperty(regola);
	try {
	    dyn2RegoleService.update(regola);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, regola, e);
	    fixRenderEntityProperty(regola);
	    setPageAttributes(model);
	    return "dyn2regole/form";
	}
	StringBuilder sbUrl = new StringBuilder("redirect:");
	if (BooleanUtils.isTrue(cmd.getPopup())) {
	    sbUrl.append("popup");
	}
	sbUrl.append("view.htm?codice=").append(regola.getId().getCodice()).append("&callerId=");
	if (cmd.getCallerId() != null) {
	    sbUrl.append(cmd.getCallerId());
	}
	if (cmd.getNewId() != null) {
	    sbUrl.append("&newId=").append(cmd.getNewId());
	}
	sbUrl.append("&status_msg=02");
	return sbUrl.toString();
	// §§§END§§§
    }

    @RequestMapping
    public String popupupdate(Model model, @ModelAttribute("regolaCommand") Dyn2RegoleCommand cmd, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	cmd.setPopup(Boolean.TRUE);
	return update(model, cmd, result, status, request);
    }

    @RequestMapping
    public String delete(@ModelAttribute("regolaCommand") Dyn2RegoleCommand cmd, BindingResult result, SessionStatus status) {

	// §§§BEGIN§§§
	Dyn2Regole regola = cmd.getRegola();
	regola = dyn2RegoleService.findById(regola.getId());
	try {
	    dyn2RegoleService.delete(regola);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, regola, e);
	    fixRenderEntityProperty(regola);
	    return "dyn2regole/form";
	}
	status.setComplete();
	// §§§END§§§
	return "redirect:list.htm";
    }

    @RequestMapping
    public ModelAndView ajaxDeleteExpression(@RequestParam(value = "index") Integer index, HttpServletRequest request) {

	ModelAndView mev = new ModelAndView();
	Dyn2RegoleCommand cmd = (Dyn2RegoleCommand) request.getSession().getAttribute("regolaCommand");
	List<Dyn2Espressioni> exprs = cmd.getEspressioni();
	if (index < exprs.size()) {
	    Dyn2Espressioni removedExpr = exprs.remove(index.intValue());
	    if (removedExpr != null) {
		if (removedExpr.getId().getCodice() != null) {
		    cmd.getEspressioniCancellate().add(removedExpr);
		}
		mev.addObject("removedIndex", index);
	    }
	}
	mev.setViewName("jsonView");
	return mev;
    }

    @RequestMapping
    public String popupdeleteEspressione(@RequestParam("codice") Integer codice, Model model, @ModelAttribute("regolaCommand") Dyn2RegoleCommand cmd,
	    BindingResult result, SessionStatus status, HttpServletRequest request) {

	return deleteEspressione(codice, model, cmd, result, status, request);
    }

    @RequestMapping
    public String deleteEspressione(@RequestParam("codice") Integer codice, Model model, @ModelAttribute("regolaCommand") Dyn2RegoleCommand cmd,
	    BindingResult result, SessionStatus status, HttpServletRequest request) {

	PkId id = new PkId(codice);
	Dyn2Espressioni objDelete = dyn2EspressioniService.findById(id);
	try {
	    dyn2EspressioniService.delete(objDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, cmd.getRegola(), true, e);
	    //fixRenderEntityProperty(regola);
	    return "dyn2regole/form";
	}
	// §§§END§§§
	if (BooleanUtils.isTrue(cmd.getPopup())) {
	    return "redirect:" + popupcloseRedirect(cmd.getRegola().getId().getCodice(), "02", cmd.getPopupCaller());
	} else {
	    status.setComplete();
	    return "redirect:view.htm?codice=" + objDelete.getDyn2Regole().getId().getCodice();
	}
    }

    @RequestMapping
    public String popupdeleteEspressioneNonSalvata(@RequestParam("index") Integer indice, Model model,
	    @ModelAttribute("regolaCommand") Dyn2RegoleCommand cmd, BindingResult result, SessionStatus status) {

	return deleteEspressioneNonSalvata(indice, model, cmd, result, status);
    }

    @RequestMapping
    public String deleteEspressioneNonSalvata(@RequestParam("index") Integer indice, Model model,
	    @ModelAttribute("regolaCommand") Dyn2RegoleCommand cmd, BindingResult result, SessionStatus status) {

	// Recupero tutte le espressioni della regola
	List<Dyn2Espressioni> dyn2Espressionis = cmd.getEspressioni();
	List<Dyn2Espressioni> dyn2EspressionisTemp = new ArrayList<Dyn2Espressioni>();
	try {
	    // Ciclo tutte le espressioni quando trovo quella con "indice" passato, la rimuovo dalla lista
	    int contatore = 0;
	    for (Dyn2Espressioni dyn2Espressioni : dyn2Espressionis) {
		if (contatore != indice.intValue()) {
		    dyn2EspressionisTemp.add(dyn2Espressioni);
		}
		contatore++;
	    }
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, cmd.getRegola(), true, e);
	    //fixRenderEntityProperty(regola);
	    return "dyn2regole/form";
	}
	cmd.setEspressioni(dyn2EspressionisTemp);
	// Setto all'oggetto regola la lista delle espressioni bonificata da quella che 
	// ho eliminato
	Set<Dyn2Espressioni> list = new HashSet<Dyn2Espressioni>(dyn2EspressionisTemp);
	cmd.getRegola().setDyn2Espressionis(list);
	setPageAttributes(model);
	model.addAttribute("regolaCommand", cmd);
	// §§§END§§§
	return "dyn2regole/form";
    }

    @RequestMapping
    public String popupvalidaEspressioni(Model model, @ModelAttribute("regolaCommand") Dyn2RegoleCommand cmd, BindingResult result,
	    SessionStatus status) {

	return validaEspressioni(model, cmd, result, status);
    }

    @RequestMapping
    public String validaEspressioni(Model model, @ModelAttribute("regolaCommand") Dyn2RegoleCommand cmd, BindingResult result, SessionStatus status) {

	// §§§BEGIN§§§
	List<Dyn2RegoleSyntaxError> errors = dyn2RegoleService.validateExpressionSyntax(cmd.getRegola());
	List<String> warnings = new ArrayList<String>();
	if (!errors.isEmpty()) {
	    for (Dyn2RegoleSyntaxError dyn2RegoleSyntaxError : errors) {
		StringBuffer warning = new StringBuffer();
		warning.append("Espressione " + dyn2RegoleSyntaxError.getExpressionIndex() + ": "
			+ getMessageFromBundle(dyn2RegoleSyntaxError.getMessage(), null) + "\n");
		List<String> p = dyn2RegoleSyntaxError.getWrongExpressionProperties();
		warnings.add(warning.toString());
	    }
	    FlashMessages.setWarnings(warnings);
	} else {
	    List<String> infos = new ArrayList<String>();
	    infos.add("Espressioni validate");
	    FlashMessages.setInfos(infos);
	}
	model.addAttribute("regolaCommand", cmd);
	setPageAttributes(model);
	// §§§END§§§
	return "dyn2regole/form";
    }

    @Override
    protected void fixMergeEntityProperty(Dyn2Regole entity) {

    }

    @Override
    protected void fixRenderEntityProperty(Dyn2Regole entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

	boolean isCartAttivo = verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_CART);
	model.addAttribute("isCartAttivo", isCartAttivo);
    }

    /**
     * Cicla le espressioni passata e se la proprietà Dyn2Campi è null la istanzia.
     * 
     * @param dyn2Espressionis
     * @param cmd
     */
    private void fixRenderListEspressioni(Set<Dyn2Espressioni> dyn2Espressionis, Dyn2RegoleCommand cmd) {

	for (Dyn2Espressioni dyn2Espressioni : dyn2Espressionis) {
	    if (dyn2Espressioni.getDyn2Campi() == null) {
		dyn2Espressioni.setDyn2Campi(new Dyn2Campi());
	    }
	    cmd.getEspressioni().add(dyn2Espressioni);
	}
    }
}
