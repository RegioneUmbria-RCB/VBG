package it.gruppoinit.pal.gp.backoffice.web;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzeoneri;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.Oneritipirateizzazione;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Raggruppamentocausalioneri;
import it.gruppoinit.pal.gp.core.domain.TipiScadenza;
import it.gruppoinit.pal.gp.core.domain.Tipimovimento;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzeoneriHelper;
import it.gruppoinit.pal.gp.core.exception.InvalidConfigurationException;
import it.gruppoinit.pal.gp.core.features.oneri.IstanzeoneriService;
import it.gruppoinit.pal.gp.core.features.oneri.rateizzazione.configurazione.OneritipirateizzazioneService;
import it.gruppoinit.pal.gp.core.filters.TipologiaOnere;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.MovimentiService;
import it.gruppoinit.pal.gp.core.service.RaggruppamentocausalioneriService;

/**
 * 
 * @author
 */
@Controller
@SessionAttributes("otr")
public class OneritipirateizzazioneController extends BaseController<Oneritipirateizzazione> {

    @Autowired
    private OneritipirateizzazioneService oneritipirateizzazioneService;
    @Autowired
    private IstanzeoneriService istanzeoneriService;
    @Autowired
    private RaggruppamentocausalioneriService raggruppamentocausalioneriService;
    @Autowired
    private MovimentiService movimentiService;
    @Autowired
    IstanzeService istanzeService;

    @RequestMapping
    public String view(@RequestParam(value = "idOnere", required = false) Integer idOnere, @RequestParam("codiceIstanza") Integer codiceIstanza,
	    @RequestParam(value = "codiceRaggruppamento", required = false) Integer codiceRaggruppamento, Model model, HttpServletRequest request) {

	// controllo se il codice istanza è null, in tal caso rilancio errore
	if (codiceIstanza == null) {
	    throw new RuntimeException("Il codice istanza non può essere nullo.");
	}
	boolean flagRaggruppamento = false;
	List<Oneritipirateizzazione> oneritipirateizzazione = oneritipirateizzazioneService.findAll(null, null);
	model.addAttribute("oneritipirateizzazione", oneritipirateizzazione);
	if (oneritipirateizzazione.isEmpty()) {
	    throw new RuntimeException("Non sono presenti Oneritipirateizzazione per il software " + ORMHelper.getSoftware());
	}
	Oneritipirateizzazione otr = oneritipirateizzazione.get(0);
	Istanze istanza = istanzeService.findById(new PkId(codiceIstanza));
	otr.setDeterminadatainizioratetransient(this.determinaInizioRata(otr.getDetermdatainiziorate(), istanza, otr));
	otr.setDataInizioTransient(otr.getDeterminadatainizioratetransient());
	model.addAttribute("otr", otr);
	model.addAttribute("istanza", istanza);
	// caso della rateizzazione in base al raggruppamento
	if (idOnere == null) {
	    Raggruppamentocausalioneri rco = raggruppamentocausalioneriService.findById(new PkId(codiceRaggruppamento));
	    List<Istanzeoneri> istanzeOneri = istanzeoneriService.findByIstanzaAndRaggruppamentiAndData(istanza, rco, false, null);
	    BigDecimal totaleOnericausale = istanzeoneriService.sumOneriCausaliByIstanzaAndRaggruppamento(istanza, rco, TipologiaOnere.CAUSALE_ONERE,
		    null);
	    IstanzeoneriHelper ioHelper = IstanzeoneriHelper.fromIstanzeOneri(istanzeOneri);
	    ioHelper.setTotaleOneriCausaleRaggruppamento(totaleOnericausale);
	    model.addAttribute("rco", rco);
	    model.addAttribute("ioHelper", ioHelper);
	    flagRaggruppamento = true;
	}
	// caso della rateizzazione in base alla causale
	if (codiceRaggruppamento == null) {
	    Istanzeoneri istanzaoneri = istanzeoneriService.findById(new PkId(idOnere));
	    IstanzeoneriHelper ioHelper = IstanzeoneriHelper.fromIstanzeOneri(istanzaoneri);
	    ioHelper.setTotaleOneriCausale(istanzaoneri.getPrezzo());
	    ioHelper.setTotaleOneriIncassati(istanzaoneri.getImportopagato());
	    model.addAttribute("istanzaoneri", istanzaoneri);
	    model.addAttribute("ioHelper", ioHelper);
	}
	model.addAttribute("periodicitaValue", otr.getPeriodicitaEnum().values());
	model.addAttribute("idOnere", idOnere);
	model.addAttribute("codiceRaggruppamento", codiceRaggruppamento);
	model.addAttribute("flagRaggruppamento", flagRaggruppamento);
	setPageAttributes(model);
	return "oneritipirateizzazione/form";
    }

