package it.gruppoinit.pal.gp.backoffice.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.ComportamentoEnum;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.IAbbonamentoService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.configurazione.AbbonamentiConfigCommand;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.configurazione.AbbonamentoConfigModel;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.configurazione.AggiornaConfigurazioneBaseModel;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.configurazione.AggiungiInformativaModel;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.configurazione.AggiungiRicaricaModel;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.configurazione.DestinatariEnum;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.configurazione.DettaglioComuneModel;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.configurazione.MessaggioNodoPagNonDispModel;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.configurazione.TipoRicaricaEnum;
import it.gruppoinit.pal.gp.core.features.manifestazioni.rest.ambulanti.model.EsitoAggiornamentoRicariche;

@Controller
@SessionAttributes(value = { "abbonamentiConfigModel" })
public class AbbonamentiConfigController extends BaseJsonController<AbbonamentiConfigCommand> {

    private static final Logger log = LoggerFactory.getLogger(AbbonamentiConfigController.class);
    private IAbbonamentoService abbonamentoService;

    @Autowired
    public void setAbbonamentoService(IAbbonamentoService abbonamentoService) {

	this.abbonamentoService = abbonamentoService;
    }

    @Override
    protected void setPageAttributes(Model model) {

	// TODO Auto-generated method stub
    }

    @Override
    protected void fixMergeEntityProperty(AbbonamentiConfigCommand entity) {

	// TODO Auto-generated method stub
    }

    @Override
    protected void fixRenderEntityProperty(AbbonamentiConfigCommand entity) {

	// TODO Auto-generated method stub
    }

