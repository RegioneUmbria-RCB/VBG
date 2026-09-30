package it.gruppoinit.dss.ws.legacy;

import java.util.List;

public class WSCertPathRevocationAnalysis {

    private String summary;
    private List<WSCertificateVerification> certificatePathVerification;
    private WSTrustedListInformation trustedListInformation;

    public WSCertPathRevocationAnalysis() {}

    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }

    public List<WSCertificateVerification> getCertificatePathVerification() { return certificatePathVerification; }
    public void setCertificatePathVerification(List<WSCertificateVerification> certificatePathVerification) { this.certificatePathVerification = certificatePathVerification; }

    public WSTrustedListInformation getTrustedListInformation() { return trustedListInformation; }
    public void setTrustedListInformation(WSTrustedListInformation trustedListInformation) { this.trustedListInformation = trustedListInformation; }
}
