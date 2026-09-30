package it.gruppoinit.pal.gp.backoffice.web;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBException;

import org.hibernate.criterion.DetachedCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.domain.Istanzestradario;
import it.gruppoinit.pal.gp.core.domain.web.AutorizzazioniFilter;
import it.gruppoinit.pal.gp.core.domain.web.IAttivitaFilter;
import it.gruppoinit.pal.gp.core.domain.web.IstanzeFilter;
import it.gruppoinit.pal.gp.core.features.attivita.IAttivitaService;
import it.gruppoinit.pal.gp.core.features.attivita.datilocalizzativi.LocalizzazioniAttivitaDTO;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.AutorizzazioniDAO;
import it.gruppoinit.pal.gp.core.features.integrazioni.cartografico.ICartograficoService;
import it.gruppoinit.pal.gp.core.features.integrazioni.cartografico.UtilizzoEnum;
import it.gruppoinit.pal.gp.core.features.integrazioni.cartografico.client.InnescoResponse;
import it.gruppoinit.pal.gp.core.features.integrazioni.cartografico.client.ParametriResponse;
import it.gruppoinit.pal.gp.core.features.istanze.datilocalizzativi.IstanzeStradarioExtendedDTO;
import it.gruppoinit.pal.gp.core.features.istanze.datilocalizzativi.IstanzestradarioService;

@SuppressWarnings({ "rawtypes", "unchecked" })
@Controller
public class IstanzeStradarioCartograficoController extends BaseJsonController {

    private static final Logger log = LoggerFactory.getLogger(IstanzeStradarioCartograficoController.class);
    private static final String ISTANZE_FILTER_IN_SESSION = "ISTANZE_FILTER_IN_SESSION";
    private static final String IATTIVITA_FILTER_IN_SESSION = "IATTIVITA_FILTER_IN_SESSION";
    private static final String AUTORIZZAZIONI_FILTER_IN_SESSION = "AUTORIZZAZIONI_FILTER_IN_SESSION";
    private static final String CONTENTTYPE_JSON = "application/json";
    private static final String CHARSET_UTF8 = "utf-8";
    private AutorizzazioniDAO autorizzazioniDAO;
    private IAttivitaService iAttivitaService;
    private IstanzestradarioService istanzestradarioService;
    private ICartograficoService cartograficoService;

    @Autowired
    public void setCartograficoService(ICartograficoService cartograficoService) {

	this.cartograficoService = cartograficoService;
    }

    @Autowired
    public void setIstanzestradarioService(IstanzestradarioService istanzestradarioService) {

	this.istanzestradarioService = istanzestradarioService;
    }

    @Autowired
    public void setAutorizzazioniDAO(AutorizzazioniDAO autorizzazioniDAO) {

	this.autorizzazioniDAO = autorizzazioniDAO;
    }

