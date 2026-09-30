package it.gruppoinit.dss.validation;

import java.util.ArrayList;
import java.util.List;

/**
 * Risposta JSON dell'API di validazione (POST /validazione/api).
 * Usata dalle chiamate esterne; non include il contenuto estratto (byte[]).
 */
public class ValidationApiResponse {

	private List<ValidationApiResultItem> results = new ArrayList<>();

	public List<ValidationApiResultItem> getResults() {
		return results;
	}

	public void setResults(List<ValidationApiResultItem> results) {
		this.results = results != null ? results : new ArrayList<>();
	}

	/**
	 * Singolo risultato per file validato.
	 */
	public static class ValidationApiResultItem {
		private String fileName;
		private boolean valid;
		private String validationErrorMessage;
		private SimpleReportSummary summary;
		private String simpleReportXml;
		private String detailedReportXml;

		public String getFileName() {
			return fileName;
		}

		public void setFileName(String fileName) {
			this.fileName = fileName;
		}

		public boolean isValid() {
			return valid;
		}

		public void setValid(boolean valid) {
			this.valid = valid;
		}

		public String getValidationErrorMessage() {
			return validationErrorMessage;
		}

		public void setValidationErrorMessage(String validationErrorMessage) {
			this.validationErrorMessage = validationErrorMessage;
		}

		public SimpleReportSummary getSummary() {
			return summary;
		}

		public void setSummary(SimpleReportSummary summary) {
			this.summary = summary;
		}

		public String getSimpleReportXml() {
			return simpleReportXml;
		}

		public void setSimpleReportXml(String simpleReportXml) {
			this.simpleReportXml = simpleReportXml;
		}

		public String getDetailedReportXml() {
			return detailedReportXml;
		}

		public void setDetailedReportXml(String detailedReportXml) {
			this.detailedReportXml = detailedReportXml;
		}
	}
}
