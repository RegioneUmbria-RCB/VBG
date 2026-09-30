package it.gruppoinit.pal.gp.core.domain.helper;

import it.gruppoinit.dss.wsclient.WsValidationReport;
import it.init.sigepro.rte.types.ErroreType;

import java.util.List;

public class ReportValidazioneFirmaDigitale {

    public static enum ESITO_VERIFICA {
	VALIDO, NON_VALIDO, NON_FIRMATO, REVOCATO, ERRORE, VERIFICA_REVOCA_NON_DETERMINATO
    };

    private String fileName;
    private WsValidationReport wsValidationReport;
    private List<ErroreType> errors;
    private List<ErroreType> warnings;

    public ReportValidazioneFirmaDigitale(String fileName, WsValidationReport wsValidationReport, List<ErroreType> errors, List<ErroreType> warnings) {

	this.fileName = fileName;
	this.wsValidationReport = wsValidationReport;
	this.errors = errors;
	this.warnings = warnings;
    }

    public String getFileName() {

	return fileName;
    }

    public WsValidationReport getWsValidationReport() {

	return wsValidationReport;
    }

    public List<ErroreType> getErrors() {

	return errors;
    }

    public List<ErroreType> getWarnings() {

	return warnings;
    }
}
