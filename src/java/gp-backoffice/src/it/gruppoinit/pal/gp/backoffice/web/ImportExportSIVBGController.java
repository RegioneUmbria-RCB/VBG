package it.gruppoinit.pal.gp.backoffice.web;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.domain.web.ImportExportSIVBGCommand;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.ImportExportSIVBGService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

@Controller
@SessionAttributes(value = { "importexportsivbgcommand" })
public class ImportExportSIVBGController extends BaseController<String> {

    @Autowired
    private ImportExportSIVBGService importExportSIVBGService;
    @Autowired
    private VerticalizzazioniService verticalizzazioniService;
    @Autowired
    private SoftwareService softwareService;
    @Autowired(required = false)
    private CacheManager cacheManager;

    @RequestMapping
    public String createImport(Model model) {

	String servizio = ORMHelper.getSoftware();
	ImportExportSIVBGCommand command = new ImportExportSIVBGCommand();
	command.setDescrizioneServizioExport(softwareService.findById(servizio).getDescrizione());
	try {
	    String url = getUrlWSrepositoryRemoto();
	    importExportSIVBGService.getListaVersioni(url, command);
	} catch (Exception e) {
	    // TODO
	}
	model.addAttribute("importexportsivbgcommand", command);
	setPageAttributes(model);
	return "importexportsivbg/formImport";
    }

    @RequestMapping
    public String createExport(Model model) {

	Format formatter = new SimpleDateFormat("dd/MM/yyyy");
	String dateString = formatter.format(new Date());
	String servizio = ORMHelper.getSoftware();
	ImportExportSIVBGCommand command = new ImportExportSIVBGCommand();
	command.setDataExport(dateString);
	command.setDescrizioneServizioExport(softwareService.findById(servizio).getDescrizione());
	command.setNoteVersioneExport("");
	model.addAttribute("importexportsivbgcommand", command);
	model.addAttribute("tab", "tabexport_all");
	//////////////////////////
	setPageAttributes(model);
	return "importexportsivbg/formExport";
    }

    @RequestMapping
    public String createExportEndo(Model model) {

	Format formatter = new SimpleDateFormat("dd/MM/yyyy");
	String dateString = formatter.format(new Date());
	String servizio = ORMHelper.getSoftware();
	ImportExportSIVBGCommand command = new ImportExportSIVBGCommand();
	command.setDataExport(dateString);
	command.setDescrizioneServizioExport(softwareService.findById(servizio).getDescrizione());
	command.setNoteVersioneExport("");
	model.addAttribute("importexportsivbgcommand", command);
	model.addAttribute("tab", "tabexport_endo");
	setPageAttributes(model);
	return "importexportsivbg/formExport";
    }

    @RequestMapping
    public String exportSIVBG(Model model, @ModelAttribute("importexportsivbgcommand") ImportExportSIVBGCommand command, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	try {
	    String urlWSRepository = getUrlWSrepositoryRemoto();
	    if (StringUtils.isBlank(command.getDataExport()) || StringUtils.isBlank(command.getNoteVersioneExport())) {
		if (StringUtils.isBlank(command.getDataExport())) {
		    result.rejectValue("dataExport", "validator.notEmpty");
		}
		if (StringUtils.isBlank(command.getNoteVersioneExport())) {
		    result.rejectValue("noteVersioneExport", "validator.notEmpty");
		}
		setPageAttributes(model);
		model.addAttribute("tab", "tabexport_all");
		return "importexportsivbg/formExport";
	    }
	    importExportSIVBGService.exportSIVBG(urlWSRepository, command);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, command, e);
	    setPageAttributes(model);
	    model.addAttribute("tab", "tabexport_all");
	    return "importexportsivbg/formExport";
	}
	status.setComplete();
	return "redirect:createExport.htm?status_msg=01";
    }

    @RequestMapping
    public String exportSIVBGendo(Model model, @ModelAttribute("importexportsivbgcommand") ImportExportSIVBGCommand command, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	try {
	    // command.getIstanzeFilter().getInventarioprocedimenti().getId().setCodice(Integer.valueOf("20790"));
	    String urlWSRepository = getUrlWSrepositoryRemoto();
	    if (StringUtils.isBlank(command.getDataExport()) || StringUtils.isBlank(command.getNoteVersioneExport())
		    || command.getIstanzeFilter().getInventarioprocedimenti().getId().getCodice() == null) {
		if (StringUtils.isBlank(command.getDataExport())) {
		    result.rejectValue("dataExport", "validator.notEmpty");
		}
		if (StringUtils.isBlank(command.getNoteVersioneExport())) {
		    result.rejectValue("noteVersioneExport", "validator.notEmpty");
		}
		if (command.getIstanzeFilter().getInventarioprocedimenti().getId().getCodice() == null) {
		    result.rejectValue("istanzeFilter.inventarioprocedimenti.id.codice", "validator.notEmpty");
		}
		setPageAttributes(model);
		model.addAttribute("tab", "tabexport_endo");
		return "importexportsivbg/formExport";
	    }
	    importExportSIVBGService.exportSIVBGendo(urlWSRepository, command);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, command, e);
	    setPageAttributes(model);
	    model.addAttribute("tab", "tabexport_endo");
	    return "importexportsivbg/formExport";
	}
	status.setComplete();
	return "redirect:createExportEndo.htm?status_msg=01";
    }

    @RequestMapping
    public String importSIVBG(Model model, @ModelAttribute("importexportsivbgcommand") ImportExportSIVBGCommand command, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	try {
	    if (StringUtils.isBlank(command.getSceltaVers())) {
		result.reject("validator.notEmpty");
		setPageAttributes(model);
		return "importexportsivbg/formImport";
	    }
	    String urlWSRepository = getUrlWSrepositoryRemoto();
	    importExportSIVBGService.importSIVBG(urlWSRepository, command);
	    String[] cacheNames = cacheManager.getCacheNames();
	    if (cacheNames != null) {
		for (String nomeCache : cacheNames) {
		    Cache cache = cacheManager.getCache(nomeCache);
		    cache.removeAll();
		}
	    }
	    model.addAttribute("caches", cacheNames);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, command, e);
	    setPageAttributes(model);
	    return "importexportsivbg/formImport";
	}
	status.setComplete();
	return "redirect:createImport.htm?status_msg=01";
    }

    @Override
    protected void setPageAttributes(Model model) {

    }

    @Override
    protected void fixMergeEntityProperty(String entity) {

	// TODO Auto-generated method stub
    }

    @Override
    protected void fixRenderEntityProperty(String entity) {

	// TODO Auto-generated method stub
    }

    private String getUrlWSrepositoryRemoto() throws Exception {

	if (verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_IMPORT_EXPORT_SIVBG)) {
	    Verticalizzazioniparametri wsUrl = verticalizzazioniService
		    .getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_IMPORT_EXPORT_SIVBG, WebConstants.IMPORT_EXPORT_SIVBG_URLWS);
	    if (StringUtils.isNotEmpty(wsUrl.getValore())) {
		return wsUrl.getValore();
	    } else {
		throw new Exception("La URL del web service non è configurata");
	    }
	} else {
	    throw new Exception("La verticalizzazione di export non è attiva");
	}
    }
}
