package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Configurazioneutente;
import it.gruppoinit.pal.gp.core.domain.ConfigurazioneutenteId;
import it.gruppoinit.pal.gp.core.domain.FileUpload;
import it.gruppoinit.pal.gp.core.domain.NotificheAusl;
import it.gruppoinit.pal.gp.core.domain.NotificheAuslId;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.security.LoggedUser;
import it.gruppoinit.pal.gp.core.service.ConfigurazioneutenteService;
import it.gruppoinit.pal.gp.core.service.NotificheAuslService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliService;

import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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
@SessionAttributes(value = { "notificheAusl", "file", "updateDati" })
public class NotificheAuslController extends BaseController<NotificheAusl> {

    private static final Logger log = LoggerFactory.getLogger(NotificheAuslController.class);
    @Autowired
    private NotificheAuslService notificheAuslService;
    @Autowired
    private ResponsabiliService responsabiliService;
    @Autowired
    private ConfigurazioneutenteService configurazioneutenteService;

    @RequestMapping
    public ModelMap list(@RequestParam("filterRagSoc") String filterRagSoc, @RequestParam("filterIndirizzo") String filterIndirizzo,
	    HttpServletRequest request, HttpServletResponse response) {

	// §§§BEGIN§§§
	List<NotificheAusl> notificheAuslList = notificheAuslService.findAll(null, null);
	ModelMap model = new ModelMap(notificheAuslList);
	boolean export = createJMesaExport(request, response, notificheAuslList);
	if (export)
	    return null;
	model.addAttribute("notificheAuslList", notificheAuslList);
	model.addAttribute("filterIndirizzo", filterIndirizzo);
	model.addAttribute("filterRagSoc", filterRagSoc);
	return model;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String delete(@ModelAttribute("notificheAusl") NotificheAusl notificheAusl, BindingResult result, SessionStatus status) {

	// §§§BEGIN§§§
	NotificheAusl objToDelete = notificheAuslService.findById(notificheAusl.getId());
	try {
	    notificheAuslService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderEntityProperty(notificheAusl);
	    return "notificheausl/form";
	}
	status.setComplete();
	// §§§END§§§
	return "redirect:list.htm?filterIndirizzo=&filterRagSoc=";
    }

    @RequestMapping
    public String insert(@ModelAttribute("notificheAusl") NotificheAusl notificheAusl, BindingResult result, SessionStatus status) {

	// §§§BEGIN§§§
	// se necessario inserire parte di codice che deve ricercare altri attributi da settare
	// all'oggetto del dominio
	fixMergeEntityProperty(notificheAusl);
	try {
	    notificheAuslService.insert(notificheAusl);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, notificheAusl, e);
	    fixRenderEntityProperty(notificheAusl);
	    return "notificheausl/form";
	}
	status.setComplete();
	// §§§END§§§
	return "redirect:view.htm?id=" + notificheAusl.getId().getCodNotifica() + "&status_msg=01";
    }

