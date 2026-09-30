package it.gruppoinit.stc.web.helper;

import it.gruppoinit.stc.domain.Configurazione;
import it.gruppoinit.stc.service.ConfigurazioneService;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.jmesa.model.WorksheetSaver;
import org.jmesa.worksheet.Worksheet;
import org.jmesa.worksheet.WorksheetColumn;
import org.jmesa.worksheet.WorksheetRow;
import org.jmesa.worksheet.WorksheetRowStatus;

public class WorksheetSaverImpl implements WorksheetSaver {

    private ConfigurazioneService configurazioneService;
    private StringBuffer saveResults = new StringBuffer();

    public WorksheetSaverImpl(ConfigurazioneService configurazioneService) {

	this.configurazioneService = configurazioneService;
    }

    public String getSaveResults() {

	return this.saveResults.toString();
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void saveWorksheet(Worksheet worksheet) {

	Iterator worksheetRows = worksheet.getRows().iterator();
	List<Configurazione> configurazioneList = this.configurazioneService.findAll(null, null);
	Map configurazioniAsMap = new HashMap<String, Configurazione>();
	for (Configurazione configurazione : configurazioneList) {
	    configurazioniAsMap.put(String.valueOf(configurazione.getIdnodo()), configurazione);
	}
	while (worksheetRows.hasNext()) {
	    boolean valid = true;
	    WorksheetRow worksheetRow = (WorksheetRow) worksheetRows.next();
	    String uniqueValue = worksheetRow.getUniqueProperty().getValue();
	    String message = null;
	    if (worksheetRow.getRowStatus().equals(WorksheetRowStatus.ADD)) {
		Configurazione newconfigurazione = new Configurazione();
		for (WorksheetColumn worksheetColumn : worksheetRow.getColumns()) {
		    valid = setProperty(worksheetColumn, newconfigurazione) & valid;
		}
		if (valid) {
		    try {
			this.configurazioneService.insert(newconfigurazione);
			message = getSuccessHtml("Record salvato (id: " + newconfigurazione.getIdnodo() + ")");
		    } catch (Exception e) {
			message = getErrorHtml("Errore: Record non salvato " + e.getMessage());
			valid = false;
		    }
		} else {
		    message = getErrorHtml("Errore: Record non salvato (id: " + uniqueValue + ")");
		}
	    } else if (worksheetRow.getRowStatus().equals(WorksheetRowStatus.MODIFY)) {
		Configurazione configurazione = (Configurazione) configurazioniAsMap.get(uniqueValue);
		for (WorksheetColumn worksheetColumn : worksheetRow.getColumns()) {
		    valid = setProperty(worksheetColumn, configurazione) & valid;
		}
		if (valid) {
		    try {
			this.configurazioneService.update(configurazione);
			message = getSuccessHtml("Record aggiornato (id: " + configurazione.getIdnodo() + ")");
		    } catch (Exception e) {
			message = getErrorHtml("Errore: Record non aggiornato " + e.getMessage());
			valid = false;
		    }
		} else {
		    message = getErrorHtml("Errore: Record non aggiornato (id: " + uniqueValue + ")");
		}
	    } else if (worksheetRow.getRowStatus().equals(WorksheetRowStatus.REMOVE)) {
		valid = true;
		Configurazione configurazione = (Configurazione) configurazioniAsMap.get(uniqueValue);
		this.configurazioneService.delete(configurazione);
		message = getSuccessHtml("Record cancellato (id: " + uniqueValue + ")");
	    }
	    if (valid) {
		worksheetRows.remove();
	    }
	    this.saveResults.append(message);
	}
    }

    private boolean setProperty(WorksheetColumn worksheetColumn, Configurazione configurazione) {

	worksheetColumn.removeError();
	String property = worksheetColumn.getProperty();
	String changedValue = worksheetColumn.getChangedValue();
	if (property.equals("remove") || property.equals("test")) {
	    return true;
	}
	try {
	    if (worksheetColumn.getProperty().equals("selected")) {
		if (changedValue.equals("checked")) {
		    PropertyUtils.setProperty(configurazione, property, "y");
		} else {
		    PropertyUtils.setProperty(configurazione, property, "n");
		}
	    } else {
		if (worksheetColumn.hasError()) {
		    return false;
		}
		validateColumn(worksheetColumn, changedValue);
		if (worksheetColumn.hasError()) {
		    return false;
		}
		if (property.equals("idnodo")) {
		    PropertyUtils.setProperty(configurazione, property, Integer.valueOf(changedValue));
		} else {
		    PropertyUtils.setProperty(configurazione, property, changedValue);
		}
	    }
	} catch (Exception ex) {
	    ex.printStackTrace();
	    worksheetColumn.setError("Errore imprevisto");
	    return false;
	}
	return true;
    }

    private void validateColumn(WorksheetColumn worksheetColumn, String changedValue) {

	if (StringUtils.isBlank(changedValue)) {
	    worksheetColumn.setError("Campo obbligatorio");
	} else {
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
