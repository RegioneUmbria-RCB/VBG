package it.gruppoinit.dss.ws.legacy;

public class WSSignatureLevelC {

    private String levelReached;
    private String certificateRefsVerification;
    private String revocationRefsVerification;

    public WSSignatureLevelC() {}

    public String getLevelReached() { return levelReached; }
    public void setLevelReached(String levelReached) { this.levelReached = levelReached; }

    public String getCertificateRefsVerification() { return certificateRefsVerification; }
    public void setCertificateRefsVerification(String certificateRefsVerification) { this.certificateRefsVerification = certificateRefsVerification; }

    public String getRevocationRefsVerification() { return revocationRefsVerification; }
    public void setRevocationRefsVerification(String revocationRefsVerification) { this.revocationRefsVerification = revocationRefsVerification; }
}