    @RequestMapping
    public String update(@ModelAttribute("notificheAusl") NotificheAusl notificheAusl, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	// §§§BEGIN§§§
	// se necessario inserire parte di codice che deve ricercare altri attributi da settare
	// all'oggetto del dominio
	fixMergeEntityProperty(notificheAusl);
	try {
	    notificheAuslService.update(notificheAusl);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, notificheAusl, e);
	    fixRenderEntityProperty(notificheAusl);
	    return "notificheausl/form";
	}
	status.setComplete();
	// §§§END§§§
	return "redirect:view.htm?id=" + notificheAusl.getId().getCodNotifica() + "&status_msg=02";
    }

    @RequestMapping
    public String create(Model model) {

	// §§§BEGIN§§§
	NotificheAusl notificheAusl = new NotificheAusl();
	NotificheAuslId notificheAuslId = new NotificheAuslId();
	notificheAuslId.setIdcomune(ORMHelper.getIdcomune());
	notificheAusl.setId(notificheAuslId);
	fixRenderEntityProperty(notificheAusl);
	model.addAttribute("notificheAusl", notificheAusl);
	setPageAttributes(model);
	// §§§END§§§
	return "notificheausl/form";
    }

    @RequestMapping
    public String view(@RequestParam("id") String codiceNotifica, Model model, HttpServletRequest request) {

	// §§§BEGIN§§§
	NotificheAuslId id = new NotificheAuslId();
	id.setCodNotifica(codiceNotifica);
	id.setIdcomune(ORMHelper.getIdcomune());
	NotificheAusl notificheAusl = notificheAuslService.findById(id);
	fixRenderEntityProperty(notificheAusl);
	model.addAttribute("notificheAusl", notificheAusl);
	setPageAttributes(model);
	// §§§END§§§
	return "notificheausl/form";
    }

    @RequestMapping
    public String createImport(Model model, HttpServletRequest request, HttpServletResponse response) {

	// §§§BEGIN§§§
	// RECUPERO IMPOSTAZIONE UTENTE//
	LoggedUser userDetail = (LoggedUser) getCurrentlyAuthenticatedUser();
	PkId idResponsabile = new PkId(userDetail.getCodiceResponsabile());
	Responsabili responsabile = responsabiliService.findById(idResponsabile);
	ConfigurazioneutenteId confUteId = new ConfigurazioneutenteId(responsabile.getId().getCodice(),
		WebConstants.CONF_UTENTE_UPDATE_NOTIFICHE_AUSL);
	Configurazioneutente configurazioneutente = configurazioneutenteService.findById(confUteId);
	String updateDati = "";
	if (configurazioneutente == null) {
	    updateDati = "false";
	    // .. inserisco i valori di default
	    configurazioneutente = new Configurazioneutente();
	    configurazioneutente.setId(confUteId);
	    configurazioneutente.setResponsabile(responsabile);
	    configurazioneutente.setValore(updateDati);
	    configurazioneutenteService.insert(configurazioneutente);
	} else {
	    if (configurazioneutente.getValore().equals("true")) {
		updateDati = "true";
	    } else {
		updateDati = "false";
	    }
	}
	model.addAttribute("updateDati", updateDati);
	model.addAttribute("file", new FileUpload());
	// §§§END§§§
	return "notificheausl/importExcel";
    }

    @RequestMapping
    public String uploadExcel(Model model, @ModelAttribute("file") FileUpload file, BindingResult result, SessionStatus status,
	    HttpServletRequest request, HttpServletResponse response) {

	// §§§BEGIN§§§
	// RECUPERO IMPOSTAZIONE UTENTE//
	LoggedUser userDetail = (LoggedUser) getCurrentlyAuthenticatedUser();
	PkId idResponsabile = new PkId(userDetail.getCodiceResponsabile());
	Responsabili responsabile = responsabiliService.findById(idResponsabile);
	ConfigurazioneutenteId confUteId = new ConfigurazioneutenteId(responsabile.getId().getCodice(),
		WebConstants.CONF_UTENTE_UPDATE_NOTIFICHE_AUSL);
	Configurazioneutente configurazioneutente = configurazioneutenteService.findById(confUteId);
	String updateDati = "";
	if (configurazioneutente == null) {
	    updateDati = "false";
	    // .. inserisco i valori di default
	    configurazioneutente = new Configurazioneutente();
	    configurazioneutente.setId(confUteId);
	    configurazioneutente.setResponsabile(responsabile);
	    configurazioneutente.setValore(updateDati);
	    configurazioneutenteService.insert(configurazioneutente);
	} else {
	    updateDati = configurazioneutente.getValore();
	}
	List<NotificheAusl> listImport = new ArrayList<NotificheAusl>();
	try {
	    InputStream inp = file.getFile().getInputStream();
	    HSSFWorkbook wb = new HSSFWorkbook(inp);
	    HSSFSheet sheet = wb.getSheetAt(0); // first sheet
	    int numeroRighe = sheet.getLastRowNum();
	    numeroRighe++;
	    for (int i = 1; i < numeroRighe; i++) {
		NotificheAusl notificheAusl = new NotificheAusl();
		HSSFRow row = sheet.getRow(i); // first row
		HSSFCell cell = row.getCell(11); // ID
		NotificheAuslId id = new NotificheAuslId(cell.getRichStringCellValue().getString());
		notificheAusl.setId(id);
		HSSFCell cell2 = row.getCell(0); // ID_DITTA
		// try-catch per controllare che i valori inseriti siano valori numerici
		try {
		    BigDecimal idDitta = new BigDecimal(cell2.getNumericCellValue());
		    notificheAusl.setIdDitta(idDitta);
		} catch (Exception e) {
		    notificheAusl.setIdDitta(null);
		}
		HSSFCell cell3 = row.getCell(1); // Ragione sociale
		// try-catch per controllare che i valori inseriti siano Stringhe
		try {
		    String ragSoc = cell3.getRichStringCellValue().getString();
		    notificheAusl.setRagSoc(ragSoc);
		} catch (Exception e) {
		    notificheAusl.setRagSoc("");
		}
		HSSFCell cell4 = row.getCell(2); // Comune
		// try-catch per controllare che i valori inseriti siano Stringhe
		try {
		    String comune = cell4.getRichStringCellValue().getString();
		    notificheAusl.setComune(comune);
		} catch (Exception e) {
		    notificheAusl.setComune("");
		}
		// try-catch per controllare che i valori inseriti siano Stringhe
		HSSFCell cell5 = row.getCell(3); // Localita
		try {
		    String localita = cell5.getRichStringCellValue().getString();
		    notificheAusl.setLocalita(localita);
		} catch (Exception e) {
		    notificheAusl.setLocalita("");
		}
		// try-catch per controllare che i valori inseriti siano Stringhe
		HSSFCell cell6 = row.getCell(4); // Indirizzo
		try {
		    String indirizzo = cell6.getRichStringCellValue().getString();
		    notificheAusl.setIndirizzo(indirizzo);
		} catch (Exception e) {
		    notificheAusl.setLocalita("");
		}
		// try-catch per controllare che i valori inseriti siano Stringhe
		HSSFCell cell7 = row.getCell(5); // Descrcomparto
		try {
		    String descComparto = cell7.getRichStringCellValue().getString();
		    notificheAusl.setDescComparto(descComparto);
		} catch (Exception e) {
		    notificheAusl.setDescComparto("");
		}
		// try-catch per controllare che i valori inseriti siano Stringhe
		HSSFCell cell8 = row.getCell(6); // DESCRATT
		try {
		    String descAtt = cell8.getRichStringCellValue().getString();
		    notificheAusl.setDescAtt(descAtt);
		} catch (Exception e) {
		    notificheAusl.setDescAtt("");
		}
		// try-catch per controllare che i valori inseriti siano Stringhe
		HSSFCell cell9 = row.getCell(7); // RAPLEG
		try {
		    String rapLeg = cell9.getRichStringCellValue().getString();
		    notificheAusl.setRapLeg(rapLeg);
		} catch (Exception e) {
		    notificheAusl.setRapLeg("");
		}
		// try-catch per controllare che i valori inseriti siano Stringhe
		HSSFCell cell10 = row.getCell(8); // COMUNE_LEGALE
		try {
		    String comuneLegale = cell10.getRichStringCellValue().getString();
		    notificheAusl.setComuneLegale(comuneLegale);
		} catch (Exception e) {
		    notificheAusl.setComuneLegale("");
		}
		// try-catch per controllare che i valori inseriti siano Stringhe
		HSSFCell cell11 = row.getCell(9); // LOCALITA_LEGALE
		try {
		    String localitaLegale = cell11.getRichStringCellValue().getString();
		    notificheAusl.setLocalitaLegale(localitaLegale);
		} catch (Exception e) {
		    notificheAusl.setLocalitaLegale("");
		}
		// try-catch per controllare che i valori inseriti siano Stringhe
		HSSFCell cell12 = row.getCell(10); // INDIRIZZO_LEGALE
		try {
		    String indirizzoLegale = cell12.getRichStringCellValue().getString();
		    notificheAusl.setIndirizzoLegale(indirizzoLegale);
		} catch (Exception e) {
		    notificheAusl.setIndirizzoLegale("");
		}
		// try-catch per controllare che i valori inseriti siano Short
		HSSFCell cell13 = row.getCell(12); // ANNOREG
		try {
		    Short annoReg = ((Double) cell13.getNumericCellValue()).shortValue();
		    notificheAusl.setAnnoReg(annoReg);
		} catch (Exception e) {
		    notificheAusl.setAnnoReg(null);
		}
		// try-catch per controllare che i valori inseriti siano Stringhe
		HSSFCell cell14 = row.getCell(13); // cod_tipoaut
		try {
		    String codTipoaut = cell14.getRichStringCellValue().getString();
		    notificheAusl.setCodTipoaut(codTipoaut);
		} catch (Exception e) {
		    notificheAusl.setCodTipoaut("");
		}
		// try-catch per controllare che i valori inseriti siano valori numerici
		HSSFCell cell15 = row.getCell(14); // ID_AUT
		try {
		    BigDecimal idAut = new BigDecimal(cell15.getNumericCellValue());
		    notificheAusl.setIdAut(idAut);
		} catch (Exception e) {
		    notificheAusl.setIdAut(null);
		}
		// try-catch per controllare che i valori inseriti siano Stringhe
		HSSFCell cell16 = row.getCell(15); // TIPO_NOTIFICA
		try {
		    String tipoNotifica = cell16.getRichStringCellValue().getString();
		    notificheAusl.setTipoNotifica(tipoNotifica);
		} catch (Exception e) {
		    notificheAusl.setTipoNotifica("");
		}
		// try-catch per controllare che i valori inseriti siano DATA
		HSSFCell cell17 = row.getCell(16); // DATAPERV
		try {
		    DateFormat myDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		    Date myDate = null;
		    try {
			myDate = myDateFormat.parse(cell17.getRichStringCellValue().getString());
		    } catch (ParseException e) {
			e.printStackTrace();
			throw new RuntimeException("ERRORE: non è possibile trasformare la data");
		    }
		    notificheAusl.setDataPerv(myDate);
		} catch (Exception e) {
		    notificheAusl.setDataPerv(null);
		}
		// try-catch per controllare che i valori inseriti siano Stringhe
		HSSFCell cell18 = row.getCell(17); // PROT_SIAN
		try {
		    String protSian = cell18.getRichStringCellValue().getString();
		    notificheAusl.setProtSian(protSian);
		} catch (Exception e) {
		    notificheAusl.setProtSian("");
		}
		// try-catch per controllare che i valori inseriti siano Stringhe
		HSSFCell cell19 = row.getCell(18); // DATA_SIAN
		try {
		    DateFormat myDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		    Date myDate = null;
		    try {
			myDate = myDateFormat.parse(cell19.getRichStringCellValue().getString());
		    } catch (ParseException e) {
			e.printStackTrace();
			throw new RuntimeException("ERRORE: non è possibile trasformare la data");
		    }
		    notificheAusl.setDataSian(myDate);
		} catch (Exception e) {
		    notificheAusl.setDataSian(null);
		}
		// try-catch per controllare che i valori inseriti siano valori numerici
		HSSFCell cell20 = row.getCell(19); // MesePervenuta
		try {
		    Short mesePervenuta = ((Double) cell20.getNumericCellValue()).shortValue();
		    notificheAusl.setMesePervenuta(mesePervenuta);
		} catch (Exception e) {
		    notificheAusl.setMesePervenuta(null);
		}
		// try-catch per controllare che i valori inseriti siano valori numerici
		HSSFCell cell21 = row.getCell(20); // AnnoPervenuta
		try {
		    Short annoPervenuta = ((Double) cell21.getNumericCellValue()).shortValue();
		    notificheAusl.setAnnoPervenuta(annoPervenuta);
		} catch (Exception e) {
		    notificheAusl.setAnnoPervenuta(null);
		}
		listImport.add(notificheAusl);
	    }
	    notificheAuslService.updateImportNotifiche(listImport, updateDati);
	} catch (Exception e) {
	    log.error("uploadExcel: {}", e.getMessage());
	    String errorString = "Errore nel upload del file excel: Controllare che il file importato sia corretto.";
	    model.addAttribute("file", new FileUpload());
	    model.addAttribute("errorString", errorString);
	    if (configurazioneutente.getValore().equals("true")) {
		updateDati = "true";
	    } else {
		updateDati = "false";
	    }
	    model.addAttribute("updateDati", updateDati);
	    return "notificheausl/importExcel";
	}
	// §§§END§§§
	return "redirect:list.htm?filterIndirizzo=&filterRagSoc=";
    }

    @Override
    protected void fixMergeEntityProperty(NotificheAusl entity) {

    }

    @Override
    protected void fixRenderEntityProperty(NotificheAusl entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
