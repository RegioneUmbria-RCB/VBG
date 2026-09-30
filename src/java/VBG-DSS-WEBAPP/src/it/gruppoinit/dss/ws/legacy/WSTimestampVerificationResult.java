package it.gruppoinit.dss.ws.legacy;

import java.util.Date;

public class WSTimestampVerificationResult {

    private String sameDigest;
    private String certPathVerification;
    private String signatureAlgorithm;
    private String serialNumber;
    private Date creationTime;
    private String issuerName;

    public WSTimestampVerificationResult() {}

    public String getSameDigest() { return sameDigest; }
    public void setSameDigest(String sameDigest) { this.sameDigest = sameDigest; }

    public String getCertPathVerification() { return certPathVerification; }
    public void setCertPathVerification(String certPathVerification) { this.certPathVerification = certPathVerification; }

    public String getSignatureAlgorithm() { return signatureAlgorithm; }
    public void setSignatureAlgorithm(String signatureAlgorithm) { this.signatureAlgorithm = signatureAlgorithm; }

    public String getSerialNumber() { return serialNumber; }
    public void setSerialNumber(String serialNumber) { this.serialNumber = serialNumber; }

    public Date getCreationTime() { return creationTime; }
    public void setCreationTime(Date creationTime) { this.creationTime = creationTime; }

    public String getIssuerName() { return issuerName; }
    public void setIssuerName(String issuerName) { this.issuerName = issuerName; }
}
