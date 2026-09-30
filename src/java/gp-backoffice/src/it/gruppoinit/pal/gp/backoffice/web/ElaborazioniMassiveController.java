package it.gruppoinit.pal.gp.backoffice.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import it.gruppoinit.pal.gp.core.domain.helper.CodiceDescrizioneBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.IdentificativoDescrizioneBean;
import it.gruppoinit.pal.gp.core.features.istanze.datidinamici.elaborazionemassiva.CreaTestataRequest;
import it.gruppoinit.pal.gp.core.features.istanze.datidinamici.elaborazionemassiva.IElaborazioneMassivaService;
import it.gruppoinit.pal.gp.core.features.istanze.datidinamici.elaborazionemassiva.model.ElaborazioniMassiveCreateCommand;
import it.gruppoinit.pal.gp.core.features.istanze.datidinamici.elaborazionemassiva.model.SchedaDinamicaModel;
import it.gruppoinit.pal.gp.core.features.istanze.datidinamici.elaborazionemassiva.model.TestataDettagliataModel;
import it.gruppoinit.pal.gp.core.features.istanze.datidinamici.elaborazionemassiva.model.TestataModel;
import it.gruppoinit.pal.gp.core.service.AlberoprocRuoliService;
import it.gruppoinit.pal.gp.core.service.StatiistanzaService;

@Controller
@SessionAttributes(value = { "elaborazioniMassiveCreateCommand" })
public class ElaborazioniMassiveController extends BaseJsonController<TestataModel> {

    @Autowired
    private IElaborazioneMassivaService elaborazioneMassivaService;
    @Autowired
    private StatiistanzaService statiistanzaService;
    @Autowired
    private AlberoprocRuoliService alberoprocRuoliService;

