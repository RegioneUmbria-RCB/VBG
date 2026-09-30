package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Dyn2Basecontesti;
import it.gruppoinit.pal.gp.core.domain.Dyn2Campi;
import it.gruppoinit.pal.gp.core.domain.Dyn2CampiScript;
import it.gruppoinit.pal.gp.core.domain.Dyn2CampiScriptId;
import it.gruppoinit.pal.gp.core.domain.Dyn2Campiproprieta;
import it.gruppoinit.pal.gp.core.domain.Dyn2CampiproprietaId;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.domain.web.ChiaveValoreBean;
import it.gruppoinit.pal.gp.core.domain.web.Dyn2CampiCommand;
import it.gruppoinit.pal.gp.core.service.Dyn2BasecontestiService;
import it.gruppoinit.pal.gp.core.service.Dyn2CampiScriptService;
import it.gruppoinit.pal.gp.core.service.Dyn2CampiService;
import it.gruppoinit.pal.gp.core.service.Dyn2CampiproprietaService;
import it.gruppoinit.pal.gp.core.service.Dyn2ModellitService.TipoControlloEnum;
import it.gruppoinit.pal.gp.core.service.ModelliDinamiciFormuleService.EventoModelli;
import it.gruppoinit.pal.gp.core.service.SoftwareService;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
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

/**
 * 
 * @author
 */
@Controller
@SessionAttributes("dyn2campi")
public class Dyn2CampiController extends BaseController<Dyn2Campi> {

