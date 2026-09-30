package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.AlboCategorie;
import it.gruppoinit.pal.gp.core.domain.AlboPubblicazioni;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.service.AlboCategorieService;
import it.gruppoinit.pal.gp.core.service.AlboPubblicazioniService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

@Controller
@SessionAttributes("albocategorie")
public class AlboCategorieController extends BaseController<AlboCategorie> {

    @Autowired
    private AlboCategorieService alboCategorieService;
    @Autowired
    private SoftwareService softwareService;
    @Autowired
    private AlboPubblicazioniService alboPubblicazioniService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	// §§§BEGIN§§§
	List<AlboCategorie> alboCategorieList = alboCategorieService.findAll(null, null);
	ModelMap model = new ModelMap(alboCategorieList);
	boolean export = createJMesaExport(request, response, alboCategorieList);
	if (export)
	    return null;
	model.addAttribute("alboCategorieList", alboCategorieList);
	return model;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String delete(@ModelAttribute("albocategorie") AlboCategorie albocategorie, BindingResult result, SessionStatus status) {

	// §§§BEGIN§§§
	AlboCategorie objToDelete = alboCategorieService.findById(albocategorie.getId());
	try {
	    alboCategorieService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, albocategorie, e);
	    fixRenderEntityProperty(albocategorie);
	    return "albocategorie/form";
	}
	status.setComplete();
	return "redirect:list.htm";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String deletePubblicazioneFromCodice(@RequestParam("codice") Integer codice, Model model,
	    @ModelAttribute("albocategorie") AlboCategorie albocategorie, BindingResult result, SessionStatus status) {

	// §§§BEGIN§§§
	AlboPubblicazioni objToDelete = alboPubblicazioniService.findById(new PkId(codice));
	try {
	    alboPubblicazioniService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, albocategorie, e);
	    Map<String, String> map = new HashMap<String, String>();
	    map.put("codice", albocategorie.getId().getCodice().toString());
	    model.addAttribute("commandName", "albocategorie");
	    model.addAttribute("method", "view.htm");
	    model.addAttribute("queryStringParams", map);
	    return "error";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + albocategorie.getId().getCodice();
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String insert(@ModelAttribute("albocategorie") AlboCategorie albocategorie, BindingResult result, SessionStatus status) {

	// §§§BEGIN§§§
	// recupero il software corrente e lo setto in alberoprocdocumenticat
	Software software = softwareService.findById(ORMHelper.getSoftware());
	albocategorie.setSoftware(software);
	fixMergeEntityProperty(albocategorie);
	try {
	    alboCategorieService.insert(albocategorie);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, albocategorie, e);
	    fixRenderEntityProperty(albocategorie);
	    return "albocategorie/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + albocategorie.getId().getCodice() + "&status_msg=01";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String update(@ModelAttribute("albocategorie") AlboCategorie albocategorie, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	// §§§BEGIN§§§
	// recupero il software corrente e lo setto in alberoprocdocumenticat
	Software software = softwareService.findById(ORMHelper.getSoftware());
	albocategorie.setSoftware(software);
	fixMergeEntityProperty(albocategorie);
	try {
	    alboCategorieService.update(albocategorie);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, albocategorie, e);
	    fixRenderEntityProperty(albocategorie);
	    return "albocategorie/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + albocategorie.getId().getCodice() + "&status_msg=02";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String create(Model model) {

	// §§§BEGIN§§§
	AlboCategorie alboCategorie = new AlboCategorie();
	alboCategorie.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
	fixRenderEntityProperty(alboCategorie);
	model.addAttribute("albocategorie", alboCategorie);
	setPageAttributes(model);
	return "albocategorie/form";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	// §§§BEGIN§§§
	PkId id = new PkId(codice);
	AlboCategorie albocategorie = alboCategorieService.findById(id);
	fixRenderEntityProperty(albocategorie);
	model.addAttribute("albocategorie", albocategorie);
	setPageAttributes(model);
	return "albocategorie/form";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    protected void fixMergeEntityProperty(AlboCategorie entity) {

	if (entity.getSoftware() != null && (entity.getSoftware().getCodice() == null || entity.getSoftware().getCodice().equals(""))) {
	    entity.setSoftware(null);
	}
    }

    @Override
    protected void fixRenderEntityProperty(AlboCategorie entity) {

	if (entity.getSoftware() == null) {
	    entity.setSoftware(new Software());
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

	// TODO Auto-generated method stub
    }
}