    @RequestMapping
    public String insert(Model model, @ModelAttribute("abbonamentiConfigModel") AbbonamentoConfigModel abbonamentoModel, BindingResult result,
	    SessionStatus status) {

	try {
	    AggiornaConfigurazioneBaseModel cfg = new AggiornaConfigurazioneBaseModel();
	    cfg.setAttivoFo(abbonamentoModel.isAttivoFo());
	    cfg.setMessaggio(abbonamentoModel.getMsgNodoPagNonDisp());
	    cfg.setTipo(abbonamentoModel.getTipoInstallazione());
	    this.abbonamentoService.salvaConfigurazioneBase(cfg);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, abbonamentoModel, e);
	    setPageAttributes(model);
	    return "abbonamenticonfig/form";
	}
	status.setComplete();
	return "redirect:view.htm?software=" + ORMHelper.getSoftware();
    }

    @RequestMapping
    public String view(Model model, HttpServletRequest request) {

	AbbonamentoConfigModel abbonamentoModel = this.abbonamentoService.findAbbonamentoConfig();
	if (abbonamentoModel.getId() == null) {
	    return "redirect:create.htm";
	}
	model.addAttribute("abbonamentiConfigModel", abbonamentoModel);
	model.addAttribute("tipiInstallazioneList", ComportamentoEnum.values());
	model.addAttribute("tipiRicaricheList", TipoRicaricaEnum.values());
	model.addAttribute("destinatariList", DestinatariEnum.values());
	setPageAttributes(model);
	return "abbonamenticonfig/form";
    }

    @RequestMapping
    public String create(Model model) {

	model.addAttribute("abbonamentiConfigModel", new AbbonamentoConfigModel());
	model.addAttribute("tipiInstallazioneList", ComportamentoEnum.values());
	model.addAttribute("tipiRicaricheList", TipoRicaricaEnum.values());
	model.addAttribute("destinatariList", DestinatariEnum.values());
	setPageAttributes(model);
	return "abbonamenticonfig/form";
    }

    @XmlRootElement(name = "request")
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class JsonDettaglioComuneRequest {

	@XmlElement(name = "comune")
	public String comune;

	public JsonDettaglioComuneRequest() {

	}

	public JsonDettaglioComuneRequest(String comune) {

	    this.comune = comune;
	}
    }

    @XmlRootElement(name = "request")
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class JsonDeleteByIdRequest {

	@XmlElement(name = "codice")
	public int codice;

	public JsonDeleteByIdRequest() {

	}

	public JsonDeleteByIdRequest(int codice) {

	    this.codice = codice;
	}
    }

    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RequestMapping(method = RequestMethod.POST)
    public void jsonUpdateConfig(Model model, HttpServletRequest request, HttpServletResponse response) throws JAXBException, IOException {

	try {
	    AggiornaConfigurazioneBaseModel cfg = fromJson(request.getInputStream(), AggiornaConfigurazioneBaseModel.class);
	    log.debug("jsonUpdateConfig# cfg.getMessaggio() {}", cfg.getMessaggio());
	    this.abbonamentoService.salvaConfigurazioneBase(cfg);
	    response.setContentType("application/json");
	    response.getOutputStream().write("{\"status\": \"OK\"}".getBytes());
	} catch (Exception ex) {
	    super.writeJsonError(response, ex.getMessage());
	}
    }

    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RequestMapping(method = RequestMethod.POST)
    public void jsonUpdateMsgNodoPag(Model model, HttpServletRequest request, HttpServletResponse response) throws JAXBException, IOException {

	try {
	    MessaggioNodoPagNonDispModel msg = fromJson(request.getInputStream(), MessaggioNodoPagNonDispModel.class);
	    if (StringUtils.isBlank(msg.getMessaggio())) {
		this.abbonamentoService.eliminaMessaggioNodoPagNonDisponibile(msg);
	    } else {
		this.abbonamentoService.salvaMessaggioNodoPagNonDisponibile(msg);
	    }
	    response.setContentType("application/json");
	    response.getOutputStream().write("{\"status\": \"OK\"}".getBytes());
	} catch (Exception ex) {
	    super.writeJsonError(response, ex.getMessage());
	}
    }

    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RequestMapping(method = RequestMethod.POST)
    public void jsonAddInformativa(Model model, HttpServletRequest request, HttpServletResponse response) throws JAXBException, IOException {

	try {
	    AggiungiInformativaModel info = fromJson(request.getInputStream(), AggiungiInformativaModel.class);
	    this.abbonamentoService.aggiungiInformativa(info);
	    response.setContentType("application/json");
	    response.getOutputStream().write("{\"status\": \"OK\"}".getBytes());
	} catch (Exception ex) {
	    super.writeJsonError(response, ex.getMessage());
	}
    }

    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RequestMapping(method = RequestMethod.POST)
    public void jsonDelInformativa(Model model, HttpServletRequest request, HttpServletResponse response) throws JAXBException, IOException {

	try {
	    JsonDeleteByIdRequest jsonRequest = fromJson(request.getInputStream(), JsonDeleteByIdRequest.class);
	    this.abbonamentoService.rimuoviInformativa(jsonRequest.codice);
	    response.setContentType("application/json");
	    response.getOutputStream().write("{\"status\": \"OK\"}".getBytes());
	} catch (Exception ex) {
	    super.writeJsonError(response, ex.getMessage());
	}
    }

    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RequestMapping(method = RequestMethod.POST)
    public void jsonAddRicarica(Model model, HttpServletRequest request, HttpServletResponse response) throws JAXBException, IOException {

	try {
	    AggiungiRicaricaModel ricarica = fromJson(request.getInputStream(), AggiungiRicaricaModel.class);
	    EsitoAggiornamentoRicariche esito = this.abbonamentoService.aggiungiRicarica(ricarica);
	    String risposta = toJson(esito, false);
	    response.setContentType("application/json");
	    response.getOutputStream().write(risposta.getBytes("utf-8"));
	} catch (Exception ex) {
	    super.writeJsonError(response, ex.getMessage());
	}
    }

    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RequestMapping(method = RequestMethod.POST)
    public void jsonDelRicarica(Model model, HttpServletRequest request, HttpServletResponse response) throws JAXBException, IOException {

	try {
	    JsonDeleteByIdRequest jsonRequest = fromJson(request.getInputStream(), JsonDeleteByIdRequest.class);
	    this.abbonamentoService.rimuoviRicarica(jsonRequest.codice);
	    response.setContentType("application/json");
	    response.getOutputStream().write("{\"status\": \"OK\"}".getBytes());
	} catch (Exception ex) {
	    super.writeJsonError(response, ex.getMessage());
	}
    }

    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RequestMapping(method = RequestMethod.POST)
    public void jsonFindConfigurazioneComune(Model model, HttpServletRequest request, HttpServletResponse response)
	    throws JAXBException, IOException {

	try {
	    JsonDettaglioComuneRequest jsonRequest = fromJson(request.getInputStream(), JsonDettaglioComuneRequest.class);
	    DettaglioComuneModel jsonResponse = this.abbonamentoService.findAbbonamentoConfigComune(jsonRequest.comune);
	    response.setContentType("application/json");
	    String test = toJson(jsonResponse, true);
	    response.getOutputStream().write(test.getBytes("utf-8"));
	} catch (Exception ex) {
	    super.writeJsonError(response, ex.getMessage());
	}
    }
    
}
