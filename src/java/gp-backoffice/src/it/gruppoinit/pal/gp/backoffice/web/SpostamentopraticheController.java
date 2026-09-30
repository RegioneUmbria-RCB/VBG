package it.gruppoinit.pal.gp.backoffice.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.CodiceDescrizioneBean;
import it.gruppoinit.pal.gp.core.features.istanze.cambiointervento.ISpostamentoPraticheService;
import it.gruppoinit.pal.gp.core.features.istanze.cambiointervento.SpostamentoPraticheParams;
import it.gruppoinit.pal.gp.core.features.istanze.cambiointervento.VerificaSpostamentoPratiche;
import it.gruppoinit.pal.gp.core.features.istanze.rest.RicercaIstanzeIstanzeResult;
import it.gruppoinit.pal.gp.core.service.AlberoprocService;
import it.gruppoinit.pal.gp.core.service.RuoliUtentiEnum;

@Controller()
@SessionAttributes(value = { "spostamentoPraticheCommand" })
public class SpostamentopraticheController extends BaseJsonController<Alberoproc> {

    @Autowired
    private AlberoprocService alberoprocService;
    @Autowired
    private ISpostamentoPraticheService spostamentoPraticheService;

    @RequestMapping
    public String start(@RequestParam(value = "codiceIntervento", required = true) Integer codiceIntervento, Model model) {

	userHasRole(true, RuoliUtentiEnum.SPOSTAMENTO_PRATICHE.name());
	Alberoproc ap = alberoprocService.findById(new PkId(codiceIntervento));
	model.addAttribute("alberoproc", ap);
	return "spostamentopratiche/form";
    }

    @RequestMapping
    public void ajaxSelectNewIntervento(@RequestParam(value = "codiceInterventoOld", required = true) Integer codiceInterventoOld,
	    @RequestParam(value = "codiceInterventoNew", required = true) Integer codiceInterventoNew, Model model, HttpServletResponse response)
	    throws JAXBException, UnsupportedEncodingException, IOException {

	userHasRole(true, RuoliUtentiEnum.SPOSTAMENTO_PRATICHE.name());
	VerificaSpostamentoPratiche esito = spostamentoPraticheService.checkInterventoSelezionabile(codiceInterventoOld, codiceInterventoNew);
	response.setContentType("application/json");
	response.getOutputStream().write(toJsonBytes(esito, false));
    }

    @RequestMapping
    public void ajaxVisualizzapratiche(@RequestParam(value = "codiceInterventoOrigine", required = true) Integer codiceInterventoOrigine,
	    @RequestParam(value = "offset", required = true) Integer offset, @RequestParam(value = "limit", required = true) Integer limit,
	    Model model, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, IOException, JAXBException {

	userHasRole(true, RuoliUtentiEnum.SPOSTAMENTO_PRATICHE.name());
	if (offset == null) {
	    offset = 0;
	}
	if (limit == null) {
	    limit = 50;
	}
	RicercaIstanzeIstanzeResult results = spostamentoPraticheService.cercaPratichePerIntervento(codiceInterventoOrigine, offset, limit);
	response.setContentType("application/json");
	response.getOutputStream().write(toJsonBytes(results));
    }

    @RequestMapping
    public void ajaxSpostaPratica(@RequestParam(value = "listaRuoli[]", required = false) List<Integer> listaRuoli,
	    @RequestParam(value = "listaModelli[]", required = false) List<Integer> listaModelli,
	    @RequestParam("codiceInterventoOld") Integer codiceInterventoOld, @RequestParam("codiceInterventoNew") Integer codiceInterventoNew,
	    HttpServletResponse response) throws IOException, JAXBException {

	userHasRole(true, RuoliUtentiEnum.SPOSTAMENTO_PRATICHE.name());
	Set<Integer> idRuoliDaAggiungere = null;
	Set<Integer> idSchedeDaAggiungere = null;
	if (listaRuoli != null) {
	    idRuoliDaAggiungere = new HashSet<Integer>(listaRuoli);
	}
	if (listaModelli != null) {
	    idSchedeDaAggiungere = new HashSet<Integer>(listaModelli);
	}
	SpostamentoPraticheParams parametri = new SpostamentoPraticheParams(codiceInterventoOld, codiceInterventoNew, idRuoliDaAggiungere,
		idSchedeDaAggiungere);
	CodiceDescrizioneBean result = new CodiceDescrizioneBean("OK", null);
	try {
	    spostamentoPraticheService.spostaPraticheDaInterventoAIntervento(parametri);
	} catch (Exception e) {
	    result.setCodice("KO");
	    result.setDescrizione(e.getMessage());
	}
	response.setContentType("application/json");
	response.getOutputStream().write(toJsonBytes(result));
    }

    @Override
    protected void setPageAttributes(Model model) {

    }

    @Override
    protected void fixMergeEntityProperty(Alberoproc entity) {

    }

    @Override
    protected void fixRenderEntityProperty(Alberoproc entity) {

    }
}
