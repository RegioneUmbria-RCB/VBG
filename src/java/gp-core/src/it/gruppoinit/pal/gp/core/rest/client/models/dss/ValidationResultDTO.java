package it.gruppoinit.pal.gp.core.rest.client.models.dss;

import java.io.Serializable;

public class ValidationResultDTO implements Serializable {

	

        /**
        * 
        */
        private static final long serialVersionUID = 1007548535285691377L;
	
        private String simpleReportXml;
	private String detailedReportXml;
	private String diagnosticDataXml;
	private SimpleReportSummary simpleReportSummary;
	private byte[] extractedContent;
	private String extractedContentFileName;
	private String validationErrorMessage;

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

	public String getDiagnosticDataXml() {
		return diagnosticDataXml;
	}

	public void setDiagnosticDataXml(String diagnosticDataXml) {
		this.diagnosticDataXml = diagnosticDataXml;
	}

	public SimpleReportSummary getSimpleReportSummary() {
		return simpleReportSummary;
	}

	public void setSimpleReportSummary(SimpleReportSummary simpleReportSummary) {
		this.simpleReportSummary = simpleReportSummary;
	}

	public byte[] getExtractedContent() {
		return extractedContent;
	}

	public void setExtractedContent(byte[] extractedContent) {
		this.extractedContent = extractedContent;
	}

	public String getExtractedContentFileName() {
		return extractedContentFileName;
	}

	public void setExtractedContentFileName(String extractedContentFileName) {
		this.extractedContentFileName = extractedContentFileName;
	}

	public String getValidationErrorMessage() {
		return validationErrorMessage;
	}

	public void setValidationErrorMessage(String validationErrorMessage) {
		this.validationErrorMessage = validationErrorMessage;
	}

	public boolean hasError() {
		return validationErrorMessage != null && !validationErrorMessage.isEmpty();
	}

	/** Per accesso da JSP EL */
	public boolean getHasError() {
		return hasError();
	}
}
