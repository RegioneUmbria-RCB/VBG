package it.gruppoinit.dss.ws.legacy;

public class WSSignatureInformation {

    private WSSignatureVerification signatureVerification;
    private WSCertPathRevocationAnalysis certPathRevocationAnalysis;
    private WSSignatureLevelAnalysis signatureLevelAnalysis;
    private WSQualificationsVerification qualificationsVerification;
    private WSQCStatementInformation qcStatementInformation;
    private String finalConclusion;

    public WSSignatureInformation() {}

    public WSSignatureVerification getSignatureVerification() { return signatureVerification; }
    public void setSignatureVerification(WSSignatureVerification signatureVerification) { this.signatureVerification = signatureVerification; }

    public WSCertPathRevocationAnalysis getCertPathRevocationAnalysis() { return certPathRevocationAnalysis; }
    public void setCertPathRevocationAnalysis(WSCertPathRevocationAnalysis certPathRevocationAnalysis) { this.certPathRevocationAnalysis = certPathRevocationAnalysis; }

    public WSSignatureLevelAnalysis getSignatureLevelAnalysis() { return signatureLevelAnalysis; }
    public void setSignatureLevelAnalysis(WSSignatureLevelAnalysis signatureLevelAnalysis) { this.signatureLevelAnalysis = signatureLevelAnalysis; }

    public WSQualificationsVerification getQualificationsVerification() { return qualificationsVerification; }
    public void setQualificationsVerification(WSQualificationsVerification qualificationsVerification) { this.qualificationsVerification = qualificationsVerification; }

    public WSQCStatementInformation getQcStatementInformation() { return qcStatementInformation; }
    public void setQcStatementInformation(WSQCStatementInformation qcStatementInformation) { this.qcStatementInformation = qcStatementInformation; }

    public String getFinalConclusion() { return finalConclusion; }
    public void setFinalConclusion(String finalConclusion) { this.finalConclusion = finalConclusion; }
}
