package it.gruppoinit.pal.gp.backoffice.web;

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

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.domain.CommedilizieConvocazioni;
import it.gruppoinit.pal.gp.core.domain.CommissioniedilizieT;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.CommedilizieConvocazioniService;
import it.gruppoinit.pal.gp.core.service.CommissioniedilizieTService;
import it.gruppoinit.pal.gp.core.utils.Utilities;

/**
 * 
 * @author gianpaolot
 */
@Controller
@SessionAttributes("commedilizieconvocazioni")
public class CommedilizieConvocazioniController extends BaseController<CommedilizieConvocazioni> {

    @Autowired
    private CommedilizieConvocazioniService commedilizieconvocazioniService;
    @Autowired
    private CommissioniedilizieTService commissioniedilizieTService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	// §§§BEGIN§§§
	List<CommedilizieConvocazioni> commedilizieconvocazioniList = commedilizieconvocazioniService.findAll(null, null);
	ModelMap model = new ModelMap(commedilizieconvocazioniList);
	boolean export = createJMesaExport(request, response, commedilizieconvocazioniList);
	if (export) {
	    return null;
	}
	model.addAttribute("commedilizieconvocazioniList", commedilizieconvocazioniList);
	model.addAttribute("sizeList", commedilizieconvocazioniList.size());
	return model;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String create(@RequestParam("codiceCommissione") Integer codiceCommissione, Model model) {

	// §§§BEGIN§§§
	CommissioniedilizieT commissioniedilizieT = commissioniedilizieTService.findById(new PkId(codiceCommissione));
	CommedilizieConvocazioni commedilizieconvocazioni = new CommedilizieConvocazioni();
	commedilizieconvocazioni.setCommissioniedilizieT(commissioniedilizieT);
	fixRenderEntityProperty(commedilizieconvocazioni);
	model.addAttribute("commedilizieconvocazioni", commedilizieconvocazioni);
	setPageAttributes(model);
	return "commedilizieconvocazioni/form";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String insert(@ModelAttribute("commedilizieconvocazioni") CommedilizieConvocazioni commedilizieconvocazioni, BindingResult result,
	    SessionStatus status) {

	try {
	    this.verificaDuplicatoConvocazione(commedilizieconvocazioni);
	    commedilizieconvocazioniService.insert(commedilizieconvocazioni);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, commedilizieconvocazioni, e);
	    fixRenderEntityProperty(commedilizieconvocazioni);
	    return "commedilizieconvocazioni/form";
	}
	status.setComplete();
	return "redirect:../commissioniediliziet/view.htm?codice=" + commedilizieconvocazioni.getCommissioniedilizieT().getId().getCodice() +
	       "&status_msg=01";
    }

    private void verificaDuplicatoConvocazione(CommedilizieConvocazioni commedilizieconvocazioni) {

	//rilancio errore se esiste già una convocazione a un determinata data e orario per quella commissione
	List<CommedilizieConvocazioni> list = commedilizieconvocazioniService
		.findByCommissioneEdiliziaT(commedilizieconvocazioni.getCommissioniedilizieT());
	if (!list.isEmpty()) {
	    for (CommedilizieConvocazioni commConv : list) {
		if (commConv.getDataconvocazione().equals(commedilizieconvocazioni.getDataconvocazione())
			&& commConv.getOraconvocazione().equals(commedilizieconvocazioni.getOraconvocazione())) {
		    String dataConv = Utilities.formatDate(commedilizieconvocazioni.getDataconvocazione(), WebConstants.DATE_FORMAT_PATTERN);
		    String messaggio = "È già presente una convocazione per la data: " + dataConv + " alle ore: " +
				       commedilizieconvocazioni.getOraconvocazione();
		    throw new RuntimeException(messaggio);
		}
	    }
	}
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	// §§§BEGIN§§§
	PkId id = new PkId(codice);
	CommedilizieConvocazioni commedilizieconvocazioni = commedilizieconvocazioniService.findById(id);
	fixRenderEntityProperty(commedilizieconvocazioni);
	model.addAttribute("commedilizieconvocazioni", commedilizieconvocazioni);
	setPageAttributes(model);
	return "commedilizieconvocazioni/form";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String update(@ModelAttribute("commedilizieconvocazioni") CommedilizieConvocazioni commedilizieconvocazioni, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	fixMergeEntityProperty(commedilizieconvocazioni);
	try {
	    this.verificaDuplicatoConvocazione(commedilizieconvocazioni);
	    commedilizieconvocazioniService.update(commedilizieconvocazioni);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, commedilizieconvocazioni, e);
	    fixRenderEntityProperty(commedilizieconvocazioni);
	    return "commedilizieconvocazioni/form";
	}
	status.setComplete();
	return "redirect:../commissioniediliziet/view.htm?codice=" + commedilizieconvocazioni.getCommissioniedilizieT().getId().getCodice() +
	       "&status_msg=02";
    }

    @RequestMapping
    public String delete(@ModelAttribute("commedilizieconvocazioni") CommedilizieConvocazioni commedilizieconvocazioni, BindingResult result,
	    SessionStatus status) {

	// §§§BEGIN§§§
	CommedilizieConvocazioni objToDelete = commedilizieconvocazioniService.findById(commedilizieconvocazioni.getId());
	try {
	    commedilizieconvocazioniService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderEntityProperty(commedilizieconvocazioni);
	    return "commedilizieconvocazioni/form";
	}
	status.setComplete();
	return "redirect:../commissioniediliziet/view.htm?codice=" + objToDelete.getCommissioniedilizieT().getId().getCodice();
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    protected void fixMergeEntityProperty(CommedilizieConvocazioni entity) {

    }

    @Override
    protected void fixRenderEntityProperty(CommedilizieConvocazioni entity) {

	if (entity.getCommissioniedilizieT() == null) {
	    entity.setCommissioniedilizieT(new CommissioniedilizieT());
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
