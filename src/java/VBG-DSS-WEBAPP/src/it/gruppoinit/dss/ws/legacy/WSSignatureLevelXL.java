package it.gruppoinit.dss.ws.legacy;

public class WSSignatureLevelXL {

    private String levelReached;
    private String certificateValuesVerification;
    private String revocationValuesVerification;

    public WSSignatureLevelXL() {}

    public String getLevelReached() { return levelReached; }
    public void setLevelReached(String levelReached) { this.levelReached = levelReached; }

    public String getCertificateValuesVerification() { return certificateValuesVerification; }
    public void setCertificateValuesVerification(String certificateValuesVerification) { this.certificateValuesVerification = certificateValuesVerification; }

    public String getRevocationValuesVerification() { return revocationValuesVerification; }
    public void setRevocationValuesVerification(String revocationValuesVerification) { this.revocationValuesVerification = revocationValuesVerification; }
}