    @RequestMapping
    public void ajaxSettaTipoRateizzazione(@RequestParam(value = "codiceIstanza") Integer codiceIstanza,
	    @RequestParam(required = false, value = "idTiporateizzazione") Integer idTiporateizzazione, HttpServletRequest request,
	    HttpServletResponse response) {

	Oneritipirateizzazione otr = oneritipirateizzazioneService.findById(new PkId(idTiporateizzazione));
	Istanze istanza = istanzeService.findById(new PkId(codiceIstanza));
	otr.setDeterminadatainizioratetransient(this.determinaInizioRata(otr.getDetermdatainiziorate(), istanza, otr));
	otr.setDataInizioTransient(otr.getDeterminadatainizioratetransient());
	String descrizione = otr.getDescrizione();
	Integer numeroRate = otr.getNumerorate();
	boolean flginteressilegali = otr.getFlagInteressiLegali();
	String frequenzarate = otr.getFrequenzarate() == null ? "0" : otr.getFrequenzarate();
	String interessirate = otr.getInteressirate() == null ? "" : otr.getInteressirate();
	String ripartizionerate = otr.getRipartizionerate();
	TipiScadenza scadenzarate = otr.getScadenzarate();
	String tipoAnatocismo = "";
	if (otr.getTipoAnatocismo() == null || otr.getTipoAnatocismo().equals(0)) {
	    tipoAnatocismo = "Senza anatocismo";
	} else {
	    tipoAnatocismo = otr.getTipoAnatocismo().toString();
	}
	String tipologiaRateizzazione = otr.getTipologiaRateizzazione();
	Tipimovimento tipimovimento = otr.getTipimovimento();
	BigDecimal speseRateizzazione = otr.getSpeseRateizzazione() == null ? BigDecimal.ZERO : otr.getSpeseRateizzazione();
	Date deterinizioratetrans = otr.getDeterminadatainizioratetransient();
	SimpleDateFormat sdf = new SimpleDateFormat(WebConstants.DATE_FORMAT_PATTERN);
	String deterinizioratetransString = sdf.format(deterinizioratetrans);
	StringBuffer sb = new StringBuffer("{");
	sb.append("\"id\":" + "\"" + idTiporateizzazione + "\",").append("\"descrizione\":" + "\"" + descrizione + "\",")
		.append("\"numeroRate\":" + "\"" + numeroRate + "\",").append("\"flginteressilegali\":" + "\"" + flginteressilegali + "\",")
		.append("\"frequenzarate\":" + "\"" + frequenzarate + "\",").append("\"interessirate\":" + "\"" + interessirate + "\",")
		.append("\"ripartizionerate\":" + "\"" + ripartizionerate + "\",").append("\"scadenzarate\":" + "{")
		.append("\"id\":" + "\"" + scadenzarate.getId() + "\",").append("\"descrizione\":" + "\"" + scadenzarate.getDescrizione() + "\"},");
	if (tipimovimento != null && tipimovimento.getId() != null && tipimovimento.getId().getTipomovimento() != null) {
	    sb.append("\"tipimovimento\":" + "{").append("\"id\":" + "\"" + tipimovimento.getId().getTipomovimento() + "\"},");
	}
	sb.append("\"tipoAnatocismo\":" + "\"" + tipoAnatocismo + "\",").append("\"tipologiaRateizzazione\":" + "\"" + tipologiaRateizzazione + "\",")
		.append("\"deterinizioratetrans\":" + "\"" + deterinizioratetransString + "\",")
		.append("\"speseRateizzazione\":" + "\"" + speseRateizzazione + "\"").append("}");
	try {
	    response.setContentType("application/json");
	    response.getOutputStream().write(sb.toString().getBytes());
	    response.getOutputStream().flush();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    @RequestMapping
    public String inserisciRateizzazione(Model model, @ModelAttribute("otr") Oneritipirateizzazione oneritipirateizzazione,
	    @RequestParam("codiceIstanza") Integer codiceIstanza, @RequestParam(value = "idOnere", required = false) Integer idOnere,
	    @RequestParam(value = "codiceRaggruppamento", required = false) Integer codiceRaggruppamento, HttpServletRequest request) {

	String status = "02";
	try {
	    if (codiceRaggruppamento == null) {
		istanzeoneriService.rateizzaIstanzeOneri(codiceIstanza, idOnere, oneritipirateizzazione.getId().getCodice(),
			oneritipirateizzazione.getDataInizioTransient(), oneritipirateizzazione.getDeterminadatainizioratetransient());
	    } else {
		istanzeoneriService.rateizzaIstanzeOneriRagguppamento(codiceIstanza, codiceRaggruppamento, oneritipirateizzazione.getId().getCodice(),
			oneritipirateizzazione.getDataInizioTransient(), oneritipirateizzazione.getDeterminadatainizioratetransient());
	    }
	} catch (IllegalArgumentException e) {
	    status = "03";
	    copyErrorsToFlashMessages(oneritipirateizzazione, false, status, e);
	} catch (InvalidConfigurationException e) {
	    status = "03";
	    copyErrorsToFlashMessages(oneritipirateizzazione, false, status, e);
	}
	return "redirect:../istanzeoneri/list.htm?codiceIstanza=" + codiceIstanza + "&status_msg=" + status;
    }

    @RequestMapping
    public String derateizzaOnere(Model model, @RequestParam("codiceIstanza") Integer codiceIstanza,
	    @RequestParam(value = "codiceCausaleOneri", required = false) Integer codiceCausaleOneri,
	    @RequestParam(value = "codiceRaggruppamento", required = false) Integer codiceRaggruppamento, HttpServletRequest request) {

	String status = "02";
	try {
	    if (codiceRaggruppamento != null) {
		istanzeoneriService.derateizzaIstanzeOneriRagguppamento(codiceIstanza, codiceRaggruppamento);
	    } else {
		istanzeoneriService.derateizzaIstanzeOneri(codiceIstanza, codiceCausaleOneri);
	    }
	} catch (IllegalArgumentException e) {
	    status = "03";
	    copyErrorsToFlashMessages(e, false, status, e);
	} catch (InvalidConfigurationException e) {
	    status = "03";
	    e.printStackTrace();
	}
	return "redirect:../istanzeoneri/list.htm?codiceIstanza=" + codiceIstanza + "&status_msg=" + status;
    }

    @Override
    protected void fixMergeEntityProperty(Oneritipirateizzazione entity) {

    }

    @Override
    protected void fixRenderEntityProperty(Oneritipirateizzazione entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }

    /**
     * Metodo per determinare la data di inizio delle rate a partire dal codice passato nella colonna
     * DETERMDATAINIZIORATE
     * 
     * @param codice
     * @param istanza
     * @param otr
     * @return
     */
    private Date determinaInizioRata(Integer codice, Istanze istanza, Oneritipirateizzazione otr) {

	Date datainizioRata = new Date();
	if (codice == null) {
	    throw new RuntimeException("Errore. Il codice non può essere null.");
	}
	switch (codice) {
	case 1:
	    datainizioRata = istanza.getDatavalidita() == null ? new Date() : istanza.getDatavalidita();
	    break;
	case 2:
	    datainizioRata = new Date();
	    break;
	case 3:
	    if (otr.getTipimovimento() != null && otr.getTipimovimento().getId() != null
		    && otr.getTipimovimento().getId().getTipomovimento() != null) {
		Movimenti mov = movimentiService.findDataByTipoMovandcodIstanza(otr.getTipimovimento().getId().getTipomovimento(),
			istanza.getId().getCodice());
		datainizioRata = mov.getData() != null ? mov.getData() : new Date();
	    }
	    break;
	case 4:
	    datainizioRata = istanza.getData() != null ? istanza.getData() : new Date();
	    break;
	case 5:
	    datainizioRata = istanza.getDataprotocollo() != null ? istanza.getDataprotocollo() : new Date();
	    break;
	default:
	    break;
	}
	return datainizioRata;
    }
}
