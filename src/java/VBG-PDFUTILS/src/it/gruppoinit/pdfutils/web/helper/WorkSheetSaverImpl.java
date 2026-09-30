package it.gruppoinit.pdfutils.web.helper;

import it.gruppoinit.pdfutils.domain.PDFMappature;
import it.gruppoinit.pdfutils.service.PDFMappingService;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.jmesa.model.WorksheetSaver;
import org.jmesa.worksheet.Worksheet;
import org.jmesa.worksheet.WorksheetColumn;
import org.jmesa.worksheet.WorksheetRow;
import org.jmesa.worksheet.WorksheetRowStatus;

public class WorkSheetSaverImpl implements WorksheetSaver {

    private PDFMappingService pdfMappingService;
    private String alias;
    private StringBuffer saveResults = new StringBuffer();

    private WorkSheetSaverImpl() {

	super();
    }

    public WorkSheetSaverImpl(PDFMappingService pdfMappingService, String alias) {

	this();
	this.pdfMappingService = pdfMappingService;
	this.alias = alias;
    }

    public String getSaveResults() {

	return this.saveResults.toString();
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void saveWorksheet(Worksheet worksheet) {

	Iterator worksheetRows = worksheet.getRows().iterator();
	List<PDFMappature> configurazioneList = this.pdfMappingService.findAll(alias, null, null);
	Map configurazioniAsMap = new HashMap<String, PDFMappature>();
	for (PDFMappature configurazione : configurazioneList) {
	    configurazioniAsMap.put(String.valueOf(configurazione.getLabel()), configurazione);
	}
	while (worksheetRows.hasNext()) {
	    boolean valid = true;
	    WorksheetRow worksheetRow = (WorksheetRow) worksheetRows.next();
	    String uniqueValue = worksheetRow.getUniqueProperty().getValue();
	    String message = null;
	    if (worksheetRow.getRowStatus().equals(WorksheetRowStatus.ADD)) {
		PDFMappature newconfigurazione = new PDFMappature();
		String messaggioErrore = "";
		for (WorksheetColumn worksheetColumn : worksheetRow.getColumns()) {
		    valid = setProperty(worksheetColumn, newconfigurazione) & valid;
		    if (worksheetColumn.hasError()) {
			messaggioErrore += "<br />La colonna <b>" + worksheetColumn.getProperty() + "</b> riporta il seguente errore: ["
				+ worksheetColumn.getError() + "]";
		    }
		}
		if (valid) {
		    try {
			this.pdfMappingService.insert(alias, newconfigurazione);
			message = getSuccessHtml("Record salvato (id: " + newconfigurazione.getLabel() + ")");
		    } catch (Exception e) {
			message = getErrorHtml("Errore: Record non salvato " + e.getMessage());
			valid = false;
		    }
		} else {
		    message = getErrorHtml("Errore: Record non salvato (id: " + uniqueValue + "). " + messaggioErrore);
		}
	    } else if (worksheetRow.getRowStatus().equals(WorksheetRowStatus.MODIFY)) {
		PDFMappature configurazione = (PDFMappature) configurazioniAsMap.get(uniqueValue);
		String messaggioErrore = "";
		for (WorksheetColumn worksheetColumn : worksheetRow.getColumns()) {
		    valid = setProperty(worksheetColumn, configurazione) & valid;
		    if (worksheetColumn.hasError()) {
			messaggioErrore += "<br />La colonna <b>" + worksheetColumn.getProperty() + "</b> riporta il seguente errore: ["
				+ worksheetColumn.getError() + "]";
		    }
		}
		if (valid) {
		    try {
			this.pdfMappingService.update(alias, configurazione);
			message = getSuccessHtml("Record aggiornato (id: " + configurazione.getLabel() + ")");
		    } catch (Exception e) {
			message = getErrorHtml("Errore: Record non aggiornato " + e.getMessage());
			valid = false;
		    }
		} else {
		    message = getErrorHtml("Errore: Record non aggiornato (id: " + uniqueValue + "). " + messaggioErrore);
		}
	    } else if (worksheetRow.getRowStatus().equals(WorksheetRowStatus.REMOVE)) {
		valid = true;
		// PDFMappature configurazione = (PDFMappature) configurazioniAsMap.get(uniqueValue);
		this.pdfMappingService.delete(alias, uniqueValue);
		message = getSuccessHtml("Record cancellato (id: " + uniqueValue + ")");
	    }
	    if (valid) {
		worksheetRows.remove();
	    }
	    this.saveResults.append(message);
	}
    }

    private boolean setProperty(WorksheetColumn worksheetColumn, PDFMappature configurazione) {

	worksheetColumn.removeError();
	String property = worksheetColumn.getProperty();
	String changedValue = worksheetColumn.getChangedValue();
	if (property.equals("remove")) {
	    return true;
	}
	try {
	    Set<String> valoriAmmessi = new TreeSet<String>();
	    boolean required = true;
	    if (worksheetColumn.hasError()) {
		return false;
	    }
	    int maxLength = 0;
	    if (property.equalsIgnoreCase("label")) {
		maxLength = 500;
	    }
	    if (property.equalsIgnoreCase("ambito")) {
		maxLength = 30;
	    }
	    if (property.equalsIgnoreCase("xpath")) {
		maxLength = 4000;
	    }
	    if (property.equalsIgnoreCase("decodTipo")) {
		required = false;
		valoriAmmessi = valoriAmmessiDecodTipo;
		maxLength = 2;
	    }
	    if (property.equalsIgnoreCase("decodFormatoInput")) {
		required = false;
		maxLength = 4000;
	    }
	    if (property.equalsIgnoreCase("decodFormatoOutput")) {
		required = false;
		maxLength = 4000;
	    }
	    validateColumn(worksheetColumn, changedValue, maxLength, valoriAmmessi, required);
	    if (worksheetColumn.hasError()) {
		return false;
	    }
	    PropertyUtils.setProperty(configurazione, property, changedValue);
	} catch (Exception ex) {
	    ex.printStackTrace();
	    worksheetColumn.setError("Errore imprevisto");
	    return false;
	}
	return true;
    }

    public static Set<String> valoriAmmessiDecodTipo = new TreeSet<String>();
    static {
	valoriAmmessiDecodTipo.add("RE");
	valoriAmmessiDecodTipo.add("JS");
	valoriAmmessiDecodTipo.add("DV");
	valoriAmmessiDecodTipo.add("PF");
    }

    private void validateColumn(WorksheetColumn worksheetColumn, String changedValue, int maxLength, Set<String> valoriAmmessi, boolean required) {

	boolean isError = false;
	if (required) {
	    if (StringUtils.isBlank(changedValue)) {
		worksheetColumn.setError("Campo obbligatorio");
		isError = true;
	    }
	}
	if (changedValue.length() > maxLength) {
	    worksheetColumn.setError("Il campo accetta solamente " + maxLength + " caratteri.");
	    isError = true;
	}
	if (!valoriAmmessi.isEmpty()) {
	    if (StringUtils.isNotBlank(StringUtils.defaultString(changedValue))) {
		if (!valoriAmmessi.contains(StringUtils.defaultString(changedValue))) {
		    String valori = "";
		    for (String valore : valoriAmmessi) {
			valori += valore + ",";
		    }
		    worksheetColumn.setError("Il campo accetta solamente i valori " + valori);
		    isError = true;
		}
	    }
	}
	if (!isError) {
	    worksheetColumn.removeError();
	}
    }

    private String getSuccessHtml(String message) {

	return "<span style=\"color:green\">" + message + "</span><br/>";
    }

    private String getErrorHtml(String message) {

	return "<span style=\"color:red\">" + message + "</span><br/>";
    }
}
