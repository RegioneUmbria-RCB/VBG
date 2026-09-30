package it.gruppoinit.pdfutils.web;

import it.gruppoinit.pdfutils.domain.PDFMappature;
import it.gruppoinit.pdfutils.service.PDFMappingService;
import it.gruppoinit.pdfutils.web.helper.WorkSheetSaverImpl;
import it.gruppoinit.pdfutils.web.helper.WorksheetPDFMappature;
import it.gruppoinit.sigeprosecurity.ws.SigeproSecurityWebServiceClient;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jmesa.limit.ExportType;
import org.jmesa.model.AllItems;
import org.jmesa.model.TableModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PDFMappingController {

    @Autowired
    private PDFMappingService pdfMappingService;
    @Autowired
    private SigeproSecurityWebServiceClient securityWebServiceClient;

    @RequestMapping
    protected String list(@RequestParam("alias") String alias, HttpServletRequest request, HttpServletResponse response) throws Exception {

	// §§§BEGIN§§§
	request.getSession().setAttribute("alias", alias);
	String html = getTable(alias, request, response);
	if (html == null) {
	    return null;
	}
	request.setAttribute("configurazioni", html);
	request.setAttribute("alias", alias);
	return "pdfmapping/worksheetconfigurazione";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    protected String delete(@RequestParam("id") String id, HttpServletRequest request, HttpServletResponse response) throws Exception {

	// §§§BEGIN§§§
	String alias = (String) request.getSession().getAttribute("alias");
	pdfMappingService.delete(alias, id);
	return "redirect:list.htm?alias=" + alias;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    private String getTable(final String alias, HttpServletRequest request, HttpServletResponse response) {

	// §§§BEGIN§§§
	TableModel tableModel = new TableModel("worksheet", request, response);
	tableModel.setExportTypes(ExportType.CSV);
	tableModel.setItems(new AllItems() {

	    public Collection<?> getItems() {

		return pdfMappingService.findAll(alias, null, null);
	    }
	});
	WorkSheetSaverImpl worksheetSaver = new WorkSheetSaverImpl(pdfMappingService, alias);
	tableModel.saveWorksheet(worksheetSaver);
	tableModel.addRowObject(new PDFMappature());
	if (tableModel.isExporting()) {
	    WorksheetPDFMappature.setTableExportingProperties(tableModel);
	    tableModel.render();
	    return null;
	} else {
	    WorksheetPDFMappature.setTableProperties(tableModel);
	}
	String html = tableModel.render();
	request.setAttribute("saveResults", worksheetSaver.getSaveResults());
	return html;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }
}
