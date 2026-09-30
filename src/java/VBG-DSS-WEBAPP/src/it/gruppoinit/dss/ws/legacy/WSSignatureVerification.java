package it.gruppoinit.dss.ws.legacy;

import java.util.Date;

public class WSSignatureVerification {

    private String signatureVerificationResult;
    private String signatureAlgorithm;
    private String digestAlgorithm;
    private Date referenceTime;

    public WSSignatureVerification() {}

    public String getSignatureVerificationResult() { return signatureVerificationResult; }
    public void setSignatureVerificationResult(String signatureVerificationResult) { this.signatureVerificationResult = signatureVerificationResult; }

    public String getSignatureAlgorithm() { return signatureAlgorithm; }
    public void setSignatureAlgorithm(String signatureAlgorithm) { this.signatureAlgorithm = signatureAlgorithm; }

    public String getDigestAlgorithm() { return digestAlgorithm; }
    public void setDigestAlgorithm(String digestAlgorithm) { this.digestAlgorithm = digestAlgorithm; }

    public Date getReferenceTime() { return referenceTime; }
    public void setReferenceTime(Date referenceTime) { this.referenceTime = referenceTime; }
}
