package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.EquitaliaTracciatiCfg;
import it.gruppoinit.pal.gp.core.domain.Equitaliatracciato;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.SanAmbiti;
import it.gruppoinit.pal.gp.core.domain.web.ChiaveValoreBean;
import it.gruppoinit.pal.gp.core.domain.web.EquitaliatracciatoCommand;
import it.gruppoinit.pal.gp.core.domain.web.TracciatoEquitaliaFilter;
import it.gruppoinit.pal.gp.core.service.ContenttypesService;
import it.gruppoinit.pal.gp.core.service.EquitaliaTracciatiCfgService;
import it.gruppoinit.pal.gp.core.service.EquitaliatracciatoService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.SanAmbitiService;
import it.gruppoinit.pal.gp.core.service.helper.FlashMessages;
import it.gruppoinit.pal.gp.core.service.helper.MessageTracciato450Helper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

@Controller
@SessionAttributes(value = { "istanzeCommand", "equitaliatracciatoCommand" })
public class EquitaliatracciatoController extends BaseController<EquitaliatracciatoCommand> {

    private static final Logger log = LoggerFactory.getLogger(EquitaliatracciatoController.class);
    @Autowired
    private EquitaliatracciatoService equitaliatracciatoService;
    @Autowired
    private IstanzeService istanzeService;
    @Autowired
    private EquitaliaTracciatiCfgService equitaliaTracciatiCfgService;
    @Autowired
    private SanAmbitiService sanAmbitiService;
    @Autowired
    private ContenttypesService contenttypesService;

    @RequestMapping
    public String searchIstanze(HttpServletRequest request, Model model) {

	EquitaliaTracciatiCfg equitaliaTracciatiCfg = equitaliaTracciatiCfgService.findBySoftware();
	//istanzeService.coutIstanzePerTracciatoEquitalia()
	EquitaliatracciatoCommand command = new EquitaliatracciatoCommand();
	TracciatoEquitaliaFilter tracciatoEquitaliaFilter = new TracciatoEquitaliaFilter(equitaliaTracciatiCfg);
	command.setTracciatoEquitaliaFilter(tracciatoEquitaliaFilter);
	command.setEquitaliaTracciatiCfg(equitaliaTracciatiCfg);
	List<SanAmbiti> list = sanAmbitiService.findAll(null, null);
	model.addAttribute("equitaliatracciatoCommand", command);
	model.addAttribute("listaAmbiti", list);
	setPageAttributes(model);
	return "equitaliatracciato/searchistanze";
    }

    private boolean validate(EquitaliatracciatoCommand equitaliatracciatoCommand, BindingResult result) {

	boolean success = true;
	if (equitaliatracciatoCommand.getTracciatoEquitaliaFilter().getIstanzaDataDa() == null) {
	    result.rejectValue("tracciatoEquitaliaFilter.istanzaDataDa", "validator.nonvuoto");
	    success = false;
	}
	if (equitaliatracciatoCommand.getTracciatoEquitaliaFilter().getIstanzaDataA() == null) {
	    result.rejectValue("tracciatoEquitaliaFilter.istanzaDataA", "validator.nonvuoto");
	    success = false;
	}
	if (equitaliatracciatoCommand.getTracciatoEquitaliaFilter().getIstanzaDataDa() != null
		&& equitaliatracciatoCommand.getTracciatoEquitaliaFilter().getIstanzaDataA() != null
		&& equitaliatracciatoCommand.getTracciatoEquitaliaFilter().getIstanzaDataDa()
			.after(equitaliatracciatoCommand.getTracciatoEquitaliaFilter().getIstanzaDataA())) {
	    result.rejectValue("tracciatoEquitaliaFilter.istanzaDataA", "errors.date.sequenza");
	    success = false;
	}
	if (equitaliatracciatoCommand.getTracciatoEquitaliaFilter().getNumeroMaxIstanzeInPacchetto() == null) {
	    result.rejectValue("tracciatoEquitaliaFilter.numeroMaxIstanzeInPacchetto", "validator.nonvuoto");
	    success = false;
	}
	return success;
    }

