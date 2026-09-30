package it.gruppoinit.pal.gp.backoffice.web;

import java.util.ArrayList;
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

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.SorteggiCategorie;
import it.gruppoinit.pal.gp.core.domain.Sorteggitestata;
import it.gruppoinit.pal.gp.core.features.sorteggi.categorie.SorteggiCategorieService;
import it.gruppoinit.pal.gp.core.features.sorteggi.testata.SorteggitestataService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;

/**
 * 
 * @author fabrizioc
 * 
 */
@Controller
@SessionAttributes("sorteggiCategorie")
public class SorteggiCategorieController extends BaseController<SorteggiCategorie> {

    @Autowired
    private SorteggiCategorieService sorteggiCategorieService;
    @Autowired
    private SorteggitestataService sorteggitestataService;
    @Autowired
    private SoftwareService softwareService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	// §§§BEGIN§§§
	List<SorteggiCategorie> sorteggiCategorieList = sorteggiCategorieService.findAll(null, null);
	ModelMap model = new ModelMap();
	if (createJMesaExport(request, response, sorteggiCategorieList)) {
	    return null;
	}
	model.addAttribute("sorteggiCategorieList", sorteggiCategorieList);
	return model;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String create(Model model) {

	// §§§BEGIN§§§
	SorteggiCategorie sorteggiCategorie = new SorteggiCategorie();
	sorteggiCategorie.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
	fixRenderEntityProperty(sorteggiCategorie);
	model.addAttribute("sorteggiCategorie", sorteggiCategorie);
	setPageAttributes(model);
	return "sorteggicategorie/form";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String insert(@ModelAttribute("sorteggiCategorie") SorteggiCategorie sorteggiCategorie, BindingResult result, SessionStatus status) {

	// §§§BEGIN§§§
	fixMergeEntityProperty(sorteggiCategorie);
	try {
	    sorteggiCategorieService.insert(sorteggiCategorie);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, sorteggiCategorie, e);
	    fixRenderEntityProperty(sorteggiCategorie);
	    return "sorteggicategorie/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + sorteggiCategorie.getId().getCodice() + "&status_msg=01";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request, HttpServletResponse response) {

	// §§§BEGIN§§§
	PkId id = new PkId(codice);
	SorteggiCategorie sorteggiCategorie = sorteggiCategorieService.findById(id);
	if (createJMesaExport(request, response, sorteggiCategorie.getSorteggiList())) {
	    return null;
	}
	fixRenderEntityProperty(sorteggiCategorie);
	model.addAttribute("sorteggiCategorie", sorteggiCategorie);
	setPageAttributes(model);
	return "sorteggicategorie/form";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String delete(@ModelAttribute("sorteggiCategorie") SorteggiCategorie sorteggiCategorie, BindingResult result, SessionStatus status) {

	// §§§BEGIN§§§
	SorteggiCategorie objToDelete = sorteggiCategorieService.findById(sorteggiCategorie.getId());
	try {
	    sorteggiCategorieService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderEntityProperty(sorteggiCategorie);
	    return "sorteggicategorie/form";
	}
	status.setComplete();
	return "redirect:list.htm";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String update(@ModelAttribute("sorteggiCategorie") SorteggiCategorie sorteggiCategorie, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	// §§§BEGIN§§§
	fixMergeEntityProperty(sorteggiCategorie);
	try {
	    sorteggiCategorieService.update(sorteggiCategorie);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, sorteggiCategorie, e);
	    fixRenderEntityProperty(sorteggiCategorie);
	    return "sorteggicategorie/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + sorteggiCategorie.getId().getCodice() + "&status_msg=02";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String viewSorteggi(Model model, @ModelAttribute("sorteggiCategorie") SorteggiCategorie sorteggiCategorie, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	// §§§BEGIN§§§
	for (Sorteggitestata sorteggitestata : sorteggiCategorie.getSorteggiList()) {
	    sorteggitestata.setTransientAssociaCategoria(true);
	}
	List<Sorteggitestata> sorteggiSenzaCategoriaList = sorteggitestataService.findAllSenzaCategoria();
	sorteggiCategorie.setTransientSorteggiSenzaCategoriaList(sorteggiSenzaCategoriaList);
	model.addAttribute("sorteggiCategorie", sorteggiCategorie);
	return "sorteggicategorie/sorteggi";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String updateSorteggi(Model model, @ModelAttribute("sorteggiCategorie") SorteggiCategorie sorteggiCategorie, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	// §§§BEGIN§§§
	List<Sorteggitestata> listSorteggiConCategoria = new ArrayList<Sorteggitestata>(sorteggiCategorie.getSorteggiList());
	assegnaCategoria(sorteggiCategorie, listSorteggiConCategoria);
	List<Sorteggitestata> sorteggiSenzaCategoria = sorteggiCategorie.getTransientSorteggiSenzaCategoriaList();
	assegnaCategoria(sorteggiCategorie, sorteggiSenzaCategoria);
	return "redirect:view.htm?codice=" + sorteggiCategorie.getId().getCodice() + "&status_msg=02";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    private void assegnaCategoria(SorteggiCategorie sorteggiCategorie, List<Sorteggitestata> sorteggi) {

	for (Sorteggitestata sorteggitestata : sorteggi) {
	    if (sorteggitestata.isTransientAssociaCategoria()) {
		sorteggitestata.setCategoria(sorteggiCategorie);
	    } else {
		sorteggitestata.setCategoria(null);
	    }
	    sorteggitestataService.aggiornaCategoria(sorteggitestata);
	}
    }

    @Override
    protected void fixMergeEntityProperty(SorteggiCategorie entity) {

    }

    @Override
    protected void fixRenderEntityProperty(SorteggiCategorie entity) {

	if (entity.getSoftware() == null) {
	    entity.setSoftware(new Software());
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
