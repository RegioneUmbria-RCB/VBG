package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.AmministrazioniAnagrafe;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.AmministrazioniAnagrafeService;
import it.gruppoinit.pal.gp.core.service.AmministrazioniService;
import it.gruppoinit.pal.gp.core.service.AnagrafeService;
import it.gruppoinit.pal.gp.core.service.exception.BusinessValidationException;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 * 
 * @author
 */
@Controller
@SessionAttributes("amministrazionianagrafe")
public class AmministrazioniAnagrafeController extends BaseController<AmministrazioniAnagrafe> {

    @Autowired
    private AmministrazioniAnagrafeService amministrazionianagrafeService;
    @Autowired
    private AmministrazioniService amministrazioniService;
    @Autowired
    private AnagrafeService anagrafeService;

    @RequestMapping
    public String listanagrafe(@RequestParam("codiceamministrazione") Integer codiceamministrazione, Model model, HttpServletRequest request,
	    HttpServletResponse response) {

	Amministrazioni amministrazione = amministrazioniService.findById(new PkId(codiceamministrazione));
	AmministrazioniAnagrafe amministrazionianagrafe = new AmministrazioniAnagrafe();
	amministrazionianagrafe.setAmministrazioni(amministrazione);
	fixRenderEntityProperty(amministrazionianagrafe);
	model.addAttribute("amministrazione", amministrazione);
	model.addAttribute("amministrazionianagrafe", amministrazionianagrafe);
	return "amministrazionianagrafe/listanagrafe";
    }

    @RequestMapping
    public String ajaxDettaglioAnagrafe(@RequestParam("codiceamministrazione") Integer codiceamministrazione,
	    @RequestParam(required = false, value = "codiceAnagrafeInserito") Integer codiceAnagrafeInserito, Model model,
	    HttpServletRequest request, HttpServletResponse response) throws Exception {

	List<AmministrazioniAnagrafe> gds = amministrazionianagrafeService.findAmministrazione(codiceamministrazione);
	model.addAttribute("gds", gds);
	model.addAttribute("codiceAnagrafeInserito", codiceAnagrafeInserito);
	return "amministrazionianagrafe/ajaxDettaglioAnagrafe";
    }

    @RequestMapping
    public void ajaxEliminaAnagrafe(@RequestParam("idRiga") Integer idRiga, Model model, HttpServletRequest request, HttpServletResponse response)
	    throws Exception {

	String result = "OK";
	try {
	    AmministrazioniAnagrafe entity = amministrazionianagrafeService.findById(new PkId(idRiga));
	    amministrazionianagrafeService.delete(entity);
	} catch (Exception e) {
	    result = "Si e' verificato un errore nella cancellazione del dato. (dettaglio:" + e.getMessage() + ")";
	}
	response.getOutputStream().write(result.getBytes());
    }

    @RequestMapping
    public void ajaxAssegnaAnagrafe(@RequestParam("codiceAmministrazione") Integer codiceAmministrazione,
	    @RequestParam("codiceAnagrafe") Integer codiceAnagrafe, Model model, HttpServletRequest request, HttpServletResponse response)
	    throws Exception {

	String result = "OK";
	try {
	    Anagrafe a = anagrafeService.findById(new PkId(codiceAnagrafe));
	    Amministrazioni amm = amministrazioniService.findById(new PkId(codiceAmministrazione));
	    //	    List<AmministrazioniAnagrafe> list = amministrazionianagrafeService.findAmministrazione(codiceAmministrazione);
	    //	    boolean trovato = false;
	    //	    for (AmministrazioniAnagrafe aa : list) {
	    //		if (EntityUtils.getNestedProperty(aa.getAmministrazioni(), "id.codice") != null
	    //			&& EntityUtils.getNestedProperty(aa.getAnagrafe(), "id.codice") != null) {
	    //		    if (codiceAnagrafe.equals(aa.getAnagrafe().getId().getCodice())) {
	    //			trovato = true;
	    //			break;
	    //		    }
	    //		}
	    //	    }
	    //	    if (trovato) {
	    //		//result = "Attenzione!! L'anagrage " + a.getDescrizioneRichiedente() + " e' gia' assegnato al mercato";
	    //		result = getMessageFromBundle("service_error.anagrafe_presente_per_amministrazione", new Object[] { a.getDescrizioneRichiedente() });
	    //	    } else {
	    //		AmministrazioniAnagrafe entity = new AmministrazioniAnagrafe();
	    //		entity.setAmministrazioni(amm);
	    //		entity.setAnagrafe(a);
	    //		amministrazionianagrafeService.insert(entity);
	    //	    }
	    AmministrazioniAnagrafe entity = new AmministrazioniAnagrafe();
	    entity.setAmministrazioni(amm);
	    entity.setAnagrafe(a);
	    amministrazionianagrafeService.insert(entity);
	} catch (Exception e) {
	    if (e instanceof BusinessValidationException) {
		result = e.getMessage();
	    } else {
		result = "Si e' verificato un errore durante l'inserimento del dato. (dettaglio:" + e.getMessage() + ")";
	    }
	}
	response.getOutputStream().write(result.getBytes());
    }

