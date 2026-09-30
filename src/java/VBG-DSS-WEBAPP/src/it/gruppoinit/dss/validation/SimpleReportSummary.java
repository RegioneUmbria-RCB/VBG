package it.gruppoinit.dss.validation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Riepilogo leggibile del Simple Report DSS per la UI.
 */
public class SimpleReportSummary implements Serializable {

	private static final long serialVersionUID = 1L;

	private String documentName;
	private String validationTime;
	private String policyName;
	private int validSignaturesCount;
	private int signaturesCount;
	private List<SignatureSummaryItem> signatureSummaries = new ArrayList<>();

	public String getDocumentName() {
		return documentName;
	}

	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}

	public String getValidationTime() {
		return validationTime;
	}

	public void setValidationTime(String validationTime) {
		this.validationTime = validationTime;
	}

	public String getPolicyName() {
		return policyName;
	}

	public void setPolicyName(String policyName) {
		this.policyName = policyName;
	}

	public int getValidSignaturesCount() {
		return validSignaturesCount;
	}

	public void setValidSignaturesCount(int validSignaturesCount) {
		this.validSignaturesCount = validSignaturesCount;
	}

	public int getSignaturesCount() {
		return signaturesCount;
	}

	public void setSignaturesCount(int signaturesCount) {
		this.signaturesCount = signaturesCount;
	}

	public List<SignatureSummaryItem> getSignatureSummaries() {
		return signatureSummaries;
	}

	public void setSignatureSummaries(List<SignatureSummaryItem> signatureSummaries) {
		this.signatureSummaries = signatureSummaries != null ? signatureSummaries : new ArrayList<>();
	}
}
