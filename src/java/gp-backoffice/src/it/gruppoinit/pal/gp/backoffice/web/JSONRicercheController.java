package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Ricerche;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.security.LoggedUser;
import it.gruppoinit.pal.gp.core.service.RicercheService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class JSONRicercheController extends BaseController<Ricerche> {

    private RicercheService ricercheService;
    private SoftwareService softwareService;

    @Autowired
    public void setRicercheService(RicercheService ricercheService) {

	this.ricercheService = ricercheService;
    }

    @Autowired
    public void setSoftwareService(SoftwareService softwareService) {

	this.softwareService = softwareService;
    }

    @SuppressWarnings("unchecked")
    @RequestMapping
    public ModelAndView list(@RequestParam("chiave_ricerca") String chiaveRicerca) {

	Map model = new HashMap();
	LoggedUser user = (LoggedUser) getCurrentlyAuthenticatedUser();
	List<Ricerche> ricerche = ricercheService.findByFilter((user.getCodiceResponsabile()), chiaveRicerca);
	model.put("ricerche", ricerche);
	return new ModelAndView("jsonView", model);
    }

    @SuppressWarnings("unchecked")
    @RequestMapping
    public ModelAndView find(@RequestParam("id_ricerca") Integer idRicerca) {

	Map model = new HashMap();
	Ricerche ricerca = ricercheService.findById(new PkId(idRicerca));
	if (ricerca == null) {
	    throw new RuntimeException("La ricerca con codice '" + idRicerca + "' non esiste.");
	}
	model.put("ricerca", ricerca);
	return new ModelAndView("jsonView", model);
    }

    @SuppressWarnings("unchecked")
    @RequestMapping
    public ModelAndView save(@RequestParam("filtro_ricerca") String filtroRicerca, @RequestParam("chiave_ricerca") String chiaveRicerca,
	    @RequestParam("desc_ricerca") String descRicerca, @RequestParam("globale_ricerca") String globaleRicerca) {

	Map model = new HashMap();
	Ricerche ricerca = new Ricerche();
	try {
	    LoggedUser user = (LoggedUser) getCurrentlyAuthenticatedUser();
	    ricerca.setCodiceresponsabile(user.getCodiceResponsabile());
	    //	    if (user.isAmministratore()) {
	    //		ricerca.setFlagGlobale(true);
	    //	    }
	    if (globaleRicerca.equals("false")) {
		ricerca.setFlagGlobale(false);
	    } else {
		ricerca.setFlagGlobale(true);
	    }
	    Software software = softwareService.findById(ORMHelper.getSoftware());
	    ricerca.setSoftware(software);
	    ricerca.setDescrizione(descRicerca);
	    ricerca.setChiave(chiaveRicerca);
	    String encodeFilter = URLDecoder.decode(filtroRicerca, "UTF-8");
	    ricerca.setFiltro(encodeFilter.getBytes());
	    ricercheService.insert(ricerca);
	} catch (Exception e) {
	    throw new RuntimeException("Errore durante il salvataggio della ricerca.");
	}
	model.put("id", ricerca.getId().getCodice().intValue());
	return new ModelAndView("jsonView", model);
    }

    @SuppressWarnings("unchecked")
    @RequestMapping
    public ModelAndView delete(@RequestParam("id_ricerca") Integer idRicerca) {

	Map model = new HashMap();
	Ricerche ricerca = ricercheService.findById(new PkId(idRicerca));
	if (ricerca == null) {
	    throw new RuntimeException("La ricerca con codice '" + idRicerca + "' non esiste.");
	}
	ricercheService.delete(ricerca);
	return new ModelAndView("jsonView", model);
    }

    @SuppressWarnings("unchecked")
    @RequestMapping
    public ModelAndView update(@RequestParam("filtro_ricerca") String filtroRicerca, @RequestParam("id_ricerca") Integer idRicerca,
	    @RequestParam("desc_ricerca") String descRicerca, @RequestParam("globale_ricerca") String globaleRicerca) {

	Map model = new HashMap();
	Ricerche ricerca = ricercheService.findById(new PkId(idRicerca));
	try {
	    if (ricerca == null) {
		throw new RuntimeException("La ricerca con codice '" + idRicerca + "' non esiste.");
	    }
	    if (globaleRicerca.equals("false")) {
		ricerca.setFlagGlobale(false);
	    } else {
		ricerca.setFlagGlobale(true);
	    }
	    ricerca.setDescrizione(descRicerca);
	    String encodeFilter;
	    encodeFilter = URLDecoder.decode(filtroRicerca, "UTF-8");
	    ricerca.setFiltro(encodeFilter.getBytes());
	} catch (Exception e) {
	    throw new RuntimeException("Errore durante il salvataggio della ricerca.");
	}
	ricercheService.update(ricerca);
	return new ModelAndView("jsonView", model);
    }

    @Override
    protected void fixMergeEntityProperty(Ricerche entity) {

	// TODO Auto-generated method stub
    }

    @Override
    protected void fixRenderEntityProperty(Ricerche entity) {

	// TODO Auto-generated method stub
    }

    @Override
    protected void setPageAttributes(Model model) {

	// TODO Auto-generated method stub
    }
}