    @RequestMapping
    public String validaPacchettoIstanze(Model model,
	    @ModelAttribute("equitaliatracciatoCommand") EquitaliatracciatoCommand equitaliatracciatoCommand, BindingResult result,
	    SessionStatus status, HttpServletRequest request, HttpServletResponse respoonse) {

	TracciatoEquitaliaFilter tracciatoEquitaliaFilter = equitaliatracciatoCommand.getTracciatoEquitaliaFilter();
	if (StringUtils.isBlank(tracciatoEquitaliaFilter.getCodiciIstanza())) {
	    // devono essere presente intervallo di date
	    validate(equitaliatracciatoCommand, result);
	}
	// validazione filter
	if (result.hasErrors()) {
	    return "equitaliatracciato/searchistanze";
	}
	List<Integer> codiceIstanze = new ArrayList<Integer>();
	if (StringUtils.isNotBlank(tracciatoEquitaliaFilter.getCodiciIstanza())) {
	    String[] ci = StringUtils.split(tracciatoEquitaliaFilter.getCodiciIstanza(), ",");
	    for (int i = 0; i < ci.length; i++) {
		codiceIstanze.add(Integer.parseInt(ci[i]));
	    }
	} else {
	    codiceIstanze = istanzeService.findIstanzaPerTracciatoEquitalia(equitaliatracciatoCommand.getTracciatoEquitaliaFilter(), true, 0,
		    equitaliatracciatoCommand.getTracciatoEquitaliaFilter().getNumeroMaxIstanzeInPacchetto());
	}
	log.debug("validaPacchettoIstanze# Valido pacchetto istanze recuperate....");
	List<ChiaveValoreBean<String, List<MessageTracciato450Helper>>> resultValidazione = equitaliatracciatoService
		.validaInformazioniIstanze(codiceIstanze);
	int istanzeConErrori = 0;
	for (ChiaveValoreBean<String, List<MessageTracciato450Helper>> chiaveValoreBean : resultValidazione) {
	    if (!chiaveValoreBean.getValore().isEmpty()) {
		List<MessageTracciato450Helper> list = chiaveValoreBean.getValore();
		for (MessageTracciato450Helper messageTracciato450Helper : list) {
		    if (messageTracciato450Helper.getIsError()) {
			istanzeConErrori++;
			break;
		    }
		}
	    }
	}
	EquitaliaTracciatiCfg equitaliaTracciatiCfg = equitaliaTracciatiCfgService.findBySoftware();
	equitaliatracciatoCommand.setCodiceIstanze(codiceIstanze);
	equitaliatracciatoCommand.setNumeroIstanzeNonValidatepacchetto(istanzeConErrori);
	equitaliatracciatoCommand.setNumeroIstanzepacchetto(codiceIstanze.size());
	equitaliatracciatoCommand.setResultValidazione(resultValidazione);
	equitaliatracciatoCommand.setEquitaliaTracciatiCfg(equitaliaTracciatiCfg);
	//	equitaliatracciatoCommand.setNomeResponsabile(getCurrentlyAuthenticatedUserDetails().getResponsabile());
	fixRenderEntityProperty(equitaliatracciatoCommand);
	model.addAttribute("equitaliatracciatoCommand", equitaliatracciatoCommand);
	SanAmbiti ambito = new SanAmbiti();
	if (StringUtils.isNotBlank(equitaliatracciatoCommand.getTracciatoEquitaliaFilter().getValoredyn2CampiFiltroAmbito())) {
	    ambito = sanAmbitiService.findById(Integer.parseInt(equitaliatracciatoCommand.getTracciatoEquitaliaFilter()
		    .getValoredyn2CampiFiltroAmbito()));
	}
	model.addAttribute("ambito", ambito);
	model.addAttribute("istanzeConErrori", istanzeConErrori);
	return "equitaliatracciato/resultValidazione";
    }

    private boolean creaTracciatoEquitalia450Istanze(EquitaliatracciatoCommand equitaliatracciatoCommand, BindingResult result) {

	boolean success = true;
	if (StringUtils.isBlank(equitaliatracciatoCommand.getNomeResponsabile())) {
	    result.rejectValue("nomeResponsabile", "validator.nonvuoto");
	    success = false;
	}
	if (StringUtils.isBlank(equitaliatracciatoCommand.getCognomeResponsabile())) {
	    result.rejectValue("cognomeResponsabile", "validator.nonvuoto");
	    success = false;
	}
	return success;
    }

