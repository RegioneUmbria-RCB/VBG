package it.gruppoinit.pal.gp.core.rest.client.models.dss;

import java.io.Serializable;

public class SignatureSummaryItem implements Serializable {
	
        /**
        * 
        */
        private static final long serialVersionUID = -5069533176840700110L;
	
        private String signatureId;
	private String indication;
	private String subIndication;
	private String signatureFormat;
	private String signedBy;
	private String errorsSummary;
	private String warningsSummary;

	public String getSignatureId() {
		return signatureId;
	}

	public void setSignatureId(String signatureId) {
		this.signatureId = signatureId;
	}

	public String getIndication() {
		return indication;
	}

	public void setIndication(String indication) {
		this.indication = indication;
	}

	public String getSubIndication() {
		return subIndication;
	}

	public void setSubIndication(String subIndication) {
		this.subIndication = subIndication;
	}

	public String getSignatureFormat() {
		return signatureFormat;
	}

	public void setSignatureFormat(String signatureFormat) {
		this.signatureFormat = signatureFormat;
	}

	public String getSignedBy() {
		return signedBy;
	}

	public void setSignedBy(String signedBy) {
		this.signedBy = signedBy;
	}

	public String getErrorsSummary() {
		return errorsSummary;
	}

	public void setErrorsSummary(String errorsSummary) {
		this.errorsSummary = errorsSummary;
	}

	public String getWarningsSummary() {
		return warningsSummary;
	}

	public void setWarningsSummary(String warningsSummary) {
		this.warningsSummary = warningsSummary;
	}
}
