package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.Configurazione;
import it.gruppoinit.pal.gp.core.domain.web.OperazioniEsterneCommand;
import it.gruppoinit.pal.gp.core.exception.FunzioneBusinessRemotaException;
import it.gruppoinit.pal.gp.core.service.ApiService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.RuoliUtentiEnum;
import it.gruppoinit.pal.gp.core.service.helper.RiferimentiPraticaSTCRestBean;

import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

@Controller
@SessionAttributes(value = { "operazioniEsterneCommand" })
public class OperazioniEsterneController extends BaseController<Configurazione> {

    @Autowired
    public ApiService apiService;
    @Autowired
    public IstanzeService istanzeService;
    private static final Logger log = LoggerFactory.getLogger(OperazioniEsterneController.class);

    @RequestMapping
    public String caricaPraticaView(@RequestParam(value = "extapp", required = false) Boolean extapp, Model model, HttpServletRequest request) {

	userHasRole(true, RuoliUtentiEnum.OP_ESTERNE_CARICA_ZIP.name());
	OperazioniEsterneCommand operazioniEsterneCommand = new OperazioniEsterneCommand();
	model.addAttribute("operazioniEsterneCommand", operazioniEsterneCommand);
	return "operazioniesterne/extappOperatoriEsterni";
    }

    @RequestMapping
    public String caricaPraticaUpdate(@RequestParam("fileUploadFromExtApp") MultipartFile mpFile, Model model,
	    @ModelAttribute("operazioniEsterneCommand") OperazioniEsterneCommand operazioniEsterneCommand, BindingResult result,
	    HttpServletRequest request, HttpServletResponse response) throws IOException {

	String numero_protocollo = (operazioniEsterneCommand.getNumero_protocollo() != null) ? operazioniEsterneCommand.getNumero_protocollo() : null;
	String data_protocollo = (operazioniEsterneCommand.getData_protocollo() != null) ? new SimpleDateFormat("yyyy-MM-dd")
		.format(operazioniEsterneCommand.getData_protocollo()) : null;
	userHasRole(true, RuoliUtentiEnum.OP_ESTERNE_CARICA_ZIP.name());
	try {
	    RiferimentiPraticaSTCRestBean wsResponse = apiService.creaPraticaDaFileZip(numero_protocollo, data_protocollo, mpFile);
	    operazioniEsterneCommand.setEntity(wsResponse);
	    if (wsResponse.getErrore() != null) {
		throw new FunzioneBusinessRemotaException(wsResponse.getErrore().getDescrizione());
	    }
	} catch (Exception ex) {
	    log.error("Errore durante la chiamata al web service carica pratica Err: " + ex.getMessage(), ex);
	    copyErrorsToFlashMessages(operazioniEsterneCommand, true, "entity", ex);
	    return "redirect:caricaPraticaView.htm";
	}
	return "redirect:caricaPraticaResult.htm";
    }

    @RequestMapping
    public String caricaPraticaResult(@ModelAttribute("operazioniEsterneCommand") OperazioniEsterneCommand operazioniEsterneCommand,
	    HttpServletRequest request, HttpServletResponse response) throws IOException {

	return "operazioniesterne/extappResultPage";
    }

    @Override
    protected void setPageAttributes(Model model) {

    }

    @Override
    protected void fixMergeEntityProperty(Configurazione entity) {

    }

    @Override
    protected void fixRenderEntityProperty(Configurazione entity) {

    }
}
