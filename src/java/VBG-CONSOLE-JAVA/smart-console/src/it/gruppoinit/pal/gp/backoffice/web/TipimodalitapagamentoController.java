/**
 * 
 */
package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.Comuniassociati;
import it.gruppoinit.pal.gp.core.domain.ComuniassociatiId;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabilicomuni;
import it.gruppoinit.pal.gp.core.domain.StarConfigOneri;
import it.gruppoinit.pal.gp.core.domain.Tipimodalitapagamento;
import it.gruppoinit.pal.gp.core.domain.web.StarConfigOneriCommand;
import it.gruppoinit.pal.gp.core.service.ComuniService;
import it.gruppoinit.pal.gp.core.service.ComuniassociatiService;
import it.gruppoinit.pal.gp.core.service.StarConfigOneriService;
import it.gruppoinit.pal.gp.core.service.TipimodalitapagamentoService;

import java.util.List;

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
import org.springframework.web.bind.support.SessionStatus;

/**
 * @author francescop
 * 
 */
@Controller
//@SessionAttributes("tipimodalitapagamento")
public class TipimodalitapagamentoController extends BaseController<Tipimodalitapagamento> {

    @Autowired
    private TipimodalitapagamentoService tipimodalitapagamentoService;
    @Autowired
    private StarConfigOneriService starConfigOneriService;
    @Autowired
    private ComuniassociatiService comuniassociatiService;
    @Autowired
    private ComuniService comuniService;

    @RequestMapping
    public ModelMap list(@ModelAttribute(value = "cfgonericmd") StarConfigOneriCommand oneriCfgCommand,
	    @RequestParam(required = false, value = "save") Boolean autoSave, HttpServletRequest request, HttpServletResponse response) {

	//StarConfigOneriCommand oneriCfgCommand = null;
	if (oneriCfgCommand == null) {
	    oneriCfgCommand = new StarConfigOneriCommand();
	} else if (BooleanUtils.isTrue(autoSave)) {
	    this.fixMergeStarConfigOneriProperty(oneriCfgCommand.getCfgOneri());
	    starConfigOneriService.update(oneriCfgCommand.getCfgOneri());
	}
	this.populateConfigOneri(oneriCfgCommand);
	List<Tipimodalitapagamento> tipimodalitapagamentoList = tipimodalitapagamentoService.findAll(null, null);
	ModelMap model = new ModelMap();
	boolean export = createJMesaExport(request, response, tipimodalitapagamentoList);
	if (export)
	    return null;
	model.addAttribute("tipimodalitapagamentoList", tipimodalitapagamentoList);
	model.addAttribute("cfgoneri", oneriCfgCommand);
	return model;
    }

    @RequestMapping
    public String delete(@ModelAttribute("tipimodalitapagamento") Tipimodalitapagamento tipimodalitapagamento, BindingResult result,
	    SessionStatus status) {

	Tipimodalitapagamento objToDelete = tipimodalitapagamentoService.findById(tipimodalitapagamento.getId());
	try {
	    tipimodalitapagamentoService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderEntityProperty(tipimodalitapagamento);
	    return "tipimodalitapagamento/form";
	}
	status.setComplete();
	return "redirect:list.htm";
    }

    @RequestMapping
    public String insert(Model model, @ModelAttribute("tipimodalitapagamento") Tipimodalitapagamento tipimodalitapagamento, BindingResult result,
	    SessionStatus status) {

	fixMergeEntityProperty(tipimodalitapagamento);
	try {
	    tipimodalitapagamentoService.insert(tipimodalitapagamento);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, tipimodalitapagamento, e);
	    fixRenderEntityProperty(tipimodalitapagamento);
	    return "tipimodalitapagamento/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + tipimodalitapagamento.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String update(Model model, @ModelAttribute("tipimodalitapagamento") Tipimodalitapagamento tipimodalitapagamento, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	fixMergeEntityProperty(tipimodalitapagamento);
	try {
	    tipimodalitapagamentoService.update(tipimodalitapagamento);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, tipimodalitapagamento, e);
	    fixRenderEntityProperty(tipimodalitapagamento);
	    return "oggettiinfo/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + tipimodalitapagamento.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String create(Model model) {

	Tipimodalitapagamento tipimodalitapagamento = new Tipimodalitapagamento();
	fixRenderEntityProperty(tipimodalitapagamento);
	model.addAttribute("tipimodalitapagamento", tipimodalitapagamento);
	setPageAttributes(model);
	return "tipimodalitapagamento/form";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	Tipimodalitapagamento tipimodalitapagamento = tipimodalitapagamentoService.findById(id);
	fixRenderEntityProperty(tipimodalitapagamento);
	model.addAttribute("tipimodalitapagamento", tipimodalitapagamento);
	setPageAttributes(model);
	return "tipimodalitapagamento/form";
    }