    @Autowired
    public void setiAttivitaService(IAttivitaService iAttivitaService) {

	this.iAttivitaService = iAttivitaService;
    }

    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RequestMapping(method = RequestMethod.GET)
    public void jsonRecuperaUrl(@RequestParam("codiceIstanza") Integer codiceIstanza, @RequestParam("uuid") String uuid, Model model,
	    HttpServletRequest request, HttpServletResponse response) throws JAXBException, IOException {

	try {
	    log.info("Ricezione chiamata su jsonRecuperaUrl");
	    String returnTo = request.getRequestURL().substring(0, request.getRequestURL().indexOf("istanzestradariocartografico"));
	    returnTo += "istanzestradariocartografico/returning.htm";
	    returnTo += "?codiceIstanza=" + codiceIstanza;
	    returnTo += "&uuid=" + uuid;
	    returnTo += "&windowName=" + request.getSession().getId();
	    returnTo += "&returning=true";
	    InnescoResponse innescoResponse = this.cartograficoService.getUrlInnescoIstanza(codiceIstanza, UtilizzoEnum.MODIFICA, uuid, returnTo);
	    String jsonInnescoResponse = toJson(innescoResponse, true);
	    response.setContentType(CONTENTTYPE_JSON);
	    response.getOutputStream().write(jsonInnescoResponse.getBytes(CHARSET_UTF8));
	} catch (Exception ex) {
	    super.writeJsonError(response, ex.getMessage());
	}
    }

    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RequestMapping(method = RequestMethod.POST)
    public void jsonMostraAttivitaInMappa(Model model, HttpServletRequest request, HttpServletResponse response) {

	try {
	    IAttivitaFilter filter = (IAttivitaFilter) request.getSession().getAttribute(IATTIVITA_FILTER_IN_SESSION);
	    List<LocalizzazioniAttivitaDTO> localizzazioni = this.iAttivitaService.findLocalizzazioniByFilter(filter);
	    String returnTo = request.getRequestURL().substring(0, request.getRequestURL().indexOf("istanzestradariocartografico"));
	    returnTo += "iattivita/returnCartografico.htm";
	    returnTo += "?codice=";
	    returnTo += "&uuidLocalizzazione=" + UUID.randomUUID().toString();
	    returnTo += "&windowName=" + request.getSession().getId();
	    InnescoResponse innescoResponse = this.cartograficoService.getUrlInnescoAttivita(UtilizzoEnum.ELENCO, localizzazioni, returnTo);
	    String jsonInnescoResponse = toJson(innescoResponse, true);
	    response.getOutputStream().write(jsonInnescoResponse.getBytes(CHARSET_UTF8));
	} catch (Exception ex) {
	    super.writeJsonError(response, ex.getMessage());
	}
    }

    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RequestMapping(method = RequestMethod.POST)
    public void jsonMostraSingolaAttivitaInMappa(@RequestParam("idAttivita") Integer idAttivita, @RequestParam("codiceIstanza") Integer codiceIstanza,
	    Model model, HttpServletRequest request, HttpServletResponse response) {

	try {
	    if (idAttivita == null) {
		throw new RuntimeException("Impossibile aprire la mappa senza passare il codice dell'attività");
	    }
	    IAttivitaFilter filter = new IAttivitaFilter();
	    filter.setCodiceAttivita(idAttivita);
	    filter.setSoloStradarioPrimario(true);
	    List<LocalizzazioniAttivitaDTO> localizzazioni = this.iAttivitaService.findLocalizzazioniByFilter(filter);
	    if (localizzazioni == null || localizzazioni.isEmpty()) {
		throw new RuntimeException("Non è presente la localizzazione primaria nell'istanza rappresentativa dell'attività");
	    }
	    if (localizzazioni.size() > 1) {
		throw new RuntimeException("Caso anomalo: sono presenti " + localizzazioni.size() +
					   " localizzazioni primarie nell'istanza rappresentativa dell'attività");
	    }
	    String returnTo = request.getRequestURL().substring(0, request.getRequestURL().indexOf("istanzestradariocartografico"));
	    if (codiceIstanza == null) {
		returnTo += "iattivita/returnCartografico.htm";
		returnTo += "?codice=" + idAttivita.toString();
		returnTo += "&uuidLocalizzazione=" + localizzazioni.get(0).getUuidIstanzeStradario();
		returnTo += "&windowName=" + request.getSession().getId();
	    } else {
		returnTo += "istanze/returnCartografico.htm";
		returnTo += "?codice=" + codiceIstanza.toString();
		returnTo += "&uuidLocalizzazione=" + localizzazioni.get(0).getUuidIstanzeStradario();
		returnTo += "&windowName=" + request.getSession().getId();
	    }
	    InnescoResponse innescoResponse = this.cartograficoService.getUrlInnescoAttivita(UtilizzoEnum.ELENCO, localizzazioni, returnTo);
	    String jsonInnescoResponse = toJson(innescoResponse, true);
	    response.getOutputStream().write(jsonInnescoResponse.getBytes(CHARSET_UTF8));
	} catch (Exception ex) {
	    super.writeJsonError(response, ex.getMessage());
	}
    }

    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RequestMapping(method = RequestMethod.POST)
    public void jsonMostraIstanzeInMappa(Model model, HttpServletRequest request, HttpServletResponse response) {

	try {
	    //1. Recupero i filtri
	    IstanzeFilter filter = (IstanzeFilter) request.getSession().getAttribute(ISTANZE_FILTER_IN_SESSION);
	    List<IstanzeStradarioExtendedDTO> localizzazioni = this.istanzestradarioService.findByIstanzeFilter(filter, null, null);
	    //2. Preparo la returnTo
	    String returnTo = request.getRequestURL().substring(0, request.getRequestURL().indexOf("istanzestradariocartografico"));
	    returnTo += "istanze/listIstanze.htm";
	    returnTo += "?windowName=" + request.getSession().getId();
	    //3. Faccio la chiamata impostando
	    InnescoResponse innescoResponse = this.cartograficoService.getUrlInnescoIstanze(UtilizzoEnum.ELENCO, localizzazioni, returnTo);
	    String jsonInnescoResponse = toJson(innescoResponse, true);
	    response.getOutputStream().write(jsonInnescoResponse.getBytes(CHARSET_UTF8));
	} catch (Exception ex) {
	    super.writeJsonError(response, ex.getMessage());
	}
    }

    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RequestMapping(method = RequestMethod.POST)
    public void jsonMostraAutorizzazioniInMappa(Model model, HttpServletRequest request, HttpServletResponse response) {

	try {
	    //1. Recupero i filtri
	    AutorizzazioniFilter filter = (AutorizzazioniFilter) request.getSession().getAttribute(AUTORIZZAZIONI_FILTER_IN_SESSION);
	    DetachedCriteria criteria = this.autorizzazioniDAO.createCriteriaFilter(filter);
	    List<IstanzeStradarioExtendedDTO> localizzazioni = this.istanzestradarioService.findByAutorizzazioniFilter(criteria, null, null);
	    //2. Preparo la returnTo
	    String returnTo = request.getRequestURL().substring(0, request.getRequestURL().indexOf("istanzestradariocartografico"));
	    returnTo += "autorizzazioni/list.htm";
	    returnTo += "?windowName=" + request.getSession().getId();
	    //3. Faccio la chiamata impostando fisso readOnly=false
	    InnescoResponse innescoResponse = this.cartograficoService.getUrlInnescoAutorizzazioni(UtilizzoEnum.ELENCO, localizzazioni, returnTo);
	    String jsonInnescoResponse = toJson(innescoResponse, true);
	    response.getOutputStream().write(jsonInnescoResponse.getBytes(CHARSET_UTF8));
	} catch (Exception ex) {
	    super.writeJsonError(response, ex.getMessage());
	}
    }

    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RequestMapping(method = RequestMethod.POST)
    public void jsonRecuperaParametri(Model model, HttpServletRequest request, HttpServletResponse response) throws JAXBException, IOException {

	throw new NotImplementedException();
    }

