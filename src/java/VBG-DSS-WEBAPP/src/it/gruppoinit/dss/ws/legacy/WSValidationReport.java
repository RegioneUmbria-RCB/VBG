package it.gruppoinit.dss.ws.legacy;

import java.util.List;

public class WSValidationReport {

    private WSTimeInformation timeInformation;
    private List<WSSignatureInformation> signatureInformationList;
    private List<WSTimestampVerificationResult> detachedTsVerificationResult;
    private WSDocument content;

    public WSValidationReport() {}

    public WSTimeInformation getTimeInformation() { return timeInformation; }
    public void setTimeInformation(WSTimeInformation timeInformation) { this.timeInformation = timeInformation; }

    public List<WSSignatureInformation> getSignatureInformationList() { return signatureInformationList; }
    public void setSignatureInformationList(List<WSSignatureInformation> signatureInformationList) { this.signatureInformationList = signatureInformationList; }

    public List<WSTimestampVerificationResult> getDetachedTsVerificationResult() { return detachedTsVerificationResult; }
    public void setDetachedTsVerificationResult(List<WSTimestampVerificationResult> detachedTsVerificationResult) { this.detachedTsVerificationResult = detachedTsVerificationResult; }

    public WSDocument getContent() { return content; }
    public void setContent(WSDocument content) { this.content = content; }
}
