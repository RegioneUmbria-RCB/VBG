package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.web.OggettiFileSystemError;
import it.gruppoinit.pal.gp.core.domain.web.OggettiFileSystemStatusBean;
import it.gruppoinit.pal.gp.core.service.OggettiFileSystemService;
import it.gruppoinit.pal.gp.core.service.VerticalizzazioniService;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.MessageFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class OggettiFileSystemController extends BaseController<Oggetti> {

    private static final Logger log = LoggerFactory.getLogger(OggettiFileSystemController.class);
    @Autowired
    private OggettiFileSystemService fsService;
    @Autowired
    private VerticalizzazioniService verticalizzazioniService;

    @Override
    protected void setPageAttributes(Model model) {

    }

    @Override
    protected void fixMergeEntityProperty(Oggetti entity) {

    }

    @Override
    protected void fixRenderEntityProperty(Oggetti entity) {

    }

    @RequestMapping
    public String prepareLaunch(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {

	// §§§BEGIN§§§
	checkAccessoFunzionalitaAmministrative(request, response);
	boolean isVertFileSystem = verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_FILESYSTEM, ORMHelper.getSoftware());
	if (isVertFileSystem) {
	    model.addAttribute("numFileToFileDocuments", fsService.countDocumentsAtRoot());
	    model.addAttribute("numBlobToFileDocuments", fsService.countDocumentsInBlob());
	    model.addAttribute("rootPath", fsService.getFileRepositoryPath());
	    model.addAttribute("setBlobNull", true);
	    //model.addAttribute("idcomunealias", ORMHelper.getIdcomuneAlias());
	} else {
	    model.addAttribute("errorString", "Vericalizzazione file system non attiva.");
	}
	//model.addAllAttributes(request.getParameterMap());
	return "oggettifilesystem/launch";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public void spostaBlobSuFileSystem(@RequestParam("setBlobNull") boolean setBlobNull, HttpServletRequest request, HttpServletResponse response)
	    throws Exception {

	// §§§BEGIN§§§
	checkAccessoFunzionalitaAmministrative(request, response);
	boolean isVertFileSystem = verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_FILESYSTEM, ORMHelper.getSoftware());
	int updatedCount = 0;
	String outMsg = "Ottimizzazione file system completata.\\r\\n Spostati {0} files in sottocartelle";
	if (isVertFileSystem) {
	    updatedCount = fsService.spostaBlobSuFileSystem(setBlobNull);
	    outMsg = MessageFormat.format(outMsg, updatedCount);
	    //model.addAttribute("updatedCount", updatedCount);
	} else {
	    //result.reject("errors.user.notauthorized", "Vericalizzazione file system non attiva.");
	    //model.addAttribute("errorMsg", "Impossibile procedere.\r\n Vericalizzazione file system non attiva.");
	    outMsg = "Impossibile procedere.\r\nVericalizzazione file system non attiva.";
	}
	//return "oggettiFileSystem/view";
	response.setContentType("application/json");
	PrintWriter pw = response.getWriter();
	pw.write("{msg: \"" + outMsg + "\"}");
	pw.flush();
	pw.close();
	// §§§END§§§
    }

    @RequestMapping
    public void ottimizzaFileSystem(HttpServletRequest request, HttpServletResponse response) throws Exception {

	// §§§BEGIN§§§
	checkAccessoFunzionalitaAmministrative(request, response);
	boolean isVertFileSystem = verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_FILESYSTEM, ORMHelper.getSoftware());
	int updatedCount = 0;
	String outMsg = "Ottimizzazione file system completata.\\r\\n Spostati {0} files in sottocartelle";
	if (isVertFileSystem) {
	    updatedCount = fsService.ottimizzaFileSystem();
	    outMsg = MessageFormat.format(outMsg, updatedCount);
	    //model.addAttribute("updatedCount", updatedCount);
	} else {
	    //result.reject("errors.user.notauthorized", "Vericalizzazione file system non attiva.");
	    //model.addAttribute("errorMsg", "Impossibile procedere.\r\n Vericalizzazione file system non attiva.");
	    outMsg = "Impossibile procedere.\r\nVericalizzazione file system non attiva.";
	}
	//return "oggettiFileSystem/view";
	response.setContentType("application/json");
	PrintWriter pw = response.getWriter();
	pw.write("{msg: \"" + outMsg + "\"}");
	pw.flush();
	pw.close();
	// §§§END§§§
    }

    @RequestMapping
    public void refreshStatus(HttpServletRequest request, HttpServletResponse response) throws Exception {

	// §§§BEGIN§§§
	OggettiFileSystemStatusBean status = fsService.getStatus();
	int totObj = 0;
	int handledObj = 0;
	boolean running = false;
	if (status != null) {
	    //responseText += status.getExecutedTasks();
	    totObj = status.getCountTotal();
	    handledObj = status.getCountHandled();
	    running = status.isRunning();
	}
	StringBuilder sbOut = new StringBuilder("{totalCount:").append(totObj);
	sbOut.append(", handledCount: ").append(handledObj);
	sbOut.append(", running: ").append(running);
	sbOut.append("}");
	response.setContentType("application/json");
	response.setContentLength(sbOut.length());
	PrintWriter pw = response.getWriter();
	pw.write(sbOut.toString());
	pw.flush();
	pw.close();
	// §§§END§§§
    }

    @RequestMapping
    public String displayResults(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {

	// §§§BEGIN§§§
	checkAccessoFunzionalitaAmministrative(request, response);
	OggettiFileSystemStatusBean status = fsService.getStatus();
	model.addAttribute("status", status);
	//model.addAttribute("errors",status.getErrors());
	//setPageAttributes(model);
	return "oggettifilesystem/output";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public void exportErrors(HttpServletResponse response) {

	// §§§BEGIN§§§
	HSSFWorkbook workbook = new HSSFWorkbook();
	HSSFSheet sheet = workbook.createSheet("Errori");
	OggettiFileSystemStatusBean status = fsService.getStatus();
	HSSFRow row = null;
	HSSFCell cell = null;
	/*
	HSSFCellStyle titleStyle = workbook.createCellStyle();
	titleStyle.setUserStyleName("Titolo");
	HSSFFont titleFont = workbook.createFont();
	titleStyle.setFont(titleFont);
	*/
	HSSFCellStyle headerStyle = null;
	HSSFCellStyle defaultStyle = null;
	HSSFCellStyle fatalStyle = null;
	if (status != null && status.getErrors().size() > 0) {
	    int rowIx = 1;
	    List<OggettiFileSystemError> errors = status.getErrors();
	    //intestazioni
	    headerStyle = workbook.createCellStyle();
	    HSSFFont headerFont = workbook.createFont();
	    headerFont.setFontName(HSSFFont.FONT_ARIAL);
	    headerFont.setFontHeightInPoints((short) 12);
	    headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
	    headerStyle.setFont(headerFont);
	    defaultStyle = workbook.createCellStyle();
	    HSSFFont defaultFont = workbook.createFont();
	    defaultFont.setFontName(HSSFFont.FONT_ARIAL);
	    defaultFont.setFontHeightInPoints((short) 10);
	    defaultFont.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
	    defaultStyle.setFont(defaultFont);
	    HSSFRow titleRow = sheet.createRow(rowIx);
	    titleRow.setRowStyle(headerStyle);
	    cell = titleRow.createCell(0);
	    cell.setCellStyle(headerStyle);
	    cell.setCellValue(new HSSFRichTextString("Codice oggetto"));
	    cell = titleRow.createCell(1);
	    cell.setCellStyle(headerStyle);
	    cell.setCellValue(new HSSFRichTextString("Nome file"));
	    cell = titleRow.createCell(2);
	    cell.setCellStyle(headerStyle);
	    cell.setCellValue(new HSSFRichTextString("Messaggio"));
	    cell = titleRow.createCell(3);
	    cell.setCellStyle(headerStyle);
	    cell.setCellValue(new HSSFRichTextString("Eccezione"));
	    for (OggettiFileSystemError error : errors) {
		HSSFCellStyle rowStyle = defaultStyle;
		row = sheet.createRow(++rowIx);
		row.setRowStyle(rowStyle);
		cell = row.createCell(0);
		cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		cell.setCellStyle(rowStyle);
		cell.setCellValue(error.getCodiceOggetto() != null ? error.getCodiceOggetto().doubleValue() : null);
		cell = row.createCell(1);
		cell.setCellStyle(rowStyle);
		cell.setCellValue(error.getNomeFile() != null ? error.getNomeFile() : "");
		cell = row.createCell(2);
		cell.setCellStyle(rowStyle);
		String msg = cleanForXls(error.getErrorMessage());
		cell.setCellValue(msg);
		cell = row.createCell(3);
		cell.setCellStyle(rowStyle);
		Throwable rootExc = error.getException();
		String excMsg = rootExc != null ? rootExc.getClass().getName() + " \r\n" + rootExc.getMessage() : "";
		cell.setCellValue(cleanForXls(excMsg));
	    }
	    sheet.autoSizeColumn(0);
	    sheet.autoSizeColumn(1);
	    sheet.autoSizeColumn(2);
	    sheet.autoSizeColumn(3);
	} else {
	    //TODO nessun'errore o nessuna procedura eseguita
	    row = sheet.createRow(1);
	    row.setRowStyle(defaultStyle);
	    cell = row.createCell(0);
	    cell.setCellValue(new HSSFRichTextString(
		    "Nessun errore riscontrato durante l'esecuzione della procedura di ottimizzazione degli OGGETTI su filesystem."));
	}
	response.setContentType("application/vnd.ms-excel;charset=UTF-8");
	response.setHeader("Content-Disposition", "attachment;filename=\"errori-ottimizzazione-oggetti.xls\"");
	response.setDateHeader("Expires", (System.currentTimeMillis() + 1000));
	try {
	    OutputStream out = response.getOutputStream();
	    workbook.write(out);
	    out.flush();
	    out.close();
	} catch (IOException e) {
	    log.error("Errore nella scrittura del file Excel", e);
	}
	// §§§END§§§
    }

    private String cleanForXls(String input) {

	// §§§BEGIN§§§
	String output = input;
	if (input != null) {
	    output = input.replaceAll("[\r\n\t\f]", "");
	} else {
	    output = "";
	}
	return output;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }
}
