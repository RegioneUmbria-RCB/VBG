package it.gruppoinit.dss.ws.legacy;

import java.util.List;

public class WSSignatureLevelT {

    private String levelReached;
    private List<WSTimestampVerificationResult> signatureTimestampsVerification;

    public WSSignatureLevelT() {}

    public String getLevelReached() { return levelReached; }
    public void setLevelReached(String levelReached) { this.levelReached = levelReached; }

    public List<WSTimestampVerificationResult> getSignatureTimestampsVerification() { return signatureTimestampsVerification; }
    public void setSignatureTimestampsVerification(List<WSTimestampVerificationResult> signatureTimestampsVerification) { this.signatureTimestampsVerification = signatureTimestampsVerification; }
}
