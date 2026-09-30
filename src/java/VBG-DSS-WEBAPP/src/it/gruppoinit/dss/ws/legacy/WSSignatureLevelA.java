package it.gruppoinit.dss.ws.legacy;

import java.util.List;

public class WSSignatureLevelA {

    private String levelReached;
    private List<WSTimestampVerificationResult> archiveTimestampsVerification;

    public WSSignatureLevelA() {}

    public String getLevelReached() { return levelReached; }
    public void setLevelReached(String levelReached) { this.levelReached = levelReached; }

    public List<WSTimestampVerificationResult> getArchiveTimestampsVerification() { return archiveTimestampsVerification; }
    public void setArchiveTimestampsVerification(List<WSTimestampVerificationResult> archiveTimestampsVerification) { this.archiveTimestampsVerification = archiveTimestampsVerification; }
}