    @RequestMapping
    public void ajaxChangeFlagInoltraComunicazione(@RequestParam("codice") Integer codice, HttpServletResponse response) throws Exception {

	AmministrazioniAnagrafe amministrazioniAnagrafe = amministrazionianagrafeService.findById(new PkId(codice));
	if (BooleanUtils.isFalse(BooleanUtils.toBoolean(amministrazioniAnagrafe.getInoltraComunicazione()))) {
	    amministrazioniAnagrafe.setInoltraComunicazione(Boolean.TRUE);
	} else {
	    amministrazioniAnagrafe.setInoltraComunicazione(Boolean.FALSE);
	}
	amministrazionianagrafeService.update(amministrazioniAnagrafe);
	response.getWriter().write(getMessageFromBundle("label.datoaggiornato", null));
    }

    /*
        @RequestMapping
        public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

    	List<AmministrazioniAnagrafe> amministrazionianagrafeList = amministrazionianagrafeService.findAll(null, null);
    	ModelMap model = new ModelMap(amministrazionianagrafeList);
    	boolean export = createJMesaExport(request, response, amministrazionianagrafeList);
    	if (export) {
    	    return null;
    	}
    	model.addAttribute("amministrazionianagrafeList", amministrazionianagrafeList);
    	return model;
        }

        @RequestMapping
        public String create(Model model) {

    	AmministrazioniAnagrafe amministrazionianagrafe = new AmministrazioniAnagrafe();
    	
    	fixRenderEntityProperty(amministrazionianagrafe);
    	model.addAttribute("amministrazionianagrafe", amministrazionianagrafe);
    	setPageAttributes(model);
    	return "amministrazionianagrafe/form";
        }

        @RequestMapping
        public String insert(@ModelAttribute("amministrazionianagrafe") AmministrazioniAnagrafe amministrazionianagrafe, BindingResult result,
    	    SessionStatus status) {

    	fixMergeEntityProperty(amministrazionianagrafe);
    	
    	try {
    	    amministrazionianagrafeService.insert(amministrazionianagrafe);
    	} catch (Exception e) {
    	    copyErrorsToBindingResult(amministrazionianagrafeService.getValidationMessages(), result, amministrazionianagrafe, e.getMessage());
    	    fixRenderEntityProperty(amministrazionianagrafe);
    	    return "amministrazionianagrafe/form";
    	}
    	status.setComplete();
    	return "redirect:view.htm?codice=" + amministrazionianagrafe.getId().getCodice() + "&status_msg=01";
        }

        @RequestMapping
        public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

    	PkId id = new PkId(codice);
    	AmministrazioniAnagrafe amministrazionianagrafe = amministrazionianagrafeService.findById(id);
    	fixRenderEntityProperty(amministrazionianagrafe);
    	model.addAttribute("amministrazionianagrafe", amministrazionianagrafe);
    	setPageAttributes(model);
    	return "amministrazionianagrafe/form";
        }

        @RequestMapping
        public String update(@ModelAttribute("amministrazionianagrafe") AmministrazioniAnagrafe amministrazionianagrafe, BindingResult result,
    	    SessionStatus status, HttpServletRequest request) {

    	fixMergeEntityProperty(amministrazionianagrafe);
    	try {
    	    amministrazionianagrafeService.update(amministrazionianagrafe);
    	} catch (Exception e) {
    	    copyErrorsToBindingResult(amministrazionianagrafeService.getValidationMessages(), result, amministrazionianagrafe, e.getMessage());
    	    fixRenderEntityProperty(amministrazionianagrafe);
    	    return "amministrazionianagrafe/form";
    	}
    	status.setComplete();
    	return "redirect:view.htm?codice=" + amministrazionianagrafe.getId().getCodice() + "&status_msg=02";
        }

        @RequestMapping
        public String delete(@ModelAttribute("amministrazionianagrafe") AmministrazioniAnagrafe amministrazionianagrafe, BindingResult result,
    	    SessionStatus status) {

    	AmministrazioniAnagrafe objToDelete = amministrazionianagrafeService.findById(amministrazionianagrafe.getId());
    	try {
    	    amministrazionianagrafeService.delete(objToDelete);
    	} catch (Exception e) {
    	    copyErrorsToBindingResult(amministrazionianagrafeService.getValidationMessages(), result, objToDelete, e.getMessage());
    	    fixRenderEntityProperty(amministrazionianagrafe);
    	    return "amministrazionianagrafe/form";
    	}
    	status.setComplete();
    	return "redirect:list.htm";
        }
    */
    @Override
    protected void fixMergeEntityProperty(AmministrazioniAnagrafe entity) {

    }

    @Override
    protected void fixRenderEntityProperty(AmministrazioniAnagrafe entity) {

	if (entity.getAmministrazioni() == null) {
	    entity.setAmministrazioni(new Amministrazioni());
	}
	if (entity.getAnagrafe() == null) {
	    entity.setAnagrafe(new Anagrafe());
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
