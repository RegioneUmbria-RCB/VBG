package it.gruppoinit.dss.ws.legacy;

public class WSCertificateVerification {

    private byte[] certificate;
    private String validityPeriodVerification;
    private WSSignatureVerification signatureVerification;
    private WSRevocationVerificationResult certificateStatus;

    public WSCertificateVerification() {}

    public byte[] getCertificate() { return certificate; }
    public void setCertificate(byte[] certificate) { this.certificate = certificate; }

    public String getValidityPeriodVerification() { return validityPeriodVerification; }
    public void setValidityPeriodVerification(String validityPeriodVerification) { this.validityPeriodVerification = validityPeriodVerification; }

    public WSSignatureVerification getSignatureVerification() { return signatureVerification; }
    public void setSignatureVerification(WSSignatureVerification signatureVerification) { this.signatureVerification = signatureVerification; }

    public WSRevocationVerificationResult getCertificateStatus() { return certificateStatus; }
    public void setCertificateStatus(WSRevocationVerificationResult certificateStatus) { this.certificateStatus = certificateStatus; }
}