    @RequestMapping
    public String saveConfig(Model model, @ModelAttribute(value = "cfgonericmd") StarConfigOneriCommand oneriCmd, BindingResult result,
	    HttpServletRequest request) {

	StarConfigOneri oneriCfg = oneriCmd.getCfgOneri();
	String statusParam = "";
	try {
	    /*
	    StarConfigOneri cfgRecord = null;
	    String codComune = null;
	    if (ORMHelper.isConsoleRegionale()) {
	    codComune = ORMHelper.getIdcomune();
	    }
	    else{
	    codComune = oneriCfg.getComune().getCodicecomune();
	    }
	    cfgRecord = this.starConfigOneriService.findConfigOneriByCodiceComune(codComune);
	    if (cfgRecord == null) {
	    cfgRecord = new StarConfigOneri();
	    cfgRecord.setComune(new Comuni(codComune));
	    cfgRecord.setInfoPagamenti(oneriCfg.getInfoPagamenti());
	    cfgRecord.setFlagOnlineEntiTerzi(oneriCfg.getFlagOnlineEntiTerzi());
	    this.starConfigOneriService.insert(cfgRecord);
	    } else {
	    cfgRecord.setInfoPagamenti(oneriCfg.getInfoPagamenti());
	    cfgRecord.setFlagOnlineEntiTerzi(oneriCfg.getFlagOnlineEntiTerzi());
	    this.starConfigOneriService.update(cfgRecord);
	    }
	    */
	    if (oneriCfg != null) {
		this.fixMergeStarConfigOneriProperty(oneriCfg);
		this.saveOneriConfig(oneriCfg);
		statusParam = "status_msg=02";
	    }
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, oneriCfg, e);
	}
	String comLoc = oneriCmd.getComuneLocalizzazione() != null ? oneriCmd.getComuneLocalizzazione().getCodicecomune() : "";
	return "redirect:list.htm?comuneLocalizzazione.codicecomune=" + StringUtils.defaultString(comLoc) + "&" + statusParam;
    }

    private void saveOneriConfig(StarConfigOneri sco) {

	StarConfigOneri dbData = this.starConfigOneriService.findById(sco.getId());
	if (dbData != null) {
	    this.starConfigOneriService.update(sco);
	} else {
	    this.starConfigOneriService.insert(sco);
	}
    }

    private void populateConfigOneri(StarConfigOneriCommand cmd) {

	StarConfigOneri cfgBase = this.starConfigOneriService.findConfigOneriBase();
	if (cfgBase == null) {
	    cfgBase = new StarConfigOneri();
	}
	cmd.setCfgRegionale(cfgBase);
	//imposto la lista dei comuni del responsabile nel command
	List<Responsabilicomuni> comResp = this.comuniassociatiService.checkComuniAbilitatiPerResponsabile();
	cmd.setComuniResponsabile(comResp);
	List<StarConfigOneri> cfgPagamenti = this.starConfigOneriService.findConfigOneriLocali();
	cmd.setConfigComuni(cfgPagamenti);
	if (comResp.size() > 0) {
	    //per ciascun comune del gruppo viene inizializzato un'oggetto StarConfigOneri vuoto se non è presente nessuna configurazione già memorizzata
	    for (Responsabilicomuni cr : comResp) {
		StarConfigOneri co = cmd.getCfgComune(cr.getComune().getCodicecomune());
		if (co == null) {
		    co = new StarConfigOneri();
		    Comuni com = new Comuni();
		    com.setCodicecomune(cr.getComune().getCodicecomune());
		    co.setComune(com);
		    cmd.addConfigComune(co);
		}
	    }
	}
	//se specificato il codice comune dall'utente lo imposto come comune corrente e ricarico la configurazione per il comune richiesto
	/*
	if (cmd.getCfgOneri() != null && cmd.getCfgOneri().getComune() != null) {
	    String codCom = cmd.getCfgOneri().getComune().getCodicecomune();
	    //if (StringUtils.isNotEmpty(codCom)) {
	    Comuni comuneCorrente = new Comuni(codCom);
	    cmd.setComuneLocalizzazione(comuneCorrente);
	    //}
	}
	*/
	if (ORMHelper.isConsoleRegionale()) {
	    cmd.setCfgOneri(cmd.getCfgRegionale());
	} else if (cmd.getComuniResponsabile().size() > 0) {
	    if (cmd.getComuniResponsabile().size() > 1) {
		Comuni comLoc = cmd.getComuneLocalizzazione();
		if (comLoc == null) {
		    comLoc = new Comuni();
		    cmd.setComuneLocalizzazione(comLoc);
		}
		cmd.setCfgOneri(cmd.getCfgComune(comLoc.getCodicecomune()));
	    } else {
		cmd.setCfgOneri(cmd.getCfgComune());
	    }
	}
    }

    private void fixMergeStarConfigOneriProperty(StarConfigOneri sco) {

	if (sco != null) {
	    sco.getId().setIdcomune(ORMHelper.getIdente());
	    if (sco.getComune() != null && StringUtils.isBlank(sco.getComune().getCodicecomune())) {
		sco.setComune(null);
	    } else {
		Comuni com = comuniService.findById(sco.getComune().getCodicecomune());
		if (com != null) {
		    sco.setComune(com);
		}
	    }
	}
    }

    @Override
    protected void fixMergeEntityProperty(Tipimodalitapagamento entity) {

    }

    @Override
    protected void fixRenderEntityProperty(Tipimodalitapagamento entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}