    @RequestMapping
    public String creaTracciatoEquitalia450Istanze(Model model, @RequestParam(required = false, value = "isForzaCreazione") Boolean isForzaCreazione,
	    @ModelAttribute("equitaliatracciatoCommand") EquitaliatracciatoCommand command, BindingResult result, SessionStatus status,
	    HttpServletRequest request, HttpServletResponse response) {

	// eseguo nuovamente la validazione in quanto qualcuno potrebbe aver cancellato dati
	int istanzeConErrori = 0;
	Integer codice = null;
	// devono essere presente intervallo di date
	creaTracciatoEquitalia450Istanze(command, result);
	// validazione filter
	if (result.hasErrors()) {
	    return "equitaliatracciato/resultValidazione";
	}
	boolean _isForzaCreazione = BooleanUtils.toBoolean(isForzaCreazione);
	try {
	    if (!_isForzaCreazione) {
		log.debug("creaTracciatoEquitalia450Istanze# Valido pacchetto istanze recuperate....");
		List<ChiaveValoreBean<String, List<MessageTracciato450Helper>>> resultValidazione = equitaliatracciatoService
			.validaInformazioniIstanze(command.getCodiceIstanze());
		for (ChiaveValoreBean<String, List<MessageTracciato450Helper>> chiaveValoreBean : resultValidazione) {
		    if (!chiaveValoreBean.getValore().isEmpty()) {
			List<MessageTracciato450Helper> list = chiaveValoreBean.getValore();
			for (MessageTracciato450Helper messageTracciato450Helper : list) {
			    if (messageTracciato450Helper.getIsError()) {
				istanzeConErrori++;
				break;
			    }
			}
		    }
		}
	    } else {
		log.debug("creaTracciatoEquitalia450Istanze# Forzo creazione tracciato escludendo pratiche con errori ");
	    }
	    if (istanzeConErrori > 0) {
		log.error("creaTracciatoEquitalia450Istanze# Validazione pacchetto non riuscita...");
	    } else {
		codice = equitaliatracciatoService.insertTracciatoEquitalia450(command.getEquitaliaTracciatiCfg(), command.getCodiceIstanze(),
			command.getNomeResponsabile(), command.getCognomeResponsabile(), _isForzaCreazione);
	    }
	} catch (Exception e) {
	    FlashMessages.getWarnings().add("Errore durante la creazione del tracciato: " + e.getMessage());
	    log.error("creaTracciatoEquitalia450Istanze# e={}", e);
	    return "redirect:searchIstanze.htm";
	}
	return "redirect:view.htm?codice=" + codice + "&status_msg=01";
    }

