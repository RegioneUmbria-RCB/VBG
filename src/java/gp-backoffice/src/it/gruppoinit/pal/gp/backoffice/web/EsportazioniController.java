package it.gruppoinit.pal.gp.backoffice.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import it.gruppoinit.pal.gp.core.domain.Esportazioni;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.Tipicontestoesportazione;
import it.gruppoinit.pal.gp.core.service.EsportazioniService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;
import it.gruppoinit.pal.gp.core.service.TipicontestoesportazioneService;

@Controller
@SessionAttributes("esportazioni")
public class EsportazioniController extends BaseController<Esportazioni> {

    @Autowired
    private EsportazioniService esportazioniService;
    @Autowired
    private TipicontestoesportazioneService tipicontestoesportazioneService;
    @Autowired
    private SoftwareService softwareService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	List<Esportazioni> listEsportazioni = esportazioniService.findAll(null, null);
	List<Tipicontestoesportazione> tipicontestoesportazione = tipicontestoesportazioneService.findAll(null, null);
	List<Software> softwareList = softwareService.findAll(null, null);
	ModelMap model = new ModelMap(listEsportazioni);
	model.addAttribute("listEsportazioni", listEsportazioni);
	model.addAttribute("tipicontestoesportazione", tipicontestoesportazione);
	model.addAttribute("softwareList", softwareList);
	return model;
    }

    @RequestMapping
    public void inserisci(@RequestParam("descrizione") String descrizione, @RequestParam("flagAbilitata") boolean flagAbilitata,
	    @RequestParam("idsoftware") String idsoftware, @RequestParam("codiceTipoContesto") String codiceTipoContesto,
	    @RequestParam("trasformazione") String trasformazione, @RequestParam("idcomune") String idcomune, HttpServletRequest request,
	    HttpServletResponse response) throws IOException {

	String status = "";
	Esportazioni esportazioni = new Esportazioni();
	this.setEsportazioni(null, idcomune, descrizione, flagAbilitata, idsoftware, codiceTipoContesto, trasformazione, esportazioni);
	try {
	    esportazioniService.inserisci(esportazioni);
	    status = "OK";
	} catch (Exception e) {
	    status = "Si è verificato un errore durante l'inserimento. Errore: " + e.getMessage();
	}
	response.getOutputStream().write(status.getBytes());
    }

    private void setEsportazioni(Integer codice, String idcomune, String descrizione, boolean flagAbilitata, String idsoftware,
	    String codiceTipoContesto, String trasformazione, Esportazioni esportazioni) {

	PkId id = new PkId(idcomune, codice);
	esportazioni.setId(id);
	esportazioni.setDescrizione(descrizione);
	esportazioni.setFlgAbilitata(flagAbilitata);
	Software software = softwareService.findById(idsoftware);
	esportazioni.setSoftware(software);
	Tipicontestoesportazione tipicontestoesportazione = tipicontestoesportazioneService.findByCodice(codiceTipoContesto);
	esportazioni.setTipicontestoesportazione(tipicontestoesportazione);
	esportazioni.setTrasformazione(trasformazione);
    }

    @RequestMapping
    public void modifica(@RequestParam("descrizione") String descrizione, @RequestParam("flagAbilitata") boolean flagAbilitata,
	    @RequestParam("idsoftware") String idsoftware, @RequestParam("codiceTipoContesto") String codiceTipoContesto,
	    @RequestParam("trasformazione") String trasformazione, @RequestParam("codice") Integer id, @RequestParam("idcomune") String idcomune,
	    HttpServletRequest request, HttpServletResponse response) throws IOException {

	String status = "";
	PkId iduniv = new PkId(idcomune, id);
	Esportazioni esportazioni = esportazioniService.findById(iduniv);
	setEsportazioni(id, idcomune, descrizione, flagAbilitata, idsoftware, codiceTipoContesto, trasformazione, esportazioni);
	try {
	    esportazioniService.update(esportazioni);
	    status = "OK";
	} catch (Exception e) {
	    status = "Si è verificato un errore durante la modifica. Errore: " + e.getMessage();
	}
	response.getOutputStream().write(status.getBytes());
    }

    @RequestMapping
    public void elimina(@RequestParam("idriga") Integer idriga, @RequestParam("idcomune") String idcomune, HttpServletRequest request,
	    HttpServletResponse response) throws IOException {

	String status = "";
	PkId id = new PkId(idcomune, idriga);
	Esportazioni esportazioni = esportazioniService.findById(id);
	try {
	    esportazioniService.delete(esportazioni);
	    status = "OK";
	} catch (Exception e) {
	    status = "Si è verificato un errore durante l'eliminazione del dato. Errore: " + e.getMessage();
	}
	response.getOutputStream().write(status.getBytes());
    }

    @Override
    protected void setPageAttributes(Model model) {

	// TODO Auto-generated method stub
    }

    @Override
    protected void fixMergeEntityProperty(Esportazioni entity) {

	// TODO Auto-generated method stub
    }

    @Override
    protected void fixRenderEntityProperty(Esportazioni entity) {

	// TODO Auto-generated method stub
    }
}
