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

import it.gruppoinit.pal.gp.core.domain.Decodifiche;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.decodifiche.DecodificheService;
import it.gruppoinit.pal.gp.core.features.decodifiche.ListaDecodifiche;
import it.gruppoinit.pal.gp.core.utils.Utilities;

@Controller
@SessionAttributes("decodifiche")
public class DecodificheController extends BaseController<Decodifiche> {

    @Autowired
    private DecodificheService decodificheService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	List<Decodifiche> decodificheList = decodificheService.findAll(null, null);
	ModelMap model = new ModelMap(decodificheList);
	model.addAttribute("decodificheList", decodificheList);
	return model;
    }

    @RequestMapping
    public String view(Model model, HttpServletRequest request) {

	Decodifiche decodifiche = new Decodifiche();
	List<String> listTabelle = decodificheService.findDistinctTabelle();
	model.addAttribute("decodifiche", decodifiche);
	model.addAttribute("listTabelle", listTabelle);
	setPageAttributes(model);
	return "decodifiche/form";
    }

    @RequestMapping
    public void findDecodificheByTabella(@RequestParam("tabella") String tabella, Model model, HttpServletResponse response) throws IOException {

	ListaDecodifiche listaDecodifiche = decodificheService.findByTabella(tabella);
	try {
	    response.setContentType("application/json");
	    String richiesta = Utilities.marshalJsonObject(listaDecodifiche.getDecodificheList(), ListaDecodifiche.class, false,
		    Utilities.JAXB_ENCODING_UTF_8);
	    response.getOutputStream().write(richiesta.getBytes("utf-8"));
	} catch (Exception e) {
	    response.setStatus(500);
	    response.getOutputStream().write("{messaggio:\"Errore nel marshalling degli oggetti\"}".getBytes());
	}
    }

    @RequestMapping
    public void modificaDecodifica(@RequestParam("tabella") String tabella, @RequestParam("chiave") String chiave,
	    @RequestParam("valore") String valore, @RequestParam("raggruppamento") String raggruppamento, @RequestParam("ordine") Integer ordine,
	    @RequestParam("flagDisabilitato") boolean flagDisabilitato, @RequestParam("codice") Integer id, HttpServletRequest request,
	    HttpServletResponse response) throws IOException {

	String status = "";
	PkId _id = new PkId(id);
	Decodifiche decodifiche = decodificheService.findById(_id);
	validaDecodifica(tabella, chiave, valore);
	this.setValoriInDecodifica(tabella, chiave, valore, raggruppamento, ordine, flagDisabilitato, decodifiche);
	try {
	    decodificheService.update(decodifiche);
	    status = "OK";
	} catch (Exception e) {
	    status = "Si è verificato un errore durante l'aggiornamento dei dati. Errore: " + e.getMessage();
	}
	response.getOutputStream().write(status.getBytes());
    }

    private void validaDecodifica(String tabella, String chiave, String valore) {

	if (tabella.isEmpty()) {
	    throw new IllegalArgumentException("DECODIFICHE: La tabella non può essere vuoto");
	}
	if (chiave.isEmpty()) {
	    throw new IllegalArgumentException("DECODIFICHE: La chiave non può essere vuoto");
	}
	if (valore.isEmpty()) {
	    throw new IllegalArgumentException("DECODIFICHE: Il valore non può essere vuoto");
	}
    }

    private void setValoriInDecodifica(String tabella, String chiave, String valore, String raggruppamento, Integer ordine, boolean flagDisabilitato,
	    Decodifiche decodifiche) {

	decodifiche.setChiave(chiave);
	decodifiche.setFlgDisabilitato(flagDisabilitato);
	decodifiche.setOrdine(ordine);
	decodifiche.setRaggruppamento(raggruppamento);
	decodifiche.setTabella(tabella);
	decodifiche.setValore(valore);
    }

    @RequestMapping
    public void inserisci(@RequestParam("tabella") String tabella, @RequestParam("chiave") String chiave, @RequestParam("valore") String valore,
	    @RequestParam("raggruppamento") String raggruppamento, @RequestParam("flagDisabilitato") boolean flagDisabilitato,
	    @RequestParam("ordine") Integer ordine, HttpServletRequest request, HttpServletResponse response) throws IOException {

	String status = "";
	Decodifiche decodifiche = new Decodifiche();
	this.validaDecodifica(tabella, chiave, valore);
	this.setValoriInDecodifica(tabella, chiave, valore, raggruppamento, ordine, flagDisabilitato, decodifiche);
	try {
	    decodificheService.insert(decodifiche);
	    status = "OK";
	} catch (Exception e) {
	    status = "Si è verificato un errore durante l'inserimento. Errore: " + e.getMessage();
	}
	response.getOutputStream().write(status.getBytes());
    }

    @Override
    protected void setPageAttributes(Model model) {

	// TODO Auto-generated method stub
    }

    @Override
    protected void fixMergeEntityProperty(Decodifiche entity) {

	// TODO Auto-generated method stub
    }

    @Override
    protected void fixRenderEntityProperty(Decodifiche entity) {

	// TODO Auto-generated method stub
    }
}
