package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.CommedilizieTipopareri;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipimovimento;
import it.gruppoinit.pal.gp.core.features.amministrazioni.collegate.model.AmministrazioneCollegataBean;
import it.gruppoinit.pal.gp.core.features.commissioni.model.CommissioniEdiliziePareriMovimentiModel;
import it.gruppoinit.pal.gp.core.features.commissioni.model.EsitoOperazioneDML;
import it.gruppoinit.pal.gp.core.service.CommedilizieTipopareriService;

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

/**
 * 
 * @author
 */
@Controller
@SessionAttributes("commedilizietipopareri")
public class CommedilizieTipopareriController extends BaseJsonController<CommedilizieTipopareri> {

    @Autowired
    private CommedilizieTipopareriService commedilizietipopareriService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	// §§§BEGIN§§§
	List<CommedilizieTipopareri> commedilizietipopareriList = commedilizietipopareriService.findAll(null, null);
	ModelMap model = new ModelMap(commedilizietipopareriList);
	boolean export = createJMesaExport(request, response, commedilizietipopareriList);
	if (export) {
	    return null;
	}
	model.addAttribute("commedilizietipopareriList", commedilizietipopareriList);
	return model;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String create(Model model) {

	// §§§BEGIN§§§
	CommedilizieTipopareri commedilizietipopareri = new CommedilizieTipopareri();
	fixRenderEntityProperty(commedilizietipopareri);
	model.addAttribute("commedilizietipopareri", commedilizietipopareri);
	setPageAttributes(model);
	// §§§END§§§
	return "commedilizietipopareri/form";
    }

    @RequestMapping
    public String insert(@ModelAttribute("commedilizietipopareri") CommedilizieTipopareri commedilizietipopareri, BindingResult result,
	    SessionStatus status) {

	// §§§BEGIN§§§
	fixMergeEntityProperty(commedilizietipopareri);
	try {
	    commedilizietipopareriService.insert(commedilizietipopareri);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, commedilizietipopareri, e);
	    fixRenderEntityProperty(commedilizietipopareri);
	    return "commedilizietipopareri/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + commedilizietipopareri.getId().getCodice() + "&status_msg=01";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	// §§§BEGIN§§§
	PkId id = new PkId(codice);
	CommedilizieTipopareri commedilizietipopareri = commedilizietipopareriService.findById(id);
	fixRenderEntityProperty(commedilizietipopareri);
	model.addAttribute("commedilizietipopareri", commedilizietipopareri);
	setPageAttributes(model);
	// §§§END§§§
	return "commedilizietipopareri/form";
    }

    @RequestMapping
    public String update(@ModelAttribute("commedilizietipopareri") CommedilizieTipopareri commedilizietipopareri, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	// §§§BEGIN§§§
	fixMergeEntityProperty(commedilizietipopareri);
	try {
	    commedilizietipopareriService.update(commedilizietipopareri);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, commedilizietipopareri, e);
	    fixRenderEntityProperty(commedilizietipopareri);
	    return "commedilizietipopareri/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + commedilizietipopareri.getId().getCodice() + "&status_msg=02";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String delete(@ModelAttribute("commedilizietipopareri") CommedilizieTipopareri commedilizietipopareri, BindingResult result,
	    SessionStatus status) {

	// §§§BEGIN§§§
	CommedilizieTipopareri objToDelete = commedilizietipopareriService.findById(commedilizietipopareri.getId());
	try {
	    commedilizietipopareriService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderEntityProperty(commedilizietipopareri);
	    return "commedilizietipopareri/form";
	}
	status.setComplete();
	return "redirect:list.htm";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public void ajaxFindMovimenti(Model model, @RequestParam("codice") Integer codice, HttpServletRequest request, HttpServletResponse response)
	    throws IOException, JAXBException {

	List<CommissioniEdiliziePareriMovimentiModel> list = commedilizietipopareriService.findMovimentiConfigurati(codice);
	response.setContentType("application/json");
	response.getOutputStream().write(this.listToJsonBytes(list, CommissioniEdiliziePareriMovimentiModel.class, false));
	response.getOutputStream().flush();
    }

    @RequestMapping
    public void ajaxAssegnaMovimento(Model model, @RequestParam("codice") Integer codice, @RequestParam("software") String software,
	    @RequestParam("tipomovimento") String tipomovimento, HttpServletRequest request, HttpServletResponse response)
	    throws IOException, JAXBException {

	EsitoOperazioneDML esito = null;
	try {
	    esito = commedilizietipopareriService.insertMovimentoPerSoftware(codice, software, tipomovimento);
	} catch (Exception e) {
	    esito = new EsitoOperazioneDML(false, "Errore nell'inserimento: " + e.getMessage());
	}
	response.setContentType("application/json");
	response.getOutputStream().write(this.toJsonBytes(esito));
	response.getOutputStream().flush();
    }

    @RequestMapping
    public void ajaxEliminaMovimento(Model model, @RequestParam("codice") Integer codice, @RequestParam("software") String software,
	    HttpServletRequest request, HttpServletResponse response) throws IOException, JAXBException {

	EsitoOperazioneDML esito = null;
	try {
	    esito = commedilizietipopareriService.eliminaMovimentoPerSoftware(codice, software);
	} catch (Exception e) {
	    esito = new EsitoOperazioneDML(false, "Errore nella cancellazione: " + e.getMessage());
	}
	response.setContentType("application/json");
	response.getOutputStream().write(this.toJsonBytes(esito));
	response.getOutputStream().flush();
    }

    @Override
    protected void fixMergeEntityProperty(CommedilizieTipopareri entity) {

    }

    @Override
    protected void fixRenderEntityProperty(CommedilizieTipopareri entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
