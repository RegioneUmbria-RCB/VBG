package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.Dyn2Campi;
import it.gruppoinit.pal.gp.core.domain.Dyn2Modellit;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipigraduatoriet;
import it.gruppoinit.pal.gp.core.domain.TipigraduatorietEsprArt;
import it.gruppoinit.pal.gp.core.domain.web.TipigraduatorietEsprArtCommand;
import it.gruppoinit.pal.gp.core.service.TipigraduatorietEsprArtService;
import it.gruppoinit.pal.gp.core.service.TipigraduatorietService;

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
@SessionAttributes("tipigraduatorietesprart")
public class TipigraduatorietEsprArtController extends BaseController<TipigraduatorietEsprArt> {

    @Autowired
    private TipigraduatorietEsprArtService tipigraduatorietesprartService;
    @Autowired
    private TipigraduatorietService tipigraduatorietService;

    @RequestMapping
    public ModelMap list(@RequestParam("codice") Integer codice, HttpServletRequest request, HttpServletResponse response) {

	//List<TipigraduatorietEsprArt> tipigraduatorietesprartList = tipigraduatorietesprartService.findAll(null, null);
	List<TipigraduatorietEsprArt> tipigraduatorietesprartList = tipigraduatorietesprartService.findByTipigraduatoriet(codice);
	Tipigraduatoriet tipigraduatoriet = tipigraduatorietService.findById(new PkId(codice));
	ModelMap model = new ModelMap(tipigraduatorietesprartList);
	boolean export = createJMesaExport(request, response, tipigraduatorietesprartList);
	if (export) {
	    return null;
	}
	model.addAttribute("tipigraduatoriet", tipigraduatoriet);
	model.addAttribute("tipigraduatorietesprartList", tipigraduatorietesprartList);
	return model;
    }

    @RequestMapping
    public String create(@RequestParam("codiceTipigraduatoriat") Integer codiceTipigraduatoriat, Model model) {

	TipigraduatorietEsprArtCommand tipigraduatorietesprart = new TipigraduatorietEsprArtCommand();
	TipigraduatorietEsprArt entity = new TipigraduatorietEsprArt();
	Tipigraduatoriet tipigraduatoriet = tipigraduatorietService.findById(new PkId(codiceTipigraduatoriat));
	entity.setTipigraduatoriet(tipigraduatoriet);
	tipigraduatorietesprart.setEntity(entity);
	fixRenderEntityProperty(tipigraduatorietesprart.getEntity());
	model.addAttribute("tipigraduatorietesprart", tipigraduatorietesprart);
	setPageAttributes(model);
	return "tipigraduatorietesprart/form";
    }

    @RequestMapping
    public String insert(@ModelAttribute("tipigraduatorietesprart") TipigraduatorietEsprArtCommand tipigraduatorietesprart, BindingResult result,
	    SessionStatus status) {

	TipigraduatorietEsprArt entity = tipigraduatorietesprart.getEntity();
	fixMergeEntityProperty(tipigraduatorietesprart.getEntity());
	try {
	    tipigraduatorietesprartService.insert(entity);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, tipigraduatorietesprart.getEntity(), true, e);
	    tipigraduatorietesprart.setDyn2Modellit(new Dyn2Modellit());
//	    tipigraduatorietesprart.setDyn2ModellitDa(new Dyn2Modellit());
//	    tipigraduatorietesprart.setDyn2ModellitPosteggio(new Dyn2Modellit());
	    tipigraduatorietesprart.setEntity(entity);
	    fixRenderEntityProperty(tipigraduatorietesprart.getEntity());
	    return "tipigraduatorietesprart/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + tipigraduatorietesprart.getEntity().getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	TipigraduatorietEsprArt entity = tipigraduatorietesprartService.findById(id);
	TipigraduatorietEsprArtCommand tipigraduatorietesprart = new TipigraduatorietEsprArtCommand();
	tipigraduatorietesprart.setEntity(entity);
	fixRenderEntityProperty(tipigraduatorietesprart.getEntity());
	model.addAttribute("tipigraduatorietesprart", tipigraduatorietesprart);
	setPageAttributes(model);
	return "tipigraduatorietesprart/form";
    }

    @RequestMapping
    public String update(@ModelAttribute("tipigraduatorietesprart") TipigraduatorietEsprArtCommand tipigraduatorietesprart, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	TipigraduatorietEsprArt entity = tipigraduatorietesprart.getEntity();
	fixMergeEntityProperty(tipigraduatorietesprart.getEntity());
	try {
	    tipigraduatorietesprartService.update(entity);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, tipigraduatorietesprart.getEntity(), true, e);
	    tipigraduatorietesprart.setDyn2Modellit(new Dyn2Modellit());
	    //	    tipigraduatorietesprart.setDyn2ModellitDa(new Dyn2Modellit());
	    //	    tipigraduatorietesprart.setDyn2ModellitPosteggio(new Dyn2Modellit());
	    tipigraduatorietesprart.setEntity(entity);
	    fixRenderEntityProperty(tipigraduatorietesprart.getEntity());
	    return "tipigraduatorietesprart/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + tipigraduatorietesprart.getEntity().getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String delete(@ModelAttribute("tipigraduatorietesprart") TipigraduatorietEsprArtCommand tipigraduatorietesprart, BindingResult result,
	    SessionStatus status) {

	TipigraduatorietEsprArt entity = tipigraduatorietesprart.getEntity();
	TipigraduatorietEsprArt objToDelete = tipigraduatorietesprartService.findById(entity.getId());
	try {
	    tipigraduatorietesprartService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, true, e);
	    fixRenderEntityProperty(tipigraduatorietesprart.getEntity());
	    return "tipigraduatorietesprart/form";
	}
	status.setComplete();
	return "redirect:list.htm?codice=" + tipigraduatorietesprart.getEntity().getTipigraduatoriet().getId().getCodice();
    }

    @Override
    protected void fixMergeEntityProperty(TipigraduatorietEsprArt entity) {

    }

    @Override
    protected void fixRenderEntityProperty(TipigraduatorietEsprArt entity) {

	if (entity.getCampiA() == null) {
	    entity.setCampiA(new Dyn2Campi());
	}
	if (entity.getCampiDa() == null) {
	    entity.setCampiDa(new Dyn2Campi());
	}
	if (entity.getCampiPosteggio() == null) {
	    entity.setCampiPosteggio(new Dyn2Campi());
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