    @Autowired
    private Dyn2CampiScriptService dyn2CampiScriptService;
    @Autowired
    private Dyn2CampiService dyn2campiService;
    @Autowired
    private SoftwareService softwareService;
    @Autowired
    private Dyn2BasecontestiService basecontestiService;
    @Autowired
    private Dyn2CampiproprietaService dyn2CampiproprietaService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	List<Dyn2Campi> dyn2campiList = dyn2campiService.findAll(null, null);
	ModelMap model = new ModelMap(dyn2campiList);
	boolean export = createJMesaExport(request, response, dyn2campiList);
	if (export) {
	    return null;
	}
	model.addAttribute("dyn2campiList", dyn2campiList);
	return model;
    }

    @RequestMapping
    public String create(Model model, @RequestParam(value = "popup", required = false) Boolean popup,
	    @RequestParam(value = "dyn2campi", required = false) Dyn2CampiCommand cmd, HttpServletRequest request) {

	Dyn2Campi dyn2campi = new Dyn2Campi();
	//dyn2campi.setPopup(popup);
	dyn2campi.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
	fixRenderEntityProperty(dyn2campi);
	//	List<Dyn2Basecontesti> basecontestis = basecontestiService.findAll(null, null);
	//	List<ChiaveValoreBean<String, String>> enumTipi = new ArrayList<ChiaveValoreBean<String, String>>();
	//	TipoControlloEnum[] tipi = TipoControlloEnum.values();
	//	for (TipoControlloEnum tipoControlloEnum : tipi) {
	//	    ChiaveValoreBean<String, String> tipo = new ChiaveValoreBean<String, String>();
	//	    tipo.setChiave(tipoControlloEnum.name());
	//	    tipo.setValore(tipoControlloEnum.value());
	//	    enumTipi.add(tipo);
	//	}
	//	model.addAttribute("enumTipi", enumTipi);
	//	model.addAttribute("basecontestis", basecontestis);
	setAttributeTipiValori(model);
	if (cmd == null) {
	    cmd = new Dyn2CampiCommand();
	}
	cmd.setEntity(dyn2campi);
	cmd.setPopup(popup);
	model.addAttribute("dyn2campi", cmd);
	setPageAttributes(model);
	return "dyn2campi/form";
    }

    @RequestMapping
    public String popupcreate(Model model, @RequestParam(value = "callerId", required = false) Integer callerId,
	    @RequestParam(value = "callerModT", required = false) Integer d2mtId, HttpServletRequest request) {

	Dyn2CampiCommand cmd = new Dyn2CampiCommand();
	cmd.setCallerId(callerId);
	cmd.setPopup(Boolean.TRUE);
	cmd.setDyn2ModellotId(d2mtId);
	return create(model, true, cmd, request);
    }

    @RequestMapping
    public String insert(Model model, @ModelAttribute("dyn2campi") Dyn2CampiCommand cmd, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	Dyn2Campi dyn2campi = cmd.getEntity();
	fixMergeEntityProperty(dyn2campi);
	dyn2campi.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
	try {
	    dyn2campiService.insert(dyn2campi);
	    Integer newbornId = dyn2campi.getId().getCodice();
	    cmd.setNewId(newbornId);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, dyn2campi, e);
	    //	    List<ChiaveValoreBean<String, String>> enumTipi = new ArrayList<ChiaveValoreBean<String, String>>();
	    //	    TipoControlloEnum[] tipi = TipoControlloEnum.values();
	    //	    for (TipoControlloEnum tipoControlloEnum : tipi) {
	    //		ChiaveValoreBean<String, String> tipo = new ChiaveValoreBean<String, String>();
	    //		tipo.setChiave(tipoControlloEnum.name());
	    //		tipo.setValore(tipoControlloEnum.value());
	    //		enumTipi.add(tipo);
	    //	    }
	    //	    model.addAttribute("enumTipi", enumTipi);
	    //	    List<Dyn2Basecontesti> basecontestis = basecontestiService.findAll(null, null);
	    //	    model.addAttribute("basecontestis", basecontestis);
	    setAttributeTipiValori(model);
	    fixRenderEntityProperty(dyn2campi);
	    return "dyn2campi/form";
	}
	StringBuilder sbUrl = new StringBuilder("redirect:");
	if (BooleanUtils.isTrue(cmd.getPopup())) {
	    sbUrl.append("popup");
	}
	sbUrl.append("view.htm?codice=").append(dyn2campi.getId().getCodice());
	if (cmd.getNewId() != null) {
	    sbUrl.append("&newId=").append(cmd.getNewId());
	}
	if (cmd.getCallerId() != null) {
	    sbUrl.append("&callerId=").append(cmd.getCallerId());
	}
	if (cmd.getDyn2ModellotId() != null) {
	    sbUrl.append("&callerModT=").append(cmd.getDyn2ModellotId());
	}
	sbUrl.append("&status_msg=01");
	return sbUrl.toString();
    }

    @RequestMapping
    public String popupinsert(Model model, @ModelAttribute("dyn2campi") Dyn2CampiCommand cmd, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	return insert(model, cmd, result, status, request);
    }

    private String popupcloseRedirect(Integer codicecampo, String status_msg, String popupCaller) {

	return "popupview.htm?codice=" + codicecampo + "&popup=true&status_msg=" + status_msg + "&popupCaller=" + popupCaller + "&done=true";
    }

    @RequestMapping
    public String view(@RequestParam(value = "codice") Integer codice, @RequestParam(value = "callerId", required = false) Integer callerId,
	    @RequestParam(value = "changeTipodato", required = false) Boolean changeTipo,
	    @RequestParam(value = "tipodato", required = false) String tipodato,
	    @RequestParam(value = "dyn2campi", required = false) Dyn2CampiCommand cmd, Model model, HttpServletRequest request) {

	Dyn2Campi dyn2campi = dyn2campiService.findById(new PkId(codice));
	if (cmd == null) {
	    cmd = new Dyn2CampiCommand();
	}
	cmd.setEntity(dyn2campi);
	cmd.setCallerId(callerId);
	cmd.setChangeTipodato(changeTipo);
	cmd.setTipodato(tipodato);
	//request.getSession().setAttribute(arg0, arg1);
	//dyn2campi.setPopup(popup);
	fixRenderEntityProperty(dyn2campi);
	// Nel caso di cambio tipo dato devo ricalcolare i tipi dati da presnetare sulla maschera
	// La view viene utilizzata per ricaricare la pagina andando a cambiare la sezione 
	// "proprietà di controllo" Dyn2Campiproprieta
	Dyn2Campi _dyn2campi = new Dyn2Campi();
	if (BooleanUtils.toBoolean(cmd.getChangeTipodato())) {
	    // Il metodo recupera i Dyn2Campiproprieta associati a dyn2campi li elimina
	    // e li ricalcalcola in base al tipo dato passato
	    _dyn2campi = dyn2campiService.insertNewCampiproprietaAndView(dyn2campi, cmd.getTipodato());
	    cmd.setEntity(_dyn2campi);
	    //_dyn2campi.setPopup(popup);
	}
	//	List<Dyn2Basecontesti> basecontestis = basecontestiService.findAll(null, null);
	//	List<ChiaveValoreBean<String, String>> enumTipi = new ArrayList<ChiaveValoreBean<String, String>>();
	//	TipoControlloEnum[] tipi = TipoControlloEnum.values();
	//	for (TipoControlloEnum tipoControlloEnum : tipi) {
	//	    ChiaveValoreBean<String, String> tipo = new ChiaveValoreBean<String, String>();
	//	    tipo.setChiave(tipoControlloEnum.name());
	//	    tipo.setValore(tipoControlloEnum.value());
	//	    enumTipi.add(tipo);
	//	}
	//	model.addAttribute("enumTipi", enumTipi);
	//	model.addAttribute("basecontestis", basecontestis);
	setAttributeTipiValori(model);
	model.addAttribute("dyn2campi", cmd);
	setPageAttributes(model);
	return "dyn2campi/form";
    }

    @RequestMapping
    public String popupview(@RequestParam(value = "codice") Integer codice, @RequestParam(value = "callerId", required = false) Integer callerId,
	    @RequestParam(value = "changeTipodato", required = false) Boolean changeTipo,
	    @RequestParam(value = "tipodato", required = false) String tipodato, @RequestParam(value = "newId", required = false) Integer newId,
	    @RequestParam(value = "callerModT", required = false) Integer d2mtId, Model model, HttpServletRequest request) {

	Dyn2CampiCommand cmd = new Dyn2CampiCommand();
	cmd.setPopup(Boolean.TRUE);
	cmd.setCallerId(callerId);
	cmd.setDyn2ModellotId(d2mtId);
	cmd.setChangeTipodato(changeTipo);
	cmd.setTipodato(tipodato);
	cmd.setNewId(newId);
	return view(codice, callerId, changeTipo, tipodato, cmd, model, request);
    }

    @RequestMapping
    public String popupupdate(Model model, @ModelAttribute("dyn2campi") Dyn2CampiCommand cmd, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	return update(model, cmd, result, status, request);
    }

    @RequestMapping
    public String update(Model model, @ModelAttribute("dyn2campi") Dyn2CampiCommand cmd, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	Dyn2Campi dyn2campi = cmd.getEntity();
	fixMergeEntityProperty(dyn2campi);
	try {
	    dyn2campiService.update(dyn2campi);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, dyn2campi, e);
	    //	    List<Dyn2Basecontesti> basecontestis = basecontestiService.findAll(null, null);
	    //	    model.addAttribute("basecontestis", basecontestis);
	    setAttributeTipiValori(model);
	    fixRenderEntityProperty(dyn2campi);
	    return "dyn2campi/form";
	}
	StringBuilder sbUrl = new StringBuilder("redirect:");
	if (BooleanUtils.isTrue(cmd.getPopup())) {
	    sbUrl.append("popup");
	}
	sbUrl.append("view.htm?codice=").append(dyn2campi.getId().getCodice()).append("&callerId=");
	if (cmd.getCallerId() != null) {
	    sbUrl.append(cmd.getCallerId());
	}
	if (cmd.getNewId() != null) {
	    sbUrl.append("&newId=").append(cmd.getNewId());
	}
	sbUrl.append("&status_msg=02");
	return sbUrl.toString();
    }

    @RequestMapping
    public String delete(Model model, @ModelAttribute("dyn2campi") Dyn2CampiCommand dyn2campi, BindingResult result, SessionStatus status) {

	Dyn2Campi objToDelete = dyn2campiService.findById(dyn2campi.getEntity().getId());
	try {
	    dyn2campiService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, dyn2campi, e);
	    //	    List<Dyn2Basecontesti> basecontestis = basecontestiService.findAll(null, null);
	    //	    List<ChiaveValoreBean<String, String>> enumTipi = new ArrayList<ChiaveValoreBean<String, String>>();
	    //	    TipoControlloEnum[] tipi = TipoControlloEnum.values();
	    //	    for (TipoControlloEnum tipoControlloEnum : tipi) {
	    //		ChiaveValoreBean<String, String> tipo = new ChiaveValoreBean<String, String>();
	    //		tipo.setChiave(tipoControlloEnum.name());
	    //		tipo.setValore(tipoControlloEnum.value());
	    //		enumTipi.add(tipo);
	    //	    }
	    //	    model.addAttribute("enumTipi", enumTipi);
	    //	    model.addAttribute("basecontestis", basecontestis);
	    setAttributeTipiValori(model);
	    fixRenderEntityProperty(dyn2campi.getEntity());
	    return "dyn2campi/form";
	}
	status.setComplete();
	return "redirect:list.htm";
    }

    @RequestMapping
    public String viewFormule(@RequestParam("codice") Integer codice, @RequestParam(value = "evento", required = false) String evento, Model model,
	    HttpServletRequest request) {

	checkAccesso();
	PkId id = new PkId(codice);
	Dyn2Campi dyn2campi = dyn2campiService.findById(id);
	fixRenderEntityProperty(dyn2campi);
	model.addAttribute("dyn2campi", dyn2campi);
	if (StringUtils.isBlank(evento)) {
	    evento = EventoModelli.Caricamento.name();
	}
	Dyn2CampiScript script = dyn2CampiScriptService.findByCampoAndEvento(codice, evento);
	String scriptString = "";
	if (script != null) {
	    if (script.getScript() != null) {
		scriptString = new String(script.getScript());
	    }
	}
	model.addAttribute("scriptString", scriptString);
	model.addAttribute("evento", evento);
	model.addAttribute("codice", codice);
	setPageAttributes(model);
	return "dyn2campi/formFormule";
    }

    @RequestMapping
    public String saveFormula(@RequestParam("codice") Integer codice, @RequestParam(value = "evento") String evento,
	    @RequestParam(value = "scriptString") String scriptString, Model model, HttpServletRequest request) {

	checkAccesso();
	Dyn2CampiScriptId id = new Dyn2CampiScriptId(codice, evento);
	Dyn2CampiScript script = dyn2CampiScriptService.findById(id);
	byte[] content = null;
	if (script == null) {
	    script = new Dyn2CampiScript();
	    script.setId(id);
	    try {
		content = scriptString.getBytes("UTF-8");
	    } catch (UnsupportedEncodingException e) {
		content = scriptString.getBytes();
	    }
	    script.setScript(content);
	    dyn2CampiScriptService.insert(script);
	} else {
	    try {
		content = scriptString.getBytes("UTF-8");
	    } catch (UnsupportedEncodingException e) {
		content = scriptString.getBytes();
	    }
	    script.setScript(content);
	    dyn2CampiScriptService.update(script);
	}
	return "redirect:viewFormule.htm?codice=" + codice + "&evento=" + evento;
    }

    private void checkAccesso() {

	Responsabili resp = getCurrentlyAuthenticatedUserDetails();
	if (StringUtils.defaultIfEmpty(resp.getAmministratore(), "0").equalsIgnoreCase("0")) {
	    throw new SecurityException("Solo Amministratori possono accedere alla configurazione delle formule dei campi dinamici");
	}
    }

    @Override
    protected void fixMergeEntityProperty(Dyn2Campi entity) {

    }

    @Override
    protected void fixRenderEntityProperty(Dyn2Campi entity) {

	if (EntityUtils.getNestedProperty(entity, "id.codice") != null) {
	    //Crea la lista di Dyn2Campiproprieta, se già esistono per il campo passato li recupera o altrimenti li crea
	    Set<Dyn2Campiproprieta> lista = dyn2campiService.createDyn2Campiproprieta(entity);
	    entity.setDyn2Campiproprietas(lista);
	}
	if (entity.getDyn2Basecontesti() == null) {
	    entity.setDyn2Basecontesti(new Dyn2Basecontesti());
	}
    }

    private Dyn2Campiproprieta checkCampoExist(Set<Dyn2Campiproprieta> campiConfigurati, String nomeCampo, String valoreCampo, Dyn2Campi campi) {

	Dyn2Campiproprieta campiproprieta = new Dyn2Campiproprieta();
	Dyn2CampiproprietaId id = new Dyn2CampiproprietaId();
	campiproprieta.setDyn2Campi(campi);
	String[] valoriCampo = valoreCampo.split("#");
	for (Dyn2Campiproprieta dyn2Campiproprieta : campiConfigurati) {
	    if (dyn2Campiproprieta.getId().getProprieta().equals(nomeCampo)) {
		campiproprieta = dyn2Campiproprieta;
		campiproprieta.setTipologiaCampoTransient(valoriCampo[1]);
		campiproprieta.setEtichettaTransiet(getMessageFromBundle(valoriCampo[0], null));
		if (valoriCampo.length == 3) {
		    campiproprieta.setValore(valoriCampo[2]);
		} else {
		    campiproprieta.setValore("");
		}
		break;
	    }
	}
	if (campiproprieta.getId() == null) {
	    id = new Dyn2CampiproprietaId();
	    id.setIdcomune(ORMHelper.getIdcomune());
	    id.setFkD2cId(campi.getId().getCodice());
	    id.setProprieta(nomeCampo);
	    campiproprieta.setId(id);
	    campiproprieta.setTipologiaCampoTransient(valoriCampo[1]);
	    campiproprieta.setEtichettaTransiet(valoriCampo[0]);
	    if (valoriCampo.length == 3) {
		campiproprieta.setValore(valoriCampo[2]);
	    } else {
		campiproprieta.setValore("");
	    }
	}
	return campiproprieta;
    }

    @Override
    protected void setPageAttributes(Model model) {

    }

    private void setAttributeTipiValori(Model model) {

	List<Dyn2Basecontesti> basecontestis = basecontestiService.findAll(null, null);
	List<ChiaveValoreBean<String, String>> enumTipi = new ArrayList<ChiaveValoreBean<String, String>>();
	TipoControlloEnum[] tipi = TipoControlloEnum.values();
	for (TipoControlloEnum tipoControlloEnum : tipi) {
	    // elimino i BOTTONI, LOCALIZZAZIONI, UPDLOAD
	    if (!(tipoControlloEnum.equals(TipoControlloEnum.Bottone) || tipoControlloEnum.equals(TipoControlloEnum.Localizzazione) || tipoControlloEnum
		    .equals(TipoControlloEnum.ListaSIGePro))) {
		ChiaveValoreBean<String, String> tipo = new ChiaveValoreBean<String, String>();
		tipo.setChiave(tipoControlloEnum.name());
		tipo.setValore(tipoControlloEnum.value());
		enumTipi.add(tipo);
	    }
	}
	model.addAttribute("enumTipi", enumTipi);
	model.addAttribute("basecontestis", basecontestis);
    }
}
