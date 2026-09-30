package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.FileUpload;
import it.gruppoinit.pal.gp.core.domain.GruppiEndoprocedimentiD;
import it.gruppoinit.pal.gp.core.domain.GruppiEndoprocedimentiT;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.Tipimovimento;
import it.gruppoinit.pal.gp.core.domain.helper.CodiceDescrizioneBean;
import it.gruppoinit.pal.gp.core.security.LoggedUser;
import it.gruppoinit.pal.gp.core.service.GruppiEndoprocedimentiDService;
import it.gruppoinit.pal.gp.core.service.GruppiEndoprocedimentiTService;
import it.gruppoinit.pal.gp.core.service.InventarioprocedimentiService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;
import it.gruppoinit.pal.gp.core.service.helper.FlashMessages;

import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
@SessionAttributes({ "gruppiEndoprocedimentiT", "gruppiEndoprocedimentiTCommand", "file" })
public class GruppiEndoprocedimentiTController extends BaseController<GruppiEndoprocedimentiT> {

    private static final Logger log = LoggerFactory.getLogger(GruppiEndoprocedimentiTController.class);
    @Autowired
    private GruppiEndoprocedimentiTService gruppiEndoprocedimentiTService;
    @Autowired
    private GruppiEndoprocedimentiDService gruppiEndoprocedimentiDService;
    @Autowired
    private InventarioprocedimentiService inventarioprocedimentiService;
    @Autowired
    private SoftwareService softwareService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	List<GruppiEndoprocedimentiT> dataList = gruppiEndoprocedimentiTService.findAll(null, null);
	ModelMap model = new ModelMap(dataList);
	boolean export = createJMesaExport(request, response, dataList);
	if (export) {
	    return null;
	}
	model.addAttribute("dataList", dataList);
	return model;
    }

    @RequestMapping
    public String create(Model model) {

	GruppiendoprocedimentiTCommand gruppiEndoprocedimentiTCommand = new GruppiendoprocedimentiTCommand();
	gruppiEndoprocedimentiTCommand.setDisplayMode(GruppiendoprocedimentiTCommand.NEW);
	GruppiEndoprocedimentiT gruppiEndoprocedimentiT = new GruppiEndoprocedimentiT();
	gruppiEndoprocedimentiT.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
	gruppiEndoprocedimentiT.setNumEndoWarning(0);
	fixRenderEntityProperty(gruppiEndoprocedimentiT);
	gruppiEndoprocedimentiTCommand.setEntity(gruppiEndoprocedimentiT);
	model.addAttribute("gruppiEndoprocedimentiTCommand", gruppiEndoprocedimentiTCommand);
	setPageAttributes(model);
	return "gruppiendoprocedimentit/form";
    }

    @RequestMapping
    public String insert(Model model,
	    @ModelAttribute("gruppiEndoprocedimentiTCommand") GruppiendoprocedimentiTCommand gruppiEndoprocedimentiTCommand, BindingResult result,
	    SessionStatus status) {

	fixMergeEntityProperty(gruppiEndoprocedimentiTCommand.getEntity());
	try {
	    gruppiEndoprocedimentiTService.insert(gruppiEndoprocedimentiTCommand.getEntity());
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, gruppiEndoprocedimentiTCommand, true, e);
	    fixRenderEntityProperty(gruppiEndoprocedimentiTCommand.getEntity());
	    return "gruppiendoprocedimentit/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + gruppiEndoprocedimentiTCommand.getEntity().getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	GruppiendoprocedimentiTCommand gruppiEndoprocedimentiTCommand = new GruppiendoprocedimentiTCommand();
	gruppiEndoprocedimentiTCommand.setDisplayMode(GruppiendoprocedimentiTCommand.VIEW);
	PkId id = new PkId(codice);
	GruppiEndoprocedimentiT gruppiEndoprocedimentiT = gruppiEndoprocedimentiTService.findById(id);
	fixRenderEntityProperty(gruppiEndoprocedimentiT);
	gruppiEndoprocedimentiTCommand.setEntity(gruppiEndoprocedimentiT);
	model.addAttribute("gruppiEndoprocedimentiTCommand", gruppiEndoprocedimentiTCommand);
	setPageAttributes(model);
	return "gruppiendoprocedimentit/form";
    }

    @RequestMapping
    public String update(Model model,
	    @ModelAttribute("gruppiEndoprocedimentiTCommand") GruppiendoprocedimentiTCommand gruppiEndoprocedimentiTCommand, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	fixMergeEntityProperty(gruppiEndoprocedimentiTCommand.getEntity());
	try {
	    gruppiEndoprocedimentiTService.update(gruppiEndoprocedimentiTCommand.getEntity());
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, gruppiEndoprocedimentiTCommand, true, e);
	    fixRenderEntityProperty(gruppiEndoprocedimentiTCommand.getEntity());
	    return "gruppiendoprocedimentit/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + gruppiEndoprocedimentiTCommand.getEntity().getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String delete(Model model,
	    @ModelAttribute("gruppiEndoprocedimentiTCommand") GruppiendoprocedimentiTCommand gruppiEndoprocedimentiTCommand, BindingResult result,
	    SessionStatus status) {

	GruppiEndoprocedimentiT objToDelete = gruppiEndoprocedimentiTService.findById(gruppiEndoprocedimentiTCommand.getEntity().getId());
	try {
	    gruppiEndoprocedimentiTService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, gruppiEndoprocedimentiTCommand, true, e);
	    fixRenderEntityProperty(gruppiEndoprocedimentiTCommand.getEntity());
	    return "gruppiendoprocedimentit/form";
	}
	status.setComplete();
	return "redirect:list.htm";
    }

    @RequestMapping
    public String ajaxDettaglioGruppi(@RequestParam("codice") Integer codiceGruppoT, Model model, HttpServletRequest request,
	    HttpServletResponse response) throws Exception {

	List<GruppiEndoprocedimentiD> gds = gruppiEndoprocedimentiDService.findByGruppiT(codiceGruppoT);
	model.addAttribute("gds", gds);
	return "gruppiendoprocedimentit/ajaxDettaglioGruppi";
    }

    @RequestMapping
    public void ajaxEliminaEndo(@RequestParam("idRiga") Integer idRiga, Model model, HttpServletRequest request, HttpServletResponse response)
	    throws Exception {

	String result = "OK";
	try {
	    GruppiEndoprocedimentiD entity = gruppiEndoprocedimentiDService.findById(new PkId(idRiga));
	    gruppiEndoprocedimentiDService.delete(entity);
	} catch (Exception e) {
	    log.error("{}", e);
	    result = "Si e' verificato un errore nella cancellazione del dato. (dettaglio:" + e.getMessage() + ")";
	}
	response.getOutputStream().write(result.getBytes());
    }

    @RequestMapping
    public void assegnaEndo(@RequestParam("codiceEndo") Integer codiceEndo, @RequestParam("codiceGruppo") Integer codiceGruppo, Model model,
	    HttpServletRequest request, HttpServletResponse response) throws Exception {

	String result = "OK";
	try {
	    Inventarioprocedimenti endo = inventarioprocedimentiService.findById(new PkId(codiceEndo));
	    List<GruppiEndoprocedimentiD> list = gruppiEndoprocedimentiDService.findByCodiceInventario(codiceEndo);
	    if (list.size() > 0) {
		result = "Attenzione!! L'endoprocedimento " + endo.getProcedimento() + "(" + endo.getId() + ")"
			+ " e' gia' usato dai seguenti gruppi <ul>";
		for (GruppiEndoprocedimentiD ged : list) {
		    result += "<li>" + ged.getGruppiEndoprocedimentiT().getDescrizione() + "</li>";
		}
		result += "</ul>";
	    } else {
		GruppiEndoprocedimentiT g = gruppiEndoprocedimentiTService.findById(new PkId(codiceGruppo));
		GruppiEndoprocedimentiD entity = new GruppiEndoprocedimentiD();
		entity.setGruppiEndoprocedimentiT(g);
		entity.setInventarioprocedimento(endo);
		gruppiEndoprocedimentiDService.insert(entity);
	    }
	} catch (Exception e) {
	    log.error("{}", e);
	    result = "Si e' verificato un errore nella cancellazione del dato. (dettaglio:" + e.getMessage() + ")";
	}
	response.getOutputStream().write(result.getBytes());
    }

    @Override
    protected void setPageAttributes(Model model) {

    }

    @Override
    protected void fixMergeEntityProperty(GruppiEndoprocedimentiT entity) {

    }

    @Override
    protected void fixRenderEntityProperty(GruppiEndoprocedimentiT entity) {

	if (entity != null) {
	    if (entity.getSoftware() == null) {
		Software s = softwareService.findById(ORMHelper.getSoftware());
		entity.setSoftware(s);
	    }
	    if (entity.getTipimovimento() == null) {
		entity.setTipimovimento(new Tipimovimento());
	    }
	}
    }

    @RequestMapping
    public String createImport(Model model, HttpServletRequest request, HttpServletResponse response) {

	LoggedUser userDetail = (LoggedUser) getCurrentlyAuthenticatedUser();
	model.addAttribute("file", new FileUpload());
	return "gruppiendoprocedimentit/importExcel";
    }

    @RequestMapping
    public String uploadExcel(Model model, @ModelAttribute("file") FileUpload file, BindingResult result, SessionStatus status,
	    HttpServletRequest request, HttpServletResponse response) {

	LoggedUser userDetail = (LoggedUser) getCurrentlyAuthenticatedUser();
	try {
	    List<CodiceDescrizioneBean> resultato = gruppiEndoprocedimentiTService.elaboraFileExcel(file.getFile().getInputStream());
	    if (resultato.size() > 0) {
		String errorMessage = "Si sono verificati i seguenti errori: <ul>";
		for (CodiceDescrizioneBean cdb : resultato) {
		    errorMessage += "<li>" + cdb.getDescrizione() + " (" + cdb.getCodice() + ")</li>";
		}
		errorMessage += "</ul>";
		FlashMessages.getWarnings().add(errorMessage);
	    }
	} catch (Exception e) {
	    FlashMessages.getWarnings().add(e.getMessage());
	    return "redirect:list.htm";
	}
	return "redirect:list.htm";
    }
}