    @RequestMapping
    public String list(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {

	List<TestataModel> list = elaborazioneMassivaService.findAllByRuoliResponsabile(getCurrentlyAuthenticatedUserDetails().getId().getCodice());
	model.addAttribute("list", list);
	return "elaborazionimassive/list";
    }

    @RequestMapping
    public String view(@RequestParam("idElaborazione") Integer idElaborazione, Model model, HttpServletRequest request, HttpServletResponse response)
	    throws Exception {

	TestataDettagliataModel dettagliataModel = elaborazioneMassivaService.findById(idElaborazione);
	Date dataInizio = dettagliataModel.getDataInizio();
	Date dataFine = dettagliataModel.getDataFine();
	Boolean disabilitaElaborazione = false;
	if (dataInizio != null && dataFine == null) {
	    disabilitaElaborazione = true;
	}
	model.addAttribute("disabilitaElaborazione", disabilitaElaborazione);
	return "elaborazionimassive/form";
    }

    @RequestMapping
    public String create(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {

	ElaborazioniMassiveCreateCommand elaborazioniMassiveCreateCommand = new ElaborazioniMassiveCreateCommand(statiistanzaService);
	model.addAttribute("elaborazioniMassiveCreateCommand", elaborazioniMassiveCreateCommand);
	return "elaborazionimassive/formCreate";
    }

    @RequestMapping
    public void ajaxValidaCommand(
	    @ModelAttribute("elaborazioniMassiveCreateCommand") ElaborazioniMassiveCreateCommand elaborazioniMassiveCreateCommand, Model model,
	    HttpServletRequest request, HttpServletResponse response) throws Exception {

	String[] schede = request.getParameterValues("scheda_id_scheda");
	List<SchedaDinamicaModel> m = new ArrayList<SchedaDinamicaModel>();
	if (schede != null && schede.length > 0) {
	    String[] ordine = request.getParameterValues("scheda_ordine");
	    for (int i = 0; i < schede.length; i++) {
		SchedaDinamicaModel sdm = new SchedaDinamicaModel();
		sdm.setIdScheda(Integer.parseInt(schede[i]));
		sdm.setOrdine(Integer.parseInt(ordine[i]));
		m.add(sdm);
	    }
	}
	elaborazioniMassiveCreateCommand.setSchedaDinamicaModels(m);
	String valida = elaborazioniMassiveCreateCommand.valida();
	if (StringUtils.isBlank(valida)) {
	    CreaTestataRequest t = elaborazioniMassiveCreateCommand.createRequest();
	    List<Integer> totIstanze = elaborazioneMassivaService.findRighePerElaborazione(t);
	    if (totIstanze.size() == 0) {
		valida = "Non sono state trovate pratiche da elaborare per i filtri impostati.";
	    }
	}
	response.getOutputStream().write(valida.getBytes());
    }

    @RequestMapping
    public String insertElaborazione(
	    @ModelAttribute("elaborazioniMassiveCreateCommand") ElaborazioniMassiveCreateCommand elaborazioniMassiveCreateCommand, Model model,
	    HttpServletRequest request, HttpServletResponse response) throws Exception {

	CreaTestataRequest t = elaborazioniMassiveCreateCommand.createRequest();
	int idElaborazione = elaborazioneMassivaService.creaElaborazione(t);
	return "redirect:../elaborazionimassive/view.htm?idElaborazione=" + idElaborazione + "&status_msg=01";
    }

    @RequestMapping
    public void ajaxCaricaModel(@RequestParam("idElaborazione") Integer idElaborazione, Model model, HttpServletRequest request,
	    HttpServletResponse response) throws Exception {

	TestataDettagliataModel dettagliataModel = elaborazioneMassivaService.findById(idElaborazione);
	try {
	    response.setContentType("application/json");
	    response.getOutputStream().write(toJsonBytes(dettagliataModel, false));
	} catch (Exception ex) {
	    super.writeJsonError(response, ex.getMessage());
	}
    }

    @RequestMapping
    public void ajaxFindSchede(@RequestParam("statoistanza") String statoistanza, //
	    @RequestParam(value = "registri", required = false) Integer[] registri, //
	    @RequestParam(value = "interventi", required = false) Integer[] interventi, //
	    HttpServletRequest request, HttpServletResponse response) throws IOException {

	ElaborazioniMassiveCreateCommand c = new ElaborazioniMassiveCreateCommand(statiistanzaService);
	c.setStatoistanza(new CodiceDescrizioneBean(statoistanza, statoistanza));
	if (interventi != null && interventi.length > 0) {
	    c.setInterventi(new ArrayList<Integer>(Arrays.asList(interventi)));
	}
	if (registri != null && registri.length > 0) {
	    c.setRegistri(new ArrayList<Integer>(Arrays.asList(registri)));
	}
	CreaTestataRequest t = c.createRequest();
	List<IdentificativoDescrizioneBean> cdbs = elaborazioneMassivaService.findSchedeDinamiche(t);
	renderHTMLResponse(response, cdbs, "id", "descrizione", true);
    }

    @RequestMapping
    public void ajaxCheckAlberoproc(@RequestParam("id") Integer codiceAlberoproc, HttpServletRequest request, HttpServletResponse response)
	    throws IOException {

	boolean isAssegnabile = alberoprocRuoliService.isAssegnabilePerRuoloDelResponsabile(codiceAlberoproc,
		getCurrentlyAuthenticatedUserDetails().getId().getCodice());
	response.setContentType("application/json");
	response.getOutputStream().write(("{\"assegnabile\":\"" + isAssegnabile + "\"}").getBytes());
    }

    @RequestMapping
    public void elabora(@RequestParam("idElaborazione") Integer idElaborazione, HttpServletRequest request, HttpServletResponse response) {

	elaborazioneMassivaService.elabora(idElaborazione);
    }

    @RequestMapping
    public String eliminaElaborazioneMassiveRiga(Model model, @RequestParam("idElaborazione") Integer idElaborazione, HttpServletRequest request,
	    HttpServletResponse response) {

	elaborazioneMassivaService.eliminaElaborazioneMassiveRiga(idElaborazione);
	return "redirect:../elaborazionimassive/list.htm";
    }
}
