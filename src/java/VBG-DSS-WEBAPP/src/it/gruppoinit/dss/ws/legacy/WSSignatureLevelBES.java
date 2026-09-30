package it.gruppoinit.dss.ws.legacy;

import java.util.List;

public class WSSignatureLevelBES {

    private String levelReached;
    private String signingCertRefVerification;
    private List<byte[]> certificates;
    private byte[] signingCertificate;
    private WSSignatureInformation[] counterSignatureVerification;

    public WSSignatureLevelBES() {}

    public String getLevelReached() { return levelReached; }
    public void setLevelReached(String levelReached) { this.levelReached = levelReached; }

    public String getSigningCertRefVerification() { return signingCertRefVerification; }
    public void setSigningCertRefVerification(String signingCertRefVerification) { this.signingCertRefVerification = signingCertRefVerification; }

    public List<byte[]> getCertificates() { return certificates; }
    public void setCertificates(List<byte[]> certificates) { this.certificates = certificates; }

    public byte[] getSigningCertificate() { return signingCertificate; }
    public void setSigningCertificate(byte[] signingCertificate) { this.signingCertificate = signingCertificate; }

    public WSSignatureInformation[] getCounterSignatureVerification() { return counterSignatureVerification; }
    public void setCounterSignatureVerification(WSSignatureInformation[] counterSignatureVerification) { this.counterSignatureVerification = counterSignatureVerification; }
}