    @RequestMapping
    public void ajaxCreaTracciatoEquitalia450IstanzeExcel(Model model,
	    @RequestParam(required = false, value = "isForzaCreazione") Boolean isForzaCreazione,
	    @ModelAttribute("equitaliatracciatoCommand") EquitaliatracciatoCommand command, BindingResult result, SessionStatus status,
	    HttpServletRequest request, HttpServletResponse response) {

	// eseguo nuovamente la validazione in quanto qualcuno potrebbe aver cancellato dati
	int istanzeConErrori = 0;
	Integer codice = null;
	// devono essere presente intervallo di date
	creaTracciatoEquitalia450Istanze(command, result);
	// validazione filter
	if (result.hasErrors()) {
	    //return "equitaliatracciato/resultValidazione";
	}
	boolean _isForzaCreazione = BooleanUtils.toBoolean(isForzaCreazione);
	try {
	    if (!_isForzaCreazione) {
		log.debug("creaTracciatoEquitalia450Istanze# Valido pacchetto istanze recuperate....");
		List<ChiaveValoreBean<String, List<MessageTracciato450Helper>>> resultValidazione = equitaliatracciatoService
			.validaInformazioniIstanze(command.getCodiceIstanze());
		for (ChiaveValoreBean<String, List<MessageTracciato450Helper>> chiaveValoreBean : resultValidazione) {
		    if (!chiaveValoreBean.getValore().isEmpty()) {
			List<MessageTracciato450Helper> list = chiaveValoreBean.getValore();
			for (MessageTracciato450Helper messageTracciato450Helper : list) {
			    if (messageTracciato450Helper.getIsError()) {
				istanzeConErrori++;
				break;
			    }
			}
		    }
		}
	    } else {
		log.debug("creaTracciatoEquitalia450Istanze# Forzo creazione tracciato escludendo pratiche con errori ");
	    }
	    if (istanzeConErrori > 0) {
		log.error("creaTracciatoEquitalia450IstanzeExcel# Validazione pacchetto non riuscita...");
	    } else {
		ChiaveValoreBean<String, byte[]> cv = equitaliatracciatoService.downloadTracciatoEquitalia450Excel(
			command.getEquitaliaTracciatiCfg(), command.getCodiceIstanze(), command.getNomeResponsabile(),
			command.getCognomeResponsabile(), _isForzaCreazione);
		byte[] responseByte = cv.getValore();
		String filename = cv.getChiave();
		if (filename != null && !filename.equals("")) {
		    response.setHeader("Pragma", "public");
		    response.setHeader("Cache-Control", "max-age=0");
		    response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
		    response.setHeader("Content-transfer-encoding", "binary");
		    String cType = contenttypesService.findMimeTypeByFileName(filename);
		    response.setContentType(cType);
		    response.setContentLength(responseByte.length);
		    ServletOutputStream out = response.getOutputStream();
		    out.write(responseByte);
		    out.flush();
		} else {
		    throw new RuntimeException("File senza nome.");
		}
	    }
	} catch (Exception e) {
	    FlashMessages.getWarnings().add("Errore durante la creazione del tracciato: " + e.getMessage());
	    log.error("Errore durante la creazione del tracciato::" + e.getMessage());
	    throw new RuntimeException("Errore durante la creazione del tracciato:" + e.getMessage());
	}
	//return "redirect:view.htm?codice=" + codice + "&status_msg=01";
    }

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	List<Equitaliatracciato> equitaliatracciatoList = equitaliatracciatoService.findAll(null, null);
	ModelMap model = new ModelMap(equitaliatracciatoList);
	boolean export = createJMesaExport(request, response, equitaliatracciatoList);
	if (export) {
	    return null;
	}
	model.addAttribute("equitaliatracciatoList", equitaliatracciatoList);
	return model;
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	Equitaliatracciato equitaliatracciato = equitaliatracciatoService.findById(id);
	model.addAttribute("equitaliatracciato", equitaliatracciato);
	setPageAttributes(model);
	return "equitaliatracciato/form";
    }

    @RequestMapping
    public String aggiornaDataTracciato(@RequestParam("codice") Integer codice, @RequestParam("dataTracciato") Date dataTracciato, Model model,
	    HttpServletRequest request) {

	equitaliatracciatoService.aggiornaDataCreazioneTracciato(codice, dataTracciato);
	return "redirect:view.htm?codice=" + codice + "&status_msg=02";
    }
    
    @RequestMapping
    public String checkfile(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	Equitaliatracciato equitaliatracciato = equitaliatracciatoService.findById(id);
	Map<String, String> resultRowLenth = equitaliatracciatoService.checkFileRowLenth(equitaliatracciato);
	model.addAttribute("resultRowLenth", resultRowLenth);
	model.addAttribute("equitaliatracciato", equitaliatracciato);
	setPageAttributes(model);
	return "equitaliatracciato/form";
    }

    @RequestMapping
    public String delete(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	try {
	    PkId id = new PkId(codice);
	    Equitaliatracciato equitaliatracciato = equitaliatracciatoService.findById(id);
	    equitaliatracciatoService.deleteTracciato(equitaliatracciato);
	} catch (Exception e) {
	    log.error("{}", e);
	    FlashMessages.getWarnings().add(e.getMessage());
	    return "redirect:view.htm?codice=" + codice + "&status_msg=03";
	}
	return "redirect:list.htm?codice=" + codice + "&status_msg=01";
    }

    @Override
    protected void setPageAttributes(Model model) {

	// TODO Auto-generated method stub
    }

    @Override
    protected void fixMergeEntityProperty(EquitaliatracciatoCommand entity) {

	// TODO Auto-generated method stub
    }

    @Override
    protected void fixRenderEntityProperty(EquitaliatracciatoCommand entity) {

	if (entity.getEquitaliaTracciatiCfg() == null) {
	    entity.setEquitaliaTracciatiCfg(new EquitaliaTracciatiCfg());
	}
    }
}
