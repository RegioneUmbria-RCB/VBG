package it.gruppoinit.dss.ws.legacy;

import java.util.List;

public class WSSignatureLevelX {

    private String levelReached;
    private List<WSTimestampVerificationResult> signatureAndRefsTimestampsVerification;
    private List<WSTimestampVerificationResult> referencesTimestampsVerification;

    public WSSignatureLevelX() {}

    public String getLevelReached() { return levelReached; }
    public void setLevelReached(String levelReached) { this.levelReached = levelReached; }

    public List<WSTimestampVerificationResult> getSignatureAndRefsTimestampsVerification() { return signatureAndRefsTimestampsVerification; }
    public void setSignatureAndRefsTimestampsVerification(List<WSTimestampVerificationResult> signatureAndRefsTimestampsVerification) { this.signatureAndRefsTimestampsVerification = signatureAndRefsTimestampsVerification; }

    public List<WSTimestampVerificationResult> getReferencesTimestampsVerification() { return referencesTimestampsVerification; }
    public void setReferencesTimestampsVerification(List<WSTimestampVerificationResult> referencesTimestampsVerification) { this.referencesTimestampsVerification = referencesTimestampsVerification; }
}