    @RequestMapping(method = RequestMethod.GET)
    public void returning(@RequestParam("codiceIstanza") Integer codiceIstanza, @RequestParam("uuid") String uuid, Model model,
	    HttpServletRequest request, HttpServletResponse response) {

	log.debug("Ricezione chiamata di rientro su returning per la sessione {}", uuid);
	//1. Recupero i parametri
	Istanzestradario localizzazione = this.istanzestradarioService.findByUuid(codiceIstanza, uuid);
	try {
	    log.debug("Richiesta parametri per la sessione {}", uuid);
	    ;
	    ParametriResponse parametriResponse = this.cartograficoService.getParametri(uuid);
	    //2. Salvo il json della risposta sul campo dinamico ( se previsto )
	    log.debug("Salvataggio parametri per la sessione {}", uuid);
	    this.cartograficoService.salvaParametri(codiceIstanza, uuid, parametriResponse);
	    //3. Aggiorno le info della localizzazione
	    log.debug("Aggiornamento dati della localizzazione per la sessione {}", uuid);
	    this.cartograficoService.aggiornaLocalizzazione(codiceIstanza, uuid, parametriResponse);
	    response.setHeader("Location", "../istanzestradario/view.htm?codice=" + //
					   localizzazione.getId().getCodice() + //
					   "&status_msg=02&windowName=" + request.getSession().getId());
	    log.debug("Fine chiamata di rientro su returning per la sessione {}", uuid);
	    response.setStatus(302);
	} catch (Exception e) {
	    log.debug("Errore durante il recupero dei parametri dal cartografico per la sessione {}: ", uuid, e);
	    response.setHeader("Location", "../istanzestradario/view.htm?codice=" + //
					   localizzazione.getId().getCodice() + //
					   "&windowName=" + request.getSession().getId());
	    response.setStatus(302);
	}
    }
}
