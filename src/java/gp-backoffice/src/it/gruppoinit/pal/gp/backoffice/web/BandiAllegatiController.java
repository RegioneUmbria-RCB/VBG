/**
 * 
 */
package it.gruppoinit.pal.gp.backoffice.web;

import java.util.Set;

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

import it.gruppoinit.pal.gp.core.domain.Bandi;
import it.gruppoinit.pal.gp.core.domain.BandiAllegati;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.service.BandiAllegatiService;
import it.gruppoinit.pal.gp.core.service.BandiService;

/**
 * @author lucap
 * 
 */
@Controller
@SessionAttributes("bandiallegati")
public class BandiAllegatiController extends BaseController<BandiAllegati> {

    @Autowired
    private BandiAllegatiService bandiAllegatiService;
    @Autowired
    private OggettiService oggettiService;
    @Autowired
    private BandiService bandiService;

    @RequestMapping
    public ModelMap list(@RequestParam("codice") Integer codice, HttpServletRequest request, HttpServletResponse response) {

	PkId id = new PkId(codice);
	Bandi bando = bandiService.findById(id);
	Set<BandiAllegati> bandiallegatiList = bando.getBandiallegatis();
	ModelMap model = new ModelMap(bandiallegatiList);
	boolean export = createJMesaExport(request, response, bandiallegatiList);
	if (export)
	    return null;
	model.addAttribute("bandiallegatiList", bandiallegatiList);
	model.addAttribute("bando", bando);
	return model;
    }

    @RequestMapping
    public String delete(@ModelAttribute("bandiallegati") BandiAllegati bandiallegati, BindingResult result, SessionStatus status, Model model) {

	Bandi bando = bandiallegati.getBandi();
	BandiAllegati objToDelete = bandiAllegatiService.findById(bandiallegati.getId());
	try {
	    bandiAllegatiService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderEntityProperty(bandiallegati);
	    model.addAttribute("bando", bando);
	    return "bandiallegati/form";
	}
	status.setComplete();
	return "redirect:list.htm?codice=" + bando.getId().getCodice();
    }

    @RequestMapping
    public String insert(@ModelAttribute("bandiallegati") BandiAllegati bandiallegati, BindingResult result, SessionStatus status, Model model) {

	// recupero l'oggetto corrente e lo inserisco in bandiallegati
	Oggetti oggetto = oggettiService.findById(bandiallegati.getOggetto().getId());
	bandiallegati.setOggetto(oggetto);
	// recupero il bando corrente e lo inserisco in bandiallegati
	Bandi bando = bandiService.findById(bandiallegati.getBandi().getId());
	bandiallegati.setBandi(bando);
	fixMergeEntityProperty(bandiallegati);
	try {
	    bandiAllegatiService.insert(bandiallegati);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, bandiallegati, e);
	    fixRenderEntityProperty(bandiallegati);
	    model.addAttribute("bando", bando);
	    return "bandiallegati/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + bandiallegati.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String update(@ModelAttribute("bandiallegati") BandiAllegati bandiallegati, BindingResult result, SessionStatus status, Model model,
	    HttpServletRequest request) {

	// recupero l'oggetto corrente e lo inserisco in bandiallegati
	Oggetti oggetto = oggettiService.findById(bandiallegati.getOggetto().getId());
	bandiallegati.setOggetto(oggetto);
	// recupero il bando corrente e lo inserisco in bandiallegati
	Bandi bando = bandiService.findById(bandiallegati.getBandi().getId());
	bandiallegati.setBandi(bando);
	fixMergeEntityProperty(bandiallegati);
	try {
	    bandiAllegatiService.update(bandiallegati);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, bandiallegati, e);
	    fixRenderEntityProperty(bandiallegati);
	    model.addAttribute("bando", bando);
	    return "bandiallegati/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + bandiallegati.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String create(@RequestParam("codice") Integer codice, Model model) {

	PkId id = new PkId(codice);
	Bandi bando = bandiService.findById(id);
	BandiAllegati bandiallegati = new BandiAllegati();
	bandiallegati.setBandi(bando);
	fixRenderEntityProperty(bandiallegati);
	model.addAttribute("bandiallegati", bandiallegati);
	model.addAttribute("bando", bando);
	setPageAttributes(model);
	return "bandiallegati/form";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	BandiAllegati bandiallegati = bandiAllegatiService.findById(id);
	Bandi bando = bandiallegati.getBandi();
	fixRenderEntityProperty(bandiallegati);
	model.addAttribute("bandiallegati", bandiallegati);
	model.addAttribute("bando", bando);
	setPageAttributes(model);
	return "bandiallegati/form";
    }

    @Override
    protected void fixMergeEntityProperty(BandiAllegati entity) {

	if (entity.getOggetto() != null && entity.getOggetto().getId() != null && entity.getOggetto().getId().getCodice() == null) {
	    entity.setOggetto(null);
	}
	if (entity.getBandi() != null && entity.getBandi().getId() != null && entity.getBandi().getId().getCodice() == null) {
	    entity.setBandi(null);
	}
    }

    @Override
    protected void fixRenderEntityProperty(BandiAllegati entity) {

	if (entity.getOggetto() == null) {
	    entity.setOggetto(new Oggetti());
	}
	if (entity.getBandi() == null) {
	    entity.setBandi(new Bandi());
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
